package com.simplewebframework.extent;

import org.apache.commons.net.io.CRLFLineReader;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.xml.XmlTest;

import java.io.IOException;
import java.lang.reflect.Method;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.simplewebframework.configClass.TestConfig;
import com.simplewebframework.core.BaseTestPlan;
import com.simplewebframework.core.TestLogger;
import com.simplewebframework.core.WebUIDriver;
import com.simplewebframework.webelements.webBasePage;

public class ExtentListener extends BaseTestPlan  implements IInvokedMethodListener,ITestListener,ISuiteListener  {

	private Logger applog =  TestLogger.getLogger(ExtentListener.class);
	private TestConfig testconfig= new TestConfig("testconfig.properties");	
	
	  //Extent Report Declarations
    private static ExtentReports extent = ExtentManager.createInstance();
    private  ThreadLocal<ExtentTest> test = new ThreadLocal<>();
 
    public ExtentListener()
    {
    }
    
    /*********************************************************************
     * <suite> - Fired first (before @BeforeSuite), when <suite>  starts.
     ********************************************************************/
    @Override
	public void onStart(ISuite suite) {
       	TestLogger.logRepInfo("$$$$$$ Suite " + suite.getName()+" started.$$$$$$"+ "\n");
		TestLogger.logAppLogInfo(applog, "$$$$$ Suite " + suite.getName()+" started.$$$$$");
	}
    
    /*********************************************************************
     * <test> - runs ONCE per <test> on start. Runs before @BeforeTest
     ********************************************************************/
    @Override
    public void onStart(ITestContext iTestContext) {
    	TestLogger.logRepInfo("****** Test " + iTestContext.getCurrentXmlTest().getName()+" started. ***"+ "\n");
		TestLogger.logAppLogInfo(applog, "****** Test " + iTestContext.getCurrentXmlTest().getName()+" started. ***");
        iTestContext.setAttribute("WebDriver", WebUIDriver.getWebDriver());
    }
    
    /*********************************************************************
     * @TEST - Fired before every @TEST starts. Runs after @BeforeMethod
     ********************************************************************/
    @Override
    public synchronized void onTestStart(ITestResult result) {
    	TestLogger.logRepInfo("*** Test " + result.getMethod().getMethodName() + " started. ***" + "\n");
		TestLogger.logAppLogInfo(applog, "*** Test" + result.getMethod().getMethodName() +" started. ***");
    	ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
        test.set(extentTest);
    }
 
    /*********************************************************************
     * Fired after @Test
     ********************************************************************/
    @Override
    public synchronized void onTestSuccess(ITestResult testResult) {
    	TestLogger.logRepInfo(testResult.getMethod().getMethodName() + " succeeded."+ "\n");
		TestLogger.logAppLogInfo(applog, testResult.getMethod().getMethodName() + " succeeded.");
        
		String imagePath = captureScreen(WebUIDriver.getWebDriver(), testResult.getName());
		try {
			test.get().pass("Test passed").addScreenCaptureFromPath(imagePath);
			//test.get().addScreenCaptureFromPath(imagePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /*********************************************************************
     * Fired after @Test
     ********************************************************************/
    @Override
    public synchronized void onTestFailure(ITestResult testResult) {
    	TestLogger.logRepInfo(testResult.getMethod().getMethodName()  + " failed."+ "\n");
		TestLogger.logAppLogError(applog, testResult.getMethod().getMethodName()  + " failed.");

		String imagePath = captureScreen(WebUIDriver.getWebDriver(), testResult.getName());
		try {
			test.get().fail(testResult.getThrowable()).addScreenCaptureFromPath(imagePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    /*********************************************************************
     * Fired after @Test
     ********************************************************************/
    @Override
    public synchronized void onTestSkipped(ITestResult result) {
       	TestLogger.logRepInfo(result.getMethod().getMethodName() + " skipped."+ "\n");
		TestLogger.logAppLogWarning(applog, result.getMethod().getMethodName() + " skipped.");
        test.get().skip(result.getThrowable());
    }
 
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }


    @Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    	
   //    	TestLogger.logRepInfo("Before Invoking - " + testResult.getTestClass().getName() + " => " + method.getTestMethod().getMethodName()+ "\n");
	//	TestLogger.logAppLogInfo(applog, "Before Invoking - " + testResult.getTestName() + " => " + method.getTestMethod().getMethodName());
	}
    @Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    //   	TestLogger.logRepInfo("After Invoking - " + testResult.getTestClass().getName() + " => " + method.getTestMethod().getMethodName()+ "\n");
	//	TestLogger.logAppLogInfo(applog, "After Invoking - " + testResult.getTestClass().getName() + " => " + method.getTestMethod().getMethodName());
		//extent.flush();
	}

    /*********************************************************************
     * <test> - runs ONCE per <test> on finish.
     ********************************************************************/
    @Override
    public void onFinish(ITestContext iTestContext) {
    	TestLogger.logRepInfo("****** Test " + iTestContext.getName()+" finished. ******"+ "\n");
		TestLogger.logAppLogInfo(applog, "****** Test" + iTestContext.getName()+" finished. ******");
        extent.flush();
    }	
    
    /*********************************************************************
     * <suite> - Runs when the Suit ends. Runs after @AfterSuite
     ********************************************************************/
    @Override
	public void onFinish(ISuite suite) {
       	TestLogger.logRepInfo("$$$$$$ Suite " + suite.getName()+" finished.$$$$$$"+ "\n");
		TestLogger.logAppLogInfo(applog, "$$$$$ Suite " + suite.getName()+" finished.$$$$$");
	}
    
}
