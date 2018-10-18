package com.simplewebframework.configReader;

import com.simplewebframework.core.BrowserType;
/**
 * 
 * @author Bhanu Pratap Singh
 *
 */
public interface ConfigReader {
	
	public int getImpliciteWait();
	public int getExplicitWait();
	public int getPageLoadTime();
	public BrowserType getBrowserType();
	public String getDriverBinPath(); 
	public String getScreenShotPath();
	public String getExtentReportPath();
	public String getTestDataPath();
	public String getLog4jPath();
	public String getUrl();
	public String getUserName();
	public String getPassword();

}
