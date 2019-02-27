/**
 * 
 * Description:  Component Factory Interface.
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.ui.component.factory;

import javax.swing.JProgressBar;

import com.eric.ui.component.progress.JProgressBarImpl;

public interface ComponentFactory 
{
	public JProgressBarImpl 	create(JProgressBar newValue);
}
