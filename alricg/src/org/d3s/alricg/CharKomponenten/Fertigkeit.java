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
 * bildet die Grundlage für diese.
 * @author V.Strelow
 */
public abstract class Fertigkeit extends CharElement {
	private boolean isWaehlbar; // Nicht wählbare können nur über die Herkunft erlangt werden
	private Voraussetzung voraussetzung; // Es muß die Voraussetzungen gelten! 
	private boolean hatText; // Gibt es noch einen Beschreibenen Text zu der Fertigkeit?
	private String fuerWelcheChars;
	private int additionsWert;	// z.B. Rüstungsgewöhung I = 1 und RG II = 2. Somit ergibt zwei mal	RG I --> ein mal RG II (sieht AH S.10)
	private String additionsID;  // "Familie" von Fertigkeiten, die aufaddiert werden z.B. Rüstungsgewöhung I und RG II
	private int gpKosten;

    /**
     * Eine additionsID kennzeichent solche Fertigkeiten, die Zusammengehören und bei mehrfachen erlangen
     * durch Rasse, Kultur, ... zusammengefasst werden. Zu was die Fertigkeiten zusammengefast werden wird 
     * über den additionsWert festgelegt.
     * Beispiel: Rüstungsgewöhung I (additiosnWert: 1) und RG II (aW: 2). Somit ergibt 2x RG I --> 1x RG II
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
	 * Fertigkeiten mit Text benötigen noch einen Text wenn sie gewählt werden: z.B.
	 * Vorurteile gegen "Orks" oder Verpflichtungen gegenüber "Praioskirche".
	 * @return true - Die Fertigkeit benötigt noch die Angabe eines Textes, ansonsten "false".
	 */
	public boolean isHatText() {
		return hatText;
	}
	/**
	 * Fertigkeiten die "Nicht wählbar" sind können nur über die Herkunft erlangt werden!
	 * D.h. diese stehen NICHT zur normalen Auswahl.
	 * @return true - Die Fertigkeit ist normal wählbar, ansonsten ist die Fertigk. NICHT wählbar (false)
	 */
	public boolean isWaehlbar() {
		return isWaehlbar;
	}
	/**
	 * Es muß nur die Voraussetzungen gelten, damit diese Fertigkeit vom Charakter gewählt werden darf.
	 * @return Liefert die voraussetzungen.
	 */
	public Voraussetzung getVoraussetzungen() {
		return voraussetzung;
	}
	
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen nötigen angaben
     */
	protected void loadXmlElementFertigkeit(Element xmlElement){
    	// TODO implement
    }
    
    /**
     * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt" und zugefügt, zu einem 
     * übergebenen XML-Elemnet
     * @param xmlElement Das Xml-Element zu dem die Daten hinzugefügt werden
     * @return Ein Xml-Element mit allen nötigen Angaben.
     */
    protected Element writeXmlElementFertigkeit(Element xmlElement) {
    	//TODO implement
    	return null;
    }
}
