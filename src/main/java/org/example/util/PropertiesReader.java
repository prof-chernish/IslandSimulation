package org.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private final String propertiesFileName;
    private final Properties properties = new Properties();

    public PropertiesReader(String propertiesFileName) {
        this.propertiesFileName = propertiesFileName;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(propertiesFileName)) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWeight() {
        return Integer.valueOf(properties.getProperty("weight"));
    }

    public int getSpeed() {
        return Integer.valueOf(properties.getProperty("speed"));
    }

    public int getMaxStarving() {
        return Integer.valueOf(properties.getProperty("max_starving"));
    }

    public int getMaxCount() {
        return Integer.valueOf(properties.getProperty("max_count"));
    }

    public int getEatingProbabilityFor(String animal) {
        return Integer.valueOf(properties.getProperty(animal));
    }

    public String getTitle() {
        return properties.getProperty("title");
    }
}
