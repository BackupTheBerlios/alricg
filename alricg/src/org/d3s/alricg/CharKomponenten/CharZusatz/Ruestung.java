/*
 * Created 20. Januar 2005 / 17:08:35
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.CharZusatz;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.Controller.ProgAdmin;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Ruestung extends Gegenstand {
	private int gRs = KEIN_WERT; //gesamte RS
	private int gBe = KEIN_WERT; // Gesamter BF
	
	// Zonen RS
	private int zoneKo = KEIN_WERT;
	private int zoneBr = KEIN_WERT;
	private int zoneRue = KEIN_WERT;
	private int zoneBa = KEIN_WERT;
	private int zoneLa = KEIN_WERT;
	private int zoneRa = KEIN_WERT;
	private int zoneLb = KEIN_WERT;
	private int zoneRb = KEIN_WERT;
	private int zoneGes = KEIN_WERT;
	
	/**
	 * Konstruktur; id beginnt mit "RUE-" für Ruestung
	 * @param id Systemweit eindeutige id
	 */
	public Ruestung(String id) {
		setId(id);
	}
	
	/**
	 * @return Liefert den gesamt Bruchfaktor.
	 */
	public int getGBe() {
		return gBe;
	}
	/**
	 * @return Liefert den gesamt Rüstungsschutz.
	 */
	public int getGRs() {
		return gRs;
	}
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	Element tmpElement;
    	
    	try {
//    		 Auslesen der ZonenRs der Rüstung
	    	if (xmlElement.getFirstChildElement("zonen") != null) {
	    		tmpElement = xmlElement.getFirstChildElement("zonen");
	    		
	    		zoneKo = Integer.parseInt(tmpElement.getAttributeValue("ko"));
	    		zoneBr = Integer.parseInt(tmpElement.getAttributeValue("br"));
	    		zoneRue = Integer.parseInt(tmpElement.getAttributeValue("rue"));
	    		zoneBa = Integer.parseInt(tmpElement.getAttributeValue("ba"));
	    		zoneLa = Integer.parseInt(tmpElement.getAttributeValue("la"));
	    		zoneRa = Integer.parseInt(tmpElement.getAttributeValue("ra"));
	    		zoneLb = Integer.parseInt(tmpElement.getAttributeValue("lb"));
	    		zoneRb = Integer.parseInt(tmpElement.getAttributeValue("rb"));
	    		zoneGes  = Integer.parseInt(tmpElement.getAttributeValue("ges"));
	    	}
	    	
	    	// Auslesen der "normalen" RS und be
	    	gRs = Integer.parseInt(xmlElement.getFirstChildElement("gRs").getValue());
	    	gBe = Integer.parseInt(xmlElement.getFirstChildElement("gBe").getValue());
	    	
    	} catch(NumberFormatException exc) {
    		// TODO bessere Fehlermeldung einfügen
    		ProgAdmin.logger.severe("Konnte Sting nicht in int umwandeln: " + exc.toString());
    	}
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("ruestung");
    	
    	// schreiben der ZonenRs
    	if (zoneKo != KEIN_WERT) {
    		tmpElement = new Element("zonen");
    		
    		tmpElement.addAttribute(new Attribute("ko", Integer.toString(zoneKo)));
    		tmpElement.addAttribute(new Attribute("br", Integer.toString(zoneBr)));
    		tmpElement.addAttribute(new Attribute("rue", Integer.toString(zoneRue)));
    		tmpElement.addAttribute(new Attribute("ba", Integer.toString(zoneBa)));
    		tmpElement.addAttribute(new Attribute("la", Integer.toString(zoneLa)));
    		tmpElement.addAttribute(new Attribute("ra", Integer.toString(zoneRa)));
    		tmpElement.addAttribute(new Attribute("lb", Integer.toString(zoneLb)));
    		tmpElement.addAttribute(new Attribute("rb", Integer.toString(zoneRb)));
    		tmpElement.addAttribute(new Attribute("ges", Integer.toString(zoneGes)));
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// schreiben der "normalen" RS
    	tmpElement = new Element("gRs");
    	tmpElement.appendChild(Integer.toString(gRs));
    	xmlElement.appendChild(tmpElement);
    	
    	// Schreiben der "normalen" Be
    	tmpElement = new Element("gBe");
    	tmpElement.appendChild(Integer.toString(gBe));
    	xmlElement.appendChild(tmpElement);
    	
    	return xmlElement;
    }
	
}
