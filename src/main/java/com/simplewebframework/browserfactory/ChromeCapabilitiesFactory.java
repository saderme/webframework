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

package com.simplewebframework.browserfactory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.simplewebframework.configClass.AppConfig;
import com.simplewebframework.configClass.DriverConfig;

public class ChromeCapabilitiesFactory implements ICapabilitiesFactory {

    public DesiredCapabilities createCapabilities(final DriverConfig webDriverConfig) {

        DesiredCapabilities capability = null;
        capability = DesiredCapabilities.chrome();
        capability.setBrowserName(DesiredCapabilities.chrome().getBrowserName());

        ChromeOptions options = new ChromeOptions();
/*        if (webDriverConfig.getUserAgentOverride() != null) {
            options.addArguments("--user-agent=" + webDriverConfig.getUserAgentOverride());
        }*/

        capability.setCapability(ChromeOptions.CAPABILITY, options);

/*        if (webDriverConfig.isEnableJavascript()) {
            capability.setJavascriptEnabled(true);
        } else {
            capability.setJavascriptEnabled(false);
        }*/

        capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

/*        if (webDriverConfig.getBrowserVersion() != null) {
            capability.setVersion(webDriverConfig.getBrowserVersion());
        }

        if (webDriverConfig.getWebPlatform() != null) {
            capability.setPlatform(webDriverConfig.getWebPlatform());
        }

        if (webDriverConfig.getProxyHost() != null) {
            Proxy proxy = webDriverConfig.getProxy();
            capability.setCapability(CapabilityType.PROXY, proxy);
        }*/

        if (AppConfig.DRIVER_BIN_PATH != null) {
            capability.setCapability("chrome.binary", AppConfig.DRIVER_BIN_PATH);
        }

        // Set ChromeDriver for local mode
   //     if (webDriverConfig.getMode() == DriverMode.LOCAL) {
            String chromeDriverPath = AppConfig.DRIVER_BIN_PATH;
            if (chromeDriverPath == null) {
                if (System.getenv("webdriver.chrome.driver") != null) {
				    System.out.println("get Chrome driver from property:" + System.getenv("webdriver.chrome.driver"));
				    System.setProperty("webdriver.chrome.driver", System.getenv("webdriver.chrome.driver"));
				} 
            } else {
                System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            }
   //     }

        return capability;
    }

 /*   public void handleExtractResources() throws IOException {
        String dir = this.getClass().getResource("/").getPath();
        dir = FileUtility.decodePath(dir);

        if (!new File(dir).exists()) {
            System.out.println("extracting chrome resources in " + dir);
            FileUtility.extractJar(dir, WebDriverExternalResources.class);
        }

        if (!new File(dir + OSUtility.getSlash() + "chromedriver.exe").exists()) {
            FileUtility.extractJar(dir, WebDriverExternalResources.class);
        }

        if (OSUtility.isWindows()) {
            System.setProperty("webdriver.chrome.driver", dir + "\\chromedriver.exe");
        } else if (OSUtility.isMac()) {
            System.setProperty("webdriver.chrome.driver", dir + "/mac/chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", dir + "/chromedriver");
            new File(dir + "/chromedriver").setExecutable(true);
        }
    }*/

}