package com.test.testfactory;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.application.dataprovider.DataProviderCentral;

public class ex_ExternalDataProviderTest
{
    private int param;
 
    @Factory(dataProvider = "dataMethod",dataProviderClass = DataProviderCentral.class)
    public ex_ExternalDataProviderTest(int param) {
        this.param = param;
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