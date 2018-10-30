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

package com.simplewebframework.browserfactory;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.simplewebframework.configClass.DriverConfig;
import com.simplewebframework.core.TestLogger;
import com.simplewebframework.core.WebUIDriver;

public abstract class AbstractWebDriverFactory {

    protected WebDriver driver;
    
    public AbstractWebDriverFactory() {

    }
    
    public void cleanUp() {
        try {
            if (driver != null) {
                try {
                    TestLogger.logRepInfo("quiting webdriver" + Thread.currentThread().getId());
                    driver.quit();
                } catch (WebDriverException ex) {
 /*               	TestLogger.logRepInfo("Exception encountered when quiting driver: "
                            + WebUIDriver.getWebDriver()..getConfig().getBrowser().name() + ":" + ex.getMessage());
*/                }

                driver = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WebDriver createWebDriver() throws Exception {
        return null;
    }

    /**
     * Accessed by sub classes so that they don't have be declared abstract class.
     *
     * @return  driver instance
     */
    public WebDriver getWebDriver() {
        return driver;
    }

    public void setImplicitWaitTimeout(final double timeout) {
        if (timeout < 1) {
            driver.manage().timeouts().implicitlyWait((long) (timeout * 1000), TimeUnit.MILLISECONDS);
        } else {
            try {
                driver.manage().timeouts().implicitlyWait(new Double(timeout).intValue(), TimeUnit.SECONDS);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setWebDriver(final WebDriver driver) {
        this.driver = driver;
    }

}
