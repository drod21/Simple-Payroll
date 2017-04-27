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
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    /* TODO: Create SQL table for storing data

     */

    static SQLiteDatabase myDatabase;

    Button setName;
    Button setPay;
    Button weekPay;

    private static DecimalFormat df = new DecimalFormat(".##");
    public static Employee employee = new Employee();
    static List<Employee> employees = new ArrayList<>();
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    static String empName;
    static double payHourly;

    public static int size;

    static EditText name;
    static EditText payRate;

    // action bar
    private ActionBar actionBar;

    // Refresh menu item
    private MenuItem refreshMenuItem;
    DatabaseHandler db = new DatabaseHandler(this);

    public void setName(View view) {


        name = (EditText) findViewById(R.id.empName);

        empName = name.getText().toString();

        boolean found = false;

        for (Employee emp1 : employees) {

            if (Objects.equals(empName, emp1.getEmployeeName())) {
                employee = emp1;
                found = true;
            }
        }

        if (found) {
            setPayRateView();
        }

        if (!found) {
            Employee emp = new Employee();
            emp.setEmployeeName(empName);

            employees.add(emp);


            emp.setID(size);
        }
        for (Employee emps : employees) {
            Log.i("Employee: ", emps.getEmployeeName());
            Log.i("Employee ID ", String.valueOf(emps.getId()));
        }
        Log.i("Employee name: ", employees.get(size).getEmployeeName());
        Log.i("Employee id: ", String.valueOf(employee.getId()));


        Log.i("ID " + employees.get(size).getId() + " Employee: ", employees.get(size).getEmployeeName() + " Date: " + String.valueOf(employees.get(size).getDate())
                + " Clock In: " + String.valueOf(employees.get(size).getClockIn()) + " Clock Out: " + String.valueOf(employees.get(size).getClockOut()));

    }


    public void setEmpPayRate(View view) {

        payRate = (EditText) findViewById(R.id.payRate);

       /* int size;
        for (size = 0; size < employees.size(); size++) {
            if (employees.get(size).getEmployeeName() == )
        }
*/
        payHourly = Double.parseDouble(payRate.getText().toString());

        if (employees.get(size).getPayRate() == 0) {
            employees.get(size).setPayRate(payHourly);
        } else {
            employees.get(size).setPayRate(payHourly);
            payRate.setText(String.valueOf(employees.get(size).getPayRate()));
        }
        Log.i("Employee pay rate: ", String.valueOf(employees.get(size).getPayRate()));

    }

    public void setPayRateView() {
        payRate = (EditText) findViewById(R.id.payRate);
        payRate.setText(String.valueOf(employees.get(size).getPayRate()));
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

        employees = db.getAllEmployees();

        int i = 0;

        for (Employee emp1 : employees) {

            if (Objects.equals(empName, emp1.getEmployeeName())) {

                break;

            }
            i++;
        }

        if (employees.size() <= 1) {
            size = 0;
        } else {
            size = i;
        }

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
        adapter.addFrag(new OneFragment(), "Home");
        adapter.addFrag(new TwoFragment(), "Employees");
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

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}