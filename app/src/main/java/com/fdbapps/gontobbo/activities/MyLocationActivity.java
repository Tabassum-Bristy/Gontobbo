package com.fdbapps.gontobbo.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fdbapps.gontobbo.R;
import com.fdbapps.gontobbo.utilities.GPSTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class MyLocationActivity extends AppCompatActivity {

    Button btnNearestTourismSpots, btnNearestHotels, btnNearestHospitals, btnEmergencyContacts;

    private GoogleMap map;
    private GPSTracker gpsTracker;
    //private ProgressDialog progressDialog;

    MapView mapView;

    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        2);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("My Location");
        actionBar.setDisplayHomeAsUpEnabled(true);

        mapView = (MapView) findViewById(R.id.mapView);

        btnNearestTourismSpots = (Button) findViewById(R.id.btnNearestTourismSpots);
        btnNearestHotels = (Button) findViewById(R.id.btnNearestHotels);
        btnNearestHospitals = (Button) findViewById(R.id.btnNearestHospitals);
        btnEmergencyContacts = (Button) findViewById(R.id.btnEmergencyContacts);

        btnNearestTourismSpots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyLocationActivity.this, TourismSpotsActivity.class);

                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);


                startActivity(intent);
            }
        });

        btnNearestHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyLocationActivity.this, HotelsActivity.class);

                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);


                startActivity(intent);
            }
        });


        btnNearestHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyLocationActivity.this, HospitalsActivity.class);

                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);

                startActivity(intent);
            }
        });

        // check if GPS is enabled
        gpsTracker = new GPSTracker(this);

        btnEmergencyContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyLocationActivity.this, EmergencyContactsActivity.class);

                intent.putExtra("district", gpsTracker.getDistrict(MyLocationActivity.this));


                startActivity(intent);
            }
        });



//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please wait while we are locating your location...");
//        progressDialog.show();

        // disable buttons
        btnEmergencyContacts.setEnabled(false);
        btnNearestHospitals.setEnabled(false);
        btnNearestHotels.setEnabled(false);
        btnNearestTourismSpots.setEnabled(false);

        if (gpsTracker.getIsGPSTrackingEnabled()) {

            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();


            mapView.onCreate(savedInstanceState);

            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    gpsTracker.getLocation();

                    map = googleMap;
                    map.getUiSettings().setMyLocationButtonEnabled(true);

                    latitude = gpsTracker.getLatitude();
                    longitude = gpsTracker.getLongitude();

                    MapsInitializer.initialize(getApplicationContext());

                    LatLng pos = new LatLng(latitude, longitude);
                    map.addMarker(new MarkerOptions().position(pos).title("My Location"));
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(pos, 16);
                    map.animateCamera(cameraUpdate);
                }
            });


            btnEmergencyContacts.setEnabled(true);
            btnNearestHospitals.setEnabled(true);
            btnNearestHotels.setEnabled(true);
            btnNearestTourismSpots.setEnabled(true);

            Toast.makeText(this, "Lat " + latitude + " Long " + longitude, Toast.LENGTH_LONG).show();
            Toast.makeText(this, "District: " + gpsTracker.getDistrict(this), Toast.LENGTH_LONG).show();
        } else {
            //if gps is not enable,then it will give alert
            gpsTracker.showSettingsAlert();
        }
    }

//    public void stopProgressBar() {
//        progressDialog.hide();
//    }


//for back button
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
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
