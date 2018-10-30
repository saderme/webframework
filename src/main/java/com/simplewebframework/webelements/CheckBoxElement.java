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

package com.simplewebframework.webelements;

import org.openqa.selenium.By;

import com.simplewebframework.core.TestLogger;

public class CheckBoxElement extends HtmlElement {

    public CheckBoxElement(final String label, final By by) {
        super(label, by);
    }

    public void check() {
    	TestLogger.logRepInfo("check " + toHTML());
        if (!isSelected()) {
            super.click();
        }
    }

    @Override
    public void click() {
    	TestLogger.logRepInfo("click on " + toHTML());
        super.click();
    }

    public boolean isSelected() {
        findElement();
        return element.isSelected();
    }

    public void uncheck() {
    	TestLogger.logRepInfo("uncheck " + toHTML());
        if (isSelected()) {
            super.click();
        }
    }
}
