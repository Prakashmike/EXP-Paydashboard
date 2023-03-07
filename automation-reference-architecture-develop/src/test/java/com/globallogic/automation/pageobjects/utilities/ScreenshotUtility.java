package com.globallogic.automation.pageobjects.utilities;

import com.globallogic.automation.pageobjects.enums.Platform;
import com.globallogic.automation.pageobjects.objectFactory.DriverFactory;
import com.globallogic.automation.pageobjects.objectFactory.PageObjectFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import javax.imageio.*;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class ScreenshotUtility {

    /**
     * Logger instance for writing log file and also printing it in the console
     */
    private static final Logger log = LoggerHelper.getLogger(ScreenshotUtility.class);

    /**
     * Video resolution
     */
    private static final String videoResolution = "320x460";

    /**
     * To capture the screenshot
     *
     * @param folderName folder name where screenshot will be save
     * @param fileName   screenshot file name
     */
    public static void captureScreenshot(String folderName, String fileName) {
        log.debug(String.format("captureScreenshot ( folderName: %s, fileName: %s )", folderName, fileName));
        try {
            File screenshot = ((TakesScreenshot) DriverFactory.getInstance().getDeviceDriver()).
                    getScreenshotAs(OutputType.FILE);
            String imagePath = String.format("./ExecutionData/%s/Screenshots/%s.jpg",
                    folderName,fileName);
            copyWithStreams(screenshot, new File(imagePath));
        } catch (Exception e) {
            log.error(String.format("Error while creating screenshot %s /n %s", fileName, e.getMessage()));
        }
    }

    /**
     * For saving screenshot file
     *
     * @param aSourceFile Source file
     * @param aTargetFile Target file
     */
    private static void copyWithStreams(File aSourceFile, File aTargetFile) {
        ensureTargetDirectoryExists(aTargetFile.getParentFile());
        if (aTargetFile.exists()) {
            //As long as the screenshot test always starts with file deletion, this will always be
            //a valid (and useful) check
            log.error("Screenshot file name must be unique!");
            throw new RuntimeException("Screenshot file name must be unique!");
        }
        try {
            ImageWriter writer = null;
            ImageReader reader = null;
            FileImageInputStream inputStream = null;
            FileImageOutputStream outputStream = null;
            float imageQuality = 1.0f;
            try {
                writer = ImageIO.getImageWritersByFormatName("jpeg").next();
                ImageWriteParam parameters = writer.getDefaultWriteParam();
                parameters.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                parameters.setCompressionQuality(imageQuality);
                inputStream = new FileImageInputStream(aSourceFile);
                reader = ImageIO.getImageReaders(inputStream).next();
                reader.setInput(inputStream, false, false);
                BufferedImage source = reader.read(0);
                //Convert from CMYK to RGB. This fixes the green tint issue.
                BufferedImage rgbImage = new BufferedImage(source.getWidth(), source.getHeight(),
                        BufferedImage.TYPE_3BYTE_BGR);
                ColorConvertOp op = new ColorConvertOp(null);
                op.filter(source, rgbImage);
                outputStream = new FileImageOutputStream(aTargetFile);
                writer.setOutput(outputStream);
                writer.write(null, new IIOImage(rgbImage, null, null), parameters);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            } finally {
                if (writer != null) {
                    writer.dispose();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (reader != null) {
                    reader.dispose();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (FileNotFoundException ex) {
            log.error(String.format("File not found:%s", ex));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * To ensure that target directory is present
     *
     * @param aTargetDir Target
     */
    private static void ensureTargetDirectoryExists(File aTargetDir) {
        if (!aTargetDir.exists()) {
            aTargetDir.mkdirs();
        }
    }

    /**
     * Method to start screen recording.
     */
    public static void startScreenRecording() {
        log.debug("startScreenRecording");
        try {
            if (PageObjectFactory.getInstance().getPlatform() == Platform.Android) {
                AndroidDriver androidDriver = (AndroidDriver) DriverFactory.getInstance().getDeviceDriver();
                androidDriver.startRecordingScreen(
                        AndroidStartScreenRecordingOptions.
                                startScreenRecordingOptions().
                                withVideoSize(videoResolution)
                                .enableBugReport()
                );
            }
        } catch (MalformedURLException e) {
            log.error(String.format("Error while starting screen recording %s", e.getMessage()));
        }
    }

    /**
     * Method to stop screen recording
     *
     * @param folderName folder name where screen recording will be save
     * @param fileName   screen recording file name
     */
    public static void stopScreenRecording(String folderName, String fileName){
        log.debug(String.format("stopScreenRecording ( folderName: %s, fileName: %s )", folderName, fileName));
        try {
            if(PageObjectFactory.getInstance().getPlatform() == Platform.Android) {
                AndroidDriver androidDriver = (AndroidDriver) DriverFactory.getInstance().getDeviceDriver();
                String result = androidDriver.stopRecordingScreen();
                byte[] decodedBytes = Base64.getDecoder().decode(result.getBytes());
                String videoPath = String.format("./ExecutionData/%s/Videos/%s.mp4",
                        folderName,fileName);
                File videoFile = new File(videoPath);
                ensureTargetDirectoryExists(videoFile.getParentFile());
                FileOutputStream out = new FileOutputStream(videoPath);
                out.write(decodedBytes);
                out.close();
            }
        } catch (IOException e) {
            log.error(String.format("Error while stopping screen recording %s", e.getMessage()));
        }
    }

    public static void takescreenshot(AppiumDriver<MobileElement> driver, String ScreenshotName) throws IOException {

        //Capture Screenshot as byte array

        byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
        //save screenshot to file

        String timestamp = new SimpleDateFormat("yyyy-MM-dd-mm-ss").format(new Date());
        String filepath = "D:\\Automation\\automation-reference-architecture-develop\\automation-reference-architecture-develop\\ExecutionData\\4/" + ScreenshotName + "_" + timestamp + ".png";
        FileOutputStream fileOutputStream = new FileOutputStream(filepath);
        try {
            fileOutputStream.write(screenshot);
        } catch (IOException e) {
            fileOutputStream.flush();
            fileOutputStream.close();
            throw new RuntimeException(e);
        }


    }

}
