package com.seo.selenium.ui.rankings.keywordranking;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

import com.seo.selenium.ui.SeleniumConstants;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
  TestKR.class, 
  TestKRPagination.class, 
  TestKRFilters.class, 
  TestKRGroups.class,
  TestKRSorting.class, 
  TestKRViews.class 
  })
public class SuiteKR {
	private static final Logger mLogger = Logger.getLogger(SuiteKR.class.getName());

	@BeforeClass
	public static void setUpClass() {
		System.setProperty(SeleniumConstants.kCredentials, SeleniumConstants.kCredentialsLiveTest);
		System.setProperty(SeleniumConstants.kTesting, SeleniumConstants.kTestingTrue);
	}

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(SuiteKR.class);
		for (Failure fail : result.getFailures()) {
			mLogger.log(Level.INFO, "[UiTestSuite]: " + fail.toString());
		}

		if (result.wasSuccessful()) {
			mLogger.log(Level.INFO, "[UiTestSuite]: All tests completed successfully!");
		}
	}
}
