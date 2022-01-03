package com.eric.test.suite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.core.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.eric.command.FormatQuoteCommand_UT;
import com.eric.test.category.IntegrationTest;


@RunWith(Categories.class)
@IncludeCategory(IntegrationTest.class)

@Suite.SuiteClasses({
						FormatQuoteCommand_UT.class
					})

public class IntegrationTestSuite
{
	
	private static final Log	methIDfirstMethod 	= LogFactory.getLog(IntegrationTestSuite.class.getName() + ".firstMethod()");		
	private static final Log   	methIDlastMethod 	= LogFactory.getLog(IntegrationTestSuite.class.getName() + ".lastMethod()");		
	
	@BeforeClass
	public static void firstMethod()
	{		
		Log logger = methIDfirstMethod;		
		logger.info("Quotes INTEGRATION Test Suite Begins...");		
		return;
	}	
	
	@AfterClass
	public static void lastMethod()
	{
		Log logger = methIDlastMethod;		
		logger.info("Quotes INTEGRATION Test Suite Ends...");		
		return;
	}	

}
