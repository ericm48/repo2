// QuotesJ.java:  Display Main Panel For Quotes App

package com.eric.controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.adapter.QuotesAdapter;
import com.eric.domain.constant.BaseConstants;
import com.eric.domain.constant.ErrorMessageConstants;
import com.eric.managers.QuoteThreadMgr;
import com.eric.ui.component.factory.ComponentWrapperFactory;
import com.eric.ui.frame.MyFrameWithExitHandling;
import com.eric.ui.holder.DialogHolder;
import com.eric.ui.listener.DialogListener;

public class QuoteGUIController extends JApplet implements ActionListener, QuoteController
{

    private static final Log methIDshowQuote;
    private static final Log methIDinit;
    private static final Log methIDinitQuoteTO; 
    private static final Log methIDupdateGUI;	
    private static final Log methIDinitGUIControls;    
    private static final Log methIDsetControlFields;
    private static final Log methIDinitQuoteMgr;
    
    static
    {
		methIDshowQuote 			= LogFactory.getLog(QuoteGUIController.class.getName() + ".showQuote()");
		methIDinit					= LogFactory.getLog(QuoteGUIController.class.getName() + ".init()");	
		methIDinitQuoteTO			= LogFactory.getLog(QuoteGUIController.class.getName() + ".initQuoteTO()");		
		methIDinitQuoteMgr			= LogFactory.getLog(QuoteGUIController.class.getName() + ".initQuoteMgr()");		
		methIDupdateGUI	 			= LogFactory.getLog(QuoteGUIController.class.getName() + ".updateGUI()");
		methIDinitGUIControls	 	= LogFactory.getLog(QuoteGUIController.class.getName() + ".initGUIControls()");		
		methIDsetControlFields	 	= LogFactory.getLog(QuoteGUIController.class.getName() + ".setControlFields()");		
    }
	
    private ChangeListener theChangeListener;

    private QuoteThreadMgr quoteMgr  				= null;
    private DialogListener dialogListener  			= null;
    private DialogHolder   dialogHolder				= null;
    private int 		   targetQuoteNumber		= 0;

    
    private JPanel        p1            			= null,
    					  p1a						= null,
    					  p1b						= null,
                          p2            			= null,
                          p2b						= null,
                          p3            			= null,
                          p3a           			= null,
                          p3b           			= null;

    private JScrollPane   jsScrollPane  			= null;

    private JTextArea     jtaDeadSpace  			= null,
                          jtaArea       			= null;

    private JLabel        jlbStatus      			= null,
                          jlbTitle       			= null,
                          jlbQuotesAppVer 			= null,
                          jlbJDK		 			= null,
                          jlbTotalQuotes 			= null;
    						
                          
    private JProgressBar  progressBar   			= null;

    private JButton       jbtEXIT       			= null;
    private JButton       jbtNEXT       			= null;

 
 
    // This Main Method enables the applet to run as an application.
    //-------------------------------------------------------------------------
    public void showQuote(int targetQuote)
    {
    	
    	Log logger = methIDshowQuote;
    	
       	logger.debug(BaseConstants.BEGINS);
    	
        // Create a Frame.
        MyFrameWithExitHandling frame = new MyFrameWithExitHandling( BaseConstants.APP_TITLE );

    	if ( targetQuote > 0 )
    	{
    		this.setTargetQuoteNumber( targetQuote );
        }

        // Add the applet instance to the frame
        frame.getContentPane().add(this, BorderLayout.CENTER);

        // Invoke init() and then start()
        this.init();
        this.start();

        // Display the frame
        frame.setSize(600, 300);
        frame.setVisible(true);

       	logger.debug(BaseConstants.ENDS);
       	
        return;
    }
    //-------------------------------------------------------------------------
    public boolean setTargetQuoteNumber(int newValue)
    {
        boolean bReturnValue = true;

        if ( newValue > 0 )
        {
           targetQuoteNumber = newValue;
        }

        return( bReturnValue );
    }
    //-------------------------------------------------------------------------
    public void init()
    {
        // Sets up GUI Panels.
    	Log logger = methIDinit;
    	String sJDK = null;
    	String sQuotesAppVersion = null;
    	
       	logger.debug(BaseConstants.BEGINS);
    	
        boolean bReturnValue       = true;

        while ( bReturnValue )
        {
        	
        	// Setup the GUI...
        	bReturnValue = initGUIControls();

        	if ( !bReturnValue )
        	{
        		break;
        	}

        	// Setup the Holder Stuff....
        	bReturnValue = initQuoteTO();

        	if ( !bReturnValue )
        	{              
        		break;
        	}

           // First Time through ONLY.  If they pass in a quote
           // number set the the target number to that.

        	if ( targetQuoteNumber > 0 )
        	{	
        		dialogHolder.getDialogListener().setTargetQuoteNumber( targetQuoteNumber );
        	}

//       		sJDK = System.getProperty(BaseConstants.JAVA_VERSION);       	
//           	logger.debug("JDK Detected: " + sJDK);
//
//        	if ( quoteTO != null )
//        	{
//        		quoteTO.getQuoteListener().setCurrentJDK(sJDK);
//        	}
        	
           	if ( !bReturnValue )
           	{
           		break;
           	}

           	bReturnValue = this.initQuoteMgr();
           
           	if ( !bReturnValue )
           	{
           		logger.error(ErrorMessageConstants.ERROR_QMGR_INIT);        	   
           		break;
           	}
           
           	bReturnValue = invokeManager();

           	if ( !bReturnValue )
           	{
           		break;
           	}

           	// Safety Purposes
           	bReturnValue = false;
           	break;
        }

        // FIX ME!!!!
        bReturnValue = this.updateGUI();

       	logger.debug(BaseConstants.ENDS);
       	
        return;
    }
    //-------------------------------------------------------------------------
    private boolean initQuoteTO()
    {
        Log logger = methIDinitQuoteTO;    	
       	logger.debug(BaseConstants.BEGINS);        
    	
        boolean       bReturnValue  = true;

        // Clear these up (from last time?)

        this.dialogHolder        	= null;
        this.dialogListener      	= null;
        this.theChangeListener   	= null;

        dialogHolder				= new DialogHolder();

        // Here Dude!        
       	// Load up Properties, Max Quotes.... 	
        //quoteListener       = new QuoteListener();

        dialogListener = QuotesAdapter.toDialogListener();
    	
        // Create the listener for the Quote Object.
        theChangeListener   = new QuoteMonitor();

        // Set to our quote reference.
        
        if ( dialogListener != null )
        {
        	dialogHolder.setQuoteListener( dialogListener );
        	dialogListener.setTargetQuoteNumber( targetQuoteNumber );
        }

        // Set to our progress bar
        if ( progressBar != null )
        {
        	
        	// Instead of directly setting this to TO object, call some
        	// form of factory w/progressBar instance to handle proper ~packaging
        	// in the form of the wrapper, then setting it into the TO as a Progress
        	// component.
        	
        	dialogHolder.setProgressComponent( ComponentWrapperFactory.getInstance().create( progressBar ));
        }

        dialogHolder.getDialogListener().setTargetQuoteNumber( targetQuoteNumber );

        // Add the Change listener to this Quote Object....
        dialogListener.addChangeListener( theChangeListener );
	        
       	logger.debug(BaseConstants.ENDS);
       	
        return( bReturnValue );
    }
    //-------------------------------------------------------------------------    
    private boolean initQuoteMgr()
    {
    	boolean returnValue = true;
    	
    	Log logger = methIDinitQuoteMgr;
       	logger.debug(BaseConstants.BEGINS);        
    	
        try
        {        
	        if ( quoteMgr == null )
	        {
	           quoteMgr = new QuoteThreadMgr( dialogHolder );
	
	           if ( quoteMgr == null )
	           {
	        	   returnValue = false;
	           }
	
	        }
	
        }
        catch (Exception ex)
        {
		    logger.error(ex.getMessage());
        }
     
       	logger.debug(BaseConstants.ENDS);        
        
    	return( returnValue );    
    }    
    //-------------------------------------------------------------------------
    private boolean initGUIControls()
    {        
        Log logger = methIDinitGUIControls;    	
       	logger.debug(BaseConstants.BEGINS);        
    	
    	boolean       bReturnValue  = true;

        // Panel p1 for holding Sub-Title ("Number: XXX")
        // Set it blank to start....
        
        p1  = new JPanel();        
        p1a = new JPanel();
        p1b = new JPanel();
       
        p1.setLayout(new BorderLayout());
        p1a.setLayout(new BorderLayout());
        p1b.setLayout(new BorderLayout());

        p1.add(p1a, BorderLayout.NORTH);
        p1.add(p1b, BorderLayout.SOUTH);        
        
        jlbTitle 				= new JLabel();
 
        p1a.add(jlbTitle, BorderLayout.NORTH);

        jlbTitle.setHorizontalAlignment(JLabel.CENTER);
 
        jlbQuotesAppVer      	= new JLabel();        
        p1a.add(jlbQuotesAppVer, BorderLayout.WEST);        

        jlbTotalQuotes            = new JLabel(); 
        p1a.add(jlbTotalQuotes, BorderLayout.EAST);
        
        jlbJDK					  = new JLabel();
        p1b.add(jlbJDK,         BorderLayout.WEST);
        
        // Panel p2 as a space holder.
        p2                        = new JPanel();
        p2.setLayout(new BorderLayout());
        
        // Panel p2b has app version
        p2b                        = new JPanel();
        p2b.setLayout(new BorderLayout());
 
        // Create the Text Area for the Quote.
        jtaArea                   = new JTextArea();
        jsScrollPane              = new JScrollPane(jtaArea);

        // Set LineWrap
        jtaArea.setLineWrap(true);

        // Set WrapStlye
        jtaArea.setWrapStyleWord(true);
 
        p3      = new JPanel();
        p3a     = new JPanel();
        p3b     = new JPanel();

        p3.setLayout(new BorderLayout());
        p3a.setLayout(new BorderLayout());
        p3b.setLayout(new FlowLayout());

        p3.add(p3a, BorderLayout.NORTH);
        p3.add(p3b, BorderLayout.SOUTH);

        // Add label Status to 3a, North

        jlbStatus = new JLabel();
        jlbStatus.setText(BaseConstants.STATUS + 
        				  BaseConstants.INITIALIZING + 
        				  BaseConstants.PERIODS);

        p3a.add(jlbStatus, BorderLayout.NORTH);

        // Add progress bar to 3a, South
        progressBar    = new JProgressBar();
        p3a.add( progressBar, BorderLayout.SOUTH );

        // Add Buttons to 3b.
        jbtEXIT   = new JButton( BaseConstants.EXIT );
        jbtNEXT   = new JButton( BaseConstants.NEXT );

        p3b.add( jbtEXIT );
        p3b.add( jbtNEXT );

        // Add panels into the applet.

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(p1,  BorderLayout.NORTH);

        // Not working
        //getContentPane().add(p2b, BorderLayout.WEST);
        
        getContentPane().add(jsScrollPane, BorderLayout.CENTER);
        getContentPane().add(p3,  BorderLayout.SOUTH);

        // Register Listeners
        jbtEXIT.addActionListener( this );
        jbtNEXT.addActionListener( this );

        if ( targetQuoteNumber > 0 )
        {
           // FIX ME!
           setControlFields();
        }

       	logger.debug(BaseConstants.ENDS); 
   
        return( bReturnValue );
    }
    //--------------------------------------------------------------------------
	private boolean invokeManager()
	{
	    boolean bReturnValue = true;
	
	    if ( quoteMgr != null )
	    {
	       bReturnValue = quoteMgr.getQuote();
	    }
	
	    return( bReturnValue );
	}
	//-------------------------------------------------------------------------
    public void actionPerformed(ActionEvent e)
    {
        boolean bReturnValue 	= true;
        boolean control			= true;

        if ( e.getSource() == jbtNEXT )
        {

           while( control )
           {
              // Set this to for a Random Selection.
              this.targetQuoteNumber 	= 0;
              this.dialogHolder			= null;              
              
              // FIX ME
              bReturnValue = this.setControlFields();

              if ( !bReturnValue )
              {
            	 control = false;
                 break;
              }

              bReturnValue = this.initQuoteTO();

              if ( !bReturnValue )
              {
            	 control = false;            	  
                 break;
              }

              quoteMgr = null;              
              quoteMgr = new QuoteThreadMgr( dialogHolder );
              
              bReturnValue = this.initQuoteMgr();              

              if ( !bReturnValue )
              {
            	 control = false;            	  
                 break;
              }
              
              bReturnValue = this.invokeManager();
              
              // Safety Purposes.
              control = false;
           }

        }
        else if ( e.getSource() == jbtEXIT )
        {
           System.exit(0);
        }

        return;
    }
    //-------------------------------------------------------------------------
    private boolean setControlFields()
    {
        Log logger = methIDsetControlFields;
       	logger.debug(BaseConstants.BEGINS);        
    	
        boolean bReturnValue  		= true;

        Integer iTemp         		= null;
        String  sMaxQ         		= null;
        String  sQNum         		= null;
        String  sJDK		  		= null;
        String  sAppVersion 		= null;
       
           int  iMaxQuotes    		= 0;

        if ( targetQuoteNumber > 0 )
        {
         
           sQNum	 = Integer.toString( targetQuoteNumber );
           
           jlbTitle.setText(BaseConstants.QUOTE_NUMBER + sQNum);
           iTemp     = null;

        }
        else
        {
           jlbTitle.setText(BaseConstants.BLANK);
        }
        
        jlbTitle.repaint();

        if (( dialogHolder != null ) && ( dialogHolder.getDialogListener() != null ))
        {	
        	
        	sMaxQ	 = Integer.toString(dialogHolder.getDialogListener().getMaxQuotes());
           
           	jlbTotalQuotes.setText(BaseConstants.TOTAL_QUOTES + 
        		   				  sMaxQ + 
        		   				  BaseConstants.BLANK);
           
           	iTemp     = null;
           	sMaxQ     = null;
           
           	sJDK = dialogHolder.getDialogListener().getCurrentJDK();       	
           	jlbJDK.setText(BaseConstants.JDK + sJDK);
           	jlbJDK.repaint();           
           
        	sAppVersion = dialogHolder.getDialogListener().getQuotesAppVersion();
        	jlbQuotesAppVer.setText(BaseConstants.VERSION + sAppVersion);
        	jlbQuotesAppVer.repaint();

            jlbTitle.repaint();  	
        	
        }
        else
        {
           jlbTotalQuotes.setText( BaseConstants.BLANK );
        }

        jlbTotalQuotes.repaint();        
        
        if ( jlbStatus != null )
        {

           if ( sQNum != null )
           {
              jlbStatus.setText(BaseConstants.STATUS + 
            		  		    BaseConstants.LOADING_QUOTE + 
            		  		    sQNum);
           }
           else
           {
              jlbStatus.setText(BaseConstants.STATUS + 
            		  	        BaseConstants.LOADING_QUOTE + 
            		  	        BaseConstants.PERIODS);
           }
           
           jlbStatus.repaint();
        }

        // Reset Progress Bar
        if ( progressBar != null )
        {
           progressBar.setValue( 0 );
        }

       	logger.debug(BaseConstants.ENDS);
       	
        return( bReturnValue );
    }
    //-------------------------------------------------------------------------
    private boolean setQuoteTextArea()
    {
        boolean bReturnValue = true;

        String  sTemp2        = null;
        
        if ( dialogListener.getQuote() != null )
        {        	
           sTemp2 = dialogListener.getQuote().getQuoteText();
        }

        // Has the Quote Text Arrived Yet?
        if ( sTemp2 != null )
        {
           if ( sTemp2.length() > 0 )
           {
              jtaArea.setText(sTemp2);
              jlbStatus.setText(BaseConstants.STATUS + BaseConstants.DONE);

              // Reset Progress Bar
              if ( progressBar != null )
              {
                progressBar.setValue( 0 );
              }
           }
           else
           {
              jtaArea.setText(BaseConstants.BLANK);
           }

        }
        else
        {
           jtaArea.setText(BaseConstants.BLANK);
        }

        jtaArea.repaint();

        return( bReturnValue );
    }
    //-------------------------------------------------------------------------
    public boolean updateGUI()
    {
        boolean bReturnValue = true;

        Log logger = methIDupdateGUI;
    	
       	logger.debug(BaseConstants.BEGINS);        
        
        while ( dialogListener != null )
        {
           setTargetQuoteNumber( dialogListener.getTargetQuoteNumber() );

           bReturnValue = setControlFields();

           if ( !bReturnValue )
           {
              break;
           }

           bReturnValue = setQuoteTextArea();

           if ( !bReturnValue )
           {
              break;
           }

           break;

        }

       	logger.debug(BaseConstants.ENDS);
        
        return( bReturnValue );
    }

    //============================================================================
    // INNER Class QuoteMonitor
    //============================================================================
    class QuoteMonitor implements ChangeListener
    {

       public void stateChanged(ChangeEvent e)
       {
           // Update the GUI Controls!
           updateGUI();

           return;
       }

    }
   
}

