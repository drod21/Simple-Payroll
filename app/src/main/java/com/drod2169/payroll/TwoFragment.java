package com.drod2169.payroll;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static android.R.layout.simple_list_item_1;


public class TwoFragment extends android.support.v4.app.Fragment {

    DatabaseHandler databaseHandler;
    static ArrayAdapter<String> arrayAdapter;
    static ArrayList<Employee> employees = new ArrayList<>();
    static ArrayList<String> results = new ArrayList<String>();
    static List<String> values;
    static final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

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


        String name;
        values = new ArrayList<String>();
        for (Employee emps : employees) {

            name = emps.getEmployeeName();

            values.add(name);
            //Collections.sort(values);

        }
        /*ArrayList<String> datesToSort = new ArrayList<>();
        for (Employee emp : employees) {
            datesToSort = emp.getDate();
        }*/

        //Collections.sort(datesToSort);

        for (Employee emp : employees) {
            String log = "Id: " + emp.getId() + " , Name: " + emp.getEmployeeName() + " , Pay Rate: " +
                    emp.getPayRate() + " , Dates: " + emp.getDate() + " , Clock In: " +
                    emp.getClockIn() + " , Clock Out: " + emp.getClockOut() + " , Hours Worked: " + emp.getHoursWorked();
            //MainActivity.employee.setID(emp.getId());
            // Write to the log
            Log.i("DB: ", log);
        }

        arrayAdapter = new ArrayAdapter<>(getActivity(), simple_list_item_1, values);

        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(), EmployeeActivity.class);
                intent.putExtra("empId", i);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDelete = i;

                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this employee record?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                updateDeleteItems(itemToDelete);

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;

            }

        });

    } catch (Exception e) {

        e.printStackTrace();

    }

    return view;

}

    public void updateListView(String newNames) {

        List<String> newList = new ArrayList<String>();
        newList.add(newNames);
        for (int i = 0; i < values.size(); i++) {
            if (Objects.equals(values.get(i), newNames)) {
                break;
            } else {
                newList.add(newNames);
            }
        }

        values = newList;
        Log.i("Values: ", String.valueOf(values));
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);

        arrayAdapter.notifyDataSetChanged();

    }


    public void updateDeleteItems(int id) {

        DatabaseHandler db = new DatabaseHandler(getContext());

        Log.i("Employee to delete: ", employees.get(id).getEmployeeName());
        db.deleteEmployee(employees.get(id));
        values.remove(id);
        employees.remove(id);
        arrayAdapter.notifyDataSetChanged();

    }

    private void populateList() {

        employees = (ArrayList<Employee>) databaseHandler.getAllEmployees();

        HashMap<String, String> temp = new HashMap<>();
        for (Employee emp : employees) {
            temp.put("name", emp.getEmployeeName());
        /*temp.put("pay_rate", String.valueOf(emp.getPayRate()));
        temp.put("date", String.valueOf(emp.getDate()));
        temp.put("clock_in", String.valueOf(emp.getClockIn()));
        temp.put("clock_out", String.valueOf(emp.getClockOut()));
        temp.put("hours_worked", String.valueOf(emp.getHoursWorked()));*/
            list.add(temp);
        }
    }

    @Override
    public void onResume() {

        super.onResume();

        arrayAdapter.setNotifyOnChange(true);

    }

}
