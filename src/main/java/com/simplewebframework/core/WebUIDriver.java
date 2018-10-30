/*
 * Copyright 2015 www.seleniumtests.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simplewebframework.core;

import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.simplewebframework.browserfactory.ChromeDriverFactory;
import com.simplewebframework.configClass.AppConfig;

/**
 * This class provides factory to create webDriver session.
 */
public class WebUIDriver {

    private static ThreadLocal<WebDriver> driverSession = new ThreadLocal<WebDriver>();
    private static ThreadLocal<BrowserType> browser = new ThreadLocal<BrowserType>();
    private static WebDriver driver = null;
    
    public WebUIDriver(String browserStrVal) throws Exception {
    	driver = createDriver(browserStrVal);
    	//driverSession.set(createDriver(browserStrVal));
    }

    public static WebDriver createDriver(String browserStrVal) throws Exception {
		if (driverSession.get() == null) {
			//browser.get().setBrowserStrVal(browserStrVal);
			TestLogger.logConsole(Thread.currentThread() + ":" + new Date()	+ ":::Start creating web driver for browser: " + browserStrVal);

			if (browserStrVal.equalsIgnoreCase("Chrome")) {
				ChromeDriverFactory cdf = new ChromeDriverFactory();
				driver = cdf.createWebDriver();
				BrowserType bt = BrowserType.Chrome;
				browser.set(bt);
				driverSession.set(driver);
			} else {
				throw new RuntimeException("Unsupported browser: " + browser);
			}
			TestLogger.logConsole(Thread.currentThread() + ":" + new Date()	+ ":::Finish creating web driver for browser: " + browserStrVal);
		}
        return driverSession.get();
    }   
    
    public void setDriver(final WebDriver driver) {
    	driverSession.set(driver);
    }    
 
    public static WebDriver getWebDriver() {
          return driverSession.get();
    }
    
    public static WebDriver getWebDriver(String browserStrVal) throws Exception {
    	return createDriver(browserStrVal);
    }
    
/*    public WebUIDriver(final String browser) {
        this.setBrowser(browser);
        //uxDriverSession.set(this);
    }*/

    public static BrowserType getBrowserType() {
        return browser.get();
    }
    

 
/*    public void setBrowser(final BrowserType browser) {
        this.browser = browser;
    }
  
    public void setBrowserStrVal(String sbrowser) {
    	browser.setBrowserStrVal(sbrowser);
    }*/
      

    
/*    public WebDriver createWebDriver() throws Exception {
    	TestLogger.logConsole(Thread.currentThread() + ":" + new Date() + ":::Start creating web driver from config instance: " + this.getBrowser());
        driver = createDriver(config.getBrowser().getBrowserType());
        driverSession.set(driver);
    	TestLogger.logConsole(Thread.currentThread() + ":" + new Date() + ":::Finish creating web driver from config instance: " + this.getBrowser());
        return driver;
    }*/

/*    public WebDriver createWebDriver(String browser) throws Exception {
    	TestLogger.logConsole(Thread.currentThread() + ":" + new Date() + ":::Start creating web driver for browser: " + this.getBrowser());
        driver = createDriver(browser);
        driverSession.set(driver);
    	TestLogger.logConsole(Thread.currentThread() + ":" + new Date() + ":::Finish creating web driver for browser: " + this.getBrowser());
        return driver;
    }   */


    
    /**
     * Get EventFiringWebDriver.
     *
     * @return webDriver
     */
/*    public static WebDriver getWebDriver() {
        return getWebDriver(false);
    }*/
    


    /**
     * Returns WebUIDriver instance Creates new WebUIDriver instance if it is null.
     *
     * @return
     */
/*    public static WebUIDriver getWebUIDriver() {
        if (uxDriverSession.get() == null) {
            uxDriverSession.set(new WebUIDriver());
        }

        return uxDriverSession.get();
    }*/


/*    public String getBrowser() {
        return config.getBrowser().getBrowserType();
    }*/

   
/*    public static DriverConfig getConfig() {
        return config;
    }*/


  
/*    public IWebDriverFactory getWebDriverBuilder() {
        return webDriverBuilder;
    }*/

  
/*    public void setBrowser(final String browser) {
        config.setBrowser(BrowserType.getBrowserType(browser));
    }

    public void setConfig(final DriverConfig config) {
        this.config = config;
    }*/


/*    public void setWebDriverBuilder(final IWebDriverFactory builder) {
        this.webDriverBuilder = builder;
    }*/

 
    public static void cleanUp() {
    	WebDriver driver = driverSession.get();
    	if (driver != null) {
            try {
                driver.quit();
            } catch (WebDriverException ex) {
                ex.printStackTrace();
            }

            driver = null;
        }
        driverSession.remove();
        browser.remove();
    }
    
    public int getExplicitWaitTimeout() {
        if (AppConfig.EXPLICIT_WAIT_TIME_OUT < AppConfig.IMPLICIT_WAIT_TIMEOUT) {
            return AppConfig.IMPLICIT_WAIT_TIMEOUT;
        } else {
            return AppConfig.EXPLICIT_WAIT_TIME_OUT;
        }
    }
}
