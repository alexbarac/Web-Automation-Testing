package com.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UserActions {
    Boolean get;
    Boolean set;
    Boolean nav;
    Boolean click;
    Boolean sleep;
    Boolean assign;
    Boolean reset;
    
    

    public UserActions(final String action) {
        this.get = action.equalsIgnoreCase("get");
        this.set = action.equalsIgnoreCase("set");
        this.nav = action.equalsIgnoreCase("nav");
        this.click = action.equalsIgnoreCase("click");
        this.sleep = action.equalsIgnoreCase("sleep");
        this.assign = action.equalsIgnoreCase("assign");
        this.reset = action.equalsIgnoreCase("reset");
    }
    public UserActions() {
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
    public Boolean isSleep() {
        return sleep;
    }
    public Boolean isAssign() {
        return assign;
    }
    public Boolean isReset() {
        return reset;
    }

    @Override
    public String toString() {
        return "UserActions{" + "get=" + get + ", set=" + set + ", nav=" + nav + ", click=" + click + ", sleep=" + sleep + " , assign=" + assign +" , reset=" + reset + 
                '}';
    }
    
    public void ieKiller() throws Exception
    {
      final String KILL = "taskkill /F /IM ";
      String processName = "IEDriverServer.exe"; //IE process
      Runtime.getRuntime().exec(KILL + processName); 
      wait(3000); //Allow OS to kill the process
    } 
    private static final String TASKLIST = "tasklist";
    
    public static boolean isProcessRunging(String serviceName) throws Exception {
     
     Process p = Runtime.getRuntime().exec(TASKLIST);
     BufferedReader reader = new BufferedReader(new InputStreamReader(
       p.getInputStream()));
     String line;
     while ((line = reader.readLine()) != null) {
     
      System.out.println(line);
      if (line.contains(serviceName)) {
       return true;
      }
     }
     
     return false;
     
    }
}