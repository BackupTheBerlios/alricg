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
 * Repr�sentiert alle Gegenst�nde in Alricg.
 * 
 * @author V.Strelow
 */
public abstract class Gegenstand extends CharElement {
	private int gewicht;
	private RegionVolk[] erh�ltlichBei;
	private String einordnung;
	private int wert;
    
    
    /**
     * Die Einordnung ist f�r eine bessere Sortierung da. M�gliche angaben w�hren "Tr�nke", "Kleidung", usw.
     * @return Liefert das Attribut einordnung.
     */
    public String getEinordnung() {
        return einordnung;
    }
    /**
     * @return Liefert die Region wo der Gegenstand typischerweise zu finden ist.
     */
    public RegionVolk[] getErh�ltlichBei() {
        return erh�ltlichBei;
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
    
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	// TODO implement
    }
    
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	// TODO implement
    	return null;
    }
}
