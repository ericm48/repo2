/**
 * 
 * Description:  Main Controller for Display Panel for Text Screen.
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.adapter.QuotesAdapter;
import com.eric.command.Command;
import com.eric.command.FormatQuoteCommand;
import com.eric.domain.constant.BaseConstants;
import com.eric.domain.constant.ErrorMessageConstants;
import com.eric.domain.quote.Quote;
import com.eric.domain.quote.QuoteHolder;
import com.eric.factory.QuoteFactory;
import com.eric.ui.holder.DialogHolder;
import com.eric.ui.listener.DialogListener;

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
    	boolean keepOnTruckin 			= true;
    	Log logger						= methIDshowQuote;
    	DialogListener dialogListener 	= null;
    	DialogHolder dialogHolder		= null;
    	QuoteFactory quoteFactory		= null;
    	Command quoteCommand 			= null;
    	QuoteHolder quoteHolder			= null;
    	Quote quote						= null;    	
    	String lineOut					= null;
    	
		logger.debug(BaseConstants.BEGINS);    	
    	
    	while( keepOnTruckin )
    	{
    		
            dialogListener 				= QuotesAdapter.toDialogListener();
            
            if ( dialogListener != null )
            {
                dialogHolder = new DialogHolder();
                dialogHolder.setDialogListener(dialogListener);            	
            	dialogListener.setTargetQuoteNumber( targetQuoteNumber );
            }
            else
            {
            	logger.error(ErrorMessageConstants.DIALOGLISTENER_IS_NULL);
            	keepOnTruckin = false;
            	break;
            }
            
            quoteFactory = new QuoteFactory();
            
		    dialogHolder = quoteFactory.getQuoteWithDialogHolder(dialogHolder);

		    if ( dialogHolder == null )
        	{
           		logger.error(ErrorMessageConstants.DIALOGHOLDER_IS_NULL);           				
        		keepOnTruckin = false;
        		break;        		
        	}
		    
		    quoteCommand = new FormatQuoteCommand(dialogHolder.getDialogListener().getQuote().getQuoteText());
	    
		    keepOnTruckin = quoteCommand.execute();

		    if ( keepOnTruckin )
		    {
			   dialogHolder.getDialogListener().getQuote().setQuoteText(quoteCommand.getResult().toString());
			}

		    quoteHolder = dialogHolder.getDialogListener().getQuoteHolder();
  		    quote = quoteHolder.getQuote();     		   
     	
        	lineOut = this.getQuoteTextOutput(quoteHolder);

        	logger.info("");
	    	logger.info( lineOut );	    	
        	logger.info("");        	
        	
        	keepOnTruckin = false;    		
    	}

		logger.debug(BaseConstants.ENDS);    	
    	
    	System.exit(0);
    	
    }
    //-----------------------------------------------------------------
    private String getQuoteTextOutput(QuoteHolder quoteHolder)
    {
    	
    	String sReturnValue = null;    	
    	
    	sReturnValue = BaseConstants.CARRIAGE_RETURN +
				   	   BaseConstants.CARRIAGE_RETURN +
    				   BaseConstants.CARRIAGE_RETURN +				   	   
    				   BaseConstants.APP_TITLE + 
    				   BaseConstants.CARRIAGE_RETURN +    				   
    				   BaseConstants.VERSION + quoteHolder.getQuotesAppVersion() +
    				   BaseConstants.CARRIAGE_RETURN +    				   
    				   BaseConstants.JDK.substring(3) + quoteHolder.getCurrentJDK() +  
    				   BaseConstants.CARRIAGE_RETURN +    				   
    				   BaseConstants.CARRIAGE_RETURN +    				   
    				   BaseConstants.QUOTE_NUMBER + 
    				   quoteHolder.getQuote().getQuoteNumber() +
    				   BaseConstants.CARRIAGE_RETURN +    				   
    				   quoteHolder.getQuote().getQuoteText() +
    				   BaseConstants.CARRIAGE_RETURN +
    				   BaseConstants.CARRIAGE_RETURN +
    				   BaseConstants.CARRIAGE_RETURN;
    	    	    	    	
        return( sReturnValue );
    }   
}




