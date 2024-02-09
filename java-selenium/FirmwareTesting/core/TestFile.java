package com.core;


import java.util.ArrayList;
import java.util.List;

public class TestFile {
    private String id;
    private Boolean enabled;

    private String fileName;
    private String fileLocation;
    private String pageTitle;
    private String pageXpath;
    private String fileDescription;
    private List<TestStep> testSteps = new ArrayList<TestStep>();

    public TestFile() {
    }

    public TestFile(final String id, final Boolean enabled, final String fileLocation,
                    final String pageTitle, final String pageXpath, final String fileName, final String fileDescription) {
        this.id = id;
        this.enabled = enabled;

        this.fileLocation = fileLocation;
        this.pageTitle = pageTitle;
        this.pageXpath = pageXpath;
        this.fileName = fileName;
        this.fileDescription = fileDescription;
    }

    public TestFile(final String id, final Boolean enabled, final String fileLocation,
                    final String pageTitle, final String pageXpath, final String fileName, final String fileDescription, final List<TestStep> testSteps) {
        this.id = id;
        this.enabled = enabled;
        this.fileDescription = fileDescription;
        this.fileLocation = fileLocation;
        this.fileName = fileName;
        this.pageTitle = pageTitle;
        this.pageXpath = pageXpath;
        this.testSteps = testSteps;
    }

    public void addTestStep(final TestStep step) {
        this.testSteps.add(step);
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    public void setFileDescription(final String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(final String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(final String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageXpath() {
        return pageXpath;
    }

    public void setPageXpath(final String pageXpath) {
        this.pageXpath = pageXpath;
    }

    public List<TestStep> getTestSteps() {
        return testSteps;
    }

    public void setTestSteps(final List<TestStep> testSteps) {
        this.testSteps = testSteps;
    }

    @Override
    public String toString() {
        System.out.println();
        return "TestFile{" + "id='" + id + '\'' + ", enabled=" + enabled + ", fileLocation='" +
                fileLocation + "fileName='" + fileName + '\'' + ", pageTitle='" + pageTitle + '\'' + ", pageXpath='" + pageXpath +
                '\'' + "\n\n, ------------testSteps=" + stepsToString() + '}';

    }

    private String stepsToString() {
        String toReturn = "";

        for (final TestStep testStep : testSteps) {
            toReturn += ("\n" + testStep.toString());
        }

        return toReturn;
    }
}