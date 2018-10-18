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

package com.simplewebframework.core;

import java.util.Collection;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

import org.testng.Assert;
import org.testng.Reporter;

/**
 * soft assert - test case continues when validation fails. soft assert is enabled only if context softAssertEnabled is
 * set to true.
 */
public class CustomAssertion {
	
    public static void assertThat(final String reason, final boolean assertion) {
            MatcherAssert.assertThat(reason, assertion);
    }

    public static <T> void assertThat(final String reason, final T actual, final Matcher<? super T> matcher) {
            MatcherAssert.assertThat(reason, actual, matcher);
    }

    public static <T> void assertThat(final T actual, final Matcher<? super T> matcher) {
            MatcherAssert.assertThat(actual, matcher);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
}
