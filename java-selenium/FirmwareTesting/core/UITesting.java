package com.core;

import junit.framework.TestCase;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class UITesting
        extends TestCase {
    PrintStream oldoutps = System.out;
    private SetupPropertyReader reader = new SetupPropertyReader();
    private List<UITestSuite> testSuites = new ArrayList<UITestSuite>();

 // String Dir = getoutputPath();

    TestFile testFile;
    FileWriter fstreamGlobal = null;
    BufferedWriter outGlobal = null;
    FileWriter fstream = null;
    BufferedWriter out = null;

    protected static final int MAX_WAIT_TIME_SEC = 60;

    public String getoutputPath() {
        return createDir();
    }

    public String createDir() {

       File dir = new File("C:\\IxResources\\InternalResources\\UserInterface\\RDL_3_0\\production\\" + getResultsName(this.getClass().getName()));
        boolean b = dir.mkdir();
        return b ? dir.getAbsolutePath() : null;
    	

    }

    public void setUp()
            throws Exception {

        System.out.println("Setting up");
     // System.out.println(Dir);
      File dir = new File("C:\\IxResources\\InternalResources\\UserInterface\\RDL_3_0\\production\\" + getResultsName(this.getClass().getName()));
      boolean b = dir.mkdir();
    //   redirectLogOutput(Dir + "\\log.txt");
       FileOutputStream outfos = null;
       try {
    	  // System.out.println(temp);
           outfos = new FileOutputStream(dir.getAbsolutePath() + "\\log.txt", true);
           PrintStream newoutps = new PrintStream(outfos); //create new output //stream
           System.setOut(newoutps); //set the output stream
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
        buildSuitesList();
    }

    public void testNow() throws IOException, InterruptedException {
        fstreamGlobal = new FileWriter("C:\\IxResources\\InternalResources\\UserInterface\\RDL_3_0\\production\\genericOutput.html");
        outGlobal = new BufferedWriter(fstreamGlobal);

        outGlobal.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        outGlobal.newLine();
        outGlobal.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        outGlobal.newLine();
        outGlobal.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        outGlobal.newLine();
        outGlobal.write("<html>");
        outGlobal.newLine();
        outGlobal.write("<head><title>Automated UI Testing</title>");
        outGlobal.write("<style type=\"text/css\">\n" +
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
        outGlobal.write("</head>");
        outGlobal.newLine();
        outGlobal.write("<body>");
        outGlobal.newLine();
        outGlobal.write("<p style=\"font-size:26px; text-align:center;\">Web Interface Automation Tests Results for IP Address " + reader.getDeviceIp() + "</p>");
        outGlobal.newLine();
        outGlobal.write("<br>");
        outGlobal.write("<br>");
        outGlobal.write("<br>");

        
       
       
        for (UITestSuite testSuite : testSuites)
        {
        	 if (testSuite.isTestSuiteEnabled())
        	{
        		 
                outGlobal.newLine();
                outGlobal.write("<h3 id=" + testSuite.getTestSuiteName() + ">Test Suite: " + testSuite.getTestSuiteName() + "</h3>");
                outGlobal.newLine();
                outGlobal.write("<br>");
                outGlobal.newLine();
                fstream = new FileWriter("C:\\IxResources\\InternalResources\\UserInterface\\RDL_3_0\\production\\genericOutput" + testSuite.getTestSuiteName() + ".txt");
                out = new BufferedWriter(fstream);

                SuiteResultCounters suiteCounters = UISuiteExcecution.runSuite(reader.getDeviceIp(),
                        formulateHttpRequest(reader.getUserName(), reader.getUserPassword(), reader.getDeviceIp()),
                        testSuite, out);

                outGlobal.write("Suite Run Results");
                outGlobal.newLine();
                outGlobal.write("<br>");
                outGlobal.newLine();
                outGlobal.write("<table id=\"box-table-a\">");
                outGlobal.newLine();
                outGlobal.write("<thead>");
                outGlobal.newLine();
                outGlobal.write("<tr>");
                outGlobal.newLine();
                outGlobal.write("<th>");
                outGlobal.newLine();
                outGlobal.write("Total Test Count");
                outGlobal.newLine();
                outGlobal.write("</th>");
                outGlobal.newLine();
                outGlobal.write("<th>");
                Thread.sleep(1000);
                outGlobal.newLine();
                outGlobal.write("Total Step Count");
                outGlobal.newLine();
                outGlobal.write("</th>");
                outGlobal.newLine();
                outGlobal.write("<th>");
                outGlobal.newLine();
                outGlobal.write("Total Passed Steps");
                outGlobal.newLine();
                outGlobal.write("</th>");
                outGlobal.newLine();
                outGlobal.write("<th>");
                outGlobal.newLine();
                outGlobal.write("Total Failed Steps");
                outGlobal.newLine();
                outGlobal.write("</th>");
                Thread.sleep(1000);
                outGlobal.newLine();
                outGlobal.write("<th>");
                outGlobal.newLine();
                outGlobal.write("Total Failed Tests");
                outGlobal.newLine();
                outGlobal.write("</th>");

                outGlobal.newLine();
                outGlobal.write("<th>");
                outGlobal.newLine();
                outGlobal.write("Pass/Fail Steps(%)");
                outGlobal.newLine();
                outGlobal.write("</th>");
                Thread.sleep(1000);
                outGlobal.newLine();
                outGlobal.write("<th>");
                outGlobal.newLine();
                outGlobal.write("Pass/Fail Tests(%)");
                outGlobal.newLine();
                outGlobal.write("</th>");

                outGlobal.newLine();
                outGlobal.write("<th>");
                outGlobal.newLine();
                outGlobal.write("Duration (HH:MM:SS)");
                outGlobal.newLine();
                outGlobal.write("</th>");
                Thread.sleep(1000);
                outGlobal.newLine();
                outGlobal.write("</thead>");
                outGlobal.newLine();
                outGlobal.write("<tbody>");
                outGlobal.newLine();
                outGlobal.write("<tr>");
                outGlobal.newLine();
                outGlobal.write("<td>");
                outGlobal.newLine();
                outGlobal.write(suiteCounters.getTests().toString());
                outGlobal.newLine();
                outGlobal.write("</td>");
                outGlobal.newLine();
                outGlobal.write("<td>");
                outGlobal.newLine();
                outGlobal.write(suiteCounters.getSteps().toString());
                outGlobal.newLine();
                outGlobal.write("</td>");
                outGlobal.newLine();
                outGlobal.write("<td>");
                outGlobal.newLine();
                outGlobal.write(suiteCounters.getPasses().toString());
                outGlobal.newLine();
                outGlobal.write("</td>");
                outGlobal.newLine();
                outGlobal.write("<td>");
                outGlobal.newLine();
                outGlobal.write(suiteCounters.getFails().toString());
                outGlobal.newLine();
                outGlobal.write("</td>");
                outGlobal.newLine();
                outGlobal.write("<td>");
                outGlobal.newLine();
                outGlobal.write(suiteCounters.getFailsTests().toString());
                outGlobal.newLine();
                outGlobal.write("</td>");
                Thread.sleep(1000);
                outGlobal.newLine();
                outGlobal.write("<td>");
                outGlobal.newLine();
                outGlobal.write(suiteCounters.getPassToFailPercentage());
                outGlobal.newLine();
                outGlobal.write("</td>");
                outGlobal.newLine();
                outGlobal.write("<td>");
                Thread.sleep(1000);
                outGlobal.newLine();
                outGlobal.write(suiteCounters.getPassToFailPercentageTests());
                outGlobal.newLine();
                outGlobal.write("</td>");
                outGlobal.newLine();
                outGlobal.write("<td>");
                outGlobal.newLine();
                outGlobal.write(suiteCounters.getTestRunDuration());
                outGlobal.newLine();
                outGlobal.write("</td>");
                Thread.sleep(1000);
                outGlobal.newLine();
                outGlobal.write("</tbody>");
                outGlobal.newLine();
                outGlobal.write("</table>");

                outGlobal.write("<br>");
                outGlobal.newLine();
                outGlobal.write("<a href=\"" + suiteCounters.getUrl() + "\">Detailed Report</a>");
                outGlobal.newLine();
                outGlobal.write("<br>");
                outGlobal.newLine();
                outGlobal.write("<hr color=\"blue\">");
                outGlobal.newLine();
            }
        }

        outGlobal.write("</body>");
        outGlobal.newLine();
        outGlobal.write("</html>");
        outGlobal.newLine();
        outGlobal.close();

    }

    private String formulateHttpRequest(String userName, String password, String ip) {
        return "http://" + userName + ":" + password + "@" + ip + "/";
    }

    @SuppressWarnings("resource")
	private void buildSuitesList() throws IOException {
        UITestsFilePath suitePath = new UITestsFilePath();
        BufferedReader UITestSuite = new BufferedReader(new FileReader(suitePath.getPath()));
        String UITestSuiteData = UITestSuite.readLine();
        int suiteCounter = 0;
        while (UITestSuiteData != null) {
            StringTokenizer UIst = new StringTokenizer(UITestSuiteData, ",");
            String id = UIst.nextToken();
            if (id.equalsIgnoreCase("id")) {
                UITestSuiteData = UITestSuite.readLine();
                continue;
            }
            UITestSuite suite = new UITestSuite(UIst.nextToken().equals("yes"), UIst.nextToken(), UIst.nextToken());

            if (suite.isTestSuiteEnabled()) {
                File suiteFile = new File(suite.getTestSuiteLocation());  // just to check if it exists
                if (suiteFile.exists()) {
                    System.out.print(getTimeStamp());
                    System.out.print(" : Suite ");
                    System.out.print(++suiteCounter);
                    System.out.print(" - ");
                    System.out.print(suite.getTestSuiteName());
                    System.out.println();
                    // Start reading the suite
                    BufferedReader suiteFileCsv = new BufferedReader(new FileReader(suite.getTestSuiteLocation()));
                    String suiteFileData = suiteFileCsv.readLine();
                    int testFileCounter = 0;
                    while (suiteFileData != null) {
                        StringTokenizer st = new StringTokenizer(suiteFileData, ",");
                        String suiteFileId = st.nextToken();
                        if (suiteFileId.equalsIgnoreCase("id")) {
                            suiteFileData = suiteFileCsv.readLine();
                            continue;
                        }
                        // build the suite's test files
                        String testEnabled = st.nextToken();
                        String fileLocation = st.nextToken();
                        String pageTitle = st.nextToken();
                        String xPath = st.nextToken();
                        String fileName = st.nextToken();
                        String fileDescription = st.nextToken();
                        TestFile testFile = new TestFile(id, testEnabled.equals("yes"), fileLocation, pageTitle,
                                xPath, fileName, fileDescription);
                        File systemTestFile = new File(testFile.getFileLocation());  // just to check if it exists
                        if (systemTestFile.exists()) {
                            System.out.print(getTimeStamp());
                            System.out.print(" : Test File ");
                            System.out.print(++testFileCounter);
                            System.out.print(" - ");
                            System.out.print(testFile.getFileName());
                            System.out.println();

                            if (testFile.isEnabled()) {
                                suite.addTestFile(testFile);
                                // build the test file's steps

                                BufferedReader testFileCsv = new BufferedReader(new FileReader(testFile.getFileLocation()));
                                String testData = testFileCsv.readLine();
                                while (testData != null) {
                                    System.out.println(testData);
                                    StringTokenizer testDataSt = new StringTokenizer(testData, ",");
                                    String stepId = testDataSt.nextToken();
                                    if (stepId.equalsIgnoreCase("id")) {
                                        testData = testFileCsv.readLine();
                                        continue;
                                    }
                                    String stepAction = testDataSt.nextToken();
                                    String stepFieldID = testDataSt.nextToken();
                                    String stepFieldType = testDataSt.nextToken();
                                    String stepFieldVal = testDataSt.nextToken();
                                    String stepResult = testDataSt.nextToken();

                                    UserActions userAction = new UserActions(stepAction);
                                    testFile.addTestStep(new TestStep(stepId, userAction, stepFieldID,
                                            stepFieldType, stepFieldVal, stepResult));
                                    testData = testFileCsv.readLine();
                                }
                            } else {
                                System.out.println("Test file is Disabled: " + testFile.getFileName());
                            }
                        } else {
                            System.out.println("Test file does not exist: " + testFile.getFileName());

                        }
                        suiteFileData = suiteFileCsv.readLine();
                    }

                } else {
                    System.out.println("Failed to find Test Suite: " + suiteFile.getName());
                }
            } else {
                System.out.println("Test Suite Disabled: " + suite.getTestSuiteName());

            }
            testSuites.add(suite);
            UITestSuiteData = UITestSuite.readLine();
        }
    }

    public void tearDown() throws Exception {

    }

    public void testUI()
            throws Exception {

    }

    public void redirectLogOutput(String temp) {

        //get the current output stream
        FileOutputStream outfos = null; //create //new output stream
        try {
        	System.out.println(temp);
            outfos = new FileOutputStream(temp, true);
            PrintStream newoutps = new PrintStream(outfos); //create new output //stream
            System.setOut(newoutps); //set the output stream
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void takeScreenshot() {
    }

    public String getResultsName(String filename) {
        String name = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        String date = getTimeStamp();
        return date + "." + name;
    }

    private String getTimeStamp() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return dateFormat.format(cal.getTime());
    }


}

