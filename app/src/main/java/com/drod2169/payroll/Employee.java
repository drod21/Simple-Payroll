package com.drod2169.payroll;

import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.util.ArrayList;


class Employee {

    // TODO: Change employee date and clock in/clock out to Joda time objects


    private String employeeName;

    private ArrayList<String> clockIn;
    private ArrayList<String> clockOut;
    private ArrayList<String> date;


    private ArrayList<DateTime> clockedInDate;
    private ArrayList<DateTime> clockedOutDate;

    private double hours;
    private double weekPay;
    private double payRate;
    private ArrayList<Double> hoursWorked;
    private double totalHoursWorked;
    private double overTimeHours;

    private int id;


    // Empty constructor


    // Constructor

    public Employee() {

    }

    public Employee(
            int id, String name, double rateOfPay, ArrayList<String> date,
            ArrayList<String> clockIn, ArrayList<String> clockOut,
            ArrayList<Double> hoursWorked) {

        this.id = id;
        this.employeeName = name;
        this.payRate = rateOfPay;
        this.date = date;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.hoursWorked = hoursWorked;
    }

    public Employee(
            String name, double rateOfPay, ArrayList<String> date,
            ArrayList<String> clockIn, ArrayList<String> clockOut,
            ArrayList<Double> hoursWorked) {

        this.employeeName = name;
        this.payRate = rateOfPay;
        this.date = date;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.hoursWorked = hoursWorked;

    }


    public Employee(int id, String name, double rateOfPay, String date, String clockIn, String clockOut, double hoursWorked) {

    }

    // test
    public Employee(int id, String name, double rateOfPay, ArrayList<DateTime> clockedInDate, ArrayList<DateTime> clockedOutDate, ArrayList<Double> hoursWorked) {

        this.id = id;
        this.employeeName = name;
        this.payRate = rateOfPay;
        this.clockedInDate = clockedInDate;
        this.clockedOutDate = clockedOutDate;
        this.hoursWorked = hoursWorked;

    }


    // Mutators


    public void setDate(ArrayList<String> date) {
        this.date = date;
    }

    public void setHours(double hours) {
        this.hours += hours;
    }

    void setWeekPay() {

        double hours = getTotalHoursWorked();

        double rate = getPayRate();

        if (overTimeHours > 0) {
            this.weekPay = (overTimeHours * (rate * 1.5)) + (hours * rate);
        }

        this.weekPay = hours * rate;


    }


    // Accessors

    public int getId() {

        return this.id;

    }

    double getWeekPay() {

        return this.weekPay;

    }

    String getEmployeeName() {

        return this.employeeName;

    }

    ArrayList<Double> getHoursWorked() {

        return this.hoursWorked;

    }

    double getPayRate() {

        return this.payRate;

    }


    public double getHours() {
        return this.hours;
    }


    public ArrayList<String> getDate() {
        return this.date;
    }


    ArrayList<String> getClockOut() {
        return this.clockOut;
    }


    ArrayList<String> getClockIn() {
        return this.clockIn;
    }

    double getTotalHoursWorked() {

        Double totalHr = 0.0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for (double hour : hoursWorked) {
            if (this.totalHoursWorked <= 40.0) {
                totalHr += hour;
            } else if (this.totalHoursWorked > 40.0) {
                this.overTimeHours += hour;
            }
        }
        this.totalHoursWorked = Double.valueOf(decimalFormat.format(totalHr));
        return this.totalHoursWorked;
    }

    public double getOverTimeHours() {
        if (totalHoursWorked > 40.0) {
            overTimeHours = totalHoursWorked - 40;
        }
        return overTimeHours;
    }

    // Test

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