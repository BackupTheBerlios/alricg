/*
 * Created 26. Dezember 2004 / 23:10:42
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.Links;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.CharKomponenten.CharElement;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Voraussetzung {
	private IdLinkVoraussetzung[] festeVoraussetzung; // Die unveränderlichen Werte
	private CharElement quelle; // Das CharElement, das diese Voraussetzung besitzt
//	 Pro Array muß mindesten eine LinkVoraussetzung erfüllt sein	
	private IdLinkVoraussetzung[][] auswahlVoraussetzung;
	
	/**
	 * Konstruktor
	 * @param quelle Das CharElement, bei dem diese Voraussetzung erfüllt sein muß
	 */
	public Voraussetzung (CharElement quelle) {
		this.quelle = quelle;
	}
	
	
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen nötigen angaben
     */
    public void loadXmlElement(Element xmlElement) {
    	Elements tmpElements, tmpElements2;
    	
    	// Auslesen der "festen" Elemente
    	tmpElements = xmlElement.getChildElements("fest");
    	festeVoraussetzung = new IdLinkVoraussetzung[tmpElements.size()];
    	for (int i = 0; i < tmpElements.size(); i++) {
    		festeVoraussetzung[i] = new IdLinkVoraussetzung(quelle);
    		festeVoraussetzung[i].loadXmlElement(tmpElements.get(i));
    	}
    	
    	// Aulesen der "Auswahl", also wo nur eines aus einer Gruppe erfüllt sein muß
    	tmpElements = xmlElement.getChildElements("auswahl");
    	auswahlVoraussetzung = new IdLinkVoraussetzung[tmpElements.size()][];
    	for (int i = 0; i < tmpElements.size(); i++) {
    		tmpElements2 = tmpElements.get(i).getChildElements("option");
    		auswahlVoraussetzung[i] = new IdLinkVoraussetzung[tmpElements2.size()];
    		for (int ii = 0; ii < tmpElements2.size(); ii++) {
    			auswahlVoraussetzung[i][ii] = new IdLinkVoraussetzung(quelle);
    			auswahlVoraussetzung[i][ii].loadXmlElement(tmpElements2.get(ii));
    		}
    	}
    }
	
    /**
     * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt".
     * @param tagName Der name des Tags
     * @return Ein Xml-Element mit allen nötigen Angaben.
     */
    public Element writeXmlElement(String tagName) {
    	Element xmlElement = new Element(tagName);
    	Element tmpElement;
    	
    	// Alle "festen" Elemente hinzufügen
    	for ( int i = 0; i < festeVoraussetzung.length; i++) {
    		xmlElement.appendChild( festeVoraussetzung[i].writeXmlElement("fest") );
    	}
    	
    	// Alle Elemente der "Auswahl" hinzufügen
    	for (int i = 0; i < auswahlVoraussetzung.length; i++) {
    		tmpElement = new Element("auswahl");
    		for (int ii = 0; ii < auswahlVoraussetzung[i].length; ii++) {
    			tmpElement.appendChild(auswahlVoraussetzung[i][ii].writeXmlElement("option"));
    		}
    		xmlElement.appendChild(tmpElement);
    	}

    	return xmlElement;
    }
    
    
	/**
	 * <u>Beschreibung:</u><br> 
	 * Beschreib eine Verbindung zwischen einer Voraussetzung und den Elementen, die
	 * vorausgesetzt werden. IdLink wird hierbei erweitert um ein flag, ob ein
	 * Grenzwert ein Minimum ist, oder ein Maximum.
	 * @author V. Strelow
	 */
	class IdLinkVoraussetzung extends IdLink {
		private boolean isMinimum = true; // Ob der Grenzwert ein Minimum oder maximum ist
		
		public IdLinkVoraussetzung(CharElement quelle) {
			super(quelle, null);
		}
		

	    /* (non-Javadoc) Methode überschrieben
	     * @see org.d3s.alricg.CharKomponenten.Links.IdLink#loadXmlElement(nu.xom.Element)
	     */
	    public void loadXmlElement(Element xmlElement) {
	    	super.loadXmlElement(xmlElement);
	    	
	    	// Guard
	    	if ( xmlElement.getAttribute("wertGrenze") == null ) {
	    		return;
	    	}
	    	
	    	// Prüfen ob der Wertebereich gültig ist!
	    	assert xmlElement.getAttributeValue("wertGrenze").equals("max")
	    		|| xmlElement.getAttributeValue("wertGrenze").equals("min");
	    	
	    	// Setzen des Wertes, "true" ist Default
	    	if ( xmlElement.getAttributeValue("wertGrenze").equals("max") ) {
	    		isMinimum = false;
	    	}
	    }
	    

		/* (non-Javadoc) Methode überschrieben
		 * @see org.d3s.alricg.CharKomponenten.Links.IdLink#writeXmlElement(java.lang.String)
		 */
		public Element writeXmlElement(String tagName) {
			Element xmlElement = super.writeXmlElement(tagName);
			
			// Hinzufügen der "wertGrenze", falls nicht Default wert
			if (!isMinimum) {
				xmlElement.addAttribute(new Attribute("wertGrenze", "max"));
			}
			return xmlElement;
		}
	}
}
