package com.drod2169.payroll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    /* TODO: This is messy.. fix this up, make it function better. Possibly two tables, one for
       TODO: employee, one for hours/date.. likely the best route to go.
     */

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "employeeManager";

    // Employees table name
    private static final String TABLE_EMPLOYEE = "employee";

    // Employee Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PAY_RATE = "pay_rate";
    private static final String KEY_DATE = "date";
    private static final String KEY_CLOCK_IN = "clock_in";
    private static final String KEY_CLOCK_OUT = "clock_out";
    private static final String KEY_HOURS = "hours_worked";

    public int id;

    private ArrayList<String> dates;
    private ArrayList<String> clockIns;
    private ArrayList<String> clockOuts;

    private ArrayList<String> mDates;
    private ArrayList<String> mClockIns;
    private ArrayList<String> mClockOuts;
    Gson gson = new Gson();

    String dateInputString;
    String clockInInputString;
    String clockOutInputString;

    JSONObject jsonObject1 = new JSONObject();
    JSONObject jsonObject2 = new JSONObject();
    JSONObject jsonObject3 = new JSONObject();

    JSONArray datesJson;
    JSONArray clockInsJson;
    JSONArray clockOutsJson;

    private static final String[] ALL_KEYS = new String[]{KEY_ID, KEY_NAME, KEY_PAY_RATE, KEY_DATE, KEY_CLOCK_IN, KEY_CLOCK_OUT, KEY_HOURS};


    DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EMPLOYEES_TABLE = "CREATE TABLE " + TABLE_EMPLOYEE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PAY_RATE + " TEXT," + KEY_DATE + " TEXT," + KEY_CLOCK_IN + " TEXT,"
                + KEY_CLOCK_OUT + " TEXT," + KEY_HOURS + " TEXT" + ")";
        db.execSQL(CREATE_EMPLOYEES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);

        // Create tables again
        onCreate(db);
    }


    public Cursor getAllRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = null;
        Cursor c = db.query(true, TABLE_EMPLOYEE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Add new employee
    void addEmployee(Employee employee) {

        SQLiteDatabase db = this.getWritableDatabase();

        dates = employee.getDate();
        clockIns = employee.getClockIn();
        clockOuts = employee.getClockOut();


        dateInputString = gson.toJson(dates);
        clockInInputString = gson.toJson(clockIns);
        clockOutInputString = gson.toJson(clockOuts);

        Log.i("Date Input String: ", dateInputString);

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, employee.getEmployeeName());
        values.put(KEY_PAY_RATE, employee.getPayRate());

        values.put(KEY_DATE, dateInputString);
        values.put(KEY_CLOCK_IN, clockInInputString);
        values.put(KEY_CLOCK_OUT, clockOutInputString);
        Log.i("Date from Gson: ", dateInputString);
        /*values.put(KEY_DATE, String.valueOf(employee.getDate()));
        values.put(KEY_CLOCK_IN, String.valueOf(employee.getClockIn()));
        values.put(KEY_CLOCK_OUT, String.valueOf(employee.getClockOut()));*/
        /*for (int i = 0; i < employee.getDate().size(); i++) {
            values.put(KEY_DATE, dates.get(i));
            values.put(KEY_CLOCK_IN, clockIns.get(i));
            values.put(KEY_CLOCK_OUT, clockOuts.get(i));
        }*/
        values.put(KEY_HOURS, employee.getHoursWorked());

        if (!dbHasData(TABLE_EMPLOYEE, KEY_NAME, employee.getEmployeeName())) {


            Log.i("Adding employee: ", employee.getEmployeeName());
            // Inserting Row
            db.insert(TABLE_EMPLOYEE, null, values);
        } else {
            db.update(TABLE_EMPLOYEE, values, KEY_ID + "=" + MainActivity.employee.getId(), null);
        }

        db.close(); // Close database connection

    }

    boolean dbHasData(String searchTable, String searchColumn, String searchKey) {
        String query = "Select * from " + searchTable + " where " + searchColumn + " = ?";
        return getReadableDatabase().rawQuery(query, new String[]{searchKey}).moveToFirst();
    }

    // Get single employee
    public Employee getEmployee(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[]{KEY_ID,
                        KEY_NAME, KEY_PAY_RATE, KEY_DATE, KEY_CLOCK_IN, KEY_CLOCK_OUT, KEY_HOURS}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Employee employee = null;
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        if (cursor != null) {
            cursor.moveToFirst();

            employee = new Employee(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), Double.parseDouble(cursor.getString(2)), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), Double.parseDouble(cursor.getString(6)));
        }
        // return employee
        cursor.close();
        return employee;
    }

    // Get single employee by name
    public Employee getEmployeeByName(String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[]{KEY_ID,
                        KEY_NAME, KEY_PAY_RATE, KEY_DATE, KEY_CLOCK_IN, KEY_CLOCK_OUT, KEY_HOURS}, KEY_NAME + "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);
        Employee employee = null;

        if (cursor != null) {
            cursor.moveToFirst();

            employee = new Employee(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), Double.parseDouble(cursor.getString(2)), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), Double.parseDouble(cursor.getString(6)));
        }
        // return employee
        cursor.close();
        return employee;
    }

    public Cursor queueAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{KEY_ID, KEY_NAME};
        Cursor cursor = db.query(TABLE_EMPLOYEE, columns,
                null, null, null, null, null);

        return cursor;
    }

    // Get all employees
    List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<Employee>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;
        Employee employee = new Employee();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String mDate;
        String mClockIn;
        String mClockOut;

        //Type type = new TypeToken<ArrayList<String>>() {}.getType();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                employee.setID(Integer.parseInt(cursor.getString(0)));
                employee.setEmployeeName(cursor.getString(1));
                employee.setPayRate(Double.parseDouble(cursor.getString(2)));
                mDate = (cursor.getString(3));
                mClockIn = (cursor.getString(4));
                mClockOut = (cursor.getString(5));

                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();

                dateInputString = gson.toJson(mDate);
                clockInInputString = gson.toJson(mClockIn);
                clockOutInputString = gson.toJson(mClockOut);

                Log.i("mDate: ", dateInputString);
                Log.i("mClockIn: ", mClockIn);

                for (String s : dateInputString.split("[\\[\\]\\,\\\\\"]")) {
                    Log.i("string ", s);
                }
                String[] splitDate = dateInputString.split("[\\[\\]\\,\\\\\"]");
                String[] splitClockIn = clockInInputString.split("[\\[\\]\\,\\\\\"]");
                String[] splitClockOut = clockOutInputString.split("[\\[\\]\\,\\\\\"]");

                List<String> dateSplit = new ArrayList<>();
                dateSplit.add(splitDate[splitDate.length - 1]);
                List<String> clockInSplit = new ArrayList<>();
                clockInSplit.add(splitClockIn[splitClockIn.length - 1]);
                List<String> clockOutSplit = new ArrayList<>();
                clockOutSplit.add(splitClockOut[splitClockOut.length - 1]);
                for (String s : dateSplit) {
                    Log.i("String split: ", s);
                }
                ArrayList<String> date = new ArrayList<>();
                ArrayList<String> clockIn = new ArrayList<>();
                ArrayList<String> clockOut = new ArrayList<>();

                String dateJson = gson.toJson(dateSplit, type);
                String clockInJson = gson.toJson(clockInSplit, type);
                String clockOutJson = gson.toJson(clockOutSplit, type);

                for (String s : dateSplit) {
                    date.add(s);

                }

                Log.i("String split array: ", String.valueOf(date));


                dates = gson.fromJson(dateJson, type);
                clockIns = gson.fromJson(clockInJson, type);
                clockOuts = gson.fromJson(clockOutJson, type);

                Log.i("date from gson array: ", String.valueOf(date));

                employee.setDate(dates);
                employee.setClockIn(clockIns);
                employee.setClockOut(clockOuts);
                String log = "Date: " + String.valueOf(date) + " , Clock in: " + String.valueOf(clockIn) +
                        " , Clock out: " + String.valueOf(clockOut);
                Log.i("Json conversion: ", log);

                // Adding employee to list
                employeeList.add(employee);

                employee.setHoursWorked(Double.parseDouble(cursor.getString(6)));
            } while (cursor.moveToNext());
        }


        cursor.close();
        // return employee list
        return employeeList;

    }

    // Get employee count
    public int getEmployeeCount() {

        int count;

        String countQuery = "SELECT * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;

    }

    // Update single employee
    public int updateEmployee(Employee employee) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, employee.getEmployeeName());
        values.put(KEY_PAY_RATE, employee.getPayRate());
        for (int i = 0; i < employee.getDate().size(); i++) {
            values.put(KEY_DATE, dates.get(i));
            values.put(KEY_CLOCK_IN, clockIns.get(i));
            values.put(KEY_CLOCK_OUT, clockOuts.get(i));
        }
        values.put(KEY_HOURS, employee.getHoursWorked());

        // Update row

        return db.update(TABLE_EMPLOYEE, values, KEY_ID + " = " + employee.getId(),
                new String[]{String.valueOf(employee.getId())});

    }


    // Delete single employee
    public void deleteEmployee(Employee employee) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, KEY_ID + " = ?",
                new String[]{String.valueOf(employee.getId())});

        db.close();

    }

    // Delete single employee by ID
    public void deleteEmployeeById(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, KEY_ID + " = ?", new String[]{String.valueOf(id)});

        db.close();

    }


}
