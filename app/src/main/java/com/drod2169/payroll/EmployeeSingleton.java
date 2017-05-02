package com.drod2169.payroll;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by derekrodriguez on 4/29/17.
 */

public class EmployeeSingleton extends Application {

    private int id;
    private String name;
    private double payRate;
    private ArrayList<String> date;
    private ArrayList<String> clockIn;
    private ArrayList<String> clockOut;
    private ArrayList<Double> workedHours;
    private double totalHours;

    private static volatile EmployeeSingleton instance;

    public static EmployeeSingleton getInstance() {

        if (instance == null) {
            synchronized (EmployeeSingleton.class) {
                if (instance == null) {

                    instance = new EmployeeSingleton();
                }
            }
        }
        // Return the instance
        return instance;
    }


    synchronized static void resetInstance() {
        instance = new EmployeeSingleton();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPayRate() {
        return payRate;
    }

    public void setPayRate(double payRate) {
        this.payRate = payRate;
    }

    public ArrayList<String> getDate() {
        return date;
    }

    public void setDate(ArrayList<String> date) {
        this.date = date;
    }

    public void setSingleDate(String date) {
        this.date.add(date);
    }

    public ArrayList<String> getClockIn() {
        return clockIn;
    }

    public void setClockIn(ArrayList<String> clockIn) {
        this.clockIn = clockIn;
    }

    public void setSingleClockOut(String clockOut) {
        this.clockOut.add(clockOut);
    }

    public void setSingleClockIn(String clockIn) {
        this.clockIn.add(clockIn);
    }

    public ArrayList<String> getClockOut() {
        return clockOut;
    }

    public void setClockOut(ArrayList<String> clockOut) {
        this.clockOut = clockOut;
    }

    public ArrayList<Double> getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(ArrayList<Double> workedHours) {
        this.workedHours = workedHours;
    }

    public void setSingleWorkedHours(double workedHours) {
        this.workedHours.add(workedHours);
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {

        for (double hour : workedHours) {
            this.totalHours += hour;
        }
    }
}
