package com.core;

public class UserActions {
    Boolean get;
    Boolean set;
    Boolean nav;
    Boolean click;

    public UserActions(final String action) {
        this.get = action.equalsIgnoreCase("get");
        this.set = action.equalsIgnoreCase("set");
        this.nav = action.equalsIgnoreCase("nav");
        this.click = action.equalsIgnoreCase("click");
    }

    public Boolean isGet() {
        return get;
    }

    public Boolean isSet() {
        return set;
    }

    public Boolean isNav() {
        return nav;
    }

    public Boolean isClick() {
        return click;
    }

    @Override
    public String toString() {
        return "UserActions{" + "get=" + get + ", set=" + set + ", nav=" + nav + ", click=" + click +
                '}';
    }
}