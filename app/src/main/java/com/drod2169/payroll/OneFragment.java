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

        EmployeeSingleton.getInstance().setSingleWorkedHours(hours_final);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        Double hrs = Double.valueOf(decimalFormat.format(EmployeeSingleton.getInstance().getTotalHours()));
        String totalHours = String.valueOf(EmployeeSingleton.getInstance().getTotalHours());

        hours.setText(String.valueOf(hrs));
        Log.i("Hours worked: ", totalHours);

    }

}

