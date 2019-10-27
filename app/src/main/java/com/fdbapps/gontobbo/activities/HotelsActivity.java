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
import com.fdbapps.gontobbo.adapters.HotelsAdapter;
import com.fdbapps.gontobbo.adapters.TourismSpotsAdapter;
import com.fdbapps.gontobbo.data.DatabaseHelper;
import com.fdbapps.gontobbo.models.Hotel;
import com.fdbapps.gontobbo.models.TourismSpot;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class HotelsActivity extends AppCompatActivity {

    ListView hotelListView;
    ArrayList<Hotel> hotels;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Hotels");
        actionBar.setDisplayHomeAsUpEnabled(true);

        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);


        hotelListView = (ListView) findViewById(R.id.hotelListView);
        DatabaseHelper helper = new DatabaseHelper(this);


        helper.getReadableDatabase();
        hotels = helper.getListHotels(new LatLng(latitude, longitude));


        final HotelsAdapter adapter = new HotelsAdapter(hotels, this);

        hotelListView.setAdapter(adapter);

        hotelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hotel hotel = hotels.get(position);
                Intent intent = new Intent(HotelsActivity.this, HotelDetailsActivity.class);

                intent.putExtra("id", hotel.getId());

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
