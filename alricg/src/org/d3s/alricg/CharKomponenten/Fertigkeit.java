/*
 * Created 26. Dezember 2004 / 23:36:19
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import org.d3s.alricg.CharKomponenten.Links.Voraussetzung;
/**
 * <b>Beschreibung:</b><br>
 * Fasst gemeinsamkeiten von Vor-/ Nachteilen und Sonderfertigkeiten zusammmen und 
 * bildet die Grundlage f�r diese.
 * @author V.Strelow
 */
public abstract class Fertigkeit extends CharElement {
	private boolean isWaehlbar; // Nicht w�hlbare k�nnen nur �ber die Herkunft erlangt werden
	private Voraussetzung[] voraussetzungen; // Es mu� nur EINE der Voraussetzungen gelten! 
	private boolean hatText; // Gibt es noch einen Beschreibenen Text zu der Fertigkeit?
	private String fuerWelcheChars;
	private int additionsWert;	// z.B. R�stungsgew�hung I = 1 und RG II = 2. Somit ergibt zwei mal	RG I --> ein mal RG II (sieht AH S.10)
	private String additionsID; // "Familie" von Fertigkeiten, die aufaddiert werden z.B. R�stungsgew�hung I und RG II

	/**
	 * @return Liefert das Attribut fuerWelcheChars.
	 */
	public String getFuerWelcheChars() {
		return fuerWelcheChars;
	}
	/**
	 * Fertigkeiten mit Text ben�tigen noch einen Text wenn sie gew�hlt werden: z.B.
	 * Vorurteile gegen "Orks" oder Verpflichtungen gegen�ber "Praioskirche".
	 * @return Liefert das Attribut hatText.
	 */
	public boolean isHatText() {
		return hatText;
	}
	/**
	 * Fertigkeiten die "Nicht w�hlbar" sind k�nnen nur �ber die Herkunft erlangt werden!
	 * D.h. diese stehen NICHT zur normalen Auswahl.
	 * @return Liefert das Attribut isWaehlbar.
	 */
	public boolean isWaehlbar() {
		return isWaehlbar;
	}
	/**
	 * Es mu� nur EINE der Voraussetzungen gelten! Aber es mu� eine gelten, damit diese
	 * Fertigkeit vom Charakter gew�hlt werden darf.
	 * @return Liefert das Attribut voraussetzungen.
	 */
	public Voraussetzung[] getVoraussetzungen() {
		return voraussetzungen;
	}
}
