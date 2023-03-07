package com.globallogic.automation.pageobjects.enums;

import com.globallogic.automation.pageobjects.utilities.LoggerHelper;
import org.apache.log4j.Logger;

/**
 * Enum for all supported device type.
 */
public enum DeviceType {
    Real,
    Simulator;

    /**
     * Logger instance for writing log file and also printing it in the console
     */
    static Logger log = LoggerHelper.getLogger(DeviceType.class);

    /**
     * Gives device type based on provided strings
     *
     * @param deviceType provided device type
     * @return DeviceType instance
     * @throws IllegalArgumentException exception
     */
    public static DeviceType getDeviceType(String deviceType) {

        switch (deviceType) {
            case "Real":
                return Real;
            case "Simulator":
                return Simulator;
            default:
                log.error("Device type not found");
                throw new IllegalArgumentException("Device type not found");
        }
    }
}
