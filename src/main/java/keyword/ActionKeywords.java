package keyword;

import com.aventstack.extentreports.Status;
import constants.FrwConstants;
import helpers.Helpers;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
//import report.AllureManager;
//import report.ExtentReportManager;
//import report.ExtentTestManager;
import report.AllureManager;
import report.ExtentReportManager;
import report.ExtentTestManager;
import utils.DateUtils;
import utils.LogUtils;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class ActionKeywords {

    private static SoftAssert softAssert = new SoftAssert();
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Actions action;
    private static JavascriptExecutor js;
    private static final int timeoutWait = 10;
    private static final int timeoutWaitForPageLoaded = 20;

    private static WebElement getElement(String locatorType, String locatorValue) {
        WebElement element = null;

        if (locatorType.equalsIgnoreCase("className")) element = driver.findElement(By.className(locatorValue));
        else if (locatorType.equalsIgnoreCase("cssSelector"))
            element = driver.findElement(By.cssSelector(locatorValue));
        else if (locatorType.equalsIgnoreCase("id")) element = driver.findElement(By.id(locatorValue));
        else if (locatorType.equalsIgnoreCase("partialLinkText"))
            element = driver.findElement(By.partialLinkText(locatorValue));
        else if (locatorType.equalsIgnoreCase("name")) element = driver.findElement(By.name(locatorValue));
        else if (locatorType.equalsIgnoreCase("xpath")) element = driver.findElement(By.xpath(locatorValue));
        else if (locatorType.equalsIgnoreCase("tagName")) element = driver.findElement(By.tagName(locatorValue));
        else {
            LogUtils.error("GetElement " + locatorType + "=" + locatorValue);
        }
        return element;
    }

    //open browser
    private static WebDriver initChromeDriver() {
        try {
            LogUtils.info("Executing: Open browser Chrome");
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
//			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
//			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            LogUtils.error("Executing: Open browser Chrome fail");
            ExtentReportManager.info("Opening browser Chrome fail");
        }

        return driver;
    }

    private static WebDriver initFirefoxDriver() {
        try {
            LogUtils.info("Executing: Open browser Firefox");
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            LogUtils.error("Executing: Open browser Firefox fail");
            ExtentReportManager.info("Opening browser Firefox fail");
        }

        return driver;
    }

    public static WebDriver openBrowser(java.lang.String browserType) {
        switch (browserType.trim().toLowerCase()) {
            case "chrome":
                driver = initChromeDriver();
                break;
            case "firefox":
                driver = initFirefoxDriver();
                break;
            default:
                LogUtils.info("Browser: " + browserType + " is invalid, Launching Chrome as browser of choice...");
                driver = initChromeDriver();
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutWait));
        ExtentReportManager.logMessage(Status.PASS, "Opening browser: " + browserType.trim());
        return driver;
    }

    //close browser
    public static void closeBrowser() {
        try {
            LogUtils.info("Executing: Closing...");
            driver.manage().deleteAllCookies();
            driver.quit();
            ExtentReportManager.pass("Closing browser");
        } catch (Exception e) {
            LogUtils.error("Executing: Close FAIL");
            ExtentReportManager.info("Can't close browser");
        }
    }

    //navigate to url
    public static void navigate(String url) {
        try {
            LogUtils.info("Opening Url: " + url);
            driver.navigate().to(url);
            waitForPageLoaded();
            ExtentReportManager.pass("Navigating to: " + url);
        } catch (Exception e) {
            LogUtils.error("Opening Url:" + url + "FAIL");
            ExtentReportManager.info("Opening Url:" + url + "FAIL");
        }
    }

    //refresh page
    public static void refresh() throws InterruptedException {
        LogUtils.info("Refreshing website");
        ExtentReportManager.pass("Refreshing website");
        driver.navigate().refresh();
        Thread.sleep(3000);
    }

    //clear
    public static void clear(String locatorType, String locatorValue) throws InterruptedException {
        LogUtils.info("Clearing the element");
        WebElement element = getElement(locatorType, locatorValue);
        element.clear();
        ExtentReportManager.pass("Clearing the element");
    }

    public void setText(WebElement element, String value) {
        LogUtils.info("Setting text");
        ExtentReportManager.pass("Setting text");
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.clear();
        element.sendKeys(value);

    }

    //set text
    public static void setText(String locatorType, String locatorValue, String value) {
        try {
            LogUtils.info("Entering text: " + value);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(value);
            ExtentReportManager.pass("Entering text: " + value);
        } catch (NoSuchElementException e) {

            LogUtils.error("SendKeys:" + locatorType + "=" + locatorValue + " not found to sendKeys| " + e.getMessage());
            ExtentReportManager.info("SendKeys:" + locatorType + "=" + locatorValue + " not found to sendKeys| " + e.getMessage());
        }

    }

    //move to element
    public static void elementPerform(String address) {

        try {
            if (address.contains("[")) {
                LogUtils.info("Moving mouse");
                Actions a = new Actions(driver);
                waitForPageLoaded();
                a.moveToElement(driver.findElement(By.xpath(address))).build().perform();
                ExtentReportManager.pass("Moving mouse");
            } else {
                Actions a = new Actions(driver);
                a.moveToElement(driver.findElement(By.id(address))).build().perform();
            }
        } catch (Exception e) {
            LogUtils.error("Move mouse: FAIL");
            ExtentReportManager.info("Moving mouse: FAIL");
        }
    }

    public void clickElement(WebElement element) {

        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    //click
    public static void clickButton(String locatorType, String locatorValue) throws Exception {
        try {
            LogUtils.info("Executing: Click on button: " + locatorType + "= " + locatorValue);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            element.click();
            ExtentReportManager.pass("Clicking on button: " + locatorType + "= " + locatorValue);
        } catch (NoSuchElementException e) {
            LogUtils.error("Click on:" + locatorValue + " not found to click " + e.getMessage());
            ExtentReportManager.info("Clicking on:" + locatorValue + " not found to click " + e.getMessage());
        }
        Thread.sleep(1500);
    }

    public static void doubleClick(String locatorType, String locatorValue) throws Exception {
        try {
            LogUtils.info("Executing: Double click on: " + locatorType + "= " + locatorValue);
            Actions action = new Actions(driver);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            action.doubleClick(element).perform();
            ExtentReportManager.pass("Executing: Double click on: " + locatorType + "= " + locatorValue);
        } catch (NoSuchElementException e) {
            LogUtils.error("Double click on:" + locatorValue + " not found to double click " + e.getMessage());
            ExtentReportManager.info("Double click on:" + locatorValue + " not found to double click " + e.getMessage());
        }
        Thread.sleep(1500);
    }

    public static void switchTo(String value) throws Exception {
        try {
            LogUtils.info("Executing: Switch to new tab");
            String currentWindow = driver.getWindowHandle();  //will keep current window to switch back
            for (String winHandle : driver.getWindowHandles()) {
                if (driver.switchTo().window(winHandle).getTitle().equals(value)) {
                    //This is the one you're looking for
                    break;
                } else {
                    driver.switchTo().window(currentWindow);
                }
                waitForPageLoaded();
            }
            ExtentReportManager.pass("Executing: Switch to new tab");
        } catch (NoSuchElementException e) {
            LogUtils.error("Switch to new tab fail");
            ExtentReportManager.info("Switching to new tab fail");
        }
        //Thread.sleep(1500);
    }


    public static void clickElement(String locatorType, String locatorValue) throws Exception {
        try {
            LogUtils.info("Executing: Click on element: " + locatorType + "= " + locatorValue);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            //Thread.sleep(4000);
            element.click();
            ExtentReportManager.pass("Clicking on element: " + locatorType + "= " + locatorValue);

        } catch (NoSuchElementException e) {
            LogUtils.error("Clicking on:" + locatorValue + " not found to click " + e.getMessage());
            ExtentReportManager.info("Clicking on:" + locatorValue + " not found to click " + e.getMessage());
        }
        Thread.sleep(1500);
    }

    public static void clickElementWithJs(String locatorType, String locatorValue) {
        js = (JavascriptExecutor) driver; // khởi tạo
        try {
            LogUtils.info("Executing: Scroll mouse down and click element: " + locatorValue);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            js.executeScript("arguments[0].scrollIntoView(true)", element);
            //js.executeScript("arguments[0].click();", element);
            ExtentReportManager.pass("Scrolling mouse down and click element: " + locatorValue);
        } catch (NoSuchElementException e) {
            LogUtils.error("|clickElementWithJs:" + locatorType + "=" + locatorValue + " not found to click| " + e.getMessage());
            ExtentReportManager.info("|clickElementWithJs:" + locatorType + "=" + locatorValue + " not found to click| " + e.getMessage());
        }
    }

    public static void waitForPageLoaded() {
        try {
            wait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")).equals("complete");
                }
            });
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load request.");
        }
    }

    public boolean verifyPageLoaded(String pageLoadedText) {
        waitForPageLoaded();
        Boolean res = false;

        List<WebElement> elementList = driver.findElements(By.xpath("//*contains(text(),'" + pageLoadedText + "')]"));
        if (elementList.size() > 0) {
            res = true;
            LogUtils.info("Page loaded(" + res + "): " + pageLoadedText);
            ExtentReportManager.info("Page loaded(" + res + "): " + pageLoadedText);
        } else {
            res = false;
            LogUtils.info("Page loaded(" + res + "): " + pageLoadedText);
            ExtentReportManager.info("Page loaded(" + res + "): " + pageLoadedText);
        }
        return res;
    }

    //displayed
    public static void displayed(String locatorType, String locatorValue) {
        LogUtils.info("Verify element displayed");
        ExtentReportManager.info("Verify element displayed");
        WebElement element = getElement(locatorType, locatorValue);
        try {
            element.isDisplayed();
            LogUtils.info("Result: Element is displayed");
            ExtentReportManager.pass("Result: Element is displayed");
        } catch (Exception e) {
            LogUtils.error("Element is not displayed");
            ExtentReportManager.info("Element is not displayed");
        }
    }

    public boolean verifyUrl(String url) {
        LogUtils.info("Executing: | Verify url");
        ExtentReportManager.pass("Executing: | Verify url");
        return driver.getCurrentUrl().contains(url);
    }

    //get URL current
    public static boolean getUrl(String value) throws InterruptedException {
        LogUtils.info("Executing: |Get Url|");
        ExtentReportManager.info("Executing: |Get Url|");
        waitForPageLoaded();
        String resultUrl = driver.getCurrentUrl();
        LogUtils.info("Expected Url -->" + value);
        LogUtils.info("Actual Url -->" + resultUrl);
        if (value.equals(resultUrl)) {
            return true;
        } else return false;
    }

    public String getPageTitle() {
        LogUtils.info("Executing: | Get page title |");
        ExtentReportManager.info("Executing: | Get page title |");
        waitForPageLoaded();
        return driver.getTitle();
    }

    public boolean verifyPageTitle(String pageTitle) {
        LogUtils.info("Executing: | Verify page title |");
        ExtentReportManager.info("Executing: | Verify page title |");
        return getPageTitle().equals(pageTitle);
    }

    //right click
    public void rightClickElement(String locatorType, String locatorValue) {

        try {
            LogUtils.info("Executing: | Right click element: " + locatorType + "= " + locatorValue);
            ExtentReportManager.pass("Executing: | Right click element: " + locatorType + "= " + locatorValue);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            wait.until(ExpectedConditions.elementToBeClickable(element));
            action.contextClick().build().perform();
        } catch (NoSuchElementException e) {
            LogUtils.error("|Right click: " + locatorType + "= " + locatorValue + " not found to click| " + e.getMessage());
            ExtentReportManager.info("|Right click: " + locatorType + "= " + locatorValue + " not found to click| " + e.getMessage());
        }
    }

    //select data from dropdown
    public static void selectOptionByText(String locatorType, String locatorValue, String text) {
        try {
            LogUtils.info("Executing: | Selection option: " + text);
            ExtentReportManager.pass("Selecting option: " + text);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            Select select = new Select(element);
            select.selectByVisibleText(text);
        } catch (NoSuchElementException e) {
            LogUtils.error("|select Option By Text|: " + locatorType + "= " + locatorValue + "|text: " + text + " not found to select| " + e.getMessage());
            ExtentReportManager.info("|select Option By Text|: " + locatorType + "= " + locatorValue + "|text: " + text + " not found to select| " + e.getMessage());
        }
    }

    public static void selectOptionByValue(String locatorType, String locatorValue, String value) {
        try {
            LogUtils.info("Executing: | Selection option: " + value);
            ExtentReportManager.pass("Selecting option: " + value);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            Select select = new Select(element);
            select.selectByValue(value);
        } catch (NoSuchElementException e) {
            LogUtils.error("Select Option By Value: " + locatorType + "= " + locatorValue + "|value: " + value + " not found to select| " + e.getMessage());
            ExtentReportManager.fail("Select Option By Value: " + locatorType + "= " + locatorValue + "|value: " + value + " not found to select| " + e.getMessage());
        }
    }

    public static void selectOptionByIndex(String locatorType, String locatorValue, int index) {

        try {
            LogUtils.info("Executing: | Selection option have index is: " + index);
            ExtentReportManager.pass("Selecting option have index is: " + index);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            Select select = new Select(element);
            select.selectByIndex(index);
        } catch (NoSuchElementException e) {
            LogUtils.error("Select Option By Index: " + locatorType + "= " + locatorValue + "|index: " + index + " not found to select| " + e.getMessage());
            ExtentReportManager.info("Select Option By Index: " + locatorType + "= " + locatorValue + "|index: " + index + " not found to select| " + e.getMessage());
        }
    }

    public boolean verifyElementText(WebElement element, String textValue) {
        LogUtils.info("Executing: | Verify element text");
        ExtentReportManager.info("Verify element text");
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText().equals(textValue);
    }

    public static boolean verifyElementText(String locatorType, String locatorValue, String value) {
        LogUtils.info("Executing: | Verify Element Text");
        ExtentReportManager.info("Verify Element Text");
        String result;
        WebElement element = getElement(locatorType, locatorValue);
        waitForPageLoaded();
        result = element.getText();
        LogUtils.info("Title expected: " + value);
        LogUtils.info("Title actual: " + result);
        if (value.equals(result)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyElementExist(By elementBy) {
        // Tạo list lưu tất cả các đối tượng WebElement
        LogUtils.info("Executing: | Verify Element Exist");
        ExtentReportManager.info("Verify Element Exist");
        List<WebElement> listElement = driver.findElements(elementBy);

        int total = listElement.size();
        if (total > 0) {
            return true;
        }
        return false;
    }

    // wait for Javascript to Loaded
    public void waitForJQueryLoaded() {
        ExpectedCondition<Boolean> jQueryload = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                try {

                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutWait));
            wait.until(jQueryload);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for JQuery.");
        }
    }

    // wait for Javascript to Loaded
    public void waitForJSLoaded() {

        ExpectedCondition<Boolean> jsload = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutWaitForPageLoaded));
            wait.until(jsload);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Javascript.");
        }
    }

    public static boolean verifyText(String locatorType, String locatorValue, String value) {
        LogUtils.info("Executing: | Verify Text");
        ExtentReportManager.info("Verify Text");
        String result;
        WebElement element = getElement(locatorType, locatorValue);
        waitForPageLoaded();
        result = element.getText();
        LogUtils.info("Title expected: " + value);
        LogUtils.info("Title actual: " + result);
        if (result.contains(value)) {
            return true;
        } else return false;
    }

    public static boolean verifyTitle(String value) {
        LogUtils.info("Executing: | Verify Title");
        ExtentReportManager.info("Verify Title");
        String actual = driver.getTitle();
        waitForPageLoaded();
        LogUtils.info("Title expected: " + value);
        LogUtils.info("Title actual: " + actual);
        if (actual.contains(value)) {
            return true;
        } else return false;
    }

    public static boolean verifyResults(String expected) throws InterruptedException {
        LogUtils.info("Executing: | Verify the result");
        ExtentReportManager.info("Verify the result");
        Boolean stt = false;
        String actual;
        if (driver.getCurrentUrl().contains("login")) {
            WebElement emailInSignInPage = getElement("name", "_username");//driver.findElement(By.name("_username"));
            WebElement pwInSignInPage = getElement("name", "_password");
            WebElement errorMessageInSignInPage = getElement("xpath", "//form[@id='login_form']/div");
            if (emailInSignInPage.getText().trim() == "" || emailInSignInPage.getText() == null || emailInSignInPage.getText().trim().contains(" ") == true || emailInSignInPage.getText().trim().contains("@") == false) {
                actual = emailInSignInPage.getAttribute("validationMessage");
                waitForPageLoaded();
                LogUtils.info("Expected Result: " + expected);
                LogUtils.info("Actual Result: " + actual);
                if (expected.equals(actual)) {
                    stt = true;
                }
            } else {
                if (pwInSignInPage.getText() == null) {
                    actual = pwInSignInPage.getAttribute("validationMessage");
                    waitForPageLoaded();
                    LogUtils.info("Expected Result: " + expected);
                    LogUtils.info("Actual Result: " + actual);
                    if (expected.equals(actual)) {
                        stt = true;
                    }
                } else {
                    actual = errorMessageInSignInPage.getText();
                    waitForPageLoaded();
                    LogUtils.info("Expected Result: " + expected);
                    LogUtils.info("Actual Result: " + actual);
                    if (expected.equals(actual)) {
                        stt = true;
                    }
                }
            }
        }
        if (driver.getCurrentUrl().contains("dang-ky")) {
            WebElement emailInSignUpPage = getElement("id", "email");//driver.findElement(By.id("email"));
            WebElement pwInSignUpPage = getElement("id", "password");
            WebElement pwConfirmInSignInPage = getElement("id", "password_confirmation");
            WebElement errorMessageInSignUpPage = driver.findElement(By.id("invalid_password_feedback"));
            if (emailInSignUpPage.getText().trim().contains("@") == false || emailInSignUpPage.getText().trim().contains(" ") == true || emailInSignUpPage.getText().trim() == "" || emailInSignUpPage.getText() == null) {
                actual = emailInSignUpPage.getAttribute("validationMessage");
                waitForPageLoaded();
                LogUtils.info("Expected Result: " + expected);
                LogUtils.info("Actual Result: " + actual);
                if (expected.equals(actual)) {
                    stt = true;
                }
            } else {
                if (pwInSignUpPage.getText() == null) {
                    actual = pwInSignUpPage.getAttribute("validationMessage");
                    waitForPageLoaded();
                    LogUtils.info("Expected Result: " + expected);
                    LogUtils.info("Actual Result: " + actual);
                    if (expected.equals(actual)) {
                        stt = true;
                    }
                } else {
                    if (pwConfirmInSignInPage.getText() == null) {
                        actual = pwConfirmInSignInPage.getAttribute("validationMessage");
                        waitForPageLoaded();
                        LogUtils.info("Expected Result: " + expected);
                        LogUtils.info("Actual Result: " + actual);
                        if (expected.equals(actual)) {
                            stt = true;
                        }
                    } else {
                        actual = errorMessageInSignUpPage.getText();
                        waitForPageLoaded();
                        LogUtils.info("Expected Result: " + expected);
                        LogUtils.info("Actual Result: " + actual);
                        if (expected.equals(actual)) {
                            stt = true;
                        }
                    }
                }
            }
        }
        if (driver.getCurrentUrl().contains("nguoi-tim-viec") && !driver.getCurrentUrl().contains("login")) {
            WebElement successSignIn = driver.findElement(By.xpath("//span[contains(text(),'Nguyễn Hoàng Anh')]"));
            actual = successSignIn.getText();
            waitForPageLoaded();
            LogUtils.info("Expected Result: " + expected);
            LogUtils.info("Actual Result: " + actual);
            if (expected.equals(actual)) {
                stt = true;
            }
        }

        AllureManager.saveTextLog("Verify the result");
        if (stt) {
            ExtentReportManager.pass("Actual result is the same as expected result");
        } else {
            ExtentReportManager.info("Actual result isn't the same as expected result");
        }
        return stt;
    }

    //upload image
    public static void uploadImage(String locatorType, String locatorValue, String value) {
        try {
            LogUtils.info("Executing: Upload image: " + value);
            ExtentReportManager.pass("Uploading image: " + value);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            wait.until(ExpectedConditions.visibilityOf(element));
            //element.clear();
            element.sendKeys(value);
        } catch (NoSuchElementException e) {
            LogUtils.error("SendKeys:" + locatorType + "=" + locatorValue + " not found to sendKeys| " + e.getMessage());
            ExtentReportManager.info("SendKeys:" + locatorType + "=" + locatorValue + " not found to sendKeys| " + e.getMessage());
        }
    }

    public static String screenShot(String CaseName) {
        try {
            // Tạo tham chiếu của TakesScreenshot với driver hiện tại
            TakesScreenshot ts = (TakesScreenshot) driver;
            // Gọi hàm capture screenshot - getScreenshotAs
            File source = ts.getScreenshotAs(OutputType.FILE);
            //Kiểm tra folder tồn tại. Nêu không thì tạo mới folder
            String path = Helpers.getCurrentDir() + FrwConstants.EXPORT_CAPTURE_PATH;
            File theDir = new File(path);
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            String imgPath = theDir + "\\" + CaseName + ".png";
            // result.getName() lấy tên của test case xong gán cho tên File chụp màn hình luôn
            FileHandler.copy(source, new File(imgPath));
            LogUtils.info("Executing: Screenshot taken: " + imgPath);
            return imgPath;
        } catch (Exception e) {
            LogUtils.error("Executing: Screenshot taken: " + CaseName + "FAIL");
        }
        return "";
    }

    public static void addScreenshotToReport(String screenshotName) {
//        ActionKeywords.screenShot(screenshotName);
        ExtentReportManager.addScreenShot(ActionKeywords.screenShot(screenshotName));
    }

    public static void stopSoftAssertAll() {
        softAssert.assertAll();
    }
}
