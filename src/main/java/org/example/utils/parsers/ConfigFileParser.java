package org.example.utils.parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.example.Constants;

public class ConfigFileParser {
    public static List<String> getAllLines(String configFilepath) throws FileNotFoundException, IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(configFilepath))) {

            String ln;
            while ((ln = reader.readLine()) != null) {
                if (ln.length() != 0 && !ln.startsWith(Constants.COMMENT_INDICATOR)) {
                    lines.add(ln);
                }
            }
        }

        return lines;
    }
}
