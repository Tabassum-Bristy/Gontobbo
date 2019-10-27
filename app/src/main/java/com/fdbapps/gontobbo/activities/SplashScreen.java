package com.fdbapps.gontobbo.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fdbapps.gontobbo.R;
import com.fdbapps.gontobbo.data.DatabaseHelper;
import com.fdbapps.gontobbo.utilities.GPSTracker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SplashScreen extends AppCompatActivity {

    private  DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        // Database

        mDatabaseHelper = new DatabaseHelper(this);

        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(database.exists() == false) {
            mDatabaseHelper.getReadableDatabase();
            if(copyDatabase(this)) {
                // success
            }
            else {
                // failed
            }
        }

        GPSTracker gpsTracker = new GPSTracker(this);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 3000);

    }

    private boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff,  0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.v("SplashScreen", "Database Copied");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
