package com.seo.selenium.ui.rankings.keywordranking;

public class KRSortingElement {
	private String mKeyword;
	private Double mValue;
	
	public KRSortingElement(String aKeyword, Double aValue) {
		this.mKeyword = aKeyword;
		this.mValue = aValue;
	}
	
	public String GetKeyword() {
		return mKeyword;
	}
	public void SetKeyword(String aKeyword) {
		this.mKeyword = aKeyword;
	}
	public Double GetValue() {
		return mValue;
	}
	public void SetValue(Double aValue) {
		this.mValue = aValue;
	}
}
