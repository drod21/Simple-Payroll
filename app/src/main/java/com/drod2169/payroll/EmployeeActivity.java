package com.drod2169.payroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

public class EmployeeActivity extends AppCompatActivity {

    /* TODO: Switch to table view for better viewing. */

    int empId;
    Employee employee;

    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        //ListView listView = (ListView) findViewById(R.id.employee_list_view);
        //listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2));

        Intent intent = getIntent();
        empId = intent.getIntExtra("empId", -1);

        if (empId != -1) {

            employee = TwoFragment.employees.get(empId);
            //Collections.sort((List<Comparable>) employee);

        } else {

            TwoFragment.employees.add(employee);
            empId = TwoFragment.employees.size() - 1;
            TwoFragment.arrayAdapter.notifyDataSetChanged();

        }


        for (int i = 0; i < employee.getDate().size(); i++) {
            View tableRow = LayoutInflater.from(this).inflate(R.layout.table_item, null, false);
            TextView empNameText = (TextView) tableRow.findViewById(R.id.emp_name_list);
            TextView empPayRateText = (TextView) tableRow.findViewById(R.id.emp_pay_rate_list);
            TextView empDateText = (TextView) tableRow.findViewById(R.id.emp_date_list);
            TextView empClockInText = (TextView) tableRow.findViewById(R.id.emp_clock_in_list);
            TextView empClockOutText = (TextView) tableRow.findViewById(R.id.emp_clock_out_list);
            TextView empHoursText = (TextView) tableRow.findViewById(R.id.emp_hours_list);

            empNameText.setText(employee.getEmployeeName());
            empPayRateText.setText(String.valueOf(employee.getPayRate()));
            for (int j = 0; j < employee.getDate().size(); j++) {
                empDateText.setText(employee.getDate().get(i));
                empClockInText.setText(employee.getClockIn().get(i));
                empClockOutText.setText(employee.getClockOut().get(i));
            }
            empHoursText.setText(String.valueOf(employee.getHours()));

            tableLayout.addView(tableRow);
            TwoFragment.arrayAdapter.notifyDataSetChanged();

        }


    }
}
