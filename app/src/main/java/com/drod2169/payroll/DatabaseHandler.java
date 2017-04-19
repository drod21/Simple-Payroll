package com.drod2169.payroll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    /* TODO: This is messy.. fix this up, make it function better. Possibly two tables, one for
       TODO: employee, one for hours/date.. likely the best route to go.
     */

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "employeeManager";

    // Employees table name
    private static final String TABLE_EMPLOYEE = "employee";

    // Employee Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PAY_RATE = "pay_rate";
    public static final String KEY_DATE = "date";
    public static final String KEY_CLOCK_IN = "clock_in";
    public static final String KEY_CLOCK_OUT = "clock_out";

    ArrayList<String> dates;
    ArrayList<String> clockIns;
    ArrayList<String> clockOuts;

    public static final String[] ALL_KEYS = new String[]{KEY_ID, KEY_NAME, KEY_DATE, KEY_CLOCK_IN, KEY_CLOCK_OUT};


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EMPLOYEES_TABLE = "CREATE TABLE " + TABLE_EMPLOYEE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PAY_RATE + " TEXT," + KEY_DATE + " TEXT," + KEY_CLOCK_IN + " TEXT,"
                + KEY_CLOCK_OUT + " TEXT" + ")";
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
    public void addEmployee(Employee employee) {

        SQLiteDatabase db = this.getWritableDatabase();

        dates = employee.getDate();
        clockIns = employee.getClockIn();
        clockOuts = employee.getClockOut();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, employee.getEmployeeName());
        values.put(KEY_PAY_RATE, employee.getPayRate());
        for (int i = 0; i < employee.getDate().size(); i++) {
            values.put(KEY_DATE, dates.get(i));
            values.put(KEY_CLOCK_IN, clockIns.get(i));
            values.put(KEY_CLOCK_OUT, clockOuts.get(i));
        }

        if (!dbHasData(TABLE_EMPLOYEE, KEY_NAME, employee.getEmployeeName())) {


            Log.i("Adding employee: ", employee.getEmployeeName());
            // Inserting Row
            db.insert(TABLE_EMPLOYEE, null, values);
        } else {
            db.update(TABLE_EMPLOYEE, values, KEY_ID + "=" + MainActivity.employee.getId(), null);
        }

        db.close(); // Close database connection

    }

    public boolean dbHasData(String searchTable, String searchColumn, String searchKey) {
        String query = "Select * from " + searchTable + " where " + searchColumn + " = ?";
        return getReadableDatabase().rawQuery(query, new String[]{searchKey}).moveToFirst();
    }

    // Get single employee
    public Employee getEmployee(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[]{KEY_ID,
                        KEY_NAME, KEY_PAY_RATE, KEY_DATE, KEY_CLOCK_IN, KEY_CLOCK_OUT}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Employee employee = new Employee(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Double.parseDouble(cursor.getString(2)), cursor.getString(3),
                cursor.getString(4), cursor.getString(5));
        // return employee
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
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<Employee>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<String> mDate = new ArrayList<>();
        ArrayList<String> mClockIn = new ArrayList<>();
        ArrayList<String> mClockOut = new ArrayList<>();
        int date_index = cursor.getColumnIndex(KEY_DATE);
        int clock_in_index = cursor.getColumnIndex(KEY_CLOCK_IN);
        int clock_out_index = cursor.getColumnIndex(KEY_CLOCK_OUT);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setID(Integer.parseInt(cursor.getString(0)));
                employee.setEmployeeName(cursor.getString(1));
                employee.setPayRate(Double.parseDouble(cursor.getString(2)));
                mDate.add(cursor.getString(3));
                mClockIn.add(cursor.getString(4));
                mClockOut.add(cursor.getString(5));

                employee.setDate(mDate);
                employee.setClockIn(mClockIn);
                employee.setClockOut(mClockOut);
                // Adding employee to list
                employeeList.add(employee);
            } while (cursor.moveToNext());
        }

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
