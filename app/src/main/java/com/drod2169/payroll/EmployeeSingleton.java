package com.drod2169.payroll;

import android.app.Application;

import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by derekrodriguez on 4/29/17.
 */

public class EmployeeSingleton extends Application {
    // TODO: Change employee date and clock in/clock out to Joda time objects

    private int id;
    private String name;
    private double payRate;
    private ArrayList<String> date;
    private ArrayList<String> clockIn;
    private ArrayList<String> clockOut;
    private ArrayList<Double> workedHours;
    private double totalHours;
    private double overTimeHours;
    private ArrayList<DateTime> clockedInDate;
    private ArrayList<DateTime> clockedOutDate;

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
        Double totalHr = 0.0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for (double hour : workedHours) {
                totalHr += Double.valueOf(decimalFormat.format(hour));
            }
        this.totalHours = Double.valueOf(decimalFormat.format(totalHr));

        return totalHours;
    }

    public double getOverTimeHours() {
        if (totalHours > 40.0) {
            overTimeHours = totalHours - 40;
        }
        return overTimeHours;
    }

    public void setOverTimeHours(double overTimeHours) {

        this.overTimeHours = overTimeHours;
    }

    public ArrayList<DateTime> getClockedInDate() {
        return clockedInDate;
    }

    public void setClockedInDate(ArrayList<DateTime> clockedInDate) {
        this.clockedInDate = clockedInDate;

    }

    public void setSingleClockedInDate(DateTime clockedInDate) {
        this.clockedInDate.add(clockedInDate);
    }

    public ArrayList<DateTime> getClockedOutDate() {
        return clockedOutDate;
    }

    public void setClockedOutDate(ArrayList<DateTime> clockedOutDate) {
        this.clockedOutDate = clockedOutDate;
    }

    public void setSingleClockedOutDate(DateTime clockedOutDate) {
        this.clockedOutDate.add(clockedOutDate);
    }
}
