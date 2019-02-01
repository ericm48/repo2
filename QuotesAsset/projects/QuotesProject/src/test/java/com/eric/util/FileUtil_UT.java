package com.eric.util;

import java.util.Properties;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eric.domain.common.enumeration.AppPropFileKey;
import com.eric.domain.constant.BaseConstants;
import com.eric.domain.constant.BaseTestConstants;
import com.eric.test.util.QuotesTestUtil;

public class FileUtil_UT
{
	 
	private static Log methIDrunTestGetPropFileNameExternalKeyPresentSUCCESS;
	private static Log methIDrunTestGetPropFileNameInternalKeyPresentSUCCESS;
	private static Log methIDrunTestGetPropFileNameNoKeyPresentFAIL;
	private static Log methIDrunTestGetTargetQuotesFileNULLPropertiesFAIL;
	private static Log methIDrunTestGetTargetQuotesFileEMPTYPropertiesFAIL;	
	private static Log methIDrunTestGetTargetQuotesFileExternalSUCCESS;
	 
	static
	{
		
		methIDrunTestGetPropFileNameExternalKeyPresentSUCCESS = LogFactory
				  	.getLog(FileUtil_UT.class.getName()
						+ ".runTestGetPropFileNameExternalKeyPresentSUCCESS()");

		methIDrunTestGetPropFileNameInternalKeyPresentSUCCESS = LogFactory
				  	.getLog(FileUtil_UT.class.getName()
						+ ".runTestGetPropFileNameInternalKeyPresentSUCCESS()");

		methIDrunTestGetPropFileNameNoKeyPresentFAIL = LogFactory
				  	.getLog(FileUtil_UT.class.getName()
						+ ".runTestGetPropFileNameNoKeyPresentSUCCESS()");
		
		methIDrunTestGetTargetQuotesFileNULLPropertiesFAIL = LogFactory
			  		.getLog(FileUtil_UT.class.getName()
			  				+ ".runTestGetTargetQuotesFileNULLPropertiesFAIL()");
		
		methIDrunTestGetTargetQuotesFileEMPTYPropertiesFAIL = LogFactory
		  			.getLog(FileUtil_UT.class.getName()
		  					+ ".runTestGetTargetQuotesFileEMPTYPropertiesFAIL()");
		
		methIDrunTestGetTargetQuotesFileExternalSUCCESS = LogFactory
	  				.getLog(FileUtil_UT.class.getName()
	  						+ ".runTestGetTargetQuotesFileExternalSUCCESS()");		 
	}
	 
	@Before
	public void setUp()
	{
		return;
	}
	
	@Test
	public void runTestGetPropFileNameExternalKeyPresentSUCCESS()
	{
		Log logger = methIDrunTestGetPropFileNameExternalKeyPresentSUCCESS;

		// Here's the important Part!!
		String targetKey = AppPropFileKey.EXTERNAL.toString();
		String targetPropFileName = BaseTestConstants.QUOTES_BAD_INTERNAL_AND_EXTERNAL_QUOTES_FILENAME;
		String returnValue = null;
		
		logger.debug(BaseTestConstants.BEGINS);

		Assert.assertNull(returnValue);
		
		System.setProperty(targetKey, targetPropFileName);

		returnValue = FileUtil.getPropFileName();

		Assert.assertNotNull(returnValue);
		
		System.clearProperty(targetKey);		  
		
		Assert.assertTrue(StringUtils.equals(returnValue, targetPropFileName));
		
		logger.debug(BaseTestConstants.ENDS);

		return;		
	}	 

	@Test
	public void runTestGetPropFileNameInternalKeyPresentSUCCESS()
	{
		Log logger = methIDrunTestGetPropFileNameInternalKeyPresentSUCCESS;

		// Here's the important Part!!
		String targetKey = AppPropFileKey.INTERNAL.toString();
		String targetPropFileName = BaseConstants.QUOTES_PROPS;		
		String returnValue = null;
		
		logger.debug(BaseTestConstants.BEGINS);
		
		Assert.assertNull(returnValue);
		
		System.setProperty(targetKey, targetPropFileName);

		returnValue = FileUtil.getPropFileName();

		Assert.assertNotNull(returnValue);
		
		System.clearProperty(targetKey);		  
		
		Assert.assertTrue(StringUtils.equals(returnValue, targetPropFileName));
		
		logger.debug(BaseTestConstants.ENDS);

		return;
	}	 
	 
	@Test
	public void runTestGetPropFileNameNoKeyPresentFAIL()
	{
		Log logger = methIDrunTestGetPropFileNameNoKeyPresentFAIL;
	
		// Here's the important Part!!
		String targetKey = null;
		String targetPropFileName = BaseConstants.QUOTES_PROPS;		
		String returnValue = null;
		
		logger.debug(BaseTestConstants.BEGINS);
		
		Assert.assertNull(returnValue);
		
		returnValue = FileUtil.getPropFileName();

		Assert.assertNotNull(returnValue);
		
		Assert.assertTrue(StringUtils.equals(returnValue, targetPropFileName));		
		
		logger.debug(BaseTestConstants.ENDS);

		return;	
	}	 
	
	@Test
	public void runTestGetTargetQuotesFileNULLPropertiesFAIL()
	{
		Log logger = methIDrunTestGetTargetQuotesFileNULLPropertiesFAIL;
	
		// Here's the important Part!!
		Properties props = null;
		String returnValue = null;
		
		logger.debug(BaseTestConstants.BEGINS);
		
		Assert.assertNull(props);
		
		returnValue = FileUtil.getTargetQuotesFileName(props);

		Assert.assertNull(returnValue);
		
		logger.debug(BaseTestConstants.ENDS);

		return;	
	}	 	
	
	@Test
	public void runTestGetTargetQuotesFileEMPTYPropertiesFAIL()
	{
		Log logger = methIDrunTestGetTargetQuotesFileEMPTYPropertiesFAIL;
	
		// Here's the important Part!!
		Properties props = new Properties();
		String returnValue = null;
		
		logger.debug(BaseTestConstants.BEGINS);
		
		Assert.assertNotNull(props);
		
		returnValue = FileUtil.getTargetQuotesFileName(props);

		Assert.assertNull(returnValue);
		
		logger.debug(BaseTestConstants.ENDS);

		return;	
	}	 	
	
	@Test
	public void runTestGetTargetQuotesFileInternalSUCCESS()
	{
		Log logger = methIDrunTestGetTargetQuotesFileEMPTYPropertiesFAIL;
	
		Properties props = null;
		String returnValue = null;
		
		logger.debug(BaseTestConstants.BEGINS);
		
		Assert.assertNull(props);
		
		// Here's the important Part!!		
		props = QuotesTestUtil.loadPropFile(BaseTestConstants.QUOTES_GOOD_INTERNAL_ONLY_QUOTES_FILENAME);
		
		returnValue = FileUtil.getTargetQuotesFileName(props);

		Assert.assertNotNull(returnValue);
		
		logger.debug(BaseTestConstants.ENDS);

		return;	
	}	 	
	
	@Test
	public void runTestGetTargetQuotesFileExternalSUCCESS()
	{
		Log logger = methIDrunTestGetTargetQuotesFileExternalSUCCESS;
	
		Properties props = null;
		String returnValue = null;
		
		logger.debug(BaseTestConstants.BEGINS);
		
		Assert.assertNull(props);
		
		// Here's the important Part!!		
		props = QuotesTestUtil.loadPropFile(BaseTestConstants.QUOTES_BAD_EXTERNAL_QUOTES_FILENAME);
		
		returnValue = FileUtil.getTargetQuotesFileName(props);

		Assert.assertNotNull(returnValue);
		
		logger.debug(BaseTestConstants.ENDS);

		return;	
	}	 	
	
	
//	@Test
//	public void runTestToDialogListenerInternalNoMaxQuotesFAIL()
//	{
//		Log logger = methIDrunTestToDialogListenerInternalNoMaxQuotesFAIL;
//		DialogListener ql = null;		  
//
//		// Here's the important Part!!
//		String targetKey = AppPropFileKey.INTERNAL.toString();
//		String targetPropFileName = BaseTestConstants.QUOTES_BAD_INTERNAL_AND_EXTERNAL_QUOTES_FILENAME;
//		  
//		logger.debug(BaseTestConstants.BEGINS);
//		  
//		System.setProperty(targetKey, targetPropFileName);
//		logger.info("Using PropertyFile: " + targetPropFileName);
//		  
//		Assert.assertNull(ql);
//		ql = QuotesAdapter.toDialogListener();
//	
//		System.clearProperty(targetKey);		  
//		  
//		Assert.assertNull(ql);
//		  
//		logger.debug(BaseTestConstants.ENDS);
//	
//		return;
//	}
//	 	 
//	@Test
//	public void runTestToDialogListenerBadInternalBadExternalQuotesFileFAIL()
//	{
//		Log logger = methIDrunTestToDialogListenerBadInternalBadExternalQuotesFileFAIL;
//		DialogListener ql = null;
//		  
//		// Here's the important Part!!
//		String targetKey = AppPropFileKey.EXTERNAL.toString();		  
//		String targetPropFileName = BaseTestConstants.QUOTES_BAD_INTERNAL_AND_EXTERNAL_QUOTES_FILENAME;
//		  
//		logger.debug(BaseTestConstants.BEGINS);
//
//		System.setProperty(targetKey, targetPropFileName);
//		logger.info("Using PropertyFile: " + targetPropFileName);
//		  
//		Assert.assertNull(ql);
//		ql = QuotesAdapter.toDialogListener();
//
//		System.clearProperty(targetKey);		  
//		  
//		Assert.assertNull(ql);
//		  
//		logger.debug(BaseTestConstants.ENDS);
//
//		return;
//	}
// 
//	@Test
//	public void runTestToDialogListenerInternalQuotesFileSUCCESS()
//	{
//		Log logger = methIDrunTestToDialogListenerInternalQuotesFileSUCCESS;
//		DialogListener ql = null;
//		int maxQuotes = 0;
//
//		// Here's the important Part!!		 
//		String targetKey = AppPropFileKey.INTERNAL.toString();
//		String targetPropFileName = BaseTestConstants.QUOTES_BAD_EXTERNAL_QUOTES_FILENAME;
//		  
//		logger.debug(BaseTestConstants.BEGINS);
//
//		System.setProperty(targetKey, targetPropFileName);
//		logger.info("Using PropertyFile: " + targetPropFileName);
//		  
//		Assert.assertNull(ql);
//		Assert.assertEquals(maxQuotes, 0);
//		  
//		ql = QuotesAdapter.toDialogListener();
//
//		System.clearProperty(targetKey);		  
//		
//		Assert.assertNotNull(ql);
//
//		maxQuotes = ql.getMaxQuotes();		  
//		Assert.assertEquals(maxQuotes, BaseTestConstants.MAX_QUOTES_INERNTAL);		  
//		  
//		logger.debug(BaseTestConstants.ENDS);
//
//		return;
//	}
//	 
//	@Test
//	public void runTestToDialogListenerExternalQuotesFileSUCCESS()
//	{
//		Log logger = methIDrunTestToDialogListenerExternalQuotesFileSUCCESS;
//		DialogListener ql = null;
//		int maxQuotes = 0;
//		  
//		// Here's the important Part!!	
//		String targetKey = AppPropFileKey.INTERNAL.toString();		  
//		String targetPropFileName = BaseTestConstants.QUOTES_BAD_INTERNAL_QUOTES_FILENAME;
//		  
//		logger.debug(BaseTestConstants.BEGINS);
//
//		System.setProperty(targetKey, targetPropFileName);
//		logger.info("Using PropertyFile: " + targetPropFileName);
//		
//		Assert.assertNull(ql);
//		Assert.assertEquals(maxQuotes, 0);
//		  
//		ql = QuotesAdapter.toDialogListener();
//
//		System.clearProperty(targetKey);		  
//		  
//		Assert.assertNotNull(ql);
//		maxQuotes = ql.getMaxQuotes();		  
//		Assert.assertTrue((maxQuotes > 0) && (maxQuotes > BaseTestConstants.MAX_QUOTES_INERNTAL));		  
//		  
//		logger.debug(BaseTestConstants.ENDS);
//
//		return;
//	}
	 
	@After
	public void cleanUp()
	{
		System.clearProperty(AppPropFileKey.INTERNAL.toString());		 
		System.clearProperty(AppPropFileKey.EXTERNAL.toString());	
		return;		 
	}

}
