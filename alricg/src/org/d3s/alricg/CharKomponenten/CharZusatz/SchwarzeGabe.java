/*
 * Created on 26.01.2005 / 16:42:51
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.CharKomponenten.CharZusatz;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.CharElement;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class SchwarzeGabe extends CharElement {
	private int kosten;
	private int minStufe;
	private int maxStufe;
	
	/**
	 * Konstruktur; id beginnt mit "SGA-" für Schwarze-Gabe
	 * @param id Systemweit eindeutige id
	 */
	public SchwarzeGabe(String id) {
		setId(id);
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
