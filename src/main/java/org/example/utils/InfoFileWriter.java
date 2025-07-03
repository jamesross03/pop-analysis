package org.example.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.example.Config;

/**
 * Writer for info files, output for each run in the results directory.
 */
public class InfoFileWriter {
    /**
     * Writes info-file for a given-run to the output directory specified in 
     * the provided Config object.
     * 
     * @param config  Config object to use
     * @param startTime 
     * @param endTime
     * @param nRecords number of records processed
     */
    public static void write(Config config, LocalDateTime startTime, LocalDateTime endTime, int nRecords) {
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(config.getOutputDir() + "/info.txt"))) {
            writer.write("Time started = " + formatDateTime(startTime) +"\n");
            writer.write("Time finished = " + formatDateTime(endTime) +"\n");
            writer.write("Num records processed = " + nRecords +"\n");
            writer.write(config.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Formats a LocalDateTime instance into a String for output, with
     * nanoseconds.
     * 
     * @param datetime
     * @return formatted datetime String
     */
    private static String formatDateTime(LocalDateTime datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss");

        return new StringBuilder(datetime.format(formatter))
            .append("." + String.format("%03d", datetime.getNano() / 1_000_000))
            .toString();
    }
}
