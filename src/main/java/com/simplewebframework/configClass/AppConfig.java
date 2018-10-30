package com.simplewebframework.configClass;

import com.simplewebframework.configReader.ObjectReader;
import com.simplewebframework.configReader.PropertyReader;

/**
 * @author Mahomed Sader
 * Retrieves all the application config details from the config file.
 * Config file path is specified in PropertyReader and name is passed from BaseTestPlan  
 */
public class AppConfig {

    public static int DEFAULT_IMPLICIT_WAIT_TIMEOUT = 5;
    public static int DEFAULT_EXPLICIT_WAIT_TIME_OUT = 15;
    public static int DEFAULT_PAGE_LOAD_TIMEOUT = 90;
    public static int WEB_SESSION_TIMEOUT = 30 * 1000;
    public static int IMPLICIT_WAIT_TIMEOUT;
    public static int EXPLICIT_WAIT_TIME_OUT; 
    public static String DRIVER_BIN_PATH;   
    public static String APP_URL;   
    public static int PAGE_LOAD_TIMEOUT;   

    public AppConfig(String configFile) {
 		ObjectReader.reader = new PropertyReader(configFile);
 		AppConfig.IMPLICIT_WAIT_TIMEOUT = ObjectReader.reader.getImpliciteWait();
 		AppConfig.EXPLICIT_WAIT_TIME_OUT = ObjectReader.reader.getExplicitWait();
 		AppConfig.DRIVER_BIN_PATH = ObjectReader.reader.getDriverBinPath();
 		AppConfig.APP_URL = ObjectReader.reader.getUrl();  	
 		AppConfig.PAGE_LOAD_TIMEOUT = ObjectReader.reader.getPageLoadTime();  	    	
    }
    
    
    
}