package com.example.stressmessungpuls.services;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;
import com.example.stressmessungpuls.AppConstant;
import java.io.IOException;
import java.io.InputStream;

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
    public static String PULSE_VALUE = "Pulswert";

    private BluetoothAdapter mBTAdapter;
    private BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path

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


        InputStream mmInStream = null;
        try{
            mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio
            String address = "A4:CF:12:24:4F:DE";
            String name = "ESP32test";

            BluetoothDevice device = mBTAdapter.getRemoteDevice(address);
            mBTSocket = device.createRfcommSocketToServiceRecord(AppConstant.BTMODULEUUID);
            mBTSocket.connect();


            InputStream tmpIn = null;
            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = mBTSocket.getInputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;

        } catch (IOException e) {

        }


        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()
        String readMessage = "69";

        while(!shouldStop){
            try {
                // Read from the InputStream
                bytes = mmInStream.available();
                if(bytes != 0) {
                    SystemClock.sleep(100); //pause and wait for rest of data. Adjust this depending on your sending speed.
                    bytes = mmInStream.available(); // how many bytes are ready to be read?
                    bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read

                    readMessage = new String(buffer, "UTF-8");
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            System.out.println("Message Received: <" + readMessage + ">");
            String[] readMessageSplitted = readMessage.split("\n", 10);
            System.out.println("Message Parsed: <" + readMessageSplitted[0].trim() + ">");


            int randomPulseValue = Integer.parseInt(readMessageSplitted[0].trim());
            /*if(intArray.length > 0)
                randomPulseValue = intArray[0];
            else
                randomPulseValue = 69;*/

            //int randomPulseValue = Integer.parseInt(readMessage);
            // TODO Do the measurement with real hardware

            // TODO for now random value is generated for pulse value
            //https://stackoverflow.com/questions/21049747/how-can-i-generate-a-random-number-in-a-certain-range/21049922
            /*int min = 40;
            int max = 250;
            randomPulseValue = new Random().nextInt((max-min)+1)+min;*/

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
