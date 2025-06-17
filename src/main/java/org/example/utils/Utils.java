package org.example.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Defines common helper functions.
 */
public class Utils {
    /**
     * Capitalises a String
     * 
     * @param s
     * @return s capitalised
     */
    public static String capitalise(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static String getDateTimeString() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss");
        return now.format(formatter) + "-" + String.format("%03d", now.getNano() / 1_000_000);
    }
}