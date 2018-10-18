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

import java.net.URISyntaxException;
import java.util.Date;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import com.simplewebframework.browserfactory.ChromeDriverFactory;
import com.simplewebframework.browserfactory.IWebDriverFactory;
import com.simplewebframework.configClass.DriverConfig;

/**
 * This class provides factory to create webDriver session.
 */
public class WebUIDriver {

    private static ThreadLocal<WebDriver> driverSession = new ThreadLocal<WebDriver>();
    private static ThreadLocal<WebUIDriver> uxDriverSession = new ThreadLocal<WebUIDriver>();
    private IWebDriverFactory webDriverBuilder;
    private WebDriver driver;    
    private static DriverConfig config = new DriverConfig();
    
    public WebUIDriver() {
        uxDriverSession.set(this);
    }

    public WebUIDriver(final String browser) {
        this.setBrowser(browser);
        uxDriverSession.set(this);
    }

    public WebDriver createWebDriver() throws Exception {
    	TestLogger.logConsole(Thread.currentThread() + ":" + new Date() + ":::Start creating web driver from config instance: " + this.getBrowser());
        driver = createDriver(config.getBrowser().getBrowserType());
        driverSession.set(driver);
    	TestLogger.logConsole(Thread.currentThread() + ":" + new Date() + ":::Finish creating web driver from config instance: " + this.getBrowser());
        return driver;
    }

    public WebDriver createWebDriver(String browser) throws Exception {
    	TestLogger.logConsole(Thread.currentThread() + ":" + new Date() + ":::Start creating web driver for browser: " + this.getBrowser());
        driver = createDriver(browser);
        driverSession.set(driver);
    	TestLogger.logConsole(Thread.currentThread() + ":" + new Date() + ":::Finish creating web driver for browser: " + this.getBrowser());
        return driver;
    }   

    public WebDriver createDriver(final String browser) throws Exception {
        WebDriver driver = null;
        config.setBrowser(BrowserType.getBrowserType(browser));
        
        if (config.getBrowser() == BrowserType.Chrome) {
			webDriverBuilder = new ChromeDriverFactory(config);
		}
        else {
			throw new RuntimeException("Unsupported browser: " + browser);
		}
        
        synchronized (this.getClass()) {
            driver = webDriverBuilder.createWebDriver();
            driverSession.set(driver);
        }
        return driver;
    }   
    
    /**
     * Get EventFiringWebDriver.
     *
     * @return webDriver
     */
    public static WebDriver getWebDriver() {
        return getWebDriver(false);
    }

    /**
     * Returns WebDriver instance. Creates a new WebDriver Instance if it is null and isCreate is true.
     *
     * @param isCreate create webdriver or not
     * @return
     */
    public static WebDriver getWebDriver(final Boolean isCreate) {
        if (driverSession.get() == null && isCreate) {
            try {
                getWebUIDriver().createWebDriver();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return driverSession.get();
    }

    /**
     * Returns WebUIDriver instance Creates new WebUIDriver instance if it is null.
     *
     * @return
     */
    public static WebUIDriver getWebUIDriver() {
        if (uxDriverSession.get() == null) {
            uxDriverSession.set(new WebUIDriver());
        }

        return uxDriverSession.get();
    }


    public String getBrowser() {
        return config.getBrowser().getBrowserType();
    }

   
    public static DriverConfig getConfig() {
        return config;
    }


  
    public IWebDriverFactory getWebDriverBuilder() {
        return webDriverBuilder;
    }

  
    public void setBrowser(final String browser) {
        config.setBrowser(BrowserType.getBrowserType(browser));
    }

    public void setConfig(final DriverConfig config) {
        this.config = config;
    }


    public void setWebDriverBuilder(final IWebDriverFactory builder) {
        this.webDriverBuilder = builder;
    }

 
    public static void cleanUp() {
        IWebDriverFactory iWebDriverFactory = getWebUIDriver().webDriverBuilder;
        if (iWebDriverFactory != null) {
            iWebDriverFactory.cleanUp();
        } else {
            WebDriver driver = driverSession.get();
            if (driver != null) {
                try {
                    driver.quit();
                } catch (WebDriverException ex) {
                    ex.printStackTrace();
                }

                driver = null;
            }
        }

        driverSession.remove();
        uxDriverSession.remove();
    }
}
