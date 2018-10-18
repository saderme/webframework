/**
 * Button Element Class
 */

package com.simplewebframework.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.simplewebframework.core.BrowserType;
import com.simplewebframework.core.TestLogger;
import com.simplewebframework.core.WebUIDriver;

public class ButtonElement extends HtmlElement {

    public ButtonElement(final String label, final By by) {
        super(label, by);
    }

    @Override
    public void click() {
        TestLogger.logInfo("click on " + toHTML());

        BrowserType browser = WebUIDriver.getConfig().getBrowser();
        if (browser == BrowserType.InternetExplore) {
            super.sendKeys(Keys.ENTER);
        } else {
            super.click();
        }
    }

    public void submit() {
    	TestLogger.logInfo("Submit form by clicking on " + toHTML());
        findElement();
        element.submit();
    }
}
