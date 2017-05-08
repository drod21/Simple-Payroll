package com.drod2169.payroll;


import android.app.ActionBar;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    /* TODO: Create SQL table for storing data

     */

    EmployeeSingleton employeeSingleton = EmployeeSingleton.getInstance();
    EmployeeSingleton employeeSingleton1 = EmployeeSingleton.getInstance();
    //EmployeeBuilder employeeBuilder = new EmployeeBuilder();
    private static DecimalFormat df = new DecimalFormat(".##");

    static List<Employee> employees = new ArrayList<>();


    static String empName;
    static double payHourly;

    public static int size;

    EditText name;
    EditText payRate;

    // Refresh menu item
    //private MenuItem refreshMenuItem;
    DatabaseHandler db = new DatabaseHandler(this);

    public void setName(View view) {

        name = (EditText) findViewById(R.id.empName);

        empName = name.getText().toString();

        findEmployee();

    }

    public void findEmployee() {


        boolean found = false;
        int i;
        int count = 0;
        for (i = 0; i < employees.size(); i++) {
            if (Objects.equals(empName, employees.get(i).getEmployeeName())) {
                count = i;
                found = true;
                break;
            }
        }


        if (employees.size() <= 1) {
            size = 0;
        } else {
            size = count;
            Log.i("Size ", String.valueOf(i));
        }

        if (found) {
            setPayRateView();
            EmployeeSingleton.getInstance().setId(employees.get(size).getId());
            EmployeeSingleton.getInstance().setName(employees.get(size).getEmployeeName());
            EmployeeSingleton.getInstance().setPayRate(employees.get(size).getPayRate());
            EmployeeSingleton.getInstance().setDate(employees.get(size).getDate());
            EmployeeSingleton.getInstance().setClockIn(employees.get(size).getClockIn());
            EmployeeSingleton.getInstance().setClockOut(employees.get(size).getClockOut());
            EmployeeSingleton.getInstance().setWorkedHours(employees.get(size).getHoursWorked());
        }

        if (!found) {

            EmployeeSingleton.getInstance().setName(empName);

            EmployeeSingleton.getInstance().setId(size);

            TwoFragment.updateListView(EmployeeSingleton.getInstance().getName());

        }

        Log.i("Singleton ", EmployeeSingleton.getInstance().getName());

    }


    public void setEmpPayRate(View view) {

        payRate = (EditText) findViewById(R.id.payRate);

        payHourly = Double.parseDouble(payRate.getText().toString());


        //use employeeSingleton
        employeeSingleton.setPayRate(payHourly);
        Log.i("Pay rate ", String.valueOf(employeeSingleton.getPayRate()));

    }

    public void setPayRateView() {
        payRate = (EditText) findViewById(R.id.payRate);
        payRate.setText(String.valueOf(employees.get(size).getPayRate()));
    }


    public void pay(View view) {

        Double totalPay;

        employees.get(size).setWeekPay(employees.get(size).getTotalHoursWorked(), employees.get(size).getPayRate());
        Log.i("Week pay: ", String.valueOf(employees.get(size).getWeekPay()));
        Log.i("Hours worked: ", String.valueOf(employees.get(size).getTotalHoursWorked()));
        Log.i("Pay rate: ", String.valueOf(employees.get(size).getPayRate()));

        totalPay = employees.get(size).getWeekPay();

        TextView payText = (TextView) findViewById(R.id.pay);
        payText.setText(String.format("$%s", String.valueOf(df.format(totalPay))));

        Toast.makeText(getApplicationContext(), "$" + df.format(totalPay), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        employees = db.getAllEmployees();

        ActionBar actionBar = getActionBar();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
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
        adapter.addFrag(new OneFragment(), "Home");
        adapter.addFrag(new TwoFragment(), "Employees");
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
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

        void addFrag(Fragment fragment, String title) {
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}