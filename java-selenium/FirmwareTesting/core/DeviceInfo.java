package com.core;

public class DeviceInfo {
    private String dutIp;
    private String userName;
    private String userPassword;

    public DeviceInfo(final String dutIp, final String userName, final String userPassword) {
        this.dutIp = dutIp;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public DeviceInfo() {
    }

    public String getDutIp() {
        return dutIp;
    }

    public void setDutIp(final String dutIp) {
        this.dutIp = dutIp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(final String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" + "dutIp='" + dutIp + '\'' + ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' + '}';
    }
}