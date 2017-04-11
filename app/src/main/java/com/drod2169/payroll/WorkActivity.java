package com.drod2169.payroll;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class WorkActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.onDateSelectedListener, TimePickerFragment.onTimeSelectedListener {

    /*
    TODO: Revisit shared preferences
     */
    //SharedPreferences sharedPreferences;

    /* TODO: Create SQL table for storing data
       TODO: Pass date back to OneFragment
     */

    DatabaseHandler db = new DatabaseHandler(this);

    Employee emp = new Employee();
    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;

    String dateString;

    int hourSelected;
    int minuteSelected;
    String name;
    double payRate;


    String timeString;
    String AMPM;

    ArrayList<String> times = new ArrayList<>();
    ArrayList<Date> timedate = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<Integer> hour = new ArrayList<>();
    ArrayList<Integer> minute = new ArrayList<>();
    ArrayList<String> timeOfDay = new ArrayList<>();


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

    public void finalHours(View v) {

        int h = 0, m = 0;
        int i;

        double hoursFinal = 0.0;

        Date t;

        for (i = 1; i < hour.size(); i++) {

            if (hour.get(i) > hour.get(i - 1)) {
                h = getHours(hour.get(i - 1), hour.get(i));
                //m = getMinutes(minute.get(i), minute.get(i + 1));
            }

            if (minute.get(i) < minute.get(i - 1)) {

                --h;
                m = getMinutes(minute.get(i - 1), minute.get(i)) + 60;

            } else if (minute.get(i) >= minute.get(i - 1)) {

                m = getMinutes(minute.get(i - 1), minute.get(i));

            }


            i++;
            Log.i("Times subtracted: ", String.valueOf(h));
            Log.i("Times subtracted: ", String.valueOf(m));

            hoursFinal = (double) h + ((double) m / 100);

            Log.i("Final: ", String.valueOf(hoursFinal));

            Intent output = new Intent();
            output.putExtra(OneFragment.hour_key, hoursFinal);
            setResult(RESULT_OK, output);

            Log.i("Insert: ", "Inserting..");
            try {

                db.addEmployee(new Employee(MainActivity.employee.getEmployeeName(), MainActivity.employee.getPayRate(), dateString, times.get(0), times.get(1)));

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("Reading: ", "Reading all employees..");
            List<Employee> employees = db.getAllEmployees();

            for (Employee emp : employees) {
                String log = "Id: " + emp.getId() + " , Name: " + emp.getEmployeeName() + " , " +
                        emp.getPayRate() + " , " + emp.getDate() + " , " + emp.getClockIn() + " , "
                        + emp.getClockOut();
                // Write to the log
                Log.i("Name: ", log);
            }

            finish();

        }

        emp.setHours(h);
        emp.setMinutes(m);
        String time = String.valueOf(h) + ":" + String.valueOf(m);

        t = getTime(time);
        timedate.add(t);

    }

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

    public Date getTime(String timePicked) {

        Date time = new Date();
        //String timeNew;

        SimpleDateFormat simpleDateFormat = null;
        try {
            time = new SimpleDateFormat("hh:mm", Locale.getDefault()).parse(timePicked);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.i("Timedate: ", String.valueOf(timedate));
        return time;

    }

    public int getMinutes(int t1, int t2) {

        int t3;


        t3 = t2 - t1;

        return t3;
    }

    public int getHours(int t1, int t2) {

        int t3 = 0;

        if (t2 > t1) {
            t3 += t2 - t1;
        }

        return t3;

    }


    @Override
    public void onDateSelected(String dateSet) {

        dateString = dateSet;


        date.add(dateSet);
        Log.i("date: ", dateString);

        for (String dates : date) {
            Log.i("Dates: ", String.valueOf(date));
        }

    }


    @Override
    public void onTimeSelected(String timeSet) {

        String test = "";
        timeString = timeSet;
        Log.i("Time ", timeString);

        times.add(timeString);

        for (String time : times) {
            Log.i("Time ", String.valueOf(times));
        }

        Log.i("Time with Date member ", String.valueOf(getTime(timeString)));


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
