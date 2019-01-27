package com.eric.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.adapter.QuotesAdapter;
import com.eric.domain.common.enumeration.AppPropFileKey;
import com.eric.domain.constant.BaseConstants;

public class FileUtil 
{
	
    private static final Log methIDinitFileSet;
    private static final Log methIDgetPropFileKeyType;
    private static final Log methIDgetPropFileName;
    
    static
    {
    	methIDinitFileSet 					= LogFactory.getLog(FileUtil.class.getName() + ".initFileSet()");  
    	methIDgetPropFileKeyType 			= LogFactory.getLog(QuotesAdapter.class.getName() + ".getPropFileKeyType()");
    	methIDgetPropFileName				= LogFactory.getLog(QuotesAdapter.class.getName() + ".getPropFileName()");    	
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
		    logger.error(BaseConstants.ERROR_QFILE_MIA + propFileName);
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
    
}
