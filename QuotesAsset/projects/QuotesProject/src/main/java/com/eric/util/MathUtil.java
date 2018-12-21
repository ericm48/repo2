package com.eric.util;

import java.util.Random;

public class MathUtil 
{	
	public static int getRandomNumber()
	{
		int iThisRand;
	
		// Get A Random Number
		Random rRand = new Random();
	
		// Make it an Int
		iThisRand = rRand.nextInt();
	
		// Call The Main Constructor.
	
		if ( iThisRand < 0 )
		{
		    iThisRand *= -1;
		}

		return(iThisRand);
	}	

}
