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

package com.simplewebframework.webelements;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.simplewebframework.configClass.AppConfig;
import com.simplewebframework.core.TestLogger;
import com.simplewebframework.core.WebUIDriver;
import com.simplewebframework.customexception.CustomSeleniumTestsException;
import com.simplewebframework.customexception.NotCurrentPageException;
import com.simplewebframework.helpers.ScreenShot;
//import com.simplewebframework.helpers.ScreenshotUtil;
import com.simplewebframework.helpers.WebUtility;

public class webPageObject extends webBasePage implements IPage {

	private static final Logger logger = TestLogger.getLogger(webPageObject.class);
	private boolean frameFlag = false;
	private final String popupWindowName = null;
	private String windowHandle = null;
	private String title = null;
	private String url = null;
	private String bodyText = null;
	private String htmlSource = null;
	private String htmlSavedToPath = null;
	private String suiteName = null;
	private String outputDirectory = null;
	private String htmlFilePath = null;
	private String imageFilePath = null;
	
	public webPageObject() throws Exception {
		driver = WebUIDriver.getWebDriver(true);

		if (url == null) {
			url = AppConfig.APP_URL;
		} 
		
		open(url);		
		waitForPageToLoad();
		assertCurrentPage(false);
	}

	public String getLocation() {
		return driver.getCurrentUrl();
	}

	public String getPopupWindowName() {
		return popupWindowName;
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public String getUrl() {
		return url;
	}

	public String getCanonicalURL() {
		return new LinkElement("Canonical URL", By.cssSelector("link[rel=canonical]")).getAttribute("href");
	}

	public String getWindowHandle() {
		return windowHandle;
	}

	public final void goBack() {
		TestLogger.logInfo("goBack");
		driver.navigate().back();
		frameFlag = false;
	}

	public final void goForward() {
		TestLogger.logInfo("goForward");
		driver.navigate().forward();
		frameFlag = false;
	}

	public boolean isFrame() {
		return frameFlag;
	}

	public final void maximizeWindow() {
		new WebUtility(driver).maximizeWindow();
	}

	private void open(final String url) throws Exception {

		if (this.getDriver() == null) {
			TestLogger.logInfo("Launch application");
			driver = webUXDriver.createWebDriver();
		}

		setUrl(url);

		try {
				driver.navigate().to(url);
		} catch (final UnreachableBrowserException e) {

			// handle if the last window is closed
			TestLogger.logInfo("Launch application");
			driver = webUXDriver.createWebDriver();
			maximizeWindow();
			driver.navigate().to(url);
		} catch (final UnsupportedCommandException e) {
			TestLogger.logInfo("get UnsupportedCommandException, retry");
			driver = webUXDriver.createWebDriver();
			maximizeWindow();
			driver.navigate().to(url);
		} catch (final org.openqa.selenium.TimeoutException ex) {
			TestLogger.logInfo("got time out when loading " + url + ", ignored");
		} catch (final org.openqa.selenium.UnhandledAlertException ex) {
			TestLogger.logInfo("got UnhandledAlertException, retry");
			driver.navigate().to(url);
		} catch (final Throwable e) {
			e.printStackTrace();
			throw new CustomSeleniumTestsException(e);
		}
	}

	private void populateAndCapturePageSnapshot() {

		try {
			setTitle(driver.getTitle());
			htmlSource = driver.getPageSource();

			try {
				bodyText = driver.findElement(By.tagName("body")).getText();
			} catch (final StaleElementReferenceException ignore) {
				logger.warn("StaleElementReferenceException got in populateAndCapturePageSnapshot");
				bodyText = driver.findElement(By.tagName("body")).getText();
			}

		} catch (final UnreachableBrowserException e) { // throw

			// UnreachableBrowserException
			throw new WebDriverException(e);
		} catch (final WebDriverException e) {
			throw e;
		}

		//capturePageSnapshot();
	}

	public final void refresh() throws NotCurrentPageException {
		TestLogger.logInfo("refresh");

		try {
			driver.navigate().refresh();
		} catch (final org.openqa.selenium.TimeoutException ex) {
			TestLogger.logInfo("got time out customexception, ignore");
		}
	}

	public final void resizeTo(final int width, final int height) {
		new WebUtility(driver).resizeWindow(width, height);
	}

	public final void selectFrame(final int index) {
		TestLogger.logInfo("select frame using index" + index);
		driver.switchTo().frame(index);
		frameFlag = true;
	}

	public final void selectFrame(final By by) {
		TestLogger.logInfo("select frame, locator={\"" + by.toString() + "\"}");
		driver.switchTo().frame(driver.findElement(by));
		frameFlag = true;
	}

	public final void selectFrame(final String locator) {
		TestLogger.logInfo("select frame, locator={\"" + locator + "\"}");
		driver.switchTo().frame(locator);
		frameFlag = true;
	}

	public final void selectWindow() throws NotCurrentPageException {
		TestLogger.logInfo("select window, locator={\"" + getPopupWindowName() + "\"}");

		// selectWindow(getPopupWindowName());
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[0]);
		waitForSeconds(1);

		// Check whether it's the expected page.
		assertCurrentPage(true);
	}

	public final void selectWindow(final int index) throws NotCurrentPageException {
		TestLogger.logInfo("select window, locator={\"" + index + "\"}");
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[index]);
	}

	public final void selectNewWindow() throws NotCurrentPageException {
		TestLogger.logInfo("select new window");
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);
		waitForSeconds(1);
	}

	/**
	 * Waits for given URL term to appear when there is URL redirect
	 *
	 * @param urlTerm
	 * @param waitCount
	 */
	public static void waitForGivenURLTermToAppear(final String urlTerm, final int waitCount) {

		for (int index = 0; index < waitCount; index++) {

			if (WebUIDriver.getWebDriver().getCurrentUrl().contains(urlTerm)) {
				break;
			} else {
				try {
					Thread.sleep(1000);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
				index = index + 1;
			}
		}
	}

	protected void setHtmlSavedToPath(final String htmlSavedToPath) {
		this.htmlSavedToPath = htmlSavedToPath;
	}

	protected void setTitle(final String title) {
		this.title = title;
	}

	protected void setUrl(final String openUrl) {
		this.url = openUrl;
	}

	public void switchToDefaultContent() {

		try {
			driver.switchTo().defaultContent();
		} catch (final UnhandledAlertException e) {
		}
	}

	private void waitForPageToLoad() throws Exception {
		try {
			populateAndCapturePageSnapshot();
		} catch (final Exception ex) {

			// ex.printStackTrace();
			throw ex;
		}
	}

	public WebElement getElement(final By by, final String elementName) {
		WebElement element = null;

		try {
			element = driver.findElement(by);
		} catch (final ElementNotFoundException e) {
			TestLogger.logError(elementName + " is not found with locator - " + by.toString());
			throw e;
		}

		return element;
	}

	public String getElementUrl(final By by, final String name) {
		return getElement(by, name).getAttribute("href");
	}

	public String getElementText(final By by, final String name) {
		return getElement(by, name).getText();
	}

	public String getElementSrc(final By by, final String name) {
		return getElement(by, name).getAttribute("src");
	}


	public String getBodyText() {
		return bodyText;
	}

	public String getHtmlSavedToPath() {
		return htmlSavedToPath;
	}

	public String getHtmlSource() {
		return htmlSource;
	}
}
