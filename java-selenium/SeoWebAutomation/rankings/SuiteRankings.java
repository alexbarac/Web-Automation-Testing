package com.seo.selenium.ui.rankings;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

import com.seo.selenium.ui.rankings.clickshare.SuiteCS;
import com.seo.selenium.ui.rankings.keywordgroups.SuiteKG;
import com.seo.selenium.ui.rankings.keywordranking.SuiteKR;
import com.seo.selenium.ui.rankings.overview.SuiteRankingsOverview;
import com.seo.selenium.ui.rankings.secomparison.SuiteSEC;
import com.seo.selenium.ui.rankings.websitecomparison.SuiteWebsiteComparison;
import com.seo.selenium.ui.rankings.websiteranking.TestWebsiteRanking;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	SuiteRankingsOverview.class,
	SuiteSEC.class,
	SuiteWebsiteComparison.class,
	TestWebsiteRanking.class,
	SuiteKR.class,
	SuiteKG.class,
	SuiteCS.class,
	TestProjectsList.class
	})
public class SuiteRankings {
	private static final Logger mLogger = Logger.getLogger(SuiteRankings.class.getName());

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(SuiteRankings.class);
		for (Failure fail : result.getFailures()) {
			mLogger.log(Level.INFO, "[UiTestSuite]: " + fail.toString());
		}

		if (result.wasSuccessful()) {
			mLogger.log(Level.INFO, "[UiTestSuite]: All tests completed successfully!");
		}
	}
}
