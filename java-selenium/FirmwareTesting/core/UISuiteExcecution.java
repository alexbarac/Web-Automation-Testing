package com.core;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Alert;
import java.io.*;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;


public class UISuiteExcecution {
	static WebDriver fireFoxdriver;
	static Alert alert;
	
    public static SuiteResultCounters runSuite(String deviceIP, String url, UITestSuite suite, BufferedWriter out) throws IOException {
    	System.setProperty("webdriver.ie.driver","C:\\IxResources\\InternalResources\\UserInterface\\RDL_3_0\\lib\\IEDriverServer.exe");

    	SuiteResultCounters suiteCouters = new SuiteResultCounters();
        //FirefoxProfile profile = new FirefoxProfile();
        //profile.setPreference("network.http.phishy-userpass-length", 255);
		//fireFoxdriver = new FirefoxDriver(profile);
		long startTime = System.currentTimeMillis();
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
    	WebDriver fireFoxdriver = new InternetExplorerDriver(caps);
		fireFoxdriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        
        fireFoxdriver.get(url);
        int passCount = 0;
        int failCount = 0;
        int steps = 0;


        out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        out.newLine();
        out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        out.newLine();
        out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        out.newLine();
        out.write("<html>");
        out.newLine();


        out.write("<head><title>Automated UI Testing - " + suite.getTestSuiteName() + "</title>");
        out.newLine();
        out.write("<style type=\"text/css\">\n" +
                "\n" +
                "#box-table-a\n" +
                "{\n" +
                "\tfont-family: \"Lucida Sans Unicode\", \"Lucida Grande\", Sans-Serif;\n" +
                "\tfont-size: 12px;\n" +
                "\tmargin: 45px;\n" +
                "\tmin-width: 50%;\n" +
                "\ttext-align: left;\n" +
                "\tborder-collapse: collapse;\n" +
                "}\n" +
                "#box-table-a th\n" +
                "{\n" +
                "\tfont-size: 13px;\n" +
                "\tfont-weight: normal;\n" +
                "\tpadding: 8px;\n" +
                "\tbackground: #b9c9fe;\n" +
                "\tborder-top: 4px solid #aabcfe;\n" +
                "\tborder-bottom: 1px solid #fff;\n" +
                "\tcolor: #039;\n" +
                "}\n" +
                "#box-table-a td\n" +
                "{\n" +
                "\tpadding: 8px;\n" +
                "\tbackground: #e8edff;\n" +
                "\tborder-bottom: 1px solid #fff;\n" +
                "\tcolor: #669;\n" +
                "\tborder-top: 1px solid transparent;\n" +
                "}\n" +
                "#box-table-a tr:hover td\n" +
                "{\n" +
                "\tbackground: #d0dafd;\n" +
                "\tcolor: #339;\n" +
                "}\n" +
                "\n" +
                "p.text\n" +
                "{\n" +
                "\tfont-family: \"Lucida Sans Unicode\", \"Lucida Grande\", Sans-Serif;\n" +
                "\tfont-size: 14px; \n" +
                "\tposition: relative; \n" +
                "\tleft: 45px;\n" +
                "}\n" +
                "\n" +
                "div.left\n" +
                "{\n" +
                "    position: relative; \n" +
                "\tleft: 45px;\n" +
                "}\n" +
                "\n" +
                "@page {\n" +
                "  margin: 10%;\n" +
                "  counter-increment: page;\n" +
                "\n" +
                "  @top-right {\n" +
                "    font-family: sans-serif;\n" +
                "    font-weight: bold;\n" +
                "    font-size: 10px;\n" +
                "    content: \"Page \" counter(page) \" of \" counter(pages);\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "</style>");
        out.newLine();
        out.write("</head>");
        out.newLine();
        out.write("<body>");
        out.newLine();
        out.write(getTOCScript());
        out.newLine();
        out.write("<p style=\"font-size:26px; text-align:center;\">Detailed Report for Test Suite: " + suite.getTestSuiteName() + "</p>");
        out.newLine();
        out.write("<a name=\"top\"><div id=\"contents\">");
        out.newLine();
        out.write("</div></a>");
        out.newLine();
        out.write("<hr color=\"blue\">");
        out.newLine();


        List<TestFile> files = suite.getTestFiles();
        for (TestFile testFile : files) {
            System.out.println("Testing file: " + testFile.getFileName());
            suiteCouters.incrementTests();
            out.write("<br>");
            out.newLine();
            out.write("<h1 id=" + testFile.getFileName() + ">" + testFile.getFileName() + "</h1>");
            out.newLine();
            buildCsvToHtmlTables(testFile.getFileLocation(), out, testFile);

            List<TestStep> testSteps = testFile.getTestSteps();
            for (TestStep testStep : testSteps) {
                System.out.println("Test Step: " + testStep.toString());
                if (testStep.getAction().isNav()) {
                    suiteCouters.incrementSteps();
                    steps++;
                    try {
                        if (testStep.getFieldType().equals("Cancel")) {
                        	WebElement element =  retrieveElement(fireFoxdriver, testFile.getPageXpath());
                            long end = System.currentTimeMillis() + 5000;
                            while (System.currentTimeMillis() < end) {
                                // Browsers which render content (such as Firefox and IE) return "RenderedWebElements"
                            	WebElement resultsDiv =  element;

                                // If results have been returned, the results are displayed in a drop down.
                                if (resultsDiv.isDisplayed()) {
                                    break;
                                }
                            }
                            ((JavascriptExecutor) fireFoxdriver).executeScript(
                                    "window.confirm = function(msg){return false;}"
                            );
                            element.click();
                            suiteCouters.incrementPasses();

                            passCount++;
                        } else if (testStep.getFieldType().equals("Ok")) {

                        	WebElement element =  retrieveElement(fireFoxdriver, testFile.getPageXpath());
                            ((JavascriptExecutor) fireFoxdriver).executeScript(
                                    "window.confirm = function(msg){return true;}"
                            );
                            element.click();
                            // sleep for 4 seconds
                            Thread.sleep(4000);

                            suiteCouters.incrementPasses();
                            passCount++;

                        } else if ((testFile.getPageTitle().equals(fireFoxdriver.getTitle()))) {

                            suiteCouters.incrementPasses();
                            passCount++;
                        } else {

                        	WebElement element =  retrieveElement(fireFoxdriver, testFile.getPageXpath());
                            element.click();
                            // sleep for 4 seconds
                            Thread.sleep(4000);
                            suiteCouters.incrementPasses();
                            passCount++;

                        }
//                                        fireFoxdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    }
                    catch (final Exception e) {
                        System.err.println(e.getMessage());
                        suiteCouters.incrementFails();
                        failCount++;
                    }
                    try 
                    { 
                    	alert = fireFoxdriver.switchTo().alert();
                		alert.accept();
                    }   // try 
                    catch (NoAlertPresentException Ex) 
                    { 
                       
                    } 
                     

                }
                if (testStep.getAction().isSet()) {
                    suiteCouters.incrementSteps();

                    steps++;
                    try {
                        if (testStep.getFieldType().equals("text")) {
//                                            takeScreenshot();
                            if (testStep.getFieldId().equals("pMaxDist")) {
                                // temporary fix for max distance
                                ((JavascriptExecutor) fireFoxdriver).executeScript(
                                        "window.alert = function(msg){return true;}"

                                );
                            }
                           WebElement element =  retrieveElement(fireFoxdriver, testStep.getFieldId());
                            element.clear();
                            element.sendKeys(testStep.getFieldValue());


                            suiteCouters.incrementPasses();
                            passCount++;

                            try 
                            { 
                            	alert = fireFoxdriver.switchTo().alert();
                        		alert.accept();
                            }   // try 
                            catch (NoAlertPresentException Ex) 
                            { 
                               
                            } 
                        }

                        if (testStep.getFieldType().equals("DDL")) {
                        	
                        	WebElement element = retrieveElement(fireFoxdriver, testStep.getFieldId());
                        	Select select = new Select(element);
                            List<WebElement> options = select.getOptions();
                            for (WebElement we : options) {
                               if (we.getText().equals(testStep.getFieldValue())) {
                                	
                                //	fireFoxdriver.switchTo().defaultContent().switchTo().frame("DynamicFrame");
                                    WebElement options2 = element.findElement(By.xpath("//option[contains(.,'" + we.getText() + "')]"));
                                	select.selectByValue(options2.getAttribute(("value")));
                                	System.out.println(options2.getAttribute(("value")));
                                }
                            

                            }
                            try 
                            { 
                            	alert = fireFoxdriver.switchTo().alert();
                        		alert.accept();
                            }   // try 
                            catch (NoAlertPresentException Ex) 
                            { 
                               
                            } 
	//                           takeScreenshot();
                            suiteCouters.incrementPasses();
                            passCount++;

                        }


                        if (testStep.getFieldType().equals("checkbox")) {
//                                            takeScreenshot();
                            if (testStep.getFieldValue().equals("on")) {

                            	WebElement element =  retrieveElement(fireFoxdriver, testStep.getFieldId());
                                if (!(element.isSelected())) {
                                    element.click();


                                }

                            } else if (testStep.getFieldValue().equals("off")) {

                            	WebElement element = retrieveElement(fireFoxdriver, testStep.getFieldId());
                                if (element.isSelected()) {
                                    element.click();


                                }
                            }

                            suiteCouters.incrementPasses();
                            passCount++;
                            try 
                            { 
                            	alert = fireFoxdriver.switchTo().alert();
                        		alert.accept();
                            }   // try 
                            catch (NoAlertPresentException Ex) 
                            { 
                               
                            } 

                        }


                    }
                    catch (final Exception e) {
                        suiteCouters.incrementFails();
                        failCount++;
                        out.write("<br>");
                        out.newLine();
                        out.write("Error " + (testStep.getFieldId()) + " - " + e.getMessage());
                        out.write("<br>");
                        out.newLine();
                        out.write(e.toString());
                        out.newLine();
                        out.write("<br>");
                        out.newLine();
                    }
                    
                   
            		
                }
                if (testStep.getAction().isGet()) {
                    suiteCouters.incrementSteps();
                    steps++;
                    WebElement element = null;
					boolean isChecked = true;
                    try {
                        if (fireFoxdriver.getPageSource().contains(testFile.getPageTitle())) {
                            element = retrieveElement(fireFoxdriver, testStep.getFieldId());

                            if (testStep.getFieldType().equals("checkbox")) {
								if(!element.isSelected())
                            		isChecked=false;                         		
								//boolean isChecked = element.getAttribute("checked").equals("true");
                                boolean expectedToBeChecked = testStep.getFieldValue().equals("on");
                                if (isChecked != expectedToBeChecked) {
                                    if (testStep.getResult().equalsIgnoreCase("pass")) {
                                        suiteCouters.incrementFails();
                                        failCount++;
                                        out.write("<br>");
                                        out.newLine();
                                        out.write("Field ID: " + (testStep.getFieldId()) + " - Expected Value:  " + (testStep.getFieldValue()) + " - UI Value:  " + (element.getAttribute("value")));
                                        out.newLine();
                                        out.write("<br>");
                                        out.newLine();
                                    } else {
                                        suiteCouters.incrementPasses();
                                        passCount++;

                                    }
                                } else if (isChecked == expectedToBeChecked) {

                                    if (testStep.getResult().equalsIgnoreCase("pass")) {
                                        suiteCouters.incrementPasses();
                                        passCount++;


                                    } else {
                                        suiteCouters.incrementFails();
                                        failCount++;

                                    }
                                }
                            } else {
                                if (!(element.getAttribute("value")).equals(testStep.getFieldValue())) {
                                    if (testStep.getResult().equalsIgnoreCase("pass")) {
                                        suiteCouters.incrementFails();
                                        failCount++;
                                        out.write("<br>");
                                        out.newLine();
                                        out.write("Field ID: " + (testStep.getFieldId()) + " - Expected Value:  " + (testStep.getFieldValue()) + " - UI Value:  " + (element.getAttribute("value")));
                                        out.newLine();
                                        out.write("<br>");
                                        out.newLine();
                                    } else {
                                        suiteCouters.incrementPasses();
                                        passCount++;

                                    }
                                } else if ((element.getAttribute("value")).equals(testStep.getFieldValue())) {

                                    if (testStep.getResult().equalsIgnoreCase("pass")) {
                                        suiteCouters.incrementPasses();
                                        passCount++;


                                    } else {
                                        suiteCouters.incrementFails();
                                        failCount++;

                                    }
                                }

                            }
                        } else if (!fireFoxdriver.getPageSource().contains(testFile.getPageTitle())) {
                            if (!(fireFoxdriver.getTitle().equals(testStep.getFieldValue()))) {

                                suiteCouters.incrementFails();
                                failCount++;

                                out.write("<br>");
                                out.newLine();
                                out.write("Didnt Find " + (testStep.getFieldValue()));
                                out.newLine();
                                out.write("<br>");
                                out.newLine();

                            } else if (fireFoxdriver.getPageSource().contains(testStep.getFieldValue())) {
                                suiteCouters.incrementPasses();
                                passCount++;

//                                                takeScreenshot();

                            }


                        }
                    }
                    catch (final Exception e) {
                        out.write("<br>");
                        out.newLine();
                       out.write("<br>");         
                       suiteCouters.incrementFails();
                        failCount++;

                    }
                }
                if (testStep.getAction().isClick()) {

                    suiteCouters.incrementSteps();
                    steps++;
                    try {
                    	WebElement element = retrieveElement(fireFoxdriver, testStep.getFieldId());
                        if (testStep.getFieldValue().equals("Ok")) {
                            ((JavascriptExecutor) fireFoxdriver).executeScript(
                                    "window.alert = function(msg){return true;}"

                            );
                            ((JavascriptExecutor) fireFoxdriver).executeScript(
                                    "window.confirm = function(msg){return true;}"

                            );

                        } else if (testStep.getFieldValue().equals("Cancel")) {
                            ((JavascriptExecutor) fireFoxdriver).executeScript(
                                    "window.alert = function(msg){return false;}"
                            );
                            ((JavascriptExecutor) fireFoxdriver).executeScript(
                                    "window.confirm = function(msg){return false;}"

                            );
                        }
                        element.click();
                        // sleep for 4 seconds

                        Thread.sleep(4000);

                        suiteCouters.incrementPasses();
                        passCount++;

                    }
                    catch (final Exception e) {
                        out.write("<br>");
                        out.newLine();
                        out.write("Error " + (testStep.getFieldId()) + " - " + e.getMessage());
                        out.write("<br>");
                        out.newLine();
                        suiteCouters.incrementFails();
                        failCount++;
                        out.write("<br>");
                        out.newLine();
                    }

                }
                if (testStep.getResult().equals("Screenshot")) {
//                                    takeScreenshot();
                }


            }

            out.write("<br>");
            out.newLine();
            out.write("<b>Total Steps: </b>" + steps);
            out.newLine();
            out.write("<br>");
            out.newLine();
            out.write("<b>Passed: </b>" + passCount);
            out.newLine();
            out.write("<br>");
            out.newLine();
            if (failCount > 0) {
                suiteCouters.incrementFailedTests();
                out.write("<b> Failed: </b>" + failCount);
                out.newLine();
                out.write("<br>");
                out.newLine();
                out.write("<font size=\"5\" color=\"Red\"> Test Case Failed </font>");
                out.newLine();
                out.write("<br>");
                out.newLine();
                out.write("<br>");
                out.newLine();
                out.write("<a href=\"#top\">Return to Top</a>");
                out.newLine();

            } else if (failCount == 0) {
                out.write("<b> Failed: </b>" + failCount);
                out.newLine();
                out.write("<br>");
                out.newLine();
                out.write("<font size=\"5\" color=\"Green\"> Test Case Passed </font>");
                out.newLine();
                out.write("<br>");
                out.newLine();
                out.write("<br>");
                out.newLine();
                out.write("<a href=\"#top\">Return to Top</a>");
                out.newLine();
            }
            passCount = 0;
            failCount = 0;
            steps = 0;
            out.write("<hr color=\"blue\">");
            out.newLine();
          //  fireFoxdriver.close();
        }
        long endTime = System.currentTimeMillis();
        Long time = endTime - startTime;
        suiteCouters.setTime(time.intValue());
        out.write("</body>");
        out.newLine();
        out.write("</html>");
        out.newLine();
        out.close();
        FileReader fstreamReader = new FileReader("c:\\IxResources\\InternalResources\\UserInterface\\RDL_3_0\\production\\genericOutput" + suite.getTestSuiteName() + ".txt");
        BufferedReader in = new BufferedReader(fstreamReader);
        String str = in.readLine();//"\\\\192.168.20.14\\results\\" + suite.getTestSuiteName() + ".html"
        FileWriter htmlFile = new FileWriter("c:\\IxResources\\InternalResources\\UserInterface\\RDL_3_0\\" + suite.getTestSuiteName() + ".html");
        BufferedWriter htmlOut = new BufferedWriter(htmlFile);
        suiteCouters.setUrl("../" + suite.getTestSuiteName() + ".html");
        /*
        FileWriter htmlFile = new FileWriter("\\\\192.168.20.14\\results\\" + suite.getTestSuiteName() + ".html");
        BufferedWriter htmlOut = new BufferedWriter(htmlFile);
        suiteCouters.setUrl("../" + suite.getTestSuiteName() + ".html");*/
        while (str != null) {
            htmlOut.write(str);
            htmlOut.newLine();
            str = in.readLine();
        }
        htmlOut.close();

       fireFoxdriver.quit();

        return suiteCouters;
    }

    public static WebElement retrieveElement(WebDriver fireFoxdriver, String field) {
        fireFoxdriver.switchTo().defaultContent().switchTo().frame("DynamicFrame");
        if (field.indexOf("xpath=") > -1) {
            String s = field.substring(field.indexOf('=') + 1);
            long end = System.currentTimeMillis() + 10000;
            while (System.currentTimeMillis() < end) {
                // Browsers which render content (such as Firefox and IE) return "RenderedWebElements"
            	WebElement resultsDiv = null;
                try {
                    resultsDiv =  fireFoxdriver.findElement(By.xpath(s));

                    // If results have been returned, the results are displayed in a drop down.
                    if (resultsDiv.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Error retrieving element: " + field + " ----  " + e.getMessage());
                    //ignore for now
                }

            }
            return fireFoxdriver.findElement(By.xpath(s));
        } else if (field.indexOf("link=") > -1) {
            fireFoxdriver.switchTo().defaultContent().switchTo().frame("LeftPanelFrame");
            String s = field.substring(field.indexOf('=') + 1);
            long end = System.currentTimeMillis() + 10000;
            while (System.currentTimeMillis() < end) {
                // Browsers which render content (such as Firefox and IE) return "RenderedWebElements"
                try {
                	WebElement resultsDiv =  fireFoxdriver.findElement(By.linkText(s));

                    // If results have been returned, the results are displayed in a drop down.
                    if (resultsDiv.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Error retrieving element: " + field + " ----  " + e.getMessage());
                    //ignore for now
                }
            }
            return fireFoxdriver.findElement(By.linkText(s));
        }
        long end = System.currentTimeMillis() + 10000;
        while (System.currentTimeMillis() < end) {
            // Browsers which render content (such as Firefox and IE) return "RenderedWebElements"
            try {
            	WebElement resultsDiv =  fireFoxdriver.findElement(By.name(field));

                // If results have been returned, the results are displayed in a drop down.
                if (resultsDiv.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Error retrieving element: " + field + " ----  " + e.getMessage());
                //ignore for now
            }
        }
        return fireFoxdriver.findElement(By.name(field));
    }

    public static void buildCsvToHtmlTables(String fileName, BufferedWriter out, TestFile testFile) {
        try {
            FileReader fstream = new FileReader(fileName);
            BufferedReader in = new BufferedReader(fstream);

            String line = in.readLine();

            out.write("<h3>Test Description: " + testFile.getFileDescription() + "</h3>");
            out.newLine();
            out.write("<br>");
            out.newLine();

            out.write("<table id=\"box-table-a\">");
            out.newLine();
            createTableHeader(line, out);
            line = in.readLine();
            out.write("<tbody>");
            while (line != null) {
                addRowToTable(line, out);
                line = in.readLine();
            }
            out.write("</tbody>");
            out.write("</table>");
            out.newLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void addRowToTable(String line, BufferedWriter out) throws IOException {
        out.write("<tr>");
        out.newLine();
        StringTokenizer st = new StringTokenizer(line, ",");
        while (st.hasMoreTokens()) {
            out.write("<td>");
            out.newLine();
            out.write(st.nextToken());
            out.newLine();
            out.write("</td>");
            out.newLine();
        }


        out.write("</tr>");
        out.newLine();
    }

    private static void createTableHeader(String line, BufferedWriter out) throws IOException {
        out.write("<thead>");
        out.write("<tr>");
        out.newLine();
        StringTokenizer st = new StringTokenizer(line, ",");
        while (st.hasMoreTokens()) {
            out.write("<th>");
            out.newLine();
            out.write(st.nextToken());
            out.newLine();
            out.write("</th>");
            out.newLine();
        }
        out.write("</tr>");
        out.write("</thead>");
        out.newLine();
    }

    private static String getTOCScript() {
        StringBuffer script = new StringBuffer();
        script.append("<script type=\"text/javascript\">");
        script.append("if (document.getElementById) onload = function () {\n" +
                "    var h2 = document.createElement ('H2');\n" +
                "    h2.appendChild (document.createTextNode ('Contents'));\n" +
                "    var ul = document.createElement ('UL');\n" +
                "  \n" +
                "    var e, i = 0,last=1;//assumes first header found is h1\n" +
                "\t\n" +
                "    // Return if there is no add toc command present\n" +
                "\tif (document.getElementById('contents') == null)\n" +
                "\t{\n" +
                "\t\treturn;\n" +
                "\t}\n" +
                "    while (e = document.getElementsByTagName ('*')[i++]) {\n" +
                "     if (e.id != \"\") {\n" +
                "        if (/^h[123456]$/i.test (e.tagName)) {\n" +
                "            //alert(last+\"--\"+e.tagName.substring(1,2));\n" +
                "            var newl = document.createElement ('UL');\n" +
                "            var li = document.createElement ('LI');\n" +
                "            var a = document.createElement ('A');\n" +
                "            a.appendChild (document.createTextNode (e.firstChild.data))\n" +
                "            a.href = '#' + e.id;\n" +
                "            li.appendChild (a);\n" +
                "          \n" +
                "            if(e.tagName.substring(1,2)>1){\n" +
                "                newl.appendChild (li);\n" +
                "                var n=2;\n" +
                "                \n" +
                "                while(n<e.tagName.substring(1,2)){\n" +
                "                    var newl2 =document.createElement ('UL');\n" +
                "                    newl2.appendChild (newl);\n" +
                "                    var newl =newl2;\n" +
                "                    var n=n+1;\n" +
                "                }\n" +
                "                ul.appendChild (newl);\n" +
                "            }else{\n" +
                "                ul.appendChild (li);\n" +
                "            }\n" +
                "            var last=e.tagName.substring(1,2);\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "    document.getElementById('contents').appendChild (h2);\n" +
                "    document.getElementById('contents').appendChild (ul);\n" +
                "}\n" +
                "\n" +
                "</script>");

        return script.toString();
    }

}
