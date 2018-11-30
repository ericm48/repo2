package com.eric.adapter;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Test;

import com.eric.domain.common.enumeration.AppPropFileKey;
import com.eric.domain.constant.BaseConstants;
import com.eric.domain.constant.BaseTestConstants;
import com.eric.ui.listener.QuoteListener;

public class QuotesAdapter_UT
{
	 
	private static Log methIDrunGetPropFileNameExternalKeyPresentSUCCESS;
	private static Log methIDrunGetPropFileNameInternalKeyPresentSUCCESS;
	private static Log methIDrunGetPropFileNameNoKeyPresentSUCCESS;
	private static Log methIDrunToQuoteListenerBadInternalBadExternalQuotesFileFAIL;
	private static Log methIDrunToQuoteListenerInternalNoMaxQuotesFAIL;
	private static Log methIDrunToQuoteListenerInternalQuotesFileSUCCESS;
	private static Log methIDrunToQuoteListenerExternalQuotesFileSUCCESS;
	 
	static
	{
		
		methIDrunGetPropFileNameExternalKeyPresentSUCCESS = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runGetPropFileNameExternalKeyPresentSUCCESS()");

		methIDrunGetPropFileNameInternalKeyPresentSUCCESS = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runGetPropFileNameInternalKeyPresentSUCCESS()");

		methIDrunGetPropFileNameNoKeyPresentSUCCESS = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runGetPropFileNameNoKeyPresentSUCCESS()");
		
		 methIDrunToQuoteListenerBadInternalBadExternalQuotesFileFAIL = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runToQuoteListenerBadInternalBadExternalQuotesFileFAIL()");
		 
		 methIDrunToQuoteListenerInternalNoMaxQuotesFAIL = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runToQuoteListenerInternalNoMaxQuotesFAIL()");

		 methIDrunToQuoteListenerInternalQuotesFileSUCCESS = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runToQuoteListenerInternalQuotesFileSUCCESS()");

		 methIDrunToQuoteListenerExternalQuotesFileSUCCESS = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runToQuoteListenerExternalQuotesFileSUCCESS()");		 
		 
	}
	 
	@Test
	public void runGetPropFileNameExternalKeyPresentSUCCESS()
	{
		Log logger = methIDrunGetPropFileNameExternalKeyPresentSUCCESS;

		// Here's the important Part!!
		String targetKey = AppPropFileKey.EXTERNAL.toString();
		String targetPropFileName = BaseTestConstants.QUOTES_BAD_INTERNAL_AND_EXTERNAL_QUOTES_FILENAME;
		String returnValue = null;
		
		logger.debug(BaseTestConstants.BEGINS);

		Assert.assertNull(returnValue);
		
		System.setProperty(targetKey, targetPropFileName);

		returnValue = QuotesAdapter.getPropFileName();

		Assert.assertNotNull(returnValue);
		
		System.clearProperty(targetKey);		  
		
		Assert.assertTrue(StringUtils.equals(returnValue, targetPropFileName));
		
		logger.debug(BaseTestConstants.ENDS);

		return;		
	}	 

	@Test
	public void runGetPropFileNameInternalKeyPresentSUCCESS()
	{
		Log logger = methIDrunGetPropFileNameInternalKeyPresentSUCCESS;

		// Here's the important Part!!
		String targetKey = AppPropFileKey.INTERNAL.toString();
		String targetPropFileName = BaseConstants.QUOTES_PROPS;		
		String returnValue = null;
		
		logger.debug(BaseTestConstants.BEGINS);
		
		Assert.assertNull(returnValue);
		
		System.setProperty(targetKey, targetPropFileName);

		returnValue = QuotesAdapter.getPropFileName();

		Assert.assertNotNull(returnValue);
		
		System.clearProperty(targetKey);		  
		
		Assert.assertTrue(StringUtils.equals(returnValue, targetPropFileName));
		
		logger.debug(BaseTestConstants.ENDS);

		return;
	}	 
	 
	@Test
	public void runGetPropFileNameNoKeyPresentSUCCESS()
	{
		Log logger = methIDrunGetPropFileNameNoKeyPresentSUCCESS;
	
		// Here's the important Part!!
		String targetKey = null;
		String targetPropFileName = BaseConstants.QUOTES_PROPS;		
		String returnValue = null;
		
		logger.debug(BaseTestConstants.BEGINS);
		
		Assert.assertNull(returnValue);
		
		returnValue = QuotesAdapter.getPropFileName();

		Assert.assertNotNull(returnValue);
		
		Assert.assertTrue(StringUtils.equals(returnValue, targetPropFileName));		
		
		logger.debug(BaseTestConstants.ENDS);

		return;	
	}	 
	 
	@Test
	public void runToQuoteListenerInternalNoMaxQuotesFAIL()
	{
		Log logger = methIDrunToQuoteListenerInternalNoMaxQuotesFAIL;
		QuoteListener ql = null;		  

		// Here's the important Part!!
		String targetKey = AppPropFileKey.INTERNAL.toString();
		String targetPropFileName = BaseTestConstants.QUOTES_BAD_INTERNAL_AND_EXTERNAL_QUOTES_FILENAME;
		  
		logger.debug(BaseTestConstants.BEGINS);
		  
		System.setProperty(targetKey, targetPropFileName);
		logger.info("Using PropertyFile: " + targetPropFileName);
		  
		Assert.assertNull(ql);
		ql = QuotesAdapter.toQuoteListener();
	
		System.clearProperty(targetKey);		  
		  
		Assert.assertNull(ql);
		  
		logger.debug(BaseTestConstants.ENDS);
	
		return;
	}
	 	 
	@Test
	public void runToQuoteListenerBadInternalBadExternalQuotesFileFAIL()
	{
		Log logger = methIDrunToQuoteListenerBadInternalBadExternalQuotesFileFAIL;
		QuoteListener ql = null;
		  
		// Here's the important Part!!
		String targetKey = AppPropFileKey.EXTERNAL.toString();		  
		String targetPropFileName = BaseTestConstants.QUOTES_BAD_INTERNAL_AND_EXTERNAL_QUOTES_FILENAME;
		  
		logger.debug(BaseTestConstants.BEGINS);

		System.setProperty(targetKey, targetPropFileName);
		logger.info("Using PropertyFile: " + targetPropFileName);
		  
		Assert.assertNull(ql);
		ql = QuotesAdapter.toQuoteListener();

		System.clearProperty(targetKey);		  
		  
		Assert.assertNull(ql);
		  
		logger.debug(BaseTestConstants.ENDS);

		return;
	}
 
	@Test
	public void runToQuoteListenerInternalQuotesFileSUCCESS()
	{
		Log logger = methIDrunToQuoteListenerInternalQuotesFileSUCCESS;
		QuoteListener ql = null;
		int maxQuotes = 0;

		// Here's the important Part!!		 
		String targetKey = AppPropFileKey.INTERNAL.toString();
		String targetPropFileName = BaseTestConstants.QUOTES_BAD_EXTERNAL_QUOTES_FILENAME;
		  
		logger.debug(BaseTestConstants.BEGINS);

		System.setProperty(targetKey, targetPropFileName);
		logger.info("Using PropertyFile: " + targetPropFileName);
		  
		Assert.assertNull(ql);
		Assert.assertEquals(maxQuotes, 0);
		  
		ql = QuotesAdapter.toQuoteListener();

		System.clearProperty(targetKey);		  
		
		Assert.assertNotNull(ql);

		maxQuotes = ql.getMaxQuotes();		  
		Assert.assertEquals(maxQuotes, BaseTestConstants.MAX_QUOTES_INERNTAL);		  
		  
		logger.debug(BaseTestConstants.ENDS);

		return;
	}
	 
	@Test
	public void runToQuoteListenerExternalQuotesFileSUCCESS()
	{
		Log logger = methIDrunToQuoteListenerExternalQuotesFileSUCCESS;
		QuoteListener ql = null;
		int maxQuotes = 0;
		  
		// Here's the important Part!!	
		String targetKey = AppPropFileKey.INTERNAL.toString();		  
		String targetPropFileName = BaseTestConstants.QUOTES_BAD_INTERNAL_QUOTES_FILENAME;
		  
		logger.debug(BaseTestConstants.BEGINS);

		System.setProperty(targetKey, targetPropFileName);
		logger.info("Using PropertyFile: " + targetPropFileName);
		
		Assert.assertNull(ql);
		Assert.assertEquals(maxQuotes, 0);
		  
		ql = QuotesAdapter.toQuoteListener();

		System.clearProperty(targetKey);		  
		  
		Assert.assertNotNull(ql);
		maxQuotes = ql.getMaxQuotes();		  
		Assert.assertTrue((maxQuotes > 0) && (maxQuotes > BaseTestConstants.MAX_QUOTES_INERNTAL));		  
		  
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
