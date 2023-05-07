package execution;

import io.qameta.allure.*;
import keyword.ActionKeywords;
import model.Data;
import model.DataOfSignIn;
import model.Locator;
import model.SignInPage;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import report.ExtentReportManager;
import utils.*;

import java.util.ArrayList;
import java.util.List;

@Listeners(listeners.TestListener.class)
public class ExecutionEngine {
    private static String scriptID;
    private static String description;
    private static String sActionKeyword;
    private static String locatorType;
    private static String locatorValue;
    private static String testData;

    ArrayList<String> arrTCIDSignUp = new ArrayList<String>();
    ArrayList<String> arrNameSignUp = new ArrayList<String>();
    ArrayList<String> arrEmailSignUp = new ArrayList<String>();
    ArrayList<String> arrPasswordSignUp = new ArrayList<String>();
    ArrayList<String> arrPasswordCfSignUp = new ArrayList<String>();
    ArrayList<String> arrResultSignUp = new ArrayList<String>();

    public static String excelPath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\data.xlsx";
    public static String jsonPath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\data.json";
    public static String xmlPath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\data.xml";
    public static String signInCSVpath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\signIn.csv";
    public static String dataOfsignInCSVpath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\DataSignIn.csv";

    int totalCasePass = 0;
    int totalCaseFail = 0;
    int totalCaseSkip = 0;
    float casePass = 0;
    float caseFail = 0;
    float caseSkip = 0;
    float STANDARD_PERCENT = (float) 0.75;

    @Ignore
    @Severity(SeverityLevel.NORMAL)
    @Description("Test suite of login function written in XML file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from XML file")
    @Test
    public void TestSuite_SignIn_XMLFile() throws Exception {
        Data xmlData = XmlUtils.xmlToData(xmlPath);
        List<SignInPage> signInPages = xmlData.getSignInPage();
        List<DataOfSignIn> dataOfSignIns = xmlData.getDataOfSignIn();
        ScreenRecorderUtils.startRecord("SignIn");
        List<Locator> locators = new ArrayList<>();
        for (DataOfSignIn dataOfSignIn : dataOfSignIns) {
            Locator lct = new Locator();
            lct.setSignInTcId(dataOfSignIn.getTcId());
            lct.setSignInEmail(dataOfSignIn.getEmail());
            lct.setSignInPw(dataOfSignIn.getPassword());
            lct.setSignInResult(dataOfSignIn.getResult());
            locators.add(lct);
        }
        // get data from list data to run script
        for (int i = 0; i < locators.size(); i++) { //sheet dataofsignin
            for (SignInPage signInPage : signInPages) { //signin
                reuseSignIn(signInPage);
                execute_Actions(testData, null, locators.get(i).getSignInEmail(), locators.get(i).getSignInPw(), null,
                        locators.get(i).getSignInResult(), locators.get(i).getSignInTcId());
            }
        }
        reportInConsole();
        ScreenRecorderUtils.stopRecord();
        considerTestCase();
    }

    @Ignore
    @Severity(SeverityLevel.NORMAL)
    @Description("Test suite of login function written in Json file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from Json file")
    @Test
    public void TestSuite_SignIn_JsonFile() throws Exception {
        Data jsonData = JsonUtils.readData(jsonPath);
        List<SignInPage> signInPages = jsonData.getSignInPage();
        List<DataOfSignIn> dataOfSignIns = jsonData.getDataOfSignIn();
        ScreenRecorderUtils.startRecord("SignIn");
        List<Locator> locators = new ArrayList<>();
        for (DataOfSignIn dataOfSignIn : dataOfSignIns) {
            Locator lct = new Locator();
            lct.setSignInTcId(dataOfSignIn.getTcId());
            lct.setSignInEmail(dataOfSignIn.getEmail());
            lct.setSignInPw(dataOfSignIn.getPassword());
            lct.setSignInResult(dataOfSignIn.getResult());
            locators.add(lct);
        }
        // get data from list data to run script

        for (int i = 0; i < locators.size(); i++) { //sheet dataofsignin
            for (SignInPage signInPage : signInPages) {
                reuseSignIn(signInPage);
                execute_Actions(testData, null, locators.get(i).getSignInEmail(), locators.get(i).getSignInPw(), null,
                        locators.get(i).getSignInResult(), locators.get(i).getSignInTcId());
            }
        }
        reportInConsole();
        ScreenRecorderUtils.stopRecord();
        considerTestCase();
    }

    @Ignore
    @Severity(SeverityLevel.NORMAL)
    @Description("Test suite of login function written in CSV file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from CSV file")
    @Test
    public void TestSuite_SignIn_CSVFile() throws Exception {
        List<SignInPage> signInPages = CsvUtils.readSignInPageCSVfile(signInCSVpath);
        List<DataOfSignIn> dataOfSignIns = CsvUtils.readDataOfSignInCSVfile(dataOfsignInCSVpath);
        ScreenRecorderUtils.startRecord("SignIn");
        List<Locator> locators = new ArrayList<>();
        for (DataOfSignIn dataOfSignIn : dataOfSignIns) {
            Locator lct = new Locator();
            lct.setSignInTcId(dataOfSignIn.getTcId());
            lct.setSignInEmail(dataOfSignIn.getEmail());
            lct.setSignInPw(dataOfSignIn.getPassword());
            lct.setSignInResult(dataOfSignIn.getResult());
            locators.add(lct);
        }
        // get data from list data to run script
        for (int i = 0; i < locators.size(); i++) { //sheet dataofsignin
            for (SignInPage signInPage : signInPages) {
                reuseSignIn(signInPage);
                execute_Actions(testData, null, locators.get(i).getSignInEmail(), locators.get(i).getSignInPw(), null,
                        locators.get(i).getSignInResult(), locators.get(i).getSignInTcId());
            }
        }
        reportInConsole();
        ScreenRecorderUtils.stopRecord();
        considerTestCase();
    }

    //@Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test suite of login function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign in")
    @Story("Read data test from Excel file")
    @Test
    public void TestSuite_SignIn_ExcelFile() throws Exception {
        ExcelUtils.setExcelFile(excelPath, "SignInPage");
        Sheet sheet = ExcelUtils.getSheet("SignInPage");
        int rowCount = sheet.getLastRowNum();
        int row = 1;
        // get data in sheet and add on list data
        ExcelUtils.setExcelFile(excelPath, "DataOfSignIn");
        Sheet data = ExcelUtils.getSheet("DataOfSignIn");
        int rowCountTest = data.getLastRowNum();
        List<Locator> locators = new ArrayList<>();
        while (true) {
            if (row > rowCountTest)
                break;
            else {
                Locator lct = new Locator();
                lct.setSignInTcId(ExcelUtils.getCellData("DataOfSignIn", row, 1) + "");
                lct.setSignInEmail(ExcelUtils.getCellData("DataOfSignIn", row, 3) + "");
                lct.setSignInPw(ExcelUtils.getCellData("DataOfSignIn", row, 4) + "");
                lct.setSignInResult(ExcelUtils.getCellData("DataOfSignIn", row, 5) + "");
                locators.add(lct);
            }
            row = row + 1;
        }
        ScreenRecorderUtils.startRecord("SignIn");
        // get data from list data to run script
        for (int i = 0; i < locators.size(); i++) { //sheet dataofsignin
            for (int iRow = 1; iRow <= rowCount; iRow++) {
                reuseSignIn(iRow);
                execute_Actions(testData, null, locators.get(i).getSignInEmail(), locators.get(i).getSignInPw(), null,
                        locators.get(i).getSignInResult(), locators.get(i).getSignInTcId());
            }
        }
        reportInConsole();
        ScreenRecorderUtils.stopRecord();
        considerTestCase();
    }

    @Ignore
    @Severity(SeverityLevel.NORMAL)
    @Description("Test suite of register function written in Excel file")
    @Epic("Website CareerLink")
    @Feature("Sign up")
    @Story("Read data test from Excel file")
    @Test
    public void TestSuite_SignUp_ExcelFile() throws Exception {
        ExcelUtils.setExcelFile(excelPath, "SignUpPage");
        Sheet sheet = ExcelUtils.getSheet("SignUpPage");
        int rowCount = sheet.getLastRowNum();
        int row = 1;
        String tmp;

        // Lấy dữ liệu trong sheet "test" và thêm vào từng mảng tương ứng
        ExcelUtils.setExcelFile(excelPath, "DataOfSignUp");
        Sheet data = ExcelUtils.getSheet("DataOfSignUp");
        int rowCountTest = data.getLastRowNum();
        while (true) {
            if (row > rowCountTest)// tmp.trim().equals("")
                break;
            else {
                tmp = ExcelUtils.getCellData("DataOfSignUp", row, 1) + "";
                arrTCIDSignUp.add(tmp);

                tmp = ExcelUtils.getCellData("DataOfSignUp", row, 3) + "";
                arrNameSignUp.add(tmp);

                tmp = ExcelUtils.getCellData("DataOfSignUp", row, 4) + "";
                arrEmailSignUp.add(tmp);

                tmp = ExcelUtils.getCellData("DataOfSignUp", row, 5) + "";
                arrPasswordSignUp.add(tmp);

                tmp = ExcelUtils.getCellData("DataOfSignUp", row, 6) + "";
                arrPasswordCfSignUp.add(tmp);

                tmp = ExcelUtils.getCellData("DataOfSignUp", row, 7) + "";
                arrResultSignUp.add(tmp);

                row = row + 1;
            }
        }
        ScreenRecorderUtils.startRecord("SignUp");
        // Bỏ hàng tiêu đề
        for (int i = 0; i < arrTCIDSignUp.size(); i++) {
            for (int iRow = 1; iRow <= rowCount; iRow++) {
                reuseSignUp(iRow);
                execute_Actions(testData, arrNameSignUp.get(i), arrEmailSignUp.get(i), arrPasswordSignUp.get(i),
                        arrPasswordCfSignUp.get(i), arrResultSignUp.get(i), arrTCIDSignUp.get(i));
            }
        }
        reportInConsole();
        ScreenRecorderUtils.stopRecord();
        considerTestCase();
    }

    @Ignore
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test suite of create CV function")
    @Epic("Website CareerLink")
    @Feature("Create CV")
    //@Story("Read data test from Excel file")
    @Test
    public void TestSuite_CreateCV() throws Exception {
        ExcelUtils.setExcelFile(excelPath, "CreateCV");
        Sheet sheet = ExcelUtils.getSheet("CreateCV");
        int rowCount = sheet.getLastRowNum();
        ScreenRecorderUtils.startRecord("CreateCV");
        // Bỏ hàng tiêu đề
        for (int iRow = 1; iRow <= rowCount; iRow++) {
            reuseCreateCV(iRow);
            execute_Actions(testData, null, null, null, null, null, "CRCV_01");
        }
        reportInConsole();
        ScreenRecorderUtils.stopRecord();
        considerTestCase();
    }

    @Ignore
    @Severity(SeverityLevel.NORMAL)
    @Description("Test suite of search and view job details function")
    @Epic("Website CareerLink")
    @Feature("Search and View job detail")
    //@Story("Read data test from Excel file")
    @Test
    public void TestSuite_SearchAndViewJobDetails() throws Exception {
        ExcelUtils.setExcelFile(excelPath, "Search");
        Sheet sheet = ExcelUtils.getSheet("Search");
        int rowCount = sheet.getLastRowNum();

        ScreenRecorderUtils.startRecord("Search");
        // Bỏ hàng tiêu đề
        for (int iRow = 1; iRow <= rowCount; iRow++) {
            reuseSearch(iRow);
            execute_Actions(testData, null, null, null, null, null, "SJO_01");
        }
        reportInConsole();
        ScreenRecorderUtils.stopRecord();
    }

    private void reuseSignIn(SignInPage signInPage) {
        scriptID = signInPage.getScriptID();
        sActionKeyword = signInPage.getKeyword();
        locatorType = signInPage.getLocatorType();
        locatorValue = signInPage.getLocatorValue();
        testData = signInPage.getTestData();
    }

    public void reuseCreateCV(int iRow) {
        scriptID = ExcelUtils.getCellData("CreateCV", iRow, 1);
        sActionKeyword = ExcelUtils.getCellData("CreateCV", iRow, 5);
        locatorType = ExcelUtils.getCellData("CreateCV", iRow, 6);
        locatorValue = ExcelUtils.getCellData("CreateCV", iRow, 7);
        testData = ExcelUtils.getCellData("CreateCV", iRow, 8);
    }

    public void reuseSignIn(int iRow) {
        scriptID = ExcelUtils.getCellData("SignInPage", iRow, 1);
        sActionKeyword = ExcelUtils.getCellData("SignInPage", iRow, 5);
        locatorType = ExcelUtils.getCellData("SignInPage", iRow, 6);
        locatorValue = ExcelUtils.getCellData("SignInPage", iRow, 7);
        testData = ExcelUtils.getCellData("SignInPage", iRow, 8);
    }

    public void reuseSignUp(int iRow) {
        scriptID = ExcelUtils.getCellData("SignUpPage", iRow, 1);
        sActionKeyword = ExcelUtils.getCellData("SignUpPage", iRow, 5);
        locatorType = ExcelUtils.getCellData("SignUpPage", iRow, 6);
        locatorValue = ExcelUtils.getCellData("SignUpPage", iRow, 7);
        testData = ExcelUtils.getCellData("SignUpPage", iRow, 8);
    }

    private void reuseSearch(int iRow) {
        scriptID = ExcelUtils.getCellData("Search", iRow, 1);
        sActionKeyword = ExcelUtils.getCellData("Search", iRow, 5);
        locatorType = ExcelUtils.getCellData("Search", iRow, 6);
        locatorValue = ExcelUtils.getCellData("Search", iRow, 7);
        testData = ExcelUtils.getCellData("Search", iRow, 8);
    }

    private void execute_Actions(String testData, String sName, String sEmail, String sPass, String sPassCf,
                                 String sResult, String sTCID) {
        try {
            switch (sActionKeyword) {
                case "openBrowser":
                    ExtentReportManager.info("Test case " + sTCID);
                    ActionKeywords.openBrowser(testData);
                    break;
                case "move":
                    ActionKeywords.elementPerform(locatorValue);
                    break;
                case "switchTo":
                    ActionKeywords.switchTo(testData);
                    break;
                case "clear":
                    ActionKeywords.clear(locatorType, locatorValue);
                    break;
                case "navigate":
                    ActionKeywords.navigate(testData);
                    break;
                case "setText":
                    if (testData.equalsIgnoreCase("varEmail"))
                        ActionKeywords.setText(locatorType, locatorValue, sEmail);
                    else {
                        if (testData.equalsIgnoreCase("varName")) {
                            ActionKeywords.setText(locatorType, locatorValue, sName);
                        } else {
                            if (testData.equalsIgnoreCase("varPassword")) {
                                ActionKeywords.setText(locatorType, locatorValue, sPass);
                            } else {
                                if (testData.equalsIgnoreCase("varPasswordCf")) {
                                    ActionKeywords.setText(locatorType, locatorValue, sPassCf);
                                } else {
                                    ActionKeywords.setText(locatorType, locatorValue, testData);
                                }
                            }
                        }
                    }
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
                    if (ActionKeywords.verifyResults(sResult)) {
                        LogUtils.info("Same result ---> Pass");
                        onPass();
                    } else {
                        LogUtils.error("Different result ---> Fail");
                        onFailed();
                        break;
                    }
                    break;
                case "verifyText":
                    if (ActionKeywords.verifyText(locatorType, locatorValue, testData)) {
                        LogUtils.info("Same result ---> Pass");
                        onPass();
                    } else {
                        LogUtils.error("Different result ---> Fail");
                        onFailed();
                    }
                    break;
                case "verifyTitle":
                    if (ActionKeywords.verifyTitle(testData)) {
                        LogUtils.info("Same result ---> Pass");
                        onPass();
                    } else {
                        LogUtils.error("Different result ---> Fail");
                        onFailed();
                    }
                    break;
                case "verifyURL":
                    if (ActionKeywords.verifyURL(testData)) {
                        LogUtils.info("Same result ---> Pass");
                        onPass();
                    } else {
                        LogUtils.error("Different result ---> Fail");
                        onFailed();
                    }
                    break;
                case "displayed":
                    try {
                        ActionKeywords.displayed(locatorType, locatorValue);
                        onPass();
                    } catch (Exception e) {
                        onFailed();
                    }
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
                    LogUtils.info("[>>ERROR<<]: |Keyword Not Found " + sActionKeyword);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void onPass() {
        totalCasePass++;
        casePass++;
    }

    private void onFailed() {
        totalCaseFail++;
        caseFail++;
        ActionKeywords.addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDate());
    }

    private void reportInConsole() {
        java.util.Date date = new java.util.Date();
        System.out.println("==========================================================");
        System.out.println("-----------" + date + "--------------");
        System.out.println("Total number of Testcases run: " + (totalCasePass + totalCaseFail + totalCaseSkip));
        System.out.println("Total number of passed Testcases: " + totalCasePass);
        System.out.println("Total number of failed Testcases: " + totalCaseFail);
        System.out.println("Total number of skiped Testcases: " + totalCaseSkip);
        System.out.println("==========================================================");
        EmailSendUtils.sendEmail(totalCasePass + totalCaseFail + totalCaseSkip, totalCasePass, totalCaseFail, totalCaseSkip);
    }

    private void considerTestCase() {
        float rs = casePass / (caseSkip + caseFail + casePass);
        if (rs < STANDARD_PERCENT)
            //ExtentReportManager.fail("Test case Fail");
            casePass = 0;
            caseFail = 0;
            caseSkip = 0;
    }
}
