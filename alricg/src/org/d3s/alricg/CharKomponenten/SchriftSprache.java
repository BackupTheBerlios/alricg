/*
 * Created 20. Januar 2005 / 16:48:58
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einf�gen
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
     * @param xmlElement Das Xml-Element mit allen n�tigen angaben
     */
    public void loadXmlElementSchriftSprache(Element xmlElement){
    	// TODO implement
    }
    
    /**
     * Dient zur Speicherung (also f�r den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt" und zugef�gt, zu einem 
     * �bergebenen XML-Elemnet
     * @param xmlElement Das Xml-Element zu dem die Daten hinzugef�gt werden
     * @return Ein Xml-Element mit allen n�tigen Angaben.
     */
    protected Element writeXmlElementSchriftSprache(Element xmlElement) {
    	//TODO implement
    	return null;
    }
}
