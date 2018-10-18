package com.simplewebframework.helpers;

import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;
import com.simplewebframework.core.TestLogger;
import com.simplewebframework.core.WebUIDriver;

public class Retry implements IRetryAnalyzer{
	private int retryCount = 0;
	private int maxRetryCount = 3;
	
	private Logger log = (Logger) TestLogger.getLogger(Retry.class);

	
    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {                      //Check if test not succeed
            if (retryCount < maxRetryCount) {                //Check if maxtry count is reached
            	retryCount++;                                //Increase the maxTry count by 1
                iTestResult.setStatus(ITestResult.FAILURE);  //Mark test as failed
                extendReportsFailOperations(iTestResult);    //ExtentReports fail operations
                return true;                                 //Tells TestNG to re-run the test
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);      //If test passes, TestNG marks it as passed
        }
        return false;
    }
    
    public void extendReportsFailOperations (ITestResult iTestResult) {
        Object testClass = iTestResult.getInstance();
        WebDriver webDriver = WebUIDriver.getWebDriver();
        String base64Screenshot = "data:image/png;base64,"+((TakesScreenshot)webDriver).getScreenshotAs(OutputType.BASE64);
     //   ExtentTestManager.getTest().log(Status.FAIL,"Test Failed",ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
    }
    
	public String getResultStatusName(int status){
		String resultName = null;
		if(status == 1){
			resultName = "SUCCESS";
		}
		if(status == 2){
			resultName = "FAILURE";
		}
		if(status == 3){
			resultName = "SKIP";
		}
		return resultName;
	}

}
 