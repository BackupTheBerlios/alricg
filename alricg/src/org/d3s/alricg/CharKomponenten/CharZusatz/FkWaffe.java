/*
 * Created 20. Januar 2005 / 17:07:50
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.CharZusatz;

import org.d3s.alricg.Controller.ProgAdmin;

import nu.xom.Attribute;
import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class FkWaffe extends Waffe {
	private int laden = KEIN_WERT;
	private int reichweite = KEIN_WERT;
	private String reichweiteTpPlus; // Zusätzliche TP durch Reichweite

	/**
	 * Konstruktur; id beginnt mit "FKW-" für Fernkampf-Waffe
	 * @param id Systemweit eindeutige id
	 */
	public FkWaffe(String id) {
		setId(id);
	}
	
	/**
	 * @return Liefert das Attribut laden.
	 */
	public int getLaden() {
		return laden;
	}
	
	/**
	 * @return Liefert das Attribut reichweite.
	 */
	public int getReichweite() {
		return reichweite;
	}
	
	/**
	 * @return Liefert das Attribut reichweiteTpPlus.
	 */
	public String getReichweiteTpPlus() {
		return reichweiteTpPlus;
	}
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	Element tmpElement;
    	
    	tmpElement = xmlElement.getFirstChildElement("tp");
    	// Auslesen der TP-Zusätze für Fernkampfwaffen
    	if ( tmpElement != null ) {
    		reichweiteTpPlus = tmpElement.getAttributeValue("plus-reichweite");
    	}
	    	
	    try {
	    	// Auslesen der weiteren Eigenschaften
	    	tmpElement = xmlElement.getFirstChildElement("eigenschaften");
	    	// Auslesen der benötigten Aktionen zum Laden
	    	if ( tmpElement != null && tmpElement.getAttribute("laden") != null ) {
	    		laden = Integer.parseInt(tmpElement.getAttributeValue("laden"));
	    	}
	    	
	    	// Auslesen der Reichweite
	    	if ( tmpElement != null && tmpElement.getAttribute("reichweite") != null ) {
	    		reichweite = Integer.parseInt(tmpElement.getAttributeValue("reichweite"));
	    	}
	    	
    	}  catch (NumberFormatException exc) {
    		// TODO Bessere Fehlermeldung einbauen
    		ProgAdmin.logger.severe("Konnte String nicht in int umwandeln: " +exc.toString());
    	}
    	
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("fkWaffe");
    	
    	tmpElement = xmlElement.getFirstChildElement("tp");
    	// Schreiben der "Schwelle" für den KK -Zuschlag durch Reichweite
    	if (reichweiteTpPlus != null) {
    		if (tmpElement == null) {
    			tmpElement = new Element("tp");
    			xmlElement.appendChild(tmpElement);
    		}
    		
    		tmpElement.addAttribute(new Attribute("plus-reichweite", reichweiteTpPlus));
    	}
    	
    	tmpElement = xmlElement.getFirstChildElement("eigenschaften");
    	// Schreiben wie lange zum Laden benötigt wird
    	if (laden != KEIN_WERT) {
    		if (tmpElement == null) {
    			tmpElement = new Element("eigenschaften");
    			xmlElement.appendChild(tmpElement);
    		}
    		
    		tmpElement.addAttribute(new Attribute("laden", Integer.toString(laden)));
    	}
    	
    	// Schreiben der Reichweite
    	if (reichweite != KEIN_WERT) {
    		if (tmpElement == null) {
    			tmpElement = new Element("eigenschaften");
    			xmlElement.appendChild(tmpElement);
    		}
    		
    		tmpElement.addAttribute(new Attribute("reichweite", Integer.toString(reichweite)));
    	}
    	
    	return xmlElement;
    }
}
