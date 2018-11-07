package com.eric.ui.component.factory;

import javax.swing.JButton;
import javax.swing.JProgressBar;

import com.eric.ui.component.progress.JProgressBarImpl;

public interface ComponentFactory 
{
	public JProgressBarImpl 	create(JProgressBar newValue);
}
