package com.drod2169.payroll;

/**
 * Created by derekrodriguez on 2/18/17.
 */

public class Employee {

    private String employeeName;
    private String dayOfWeek;
    private String monthOfYear;
    private String year;

    private double hours;
    private double minutes;
    private double weekPay;
    private double payRate;
    private double hoursWorked;
    private double totalPay;



    // Constructor

    public Employee() {

        payRate = 0.0;
        hoursWorked = 0.0;
        hours = 0;
        minutes = 0;

        employeeName = "";

        dayOfWeek = "";
        monthOfYear = "";
        year = "";

    }

    // Mutators

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


}
