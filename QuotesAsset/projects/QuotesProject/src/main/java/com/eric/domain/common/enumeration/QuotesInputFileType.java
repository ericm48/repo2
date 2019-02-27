/**
 * 
 * Description:  These are the keys that are required to be present in the CONTEXT as they
 * relate to Application Property  File (Internal or External).
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.domain.common.enumeration;

import com.eric.domain.constant.BaseConstants;


/**
 * 
 * Description:  These are the keys that are required to be present in the CONTEXT as they
 * relate to QuotesInputFile Type (Internal or External).
 * 
 * @author Eric
 * 
 * 
 */
public enum QuotesInputFileType 
{
	INTERNAL(BaseConstants.INTERNAL),								// Internal aboard .jar file.
	EXTERNAL(BaseConstants.EXTERNAL),								// External File.
	NOT_SET(BaseConstants.NOT_SET); 								// Initializer.
	
	private QuotesInputFileType(String newValue)
	{
		this.value = newValue;
	}
	
	private final String value;
	
	public String toString() 
	{
		return value;
	}		
	
}