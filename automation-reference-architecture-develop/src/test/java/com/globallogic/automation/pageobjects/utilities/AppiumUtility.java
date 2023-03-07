package com.globallogic.automation.pageobjects.utilities;

import com.globallogic.automation.pageobjects.STRINGID;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Class responsible for managing Appium
 */
public class AppiumUtility {

    /**
     * AppiumDriverLocalService instance to start appium server
     */
    private static AppiumDriverLocalService mAppiumDriverLocalService;

    /**
     * Logger instance for writing log file and also printing it in the console
     */
    private static Logger log = LoggerHelper.getLogger(AppiumUtility.class);

    /**
     * ApplicationProperties instance
     */
    private static final ApplicationProperties mAppProperties = ApplicationProperties.getInstance();

    /**
     * Start Appium server
     */
    public static void startAppium() {
        Field streamField = null;
        Field streamsField = null;
        log.info("Start Appium called...");
        //Build the Appium service
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress("127.0.0.1");
        builder.usingPort(4723);
        builder.withLogFile(new File("appiumLogs"));
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");

        //Start the server with the builder
        mAppiumDriverLocalService = AppiumDriverLocalService.buildService(builder);

        //This code skips the appium logging
        try {
            streamField = AppiumDriverLocalService.class.getDeclaredField("stream");
            streamField.setAccessible(true);
            streamsField = Class.forName("io.appium.java_client.service.local.ListOutputStream")
                    .getDeclaredField("streams");
            streamsField.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            log.error(e.getMessage());
        }
        try {
            ((ArrayList<OutputStream>) streamsField.get(streamField.get(mAppiumDriverLocalService))).clear(); // remove System.out logging
        } catch (IllegalAccessException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
        mAppiumDriverLocalService.start();
    }

    /**
     * Stop Appium server
     */
    public static void stopAppium() {
        log.info("Stop Appium called...");
        mAppiumDriverLocalService.stop();
    }
}
