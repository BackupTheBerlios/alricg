/*
 * Created 26. Dezember 2004 / 23:45:37
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br> 
 * Repr�sentiert eine Schrift
 * @author V.Strelow
 */
public class Schrift extends SchriftSprache {

	/**
	 * Konstruktur; id beginnt mit "SFT-" f�r Schrift
	 * @param id Systemweit eindeutige id
	 */
	public Schrift(String id) {
		setId(id);
	}
	
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	// Noop!
    }
    
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	
    	xmlElement.setLocalName("schrift");
    	
    	return xmlElement;
    }
}