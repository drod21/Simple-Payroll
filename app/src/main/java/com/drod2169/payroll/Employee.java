package com.drod2169.payroll;

import java.util.ArrayList;

/**
 * Created by derekrodriguez on 2/18/17.
 */

public class Employee {

    /* TODO: Possibly need to get rid of ArrayLists... Explore options */


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

    public void setID(int id) {

        this.id = id;

    }

    public void setDayOfWeek(String dayOfWeek) {

        this.dayOfWeek = dayOfWeek;

    }

    public void setMonthOfYear(String monthOfYear) {

        this.monthOfYear = monthOfYear;

    }

    public void setYear(String year) {

        this.year = year;

    }

    public void setEmployeeName(String employeeName) {

        this.employeeName = employeeName;

    }

    public void setHoursWorked(ArrayList<Double> hoursWorked) {

        this.hoursWorked = hoursWorked;

    }

    public void setPayRate(double payRate) {

        this.payRate = payRate;

    }

    public void setSingleDate(String date) {
        this.date.add(date);
    }


    public void setDate(ArrayList<String> date) {
        this.date = date;
    }

    public void setClockOut(ArrayList<String> clockOut) {
        this.clockOut = clockOut;
    }

    public void setSingleClockOut(String clockOut) {
        this.clockOut.add(clockOut);
    }

    public void setSingleClockIn(String clockIn) {
        this.clockIn.add(clockIn);
    }
    public void setClockIn(ArrayList<String> clockIn) {
        this.clockIn = clockIn;
    }

    public void setMinutes(double minutes) {
        this.minutes += minutes;
    }

    public void setHours(double hours) {
        this.hours += hours;
    }

    public void setWeekPay(double hours, double rate) {

        hours = getTotalHoursWorked();

        rate = getPayRate();

        this.weekPay = hours * rate;


    }


    // Accessors

    public int getId() {

        return this.id;

    }

    public double getWeekPay() {

        return this.weekPay;

    }

    public String getDayOfWeek() {

        return this.dayOfWeek;

    }

    public String getMonthOfYear() {

        return this.monthOfYear;

    }

    public String getYear() {

        return this.year;

    }

    public String getEmployeeName() {

        return this.employeeName;

    }

    public ArrayList<Double> getHoursWorked() {

        return this.hoursWorked;

    }

    public double getPayRate() {

        return this.payRate;

    }

    public double getMinutes() {
        return this.minutes;
    }


    public double getHours() {
        return this.hours;
    }


    public ArrayList<String> getDate() {
        return this.date;
    }


    public ArrayList<String> getClockOut() {
        return this.clockOut;
    }


    public ArrayList<String> getClockIn() {
        return this.clockIn;
    }

    public double getTotalHoursWorked() {
        return totalHoursWorked;
    }

    public void setTotalHoursWorked(double totalHoursWorked) {
        for (double hour : hoursWorked) {
            this.totalHoursWorked += hour;
        }
    }


}