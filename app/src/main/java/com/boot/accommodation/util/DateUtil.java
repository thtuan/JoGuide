package com.boot.accommodation.util;

import android.util.Log;

import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Util ho tro cho ngay
 *
 * @author tuanlt
 * @since: 11:44 AM 5/13/2016
 */
public class DateUtil {

    public static final String FORMAT_DATE = "dd/MM/yyyy";
    public static final String FORMAT_DATE_SQL = "yyyy-MM-dd";
    public static final String FORMAT_DATE_TIME_ZONE = "yyyy-MM-dd HH:mm:ss.SSSZZZ";
    public static final String FORMAT_DATE_WITHOUT_TIME_ZONE = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_FOR_FILE = "dd_MM_yyyy_HH_mm_ss";
    public static final String FORMAT_TIME = "HH:mm";
    public static final String FORMAT_HOUR = "HH";
    public static final String FORMAT_MINUTES = "mm";
    public static final String FORMAT_SECONDS = "ss";
    public static final String FORMAT_TIME_SECOND = "HH:mm:ss";
    public static final String FORMAT_DATE_TIME = FORMAT_TIME + " " + FORMAT_DATE ;
    public static final String FORMAT_DATE_TIME_FULL = FORMAT_DATE + " " + FORMAT_TIME_SECOND;
    public static final SimpleDateFormat defaultDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
    /** The Constant SECOND. */
    public static final long SECOND = 1000;
    /** The Constant MINUTE. */
    public static final long MINUTE = SECOND * 60;
    /** The Constant HOUR. */
    public static final long HOUR = MINUTE * 60;

    public static String now() {
        return formatDate(Calendar.getInstance().getTimeInMillis());
    }

    public static String formatNow(String format) {
        return formatDate(Calendar.getInstance().getTimeInMillis(), format);
    }

    public static String formatDate(long milliseconds) {
        return formatDateTime(new Date(milliseconds), FORMAT_DATE);
    }

    public static String formatDate(long milliseconds, String format) {
        return formatDateTime(new Date(milliseconds), format);
    }

    public static String formatTime(long milliseconds) {
        return formatDateTime(new Date(milliseconds), FORMAT_TIME);
    }

    public static String formatTime(long milliseconds, String format) {
        return formatDateTime(new Date(milliseconds), format);
    }

    public static String formatDateTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * Dinh dang thoi gian theo format
     * @param strDate
     * @param format
     * @return
     */
    public static String formatTime(String strDate, String format) {
        String t = "";
        try {
            Date date = new SimpleDateFormat(format).parse(strDate);
            t = formatDateTime(date, format);
        } catch (ParseException e) {
            Log.w("",TraceExceptionUtils.getReportFromThrowable(e));
        }
        return t;
    }

    /**
     * Compare hai ngay kieu string voi nhau
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(String date1, String date2) {
        int result = -2;
        try {
            date1 = date1.substring(0, 10);
            date2 = date2.substring(0, 10);

            Date date11 = defaultDateFormat.parse(date1);
            Date date22 = defaultDateFormat.parse(date2);

            if (date11.before(date22)) {
                result = -1;
            } else if (date11.after(date22)) {
                result = 1;
            } else {
                result = 0;
            }
        } catch (Exception e) {
            Log.w("", TraceExceptionUtils.getReportFromThrowable(e));
        }
        return result;
    }

    /**
     * Compare hai ngay dang Date voi nhau
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate( Date date1, Date date2 ) {

        if ((date1 != null) && (date2 == null)) {
            return -1;
        } else if ((date1 == null) && (date2 != null)) {
            return 1;
        } else if ((date1 == null) && (date2 == null)) {
            return 0;
        }
        long time1 = date1.getTime() / HOUR;
        long time2 = date2.getTime() / HOUR;
        if (time1 == time2) {
            return 0;
        } else if (time1 < time2) {
            return -1;
        } else {
            return 1;
        }
    }


    /**
     * Lay thoi gian giua hai ngay trong cung mot ngay
     * @param date1
     * @param date2
     */
    public static String subtractDatesToHourMinutes(String date1, String date2){
        String time = "";
        try {
            Date date11 = defaultDateFormat.parse(date1);
            Date date22 = defaultDateFormat.parse(date2);
            long diff = date11.getTime() - date22.getTime();
            long timeInSeconds = diff / 1000;
            long hours, minutes;
            hours = timeInSeconds / 3600;
            timeInSeconds = timeInSeconds - (hours * 3600);
            minutes = timeInSeconds / 60;
            time = Math.abs(hours) + StringUtil.getString(R.string.text_hour) + (minutes < 10 ? "0" +
                minutes : minutes);
        } catch (ParseException e) {
            Log.w("", TraceExceptionUtils.getReportFromThrowable(e));
        }
        return time;
    }

    /**
     *
     * Format String date with format
     * @param date1
     * @param format1
     * @param format2
     * @return
     */
    public static String convertDateWithFormat(String date1, String format1, String format2){
        String t = "";
        try {
            if(!StringUtil.isNullOrEmpty(date1)) {
                Date date = new SimpleDateFormat(format1).parse(date1);
                t = formatDateTime(date, format2);
            }
        } catch (ParseException e) {
            Log.w("",TraceExceptionUtils.getReportFromThrowable(e));
        }
        return t;
    }

    /**
     * Format date with seq
     * @param date date start
     * @param format format date
     * @param dateSeq date seq start from 1
     * @return
     */
    public static String convertDateWithSeq(String date, String format, int dateSeq){
        String t = "";

            try {
                if (!StringUtil.isNullOrEmpty(date)){
                    Date date1 = new SimpleDateFormat(format).parse(date);
                    date1.setTime(date1.getTime()+((dateSeq -1)*Constants.MILI_SECOND_OF_DAY));
                    t = formatDateTime(date1,FORMAT_DATE_TIME_ZONE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        return t;
    }

    /**
     * Num day between 2 date
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int daysBetween(Date d1, Date d2) {
        if ((d1 != null) && (d2 == null)) {
            return -1;
        } else if ((d1 == null) && (d2 != null)) {
            return 1;
        } else if ((d1 == null) && (d2 == null)) {
            return 0;
        }
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * Compare date with format
     * @param date1
     * @param date2
     * @param format
     * @return
     */
    public static int compareDateWithFormat(String date1, String date2, String format) {
        int result = -2;
        try {
            SimpleDateFormat defaultDateFormat = new SimpleDateFormat(format);
            Date date11 = defaultDateFormat.parse(date1);
            Date date22 = defaultDateFormat.parse(date2);

            if (date11.before(date22)) {
                result = -1;
            } else if (date11.after(date22)) {
                result = 1;
            } else {
                result = 0;
            }
        } catch (Exception e) {
            Log.w("", TraceExceptionUtils.getReportFromThrowable(e));
        }
        return result;
    }

}
