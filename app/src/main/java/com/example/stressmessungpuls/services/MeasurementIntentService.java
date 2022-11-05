package com.example.stressmessungpuls.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;

import java.util.Random;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MeasurementIntentService extends IntentService {
    public static volatile boolean shouldStop = false;

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String DO_MEASUREMENT = "com.example.stressmessungpuls.services.action.do.measurement";

    // TODO: Rename parameters
    public static final String PULSE_VALUE = "Pulswert";

    public MeasurementIntentService() {
        super("MeasurementIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionDoMeasurement(Context context) {
        Intent intent = new Intent(context, MeasurementIntentService.class);
        intent.setAction(DO_MEASUREMENT);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        // https://o7planning.org/de/10421/die-anleitung-zu-android-services

        if (intent != null) {
            final String action = intent.getAction();
            if (DO_MEASUREMENT.equals(action)) {

                handleActionDoMeasurement();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDoMeasurement() {
        // TODO: Handle action Foo
        // Loop 100 times broadcast of Intent.
        // Create Intent object (to broadcast).

        Intent broadcastIntent = new Intent();
        // Set Action name for this Intent.
        // A Intent can perform many different actions.
        broadcastIntent.setAction(MeasurementIntentService.DO_MEASUREMENT);

        while(!shouldStop){
            int randomPulseValue;
            // TODO Do the measurement with real hardware

            // TODO for now random value is generated for pulse value
            //https://stackoverflow.com/questions/21049747/how-can-i-generate-a-random-number-in-a-certain-range/21049922
            int min = 40;
            int max = 250;
            randomPulseValue = new Random().nextInt((max-min)+1)+min;

            // Set data
            broadcastIntent.putExtra(PULSE_VALUE, randomPulseValue);

            // Send broadcast
            sendBroadcast(broadcastIntent);

            // Sleep 100 Milliseconds.
            SystemClock.sleep(10000);
        }
        stopSelf();
        return;
    }

}
