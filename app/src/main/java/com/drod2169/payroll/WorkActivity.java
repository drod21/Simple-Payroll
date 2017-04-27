package com.drod2169.payroll;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    String timeString;
    String AMPM;

    ArrayList<String> clockIn = new ArrayList<>();
    ArrayList<String> clockOut = new ArrayList<>();
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
        btnTimePicker = (Button) findViewById(R.id.btn_clock_in);
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

            MainActivity.employees.get(MainActivity.size).setHoursWorked(hoursFinal);

            Log.i("Hours worked: ", String.valueOf(MainActivity.employees.get(MainActivity.size).getHoursWorked()));

            Intent output = new Intent();
            output.putExtra(OneFragment.hour_key, hoursFinal);
            setResult(RESULT_OK, output);

            Log.i("Time from object: ", String.valueOf(MainActivity.employees.get(MainActivity.size).getClockIn()));
            Log.i("ClockOut from object: ", String.valueOf(MainActivity.employees.get(MainActivity.size).getClockOut()));

            Log.i("Insert: ", "Inserting..");
            try {

                db.addEmployee(MainActivity.employees.get(MainActivity.size));

                TwoFragment.arrayAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("Reading: ", "Reading all employees..");
            List<Employee> employees = db.getAllEmployees();
            for (Employee emp : employees) {
                String log = "Id: " + emp.getId() + " , Name: " + emp.getEmployeeName() + " , Pay Rate: " +
                        emp.getPayRate() + " , Dates: " + emp.getDate() + " , Clock In: " +
                        emp.getClockIn() + " , Clock Out: " + emp.getClockOut() + " , Hours Worked: " + emp.getHoursWorked();
                //MainActivity.employee.setID(emp.getId());
                // Write to the log
                Log.i("DB: ", log);


            }
            /*for (i = 0; i < employees.size(); i++) {
                    Log.i("Deleting: ", "Deleting employees.. " + employees.get(i));
                    db.deleteEmployee(employees.get(i));
                }*/

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


    public void showClockInDialog(View v) {

        DialogFragment dialogFragment = new TimePickerFragment();
        dialogFragment.show(getFragmentManager(), "timePicker");
    }

    public void showClockOutDialog(View v) {

        DialogFragment dialogFragment = new TimePickerFragment();
        dialogFragment.show(getFragmentManager(), "timePicker");

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


        if (MainActivity.employees.get(MainActivity.size).getDate() == null) {
            MainActivity.employees.get(MainActivity.size).setDate(date);
        } else {
            MainActivity.employees.get(MainActivity.size).setSingleDate(dateSet);
        }

        Log.i("Dates from object: ", String.valueOf(MainActivity.employees.get(MainActivity.size).getDate()));

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

            if (MainActivity.employees.get(MainActivity.size).getClockIn() == null) {
                MainActivity.employees.get(MainActivity.size).setClockIn(clockIn);
            } else {
                MainActivity.employees.get(MainActivity.size).setSingleClockIn(clockIn.get(i));
            }
        }

        if (!clockOutTest.isEmpty()) {
            clockOut.add(clockOutTest);
            int j = clockOut.indexOf(clockOutTest);

            if (MainActivity.employees.get(MainActivity.size).getClockOut() == null) {
                MainActivity.employees.get(MainActivity.size).setClockOut(clockOut);
            } else {
                MainActivity.employees.get(MainActivity.size).setSingleClockOut(clockOut.get(j));
            }

        }

        for (String time : clockIn) {
            Log.i("Clock In: ", String.valueOf(clockIn));
        }
        for (String time : clockOut) {
            Log.i("Clock Out: ", String.valueOf(clockOut));
        }

        Log.i("ClockIn from object: ", String.valueOf(MainActivity.employees.get(MainActivity.size).getClockIn()));
        Log.i("ClockOut from object: ", String.valueOf(MainActivity.employees.get(MainActivity.size).getClockOut()));

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