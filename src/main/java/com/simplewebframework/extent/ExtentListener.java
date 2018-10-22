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
import java.lang.reflect.Method;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.simplewebframework.core.BaseTestPlan;
import com.simplewebframework.core.TestLogger;

public class ExtentListener extends BaseTestPlan implements IInvokedMethodListener,ITestListener,ISuiteListener  {

	private Logger applog =  TestLogger.getLogger(ExtentListener.class);
	private WebDriver webDriver;
	  //Extent Report Declarations
 //   private static ExtentReports extent = ExtentManager.createInstance();
 //  private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    //Before starting all tests, below method runs.
    @Override
    public void onStart(ITestContext iTestContext) {
		Reporter.log("Class " + iTestContext.getCurrentXmlTest().getName()+" started.."+ "\n");
		applog.info("Class " + iTestContext.getCurrentXmlTest().getName()+" started..");
        iTestContext.setAttribute("WebDriver", driver);
    }
    
    //After ending all tests, below method runs.
    @Override
    public void onFinish(ITestContext iTestContext) {
        Reporter.log("Test " + iTestContext.getName()+" finished."+ "\n");
        applog.info("Test" + iTestContext.getName()+" finished.");
        extent.flush();
    }	
	
    @Override
    public synchronized void onTestStart(ITestResult result) {
		Reporter.log( "Test " + result.getMethod().getMethodName() + " started." + "\n");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
        test.set(extentTest);
    }
 
    @Override
    public synchronized void onTestSuccess(ITestResult result) {
		Reporter.log(result.getMethod().getMethodName() + " succeeded."+ "\n");
		applog.info(result.getMethod().getMethodName() + " succeeded.");
        test.get().pass("Test passed");
    }
 
    @Override
    public synchronized void onTestFailure(ITestResult result) {
		Reporter.log((result.getMethod().getMethodName()  + " failed."+ "\n"));
		applog.error((result.getMethod().getMethodName()  + " failed."));
        test.get().fail(result.getThrowable());
    }
 
    @Override
    public synchronized void onTestSkipped(ITestResult result) {
		Reporter.log(result.getMethod().getMethodName() + " skipped."+ "\n");
		applog.warn(result.getMethod().getMethodName() + " skipped.");
        test.get().skip(result.getThrowable());
    }
 
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }
	
	public void onStart(ISuite suite) {
		Reporter.log("Suite " + suite.getName()+" started."+ "\n");
		applog.info("Suite " + suite.getName()+" started.");	
	}

	public void onFinish(ISuite suite) {
		Reporter.log("Suite " + suite.getName()+" finished."+ "\n");
		applog.info("Suite " + suite.getName()+" finished.");	
	}

	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		Reporter.log("Before Invoking - " + testResult.getTestClass().getName() + " => " + method.getTestMethod().getMethodName()+ "\n");
		applog.info("Before Invoking - " + testResult.getTestClass().getName() + " => " + method.getTestMethod().getMethodName());	
	}

	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		Reporter.log("After Invoking - " + testResult.getTestClass().getName() + " => " + method.getTestMethod().getMethodName()+ "\n");
		applog.info("After Invoking - " + testResult.getTestClass().getTestName() + " => " + method.getTestMethod().getMethodName());

	}
	
}
