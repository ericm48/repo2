package com.eric.domain.quote;

public class QuoteHolder 
{
	private Quote quote							= null;	
	private int targetQuoteNumber				= 0;
    private int maxQuotes						= 0;	
    private String currentJDK					= null;
    private String quotesAppVersion				= null;
    
	public Quote getQuote() 
	{
		return quote;
	}
	public void setQuote(Quote quote) 
	{
		this.quote = quote;
	}
	public int getTargetQuoteNumber() 
	{
		return targetQuoteNumber;
	}
	public void setTargetQuoteNumber(int targetQuoteNumber) 
	{
		this.targetQuoteNumber = targetQuoteNumber;
	}
	public int getMaxQuotes() 
	{
		return maxQuotes;
	}
	public void setMaxQuotes(int maxQuotes) 
	{
		this.maxQuotes = maxQuotes;
	}
	public String getCurrentJDK() 
	{
		return currentJDK;
	}
	public void setCurrentJDK(String currentJDK) 
	{
		this.currentJDK = currentJDK;
	}
	public String getQuotesAppVersion() 
	{
		return quotesAppVersion;
	}
	public void setQuotesAppVersion(String quotesAppVersion) 
	{
		this.quotesAppVersion = quotesAppVersion;
	}

}
