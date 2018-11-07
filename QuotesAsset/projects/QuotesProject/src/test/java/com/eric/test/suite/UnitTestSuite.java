package com.eric.test.suite;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.eric.command.FormatQuoteCommandTest;
import com.eric.test.category.UnitTest;

@RunWith(Categories.class)
@IncludeCategory(UnitTest.class)

@Suite.SuiteClasses({
						FormatQuoteCommandTest.class
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
