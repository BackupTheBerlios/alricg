/*
 * Created on 23.01.2005 / 19:37:35
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.CharKomponenten.CharZusatz;

/**
 * <u>Beschreibung:</u><br> 
 * Eine Sammlung von Würfel mit variabler Anzahl und Augen der Würfel,
 * sowie einem Wert der fest hinzuaddiert wird.
 * @author V. Strelow
 */
public class WuerfelSammlung {
	private int festWert;
	private int[] anzahlWuerfel; // Anzahl der Würfel
	private int[] augenWuerfel; // Augenzahl beim gleichen Index wie groesseWuerfel

	
	/**
	 * @return Liefert das Attribut anzahlWuerfel.
	 */
	public int[] getAnzahlWuerfel() {
		return anzahlWuerfel;
	}
	/**
	 * @return Liefert das Attribut augenWuerfel.
	 */
	public int[] getAugenWuerfel() {
		return augenWuerfel;
	}
	/**
	 * @return Liefert das Attribut festWert.
	 */
	public int getFestWert() {
		return festWert;
	}
}
