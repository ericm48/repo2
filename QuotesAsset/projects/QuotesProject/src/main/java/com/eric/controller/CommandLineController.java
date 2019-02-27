/**
 * 
 * Description:  Main Command Line Controller Class to invoke BOTH Text & GUI Versions of
 * Quotes App.
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.domain.constant.BaseConstants;

public class CommandLineController
{

	 /**
	  * Getting Logger Object
	  */
	 private static final Log methIDMain;

	 static
	 {
		  methIDMain = LogFactory.getLog(CommandLineController.class.getName()
					 + ".main()");
	 }

	 /**
	  * Main Method for invoking the either Text or GUI version.
	  * 
	  * @param args
	  * @param Interface
	  *            Type Required
	  * @param Quote
	  *            Number Optional
	  * 
	  */
	 public static void main(String[] args)
	 {

		  Log logger = methIDMain;

		  int targetQuote = 0;
		  int i = 0;
		  boolean control = true;
		  QuoteController qc = null;

		  // =================================================================
		  // Order Should be:
		  //
		  // -g/-G or -t/-T -Applet-GUI Interface or Text Interface
		  // Quote # -Quote # to Display.
		  //
		  // =================================================================

		  logger.debug(BaseConstants.BEGINS);

		  if ( args.length > 0 )
		  {
				i = 0;

				while (control)
				{
					 if ( args[i].equalsIgnoreCase(BaseConstants.DASH_G) )
					 {
						  qc = new QuoteGUIController();
					 }
					 else if ( args[i].equalsIgnoreCase(BaseConstants.DASH_T) )
					 {
						  qc = new QuoteTextController();
					 }
					 else
					 {
						  System.out
									 .println("\n\n*** ERROR Invalid Argument Received: "
												+ args[i] + " \n");
						  control = false;
						  printUsage();
						  break;
					 }

					 if ( args.length > 1 )
					 {
						  targetQuote = Integer.valueOf(args[++i]).intValue();
					 }

					 i++;

					 // Safety Purposes
					 if ( i >= args.length )
					 {
						  control = false;
						  break;
					 }

				}

				if ( qc != null )
				{
					 qc.showQuote(targetQuote);
					 qc = null;
				}

		  }
		  else
		  {
				printUsage();
		  }

		  logger.debug(BaseConstants.ENDS);

	 }

	 /**
	  * 
	  * Displays usage information, params, etc.
	  * 
	  */
	 protected static void printUsage()
	 {
		  System.out.println("Usage: java "
					 + CommandLineController.class.getName()
					 + " [interface-type], <QuoteNumber>");
		  System.out.println("");
		  System.out
					 .println("                                                          -g/-G  GUI-Applet Interface");
		  System.out
					 .println("                                                          -t/-T  Text Command Line Interface");
		  System.out.println("");
		  System.out.println("");
		  System.out.println("eg: java -classpath <jars and config files> "
					 + CommandLineController.class.getName() + " -G 443");
		  System.out.println("    Runs the GUI-Applet, displaying Quote #443.");
	 }

}
