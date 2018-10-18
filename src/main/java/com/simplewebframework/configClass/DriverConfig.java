package com.simplewebframework.configClass;
import org.openqa.selenium.WebDriver;

import com.simplewebframework.core.BrowserType;

public class DriverConfig {

    private WebDriver driver;
    private BrowserType browser = BrowserType.Chrome;
   
    public BrowserType getBrowser() {
        return browser;
    }
    
    public WebDriver getDriver() {
        return driver;
    }

    public int getExplicitWaitTimeout() {
        if (AppConfig.EXPLICIT_WAIT_TIME_OUT < AppConfig.IMPLICIT_WAIT_TIMEOUT) {
            return AppConfig.IMPLICIT_WAIT_TIMEOUT;
        } else {
            return AppConfig.EXPLICIT_WAIT_TIME_OUT;
        }
    }
 
    public void setBrowser(final BrowserType browser) {
        this.browser = browser;
    }
  
    public void setBrowser(final String sbrowser) {
    	browser.setBrowserType(sbrowser);
    }
      
    public void setDriver(final WebDriver driver) {
        this.driver = driver;
    }

 	public static void main(String[] args) {
 		//DriverConfig dc = new DriverConfig();
 		AppConfig con = new AppConfig("config.properties");
 		System.out.println("The explicit timeout is " + AppConfig.DRIVER_BIN_PATH);
	}

}
