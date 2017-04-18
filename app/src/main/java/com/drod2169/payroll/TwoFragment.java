package com.drod2169.payroll;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;


public class TwoFragment extends android.support.v4.app.Fragment {

    DatabaseHandler databaseHandler;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<String> results = new ArrayList<String>();

    ListView listView;

public TwoFragment() {

}

@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_two, container, false);
    listView = (ListView) view.findViewById(R.id.employee_list);
    databaseHandler = new DatabaseHandler(getContext());

    try {
        employees = (ArrayList<Employee>) databaseHandler.getAllEmployees();

        String name;
        Double pay_rate;
        ArrayList<String> date;
        ArrayList<String> clock_in;
        ArrayList<String> clock_out;
        List<String> values = new ArrayList<String>();
        for (Employee emps : employees) {

            name = emps.getEmployeeName();
            pay_rate = emps.getPayRate();
            date = emps.getDate();
            clock_in = emps.getClockIn();
            clock_out = emps.getClockOut();

            values.add(name + " - " + date + " - " + pay_rate + " - " + clock_in + " - " + clock_out);

        }


        for (Employee emp : employees) {
            String log = "Id: " + emp.getId() + " , Name: " + emp.getEmployeeName() + " , Pay Rate: " +
                    emp.getPayRate() + " , Dates: " + emp.getDate() + " , Clock In: " +
                    emp.getClockIn() + " , Clock Out: " + emp.getClockOut();
            //MainActivity.employee.setID(emp.getId());
            // Write to the log
            Log.i("DB: ", log);
        }
        arrayAdapter = new ArrayAdapter<>(getActivity(), simple_list_item_1, values);
        listView.setAdapter(arrayAdapter);
    } catch (Exception e) {
        e.printStackTrace();
    }


    return view;
}

/*
private void listViewItems() {

    databaseHandler = new DatabaseHandler(getContext());

    Cursor cursor = databaseHandler.getAllRows();

    List<String> values = new ArrayList<String>();

    if (cursor != null && cursor.getCount() > 0) {

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(1);
                String name = cursor.getString(2);
                String date = cursor.getString(3);
                String pay_rate = cursor.getString(4);
                String clock_in = cursor.getString(5);
                String clock_out = cursor.getString(6);

                values.add(name + " - " + date + " - " + pay_rate + " - " + clock_in + " - " + clock_out);


            } while (cursor.moveToNext());

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                    simple_list_item_1, values);
            listView.setAdapter(adapter);

            cursor.close();
        }
    }
}*/
}
