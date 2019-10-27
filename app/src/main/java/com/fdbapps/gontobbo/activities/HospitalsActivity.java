package com.fdbapps.gontobbo.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.fdbapps.gontobbo.R;
import com.fdbapps.gontobbo.adapters.HospitalsAdapter;
import com.fdbapps.gontobbo.data.DatabaseHelper;
import com.fdbapps.gontobbo.models.Hospital;
import com.fdbapps.gontobbo.models.Hotel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class HospitalsActivity extends AppCompatActivity {


    ListView hospitalListView;
    ArrayList<Hospital> hospitals;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);


        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);

        DatabaseHelper helper = new DatabaseHelper(this);
        helper.getReadableDatabase();
        hospitals = helper.getListHospitals(new LatLng(latitude, longitude));


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Hospitals");
        actionBar.setDisplayHomeAsUpEnabled(true);


        final HospitalsAdapter adapter = new HospitalsAdapter(hospitals, this);

        hospitalListView = (ListView) findViewById(R.id.hospitalListView);
        hospitalListView.setAdapter(adapter);

        hospitalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hospital hospital = hospitals.get(position);
                Intent intent = new Intent(HospitalsActivity.this, HospitalDetailsActivity.class);

                intent.putExtra("id", hospital.getId());

                startActivity(intent);
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
