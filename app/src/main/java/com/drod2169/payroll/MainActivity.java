package com.drod2169.payroll;


import android.app.ActionBar;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    /* TODO: Create SQL table for storing data

     */

    static SQLiteDatabase myDatabase;

    Button setName;
    Button setPay;
    Button weekPay;

    private static DecimalFormat df = new DecimalFormat(".##");
    public static Employee employee = new Employee();
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    static String empName;
    static double payHourly;

    static EditText name;
    static EditText payRate;

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;

    // action bar
    private ActionBar actionBar;

    // Refresh menu item
    private MenuItem refreshMenuItem;

    public void setName(View view) {

        ArrayList<Employee> employees = new ArrayList<>();
        name = (EditText) findViewById(R.id.empName);

        empName = name.getText().toString();
        employee.setEmployeeName(empName);
        employees.add(employee);
        employee.setID(1);
        Log.i("Employee name: ", employee.getEmployeeName());
        Log.i("Employee id: ", String.valueOf(employee.getId()));

    }

    public void setEmpPayRate(View view) {

        payRate = (EditText) findViewById(R.id.payRate);

        payHourly = Double.parseDouble(payRate.getText().toString());
        employee.setPayRate(payHourly);
        Log.i("Employee pay rate: ", String.valueOf(employee.getPayRate()));

    }


    public void pay(View view) {

        Double totalPay;


        // Double hoursWorked = Double.parseDouble(hours.getText().toString());
        // employee.setHoursWorked(hoursWorked);


        employee.setWeekPay(employee.getHoursWorked(), Double.parseDouble(payRate.getText().toString()));
        Log.i("Week pay: ", String.valueOf(employee.getWeekPay()));
        Log.i("Hours worked: ", String.valueOf(employee.getHoursWorked()));
        Log.i("Pay rate: ", String.valueOf(employee.getPayRate()));

        totalPay = employee.getWeekPay();

        TextView payText = (TextView) findViewById(R.id.pay);
        payText.setText(new StringBuilder().append("$").append(String.valueOf(df.format(totalPay))).toString());

        Toast.makeText(getApplicationContext(), "$" + df.format(totalPay), Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getActionBar();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        /*
        TabTextColor sets the color for the title of the tabs, passing a ColorStateList here makes
        tab change colors in different situations such as selected, active, inactive etc

        TabIndicatorColor sets the color for the indiactor below the tabs
         */

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.indicator));

        /*
        Adding a onPageChangeListener to the viewPager
        1st we add the PageChangeListener and pass a TabLayoutPageChangeListener so that Tabs Selection
        changes when a viewpager page changes.
         */





    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "ONE");
        adapter.addFrag(new TwoFragment(), "TWO");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_settings:
                // settings action
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
