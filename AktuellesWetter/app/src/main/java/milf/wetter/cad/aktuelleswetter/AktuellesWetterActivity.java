package milf.wetter.cad.aktuelleswetter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class AktuellesWetterActivity extends AppCompatActivity implements LocationListener {

    private static final boolean AUTO_HIDE = true;

    private double lat;
    private double lng;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;


    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;

    private ImageView wetterIcons;
    private ImageView wetterMO;
    private ImageView wetterDI;
    private ImageView wetterMI;
    private ImageView wetterDO;
    private ImageView wetterFR;
    private ImageView wetterSA;
    private TextView stadt;
    private TextView datum;
    private TextView MO;
    private TextView DI;
    private TextView MI;
    private TextView DO;
    private TextView FR;
    private TextView SA;
    private Datum dates;
    private Wetter wetter;
    /* GPS Constant Permission */
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;

    /* Position */
    private static final int MINIMUM_TIME = 10000;  // 10s
    private static final int MINIMUM_DISTANCE = 50; // 50m

    /* GPS */
    private String mProviderName;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {

            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aktuelles_wetter);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        wetterIcons =(ImageView)findViewById(R.id.wetterIcon);
        wetterMO = (ImageView)findViewById(R.id.imgMO);
        wetterDI = (ImageView)findViewById(R.id.imgDI);
        wetterMI = (ImageView)findViewById(R.id.imgMI);
        wetterDO = (ImageView)findViewById(R.id.imgDO);
        wetterFR = (ImageView)findViewById(R.id.imgFR);
        wetterSA = (ImageView)findViewById(R.id.imgSA);

        stadt = (TextView) findViewById(R.id.stadtTxt);
        datum = (TextView) findViewById(R.id.uhrZeitDatum);
        MO = (TextView) findViewById(R.id.MO);
        DI = (TextView) findViewById(R.id.DI);
        MI = (TextView) findViewById(R.id.MI);
        DO = (TextView) findViewById(R.id.DO);
        FR = (TextView) findViewById(R.id.FR);
        SA = (TextView) findViewById(R.id.SA);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("NOW"));
        Intent serv = new Intent(this,MqttService.class);
        startService(serv);


        dates =  new Datum(datum,MO,DI,MI,DO,FR,SA);
        wetter = new Wetter(getApplicationContext(),wetterIcons,wetterMO,wetterDI,wetterMI,wetterDO,wetterFR,wetterSA);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

        }
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        // Get the best provider between gps, network and passive
        Criteria criteria = new Criteria();
        mProviderName = locationManager.getBestProvider(criteria, true);

        // API 23: we have to check if ACCESS_FINE_LOCATION and/or ACCESS_COARSE_LOCATION permission are granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // No one provider activated: prompt GPS
            if (mProviderName == null || mProviderName.equals("")) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
            // At least one provider activated. Get the coordinates
            switch (mProviderName) {
                case "gps":
                    locationManager.requestLocationUpdates(mProviderName, MINIMUM_TIME, MINIMUM_DISTANCE, this);
                    Location location = locationManager.getLastKnownLocation(mProviderName);
                    if(location != null){
                        Geocoder gcd = new Geocoder(this, Locale.getDefault());
                        try {
                            List<Address> addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (addresses.size() > 0) {
                                stadt.setText(""+addresses.get(0).getLocality());
                                dates.setDatum();
                            }
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                    break;
            }
            // One or both permissions are denied.
        } else {
            // The ACCESS_COARSE_LOCATION is denied, then I request it and manage the result in
            // onRequestPermissionsResult() using the constant MY_PERMISSION_ACCESS_FINE_LOCATION
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSION_ACCESS_COARSE_LOCATION);
            }
            // The ACCESS_FINE_LOCATION is denied, then I request it and manage the result in
            // onRequestPermissionsResult() using the constant MY_PERMISSION_ACCESS_FINE_LOCATION
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                        MY_PERMISSION_ACCESS_FINE_LOCATION);
            }
        }

        dates.setDatum();
        dates.setEins();
        dates.setZwei();
        dates.setDrei();
        dates.setVier();
        dates.setFuenf();
        dates.setSechs();

        wetter.setWetterEins();
        wetter.setWetterZwei();
        wetter.setWetterDrei();
        wetter.setWetterVier();
        wetter.setWetterFuenf();
        wetter.setWetterSechs();

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });


        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {
            byte [] mes = intent.getByteArrayExtra("Message");
            Log.e("onReceive: ",new String (mes));

            if(new String(mes).contains("hallo")){

            }else{
            JSONObject payload = toJson(mes);

            String wetterIcon = null;
            try {
                wetterIcon = (String)payload.get("weatherIcon");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("onReceive: ", wetterIcon);

            String topic = intent.getStringExtra("Topic");

            if(topic.contains("today")) {
                 wetter.setWetter(wetterIcon);
                }else if(topic.contains("weekly")){

            }
        }}
    };


    public JSONObject toJson (byte[] responseBody){

        try {
            JSONObject testV= new JSONObject(new String(responseBody));

            return testV;

        } catch (JSONException e) {
            e.printStackTrace();
        }
            return null;

    }










    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        delayedHide(100);
    }







    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }









    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }








    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */




    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }






    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                } else {
                    // permission denied
                }
                break;
            }
                case MY_PERMISSION_ACCESS_FINE_LOCATION: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permission was granted
                    } else {
                        // permission denied
                    }
                    break;
                }

            }
        }





    public void onLocationChanged(Location location) {
        if(location != null){
            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses.size() > 0) {

                    stadt.setText(addresses.get(0).getLocality());
                    dates.setDatum();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
