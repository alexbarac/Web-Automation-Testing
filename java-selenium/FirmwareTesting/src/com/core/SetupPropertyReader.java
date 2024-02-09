package com.core;

import java.io.FileInputStream;
import java.util.Properties;
import java.io.File;

public class SetupPropertyReader {
    private Properties properties = new Properties();
    public String deviceIp;
    public String userName;
    public String userPassword;
    public String browser;
    public String encoding;
    public String htmlOutput;
    public String screenshotOutput;
    public String freq;
    //private static final String defltPropertyFile = "C:\\IxResources\\InternalResources\\UserInterface\\RDL_3_0\\properties\\setup.properties";
    private static final String defltPropertyFile = "C:\\IxResources\\InternalResources\\UserInterface\\Connect_TestWeb\\properties\\setup.properties";
    private String BaseURL;

    public SetupPropertyReader() {
        try {
            load();
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void load()
            throws Exception {
        FileInputStream fiStream;
        fiStream = new FileInputStream(defltPropertyFile);
        properties.load(fiStream);
        deviceIp = properties.getProperty("deviceIp");
        userName = properties.getProperty("userName");
        userPassword = properties.getProperty("userPassword");
        browser = properties.getProperty("browser");
        encoding = properties.getProperty("encoding");
        BaseURL = properties.getProperty("BaseURL");
        freq = properties.getProperty("freq");


    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getBrowser() {
        return browser;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getBaseURL() {
        return BaseURL;
    }

    public String getFreq() {
        return freq;
    }

}