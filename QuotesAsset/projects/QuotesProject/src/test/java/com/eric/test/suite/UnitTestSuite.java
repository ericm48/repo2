package com.eric.test.suite;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.eric.adapter.QuotesAdapter_UT;
import com.eric.command.FormatQuoteCommand_UT;
import com.eric.factory.QuoteFactory_UT;
import com.eric.test.category.UnitTest;
import com.eric.util.FileUtil_UT;

@RunWith(Categories.class)
@IncludeCategory(UnitTest.class)

@Suite.SuiteClasses({
						QuotesAdapter_UT.class,
						FileUtil_UT.class,
						FormatQuoteCommand_UT.class,
						QuoteFactory_UT.class
					})

public class UnitTestSuite
{
	private static final Logger	methIDfirstMethod 	= Logger.getLogger(UnitTestSuite.class.getName() + ".firstMethod()");		
	private static final Logger methIDlastMethod 	= Logger.getLogger(UnitTestSuite.class.getName() + ".lastMethod()");		
	
	@BeforeClass
	public static void firstMethod()
	{		
		Logger logger = methIDfirstMethod;		
		logger.info("Quotes UNIT Test Suite Begins...");		
		return;
	}	
	
	@AfterClass
	public static void lastMethod()
	{
		Logger logger = methIDlastMethod;		
		logger.info("Quotes UNIT Test Suite Ends...");		
		return;
	}		

}
