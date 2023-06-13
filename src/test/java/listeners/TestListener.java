package listeners;

import annotations.FrwAnnotation;
import com.github.automatedowl.tools.AllureEnvironmentWriter;
import com.google.common.collect.ImmutableMap;
import constants.FrwConstants;
import enums.AuthorType;
import enums.Browser;
import enums.CategoryType;
import helpers.PropertiesHelpers;
import keyword.ActionKeywords;
import org.testng.*;
import report.AllureManager;
import report.ExtentReportManager;
import report.TelegramManager;
import utils.*;

import java.awt.*;
import java.io.IOException;

public class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {
    static int count_totalTCs;
    static int count_passedTCs;
    static int count_skippedTCs;
    static int count_failedTCs;

    private ScreenRecorderUtils screenRecorder;
    public TestListener() {
        try {
            screenRecorder = new ScreenRecorderUtils();
        } catch (IOException | AWTException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }

    @Override
    public void onStart(ITestContext iSuite) {
        System.out.println("========= INSTALLING CONFIGURATION DATA =========");
        PropertiesHelpers.loadAllFiles();
        AllureManager.setAllureEnvironmentInformation();
        ExtentReportManager.initReports();
        System.out.println("========= INSTALLED CONFIGURATION DATA =========");
        LogUtils.info("Starting Suite: " + iSuite.getName());
    }

    @Override
    public void onFinish(ITestContext iSuite) {
        LogUtils.info("End Suite: " + iSuite.getName());
        ActionKeywords.stopSoftAssertAll();

        //End Suite and execute Extents Report
        ExtentReportManager.flushReports();
        reportInConsole();

        //Send notification to Telegram
        TelegramManager.sendReportPath();

        //Send mail
        EmailSendUtils.sendEmail(count_totalTCs, count_passedTCs, count_failedTCs, count_skippedTCs);

        //Write information in Allure Report
        AllureEnvironmentWriter.allureEnvironmentWriter(
                ImmutableMap.<String, String>builder().
                        put("Test URL", FrwConstants.URL_HRM).
                        put("Target Execution", FrwConstants.TARGET).
                        put("Global Timeout", String.valueOf(FrwConstants.WAIT_DEFAULT)).
                        put("Page Load Timeout", String.valueOf(FrwConstants.WAIT_PAGE_LOADED)).
                        put("Headless Mode", FrwConstants.HEADLESS).
                        put("Local Browser", String.valueOf(Browser.CHROME)).
                        put("Remote URL", FrwConstants.REMOTE_URL).
                        put("Remote Port", FrwConstants.REMOTE_PORT).
                        put("TCs Total", String.valueOf(count_totalTCs)).
                        put("TCs Passed", String.valueOf(count_passedTCs)).
                        put("TCs Skipped", String.valueOf(count_skippedTCs)).
                        put("TCs Failed", String.valueOf(count_failedTCs)).
                        build());
    }

    public AuthorType[] getAuthorType(ITestResult iTestResult) {
        if (iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrwAnnotation.class) == null) {
            return null;
        }
        AuthorType authorType[] = iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrwAnnotation.class).author();
        return authorType;
    }

    public CategoryType[] getCategoryType(ITestResult iTestResult) {
        if (iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrwAnnotation.class) == null) {
            return null;
        }
        CategoryType categoryType[] = iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrwAnnotation.class).category();
        return categoryType;
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        LogUtils.info("Test case: " + getTestName(iTestResult) + " is starting...");
        count_totalTCs = count_totalTCs + 1;
        ExtentReportManager.createTest(iTestResult.getName());
        ExtentReportManager.addAuthors(getAuthorType(iTestResult));
        ExtentReportManager.addCategories(getCategoryType(iTestResult));
        ExtentReportManager.info(BrowserInfoUtils.getOSInfo());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        count_passedTCs = count_passedTCs + 1;
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        LogUtils.error("Test case: " + getTestDescription(iTestResult) + " is failed.");
        count_failedTCs = count_failedTCs + 1;

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        count_skippedTCs = count_skippedTCs + 1;
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        LogUtils.error("Test failed but it is in defined success ratio: " + getTestName(iTestResult));
        ExtentReportManager.logMessage("Test failed but it is in defined success ratio: " + getTestName(iTestResult));
    }
    private void reportInConsole() {
        java.util.Date date = new java.util.Date();
        System.out.println("==========================================================");
        System.out.println("-----------" + date + "--------------");
        System.out.println("Total number of Testcases run: " + (count_totalTCs));
        System.out.println("Total number of passed Testcases: " + count_passedTCs);
        System.out.println("Total number of failed Testcases: " + count_failedTCs);
        System.out.println("Total number of skiped Testcases: " + count_skippedTCs);
        System.out.println("==========================================================");
    }
}
