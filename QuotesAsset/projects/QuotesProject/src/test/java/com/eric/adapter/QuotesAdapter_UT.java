package com.eric.adapter;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eric.domain.common.enumeration.AppPropFileKey;
import com.eric.domain.constant.BaseTestConstants;
import com.eric.test.util.QuotesTestUtil;
import com.eric.ui.listener.DialogListener;

public class QuotesAdapter_UT
{
	 
	private static Log methIDrunTestGetPropFileNameExternalKeyPresentSUCCESS;
	private static Log methIDrunTestGetPropFileNameInternalKeyPresentSUCCESS;
	private static Log methIDrunTestGetPropFileNameNoKeyPresentSUCCESS;
	private static Log methIDrunTestToDialogListenerBadInternalBadExternalQuotesFileFAIL;
	private static Log methIDrunTestToDialogListenerInternalNoMaxQuotesFAIL;
	private static Log methIDrunTestToDialogListenerInternalQuotesFileSUCCESS;
	private static Log methIDrunTestToDialogListenerExternalQuotesFileSUCCESS;
	 
	static
	{
		
		methIDrunTestGetPropFileNameExternalKeyPresentSUCCESS = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runTestGetPropFileNameExternalKeyPresentSUCCESS()");

		methIDrunTestGetPropFileNameInternalKeyPresentSUCCESS = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runTestGetPropFileNameInternalKeyPresentSUCCESS()");

		methIDrunTestGetPropFileNameNoKeyPresentSUCCESS = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runTestGetPropFileNameNoKeyPresentSUCCESS()");
		
		 methIDrunTestToDialogListenerBadInternalBadExternalQuotesFileFAIL = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runTestToDialogListenerBadInternalBadExternalQuotesFileFAIL()");
		 
		 methIDrunTestToDialogListenerInternalNoMaxQuotesFAIL = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runTestToDialogListenerInternalNoMaxQuotesFAIL()");

		 methIDrunTestToDialogListenerInternalQuotesFileSUCCESS = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runTestToDialogListenerInternalQuotesFileSUCCESS()");

		 methIDrunTestToDialogListenerExternalQuotesFileSUCCESS = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runTestToDialogListenerExternalQuotesFileSUCCESS()");		 
		 
	}
	 
	@Before
	public void setUp()
	{
		return;
	}
	
//	@Test
//	public void runTestGetPropFileNameExternalKeyPresentSUCCESS()
//	{
//		Log logger = methIDrunTestGetPropFileNameExternalKeyPresentSUCCESS;
//
//		// Here's the important Part!!
//		String targetKey = AppPropFileKey.EXTERNAL.toString();
//		String targetPropFileName = BaseTestConstants.QUOTES_BAD_INTERNAL_AND_EXTERNAL_QUOTES_FILENAME;
//		String returnValue = null;
//		
//		logger.debug(BaseTestConstants.BEGINS);
//
//		Assert.assertNull(returnValue);
//		
//		System.setProperty(targetKey, targetPropFileName);
//
//		returnValue = QuotesAdapter.getPropFileName();
//
//		Assert.assertNotNull(returnValue);
//		
//		System.clearProperty(targetKey);		  
//		
//		Assert.assertTrue(StringUtils.equals(returnValue, targetPropFileName));
//		
//		logger.debug(BaseTestConstants.ENDS);
//
//		return;		
//	}	 
//
//	@Test
//	public void runTestGetPropFileNameInternalKeyPresentSUCCESS()
//	{
//		Log logger = methIDrunTestGetPropFileNameInternalKeyPresentSUCCESS;
//
//		// Here's the important Part!!
//		String targetKey = AppPropFileKey.INTERNAL.toString();
//		String targetPropFileName = BaseConstants.QUOTES_PROPS;		
//		String returnValue = null;
//		
//		logger.debug(BaseTestConstants.BEGINS);
//		
//		Assert.assertNull(returnValue);
//		
//		System.setProperty(targetKey, targetPropFileName);
//
//		returnValue = QuotesAdapter.getPropFileName();
//
//		Assert.assertNotNull(returnValue);
//		
//		System.clearProperty(targetKey);		  
//		
//		Assert.assertTrue(StringUtils.equals(returnValue, targetPropFileName));
//		
//		logger.debug(BaseTestConstants.ENDS);
//
//		return;
//	}	 
//	 
//	@Test
//	public void runTestGetPropFileNameNoKeyPresentSUCCESS()
//	{
//		Log logger = methIDrunTestGetPropFileNameNoKeyPresentSUCCESS;
//	
//		// Here's the important Part!!
//		String targetKey = null;
//		String targetPropFileName = BaseConstants.QUOTES_PROPS;		
//		String returnValue = null;
//		
//		logger.debug(BaseTestConstants.BEGINS);
//		
//		Assert.assertNull(returnValue);
//		
//		returnValue = QuotesAdapter.getPropFileName();
//
//		Assert.assertNotNull(returnValue);
//		
//		Assert.assertTrue(StringUtils.equals(returnValue, targetPropFileName));		
//		
//		logger.debug(BaseTestConstants.ENDS);
//
//		return;	
//	}	 
	 
	@Test
	public void runTestToDialogListenerInternalNoMaxQuotesFAIL()
	{
		Log logger = methIDrunTestToDialogListenerInternalNoMaxQuotesFAIL;
		DialogListener ql = null;		  

		// Here's the important Part!!
		String targetKey = AppPropFileKey.INTERNAL.toString();
		String targetPropFileName = BaseTestConstants.QUOTES_BAD_INTERNAL_AND_EXTERNAL_QUOTES_FILENAME;
		  
		logger.debug(BaseTestConstants.BEGINS);
		  
		System.setProperty(targetKey, targetPropFileName);
		logger.info("Using PropertyFile: " + targetPropFileName);
		  
		Assert.assertNull(ql);
		ql = QuotesAdapter.toDialogListener();
	
		System.clearProperty(targetKey);		  
		  
		Assert.assertNull(ql);
		  
		logger.debug(BaseTestConstants.ENDS);
	
		return;
	}
	 	 
	@Test
	public void runTestToDialogListenerBadInternalBadExternalQuotesFileFAIL()
	{
		Log logger = methIDrunTestToDialogListenerBadInternalBadExternalQuotesFileFAIL;
		DialogListener ql = null;
		  
		// Here's the important Part!!
		String targetKey = AppPropFileKey.EXTERNAL.toString();		  
		String targetPropFileName = BaseTestConstants.QUOTES_BAD_INTERNAL_AND_EXTERNAL_QUOTES_FILENAME;
		  
		logger.debug(BaseTestConstants.BEGINS);

		System.setProperty(targetKey, targetPropFileName);
		logger.info("Using PropertyFile: " + targetPropFileName);
		  
		Assert.assertNull(ql);
		ql = QuotesAdapter.toDialogListener();

		System.clearProperty(targetKey);		  
		  
		Assert.assertNull(ql);
		  
		logger.debug(BaseTestConstants.ENDS);

		return;
	}
 
	@Test
	public void runTestToDialogListenerInternalQuotesFileSUCCESS()
	{
		Log logger = methIDrunTestToDialogListenerInternalQuotesFileSUCCESS;
		DialogListener dialogListener = null;
		int maxQuotes = 0;

		// Here's the important Part!!		 
		String targetKey = AppPropFileKey.INTERNAL.toString();
		String targetPropFileName = BaseTestConstants.QUOTES_BAD_EXTERNAL_QUOTES_FILENAME;
		  
		logger.debug(BaseTestConstants.BEGINS);

		System.setProperty(targetKey, targetPropFileName);
		logger.info("Using PropertyFile: " + targetPropFileName);
		  
		Assert.assertNull(dialogListener);
		Assert.assertEquals(maxQuotes, 0);
		  
		dialogListener = QuotesAdapter.toDialogListener();

		System.clearProperty(targetKey);		  
		
		Assert.assertNotNull(dialogListener);

		dialogListener.setQuote(QuotesTestUtil.toQuote(0));
		
		logger.debug("DialogListener: " + dialogListener.toString());
		
		maxQuotes = dialogListener.getMaxQuotes();		  
		Assert.assertEquals(maxQuotes, BaseTestConstants.MAX_QUOTES_INERNTAL);		  
		  
		logger.debug(BaseTestConstants.ENDS);

		return;
	}
	 
	@Test
	public void runTestToDialogListenerExternalQuotesFileSUCCESS()
	{
		Log logger = methIDrunTestToDialogListenerExternalQuotesFileSUCCESS;
		DialogListener dialogListener = null;
		int maxQuotes = 0;
		  
		// Here's the important Part!!	
		String targetKey = AppPropFileKey.INTERNAL.toString();		  
		String targetPropFileName = BaseTestConstants.QUOTES_BAD_INTERNAL_QUOTES_FILENAME;
		  
		logger.debug(BaseTestConstants.BEGINS);

		System.setProperty(targetKey, targetPropFileName);
		logger.info("Using PropertyFile: " + targetPropFileName);
		
		Assert.assertNull(dialogListener);
		Assert.assertEquals(maxQuotes, 0);
		  
		dialogListener = QuotesAdapter.toDialogListener();

		System.clearProperty(targetKey);		  
		  
		Assert.assertNotNull(dialogListener);
		maxQuotes = dialogListener.getMaxQuotes();		  
		Assert.assertTrue((maxQuotes > 0) && (maxQuotes >= BaseTestConstants.MAX_QUOTES_INERNTAL));		  
		  
		logger.debug(BaseTestConstants.ENDS);

		return;
	}
	
	@After
	public void cleanUp()
	{
		System.clearProperty(AppPropFileKey.INTERNAL.toString());		 
		System.clearProperty(AppPropFileKey.EXTERNAL.toString());	
		return;		 
	}

}
