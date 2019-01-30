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
public enum QuoteInputFileType 
{
	INTERNAL(BaseConstants.INTERNAL),								// Internal aboard .jar file.
	EXTERNAL(BaseConstants.EXTERNAL),								// External File.
	NOT_SET(BaseConstants.NOT_SET); 								// Initializer.
	
	private QuoteInputFileType(String newValue)
	{
		this.value = newValue;
	}
	
	private final String value;
	
	public String toString() 
	{
		return value;
	}		
	
}