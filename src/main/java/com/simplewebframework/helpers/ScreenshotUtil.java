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

import java.io.IOException;

import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.simplewebframework.configClass.TestConfig;
import com.simplewebframework.core.BrowserType;
import com.simplewebframework.core.TestLogger;
import com.simplewebframework.core.WebUIDriver;
import com.simplewebframework.customexception.WebSessionEndedException;

import com.simplewebframework.helpers.FileUtility;
import com.simplewebframework.helpers.HashCodeGenerator;

public class ScreenshotUtil {
    private static final Logger applog = TestLogger.getLogger(ScreenshotUtil.class);

    public static String captureEntirePageScreenshotToString(final WebDriver driver, final String arg0) {
        if (driver == null) {
            return "";
        }

        try {

            // Don't capture snapshot for htmlunit
            if (WebUIDriver.getBrowserType() == BrowserType.HtmlUnit) {
                return null;
            }

/*            if (WebUIDriver.getWebUIDriver().getBrowser().equalsIgnoreCase(BrowserType.Android.getBrowserType())) {
                return null;
            }*/

            TakesScreenshot screenShot = (TakesScreenshot) driver;
            return screenShot.getScreenshotAs(OutputType.BASE64);
        } catch (Exception ex) {

            // Ignore all exceptions
            ex.printStackTrace();
        }

        return "";
    }

    private String suiteName;
    private String outputDirectory;
    private WebDriver driver;
    private String filename;

    public ScreenshotUtil() {
        suiteName = "Test";
        outputDirectory = TestConfig.SCREENSHOT_PATH;
        this.driver = WebUIDriver.getWebDriver();
    }

    public ScreenshotUtil(final WebDriver driver) {
        suiteName = "Test";
        outputDirectory = TestConfig.SCREENSHOT_PATH;
        this.driver = driver;
    }


    private void handleSource(String htmlSource, final ScreenShot screenShot) {
        if (htmlSource == null) {

            // driver.switchTo().defaultContent();
            htmlSource = driver.getPageSource();
        }

        if (htmlSource != null) {
            try {
                FileUtility.writeToFile(outputDirectory + "/htmls/" + filename + ".html", htmlSource);
                screenShot.setHtmlSourcePath(suiteName + "/htmls/" + filename + ".html");
            } catch (IOException e) {
            	applog.warn("Ex", e);
            }

        }
    }

    private void handleImage(final ScreenShot screenShot) {
        try {
            String screenshotString = captureEntirePageScreenshotToString(WebUIDriver.getWebDriver(), "");

            if (screenshotString != null && !screenshotString.equalsIgnoreCase("")) {
                byte[] byteArray = screenshotString.getBytes();
                FileUtility.writeImage(outputDirectory + "/screenshots/" + filename + ".png", byteArray);
                screenShot.setImagePath(suiteName + "/screenshots/" + filename + ".png");

            }
        } catch (Throwable e) {
        	applog.warn("Ex", e);
            e.printStackTrace();
        }
    }

    private void handleTitle(String title, final ScreenShot screenShot) {
        if (title == null) {

            // driver.switchTo().defaultContent();
            title = driver.getTitle();
        }

        if (title == null) {
            title = "";
        }

        screenShot.setTitle(title);
    }

    public ScreenShot captureWebPageSnapshot() {

        ScreenShot screenShot = new ScreenShot();

        if ( outputDirectory == null) {
            return screenShot;
        }

        screenShot.setSuiteName(this.suiteName);

        try {
            String url = null;
            try {
                url = driver.getCurrentUrl();
            } catch (org.openqa.selenium.UnhandledAlertException ex) {

                // ignore alert customexception
                ex.printStackTrace();
                url = driver.getCurrentUrl();
            }

            String title = driver.getTitle();
            String pageSource = driver.getPageSource();

            String filename = HashCodeGenerator.getRandomHashCode("web");
            this.filename = filename;
            screenShot.setLocation(url);

            handleTitle(title, screenShot);
            handleSource(pageSource, screenShot);

            handleImage(screenShot);

        } catch (WebSessionEndedException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return screenShot;
    }


    public static void captureSnapshot(final String messagePrefix) {

            String filename = HashCodeGenerator.getRandomHashCode("HtmlElement");
            StringBuffer sbMessage = new StringBuffer();
            try {
                String img = ScreenshotUtil.captureEntirePageScreenshotToString(WebUIDriver.getWebDriver(), "");
                if (img == null) {
                    return;
                }

                byte[] byteArray = img.getBytes();
                if (byteArray != null && byteArray.length > 0) {
                    String imgFile = "/screenshots/" + filename + ".png";
                    FileUtility.writeImage(TestConfig.SCREENSHOT_PATH + imgFile, byteArray);

                    ScreenShot screenShot = new ScreenShot();
                    String imagePath = "Test" + imgFile;
                    screenShot.setImagePath(imagePath);
                    sbMessage.append(messagePrefix + ": <a href='" + imagePath
                            + "' class='lightbox'>Application Snapshot</a>");
                    TestLogger.logWebOutput(sbMessage.toString(), false);
                    sbMessage = null;
                }
            } catch (WebSessionEndedException ex) {
                throw ex;
            } catch (Throwable e) {
                e.printStackTrace();
            }

        
    }
}
