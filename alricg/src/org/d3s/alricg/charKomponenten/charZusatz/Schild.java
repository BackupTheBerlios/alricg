/*
 * Created on 26.01.2005 / 00:44:45
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.charZusatz;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class Schild extends Gegenstand {
	private String typ;
	private int bf = KEIN_WERT; // Bruchfaktor
	private int ini = KEIN_WERT; // Bruchfaktor
	private int wmAT = KEIN_WERT; // Waffenmodifikator / AT
	private int wmPA = KEIN_WERT; // Waffenmodifikator / PA
	
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#getCharKomponente()
	 */
	public CharKomponente getCharKomponente() {
		return CharKomponente.schild;
	}
	
	/**
	 * Konstruktur; id beginnt mit "SLD-" für Schild
	 * @param id Systemweit eindeutige id
	 */
	public Schild(String id) {
		setId(id);
	}
	
	/**
	 * @return Liefert den Bruchfaktor.
	 */
	public int getBf() {
		return bf;
	}
	
	/**
	 * @return Liefert den initiative Modi.
	 */
	public int getIni() {
		return ini;
	}
	
	/**
	 * @return Liefert den Waffenmodifikator / AT.
	 */
	public int getWmAT() {
		return wmAT;
	}
	
	/**
	 * @return Liefert den Waffenmodifikator / PA.
	 */
	public int getWmPA() {
		return wmPA;
	}
	
	
	
	/**
	 * @return Liefert das Attribut typ.
	 */
	public String getTyp() {
		return typ;
	}
	/**
	 * @param typ Setzt das Attribut typ.
	 */
	public void setTyp(String typ) {
		this.typ = typ;
	}
	/**
	 * @param bf Setzt das Attribut bf.
	 */
	public void setBf(int bf) {
		this.bf = bf;
	}
	/**
	 * @param ini Setzt das Attribut ini.
	 */
	public void setIni(int ini) {
		this.ini = ini;
	}
	/**
	 * @param wmAT Setzt das Attribut wmAT.
	 */
	public void setWmAT(int wmAT) {
		this.wmAT = wmAT;
	}
	/**
	 * @param wmPA Setzt das Attribut wmPA.
	 */
	public void setWmPA(int wmPA) {
		this.wmPA = wmPA;
	}
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
    	// den Schild-Typ auslesen
    	if (xmlElement.getFirstChildElement("typ") != null) {
    		typ = xmlElement.getFirstChildElement("typ").getValue();
    	}
    	try {
    		// Eigenschaften auslesen
    		if ( xmlElement.getFirstChildElement("eigenschaften") != null ) {
    			
    			// bf auslesen
    			if ( xmlElement.getFirstChildElement("eigenschaften").getAttribute("bf") != null ) {
    				bf = Integer.parseInt(xmlElement.getFirstChildElement("eigenschaften")
    												.getAttributeValue("bf"));
    			}
    			
    			// ini auslesen
    			if ( xmlElement.getFirstChildElement("eigenschaften").getAttribute("ini") != null ) {
    				ini = Integer.parseInt(xmlElement.getFirstChildElement("eigenschaften")
    												.getAttributeValue("ini"));
    			}
    		}
    		
    		// WM auslesen 
    		if ( xmlElement.getFirstChildElement("wm") != null ) {
    			
    			// bf auslesen
    			if ( xmlElement.getFirstChildElement("wm").getAttribute("at") != null ) {
    				wmAT = Integer.parseInt(xmlElement.getFirstChildElement("wm")
    												.getAttributeValue("at"));
    			}
    			
    			// ini auslesen
    			if ( xmlElement.getFirstChildElement("wm").getAttribute("pa") != null ) {
    				wmPA = Integer.parseInt(xmlElement.getFirstChildElement("wm")
    												.getAttributeValue("pa"));
    			}
    		}
    		
    	} catch (NumberFormatException exc) {
    		// TODO Bessere Fehlermeldung einbauen
    		ProgAdmin.logger.severe("Konnte String nicht in int umwandeln: " + exc.toString());
    	}
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("schild");
    	
    	// typ des Schildes Schreiben
    	if (typ != null && typ.trim().length() > 0) {
    		tmpElement = new Element("typ");
    		tmpElement.appendChild(typ.trim());
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Eigenschaften schreiben
    	if (bf != KEIN_WERT || ini != KEIN_WERT) {
    		tmpElement = new Element("eigenschaften");
    	
    		// bf schreiben
    		if (bf != KEIN_WERT) {
    			tmpElement.addAttribute(new Attribute("bf", Integer.toString(bf)));
    		}
    		
    		// ini schreiben
    		if (ini != KEIN_WERT) {
    			tmpElement.addAttribute(new Attribute("ini", Integer.toString(ini)));
    		}
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Waffen Modis schreiben
    	if (wmAT != KEIN_WERT || wmPA != KEIN_WERT) {
    		tmpElement = new Element("wm");
    	
    		// bf schreiben
    		if (wmAT != KEIN_WERT) {
    			tmpElement.addAttribute(new Attribute("at", Integer.toString(wmAT)));
    		}
    		
    		// ini schreiben
    		if (wmPA != KEIN_WERT) {
    			tmpElement.addAttribute(new Attribute("pa", Integer.toString(wmPA)));
    		}
    		xmlElement.appendChild(tmpElement);
    	}

    	return xmlElement;
    }
}
