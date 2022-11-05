package com.example.stressmessungpuls.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.Random;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GpsIntentService extends IntentService {
    public static volatile boolean shouldStop = false;

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String FETCH_GPS_DATA = "com.example.stressmessungpuls.services.action.fetchGpsData";

    // TODO: Rename parameters
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";
    public static final String STREET = "STREET";
    public static final String ZIP_CODE = "ZIP_CODE";
    public static final String CITY = "CITY";

    public GpsIntentService() {
        super("GpsIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFetchGpsData(Context context) {
        Intent intent = new Intent(context, GpsIntentService.class);
        intent.setAction(FETCH_GPS_DATA);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        // https://o7planning.org/de/10421/die-anleitung-zu-android-services



        if (intent != null) {
            final String action = intent.getAction();
            if (FETCH_GPS_DATA.equals(action)) {

                handleActionFetchGpsData();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFetchGpsData() {
        // TODO: Handle action Foo
        // Loop 100 times broadcast of Intent.
        // Create Intent object (to broadcast).



        while(!shouldStop){
            Intent broadcastIntent = new Intent();
            // Set Action name for this Intent.
            // A Intent can perform many different actions.
            broadcastIntent.setAction(GpsIntentService.FETCH_GPS_DATA);

            // TODO get gps and location information

            // Set data
            broadcastIntent.putExtra(LONGITUDE, 3.14);
            broadcastIntent.putExtra(LATITUDE, 3.15);
            broadcastIntent.putExtra(STREET, "Klosterwiesgasse");
            broadcastIntent.putExtra(ZIP_CODE, "8010");
            broadcastIntent.putExtra(CITY, "Graz");

            // Send broadcast
            sendBroadcast(broadcastIntent);

            // Sleep 100 Milliseconds.
            SystemClock.sleep(10000);
        }
        stopSelf();
        return;
    }



}
