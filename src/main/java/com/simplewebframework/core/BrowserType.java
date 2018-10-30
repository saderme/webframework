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

public enum BrowserType{
    FireFox("Firefox"),
    Marionette("Marionette"),
    InternetExplore("InternetExplore"),
    Chrome("Chrome"),
    HtmlUnit("HtmlUnit"),
    Safari("Safari"),
    Android("Android"),
    IPhone("IPhone"),
    PhantomJS("PhantomJS"),
    SauceLabs("SauceLab");
	
    private String browserStrVal;
	
    public static BrowserType getBrowserType(final String BrowserStr) {
        if (BrowserStr.equalsIgnoreCase("firefox")) {
            return BrowserType.FireFox;
        }if (BrowserStr.equalsIgnoreCase("marionette")) {
            return BrowserType.Marionette;
        } else if (BrowserStr.equalsIgnoreCase("iexplore")) {
            return BrowserType.InternetExplore;
        } else if (BrowserStr.equalsIgnoreCase("chrome")) {
            return BrowserType.Chrome;
        } else if (BrowserStr.equalsIgnoreCase("htmlunit")) {
            return BrowserType.HtmlUnit;
        } else if (BrowserStr.equalsIgnoreCase("safari")) {
            return BrowserType.Safari;
        } else if (BrowserStr.equalsIgnoreCase("android")) {
            return BrowserType.Android;
        } else if (BrowserStr.equalsIgnoreCase("iphone")) {
            return BrowserType.IPhone;
        }else if (BrowserStr.equalsIgnoreCase("phantomjs")) {
            return BrowserType.PhantomJS;
        } else if (BrowserStr.equalsIgnoreCase("saucelabs")) {
            return BrowserType.SauceLabs;
        } else {
            return BrowserType.Chrome;
        }
    }
	
    BrowserType(final String type) {
        this.browserStrVal = type;
    }

    public String getBrowserStrVal() {
        return browserStrVal;
    }
    
    public void setBrowserStrVal(String type) {
    	browserStrVal = type;
    }

}
