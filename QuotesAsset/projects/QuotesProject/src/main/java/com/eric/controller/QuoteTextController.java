
package com.eric.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.adapter.QuotesAdapter;
import com.eric.domain.constant.BaseConstants;
import com.eric.domain.quote.Quote;
import com.eric.factory.QuoteFactory;

public class QuoteTextController implements QuoteController
{
	
	private static Log methIDshowQuote;
	
	   static
	    {
		   methIDshowQuote 				= LogFactory.getLog(QuoteTextController.class.getName() + ".showQuote()");
	    }	
	
    private int       maxQuotes;
   
    //-----------------------------------------------------------------
    // Default Constructor
    //-----------------------------------------------------------------
    public QuoteTextController()
    {
        super();
    }    
    //-----------------------------------------------------------------
    // Constructor a quote from specific quote number.
    //-----------------------------------------------------------------
    public QuoteTextController(int iThisQuote)
    {
        super();
    }  
    //-----------------------------------------------------------------
    // Show This Quote
    //-----------------------------------------------------------------
    public void showQuote(int targetQuoteNumber)
    {    	
    	boolean control 		= true;
    	Log logger				= methIDshowQuote;
    	
    	QuoteFactory qFactory	= null;
    	Quote quote				= null;    	
    	
    	String lineOut			= null;
    	
		logger.debug(BaseConstants.BEGINS);    	
    	
    	while( control )
    	{ 
    		
    		// TODO:  Require This to call the adapter, get a quoteHolder (has JDK, Version, MaxQuotes, internal/external)?
    		// or same method with dialogHolder, just null for dialog props?
    		// TODO:  Next call appropo method on factory...could pass it with dialogHolder, just make sure
    		// we got null checks for UI controls, and do nothing if they are null...
    		
    		qFactory = new QuoteFactory();

        	// TODO: FIX THIS!!!  Text Mode.
        	//quote = qFactory.getQuote( targetQuoteNumber );
        	
        	if ( quote == null )
        	{
           		logger.error("*** ERROR:  Quote Number: " + targetQuoteNumber + " Returned From Factory is Null!");           				
        		control = false;
        		break;        		
        	}
     	
        	lineOut = this.getQuoteHeader( quote );

        	logger.info("");
	    	logger.info( lineOut );	    	
	    	logger.info(quote.getQuoteText());	    	
        	logger.info("");
        	logger.info("");        	
        	
        	control = false;    		
    	}

		logger.debug(BaseConstants.ENDS);    	
    	
    	System.exit(0);
    	
    }
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------
    private String getQuoteHeader(Quote quote)
    {
    	
    	String sReturnValue = null;    	
    	
    	sReturnValue = BaseConstants.CARRIAGE_RETURN + 
    				   BaseConstants.APP_TITLE + 
    				   BaseConstants.CARRIAGE_RETURN +
    				   BaseConstants.QUOTE_NUMBER + 
    				   quote.getQuoteNumber();
    	    	    	    	
        return( sReturnValue );
    }   
}




