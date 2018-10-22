package com.test.testscripts.testfactory;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.simplewebframework.core.BaseTestPlan;

public class ex_DataProviderTest extends BaseTestPlan
{
    private int param;
 
    @Factory(dataProvider = "dataMethod")
    public ex_DataProviderTest(int param) {
        this.param = param;
    }
 
    @DataProvider
    public static Object[][] dataMethod() {
        return new Object[][] { { 0 }, { 1 } };
    }
 
    @Test
    public void testMethodOne() {
        int opValue = param + 1;
        System.out.println("Test method one output: " + opValue);
    }
 
    @Test
    public void testMethodTwo() {
        int opValue = param + 2;
        System.out.println("Test method two output: " + opValue);
    }
}