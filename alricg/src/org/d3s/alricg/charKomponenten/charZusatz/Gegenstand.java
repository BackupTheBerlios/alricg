/*
 * Created 20. Januar 2005 / 17:01:28
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten.charZusatz;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.RegionVolk;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;
/**
 * <b>Beschreibung:</b><br>
 * Repr�sentiert alle Gegenst�nde in Alricg.
 * 
 * @author V.Strelow
 */
public abstract class Gegenstand extends CharElement {
	private int gewicht = KEIN_WERT;
	private RegionVolk[] erh�ltlichBei;
	private String einordnung;
	private int wert = KEIN_WERT;
    
    
    /**
     * Die Einordnung ist f�r eine bessere Sortierung da. M�gliche angaben w�hren "Tr�nke", "Kleidung", usw.
     * @return Liefert das Attribut einordnung.
     */
    public String getEinordnung() {
        return einordnung;
    }
    /**
     * @return Liefert die Region wo der Gegenstand typischerweise zu finden ist.
     */
    public RegionVolk[] getErh�ltlichBei() {
        return erh�ltlichBei;
    }
    /**
     * @return Liefert das Gewicht des Gegenstandes in Unzen.
     */
    public int getGewicht() {
        return gewicht;
    }
    /**
     * @return Liefert der Wert des Gegenstandes in Kreuzern.
     */
    public int getWert() {
        return wert;
    }
    
	/**
	 * @param einordnung Setzt das Attribut einordnung.
	 */
	public void setEinordnung(String einordnung) {
		this.einordnung = einordnung;
	}
	/**
	 * @param erh�ltlichBei Setzt das Attribut erh�ltlichBei.
	 */
	public void setErh�ltlichBei(RegionVolk[] erh�ltlichBei) {
		this.erh�ltlichBei = erh�ltlichBei;
	}
	/**
	 * @param gewicht Setzt das Attribut gewicht.
	 */
	public void setGewicht(int gewicht) {
		this.gewicht = gewicht;
	}
	/**
	 * @param wert Setzt das Attribut wert.
	 */
	public void setWert(int wert) {
		this.wert = wert;
	}
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	Elements tmpElements;
    	
		try {
	    	// Auslesen des Gewichts
	    	if ( xmlElement.getFirstChildElement("gewicht") != null) {
	    		gewicht = Integer.parseInt(xmlElement
	    							.getFirstChildElement("gewicht").getValue());
	    	}
	    	
	    	// Auslesen der Wertes
	    	if ( xmlElement.getFirstChildElement("wert") != null) {
	    		wert = Integer.parseInt(xmlElement
	    							.getFirstChildElement("wert").getValue());
	    	}
		} catch (NumberFormatException exc) {
			// TODO bessere Fehlermeldung einbauen
			ProgAdmin.logger.severe("Konnte String nicht in int umwandeln: " + exc.toString());
		}
		
		// Auslesen, wo dieser Gegenstand typischer Weise erh�ltlich ist
		tmpElements = xmlElement.getChildElements("erhaeltlichBei");
		erh�ltlichBei = new RegionVolk[tmpElements.size()];
		for (int i = 0; i < tmpElements.size(); i++) {
			erh�ltlichBei[i] = (RegionVolk) ProgAdmin.charKompAdmin.getCharElement(
					tmpElements.get(i).getValue(),
					CharKomponente.region
				);
		}
		
		// Auslesen der Einordnung des Gegenstandes
		if ( xmlElement.getAttribute("einordnung") != null ) {
			einordnung = xmlElement.getAttributeValue("einordnung");
		}
		
    }
    
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	// Schreiben des Gewichtes
    	if ( gewicht != KEIN_WERT ) {
    		tmpElement = new Element("gewicht");
    		tmpElement.appendChild(Integer.toString(gewicht));
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben des Wertes des Gegenstands
    	if ( wert != KEIN_WERT ) {
    		tmpElement = new Element("wert");
    		tmpElement.appendChild(Integer.toString(wert));
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben wo typischer wiese zu erhalten
    	for ( int i = 0; i < erh�ltlichBei.length; i++) {
    		tmpElement = new Element("erhaeltlichBei");
    		tmpElement.appendChild(erh�ltlichBei[i].getId());
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben der Einordnung
    	if ( einordnung != null && einordnung.trim().length() > 0) {
    		xmlElement.addAttribute(new Attribute("einordnung", einordnung));
    	}
    	
    	return xmlElement;
    }
}
