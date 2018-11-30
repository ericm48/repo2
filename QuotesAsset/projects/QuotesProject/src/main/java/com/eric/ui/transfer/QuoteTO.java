/*  
 * QuoteTransfer Object Class.       -Holds the Quote as well as the progress bar
 *                                   that is updated as the Factory locates the quote.
 */
package com.eric.ui.transfer;

import com.eric.ui.component.progress.ProgressComponent;
import com.eric.ui.listener.QuoteListener;


// ToDo: Refactor as "DialogHolder"

public class QuoteTO
{
	private ProgressComponent		progressComponent;    
    private QuoteListener       	quoteListener;  
    private Thread    				thread;
	
	public QuoteListener getQuoteListener() 
	{
		return quoteListener;
	}
	public void setQuoteListener(QuoteListener newValue) 
	{
		this.quoteListener = newValue;
	}

	public Thread getThread() 
	{
		return thread;
	}
	public void setThread(Thread thread) 
	{
		this.thread = thread;
	}

	public ProgressComponent getProgressComponent() 
	{
		return progressComponent;
	}

	public void setProgressComponent(ProgressComponent newComponent) 
	{
		this.progressComponent = newComponent;
	}


}