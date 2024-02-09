package com.seo.selenium.ui.rankings.secomparison;

public class SECSortingElement {
	private String mKeyword;
	private int mWebsitePosition;

	public SECSortingElement(String aKeyword, int aWebsitePosition) {
		this.mKeyword = aKeyword;
		this.mWebsitePosition = aWebsitePosition;
	}

	public String GetKeyword() {
		return mKeyword;
	}

	public void SetKeyword(String aKeyword) {
		this.mKeyword = aKeyword;
	}

	public int GetWebsitePosition() {
		return mWebsitePosition;
	}

	public void SetWebsitePosition(int aWebsitePosition) {
		this.mWebsitePosition = aWebsitePosition;
	}

}
