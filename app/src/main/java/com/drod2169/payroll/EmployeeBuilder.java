package com.drod2169.payroll;

import java.text.DecimalFormat;
import java.util.ArrayList;

class EmployeeBuilder {

    // TODO: Change employee date and clock in/clock out to Joda time objects
    // TODO: Finish up overtime stuff

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

    public double getTotalHoursWorked() {
        Double totalHr = 0.0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        for (Double hr : hoursWorked) {
            totalHr += Double.valueOf(decimalFormat.format(hr));
        }

        this.totalHoursWorked = Double.valueOf(decimalFormat.format(totalHr));
        return totalHoursWorked;
    }

    public void setTotalHoursWorked(double totalHoursWorked) {
        this.totalHoursWorked = totalHoursWorked;
    }

    Employee createEmployee() {
        return new Employee(id, name, rateOfPay, date, clockIn, clockOut, hoursWorked);
    }


}