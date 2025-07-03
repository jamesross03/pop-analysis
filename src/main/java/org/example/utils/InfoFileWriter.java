package org.example.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.example.Config;

public class InfoFileWriter {
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

    private static String formatDateTime(LocalDateTime datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss");

        return new StringBuilder(datetime.format(formatter))
            .append("." + String.format("%03d", datetime.getNano() / 1_000_000))
            .toString();
    }
}
