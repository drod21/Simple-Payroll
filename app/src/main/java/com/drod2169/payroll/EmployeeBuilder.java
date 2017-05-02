package com.drod2169.payroll;

import java.util.ArrayList;

public class EmployeeBuilder {
    private int id;
    private String name;
    private double rateOfPay;
    private ArrayList<String> date;
    private ArrayList<String> clockIn;
    private ArrayList<String> clockOut;
    private ArrayList<Double> hoursWorked;
    private double totalHoursWorked;
    private String date0;
    private String clockIn0;
    private String clockOut0;

    public EmployeeBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public EmployeeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public EmployeeBuilder setRateOfPay(double rateOfPay) {
        this.rateOfPay = rateOfPay;
        return this;
    }

    public EmployeeBuilder setDate(ArrayList<String> date) {
        this.date = date;
        return this;
    }

    public EmployeeBuilder setClockIn(ArrayList<String> clockIn) {
        this.clockIn = clockIn;
        return this;
    }

    public EmployeeBuilder setClockOut(ArrayList<String> clockOut) {
        this.clockOut = clockOut;
        return this;
    }

    public EmployeeBuilder setHoursWorked(ArrayList<Double> hoursWorked) {
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

    public double getTotalHoursWorked() {
        return totalHoursWorked;
    }

    public void setTotalHoursWorked(double totalHoursWorked) {
        this.totalHoursWorked = totalHoursWorked;
    }

    public Employee createEmployee() {
        return new Employee(id, name, rateOfPay, date, clockIn, clockOut, hoursWorked);
    }


}