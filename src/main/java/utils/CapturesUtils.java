package utils;

import constants.FrwConstants;
import helpers.Helpers;
import org.apache.commons.io.FileUtils;
import org.monte.media.Format;

import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CapturesUtils extends ScreenRecorder {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

    public CapturesUtils(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
    }

    public static void captureScreenshot(WebDriver driver, String screenName) {
        try {
            String path = Helpers.getCurrentDir() + FrwConstants.EXPORT_CAPTURE_PATH;
            File file = new File(path);
            if (!file.exists()) {
                LogUtils.info("No Folder: " + path);
                file.mkdir();
                LogUtils.info("Folder created: " + file);
            }

            LogUtils.info("Driver for Screenshot: " + driver);
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File(path + "/" + screenName + "_" + dateFormat.format(new Date()) + ".png"));
            LogUtils.info("Screenshot taken: " + screenName);
            LogUtils.info("Screenshot taken current URL: " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }
}
