/*
 * Created on 08.04.2005 / 00:23:01
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */
package org.d3s.alricg.gui.views;

import java.util.Comparator;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.gui.komponenten.table.SortableTable;

/**
 * <u>Beschreibung:</u><br> 
 * Dient den SortableTreeTable und SortableTables als Vorlage f�r die Darstellung.
 * Alle speziellen Methoden f�r die Anzeige von Elementen werden in diesen Schemas
 * zusammengefasst.
 * @author V. Strelow
 */
public interface SpaltenSchema {
	String buttonValue = "button";
	NamensComparator namensComparator = new NamensComparator();
	
	public enum SpaltenArt {
		objektDirekt, // Wenn die Objekte direkt angezeigt werden z.B. "Talente"
		objektLink, // Wenn Links mit den Objekten angezeigt werden
		editor; // F�r den Editor (auch direkt Objekte)
	}
	
	/**
	 * Liefert ein Enum mit den Spalten die in einer Table/ TreeTable von der 
	 * entsprechenden Art von Spalten angezeigt werden.
	 * @param art Die Art der Spalten
	 * @return Ein Array mit den Enums aller anzuzeigenden Spalten
	 */
	public Enum[] getSpalten(SpaltenArt art);
	
	/**
	 * Bereitet eine Tabelle (auch TreeTable) auf die Benutzung vor. Dabei werden
	 * die Renderer, Editoren, Bilder und �hnliches f�r die entsprechenden Spalten
	 * gesetzt.
	 * @param table Die Table/ TreeTable die vorbereitet werden soll
	 * @param art Die Art der darzustellenden Spalten
	 */
	public void initTable(SortableTable table, SpaltenArt art);
	
	/**
	 * Um Tablen nach verschiedenen Spalten sortieren zu k�nnen, mu� f�r 
	 * jede Spalte ein Comparator verf�gbar sein. Dieser Comparator
	 * wird hiermit geliefert
	 * @param column Die Spalte, �ber die sortiert werden soll
	 * @return Ein Comparator, mit dem Elemente nach der Spalte sortiert werden
	 * 		k�nnen
	 */
	public Comparator getComparator(Object column);
	
	/**
	 * Gibt zur�ck, ob nach einer Spalte sortiert werden kann, also daf�r 
	 * entsprechende Pfeile angezeigt werden!
	 * @param column Die Spalte nach der Sortiert werden soll
	 * @return true: Nach dieser Spalte kann Sortiert werden, sonst false
	 */
	public boolean isSortable(Object column);
		
	/**
	 * Liefert den ToolTipText f�r eine Tabellen �berschrift
	 * @param column Die Spalte auf dessen Titel der Mauszeiger steht
	 * @return Der ToolTipText f�r den Titel dieser Spalte 
	 */
	public String getHeaderToolTip(Object column);
	
	/**
	 * <u>Beschreibung:</u><br> 
	 * Comparator um Namen von CharElementen in einer TreeTable miteinander 
	 * vergleichen zu k�nnen. Es k�nnen sowahl Strings als auch unterklassen von 
	 * "CharElement" verglichen werden.
	 * @author V. Strelow
	 */
	public class NamensComparator implements Comparator {

		/* (non-Javadoc) Methode �berschrieben
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Object arg0, Object arg1) {
			String str1, str2;
			
			if (arg0 instanceof String) {
				str1 = (String) arg0;
			} else {
				str1 = ((CharElement) arg0).getName();
			}
			
			if (arg1 instanceof String) {
				str2 = (String) arg1;
			} else {
				str2 = ((CharElement) arg1).getName();
			}
			
			return str1.compareTo(str2);
		}
		
	}

}