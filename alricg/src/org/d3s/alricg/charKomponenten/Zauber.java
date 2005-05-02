/*
 * Created 23. Dezember 2004 / 14:53:55
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.Werte.MagieMerkmal;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Zauber extends Faehigkeit {
	private MagieMerkmal[] merkmale;
	private Verbreitung[] verbreitung; // Welche Repräsentationen den Zauber können
	private String probenModi = ""; // Modis auf die Probe "+MR" / "+Modi"
	private String zauberdauer;
	private String aspKosten;
	private String ziel;
	private String reichweite;
	private String wirkungsdauer;

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#getCharKomponente()
	 */
	public CharKomponente getCharKomponente() {
		return CharKomponente.zauber;
	}
	
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
	
	/**
	 * @return Liefert einen kompletten String mit allen Verbreitungen 
	 * 				als Abkürzungen.
	 */
	public String getVerbreitungAbkText() {
		StringBuffer buffer = verbreitung[0].getAbkuerzungText();
		
		for (int i = 1; i < verbreitung.length; i++) {
			buffer.append(", ").append(verbreitung[i].getAbkuerzungText());
		}
		
		return buffer.toString();
	}
	
	/**
	 * @return Liefert die Verbreitungen in der der Zauber verhanden ist
	 */
	public Verbreitung[] getVerbreitung() {
		return verbreitung;
	}	
	
	/**
	 * @return Liefert das Attribut aspKosten.
	 */
	public String getAspKosten() {
		return aspKosten;
	}
	/**
	 * @param aspKosten Setzt das Attribut aspKosten.
	 */
	public void setAspKosten(String aspKosten) {
		this.aspKosten = aspKosten;
	}
	/**
	 * @return Liefert das Attribut probenModi.
	 */
	public String getProbenModi() {
		return probenModi;
	}
	/**
	 * @param probenModi Setzt das Attribut probenModi.
	 */
	public void setProbenModi(String probenModi) {
		this.probenModi = probenModi;
	}
	/**
	 * @return Liefert das Attribut reichweite.
	 */
	public String getReichweite() {
		return reichweite;
	}
	/**
	 * @param reichweite Setzt das Attribut reichweite.
	 */
	public void setReichweite(String reichweite) {
		this.reichweite = reichweite;
	}
	/**
	 * @return Liefert das Attribut wirkungsdauer.
	 */
	public String getWirkungsdauer() {
		return wirkungsdauer;
	}
	/**
	 * @param wirkungsdauer Setzt das Attribut wirkungsdauer.
	 */
	public void setWirkungsdauer(String wirkungsdauer) {
		this.wirkungsdauer = wirkungsdauer;
	}
	/**
	 * @return Liefert das Attribut zauberdauer.
	 */
	public String getZauberdauer() {
		return zauberdauer;
	}
	/**
	 * @param zauberdauer Setzt das Attribut zauberdauer.
	 */
	public void setZauberdauer(String zauberdauer) {
		this.zauberdauer = zauberdauer;
	}
	/**
	 * @return Liefert das Attribut ziel.
	 */
	public String getZiel() {
		return ziel;
	}
	/**
	 * @param ziel Setzt das Attribut ziel.
	 */
	public void setZiel(String ziel) {
		this.ziel = ziel;
	}
	/**
	 * @param merkmale Setzt das Attribut merkmale.
	 */
	public void setMerkmale(MagieMerkmal[] merkmale) {
		this.merkmale = merkmale;
	}
	/**
	 * @param verbreitung Setzt das Attribut verbreitung.
	 */
	public void setVerbreitung(Verbreitung[] verbreitung) {
		this.verbreitung = verbreitung;
	}
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
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
    	
    	// Auslesen der Modis auf die Probe
    	if (xmlElement.getFirstChildElement("probenModi") != null) {
    		probenModi = xmlElement.getFirstChildElement("probenModi").getValue();
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
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("zauber");
    	
    	// Schreiben der Merkmale
    	for (int i = 0; i < merkmale.length; i++) {
    		tmpElement = new Element("merkmale");
    		tmpElement.appendChild(merkmale[i].getXmlValue());
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben der Repräsentationen
    	for (int i = 0; i < verbreitung.length; i++) {
    		xmlElement.appendChild(verbreitung[i].writeXmlElement());
    	}
    	
    	// Schreiben der Modis auf die Probe
    	if (probenModi.length() > 0) {
	    	tmpElement = new Element("probenModi");
	    	tmpElement.appendChild(probenModi);
	    	xmlElement.appendChild(tmpElement);
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
		
		/**
		 * @return Den kompleten Text mit den Abkürzungen für die Repräsentationen
		 */
		public StringBuffer getAbkuerzungText() {
			StringBuffer buffer = new StringBuffer(repraesentation.getAbkuerzung());
			
			if (bekanntBei != null) {
				buffer.append("(")
					.append(bekanntBei.getAbkuerzung())
					.append(")");
			}
			buffer.append(wert);
			
			return buffer;			
		}
		
    	/* (non-Javadoc) Methode überschrieben
         * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
         */
        public void loadXmlElement(Element xmlElement) {
        	
        	// Einlesen des "Bekannt bei" Wertes
        	if ( xmlElement.getAttribute("bekanntBei") != null ) {
        		bekanntBei = (Repraesentation) ProgAdmin.charKompAdmin.getCharElement(
        				xmlElement.getAttributeValue("bekanntBei"),
        				CharKomponente.repraesentation
        		);
        	}
        	
        	// Einlesen der Repraesentation
        	repraesentation = (Repraesentation) ProgAdmin.charKompAdmin.getCharElement(
    				xmlElement.getAttributeValue("repraesentation"),
    				CharKomponente.repraesentation
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
         * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
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
    }
}
