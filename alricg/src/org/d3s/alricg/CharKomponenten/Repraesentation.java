/*
 * Created on 12.03.2005 / 12:18:22
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

/**
 * <u>Beschreibung:</u><br> 
 * Repräsentiert eine magische Repräsentation. Dabei werden auch "unechte" Repräsentationen
 * mit aufgenommen, wie Derwische, Tierkrieger, usw. für ein besseres Handling
 * @author V. Strelow
 */
public class Repraesentation extends CharElement {
	private boolean istEchteRep = true; // "Echte" Repräsentationen sind solche, die im Liber stehen.
	
	/**
	 * Konstruktur; id beginnt mit "REP-" für Repraesentation
	 * @param id Systemweit eindeutige id
	 */
	public Repraesentation(String id) {
		setId(id);
	}
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
    	// Auslesen, ob es eine "echte" Repräsentation ist
    	if ( xmlElement.getFirstChildElement("istEchteRep") != null ) {
    		assert xmlElement.getFirstChildElement("istEchteRep").getValue().equals("true")
    			|| xmlElement.getFirstChildElement("istEchteRep").getValue().equals("false");
    		
    		if ( xmlElement.getFirstChildElement("istEchteRep").getValue().equals("false") ) {
    			istEchteRep = false;
    		} // true ist Default, muß nicht gesetzt werden
    	}
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("repraesentation");
    	
    	// Schreiben ob es eine "echte" Repräsentation ist
    	if ( !istEchteRep ) {
    		tmpElement = new Element("istEchteRep");
    		tmpElement.appendChild("false");
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	return xmlElement;
    }

}
