/**
 * QuoteLisenter Class includes domain quote class, plus listener items.
 */
package com.eric.ui.listener;

import java.io.Serializable;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import com.eric.domain.quote.Quote;


// ToDo: Refactor name as "DialogListener"
// ToDo: Refactor Yank first-5 attribs to another class as QuoteHolder, or QuoteTO.

public class QuoteListener 
{	
	private Quote quote							= null;	
	private int targetQuoteNumber				= 0;
    private int maxQuotes						= 0;	
    private String currentJDK					= null;
    private String quotesAppVersion				= null;
	
    protected ChangeListener changeListener     = null; 
    protected EventListenerList listenerList    = new EventListenerList();
    protected transient ChangeEvent changeEvent = null;

    //===================================================================
    // Inner Class
    //===================================================================
    private class ModelListener implements ChangeListener, Serializable
    {
        public void stateChanged(ChangeEvent e)
        {
            fireStateChanged();
            return;
        }
    }

    //-----------------------------------------------------------------
    public QuoteListener()
    {
        return;
    }
    //-----------------------------------------------------------------
    public QuoteListener(int newValue)
    {
        this.setTargetQuoteNumber( newValue );

        return;
    }
    //-----------------------------------------------------------------
    public void addChangeListener(ChangeListener l)
    {
        listenerList.add(ChangeListener.class, l);
        return;
    }
    //-----------------------------------------------------------------
    public void removeChangeListener(ChangeListener l)
    {
        listenerList.remove(ChangeListener.class, l);
        return;
    }
    //-----------------------------------------------------------------
    protected void fireStateChanged()
    {
        Object[] listeners = null;
                     int i = 0;
                     
        listeners = listenerList.getListenerList();

        for (i = listeners.length - 2; i >= 0; i -= 2)
        {
            if ( listeners[i]==ChangeListener.class )
            {
                if ( changeEvent == null )
                {
                    changeEvent = new ChangeEvent(this);
                }
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }
        }

        return;
    }
    //-----------------------------------------------------------------
    protected ChangeListener createChangeListener()
    {
        return new ModelListener();
    }
	public int getTargetQuoteNumber() 
	{
		return targetQuoteNumber;
	}
	public void setTargetQuoteNumber(int targetQuoteNumber) 
	{
		this.targetQuoteNumber = targetQuoteNumber;
		this.fireStateChanged();
	}
	public Quote getQuote() 
	{		
		return quote;
	}
	public void setQuote(Quote quote) 
	{
		this.quote = quote;
		this.fireStateChanged();		
	}
	public int getMaxQuotes() 
	{
		return maxQuotes;
	}
	public void setMaxQuotes(int maxQuotes) 
	{
		this.maxQuotes = maxQuotes;
		this.fireStateChanged();		
	}
	
	public void setCurrentJDK(String newString)
	{
		this.currentJDK = newString;
	}
	
	public String getCurrentJDK()
	{
		return this.currentJDK;
	}
	
	public void setQuotesAppVersion(String newString)
	{
		this.quotesAppVersion = newString;
	}
	
	public String getQuotesAppVersion()
	{
		return this.quotesAppVersion;
	}	
	

}
