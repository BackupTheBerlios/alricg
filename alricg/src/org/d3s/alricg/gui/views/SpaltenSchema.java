/*
 * Created on 08.04.2005 / 00:23:01
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */
package org.d3s.alricg.gui.views;

import java.util.Comparator;

import org.d3s.alricg.gui.komponenten.table.SortableTable;

/**
 * <u>Beschreibung:</u><br> 
 * Dient den SortableTreeTable und SortableTables als Vorlage für die Darstellung.
 * In diesem Schema werden spezielle Methoden für die Anzeige von Elementen  
 * zusammengefasst, und zwar solche Methoden die nur mit den Spalten der Tabelle, nicht mit
 * den Elementen in der Tabelle zusammenhängen. Da jedes CharElement andere Spalten benötigt,
 * wird für jedesCharElemnt ein Spaltenschema benötigt.
 * In jeder Implementierung des Spaltenschemas wird per Enum angegeben, welche Spalten zu
 * dem CharElement existieren. Per "getSpalten" werden dann jeweils die passenden 
 * Spalten geliefert.
 * @author V. Strelow
 */
public interface SpaltenSchema {
	String buttonValue = "button";
	NamensComparator namensComparator = new NamensComparator();
	
	public enum SpaltenArt {
		// Wenn die Objekte direkt angezeigt werden z.B. "Talente"
		objektDirekt, 
		
		// Wenn "GeneratorLinks" mit den Objekten angezeigt werden, für bereist gewählte Links
		// bei der Generierung
		objektLinkGen, 
		
		// Wenn "HeldenLinks" mit den Objekten angezeigt werden, für bereits gewählte Links
		// bei dem Management von Helden
		objektLinkHel, 
		
		// Für den Editor (auch objektDirekt, aber mit anderen Spalten)
		editorAuswahl,
		editorGewaehlt;
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
	 * die Renderer, Editoren, Bilder und ähnliches für die entsprechenden Spalten
	 * gesetzt.
	 * @param table Die Table/ TreeTable die vorbereitet werden soll
	 * @param art Die Art der darzustellenden Spalten
	 */
	public void initTable(SortableTable table, SpaltenArt art);
	
	/**
	 * Um Tablen nach verschiedenen Spalten sortieren zu können, muß für 
	 * jede Spalte ein Comparator verfügbar sein. Dieser Comparator
	 * wird hiermit geliefert
	 * @param column Die Spalte, über die sortiert werden soll
	 * @return Ein Comparator, mit dem Elemente nach der Spalte sortiert werden
	 * 		können
	 */
	public Comparator getComparator(Object column);
	
	/**
	 * Gibt zurück, ob nach einer Spalte sortiert werden kann, also dafür 
	 * entsprechende Pfeile angezeigt werden!
	 * @param column Die Spalte nach der Sortiert werden soll
	 * @return true: Nach dieser Spalte kann Sortiert werden, sonst false
	 */
	public boolean isSortable(Object column);
		
	/**
	 * Liefert den ToolTipText für eine Tabellen Überschrift
	 * @param column Die Spalte auf dessen Titel der Mauszeiger steht
	 * @return Der ToolTipText für den Titel dieser Spalte 
	 */
	public String getHeaderToolTip(Object column);
	
	/**
	 * <u>Beschreibung:</u><br> 
	 * Comparator um Namen von CharElementen in einer TreeTable miteinander 
	 * vergleichen zu können. Es können sowohl Strings als auch Unterklassen von 
	 * "CharElement" verglichen werden.
	 * Vorteil: Es können auch Strings (wie von Ordnern in TreeTable) und CharElemente
	 * miteinander verglichen werden.
	 * @author V. Strelow
	 */
	public class NamensComparator implements Comparator {

		/* (non-Javadoc) Methode überschrieben
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Object arg0, Object arg1) {
			
			return arg0.toString().compareTo(arg1.toString());
			
			/*String str1, str2;
			
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
			
			return str1.compareTo(str2);*/
		}
		
	}

}