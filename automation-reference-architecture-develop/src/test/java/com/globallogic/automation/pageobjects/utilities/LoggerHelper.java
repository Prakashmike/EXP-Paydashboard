package com.globallogic.automation.pageobjects.utilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Class to handle all logging calls.
 */
public class LoggerHelper {
    /**
     * To check whether logger initialised or not
     */
    private static boolean mInitialise = false;

    /**
     * Gets Logger instance
     *
     * @param cls Class object
     * @return Logger instance
     */
    public static Logger getLogger(Class cls) {
        if (mInitialise) {
            return Logger.getLogger(cls);
        }
        PropertyConfigurator.configure("./src/test/resources/log4j.properties");
        mInitialise = true;
        return Logger.getLogger(cls);
    }
}
