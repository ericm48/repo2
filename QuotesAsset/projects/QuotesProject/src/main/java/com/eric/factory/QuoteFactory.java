package com.eric.factory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.domain.constant.BaseConstants;
import com.eric.domain.constant.ErrorMessageConstants;
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

    private int pbMax = 0;
    private final int pbMin = 0;

    // -----------------------------------------------------------------
    // -----------------------------------------------------------------

    //private int initQFile()
//    private int initQFile(Properties props)    
//    {
//    	// TODO:  Rewire factory to get target quote.txt file from what adapter processes...Maybe we need
//    	// a context?  Factory already has KT of UI dialogs so it can update/maintain the progress bar....Hmmm...
//    	// The holder can basically become the context???
//    	
//		Log logger 			= methIDinitQFile;
//		String fileName 	= null;
//		String lineIn 		= null;
//	
//		logger.debug(BaseConstants.BEGINS);
//	
//		if (( props != null ) && ( props.size() > 0))
//		{
//			
//			try
//			{	
//			    // Quotes File Name
//			    fileName = props.getProperty(BaseConstants.QUOTES_EXT_FILE_KEY);
//		
//			    if ( fileName != null )
//			    {
//					logger.info("Attempting To Read: " + fileName);
//			
//					fileReader 	= new FileReader(fileName);
//			
//					bufferedReader 	= new BufferedReader(fileReader);
//			
//					// Read 1st Line & Trim It.
//					lineIn 	= bufferedReader.readLine();
//					lineIn 	= lineIn.trim();
//			
//					// Convert to int
//					this.setMaxQuotes(Integer.parseInt(lineIn));
//			
//					logger.info("Max Quotes Available: " + this.getMaxQuotes());
//		
//			    }
//			    else
//			    {
//					logger.error("*** ERROR *** Property for key: "
//						+ BaseConstants.QUOTES_EXT_FILE_KEY
//						+ " is NULL or DOES NOT EXIST in Property File: "
//						+ BaseConstants.QUOTES_PROPS + " !!");
//			    }
//			    
//		
//			}
//			catch ( FileNotFoundException fnfe )
//			{
//			    logger.error(BaseConstants.ERROR_QFILE_MIA + fileName);
//			    logger.error(fnfe.getMessage());
//			}
//			catch ( IOException ioex )
//			{
//			    logger.error(ioex.getMessage());
//			}	
//			catch ( Exception ex )
//			{
//			    logger.error("*** ERROR Exception Encountered!! Message: "
//				    + ex.getMessage());
//			}
//
//		}
//		else
//		{
//		    logger.error("Properties ARE NULL or EMPTY!!!");			
//		}
//		
//		logger.debug(BaseConstants.ENDS);
//	
//		return(this.getMaxQuotes());
//    }

// TODO: Rewire this to return the fileStream....
    
  private int initQFile(Properties props)    
  {
  	// TODO:  Rewire factory to get target quote.txt file from what adapter processes...Maybe we need
  	// a context?  Factory already has KT of UI dialogs so it can update/maintain the progress bar....Hmmm...
  	// The holder can basically become the context???
  	
		Log logger 			= methIDinitQFile;
		String fileName 	= null;
		String lineIn 		= null;
	
		logger.debug(BaseConstants.BEGINS);
	
		if (( props != null ) && ( props.size() > 0))
		{
			
			try
			{	
			    // Quotes File Name
			    fileName = props.getProperty(BaseConstants.QUOTES_EXT_FILE_KEY);
		
			    if ( fileName != null )
			    {
					logger.info("Attempting To Read: " + fileName);
			
					fileReader 	= new FileReader(fileName);
			
					bufferedReader 	= new BufferedReader(fileReader);
			
					// Read 1st Line & Trim It.
					lineIn 	= bufferedReader.readLine();
					lineIn 	= lineIn.trim();
			
					// Convert to int
					this.setMaxQuotes(Integer.parseInt(lineIn));
			
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
			    logger.error(ErrorMessageConstants.ERROR_QFILE_MIA + fileName);
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

		}
		else
		{
		    logger.error("Properties ARE NULL or EMPTY!!!");			
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
	
		boolean keepOnTruckin = true;
		boolean returnValue = true;
	
		Quote quote = null;
		DialogListener dialogListener = null;	
		ProgressComponent progressComp = null;
		Properties props = null;		
		
		int targetQuoteNumber = 0;
	
		while (keepOnTruckin)
		{
	
		    if ( dialogHolder == null )
		    {
				logger.error("DialogHolder is NULL!!!");
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
				logger.error("DialogListener IS NULL!!!");
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
		    else
		    {
				logger.error("ProgressComponent is NULL!!!");
				keepOnTruckin = false;
				break;
		    }
		    
		    props = dialogHolder.getDialogListener().getProperties(); 
		    
		    if ((props != null ) && (props.size() > 0))
		    {

		    	// HERE DUDE!!
		    	this.initQFile(props);
		    	
			    maxQuotes = dialogHolder.getDialogListener().getQuoteHolder().getMaxQuotes();			    
			    
		    }
		    else
		    {
				logger.error("Properties are NULL or EMPTY!!!");
				keepOnTruckin = false;
				break;
		    }
	
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
		
				if ( targetQuoteNumber > this.getMaxQuotes() )
				{
				    targetQuoteNumber = targetQuoteNumber % this.getMaxQuotes();
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
	
		    returnValue = closeQFile();
	
		    keepOnTruckin = false;
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
    
    	// TODO: FIX THIS!!!
//    public Quote getQuote(int targetQuoteNumber)
//    {
//		Log logger = methIDgetQuote;
//		logger.debug(BaseConstants.BEGINS);
//	
//		boolean keepOnTruckin = true;
//		boolean returnValue = true;
//	
//		int maxQuotes = 0;
//	
//		Quote quote = null;
//	
//		while (keepOnTruckin)
//		{
//	
//		    if ( (quote != null) && quote.getQuoteNumber() == targetQuoteNumber )
//		    {
//				// Already Loaded!
//				keepOnTruckin = false;
//				break;
//		    }
//	
//		    // HERE DUDE!!
//		    maxQuotes = this.initQFile();
// 			    
//		    if ( maxQuotes <= 0 )
//		    {
//				logger.error("*** ERROR:  Unable To Init Quotes File!!");
//				keepOnTruckin = false;
//				break;
//		    }
//	
//		    if ( targetQuoteNumber > maxQuotes )
//		    {
//				logger.error("*** ERROR: Target Quote # is too high!  Only: "
//					+ maxQuotes + " available. ");
//		
//				keepOnTruckin = false;
//				break;
//		    }
//	
//		    if ( targetQuoteNumber == 0 )
//		    {
//				// Generate a Random, less than the max.
//				targetQuoteNumber = MathUtil.getRandomNumber();
//		
//				if ( targetQuoteNumber > this.getMaxQuotes() )
//				{
//				    targetQuoteNumber = targetQuoteNumber % this.getMaxQuotes();
//				}
//	
//				logger.info("Random Number Generated: " + targetQuoteNumber);
//	
//		    }
//	
//		    quote = loadQuote(targetQuoteNumber, null);
//	
//		    if ( !returnValue )
//		    {
//				keepOnTruckin = false;
//				break;
//		    }
//	
//		    returnValue = closeQFile();
//	
//		    keepOnTruckin = false;
//		}
//	
//		logger.debug(BaseConstants.ENDS);
//	
//		return(quote);
//    }

    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    private Quote loadQuote(int targetQuoteNumber, DialogHolder dialogHolder)
    {
		Log logger 						= methIDloadQuote;
		boolean returnValue 			= true;
		boolean keepOnTruckin 			= true;	
		int quoteIndex 					= 0;
	
		String lineIn 					= "";
		String quoteText 				= "";
	
		Quote quote 					= null;
	
		Thread thread 					= null;
		ProgressComponent progressBar 	= null;
	
		logger.debug(BaseConstants.BEGINS);
	
		try
		{
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
				    logger.error("*** Interrupted Exception Encountered.  Message: "
						    + iex.getMessage());
				    keepOnTruckin = false;
				    break;
				}
	
		    } // end while
	
		    
		    // TODO: Fix This!!!
		    if ( returnValue )
		    {	
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
