package com.core;

import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: malghafari
 * Date: Sep 19, 2011
 * Time: 8:19:09 AM
 */
public class SuiteResultCounters {
    private Integer tests = 0;
    private Integer steps = 0;
    private Integer passes = 0;
    private Integer fails = 0;
    private Integer failsTests = 0;
    private Integer time = 0;
    private String url = null;

    public SuiteResultCounters() {
    }

    public void incrementTests() {
        tests++;
    }

    public void incrementSteps() {
        steps++;
    }

    public void incrementPasses() {
        passes++;
    }

    public void incrementFails() {
        fails++;
    }

    public void incrementFailedTests() {
        failsTests++;
    }

    public Integer getTests() {
        return tests;
    }

    public void setTests(Integer tests) {
        this.tests = tests;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Integer getPasses() {
        return passes;
    }

    public void setPasses(Integer passes) {
        this.passes = passes;
    }

    public Integer getFails() {
        return fails;
    }

    public void setFails(Integer fails) {
        this.fails = fails;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getFailsTests() {
        return failsTests;
    }

    public void setFailsTests(Integer failsTests) {
        this.failsTests = failsTests;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getPassToFailPercentage() {
        Double pass = (getPasses().doubleValue() / getSteps().doubleValue()) * 100;
        Double fail = (getFails().doubleValue() / getSteps().doubleValue()) * 100;
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return twoDForm.format(pass) + " / " + twoDForm.format(fail);
    }

    public String getPassToFailPercentageTests() {
        Integer passedTests = getTests() - getFailsTests();
        Double pass = (passedTests.doubleValue() / getTests().doubleValue()) * 100;
        Double fail = (getFailsTests().doubleValue() / getTests().doubleValue()) * 100;

        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return twoDForm.format(pass) + " / " + twoDForm.format(fail);
    }

    public String getTestRunDuration() {
        int seconds = (int) (getTime() / 1000) % 60;
        int minutes = (int) ((getTime() / (1000 * 60)) % 60);
        int hours = (int) ((getTime() / (1000 * 60 * 60)) % 24);

        return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }

}
