/*  
 * QuoteTransfer Object Class.       -Holds the Quote as well as the progress bar
 *                                   that is updated as the Factory locates the quote.
 */
package com.eric.ui.holder;

import com.eric.ui.component.progress.ProgressComponent;
import com.eric.ui.listener.DialogListener;


public class DialogHolder
{
	private ProgressComponent		progressComponent;    
    private DialogListener       	dialogListener;  
    private Thread    				thread;
	
	public DialogListener getDialogListener() 
	{
		return dialogListener;
	}
	public void setDialogListener(DialogListener newValue) 
	{
		this.dialogListener = newValue;
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