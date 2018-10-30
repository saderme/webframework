package com.simplewebframework.configReader;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.simplewebframework.core.BrowserType;
import com.simplewebframework.helpers.ResourceHelper;

public class PropertyReader implements ConfigReader {

	private static FileInputStream file;
	public static Properties OR;

	public PropertyReader() {
		try {
			String filePath = ResourceHelper.getResourcePath("src\\main\\resources\\configfile\\config.properties");
			file = new FileInputStream(new File(filePath));
			OR = new Properties();
			OR.load(file);
			
/*			String filePath1 = ResourceHelper.getResourcePath("src\\main\\resources\\configfile\\config1.properties");
			file = new FileInputStream(new File(filePath1));
			OR = new Properties();
			OR.load(file);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PropertyReader(String propFile) {
		try {
			String filePath = ResourceHelper.getResourcePath("src\\main\\resources\\configfile\\" + propFile);
			file = new FileInputStream(new File(filePath));
			OR = new Properties();
			OR.load(file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getImpliciteWait() {
		return Integer.parseInt(OR.getProperty("implicitwait"));
	}

	public int getExplicitWait() {
		return Integer.parseInt(OR.getProperty("explicitwait"));
	}

	public String getExtentReportPath() {
		if(System.getProperty("extentreportpath")!=null){
			return System.getProperty("extentreportpath");
		}
		return OR.getProperty("extentreportpath");
	}

	public String getTestDataPath() {
		if(System.getProperty("testdatapath")!=null){
			return System.getProperty("testdatapath");
		}
		return ResourceHelper.getResourcePath(OR.getProperty("testdatapath"));
	}
	
	
	
	public int getPageLoadTime() {
		return Integer.parseInt(OR.getProperty("pageloadtime"));
	}

	public String getDriverBinPath() {
		if(System.getProperty("driverbinpath")!=null){
			return System.getProperty("driverbinpath");
		}
		return OR.getProperty("driverbinpath");
	}
	
	public String getScreenShotPath() {
		if(System.getProperty("screenshotpath")!=null){
			return System.getProperty("screenshotpath");
		}
		return ResourceHelper.getResourcePath((OR.getProperty("screenshotpath")));
	}	
	
	
	public BrowserType getBrowserType() {
		return BrowserType.valueOf(OR.getProperty("browserType"));
	}
	
	public String getUrl() {
		if(System.getProperty("url")!=null){
			return System.getProperty("url");
		}
		return OR.getProperty("applicationUrl");
	}

	public String getUserName() {
		if(System.getProperty("userName")!=null){
			return System.getProperty("userName");
		}
		return OR.getProperty("userName");
	}

	public String getPassword() {
		if(System.getProperty("password")!=null){
			return System.getProperty("password");
		}
		return OR.getProperty("password");
	}

	public String getLog4jPath() {
		
		if(System.getProperty("log4jpath")!=null){
			return System.getProperty("log4jpath");
		}
		return ResourceHelper.getResourcePath(OR.getProperty("log4jpath"));
	}

	@Override
	public String getDefaultOS() {
		// TODO Auto-generated method stub
		if(System.getProperty("defaultOS")!=null){
			return System.getProperty("defaultOS");
		}else
		{
			System.setProperty("defaultOS", OR.getProperty("defaultOS"));
		}
		return OR.getProperty("defaultOS");
	}

	@Override
	public String getExtentReportPathWin() {
		// TODO Auto-generated method stub
		if(System.getProperty("extentreportpathWin")!=null){
			return System.getProperty("extentreportpathWin");
		}
		return ResourceHelper.getResourcePath(OR.getProperty("extentreportpathWin"));
	}

	@Override
	public String getExtentReportPathMac() {
		// TODO Auto-generated method stub
		if(System.getProperty("extentreportpathMac")!=null){
			return System.getProperty("extentreportpathMac");
		}
		return OR.getProperty("extentreportpathMac");
	}

	@Override
	public String getExtentReportFileName() {
		if(System.getProperty("extentreportpathFileName")!=null){
			return System.getProperty("extentreportpathFileName");
		}
		return OR.getProperty("extentreportpathFileName");
	}
	
}
