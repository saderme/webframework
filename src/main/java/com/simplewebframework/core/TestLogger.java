package com.simplewebframework.core;

import java.util.Date;

import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Reporter;

import com.simplewebframework.configClass.TestConfig;
import com.simplewebframework.helpers.ResourceHelper;

public class TestLogger {

	private static boolean root=false;
	
	public static Logger getLogger(Class cls){
		if(root){
			return LogManager.getLogger(cls);
		}
		PropertyConfigurator.configure(ResourceHelper.getResourcePath(TestConfig.LOG4J_PATH));
		root = true;
		return LogManager.getLogger(cls);
	}

    /**
     * Log info.
     *
     * @param  message
     */
    public static void logInfo(String message) {
        if (message == null) {
            message = "";
        }
        message = "<li><font color='#00cd00'>" + message + "</font></li>";
    	Reporter.log((message), false);
    }

    /**
     * error Logger.
     *
     * @param  message
     */
    public static void logError(String message) {
        message = message.replaceAll("\\n", "<br/>");
        message = "<li><b><font color='#6600CC'>" + message + "</font></b></li>";
    	Reporter.log((message), false);
    }
    
    /**
     * warn Logger.
     *
     * @param  message
     */
    public static void logLoggerWarning(Logger logger, String message) {
    	logger.warn(message);
    }    
   
    /**
     * Error Logger.
     *
     * @param  message
     */
    public static void logLoggerError(Logger logger, String message) {
    	logger.error(message);
    }  
    
    /**
     * Info Logger.
     *
     * @param  message
     */
    public static void logLoggerInfo(Logger logger, String message) {
    	logger.info(message);
    }  
    
    /**
     * Fatal Logger.
     *
     * @param  message
     */
    public static void logLoggerFatal(Logger logger, String message) {
    	logger.fatal(message);
    }     
    
    
    /**
     * warn Logger.
     *
     * @param  message
     */
    public static void logFatal(String message) {
    	
        message = "<span style=\"font-weight:bold;color:#cc0052;\">" + message + "</span>";
        
    	Reporter.log((message),true);
    	
    }   
    
    /**
     * warn Logger.
     *
     * @param  message
     */
    public static void logConsole(String message) {
    	System.out.println(message);
    }        
    
    
	public static void main(String[] args) throws Exception {
		//LoginPage lp = new LoginPage();
		
	    Logger log = TestLogger.getLogger(TestLogger.class);
		log.info("logger is configured");
		log.info("logger is configured");
		log.info("logger is configured");
		
	}
}