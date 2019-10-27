package com.fdbapps.gontobbo.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fdbapps.gontobbo.R;
import com.fdbapps.gontobbo.data.DatabaseHelper;
import com.fdbapps.gontobbo.models.Hospital;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HospitalDetailsActivity extends AppCompatActivity {

    MapView mapView;
    GoogleMap map;

    Hospital hospital;
    TextView tvHospitalTitle, tvHospitalAddress, tvHospitalPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);

        int id = getIntent().getIntExtra("id", 0);

        DatabaseHelper helper = new DatabaseHelper(this);
        helper.getReadableDatabase();
        hospital = helper.getHospital(id);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Hospital Details");
        actionBar.setDisplayHomeAsUpEnabled(true);


        tvHospitalTitle = (TextView) findViewById(R.id.tvHospitalTitle);
        tvHospitalAddress = (TextView) findViewById(R.id.tvHospitalAddress);
        tvHospitalPhoneNumber = (TextView) findViewById(R.id.tvHospitalPhoneNumber);

        tvHospitalTitle.setText(hospital.getTitle());
        tvHospitalAddress.setText(hospital.getAddress());
        tvHospitalPhoneNumber.setText(hospital.getPhoneNumber());

        Button btnShowRoute = (Button) findViewById(R.id.btnShowRoute);

        btnShowRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+ hospital.getLongitude() + ","+ hospital.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


        // initialize mapview
        mapView = (MapView) findViewById(R.id.mapView);

        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.getUiSettings().setMyLocationButtonEnabled(false);

                //MapsInitializer.initialize(getApplicationContext());

                LatLng pos = new LatLng(hospital.getLatitude(), hospital.getLongitude());
                map.addMarker(new MarkerOptions().position(pos).title(hospital.getTitle()));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(pos, 14);
                map.animateCamera(cameraUpdate);
            }
        });



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
