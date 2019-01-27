package com.eric.adapter;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
	    		    	
	    	maxQuotes = FileUtil.getMaxQuotes(props);
	    	
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
