package com.drod2169.payroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LVActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expandableListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lv);

        // get the listview
        expandableListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expandableListView.setAdapter(listAdapter);
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Dates worked");
        listDataHeader.add("Hours Worked");


       /* Bundle date = getIntent().getExtras();

        if (date != null) {

            int day = date.getInt("day");
            int month = date.getInt("month");
            int year = date.getInt("year");

            dateString = (month + 1) + "/" + day + "/" + year;

            Toast.makeText(this, dateString, Toast.LENGTH_SHORT);

        } else {

            Toast.makeText(this, "Bundle is empty", Toast.LENGTH_SHORT).show();
        }*/

        // Adding child data
        List<String> dates = new ArrayList<>();
        /*String dateAdded = ((EditText)findViewById(R.id.in_date)).getText().toString();
        if (dateAdded != null) {
            dates.add(dateAdded);
        }*/
        //dates.add(getDate());


        List<String> hours = new ArrayList<>();
        hours.add("The Conjuring");



        listDataChild.put(listDataHeader.get(0), dates); // Header, Child data
        listDataChild.put(listDataHeader.get(1), hours);
    }
}
