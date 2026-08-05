package io.github.jcodeforge.invoice4jbase.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class StringUtils {

    public static String listToCommaSeparatedString(List<String> list) {
        if (list == null || list.isEmpty())
            return "";
        else {
            StringBuilder sb = new StringBuilder("");
            for (int i=0; i < list.size(); i++) {
                sb.append(list.get(i));
                if (i < list.size()-1)
                    sb.append(", ");
            }

            return sb.toString();
        }
    }

    public static List<String> commaSeparatedStringToList(String string) {
        if (string == null || string.isEmpty())
            return new ArrayList<>(0);
         else
            return new ArrayList<>(Arrays.asList(string.split(", ")));
    }

    public static List<String> doubleToList(double d) {
        String doubleString = String.valueOf(d);
        int indexOfDecimal = doubleString.indexOf(".");

        List<String> stringList = new ArrayList<>(2);
        stringList.add(doubleString.substring(0, indexOfDecimal));
        stringList.add(doubleString.substring(indexOfDecimal + 1));

        return stringList;
    }

    public static String substringBefore(String string, String regex) {
        return string.split(regex)[0];
    }

    public static double stringToDouble(String s) {
        try {
            return Double.parseDouble(s.replace(",", "."));
        } catch (Exception e) {
            return 0;
        }
    }

    public static String doubleToString(double d) {
        try {
            return d > 0 ? new BigDecimal(String.valueOf(d)).toString() : "" ;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Count lines in string.
     * @param string it is assumed that line terminator is either \n or \r or \r\n, but not a mix of them.
     * @return number of lines in this string
     */
    public static int countLines(String string) {
        int lines = 1;
        int pos = 0;
        while ((pos = Math.max(string.indexOf("\n", pos), string.indexOf("\r", pos)) + 1) != 0) {
            lines++;
        }
        return lines;
    }

    public static String readAllBytes(Path path) {
        String result = "";
        try {
            result = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        } catch (IOException ignored) {
            // Do anything here?
        }

        return result;
    }

    /**
     *
     * @return either the passed in String, or if the String is null, an empty String
     */
    public static String defaultEmptyString(String s) {
        return s != null ? s : "";
    }
}
