/*
 * Created 27. Dezember 2004 / 01:38:24
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Faehigkeit extends CharElement {
	private int[] dreiEigenschaften = new int[3];
	private int kostenKlasse;
	/**
	 * @return Liefert das Attribut dreiEigenschaften.
	 */
	public int[] getDreiEigenschaften() {
		return dreiEigenschaften;
	}
	/**
	 * @return Liefert das Attribut kostenKlasse.
	 */
	public int getKostenKlasse() {
		return kostenKlasse;
	}
}
