/**
 * 
 * Description:  ProgressBar Implementation.
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.ui.component.progress;

import javax.swing.JProgressBar;

public class JProgressBarImpl extends BaseProgressComponent
{	
	JProgressBar progressBar			= null;

	private JProgressBarImpl()
	{
	}
	
	public JProgressBarImpl(JProgressBar newValue)
	{
		if ( newValue != null )
		{
			this.setProgressBar( newValue );
			this.init();
		}
	}
	
	private void init()
	{
		this.setMaxValue( 0 );
		this.setMinValue( 0 );
		this.setValue( 0 );
	}	
	
	public boolean setValue(int newValue)
	{		
		return( this.setProgressBarValue( newValue ) );
	}
		
	private boolean setProgressBarValue(int newValue)
	{		
		boolean returnValue = true;
	
		if ( progressBar != null )
		{
			progressBar.setValue( newValue );			
		}		
		
		return( returnValue );
	}
	
	
	public boolean setMinValue(int newValue)
	{		
		boolean returnValue = true;
	
		if ( progressBar != null )
		{
			progressBar.setMinimum( newValue );			
		}		
		
		return( returnValue );
	}

	public boolean setMaxValue(int newValue)
	{		
		boolean returnValue = true;
	
		if ( progressBar != null )
		{
			progressBar.setMaximum( newValue );			
		}		
		
		return( returnValue );
	}
	
	public JProgressBar getProgressBar() 
	{
		return progressBar;
	}


	private void setProgressBar(JProgressBar progressBar) 
	{
		this.progressBar = progressBar;
	}


}
