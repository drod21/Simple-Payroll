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

import static android.app.Activity.RESULT_OK;


public class OneFragment extends Fragment {
    /* TODO: Create SQL table for storing data

     */

    static final int HOUR_REQUEST_CODE = 1;
    double hours_final;
    static String hour_key;
    String date_final;

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
        hours.setText(String.valueOf(hours_final));
        MainActivity.employee.setHoursWorked(hours_final);
        Log.i("Hours worked: ", String.valueOf(hours_final));
       /* try {

            MainActivity.myDatabase = getActivity().openOrCreateDatabase("EmployeePay", MODE_PRIVATE, null);

            MainActivity.myDatabase.execSQL("CREATE TABLE IF NOT EXISTS employeepay (name VARCHAR, date VARCHAR, payrate DOUBLE(4), clockIn DOUBLE(4), clockOut DOUBLE(4), id INTEGER PRIMARY KEY)");

            ContentValues values = new ContentValues();
            values.put("name", "test");
            values.put("date", date_final);
            values.put("payrate", 9.00);
            values.put("clockIn", hours_final);
            values.put("clockOut", hours_final);

            MainActivity.myDatabase.insert("employeepay", null, values);

            //MainActivity.myDatabase.execSQL("INSERT INTO employeepay (name, clockIn, clockOut, id) VALUES ('Test', " + hours_final + ", " + hours_final + ")");

            Cursor c = MainActivity.myDatabase.rawQuery("SELECT * FROM employeepay", null);

            int nameIndex = c.getColumnIndex("name");
            int dateIndex = c.getColumnIndex("date");
            int payRateIndex = c.getColumnIndex("payrate");
            int clockInIndex = c.getColumnIndex("clockIn");
            int clockOutIndex = c.getColumnIndex("clockOut");
            int idIndex = c.getColumnIndex("id");
            String start = null;
            String end = null;
            String type = null;


            if (c.moveToFirst()) {
                do {

                    start = c.getString(nameIndex);
                    end = c.getString(clockOutIndex);
                    Log.i("name", c.getString(nameIndex));
                    Log.i("date", c.getString(dateIndex));
                    Log.i("Pay rate: ", Double.toString(c.getDouble(payRateIndex)));
                    Log.i("Clock in: ", Double.toString(c.getDouble(clockInIndex)));
                    Log.i("Clock out: ", Double.toString(c.getDouble(clockOutIndex)));

                    //Log.i("ID: ", Integer.toString(c.getInt(idIndex)));

                } while (c.moveToNext());

            }

            //MainActivity.myDatabase.execSQL("DROP TABLE employeepay");


        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}

