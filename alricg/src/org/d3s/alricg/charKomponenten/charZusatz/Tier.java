/*
 * Created 20. Januar 2005 / 17:17:22
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten.charZusatz;

import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Tier extends CharElement{

	// TODO Die Felder für das Tier anlegen!
	
	/**
	 * Konstruktur; id beginnt mit "TIE-" für Tier
	 * @param id Systemweit eindeutige id
	 */
	public Tier(String id) {
		setId(id);
	}
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	// TODO implement
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	
    	xmlElement.setLocalName("tier");
    	// TODO implement
    	return null;
    }
}
