/**
  */
package com.eric.test.suite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	private static final Log methIDfirstMethod 	= LogFactory.getLog(FullRegressionTestSuite.class.getName() + ".firstMethod()");		
	private static final Log methIDlastMethod 	= LogFactory.getLog(FullRegressionTestSuite.class.getName() + ".lastMethod()");		
	
	@BeforeClass
	public static void firstMethod()
	{		
		Log logger = methIDfirstMethod;		
		logger.info("Quotes FULL-REGRESSION Test Suite Begins...");		
		return;
	}	
	
	@AfterClass
	public static void lastMethod()
	{
		Log logger = methIDlastMethod;		
		logger.info("Quotes FULL-REGRESSION Test Suite Ends...");		
		return;
	}
	
}
