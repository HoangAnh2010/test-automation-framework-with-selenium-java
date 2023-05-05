package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import constants.FrwConstants;
import enums.AuthorType;
import enums.CategoryType;
import org.apache.commons.io.FileUtils;
import utils.IconUtils;
import utils.ReportUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import static constants.FrwConstants.EXTENT_REPORT_FILE_PATH;

public class ExtentReportManager {

    private static ExtentReports extentReports;
    private static String link = "";

    public static void initReports() {
        if (Objects.isNull(extentReports)) {
            extentReports = new ExtentReports();
            link = EXTENT_REPORT_FILE_PATH;
            System.out.println("Link Extent Report: " + link);
//            ExtentPDFReporter pdf = new ExtentPDFReporter("reports/ExtentReports/PdfReport.pdf");
//            try {
//                pdf.loadJSONConfig(new File("src/test/resources/pdf-config.json"));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            extentReports.attachReporter(pdf);

            ExtentHtmlReporter report = new ExtentHtmlReporter(link);
            //ExtentSparkReporter report = new ExtentSparkReporter(link);
            report.config().setTheme(Theme.STANDARD);
            report.config().setDocumentTitle(FrwConstants.REPORT_TITLE);
            report.config().setReportName(FrwConstants.REPORT_TITLE);
            extentReports.attachReporter(report);

            extentReports.setSystemInfo("Framework Name", FrwConstants.REPORT_TITLE);
            extentReports.setSystemInfo("Author", FrwConstants.AUTHOR);
            System.out.println("Extent Reports is installed.");
        }
    }

    public static void flushReports() {
        if (Objects.nonNull(extentReports)) {
            extentReports.flush();
        }
        ExtentTestManager.unload();
        ReportUtils.openReports(link);
    }

    public static void createTest(String testCaseName) {
        ExtentTestManager.setExtentTest(extentReports.createTest(IconUtils.getBrowserIcon() + " : " + testCaseName));
    }

    /**
     * Adds the screenshot.
     *
     * @param imageDir the message
     */
    public static void addScreenShot(String imageDir) {
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(imageDir));
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            ExtentTestManager.getExtentTest().log(Status.INFO,"Screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(encodedString).build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized public static void addAuthors(AuthorType[] authors) {
        if (authors == null) {
            ExtentTestManager.getExtentTest().assignAuthor("NGUYEN HOANG ANH");
        } else {
            for (AuthorType author : authors) {
                ExtentTestManager.getExtentTest().assignAuthor(author.toString());
            }
        }
    }

    // public static void addCategories(String[] categories) {
    synchronized public static void addCategories(CategoryType[] categories) {
        if (categories == null) {
            ExtentTestManager.getExtentTest().assignCategory("REGRESSION");
        } else {
            // for (String category : categories) {
            for (CategoryType category : categories) {
                ExtentTestManager.getExtentTest().assignCategory(category.toString());
            }
        }
    }

//    synchronized public static void addDevices() {
//        ExtentTestManager.getExtentTest().(BrowserInfoUtils.getBrowserInfo());
//		ExtentReportManager.getExtentTest()
//				.assignDevice(BrowserIconUtils.getBrowserIcon() + " : " + BrowserInfoUtils.getBrowserInfo());
//    }

    public static void logMessage(String message) {
        ExtentTestManager.getExtentTest().log(Status.INFO, message);
    }

    public static void logMessage(Status status, String message) {
        ExtentTestManager.getExtentTest().log(status, message);
    }

    public static void logMessage(Status status, Object message) {
        ExtentTestManager.getExtentTest().log(status, (Throwable) message);
    }

    public static void pass(String message) {
        ExtentTestManager.getExtentTest().pass(message);
    }

    public static void pass(Markup message) {
        ExtentTestManager.getExtentTest().pass(message);
    }

    public static void fail(String message) {
        ExtentTestManager.getExtentTest().fail(message);
    }

    public static void fail(Object message) {
        ExtentTestManager.getExtentTest().fail((String) message);
    }

    public static void fail(Markup message) {
        ExtentTestManager.getExtentTest().fail(message);
    }

    public static void skip(String message) {
        ExtentTestManager.getExtentTest().skip(message);
    }

    public static void skip(Markup message) {
        ExtentTestManager.getExtentTest().skip(message);
    }

    public static void info(Markup message) {
        ExtentTestManager.getExtentTest().info(message);
    }

    public static void info(String message) {
        ExtentTestManager.getExtentTest().info(message);
    }

    public static void warning(String message) {
        ExtentTestManager.getExtentTest().log(Status.WARNING, message);
    }

}
