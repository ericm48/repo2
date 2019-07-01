/**
 * 
 * Description:  QuoteHolder Domain Object.
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.domain.quote;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.eric.domain.constant.BaseConstants;

public class QuoteHolder 
{
	private Quote quote							= null;	
	private int targetQuoteNumber				= 0;
    private int maxQuotes						= 0;	
    private String currentJDK					= null;
    private String quotesAppVersion				= null;
    private String hostName						= null;
    private String quotesFileID					= null;
    private int sleepInterval					= 0;
    private int maxLoop							= 0;    
    private String javaRuntimePath				= null;
    
    private Properties props					= null;    
    
	public Quote getQuote() 
	{
		return this.quote;
	}
	public void setQuote(Quote newValue) 
	{
		this.quote = newValue;
	}
	public int getTargetQuoteNumber() 
	{
		return this.targetQuoteNumber;
	}
	public void setTargetQuoteNumber(int newValue) 
	{
		this.targetQuoteNumber = newValue;
	}
	public int getMaxQuotes() 
	{
		return this.maxQuotes;
	}
	public void setMaxQuotes(int newValue) 
	{
		this.maxQuotes = newValue;
	}
	public String getCurrentJDK() 
	{
		return this.currentJDK;
	}
	public void setCurrentJDK(String newValue) 
	{
		this.currentJDK = newValue;
	}
	public String getQuotesAppVersion() 
	{
		return this.quotesAppVersion;
	}
	public void setQuotesAppVersion(String newValue) 
	{
		this.quotesAppVersion = newValue;
	}
	public String getQuotesFileID() 
	{
		return this.quotesFileID;
	}
	public void setgetQuotesFileID(String newValue) 
	{
		this.quotesFileID = newValue;
	}
	public String getHostName() 
	{
		return this.hostName;
	}
	public void setHostName(String newValue) 
	{
		this.hostName = newValue;
	}		
	public void setProperties(Properties newValue)
	{
		this.props = newValue;
	}
	public Properties getProperties()
	{
		return( this.props );
	}	
	public int getSleepInterval() 
	{
		return this.sleepInterval;
	}
	public void setSleepInterval(int newValue) 
	{
		this.sleepInterval = newValue;
	}	
	public int getMaxLoop() 
	{
		return this.maxLoop;
	}
	public void setMaxLoop(int newValue) 
	{
		this.maxLoop = newValue;
	}
	public String getJavaRuntimePath() 
	{
		return this.javaRuntimePath;
	}
	public void setJavaRuntimePath(String newValue) 
	{
		this.javaRuntimePath = newValue;
	}
	
	
	// TODO: Here Dude:  Add toString()
	
	@SuppressWarnings("unchecked")
	public String toString()
	{
		Properties props				= null;
		Enumeration<String> propEnums	= null;	
		String key						= null;
		String value					= null;
		String returnValue				= null;
		StringBuilder sb				= null;
		Quote quote						= null;
		
		sb = new StringBuilder();

		sb.append("[ QuoteHolder: "); 

		quote = this.getQuote();
		
		returnValue = ( quote != null ) ? quote.toString() : BaseConstants.NULL_DELIMITED; 
		
		sb.append( 		
 							     " quote = "  + returnValue +                	                
                     " targetQuoteNumber = |" + this.getTargetQuoteNumber() + "| " +
                	          "maxQuotes = |" + this.getMaxQuotes() + "| " +
                	         "currentJDK = |" + this.getCurrentJDK() + "| " +
                	   "quotesAppVersion = |" + this.getQuotesAppVersion() + "| " +                	          
                	           "hostName = |" + this.getHostName() + "| " +                	          
                	       "quotesFileID = |" + this.getQuotesFileID() + "| " +                	 
                	      "sleepInterval = |" + this.getSleepInterval() + "| " +
                	            "maxLoop = |" + this.getMaxLoop() + "| " +                	      
                	    "javaRuntimePath = |" + this.getJavaRuntimePath() + "| " +
                	 BaseConstants.FIVE_SPACES + 
                	"[ Properties: "
				  );
		
		
		// Check and see what we have for properties...
		
		props = this.getProperties();
		
		if (( props != null ) || ( !props.isEmpty()))
		{		
			propEnums = (Enumeration<String>) props.propertyNames();
			
		    while (propEnums.hasMoreElements()) 
		    {
		      key = propEnums.nextElement();
		      value = props.getProperty(key);

		      sb.append(    " key = |" + key +
			  "|, value = |" + value +
			  "|"   );					
		      
		    }			
			
		}
		else
		{
			sb.append(BaseConstants.NULL_DELIMITED);
		}
			
		sb.append(" ] ");
		
		returnValue = sb.toString();
		
		return( returnValue );
			
	}		
	
}
