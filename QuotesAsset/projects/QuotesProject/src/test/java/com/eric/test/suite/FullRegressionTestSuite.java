/**
 * © 2014 CenturyLink. All Rights Reserved.
 */
package com.eric.test.suite;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.eric.command.FormatQuoteCommand_UT;


@RunWith( Suite.class )
@Suite.SuiteClasses({
						FormatQuoteCommand_UT.class
					})

/**
 * This Suite is the FULL-REGRESSION test Suite (ie Non-INT).
 *  
 * @author eric.manley
 * 
 */

public class FullRegressionTestSuite
{

	private static final Logger	methIDfirstMethod 	= Logger.getLogger(FullRegressionTestSuite.class.getName() + ".firstMethod()");		
	private static final Logger methIDlastMethod 	= Logger.getLogger(FullRegressionTestSuite.class.getName() + ".lastMethod()");		
	
	@BeforeClass
	public static void firstMethod()
	{		
		Logger logger = methIDfirstMethod;		
		logger.info("Quotes FULL-REGRESSION Test Suite Begins...");		
		return;
	}	
	
	@AfterClass
	public static void lastMethod()
	{
		Logger logger = methIDlastMethod;		
		logger.info("Quotes FULL-REGRESSION Test Suite Ends...");		
		return;
	}
	
}
