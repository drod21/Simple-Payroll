package com.drod2169.payroll;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class WorkActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.onDateSelectedListener, TimePickerFragment.onTimeSelectedListener {

    /*
    TODO: Revisit shared preferences
     */
    //SharedPreferences sharedPreferences;

    /* TODO: Create SQL table for storing data
       TODO: Pass date back to OneFragment
     */

    DatabaseHandler db = new DatabaseHandler(this);

    Button btnDatePicker, btnClockInPicker, btnClockOutPicker;
    EditText txtDate, txtTime;

    EmployeeSingleton employeeSingleton = EmployeeSingleton.getInstance();

    String dateString;

    int hourSelected;
    int minuteSelected;

    String timeString;
    String AMPM;

    int h = 0;
    int m = 0;

    ArrayList<String> clockIn = new ArrayList<>();
    ArrayList<String> clockOut = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<Integer> hour = new ArrayList<>();
    ArrayList<Integer> minute = new ArrayList<>();
    ArrayList<String> timeOfDay = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnClockInPicker = (Button) findViewById(R.id.btn_clock_in);
        btnClockOutPicker = (Button) findViewById(R.id.btn_clock_out);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnClockInPicker.setOnClickListener(this);
        btnClockOutPicker.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    public void getHoursFinal() {
        double hour;
        double hoursFinal;

        int hoursTime, minutesTime;

        hoursTime = getHours();
        minutesTime = getMinutes();
        Log.i("Hours subtracted: ", String.valueOf(h));
        Log.i("Minutes subtracted: ", String.valueOf(m));
        Log.i("Hours subtracted new: ", String.valueOf(hoursTime));
        Log.i("Minute subtracted new: ", String.valueOf(minutesTime));

        hour = (double) hoursTime + ((double) minutesTime / 100);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        hoursFinal = Double.valueOf(decimalFormat.format(hour));
        ArrayList<Double> hours = new ArrayList<>();
        hours.add(hoursFinal);

        Log.i("Final: ", String.valueOf(hoursFinal));
        if (EmployeeSingleton.getInstance().getWorkedHours() == null) {
            EmployeeSingleton.getInstance().setWorkedHours(hours);
        } else {
            EmployeeSingleton.getInstance().setSingleWorkedHours(hoursFinal);
        }

        Intent output = new Intent();
        output.putExtra(OneFragment.hour_key, hoursFinal);
        setResult(RESULT_OK, output);

    }

    public void addToDb() {

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        db.addEmployee(employeeSingleton);
                        Log.i("Reading: ", "Reading all employees..");
                        List<Employee> employees = db.getAllEmployees();
                        for (Employee emp : employees) {
                            String log = "Id: " + emp.getId() + " , Name: " + emp.getEmployeeName() + " , Pay Rate: " +
                                    emp.getPayRate() + " , Dates: " + emp.getDate() + " , Clock In: " +
                                    emp.getClockIn() + " , Clock Out: " + emp.getClockOut() + " , Hours Worked: " + emp.getHoursWorked();
                            //MainActivity.employee.setID(emp.getId());
                            // Write to the log
                            Log.i("DB from WorkActivity ", log);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }).start();

    }


    public void finalHours(View v) {

        getHoursFinal();
        addToDb();

        Log.i("Time from object: ", String.valueOf(employeeSingleton.getClockIn()));
        Log.i("ClockOut from object: ", String.valueOf(employeeSingleton.getClockOut()));

        Log.i("Insert: ", "Inserting..");

        finish();

    }

    public void deleteDb() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                List<Employee> employees = db.getAllEmployees();

                for (int i = 0; i < employees.size(); i++) {
                    Log.i("Deleting: ", "Deleting employees.. " + employees.get(i));
                    db.deleteEmployee(employees.get(i));
                }

            }
        }).start();


    }


    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getFragmentManager(), "datePicker");
        }

        if (v == btnClockInPicker || v == btnClockOutPicker) {
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(getFragmentManager(), "timePicker");
        }
    }


    public int getMinutes() {

        int t1, t2, t3 = 0;

        t2 = minute.get(1);
        t1 = minute.get(0);

        if (t2 < t1 && h != 0) {
            --h;
            t3 = (t1 - t2) + 60;
        } else {
            t3 = t2 - t1;
        }
        m = t3;

        return m;

    }

    public int getHours() {

        int t1, t2, t3 = 0;

        t2 = hour.get(1);
        t1 = hour.get(0);
        String msg = "Clock out must be a later time than clock in.";

        if (t2 > t1) {
            t3 = t2 - t1;
        } else {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
        h = t3;

        return h;

    }


    @Override
    public void onDateSelected(String dateSet) {

        dateString = dateSet;

        date.add(dateSet);
        Log.i("date: ", dateString);

        for (String dates : date) {
            Log.i("Dates: ", String.valueOf(date));
        }


        if (employeeSingleton.getDate() == null) {
            employeeSingleton.setDate(date);
        } else {
            for (String dates : employeeSingleton.getDate()) {
                if (!Objects.equals(dates, dateSet)) {
                    employeeSingleton.setSingleDate(dateSet);
                } else {
                    break;
                }
            }
        }

        Log.i("Dates from object: ", String.valueOf(employeeSingleton.getDate()));

    }


    /* TODO: Correctly add times to corresponding employee instead of all times to all employees
    * TODO: set employee object equal to database employee */
    @Override
    public void onTimeSelected(String timeSet) {

        String test = "";
        timeString = timeSet;
        Log.i("Time ", timeString);

        TextView tv = (TextView) findViewById(R.id.in_time);
        TextView tv2 = (TextView) findViewById(R.id.out_time);

        String clockInTest = tv.getText().toString();
        String clockOutTest = tv2.getText().toString();

        if (!clockInTest.isEmpty() && clockOutTest.isEmpty()) {
            clockIn.add(clockInTest);
            int i = clockIn.indexOf(clockInTest);

            if (employeeSingleton.getClockIn() == null) {
                employeeSingleton.setClockIn(clockIn);
            } else {
                employeeSingleton.setSingleClockIn(clockIn.get(i));
            }
        }

        if (!clockOutTest.isEmpty()) {
            clockOut.add(clockOutTest);
            int j = clockOut.indexOf(clockOutTest);

            if (employeeSingleton.getClockOut() == null) {
                employeeSingleton.setClockOut(clockOut);
            } else {
                employeeSingleton.setSingleClockOut(clockOut.get(j));
            }

        }

        for (String time : clockIn) {
            Log.i("Clock In: ", String.valueOf(clockIn));
        }
        for (String time : clockOut) {
            Log.i("Clock Out: ", String.valueOf(clockOut));
        }

        Log.i("ClockIn from object: ", String.valueOf(employeeSingleton.getClockIn()));
        Log.i("ClockOut from object: ", String.valueOf(employeeSingleton.getClockOut()));

    }

    @Override
    public void onHourSelected(int mHour) {

        hourSelected = mHour;
        hour.add(hourSelected);
        for (int hours : hour) {
            Log.i("Hours: ", String.valueOf(hour));
        }


    }

    @Override
    public void onMinuteSelected(int mMinute) {

        minuteSelected = mMinute;
        minute.add(minuteSelected);
        for (int minutes : minute) {
            Log.i("Hours: ", String.valueOf(minute));
        }

    }

    @Override
    public void onAMPM(String mAmPm) {

        AMPM = mAmPm;
        timeOfDay.add(AMPM);

    }


}