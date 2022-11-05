package com.example.stressmessungpuls;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.content.pm.PackageManager;
import android.icu.util.Measure;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.stressmessungpuls.database.AppDatabase;
import com.example.stressmessungpuls.database.Pulsedata;
import com.example.stressmessungpuls.database.User;
import com.example.stressmessungpuls.services.FetchAddressIntentService;
import com.example.stressmessungpuls.services.GpsIntentService;
import com.example.stressmessungpuls.services.MeasurementIntentService;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Tab_Messung extends Fragment implements View.OnClickListener {
    public static Boolean GPS_ACTIVATED = false;

    View view;


    private int REQUEST_CODE_LOCATION_PERMISSION = 1;

    //----------------------------------------------------------------------------------------------
    //      METHOD: onCreateView
    //----------------------------------------------------------------------------------------------
    //@Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_messung, container, false);

        ImageButton btn = (ImageButton) view.findViewById(R.id.image_btn_heart);
        btn.setOnClickListener(this);

        getActivity().registerReceiver(receiver, new IntentFilter(MeasurementIntentService.DO_MEASUREMENT));
        //getActivity().registerReceiver(receiver, new IntentFilter(GpsIntentService.FETCH_GPS_DATA));
        getActivity().registerReceiver(receiver, new IntentFilter(FetchAddressIntentService.FETCH_ADDRESS_DATA));

        //resultReceiver = new AddressResultReceiver(new Handler());

        return view;
    }

    public class ToastRunnable implements Runnable {
        private String message;
        public ToastRunnable(String _message) {
            this.message = _message;
        }

        @Override
        public void run() {
            Toast.makeText(getActivity(), this.message, Toast.LENGTH_LONG).show();
        }
    }

    public class DatabaseRunnable implements Runnable {
        private Pulsedata data;
        private AppDatabase database;
        public DatabaseRunnable(AppDatabase _database, Pulsedata _data) {
            this.database = _database;
            this.data = _data;
        }

        @Override
        public void run() {
            database.pulsdataDao().insertAll(this.data);
            getActivity().runOnUiThread(new ToastRunnable(
                    "Measurement saved to database. PulseValue<" + this.data.pulsevalue + ">, " +
                            "lon <" + this.data.longitude + ">, lat <" + this.data.latitude + ">, " +
                            "street <" + this.data.street + ">, zipCode <" + this.data.zipcode + ">, " +
                            "city <" + this.data.city + ">, datetime <" + this.data.datetime + ">"));
        }
    }


    //public static Location location;
    private Intent serviceIntent;

    private ResponseReceiver receiver = new ResponseReceiver();
    //private ResultReceiver resultReceiver;

    // Broadcast component
    public class ResponseReceiver extends BroadcastReceiver {
        private double longitude;
        private double latitude;

        private String street;
        private String zipCode;
        private String city;


        // On broadcast received
        @Override
        public void onReceive(Context context, Intent intent) {

            // Check action name.
            if(intent.getAction().equals(MeasurementIntentService.DO_MEASUREMENT)) {
                int value = intent.getIntExtra(MeasurementIntentService.PULSE_VALUE, 0);

                CharSequence bpm = String.valueOf(value);
                EditText editText = (EditText) view.findViewById(R.id.edt_txt_bpm);
                editText.setText(bpm);

                TextView txtPulseRange = (TextView) view.findViewById(R.id.txt_measurement_pulse_range_message);
                if(value < 60)
                {
                    //txtPulseRange.setTextColor();
                    txtPulseRange.setText("Ihr Puls ist zu niedrig");
                }
                if(value >= 60 && value <= 80)
                {
                    txtPulseRange.setTextColor(Color.GREEN);
                    txtPulseRange.setText("Alles okay - ihr Puls ist im Normalbereich");
                }
                if(value > 80 && value < 100)
                {
                    txtPulseRange.setTextColor(Color.YELLOW);
                    txtPulseRange.setText("ihr Puls ist leicht erhöht");
                }else if(value >= 100)
                {
                    txtPulseRange.setTextColor(Color.RED);
                    txtPulseRange.setText("Achtung Puls ist erhöht - Zeit für Entspannung");
                }

                getActivity().runOnUiThread(new ToastRunnable("Measurement received"));

                if(GPS_ACTIVATED == true){
                    getCurrentLocation();
                }
                else {
                    this.latitude = 0.0;
                    this.latitude = 0.0;
                    this.street = "GPS DEACTIVATED";
                    this.zipCode = "GPS DEACTIVATED";
                    this.city = "GPS DEACTIVATED";
                }


                Pulsedata pulseData = new Pulsedata();
                pulseData.pulsevalue = value;

                pulseData.latitude = this.latitude;
                pulseData.longitude = this.longitude;

                pulseData.street = this.street;
                pulseData.zipcode = this.zipCode;
                pulseData.city = this.city;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                pulseData.datetime = currentDateandTime;

                final AppDatabase database = AppDatabase.getInstance(getActivity());
                AppDatabase.databaseExecutor.execute(new DatabaseRunnable(database, pulseData));
            }

            if(intent.getAction().equals(GpsIntentService.FETCH_GPS_DATA)) {
                this.longitude = intent.getDoubleExtra(GpsIntentService.LONGITUDE, 0);
                this.latitude = intent.getDoubleExtra(GpsIntentService.LATITUDE, 0);

                this.street = intent.getStringExtra(GpsIntentService.STREET);
                this.zipCode = intent.getStringExtra(GpsIntentService.ZIP_CODE);
                this.city = intent.getStringExtra(GpsIntentService.CITY);

                getActivity().runOnUiThread(
                        new ToastRunnable("Gps data received " +
                                "long<" + this.longitude + ">, lat<" + this.latitude + "> " +
                                "street<" + this.street + ">, zipcode<" + this.zipCode + "> " +
                                "city<" + this.city + ">"));
            }

            if(intent.getAction().equals(FetchAddressIntentService.FETCH_ADDRESS_DATA)) {
                this.longitude = intent.getDoubleExtra(GpsIntentService.LONGITUDE, 0);
                this.latitude = intent.getDoubleExtra(GpsIntentService.LATITUDE, 0);

                this.street = intent.getStringExtra(GpsIntentService.STREET);
                this.zipCode = intent.getStringExtra(GpsIntentService.ZIP_CODE);
                this.city = intent.getStringExtra(GpsIntentService.CITY);

                getActivity().runOnUiThread(
                        new ToastRunnable("Gps data received " +
                                "long<" + this.longitude + ">, lat<" + this.latitude + "> " +
                                "street<" + this.street + ">, zipcode<" + this.zipCode + "> " +
                                "city<" + this.city + ">"));
            }
        }
    }

    @Override
    public void onClick(View v) {
        ImageButton btn = (ImageButton) view.findViewById(R.id.image_btn_heart);

        if(btn.isActivated())
        {
            MeasurementIntentService.shouldStop = true;
            Toast.makeText(getActivity(), "Stopping MeasurementIntentService", Toast.LENGTH_SHORT).show();
            btn.setActivated(false);
        }
        else
        {
            //MeasurementIntentService.shouldStop
            this.serviceIntent = new Intent(getActivity(), MeasurementIntentService.class);
            getActivity().startService(this.serviceIntent.setAction(MeasurementIntentService.DO_MEASUREMENT));
            Toast.makeText(getActivity(), "Starting MeasurementIntentService", Toast.LENGTH_SHORT).show();
            btn.setActivated(true);


        }
    }

    public void getCurrentLocation(){


        int REQUEST_CODE_LOCATION_PERMISSION = 1;
        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        }

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(getActivity())
                .requestLocationUpdates(locationRequest, new LocationCallback(){

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        //LocationServices.getFusedLocationProviderClient(getActivity())
                        //        .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLongitude();


                            Intent intent = new Intent(getActivity(), FetchAddressIntentService.class);
                            intent.putExtra(FetchAddressIntentService.LATITUDE, latitude);
                            intent.putExtra(FetchAddressIntentService.LONGITUDE, longitude);
                            getActivity().startService(intent);

                        }
                    }
                }, Looper.getMainLooper());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            } else {
                Toast.makeText(getActivity(), "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
