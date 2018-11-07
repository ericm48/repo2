package com.eric.factory;

import ab.erc.logger.Logger;
import ab.erc.logger.LoggerFactory;

import com.eric.domain.constant.BaseConstantsTest;
import com.eric.domain.quote.Quote;
import com.eric.unittest.EricUnitTestCase;

public class QuoteFactoryTest extends EricUnitTestCase 
{
		
		private static Logger methIDTestGetQuote471;
		
	    static 
	    {
	    	methIDTestGetQuote471 						= LoggerFactory.getLogger(QuoteFactoryTest.class.getName() +".testGetQuote471()");
	    }
		
		public QuoteFactoryTest()
		{
			super();
		}

		protected void setUp()
		{
		}

		protected void tearDown()
		{		
		}
		
		
		public void testGetQuote471()
		{

			Logger logger 					= methIDTestGetQuote471;
	        boolean returnValue				= false;
	        String result					= null;
	        
	        QuoteFactory qf					= null;
	        Quote q							= null;

	        logger.info("Test Begins...");
	        
	        logger.info("QuoteFactory: Creating Instance...");

	        qf = new QuoteFactory();
	        
	        assertNotNull( qf );
	        
	        logger.info("QuoteFactory: Created Successfully!");
	        
	        logger.info("QuoteFactory: Attempting to get Quote #471...");	        
	        
	        q = qf.getQuote( 471 );
	         
	        assertNotNull( q );

	        result = q.getQuoteText().trim();
	        
	        logger.info("QuoteFactory: Received As Quote: " + result );	    
	        
	        
	        if ( result.equals( BaseConstantsTest.QUOTE_471_RAW ))
	        {
		    	logger.info("QuoteFactory: SUCCESS Received Expected Result!!" );	        	
	        }
	        else
	        {
	        	logger.error("QuoteFactory: Quote #471 returned Does NOT MATCH!!!");
		    	logger.error("Value Returned: " + q.getQuoteText());
		    	logger.error("Value Expected: " + BaseConstantsTest.QUOTE_471_RAW);
		    	fail();
	        }
	        
	        logger.info("Test Ends...");
	        
	        return;
		}
		
	
}
