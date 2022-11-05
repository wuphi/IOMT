package com.example.stressmessungpuls;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.stressmessungpuls.database.AppDatabase;
import com.example.stressmessungpuls.database.User;

import java.util.Calendar;
import java.util.List;

public class Tab_Statistik extends Fragment implements View.OnClickListener {

    private static final String TAG = "Tab_Statistik";

    private TextView mDisplayDate1;
    private TextView mDisplayDate2;
    private DatePickerDialog.OnDateSetListener onDateSetListener1;
    private DatePickerDialog.OnDateSetListener onDateSetListener2;


    View view;

    //----------------------------------------------------------------------------------------------
    //      METHOD: onCreateView
    //----------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statistik, container, false);

        Button btn = (Button) view.findViewById(R.id.btn_result);
        btn.setOnClickListener(this);

        mDisplayDate1 = (TextView) view.findViewById(R.id.textView11);
        mDisplayDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog1;
                dialog1 = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener1, year, month, day);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.show();
            }
        });
        onDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy: " + year + "/" + month + "/" + day);
                String date = day + "/" + month + "/" + year;
                mDisplayDate1.setText(date);
            }
        };

        mDisplayDate2 = (TextView) view.findViewById(R.id.textView12);
        mDisplayDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog2;
                dialog2 = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener2, year, month, day);
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.show();
            }
        });
        onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy: " + year + "/" + month + "/" + day);
                String date = day + "/" + month + "/" + year;
                mDisplayDate2.setText(date);
            }
        };
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}


