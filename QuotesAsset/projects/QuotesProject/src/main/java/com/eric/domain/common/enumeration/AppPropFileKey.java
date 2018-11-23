/**
 * @author Eric
 */
package com.eric.domain.common.enumeration;

import com.eric.domain.constant.BaseConstants;


/**
 * 
 * Description:  These are the keys that are required to be present in the CONTEXT as they
 * relate to CryptoCommand.
 * 
 * @author Eric
 * 
 * 
 */
public enum AppPropFileKey 
{
	EXTERNAL(BaseConstants.EXT_PROP_FILE),								// External File.
	INTERNAL(BaseConstants.INT_PROP_FILE),								// Internal aboard .jar file.
	NOT_SET(BaseConstants.NOT_SET); 									// Initializer.
	
	private AppPropFileKey(String newValue)
	{
		this.value = newValue;
	}
	
	private final String value;
	
	public String toString() 
	{
		return value;
	}		
	
}