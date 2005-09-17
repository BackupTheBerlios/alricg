/*
 * Created on 07.04.2005 / 17:57:24
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui.komponenten.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.table.AbstractTableModel;

import org.d3s.alricg.gui.views.SpaltenSchema;

/**
 * <u>Beschreibung:</u><br> 
 * Dient als Model zur Darstellung von Daten in einer JTable (SortableTable). 
 * In dieser Klasse werden alle Grundfunktionen dafür gehalten, um auf die Daten zu 
 * zugreifen und diese zu verwalten.
 * Die spezifischen Funktionen, die von der Art der Daten (Talent, Zauber, Rasse, usw.)
 * abhängen, werden in einem Schema gehalten. Während dieses Modell speziell für die
 * Darstellung in einer JTable gemacht ist, ist das Schema unabhängig von der Art der
 * Darstellung und kann somit auch für die TreeTable verwendet werden.
 * 
 * @author V. Strelow
 */
public class SortableTableModel<E> extends AbstractTableModel {
	private ArrayList<E> dataList = new ArrayList<E>();
	private Enum[] columns;
	private SpaltenSchema schema;
	private boolean[] lastAscSorted;
	
	public SortableTableModel(SpaltenSchema schema, Enum[] columns) {
		this.columns = columns;
		this.schema = schema;
		lastAscSorted = new boolean[columns.length];
		
		Arrays.fill(lastAscSorted, false); // Damit überall ein Wert steht
	}
	
	/**
	 * Setzt die Elemente des DatenModells
	 * @param elemListe Liste von allem Elementen die die Tabelle anzeigen soll
	 */
	public void setData(ArrayList<E> elemListe) {
		dataList = elemListe;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return dataList.size();
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columns.length;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int colIdx) {
		return columns[colIdx].toString();
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.komponenten.table.SortableTableModelInterface#getHeaderToolTip(int)
	 */
	public String getHeaderToolTip(int colIdx) {
		return schema.getHeaderToolTip(columns[colIdx]);
	}
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.komponenten.table.SortableTableModelInterface#getToolTip(int, int)
	 */
	public String getToolTip(int rowIdx, int colIdx) {
		return schema.getToolTip(dataList.get(rowIdx), columns[colIdx]);
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIdx, int colIdx) {
		return schema.getCellValue(dataList.get(rowIdx), columns[colIdx]);
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object aValue, int rowIdx, int colIdx) {
		schema.setCellValue(aValue, dataList.get(rowIdx), columns[colIdx]);
	}
 
	/* (non-Javadoc) Methode überschrieben
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIdx, int colIdx) {
		return schema.isCellEditable(dataList.get(rowIdx), columns[colIdx]);
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.komponenten.table.SortableTableModelInterface#sortTableByColumn(int)
	 */
	public void sortTableByColumn(int colIdx) {
		Collections.sort(dataList, schema.getComparator(columns[colIdx]));
		
		// Somit wird beim zweiten klick die Reihenfolge vertauscht
		if ( lastAscSorted[colIdx]) {
			Collections.reverse(dataList);
			lastAscSorted[colIdx] = false;
		} else {
			lastAscSorted[colIdx] = true;
		}
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.komponenten.table.SortableTableModelInterface#isSortable(int)
	 */
	public boolean isSortable(int colIdx) {
		return schema.isSortable(columns[colIdx]);
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.komponenten.table.SortableTableModelInterface#isSortColumnDesc(int)
	 */
	public boolean isSortColumnDesc(int colIdx) {
		return !lastAscSorted[colIdx];
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.gui.komponenten.table.SortableTableModelInterface#getViewSchema()
	 */
	public SpaltenSchema getViewSchema() {
		return schema;
	}
	
}



