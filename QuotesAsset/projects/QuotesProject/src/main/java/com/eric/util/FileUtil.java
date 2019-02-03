package com.eric.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.adapter.QuotesAdapter;
import com.eric.domain.common.enumeration.AppPropFileKey;
import com.eric.domain.common.enumeration.QuotesInputFileType;
import com.eric.domain.constant.BaseConstants;
import com.eric.domain.constant.ErrorMessageConstants;
import com.eric.factory.QuoteFactory;

public class FileUtil 
{
	
    private static final Log methIDinitFileSet;
    private static final Log methIDgetPropFileKeyType;
    private static final Log methIDgetPropFileName;
    private static final Log methIDgetTargetQuotesFileName;
    private static final Log methIDgetTargetQuotesFileType;    
    private static final Log methIDgetMaxQuotes;
    private static final Log methIDgetMaxQuotesFromFile;
    private static final Log methIDgetFileReaderToQuotesFile;
    private static final Log methIDcloseFile;
    
    static
    {
    	methIDinitFileSet 					= LogFactory.getLog(FileUtil.class.getName() + ".initFileSet()");  
    	methIDgetPropFileKeyType 			= LogFactory.getLog(FileUtil.class.getName() + ".getPropFileKeyType()");
    	methIDgetPropFileName				= LogFactory.getLog(FileUtil.class.getName() + ".getPropFileName()");
    	methIDgetTargetQuotesFileName		= LogFactory.getLog(FileUtil.class.getName() + ".getTargetQuotesFileName()");    	
    	methIDgetTargetQuotesFileType		= LogFactory.getLog(FileUtil.class.getName() + ".getTargetQuotesFileType()");    	
    	methIDgetMaxQuotes					= LogFactory.getLog(FileUtil.class.getName() + ".getMaxQuotes()");     	
    	methIDgetMaxQuotesFromFile			= LogFactory.getLog(FileUtil.class.getName() + ".getMaxQuotesFromFile()");    	
    	methIDgetFileReaderToQuotesFile		= LogFactory.getLog(FileUtil.class.getName() + ".getFileReaderToQuotesFile()");
		methIDcloseFile 					= LogFactory.getLog(FileUtil.class.getName()+ ".closeFile()");	    	
    	
    }
    
    
	public static Properties initFileSet()
    {
		Log logger = methIDinitFileSet;
		String propFileName = null;
		Properties props = null;
		FileInputStream fis = null;
		
		AppPropFileKey propFileKeyType = AppPropFileKey.NOT_SET;
		
		logger.debug(BaseConstants.BEGINS);
	
		if ( props == null )
		{
		    props = new Properties();
		}
	
		try
		{			
			propFileKeyType = getPropFileKeyType();
			
			propFileName = getPropFileName();
		
		    if ( propFileKeyType.equals(AppPropFileKey.EXTERNAL))
		    {
			    // Loads External		    	
			    logger.info("Attempting To Load EXTERNAL PropertyFile: " + propFileName);

			    fis = new FileInputStream(propFileName);
			    
			    props.load(fis);			    
		    }
		    
		    if ( propFileKeyType.equals(AppPropFileKey.INTERNAL))
		    {
			    // Loads Internal
		    	logger.info("Attempting To Load INTERNAL PropertyFile: " + propFileName);
		    	
			    props.load(QuotesAdapter.class
				    .getResourceAsStream(propFileName));	    
		    }  		    
			    
		}
		catch ( FileNotFoundException fnfe )
		{
		    logger.error(ErrorMessageConstants.ERROR_QFILE_MIA + propFileName);
		    logger.error("*** ERROR Exception Encountered!! Message: " + fnfe.getMessage());		    
		}
		catch ( IOException ioex )
		{
		    logger.error("*** ERROR Exception Encountered!! Message: " + ioex.getMessage());
		}
	
		catch ( Exception ex )
		{
		    logger.error("*** ERROR Exception Encountered!! Message: " + ex.getMessage());
		}
	
		logger.debug(BaseConstants.ENDS);
	
		return(props);
    }

	
	// TODO: Write a method that will get the QuotesFileName to use...(again internal or external).
	
	// Determines if the property file to use is internal, or external (set via -d variable).
	
    public static AppPropFileKey getPropFileKeyType()
    { 
		Log logger = methIDgetPropFileKeyType;
    	
    	boolean keepOnTruckin = true;
    	AppPropFileKey returnValue = AppPropFileKey.NOT_SET;
    	String targetKey = null;
    	String targetValue = null;
    	
		logger.debug(BaseConstants.BEGINS);

		while ( keepOnTruckin )
		{
			targetKey = AppPropFileKey.EXTERNAL.toString();
			
			logger.debug("FIRST Attempting To Retrieve -D Key (EXTERNAL): " + targetKey);
			
			targetValue = System.getProperty(targetKey);
			
			if ( StringUtils.isNotEmpty( targetValue ))
			{
				logger.debug("Value Received: " + targetValue + " For Key: " + targetKey);				
				returnValue = AppPropFileKey.EXTERNAL;				
				break;
			}
			
			targetKey = AppPropFileKey.INTERNAL.toString();
			
			logger.debug("SECOND Attempting To Retrieve -D Key (INTERNAL): " + targetKey);
			
			targetValue = System.getProperty(targetKey);

			if ( StringUtils.isNotEmpty( targetValue ))
			{
				logger.debug("Value Received: " + targetValue + " For Key: " + targetKey);
				
				returnValue = AppPropFileKey.INTERNAL;				
				break;
			}
			else
			{
				logger.debug("No PropertyFile Key Set, Defaulting To INTERNAL.");				
				returnValue = AppPropFileKey.INTERNAL;
			}
			
			// Safety Purposes
			keepOnTruckin = false;
			break;			
		}
    	
		logger.debug(BaseConstants.ENDS);
		
    	return( returnValue );    	
    }	
    
    public static String getPropFileName()
 	{
 		Log logger = methIDgetPropFileName;
 	
 		String returnValue = null;
 		String targetKey = null;
 		
 		AppPropFileKey targetKeyType = AppPropFileKey.NOT_SET;
 	
 		logger.debug(BaseConstants.BEGINS);
 		
 		targetKeyType = getPropFileKeyType();
 		
 		if (targetKeyType.equals(AppPropFileKey.EXTERNAL))
 		{
 			targetKey = BaseConstants.EXT_PROP_FILE;
 			
 			logger.debug("Attempting To Retrieve Property Key: " + targetKey);
 			
 			returnValue = System.getProperty(targetKey);
 			
 			logger.debug("Retrieved PropFile Name: " + returnValue);			
 		}
 		
 		if (targetKeyType.equals(AppPropFileKey.INTERNAL))
 		{
 			targetKey = BaseConstants.INT_PROP_FILE;
 			
 			logger.debug("Attempting To Retrieve INTERNAL Key: " + targetKey);
 			
 			returnValue = System.getProperty(targetKey);
 			
 			logger.debug("Retrieved PropFile Name: " + returnValue);			
 		}

 		if (StringUtils.isEmpty(returnValue))
 		{
 			returnValue = BaseConstants.QUOTES_PROPS;			
 			logger.debug("No PropertyFileKey Set, Defaulting To: " + returnValue);	
 		}
 		
 		logger.info("Using PropFile Name: " + returnValue);
 		
 		logger.debug(BaseConstants.ENDS);
 		
 		return( returnValue );		
 	}
    
    
    public static String getTargetQuotesFileName(Properties props)
  	{
  		Log logger 								= methIDgetTargetQuotesFileName;  	
  		String returnValue 						= null;
  		QuotesInputFileType quotesInputFileType = QuotesInputFileType.NOT_SET; 
  		
		logger.debug(BaseConstants.BEGINS);
		
		if ( props != null )
		{
			// First, search to see if we've 'marked' an input file (Internal or External) with the presence of
			// property:  
			
			quotesInputFileType = getTargetQuotesFileType(props);

			if (quotesInputFileType.equals(QuotesInputFileType.NOT_SET))
			{
				// Search for an EXT prop first, if found return it.				
				logger.debug("Attempting To Locate Key: " + BaseConstants.QUOTES_EXT_FILE_NAME_KEY);
				returnValue = props.getProperty(BaseConstants.QUOTES_EXT_FILE_NAME_KEY);	
				logger.debug ("Received Value: " + returnValue + " For Key: " + BaseConstants.QUOTES_EXT_FILE_NAME_KEY);
				
				// If EXT isn't present, then look for INT prop.
				if ( returnValue != null )
				{
					props.setProperty(BaseConstants.TARGET_QUOTE_INPUT_FILE_TYPE, QuotesInputFileType.EXTERNAL.toString());					
				}
				else
				{				
					logger.debug("Attempting To Locate Key: " + BaseConstants.QUOTES_INT_FILE_NAME_KEY);				
					returnValue = props.getProperty(BaseConstants.QUOTES_INT_FILE_NAME_KEY);	
					logger.debug ("Received Value: " + returnValue + " For Key: " + BaseConstants.QUOTES_INT_FILE_NAME_KEY);
					
					if ( returnValue != null )
					{
						props.setProperty(BaseConstants.TARGET_QUOTE_INPUT_FILE_TYPE, QuotesInputFileType.INTERNAL.toString());					
					}
					else
					{
						logger.error(ErrorMessageConstants.QUOTES_FILE_PROPS_ARE_MISSING);
					}
					
				}
				
			}
			else
			{
				if (quotesInputFileType.equals(QuotesInputFileType.INTERNAL))
				{
					logger.debug("Attempting To Locate Key: " + BaseConstants.QUOTES_INT_FILE_NAME_KEY);				
					returnValue = props.getProperty(BaseConstants.QUOTES_INT_FILE_NAME_KEY);	
					logger.debug ("Received Value: " + returnValue + " For Key: " + BaseConstants.QUOTES_INT_FILE_NAME_KEY);	
				}
				
				if (quotesInputFileType.equals(QuotesInputFileType.EXTERNAL))
				{
					logger.debug("Attempting To Locate Key: " + BaseConstants.QUOTES_EXT_FILE_NAME_KEY);
					returnValue = props.getProperty(BaseConstants.QUOTES_EXT_FILE_NAME_KEY);	
					logger.debug ("Received Value: " + returnValue + " For Key: " + BaseConstants.QUOTES_EXT_FILE_NAME_KEY);				
				}
				
			}
			
		}
		else
		{
			logger.error(ErrorMessageConstants.PROPS_ARE_NULL);				
		}		
  		
		logger.debug(BaseConstants.ENDS);  		
  		
  		return (returnValue );  		
  	}        

    public static QuotesInputFileType getTargetQuotesFileType(Properties props)
  	{
  		Log logger 								= methIDgetTargetQuotesFileType;  	
  		String keyValue 						= null;  				
  		QuotesInputFileType quotesInputFileType 	= QuotesInputFileType.NOT_SET;
  		String errorMsg							= null;
  		
		logger.debug(BaseConstants.BEGINS);
		
		if ( props != null )
		{
			// Search for the QuoteInputFileTypeKey			
			logger.debug("Attempting To Locate Key: " + BaseConstants.TARGET_QUOTE_INPUT_FILE_TYPE);
			keyValue = props.getProperty(BaseConstants.TARGET_QUOTE_INPUT_FILE_TYPE);	

			// If EXT isn't present look for INT prop.
			if ( keyValue != null )
			{
				
				logger.debug ("Received Value: " + keyValue + " For Key: " + BaseConstants.TARGET_QUOTE_INPUT_FILE_TYPE);
				
				if ( keyValue.equals(QuotesInputFileType.INTERNAL.toString()) )
				{
					quotesInputFileType = QuotesInputFileType.INTERNAL;
				}
				
				if ( keyValue.equals(QuotesInputFileType.EXTERNAL.toString()) )
				{
					quotesInputFileType = QuotesInputFileType.EXTERNAL;
				}
				
			}
			else
			{
				errorMsg = String.format(ErrorMessageConstants.MISSING_PROPERTY_KEY,BaseConstants.TARGET_QUOTE_INPUT_FILE_TYPE);
				logger.error(errorMsg);				
			}		
			
		}
		else
		{
			logger.error(ErrorMessageConstants.PROPS_ARE_NULL);				
		}		
  		
		logger.debug(BaseConstants.ENDS);  		
  		
  		return ( quotesInputFileType );  		
  	}    
    
    
    //TODO: Refactor this back into the adapter!
    public static int getMaxQuotes(Properties props)
    {
		Log logger = methIDgetMaxQuotes;
		
		QuotesInputFileType quotesInputFileType = QuotesInputFileType.NOT_SET;
		int returnValue = 0;
		
		logger.debug(BaseConstants.BEGINS);
		
		quotesInputFileType = getTargetQuotesFileType(props);

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

    //TODO: Refactor this back into the adapter!
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

//			if ( bufferedReader == null )
//			{
//				logger.error(ErrorMessageConstants.BUFFERED_READER_IS_NULL);				
//				keepOnTruckin = false;
//				break;
//			}
			
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

				closeFile(fileReader);
			
			}
			catch ( FileNotFoundException fnfe )
			{
			    logger.error(ErrorMessageConstants.ERROR_QFILE_MIA + fileName);
			    logger.error(fnfe.getMessage());
			}
			catch ( IOException ioex )
			{
			    logger.error(ErrorMessageConstants.ERROR_QFILE_MIA + fileName);					
			    logger.error(ioex.getMessage());
			}
		
			catch ( Exception ex )
			{
			    logger.error("*** ERROR Exception Encountered!! Message: "
				    + ex.getMessage());
			}
				
			// Safety Purposes
			keepOnTruckin = false;
			break;			
		
				
		}	// End-While
		
		logger.debug(BaseConstants.ENDS);
		
		return( returnValue );		
    }
    
    // TODO:  Change this to returning a file handle, let the caller open the Buffered Reader, that way
    // we can wire up a .close() method at file level for both Adapter & Factory to use...
    
    
   // public static BufferedReader getBufferedReaderToQuotesFile(Properties props, QuoteInputFile quoteInputFile)
    
    public static FileReader getFileReaderToQuotesFile(Properties props, QuotesInputFileType quotesInputFileType)    
    {
		Log logger = methIDgetFileReaderToQuotesFile;
    	
	    FileReader fileReader = null;

	    // Buffered Reader
	    //BufferedReader bufferedReader = null;
		String fileName = null;
		ClassLoader classLoader = null;		
		File file = null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( props != null )
		{
		    // Quotes File Name			
			fileName = FileUtil.getTargetQuotesFileName(props);
			
//			if ( quoteInputFileType.equals(QuoteInputFileType.INTERNAL ))
//			{			
//				fileName = props.getProperty(BaseConstants.QUOTES_INT_FILE_NAME_KEY);
//			}
//			else if ( quoteInputFileType.equals(QuoteInputFileType.EXTERNAL ))
//			{
//				fileName = props.getProperty(BaseConstants.QUOTES_EXT_FILE_NAME_KEY);				
//			}
			
		    if ( fileName != null )
		    {		    	
				logger.info("Attempting To Read File: " + fileName);
		
				try
				{				

					if ( quotesInputFileType.equals(QuotesInputFileType.INTERNAL ))
					{					
						// Internal file get from resources folder with classloader.
						classLoader 	= QuotesAdapter.class.getClassLoader();
						file 			= new File(classLoader.getResource(fileName).getFile());					
						fileReader 		= new FileReader(file);					
					}
					else if ( quotesInputFileType.equals(QuotesInputFileType.EXTERNAL ))
					{
						// External file, just read it.
						fileReader 		= new FileReader(fileName);						
					}										
					
					//bufferedReader 	= new BufferedReader(fileReader);
					
				}
				catch ( FileNotFoundException fnfe )
				{
				    logger.error(ErrorMessageConstants.ERROR_QFILE_MIA + fileName);
				    logger.error(fnfe.getMessage());
				}
				catch ( IOException ioex )
				{
				    logger.error(ErrorMessageConstants.ERROR_QFILE_MIA + fileName);					
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
					+ BaseConstants.QUOTES_EXT_FILE_NAME_KEY
					+ " is NULL or DOES NOT EXIST in Property File: "
					+ BaseConstants.QUOTES_PROPS + " !!");
		    }
		    
		    
		}
		else
		{
			logger.error(ErrorMessageConstants.PROPS_ARE_NULL);
		}

	    return( fileReader );
    }

    public static boolean closeFile(FileReader fileReader)
    {
		boolean returnValue = true;
		Log logger 			= methIDcloseFile;
		
		logger.debug(BaseConstants.BEGINS);
		
		try
		{
		    if ( fileReader != null )
		    {
		    	fileReader.close();
		    }
		    
		}
		catch ( IOException ioex )
		{
		    logger.error("*** ERROR Encountered.  Message: " + ioex.getMessage());
		}

		logger.debug(BaseConstants.ENDS);
		
		return(returnValue);

    }    
}
