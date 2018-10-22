package com.test.testscripts.testfactory;

import java.lang.reflect.Method;

import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.application.dataprovider.BBOSTestAccount;
import com.application.dataprovider.DataProviderCentral;
import com.application.pageobjects.LoginPage;
import com.application.pageobjects.idvPartialPasswordPage;
import com.simplewebframework.configClass.TestConfig;
import com.simplewebframework.core.BaseTestPlan;
/* The data provider class is stored in a separate external class which is specified by the dataProviderClass variable*/
import com.simplewebframework.helpers.ExcelHelper;

public class TestNG_Factory_DataProvider extends BaseTestPlan implements ITest {
	/***
	 * Need testConfig Class so we can use TestConfig.TEST_DATA_PATH. When using Factory, this is not initialised
	 * so therefore need to instantiate and force creation of TestConfig static variables.**/
	private static TestConfig testconfig= new TestConfig("testconfig.properties");  //need this to use the test

	private String login;
    private String email;
    private String password;
    private String runmode;
    
/*    @Factory(dataProvider = "BBOS_LOGIN_DATA",dataProviderClass = DataProviderCentral.class)
    public TestNG_Factory_DataProvider(String login,String email,String password ,String runmode) {
    	this.testname = "Testing" + email;
        this.login = login;
        this.email = email;
        this.password = password;
        this.runmode = runmode;
     }*/
    
   @Factory(dataProvider = "BBOS_DATA_CLASS",dataProviderClass = DataProviderCentral.class)
  //  @Factory(dataProvider = "BBOS_DATA_CLASS")
    public TestNG_Factory_DataProvider(BBOSTestAccount testacc) {
        this.login = testacc.getLogin();
        this.email = testacc.getEmail();
        this.password = testacc.getPassword();
        this.runmode = testacc.getRunMode();
     }
    
/*	@DataProvider(name = "BBOS_DATA_CLASS")
	public static Object[][] createLoginData() {
		Object[][] testdata = ExcelHelper.getExcelDataToBBOS(TestConfig.getConfigPath(), "loginData");
		return testdata;
	}	
	*/
	
	@Test(description = "Verify Next button is enabled" )
	public void testNextisEnabled() throws Exception{
		System.out.println("Test Data " + login + email+ password + runmode);
		LoginPage loginPage = new LoginPage();
       loginPage.assertElementEnabled(loginPage.getNextBtn());
	}
   
/*	@Test(description = "Verify IDV Page is displayed when Next us clicked" )
	public void testIDVPageisDisplayed() throws Exception{
		LoginPage loginPage = new LoginPage();
		loginPage.enterEmailAddress(this.email);
		loginPage.enterPassword(this.password);
		loginPage.waitForElementToBeVisible(loginPage.getNextBtn());
		loginPage.getNextBtn().simulateClick();
        idvPartialPasswordPage idvpp = new idvPartialPasswordPage();
        idvpp.assertElementDisplayed(idvpp.getXPasscode());
 	}*/
	
	@Override
	public String getTestName() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName() + "[login=").append(this.login).append(", email=").append(this.email).append(", password=").append(this.password).append(", runmode=").append(this.runmode).append("]");
        return builder.toString();
	}
	
/*	@Override
    public String getTestName() {
	  return getClass().getSimpleName() + "-" + this.email;
    }*/

/*	@AfterClass
	public void afterClass() {
		driver.quit();
	}*/

}
