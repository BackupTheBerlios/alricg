/*
 * Created 20. Januar 2005 / 17:01:28
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.CharZusatz;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.CharElement;
import org.d3s.alricg.CharKomponenten.RegionVolk;
/**
 * <b>Beschreibung:</b><br>
 * Repräsentiert alle Gegenstände in Alricg.
 * 
 * @author V.Strelow
 */
public abstract class Gegenstand extends CharElement {
	private int gewicht;
	private RegionVolk[] erhältlichBei;
	private String einordnung;
	private int wert;
    
    
    /**
     * Die Einordnung ist für eine bessere Sortierung da. Mögliche angaben währen "Tränke", "Kleidung", usw.
     * @return Liefert das Attribut einordnung.
     */
    public String getEinordnung() {
        return einordnung;
    }
    /**
     * @return Liefert die Region wo der Gegenstand typischerweise zu finden ist.
     */
    public RegionVolk[] getErhältlichBei() {
        return erhältlichBei;
    }
    /**
     * @return Liefert das Gewicht des Gegenstandes in Unzen.
     */
    public int getGewicht() {
        return gewicht;
    }
    /**
     * @return Liefert der Wert des Gegenstandes in Kreuzern.
     */
    public int getWert() {
        return wert;
    }
    
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen nötigen angaben
     */
    public void loadXmlElementGegenstand(Element xmlElement){
    	// TODO implement
    }
    
    /**
     * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt" und zugefügt, zu einem 
     * übergebenen XML-Elemnet
     * @param xmlElement Das Xml-Element zu dem die Daten hinzugefügt werden
     * @return Ein Xml-Element mit allen nötigen Angaben.
     */
    protected Element writeXmlElementGegenstand(Element xmlElement) {
    	//TODO implement
    	return null;
    }
}
