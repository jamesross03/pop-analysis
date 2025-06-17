package org.example.utils;

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
}