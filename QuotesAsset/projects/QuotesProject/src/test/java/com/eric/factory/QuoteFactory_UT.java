package com.eric.factory;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eric.domain.common.enumeration.AppPropFileKey;
import com.eric.domain.constant.BaseTestConstants;
import com.eric.domain.quote.QuoteHolder;
import com.eric.test.util.QuotesTestUtil;
import com.eric.ui.holder.DialogHolder;

public class QuoteFactory_UT
{
	
	private static Log methIDrunTestGetQuoteWithHolderNULLDiaglogHolderFAIL;	 
	private static Log methIDrunTestGetQuoteWithHolderNULLDiaglogListenerFAIL;

	private static Log methIDrunTestGetQuoteWithHolderSUCCESS;
	
//	private static Log methIDrunToDialogListenerBadInternalBadExternalQuotesFileFAIL;
//	private static Log methIDrunToDialogListenerInternalNoMaxQuotesFAIL;
//	private static Log methIDrunToDialogListenerInternalQuotesFileSUCCESS;
//	private static Log methIDrunToDialogListenerExternalQuotesFileSUCCESS;
	 
	static
	{

		methIDrunTestGetQuoteWithHolderNULLDiaglogHolderFAIL = LogFactory
			  	.getLog(QuoteFactory_UT.class.getName()
					+ ".runTestGetQuoteWithHolderNULLDiaglogHolderFAIL()");

		methIDrunTestGetQuoteWithHolderNULLDiaglogListenerFAIL = LogFactory
			  	.getLog(QuoteFactory_UT.class.getName()
					+ ".runTestGetQuoteWithHolderNULLDiaglogListenerFAIL()");

//		 methIDrunToDialogListenerBadInternalBadExternalQuotesFileFAIL = LogFactory
//	  			.getLog(QuoteFactory_UT.class.getName()
//					+ ".runToDialogListenerBadInternalBadExternalQuotesFileFAIL()");
//
//		methIDrunToDialogListenerInternalNoMaxQuotesFAIL = LogFactory
//	  			.getLog(QuoteFactory_UT.class.getName()
//					+ ".runToDialogListenerInternalNoMaxQuotesFAIL()");
//
//		methIDrunToDialogListenerInternalQuotesFileSUCCESS = LogFactory
//	  			.getLog(QuoteFactory_UT.class.getName()
//					+ ".runToDialogListenerInternalQuotesFileSUCCESS()");
//
//		methIDrunToDialogListenerExternalQuotesFileSUCCESS = LogFactory
//	  			.getLog(QuoteFactory_UT.class.getName()
//					+ ".runToDialogListenerExternalQuotesFileSUCCESS()");		 
		
		
		
		methIDrunTestGetQuoteWithHolderSUCCESS = LogFactory
				  	.getLog(QuoteFactory_UT.class.getName()
						+ ".runTestGetQuoteWithHolderSUCCESS()");

		
		 
	}

	@Before
	public void setUp()
	{
		return;
	}
	
	
	@Test
	public void runTestGetQuoteWithHolderNULLDiaglogHolderFAIL()
	{
		Log logger = methIDrunTestGetQuoteWithHolderNULLDiaglogHolderFAIL;

		QuoteFactory qf = null;
		
		// Here's the important Part!!
		DialogHolder dialogHolder = null;
				
		String returnValue = null;
		
		logger.debug(BaseTestConstants.BEGINS);

		Assert.assertNull(dialogHolder);
		Assert.assertNull(returnValue);

		qf = new QuoteFactory();
		Assert.assertNotNull(qf);
		
		dialogHolder = qf.getQuoteWithHolder(dialogHolder);
		
		Assert.assertNull(dialogHolder);
		
		logger.debug(BaseTestConstants.ENDS);

		return;		
	}	 

	@Test
	public void runTestGetQuoteWithHolderNULLDiaglogListenerFAIL()
	{
		Log logger = methIDrunTestGetQuoteWithHolderNULLDiaglogListenerFAIL;

		QuoteFactory qf = null;
		
		DialogHolder dialogHolder = null;
				
		String returnValue = null;
		
		logger.debug(BaseTestConstants.BEGINS);

		Assert.assertNull(dialogHolder);
		Assert.assertNull(returnValue);

		qf = new QuoteFactory();
		Assert.assertNotNull(qf);

		dialogHolder = QuotesTestUtil.toDialogHolder();
		
		// Here's the important Part!!
		dialogHolder.setQuoteListener(null);
		
		dialogHolder = qf.getQuoteWithHolder(dialogHolder);
		
		Assert.assertNotNull(dialogHolder);
		Assert.assertNull(dialogHolder.getDialogListener());
		
		logger.debug(BaseTestConstants.ENDS);

		return;		
	}	 	
	
	@Test
	public void runTestGetQuoteWithHolderSUCCESS()
	{
		Log logger 							= methIDrunTestGetQuoteWithHolderSUCCESS;
		QuoteFactory qf 					= null;
		QuoteHolder quoteHolder 			= null;
		
		// Here's the important Part!!
		String returnValue = null;

		DialogHolder dialogHolder = null;
		
		logger.debug(BaseTestConstants.BEGINS);

		Assert.assertNull(returnValue);

		qf = new QuoteFactory();

		Assert.assertNotNull(qf);
		
		dialogHolder = QuotesTestUtil.toDialogHolder();
		
		Assert.assertNotNull(dialogHolder);		
		
		dialogHolder = qf.getQuoteWithHolder(dialogHolder);
		
		Assert.assertNotNull(dialogHolder);		
		
		logger.debug("   Received Quote #: " + dialogHolder.getDialogListener().getQuote().getQuoteNumber());
		logger.debug("Received Quote Text: " + dialogHolder.getDialogListener().getQuote().getQuoteText());		
		
		logger.debug(BaseTestConstants.ENDS);

		return;		
	}	 

	
	 
	@After
	public void cleanUp()
	{		
		return;		 
	}

}
