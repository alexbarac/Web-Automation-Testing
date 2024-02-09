package com.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: malghafari
 * Date: Sep 2, 2011
 * Time: 10:46:22 AM
 */
public class UITestSuite {

    private Boolean testSuiteEnabled;
    private String testSuiteLocation;
    private String testSuiteName;

    private List<TestFile> testFiles = new ArrayList<TestFile>();

    public UITestSuite(Boolean testSuiteEnabled, String testSuiteLocation, String testSuiteName) {
        this.testSuiteEnabled = testSuiteEnabled;
        this.testSuiteLocation = testSuiteLocation;
        this.testSuiteName = testSuiteName;
    }

    public UITestSuite(Boolean testSuiteEnabled, String testSuiteLocation, String testSuiteName, List<TestFile> testFiles) {
        this.testSuiteEnabled = testSuiteEnabled;
        this.testSuiteLocation = testSuiteLocation;
        this.testSuiteName = testSuiteName;
        this.testFiles = testFiles;
    }

    public void addTestFile(final TestFile testFile) {
        this.testFiles.add(testFile);
    }

    public Boolean isTestSuiteEnabled() {
        return testSuiteEnabled;
    }

    public void setTestSuiteEnabled(Boolean testSuiteEnabled) {
        this.testSuiteEnabled = testSuiteEnabled;
    }

    public String getTestSuiteLocation() {
        return testSuiteLocation;
    }

    public void setTestSuiteLocation(String testSuiteLocation) {
        this.testSuiteLocation = testSuiteLocation;
    }

    public String getTestSuiteName() {
        return testSuiteName;
    }

    public void setTestSuiteName(String testSuiteName) {
        this.testSuiteName = testSuiteName;
    }

    public List<TestFile> getTestFiles() {
        return testFiles;
    }

    public void setTestFiles(List<TestFile> testFiles) {
        this.testFiles = testFiles;
    }

    @Override
    public String toString() {
        return "UITestSuite{" +
                "testSuiteEnabled=" + testSuiteEnabled +
                ", testSuiteLocation='" + testSuiteLocation + '\'' +
                ", testSuiteName='" + testSuiteName + '\'' +
                ", testFiles=" + testFiles +
                '}';
    }
}
