package com.application.dataprovider;

import java.util.Iterator;

import org.testng.annotations.DataProvider;

import com.simplewebframework.configClass.TestConfig;
import com.simplewebframework.helpers.ExcelHelper;

public class DataProviderCentral {
	@DataProvider(name = "BBOS_TEST_DATA_SAMPLE")
	public static Object[][] createData() {
		return new Object[][] { new Object[] { new Integer(42) }, { new Integer(43) }, { new Integer(44) } };
	}

	@DataProvider(name = "BBOS_LOGIN_DATA")
	public static Object[][] createLoginData() {
		return ExcelHelper.getExcelData(TestConfig.TEST_DATA_PATH, "loginData");
	}

	@DataProvider(name = "BBOS_ITERATOR")
	public Iterator<Object[]> createData2() {
		int DATA = 99;
		// return new MyIterator(DATA);
		return new MyIterator<Object[]>(DATA);
	}
	
	//Provided data for factory in ExternalDataProviderTest
	@DataProvider(name = "dataMethod")
	public static Object[][] dataMethod() {
		return new Object[][] { { 0 }, { 1 } };
	}
}
