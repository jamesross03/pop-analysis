package org.example.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.example.Constants;

/**
 * Parser for configuration files.
 */
public class ConfigFileParser {
    /**
     * Gets all lines from the specified configuration file (ignoring comments
     * and/or blank lines).
     * 
     * @param configFilepath
     * @return all non-blank/non-comment lines
     * @throws FileNotFoundException
     * @throws IOException
     */
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
