package com.drod2169.payroll;

import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.util.ArrayList;

class EmployeeBuilder {

    // TODO: Change employee date and clock in/clock out to Joda time objects

    private int id;
    private String name;
    private double rateOfPay;
    private ArrayList<String> date;
    private ArrayList<String> clockIn;
    private ArrayList<String> clockOut;
    private ArrayList<Double> hoursWorked;
    private double totalHoursWorked;
    private double overTimeHours;
    private String date0;
    private String clockIn0;
    private String clockOut0;


    private ArrayList<DateTime> clockedInDate = new ArrayList<>();
    private ArrayList<DateTime> clockedOutDate = new ArrayList<>();

    public EmployeeBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public EmployeeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    EmployeeBuilder setRateOfPay(double rateOfPay) {
        this.rateOfPay = rateOfPay;
        return this;
    }

    public EmployeeBuilder setDate(ArrayList<String> date) {
        this.date = date;
        return this;
    }

    EmployeeBuilder setClockIn(ArrayList<String> clockIn) {
        this.clockIn = clockIn;
        return this;
    }

    EmployeeBuilder setClockOut(ArrayList<String> clockOut) {
        this.clockOut = clockOut;
        return this;
    }

    EmployeeBuilder setHoursWorked(ArrayList<Double> hoursWorked) {
        this.hoursWorked = hoursWorked;
        return this;
    }

    public EmployeeBuilder setHoursWorked0(Double hoursWorked) {
        this.hoursWorked.add(hoursWorked);
        return this;
    }

    public EmployeeBuilder setDate0(String date0) {
        this.date0 = date0;
        return this;
    }

    public EmployeeBuilder setClockIn0(String clockIn0) {
        this.clockIn0 = clockIn0;
        return this;
    }

    public EmployeeBuilder setClockOut0(String clockOut0) {
        this.clockOut0 = clockOut0;
        return this;
    }

    public double getOverTimeHours() {
        if (totalHoursWorked > 40.0) {
            overTimeHours = totalHoursWorked - 40;
        }
        return overTimeHours;
    }

    public EmployeeBuilder setOverTimeHours(double overTimeHours) {
        this.overTimeHours = overTimeHours;
        return this;
    }

    public double getTotalHoursWorked() {
        Double totalHr = 0.0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        for (Double hr : hoursWorked) {
            totalHr += Double.valueOf(decimalFormat.format(hr));
        }

        this.totalHoursWorked = Double.valueOf(decimalFormat.format(totalHr));
        return totalHoursWorked;
    }

    public EmployeeBuilder setTotalHoursWorked(double totalHoursWorked) {
        this.totalHoursWorked = totalHoursWorked;
        return this;
    }


    public ArrayList<DateTime> getClockedInDate() {
        return clockedInDate;
    }

    public EmployeeBuilder setClockedInDate(ArrayList<DateTime> clockedInDate) {
        this.clockedInDate = clockedInDate;
        return this;

    }

    public EmployeeBuilder setSingleClockedInDate(DateTime clockedInDate) {
        this.clockedInDate.add(clockedInDate);
        return this;
    }

    public ArrayList<DateTime> getClockedOutDate() {
        return clockedOutDate;
    }

    public EmployeeBuilder setClockedOutDate(ArrayList<DateTime> clockedOutDate) {
        this.clockedOutDate = clockedOutDate;
        return this;
    }

    public EmployeeBuilder setSingleClockedOutDate(DateTime clockedOutDate) {
        this.clockedOutDate.add(clockedOutDate);
        return this;
    }

    Employee createEmployeeTest() {
        return new Employee(id, name, rateOfPay, clockedInDate, clockedOutDate, hoursWorked);
    }

    Employee createEmployee() {
        return new Employee(id, name, rateOfPay, date, clockIn, clockOut, hoursWorked);
    }


}