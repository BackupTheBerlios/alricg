/*
 * Created on 01.03.2005 / 13:48:04
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.links;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.charZusatz.SimpelGegenstand;
import org.d3s.alricg.charKomponenten.Herkunft;
import org.d3s.alricg.controller.ProgAdmin;

/**
 * <u>Beschreibung:</u><br> 
 * Repräsentiert eine Auswahl von Ausrüstungsgegenständen für Kultur / Professionen
 * @author V. Strelow
 */
public class AuswahlAusruestung {
	
	private Herkunft herkunft;
	private HilfsAuswahl festeAuswahl; // Die unveränderlichen Werte
	private HilfsAuswahl[] variableAuswahl;
	
	/**
	 * Konstruktor
	 * @param herkunft Die "quelle" dieser Auswahl
	 */
	public AuswahlAusruestung(Herkunft herkunft) {
		this.herkunft = herkunft;
	}
	

	/**
	 * @return Liefert das Attribut festeAuswahl.
	 */
	public HilfsAuswahl getFesteAuswahl() {
		return festeAuswahl;
	}
	/**
	 * @param festeAuswahl Setzt das Attribut festeAuswahl.
	 */
	public void setFesteAuswahl(HilfsAuswahl festeAuswahl) {
		this.festeAuswahl = festeAuswahl;
	}
	/**
	 * @return Liefert das Attribut herkunft.
	 */
	public Herkunft getHerkunft() {
		return herkunft;
	}
	/**
	 * @param herkunft Setzt das Attribut herkunft.
	 */
	public void setHerkunft(Herkunft herkunft) {
		this.herkunft = herkunft;
	}
	/**
	 * @return Liefert das Attribut variableAuswahl.
	 */
	public HilfsAuswahl[] getVariableAuswahl() {
		return variableAuswahl;
	}
	/**
	 * @param variableAuswahl Setzt das Attribut variableAuswahl.
	 */
	public void setVariableAuswahl(HilfsAuswahl[] variableAuswahl) {
		this.variableAuswahl = variableAuswahl;
	}
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen nötigen angaben
     */
    public void loadXmlElement(Element xmlElement) {
    	Elements tmpElements;
    	int anzahl;
    	
    	// Auslesen der festen Gegenstände
    	if ( xmlElement.getFirstChildElement("fest") != null) {
    		festeAuswahl = new HilfsAuswahl();
    		festeAuswahl.loadXmlElement(xmlElement.getFirstChildElement("fest"), herkunft);
    	}
    	
    	// Auslesen der Auswahl
    	tmpElements = xmlElement.getChildElements("auswahl");
    	variableAuswahl = new HilfsAuswahl[tmpElements.size()];
    	for ( int i = 0; i < variableAuswahl.length; i++) {
    		variableAuswahl[i] = new HilfsAuswahl();
    		variableAuswahl[i].loadXmlElement(tmpElements.get(i), herkunft);
    	}
    }
    	
    /**
     * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt".
     * @param tagName Der name des Tags
     * @return Ein Xml-Element mit allen nötigen Angaben.
     */
    public Element writeXmlElement(String tagName){
    	Element xmlElement = new Element(tagName);
    	
    	// Schreiben der "festen" Elemente
    	if (festeAuswahl != null) {
    		xmlElement.appendChild(festeAuswahl.writeXmlElement("fest"));
    	}
    	
    	// Schreiben der "wählbaren" Elemente
    	for (int i = 0; i < variableAuswahl.length; i++) {
    		xmlElement.appendChild(variableAuswahl[i].writeXmlElement("auswahl"));
    	}
    	
    	return xmlElement;
    }
    
    /**
     * <u>Beschreibung:</u><br> 
     *  Hilfklasse für die Datenhaltung
     * @author V. Strelow
     */
    private class HilfsAuswahl  {
    	private int anzahl = 1;
    	private IdLink[] links;
    	private SimpelGegenstand[] simpGegenstand;
    	
		/**
		 * @return Liefert das Attribut anzahl.
		 */
		public int getAnzahl() {
			return anzahl;
		}
		/**
		 * @param anzahl Setzt das Attribut anzahl.
		 */
		public void setAnzahl(int anzahl) {
			this.anzahl = anzahl;
		}
		/**
		 * @return Liefert das Attribut links.
		 */
		public IdLink[] getLinks() {
			return links;
		}
		/**
		 * @param links Setzt das Attribut links.
		 */
		public void setLinks(IdLink[] links) {
			this.links = links;
		}
		/**
		 * @return Liefert das Attribut simpGegenstand.
		 */
		public SimpelGegenstand[] getSimpGegenstand() {
			return simpGegenstand;
		}
		/**
		 * @param simpGegenstand Setzt das Attribut simpGegenstand.
		 */
		public void setSimpGegenstand(SimpelGegenstand[] simpGegenstand) {
			this.simpGegenstand = simpGegenstand;
		}
    	/**
    	 * Liest aus einem XML-Element alle Daten ein
    	 * @param xmlElement Das zu lesende Element
    	 * @param herkunft Die Herkunft diese Auswahl
    	 */
		public void loadXmlElement(Element xmlElement, Herkunft herkunft) {
	    	Elements tmpElements;

	    	// Die Anzahl auslesen, falls angegeben?
	    	if ( xmlElement.getAttribute("anzahl") != null) {
	    		try {
	    			anzahl = Integer.parseInt(xmlElement.getAttributeValue("anzahl"));
	    		} catch (NumberFormatException exc) {
	    			ProgAdmin.logger.severe("Konnte Sting nicht in int umwandeln: " + exc);
	    		}
	    	}
	    	
	    	// Auslesen der Gegenstände die verlinkt werden!
	    	tmpElements = xmlElement.getChildElements("ausruestungLink");
    		links = new IdLink[tmpElements.size()];
    		for (int i = 0; i < tmpElements.size(); i++) {
    			
    			// ACHTUNG Die "null" an der Stelle könnte zu Problemen führen!!
    			links[i] = new IdLink(herkunft, null);
    			links[i].loadXmlElement( tmpElements.get(i) );
    		}
    		
    		// Auslesen der simplen Gegenstände, die direkt angegeben werden
    		tmpElements = xmlElement.getChildElements("ausruestungNeu");
    		simpGegenstand = new SimpelGegenstand[tmpElements.size()];
    		for (int i = 0; i < tmpElements.size(); i++) {
    			simpGegenstand[i] = new SimpelGegenstand();
    			simpGegenstand[i].loadXmlElement( tmpElements.get(i) );
    		}
		}
		
		/**
		 * Schreib alle Daten in ein XML-Element mit dem gegebenem Tag-Namen
		 * @param tagName Der Name des Tags
		 * @return xml-Element mit allen Daten.
		 */
        public Element writeXmlElement(String tagName){
        	Element xmlElement = new Element(tagName);
        	Element tmpElement;
        	
        	// Schreiben der Anzahl
        	if ( anzahl != 1 ) {
        		xmlElement.addAttribute(new Attribute("anzahl", Integer.toString(anzahl)));
        	}
        	
        	// Schreiben der Links
        	for (int i = 0; i < links.length; i++) {
        		xmlElement.appendChild(links[i].writeXmlElement("ausruestungLink"));
        	}
        	
        	// Schreiben der simplen Gegenstände, die direkt angegeben werden
        	for (int i = 0; i < simpGegenstand.length; i++) {
        		xmlElement.appendChild(simpGegenstand[i].writeXmlElement("ausruestungNeu"));
        	}
        	
        	return xmlElement;
        }
    }
}
