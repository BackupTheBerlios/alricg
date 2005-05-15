/*
 * Created on 26.01.2005 / 16:42:51
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.charZusatz;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.controller.CharKomponente;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class SchwarzeGabe extends CharElement {
	private int kosten = KEIN_WERT;
	private int minStufe = KEIN_WERT;
	private int maxStufe = KEIN_WERT;
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#getCharKomponente()
	 */
	public CharKomponente getCharKomponente() {
		return CharKomponente.schwarzeGabe;
	}
	
	/**
	 * Konstruktur; id beginnt mit "SGA-" für Schwarze-Gabe
	 * @param id Systemweit eindeutige id
	 */
	public SchwarzeGabe(String id) {
		setId(id);
	}
	
	/**
	 * @return Liefert das Attribut kosten.
	 */
	public int getKosten() {
		return kosten;
	}
	/**
	 * @param kosten Setzt das Attribut kosten.
	 */
	public void setKosten(int kosten) {
		this.kosten = kosten;
	}
	/**
	 * @return Liefert das Attribut maxStufe.
	 */
	public int getMaxStufe() {
		return maxStufe;
	}
	/**
	 * @param maxStufe Setzt das Attribut maxStufe.
	 */
	public void setMaxStufe(int maxStufe) {
		this.maxStufe = maxStufe;
	}
	/**
	 * @return Liefert das Attribut minStufe.
	 */
	public int getMinStufe() {
		return minStufe;
	}
	/**
	 * @param minStufe Setzt das Attribut minStufe.
	 */
	public void setMinStufe(int minStufe) {
		this.minStufe = minStufe;
	}
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
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
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
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
