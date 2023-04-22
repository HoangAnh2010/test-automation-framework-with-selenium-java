package keyword;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import utils.Log;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.function.Function;

public class ActionKeywords {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Actions action;
    private static JavascriptExecutor js;
    private static final int timeoutWait = 10;
    private static final int timeoutWaitForPageLoaded = 20;


    public static Properties OR = new Properties(System.getProperties());

    private static WebElement getElement(String locatorType, String locatorValue) {
        WebElement element = null;

        if (locatorType.equalsIgnoreCase("className"))
            element = driver.findElement(By.className(locatorValue));
        else if (locatorType.equalsIgnoreCase("cssSelector"))
            element = driver.findElement(By.cssSelector(locatorValue));
        else if (locatorType.equalsIgnoreCase("id"))
            element = driver.findElement(By.id(locatorValue));
        else if (locatorType.equalsIgnoreCase("partialLinkText"))
            element = driver.findElement(By.partialLinkText(locatorValue));
        else if (locatorType.equalsIgnoreCase("name"))
            element = driver.findElement(By.name(locatorValue));
        else if (locatorType.equalsIgnoreCase("xpath"))
            element = driver.findElement(By.xpath(locatorValue));
        else if (locatorType.equalsIgnoreCase("tagName"))
            element = driver.findElement(By.tagName(locatorValue));
        else {
            Log.error("GetElement " + locatorType + "=" + locatorValue);
        }
        return element;
    }

    //open browser
    private static WebDriver initChromeDriver() {
        try {
            Log.info("Executing: Open browser Chrome");
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
//			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
//			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.error("Executing: Open browser Chrome fail");
        }

        return driver;
    }

    private static WebDriver initFirefoxDriver() {
        try {
            Log.info("Executing: Open browser Firefox");
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.error("Executing: Open browser Firefox fail");
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
                Log.info("Browser: " + browserType + " is invalid, Launching Chrome as browser of choice...");
                driver = initChromeDriver();
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutWait));
        return driver;
    }

    //close browser
    public static void closeBrowser() {
        try {
            Log.info("Executing: Closing...");
            driver.manage().deleteAllCookies();
            driver.quit();
        } catch (Exception e) {
            Log.error("Executing: Close FAIL");
        }
    }

    //navigate to url
    public static void navigate(String url) {
        try {
            Log.info("Executing: Opening Url: " + url);
            driver.navigate().to(url);
            waitForPageLoaded();
        } catch (Exception e) {
            Log.error("Executing: Open Url:" + url + "FAIL");
        }
    }

    //refresh page
    public static void refresh() throws InterruptedException {
        Log.info("Executing: Refreshing website");
        driver.navigate().refresh();
        Thread.sleep(3000);
    }

    //clear
    public static void clear(String locatorType, String locatorValue) throws InterruptedException {
        Log.info("Executing: Clearing the element");
        WebElement element = getElement(locatorType, locatorValue);
        element.clear();
    }

    public void setText(WebElement element, String value) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.clear();
        element.sendKeys(value);

    }

    //set text
    public static void setText(String locatorType, String locatorValue, String value) {
        try {
            Log.info("Executing: Enter text: " + value);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(value);
        } catch (NoSuchElementException e) {

            Log.error("SendKeys:" + locatorType + "=" + locatorValue
                    + " not found to sendKeys| " + e.getMessage());
        }

    }

    //move to element
    public static void elementPerform(String address) {

        try {
            if (address.contains("[")) {
                Log.info("Executing: Moving mouse");
                Actions a = new Actions(driver);
                waitForPageLoaded();
                a.moveToElement(driver.findElement(By.xpath(address))).
                        build().perform();

            } else {
                Actions a = new Actions(driver);
                a.moveToElement(driver.findElement(By.id(address))).build().perform();
            }
        } catch (Exception e) {
            Log.error("Move mouse: FAIL");
        }
    }

    public void clickElement(WebElement element) {

        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    //click
    public static void clickButton(String locatorType, String locatorValue) throws Exception {
        try {
            Log.info("Executing: Click button: " + locatorType + "= " + locatorValue);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            element.click();
        } catch (NoSuchElementException e) {
            Log.error("Click:" + locatorValue + " not found to click " + e.getMessage());
        }
        Thread.sleep(1500);
    }

    public static void doubleClick(String locatorType, String locatorValue) throws Exception {
        try {
            Log.info("Executing: Double click: " + locatorType + "= " + locatorValue);
            Actions action = new Actions(driver);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            action.doubleClick(element).perform();
        } catch (NoSuchElementException e) {
            Log.error("Double click:" + locatorValue + " not found to double click " + e.getMessage());
        }
        Thread.sleep(1500);
    }

    public static void switchTo(String value) throws Exception {
        try {
            Log.info("Executing: Switch to new tab");
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
        } catch (NoSuchElementException e) {
            Log.error("Double click: Switch to new tab fail");
        }
        //Thread.sleep(1500);
    }


    public static void clickElement(String locatorType, String locatorValue) throws Exception {
        try {
            Log.info("Executing: Click element: " + locatorType + "= " + locatorValue);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            //Thread.sleep(4000);
            element.click();
        } catch (NoSuchElementException e) {
            Log.error("Click:" + locatorValue + " not found to click " + e.getMessage());
        }
        Thread.sleep(1500);
    }

    public static void clickElementWithJs(String locatorType, String locatorValue) {
        js = (JavascriptExecutor) driver; // khởi tạo
        try {
            Log.info("Executing: Scroll mouse down and click element: " + locatorValue);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            js.executeScript("arguments[0].scrollIntoView(true)", element);
            //js.executeScript("arguments[0].click();", element);
        } catch (NoSuchElementException e) {
            Log.error("|clickElementWithJs:" + locatorType + "=" + locatorValue
                    + " not found to click| " + e.getMessage());
        }
    }

    public static void waitForPageLoaded() {
        try {
            wait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                            .equals("complete");
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
            System.out.println("Page loaded(" + res + "): " + pageLoadedText);
        } else {
            res = false;
            System.out.println("Page loaded(" + res + "): " + pageLoadedText);
        }
        return res;
    }

    //displayed
    public static void displayed(String locatorType, String locatorValue) {
        Log.info("Executing: Verify element displayed");
        WebElement element = getElement(locatorType, locatorValue);
        try {
            element.isDisplayed();
            Log.info("Result: Element is displayed");
        } catch (Exception e) {
            Log.error("Element is not displayed");
        }
    }

    public boolean verifyUrl(String url) {
        return driver.getCurrentUrl().contains(url);
    }

    //get URL current
    public static boolean getUrl(String value) throws InterruptedException {
        Log.info("Executing: |Get Url|");
        waitForPageLoaded();
        String ResultUrl = driver.getCurrentUrl();

        Log.info("Expected Url -->" + value);
        Log.info("Actual Url -->" + ResultUrl);
        if (value.equals(ResultUrl)) {
            return true;
        } else return false;
    }

    public String getPageTitle() {
        waitForPageLoaded();
        return driver.getTitle();
    }

    public boolean verifyPageTitle(String pageTitle) {
        return getPageTitle().equals(pageTitle);
    }

    //right click
    public void rightClickElement(String locatorType, String locatorValue) {

        try {
            Log.info("Executing: Right click element: " + locatorType + "= " + locatorValue);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            wait.until(ExpectedConditions.elementToBeClickable(element));
            action.contextClick().build().perform();
        } catch (NoSuchElementException e) {
            Log.error("|Right click: " + locatorType + "= " + locatorValue
                    + " not found to click| " + e.getMessage());
        }
    }

    //select data from dropdown
    public static void selectOptionByText(String locatorType, String locatorValue, String text) {
        try {
            Log.info("Executing: Selection option: " + text);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            Select select = new Select(element);
            select.selectByVisibleText(text);
        } catch (NoSuchElementException e) {
            Log.error("|select Option By Text|: " + locatorType + "= " + locatorValue + "|text: "
                    + text + " not found to select| " + e.getMessage());
        }
    }

    public static void selectOptionByValue(String locatorType, String locatorValue, String value) {
        try {
            Log.info("Executing: Selection option: " + value);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            Select select = new Select(element);
            select.selectByValue(value);
        } catch (NoSuchElementException e) {
            Log.error("Select Option By Value: " + locatorType + "= " + locatorValue + "|value: "
                    + value + " not found to select| " + e.getMessage());
        }
    }

    public static void selectOptionByIndex(String locatorType, String locatorValue, int index) {

        try {
            Log.info("Executing: Selection option have index is: " + index);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            Select select = new Select(element);
            select.selectByIndex(index);
        } catch (NoSuchElementException e) {
            Log.error("Select Option By Index: " + locatorType + "= " + locatorValue + "|index: "
                    + index + " not found to select| " + e.getMessage());
        }
    }

    public boolean verifyElementText(WebElement element, String textValue) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText().equals(textValue);
    }

    public static boolean verifyElementText(String locatorType, String locatorValue, String value) {
        Log.info("Executing: Verify Element Text");
        String result;
        WebElement element = getElement(locatorType, locatorValue);
        waitForPageLoaded();
        result = element.getText();
        Log.info("Title expected: " + value);
        Log.info("Title actual: " + result);
        if (value.equals(result)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyElementExist(By elementBy) {
        // Tạo list lưu tất cả các đối tượng WebElement
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
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
                        .equals("complete");
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
        Log.info("Executing: Verify Text");
        String result;
        WebElement element = getElement(locatorType, locatorValue);
        waitForPageLoaded();
        result = element.getText();
        Log.info("Title expected: " + value);
        Log.info("Title actual: " + result);
        if (result.contains(value)) {
            return true;
        } else return false;
    }

    public static boolean verifyTitle(String value) {
        Log.info("Executing: Verify Title");
        String actual = driver.getTitle();
        waitForPageLoaded();
        Log.info("Title expected: " + value);
        Log.info("Title actual: " + actual);
        if (actual.contains(value)) {
            return true;
        } else return false;
    }

    public static boolean verifyTextInSignIn(String value) throws InterruptedException {
        Log.info("Executing: |Verify the result when successfully signed in|");
        WebElement result = driver.findElement(By.xpath("//span[contains(text(),'Nguyễn Hoàng Anh')]"));
        String expect = result.getText();
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + result.getText());
        if (value.equals(expect)) {
            return true;
        } else return false;
    }

    public static boolean verifyAlertInSignIn(String value) throws InterruptedException {
        Log.info("Executing: |Verify the alert of email|");
        WebElement email = driver.findElement(By.xpath("//form[@id='login_form']/div"));
        String expect = email.getText();
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + email.getText());
        if (value.equals(expect)) {
            return true;
        } else return false;
    }
    public static boolean verifyResults(String expected) throws InterruptedException {
        Log.info("Executing: | Verify the result");
        Boolean stt = false;
        String actual;
        if (driver.getCurrentUrl().contains("login")) {
            WebElement emailInSignInPage = getElement("name", "_username");//driver.findElement(By.name("_username"));
            WebElement pwInSignInPage = getElement("name", "_password");
            WebElement errorMessageInSignInPage = getElement("xpath", "//form[@id='login_form']/div");
            if (emailInSignInPage.getText().trim() == "" || emailInSignInPage.getText() == null || emailInSignInPage.getText().trim().contains(" ") == true
                    || emailInSignInPage.getText().trim().contains("@") == false) {
                actual = emailInSignInPage.getAttribute("validationMessage");
                waitForPageLoaded();
                Log.info("Expected Result: " + expected);
                Log.info("Actual Result: " + actual);
                if (expected.equals(actual)) {
                    stt = true;
                }
            } else {
                if (pwInSignInPage.getText() == null) {
                    actual = pwInSignInPage.getAttribute("validationMessage");
                    waitForPageLoaded();
                    Log.info("Expected Result: " + expected);
                    Log.info("Actual Result: " + actual);
                    if (expected.equals(actual)) {
                        stt = true;
                    }
                } else {
                    actual = errorMessageInSignInPage.getText();
                    waitForPageLoaded();
                    Log.info("Expected Result: " + expected);
                    Log.info("Actual Result: " + actual);
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
            if (emailInSignUpPage.getText().trim().contains("@") == false || emailInSignUpPage.getText().trim().contains(" ") == true ||
                    emailInSignUpPage.getText().trim() == "" || emailInSignUpPage.getText() == null) {
                actual = emailInSignUpPage.getAttribute("validationMessage");
                waitForPageLoaded();
                Log.info("Expected Result: " + expected);
                Log.info("Actual Result: " + actual);
                if (expected.equals(actual)) {
                    stt = true;
                }
            } else {
                if (pwInSignUpPage.getText() == null) {
                    actual = pwInSignUpPage.getAttribute("validationMessage");
                    waitForPageLoaded();
                    Log.info("Expected Result: " + expected);
                    Log.info("Actual Result: " + actual);
                    if (expected.equals(actual)) {
                        stt = true;
                    }
                } else {
                    if (pwConfirmInSignInPage.getText() == null) {
                        actual = pwConfirmInSignInPage.getAttribute("validationMessage");
                        waitForPageLoaded();
                        Log.info("Expected Result: " + expected);
                        Log.info("Actual Result: " + actual);
                        if (expected.equals(actual)) {
                            stt = true;
                        }
                    } else {
                        actual = errorMessageInSignUpPage.getText();
                        waitForPageLoaded();
                        Log.info("Expected Result: " + expected);
                        Log.info("Actual Result: " + actual);
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
            Log.info("Expected Result: " + expected);
            Log.info("Actual Result: " + actual);
            if (expected.equals(actual)) {
                stt = true;
            }
        }
        return stt;
    }

    public static boolean verifyAlertOfEmailinSignInPagehtml5(String locatorType, String locatorValue, String value) throws InterruptedException {
        Log.info("Executing: |Verify the alert of email in sign in page|");
        WebElement email = getElement(locatorType, locatorValue);//driver.findElement(By.name("_username"));
        String expect = email.getAttribute("validationMessage");
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + email.getAttribute("validationMessage"));
        if (value.equals(expect)) {
            return true;
        } else return false;
    }

    public static boolean verifyAlertOfPasswordinSignInPagehtml5(String value) throws InterruptedException {
        Log.info("Executing: |Verify the alert of password in sign in page|");
        WebElement password = driver.findElement(By.name("_password"));
        String expect = password.getAttribute("validationMessage");
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + password.getAttribute("validationMessage"));
        if (value.equals(expect)) {
            return true;
        } else return false;
    }

    public static boolean verifyAlertNameSignUp(String value) throws InterruptedException {
        Log.info("Executing: Verify the alert of name in sign up page");
        WebElement name = driver.findElement(By.id("full_name"));
        String expect = name.getAttribute("validationMessage");
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + name.getAttribute("validationMessage"));
        if (value.equals(expect)) {
            return true;
        } else return false;
    }

    public static boolean verifyAlertOfEmailinSignUpPagehtml5(String value) throws InterruptedException {
        Log.info("Executing: Verify the alert of email in sign up page");
        WebElement email = driver.findElement(By.id("email"));
        String expect = email.getAttribute("validationMessage");
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + email.getAttribute("validationMessage"));
        if (value.equals(expect)) {
            return true;
        } else return false;
    }

    public static boolean verifyAlertOfPasswordinSignUpPagehtml5(String value) throws InterruptedException {
        Log.info("Executing: |Verify the alert of password in sign in page|");
        WebElement password = driver.findElement(By.id("password"));
        String expect = password.getAttribute("validationMessage");
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + password.getAttribute("validationMessage"));
        if (value.equals(expect)) {
            return true;
        } else return false;
    }

    public static boolean verifyAlertOfPasswordConfinSignUpPagehtml5(String value) throws InterruptedException {
        Log.info("Executing: |Verify the alert of password confirm in sign in page|");
        WebElement passwordcf = driver.findElement(By.id("password_confirmation"));
        String expect = passwordcf.getAttribute("validationMessage");
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + passwordcf.getAttribute("validationMessage"));
        if (value.equals(expect)) {
            return true;
        } else return false;
    }

    public static boolean verifyAlertOfEmailinSignUpPage(String value) throws InterruptedException {
        Log.info("Executing: |Verify the alert of email in sign up|");
        WebElement email = driver.findElement(By.xpath("//span[@data-message='invalid']"));
        String expect = email.getText();
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + email.getText());
        if (value.equals(expect)) {
            return true;
        } else return false;
    }

    public static boolean verifyAlertOfPasswordConfinSignUpPage(String value) throws InterruptedException {
        Log.info("Executing: |Verify the alert of password confirm in sign in page|");
        WebElement passwordcf = driver.findElement(By.id("invalid_password_feedback"));
        String expect = passwordcf.getText();
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + passwordcf.getText());
        if (value.equals(expect)) {
            return true;
        } else return false;
    }

    //upload image
    public static void uploadImage(String locatorType, String locatorValue, String value) {
        try {
            Log.info("Executing: Upload image: " + value);
            WebElement element = getElement(locatorType, locatorValue);
            waitForPageLoaded();
            wait.until(ExpectedConditions.visibilityOf(element));
            //element.clear();
            element.sendKeys(value);
        } catch (NoSuchElementException e) {
            Log.error("SendKeys:" + locatorType + "=" + locatorValue
                    + " not found to sendKeys| " + e.getMessage());
        }
    }

    public static void screenShot(String CaseName) throws IOException {
        try {
            // Tạo tham chiếu của TakesScreenshot với driver hiện tại
            TakesScreenshot ts = (TakesScreenshot) driver;
            // Gọi hàm capture screenshot - getScreenshotAs
            File source = ts.getScreenshotAs(OutputType.FILE);
            //Kiểm tra folder tồn tại. Nêu không thì tạo mới folder
            File theDir = new File("./Screenshots/");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            // result.getName() lấy tên của test case xong gán cho tên File chụp màn hình luôn
            FileHandler.copy(source, new File("./Screenshots/" + CaseName + ".png"));
            Log.info("Executing: Screenshot taken: " + CaseName);
        } catch (Exception e) {
            Log.error("Executing: Screenshot taken: " + CaseName + "FAIL");
        }
    }
}
