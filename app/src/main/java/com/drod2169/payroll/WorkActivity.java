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

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class WorkActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.onDateSelectedListener, TimePickerFragment.onTimeSelectedListener {

    /*
    TODO: Revisit shared preferences
     */
    //SharedPreferences sharedPreferences;


    DatabaseHandler db = new DatabaseHandler(this);

    Button btnDatePicker, btnClockInPicker, btnClockOutPicker;
    EditText txtDate, txtTime;

    EmployeeSingleton employeeSingleton = EmployeeSingleton.getInstance();

    int hourSelected;
    int minuteSelected;
    LocalDate mLocalDate;
    LocalTime clockInLocal;
    LocalTime clockOutLocal;

    DateTime clockInDateTime = null;
    DateTime clockOutDateTime = null;

    int h = 0;
    int m = 0;

    ArrayList<String> clockIn = new ArrayList<>();
    ArrayList<String> clockOut = new ArrayList<>();
    ArrayList<DateTime> clockedIn = new ArrayList<>();
    ArrayList<DateTime> clockedOut = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<Integer> hour = new ArrayList<>();
    ArrayList<Integer> minute = new ArrayList<>();


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

        // Initialize JodaTime
        JodaTimeAndroid.init(this);

    }


    public void getHoursFinal() {
        double hour;
        double hoursFinal;

        int minutesTime;

        getHours();
        minutesTime = getMinutes();
        Log.i("Hours subtracted: ", String.valueOf(h));
        Log.i("Minutes subtracted: ", String.valueOf(m));
        Log.i("Minute subtracted new: ", String.valueOf(minutesTime));

        hour = (double) h + ((double) minutesTime / 100);
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

        Employee employee = new EmployeeBuilder()
                .setId(EmployeeSingleton.getInstance().getId())
                .setName(EmployeeSingleton.getInstance().getName())
                .setRateOfPay(EmployeeSingleton.getInstance().getPayRate())
                .setClockedInDate(clockedIn)
                .setClockedOutDate(clockedOut)
                .setHoursWorked(hours)
                .createEmployeeTest();

        String log = "ID: " + employee.getId() + " Name: " + employee.getEmployeeName() + " Pay: " + employee.getPayRate() + " Clocked in: " + employee.getClockedInDate() + " Clocked out: " + employee.getClockedOutDate()
                + " Hours worked: " + employee.getHoursWorked();
        Log.i("Employee with joda ", log);


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
                                emp.getPayRate() + " , Clock In: " +
                                emp.getClockedInDate() + " , Clock Out: " + emp.getClockedOutDate() + " , Hours Worked: " + emp.getHoursWorked();
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

        double t1, t2, t3;
        t1 = minute.get(0);
        t2 = minute.get(1);

        if (t2 < t1 && h != 0) {
            --h;
        }

        t3 = (t2 - t1) / 60 * 100;
        if (t3 < 0) {
            t3 = Math.abs(t3);
        }

        m = (int) t3;

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
    public void onLocalDateSelected(LocalDate localDate) {
        mLocalDate = localDate;
    }

    @Override
    public void onTimeSelected(String timeSet) {

        LocalDate clockInDate;
        LocalDate clockOutDate;

        TextView tv = (TextView) findViewById(R.id.in_time);
        TextView tv2 = (TextView) findViewById(R.id.out_time);

        String clockInTest = tv.getText().toString();
        String clockOutTest = tv2.getText().toString();

        if (!clockInTest.isEmpty() && clockOutTest.isEmpty()) {
            clockInDate = mLocalDate;
            LocalTime clIn = LocalTime.parse(clockInTest);
            clockInDateTime = clockInDate.toDateTime(clIn);
            clockedIn.add(clockInDateTime);

            if (employeeSingleton.getClockedInDate() == null) {
                employeeSingleton.setClockedInDate(clockedIn);
            } else {
                employeeSingleton.setSingleClockedInDate(clockInDateTime);
            }
        }

        if (!clockOutTest.isEmpty()) {
            clockOutDate = mLocalDate;
            LocalTime clOut = LocalTime.parse(clockOutTest);
            clockOutDateTime = clockOutDate.toDateTime(clOut);
            clockedOut.add(clockOutDateTime);

            if (employeeSingleton.getClockedOutDate() == null) {
                employeeSingleton.setClockedOutDate(clockedOut);
            } else {
                employeeSingleton.setSingleClockedOutDate(clockOutDateTime);
            }

        }
        Log.i("ClockIn from object: ", String.valueOf(employeeSingleton.getClockedInDate()));
        Log.i("ClockOut from object: ", String.valueOf(employeeSingleton.getClockedOutDate()));

    }

    @Override
    public void onHourSelected(int mHour) {
        hourSelected = mHour;
        hour.add(hourSelected);
    }

    @Override
    public void onMinuteSelected(int mMinute) {
        minuteSelected = mMinute;
        minute.add(minuteSelected);
    }

    @Override
    public void onClockInSelected(LocalTime clockInTest) {
        clockInLocal = clockInTest;
        Log.i("ClockInLocal ", String.valueOf(clockInLocal));
    }

    @Override
    public void onClockOutSelected(LocalTime clockOutTest) {
        clockOutLocal = clockOutTest;
        Log.i("ClockOutLocal ", String.valueOf(clockOutLocal));
    }
}