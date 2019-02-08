package com.eric.test.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JProgressBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.domain.common.enumeration.QuotesInputFileType;
import com.eric.domain.constant.BaseConstants;
import com.eric.domain.constant.BaseTestConstants;
import com.eric.domain.constant.ErrorMessageConstants;
import com.eric.domain.quote.Quote;
import com.eric.domain.quote.QuoteHolder;
import com.eric.ui.component.factory.ComponentWrapperFactory;
import com.eric.ui.holder.DialogHolder;
import com.eric.ui.listener.DialogListener;

public class QuotesTestUtil 
{

	private static Log methIDloadPropFile;	 

	static
	{
		methIDloadPropFile = LogFactory.getLog(QuotesTestUtil.class.getName() + "loadPropFile()");
	}
	
	public static DialogHolder toDialogHolder()
	{
		DialogHolder   dialogHolder 		= null;
	    JProgressBar  progressBar 			= null;
		
		dialogHolder = new DialogHolder();
		progressBar = new JProgressBar();	
				
		dialogHolder.setQuoteListener(toDialogListener());
		
		dialogHolder.setProgressComponent(ComponentWrapperFactory.getInstance().create( progressBar ));
		
		return( dialogHolder );
	}	
	
	public static DialogListener toDialogListener()
	{
		DialogListener dialogListener 		= null;
		dialogListener 						= new DialogListener();
		dialogListener.setQuoteHolder(toQuoteHolder(loadPropFile(null)));
		
		return( dialogListener );
	}
	
	public static QuoteHolder toQuoteHolder(Properties props)
	{
		QuoteHolder quoteHolder				= null;
		String jdkVersion					= null;
		
		jdkVersion  = System.getProperty(BaseConstants.JAVA_VERSION);       	
		
		quoteHolder = new QuoteHolder();
		
		if ( props != null )
		{
			quoteHolder.setProperties(props);
		}
		
		quoteHolder.setQuote(toQuote( 0 ));
		quoteHolder.setCurrentJDK(jdkVersion);		
		quoteHolder.setMaxQuotes(BaseTestConstants.MAX_QUOTES_INERNTAL);
		quoteHolder.setQuotesAppVersion(BaseTestConstants.APP_TEST_VERSION);
		
		return( quoteHolder );		
	}
	
	private static Quote toQuote(int targetQuoteNumber)
	{
		Quote quote = null;
		
		quote = new Quote();		
		
		if ( targetQuoteNumber <= 0 )
		{
			quote.setQuoteNumber(BaseTestConstants.QUOTE1_INDEX);
			quote.setQuoteText(BaseTestConstants.FIX_QUOTE1);
		}
			
		return( quote );
	}	
	
	public static Properties loadPropFile(String targetFileName)
	{	
		Log logger = methIDloadPropFile;
	
		logger.debug(BaseTestConstants.BEGINS);		
		
		Properties props = null;
	
		props = new Properties();

		if ( targetFileName == null )
		{
			targetFileName = BaseTestConstants.QUOTES_GOOD_INTERNAL_ONLY_QUOTES_FILENAME;
			props.setProperty(BaseConstants.TARGET_QUOTE_INPUT_FILE_TYPE, QuotesInputFileType.INTERNAL.toString());			
		}

		logger.debug("Using Properties File: " + targetFileName);
		
		try
		{		
			props.load(QuotesTestUtil.class
				    .getResourceAsStream(targetFileName));	    
		}
		catch ( FileNotFoundException fnfe )
		{
		    logger.error(ErrorMessageConstants.ERROR_QFILE_MIA + targetFileName);
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
		
		logger.debug(BaseTestConstants.ENDS);
		
		return( props );
	}
	
	
}
