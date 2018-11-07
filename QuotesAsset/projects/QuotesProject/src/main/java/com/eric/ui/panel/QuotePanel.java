

package com.eric.ui.panel;
import java.awt.BorderLayout;
import java.awt.Panel;

import javax.swing.JLabel;

import com.eric.controller.QuoteTextController;



class QuotePanel extends Panel 
{

    // Header title of the quote panel

    private JLabel jlbTitle;

    public QuoteTextController qc = null;

    // Individual clock resume and suspend control buttons
    // private JButton jbtResume, jbtSuspend;

    // Constructor
    public QuotePanel()
    {
        int iThisOne = 0; //545;

        if ( iThisOne > 0 )
        {
        	qc = new QuoteTextController(iThisOne);
        }
        else
        {
        	qc = new QuoteTextController();
        }

        // Set Borderlayout for the QuotePanel
        setLayout(new BorderLayout());

        // Add title label to the north of the panel.
        add(jlbTitle = new JLabel(), BorderLayout.NORTH);
        jlbTitle.setHorizontalAlignment(JLabel.CENTER);

        return;
    }

    public void setTitle(String sTitle)
    {
        jlbTitle.setText(sTitle);
        return;
    }
  

}
