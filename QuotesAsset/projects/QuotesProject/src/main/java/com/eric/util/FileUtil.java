/**
 * 
 * Description:  Utility Class for handling File IO.
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.adapter.QuotesAdapter;
import com.eric.domain.common.enumeration.AppPropFileKey;
import com.eric.domain.common.enumeration.PropKeyType;
import com.eric.domain.common.enumeration.QuotesInputFileType;
import com.eric.domain.constant.BaseConstants;
import com.eric.domain.constant.ErrorMessageConstants;
import com.eric.domain.message.ExMessages;

public class FileUtil 
{
	
    private static final Log methIDinitFileSet;
    private static final Log methIDgetPropFileKeyType;
    private static final Log methIDgetPropFileName_AppPropFileKey;
    private static final Log methIDgetPropFileName;
    private static final Log methIDgetTargetQuotesFileName;
    private static final Log methIDgetTargetQuotesFileType;    
    private static final Log methIDgetFileReaderToQuotesFile;
    private static final Log methIDcloseFile;
    
    static
    {
    	methIDinitFileSet 						= LogFactory.getLog(FileUtil.class.getName() + ".initFileSet()");  
    	methIDgetPropFileKeyType 				= LogFactory.getLog(FileUtil.class.getName() + ".getPropFileKeyType()");
    	methIDgetPropFileName_AppPropFileKey	= LogFactory.getLog(FileUtil.class.getName() + ".getPropFileKeyType(AppPropFileKey)");
    	methIDgetPropFileName					= LogFactory.getLog(FileUtil.class.getName() + ".getPropFileName()");
    	methIDgetTargetQuotesFileName			= LogFactory.getLog(FileUtil.class.getName() + ".getTargetQuotesFileName()");    	
    	methIDgetTargetQuotesFileType			= LogFactory.getLog(FileUtil.class.getName() + ".getTargetQuotesFileType()");    	
    	methIDgetFileReaderToQuotesFile			= LogFactory.getLog(FileUtil.class.getName() + ".getFileReaderToQuotesFile()");
		methIDcloseFile 						= LogFactory.getLog(FileUtil.class.getName()+ ".closeFile()");
    }
    
    
	@SuppressWarnings("unchecked")
	public static Properties initFileSet()
    {
		Log logger = methIDinitFileSet;
		String propFileName = null;
		Properties props = null;
		FileInputStream fis = null;
		Enumeration<String> propEnums = null;				
		String key = null;
		String keyType = null;
		String value = null;
		String lineOut = null;
		
		AppPropFileKey propFileKeyType = AppPropFileKey.NOT_SET;
		
		logger.debug(BaseConstants.BEGINS);
	
		if ( props == null )
		{
		    props = new Properties();
		}
	
		try
		{			
			propFileKeyType = getPropFileKeyType();
			
			propFileName = getPropFileName(propFileKeyType);
		
		    if ( propFileKeyType.equals(AppPropFileKey.EXTERNAL))
		    {
			    // Loads External		    	
			    logger.info("Attempting To Load EXTERNAL PropertyFile: " + propFileName);

			    fis = new FileInputStream(propFileName);
			    
			    props.load(fis);			    
			    
			    logger.info("EXTERNAL PropertyFile: " + propFileName + " loaded SUCCESSFULL!");

			    props.put(AppPropFileKey.EXTERNAL.toString(), propFileName);			    
		    }
		    
		    if ( propFileKeyType.equals(AppPropFileKey.INTERNAL))
		    {
			    // Loads Internal
		    	logger.info("Attempting To Load INTERNAL PropertyFile: " + propFileName);
		    	
			    props.load(QuotesAdapter.class.getResourceAsStream(propFileName));
			    
			    logger.info("INTERNAL PropertyFile: " + propFileName + " loaded SUCCESSFULL!");

			    props.put(AppPropFileKey.INTERNAL.toString(), propFileName);			    
		    }  		    
		
		    if ( logger.isDebugEnabled() )
		    {
		    	propEnums = (Enumeration<String>) props.propertyNames();
	
			    while (propEnums.hasMoreElements()) 
			    {			      
			    	key = propEnums.nextElement();		      
			    	value = props.getProperty(key);
			      
			    	keyType = BaseConstants.INT_PREFIX;
			    	
			    	if (key.contains(BaseConstants.EXT_PREFIX))
			    	{
			    		keyType = BaseConstants.EXT_PREFIX;
			    	}
			    	
			    	lineOut = String.format(BaseConstants.KEY_VALUE_OUT, keyType, key, value);
			    	
			    	
			    	logger.debug(lineOut);			    	
			    }
			    
		    }
		    
			    
		}
		catch ( FileNotFoundException fnfe )
		{
		    logger.error(ErrorMessageConstants.ERROR_QFILE_MIA + propFileName);
		    
			lineOut = null;			
			lineOut = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED, 
									FileNotFoundException.class.getName(), 
									fnfe.getMessage());

			logger.error(lineOut);
		}
		catch ( IOException ioex )
		{			
			lineOut = null;			
			lineOut = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED, 
									IOException.class.getName(), 
									ioex.getMessage());

			logger.error(lineOut);
		}
	
		catch ( Exception ex )
		{			
			lineOut = null;			
			lineOut = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED, 
									Exception.class.getName(), 
									ex.getMessage());

			logger.error(lineOut);	
		}
	
		logger.debug(BaseConstants.ENDS);
	
		return(props);
    }
	
	
	// Determines if the property file to use is internal, or external (set via -d variable).
    public static AppPropFileKey getPropFileKeyType()
    { 
		Log logger = methIDgetPropFileKeyType;
    	
    	boolean keepOnTruckin = true;
    	AppPropFileKey returnValue = AppPropFileKey.NOT_SET;
    	String targetKey = null;
    	String targetValue = null;
    	String lineOut = null;
    	
		logger.debug(BaseConstants.BEGINS);

		while ( keepOnTruckin )
		{
			targetKey = AppPropFileKey.EXTERNAL.toString();
			
			logger.debug("FIRST Attempting To Retrieve -D External-Key for an EXTERNAL reference: " + targetKey);
			
			targetValue = System.getProperty(targetKey);
			
			if ( StringUtils.isNotEmpty( targetValue ))
			{
				lineOut = String.format(BaseConstants.KEY_VALUE_OUT, BaseConstants.EXTERNAL, targetKey, targetValue);				
				logger.debug(lineOut);				
				returnValue = AppPropFileKey.EXTERNAL;
				break;
			}
			
			targetKey = AppPropFileKey.INTERNAL.toString();
			
			logger.debug("SECOND Attempting To Retrieve -D External-Key for an INTERNAL reference: " + targetKey);
			
			targetValue = System.getProperty(targetKey);

			if ( StringUtils.isNotEmpty( targetValue ))
			{
				lineOut = String.format(BaseConstants.KEY_VALUE_OUT,  BaseConstants.INTERNAL, targetKey, targetValue);				
				logger.debug(lineOut);				
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
    
    public static String getPropFileName(AppPropFileKey targetKeyType)
 	{
 		Log logger = methIDgetPropFileName_AppPropFileKey;
 	
 		String returnValue = null;
 		String targetKey = null;
	
 		logger.debug(BaseConstants.BEGINS);
 		
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
  		Log logger 									= methIDgetTargetQuotesFileType;  	
  		String keyValue 							= null;  				
  		QuotesInputFileType quotesInputFileType 	= QuotesInputFileType.NOT_SET;
  		String errorMsg								= null;
  		
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
    
    public static FileReader getFileReaderToQuotesFile(Properties props, QuotesInputFileType quotesInputFileType)    
    {
		Log logger = methIDgetFileReaderToQuotesFile;
    	
	    FileReader fileReader = null;
		String fileName = null;
		ClassLoader classLoader = null;		
		File file = null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( props != null )
		{
		    // Quotes File Name			
			fileName = FileUtil.getTargetQuotesFileName(props);
		
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
				    logger.error(ErrorMessageConstants.ERROR_QFILE_MIA + fileName);					
			
					String lineOut = null;

				    logger.error(ErrorMessageConstants.ERROR_QFILE_MIA + fileName);
					
					lineOut = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED, 
											IOException.class.getName(), 
											ioex.getMessage());
				    
				}
			
				catch ( Exception ex )
				{
					String lineOut = null;
					
					lineOut = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED, 
											Exception.class.getName(), 
											ex.getMessage());

					logger.error(lineOut);			
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
			String lineOut = null;
			
			lineOut = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED, 
									IOException.class.getName(), 
									ioex.getMessage());

			logger.error(lineOut);	
		}

		logger.debug(BaseConstants.ENDS);
		
		return(returnValue);

    }    
}
