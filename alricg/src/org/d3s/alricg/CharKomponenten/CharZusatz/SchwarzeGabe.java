/*
 * Created on 26.01.2005 / 16:42:51
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.CharKomponenten.CharZusatz;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.CharElement;
import org.d3s.alricg.Controller.ProgAdmin;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class SchwarzeGabe extends CharElement {
	private int kosten = KEIN_WERT;
	private int minStufe = KEIN_WERT;
	private int maxStufe = KEIN_WERT;
	
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
    	
    	try {
//    		 Stufengrenzen auslesen
	    	if (xmlElement.getFirstChildElement("stufenGrenzen") != null) {
	    		minStufe = Integer.parseInt(xmlElement.getFirstChildElement("stufenGrenzen")
	    												.getAttributeValue("minStufe"));
	    		maxStufe = Integer.parseInt(xmlElement.getFirstChildElement("stufenGrenzen")
														.getAttributeValue("maxStufe"));
	    	}
	    	
	    	kosten = Integer.parseInt(xmlElement.getAttributeValue("kosten"));
		} catch (NumberFormatException exc) {
			// TODO bessere Fehlermeldung
			ProgAdmin.logger.severe("Konnte String nicht in int umwandeln: " + exc.toString());
		}
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("gabe");
    	
    	// Schreiben der Stufengrenzen
    	if (minStufe != KEIN_WERT || maxStufe != KEIN_WERT) {
    		tmpElement = new Element("stufenGrenzen");
    		
    		if (minStufe != KEIN_WERT) {
    			tmpElement.addAttribute(new Attribute("minStufe", Integer.toString(minStufe)));
    		}
    		if (maxStufe != KEIN_WERT) {
    			tmpElement.addAttribute(new Attribute("maxStufe", Integer.toString(maxStufe)));
    		}
    	}
    	
    	// Schreiben der Kosten
    	xmlElement.addAttribute(new Attribute("kosten", Integer.toString(kosten)));
    	
    	return xmlElement;
    }
	
}
