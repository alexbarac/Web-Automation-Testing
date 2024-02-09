package com.seo.selenium.ui.flow.customer;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	TestAssignProjects.class, 
	TestDisplayMode.class,
	TestViewAll.class,
	TestAccountSettings.class,
	TestOptions.class,
	TestReportsOverview.class,
	TestQuickReports.class
	})
public class SuiteCustomer {
	private static final Logger mLogger = Logger.getLogger(SuiteCustomer.class.getName());

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(SuiteCustomer.class);
		for (Failure fail : result.getFailures()) {
			mLogger.log(Level.INFO, "[UiTestSuite]: " + fail.toString());
		}

		if (result.wasSuccessful()) {
			mLogger.log(Level.INFO, "[UiTestSuite]: All tests completed successfully!");
		}
	}
}
