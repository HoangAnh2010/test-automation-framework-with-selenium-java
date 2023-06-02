package execution;

import enums.FailureHandling;
import input.DataProvider;
import io.qameta.allure.*;
import keyword.ActionKeywords;
import model.Data;
import model.DataOfSignIn;
import model.DataOfSignUp;
import model.SignInPage;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import report.ExtentReportManager;
import utils.*;

import java.util.ArrayList;
import java.util.List;

@Listeners(listeners.TestListener.class)
public class ExecutionEngine {
    public static String excelPath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\data.xlsx";
    public static String jsonPath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\data.json";
    public static String xmlPath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\data.xml";
    public static String signInCSVpath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\signIn.csv";
    public static String dataOfsignInCSVpath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\DataSignIn.csv";
    public static List<DataProvider> testCaseStep = new ArrayList<>();

    private static boolean isPass = false;

    private void considerTestCase() {
        if (!isPass) {
            Assert.fail("Actual result is the same as expected result");
        }
    }

    private void executeActions(DataProvider testScript) {
        try {
            String keyword = testScript.getKeyword();
            String scriptTitle = testScript.getScriptTitle();
            String testData = testScript.getTestData();
            String locatorValue = testScript.getLocatorValue();
            String locatorType = testScript.getLocatorType();
            switch (keyword) {
                case "openBrowser":
                    ExtentReportManager.info("Test case: " + scriptTitle);
                    ActionKeywords.openBrowser(testData);
                    break;
                case "navigate":
                    ActionKeywords.navigate(testData);
                    break;
                case "clear":
                    ActionKeywords.clear(locatorType, locatorValue);
                    break;
                case "move":
                    ActionKeywords.moveElement(locatorValue);
                    break;
                case "switchTo":
                    ActionKeywords.switchTo(testData);
                    break;
                case "setText":
                    ActionKeywords.setText(locatorType, locatorValue, testData);
                    break;
                case "uploadImage":
                    ActionKeywords.uploadImage(testData);
                    break;
                case "clickButton":
                    ActionKeywords.clickButton(locatorType, locatorValue);
                    break;
                case "clickElement":
                    ActionKeywords.clickElement(locatorType, locatorValue);
                    break;
                case "doubleClick":
                    ActionKeywords.doubleClick(locatorType, locatorValue);
                    break;
                case "verifyResults":
                    isPass = ActionKeywords.verifyResults(testData, FailureHandling.CONTINUE_ON_FAILURE);
                    if (isPass) {
                        LogUtils.info("Same result ---> Pass");
                    } else {
                        LogUtils.error("Different result ---> Fail");
                        Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
                    }
                    break;
                case "verifyText":
                    isPass = ActionKeywords.verifyText(locatorType, locatorValue, testData, FailureHandling.CONTINUE_ON_FAILURE);
                    if (isPass) {
                        LogUtils.info("Same result ---> Pass");
                    } else {
                        LogUtils.error("Different result ---> Fail");
                    }
                    break;
                case "verifyTitle":
                    isPass = ActionKeywords.verifyTitle(testData, FailureHandling.CONTINUE_ON_FAILURE);
                    if (isPass) {
                        LogUtils.info("Same result ---> Pass");
                    } else {
                        LogUtils.error("Different result ---> Fail");
                    }
                    break;
                case "verifyURL":
                    isPass = ActionKeywords.verifyURL(testData);
                    if (isPass) {
                        LogUtils.info("Same result ---> Pass");
                    } else {
                        LogUtils.error("Different result ---> Fail");
                    }
                    break;
                case "displayed":
                    ActionKeywords.displayed(locatorType, locatorValue);
                    break;
                case "selectOptionByValue":
                    ActionKeywords.selectOptionByValue(locatorType, locatorValue, testData);
                    break;
                case "selectOptionByText":
                    ActionKeywords.selectOptionByText(locatorType, locatorValue, testData);
                    break;
                case "scrollDown":
                    ActionKeywords.scrollAndClick(locatorType, locatorValue);
                    break;
                case "closeBrowser":
                    ActionKeywords.closeBrowser();
                    break;
                default:
                    LogUtils.error("Keyword Not Found " + keyword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of login function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from Excel file")
    @Test(priority = 0)
    public void testCase_ExcelFile_SignInWithoutEmail() {
        List<DataProvider> listHaveVar = DataReaderUtils.getTestStep(excelPath, "SignInPage");
        List<DataOfSignIn> dataOfSignIns = DataReaderUtils.getDataSignIn(excelPath, "DataOfSignIn", "SIN_TD_02");
        testCaseStep = DataReaderUtils.getFinalDataSignIn(listHaveVar, dataOfSignIns);
        ScreenRecorderUtils.startRecord("ExcelFile_SignInWithoutEmail");
        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of register function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from Excel file")
    @Test(priority = 1)
    public void testCase_ExcelFile_SignInWithWrongEmailFormat_HaveSpace() {
        List<DataProvider> listHaveVar = DataReaderUtils.getTestStep(excelPath, "SignInPage");
        List<DataOfSignIn> dataOfSignIns = DataReaderUtils.getDataSignIn(excelPath, "DataOfSignIn", "SIN_TD_04");
        testCaseStep = DataReaderUtils.getFinalDataSignIn(listHaveVar, dataOfSignIns);
        ScreenRecorderUtils.startRecord("ExcelFile_SignInWithWrongEmailFormat_HaveSpace");
        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();

    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of register function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from Excel file")
    @Test(priority = 2)
    public void testCase_ExcelFile_SignInWithWrongEmailFormat_MissingSymbol() {
        List<DataProvider> listHaveVar = DataReaderUtils.getTestStep(excelPath, "SignInPage");
        List<DataOfSignIn> dataOfSignIns = DataReaderUtils.getDataSignIn(excelPath, "DataOfSignIn", "SIN_TD_06");
        testCaseStep = DataReaderUtils.getFinalDataSignIn(listHaveVar, dataOfSignIns);
        ScreenRecorderUtils.startRecord("ExcelFile_SignInWithWrongEmailFormat_MissingSymbol");
        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of login function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from Excel file")
    @Test(priority = 3)
    public void testCase_ExcelFile_SignInWithInvalidAccount() {
        List<DataProvider> listHaveVar = DataReaderUtils.getTestStep(excelPath, "SignInPage");
        List<DataOfSignIn> dataOfSignIns = DataReaderUtils.getDataSignIn(excelPath, "DataOfSignIn", "SIN_TD_07");
        testCaseStep = DataReaderUtils.getFinalDataSignIn(listHaveVar, dataOfSignIns);
        ScreenRecorderUtils.startRecord("ExcelFile_SignInWithInvalidAccount");
        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of login function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from Excel file")
    @Test(priority = 4)
    public void testCase_ExcelFile_SignInWithoutPassword() {
        List<DataProvider> listHaveVar = DataReaderUtils.getTestStep(excelPath, "SignInPage");
        List<DataOfSignIn> dataOfSignIns = DataReaderUtils.getDataSignIn(excelPath, "DataOfSignIn", "SIN_TD_11");
        testCaseStep = DataReaderUtils.getFinalDataSignIn(listHaveVar, dataOfSignIns);
        ScreenRecorderUtils.startRecord("ExcelFile_SignInWithoutPassword");
        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of login function written in XML file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from XML file")
    @Test(priority = 5)
    public void testCase_XMLFile_SignInWithoutEmail() {
        Data xmlData = XmlUtils.xmlToData(xmlPath);
        List<SignInPage> signInPages = xmlData.getSignInPage();
        List<DataOfSignIn> dataOfSignIns = xmlData.getDataOfSignIn();
        testCaseStep = DataReaderUtils.getFinalDataSignIn(DataReaderUtils.convertSignInToDataProvider(signInPages), dataOfSignIns);
        ScreenRecorderUtils.startRecord("XMLFile_SignInWithoutEmail");
        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of login function written in JSON file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from JSON file")
    @Test(priority = 6)
    public void testCase_JSONFile_SignInWithoutEmail() {
        Data jsonData = JsonUtils.readData(jsonPath);
        List<SignInPage> signInPages = jsonData.getSignInPage();
        List<DataOfSignIn> dataOfSignIns = jsonData.getDataOfSignIn();
        testCaseStep = DataReaderUtils.getFinalDataSignIn(DataReaderUtils.convertSignInToDataProvider(signInPages), dataOfSignIns);
        ScreenRecorderUtils.startRecord("JSONFile_SignInWithoutEmail");

        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of login function written in CSV file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from CSV file")
    @Test(priority = 7)
    public void testCase_CSVFile_SignInWithoutEmail() {
        List<SignInPage> signInPages = CsvUtils.readSignInPageCSVfile(signInCSVpath);
        List<DataOfSignIn> dataOfSignIns = CsvUtils.readDataOfSignInCSVfile(dataOfsignInCSVpath);
        testCaseStep = DataReaderUtils.getFinalDataSignIn(DataReaderUtils.convertSignInToDataProvider(signInPages), dataOfSignIns);
        ScreenRecorderUtils.startRecord("CSVFile_SignInWithoutEmail");

        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

    //@Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of register function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign up")
    @Story("Read data test from Excel file")
    @Test(priority = 8)
    public void testCase_ExcelFile_SignUpWithoutName() {
        List<DataProvider> listHaveVar = DataReaderUtils.getTestStep(excelPath, "SignUpPage");
        List<DataOfSignUp> dataOfSignUps = DataReaderUtils.getDataSignUp(excelPath, "DataOfSignUp", "SUP_TD_02");
        testCaseStep = DataReaderUtils.getFinalDataSignUp(listHaveVar, dataOfSignUps);
        ScreenRecorderUtils.startRecord("ExcelFile_SignUpWithoutName");
        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of register function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign up")
    @Story("Read data test from Excel file")
    @Test(priority = 8)
    public void testCase_ExcelFile_SignUpWithoutEmail() {
        List<DataProvider> listHaveVar = DataReaderUtils.getTestStep(excelPath, "SignUpPage");
        List<DataOfSignUp> dataOfSignUps = DataReaderUtils.getDataSignUp(excelPath, "DataOfSignUp", "SUP_TD_08");
        testCaseStep = DataReaderUtils.getFinalDataSignUp(listHaveVar, dataOfSignUps);
        ScreenRecorderUtils.startRecord("ExcelFile_SignUpWithoutEmail");
        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of register function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign up")
    @Story("Read data test from Excel file")
    @Test(priority = 9)
    public void testCase_ExcelFile_SignUpWithWrongEmailFormat_HaveSpace() {
        List<DataProvider> listHaveVar = DataReaderUtils.getTestStep(excelPath, "SignUpPage");
        List<DataOfSignUp> dataOfSignUps = DataReaderUtils.getDataSignUp(excelPath, "DataOfSignUp", "SUP_TD_10");
        testCaseStep = DataReaderUtils.getFinalDataSignUp(listHaveVar, dataOfSignUps);
        ScreenRecorderUtils.startRecord("ExcelFile_SignUpWithWrongEmailFormat_HaveSpace");
        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of register function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign up")
    @Story("Read data test from Excel file")
    @Test(priority = 10)
    public void testCase_ExcelFile_SignUpWithWrongEmailFormat_MissingSymbol() {
        List<DataProvider> listHaveVar = DataReaderUtils.getTestStep(excelPath, "SignUpPage");
        List<DataOfSignUp> dataOfSignUps = DataReaderUtils.getDataSignUp(excelPath, "DataOfSignUp", "SUP_TD_14");
        testCaseStep = DataReaderUtils.getFinalDataSignUp(listHaveVar, dataOfSignUps);
        ScreenRecorderUtils.startRecord("ExcelFile_SignUpWithWrongEmailFormat_MissingSymbol");
        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of register function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign up")
    @Story("Read data test from Excel file")
    @Test(priority = 11)
    public void testCase_ExcelFile_SignUpWithoutPassword() {
        List<DataProvider> listHaveVar = DataReaderUtils.getTestStep(excelPath, "SignUpPage");
        List<DataOfSignUp> dataOfSignUps = DataReaderUtils.getDataSignUp(excelPath, "DataOfSignUp", "SUP_TD_18");
        testCaseStep = DataReaderUtils.getFinalDataSignUp(listHaveVar, dataOfSignUps);
        ScreenRecorderUtils.startRecord("ExcelFile_SignUpWithoutPassword");
        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of register function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign up")
    @Story("Read data test from Excel file")
    @Test(priority = 12)
    public void testCase_ExcelFile_SignUpWithoutPasswordConfirm() {
        List<DataProvider> listHaveVar = DataReaderUtils.getTestStep(excelPath, "SignUpPage");
        List<DataOfSignUp> dataOfSignUps = DataReaderUtils.getDataSignUp(excelPath, "DataOfSignUp", "SUP_TD_19");
        testCaseStep = DataReaderUtils.getFinalDataSignUp(listHaveVar, dataOfSignUps);
        ScreenRecorderUtils.startRecord("ExcelFile_SignUpWithoutPasswordConfirm");
        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of register function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign up")
    @Story("Read data test from Excel file")
    @Test(priority = 13)
    public void testCase_ExcelFile_SignUpWithPwDiffFromPwCf() {
        List<DataProvider> listHaveVar = DataReaderUtils.getTestStep(excelPath, "SignUpPage");
        List<DataOfSignUp> dataOfSignUps = DataReaderUtils.getDataSignUp(excelPath, "DataOfSignUp", "SUP_TD_20");
        testCaseStep = DataReaderUtils.getFinalDataSignUp(listHaveVar, dataOfSignUps);
        ScreenRecorderUtils.startRecord("ExcelFile_SignUpWithPwDiffFromPwCf");
        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

    @Ignore
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test suite of Create CV function")
    @Epic("Website CareerLink")
    @Feature("Create CV")
    @Story("Read data test from Excel file")
    @Test(priority = 14)
    public void testCaseCreateCV() {
        testCaseStep = DataReaderUtils.getTestStep(excelPath, "CreateCV");
        ScreenRecorderUtils.startRecord("CreateCV");
        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

    @Ignore
    @Severity(SeverityLevel.NORMAL)
    @Description("Test suite of search and view job details function")
    @Epic("Website CareerLink")
    @Feature("Search and View job details")
    @Story("Read data test from Excel file")
    @Test(priority = 15)
    public void testCaseSearchAndViewJobDetails() {
        testCaseStep = DataReaderUtils.getTestStep(excelPath, "Search");
        ScreenRecorderUtils.startRecord("SearchAndViewJobDetRails");
        for (DataProvider step : testCaseStep) {
            executeActions(step);
        }
        considerTestCase();
        ScreenRecorderUtils.stopRecord();
    }

}
