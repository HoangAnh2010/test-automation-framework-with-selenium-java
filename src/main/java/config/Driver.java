package config;

import org.openqa.selenium.WebDriver;

public class Driver {

	 private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	    private Driver() {
	    }

	    public static WebDriver getDriver() {
	        return driver.get();
	    }

	    public static void setDriver(WebDriver driver) {
	    	Driver.driver.set(driver);
	    }

	    public static void quit() {
	    	Driver.driver.get().quit();
	        driver.remove();
	    }
	    
}
