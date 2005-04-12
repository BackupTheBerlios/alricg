/*
 * Created on 08.04.2005 / 00:23:01
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */
package org.d3s.alricg.GUI.views;

import java.util.Comparator;

/**
 * <u>Beschreibung:</u><br> 
 * Dient den SortableTreeTable und SortableTables als Vorlage für die Darstellung.
 * Alle speziellen Methoden für die Anzeige von Elementen werden in diesen Schemas
 * zusammengefasst.
 * @author V. Strelow
 */
public interface ViewSchema {
	public static final int KEIN_BUTTON = 0;
	public static final int PLUS_BUTTON = 1;
	public static final int MINUS_BUTTON = 2;
	
	
	/**
	 * Wichtig für casting und ob Sammelbegriffe möglich sind
	 * @return true: Die Elemente dieses Schemas sind CharElemente, sonst false
	 */
	public boolean hasSammelbegriff();
	
	/**
	 * Wichtig für casting und ob Varianten möglich sind
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
	public Object getCellValue(Object object, Enum column);
	
	/**
	 * Um Tablen nach verschiedenen Spalten sortieren zu können, muß für 
	 * jede Spalte ein Comparator verfügbar sein. Dieser Comparator
	 * wird hiermit geliefert
	 * @param column Die Spalte, über die sortiert werden soll
	 * @return Ein Comparator, mit dem Elemente nach der Spalte sortiert werden
	 * 		können
	 */
	public Comparator getComparator(Enum column);
	
	/**
	 * Gibt zurück, ob nach einer Spalte sortiert werden kann, also dafür 
	 * entsprechende Pfeile angezeigt werden!
	 * @param column Die Spalte nach der Sortiert werden soll
	 * @return true: Nach dieser Spalte kann Sortiert werden, sonst false
	 */
	public boolean isSortable(Enum column);
	
	/**
	 * Liefert den ToolTipText für ein Element in der Tabelle
	 * @param object Das Object, über dem der Mauszeiger steht
	 * @param column Die Spalte, über dem der Mauszeiger steht
	 * @return Liefert den ToolTip Text für das Objekt und die Spalte
	 */
	public String getToolTip(Object object, Enum column);
	
	/**
	 * Liefert den ToolTipText für eine Tabellen Überschrift
	 * @param column Die Spalte auf dessen Titel der Mauszeiger steht
	 * @return Der ToolTipText für den Titel dieser Spalte 
	 */
	public String getHeaderToolTip(Enum column);
	
	/**
	 * Wird von "sortiereNachEnum(...)" benutzt. Liefert ein Array von Enums, nach
	 * denen der Tree sortiert und geordnet werden soll. 
	 * Die Methode "getOrdinalFromElement(...)" muß mit dieser Methode abgestimmt sein.
	 * 
	 * @return Ein Array von Enums nach denen der Tree geordnent wird, oder null
	 * 		wenn nach NICHT nach Enums geordnet wird
	 */
	public Enum[] getSortEnums();
	
	/**
	 * Wird von "sortiereNachEnum(...)" benutzt. Liefert zu den übergebenen 
	 * elementen die Ordinal-Zahl des betreffenden enums zurück.
	 * Die Methode "getEnums()" muß mit dieser Methode abgestimmt sein.
	 * WICHTIG: Wenn nicht das Enums geordnet werden soll, so gibt diese Methode
	 * stehts die Zahl "0" zurück.
	 * 
	 * @param element Das Element zu dem eine enum herausgesucht werden soll und
	 * 		dessen ordinal-Zahl zurückgeliefert wird
	 * @return Die gewünschte ordinal-Zahl des Elements bzw. "0" wenn nicht nach
	 * 		Elementen geordnet wird.
	 */
	public int getOrdinalFromElement(Object element);
}
