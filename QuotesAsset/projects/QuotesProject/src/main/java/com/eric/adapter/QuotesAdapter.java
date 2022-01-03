/**
 * 
 * Description:  Adapter pattern to handle the property files
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.adapter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.domain.common.enumeration.QuotesInputFileType;
import com.eric.domain.constant.BaseConstants;
import com.eric.domain.constant.ErrorMessageConstants;
import com.eric.domain.message.ExMessages;
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
    private static final Log methIDgetHostName;    
    
    static
    {
    	methIDtoDialogListener 				= LogFactory.getLog(QuotesAdapter.class.getName() + ".toDialogListener()");
    	methIDgetAppVersion 				= LogFactory.getLog(QuotesAdapter.class.getName() + ".getAppVersion()");
    	methIDgetMaxQuotes					= LogFactory.getLog(QuotesAdapter.class.getName() + ".getMaxQuotes()");     	
    	methIDgetMaxQuotesFromFile			= LogFactory.getLog(QuotesAdapter.class.getName() + ".getMaxQuotesFromFile()");
    	methIDgetJDKVersion					= LogFactory.getLog(QuotesAdapter.class.getName() + ".getJDKVersion()");  	
    	methIDgetHostName					= LogFactory.getLog(QuotesAdapter.class.getName() + ".getHostName()");    	
    	
    }
    
    public static DialogListener toDialogListener()
    {
    	Log logger 						= methIDtoDialogListener;    	
    	DialogListener dialogListener 	= null; 
    	QuoteHolder quoteHolder 		= null;  	
    	Properties props 				= null;
    	String appVersion 				= null;
    	String JDKVersion 				= null;
    	String hostName					= null;
    	int maxQuotes 					= 0;
    	
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
	    	
	    	// TODO: Here Dude!!  Add quotesFileID, hostName, sleepInternval, loopMax
	    	
	    	hostName = getHostName();
	    	if ( hostName == null )
	    	{
	    		logger.error(ErrorMessageConstants.HOSTNAME_IS_NULL);	    		
	    		keepOnTruckin = false;
	    		break;	    			    		
	    	}
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	// Build the Object Structure
	    	dialogListener = new DialogListener();

	    	quoteHolder = new QuoteHolder();	    	
	    	quoteHolder.setProperties(props);
	    	quoteHolder.setQuotesAppVersion(appVersion);
	    	quoteHolder.setMaxQuotes(maxQuotes);
	    	quoteHolder.setCurrentJDK(JDKVersion);
	    	
	    	dialogListener.setQuoteHolder(quoteHolder);

	    	// Safety Purposes
			keepOnTruckin = false;
			break;
			
		}
    	
		logger.debug(BaseConstants.ENDS);

    	return( dialogListener );
    }
   
    public static int getMaxQuotes(Properties props)
	{
		Log logger = methIDgetMaxQuotes;
		
		QuotesInputFileType quotesInputFileType = QuotesInputFileType.NOT_SET;
		int returnValue = 0;
		
		logger.debug(BaseConstants.BEGINS);
		
		quotesInputFileType = FileUtil.getTargetQuotesFileType(props);
	
		// If its not set, then make 2 checks.  External first, then internal, and set the prop off of what we learn.
		if ( quotesInputFileType.equals(QuotesInputFileType.NOT_SET) )
		{
			returnValue = getMaxQuotesFromFile(props, QuotesInputFileType.EXTERNAL);
			
			// Make an entry to the props that indicates which quotes file type (INT or EXT)l, we're gonna use.		
			
			if ( returnValue <= 0 )
			{
				returnValue = getMaxQuotesFromFile(props, QuotesInputFileType.INTERNAL);
				
				if ( returnValue > 0)
				{
					props.setProperty(BaseConstants.TARGET_QUOTE_INPUT_FILE_TYPE, QuotesInputFileType.INTERNAL.toString());				
				}
			}
			else
			{
				props.setProperty(BaseConstants.TARGET_QUOTE_INPUT_FILE_TYPE, QuotesInputFileType.EXTERNAL.toString());
			}
			
		}
		else  
		{
			returnValue = getMaxQuotesFromFile(props, quotesInputFileType);			
		}
	
		logger.debug(BaseConstants.ENDS);
		
		return( returnValue );    	
	}

	public static int getMaxQuotesFromFile(Properties props, QuotesInputFileType quotesInputFileType)
	{
		Log logger = methIDgetMaxQuotesFromFile;
		
		int returnValue = 0;
	    FileReader fileReader = null;
	
	    // Buffered Reader
	    BufferedReader bufferedReader = null;
		String fileName = null;
		String lineIn = null;
		boolean keepOnTruckin = true;
				
		logger.debug(BaseConstants.BEGINS);
		
		while ( keepOnTruckin )
		{
	
			if ( props == null )
			{
				logger.error(ErrorMessageConstants.PROPS_ARE_NULL);				
				keepOnTruckin = false;
				break;				
			}
			
			if ( quotesInputFileType.equals(QuotesInputFileType.NOT_SET) )
			{
				logger.error(ErrorMessageConstants.QUOTES_INPUT_FILE_TYPE_NOT_SET);				
				keepOnTruckin = false;
				break;				
			}
			
			fileReader = FileUtil.getFileReaderToQuotesFile(props, quotesInputFileType);
			
			if ( fileReader == null )
			{
				logger.error(ErrorMessageConstants.FILE_READER_IS_NULL);				
				keepOnTruckin = false;
				// Now, if this fails (which it just did), we need to indicate the OPPOSITE (most likely, we tried an external file,
				// and it didn't exist, or there was a problem), SOOO set the quotesInputFileType prop to INTERNAL, so we stop
				// looking EXTERNAL!				
	
				if ( quotesInputFileType.equals(QuotesInputFileType.EXTERNAL))
				{				
					logger.warn("Setting QuotesInputFileType To: " + QuotesInputFileType.INTERNAL.toString());
					props.setProperty(BaseConstants.TARGET_QUOTE_INPUT_FILE_TYPE, QuotesInputFileType.INTERNAL.toString());				
				}
				
				break;
			}
				
			bufferedReader = new BufferedReader(fileReader);
	
			logger.info("Input File Type: " + quotesInputFileType);
		
			try
			{				
				
				// Read 1st Line & Trim It.
				lineIn 	= bufferedReader.readLine();
				lineIn 	= lineIn.trim();
		
				if ( lineIn != null )
				{					
					// Convert to int
					returnValue = Integer.parseInt(lineIn);
					logger.info("Max Quotes Available: " + lineIn);
				}
				
				bufferedReader.close();
				bufferedReader = null;
	
				FileUtil.closeFile(fileReader);
			
			}
			catch ( FileNotFoundException fnfe )
			{
				String lineOut = null;				
			    logger.error(ErrorMessageConstants.ERROR_QFILE_MIA + fileName);
				lineOut = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED, 
										FileNotFoundException.class.getName(), 
										fnfe.getMessage());
				
			    logger.error(lineOut);				
	
			}
			catch ( IOException ioex )
			{
				String lineOut = null;				
	
			    logger.error(ErrorMessageConstants.ERROR_QFILE_MIA + fileName);
			    
				lineOut = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED, 
										IOException.class.getName(), 
										ioex.getMessage());
	
				logger.error(lineOut);			
			}
		
			catch ( Exception ex )
			{
				String lineOut = null;				
				lineOut = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED, 
										Exception.class.getName(), 
										ex.getMessage());
	
				logger.error(lineOut);				
			}
				
			// Safety Purposes
			keepOnTruckin = false;
			break;			
		
				
		}	// End-While
		
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
    
    private static String getHostName()
    {
    	Log logger = methIDgetHostName;
    	InetAddress ipAddress = null;
    	String hostName = null;
    	String lineOut = null;
    	
		logger.debug(BaseConstants.BEGINS);    	
    	
	    try 
	    {
	    	ipAddress = InetAddress.getLocalHost();
	        hostName = ipAddress.getHostName();
	        
	        logger.debug("Detected IP address: " + ipAddress);
	        logger.debug(" Dectected Hostname: " + hostName);

	    } 
	    catch (UnknownHostException uke) 
	    {
			lineOut = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED, 
									UnknownHostException.class.getName(), 
									uke.getMessage());

			logger.error(lineOut);
	    }    
		catch ( Exception ex )
		{
			lineOut = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED, 
									Exception.class.getName(), 
									ex.getMessage());

			logger.error(lineOut);				
		}
		
		logger.debug(BaseConstants.ENDS);
       	
       	return( hostName );
    }

    
}
