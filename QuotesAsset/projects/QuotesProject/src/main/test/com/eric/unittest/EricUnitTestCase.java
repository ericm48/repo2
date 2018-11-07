/*
 *
 * $Name: EricUnitTestCase.java $
 * $Revision: 
 * $Date: 2008-03-11 14:59:39 GMT $
 * $Author: Eric Manley $
 */
package com.eric.unittest;


import junit.framework.TestCase;

import ab.erc.logger.Level;
import ab.erc.logger.Logger;
import ab.erc.logger.LoggerFactory;


/**
 * A generic base class for Geo Junit test cases.
 * 
  */
public class EricUnitTestCase extends TestCase 
{
	private static Logger methIDSetFailON;

	private boolean failON	= true;

	private final static String SET_FAILON								= "Setting FAILON To: %s";

	
	static 
	{
		methIDSetFailON		 											= LoggerFactory.getLogger(EricUnitTestCase.class.getName() +".setFailON()");		
	}

	public EricUnitTestCase() 
	{
		super();
	}

	/**
	 * @param arg0
	 */
	public EricUnitTestCase(String arg0)
	{
		super(arg0);
	}

	public boolean isFailON() 
	{
		return failON;
	}

	public void setFailON(boolean failON) 
	{
		Logger logger 		= methIDSetFailON;				
		String lineItem 	= null;
		
		lineItem 			= String.format(this.SET_FAILON, failON);
		
		logger.info( lineItem );
		
		this.failON = failON;
		
		return;
	}
	
	/**
	 * Creates an initial context for JNDI using the in memory database
	 */
	protected void setUp() throws Exception
	{
		super.setUp();
	}
	
	public void testFake()
	{	
		return;
	}
	
}
