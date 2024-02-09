package com.core;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SeleniumTest extends TestCase{

    public SeleniumTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        System.out.println("Trying");
        return new TestSuite( UITesting.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testSeleniumTest()
    {
        assertTrue( true );
    }
}
