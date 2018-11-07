package com.eric.ui.component.factory;

import javax.swing.JProgressBar;
import javax.swing.JComponent;

import com.eric.ui.component.progress.JProgressBarImpl;
import com.eric.ui.component.progress.ProgressComponent;

// Make this a delegate-factory-container class ???  Needs NEW I/F 4 contract behavior.

// That (via generics) can instantiate the correct impl (no "instanceof") which 
// implemented the progress I/F that contains an instance of the JProgressBar (JProgressBarImpl).
// Create another concrete instance that wraps that control.

public class ComponentWrapperFactory extends BaseComponentFactory
{
	
	private static ComponentWrapperFactory instance = new ComponentWrapperFactory(); 
	
	private ComponentWrapperFactory()
	{		
	}
	
	public static ComponentWrapperFactory getInstance()
	{
		return( instance );
	}
		
	public JProgressBarImpl create(JProgressBar newValue)
	{
		JProgressBarImpl returnValue = null;
		
		
		//super.create((JComponent)newValue);
		
		if ( newValue != null )
		{
				returnValue = new JProgressBarImpl( newValue );
		}
		else
		{
			// OR Throw some exception!				
		}			
		
		return( returnValue );
	}	

}
