package com.fdbapps.gontobbo.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fdbapps.gontobbo.R;
import com.fdbapps.gontobbo.adapters.TourismSpotsAdapter;
import com.fdbapps.gontobbo.data.DatabaseHelper;
import com.fdbapps.gontobbo.models.TourismSpot;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class TourismSpotsActivity extends AppCompatActivity {

    private ListView listView;
    private SearchView searchView;

    private ArrayList<TourismSpot> spots;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism_spots);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tourism Spots");
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.tourismListView);


        // get values passed to this intent
        //from my location put extra now getting
        //if not passing default value will be 0
        final double latitude = getIntent().getDoubleExtra("latitude", 0);
        final double longitude = getIntent().getDoubleExtra("longitude", 0);


        dbHelper = new DatabaseHelper(this);
        dbHelper.getReadableDatabase();

        spots = dbHelper.getListTourismSpots(new LatLng(latitude, longitude));

        final TourismSpotsAdapter adapter = new TourismSpotsAdapter(spots, this);
       //list view binding
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TourismSpot spot = adapter.getItem(position);
                Intent intent = new Intent(TourismSpotsActivity.this, SpotDetailsActivity.class);

                intent.putExtra("id", spot.getId());

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
