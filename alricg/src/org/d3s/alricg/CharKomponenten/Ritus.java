/*
 * Created 27. Dezember 2004 / 01:48:37
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

/**
 * <b>Beschreibung:</b><br>
 * Fasst Gemeinsamkeiten von Liturgie und Ritual zusammen und 
 * bildet die Grundlage für diese. 
 * @author V.Strelow
 */
public abstract class Ritus extends CharElement {
	private int grad;
	private String herkunft;
	
	/**
	 * @return Liefert das Attribut grad.
	 */
	public int getGrad() {
		return grad;
	}
	/**
	 * @return Liefert das Attribut herkunft.
	 */
	public String getHerkunft() {
		return herkunft;
	}
}
