/**
 * 
 * Description:  Utility Class for handling getting a random number.
 * 
 * @author Eric
 * 
 * 
 */
package com.eric.util;

import java.util.Random;

public class MathUtil 
{	
	public static int getRandomNumber()
	{
		int returnValue = 0;
	
		// Get A Random Number
		Random rRand = new Random();
	
		// Make it an Int
		returnValue = rRand.nextInt();
	
		// Call The Main Constructor.
	
		if ( returnValue < 0 )
		{
			returnValue *= -1;
		}

		return(returnValue);
	}	

}
