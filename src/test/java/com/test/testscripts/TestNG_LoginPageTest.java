package com.test.testscripts;

import static com.simplewebframework.core.CustomAssertion.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.openqa.selenium.By;
import org.testng.ISuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.application.pageobjects.LoginPage;
import com.application.pageobjects.idvPartialPasswordPage;
import com.simplewebframework.core.BaseTestPlan;
import com.simplewebframework.webelements.ButtonElement;
import com.simplewebframework.webelements.HtmlElement;
import com.simplewebframework.webelements.webBasePage;

public class TestNG_LoginPageTest extends BaseTestPlan{


/*	@Test(description = "Verify Next button is displayed" )
	public void testNextisDisplayed() throws Exception{
		LoginPage loginPage = new LoginPage();
        loginPage.assertElementDisplayed(loginPage.getNextBtn());
	}*/

	@Test(description = "Verify Next button is enabled" )
	public void testNextisEnabled() throws Exception{
		LoginPage loginPage = new LoginPage();
        loginPage.assertElementEnabled(loginPage.getNextBtn());
	}

	@Test(description = "Verify IDV Page is displayed when Next us clicked" )
	public void testIDVPageisDisplayed() throws Exception{
		
		LoginPage loginPage = new LoginPage();
		loginPage.enterEmailAddress("Test@test.com");
		loginPage.enterPassword("123456");
		loginPage.waitForElementToBeVisible(loginPage.getNextBtn());
		loginPage.getNextBtn().simulateClick();
        idvPartialPasswordPage idvpp = new idvPartialPasswordPage();
        idvpp.assertElementDisplayed(idvpp.getXPasscode());
     
	}
	
	
/*	@AfterClass
	public void afterClass() {
		driver.quit();
	}*/

}
