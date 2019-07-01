/**
 * 
 * Description:  Quote Domain Object.
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.domain.quote;

import java.util.Enumeration;
import java.util.Properties;

import com.eric.domain.constant.BaseConstants;

public class Quote 
{	
    private String    quoteText;    
    private int       quoteNumber;
    private String	  quoteAuthor;	
 
	public Quote()
    {    	
    }
    
    public Quote(int targetQuoteNumber)
    {
    	if ( targetQuoteNumber != 0 )
    	{
    		this.setQuoteNumber( targetQuoteNumber );
    	}
    }
    
    public String getQuoteText() 
	{
		return quoteText;
	}
	public void setQuoteText(String quoteText) 
	{
		this.quoteText = quoteText;
	}
	public int getQuoteNumber() 
	{
		return quoteNumber;
	}
	public void setQuoteNumber(int quoteNumber) 
	{
		this.quoteNumber = quoteNumber;
	}

    public String getQuoteAuthor() 
    {
		return quoteAuthor;
	}

	public void setQuoteAuthor(String quoteAuthor) 
	{
		this.quoteAuthor = quoteAuthor;
	}

	//@SuppressWarnings("unchecked")
	public String toString()
	{
		String returnValue				= null;
		StringBuilder sb				= null;
		
		sb = new StringBuilder();

		sb.append("[ Quote: "); 

		sb.append( 		
 						   "|, quoteText = |" + this.getQuoteText() + "| " +      	                
                           " quoteNumber = |" + this.getQuoteNumber() + "| " +
                           " quoteAuthor = |" + this.getQuoteAuthor() + "| "  								   
				  );
		
		
			
		sb.append(" ] ");
		
		returnValue = sb.toString();
		
		return( returnValue );
			
	}	

}
