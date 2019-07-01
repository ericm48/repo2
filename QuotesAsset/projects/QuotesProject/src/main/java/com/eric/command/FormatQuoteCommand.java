/**
 * 
 * Description:  Command Implementation for reformatting quotes.
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.domain.constant.BaseConstants;


// TODO: Fix Formatting Quotes #1860, 1827, 2133, 2583, 1757 for formatting special chars.

public class FormatQuoteCommand extends BaseCommand
{
	 private static final Log methIDExecute = LogFactory
				.getLog(FormatQuoteCommand.class.getName() + ".execute()");

	 private static String oldQuoteText;

	 private final static String ONE_SPACE = " ";
	 private final static char CHAR_SPACE = ' ';

	 private String quoteText = null;

	 private char[] PuncChars =
	 {
				'.', '?', '!', ';', ':', '(', ')', '%'
	 };

	 private FormatQuoteCommand()
	 {
	 }

	 public FormatQuoteCommand(String newValue)
	 {
		  if ( newValue != null )
		  {
				this.setQuoteText(newValue);
				this.setOldQuoteText(newValue);
		  }
	 }

	 public boolean execute()
     {
		  
	    Log logger = methIDExecute;
    
        boolean returnValue = true;
        boolean control = true;
        boolean skip = false;
        String temp = null;
        String stringOut = null;
        
        int i = 0;
        int maxSize = 0;
        
        char[] textIn = null;
        char[] textOut = null;
        
        logger.debug(BaseConstants.BEGINS);
        
        // Iterate through the quote text that was ~set in, and yank ALL
        // double-spaces,
        // without leading periods. Then ~reset back to our instance.
        // caller will have to cal ~get to get new value. Undo will restore
        // original value.
        
        if ( quoteText != null )
        {
        	    
           	maxSize = quoteText.length();
           	textIn = quoteText.toCharArray();
            
           	// Is the first character a space?
           	if ( textIn[0] == ONE_SPACE.toCharArray()[0] )
           	{
           	    quoteText = quoteText.substring(1);
           	    maxSize = quoteText.length();
           	    textIn = quoteText.toCharArray();
           	}
            
           	// For #471 pos 54 is the first occurance.
            
           	stringOut = "";
            
           	for (i = 0; i < maxSize; i++)
           	{
            
           	    if ( textIn[i] == CHAR_SPACE )
           	    {
            
           	   	  if ( (i + 1) == maxSize )
           	   	  {
           	   			break;
           	   	  }
            
           	   	  if ( (textIn[i + 1] == CHAR_SPACE) && (isPuncChar(textIn[i - 1])) )
           	   	  {
           	   			stringOut = stringOut + textIn[i];
           	   	  }
           	   	  else if ( textIn[i + 1] == CHAR_SPACE )
           	   	  {
           	   			// Shift Everything UP 1.
           	   			// Next Inner Loop 'til end of spaces...
           	   			i++;
           	   			skip = true;
           	   	  }
            
            	 }
            	 else
            	 {
            		  // End of a series of spaces. Make sure we just get ONE.
            		  if ( skip )
            		  {
            				skip = false;
            				stringOut = stringOut + String.valueOf(CHAR_SPACE);
            		  }
            	  }
            
            	  if ( !skip )
            	  {
            			stringOut = stringOut + String.valueOf( textIn[i] );
            	  }
            
            }

           	this.setQuoteText(stringOut);
           	
        }
        else
        {
      		logger.warn("Text Value is NULL!");
      		returnValue = false;
        }
        
        logger.debug(BaseConstants.ENDS);
        
        return(returnValue);
    }

	 private boolean isPuncChar(char e)
	 {
		  boolean returnValue = false;

		  for (int i = 0; (i < PuncChars.length); i++)
		  {
				if ( e == PuncChars[i] )
				{
					 returnValue = true;
					 break;
				}

		  }

		  return(returnValue);
	 }

	 public boolean undo()
	 {
		  boolean returnValue = true;

		  this.setQuoteText(this.getOldQuoteText());

		  return(returnValue);
	 }

	 public Object getResult()
	 {
		  return((String) this.getQuoteText());
	 }

	 private String getQuoteText()
	 {
		  return quoteText;
	 }

	 private static String getOldQuoteText()
	 {
		  return oldQuoteText;
	 }

	 private static void setOldQuoteText(String newValue)
	 {
		  FormatQuoteCommand.oldQuoteText = newValue;
	 }

	 private void setQuoteText(String quoteText)
	 {
		  this.quoteText = quoteText;
	 }

}
