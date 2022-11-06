package com.example.stressmessungpuls;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Tab_Entspannung extends Fragment {
    View view;

    //----------------------------------------------------------------------------------------------
    //      METHOD: onCreateView
    //----------------------------------------------------------------------------------------------
    //@Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_entspannung, container, false);


        RelativeLayout compBtn = view.findViewById(R.id.image_btn_piano);

        compBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                // Start NewActivity.class
                Intent myIntent = new Intent(getActivity(), KlassischeMusik.class);
                startActivity(myIntent);

            }
        });


        RelativeLayout compBtnMeditation = view.findViewById(R.id.image_btn_meditation);

        compBtnMeditation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                // Start NewActivity.class
                Intent myIntentM = new Intent(getActivity(), Meditation.class);
                startActivity(myIntentM);

            }
        });

        RelativeLayout compBtnNatursounds = view.findViewById(R.id.image_btn_bird);

        compBtnNatursounds.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                // Start NewActivity.class
                Intent myIntentN = new Intent(getActivity(), Natursounds.class);
                startActivity(myIntentN);

            }
        });

        RelativeLayout compBtnTrommeln = view.findViewById(R.id.image_btn_drum);

        compBtnTrommeln.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                // Start NewActivity.class
                Intent myIntentT = new Intent(getActivity(), Trommeln.class);
                startActivity(myIntentT);

            }
        });
        return view;
    }

}



