package com.drod2169.payroll;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class WorkActivity extends AppCompatActivity implements View.OnClickListener {


    static EditText DateEdit;
    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;

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

        EditText hoursWorked = (EditText) findViewById(R.id.hours_worked);



        /*DateEdit = (EditText) findViewById(R.id.in_date);
        DateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
                showTimePickerDialog(v);
            }
        });*/
    }


    public void onButtonClicked(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "Date Picker");
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            onButtonClicked(btnDatePicker);

        }
        if (v == btnTimePicker) {

            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(getFragmentManager(), "TimePicker");

        }
    }


}
