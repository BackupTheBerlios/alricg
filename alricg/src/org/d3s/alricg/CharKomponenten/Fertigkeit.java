/*
 * Created 26. Dezember 2004 / 23:36:19
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import org.d3s.alricg.CharKomponenten.Links.Voraussetzung;
/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Fertigkeit extends CharElement {
	private boolean isWaehlbar;
	private Voraussetzung[] voraussetzungen;
	private boolean hatText;
	private String fuerWelcheChars;
	/**
	 * @return Liefert das Attribut fuerWelcheChars.
	 */
	public String getFuerWelcheChars() {
		return fuerWelcheChars;
	}
	/**
	 * @return Liefert das Attribut hatText.
	 */
	public boolean isHatText() {
		return hatText;
	}
	/**
	 * @return Liefert das Attribut isWaehlbar.
	 */
	public boolean isWaehlbar() {
		return isWaehlbar;
	}
	/**
	 * @return Liefert das Attribut voraussetzungen.
	 */
	public Voraussetzung[] getVoraussetzungen() {
		return voraussetzungen;
	}
}
