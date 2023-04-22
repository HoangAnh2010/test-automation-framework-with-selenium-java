package utils;

import config.Driver;
import constants.FrwConstants;
import helpers.Helpers;
import org.apache.commons.io.FileUtils;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.monte.media.AudioFormatKeys.EncodingKey;
import static org.monte.media.AudioFormatKeys.FrameRateKey;
import static org.monte.media.AudioFormatKeys.KeyFrameIntervalKey;
import static org.monte.media.AudioFormatKeys.MIME_AVI;
import static org.monte.media.AudioFormatKeys.MediaTypeKey;
import static org.monte.media.AudioFormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.*;

public class CapturesUtils extends ScreenRecorder{


    public static ScreenRecorder screenRecorder;
    public String name;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
    //Hàm xây dựng
    public CapturesUtils(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
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
            // Tạo tham chiếu của TakesScreenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            // Gọi hàm capture screenshot - getScreenshotAs
            File source = ts.getScreenshotAs(OutputType.FILE);
            // result.getName() lấy tên của test case xong gán cho tên File chụp màn hình
            FileUtils.copyFile(source, new File(path + "/" + screenName + "_" + dateFormat.format(new Date()) + ".png"));
            LogUtils.info("Screenshot taken: " + screenName);
            LogUtils.info("Screenshot taken current URL: " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }

    // Hàm Start record video
    public static void startRecord(String methodName) {
    	try {
    		//Tạo thư mục để lưu file video vào
            File file = new File("./VideoRecord/");
            if (!file.exists()) {
                file.mkdirs();
            }

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = screenSize.width;
            int height = screenSize.height;

            Rectangle captureSize = new Rectangle(0, 0, width, height);

            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
            try {
                screenRecorder = new CapturesUtils(gc, captureSize, new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI), new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60), new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)), null, file, methodName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }
            try {
                screenRecorder.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            LogUtils.info("Executing: Recording...");
		} catch (Exception e) {
			LogUtils.error("Cannot record");
		}
        
    }

    // Stop record video
    public static void stopRecord() {
        try {
        	LogUtils.info("Stop record");
            screenRecorder.stop();
            
        } catch (IOException e) {
        	LogUtils.error("Cannot stop record ");
            throw new RuntimeException(e);
        }
    }

    //Take a screenshot
    public static void takeScreenshot(ITestResult result) {
    	try {
            // Tạo tham chiếu của TakesScreenshot với driver hiện tại
            TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
            // Gọi hàm capture screenshot - getScreenshotAs
            File source = ts.getScreenshotAs(OutputType.FILE);
            //Kiểm tra folder tồn tại. Nêu không thì tạo mới folder
            File theDir = new File("./Screenshots/");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            // result.getName() lấy tên của test case xong gán cho tên File chụp màn hình luôn
            FileHandler.copy(source, new File("./Screenshots/" + result.getName() + ".png"));
            LogUtils.info("Executing: Screenshot taken: " + result.getName());
        }
        catch (Exception e)
        {
            LogUtils.error("Executing: Screenshot taken: " + result.getName() + "FAIL");
        }
    }

    //Take a screenshot
    public static void takeScreenshot(String caseName) {
    	try {
            // Tạo tham chiếu của TakesScreenshot với driver hiện tại
            TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
            // Gọi hàm capture screenshot - getScreenshotAs
            File source = ts.getScreenshotAs(OutputType.FILE);
            //Kiểm tra folder tồn tại. Nếu không thì tạo mới folder
            File theDir = new File("./Screenshots/");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            // result.getName() lấy tên của test case xong gán cho tên File chụp màn hình luôn
            FileHandler.copy(source, new File("./Screenshots/" + caseName + ".png"));
            LogUtils.info("Executing: Screenshot taken: " + caseName);
        }
        catch (Exception e)
        {
            LogUtils.error("Executing: Screenshot taken: " + caseName + "FAIL");
        }
    }

    public static File getScreenshot(String screenshotName) {
        Rectangle allScreenBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        String dateName = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss.SSS").format(new Date());
        BufferedImage image = null;
        try {
            image = new Robot().createScreenCapture(allScreenBounds);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        String path = Helpers.getCurrentDir() + FrwConstants.EXTENT_REPORT_FOLDER + File.separator + "images";
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
            LogUtils.info("Folder created: " + folder);
        }

        String filePath = path + File.separator + screenshotName + dateName + ".png";
        File file = new File(filePath);
        try {
            ImageIO.write(image, "PNG", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

}
