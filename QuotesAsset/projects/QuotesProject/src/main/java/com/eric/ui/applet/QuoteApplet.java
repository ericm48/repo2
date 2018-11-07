
package com.eric.ui.applet;

import java.applet.Applet;
import java.awt.Graphics;
              
public class QuoteApplet extends Applet 
{


	int iPaintCount = 0,	
	    iInitCount  = 0,
	    iStartCount = 0,
	    iStopCount  = 0;


//--------------------------------------------------------------------------
public void paint(Graphics g) 
{
   	
   iPaintCount++;
   	
   g.drawString("HelloXX world!!", 50, 25);   	

   System.out.println("");
   System.out.println("Counts Display");
      
   System.out.println("");
      
   System.out.println("Paint Count: " + this.iPaintCount);
   System.out.println("Start Count: " + this.iStartCount);
   System.out.println(" Stop Count: " + this.iStopCount);
   System.out.println(" Init Count: " + this.iInitCount);  	
      
   return;   	
   	
}
//--------------------------------------------------------------------------
public void init() 
{ 

   iInitCount++;

   iPaintCount = 0;		   
   iStartCount = 0;
   iStopCount  = 0;

   return;
    
} 
//--------------------------------------------------------------------------  
public void start() 
{ 

   iStartCount++;
    
   return;
}
//--------------------------------------------------------------------------  	
public void stop() 
{ 

   iStopCount++;
		
   return;
    
}
//--------------------------------------------------------------------------
  
}