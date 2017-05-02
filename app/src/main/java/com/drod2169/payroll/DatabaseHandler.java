package com.drod2169.payroll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class DatabaseHandler extends SQLiteOpenHelper {

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
    private ArrayList<Double> hoursWorked;

    private Gson gson = new Gson();

    private String dateInputString;
    private String clockInInputString;
    private String clockOutInputString;
    private String hoursWorkedGson;


    //private static final String[] ALL_KEYS = new String[]{KEY_ID, KEY_NAME, KEY_PAY_RATE, KEY_DATE, KEY_CLOCK_IN, KEY_CLOCK_OUT, KEY_HOURS};


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


   /* public Cursor getAllRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = null;
        Cursor c = db.query(true, TABLE_EMPLOYEE, ALL_KEYS,
                null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }*/

    // Add new employee
    void addEmployee(EmployeeSingleton employee) {

        SQLiteDatabase db = this.getWritableDatabase();

        dates = employee.getDate();
        clockIns = employee.getClockIn();
        clockOuts = employee.getClockOut();
        hoursWorked = employee.getWorkedHours();


        dateInputString = gson.toJson(dates);
        clockInInputString = gson.toJson(clockIns);
        clockOutInputString = gson.toJson(clockOuts);
        hoursWorkedGson = gson.toJson(hoursWorked);


        Log.i("Date Input String: ", dateInputString);

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, employee.getName());
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
        values.put(KEY_HOURS, hoursWorkedGson);

        if (!dbHasData(TABLE_EMPLOYEE, KEY_NAME, employee.getName())) {


            Log.i("Adding employee: ", employee.getName());
            // Inserting Row
            db.insert(TABLE_EMPLOYEE, null, values);
        } else {
            db.update(TABLE_EMPLOYEE, values, KEY_ID + "=" + MainActivity.employees.get(MainActivity.size).getId(), null);
        }

        db.close(); // Close database connection

    }

    private boolean dbHasData(String searchTable, String searchColumn, String searchKey) {
        String query = "Select * from " + searchTable + " where " + searchColumn + " = ?";
        return getReadableDatabase().rawQuery(query, new String[]{searchKey}).moveToFirst();
    }

    /* Get single employee
    public Employee getEmployee(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[]{KEY_ID,
                        KEY_NAME, KEY_PAY_RATE, KEY_DATE, KEY_CLOCK_IN, KEY_CLOCK_OUT, KEY_HOURS}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        Employee employee = null;
        if (cursor != null) {
            cursor.moveToFirst();

                employee = new EmployeeBuilder().setId(Integer.parseInt(cursor.getString(0))).setName(cursor.getString(1)).setRateOfPay(Double.parseDouble(cursor.getString(2))).setDate0(cursor.getString(3)).setClockIn0(cursor.getString(4)).setClockOut0(cursor.getString(5)).setHoursWorked0(Double.parseDouble(cursor.getString(6))).createEmployee();

            }
        if (cursor != null) {
            cursor.close();
        }
        // return employee
        return employee;
    }*/

    /* Get single employee by name
    public Employee getEmployeeByName(String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[]{KEY_ID,
                        KEY_NAME, KEY_PAY_RATE, KEY_DATE, KEY_CLOCK_IN, KEY_CLOCK_OUT, KEY_HOURS}, KEY_NAME + "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);
        Employee employee = null;


        if (cursor != null) {
            cursor.moveToFirst();

            employee = new EmployeeBuilder().setId(Integer.parseInt(cursor.getString(0))).setName(cursor.getString(1)).setRateOfPay(Double.parseDouble(cursor.getString(2))).setDate0(cursor.getString(3)).setClockIn0(cursor.getString(4)).setClockOut0(cursor.getString(5)).setHoursWorked0(Double.parseDouble(cursor.getString(6))).createEmployee();
        }

        if (cursor != null) {
            cursor.close();
        }
        // return employee
        return employee;
    }*/

    /*public Cursor queueAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{KEY_ID, KEY_NAME};

        return db.query(TABLE_EMPLOYEE, columns,
                null, null, null, null, null);
    }*/

    // Get all employees
    List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String mDate;
        String mClockIn;
        String mClockOut;
        String hours;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            Employee employee;
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                Double payRate = Double.parseDouble(cursor.getString(2));
                mDate = (cursor.getString(3));
                mClockIn = (cursor.getString(4));
                mClockOut = (cursor.getString(5));
                //ArrayList<String> hours = new ArrayList<>();
                hours = (cursor.getString(6));


                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                Type typeDouble = new TypeToken<ArrayList<Double>>() {
                }.getType();


                dateInputString = gson.toJson(mDate);
                clockInInputString = gson.toJson(mClockIn);
                clockOutInputString = gson.toJson(mClockOut);
                hoursWorkedGson = gson.toJson(hours);

                String[] splitHours = hoursWorkedGson.split("[\\[\\]\\s*, \"]");
                String hrReplace = "";
                for (String splitHr : splitHours) {
                    hrReplace += splitHr;
                }
                Log.i("hr split ", String.valueOf(splitHours));
                // String []splitHr2 = hrReplace.split(",");

                String replace1;
                String replace2 = "";
                String replClockIn = "";

                String[] splitDate = dateInputString.split("[\\[\\]\\s*, \"]");

                for (String aSplitDate : splitDate) {
                    replace2 += aSplitDate;
                }
                replace1 = replace2.replaceAll("\\\\", ",");

                String[] splitClockIn = clockInInputString.split("[\\[\\]\\s*, \"]");
                for (String aSplitClockIn : splitClockIn) {
                    replClockIn += aSplitClockIn;
                }

                String replClockIn2 = replClockIn.replaceAll("\\\\", ",");
                String[] splitClockIn2 = replClockIn2.split(",");

                String[] splitClockOut = clockOutInputString.split("[\\[\\]\\s*, \"]");
                String replClockOut = "";

                for (String aSplitClockOut : splitClockOut) {
                    replClockOut += aSplitClockOut;
                }
                String replClockOut2 = replClockOut.replaceAll("\\\\", ",");
                String[] splitClockOut2 = replClockOut2.split(",");

                String[] splitDate2 = replace1.split(",");

                List<String> dateSplit = Arrays.asList(splitDate2);
                List<String> clockInSplit = Arrays.asList(splitClockIn2);

                List<String> hoursSplit = Arrays.asList(splitHours);
                List<String> clockOutSplit = Arrays.asList(splitClockOut2);

                ArrayList<String> date = new ArrayList<>();
                ArrayList<String> clockIn = new ArrayList<>();
                ArrayList<String> clockOut = new ArrayList<>();
                ArrayList<Double> hour = new ArrayList<>();

                String dateJson = gson.toJson(dateSplit, type);
                String clockInJson = gson.toJson(clockInSplit, type);
                String clockOutJson = gson.toJson(clockOutSplit, type);
                String hoursJson = gson.toJson(hoursSplit, type);

                for (String s : hoursSplit) {
                    if (!s.isEmpty()) {
                        hour.add(Double.parseDouble(s));
                        Log.i("Hr split: ", s);
                    }
                }

                for (String s : dateSplit) {
                    if (!s.isEmpty()) {
                        date.add(s);
                    }
                }

                for (String s : clockInSplit) {
                    if (!s.isEmpty()) {
                        clockIn.add(s);
                    }
                }
                for (String s : clockOutSplit) {
                    if (!s.isEmpty()) {
                        clockOut.add(s);
                    }
                }

                // hoursWorked = gson.fromJson(hoursWorkedGson, typeDouble);
                dates = gson.fromJson(dateJson, type);
                clockIns = gson.fromJson(clockInJson, type);
                clockOuts = gson.fromJson(clockOutJson, type);

                try {
                    employee = new EmployeeBuilder().setId(id).setName(name).setRateOfPay(payRate).setDate(date).setClockIn(clockIn).setClockOut(clockOut).setHoursWorked(hour).createEmployee();
                    employeeList.add(employee);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String log = "Date: " + String.valueOf(date) + " , Clock in: " + String.valueOf(clockIn) +
                        " , Clock out: " + String.valueOf(clockOut);
                Log.i("Json conversion: ", log);

                // Adding employee to list


            } while (cursor.moveToNext());
        }

        cursor.close();
        // return employee list
        return employeeList;

    }

    /* Get employee count
    public int getEmployeeCount() {

        int count;

        String countQuery = "SELECT * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;

    }*/

    /* Update single employee
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
        values.put(KEY_HOURS, String.valueOf(employee.getHoursWorked()));

        // Update row

        return db.update(TABLE_EMPLOYEE, values, KEY_ID + " = " + employee.getId(),
                new String[]{String.valueOf(employee.getId())});

    }*/


    // Delete single employee
    void deleteEmployee(Employee employee) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, KEY_ID + " = ?",
                new String[]{String.valueOf(employee.getId())});

        db.close();

    }

    /* Delete single employee by ID
    public void deleteEmployeeById(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, KEY_ID + " = ?", new String[]{String.valueOf(id)});

        db.close();

    }
*/

}
