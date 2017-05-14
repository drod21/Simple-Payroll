package com.drod2169.payroll;

import java.text.DecimalFormat;
import java.util.ArrayList;


class Employee {

    // TODO: Change employee date and clock in/clock out to Joda time objects
    // TODO: Finish up overtime stuff

    private String employeeName;
    private String dayOfWeek;
    private String monthOfYear;
    private String year;

    private ArrayList<String> clockIn;
    private ArrayList<String> clockOut;
    private ArrayList<String> date;

    private double hours;
    private double minutes;
    private double weekPay;
    private double payRate;
    private ArrayList<Double> hoursWorked;
    private double totalHoursWorked;
    private double overTimeHours;
    private double totalPay;

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
        return overTimeHours;
    }


}