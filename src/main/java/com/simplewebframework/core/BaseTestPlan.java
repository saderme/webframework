package com.simplewebframework.core;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.WebDriver;
import org.testng.IAttributes;
import org.testng.ISuite;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
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

/**
 * @author GB118656
 * This class is the TestNG Base Test Class. 
 * It contains common functions applicable to all TestNG tests.
 */
public abstract class BaseTestPlan {
	
	/*** Configuration Details ***/
	protected AppConfig appconfig = new AppConfig("appconfig.properties");
	protected TestConfig testconfig= new TestConfig("testconfig.properties");
	
	/*** WeDriver Instance ***/
	protected WebDriver driver = WebUIDriver.getWebDriver();
    
	/*** Extent Report ***/
    protected static ExtentReports extent = ExtentManager.createInstance();
    //protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private final Logger applog = TestLogger.getLogger(BaseTestPlan.class);
    private Date start;
    
    /**
     * @param   testContext
     *
     * @throws  IOException
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeTestSuite(final ITestContext testContext) throws IOException {
        start = new Date();
    }

/*    @BeforeSuite(alwaysRun = true)
    @Parameters( {"testconfig"})
    public void setUpSelenium(String testconfig)throws Exception {
        System.out.println("in setUpSeleniumServer: " + testconfig );
        this.testconfig = new TestConfig(testconfig);      
    } */
    
    /**
     * Before each test, set the browser
     *
     * @param  xmlTest
     */
    @BeforeTest(alwaysRun = true)
	@Parameters({"browser"})
    public void beforeTest(final ITestContext testContext, final XmlTest xmlTest, @Optional("Chrome") String browser) {
		WebUIDriver.getWebUIDriver().setBrowser(browser);
		applog.info("*** Test " + xmlTest.getName() + " started. ***");
    }
    
    /**
     * Before each method, log method entry in log file
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod(final Object[] parameters, final Method method, final ITestContext testContex, final XmlTest xmlTest) {
    	applog.info("*** " +  Thread.currentThread() + " Method " + method.getName() + " started. ***");
    }
    
    /**
     * After Suite completes, log entry in log file
     */
    @AfterSuite(alwaysRun = true)
    public void afterTestSuite() {
    	applog.info("*** Test Suite Execution Time: " + (new Date().getTime() - start.getTime()) / 1000 / 60 + " minutes. ***");
    }

    /**
     * clean up.
     *
     * @param  parameters
     * @param  method
     * @param  testContex
     * @param  xmlTest
     * @throws Exception 
     */
    
    @AfterMethod(alwaysRun = true)
    public void afterTestMethod(final Object[] parameters, final Method method, final ITestContext testContex, final XmlTest xmlTest, ITestResult result) throws Exception {
         WebUIDriver.cleanUp();
         applog.info(Thread.currentThread() + " Method " + method.getName() + " finished.");
    }

}