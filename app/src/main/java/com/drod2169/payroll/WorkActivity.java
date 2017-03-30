package com.drod2169.payroll;


import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

import java.text.SimpleDateFormat;
import java.util.Date;


public class WorkActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.onDateSelectedListener, TimePickerFragment.onTimeSelectedListener {

    SharedPreferences sharedPreferences;

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;

    private String dateString;
    private String timeString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);


        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

    }

    /* TODO: Create methods to calculate hours.
       TODO: Create ArrayList to store hours on week basis.
       TODO: Pass values back to MainActivity.
     */

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getFragmentManager(), "datePicker");


        }

        if (v == btnTimePicker) {

            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(getFragmentManager(), "timePicker");

        }
    }

    @Override
    public void onDateSelected(String dateSet) {

        dateString = dateSet;
        Log.i("date: ", dateString);
    }


    @Override
    public void onTimeSelected(String timeSet) {

        timeString = timeSet;
        Log.i("Time ", timeString);

    }

}
