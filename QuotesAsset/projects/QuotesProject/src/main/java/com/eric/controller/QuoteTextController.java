
package com.eric.controller;

import com.eric.domain.constant.BaseConstants;
import com.eric.domain.quote.Quote;
import com.eric.factory.QuoteFactory;

public class QuoteTextController implements QuoteController
{
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
    	
    	QuoteFactory qFactory	= null;
    	Quote quote				= null;    	
    	
    	String lineOut			= null;
    	
    	while( control )
    	{ 
    		qFactory = new QuoteFactory();

        	if ( qFactory == null )
        	{
        		System.out.println("** ERROR:  Unable To Create Factory!! ");
        		control = false;
        		break;
        	}
        	
        	// TODO: FIX THIS!!!
        	//quote = qFactory.getQuote( targetQuoteNumber );
        	
        	if ( quote == null )
        	{
           		System.out.println("*** ERROR:  Quote Number: " + 
           						   targetQuoteNumber + " Returned From Factory is Null!");
           				
        		control = false;
        		break;        		
        	}
     	
        	lineOut = this.getQuoteHeader( quote );

	    	System.out.println();   	
	    	System.out.println( lineOut );
	    	
	    	System.out.println(quote.getQuoteText());
	    	
	    	System.out.println();
	    	System.out.println();    	
        	
        	control = false;    		
    	}

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




