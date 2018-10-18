package com.test.testscripts;

import org.testng.annotations.Test;
import com.application.pageobjects.LoginPage;
import com.application.pageobjects.MyAccountPage;
import com.application.pageobjects.RegistrationPage;
import com.framework.file.helpers.ObjectReader;
import com.framework.ui.basepage.BaseBrowserPage;
import com.framework.ui.helpers.AssertionHelper;
import com.uiframework.core.TestBase;

public class TestNG_RegistrationPage_TestSuite extends TestBase{
	
	LoginPage loginPage;
	RegistrationPage register;
	MyAccountPage myAccountPage;
	
	@Test
	public void testRegistration(){
		// go to application
		BaseBrowserPage.getApplicationUrl(ObjectReader.reader.getUrl());
		
		loginPage = new LoginPage(driver);
		loginPage.clickOnSignInLink();
		loginPage.enterRegistrationEmail();
		
		register = loginPage.clickOnCreateAnAccount();
		
		// enter registration data
		register.setMrRadioButton();
		register.setFirstName("firstName");
		register.setLastname("lastname");
		register.setPassword("password");
		register.setAddress("address");
		register.setDay("5");
		register.setMonth("June");
		register.setYear("2017");
		register.setYourAddressFirstName("yourAddressFirstName");
		register.setYourAddressLastName("yourAddressLastName");
		register.setYourAddressCompany("yourAddressCompany");
		register.setYourAddressCity("yourAddressCity");
		register.setYourAddressState("Alaska");
		register.setYourAddressPostCode("99501");
		register.setMobilePhone("9999999999");
		register.setAddressAlias("addressAlias");
		register.clickRegistration();
		
		myAccountPage = new MyAccountPage(driver);
		boolean status = myAccountPage.isYourAccountPageMessage();
		
		AssertionHelper.updateTestStatus(status);
	}
}
