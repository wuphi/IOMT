package com.example.stressmessungpuls;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.stressmessungpuls.services.FetchAddressIntentService;
import com.example.stressmessungpuls.services.GpsIntentService;
import com.example.stressmessungpuls.services.MeasurementIntentService;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class Tab_Einstellungen extends Fragment {
    View view;

    private int REQUEST_CODE_LOCATION_PERMISSION = 1;
    //----------------------------------------------------------------------------------------------
    //      METHOD: onCreateView
    //----------------------------------------------------------------------------------------------
    //@Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_einstellungen, container, false);

        Switch gpsSwitch = (Switch) view.findViewById(R.id.swi_gps);
        gpsSwitch.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                // Start NewActivity.class
                Intent myIntentT = new Intent(getActivity(), BluetoothActivity.class);
                startActivity(myIntentT);

            }
            /*@Override
            public void onClick(View view) {


                Switch gpsSwitch = (Switch) view.findViewById(R.id.swi_gps);
                if(gpsSwitch.isChecked())
                {
                    // Stopp gps service
                    //GpsIntentService.shouldStop = true;
                    Tab_Messung.GPS_ACTIVATED = true;

                }
                else {
                    //Intent serviceIntent = new Intent(getActivity(), GpsIntentService.class);
                    //getActivity().startService(serviceIntent.setAction(GpsIntentService.FETCH_GPS_DATA));
                    //Start GPS Service
                    Tab_Messung.GPS_ACTIVATED = false;

                }


            }*/
        });

        return view;
    }


}
