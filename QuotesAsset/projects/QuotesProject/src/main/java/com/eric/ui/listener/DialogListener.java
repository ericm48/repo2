/**
 * DialogListener Class includes domain quote class, plus listener items.
 */
package com.eric.ui.listener;

import java.io.Serializable;
import java.util.Properties;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import com.eric.domain.quote.Quote;
import com.eric.domain.quote.QuoteHolder;

public class DialogListener 
{	
	private QuoteHolder quoteHolder				= null;	
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
    public DialogListener()
    {
        return;
    }
    //-----------------------------------------------------------------
    public DialogListener(int newValue)
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
    
    public void setQuoteHolder(QuoteHolder newValue)
    {
    	if ( newValue != null)
    	{
    		this.quoteHolder = newValue;
    	}
    	
    }
    
    public QuoteHolder getQuoteHolder()
    {
    	return( this.quoteHolder );
    }
    
	public int getTargetQuoteNumber() 
	{
		return( quoteHolder.getTargetQuoteNumber() );
	}
	public void setTargetQuoteNumber(int targetQuoteNumber) 
	{
		this.quoteHolder.setTargetQuoteNumber(targetQuoteNumber);
		this.fireStateChanged();
	}
	public Quote getQuote() 
	{		
		return( quoteHolder.getQuote() );
	}
	public void setQuote(Quote quote) 
	{
		this.quoteHolder.setQuote(quote);		
		this.fireStateChanged();		
	}
	public int getMaxQuotes() 
	{
		return( quoteHolder.getMaxQuotes() );
	}
	public void setMaxQuotes(int maxQuotes) 
	{
		this.quoteHolder.setMaxQuotes(maxQuotes);
		this.fireStateChanged();		
	}
	
//	public void setCurrentJDK(String newString)
//	{
//		this.quoteHolder.setCurrentJDK(newString);
//	}
//	
//	public String getCurrentJDK()
//	{
//		return( quoteHolder.getCurrentJDK() );
//	}
	
//	public void setQuotesAppVersion(String newString)
//	{
//		this.quoteHolder.setQuotesAppVersion(newString);
//	}
//	
//	public String getQuotesAppVersion()
//	{
//		return( this.quoteHolder.getQuotesAppVersion());
//	}	
	
//	public void setProperties(Properties newProps)
//	{
//		this.props = newProps;
//	}
//
//	public Properties getProperties()
//	{
//		return( this.props );
//	}
	
}
