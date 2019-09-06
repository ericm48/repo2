/**
 * 
 * Description:  These are the key types present in the CONTEXT.
 * They are either EXTERNAL (ie -D from command line, environment),
 * or INTERNAL, (ie from properties file).
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.domain.common.enumeration;

import com.eric.domain.constant.BaseConstants;


public enum PropKeyType 
{
	EXTERNAL(BaseConstants.EXTERNAL),								// External Key (ie -D from command line).
	INTERNAL(BaseConstants.INTERNAL),								// Internal Key (ie from properties file).
	NOT_SET(BaseConstants.NOT_SET); 								// Initializer.
	
	private PropKeyType(String newValue)
	{
		this.value = newValue;
	}
	
	private final String value;
	
	public String toString() 
	{
		return value;
	}		
	
}