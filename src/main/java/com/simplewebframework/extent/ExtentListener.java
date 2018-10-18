package com.simplewebframework.extent;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
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

public class ExtentListener extends BaseTestPlan implements IInvokedMethodListener,ITestListener,ISuiteListener {

	private Logger log =  TestLogger.getLogger(ExtentListener.class);
	private WebDriver webDriver;
	  //Extent Report Declarations
 //   private static ExtentReports extent = ExtentManager.createInstance();
 //  private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    //Before starting all tests, below method runs.
    @Override
    public void onStart(ITestContext iTestContext) {
		Reporter.log(iTestContext.getCurrentXmlTest().getName()+" Class Started..");
		log.info(iTestContext.getCurrentXmlTest().getName()+" Class Started..");
        iTestContext.setAttribute("WebDriver", driver);
    }
    
    //After ending all tests, below method runs.
    @Override
    public void onFinish(ITestContext iTestContext) {
        Reporter.log(iTestContext.getName()+" Test Finished..");
        log.info(iTestContext.getName()+" Test Finished..");
        extent.flush();
    }	
	
    @Override
    public synchronized void onTestStart(ITestResult result) {
		Reporter.log(result.getMethod().getMethodName() + " start");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
        test.set(extentTest);
    }
 
    @Override
    public synchronized void onTestSuccess(ITestResult result) {
		Reporter.log(result.getMethod().getMethodName() + " succeeded");
		log.info(result.getMethod().getMethodName() + " succeeded");
        test.get().pass("Test passed");
    }
 
    @Override
    public synchronized void onTestFailure(ITestResult result) {
		Reporter.log((result.getMethod().getMethodName()  + " failed"));
		log.error((result.getMethod().getMethodName()  + " failed"));
        test.get().fail(result.getThrowable());
    }
 
    @Override
    public synchronized void onTestSkipped(ITestResult result) {
		Reporter.log(result.getMethod().getMethodName() + " skipped");
		log.warn(result.getMethod().getMethodName() + " skipped");
        test.get().skip(result.getThrowable());
    }
 
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }
	
	public void onStart(ISuite suite) {
		Reporter.log(suite.getName()+" Suite Started..");
		log.info(suite.getName()+" Suite Started..");	
	}

	public void onFinish(ISuite suite) {
		Reporter.log(suite.getName()+" Suite Finished..");
		log.info(suite.getName()+" Suite Finished..");	
	}

	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		Reporter.log(" Before Invocation..." + testResult.getTestClass().getName() + 
				" => " + method.getTestMethod().getMethodName());
		log.info(" Before Invocation.." + testResult.getTestClass().getName() + 
				" => " + method.getTestMethod().getMethodName());	
	}

	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		Reporter.log(" After Invocation..." + testResult.getTestClass().getName() + 
				" => " + method.getTestMethod().getMethodName());
		log.info(" After Invocation.." + testResult.getTestClass().getName() + 
				" => " + method.getTestMethod().getMethodName());			
	}

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
	
}
