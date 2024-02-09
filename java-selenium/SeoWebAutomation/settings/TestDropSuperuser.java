package com.seo.selenium.ui.settings;

import org.junit.Assert;
import org.junit.Test;

import com.seo.selenium.ui.SeleniumConstants;
import com.seo.serp.utils.mysql.DatabaseUtils;

public class TestDropSuperuser {

	@Test
	public void TestDropUser() throws Exception {
		if (DatabaseUtils.GetSuperuserByEmail(SeleniumConstants.kUserEmail1) < 0) {
			Assert.fail("User can't be found in the database. ");
		}

		DatabaseUtils.DeleteSuperuser(SeleniumConstants.kUserEmail1);

		if (DatabaseUtils.GetSuperuserByEmail(SeleniumConstants.kUserEmail1) > 0) {
			Assert.fail("User wasn't deleted from the database. ");
		}
	}
}
