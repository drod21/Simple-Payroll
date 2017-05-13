package com.drod2169.payroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

import static android.app.Activity.RESULT_OK;


public class OneFragment extends Fragment {

    static final int HOUR_REQUEST_CODE = 1;
    double hours_final;
    static String hour_key;

    View rootView;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_one, container, false);
        Button hours = (Button) rootView.findViewById(R.id.hours);

        hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), WorkActivity.class);
                hours_final = 0.0;
                intent.putExtra(hour_key, hours_final);
                startActivityForResult(intent, HOUR_REQUEST_CODE);

            }
        });

        //DatabaseHandler db = new DatabaseHandler(getContext());




/*
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, empNameList);



        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("item", (String) parent.getItemAtPosition(position));
                String name = (String) parent.getItemAtPosition(position);
                if (EmployeeSingleton.getInstance() != null) {
                    EmployeeSingleton.resetInstance();
                }
                EmployeeSingleton.getInstance().setName(name);

                EmployeeSingleton.getInstance().setId(employees.get(position).getId());
                EmployeeSingleton.getInstance().setPayRate(employees.get(position).getPayRate());
                EmployeeSingleton.getInstance().setDate(employees.get(position).getDate());
                EmployeeSingleton.getInstance().setClockIn(employees.get(position).getClockIn());
                EmployeeSingleton.getInstance().setClockOut(employees.get(position).getClockOut());
                EmployeeSingleton.getInstance().setWorkedHours(employees.get(position).getHoursWorked());



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });*/
        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == HOUR_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                hours_final = data.getDoubleExtra(hour_key, HOUR_REQUEST_CODE);

            }
        }

        EditText hours = (EditText) rootView.findViewById(R.id.hours_worked);

        try {
            if (EmployeeSingleton.getInstance() != null) {
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                Double hrs = Double.valueOf(decimalFormat.format(EmployeeSingleton.getInstance().getTotalHours()));
                String totalHours = String.valueOf(EmployeeSingleton.getInstance().getTotalHours());

                hours.setText(String.valueOf(hrs));
                Log.i("Hours worked: ", totalHours);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}