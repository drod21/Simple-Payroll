package com.drod2169.payroll;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    onDateSelectedListener mCallBack;
    String stringOfDate;


    // Required empty constructor
    public DatePickerFragment() {

    }

    public interface onDateSelectedListener {

        void onDateSelected(String dateSet);

        void onLocalDateSelected(LocalDate date);

    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        //Create a new DatePickerDialog instance and return it
        /*
            DatePickerDialog Public Constructors - Here we uses first one
            public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
            public DatePickerDialog (Context context, int theme, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth)
         */
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Do something with the date chosen by the user
        TextView tv = (TextView) getActivity().findViewById(R.id.in_date);

        Context context = getActivity();
       /* SharedPreferences sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("day", String.valueOf(day));
        editor.putString("month", String.valueOf(month));
        editor.putString("year", String.valueOf(year));
        editor.commit();*/


        stringOfDate = (month + 1) + "/" + day + "/" + year;
        tv.setText(stringOfDate);
        LocalDate dateSelected = new LocalDate(year, (month + 1), day);


        mCallBack.onDateSelected(stringOfDate);
        mCallBack.onLocalDateSelected(dateSelected);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        try {
            mCallBack = (onDateSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onDateSelectedListener");
        }
    }

}