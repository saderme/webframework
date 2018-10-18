package com.simplewebframework.webelements;

import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.simplewebframework.configClass.AppConfig;
import com.simplewebframework.core.BaseTestPlan;
//import com.simplewebframework.core.CustomAssertion;
import com.simplewebframework.core.TestLogger;
import com.simplewebframework.core.WebUIDriver;
import com.simplewebframework.customexception.NotCurrentPageException;
import com.simplewebframework.helpers.JavaScriptHelper;
import com.simplewebframework.helpers.WaitHelper;

/**
 * Base html page abstraction. Used by webPageObject and WebPageSection
 */
public abstract class webBasePage {
	
	protected AppConfig con = new AppConfig("appconfig.properties");
	protected WebDriver driver = WebUIDriver.getWebDriver();
	protected final WebUIDriver webUXDriver = WebUIDriver.getWebUIDriver();
	private static final Logger logger = TestLogger.getLogger(webBasePage.class);
	
	public webBasePage() {
	}

	public void acceptAlert() throws NotCurrentPageException {
		final Alert alert = driver.switchTo().alert();
		alert.accept();
		driver.switchTo().defaultContent();
	}

	public void assertAlertPresent() {
		TestLogger.logInfo("assert alert present.");

		try {
			driver.switchTo().alert();
		} catch (final Exception ex) {
			assertAlertHTML(false, "assert alert present.");
		}
	}

	public void assertAlertText(final String text) {
		TestLogger.logInfo("assert alert text.");

		final Alert alert = driver.switchTo().alert();
		final String alertText = alert.getText();
		assertAlertHTML(alertText.contains(text), "assert alert text.");
	}

	/**
	 * @param element
	 * @param attributeName
	 * @param value
	 */
	public void assertAttribute(final HtmlElement element, final String attributeName, final String value) {
		TestLogger.logInfo(	"assert " + element.toHTML() + " attribute = " + attributeName + ", expectedValue ={" + value + "}.");

		final String attributeValue = element.getAttribute(attributeName);

		assertHTML((value != null) && value.equals(attributeValue), element.toString() + " attribute = " + attributeName
				+ ", expectedValue = {" + value + "}" + ", attributeValue = {" + attributeValue + "}");
	}

	public void assertAttributeContains(final HtmlElement element, final String attributeName, final String keyword) {
		TestLogger.logInfo("assert " + element.toHTML() + " attribute=" + attributeName + ", contains keyword = {"
				+ keyword + "}.");

		final String attributeValue = element.getAttribute(attributeName);

		assertHTML((attributeValue != null) && (keyword != null) && attributeValue.contains(keyword),
				element.toString() + " attribute=" + attributeName + ", expected to contains keyword {" + keyword + "}"
						+ ", attributeValue = {" + attributeValue + "}");
	}

	public void assertAttributeMatches(final HtmlElement element, final String attributeName, final String regex) {
		TestLogger.logInfo("assert " + element.toHTML() + " attribute=" + attributeName + ", matches regex = {" + regex + "}.");

		final String attributeValue = element.getAttribute(attributeName);

		assertHTML((attributeValue != null) && (regex != null) && attributeValue.matches(regex),
				element.toString() + " attribute=" + attributeName + " expected to match regex {" + regex + "}"
						+ ", attributeValue = {" + attributeValue + "}");
	}

	public void assertConfirmationText(final String text) {
		TestLogger.logInfo("assert confirmation text.");

		final Alert alert = driver.switchTo().alert();
		final String seenText = alert.getText();

		assertAlertHTML(seenText.contains(text), "assert confirmation text.");
	}

	protected void assertCurrentPage(final boolean log) throws NotCurrentPageException {

	}

	public void assertElementNotPresent(final HtmlElement element) {
		TestLogger.logInfo("assert " + element.toHTML() + " is not present.");
		assertHTML(!element.isElementPresent(), element.toString() + " found.");
	}

	public void assertElementPresent(final HtmlElement element) {
		TestLogger.logInfo("assert " + element.toHTML() + " is present.");
		assertHTML(element.isElementPresent(), element.toString() + " not found.");
	}

	public void assertElementEnabled(final HtmlElement element) {
        logger.info(Thread.currentThread() + " assert " + element.toHTML() + " is enabled.");
		TestLogger.logInfo("assert " + element.toHTML() + " is enabled.");
		
		assertHTML(element.isEnabled(), element.toString() + " not found.");
	}

	public void assertElementNotEnabled(final HtmlElement element) {
        logger.info(Thread.currentThread() + " assert " + element.toHTML() + " is not enabled.");
		TestLogger.logInfo("assert " + element.toHTML() + " is not enabled.");
		assertHTML(!element.isEnabled(), element.toString() + " not found.");
	}

	public void assertElementDisplayed(final HtmlElement element) {
		TestLogger.logInfo("assert " + element.toHTML() + " is displayed.");
		assertHTML(element.isDisplayed(), element.toString() + " not found.");
	}

	public void assertElementSelected(final HtmlElement element) {
		TestLogger.logInfo("assert " + element.toHTML() + " is selected.");
		assertHTML(element.isSelected(), element.toString() + " not found.");
	}

	public void assertElementNotSelected(final HtmlElement element) {
		TestLogger.logInfo("assert " + element.toHTML() + " is NOT selected.");
		assertHTML(!element.isSelected(), element.toString() + " not found.");
	}

	public void assertCondition(final boolean condition, final String message) {
		TestLogger.logInfo("assert that " + message);
		assert condition;
	}

	void assertHTML(final boolean condition, final String message) {

		if (!condition) {
			Assert.assertTrue(condition, message);
		//	CustomAssertion.assertTrue(condition, message);
		}
	}

	void assertAlertHTML(final boolean condition, final String message) {

		if (!condition) {
			Assert.assertTrue(condition, message);
		//	CustomAssertion.assertTrue(condition, message);
		}
	}

	public void assertPromptText(final String text) {
		TestLogger.logInfo("assert prompt text.");

		final Alert alert = driver.switchTo().alert();
		final String seenText = alert.getText();
		assertAlertHTML(seenText.contains(text), "assert prompt text.");
	}

	public void assertTable(final Table table, final int row, final int col, final String text) {
		TestLogger.logInfo("assert text \"" + text + "\" equals " + table.toHTML() + " at (row, col) = (" + row
				+ ", " + col + ").");

		final String content = table.getContent(row, col);
		assertHTML((content != null) && content.equals(text), "Text= {" + text + "} not found on " + table.toString()
				+ " at cell(row, col) = {" + row + "," + col + "}");
	}

	public void assertTableContains(final Table table, final int row, final int col, final String text) {
		TestLogger.logInfo("assert text \"" + text + "\" contains " + table.toHTML() + " at (row, col) = (" + row
				+ ", " + col + ").");

		final String content = table.getContent(row, col);
		assertHTML((content != null) && content.contains(text), "Text= {" + text + "} not found on " + table.toString()
				+ " at cell(row, col) = {" + row + "," + col + "}");
	}

	public void assertTableMatches(final Table table, final int row, final int col, final String text) {
		TestLogger.logInfo("assert text \"" + text + "\" matches " + table.toHTML() + " at (row, col) = (" + row
				+ ", " + col + ").");

		final String content = table.getContent(row, col);
		assertHTML((content != null) && content.matches(text), "Text= {" + text + "} not found on " + table.toString()
				+ " at cell(row, col) = {" + row + "," + col + "}");
	}

	public void assertTextNotPresent(final String text) {
		TestLogger.logInfo("assert text \"" + text + "\" is not present.");
		assertHTML(!isTextPresent(text), "Text= {" + text + "} found.");
	}

	public void assertTextNotPresentIgnoreCase(final String text) {
		TestLogger.logInfo("assert text \"" + text + "\" is not present.(ignore case)");
		assertHTML(!getBodyText().toLowerCase().contains(text.toLowerCase()), "Text= {" + text + "} found.");
	}

	public void assertTextPresent(final String text) {
		TestLogger.logInfo("assert text \"" + text + "\" is present.");
		assertHTML(isTextPresent(text), "Text= {" + text + "} not found.");
	}

	public void assertTextPresentIgnoreCase(final String text) {
		TestLogger.logInfo("assert text \"" + text + "\" is present.(ignore case)");
		assertHTML(getBodyText().toLowerCase().contains(text.toLowerCase()), "Text= {" + text + "} not found.");
	}

	public String cancelConfirmation() throws NotCurrentPageException {
		final Alert alert = driver.switchTo().alert();
		final String seenText = alert.getText();
		alert.dismiss();
		driver.switchTo().defaultContent();

		return seenText;
	}

	public Alert getAlert() {
		final Alert alert = driver.switchTo().alert();

		return alert;
	}

	public String getAlertText() {
		final Alert alert = driver.switchTo().alert();
		final String seenText = alert.getText();

		return seenText;
	}

	private String getBodyText() {
		final WebElement body = driver.findElement(By.tagName("body"));

		return body.getText();
	}

	public String getConfirmation() {
		final Alert alert = driver.switchTo().alert();
		final String seenText = alert.getText();

		return seenText;
	}

	public WebDriver getDriver() {
		driver = WebUIDriver.getWebDriver();

		return driver;

	}

/*	public JavaScriptHelper getjsHelper() {
		return jsHelper;
	}*/
	
	public String getPrompt() {
		final Alert alert = driver.switchTo().alert();
		final String seenText = alert.getText();

		return seenText;
	}

	public boolean isElementPresent(final By by) {
		int count = 0;

		try {
			count = WebUIDriver.getWebDriver().findElements(by).size();
		} catch (final RuntimeException e) {

			if ((e instanceof InvalidSelectorException) || ((e.getMessage() != null)
					&& e.getMessage().contains("TransformedEntriesMap cannot be cast to java.util.List"))) {
				TestLogger.logInfo("InvalidSelectorException or CastException got, retry");
				WaitHelper.waitForSeconds(2);
				count = WebUIDriver.getWebDriver().findElements(by).size();
			} else {
				throw e;
			}
		}

		if (count == 0) {
			return false;
		}

		return true;

	}

	public boolean isFrame() {
		return false;
	}

	public boolean isTextPresent(final String text) {
	//	CustomAssertion.assertNotNull(text, "isTextPresent: text should not be null!");
		driver = WebUIDriver.getWebDriver();

		final WebElement body = driver.findElement(By.tagName("body"));
		return body.getText().contains(text);
	}

	public void selectFrame(final By by) {
		TestLogger.logInfo("select frame, locator={\"" + by.toString() + "\"}");
		driver.switchTo().frame(driver.findElement(by));
	}

	/**
	 * If current window is closed then use driver.switchTo.window(handle).
	 *
	 * @param windowName
	 *
	 * @throws com.simplewebframework.customexception.NotCurrentPageException
	 */
	public final void selectWindow(final String windowName) throws NotCurrentPageException {

		if (windowName == null) {
			try {
				driver.switchTo().window(windowName);
			} catch (final Exception e) {
				driver.switchTo().defaultContent();
			}
		}
	}

	public void refreshPageTillTextAppears(final String text, final int waitPeriodInSec) throws InterruptedException {
		for (int i = 0; i <= waitPeriodInSec; i++) {
			if (!driver.getPageSource().contains(text)) {
				Thread.sleep(1000);
				driver.navigate().refresh();
			} else {
				break;
			}
		}
	}

	public void waitForElementChecked(final HtmlElement element) {
		Assert.assertNotNull(element, "Element can't be null");
		TestLogger.logInfo("wait for " + element.toString() + " to be checked.");

		final WebDriverWait wait = new WebDriverWait(driver, AppConfig.EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.elementToBeSelected(element.getBy()));
	}

	public void waitForElementEditable(final HtmlElement element) {
		Assert.assertNotNull(element, "Element can't be null");
		TestLogger.logInfo("wait for " + element.toString() + " to be editable.");
		final WebDriverWait wait = new WebDriverWait(driver, AppConfig.EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.elementToBeClickable(element.getBy()));
		
	}

	public void waitForElementPresent(final By by) {
		TestLogger.logInfo("wait for " + by.toString() + " to be present.");

		final WebDriverWait wait = new WebDriverWait(driver, AppConfig.EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public void waitForElementPresent(final By by, final int timeout) {
		TestLogger.logInfo("wait for " + by.toString() + " to be present.");

		final WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public void waitForElementPresent(final HtmlElement element) {
		Assert.assertNotNull(element, "Element can't be null");
		TestLogger.logInfo("wait for " + element.toString() + " to be present.");

		final WebDriverWait wait = new WebDriverWait(driver, AppConfig.EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.presenceOfElementLocated(element.getBy()));
	}

	public void waitForElementToBeVisible(final HtmlElement element) {
		Assert.assertNotNull(element, "Element can't be null");
		TestLogger.logInfo("wait for " + element.toString() + " to be visible.");

		final WebDriverWait wait = new WebDriverWait(WebUIDriver.getWebDriver(), AppConfig.EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element.getBy()));
	}

	public static void waitForURLToChange(final String match, final int waitSeconds, final boolean partialMatch) {

		for (int i = 0; i < waitSeconds; i++) {
			if (partialMatch) {
				if (WebUIDriver.getWebDriver().getCurrentUrl().contains(match)) {
					break;
				} else {
					WaitHelper.waitForSeconds(1);
				}
			} else {
				if (WebUIDriver.getWebDriver().getCurrentUrl().equals(match)) {
					break;
				} else {
					WaitHelper.waitForSeconds(1);
				}
			}
		}
	}

	public static void switchToWindow(final Set<String> allWindows, final String currentWindow) {

		for (final String window : allWindows) {

			if (!window.equals(currentWindow)) {
				WebUIDriver.getWebDriver().switchTo().window(window);
			}
		}
	}

	public void waitForElementToDisappear(final HtmlElement element) {
		Assert.assertNotNull(element, "Element can't be null");
		TestLogger.logInfo("wait for " + element.toString() + " to disappear.");

		final WebDriverWait wait = new WebDriverWait(driver, AppConfig.EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(element.getBy()));
	}

	/**
	 * Wait For seconds. Provide a value less than WebSessionTimeout i.e. 180
	 * Seconds
	 *
	 * @param seconds
	 */
	protected void waitForSeconds(final int seconds) {
		WaitHelper.waitForSeconds(seconds);
	}

	public void waitForTextPresent(final HtmlElement element, final String text) {
		Assert.assertNotNull(text, "Text can't be null");
		TestLogger.logInfo("wait for text \"" + text + "\" to be present.");

		final WebDriverWait wait = new WebDriverWait(driver, AppConfig.EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.textToBePresentInElement(element.getBy(), text));
	}

	public void waitForTextPresent(final String text) {
		Assert.assertNotNull(text, "Text can't be null");
		TestLogger.logInfo("wait for text \"" + text + "\" to be present.");

		boolean b = false;

		for (int millisec = 0; millisec < (AppConfig.EXPLICIT_WAIT_TIME_OUT * 1000); millisec += 1000) {

			try {

				if ((isTextPresent(text))) {
					b = true;

					break;
				}
			} catch (final Exception ignore) {
			}

			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				throw new RuntimeException(e.getMessage());
			}
		}

		assertHTML(b, "Timed out waiting for text \"" + text + "\" to be there.");
	}

	public void waitForTextToDisappear(final String text) {
		Assert.assertNotNull(text, "Text can't be null");
		TestLogger.logInfo("wait for text \"" + text + "\" to disappear.");

		boolean textPresent = true;

		for (int millisec = 0; millisec < (AppConfig.EXPLICIT_WAIT_TIME_OUT * 1000); millisec += 1000) {

			try {

				if (!(isTextPresent(text))) {
					textPresent = false;

					break;
				}
			} catch (final Exception ignore) {
			}

			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				throw new RuntimeException(e.getMessage());
			}
		}

		assertHTML(!textPresent, "Timed out waiting for text \"" + text + "\" to be gone.");
	}

	public void waitForTextToDisappear(final String text, final int explicitWaitTimeout) {
		Assert.assertNotNull(text, "Text can't be null");
		TestLogger.logInfo("wait for text \"" + text + "\" to disappear.");

		boolean textPresent = true;

		for (int millisec = 0; millisec < (explicitWaitTimeout * 1000); millisec += 1000) {

			try {

				if (!(isTextPresent(text))) {
					textPresent = false;

					break;
				}
			} catch (final Exception ignore) {
			}

			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				throw new RuntimeException(e.getMessage());
			}
		}

		assertHTML(!textPresent, "Timed out waiting for text \"" + text + "\" to be gone.");
	}

}
