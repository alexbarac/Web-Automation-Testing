package com.seo.selenium.ui.rankings.overview;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	TestOverviewEmptyProject.class, 
	TestOverviewFullProject.class, 
	TestOverviewUpdatedProject.class 
	})
public class SuiteRankingsOverview {
	private static final Logger mLogger = Logger.getLogger(SuiteRankingsOverview.class.getName());

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(SuiteRankingsOverview.class);
		for (Failure fail : result.getFailures()) {
			mLogger.log(Level.INFO, "[UiTestSuite]: " + fail.toString());
		}

		if (result.wasSuccessful()) {
			mLogger.log(Level.INFO, "[UiTestSuite]: All tests completed successfully!");
		}
	}
}
