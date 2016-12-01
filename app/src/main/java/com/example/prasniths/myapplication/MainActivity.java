package com.example.prasniths.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Typeface tf = Typeface.createFromAsset(getAssets(), "SaiIndira.ttf");
        TextView viewById = (TextView) findViewById(R.id.connectivityResult);
        viewById.setTypeface(tf);

        Button skypeButton = (Button) findViewById(R.id.openSkype);

        skypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

                if (!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                    updateStatus();
                }

                PackageManager pm = getPackageManager();
                Intent appStartIntent = pm.getLaunchIntentForPackage("com.skype.raider");
                if (null != appStartIntent) {
                    startActivity(appStartIntent);
                } else {
                    updateStatusWithMsg("ஸ்கேய்பே தொடர்பு கொள்ள முடியவில்லை ");
                }


            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatus();
                Snackbar.make(view, getConnectivityStatus(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateStatus();
    }

    private void updateStatus() {

        String connectivityStatus = getConnectivityStatus();
        updateStatusWithMsg(connectivityStatus);
    }

    private void updateStatusWithMsg(String connectivityStatus) {
        TextView viewById = (TextView) findViewById(R.id.connectivityResult);

        viewById.setText(connectivityStatus);
    }


    private String getConnectivityStatus() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        String temp = "இன்டர்நெட் தொடர்பு இல்லை";
        if (isConnected) {
            temp = "இன்டர்நெட் தொடர்பு உள்ளது";
        }
        return temp;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
