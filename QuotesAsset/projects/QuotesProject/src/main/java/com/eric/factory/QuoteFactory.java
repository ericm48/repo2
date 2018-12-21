package com.eric.factory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.domain.constant.BaseConstants;
import com.eric.domain.quote.Quote;
import com.eric.ui.component.progress.ProgressComponent;
import com.eric.ui.holder.DialogHolder;
import com.eric.ui.listener.DialogListener;
import com.eric.util.MathUtil;

public class QuoteFactory
{

    private static final Log methIDgetQuote;
    private static final Log methIDinitQFile;
    private static final Log methIDloadQuote;
    private static final Log methIDgetQuoteWithHolder;

    static
    {
		methIDgetQuote = LogFactory.getLog(QuoteFactory.class.getName()
			+ ".getQuote()");
		methIDinitQFile = LogFactory.getLog(QuoteFactory.class.getName()
			+ ".initQFile()");
		methIDloadQuote = LogFactory.getLog(QuoteFactory.class.getName()
			+ ".LoadQuote()");
		methIDgetQuoteWithHolder = LogFactory.getLog(QuoteFactory.class.getName()
			+ ".getQuoteWithHolder()");
	    }
    // Declare file reader and writer streams
    private FileReader fileReader = null;

    // Buffered Reader
    private BufferedReader bufferedReader = null;

    private int maxQuotes = 0;

    private int iPBMax = 0;
    private final int iPBMin = 0;

    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    private int initQFile()
    {
    	// TODO:  Rewire factory to get target quote.txt file from what adapter processes...Maybe we need
    	// a context?  Factory already has KT of UI dialogs so we can maintain the progress bar....Hmmm...
    	// The holder can basically become the context???
    	
		Log logger = methIDinitQFile;
		String fileName = null;
		String sLineIn = null;
		Properties props = null;
	
		logger.debug(BaseConstants.BEGINS);
	
		if ( props == null )
		{
		    props = new Properties();
		}
	
		try
		{
		    logger.info("Attempting To Load: " + BaseConstants.QUOTES_PROPS);
		    props.load(QuoteFactory.class
			    .getResourceAsStream(BaseConstants.QUOTES_PROPS));
	
		    // Quotes File Name
		    fileName = props.getProperty(BaseConstants.QUOTES_EXT_FILE_KEY);
	
		    if ( fileName != null )
		    {
				logger.info("Attempting To Read: " + fileName);
		
				fileReader 	= new FileReader(fileName);
		
				bufferedReader 	= new BufferedReader(fileReader);
		
				// Read 1st Line & Trim It.
				sLineIn 	= bufferedReader.readLine();
				sLineIn 	= sLineIn.trim();
		
				// Convert to int
				this.setMaxQuotes(Integer.parseInt(sLineIn));
		
				logger.info("Max Quotes Available: " + this.getMaxQuotes());
	
		    }
		    else
		    {
				logger.error("*** ERROR *** Property for key: "
					+ BaseConstants.QUOTES_EXT_FILE_KEY
					+ " is NULL or DOES NOT EXIST in Property File: "
					+ BaseConstants.QUOTES_PROPS + " !!");
		    }
	
		}
		catch ( FileNotFoundException fnfe )
		{
		    logger.error(BaseConstants.ERROR_QFILE_MIA + fileName);
		    logger.error(fnfe.getMessage());
		}
		catch ( IOException ioex )
		{
		    logger.error(ioex.getMessage());
		}	
		catch ( Exception ex )
		{
		    logger.error("*** ERROR Exception Encountered!! Message: "
			    + ex.getMessage());
		}
	
		logger.debug(BaseConstants.ENDS);
	
		return(this.getMaxQuotes());
    }

    /**
     * 
     * Returns a Simple Quote for a specific Quote Number
     * 
     * @param quoteListener
     *            -Listener Object To Process & Return.
     * 
     */
    public DialogHolder getQuoteWithHolder(DialogHolder dialogHolder)
    {
		Log logger = methIDgetQuoteWithHolder;
		logger.debug(BaseConstants.BEGINS);
	
		boolean control = true;
		boolean returnValue = true;
	
		Quote quote = null;
		DialogListener dialogListener = null;	
		ProgressComponent progressComp = null;
	
		int targetQuoteNumber = 0;
	
		while (control)
		{
	
		    if ( dialogHolder == null )
		    {
				logger.error("QuoteTO is NULL!!!");
				control = false;
				break;
		    }
	
		    if ( dialogHolder.getDialogListener() != null )
		    {
				// Set Our pointer to the Listener Object.
				dialogListener = dialogHolder.getDialogListener();
		    }
		    else
		    {
				logger.error("Listener IS NULL!!!");
				control = false;
				break;
		    }
	
		    if ( dialogHolder.getProgressComponent() != null )
		    {
				progressComp = dialogHolder.getProgressComponent();
		
				progressComp.setMinValue(iPBMin);
				progressComp.setMaxValue(iPBMax);
		
				progressComp.setValue(iPBMin);
	
		    }
		    else
		    {
				logger.error("Progress Bar is NULL!!!");
				control = false;
				break;
		    }
	
		    maxQuotes = this.initQFile();
	
		    if ( maxQuotes > 0 )
		    {
		    	dialogHolder.getDialogListener().setMaxQuotes(maxQuotes);
		    }
		    else
		    {
				logger.error("*** ERROR:  Unable To Init Quotes File!!");
				control = false;
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
		
				control = false;
				break;
		    }
	
		    if ( targetQuoteNumber == 0 )
		    {
				// Generate a Random, less than the max.
				targetQuoteNumber = MathUtil.getRandomNumber();
		
				if ( targetQuoteNumber > this.getMaxQuotes() )
				{
				    targetQuoteNumber = targetQuoteNumber % this.getMaxQuotes();
				}
		
				logger.info("Random Number Generated: " + targetQuoteNumber);
		
				dialogListener.setTargetQuoteNumber(targetQuoteNumber);
	
		    }
	
		    if ( progressComp != null )
		    {
				iPBMax = targetQuoteNumber;
		
				progressComp.setMinValue(iPBMin);
				progressComp.setMaxValue(iPBMax);
				progressComp.setValue(iPBMin);
		    }
	
		    quote = loadQuote(targetQuoteNumber, dialogHolder);
	
		    if ( quote == null )
		    {
				logger.error("Quote Returned IS NULL!!!");
				control = false;
				break;
		    }
	
		    returnValue = closeQFile();
	
		    control = false;
		}
	
		logger.debug(BaseConstants.ENDS);
	
		return(dialogHolder);
    }

    /**
     * 
     * Returns a Simple Quote for a specific Quote Number
     * 
     * @param targetQuoteNumber
     *            -Target Quote Number to retrieve.
     * 
     */
    public Quote getQuote(int targetQuoteNumber)
    {
		Log logger = methIDgetQuote;
		logger.debug(BaseConstants.BEGINS);
	
		boolean control = true;
		boolean returnValue = true;
	
		int maxQuotes = 0;
	
		Quote quote = null;
	
		while (control)
		{
	
		    if ( (quote != null) && quote.getQuoteNumber() == targetQuoteNumber )
		    {
				// Already Loaded!
				control = false;
				break;
		    }
	
		    maxQuotes = this.initQFile();
	
		    if ( maxQuotes <= 0 )
		    {
				logger.error("*** ERROR:  Unable To Init Quotes File!!");
				control = false;
				break;
		    }
	
		    if ( targetQuoteNumber > maxQuotes )
		    {
				logger.error("*** ERROR: Target Quote # is too high!  Only: "
					+ maxQuotes + " available. ");
		
				control = false;
				break;
		    }
	
		    if ( targetQuoteNumber == 0 )
		    {
				// Generate a Random, less than the max.
				targetQuoteNumber = MathUtil.getRandomNumber();
		
				if ( targetQuoteNumber > this.getMaxQuotes() )
				{
				    targetQuoteNumber = targetQuoteNumber % this.getMaxQuotes();
				}
	
				logger.info("Random Number Generated: " + targetQuoteNumber);
	
		    }
	
		    quote = loadQuote(targetQuoteNumber, null);
	
		    if ( !returnValue )
		    {
				control = false;
				break;
		    }
	
		    returnValue = closeQFile();
	
		    control = false;
		}
	
		logger.debug(BaseConstants.ENDS);
	
		return(quote);
    }

    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    private Quote loadQuote(int targetQuoteNumber, DialogHolder dialogHolder)
    {
		Log logger 		= methIDloadQuote;
		boolean bReturnValue 	= true;
		boolean control 	= true;
	
		int iNQuote 		= 0;
	
		String sLineIn 		= "";
		String sQuote 		= "";
	
		Quote quote 		= null;
	
		Thread thread 		= null;
		ProgressComponent progressBar = null;
	
		logger.debug(BaseConstants.BEGINS);
	
		try
		{
		    sLineIn = "";
	
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
	
		    while ((control) && (sLineIn != null))
		    {
				try
				{
				    if ( thread != null )
				    {
				    	thread.sleep(1);
				    }
		
				    sLineIn = bufferedReader.readLine();
		
				    // Start of a Quote, Blank Line
				    if ( sLineIn.trim().length() == 0 )
				    {		
						if ( iNQuote == targetQuoteNumber )
						{
						    control = false;
						    logger.info("Quote: " + targetQuoteNumber
							    + " LOCATED!");
						}
						else
						{
		
						    // If using a progress bar, calculate it's position.
						    if ( progressBar != null )
						    {
						    	progressBar.setValue(iNQuote);
						    }
		
						    sQuote = "";
						    iNQuote++;
						}
		
				    }
				    else
				    {
				    	sQuote = sQuote + " " + sLineIn;
				    }
		
				}
				catch ( InterruptedException iex )
				{
				    logger.error("*** Interrupted Exception Encountered.  Message: "
						    + iex.getMessage());
				    control = false;
				    break;
				}
	
		    } // end while
	
		    if ( bReturnValue )
		    {	
				quote = new Quote();
				quote.setQuoteText(sQuote);
				quote.setQuoteNumber(iNQuote);
		
				if ( dialogHolder != null )
				{
				    if ( dialogHolder.getDialogListener() != null )
				    {
				    	dialogHolder.getDialogListener().setQuote(quote);
				    }
				}
	
		    }
	
		} // end try
	
		catch ( IOException ioex )
		{
		    logger.error("*** ERROR Encountered.  Message: "
			    + ioex.getMessage());
		}
	
		logger.debug(BaseConstants.ENDS);
		return(quote);

    }
 
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    private boolean closeQFile()
    {
		boolean returnValue = true;
	
		try
		{
		    if ( fileReader != null )
		    {
		    	fileReader.close();
		    }
		}
		catch ( IOException ex )
		{
		    System.out.println(ex);
		}

		return(returnValue);

    }

    public int getMaxQuotes()
    {
    	return maxQuotes;
    }

    public void setMaxQuotes(int maxQuotes)
    {
    	this.maxQuotes = maxQuotes;
    }
}
