package com.seo.selenium.ui;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class SeleniumUtils {

	public static FirefoxDriver BuildDriver()
	{
		final FirefoxProfile firefoxProfile = new FirefoxProfile();
		firefoxProfile.setPreference("xpinstall.signatures.required", false);
		return new FirefoxDriver(firefoxProfile);
	}
	
}
