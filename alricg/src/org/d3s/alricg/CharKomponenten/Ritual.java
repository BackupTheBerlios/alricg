/*
 * Created 23. Dezember 2004 / 13:17:28
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Ritual extends Ritus {

	/**
	 * Konstruktur; id beginnt mit "RIT-" für Ritual
	 * @param id Systemweit eindeutige id
	 */
	public Ritual(String id) {
		setId(id);
	}
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	// Noop!
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement() {
    	Element xmlElement = super.writeXmlElement();
    	
    	xmlElement.setLocalName("ritual");
    	
    	return xmlElement;
    }
}
