package io.github.jcodeforge.invoice4jbase.utils;

import java.time.Instant;
import java.util.Calendar;
import java.util.GregorianCalendar;

public final class Invoice4jDateTimeUtils {

    public static GregorianCalendar toGregorianCalendar(Calendar calendar) {
        if (calendar == null) {
            return null;
        }

        if (calendar instanceof GregorianCalendar gregorian) {
            return gregorian;
        }

        GregorianCalendar result = new GregorianCalendar();
        result.setTimeInMillis(calendar.getTimeInMillis());

        return result;
    }

    public static String getCurrentTimestamp() {
        return Instant.now().toString();
    }
}
