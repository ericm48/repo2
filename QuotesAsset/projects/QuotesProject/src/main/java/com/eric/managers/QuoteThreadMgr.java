// QuoteMgr.java: Handles Quote File I/O, will also generate a Random Number

package com.eric.managers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.command.Command;
import com.eric.command.FormatQuoteCommand;
import com.eric.domain.constant.BaseConstants;
import com.eric.domain.quote.Quote;
import com.eric.factory.QuoteFactory;
import com.eric.ui.component.progress.ProgressComponent;
import com.eric.ui.listener.QuoteListener;
import com.eric.ui.transfer.QuoteTO;

public class QuoteThreadMgr implements Runnable
{

	 private static final Log methIDLocateQuote;
	 private static final Log methIDInitQFile;
	 private static final Log methIDRun;

	 static
	 {

		  methIDLocateQuote = LogFactory.getLog(QuoteThreadMgr.class.getName()
					 + ".locateQuote()");
		  methIDInitQFile = LogFactory.getLog(QuoteThreadMgr.class.getName()
					 + ".initQFile()");
		  methIDRun = LogFactory
					 .getLog(QuoteThreadMgr.class.getName() + ".run()");
	 }

	 // Constants
	 final static String QUOTES_FILE = "c:\\apps\\classes\\QUOTES.TXT";
	 final static String MSG_ERROR_QFILE_MIA = "Error Unable To Open File: ";

	 // Declare file reader and writer streams
	 private FileReader fileReader = null;

	 // Buffered Reader
	 private BufferedReader bufferedReader = null;

	 // Random Access File
	 private RandomAccessFile raFile = null;

	 private long lFilePointer = 0;
	 private long lFileLength = 0;

	 private ProgressComponent progressBar = null;

	 private Thread ourThread;

	 private QuoteListener quoteListener = null;

	 // Progress Bar Items.
	 private final int iPBMin = 0;
	 private int iPBCount = 0;
	 private int iPBMax = 0;

	 // Determine if the thread is suspended.
	 private boolean bSuspended = false;
	 private int iMaxQuotes = 0;
	 private int targetQuoteNumber = 0;
	 protected boolean bInitialized = true;
	 private QuoteTO quoteTO;

	 // -----------------------------------------------------------------
	 // Default Constructor
	 // -----------------------------------------------------------------
	 private QuoteThreadMgr()
	 {
		  super();
		  return;
	 }

	 // -----------------------------------------------------------------
	 // Constructor a quote from specific quote number.
	 // -----------------------------------------------------------------
	 public QuoteThreadMgr(QuoteTO newValue)
	 {
		  super();

		  boolean bReturnValue = true;

		  while (bReturnValue)
		  {

				this.quoteTO = newValue;

				bReturnValue = this.loadFromTO(quoteTO);

				break;

		  }

		  return;
	 }

	 // -----------------------------------------------------------------
	 // Load View Param's
	 // -----------------------------------------------------------------
	 private boolean loadFromTO(QuoteTO quoteTO)
	 {

		  boolean bReturnValue = true;

		  while (quoteTO != null)
		  {

				if ( quoteTO.getProgressComponent() != null )
				{
					 this.setProgressBar(quoteTO.getProgressComponent());

					 this.initProgressComp();
				}

				if ( quoteTO.getQuoteListener() != null )
				{
					 this.setTargetQuoteNumber(quoteTO.getQuoteListener()
								.getTargetQuoteNumber());
				}

				if ( quoteTO.getQuoteListener() != null )
				{
					 this.quoteListener = quoteTO.getQuoteListener();
				}
				else
				{
					 bReturnValue = false;
					 break;
				}

				// Init the Thread & Set the Thread Instance Back into the TO for
				// the
				// factory to get.

				// this.initThread();

				break;

		  }

		  return(bReturnValue);
	 }

	 // -----------------------------------------------------------------
	 //
	 // -----------------------------------------------------------------
	 public boolean setProgressBar(ProgressComponent progressBarNew)
	 {
		  boolean bReturnValue = true;

		  if ( progressBarNew != null )
		  {
				this.progressBar = progressBarNew;

				// Initialize Their Progress Bar
				this.initProgressComp();
		  }
		  else
		  {
				bReturnValue = false;
		  }

		  return(bReturnValue);
	 }

	 // -----------------------------------------------------------------
	 // Set the Holder Stuff for sub-sequent runs....
	 // -----------------------------------------------------------------
	 public boolean setQuoteTO(QuoteTO quoteTO)
	 {
		  boolean bReturnValue = true;

		  this.loadFromTO(quoteTO);

		  return(bReturnValue);
	 }

	 // -------------------------------------------------------------------------
	 // Locate the quote for this number, create a quote object with data
	 // -------------------------------------------------------------------------
	 public boolean getQuote()
	 {
		  boolean bReturnValue = true;

		  if ( this.ourThread == null )
		  {
				this.initThread();
				ourThread.start();

		  }
		  else
		  {
				this.ourThread = null;
				this.initThread();
				ourThread.start();

		  }

		  return(bReturnValue);
	 }

	 /**
	  * 
	  * 
	  * New Version.
	  * 
	  */
	 public void run()
	 {
		  Log logger = methIDRun;

		  QuoteFactory qf = null;
		  Command qc = null;
		  
		  Quote quote = null;

		  boolean returnValue = true;
		  boolean control = true;

		  logger.debug(BaseConstants.BEGINS);

		  synchronized (this)
		  {
				qf = new QuoteFactory();

				iPBMax = this.targetQuoteNumber;
				this.initProgressComp();

				while (control)
				{

					 if ( qf == null )
					 {
						  methIDRun.error("***ERROR Quote Factory is Null!!");
						  control = false;
						  break;
					 }

					 quoteTO = qf.getQuoteWithTO(quoteTO);

					 qc = new FormatQuoteCommand(quoteTO.getQuoteListener()
								.getQuote().getQuoteText());

					 returnValue = qc.execute();

					 if ( returnValue )
					 {
					  
	          		   quoteTO.getQuoteListener().getQuote().setQuoteText(qc.getResult().toString());
	          		   
	          		   quote = quoteTO.getQuoteListener().getQuote();

	          		   // Reset the Quote To FIRE the listener.
	          		   quoteTO.getQuoteListener().setQuote(quote);        		   

					 }

					 // Safety Purposes.
					 control = false;
					 break;

				} // End While.

				qf = null;
		  }

		  logger.debug(BaseConstants.ENDS);

		  return;
	 }

	 // -----------------------------------------------------------------
	 private boolean initThread()
	 {
		  boolean bReturnValue = true;

		  // Create the thread
		  ourThread = new Thread(this);

		  if ( ourThread != null )
		  {
				quoteTO.setThread(ourThread);
		  }
		  else
		  {
				bReturnValue = false;
		  }

		  return(bReturnValue);
	 }

	 // -----------------------------------------------------------------
	 private synchronized boolean locateQuote()
	 {

		  Log logger = methIDLocateQuote;

		  logger.debug(BaseConstants.BEGINS);

		  boolean bReturnValue = true;
		  boolean bLoop = true;

		  double dTemp = 0.00;

		  int iNQuote = 0;

		  String sLineIn = null;

		  StringBuffer strBuffer = null;

		  // FIX THIS!!!!
		  iPBMax = this.targetQuoteNumber;
		  this.initProgressComp();

		  strBuffer = null; // new StringBuffer();

		  try
		  {
				sLineIn = "";

				// iNQuote = 1;

				logger.info("Locating Quote Number: " + targetQuoteNumber);

				while ((bLoop) && (sLineIn != null))
				{
					 if ( lFilePointer < lFileLength )
					 {
						  bLoop = true;
					 }
					 else
					 {
						  bLoop = false;
						  break;
					 }

					 try
					 {

						  // FIX THIS!!!
						  ourThread.sleep(1);

						  while (bSuspended)
						  {
								wait();
						  }

						  sLineIn = raFile.readLine().trim();
						  lFilePointer = raFile.getFilePointer();

						  // Start of a Quote, Blank Line
						  if ( sLineIn.trim().length() == 0 )
						  {

								if ( iNQuote == targetQuoteNumber )
								{
									 bLoop = false;

									 logger.info("Quote Number: " + targetQuoteNumber
												+ " LOCATED!!!");
								}
								else
								{
									 // If using a progress bar, calculate it's position.
									 if ( progressBar != null )
									 {
										  progressBar.setValue(iNQuote);
									 }

									 strBuffer = null;
									 strBuffer = new StringBuffer();

									 iNQuote++;
								}

						  }
						  else
						  {
								strBuffer.append(" ");
								strBuffer.append(sLineIn);
						  }

					 } // end try for Thread
					 catch ( InterruptedException e )
					 {
						  logger.error("Caught in Interrupted Ex: " + e.getMessage());
						  bReturnValue = false;
					 }

				} // end while

		  } // end try for File I/O

		  catch ( IOException e )
		  {
				System.err.println("Error: " + e);
				bReturnValue = false;
		  }

		  if ( bReturnValue )
		  {
				quoteListener.getQuote().setQuoteText(strBuffer.toString());
		  }

		  logger.debug(BaseConstants.ENDS);

		  return(bReturnValue);

	 }

	 // -------------------------------------------------------------------------
	 private boolean initProgressComp()
	 {
		  boolean bReturnValue = true;

		  iPBCount = iPBMin;

		  if ( quoteTO.getProgressComponent() != null )
		  {
				quoteTO.getProgressComponent().setMinValue(iPBMin);
				quoteTO.getProgressComponent().setMaxValue(iPBMin);
				quoteTO.getProgressComponent().setValue(iPBMin);
		  }

		  return(bReturnValue);
	 }

	 // -----------------------------------------------------------------
	 private synchronized boolean closeQFile()
	 {
		  boolean bReturnValue = true;

		  try
		  {
				// if ( fileReader != null )
				if ( raFile != null )
				{
					 // fileReader.close();
					 raFile.close();

					 // fileReader = null;
					 raFile = null;
					 bufferedReader = null;
				}
		  }
		  catch ( IOException ex )
		  {
				System.out.println(ex);
		  }

		  return(bReturnValue);

	 }

	 // -----------------------------------------------------------------
	 private void setMaxQuotes(int iNewValue)
	 {
		  iMaxQuotes = iNewValue;
		  return;
	 }

	 // -----------------------------------------------------------------
	 public void setTargetQuoteNumber(int iNewValue)
	 {
		  targetQuoteNumber = iNewValue;
		  return;
	 }

	 // -----------------------------------------------------------------
	 // Implement Suspend
	 // -----------------------------------------------------------------
	 public synchronized void suspend()
	 {
		  bSuspended = true;
		  return;
	 }

	 // -----------------------------------------------------------------
	 // Implement resume.
	 // -----------------------------------------------------------------
	 public synchronized void resume()
	 {

		  if ( bSuspended )
		  {
				bSuspended = false;
				notify();
		  }
		  return;
	 }

	 // -----------------------------------------------------------------
	 public boolean isInitialized()
	 {
		  return(this.bInitialized);
	 }

	 // -----------------------------------------------------------------
	 public int getMaxQuotes()
	 {
		  return(iMaxQuotes);
	 }

	 public QuoteTO getQuoteTO()
	 {
		  return quoteTO;
	 }

}
