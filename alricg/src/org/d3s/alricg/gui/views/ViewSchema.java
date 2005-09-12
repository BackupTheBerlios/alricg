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

/**
 * <u>Beschreibung:</u><br> 
 * Dient den SortableTreeTable und SortableTables als Vorlage f�r die Darstellung.
 * Alle speziellen Methoden f�r die Anzeige von Elementen werden in diesen Schemas
 * zusammengefasst.
 * @author V. Strelow
 */
public interface ViewSchema {
	String buttonValue = "button";
	NamensComparator namensComparator = new NamensComparator();
	
	/**
	 * Wichtig f�r casting und ob Sammelbegriffe m�glich sind
	 * @return true: Die Elemente dieses Schemas sind CharElemente, sonst false
	 */
	public boolean hasSammelbegriff();
	
	/**
	 * Wichtig f�r casting und ob Varianten m�glich sind
	 * @return true:Die Elemente dieses Schemas sind Herkunft, sonst false
	 */
	public boolean isHerkunft();
	
	/**
	 * Dies ist die eigentliche Methode, mit der Werte abgerufen werden! 
	 * @param object Das Objekt welches eigentlich dargestellt wird (Talent,
	 * 		Rasse, Zauber, Link, usw.)
	 * @param column Die "Spalte" welche aus diesem Objekt gefragt ist (Talent.sorte,
	 * 		Rasse.gp, Zauber.merkmale, usw.)
	 * @return Ein Objekt mit dem Entsprechendem Wert
	 */
	public Object getCellValue(Object object, Object column);
	

	/**
	 * Setzt einen Wert neu an einer bestimmten Position der Tabelle.
	 * @param object Das Objekt welches eigentlich dargestellt wird (Talent,
	 * 		Rasse, Zauber, Link, usw.)
	 * @param column Die "Spalte" welche aus diesem Objekt gefragt ist (Talent.sorte,
	 * 		Rasse.gp, Zauber.merkmale, usw.)
	 * @param newValue Der Neue Wert an dieser Stelle
	 */
	public void setCellValue(Object newValue, Object object, Object column);
	
	/**
	 * Pr�ft ob eine Tabellen Zelle editiert werden kann.
	 * @param object Das Objekt welches eigentlich dargestellt wird (Talent,
	 * 		Rasse, Zauber, Link, usw.)
	 * @param column Die "Spalte" welche aus diesem Objekt gefragt ist (Talent.sorte,
	 * 		Rasse.gp, Zauber.merkmale, usw.)
	 */
	public boolean isCellEditable(Object object, Object column);
	
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
	 * Liefert den ToolTipText f�r ein Element in der Tabelle
	 * @param object Das Object, �ber dem der Mauszeiger steht
	 * @param column Die Spalte, �ber dem der Mauszeiger steht
	 * @return Liefert den ToolTip Text f�r das Objekt und die Spalte
	 */
	public String getToolTip(Object object, Object column);
	
	/**
	 * Liefert den ToolTipText f�r eine Tabellen �berschrift
	 * @param column Die Spalte auf dessen Titel der Mauszeiger steht
	 * @return Der ToolTipText f�r den Titel dieser Spalte 
	 */
	public String getHeaderToolTip(Object column);
	
	/**
	 * Liefert die Elemente nach denen die TreeTable geordnet werden kann 
	 * (Also nach denen die Elemente der TreeTable in Ordner angeordnet werden).
	 * Das Element "Keine" gibt es immer, wird jedoch durch das Panel hinzugef�gt,
	 * taucht hier also NICHT auf!
	 * Beispiel: "Sorte" bei Talenten
	 * @return Die Elemente zum Ordnen der TreeTable (ohne "Keine")
	 */
	public Enum[] getOrdnungElem();
	
	/**
	 * Liefert die Elemente nach denen die TreeTable gefiltert werden kann. 
	 * Bestimmte Elementen sollen je nach Filter nicht angezeigt werden.
	 * Das Elemente "Keiner" gibt es immer, es geh�rt mit zu dem Elementen 
	 * die hier zur�ckgeliefert werden!
	 * Beispiel: "Nur W�hlbare"
	 * @return Die Elemente zum Filtern der TreeTable (mit "Keiner")
	 */
	public Enum[] getFilterElem();
	
	/**
	 * Wird von "sortiereNachOrdnern(...)" benutzt. Liefert ein Array von Objekten,
	 * nach denen der Tree sortiert und geordnet werden soll. 
	 * Die Methode "getOrdinalFromElement(...)" mu� mit dieser Methode abgestimmt sein.
	 * 
	 * @return Ein Array von Objekten nach denen der Tree geordnent wird, oder null
	 * 		wenn nach NICHT geordnet wird
	 */
	public Object[] getSortOrdner();
	
	/**
	 * Wird von "sortiereNachOrdnern(...)" benutzt. Liefert zu den �bergebenen 
	 * elementen die Ordinal-Zahl des betreffenden Elements zur�ck.
	 * Wichtig ist, das der zur�ckgelieferte Index mit dem Index der Methode
	 * "getSortOrdner()" abgestimmt ist.
	 * WICHTIG: Wenn nicht geordnet werden soll, so gibt diese Methode
	 * stehts die Zahl "0" zur�ck.
	 * 
	 * @param element Das Element zu dem eine Ordner herausgesucht werden soll und
	 * 		dessen ordinal-Zahl zur�ckgeliefert wird
	 * @return Die gew�nschte ordinal-Zahl des Elements bzw. "0" wenn nicht nach
	 * 		Elementen geordnet wird.
	 */
	public int[] getOrdinalFromElement(Object element);
	
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