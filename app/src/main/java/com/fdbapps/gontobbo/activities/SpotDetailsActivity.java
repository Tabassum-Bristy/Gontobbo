package com.fdbapps.gontobbo.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fdbapps.gontobbo.R;
import com.fdbapps.gontobbo.data.DatabaseHelper;
import com.fdbapps.gontobbo.models.TourismSpot;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SpotDetailsActivity extends AppCompatActivity {

    private MapView mapView;
    private GoogleMap map;
    private ImageView imgPlace;
    private TextView txtDescription;
    private DatabaseHelper dbHelper;

    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_details);

        // collect item from db
        int id = getIntent().getIntExtra("id", 1);

        dbHelper = new DatabaseHelper(this);
        dbHelper.getReadableDatabase();
        final TourismSpot spot = dbHelper.getTourismSpot(id);

        latitude = spot.getLatitude();
        longitude = spot.getLongitude();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(spot.getTitle());
        actionBar.setDisplayHomeAsUpEnabled(true);

        // set image
        imgPlace = (ImageView) findViewById(R.id.imgPlace);
       // int imageId = getResources().getIdentifier(spot.getImageName(), "drawable", getPackageName());
        imgPlace.setImageBitmap(BitmapFactory.decodeByteArray(spot.getImageName(), 0, spot.getImageName().length));



        // set description
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtDescription.setText(spot.getDescription());




        Button btnNearestHotels = (Button) findViewById(R.id.btnNearestHotels);
        Button btnNearestHospitals = (Button) findViewById(R.id.btnNearestHospitals);
        Button btnEmergencyContacts = (Button) findViewById(R.id.btnEmergencyContacts) ;
        Button btnShowRoute = (Button) findViewById(R.id.btnShowRoute);

        btnShowRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+latitude + ","+longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });



        btnNearestHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpotDetailsActivity.this, HotelsActivity.class);

                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);

                startActivity(intent);
            }
        });


        btnNearestHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpotDetailsActivity.this, HospitalsActivity.class);

                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);

                startActivity(intent);
            }
        });

        btnEmergencyContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpotDetailsActivity.this, EmergencyContactsActivity.class);
                //Toast.makeText(getApplicationContext(), "District " + spot.getDistrict(), Toast.LENGTH_SHORT).show();
                intent.putExtra("district", spot.getDistrict());

                startActivity(intent);
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

                MapsInitializer.initialize(getApplicationContext());

                LatLng pos = new LatLng(spot.getLatitude(),spot.getLongitude());
                map.addMarker(new MarkerOptions().position(pos).title(spot.getTitle()));
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
