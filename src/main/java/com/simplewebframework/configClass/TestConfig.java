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
    public static String EXTENT_REPORT_PATH;  
    public static String TEST_DATA_PATH;
    public static String APP_URL;   
    public static int PAGE_LOAD_TIMEOUT;   

    public TestConfig(String testconfigFile) {
 		ObjectReader.reader = new PropertyReader(testconfigFile);
 		TestConfig.IMPLICIT_WAIT_TIMEOUT = ObjectReader.reader.getImpliciteWait();
 		TestConfig.EXPLICIT_WAIT_TIME_OUT = ObjectReader.reader.getExplicitWait();
 		TestConfig.DRIVER_BIN_PATH = ObjectReader.reader.getDriverBinPath();
 		TestConfig.LOG4J_PATH = ObjectReader.reader.getLog4jPath();
 		TestConfig.EXTENT_REPORT_PATH = ObjectReader.reader.getExtentReportPath();
 		TestConfig.TEST_DATA_PATH = ResourceHelper.getResourcePath(ObjectReader.reader.getTestDataPath());
 		TestConfig.APP_URL = ObjectReader.reader.getUrl();  	
 		TestConfig.PAGE_LOAD_TIMEOUT = ObjectReader.reader.getPageLoadTime();  	    	
    }
     
}