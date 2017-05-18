package com.drod2169.payroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class EmployeeActivity extends AppCompatActivity {

    /* TODO: Switch to table view for better viewing. */

    int empId;
    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        //ListView listView = (ListView) findViewById(R.id.employee_list_view);
        //listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2));

        Intent intent = getIntent();
        empId = intent.getIntExtra("empId", -1);

        if (empId != -1) {

            employee = TwoFragment.employees.get(empId);
            Log.i("emp id ", String.valueOf(empId));
            //Collections.sort((List<Comparable>) employee);

        } else {

            TwoFragment.employees.add(employee);
            empId = TwoFragment.employees.size() - 1;
            TwoFragment.arrayAdapter.notifyDataSetChanged();

        }

        String totalHrsMsg = "Total hours: " + String.valueOf(employee.getTotalHoursWorked());
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("EE, MM/dd/yyyy h:mm aa");

        for (int i = 0; i < employee.getClockedInDate().size() && i < employee.getClockedOutDate().size(); i++) {
            View tableRow = LayoutInflater.from(this).inflate(R.layout.table_item, tableLayout, false);
            TextView empNameText = (TextView) tableRow.findViewById(R.id.emp_name_list);
            TextView empPayRateText = (TextView) tableRow.findViewById(R.id.emp_pay_rate_list);
            //TextView empDateText = (TextView) tableRow.findViewById(R.id.emp_date_list);
            TextView empClockInText = (TextView) tableRow.findViewById(R.id.emp_clock_in_list);
            TextView empClockOutText = (TextView) tableRow.findViewById(R.id.emp_clock_out_list);
            TextView empHoursText = (TextView) tableRow.findViewById(R.id.emp_hours_list);
            TextView empTotalHours = (TextView) tableRow.findViewById(R.id.total_hours);

            empNameText.setText(String.valueOf(employee.getId()));
            empPayRateText.setText(String.valueOf(employee.getPayRate()));

            //empDateText.setText(employee.getDate().get(i));
            empClockInText.setText(dateTimeFormatter.print(employee.getClockedInDate().get(i)));
            empClockOutText.setText(dateTimeFormatter.print(employee.getClockedOutDate().get(i)));

            empHoursText.setText(String.valueOf(employee.getHoursWorked().get(i)));

            empTotalHours.setText(totalHrsMsg);


            tableLayout.addView(tableRow);
            TwoFragment.arrayAdapter.notifyDataSetChanged();

        }


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {

        employee = TwoFragment.employees.get(empId);

        super.onResume();

    }
}
