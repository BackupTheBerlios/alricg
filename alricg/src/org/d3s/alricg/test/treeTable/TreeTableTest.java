/*
 * Created on 16.03.2005 / 00:16:16
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.test.treeTable;

import javax.swing.JScrollPane;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class TreeTableTest extends JScrollPane {

	/**
	 * This is the default constructor
	 */
	public TreeTableTest() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		this.setSize(300,200);
	}
}
