/*
 * Created 23. Dezember 2004 / 12:53:19
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;
import org.d3s.alricg.prozessor.FormelSammlung;

/**
 * <b>Beschreibung:</b><br>
 * Repräsentiert eine Sonderfertigkeit.
 * @author V.Strelow
 */
public class Sonderfertigkeit extends Fertigkeit {
	
	public enum Art {
		allgemein("allgemein"),
		waffenloskampf("waffenlosKampf"), 
		bewaffnetkampf("bewaffnetKampf"), 
		magisch("magisch"), 
		geweiht("geweiht"), 
		schamanisch("schamanisch"), 
		sonstige("sonstige");
		private String xmlValue; // XML-Tag des Elements
		
		private Art(String xmlValue) {
			this.xmlValue = xmlValue;
		}
		
		public String getXmlValue() {
			return xmlValue;
		}
	}

	private int apKosten = KEIN_WERT; // falls sich AP nicht aus GP berechnen lassen
	private Art art;
	private int permAsp = 0, permKa = 0, permLep = 0; // Permanente Kosten

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#getCharKomponente()
	 */
	public CharKomponente getCharKomponente() {
		return CharKomponente.sonderfertigkeit;
	}
	
	/**
	 * Konstruktur; id beginnt mit "SF-" für Sonderfertigkeit
	 * @param id Systemweit eindeutige id
	 */
	public Sonderfertigkeit(String id) {
		setId(id);
	}
	
	/**
	 * @return Liefert das Attribut ap - die Kosten für diese SF in
	 * Abenteuerpunkten.
	 */
	public int getApKosten() {
		return apKosten;
	}	
	
	/**
	 * @return Liefert das Attribut art.
	 */
	public Art getArt() {
		return art;
	}

	
	
	/**
	 * @return Liefert das Attribut permAsp.
	 */
	public int getPermAsp() {
		return permAsp;
	}
	/**
	 * @param permAsp Setzt das Attribut permAsp.
	 */
	public void setPermAsp(int permAsp) {
		this.permAsp = permAsp;
	}
	/**
	 * @return Liefert das Attribut permKa.
	 */
	public int getPermKa() {
		return permKa;
	}
	/**
	 * @param permKa Setzt das Attribut permKa.
	 */
	public void setPermKa(int permKa) {
		this.permKa = permKa;
	}
	/**
	 * @return Liefert das Attribut permLep.
	 */
	public int getPermLep() {
		return permLep;
	}
	/**
	 * @param permLep Setzt das Attribut permLep.
	 */
	public void setPermLep(int permLep) {
		this.permLep = permLep;
	}
	/**
	 * @param apKosten Setzt das Attribut apKosten.
	 */
	public void setApKosten(int apKosten) {
		this.apKosten = apKosten;
	}
	/**
	 * @param art Setzt das Attribut art.
	 */
	public void setArt(Art art) {
		this.art = art;
	}
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
    	try {
//    		 Auslesen der permanenten AsP Kosten
    		if ( xmlElement.getFirstChildElement("permAsP") != null ) {
	    		permAsp = Integer.parseInt(xmlElement
	    							.getFirstChildElement("permAsP").getValue());
	    	}
    		
    		// Auslesen der permanenten Karmaenergie Kosten
	    	if ( xmlElement.getFirstChildElement("permKa") != null ) {
	    		permKa = Integer.parseInt(xmlElement
	    							.getFirstChildElement("permKa").getValue());
	    	}
	    	
	    	// Auslesen der permanenten Lebensenergie Kosten
	    	if ( xmlElement.getFirstChildElement("permLeP") != null ) {
	    		permLep = Integer.parseInt(xmlElement
	    							.getFirstChildElement("permLeP").getValue());
	    	}
	    	
	    	// Auslesen der AP kosten (nur nötig, falls abweichend von GP x 50)
	    	if ( xmlElement.getAttribute("ap") != null ) {
	    		apKosten = Integer.parseInt(xmlElement.getAttributeValue("ap"));
	    	}
    	} catch(NumberFormatException exc) {
    		ProgAdmin.logger.severe("Fehler bei Umwandlung in Zahl: " + exc.toString());
    	}
    	
    	// Auslesen der Art der Sonderfertigkeit
    	for (int i = 0; i < Art.values().length; i++) {
    		if (Art.values()[i].getXmlValue().equals(xmlElement
    							.getFirstChildElement("art").getValue())) 
    		{
    				art = Art.values()[i];
    				break; // Gefunden, abbrechen
    		}
    	}
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("sonderf");
    	
    	// Hinzufügen der AP-Kosten
    	if (   apKosten != KEIN_WERT 
    		&& apKosten != FormelSammlung.berechneSfAp(this.getGpKosten()) ) 
    	{
    		xmlElement.addAttribute(new Attribute("ap", Integer.toString(apKosten)));
    	}
    	
    	// Hinzufügen der permanetnen ASP Kosten
    	if ( permAsp != 0 ) {
    		tmpElement = new Element("permAsP");
    		tmpElement.appendChild(Integer.toString(permAsp));
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Hinzufügen der permanetnen Karmaenergie Kosten
    	if ( permKa != 0 ) {
    		tmpElement = new Element("permKa");
    		tmpElement.appendChild(Integer.toString(permKa));
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Hinzufügen der permanetnen Lebensenergie Kosten
    	if ( permLep != 0 ) {
    		tmpElement = new Element("permLeP");
    		tmpElement.appendChild(Integer.toString(permLep));
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Hinzufügen der Art dieser Sonderf
    	tmpElement = new Element("art");
    	tmpElement.appendChild(art.getXmlValue());
    	xmlElement.appendChild(tmpElement);
    	
    	return xmlElement;
    }

}
