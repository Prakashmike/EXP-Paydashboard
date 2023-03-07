package com.globallogic.automation.pageobjects.utilities;

import org.apache.log4j.Logger;
import java.io.*;
import java.util.Properties;

/**
 * Class responsible for loading properties from property file and retrieving value based on key.
 */
public class ApplicationProperties {

    /**
     * Instance of ApplicationProperties singleton class.
     */
    private static final ApplicationProperties ourInstance = new ApplicationProperties();

    /**
     * Logger instance for writing log file and also printing it in the console
     */
    private static final Logger log = LoggerHelper.getLogger(ApplicationProperties.class);

    /**
     * Properties instance
     */
    private final Properties mProperties = new Properties();

    /**
     * Application property path
     */
    private static final String PATH_APPLICATION_PROPERTIES = "./src/test/resources/application.properties";

    /**
     * Methodto get singleton class instance.
     *
     * @return instance of ApplicationProperties class.
     */
    public static ApplicationProperties getInstance() {
        return ourInstance;
    }

    /**
     * Private Constructor of ApplicationProperties class.
     */
    private ApplicationProperties() {
        loadProperties();
    }

    /**
     * Load properties from property file
     */
    private void loadProperties() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(PATH_APPLICATION_PROPERTIES);
            mProperties.load(inputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * Gets property value based on provided key
     *
     * @param key key value
     * @return property value based on provided key
     */
    public String getPropertyForKey(String key) {
        return mProperties.getProperty(key);
    }
}
