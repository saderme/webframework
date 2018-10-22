package com.test.testscripts.testfactory;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.simplewebframework.core.BaseTestPlan;

public class ex_TestFactory extends BaseTestPlan {
    @Factory
    public static Object[] produce() {
        return new Object[]{
                new TestInstance(),
                new TestInstance(),
                new TestInstance(),
                new TestInstance(),
                new TestInstance()
        };
    }

    public static class TestInstance {
        @Test
        void testMethod1() {
            System.err.println(debug("TestMethod1"));
        }

        private String debug(String methodName) {
            return methodName + " running on Thread " + Thread.currentThread().getId() +
                    " with instance as " + this;
        }

        @Test(dependsOnMethods = "testMethod1")
        void testMethod2() {
            System.err.println(debug("TestMethod2"));
        }

    }
}