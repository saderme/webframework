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

	public DesiredCapabilities createCapabilities() {

		DesiredCapabilities capability = null;
		capability = DesiredCapabilities.chrome();
		capability.setBrowserName(DesiredCapabilities.chrome().getBrowserName());

		ChromeOptions options = new ChromeOptions();
		capability.setCapability(ChromeOptions.CAPABILITY, options);
		capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		if (AppConfig.DRIVER_BIN_PATH != null) {
			capability.setCapability("chrome.binary", AppConfig.DRIVER_BIN_PATH);
		}

		String chromeDriverPath = AppConfig.DRIVER_BIN_PATH;
		if (chromeDriverPath == null) {
			if (System.getenv("webdriver.chrome.driver") != null) {
				System.out.println("get Chrome driver from property:" + System.getenv("webdriver.chrome.driver"));
				System.setProperty("webdriver.chrome.driver", System.getenv("webdriver.chrome.driver"));
			}
		} else {
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		}

		return capability;
	}
}
