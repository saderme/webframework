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

package com.simplewebframework.helpers;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import com.simplewebframework.core.BrowserType;
import com.simplewebframework.core.TestLogger;
import com.simplewebframework.core.WebUIDriver;

public class WebUtility {
    private WebDriver driver;

    public WebUtility(final WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Resize window to given dimensions.
     *
     * @param  width
     * @param  height
     */
    public void resizeWindow(final int width, final int height) {
        try {
            TestLogger.logRepInfo("Resize browser window to width " + width + " height " + height);

            Dimension size = new Dimension(width, height);
            driver.manage().window().setPosition(new Point(0, 0));
            driver.manage().window().setSize(size);
        } catch (Exception ex) { }
    }

    public void maximizeWindow() {
        try {
            BrowserType browser = WebUIDriver.getBrowserType();
            if (browser == BrowserType.Android || browser == BrowserType.IPhone) {
                return;
            }

            driver.manage().window().maximize();
        } catch (Exception ex) {

            try {
                ((JavascriptExecutor) driver).executeScript(
                    "if (window.screen){window.moveTo(0, 0);window.resizeTo(window.screen.availWidth,window.screen.availHeight);}");
            } catch (Exception ignore) {
                TestLogger.logRepInfo("Unable to maximize browser window. Exception occured: " + ignore.getMessage());
            }
        }
    }

    public static void main(final String[] args) {
       // WebUIDriver.getWebUIDriver().setMode("ExistingGrid");
       // WebUIDriver.getWebUIDriver().setAppUrl(" ");

        WebDriver driver = WebUIDriver.getWebDriver();
        System.out.print(driver.manage().window().getSize().width + ":" + driver.manage().window().getSize().height);
    }

}
