package com.drod2169.payroll;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import org.joda.time.LocalTime;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    onTimeSelectedListener mCallBack;

    interface onTimeSelectedListener {

        void onTimeSelected(String timeSet);

        void onHourSelected(int mHour);

        void onMinuteSelected(int mMin);

        void onClockInSelected(LocalTime clockInTest);

        void onClockOutSelected(LocalTime clockOutTest);

    }

    String time;
    String clockIn;
    String clockOut;

    // Required empty default constructor
    public TimePickerFragment() {


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String AMPM = "AM";

        if (hourOfDay > 11) {

            AMPM = "PM";
        }


        //Do something with the user chosen time
        //Get reference of host activity (XML Layout File) TextView widget
        TextView tv = (TextView) getActivity().findViewById(R.id.in_time);
        TextView tv2 = (TextView) getActivity().findViewById(R.id.out_time);
        //Display the user changed time on TextView


        //String hourString = hourOfDay > 11 ? String.valueOf(hourOfDay - 12) : "" + hourOfDay;
        String hourString = String.valueOf(hourOfDay);
        String minuteString = minute < 10 ? "0" + minute : "" + minute;

        time = hourString + ":" + minuteString;

        clockIn = tv.getText().toString();
        clockOut = tv2.getText().toString();
        LocalTime clockInJoda = null;
        LocalTime clockOutJoda = null;

        if (TextUtils.isEmpty(clockIn)) {
            clockInJoda = LocalTime.parse(time);
            tv.setText(String.valueOf(clockInJoda));
        } else {
            clockOutJoda = LocalTime.parse(time);
            tv2.setText(String.valueOf(clockOutJoda));
        }


        mCallBack.onTimeSelected(time);
        mCallBack.onClockInSelected(clockInJoda);
        mCallBack.onClockOutSelected(clockOutJoda);
        mCallBack.onHourSelected(hourOfDay);
        mCallBack.onMinuteSelected(minute);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallBack = (onTimeSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onTimeSelectedListener");
        }
    }
}