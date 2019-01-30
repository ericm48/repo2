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
import com.eric.domain.common.enumeration.QuoteInputFileType;
import com.eric.domain.constant.BaseConstants;
import com.eric.domain.constant.ErrorMessageConstants;

public class FileUtil 
{
	
    private static final Log methIDinitFileSet;
    private static final Log methIDgetPropFileKeyType;
    private static final Log methIDgetPropFileName;
    private static final Log methIDgetMaxQuotes;
    private static final Log methIDgetMaxQuotesFromFile;
    private static final Log methIDgetBufferedReaderToQuotesFile;
    
    static
    {
    	methIDinitFileSet 					= LogFactory.getLog(FileUtil.class.getName() + ".initFileSet()");  
    	methIDgetPropFileKeyType 			= LogFactory.getLog(FileUtil.class.getName() + ".getPropFileKeyType()");
    	methIDgetPropFileName				= LogFactory.getLog(FileUtil.class.getName() + ".getPropFileName()");
    	methIDgetMaxQuotes					= LogFactory.getLog(FileUtil.class.getName() + ".getMaxQuotes()");     	
    	methIDgetMaxQuotesFromFile			= LogFactory.getLog(FileUtil.class.getName() + ".getMaxQuotesFromFile()");    	
    	methIDgetBufferedReaderToQuotesFile	= LogFactory.getLog(FileUtil.class.getName() + ".getBufferedReaderToQuotesFile()");    	
    	
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
    	String targetKeyValue = null;
    	
		logger.debug(BaseConstants.BEGINS);

		while ( keepOnTruckin )
		{
			targetKey = AppPropFileKey.EXTERNAL.toString();
			
			logger.debug("FIRST Attempting To Retrieve -D Key (EXTERNAL): " + targetKey);
			
			targetKeyValue = System.getProperty(targetKey);
			
			if ( StringUtils.isNotEmpty( targetKeyValue ))
			{
				logger.debug(targetKey + " Key Received: " + targetKeyValue);				
				returnValue = AppPropFileKey.EXTERNAL;				
				break;
			}
			
			targetKey = AppPropFileKey.INTERNAL.toString();
			
			logger.debug("SECOND Attempting To Retrieve -D Key (INTERNAL): " + targetKey);
			
			targetKeyValue = System.getProperty(targetKey);

			if ( StringUtils.isNotEmpty( targetKeyValue ))
			{
				logger.debug(targetKey + " Key Received: " + targetKeyValue);				
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
    
    public static int getMaxQuotes(Properties props)
    {
		Log logger = methIDgetMaxQuotes;
		
		int returnValue = 0;
		
		logger.debug(BaseConstants.BEGINS);
		
		returnValue = getMaxQuotesFromFile(props, QuoteInputFileType.EXTERNAL);
		
		if ( returnValue <= 0 )
		{
			returnValue = getMaxQuotesFromFile(props, QuoteInputFileType.INTERNAL);			
		}		
		
		logger.debug(BaseConstants.ENDS);
		
		return( returnValue );    	
    }      

    public static int getMaxQuotesFromFile(Properties props, QuoteInputFileType quoteInputFileType)
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
			
			if ( quoteInputFileType.equals(QuoteInputFileType.NOT_SET) )
			{
				logger.error(ErrorMessageConstants.QUOTES_FILE_NAME_IS_NULL);				
				keepOnTruckin = false;
				break;				
			}
			
			fileReader = FileUtil.getFileReaderToQuotesFile(props, quoteInputFileType);
			
			if ( fileReader == null )
			{
				logger.error(ErrorMessageConstants.FILE_READER_IS_NULL);				
				keepOnTruckin = false;
				break;
			}
				
			bufferedReader = new BufferedReader(fileReader);

//			if ( bufferedReader == null )
//			{
//				logger.error(ErrorMessageConstants.BUFFERED_READER_IS_NULL);				
//				keepOnTruckin = false;
//				break;
//			}
			
			logger.info("Attempting To Read: " + quoteInputFileType);
		
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

				// TODO: FIX ME!!!!
				fileReader.close();
				fileReader = null;
			
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
    public static FileReader getFileReaderToQuotesFile(Properties props, QuoteInputFileType quoteInputFileType)
    
    {
		Log logger = methIDgetBufferedReaderToQuotesFile;
    	
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
			if ( quoteInputFileType.equals(QuoteInputFileType.INTERNAL ))
			{			
				fileName = props.getProperty(BaseConstants.QUOTES_INT_FILE_KEY);
			}
			else if ( quoteInputFileType.equals(QuoteInputFileType.EXTERNAL ))
			{
				fileName = props.getProperty(BaseConstants.QUOTES_EXT_FILE_KEY);				
			}
			
		    if ( fileName != null )
		    {
		    	
				logger.info("Attempting To Read: " + fileName);
		
				try
				{				

					if ( quoteInputFileType.equals(QuoteInputFileType.INTERNAL ))
					{					
						// Internal file get from resources folder with classloader.
						classLoader 	= QuotesAdapter.class.getClassLoader();
						file 			= new File(classLoader.getResource(fileName).getFile());					
						fileReader 		= new FileReader(file);					
					}
					else if ( quoteInputFileType.equals(QuoteInputFileType.EXTERNAL ))
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
					+ BaseConstants.QUOTES_EXT_FILE_KEY
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

}
