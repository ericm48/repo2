/**
 * 
 * Description:  Quote Domain Object.
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.domain.quote;

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
    

}
