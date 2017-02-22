package com.drod2169.payroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


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
    }



}
