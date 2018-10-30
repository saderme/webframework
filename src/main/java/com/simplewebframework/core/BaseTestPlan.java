package com.simplewebframework.core;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IAttributes;
import org.testng.ISuite;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.xml.XmlTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Test;
import com.simplewebframework.configClass.AppConfig;
import com.simplewebframework.configClass.TestConfig;
import com.simplewebframework.extent.ExtentManager;
import com.simplewebframework.webelements.webBasePage;

/**
 * @author GB118656
 * This class is the TestNG Base Test Class. 
 * It contains common functions applicable to all TestNG tests.
 */
public abstract class BaseTestPlan {
	
	/*** Configuration Details ***/
	protected static AppConfig appconfig = new AppConfig("appconfig.properties");
	protected static TestConfig testconfig= new TestConfig("testconfig.properties");
	private static final Logger applog = TestLogger.getLogger(BaseTestPlan.class);
    private Date start;
    
    public BaseTestPlan()
    {
    }

    /*********************************************************************
     * SUITE - Runs ONCE when the Suit starts. Runs after ExtentListener -> Suite_onStart
     ********************************************************************/
    @BeforeSuite(alwaysRun = true)
    public void beforeTestSuite(final ITestContext testContext) throws IOException {
     	start = new Date();
        TestLogger.logAppLogInfo(applog, "@@BeforeSuite - Test Suite Started: " + start.getTime());

    }
    
    /*********************************************************************
     * TEST - Runs ONCE for each <test> before any @Test is run .Runs after ExtentListener -> Test_onStart
     ********************************************************************/
    @BeforeTest(alwaysRun = true)
	@Parameters({"browser","softassert"})
    public void beforeTest(final ITestContext testContext, final XmlTest xmlTest, @Optional("Chrome") String browser, @Optional("false") String softassert) throws Exception {
    	TestConfig.setSoftAssert(softassert);
    	//webDriver =  WebUIDriver.createDriver(browser);
        TestLogger.logAppLogInfo(applog, "@BeforeTest - " + xmlTest.getName() + " started. ***");
    }
    

    /*********************************************************************
     * TEST - Runs before every @Test in <test>
     ********************************************************************/
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod(final Object[] parameters, final Method method, final ITestContext testContex, final XmlTest xmlTest) {
        TestLogger.logAppLogInfo(applog, "@BeforeMethod - " +  Thread.currentThread() + " Method " + method.getName() + " started. ***");
    }
    

    /*********************************************************************
     * TEST - Runs after every @Test in <test>
     ********************************************************************/    
    @AfterMethod(alwaysRun = true)
    public void afterTestMethod(final Object[] parameters, final Method method, final ITestContext testContex, final XmlTest xmlTest, ITestResult result) throws Exception {
        // WebUIDriver.cleanUp();
         TestLogger.logAppLogInfo(applog, "@AfterMethod - " + Thread.currentThread() + method.getName() + " finished.");
    }
    
    /*********************************************************************
     * TEST - Runs ONCE for each <test> after all @Test have run. Runs after ExtentListener -> Test_onFinish
     ********************************************************************/
    /*    @AfterTest(alwaysRun = true)
    public void afterTest() {
        WebUIDriver.cleanUp();    	
    }*/
    
    /*********************************************************************
     * <suite> - Runs ONCE when the Suit finishes. Runs before ExtentListener -> Suite_onFinish
     ********************************************************************/
    @AfterSuite(alwaysRun = true)
    public void afterTestSuite() {
        TestLogger.logAppLogInfo(applog, "@AfterSuite - Test Suite Execution Time: " + (new Date().getTime() - start.getTime()) / 1000 / 60 + " minutes. ***");
    }
    
	public synchronized static String captureScreen(WebDriver driver,String fileName){
		if(driver == null){
			applog.info("driver is null..");
			return null;
		}
		if(fileName==""){
			fileName = "blank";
		}
		TestLogger.logRepInfo("captureScreen method called");
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss_SSSS");
		File screFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try{
			destFile = new File(TestConfig.SCREENSHOT_PATH+"/"+fileName+"_"+formater.format(calendar.getTime())+".png");
			Files.copy(screFile.toPath(), destFile.toPath());
			TestLogger.logRepInfo("<a href='"+destFile.getAbsolutePath()+"'><img src='"+destFile.getAbsolutePath()+"'height='50%' width='50%'/></a>");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return destFile.toString();
	}	
}