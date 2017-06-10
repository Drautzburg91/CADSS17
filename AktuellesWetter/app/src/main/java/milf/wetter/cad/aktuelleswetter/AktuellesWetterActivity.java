package milf.wetter.cad.aktuelleswetter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.R.attr.max;


public class AktuellesWetterActivity extends AppCompatActivity implements LocationListener {

    private static final boolean AUTO_HIDE = false;

    private Activity mActivity;

    private double lat;
    private double lng;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    Notification.Builder n  ;
    NotificationManager mNotificationManager;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private Context context;
    private ImageView wetterIcons;
    final Context contextOne = this;
    private Intent serv;
    private ImageView wetterMO;
    private ImageView wetterDI;
    private ImageView wetterMI;
    private ImageView wetterDO;
    private ImageView wetterFR;
    private ImageView wetterSA;

    private TextView stadt;
    private TextView datum;
    private TextView tempMom;
    private TextView weatherState;
    private TextView windSpeed;
    private TextView regenWahr;
    private ImageView windDirection;
    private LinearLayout tempChart;


    String plz;
    String gpsPlz;
    private ImageButton btn;
    private FrameLayout mFrameLayout;
    private PopupWindow mPopupWindow;
    private EditText plzStadt;
    private InputMethodManager imm;
    Intent in = new Intent();
    private Alerts alerts;

    private TextView MO;
    private TextView DI;
    private TextView MI;
    private TextView DO;
    private TextView FR;
    private TextView SA;

    private TextView maxTempMo;
    private TextView maxTempDi;
    private TextView maxTempMi;
    private TextView maxTempDo;
    private TextView maxTempFr;
    private TextView maxTempSa;

    private TextView minTempMo;
    private TextView minTempDi;
    private TextView minTempMi;
    private TextView minTempDo;
    private TextView minTempFr;
    private TextView minTempSa;

    private Datum dates;
    private Wetter wetter;
    private Temparatur temparatur;
    private Chart chart;
    private Uebersetzung ueberSetzung;

    /* GPS Constant Permission */
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;

    /* Position */
    private static final int MINIMUM_TIME = 10000;  // 10s
    private static final int MINIMUM_DISTANCE = 50; // 50m

    /* GPS */
    private String mProviderName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aktuelles_wetter);

        mActivity = AktuellesWetterActivity.this;

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        context =getApplicationContext();

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
        tempMom = (TextView) findViewById(R.id.tempMom);
        weatherState = (TextView) findViewById(R.id.wetterStatus);
        windSpeed=  (TextView) findViewById(R.id.windgeschwindigkeit);
        windDirection = (ImageView) findViewById(R.id.windrichtungNadel);
        regenWahr = (TextView) findViewById(R.id.Regenwahrsch);
        tempChart = (LinearLayout) findViewById(R.id.chart);

        btn = (ImageButton) findViewById(R.id.Standort);
        mFrameLayout = (FrameLayout) findViewById(R.id.mainFrame);



        MO = (TextView) findViewById(R.id.MO);
        DI = (TextView) findViewById(R.id.DI);
        MI = (TextView) findViewById(R.id.MI);
        DO = (TextView) findViewById(R.id.DO);
        FR = (TextView) findViewById(R.id.FR);
        SA = (TextView) findViewById(R.id.SA);

        maxTempDi=(TextView) findViewById(R.id.maxTempDi);
        maxTempDo=(TextView) findViewById(R.id.maxTempDo);
        maxTempFr=(TextView) findViewById(R.id.maxTempFr);
        maxTempMi=(TextView) findViewById(R.id.maxTempMi);
        maxTempMo=(TextView) findViewById(R.id.maxTempMo);
        maxTempSa=(TextView) findViewById(R.id.maxTempSa);

        minTempDi=(TextView) findViewById(R.id.minTempDi);
        minTempDo=(TextView) findViewById(R.id.minTempDo);
        minTempSa=(TextView) findViewById(R.id.minTempSa);
        minTempMi=(TextView) findViewById(R.id.minTempMi);
        minTempMo=(TextView) findViewById(R.id.minTempMo);
        minTempFr=(TextView) findViewById(R.id.minTempFr);


        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("NOW"));

        serv = new Intent(this,MqttService.class);
       // startService(serv);

        dates =  new Datum(datum,MO,DI,MI,DO,FR,SA);
        wetter = new Wetter(getApplicationContext(),wetterIcons,wetterMO,wetterDI,wetterMI,wetterDO,wetterFR,wetterSA);
        temparatur =new Temparatur(maxTempMo,maxTempDi,maxTempMi,maxTempDo,maxTempFr,maxTempSa,minTempMo,minTempDi,minTempMi,minTempDo,minTempFr,minTempSa,tempMom);
        chart= new Chart();
        ueberSetzung = new Uebersetzung();
        alerts = new Alerts();
        final Geocoder gcdOne = new Geocoder(this, Locale.GERMAN);

        btn.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        // get prompts.xml view
                        LayoutInflater li = LayoutInflater.from(contextOne);
                        View promptsView = li.inflate(R.layout.custom_dialog, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                contextOne);

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        final EditText stadtInput = (EditText) promptsView
                                .findViewById(R.id.editText);

                        // set dialog message
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                // get user input and set it to result
                                                // edit text
                                                String stadtInp = stadtInput.getText().toString();
                                                if (!stadtInp.equals("")) {
                                                    Log.e("alert", stadtInp);
                                                    stadt.setText(stadtInp);

                                                    try {
                                                        List<Address> addresses = gcdOne.getFromLocationName(stadtInp, 1);
                                                        if (addresses.size() > 0) {
                                                            addresses = gcdOne.getFromLocation(addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), 1);
                                                            if (addresses.size() > 0) {
                                                                Log.e("addresses", addresses.get(0).getPostalCode());
                                                                gpsPlz = addresses.get(0).getPostalCode();
                                                                serv.putExtra("plz", gpsPlz);
                                                                serv.putExtra("alert",false);
                                                                serv.putExtra("lastPlz",plz);
                                                                startService(serv);
                                                            }

                                                        }
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                }else {
                                                    gps();
                                                }
                                            }


                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();
                                            }
                                        })
                        .setNeutralButton("GPS",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                serv.putExtra("alert",false);
                                serv.putExtra("lastPlz",gpsPlz);
                                gps();
                                dialog.cancel();
                            }
                        });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    }
                });

        gps();

        dates.setDatum();
        dates.setEins();
        dates.setZwei();
        dates.setDrei();
        dates.setVier();
        dates.setFuenf();
        dates.setSechs();



        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        in.setAction("PLZ");
        in.putExtra("PLZ",plz);



    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {
            byte [] mes = intent.getByteArrayExtra("Message");
            Log.e("onReceive: ",new String (mes));

            if(new String(mes).contains("hallo")){}
            else{
            JSONObject payload = toJson(mes);

            String topic = intent.getStringExtra("Topic");

            if(topic.contains("today")) {
                String wetterIcon = null;
                Double temperature = null;
                Double windspeed = null;
                Double windDig = null;
                int temp = 0;
                int winds = 0;
                int weaterText = 0;
                try {
                    wetterIcon = (String)payload.get("weatherIcon");
                    temperature = (Double) payload.get("temperature");
                    windspeed = (Double) payload.get("windspeed");
                    weaterText = (Integer) payload.get("currentWeatherId");
                    windDig = (Double) payload.get("windDeg");
                    temp = temperature.intValue();
                    winds = (int)((windspeed.doubleValue()/1000d)*3600d);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                 wetter.setWetter(wetterIcon);
                 temparatur.setTemp(Integer.toString(temp));
                 weatherState.setText(ueberSetzung.uebersetzen(weaterText));
                 windSpeed.setText("Wind: "+winds+"km/h");
                 windDirection.setRotation(windDig.floatValue());
                 regenWahr.setText("38%");


                }else {
                if (topic.contains("weekly")) {
                    JSONArray array = null;
                    JSONObject dayOfWeek = null;

                    String wetterIconDayOne = null;
                    String wetterIconDayTwo = null;
                    String wetterIconDayThree = null;
                    String wetterIconDayFour = null;
                    String wetterIconDayFive = null;
                    String wetterIconDaySix = null;

                    try {
                        array = payload.getJSONArray("days");
                        int [] max = new int[5];
                        int [] min = new int[5];
                        for (int i = 0; i < array.length(); i++) {
                            dayOfWeek = array.getJSONObject(i);
                            String date = dayOfWeek.get("date").toString();
                            DateFormat formatWeekDate = new SimpleDateFormat("yyyy-mm-d");
                            Date weekDate = formatWeekDate.parse(date);
                            DateTime dateWeek = new DateTime(weekDate);
                            Date d = new Date();
                            DateTime dtOrg = new DateTime(d);


                            if(dateWeek.equals(dtOrg.plusDays(1)))
                            {
                                wetterIconDayOne = (String) payload.get("weatherIcon");
                                temparatur.setTempMo((String) payload.get("temperatureMin"),(String) payload.get("temperatureMax"));
                                wetter.setWetterEins(wetterIconDayOne);
                                max[0] = (int)payload.get("temperatureMax");
                                min[0] = (int)payload.get("temperatureMax");
                            }
                            if(dateWeek.equals(dtOrg.plusDays(2)))
                            {
                                wetterIconDayTwo = (String) payload.get("weatherIcon");
                                temparatur.setTempDi((String) payload.get("temperatureMin"),(String) payload.get("temperatureMax"));
                                wetter.setWetterZwei(wetterIconDayTwo);
                                max[1] = (int)payload.get("temperatureMax");
                                min[1] = (int)payload.get("temperatureMax");
                            }
                            if(dateWeek.equals(dtOrg.plusDays(3)))
                            {
                                wetterIconDayThree = (String) payload.get("weatherIcon");
                                temparatur.setTempMi((String) payload.get("temperatureMin"),(String) payload.get("temperatureMax"));
                                wetter.setWetterDrei(wetterIconDayThree);
                                max[2] = (int)payload.get("temperatureMax");
                                min[2] = (int)payload.get("temperatureMax");
                            }
                            if(dateWeek.equals(dtOrg.plusDays(4)))
                            {
                                wetterIconDayFour = (String) payload.get("weatherIcon");
                                temparatur.setTempDo((String) payload.get("temperatureMin"),(String) payload.get("temperatureMax"));
                                wetter.setWetterVier(wetterIconDayFour);
                                max[3] = (int)payload.get("temperatureMax");
                                min[3] = (int)payload.get("temperatureMax");
                            }
                            if(dateWeek.equals(dtOrg.plusDays(5)))
                            {
                                wetterIconDayFive = (String) payload.get("weatherIcon");
                                temparatur.setTempFr((String) payload.get("temperatureMin"),(String) payload.get("temperatureMax"));
                                wetter.setWetterFuenf(wetterIconDayFive);
                                max[4] = (int)payload.get("temperatureMax");
                                min[4] = (int)payload.get("temperatureMax");
                            }
                            if(dateWeek.equals(dtOrg.plusDays(6)))
                            {
                                wetterIconDaySix = (String) payload.get("weatherIcon");
                                temparatur.setTempSa((String) payload.get("temperatureMin"),(String) payload.get("temperatureMax"));
                                wetter.setWetterSechs(wetterIconDaySix);
                                max[5] = (int)payload.get("temperatureMax");
                                min[5] = (int)payload.get("temperatureMax");
                            }
                        }
                        chart.makeChart(tempChart,getApplicationContext(),max,min);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }




                } else if (topic.contains("alert")) {
                    String[] alertArray = null;
                    try {
                        alertArray = alerts.alert((String) payload.get("alertTitel"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Notification noti =
                            new NotificationCompat.Builder(context)
                                    .setSmallIcon(R.drawable.notification_icon)
                                    .setContentTitle(alertArray[0])
                                    .setContentText(alertArray[1]).getNotification();
                    mNotificationManager.notify(001, noti);


                }
            }
        }}
    };

    public void gps(){
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
                                plz = addresses.get(0).getPostalCode();
                                serv.putExtra("plz",plz);
                                Log.e("GPS",addresses.get(0).getPostalCode());
                                startService(serv);
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



    }

    public JSONObject toJson (byte[] responseBody){

        try {
            JSONObject testV= new JSONObject(new String(responseBody));

            return testV;

        } catch (JSONException e) {
            e.printStackTrace();
        }
            return null;

    }

    public void onLocationChanged(Location location) {
      /*  if(location != null){
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
        }*/
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
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
