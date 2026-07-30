package io.github.jcodeforge.core.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.Locale;

public final class DateConverter {

    //RFC_3339
    private static final String sPattern = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";

    public static String toString(Date date) {
        return date == null ? null : new SimpleDateFormat(sPattern, Locale.ROOT).format(date);
    }

    public static String toString(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            return toString(date);
        }

        return null;
    }

    public static Date toDate(String date, String pattern) {
        try {
            return date == null ? null : new SimpleDateFormat(pattern, Locale.ROOT).parse(date);
        } catch (Exception ignored) {}

        return null;
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return localDateTime != null ?
                Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

    public static Date toDate(String date) {
        return toDate(date, sPattern);
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime toLocalDateTime(String s) {
        try {
            Date date = toDate(s);
            if (date != null) {
                return toLocalDateTime(date);
            }
        } catch (Exception ignored) {}

        return null;
    }
}
