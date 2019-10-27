package com.fdbapps.gontobbo.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdbapps.gontobbo.R;
import com.fdbapps.gontobbo.data.DatabaseHelper;
import com.fdbapps.gontobbo.models.Hotel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class HotelDetailsActivity extends AppCompatActivity {

    MapView mapView;
    GoogleMap map;
    TextView tvDescription, tvRoomFair, tvPhoneNumber;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);

        int id = getIntent().getIntExtra("id", 0);

        DatabaseHelper helper = new DatabaseHelper(this);
        helper.getReadableDatabase();
        final Hotel hotel = helper.getHotel(id);


        // Set title of the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(hotel.getTitle());

        // this will show a back button on action bar
        actionBar.setDisplayHomeAsUpEnabled(true);


        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvRoomFair = (TextView) findViewById(R.id.tvRoomFairs);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);

        imageView = (ImageView) findViewById(R.id.imageView);

        tvDescription.setText(hotel.getDescription());
        tvRoomFair.setText(hotel.getRoomFairs());
        tvPhoneNumber.setText(hotel.getPhoneNumber());

        // set image
       // int imageId = getResources().getIdentifier(hotel.getImageName(), "drawable", getPackageName());
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(hotel.getImageName(), 0, hotel.getImageName().length));

        Button btnShowRoute = (Button) findViewById(R.id.btnShowRoute);

        btnShowRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+ hotel.getLatitude() + ","+ hotel.getLongitude());
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

                LatLng pos = new LatLng(hotel.getLatitude(), hotel.getLongitude());
                map.addMarker(new MarkerOptions().position(pos).title("Diaz Hotel And Resort"));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(pos, 14);
                map.animateCamera(cameraUpdate);

                // added mapView onResume
                mapView.onResume();
            }
        });
    }

    // this will make the back button to go back to previous activity
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

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
