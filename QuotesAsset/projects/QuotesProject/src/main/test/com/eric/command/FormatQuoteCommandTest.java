/*
 * COPYRIGHT 2008 BY ANHEUSER-BUSCH. ALL RIGHTS RESERVED.
 *
 * $Name: DataSourceTest.java $
 * $Revision: $
 * $Date: 2008-03-11 17:33:37 GMT $
 * $Author: ZMM0362 $
 */

package com.eric.command;

import ab.erc.logger.Logger;
import ab.erc.logger.LoggerFactory;

import com.eric.domain.constant.BaseConstantsTest;
import com.eric.unittest.EricUnitTestCase;

public class FormatQuoteCommandTest extends EricUnitTestCase 
{
	
	private static Logger methIDTestFormatUnFormat;
	
    static 
    {
    	methIDTestFormatUnFormat 						= LoggerFactory.getLogger(FormatQuoteCommandTest.class.getName() +".testFormatUnFormat()");
    }
	
	public FormatQuoteCommandTest()
	{
		super();
	}

	protected void setUp()
	{
	}

	protected void tearDown()
	{		
	}
	
	
	public void testFormatUnFormat()
	{

		Logger logger 					= methIDTestFormatUnFormat;
        boolean returnValue				= false;
        String result					= null;

        
        Command qc						= null;

        logger.info("Test Begins...");	
		
        qc = new FormatQuoteCommand( BaseConstantsTest.QUOTE_471_RAW );
		
        logger.info("FormatQuoteCommand: Loaded With #471 Raw, Created Successfully....");
        
	    assertNotNull( qc );

        logger.info("FormatQuoteCommand: Attempting EXECUTE()");   
	    returnValue = qc.execute();

	    assertTrue( returnValue );
	    logger.info("FormatQuoteCommand: Executed Correctly.");	    
			
	    result = qc.getResult().toString();
	    
	    assertNotNull( result );
	    logger.info("FormatQuoteCommand: Execute Result Returned: " + result);
	    
	    if ( result.equals( BaseConstantsTest.QUOTE_471_FORMATTED ))
	    {
	    	
	    	logger.info("FormatQuoteCommand: Execute SUCCESS Received Expected Result!!" );
	    	
	    	result = null;

	        logger.info("FormatQuoteCommand: Attempting UNDO()");   
	    	
	    	returnValue = qc.undo();
		    
	    	assertTrue( returnValue );
		    logger.info("FormatQuoteCommand: UnDo Correctly.");	    
	    	
		    result = qc.getResult().toString();
		    
		    assertNotNull( result );
	    	
		    logger.info("FormatQuoteCommand: Undo Result Returned: " + result);	    	
	    	
		    if ( result.equals( BaseConstantsTest.QUOTE_471_RAW ))
		    {
		    	logger.info("FormatQuoteCommand: Undo SUCCESS Received Expected Result!!" );		    
		    }
	    	
	    }
	    else
	    {
	    	logger.error("*** ERROR: FormatQuoteCommand Format FAILS");
	    	logger.error("Value Returned: " + result);
	    	logger.error("Value Expected: " + BaseConstantsTest.QUOTE_471_FORMATTED);
	    	
	    	fail();
	    	
	    }
	    
	    qc = null;
	    
		logger.info("Test Ends...");	
        
		return;
	}
	
	
	public void testX(boolean failON)
	{	
		return;				
	}	



}
