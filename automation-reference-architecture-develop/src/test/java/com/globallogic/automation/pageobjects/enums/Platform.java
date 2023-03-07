package com.globallogic.automation.pageobjects.enums;

import com.globallogic.automation.pageobjects.utilities.LoggerHelper;
import org.apache.log4j.Logger;

/**
 * Enum for all supported platforms.
 */
public enum Platform {
    iPhone,
    Android;


    /**
     * Logger instance for writing log file and also printing it in the console
     */
    static Logger log = LoggerHelper.getLogger(Platform.class);

    /**
     * Gives platform based on provided strings
     *
     * @param platform provided platform
     * @return Platform instance
     * @throws IllegalArgumentException exception
     */
    public static Platform getPlatform(String platform) {

        switch (platform) {
            case "iPhone":
                return iPhone;
            case "Android":
                return Android;
            default:
                log.error("Platform not found");
                throw new IllegalArgumentException("Platform not found");
        }
    }
}
