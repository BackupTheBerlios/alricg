/*
 * Created 23. Dezember 2004 / 14:53:48
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.Links.Voraussetzung;
/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Talent extends Faehigkeit {
	
	public enum Art {BASIS, SPEZIAL, BERUF }
	public enum Sorte {KAMPF, KOERPER, GESELLSCHAFT, NATUR, WISSEN, HANDWERK }
	private String[] spezialisierungen;
	private Art art;
    private Sorte sorte;
    private int abWert; // Bezieht sich auf Voraussetzung
    private Voraussetzung voraussetzung;
    
	/**
	 * @return Liefert das Attribut art.
	 */
	public Art getArt() {
		return art;
	}
	/**
	 * @return Liefert das Attribut sorte.
	 */
	public Sorte getSorte() {
		return sorte;
	}
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	// TODO implement
    }

    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	// TODO implement
    	return null;
    }
}
