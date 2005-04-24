/*
 * Created on 07.04.2005 / 19:59:20
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui.komponenten.table;

import javax.swing.table.TableModel;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public interface SortableTableModelInterface extends TableModel {

	public abstract boolean isSortable(int column);

	public abstract void sortTableByColumn(int column);

	public abstract boolean isSortColumnDesc(int column);

	/**
	 * Liefert den ToolTip Text f�r die Zeile "row" und Spalte "column".
	 * Der ToolTip Text kann somit vom Wert der Zeile/ Spalte abh�ngen
	 * @param row Die Zeile des gew�nschten ToolTip-Textes
	 * @param column Die Spalte des gew�nschten ToolTip-Textes
	 * @return Der ToolTip Text an der Stelle row/ column
	 */
	public abstract String getToolTip(int row, int column);

	public abstract String getHeaderToolTip(int column);
}