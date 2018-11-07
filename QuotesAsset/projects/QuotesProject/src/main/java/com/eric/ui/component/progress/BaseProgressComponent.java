package com.eric.ui.component.progress;

public abstract class BaseProgressComponent implements ProgressComponent 
{

	public abstract boolean setMinValue(int newValue);
	public abstract boolean setMaxValue(int newValue);

}
