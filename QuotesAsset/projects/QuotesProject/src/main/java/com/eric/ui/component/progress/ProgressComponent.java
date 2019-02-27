/**
 * 
 * Description:  Progress Component Interface.
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.ui.component.progress;

public interface ProgressComponent 
{
	public boolean setValue(int newValue);
	public boolean setMinValue(int newValue);
	public boolean setMaxValue(int newValue);

}
