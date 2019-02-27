/**
 * 
 * Description:  Factory for creating the Quote Domain Object, is optionally aware of a progress
 * 				 component, which it will update as it searches for the target quote.
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.domain.common.enumeration.QuotesInputFileType;
import com.eric.domain.constant.BaseConstants;
import com.eric.domain.constant.ErrorMessageConstants;
import com.eric.domain.message.ExMessages;
import com.eric.domain.quote.Quote;
import com.eric.domain.quote.QuoteHolder;
import com.eric.ui.component.progress.ProgressComponent;
import com.eric.ui.holder.DialogHolder;
import com.eric.ui.listener.DialogListener;
import com.eric.util.FileUtil;
import com.eric.util.MathUtil;

public class QuoteFactory
{

    private static final Log methIDloadQuote;
    private static final Log methIDgetQuoteWithDialogHolder;
    private static final Log methIDgetQuoteWithQuoteHolder;
    
    static
    {
		methIDloadQuote = LogFactory.getLog(QuoteFactory.class.getName() + ".LoadQuote()");
		methIDgetQuoteWithDialogHolder = LogFactory.getLog(QuoteFactory.class.getName() + ".getQuoteWithDialogHolder()");
		methIDgetQuoteWithQuoteHolder = LogFactory.getLog(QuoteFactory.class.getName() + ".getQuoteWithQuoteHolder()");		
	}
    
    /**
     * 
     * Returns a Simple Quote for a specific Quote Number
     * 
     * @param quoteListener
     *            -Listener Object To Process & Return.
     * 
     */
    public DialogHolder getQuoteWithDialogHolder(DialogHolder dialogHolder)
    {
		Log logger 						= methIDgetQuoteWithDialogHolder;	
		boolean keepOnTruckin 			= true;
		boolean returnValue 			= true;	
		Quote quote 					= null;
		DialogListener dialogListener 	= null;	
		ProgressComponent progressComp 	= null;
		Properties props 				= null;		
		int targetQuoteNumber 			= 0;
		int pbMax						= 0;
		int pbMin						= 0;
		int maxQuotes					= 0;			

		logger.debug(BaseConstants.BEGINS);

		while (keepOnTruckin)
		{
	
		    if ( dialogHolder == null )
		    {
				logger.error(ErrorMessageConstants.DIALOGHOLDER_IS_NULL);
				keepOnTruckin = false;
				break;
		    }
	
		    if ( dialogHolder.getDialogListener() != null )
		    {
				// Set Our pointer to the Listener Object.
				dialogListener = dialogHolder.getDialogListener();
		    }
		    else
		    {
				logger.error(ErrorMessageConstants.DIALOGLISTENER_IS_NULL);
				keepOnTruckin = false;
				break;
		    }
	
		    if ( dialogHolder.getProgressComponent() != null )
		    {
				progressComp = dialogHolder.getProgressComponent();		
				progressComp.setMinValue(pbMin);
				progressComp.setMaxValue(pbMax);		
				progressComp.setValue(pbMin);	
		    }
	    
		    props = dialogHolder.getDialogListener().getQuoteHolder().getProperties(); 
    
			if ((props == null ) || (props.size() <= 0))		    	
		    {
				logger.error("Properties are NULL or EMPTY!!!");
				keepOnTruckin = false;
				break;
		    }

		    maxQuotes = dialogHolder.getDialogListener().getQuoteHolder().getMaxQuotes();
			
		    if ( maxQuotes > 0 )
		    {
		    	dialogHolder.getDialogListener().setMaxQuotes(maxQuotes);
		    }
		    else
		    {
				logger.error("*** ERROR:  Unable To Init Quotes File!!");
				keepOnTruckin = false;
				break;
		    }
	
		    if ( dialogListener.getTargetQuoteNumber() <= maxQuotes )
		    {
		    	targetQuoteNumber = dialogListener.getTargetQuoteNumber();
		    }
		    else
		    {
				logger.error("*** ERROR: Target Quote # is too high!  Only: "
					+ maxQuotes + " available. ");
		
				keepOnTruckin = false;
				break;
		    }
	
		    if ( targetQuoteNumber == 0 )
		    {
				// Generate a Random, less than the max.
				targetQuoteNumber = MathUtil.getRandomNumber();
		
				if ( targetQuoteNumber > maxQuotes )
				{
				    targetQuoteNumber = targetQuoteNumber % maxQuotes;
				}
		
				logger.info("Random Number Generated: " + targetQuoteNumber);
		
				dialogListener.setTargetQuoteNumber(targetQuoteNumber);
	
		    }
	
		    if ( progressComp != null )
		    {
				pbMax = targetQuoteNumber;
		
				progressComp.setMinValue(pbMin);
				progressComp.setMaxValue(pbMax);
				progressComp.setValue(pbMin);
		    }
	
		    quote = loadQuote(targetQuoteNumber, dialogHolder);
	
		    if ( quote == null )
		    {
				logger.error("Quote Returned IS NULL!!!");
				keepOnTruckin = false;
				break;
		    }
	
		    keepOnTruckin = false;
		}
	
		logger.debug(BaseConstants.ENDS);
	
		return(dialogHolder);
    }

    /**
	 * 
	 * Returns a Simple Quote for a specific Quote Number
	 * 
	 * @param quoteListener
	 *            -Listener Object To Process & Return.
	 * 
	 */
	public QuoteHolder getQuoteWithQuoteHolder(QuoteHolder quoteHolder)
	{
		Log logger 						= methIDgetQuoteWithQuoteHolder;	
		boolean keepOnTruckin 			= true;
		boolean returnValue 			= true;	
		Quote quote 					= null;
		Properties props 				= null;		
		int targetQuoteNumber 			= 0;
		int maxQuotes					= 0;			
	
		logger.debug(BaseConstants.BEGINS);
	
		while (keepOnTruckin)
		{
	
		    if ( quoteHolder == null )
		    {
				logger.error(ErrorMessageConstants.QUOTEHOLDER_IS_NULL);
				keepOnTruckin = false;
				break;
		    }
		    
		    props = quoteHolder.getProperties(); 
	
			if ((props == null ) || (props.size() <= 0))		    	
		    {
				logger.error("Properties are NULL or EMPTY!!!");
				keepOnTruckin = false;
				break;
		    }
	
		    maxQuotes = quoteHolder.getMaxQuotes();

		    if ( targetQuoteNumber == 0 )
		    {
				// Generate a Random, less than the max.
				targetQuoteNumber = MathUtil.getRandomNumber();
		
				if ( targetQuoteNumber > maxQuotes )
				{
				    targetQuoteNumber = targetQuoteNumber % maxQuotes;
				}
		
				logger.info("Random Number Generated: " + targetQuoteNumber);
		
				quoteHolder.setTargetQuoteNumber(targetQuoteNumber);
	
		    }

		    if ( quote == null )
		    {
				logger.error("Quote Returned IS NULL!!!");
				keepOnTruckin = false;
				break;
		    }
	
		    keepOnTruckin = false;
		}
	
		logger.debug(BaseConstants.ENDS);
	
		return(quoteHolder);
	}
    
    /**
     * 
     * Returns a Simple Quote for a specific Quote Number
     * 
     * @param targetQuoteNumber
     *            -Target Quote Number to retrieve.
     * 
     */
    
    // -----------------------------------------------------------------
    private Quote loadQuote(int targetQuoteNumber, DialogHolder dialogHolder)
    {
		Log logger 								= methIDloadQuote;
		boolean returnValue 					= true;
		boolean keepOnTruckin 					= true;	
		int quoteIndex 							= 0;
		Properties props						= null;
		QuotesInputFileType quotesInputFileType = QuotesInputFileType.NOT_SET;
		
		String lineIn 							= "";
		String quoteText 						= "";
		FileReader fileReader					= null;		
		BufferedReader bufferedReader			= null;
		
		Quote quote 							= null;
	
		Thread thread 							= null;
		ProgressComponent progressBar 			= null;
	
		logger.debug(BaseConstants.BEGINS);
	
	    lineIn = "";
	    
	    if ( dialogHolder != null )
	    {
			if ( dialogHolder.getThread() != null )
			{
			    thread = dialogHolder.getThread();
			}
	
			if ( dialogHolder.getProgressComponent() != null )
			{
			    progressBar = dialogHolder.getProgressComponent();
			}
	    }

	    logger.info("Locating Quote: " + targetQuoteNumber);
	    
	    while ( keepOnTruckin )
	    {
		    props = dialogHolder.getDialogListener().getQuoteHolder().getProperties();
		    
			if ( props == null )
			{
				logger.error(ErrorMessageConstants.PROPS_ARE_NULL);				
				keepOnTruckin = false;
				break;				
			}
		
			quotesInputFileType = FileUtil.getTargetQuotesFileType(props);
			
			if (quotesInputFileType.equals(QuotesInputFileType.NOT_SET))
			{
				logger.error(ErrorMessageConstants.QUOTES_INPUT_FILE_TYPE_NOT_SET);				
				keepOnTruckin = false;
				break;
			}
		    
		    fileReader = FileUtil.getFileReaderToQuotesFile(props, quotesInputFileType);
		    
		    if (fileReader == null)
		    {
				logger.error(ErrorMessageConstants.FILE_READER_IS_NULL);				
				keepOnTruckin = false;
				break;		    	
		    }		    
		    
		    bufferedReader = new BufferedReader(fileReader);		    
	    
		    try
		    {			    
			    while ((keepOnTruckin) && (lineIn != null))
			    {
					try
					{
					    if ( thread != null )
					    {
					    	thread.sleep(1);
					    }
			
					    lineIn = bufferedReader.readLine();
			
					    // Start of a Quote, Blank Line
					    if ( lineIn.trim().length() == 0 )
					    {		
							if ( quoteIndex == targetQuoteNumber )
							{
							    keepOnTruckin = false;
							    logger.info("Quote: " + targetQuoteNumber + " LOCATED!");
							    
								quote = new Quote();
								quote.setQuoteText(quoteText);
								quote.setQuoteNumber(quoteIndex);
						
								if ( dialogHolder != null )
								{
								    if ( dialogHolder.getDialogListener() != null )
								    {
								    	dialogHolder.getDialogListener().setQuote(quote);
								    }
								}				    
							    
							}
							else
							{			
							    // If using a progress bar, calculate it's position.
							    if ( progressBar != null )
							    {
							    	progressBar.setValue(quoteIndex);
							    }
			
							    quoteText = "";
							    quoteIndex++;
							}
			
					    }
					    else
					    {
					    	quoteText = quoteText + " " + lineIn;
					    }
			
					}
					catch ( InterruptedException iex )
					{
						String lineOut = null;
						
						lineOut = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED, 
												InterruptedException.class.getName(), 
												iex.getMessage());
	
						logger.error(lineOut);
						
					    keepOnTruckin = false;
					    break;
					}
		
			    } // end while
		
			    bufferedReader.close();
			    
			    FileUtil.closeFile(fileReader);
		
			} // end try
		
			catch ( IOException ioex )
			{
				String lineOut = null;
				
				lineOut = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED, 
										IOException.class.getName(), 
										ioex.getMessage());

				logger.error(lineOut);
				
			}

		    // Safety Purposes
		    keepOnTruckin = false;
		    break;
		    
	    }
		logger.debug(BaseConstants.ENDS);
		
		return(quote);

    }

}
