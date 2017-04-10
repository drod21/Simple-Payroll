package com.drod2169.payroll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PAY_RATE = "pay_rate";
    private static final String KEY_DATE = "date";
    private static final String KEY_CLOCK_IN = "clock_in";
    private static final String KEY_CLOCK_OUT = "clock_out";


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

    // Add new employee
    public void addEmployee(Employee employee) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, employee.getEmployeeName());
        values.put(KEY_PAY_RATE, employee.getPayRate());
        values.put(KEY_DATE, employee.getDate());
        values.put(KEY_CLOCK_IN, employee.getClockIn());
        values.put(KEY_CLOCK_OUT, employee.getClockOut());

        // Inserting Row
        db.insert(TABLE_EMPLOYEE, null, values);
        db.close(); // Close database connection

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

    // Get all employees
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<Employee>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setID(Integer.parseInt(cursor.getString(0)));
                employee.setEmployeeName(cursor.getString(1));
                employee.setPayRate(Double.parseDouble(cursor.getString(2)));
                employee.setDate(cursor.getString(3));
                employee.setClockIn(cursor.getString(4));
                employee.setClockOut(cursor.getString(5));
                // Adding employee to list
                employeeList.add(employee);
            } while (cursor.moveToNext());
        }

        // return employee list
        return employeeList;

    }

    // Get employee count
    public int getEmployeeCount() {

        String countQuery = "SELECT * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();

    }

    // Update single employee
    public int updateEmployee(Employee employee) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, employee.getEmployeeName());
        values.put(KEY_PAY_RATE, employee.getPayRate());
        values.put(KEY_DATE, employee.getDate());
        values.put(KEY_CLOCK_IN, employee.getClockIn());
        values.put(KEY_CLOCK_OUT, employee.getClockOut());

        // Update row

        return db.update(TABLE_EMPLOYEE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(employee.getId())});

    }

    // Delete single employee
    public void deleteEmployee(Employee employee) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, KEY_ID + " = ?",
                new String[]{String.valueOf(employee.getId())});

        db.close();

    }


}
