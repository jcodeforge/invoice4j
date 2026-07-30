package io.github.codeforgecore.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class DateTimeUtils {

    public static Calendar getCalendarByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }

    public static Date getDate(int year, int month, int day, int hour, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);

        return calendar.getTime();
    }

    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getCurrentMinutes() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static int getYearOfDate(Date date) {
        return getCalendarByDate(date).get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        return getCalendarByDate(date).get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        return getCalendarByDate(date).get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour(Date date) {
        return getCalendarByDate(date).get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinutes(Date date) {
        return getCalendarByDate(date).get(Calendar.MINUTE);
    }

    public static long calculateDiffInDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static long calculateDiffInMinutes(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static long calculateDiffInMillis(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.MILLISECONDS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static String formatDate(Date date) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatDate(LocalDateTime date) {
        try {
            return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatDateToTimeString(Date date) {
        if (date == null) return "";

        String minutes = getMinutes(date) > 9 ?
                String.valueOf(getMinutes(date)) : "0" + getMinutes(date) ;

        return getHour(date) + ":" + minutes;
    }

    public static String formatDateToTimeString(String s) {
        Date date = DateConverter.toDate(s);
        return date != null ? formatDateToTimeString(date) : "" ;
    }

    /**
     * Compares two Dates
     *
     * Returns:
     * the value 0 if the first Date is equal to the second;
     * a value less than 0 if the first date is before the second;
     * and a value greater than 0 if the Date is after the second Date argument.
     */
    public static int compareDates(String d1s, String d2s) {
        Date date1 = DateConverter.toDate(d1s);
        Date date2 = DateConverter.toDate(d2s);

        if (date1 == date2) {
            return 0;
        }
        if (date1 == null) {
            return -1;
        }
        if (date2 == null) {
            return 1;
        }

        return date1.compareTo(date2);
    }
}
