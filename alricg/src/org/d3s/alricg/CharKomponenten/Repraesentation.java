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
 * Repr�sentiert eine magische Repr�sentation. Dabei werden auch "unechte" Repr�sentationen
 * mit aufgenommen, wie Derwische, Tierkrieger, usw. f�r ein besseres Handling
 * @author V. Strelow
 */
public class Repraesentation extends CharElement {
	private boolean istEchteRep = true; // "Echte" Repr�sentationen sind solche, die im Liber stehen.
	
	/**
	 * Konstruktur; id beginnt mit "REP-" f�r Repraesentation
	 * @param id Systemweit eindeutige id
	 */
	public Repraesentation(String id) {
		setId(id);
	}
	
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
    	// Auslesen, ob es eine "echte" Repr�sentation ist
    	if ( xmlElement.getFirstChildElement("istEchteRep") != null ) {
    		assert xmlElement.getFirstChildElement("istEchteRep").getValue().equals("true")
    			|| xmlElement.getFirstChildElement("istEchteRep").getValue().equals("false");
    		
    		if ( xmlElement.getFirstChildElement("istEchteRep").getValue().equals("false") ) {
    			istEchteRep = false;
    		} // true ist Default, mu� nicht gesetzt werden
    	}
    }
    
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("repraesentation");
    	
    	// Schreiben ob es eine "echte" Repr�sentation ist
    	if ( !istEchteRep ) {
    		tmpElement = new Element("istEchteRep");
    		tmpElement.appendChild("false");
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	return xmlElement;
    }

}
