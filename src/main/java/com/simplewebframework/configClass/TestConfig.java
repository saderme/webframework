package com.simplewebframework.configClass;

import com.simplewebframework.configReader.ObjectReader;
import com.simplewebframework.configReader.PropertyReader;
import com.simplewebframework.helpers.ResourceHelper;

/**
 * @author Mahomed Sader
 * Retrieves all the test config details from the config file.
 * Config file path is specified in PropertyReader and name is passed from BaseTestPlan  
 */
public class TestConfig {

    public static int DEFAULT_IMPLICIT_WAIT_TIMEOUT = 5;
    public static int DEFAULT_EXPLICIT_WAIT_TIME_OUT = 15;
    public static int DEFAULT_PAGE_LOAD_TIMEOUT = 90;
    public static int WEB_SESSION_TIMEOUT = 30 * 1000;
    public static int IMPLICIT_WAIT_TIMEOUT;
    public static int EXPLICIT_WAIT_TIME_OUT; 
    public static String DRIVER_BIN_PATH;   
    public static String LOG4J_PATH;   
    public static String DEFAULT_OS; 
    public static String EXTENT_REPORT_PATH_WIN; 
    public static String EXTENT_REPORT_PATH_MAC;
    public static String EXTENT_REPORT_FILENAME; 
    public static String EXTENT_REPORT_PATH; 
    
    public static String TEST_DATA_PATH;
    public static String SCREENSHOT_PATH;
    public static String APP_URL;   
    public static int PAGE_LOAD_TIMEOUT;   
    public static boolean SOFT_ASSERT = false;  //set via testng parameter
    
    public TestConfig(String testconfigFile) {
 		ObjectReader.reader = new PropertyReader(testconfigFile);
 		TestConfig.IMPLICIT_WAIT_TIMEOUT = ObjectReader.reader.getImpliciteWait();
 		TestConfig.EXPLICIT_WAIT_TIME_OUT = ObjectReader.reader.getExplicitWait();
 		TestConfig.DRIVER_BIN_PATH = ObjectReader.reader.getDriverBinPath();
 		TestConfig.LOG4J_PATH = ObjectReader.reader.getLog4jPath();
 		TestConfig.EXTENT_REPORT_PATH = ObjectReader.reader.getExtentReportPath();
 		
 		TestConfig.DEFAULT_OS = ObjectReader.reader.getDefaultOS();
 		TestConfig.EXTENT_REPORT_PATH_WIN = ObjectReader.reader.getExtentReportPathWin();
 		TestConfig.EXTENT_REPORT_PATH_MAC = ObjectReader.reader.getExtentReportPathMac();
 		TestConfig.EXTENT_REPORT_FILENAME = ObjectReader.reader.getExtentReportFileName();		
 		TestConfig.TEST_DATA_PATH = ObjectReader.reader.getTestDataPath();
 		TestConfig.SCREENSHOT_PATH = ObjectReader.reader.getScreenShotPath();
 		TestConfig.APP_URL = ObjectReader.reader.getUrl();  	
 		TestConfig.PAGE_LOAD_TIMEOUT = ObjectReader.reader.getPageLoadTime();  	    	
    }
    
    public static String getTestDataPath(){
    	return TEST_DATA_PATH;
    }
    
    public static void setSoftAssert(String softassert){
    	if (softassert.equalsIgnoreCase("true")){
    		System.out.println("Setting SoftAssert to true");
     		SOFT_ASSERT = true;
    	} else
    	{  SOFT_ASSERT = false;	
    	System.out.println("Setting SoftAssert to false");}
    }
    
     
}