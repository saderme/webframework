package com.application.dataprovider;

import java.util.Iterator;

import org.testng.annotations.DataProvider;

import com.simplewebframework.configClass.TestConfig;
import com.simplewebframework.core.BaseTestPlan;
import com.simplewebframework.helpers.ExcelHelper;

/**
 * @author Mahomed Sader
 *Class that contains all the data providers for TestNG Test Classes
 *DataProviders for Factory must return Object[], not Iterator
 */

public class DataProviderCentral {
	
	/***
	 * Need testConfig Class so we can use TestConfig.TEST_DATA_PATH. When using Factory, this is not initialised
	 * so therefore need to instantiate and force creation of TestConfig static variables.**/
	private static TestConfig testconfig= new TestConfig("testconfig.properties");  //need this to use the test
	
	//Sample Test Data
	@DataProvider(name = "BBOS_TEST_DATA_SAMPLE")
	public static Object[][] createData() {
		return new Object[][] { new Object[] { new Integer(42) }, { new Integer(43) }, { new Integer(44) } };
	}
	
	//Primitive Login Data from excel spreadsheet
	@DataProvider(name = "BBOS_LOGIN_DATA")
	public static Object[][] createBBOSTestAccounts() {
		return ExcelHelper.getExcelData(TestConfig.getConfigPath(), "loginData");
	}
	
	//Class Login Data from excel spreadsheet
	@DataProvider(name = "BBOS_DATA_CLASS")
	public static Object[][] createLoginData() {
		return ExcelHelper.getExcelDataToBBOS(TestConfig.getConfigPath(), "loginData");
	}	
	
	//Iterator returned from excel spreadsheet. Cannot be used with Factory.
	@DataProvider(name = "BBOS_ITERATOR")
	public Iterator<Object[]> createData2() {
		int DATA = 99;
		// return new MyIterator(DATA);
		return new MyIterator<Object[]>(DATA);
	}
	
	//Test data for @factory in ExternalDataProviderTest
	@DataProvider(name = "dataMethod")
	public static Object[][] dataMethod() {
		return new Object[][] { { 0 }, { 1 } };
	}
}
