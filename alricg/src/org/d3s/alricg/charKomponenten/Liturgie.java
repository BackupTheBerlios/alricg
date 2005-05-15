/*
 * Created 23. Dezember 2004 / 13:17:17
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import nu.xom.Element;

import org.d3s.alricg.controller.CharKomponente;

/**
 * <b>Beschreibung:</b><br>
 * Steht für eine Liturgie. Verfügt über keine extra Attribute, ist zur Unterscheidung
 * trotzdem eine eigenständige Klasse.
 * @author V.Strelow
 */
public class Liturgie extends Ritus {

	/**
	 * Konstruktur; id beginnt mit "LIT-" für Liturgie
	 * @param id Systemweit eindeutige id
	 */
	public Liturgie(String id) {
		setId(id);
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#getCharKomponente()
	 */
	public CharKomponente getCharKomponente() {
		return CharKomponente.liturgie;
	}
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	// Noop!
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	
    	xmlElement.setLocalName("liturgie");
    	
    	return xmlElement;
    }
	
}
