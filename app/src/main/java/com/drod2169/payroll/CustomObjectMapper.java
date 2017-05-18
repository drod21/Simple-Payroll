package com.drod2169.payroll;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by derekrodriguez on 5/17/17.
 */

public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        super();
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault()));
    }
}