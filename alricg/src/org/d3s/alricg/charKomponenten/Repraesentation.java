/*
 * Created on 12.03.2005 / 12:18:22
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.controller.CharKomponente;

/**
 * <u>Beschreibung:</u><br> 
 * Repräsentiert eine magische Repräsentation. Dabei werden auch "unechte" Repräsentationen
 * mit aufgenommen, wie Derwische, Tierkrieger, usw. für ein besseres Handling
 * @author V. Strelow
 */
public class Repraesentation extends CharElement {
	private boolean isEchteRep = true; // "Echte" Repräsentationen sind solche, die im Liber stehen.
	private String abkuerzung;
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#getCharKomponente()
	 */
	public CharKomponente getCharKomponente() {
		return CharKomponente.repraesentation;
	}
	
	/**
	 * Konstruktur; id beginnt mit "REP-" für Repraesentation
	 * @param id Systemweit eindeutige id
	 */
	public Repraesentation(String id) {
		setId(id);
	}
	
	/**
	 * 
	 * @return Liefert die Abkürzung für diese Repräsention, z.B. "Mag" für Gildenmagier
	 */
	public String getAbkuerzung() {
		return abkuerzung;
	}

	/**
	 * @return Liefert das Attribut isEchteRep.
	 */
	public boolean isEchteRep() {
		return isEchteRep;
	}
	/**
	 * @param isEchteRep Setzt das Attribut isEchteRep.
	 */
	public void setEchteRep(boolean istEchteRep) {
		this.isEchteRep = istEchteRep;
	}
	/**
	 * @param abkuerzung Setzt das Attribut abkuerzung.
	 */
	public void setAbkuerzung(String abkuerzung) {
		this.abkuerzung = abkuerzung;
	}
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
    	// Auslesen, ob es eine "echte" Repräsentation ist
    	if ( xmlElement.getFirstChildElement("isEchteRep") != null ) {
    		assert xmlElement.getFirstChildElement("isEchteRep").getValue().equals("true")
    			|| xmlElement.getFirstChildElement("isEchteRep").getValue().equals("false");
    		
    		if ( xmlElement.getFirstChildElement("isEchteRep").getValue().equals("false") ) {
    			isEchteRep = false;
    		} // true ist Default, muß nicht gesetzt werden
    	}
    	
    	// Auslesen der Abkürzung
    	abkuerzung = xmlElement.getAttributeValue("abkuerzung");
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("repraesentation");
    	
    	// Schreiben ob es eine "echte" Repräsentation ist
    	if ( !isEchteRep ) {
    		tmpElement = new Element("isEchteRep");
    		tmpElement.appendChild("false");
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben des Attributs Abkürzung
    	xmlElement.addAttribute( new Attribute("abkuerzung", abkuerzung) );
		
    	return xmlElement;
    }

}
