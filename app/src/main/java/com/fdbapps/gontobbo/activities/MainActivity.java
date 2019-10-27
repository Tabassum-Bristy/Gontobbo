  package com.fdbapps.gontobbo.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fdbapps.gontobbo.R;
import com.fdbapps.gontobbo.utilities.GPSTracker;

public class MainActivity extends AppCompatActivity {

    Button btnMyLocation, btnTourismSpots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        2);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // request for gps update
        GPSTracker gpsTracker = new GPSTracker(this);
        gpsTracker.getLocation();

        btnMyLocation = (Button) findViewById(R.id.btnMyLocation);
        btnTourismSpots = (Button) findViewById(R.id.btnTourismSpots);


        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyLocationActivity.class);
                startActivity(intent);
            }
        });


        btnTourismSpots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TourismSpotsActivity.class);
                startActivity(intent);
            }
        });


    }
}
