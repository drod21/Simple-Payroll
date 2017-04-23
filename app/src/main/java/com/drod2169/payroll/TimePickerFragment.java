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

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    onTimeSelectedListener mCallBack;

    public interface onTimeSelectedListener {

        public void onTimeSelected(String timeSet);

        public void onHourSelected(int mHour);

        public void onMinuteSelected(int mMin);

        public void onAMPM(String mAmPm);

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

        int currentHour;

        if (hourOfDay > 11) {

            currentHour = hourOfDay - 12;

        } else {

            currentHour = hourOfDay;

        }
        //Do something with the user chosen time
        //Get reference of host activity (XML Layout File) TextView widget
        TextView tv = (TextView) getActivity().findViewById(R.id.in_time);
        TextView tv2 = (TextView) getActivity().findViewById(R.id.out_time);
        //Display the user changed time on TextView

        // Temp hack to get minutes showing correctly with leading zero

        if (minute < 10) {

            time = String.valueOf(currentHour) + ":" + "0" + String.valueOf(minute) + " " + AMPM;

        } else {

            time = String.valueOf(currentHour) + ":" + String.valueOf(minute) + " " + AMPM;

        }

        clockIn = tv.getText().toString();
        clockOut = tv2.getText().toString();

        if (TextUtils.isEmpty(clockIn)) {
            tv.setText(time);
        } else {
            tv2.setText(time);
        }


        mCallBack.onTimeSelected(time);
        mCallBack.onHourSelected(hourOfDay);
        mCallBack.onMinuteSelected(minute);
        mCallBack.onAMPM(AMPM);

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