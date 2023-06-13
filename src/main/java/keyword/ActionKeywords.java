package keyword;

import com.aventstack.extentreports.Status;
import constants.FrwConstants;
import enums.FailureHandling;
import helpers.Helpers;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
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
import report.AllureManager;
import report.ExtentReportManager;
import report.ExtentTestManager;
import utils.DateUtils;
import utils.LogUtils;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
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

        if (locatorType.equalsIgnoreCase("className"))
            element = driver.findElement(By.className(locatorValue));
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

    private static WebDriver initChromeDriver() {
        try {
            LogUtils.info("Open browser: Chrome");
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        } catch (Exception e) {
            LogUtils.error("Can't open browser Chrome");
            ExtentReportManager.info("Can't open browser Chrome");
        }

        return driver;
    }

    private static WebDriver initFirefoxDriver() {
        try {
            LogUtils.info("Open browser: Firefox");
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            LogUtils.error("Can't open browser Firefox");
            ExtentReportManager.info("Can't opening browser Firefox");
        }

        return driver;
    }

    @Step("Open browser: {0}")
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
        ExtentReportManager.logMessage(Status.PASS, "Open browser: " + browserType.trim());
        return driver;
    }

    @Step("Quit browser")
    public static void quitBrowser() {
        try {
            LogUtils.info("Quit browser");
            driver.manage().deleteAllCookies();
            driver.quit();
            ExtentReportManager.pass("Quit browser");
        } catch (Exception e) {
            LogUtils.error("Can't quit browser");
            ExtentReportManager.info("Can't quit browser");
            AllureManager.saveTextLog("Can't quit browser");
        }
    }

    @Step("Close browser")
    public static void closeBrowser() {
        try {
            LogUtils.info("Close browser");
            driver.manage().deleteAllCookies();
            driver.close();
            ExtentReportManager.pass("Close browser");
        } catch (Exception e) {
            LogUtils.error("Can't close browser");
            ExtentReportManager.info("Can't close browser");
            AllureManager.saveTextLog("Can't close browser");
        }
    }

    @Step("Navigate to: {0}")
    public static void navigate(String url) {
        try {
            LogUtils.info("Navigate to: " + url);
            driver.navigate().to(url);
            waitForPageLoaded();
            ExtentReportManager.pass("Navigate to: " + url);
        } catch (Exception e) {
            LogUtils.error("Can't navigate to: " + url);
            ExtentReportManager.info("Can't navigate to: " + url);
            AllureManager.saveTextLog("Can't navigate to: " + url);
        }
    }

    @Step("Refresh the website")
    public static void refresh() throws InterruptedException {
        LogUtils.info("Refresh the website");
        ExtentReportManager.pass("Refresh the website");
        driver.navigate().refresh();
        Thread.sleep(3000);
    }

    @Step("Clear the element")
    public static void clear(String locatorType, String locatorValue) throws InterruptedException {
        LogUtils.info("Clear the element");
        WebElement element = getElement(locatorType, locatorValue);
        element.clear();
        ExtentReportManager.pass("Clear the element");
    }

    @Step("Enter text")
    public void setText(WebElement element, String value) {
        LogUtils.info("Enter text: " + value);
        ExtentReportManager.pass("Enter text: " + value);
        AllureManager.saveTextLog("Enter text: " + value);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.clear();
        element.sendKeys(value);
    }

    @Step("Enter text")
    public static void setText(String locatorType, String locatorValue, String value) {
        try {
            LogUtils.info("Enter text: " + value);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(value);
            ExtentReportManager.pass("Enter text: " + value);
            AllureManager.saveTextLog("Enter text: " + value);
        } catch (NoSuchElementException e) {
            LogUtils.error("Locator:" + locatorType + "=" + locatorValue + " not found to enter text | " + e.getMessage());
            ExtentReportManager.info("Locator:" + locatorType + "=" + locatorValue + " not found to enter text | " + e.getMessage());
            AllureManager.saveTextLog("Locator:" + locatorType + "=" + locatorValue + " not found to enter text | " + e.getMessage());
        }
    }

    @Step("Move to the element")
    public static void moveElement(String address) {
        try {
            if (address.contains("[")) {
                LogUtils.info("Move to the element");
                Actions a = new Actions(driver);
                waitForPageLoaded();
                a.moveToElement(driver.findElement(By.xpath(address))).build().perform();
                ExtentReportManager.pass("Move to the element");
            } else {
                Actions a = new Actions(driver);
                a.moveToElement(driver.findElement(By.id(address))).build().perform();
            }
        } catch (Exception e) {
            LogUtils.error("Can't move to the element");
            ExtentReportManager.info("Can't move to the element");
            AllureManager.saveTextLog("Can't move to the element");
        }
    }

    @Step("Click on element")
    public void clickElement(WebElement element) {
        LogUtils.info("Click on element");
        ExtentReportManager.pass("Click on element");
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    @Step("Click on button")
    public static void clickButton(String locatorType, String locatorValue) throws InterruptedException {
        try {
            LogUtils.info("Click on button: " + locatorType + "= " + locatorValue);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            element.click();
            ExtentReportManager.pass("Click on button: " + locatorType + "= " + locatorValue);
        } catch (NoSuchElementException e) {
            LogUtils.error("Locator:" + locatorValue + " not found to click | " + e.getMessage());
            ExtentReportManager.info("Locator:" + locatorValue + " not found to click | " + e.getMessage());
            AllureManager.saveTextLog("Locator:" + locatorValue + " not found to click | " + e.getMessage());
        }
        Thread.sleep(2000);
    }

    @Step("Click on element")
    public static void clickElement(String locatorType, String locatorValue) throws InterruptedException {
        try {
            LogUtils.info("Click on element: " + locatorType + "= " + locatorValue);
            WebElement element = getElement(locatorType,locatorValue);
            //waitForPageLoaded();
            element.click();
            ExtentReportManager.pass("Click on element: " + locatorType + "= " + locatorValue);
        } catch (NoSuchElementException e) {
            LogUtils.error("Locator:" + locatorValue + " not found to click | " + e.getMessage());
            ExtentReportManager.info("Locator:" + locatorValue + " not found to click | " + e.getMessage());
            AllureManager.saveTextLog("Locator:" + locatorValue + " not found to click | " + e.getMessage());
        }
        Thread.sleep(1000);
    }

    @Step("Right click on element")
    public void rightClickElement(String locatorType, String locatorValue) {
        try {
            LogUtils.info("Right click on element: " + locatorType + "= " + locatorValue);
            ExtentReportManager.pass("Right click on element: " + locatorType + "= " + locatorValue);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            wait.until(ExpectedConditions.elementToBeClickable(element));
            action.contextClick().build().perform();
        } catch (NoSuchElementException e) {
            LogUtils.error("Locator: " + locatorType + "= " + locatorValue + " not found to right click | " + e.getMessage());
            ExtentReportManager.info("Locator: " + locatorType + "= " + locatorValue + " not found to right click | " + e.getMessage());
            AllureManager.saveTextLog("Locator: " + locatorType + "= " + locatorValue + " not found to right click | " + e.getMessage());
        }
    }

    @Step("Double click")
    public static void doubleClick(String locatorType, String locatorValue) throws Exception {
        try {
            LogUtils.info("Double click on: " + locatorType + "= " + locatorValue);
            Actions action = new Actions(driver);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            action.doubleClick(element).perform();
            ExtentReportManager.pass("Double click on: " + locatorType + "= " + locatorValue);
        } catch (NoSuchElementException e) {
            LogUtils.error("Locator:" + locatorValue + " not found to double click | " + e.getMessage());
            ExtentReportManager.info("Locator:" + locatorValue + " not found to double click | " + e.getMessage());
            AllureManager.saveTextLog("Locator:" + locatorValue + " not found to double click | " + e.getMessage());
        }
        Thread.sleep(1500);
    }

    @Step("Scroll mouse down and click element")
    public static void scrollAndClick(String locatorType, String locatorValue) {
        js = (JavascriptExecutor) driver;
        try {
            LogUtils.info("Scroll mouse down and click element: " + locatorValue);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            js.executeScript("arguments[0].scrollIntoView(true)", element);
            ExtentReportManager.pass("Scroll mouse down and click element: " + locatorValue);
        } catch (NoSuchElementException e) {
            LogUtils.error("Locator: " + locatorType + "=" + locatorValue + " not found to click | " + e.getMessage());
            ExtentReportManager.info("Locator: " + locatorType + "=" + locatorValue + " not found to click | " + e.getMessage());
            AllureManager.saveTextLog("Locator: " + locatorType + "=" + locatorValue + " not found to click | " + e.getMessage());
        }
    }

    @Step("Switch to new tab")
    public static void switchTo(String value) throws Exception {
        try {
            LogUtils.info("Switch to new tab");
            String currentWindow = driver.getWindowHandle();  //will keep current window to switch back
            for (String winHandle : driver.getWindowHandles()) {
                if (driver.switchTo().window(winHandle).getTitle().equals(value)) {
                    break;
                } else {
                    driver.switchTo().window(currentWindow);
                }
                waitForPageLoaded();
            }
            ExtentReportManager.pass("Switch to new tab");
        } catch (NoSuchElementException e) {
            LogUtils.error("Can't switch to new tab");
            ExtentReportManager.info("Can't switch to new tab");
            AllureManager.saveTextLog("Can't switch to new tab");
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

    @Step("Verify element displayed")
    public static void displayed(String locatorType, String locatorValue) {
        LogUtils.info("Verify element displayed");
        ExtentReportManager.info("Verify element displayed");
        WebElement element = getElement(locatorType, locatorValue);
        try {
            element.isDisplayed();
            LogUtils.info("Element is displayed");
            ExtentReportManager.pass("Element is displayed");
            //AllureManager.saveTextLog("Element is displayed");
        } catch (Exception e) {
            LogUtils.error("Element is not displayed");
            ExtentReportManager.info("Element is not displayed");
            AllureManager.saveTextLog("Element is not displayed");
        }
    }

    @Step("Get current URL")
    public boolean getURL(String url) {
        LogUtils.info("Get current URL");
        ExtentReportManager.pass("Get current URL");
        return driver.getCurrentUrl().contains(url);
    }

    @Step("Get the title of page")
    public String getPageTitle() {
        LogUtils.info("Get the title of page");
        ExtentReportManager.info("Get the title of page");
        waitForPageLoaded();
        return driver.getTitle();
    }

    @Step("Select option by text")
    public static void selectOptionByText(String locatorType, String locatorValue, String text) {
        try {
            LogUtils.info("Select option by text: " + text);
            ExtentReportManager.pass("Select option by text: " + text);
            AllureManager.saveTextLog("Select option by text: " + text);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            Select select = new Select(element);
            select.selectByVisibleText(text);
        } catch (NoSuchElementException e) {
            LogUtils.error("Locator: " + locatorType + " = " + locatorValue + " | text: " + text + " not found to select | " + e.getMessage());
            ExtentReportManager.info("Locator: " + locatorType + " = " + locatorValue + " | text: " + text + " not found to select | " + e.getMessage());
            AllureManager.saveTextLog("Locator: " + locatorType + " = " + locatorValue + " | text: " + text + " not found to select | " + e.getMessage());
        }
    }

    @Step("Select option by value")
    public static void selectOptionByValue(String locatorType, String locatorValue, String value) {
        try {
            LogUtils.info("Select option by value: " + value);
            ExtentReportManager.pass("Select option by value: " + value);
            AllureManager.saveTextLog("Select option by value: " + value);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            Select select = new Select(element);
            select.selectByValue(value);
        } catch (NoSuchElementException e) {
            LogUtils.error("Locator: " + locatorType + "= " + locatorValue + " | value: " + value + " not found to select | " + e.getMessage());
            ExtentReportManager.fail("Locator: " + locatorType + "= " + locatorValue + " | value: " + value + " not found to select | " + e.getMessage());
            AllureManager.saveTextLog("Locator: " + locatorType + "= " + locatorValue + " | value: " + value + " not found to select | " + e.getMessage());
        }
    }

    @Step("Select option by index")
    public static void selectOptionByIndex(String locatorType, String locatorValue, int index) {
        try {
            LogUtils.info("Select option by index: " + index);
            ExtentReportManager.pass("Select option by index: " + index);
            AllureManager.saveTextLog("Select option by index: " + index);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            Select select = new Select(element);
            select.selectByIndex(index);
        } catch (NoSuchElementException e) {
            LogUtils.error("Locator: " + locatorType + " = " + locatorValue + " | index: " + index + " not found to select | " + e.getMessage());
            ExtentReportManager.info("Locator: " + locatorType + " = " + locatorValue + " | index: " + index + " not found to select | " + e.getMessage());
            AllureManager.saveTextLog("Locator: " + locatorType + " = " + locatorValue + " | index: " + index + " not found to select | " + e.getMessage());
        }
    }

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

    public boolean verifyPageLoaded(String pageLoadedText) {
        waitForPageLoaded();
        Boolean res = false;
        List<WebElement> elementList = driver.findElements(By.xpath("//*contains(text(),'" + pageLoadedText + "')]"));
        if (elementList.size() > 0) {
            res = true;
            LogUtils.info("Page loaded(" + res + "): " + pageLoadedText);
            ExtentReportManager.info("Page loaded(" + res + "): " + pageLoadedText);
            AllureManager.saveTextLog("Page loaded(" + res + "): " + pageLoadedText);
        } else {
            res = false;
            LogUtils.info("Page loaded(" + res + "): " + pageLoadedText);
            ExtentReportManager.info("Page loaded(" + res + "): " + pageLoadedText);
            AllureManager.saveTextLog("Page loaded(" + res + "): " + pageLoadedText);
        }
        return res;
    }

    @Step("Verify Element Exist")
    public boolean verifyElementExist(By elementBy) {
        // Tạo list lưu tất cả các đối tượng WebElement
        LogUtils.info("Verify Element Exist");
        ExtentReportManager.info("Verify Element Exist");
        List<WebElement> listElement = driver.findElements(elementBy);
        int total = listElement.size();
        if (total > 0) {
            return true;
        }
        return false;
    }

    @Step("Verify the title of page")
    public boolean verifyPageTitle(String pageTitle) {
        LogUtils.info("Verify the title of page");
        ExtentReportManager.info("Verify the title of page");
        return getPageTitle().equals(pageTitle);
    }

    @Step("Verify URL")
    public static boolean verifyURL(String expected) throws InterruptedException {
        LogUtils.info("Verify URL");
        ExtentReportManager.info("Verify URL");
        waitForPageLoaded();
        String actual = driver.getCurrentUrl();
        LogUtils.info("Expected URL: " + expected);
        LogUtils.info("Actual URL: " + actual);
        AllureManager.saveTextLog("actual: " + actual);
        if (expected.equals(actual)) {
            statusVerify(true);
            return true;
        } else {
            statusVerify(false);
            return false;
        }
    }

    @Step("Verify the text of element")
    public boolean verifyElementText(WebElement element, String textValue) {
        LogUtils.info("Verify the text of element");
        ExtentReportManager.info("Verify the text of element");
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText().equals(textValue);
    }

    @Step("Verify the text of element")
    public static boolean verifyElementText(String locatorType, String locatorValue, String expected, FailureHandling flowControl) {
        LogUtils.info("Verify the text of element");
        ExtentReportManager.info("Verify the text of element");
        String actual;
        WebElement element = getElement(locatorType, locatorValue);
        waitForPageLoaded();
        actual = element.getText();
        LogUtils.info("Expected result: " + expected);
        LogUtils.info("Actual result: " + actual);
        AllureManager.saveTextLog("actual: " + actual);
        Boolean result = actual.equals(expected);

        if (flowControl.equals(FailureHandling.STOP_ON_FAILURE)) {
            Assert.assertEquals(actual, expected, "The actual text is [" + actual + "] not equals [" + expected + "]");
        }
        if (flowControl.equals(FailureHandling.CONTINUE_ON_FAILURE)) {
//            softAssert.assertEquals(actual, expected, "The actual text is [" + actual + "] not equals [" + expected + "]");
            if (result == false) {
                ExtentReportManager.fail("The actual text is [" + actual + "] not equals [" + expected + "]");
            }
        }
        if (flowControl.equals(FailureHandling.OPTIONAL)) {
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.warning("Verify text of an element [Equals] - " + result.toString().toUpperCase());
                ExtentReportManager.warning("The actual text is [" + actual + "] not equals [" + expected + "]");
            }
            AllureManager.saveTextLog("Verify text of an element [Equals] - " + result.toString().toUpperCase() + ". The actual text is [" + actual + "] not equals [" + expected + "]");
        }
        if (result) {
            statusVerify(true);
            return true;
        } else {
            statusVerify(false);
            return false;
        }
    }

    @Step("Verify text")
    public static boolean verifyText(String locatorType, String locatorValue, String expected, FailureHandling flowControl) throws InterruptedException {
        LogUtils.info("Verify Text");
        ExtentReportManager.info("Verify Text");
        WebElement element = getElement(locatorType, locatorValue);
        waitForPageLoaded();
        String actual = element.getText();
        LogUtils.info("Expected result: " + expected);
        LogUtils.info("Actual result: " + actual);
        AllureManager.saveTextLog("actual: " + actual);
        Boolean result = actual.contains(expected);

        if (flowControl.equals(FailureHandling.STOP_ON_FAILURE)) {
            Assert.assertEquals(actual, expected, "The actual text is [" + actual + "] not equals [" + expected + "]");
        }
        if (flowControl.equals(FailureHandling.CONTINUE_ON_FAILURE)) {
            softAssert.assertEquals(actual, expected, "The actual text is [" + actual + "] not equals [" + expected + "]");
            if (result == false) {
                ExtentReportManager.fail("The actual text is [" + actual + "] not equals [" + expected + "]");
            }
        }
        if (flowControl.equals(FailureHandling.OPTIONAL)) {
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.warning("Verify text of an element [Equals] - " + result.toString().toUpperCase());
                ExtentReportManager.warning("The actual text is [" + actual + "] not equals [" + expected + "]");
            }
            AllureManager.saveTextLog("Verify text of an element [Equals] - " + result.toString().toUpperCase() + ". The actual text is [" + actual + "] not equals [" + expected + "]");
        }
        if (result) {
            statusVerify(true);
            return true;
        } else {
            statusVerify(false);
            return false;
        }
    }

    @Step("Verify title")
    public static boolean verifyTitle(String expected, FailureHandling flowControl) {
        LogUtils.info("Verify Title");
        ExtentReportManager.info("Verify Title");
        String actual = driver.getTitle();
        waitForPageLoaded();
        LogUtils.info("Expected result: " + expected);
        LogUtils.info("Actual result: " + actual);
        AllureManager.saveTextLog("actual: " + actual);
        Boolean result = actual.equals(expected);

        if (flowControl.equals(FailureHandling.STOP_ON_FAILURE)) {
            Assert.assertEquals(actual, expected, "The actual text is [" + actual + "] not equals [" + expected + "]");
        }
        if (flowControl.equals(FailureHandling.CONTINUE_ON_FAILURE)) {
            softAssert.assertEquals(actual, expected, "The actual text is [" + actual + "] not equals [" + expected + "]");
            if (result == false) {
                ExtentReportManager.fail("The actual text is [" + actual + "] not equals [" + expected + "]");
            }
        }
        if (flowControl.equals(FailureHandling.OPTIONAL)) {
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.warning("Verify text of an element [Equals] - " + result.toString().toUpperCase());
                ExtentReportManager.warning("The actual text is [" + actual + "] not equals [" + expected + "]");
            }
            AllureManager.saveTextLog("Verify text of an element [Equals] - " + result.toString().toUpperCase() + ". The actual text is [" + actual + "] not equals [" + expected + "]");
        }
        if (result) {
            statusVerify(true);
            return true;
        } else {
            statusVerify(false);
            return false;
        }
    }

    @Step("Verify the result")
    public static boolean verifyResults(String expected, FailureHandling flowControl) {
        LogUtils.info("Verify the result");
        ExtentReportManager.info("Verify the result");
        String actual = "";
        waitForPageLoaded();
        if (driver.getCurrentUrl().contains("login")) {
            WebElement emailInSignInPage = getElement("name", "_username");
            WebElement pwInSignInPage = getElement("name", "_password");
            WebElement errorMessageInSignInPage = getElement("xpath", "//form[@id='login_form']/div");
            if (errorMessageInSignInPage.getText().equals("")) {
                actual = (emailInSignInPage.getAttribute("validationMessage").equals("") ?
                        pwInSignInPage.getAttribute("validationMessage") :
                        emailInSignInPage.getAttribute("validationMessage"));
            } else {
                actual = errorMessageInSignInPage.getText();
            }
        }

        if (driver.getCurrentUrl().contains("dang-ky")) {
            WebElement nameInSignUpPage = getElement("id","full_name");
            WebElement emailInSignUpPage = getElement("id", "email");//driver.findElement(By.id("email"));
            WebElement pwInSignUpPage = getElement("id", "password");
            WebElement pwConfirmInSignInPage = getElement("id", "password_confirmation");
            WebElement errorMessageInSignUpPage = driver.findElement(By.id("invalid_password_feedback"));
            if (errorMessageInSignUpPage.getText().equals("")) {
                actual = (nameInSignUpPage.getAttribute("validationMessage").equals("")?
                        ((emailInSignUpPage.getAttribute("validationMessage").equals("") ?
                        (pwInSignUpPage.getAttribute("validationMessage").equals("") ?
                                pwConfirmInSignInPage.getAttribute("validationMessage") :
                                pwInSignUpPage.getAttribute("validationMessage")) :
                                emailInSignUpPage.getAttribute("validationMessage"))):
                                nameInSignUpPage.getAttribute("validationMessage"));

            } else {
                actual = errorMessageInSignUpPage.getText();
            }
        }

        if (driver.getCurrentUrl().equals("https://www.careerlink.vn/nguoi-tim-viec")) {
            WebElement successSignIn = driver.findElement(By.xpath("//span[contains(text(),'Nguyễn Hoàng Anh')]"));
            actual = successSignIn.getText();
        }

        LogUtils.info("Expected result: " + expected);
        LogUtils.info("Actual result: " + actual);
        AllureManager.saveTextLog("actual: " + actual);

        Boolean result = actual.equals(expected);

        if (flowControl.equals(FailureHandling.STOP_ON_FAILURE)) {
            Assert.assertEquals(actual, expected, "The actual text is [" + actual + "] not equals [" + expected + "]");
        }
        if (flowControl.equals(FailureHandling.CONTINUE_ON_FAILURE)) {
//            softAssert.assertTrue(result, "The actual text is [" + actual + "] not equals [" + expected + "]");
            if(!result) {
//                Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
                ExtentReportManager.fail("The actual text is [" + actual + "] not equals [" + expected + "]");
            }
        }
        if (flowControl.equals(FailureHandling.OPTIONAL)) {
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.warning("Verify text of an element [Equals] - " + result.toString().toUpperCase());
                ExtentReportManager.warning("The actual text is [" + actual + "] not equals [" + expected + "]");
            }
            AllureManager.saveTextLog("Verify text of an element [Equals] - " + result.toString().toUpperCase() + ". The actual text is [" + actual + "] not equals [" + expected + "]");
        }
        if (result) {
            statusVerify(true);
            return true;
        } else {
            statusVerify(false);
            return false;
        }
    }

    private static void statusVerify(Boolean stt) {
        if (stt) {
            ExtentReportManager.pass("Actual result is the same as expected result");
            AllureManager.saveTextLog("Actual result is the same as expected result");
        } else {
            //ExtentReportManager.fail("Actual result is different from expected result");
            //AllureManager.saveTextLog("Actual result is different from expected result");
            ActionKeywords.addScreenshotToReport(DateUtils.getCurrentDate());
            AllureManager.takeScreenshotStep(ActionKeywords.screenShot(DateUtils.getCurrentDate()));
            Allure.getLifecycle().updateStep(stepResult -> stepResult.setStatus(io.qameta.allure.model.Status.FAILED));
            Allure.getLifecycle().stopStep();
//            Allure.getLifecycle().updateTestCase(t->t.setStatus(io.qameta.allure.model.Status.FAILED));
        }
    }

    //upload image
    @Step("Upload image")
    public static void uploadImage(String path) {
        try {
            LogUtils.info("Upload image: " + path);
            ExtentReportManager.pass("Upload image: " + path);
            waitForPageLoaded();

            Robot rb = null;
            try {
                rb = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            StringSelection str = new StringSelection(path);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str,null);
            Thread.sleep(2000);

            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);

            rb.keyRelease(KeyEvent.VK_CONTROL);
            rb.keyRelease(KeyEvent.VK_V);

            Thread.sleep(2000);

            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER);

            Thread.sleep(2000);

        } catch (NoSuchElementException | InterruptedException e) {
            LogUtils.error("Can not found image to upload | " + e.getMessage());
            ExtentReportManager.info("Can not found image to upload | " + e.getMessage());
            AllureManager.saveTextLog("Can not found image to upload | " + e.getMessage());
        }
    }

    //    @Step("Take a screenshot")
    public static String screenShot(String CaseName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String path = Helpers.getCurrentDir() + FrwConstants.EXPORT_CAPTURE_PATH;
            File theDir = new File(path);
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            String imgPath = theDir + "\\" + CaseName + ".png";
            FileHandler.copy(source, new File(imgPath));
            LogUtils.info("Take a screenshot: " + imgPath);
            return imgPath;
        } catch (Exception e) {
            LogUtils.error("Can't take a screenshot: " + CaseName);
            ExtentReportManager.fail("Can't take a screenshot: " + CaseName);
            AllureManager.saveTextLog("Can't take a screenshot: " + CaseName);
        }
        return "";
    }

    public static void addScreenshotToReport(String screenshotName) {
        ExtentReportManager.addScreenShot(ActionKeywords.screenShot(screenshotName));
    }

    public static void stopSoftAssertAll() {
        //softAssert.asserFtAll();
    }
}
