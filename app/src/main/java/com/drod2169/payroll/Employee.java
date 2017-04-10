package com.drod2169.payroll;

/**
 * Created by derekrodriguez on 2/18/17.
 */

public class Employee {

    private String employeeName;
    private String dayOfWeek;
    private String monthOfYear;
    private String year;

    private String clockIn;
    private String clockOut;
    private String date;

    private double hours;
    private double minutes;
    private double weekPay;
    private double payRate;
    private double hoursWorked;
    private double totalPay;

    private int id;


    // Empty constructor


    // Constructor

    public Employee() {

    }

    public Employee(int id, String name, double rateOfPay, String date, String clockIn, String clockOut) {

        this.id = id;
        this.employeeName = name;
        this.payRate = rateOfPay;
        this.date = date;
        this.clockIn = clockIn;
        this.clockOut = clockOut;

    }

    public Employee(String name, double rateOfPay, String date, String clockIn, String clockOut) {

        this.employeeName = name;
        this.payRate = rateOfPay;
        this.date = date;
        this.clockIn = clockIn;
        this.clockOut = clockOut;

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

    public void setHoursWorked(double hoursWorked) {

        this.hoursWorked = hoursWorked;

    }

    public void setPayRate(double payRate) {

        this.payRate = payRate;

    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setClockOut(String clockOut) {
        this.clockOut = clockOut;
    }

    public void setClockIn(String clockIn) {
        this.clockIn = clockIn;
    }

    public void setMinutes(double minutes) {
        this.minutes += minutes;
    }

    public void setHours(double hours) {
        this.hours += hours;
    }

    public void setWeekPay(double hours, double rate) {

        hours = getHoursWorked();

        rate = getPayRate();

        this.weekPay = hours * rate;


    }


    // Accessors

    public int getId() {

        return id;

    }

    public double getWeekPay() {

        return weekPay;

    }

    public String getDayOfWeek() {

        return dayOfWeek;

    }

    public String getMonthOfYear() {

        return monthOfYear;

    }

    public String getYear() {

        return year;

    }

    public String getEmployeeName() {

        return employeeName;

    }

    public double getHoursWorked() {

        return hoursWorked;

    }

    public double getPayRate() {

        return payRate;

    }

    public double getMinutes() {
        return minutes;
    }


    public double getHours() {
        return hours;
    }


    public String getDate() {
        return date;
    }


    public String getClockOut() {
        return clockOut;
    }


    public String getClockIn() {
        return clockIn;
    }


}
