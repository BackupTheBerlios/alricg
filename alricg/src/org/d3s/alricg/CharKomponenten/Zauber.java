/*
 * Created 23. Dezember 2004 / 14:53:55
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.CharKomponenten.Werte.MagieMerkmal;
import org.d3s.alricg.Controller.ProgAdmin;
import org.d3s.alricg.Controller.CharKompAdmin.CharKomponenten;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Zauber extends Faehigkeit {
	private MagieMerkmal[] merkmale;
	private Verbreitung[] verbreitung; // Welche Repräsentationen den Zauber können
	private String zauberdauer;
	private String aspKosten;
	private String ziel;
	private String reichweite;
	private String wirkungsdauer;

	/**
	 * Konstruktur; id beginnt mit "ZAU-" für Zauber
	 * @param id Systemweit eindeutige id
	 */
	public Zauber(String id) {
		setId(id);
	}
	
	/**
	 * @return Liefert das Attribut merkmale.
	 */
	public MagieMerkmal[] getMerkmale() {
		return merkmale;
	}
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	Elements tmpElements;
    	
    	// Auslesen der Merkmale
    	tmpElements = xmlElement.getChildElements("merkmale");
    	merkmale = new MagieMerkmal[tmpElements.size()];
    	for (int i = 0; i < merkmale.length; i++) {
    		merkmale[i] = Werte.getMagieMerkmalByXmlValue(tmpElements.get(i).getValue());
    	}
    	
    	// Auslesen der repraesentationen
    	tmpElements = xmlElement.getChildElements("verbreitung");
    	verbreitung = new Verbreitung[tmpElements.size()];
    	for (int i = 0; i < verbreitung.length; i++) {
    		verbreitung[i] = new Verbreitung();
    		verbreitung[i].loadXmlElement(tmpElements.get(i));
    	}
    	
    	// Auslesen der zauberdauer
    	zauberdauer = xmlElement.getFirstChildElement("zauberdauer").getValue();
    	
    	// Auslesen der aspKosten
    	aspKosten = xmlElement.getFirstChildElement("aspKosten").getValue();
    	
    	// Auslesen des ziels
    	ziel = xmlElement.getFirstChildElement("ziel").getValue();
    	
    	// Auslesen der reichweite
    	reichweite  = xmlElement.getFirstChildElement("reichweite").getValue();
    	
    	// Auslesen der Wirkungsdauer
    	wirkungsdauer = xmlElement.getFirstChildElement("wirkungsdauer").getValue();
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	// Schreiben der Merkmale
    	for (int i = 0; i < merkmale.length; i++) {
    		tmpElement = new Element("merkmale");
    		tmpElement.appendChild(merkmale[i].getXmlValue());
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben der Repräsentationen
    	for (int i = 0; i < merkmale.length; i++) {
    		xmlElement.appendChild(verbreitung[i].writeXmlElement());
    	}
    	
    	// Schreiben der Zauberdauer
    	tmpElement = new Element("zauberdauer");
    	tmpElement.appendChild(zauberdauer);
    	xmlElement.appendChild(tmpElement);
    	
    	// Schreiben der Asp Kosten
    	tmpElement = new Element("aspKosten");
    	tmpElement.appendChild(aspKosten);
    	xmlElement.appendChild(tmpElement);
    	
    	// Schreiben des Ziels
    	tmpElement = new Element("ziel");
    	tmpElement.appendChild(ziel);
    	xmlElement.appendChild(tmpElement);
    	
    	// Schreiben der Reichweite
    	tmpElement = new Element("reichweite");
    	tmpElement.appendChild(reichweite);
    	xmlElement.appendChild(tmpElement);
    	
    	// Schreiben der Wirkungsweise
    	tmpElement = new Element("wirkungsdauer");
    	tmpElement.appendChild(wirkungsdauer);
    	xmlElement.appendChild(tmpElement);
    	
    	return xmlElement;
    }
    
    /**
     * <u>Beschreibung:</u><br> 
     * In welchen Repräsentationen welchen Gruppierungen dieser Zauber bekannt ist.
     * @author V. Strelow
     */
    public class Verbreitung {
    	private Repraesentation bekanntBei; // Bei welcher Gruppe der Zauber bekannt ist
    	private Repraesentation repraesentation; // In welcher Repräsentation der Zauber bekannt ist
    	private int wert; // Der Wert des bekanntheit
       
    	/*	So kann z.N. "Hex(Mag)2" nachgebildet werden - Hexen bekannt in der 
    	 * Repräsentation der Magier mit dem Wert 2 
    	 */
       	
    	/* (non-Javadoc) Methode überschrieben
         * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
         */
        public void loadXmlElement(Element xmlElement) {
        	
        	// Einlesen des "Bekannt bei" Wertes
        	if ( xmlElement.getAttribute("bekanntBei") != null ) {
        		bekanntBei = (Repraesentation) ProgAdmin.charKompAdmin.getCharElement(
        				xmlElement.getAttributeValue("bekanntBei"),
        				CharKomponenten.repraesentation
        		);
        	}
        	
        	// Einlesen der Repraesentation
        	repraesentation = (Repraesentation) ProgAdmin.charKompAdmin.getCharElement(
    				xmlElement.getAttributeValue("repraesentation"),
    				CharKomponenten.repraesentation
        	);
        	
        	// Einlesen des Wertes
        	try {
        		wert = Integer.parseInt(xmlElement.getAttributeValue("wert"));
        	} catch (NumberFormatException exc) {
        		ProgAdmin.logger.severe("String konnte nicht in Nummer umgewandelt werden: " 
        										+ exc.toString());
        	}
        }
        
        /* (non-Javadoc) Methode überschrieben
         * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
         */
        public Element writeXmlElement(){
        	Element xmlElement = new Element("verbreitung");
        	
        	// Schreiben des "bekanntBei"
        	if ( bekanntBei != null && !bekanntBei.equals(repraesentation) ) {
        		xmlElement.addAttribute(new Attribute("bekanntBei", bekanntBei.getId()));
        	}
        	
        	// Schreiben der Repräsentation
        	xmlElement.addAttribute(new Attribute("repraesentation", repraesentation.getId()));
        	
        	// Schreibe den Wert
        	xmlElement.addAttribute(new Attribute("wert", Integer.toString(wert)));
        	
        	return xmlElement;
        }
    	
		/**
		 * @return Liefert das Attribut bekanntBei.
		 */
		public Repraesentation getBekanntBei() {
			if ( bekanntBei == null ) {
				return repraesentation;
			}
			return bekanntBei;
		}
		/**
		 * @return Liefert das Attribut repraesentation.
		 */
		public Repraesentation getRepraesentation() {
			return repraesentation;
		}
		/**
		 * @return Liefert das Attribut wert.
		 */
		public int getWert() {
			return wert;
		}
    }
}
