package com.fdbapps.gontobbo.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.fdbapps.gontobbo.R;
import com.fdbapps.gontobbo.adapters.EmergencyContactsAdapter;
import com.fdbapps.gontobbo.data.DatabaseHelper;
import com.fdbapps.gontobbo.models.EmergencyContact;

import java.util.ArrayList;

public class EmergencyContactsActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<EmergencyContact> contacts;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);

        String district = getIntent().getStringExtra("district");

        DatabaseHelper helper = new DatabaseHelper(this);
        helper.getReadableDatabase();
        contacts = helper.getListEmergencyContact(district);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Emergency Contacts");
        actionBar.setDisplayHomeAsUpEnabled(true);

        final EmergencyContactsAdapter adapter = new EmergencyContactsAdapter(contacts, this);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final EmergencyContact contact = contacts.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(EmergencyContactsActivity.this);
                builder.setMessage("Do you want to make a call to " + contact.getPhoneNumber() + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(isPermissionGranted()) {
                            Intent intent = new Intent(Intent.ACTION_CALL);

                            intent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));

                            startActivity(intent);
                        }
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do notheing
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

        searchView = (SearchView) findViewById(R.id.searchView);

        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Here...");
        searchView.setFocusable(false);
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });


    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
            //    Log.v("TAG","Permission is granted");
                return true;
            } else {

              //  Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
           // Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   // Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                   // call_action();
                } else {
                   // Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }




    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }



}
