/*
 * Created on 09.06.2005 / 12:53:36
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.test.prototyp;

import javax.swing.JFrame;

import javax.swing.JButton;
/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class ToolTipTest extends JFrame {

	private javax.swing.JPanel jContentPane = null;
	private JButton jButton = null;
	/**
	 * This is the default constructor
	 */
	public ToolTipTest() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300,200);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJButton(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
		}
		jButton.setToolTipText(
				"<html>"
					+ "&#8226; This is a<br><hr>"
					+ "&#8226; tool tip<br><br><br>"+
				"</html>");;
		return jButton;
	}
 }
