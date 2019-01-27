package com.eric.adapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.domain.common.enumeration.QuoteInputFile;
import com.eric.domain.constant.BaseConstants;
import com.eric.domain.constant.ErrorMessageConstants;
import com.eric.domain.quote.Quote;
import com.eric.domain.quote.QuoteHolder;
import com.eric.ui.listener.DialogListener;
import com.eric.util.FileUtil;

public class QuotesAdapter
{
    private static final Log methIDtoDialogListener;
    private static final Log methIDgetAppVersion;
    private static final Log methIDgetMaxQuotes;
    private static final Log methIDgetMaxQuotesFromFile;
    private static final Log methIDgetJDKVersion;    
    
    static
    {
    	methIDtoDialogListener 				= LogFactory.getLog(QuotesAdapter.class.getName() + ".toDialogListener()");
    	methIDgetAppVersion 				= LogFactory.getLog(QuotesAdapter.class.getName() + ".getAppVersion()");
    	methIDgetMaxQuotes					= LogFactory.getLog(QuotesAdapter.class.getName() + ".getMaxQuotes()");     	
    	methIDgetMaxQuotesFromFile			= LogFactory.getLog(QuotesAdapter.class.getName() + ".getMaxQuotesFromFile()");
    	methIDgetJDKVersion					= LogFactory.getLog(QuotesAdapter.class.getName() + ".getJDKVersion()");  	
    }
    
    public static DialogListener toDialogListener()
    {
    	Log logger = methIDtoDialogListener;
    	
    	DialogListener dialogListener = null; 
    	QuoteHolder quoteHolder = null;
    	Quote quote = null;
    	
    	Properties props = null;
    	String appVersion = null;
    	String JDKVersion = null;
    	int maxQuotes = 0;
    	
    	boolean keepOnTruckin = true;
    	
		logger.debug(BaseConstants.BEGINS);
    	
		while ( keepOnTruckin )
		{			
	    	props = FileUtil.initFileSet();
	    	
	    	if ( props == null )
	    	{
	    		logger.error(ErrorMessageConstants.PROPS_ARE_NULL);
	    		keepOnTruckin = false;
	    		break;
	    	}
	    	
	    	if ( props.size() <= 0)
	    	{
	    		logger.error(ErrorMessageConstants.PROPS_ARE_EMPTY);
	    		keepOnTruckin = false;
	    		break;
	    	}  	

	    	appVersion = getAppVersion(props);
	    	logger.debug("Application Version Loaded: " + appVersion);
	    	
	    	if ( appVersion == null )
	    	{	    		
	    		logger.error(ErrorMessageConstants.APP_VERSION_IS_NULL);	    		
	    		keepOnTruckin = false;
	    		break;	    		
	    	}
	    		    	
	    	maxQuotes = getMaxQuotes(props);
	    	logger.debug("Max Quotes Loaded: " + maxQuotes);
	    	
	    	if ( maxQuotes <= 0 )
	    	{
	    		logger.error(ErrorMessageConstants.MAX_QUOTES_IS_ZERO);	    		
	    		keepOnTruckin = false;
	    		break;	    			    		
	    	}
	    	
	    	JDKVersion = getJDKVersion();
	    	if ( JDKVersion == null )
	    	{
	    		logger.error(ErrorMessageConstants.JDK_VERSION_IS_NULL);	    		
	    		keepOnTruckin = false;
	    		break;	    			    		
	    	}
	    	
	    	// Build the Object Structure
	    	dialogListener = new DialogListener();

	    	quoteHolder = new QuoteHolder();	    	
	    	dialogListener.setQuoteHolder(quoteHolder);
	    	
	    	dialogListener.setQuotesAppVersion(appVersion);
	    	dialogListener.setMaxQuotes(maxQuotes);
	    	dialogListener.setCurrentJDK(JDKVersion);
	    	dialogListener.setProperties(props);
	    	
	    	// TODO: Do we Need This?
	    	//quote = new Quote();	    	
	    	//dialogListener.getQuoteHolder().setQuote(quote);
	    	
			// Safety Purposes
			keepOnTruckin = false;
			break;
			
		}
    	
		logger.debug(BaseConstants.ENDS);

    	return( dialogListener );
    }

    private static int getMaxQuotes(Properties props)
    {
		Log logger = methIDgetMaxQuotes;
		
		int returnValue = 0;
		
		logger.debug(BaseConstants.BEGINS);
		
		returnValue = getMaxQuotesFromFile(props, QuoteInputFile.EXTERNAL);
		
		if ( returnValue <= 0 )
		{
			returnValue = getMaxQuotesFromFile(props, QuoteInputFile.INTERNAL);			
		}		
		
		logger.debug(BaseConstants.ENDS);
		
		return( returnValue );    	
    }      

    private static int getMaxQuotesFromFile(Properties props, QuoteInputFile quoteInputFile)
    {
		Log logger = methIDgetMaxQuotesFromFile;
    	
		int returnValue = 0;
	    FileReader fileReader = null;

	    // Buffered Reader
	    BufferedReader bufferedReader = null;
		String fileName = null;
		String sLineIn = null;
		ClassLoader classLoader = null;		
		File file = null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( props != null )
		{
		    // Quotes File Name			
			if ( quoteInputFile.equals(QuoteInputFile.INTERNAL ))
			{			
				fileName = props.getProperty(BaseConstants.QUOTES_INT_FILE_KEY);
			}
			else if ( quoteInputFile.equals(QuoteInputFile.EXTERNAL ))
			{
				fileName = props.getProperty(BaseConstants.QUOTES_EXT_FILE_KEY);				
			}
			
		    if ( fileName != null )
		    {
				logger.info("Attempting To Read: " + fileName);
		
				try
				{				

					if ( quoteInputFile.equals(QuoteInputFile.INTERNAL ))
					{					
						// Internal file get from resources folder with classloader.
						classLoader 	= QuotesAdapter.class.getClassLoader();
						file 			= new File(classLoader.getResource(fileName).getFile());					
						fileReader 		= new FileReader(file);					
					}
					else if ( quoteInputFile.equals(QuoteInputFile.EXTERNAL ))
					{
						// External file, just read it.
						fileReader 		= new FileReader(fileName);						
					}					
					
					bufferedReader 	= new BufferedReader(fileReader);
			
					// Read 1st Line & Trim It.
					sLineIn 	= bufferedReader.readLine();
					sLineIn 	= sLineIn.trim();
			
					if ( sLineIn != null )
					{					
						// Convert to int
						returnValue = Integer.parseInt(sLineIn);
						logger.info("Max Quotes Available: " + sLineIn);
					}
					
					bufferedReader.close();
					bufferedReader = null;
					
					fileReader.close();
					fileReader = null;
				
				}
				catch ( FileNotFoundException fnfe )
				{
				    logger.error(BaseConstants.ERROR_QFILE_MIA + fileName);
				    logger.error(fnfe.getMessage());
				}
				catch ( IOException ioex )
				{
				    logger.error(BaseConstants.ERROR_QFILE_MIA + fileName);					
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
				logger.error("*** ERROR *** Property for key: "
					+ BaseConstants.QUOTES_EXT_FILE_KEY
					+ " is NULL or DOES NOT EXIST in Property File: "
					+ BaseConstants.QUOTES_PROPS + " !!");
		    }
		
			
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return( returnValue );		
    }
   
    private static String getAppVersion(Properties props)
    {
		Log logger = methIDgetAppVersion;

		logger.debug(BaseConstants.BEGINS);
    	
		String returnValue = null;

		if ( props != null )
		{		
			returnValue = props.getProperty(BaseConstants.QUOTES_APP_VERSION_KEY);
			
			if ( returnValue != null )
			{
				logger.info("QuotesApp Version: " + returnValue);				
			}
			
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return( returnValue );		
    }
    
    private static String getJDKVersion()
    {
    	Log logger = methIDgetJDKVersion;
    	String returnValue = null;
    	
		logger.debug(BaseConstants.BEGINS);    	
    	
		returnValue  = System.getProperty(BaseConstants.JAVA_VERSION);       	
       	logger.debug("JDK Detected: " + returnValue);

		logger.debug(BaseConstants.ENDS);
       	
       	return( returnValue );
    }

}
