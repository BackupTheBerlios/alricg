/*
 * Created 20. Januar 2005 / 16:48:58
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
abstract public class SchriftSprache extends CharElement {
	private int komplexitaet;
	private int kostenKlasse;
	
	/**
	 * @return Liefert das Attribut komplexitaet.
	 */
	public int getKomplexitaet() {
		return komplexitaet;
	}
	/**
	 * @return Liefert das Attribut kostenKlasse.
	 */
	public int getKostenKlasse() {
		return kostenKlasse;
	}
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen nötigen angaben
     */
    public void loadXmlElementSchriftSprache(Element xmlElement){
    	// TODO implement
    }
    
    /**
     * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt" und zugefügt, zu einem 
     * übergebenen XML-Elemnet
     * @param xmlElement Das Xml-Element zu dem die Daten hinzugefügt werden
     * @return Ein Xml-Element mit allen nötigen Angaben.
     */
    protected Element writeXmlElementSchriftSprache(Element xmlElement) {
    	//TODO implement
    	return null;
    }
}
