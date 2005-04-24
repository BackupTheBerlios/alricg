/*
 * Created 26. Dezember 2004 / 23:36:19
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.links.Voraussetzung;
import org.d3s.alricg.controller.ProgAdmin;
/**
 * <b>Beschreibung:</b><br>
 * Fasst gemeinsamkeiten von Vor-/ Nachteilen und Sonderfertigkeiten zusammmen und 
 * bildet die Grundlage f�r diese.
 * @author V.Strelow
 */
public abstract class Fertigkeit extends CharElement {
	private Werte.CharArten[] fuerWelcheChars; // Welche Chars diese Fertigkeit w�hlen k�nnen
	private Voraussetzung voraussetzung; // Es mu� die Voraussetzungen gelten! 
	private boolean hatText = false; // Gibt es noch einen Beschreibenen Text zu der Fertigkeit?
	private boolean isWaehlbar = true; // Nicht w�hlbare k�nnen nur �ber die Herkunft erlangt werden
	private String additionsID;  // "Familie" von Fertigkeiten, die aufaddiert werden z.B. R�stungsgew�hung I und RG II
	private int additionsWert = KEIN_WERT;	// z.B. R�stungsgew�hung I = 1 und RG II = 2. Somit ergibt zwei mal	RG I --> ein mal RG II (sieht AH S.10)
	private int gpKosten;

    /**
     * Eine additionsID kennzeichent solche Fertigkeiten, die Zusammengeh�ren und bei mehrfachen erlangen
     * durch Rasse, Kultur, ... zusammengefasst werden. Zu was die Fertigkeiten zusammengefast werden wird 
     * �ber den additionsWert festgelegt.
     * Beispiel: R�stungsgew�hung I (additiosnWert: 1) und RG II (aW: 2). Somit ergibt 2x RG I --> 1x RG II
     * @return Liefert die additionsID.
     */
    public String getAdditionsID() {
        return additionsID;
    }
    /**
     * @see getAdditionsID()
     * @return Liefert das Attribut additionsWert.
     */
    public int getAdditionsWert() {
        return additionsWert;
    }
    
    /**
     * @return Liefert die Kosten in Generierungspunkten.
     */
    public int getGpKosten() {
        return gpKosten;
    }
    
	/**
	 * @return Liefert das Attribut fuerWelcheChars.
	 */
	public Werte.CharArten[] getFuerWelcheChars() {
		return fuerWelcheChars;
	}
	/**
	 * Fertigkeiten mit Text ben�tigen noch einen Text wenn sie gew�hlt werden: z.B.
	 * Vorurteile gegen "Orks" oder Verpflichtungen gegen�ber "Praioskirche".
	 * @return true - Die Fertigkeit ben�tigt noch die Angabe eines Textes, ansonsten "false".
	 */
	public boolean isHatText() {
		return hatText;
	}
	/**
	 * Fertigkeiten die "Nicht w�hlbar" sind k�nnen nur �ber die Herkunft erlangt werden!
	 * D.h. diese stehen NICHT zur normalen Auswahl.
	 * @return true - Die Fertigkeit ist normal w�hlbar, ansonsten ist die Fertigk. NICHT w�hlbar (false)
	 */
	public boolean isWaehlbar() {
		return isWaehlbar;
	}
	/**
	 * Es mu� nur die Voraussetzungen gelten, damit diese Fertigkeit vom Charakter gew�hlt werden darf.
	 * @return Liefert die voraussetzungen.
	 */
	public Voraussetzung getVoraussetzung() {
		return voraussetzung;
	}
	
	/**
	 * @param additionsID Setzt das Attribut additionsID.
	 */
	public void setAdditionsID(String additionsID) {
		this.additionsID = additionsID;
	}
	/**
	 * @param additionsWert Setzt das Attribut additionsWert.
	 */
	public void setAdditionsWert(int additionsWert) {
		this.additionsWert = additionsWert;
	}
	/**
	 * @param fuerWelcheChars Setzt das Attribut fuerWelcheChars.
	 */
	public void setFuerWelcheChars(Werte.CharArten[] fuerWelcheChars) {
		this.fuerWelcheChars = fuerWelcheChars;
	}
	/**
	 * @param gpKosten Setzt das Attribut gpKosten.
	 */
	public void setGpKosten(int gpKosten) {
		this.gpKosten = gpKosten;
	}
	/**
	 * @param hatText Setzt das Attribut hatText.
	 */
	public void setHatText(boolean hatText) {
		this.hatText = hatText;
	}
	/**
	 * @param isWaehlbar Setzt das Attribut isWaehlbar.
	 */
	public void setWaehlbar(boolean isWaehlbar) {
		this.isWaehlbar = isWaehlbar;
	}
	/**
	 * @param voraussetzung Setzt das Attribut voraussetzung.
	 */
	public void setVoraussetzung(Voraussetzung voraussetzung) {
		this.voraussetzung = voraussetzung;
	}
	
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	Elements tmpElements;
    	
    	// Auslesen der Additions-Werte
    	if (xmlElement.getFirstChildElement("addition") != null) {
    		additionsID = xmlElement.getFirstChildElement("addition")
    								.getAttributeValue("id");
    		try {
        		additionsWert = Integer.parseInt(xmlElement
        				.getFirstChildElement("addition")
						.getAttributeValue("wertigkeit"));
    		} catch (NumberFormatException exc) {
    			// TODO Richtige Fehlermeldung einbauen
    			ProgAdmin.logger.severe("Fehler beim Auslesen einer Nummer: " + exc);
    		}
    	}
    	
    	// Auslesen, ob diese Fertigkeit noch Text ben�tigt
    	if ( xmlElement.getFirstChildElement("hatText") != null ) {
    		
    		// Sicherstellen des Wertebereiches
    		assert xmlElement.getFirstChildElement("hatText").getValue().equals("true") 
    			|| xmlElement.getFirstChildElement("hatText").getValue().equals("false");
    		
    		if ( xmlElement.getFirstChildElement("hatText").getValue().equals("true") ) {
    			hatText = true;
    		} // false ist default
    	}
    	
    	// Auslesen, ob normal W�hlbar oder nur �ber Herkunft o.�. zu bekommen
    	if ( xmlElement.getFirstChildElement("istWaehlbar") != null ) {
    		assert xmlElement.getFirstChildElement("istWaehlbar").getValue().equals("true")
    			|| xmlElement.getFirstChildElement("istWaehlbar").getValue().equals("false");
    		
    		if ( xmlElement.getFirstChildElement("istWaehlbar").getValue().equals("false") ) {
    			isWaehlbar = false;
    		} // true ist default
    	}
    	
    	// Auslesen f�r welche CharArten die Fertigkeit ist
    	tmpElements = xmlElement.getChildElements("fuerWelcheChars");
    	fuerWelcheChars = new Werte.CharArten[tmpElements.size()];
    	for (int i = 0; i < tmpElements.size(); i++) {
    		fuerWelcheChars[i] = Werte.getCharArtenByXmlValue(
    			tmpElements.get(i).getValue()
    		);
    	}
    	
    	// Auslesen der Voraussetzungen
    	if ( xmlElement.getFirstChildElement("voraussetzungen") != null ) {
    		voraussetzung = new Voraussetzung(this);
    		voraussetzung.loadXmlElement(xmlElement.getFirstChildElement("voraussetzungen"));
    	}
    	
    	// Auslesen der GP Kosten
		try {
			gpKosten = Integer.parseInt(xmlElement.getAttributeValue("gp"));
		} catch (NumberFormatException exc) {
			// TODO Richtige Fehlermeldung einbauen
			ProgAdmin.logger.severe("Fehler beim Auslesen einer Nummer: " + exc);
		}

    }
    
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	// Schreiben der additions-Werte
    	if ( additionsWert != KEIN_WERT ) {
    		tmpElement = new Element("addition");
    		tmpElement.addAttribute(new Attribute("id", additionsID));
    		tmpElement.addAttribute(new Attribute("wertigkeit", 
    								Integer.toString(additionsWert)));
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben, ob die Fertigkeit einen Text ben�tigt
    	if (hatText) {
    		tmpElement = new Element("hatText");
    		tmpElement.appendChild("true");
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben, ob das Element normal w�hlbar ist
    	if (!isWaehlbar) {
    		tmpElement = new Element("istWaehlbar");
    		tmpElement.appendChild("false");
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben der zul�ssigen CharArten
    	for (int i = 0; i < fuerWelcheChars.length; i++) {
    		tmpElement = new Element("fuerWelcheChars");
    		tmpElement.appendChild(fuerWelcheChars[i].getXmlValue());
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben der Voraussetzungen
    	if ( voraussetzung != null ) {
    		xmlElement.appendChild(voraussetzung.writeXmlElement("voraussetzungen"));
    	}
    	
    	// Schreiben der GP Kosten
    	xmlElement.addAttribute(new Attribute("gp", Integer.toString(gpKosten)));
    	
    	return xmlElement;
    }
}
