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
