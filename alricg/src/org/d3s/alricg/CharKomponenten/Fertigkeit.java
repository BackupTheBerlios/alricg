/*
 * Created 26. Dezember 2004 / 23:36:19
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.Links.Voraussetzung;
/**
 * <b>Beschreibung:</b><br>
 * Fasst gemeinsamkeiten von Vor-/ Nachteilen und Sonderfertigkeiten zusammmen und 
 * bildet die Grundlage f�r diese.
 * @author V.Strelow
 */
public abstract class Fertigkeit extends CharElement {
	private boolean isWaehlbar; // Nicht w�hlbare k�nnen nur �ber die Herkunft erlangt werden
	private Voraussetzung voraussetzung; // Es mu� die Voraussetzungen gelten! 
	private boolean hatText; // Gibt es noch einen Beschreibenen Text zu der Fertigkeit?
	private String fuerWelcheChars;
	private int additionsWert;	// z.B. R�stungsgew�hung I = 1 und RG II = 2. Somit ergibt zwei mal	RG I --> ein mal RG II (sieht AH S.10)
	private String additionsID;  // "Familie" von Fertigkeiten, die aufaddiert werden z.B. R�stungsgew�hung I und RG II
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
	public String getFuerWelcheChars() {
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
	public Voraussetzung getVoraussetzungen() {
		return voraussetzung;
	}
	
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen n�tigen angaben
     */
	protected void loadXmlElementFertigkeit(Element xmlElement){
    	// TODO implement
    }
    
    /**
     * Dient zur Speicherung (also f�r den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt" und zugef�gt, zu einem 
     * �bergebenen XML-Elemnet
     * @param xmlElement Das Xml-Element zu dem die Daten hinzugef�gt werden
     * @return Ein Xml-Element mit allen n�tigen Angaben.
     */
    protected Element writeXmlElementFertigkeit(Element xmlElement) {
    	//TODO implement
    	return null;
    }
}
