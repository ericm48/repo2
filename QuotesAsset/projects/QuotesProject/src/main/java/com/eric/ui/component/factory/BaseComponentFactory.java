/**
 * 
 * Description:  Base Implementation of Factory for Swing Components
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.ui.component.factory;

import javax.swing.JComponent;

public abstract class BaseComponentFactory implements ComponentFactory 
{
	public JComponent create(JComponent newValue)
	{
		// Place holder method for JComponent Level.
		return( newValue );
	}
	
}
