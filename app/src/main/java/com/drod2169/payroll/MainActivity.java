package com.drod2169.payroll;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;


    public void pay(View view) {

        Employee employee = new Employee();

        Double totalPay;

        EditText name = (EditText) findViewById(R.id.empName);
        EditText payRate = (EditText) findViewById(R.id.payRate);


        String empName = name.getText().toString();
        employee.setEmployeeName(empName);

        // Double hoursWorked = Double.parseDouble(hours.getText().toString());
        // employee.setHoursWorked(hoursWorked);

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


        Button hours = (Button) findViewById(R.id.hours);
        /*btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);*/

        hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, WorkActivity.class);
                String strName = "";
                intent.putExtra("hour", strName);
                startActivity(intent);

            }
        });

    }

    /*public void onButtonClicked(View v) {
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
    }*/

}
