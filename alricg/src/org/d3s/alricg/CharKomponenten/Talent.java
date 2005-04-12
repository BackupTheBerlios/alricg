/*
 * Created 23. Dezember 2004 / 14:53:48
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import java.util.SortedMap;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.CharKomponenten.Links.Voraussetzung;
import org.d3s.alricg.Controller.Library;
import org.d3s.alricg.Controller.ProgAdmin;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Talent extends Faehigkeit {
	
	public enum Art {
		basis("basis"), 
		spezial("spezial"), 
		beruf("beruf");
		private String xmlValue; // XML-Tag des Elements
		private String bezeichner; // Name der Angezeigt wird
		
		private Art(String xmlValue) {
			this.xmlValue = xmlValue;
			bezeichner = Library.getShortTxt(xmlValue);
		}
		
		public String getXmlValue() {
			return xmlValue;
		}
		
		public String toString() {
			return bezeichner;
		}
	}
	public enum Sorte {
		kampf("kampf"), 
		koerper("koerper"), 
		gesellschaft("gesellschaft"), 
		natur("natur"), 
		wissen("wissen"), 
		handwerk("handwerk");
		private String xmlValue; // XML-Tag des Elements
		private String bezeichner; // Name der Angezeigt wird
		
		private Sorte(String xmlValue) {
			this.xmlValue = xmlValue;
			bezeichner = Library.getShortTxt(xmlValue);
		}
		
		public String getXmlValue() {
			return xmlValue;
		}
		
		public String toString() {
			return bezeichner;
		}
	}
	
	private String[] spezialisierungen;
	private Art art;
    private Sorte sorte;
    private int abWert; // Bezieht sich auf Voraussetzung
    private TalentVoraussetzung voraussetzung;
    
	/**
	 * Konstruktur id; beginnt mit "TAL-" für Talent
	 * @param id Systemweit eindeutige id
	 */
	public Talent(String id) {
		setId(id);
	}
    
	/**
	 * @return Liefert das Attribut art.
	 */
	public Art getArt() {
		return art;
	}
	/**
	 * @return Liefert das Attribut sorte.
	 */
	public Sorte getSorte() {
		return sorte;
	}
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	Elements tmpElements;
    	String value;
    	
    	// Auslesen der Art
    	value = xmlElement.getFirstChildElement("einordnung").getAttributeValue("art");
    	if ( value.equals(Art.basis.getXmlValue()) ) {
    		art = Art.basis;
    	} else if ( value.equals(Art.spezial.getXmlValue()) ) {
    		art = Art.spezial;
    	} else if ( value.equals(Art.beruf.getXmlValue()) ) {
    		art = Art.beruf;
    	} else {
    		ProgAdmin.logger.severe("Richtige Art nicht gefunden!");
    	}
    	
    	// Auslesen der Sorte
    	value = xmlElement.getFirstChildElement("einordnung").getAttributeValue("sorte");
    	if ( value.equals(Sorte.kampf.getXmlValue()) ) {
    		sorte = Sorte.kampf;
    	} else if ( value.equals(Sorte.koerper.getXmlValue()) ) {
    		sorte = Sorte.koerper;
    	} else if ( value.equals(Sorte.gesellschaft.getXmlValue()) ) { 
    		sorte = Sorte.gesellschaft;
    	} else if ( value.equals(Sorte.natur.getXmlValue()) ) { 
    		sorte = Sorte.natur;
    	} else if ( value.equals(Sorte.wissen.getXmlValue()) ) { 
    		sorte = Sorte.wissen;
    	} else if ( value.equals(Sorte.handwerk.getXmlValue()) ) { 
    		sorte = Sorte.handwerk;
    	} else {
    		ProgAdmin.logger.severe("Richtige Sorte nicht gefunden!");
    	}
    	
    	// Einlesen der spezialisierung
    	if ( xmlElement.getFirstChildElement("spezialisierungen") != null ) {
    		tmpElements = xmlElement.getFirstChildElement("spezialisierungen")
    								.getChildElements("name");
    		spezialisierungen = new String[tmpElements.size()];
    		for (int i = 0; i < spezialisierungen.length; i++) {
    			spezialisierungen[i] =	tmpElements.get(i).getValue();
    		}
    	}
    	
    	// Auslesen der Voraussetzungen für dieses Talent
    	if ( xmlElement.getFirstChildElement("voraussetzungTalent") != null ) {
    		voraussetzung = new TalentVoraussetzung(this);
    		voraussetzung.loadXmlElement(xmlElement.getFirstChildElement("voraussetzungTalent"));
    	}
    }

    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("talent");
    	
    	// Schreiben der Art & Sorte des Talents
    	tmpElement = new Element("einordnung");
    	tmpElement.addAttribute( new Attribute("art", art.getXmlValue()) );
    	tmpElement.addAttribute( new Attribute("sorte", sorte.getXmlValue()) );
    	xmlElement.appendChild(tmpElement);
    	
    	// Schreiben der Spezialisierungen
    	if ( spezialisierungen != null ) {
    		tmpElement = new Element("spezialisierungen");
    		xmlElement.appendChild(tmpElement);
    		for (int i = 0; i < spezialisierungen.length; i++) {
    			tmpElement = new Element("name");
    			tmpElement.appendChild(spezialisierungen[i]);
    			xmlElement.getFirstChildElement("spezialisierungen").appendChild(tmpElement);
    		}
    	}
    	
    	// Schreiben der Voraussetzungen
    	if ( voraussetzung != null) {
    		xmlElement.appendChild(voraussetzung.writeXmlElement("voraussetzungTalent"));
    	}
    	
    	return xmlElement;
    }
    
    /**
     * <u>Beschreibung:</u><br> 
     * Bei manchen Talenten gilt die Voraussetzung erst am einem bestimmten Wert
     * (typischer Weise 10). Dieser Wert kann hier angegeben werden!
     * @author V. Strelow
     */
    public class TalentVoraussetzung extends Voraussetzung {
    	private int abWert = 1; // Ab welchem Wert diese Voraussetzung gilt
    	
		/**
		 * @param quelle Zu welchem Talent diese Voraussezung gehört
		 */
		public TalentVoraussetzung(CharElement quelle) {
			super(quelle);
		}
    	
		/* (non-Javadoc) Methode überschrieben
		 * @see org.d3s.alricg.CharKomponenten.Links.Voraussetzung#loadXmlElement(nu.xom.Element)
		 */
		public void loadXmlElement(Element xmlElement) {
			super.loadXmlElement(xmlElement);
			
			if ( xmlElement.getAttribute("abWert") != null ) {
				try {
					abWert = Integer.parseInt(xmlElement.getAttributeValue("abWert"));
				} catch (NumberFormatException exc) {
					ProgAdmin.logger.severe("Xml-Tag konnte nicht in Nummer umgewandelt werden: " + exc);
				}
			}
		}
		
		/* (non-Javadoc) Methode überschrieben
		 * @see org.d3s.alricg.CharKomponenten.Links.Voraussetzung#writeXmlElement(java.lang.String)
		 */
		public Element writeXmlElement(String tagName) {
			Element xmlElement = super.writeXmlElement(tagName);
			
			// Schreiben ab wann diese Voraussetzung gilt
			if ( abWert != 1 ) {
				xmlElement.addAttribute(new Attribute("abWert", Integer.toString(abWert)));
			}
			
			return xmlElement;
		}
    }
}
