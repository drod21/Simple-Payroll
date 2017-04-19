package com.drod2169.payroll;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class EmployeeActivity extends AppCompatActivity {

    int empId;
    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        EditText empNameText = (EditText) findViewById(R.id.emp_name);
        EditText empPayRateText = (EditText) findViewById(R.id.emp_pay_rate);
        EditText empDateText = (EditText) findViewById(R.id.emp_dates);
        EditText empClockInText = (EditText) findViewById(R.id.emp_clock_in);
        EditText empClockOutText = (EditText) findViewById(R.id.emp_clock_out);
        EditText empHoursText = (EditText) findViewById(R.id.emp_hours);

        Intent intent = getIntent();
        empId = intent.getIntExtra("empId", -1);

        if (empId != -1) {

            employee = TwoFragment.employees.get(empId);

            empNameText.setText(employee.getEmployeeName());
            empPayRateText.setText(String.valueOf(employee.getPayRate()));
            for (int i = 0; i < employee.getDate().size(); i++) {
                empDateText.append(employee.getDate().get(i) + " ");
                empClockInText.append(employee.getClockIn().get(i) + " ");
                empClockOutText.append(employee.getClockOut().get(i) + " ");
            }
            empHoursText.setText(String.valueOf(employee.getHours()));


        } else {

            TwoFragment.employees.add(employee);
            empId = TwoFragment.employees.size() - 1;
            TwoFragment.arrayAdapter.notifyDataSetChanged();


        }

        empNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                employee.setEmployeeName(String.valueOf(charSequence));

                TwoFragment.employees.set(empId, employee);
                TwoFragment.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.drod2169.payroll", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet<String>(TwoFragment.results);

                sharedPreferences.edit().putStringSet("employees", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}
