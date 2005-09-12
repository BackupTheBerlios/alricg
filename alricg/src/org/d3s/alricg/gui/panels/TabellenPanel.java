/*
 * Created on 12.09.2005 / 17:32:17
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.d3s.alricg.gui.komponenten.table.AbstractTreeTableModel;
import org.d3s.alricg.gui.komponenten.table.SortableTable;

/**
 * <u>Beschreibung:</u><br> 
 * Dieses Panel dient der Anzeige aller "standart" Tabellen mit 
 * CharElementen (Talente, Zauber, Professionen, SF, usw.). 
 * @author V. Strelow
 */
public class TabellenPanel extends JPanel {

	private JPanel optionsPanel = null;
	private JLabel lblOrdnung = null;
	private JComboBox cbxOrdnung = null;
	private JLabel lblFilter = null;
	private JComboBox cbxFilter = null;
	private JScrollPane scpTabelle = null;
	private SortableTable tblMainTabelle = null;
	
	/**
	 * This is the default constructor
	 */
	public TabellenPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(300, 200);
		this.add(getOptionsPanel(), java.awt.BorderLayout.NORTH);
		this.add(getScpTabelle(), java.awt.BorderLayout.CENTER);
	}

	
	public void setTable(SortableTable table) {
		tblMainTabelle = table;
		scpTabelle.setViewportView(tblMainTabelle);
		
	}
	
	
	/**
	 * This method initializes optionsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getOptionsPanel() {
		if (optionsPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			lblFilter = new JLabel();
			lblFilter.setText("JLabel");
			lblOrdnung = new JLabel();
			lblOrdnung.setText("JLabel");
			optionsPanel = new JPanel();
			optionsPanel.setLayout(flowLayout);
			optionsPanel.add(lblOrdnung, null);
			optionsPanel.add(getCbxOrdnung(), null);
			optionsPanel.add(lblFilter, null);
			optionsPanel.add(getCbxFilter(), null);
		}
		return optionsPanel;
	}

	/**
	 * This method initializes cbxOrdnung	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getCbxOrdnung() {
		if (cbxOrdnung == null) {
			cbxOrdnung = new JComboBox();
		}
		return cbxOrdnung;
	}

	/**
	 * This method initializes cbxFilter	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getCbxFilter() {
		if (cbxFilter == null) {
			cbxFilter = new JComboBox();
		}
		return cbxFilter;
	}

	/**
	 * This method initializes scpTabelle	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getScpTabelle() {
		if (scpTabelle == null) {
			scpTabelle = new JScrollPane();
			//scpTabelle.setViewportView(getTblMainTabelle());
		}
		return scpTabelle;
	}

	/*
	 * This method initializes tblMainTabelle	
	 * 	
	 * @return javax.swing.JTable	
	 *  
	private JTable getTblMainTabelle() {
		if (tblMainTabelle == null) {
			tblMainTabelle = new JTable();
		}
		return tblMainTabelle;
	}*/

}
