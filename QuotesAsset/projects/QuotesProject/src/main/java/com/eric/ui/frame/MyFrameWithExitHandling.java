
// Frame 2 w/Exit Handling

package com.eric.ui.frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class MyFrameWithExitHandling extends JFrame implements WindowListener
{

      // Default Constructor
      //-----------------------------------------------------------
      public MyFrameWithExitHandling()
      {
         super();
         addWindowListener(this);   // Register Listener
         return;
      }

      // Constructor with Frame & Title
      //-----------------------------------------------------------
      public MyFrameWithExitHandling(String title)
      {
         super(title);
         addWindowListener(this);
         return;
      }

      // Center Method
      //-----------------------------------------------------------
      public void center()
      {

         // Get the Screen Size

         int screenWidth,
             screenHeight,
             x,
             y;

         Dimension screenSize    = Toolkit.getDefaultToolkit().getScreenSize();
         Dimension frameSize     = this.getSize();

         screenWidth             = screenSize.width;
         screenHeight            = screenSize.height;

         x = ((screenWidth  - frameSize.width)  / 2);
         y = ((screenHeight - frameSize.height) / 2);

         // Determine Top Left.

         if ( x < 0 )
         {
            x = 0;
            frameSize.width = screenWidth;
         }

         if ( y < 0 )
         {
            y = 0;
            frameSize.height = screenHeight;
         }

         this.setLocation(x, y);

         return;
      }

      // Handler For Window Closed Event
      public void windowClosed(WindowEvent event)
      {
         return;
      }

      // Handler For Window Deiconified Event
      public void windowDeiconified(WindowEvent event)
      {
         return;
      }

      // Handler For WindowIconified Event
      public void windowIconified(WindowEvent event)
      {
         return;
      }

      // Handler For Window Activated Event
      public void windowActivated(WindowEvent event)
      {
         return;
      }

      // Handler For Window DeActivated Event
      public void windowDeactivated(WindowEvent event)
      {
         return;
      }

      // Handler For Window Opened Event
      public void windowOpened(WindowEvent event)
      {
         return;
      }

      // Handler For Window Closing Event
      public void windowClosing(WindowEvent event)
      {
         dispose();
         System.exit(0);
      }


}