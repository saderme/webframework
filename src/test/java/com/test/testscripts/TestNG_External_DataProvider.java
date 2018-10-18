package com.test.testscripts;

import org.testng.annotations.Test;


import com.application.dataprovider.DataProviderCentral;
import com.simplewebframework.core.BaseTestPlan;
/* The data provider class is stored in a separate external class which is specified by the dataProviderClass variable*/

public class TestNG_External_DataProvider extends BaseTestPlan{

	@Test(description = "Verify Next button is enabled" , 
		  dataProvider = "BBOS_LOGIN_DATA", dataProviderClass = DataProviderCentral.class)
	public void LoginTest(String a,String b,String c ,String d) {
			System.out.println("Value received from data provider" + a + b + c + d);
	}
	  

	
/*	@AfterClass
	public void afterClass() {
		driver.quit();
	}*/

}
