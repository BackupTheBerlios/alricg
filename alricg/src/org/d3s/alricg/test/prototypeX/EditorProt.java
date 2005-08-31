/*
 * Created on 25.03.2005 / 13:41:31
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.test.prototypeX;

import javax.swing.JFrame;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class EditorProt extends JFrame {

	private javax.swing.JPanel jContentPane = null;
	/**
	 * This is the default constructor
	 */
	public EditorProt() {
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
		}
		return jContentPane;
	}
}
