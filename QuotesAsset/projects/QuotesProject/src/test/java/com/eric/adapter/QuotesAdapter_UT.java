package com.eric.adapter;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Test;

import com.eric.domain.constant.BaseConstants;
import com.eric.domain.constant.BaseTestConstants;
import com.eric.ui.listener.QuoteListener;

public class QuotesAdapter_UT
{
	 private static Log methIDrunToQuoteListenerBadInternalBadExternalQuotesFileFAIL;
	 private static Log methIDrunToQuoteListenerNoMaxQuotesFAIL;
	 private static Log methIDrunToQuoteListenerInternalQuotesFileSUCCESS;
	 private static Log methIDrunToQuoteListenerExternalQuotesFileSUCCESS;
	 
	 static
	 {
		 methIDrunToQuoteListenerBadInternalBadExternalQuotesFileFAIL = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runToQuoteListenerBadInternalBadExternalQuotesFileFAIL()");
		 
		 methIDrunToQuoteListenerNoMaxQuotesFAIL = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runToQuoteListenerNoMaxQuotesFAIL()");

		 methIDrunToQuoteListenerInternalQuotesFileSUCCESS = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runToQuoteListenerInternalQuotesFileSUCCESS()");

		 methIDrunToQuoteListenerExternalQuotesFileSUCCESS = LogFactory
				  	.getLog(QuotesAdapter_UT.class.getName()
						+ ".runToQuoteListenerExternalQuotesFileSUCCESS()");		 
		 
	 }

	 @Test
	 public void runToQuoteListenerNoMaxQuotesFAIL()
	 {
		  Log logger = methIDrunToQuoteListenerNoMaxQuotesFAIL;
		  QuoteListener ql = null;		  

		  // Here's the important Part!!		  
		  String targetPropFileName = BaseTestConstants.QUOTES_BAD_INTERNAL_AND_EXTERNAL_QUOTES_FILENAME;
		  
		  logger.debug("Begins...");
		  
		  System.setProperty(BaseConstants.PROP_FILE, targetPropFileName);
		  logger.info("Using PropertyFile: " + targetPropFileName);
		  
		  Assert.assertNull(ql);
		  ql = QuotesAdapter.toQuoteListener();

		  Assert.assertNull(ql);

		  System.clearProperty(BaseConstants.PROP_FILE);		  
		  
		  logger.debug("Ends...");

		  return;
	 }
	 	 
	 @Test
	 public void runToQuoteListenerBadInternalBadExternalQuotesFileFAIL()
	 {
		  Log logger = methIDrunToQuoteListenerBadInternalBadExternalQuotesFileFAIL;
		  QuoteListener ql = null;
		  
		  // Here's the important Part!!		  
		  String targetPropFileName = BaseTestConstants.QUOTES_BAD_INTERNAL_AND_EXTERNAL_QUOTES_FILENAME;
		  
		  logger.debug("Begins...");

		  System.setProperty(BaseConstants.PROP_FILE, targetPropFileName);
		  logger.info("Using PropertyFile: " + targetPropFileName);
		  
		  Assert.assertNull(ql);
		  ql = QuotesAdapter.toQuoteListener();

		  Assert.assertNull(ql);

		  logger.debug("Ends...");

		  return;

	 }
 
	 @Test
	 public void runToQuoteListenerInternalQuotesFileSUCCESS()
	 {
		  Log logger = methIDrunToQuoteListenerInternalQuotesFileSUCCESS;
		  QuoteListener ql = null;
		  int maxQuotes = 0;

		  // Here's the important Part!!		  
		  String targetPropFileName = BaseTestConstants.QUOTES_BAD_EXTERNAL_QUOTES_FILENAME;
		  
		  logger.debug("Begins...");

		  System.setProperty(BaseConstants.PROP_FILE, targetPropFileName);
		  logger.info("Using PropertyFile: " + targetPropFileName);
		  
		  Assert.assertNull(ql);
		  Assert.assertEquals(maxQuotes, 0);
		  
		  ql = QuotesAdapter.toQuoteListener();

		  Assert.assertNotNull(ql);

		  maxQuotes = ql.getMaxQuotes();		  
		  Assert.assertEquals(maxQuotes, BaseTestConstants.MAX_QUOTES_INERNTAL);		  
		  
		  logger.debug("Ends...");

		  return;

	 }
	 
	 @Test
	 public void runToQuoteListenerExternalQuotesFileSUCCESS()
	 {
		  Log logger = methIDrunToQuoteListenerExternalQuotesFileSUCCESS;
		  QuoteListener ql = null;
		  int maxQuotes = 0;
		  
		  // Here's the important Part!!		  
		  String targetPropFileName = BaseTestConstants.QUOTES_BAD_INTERNAL_QUOTES_FILENAME;
		  
		  logger.debug("Begins...");

		  Assert.assertNull(ql);
		  Assert.assertEquals(maxQuotes, 0);
		  
		  ql = QuotesAdapter.toQuoteListener();

		  Assert.assertNotNull(ql);
		  maxQuotes = ql.getMaxQuotes();		  
		  Assert.assertTrue((maxQuotes > 0) && (maxQuotes > BaseTestConstants.MAX_QUOTES_INERNTAL));		  
		  
		  logger.debug("Ends...");

		  return;

	 }
	 
	 @After
	 public void cleanUp()
	 {
		  System.clearProperty(BaseConstants.PROP_FILE);		  
		  return;		 
	 }

}
