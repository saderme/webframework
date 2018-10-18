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

import java.io.IOException;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.simplewebframework.configClass.AppConfig;
import com.simplewebframework.configClass.DriverConfig;

public class ChromeDriverFactory extends AbstractWebDriverFactory implements IWebDriverFactory {

    public ChromeDriverFactory(final DriverConfig cfg) {
        super(cfg);
    }

    protected WebDriver createNativeDriver() {
        return new ChromeDriver(new ChromeCapabilitiesFactory().createCapabilities(webDriverConfig));
    }

    @Override
    public WebDriver createWebDriver() throws IOException {
        DriverConfig cfg = this.getWebDriverConfig();

        driver = createNativeDriver();

        setImplicitWaitTimeout(AppConfig.IMPLICIT_WAIT_TIMEOUT);
        if (AppConfig.PAGE_LOAD_TIMEOUT >= 0) {
            setPageLoadTimeout(AppConfig.PAGE_LOAD_TIMEOUT);
        }

        this.setWebDriver(driver);
        return driver;
    }

    protected void setPageLoadTimeout(final long timeout) {
        try {
            driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
        } catch (UnsupportedCommandException e) { }
    }

}
