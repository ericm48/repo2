package com.eric.command;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class FormatQuoteCommand_UT
{

	 /**
	  * Getting Logger Objects
	  */
	 private static final Log methIDRunFormatNullQuoteFAIL;
	 
	 private static final Log methIDRunFormatQuoteTest471SUCCESS;
	 private static final Log methIDRunFormatQuoteTest471UndoSUCCESS;	 
	 private static final String badQuote;
	 private static final String fixedQuote;

	 static
	 {

		  methIDRunFormatNullQuoteFAIL = LogFactory
				  	.getLog(FormatQuoteCommand_UT.class.getName()
						+ ".runFormatNullQuoteFail()");
		  
		  methIDRunFormatQuoteTest471SUCCESS = LogFactory
					 .getLog(FormatQuoteCommand_UT.class.getName()
						+ ".runFormatQuoteTest471()");
		  
		  methIDRunFormatQuoteTest471UndoSUCCESS = LogFactory
				  	.getLog(FormatQuoteCommand_UT.class.getName()
						+ ".runFormatQuoteTest471Undo()");
		  
		  badQuote = "More powerfull than all success slogans ever penned by humans                     is the realization for every man that he has but one boss.  That                  boss is the man--he--himself.";
		  fixedQuote = "More powerfull than all success slogans ever penned by humans is the realization for every man that he has but one boss.   That boss is the man--he--himself.";

	 }

	 @Test
	 public void runFormatNullQuoteFAIL()
	 {

		  Log logger = methIDRunFormatNullQuoteFAIL;

		  logger.debug("Begins...");

		  String s2 = null;
		  Command fCmd = null;
		  boolean returnValue = false;

		  fCmd = new FormatQuoteCommand(null);

		  logger.info("String Before: " + null);

		  returnValue = fCmd.execute();

		  Assert.assertEquals(false, returnValue);

		  Assert.assertNull(s2);

		  logger.info("String After: " + s2);

		  logger.debug("Ends...");

		  return;

	 }

	 @Test
	 public void runFormatQuoteTest471SUCCESS()
	 {
		  Log logger = methIDRunFormatQuoteTest471SUCCESS;

		  logger.debug("Begins...");

		  Command fCmd = null;
		  String s2 = null;
		  boolean returnValue = false;

		  fCmd = new FormatQuoteCommand(badQuote);

		  logger.info("String Before: " + badQuote);

		  returnValue = fCmd.execute();

		  Assert.assertEquals(true, returnValue);

		  s2 = fCmd.getResult().toString();

		  logger.info("String After: " + s2);

		  Assert.assertNotNull(s2);

		  Assert.assertEquals(s2, fixedQuote);

		  logger.debug("Ends...");

		  return;
	 }

	 
	 @Test
	 public void runFormatQuoteTest471UndoSUCCESS()
	 {
		  Log logger = methIDRunFormatQuoteTest471UndoSUCCESS;

		  logger.debug("Begins...");

		  Command fCmd 			= null;
		  String s2 			= null;
		  String s3				= null;
		  boolean returnValue 	= false;

		  fCmd = new FormatQuoteCommand(badQuote);

		  logger.info("String Before: " + badQuote);

		  returnValue = fCmd.execute();

		  Assert.assertEquals(true, returnValue);

		  s2 = fCmd.getResult().toString();

		  logger.info("String  After: " + s2);

		  Assert.assertNotNull( s2 );
		  Assert.assertEquals(s2, fixedQuote);
		  
		  returnValue = fCmd.undo();
		  Assert.assertEquals(true, returnValue);
		  
		  s3 = fCmd.getResult().toString();

		  logger.info("String  After: " + s3);

		  Assert.assertNotNull( s3 );
		  Assert.assertEquals(s3, badQuote);
		  
		  logger.debug("Ends...");

		  return;
	 }
	 
}
