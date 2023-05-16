package execution;

import input.DataProvider;
import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import keyword.ActionKeywords;
import model.Data;
import model.DataOfSignIn;
import model.SignInPage;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import report.ExtentReportManager;
import utils.DataReaderUtils;
import utils.LogUtils;
import utils.ScreenRecorderUtils;
import utils.XmlUtils;
import java.lang.reflect.Method;
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



//    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of login function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from Excel file")
    @Test
    public void testCase_ExcelFile_SignInWithoutEmail() {
        List<DataProvider> listHaveVar = DataReaderUtils.getTestStep(excelPath, "SignInPage");
        List<DataOfSignIn> dataOfSignIns = DataReaderUtils.getDataSignIn(excelPath, "DataOfSignIn", "TC_DN1");
        testCaseStep = DataReaderUtils.getFinalDataSignIn(listHaveVar, dataOfSignIns);
        ScreenRecorderUtils.startRecord("SignIn");
        // get data from list data to run script
        for (DataProvider step : testCaseStep) {
            execute_Actions(step);
        }
        ScreenRecorderUtils.stopRecord();
    }

//    @Ignore
    @Severity(SeverityLevel.NORMAL)
    @Description("Test suite of register function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from Excel file")
    @Test
    public void testCase_ExcelFile_SignInWithoutPassword() {
        List<DataProvider> listHaveVar = DataReaderUtils.getTestStep(excelPath, "SignInPage");
        List<DataOfSignIn> dataOfSignIns = DataReaderUtils.getDataSignIn(excelPath, "DataOfSignIn", "TC_DN2");
        testCaseStep = DataReaderUtils.getFinalDataSignIn(listHaveVar, dataOfSignIns);
        ScreenRecorderUtils.startRecord("SignIn");
        for (DataProvider step : testCaseStep) {
            execute_Actions(step);
        }
        ScreenRecorderUtils.stopRecord();
    }

    //    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of login function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from Excel file")
    @Test
    public void testCase_ExcelFile_SignInWithInvalidAccount() {
//        testCaseStep = ExcelReader.getTestCases(excelPath, "SignInPage", "SignInWithoutEmail");
        List<DataProvider> listHaveVar = DataReaderUtils.getTestStep(excelPath, "SignInPage");
        List<DataOfSignIn> dataOfSignIns = DataReaderUtils.getDataSignIn(excelPath, "DataOfSignIn", "TC_DN3");
        testCaseStep = DataReaderUtils.getFinalDataSignIn(listHaveVar, dataOfSignIns);
        ScreenRecorderUtils.startRecord("SignIn");
        // get data from list data to run script
        for (DataProvider step : testCaseStep) {
            execute_Actions(step);
        }
        ScreenRecorderUtils.stopRecord();
    }

    @Ignore
    @Severity(SeverityLevel.NORMAL)
    @Description("Test suite of login function written in XML file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from XML file")
    @Test
    public void TestSuite_SignIn_XMLFile() {
        Data xmlData = XmlUtils.xmlToData(xmlPath);
        List<SignInPage> signInPages = xmlData.getSignInPage();
        List<DataOfSignIn> dataOfSignIns = xmlData.getDataOfSignIn();
        testCaseStep = DataReaderUtils.getFinalDataSignIn(DataReaderUtils.convertSignInToDataProvider(signInPages), dataOfSignIns);
        ScreenRecorderUtils.startRecord("SignIn");
        // get data from list data to run script
        for (DataProvider step : testCaseStep) {
            execute_Actions(step);
        }
        ScreenRecorderUtils.stopRecord();
    }
//
//    @Ignore
//    @Severity(SeverityLevel.NORMAL)
//    @Description("Test suite of login function written in Json file")
//    @Epic("Website CareerLink")
//    @Feature("Sign in")
//    @Story("Read data test from Json file")
//    @Test
//    public void TestSuite_SignIn_JsonFile() throws Exception {
//        Data jsonData = JsonUtils.readData(jsonPath);
//        List<SignInPage> signInPages = jsonData.getSignInPage();
//        List<DataOfSignIn> dataOfSignIns = jsonData.getDataOfSignIn();
//        ScreenRecorderUtils.startRecord("SignIn");
//        List<Locator> locators = new ArrayList<>();
//        for (DataOfSignIn dataOfSignIn : dataOfSignIns) {
//            Locator lct = new Locator();
//            lct.setSignInTcId(dataOfSignIn.getTcId());
//            lct.setSignInEmail(dataOfSignIn.getEmail());
//            lct.setSignInPw(dataOfSignIn.getPassword());
//            lct.setSignInResult(dataOfSignIn.getResult());
//            locators.add(lct);
//        }
//        // get data from list data to run script
//
//        for (int i = 0; i < locators.size(); i++) { //sheet dataofsignin
//            for (SignInPage signInPage : signInPages) {
//                reuseSignIn(signInPage);
//                execute_Actions(testData, null, locators.get(i).getSignInEmail(), locators.get(i).getSignInPw(), null,
//                        locators.get(i).getSignInResult(), locators.get(i).getSignInTcId());
//            }
//        }
//        reportInConsole();
//        ScreenRecorderUtils.stopRecord();
//        considerTestCase();
//    }
//
//    @Ignore
//    @Severity(SeverityLevel.NORMAL)
//    @Description("Test suite of login function written in CSV file")
//    @Epic("Website CareerLink")
//    @Feature("Sign in")
//    @Story("Read data test from CSV file")
//    @Test
//    public void TestSuite_SignIn_CSVFile() throws Exception {
//        List<SignInPage> signInPages = CsvUtils.readSignInPageCSVfile(signInCSVpath);
//        List<DataOfSignIn> dataOfSignIns = CsvUtils.readDataOfSignInCSVfile(dataOfsignInCSVpath);
//        ScreenRecorderUtils.startRecord("SignIn");
//        List<Locator> locators = new ArrayList<>();
//        for (DataOfSignIn dataOfSignIn : dataOfSignIns) {
//            Locator lct = new Locator();
//            lct.setSignInTcId(dataOfSignIn.getTcId());
//            lct.setSignInEmail(dataOfSignIn.getEmail());
//            lct.setSignInPw(dataOfSignIn.getPassword());
//            lct.setSignInResult(dataOfSignIn.getResult());
//            locators.add(lct);
//        }
//        // get data from list data to run script
//        for (int i = 0; i < locators.size(); i++) { //sheet dataofsignin
//            for (SignInPage signInPage : signInPages) {
//                reuseSignIn(signInPage);
//                execute_Actions(testData, null, locators.get(i).getSignInEmail(), locators.get(i).getSignInPw(), null,
//                        locators.get(i).getSignInResult(), locators.get(i).getSignInTcId());
//            }
//        }
//        reportInConsole();
//        ScreenRecorderUtils.stopRecord();
//        considerTestCase();
//    }
//
//


    //    @Ignore
//    @Severity(SeverityLevel.BLOCKER)
//    @Description("Test suite of create CV function")
//    @Epic("Website CareerLink")
//    @Feature("Create CV")
//    //@Story("Read data test from Excel file")
//    @Test
//    public void TestSuite_CreateCV() throws Exception {
//        ExcelUtils.setExcelFile(excelPath, "CreateCV");
//        Sheet sheet = ExcelUtils.getSheet("CreateCV");
//        int rowCount = sheet.getLastRowNum();
//        ScreenRecorderUtils.startRecord("CreateCV");
//        // Bỏ hàng tiêu đề
//        for (int iRow = 1; iRow <= rowCount; iRow++) {
//            reuseCreateCV(iRow);
//            execute_Actions(testData, null, null, null, null, null, "CRCV_01");
//        }
//        reportInConsole();
//        ScreenRecorderUtils.stopRecord();
//        considerTestCase();
//    }
//
//    @Ignore
//    @Severity(SeverityLevel.NORMAL)
//    @Description("Test suite of search and view job details function")
//    @Epic("Website CareerLink")
//    @Feature("Search and View job detail")
//    //@Story("Read data test from Excel file")
//    @Test
//    public void TestSuite_SearchAndViewJobDetails() throws Exception {
//        ExcelUtils.setExcelFile(excelPath, "Search");
//        Sheet sheet = ExcelUtils.getSheet("Search");
//        int rowCount = sheet.getLastRowNum();
//
//        ScreenRecorderUtils.startRecord("Search");
//        // Bỏ hàng tiêu đề
//        for (int iRow = 1; iRow <= rowCount; iRow++) {
//            reuseSearch(iRow);
//            execute_Actions(testData, null, null, null, null, null, "SJO_01");
//        }
//        reportInConsole();
//        ScreenRecorderUtils.stopRecord();
//    }
//
    private void execute_Actions(DataProvider testScript) {
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
                    ActionKeywords.uploadImage(locatorType, locatorValue, testData);
                    break;
                case "clickButton":
                    ActionKeywords.clickButton(locatorType, locatorValue);
                    break;
                case "doubleClick":
                    ActionKeywords.doubleClick(locatorType, locatorValue);
                    break;
                case "clickElement":
                    ActionKeywords.clickElement(locatorType, locatorValue);
                    break;
                case "verifyResults":
                    if (ActionKeywords.verifyResults(testData)) {
                        LogUtils.info("Same result ---> Pass");
                    } else {
                        LogUtils.error("Different result ---> Fail");
                        Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
                    }
                    break;
                case "verifyText":
                    if (ActionKeywords.verifyText(locatorType, locatorValue, testData)) {
                        LogUtils.info("Same result ---> Pass");
                    } else {
                        LogUtils.error("Different result ---> Fail");
                    }
                    break;
                case "verifyTitle":
                    if (ActionKeywords.verifyTitle(testData)) {
                        LogUtils.info("Same result ---> Pass");
                    } else {
                        LogUtils.error("Different result ---> Fail");
                    }
                    break;
                case "verifyURL":
                    if (ActionKeywords.verifyURL(testData)) {
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
            //handle (show ra report) -> khong tim thay element
        }
    }

}
