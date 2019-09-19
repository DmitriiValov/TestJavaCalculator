package ru.dvalov.calc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static Date parseDate(String dateStr) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStr);
    }
}
