package com.application.pageobjects;

import static com.simplewebframework.core.Locator.locateById;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import com.simplewebframework.core.TestLogger;
import com.simplewebframework.webelements.ButtonElement;
import com.simplewebframework.webelements.LinkElement;
import com.simplewebframework.webelements.TextFieldElement;
import com.simplewebframework.webelements.webPageObject;
/**
 * This class represents the Login Page 
 */
public class LoginPage extends webPageObject {
	/**
	 * WebElements 
	 */
    private static final TextFieldElement emailAddress = new TextFieldElement("email address or userid field",locateById("userid"));
    private static final TextFieldElement password = new TextFieldElement("password field",locateById("password"));
    private static final ButtonElement nextBtn = new ButtonElement("Next Button",locateById("nextBtn"));
    private static final LinkElement register = new LinkElement("Register link",locateById("goToRegistration"));
    private static final LinkElement forgotuser = new LinkElement("Forgotten User link",locateById("forgotUserId"));
    private static final LinkElement forgotpass = new LinkElement("Forgotten Password link",locateById("forgotPwdBtn"));

    /**
	 * Constructor 
	 */	
    public LoginPage() throws Exception {
		PageFactory.initElements(driver, this);
		waitForElementPresent(emailAddress);
    }
 
    /**
	 * WebElement Interactions 
	 */	
    public LoginPage enterEmailAddress(final String emailAdd) {
    	emailAddress.clearAndType(emailAdd);
        return this;
    }
  
	public LoginPage enterPassword(String pass){
    	password.clearAndType(pass);
        return this;
	}   

	public LoginPage clickRegister(){
		register.click();
        return this;
	}
	
	public LoginPage clickForgotUser(){
		forgotuser.click();
        return this;
	}
	
	public LoginPage clickForgotPassword(){
		forgotpass.click();
        return this;
	}
	
    public idvPartialPasswordPage clickNextBtn() throws Exception {
    	nextBtn.click();
        return new idvPartialPasswordPage();
    }
   
    public ButtonElement getNextBtn() throws Exception {
        return  nextBtn;
    }
    
 	public static void main(String[] args) throws Exception {
 		Logger logger = TestLogger.getLogger(LoginPage.class);
 		LoginPage lp = new LoginPage();
 		lp.enterEmailAddress("Test@Test.com");
 		lp.enterPassword("abcde");
 		lp.assertElementDisplayed(nextBtn);
  	}
    
}
