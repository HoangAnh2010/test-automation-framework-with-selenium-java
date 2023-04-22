package execution;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import model.Data;
import model.DataOfSignIn;
import model.Locator;
import model.SignInPage;
import report.ExtentTestManager;

import org.apache.poi.ss.usermodel.Sheet;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;

import keyword.*;

import utils.*;

import static report.ExtentManager.getExtentReports;

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

    public static String excelPath = System.getProperty("user.dir") + "\\dataEngine\\data.xlsx";
    public static String jsonPath = System.getProperty("user.dir") + "\\dataEngine\\data.json";
    public static String xmlPath = System.getProperty("user.dir") + "\\dataEngine\\data.xml";
    public static String signInCSVpath = System.getProperty("user.dir") + "\\dataEngine\\signIn.csv";
    public static String dataOfsignInCSVpath = System.getProperty("user.dir") + "\\dataEngine\\DataSignIn.csv";

    int casePass = 0;
    int caseFail = 0;
    int caseSkip = 0;

    @Ignore
    @Test //(priority = 3) // Sign in
    public void TestScript_SignIn_XML() throws Exception {
        Data xmlData = XmlUtils.xmlToData(xmlPath);
        List<SignInPage> signInPages = xmlData.getSignInPage();
        List<DataOfSignIn> dataOfSignIns = xmlData.getDataOfSignIn();
        CapturesUtils.startRecord("SignIn");
        List<Locator> locators = new ArrayList<>();
        for (DataOfSignIn dataOfSignIn : dataOfSignIns){
            Locator lct = new Locator();
            lct.setSignInTcId(dataOfSignIn.getTcId());
            lct.setSignInEmail(dataOfSignIn.getEmail());
            lct.setSignInPw(dataOfSignIn.getPassword());
            lct.setSignInResult(dataOfSignIn.getResult());
            locators.add(lct);
        }
        // get data from list data to run script
        for (int i = 0; i < locators.size(); i++) { //sheet dataofsignin
            for (SignInPage signInPage : signInPages){ //signin
                reuseSignIn(signInPage);
                execute_Actions(testData, null, locators.get(i).getSignInEmail(), locators.get(i).getSignInPw(), null,
                        locators.get(i).getSignInResult(), locators.get(i).getSignInTcId());
            }
        }
        reportInConsole();
        CapturesUtils.stopRecord();
    }

    @Ignore
    @Test //(priority = 3) // Sign in
    public void TestScript_SignIn_JsonFile() throws Exception {
        Data jsonData = JsonUtils.readData(jsonPath);
        List<SignInPage> signInPages = jsonData.getSignInPage();
        List<DataOfSignIn> dataOfSignIns = jsonData.getDataOfSignIn();
        CapturesUtils.startRecord("SignIn");
        List<Locator> locators = new ArrayList<>();
        for (DataOfSignIn dataOfSignIn : dataOfSignIns){
            Locator lct = new Locator();
                lct.setSignInTcId(dataOfSignIn.getTcId());
                lct.setSignInEmail(dataOfSignIn.getEmail());
                lct.setSignInPw(dataOfSignIn.getPassword());
                lct.setSignInResult(dataOfSignIn.getResult());
            locators.add(lct);
        }
        // get data from list data to run script
//        System.out.println(locators.size());

        for (int i = 0; i < locators.size(); i++) { //sheet dataofsignin
            for (SignInPage signInPage : signInPages){
                reuseSignIn(signInPage);
                execute_Actions(testData, null, locators.get(i).getSignInEmail(), locators.get(i).getSignInPw(), null,
                        locators.get(i).getSignInResult(), locators.get(i).getSignInTcId());
            }
        }
        reportInConsole();
        CapturesUtils.stopRecord();
    }

    //    @Ignore
    @Test //(priority = 3) // Sign in
    public void TestScript_SignIn_CSVFile() throws Exception {
        List<SignInPage> signInPages = CsvUtils.readSignInPageCSVfile(signInCSVpath);
        List<DataOfSignIn> dataOfSignIns = CsvUtils.readDataOfSignInCSVfile(dataOfsignInCSVpath);
        CapturesUtils.startRecord("SignIn");
        List<Locator> locators = new ArrayList<>();
        for (DataOfSignIn dataOfSignIn : dataOfSignIns){
            Locator lct = new Locator();
            lct.setSignInTcId(dataOfSignIn.getTcId());
            lct.setSignInEmail(dataOfSignIn.getEmail());
            lct.setSignInPw(dataOfSignIn.getPassword());
            lct.setSignInResult(dataOfSignIn.getResult());
            locators.add(lct);
        }
        // get data from list data to run script
        for (int i = 0; i < locators.size(); i++) { //sheet dataofsignin
            for (SignInPage signInPage : signInPages){
                reuseSignIn(signInPage);
                execute_Actions(testData, null, locators.get(i).getSignInEmail(), locators.get(i).getSignInPw(), null,
                        locators.get(i).getSignInResult(), locators.get(i).getSignInTcId());
            }
        }
        reportInConsole();
        CapturesUtils.stopRecord();
    }

    @Ignore
    @Test //(priority = 3)
    public void TestScript_SignIn_ExcelFile() throws Exception {
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
        CapturesUtils.startRecord("SignIn");
        // get data from list data to run script
        for (int i = 0; i < locators.size(); i++) { //sheet dataofsignin
            for (int iRow = 1; iRow <= rowCount; iRow++) {
                reuseSignIn(iRow);
                execute_Actions(testData, null, locators.get(i).getSignInEmail(), locators.get(i).getSignInPw(), null,
                        locators.get(i).getSignInResult(), locators.get(i).getSignInTcId());
            }
        }
        reportInConsole();
        CapturesUtils.stopRecord();
    }

    public void reuseSignIn(SignInPage signInPage) {
        scriptID = signInPage.getScriptID();
        sActionKeyword = signInPage.getKeyword();
        locatorType =signInPage.getLocatorType();
        locatorValue = signInPage.getLocatorValue();
        testData = signInPage.getTestData();
    }

    public void reuseSignIn(int iRow) {
        scriptID = ExcelUtils.getCellData("SignInPage", iRow, 1);
        sActionKeyword = ExcelUtils.getCellData("SignInPage", iRow, 5);
        locatorType = ExcelUtils.getCellData("SignInPage", iRow, 6);
        locatorValue = ExcelUtils.getCellData("SignInPage", iRow, 7);
        testData = ExcelUtils.getCellData("SignInPage", iRow, 8);
    }

    @Ignore
    @Test //(priority = 2) // Sign up
    public void TestSuite_SignUp() throws Exception {

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
        CapturesUtils.startRecord("SignUp");
        // Bỏ hàng tiêu đề
        for (int i = 0; i < arrTCIDSignUp.size(); i++) {
            for (int iRow = 1; iRow <= rowCount; iRow++) {
                reuseSignUp(iRow);
                execute_Actions(testData, arrNameSignUp.get(i), arrEmailSignUp.get(i), arrPasswordSignUp.get(i),
                        arrPasswordCfSignUp.get(i), arrResultSignUp.get(i), arrTCIDSignUp.get(i));
            }
        }
        reportInConsole();
        CapturesUtils.stopRecord();
    }

    public void reuseSignUp(int iRow) {
        scriptID = ExcelUtils.getCellData("SignUpPage", iRow, 1);
        sActionKeyword = ExcelUtils.getCellData("SignUpPage", iRow, 5);
        locatorType = ExcelUtils.getCellData("SignUpPage", iRow, 6);
        locatorValue = ExcelUtils.getCellData("SignUpPage", iRow, 7);
        testData = ExcelUtils.getCellData("SignUpPage", iRow, 8);
    }

    @Ignore
    @Test(priority = 4) // Create CV
    public void TestSuite_CreateCV() throws Exception {

        ExcelUtils.setExcelFile(excelPath, "CreateCV");
        Sheet sheet = ExcelUtils.getSheet("CreateCV");
        int rowCount = sheet.getLastRowNum();
//		int row = 1;
//		String tmp;
//
//		// Lấy dữ liệu trong sheet "test" và thêm vào từng mảng tương ứng
//		ExcelUtils.setExcelFile(sPath, "DataOfCreateCV");
//		Sheet data = ExcelUtils.getSheet("DataOfCreateCV");
//		int rowCountTest = data.getLastRowNum();
//		while (true) {
//			if (row > rowCountTest)// tmp.trim().equals("")
//				break;
//
//			tmp = ExcelUtils.getCellData("DataOfCreateCV", row, 1) + "";
//			arrTCIDSignIn.add(tmp);
//
//			tmp = ExcelUtils.getCellData("DataOfCreateCV", row, 3) + "";
//			arrEmailSignIn.add(tmp);
//
//			tmp = ExcelUtils.getCellData("DataOfCreateCV", row, 4) + "";
//			arrPasswordSignIn.add(tmp);
//
//			tmp = ExcelUtils.getCellData("DataOfCreateCV", row, 5) + "";
//			arrResultSignIn.add(tmp);
//
//			row = row + 1;
//		}
        CapturesUtils.startRecord("CreateCV");
        // Bỏ hàng tiêu đề
        for (int iRow = 1; iRow <= rowCount; iRow++) {
            reuseCreateCV(iRow);
            execute_Actions(testData, null, null, null, null, null, null);
        }
        reportInConsole();
        CapturesUtils.stopRecord();
    }

    public void reuseCreateCV(int iRow) {
        scriptID = ExcelUtils.getCellData("CreateCV", iRow, 1);
        sActionKeyword = ExcelUtils.getCellData("CreateCV", iRow, 5);
        locatorType = ExcelUtils.getCellData("CreateCV", iRow, 6);
        locatorValue = ExcelUtils.getCellData("CreateCV", iRow, 7);
        testData = ExcelUtils.getCellData("CreateCV", iRow, 8);
    }

    @Ignore
    @Test(priority = 1) // Search
    public void TestSuite_SearchAndViewJobDetails() throws Exception {

        ExcelUtils.setExcelFile(excelPath, "Search");
        Sheet sheet = ExcelUtils.getSheet("Search");
        int rowCount = sheet.getLastRowNum();

        CapturesUtils.startRecord("Search");
        // Bỏ hàng tiêu đề
        for (int iRow = 1; iRow <= rowCount; iRow++) {
            reuseSearch(iRow);
            execute_Actions(testData, null, null, null, null, null, null);
        }
        reportInConsole();
        CapturesUtils.stopRecord();
    }

    private void reuseSearch(int iRow) {
        scriptID = ExcelUtils.getCellData("Search", iRow, 1);
        sActionKeyword = ExcelUtils.getCellData("Search", iRow, 5);
        locatorType = ExcelUtils.getCellData("Search", iRow, 6);
        locatorValue = ExcelUtils.getCellData("Search", iRow, 7);
        testData = ExcelUtils.getCellData("Search", iRow, 8);
    }

    private void execute_Actions(String testData, String sName, String sEmail, String sPass, String sPassCf,
                                String sResult, String sTCID) throws Exception {
        try {
            switch (sActionKeyword) {
                case "openBrowser":
                    // Log.info("--------------Execute Test Case--------------");
//                        ExtentTestManager.saveToReport("Test Case", "");
                    try {
                        ActionKeywords.openBrowser(testData);
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (Exception e) {
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "move":
                    try {
                        ActionKeywords.elementPerform(locatorValue);
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (Exception e) {
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "switchTo":
                    try {
                        ActionKeywords.switchTo(testData);
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (Exception e) {
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "clear":
                    try {
                        ActionKeywords.clear(locatorType, locatorValue);
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (Exception e) {
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "navigate":
                    try {
                        ActionKeywords.navigate(testData);
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (Exception e) {
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "setText":
                    try {
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

                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (NoSuchElementException e) {
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }

                    break;
                case "uploadImage":
                    try {
                        ActionKeywords.uploadImage(locatorType, locatorValue, testData);
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (Exception e) {
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "clickButton":
                    try {
                        ActionKeywords.clickButton(locatorType, locatorValue);
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (NoSuchElementException e) {
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }

                    break;
                case "doubleClick":
                    try {
                        ActionKeywords.doubleClick(locatorType, locatorValue);
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (NoSuchElementException e) {
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }

                    break;
                case "clickElement":
                    try {
                        ActionKeywords.clickElement(locatorType, locatorValue);
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (NoSuchElementException e) {
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "verifyResults":
                    if (ActionKeywords.verifyResults(sResult)) {
                        Log.info("Same result ---> Pass");
                        casePass++;
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } else {
                        Log.error("Different result ---> Fail");
                        caseFail++;
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "verifyTextInSignIn":
                    if (ActionKeywords.verifyTextInSignIn(sResult)) {
                        Log.info("Same result ---> Pass");
                        casePass++;
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } else {
                        Log.error("Different result ---> Fail");
                        caseFail++;
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "alertInSignInPage":
                    if (ActionKeywords.verifyAlertInSignIn(sResult)) {
                        Log.info("Same result ---> Pass");
                        casePass++;
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } else {
                        Log.error("Different result ---> Fail");
                        caseFail++;
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "alertOfEmailinSignInPagehtml5":
                    if (ActionKeywords.verifyAlertOfEmailinSignInPagehtml5(locatorType, locatorValue, sResult)) {
                        Log.info("Same result ---> Pass");
                        casePass++;
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } else {
                        Log.error("Different result ---> Fail");
                        caseFail++;
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "alertOfPasswordinSignInPagehtml5":
                    if (ActionKeywords.verifyAlertOfPasswordinSignInPagehtml5(sResult)) {
                        Log.info("Same result ---> Pass");
                        casePass++;
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } else {
                        Log.error("Different result ---> Fail");
                        caseFail++;
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "alertNameSignUp":
                    if (ActionKeywords.verifyAlertNameSignUp(sResult)) {
                        Log.info("Same result ---> Pass");
                        casePass++;
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } else {
                        Log.error("Different result ---> Fail");
                        caseFail++;
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "verifyText":
                    if (ActionKeywords.verifyText(locatorType, locatorValue, testData)) {
                        Log.info("Same result ---> Pass");
                        casePass++;
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } else {
                        Log.error("Different result ---> Fail");
                        caseFail++;
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "verifyTitle":
                    if (ActionKeywords.verifyTitle(testData)) {
                        Log.info("Same result ---> Pass");
                        casePass++;
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } else {
                        Log.error("Different result ---> Fail");
                        caseFail++;
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "verifyUrl":
                    if (ActionKeywords.getUrl(testData)) {
                        Log.info("Same result ---> Pass");
                        casePass++;
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } else {
                        Log.error("Different result ---> Fail");
                        caseFail++;
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "displayed":
                    try {
                        ActionKeywords.displayed(locatorType, locatorValue);
                        casePass++;
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (Exception e) {
                        caseFail++;
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "screenShot":
                    try {
                        ActionKeywords.screenShot(scriptID + "_" + sTCID);
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (Exception e) {
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "selectOptionByValue":
                    try {
                        ActionKeywords.selectOptionByValue(locatorType, locatorValue, testData);
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (NoSuchElementException e) {
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "selectOptionByText":
                    try {
                        ActionKeywords.selectOptionByText(locatorType, locatorValue, testData);
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (NoSuchElementException e) {
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "scrollDown":
                    try {
                        ActionKeywords.clickElementWithJs(locatorType, locatorValue);
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (NoSuchElementException e) {
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                case "closeBrowser":
                    try {
                        ActionKeywords.closeBrowser();
                        //ExtentTestManager.logMessage(Status.PASS, description);
                    } catch (Exception e) {
                        //ExtentTestManager.logMessage(Status.FAIL, description);
                    }
                    break;
                default:
                    Log.info("[>>ERROR<<]: |Keyword Not Found " + sActionKeyword);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        getExtentReports().flush();

    }

    public void reportInConsole() {
        java.util.Date date = new java.util.Date();
        System.out.println("==========================================================");
        System.out.println("-----------" + date + "--------------");
        System.out.println("Total number of Testcases run: " + (casePass + caseFail + caseSkip));
        System.out.println("Total number of passed Testcases: " + casePass);
        System.out.println("Total number of failed Testcases: " + caseFail);
        System.out.println("Total number of skiped Testcases: " + caseSkip);
        System.out.println("==========================================================");
    }
}
