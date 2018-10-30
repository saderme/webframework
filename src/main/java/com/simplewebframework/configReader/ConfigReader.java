package com.simplewebframework.configReader;

import com.simplewebframework.core.BrowserType;

public interface ConfigReader {
	
	public int getImpliciteWait();
	public int getExplicitWait();
	public int getPageLoadTime();
	public BrowserType getBrowserType();
	public String getDriverBinPath(); 
	public String getScreenShotPath();
	public String getExtentReportPath();
	public String getDefaultOS();
	public String getExtentReportPathWin();
	public String getExtentReportPathMac();
	public String getExtentReportFileName();
	public String getTestDataPath();
	public String getLog4jPath();
	public String getUrl();
	public String getUserName();
	public String getPassword();

}
