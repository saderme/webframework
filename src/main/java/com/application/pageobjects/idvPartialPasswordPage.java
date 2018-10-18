package com.application.pageobjects;

import static com.simplewebframework.core.Locator.locateById;

import org.openqa.selenium.support.PageFactory;

import com.simplewebframework.webelements.ButtonElement;
import com.simplewebframework.webelements.HtmlElement;
import com.simplewebframework.webelements.LinkElement;
import com.simplewebframework.webelements.webPageObject;
import com.simplewebframework.webelements.TextFieldElement;

public class idvPartialPasswordPage extends webPageObject {
	
    private static final TextFieldElement xpasscode = new TextFieldElement("password verification filed 1",locateById("xpasscode"));
    private static final TextFieldElement ypasscode = new TextFieldElement("password verification field 2",locateById("ypasscode"));
    private ButtonElement submitBtn = new ButtonElement("Submit Button",locateById("passcodeSubmit"));
    private static final LinkElement resetPasscode = new LinkElement("Reset Password link",locateById("resetPasscode"));
     
    public idvPartialPasswordPage() throws Exception {
		PageFactory.initElements(driver, this);
		waitForElementPresent(xpasscode);
    }
    
    public idvPartialPasswordPage enterXPasscode(final String xpass) {
    	xpasscode.clearAndType(xpass);
        return this;
    }
  
	public idvPartialPasswordPage enterYPasscode(String ypass){
		ypasscode.clearAndType(ypass);
        return this;
	}   
  
    public idvPartialPasswordPage clickSubmitBtn() throws Exception {
    	submitBtn.click();
        return this;
    }
    
    public idvPartialPasswordPage clickResetPasscode() throws Exception {
    	resetPasscode.click();
        return this;
    }	
    
    public HtmlElement getXPasscode() {
        return xpasscode;
    }
    
    public HtmlElement getYPasscode() {
        return ypasscode;
    }
}
