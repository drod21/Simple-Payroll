package com.drod2169.payroll;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mHour, mMinute;


    public void pay(View view) {

        Employee employee = new Employee();

        Double totalPay;

        EditText name = (EditText) findViewById(R.id.empName);
        EditText hours = (EditText) findViewById(R.id.hours);
        EditText payRate = (EditText) findViewById(R.id.payRate);

        String empName = name.getText().toString();
        employee.setEmployeeName(empName);

        Double hoursWorked = Double.parseDouble(hours.getText().toString());
        employee.setHoursWorked(hoursWorked);

        Double payHourly = Double.parseDouble(payRate.getText().toString());
        employee.setPayRate(payHourly);
        employee.setWeekPay(employee.getHoursWorked(), employee.getPayRate());

        totalPay = employee.getWeekPay();

        Toast.makeText(getApplicationContext(), "$" + totalPay, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);



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
