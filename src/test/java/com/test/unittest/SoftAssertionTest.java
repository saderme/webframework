/*
 * Copyright 2015 www.seleniumtests.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.test.unittest;

import com.simplewebframework.core.BaseTestPlan;
import com.simplewebframework.core.CustomAssertion;
import com.simplewebframework.core.TestLogger;
import org.testng.annotations.Test;

import static com.simplewebframework.core.CustomAssertion.*;
import static org.hamcrest.CoreMatchers.is;

/**
 * Demonstrate test execution continues even though assertions fail.
 *
 * <p/>Date: 10/2/13 Time: 4:59 PM
 */
public class SoftAssertionTest extends BaseTestPlan {

    /**
     * Continues with test execution even though assertions fail.
     */
    @Test(groups = "softAssertionTest", description = "Continues with test execution even though assertions fail")
    public void softAssertionTest() {
        assertThat("selenium", is("selenium"));
        assertEquals(true, false, "boolean test failure");
        assertTrue(false, "another boolean test failure");
        assertEquals("selenium", "qtp", "universal test failure :)");
        assertTrue(1 == 1, "never fails");
        TestLogger.logRepInfo(
            "This message is logged after initial assertion failures. Hence test execution continues even in the wake of test failures");
        // With out asserting verification failures, test would not be marked as fail 
       // assert CustomAssertion.getVerificationFailures().isEmpty():"Verification Errors";
    }
}
