package com.fdbapps.gontobbo.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fdbapps.gontobbo.models.EmergencyContact;
import com.fdbapps.gontobbo.models.Hospital;
import com.fdbapps.gontobbo.models.Hotel;
import com.fdbapps.gontobbo.models.TourismSpot;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DBNAME = "gontobbo.sqlite";
    public static final String DBLOCATION = "/data/data/com.fdbapps.gontobbo/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;
//constructor
    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath((DBNAME)).getPath();

        if (mDatabase != null && mDatabase.isOpen()) {
            // database already loaded
            return;
        }

        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    ;


    private static final String TABLE_NAME_TOURISM_SPOT = "TourismSpot";

    public ArrayList<TourismSpot> getListTourismSpots(LatLng latLng) {
        TourismSpot spot = null;
        ArrayList<TourismSpot> spotList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_TOURISM_SPOT, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            spot = new TourismSpot(cursor.getInt(0), cursor.getString(1), cursor.getString(5),
                    cursor.getString(2), cursor.getBlob(6), cursor.getDouble(3), cursor.getDouble(4), cursor.getString(7));
            spotList.add(spot);
            cursor.moveToNext();
        }

        cursor.close();
        closeDatabase();

        if (latLng.latitude == 0 && latLng.longitude == 0)
            return spotList;

        // sort based on latitude and longitude
        Collections.sort(spotList, new SortPlaces(latLng));

        return spotList;
    }

    public TourismSpot getTourismSpot(int id) {
        TourismSpot spot = null;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_TOURISM_SPOT + " WHERE id = " + id, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            spot = new TourismSpot(cursor.getInt(0), cursor.getString(1), cursor.getString(5),
                    cursor.getString(2), cursor.getBlob(6), cursor.getDouble(3), cursor.getDouble(4), cursor.getString(7));
            cursor.moveToNext();
        }

        cursor.close();
        closeDatabase();

        return spot;
    }

    public static final String TABLE_NAME_HOTEL = "Hotel";

    private static final int HOTEL_ID = 0;
    private static final int HOTEL_TITLE = 1;
    private static final int HOTEL_DESCRIPTION = 2;
    private static final int HOTEL_ADDRESS = 3;
    private static final int HOTEL_IMAGE_NAME = 4;
    private static final int HOTEL_ROOM_FAIR = 5;
    private static final int HOTEL_PHONE_NUMBER = 6;
    private static final int HOTEL_LATITUDE = 7;
    private static final int HOTEL_LONGITUDE = 8;


    public ArrayList<Hotel> getListHotels(LatLng latLng) {
        Hotel hotel = null;
        ArrayList<Hotel> hotels = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_HOTEL, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            hotel = new Hotel(cursor.getInt(HOTEL_ID), cursor.getString(HOTEL_TITLE), cursor.getString(HOTEL_DESCRIPTION),
                    cursor.getString(HOTEL_ADDRESS), cursor.getBlob(HOTEL_IMAGE_NAME), cursor.getDouble(HOTEL_LATITUDE),
                    cursor.getDouble(HOTEL_LONGITUDE), cursor.getString(HOTEL_ROOM_FAIR), cursor.getString(HOTEL_PHONE_NUMBER));

            hotels.add(hotel);
            cursor.moveToNext();
        }

        cursor.close();
        closeDatabase();

        if (latLng.latitude == 0 && latLng.longitude == 0)
            return hotels;

        // sort based on latitude and longitude
        Collections.sort(hotels, new SortPlaces(latLng));

        return hotels;
    }

    public Hotel getHotel(int id) {
        Hotel hotel = null;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_HOTEL + "  WHERE id = " + id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            hotel = new Hotel(cursor.getInt(HOTEL_ID), cursor.getString(HOTEL_TITLE), cursor.getString(HOTEL_DESCRIPTION),
                    cursor.getString(HOTEL_ADDRESS), cursor.getBlob(HOTEL_IMAGE_NAME), cursor.getDouble(HOTEL_LATITUDE),
                    cursor.getDouble(HOTEL_LONGITUDE), cursor.getString(HOTEL_ROOM_FAIR), cursor.getString(HOTEL_PHONE_NUMBER));
            cursor.moveToNext();
        }

        cursor.close();
        closeDatabase();

        return hotel;
    }

    public static final String TABLE_NAME_HOSPITAL = "Hospital";

    private static final int HOSPITAL_ID = 0;
    private static final int HOSPITAL_TITLE = 1;
    private static final int HOSPITAL_ADDRESS = 2;
    private static final int HOSPITAL_PHONE_NUMBER = 3;
    private static final int HOSPITAL_LATITUDE = 4;
    private static final int HOSPITAL_LONGITUDE = 5;

    public ArrayList<Hospital> getListHospitals(LatLng latLng) {

        Hospital hospital = null;
        ArrayList<Hospital> hospitals = new ArrayList<>();

        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_HOSPITAL, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            hospital = new Hospital(cursor.getInt(HOSPITAL_ID), cursor.getString(HOSPITAL_TITLE),
                    cursor.getString(HOSPITAL_ADDRESS), cursor.getDouble(HOSPITAL_LATITUDE),
                    cursor.getDouble(HOSPITAL_LONGITUDE), cursor.getString(HOSPITAL_PHONE_NUMBER));

            hospitals.add(hospital);
            cursor.moveToNext();
        }

        cursor.close();
        closeDatabase();

        if (latLng.latitude == 0 && latLng.longitude == 0)
            return hospitals;

        // sort based on latitude and longitude
        Collections.sort(hospitals, new SortPlaces(latLng));


        return hospitals;

    }

    public Hospital getHospital(int id) {

        Hospital hospital = null;

        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_HOSPITAL + " WHERE id = " + id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            hospital = new Hospital(cursor.getInt(HOSPITAL_ID), cursor.getString(HOSPITAL_TITLE),
                    cursor.getString(HOSPITAL_ADDRESS), cursor.getDouble(HOSPITAL_LATITUDE),
                    cursor.getDouble(HOSPITAL_LONGITUDE), cursor.getString(HOSPITAL_PHONE_NUMBER));

            cursor.moveToNext();
        }

        cursor.close();
        closeDatabase();

        return hospital;

    }


    public ArrayList<EmergencyContact> getListEmergencyContact(String district) {
        ArrayList<EmergencyContact> contacts = new ArrayList<>();
        EmergencyContact contact = null;

        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM EmergencyContact WHERE district = '" + district +"'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            contact = new EmergencyContact(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            contacts.add(contact);
            cursor.moveToNext();
        }

        cursor.close();
        closeDatabase();

        return contacts;
    }

}
