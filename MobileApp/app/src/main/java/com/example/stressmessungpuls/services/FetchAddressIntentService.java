package com.example.stressmessungpuls.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FetchAddressIntentService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String FETCH_ADDRESS_DATA = "com.example.stressmessungpuls.services.action.fetchAddressData";

    // input/output variables
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";
    // output varables
    public static final String STREET = "STREET";
    public static final String ZIP_CODE = "ZIP_CODE";
    public static final String CITY = "CITY";

    public FetchAddressIntentService(){
        super("FetchAddressIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        // https://o7planning.org/de/10421/die-anleitung-zu-android-services

        if (intent != null){
            String errorMessage = "";
            double longitude = intent.getDoubleExtra(LONGITUDE,0.0);
            double latitude = intent.getDoubleExtra(LATITUDE,0.0);

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;

            Intent broadcastIntent = new Intent();
            // Set Action name for this Intent.
            // A Intent can perform many different actions.
            broadcastIntent.setAction(FetchAddressIntentService.FETCH_ADDRESS_DATA);

            try{
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (Exception exception){
                errorMessage = exception.getMessage();
                broadcastIntent.putExtra(LONGITUDE, longitude);
                broadcastIntent.putExtra(LATITUDE, latitude);
                broadcastIntent.putExtra(STREET, errorMessage);
                broadcastIntent.putExtra(ZIP_CODE, "null");
                broadcastIntent.putExtra(CITY, "ERROR");

                // Send broadcast
                sendBroadcast(broadcastIntent);
            }
            if(addresses == null || addresses.isEmpty()){
                // Set data
                broadcastIntent.putExtra(LONGITUDE, longitude);
                broadcastIntent.putExtra(LATITUDE, latitude);
                broadcastIntent.putExtra(STREET, "null");
                broadcastIntent.putExtra(ZIP_CODE, "null");
                broadcastIntent.putExtra(CITY, "ERROR");

                // Send broadcast
                sendBroadcast(broadcastIntent);
            }else{
                Address address = addresses.get(0);
                ArrayList<String> addressFragments = new ArrayList<>();
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++){
                    addressFragments.add(address.getAddressLine(i));
                }

                String[] separated = addressFragments.get(0).split(",");
                String street = separated[0];
                String[] zipCodeStreet = separated[1].trim().split(" ");
                String zipCode = zipCodeStreet[0];
                String city = separated[1].trim().replace(zipCode,"");

                // Set data
                broadcastIntent.putExtra(LONGITUDE, longitude);
                broadcastIntent.putExtra(LATITUDE, latitude);
                broadcastIntent.putExtra(STREET, street);
                broadcastIntent.putExtra(ZIP_CODE, zipCode);
                broadcastIntent.putExtra(CITY, city);

                // Send broadcast
                sendBroadcast(broadcastIntent);

            }
        }
    }


}
