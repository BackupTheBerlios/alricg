/*
 * Created on 27.01.2005 / 11:13:56
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.held;

import org.d3s.alricg.charKomponenten.links.IdLink;

/**
 * <u>Beschreibung:</u><br> 
 * Eine Verbindung zwischen einem Held und einem CharElement, das der Held
 * "besitzt". Z.B. ein Talent oder eine Sonderfertigkeit, die der Held hat.
 * Der Link speichert die Verbindung, den Wert und ggf. die Modifikationen 
 * durch Herkunft.
 * 
 * @author V. Strelow
 */
public class HeldenLink {
	private String zielId; // Das Ziel dieses Links (z.B. eine SF)
	private String linkId; // Ein in Beziehung stehendes Element (z.B. Unfähigkeit "SCHWERT")
	private String text; // Ein Text (z.B. Vorurteile gegen "Orks")
	//private int wert; // Wert der Beziehung (z.B. Höhenangst 5) / "-100": es gibt keinen Wert
	private boolean leitwert; // Für Elfen, verändert kosten
	
	private int wert = IdLink.KEIN_WERT; // Wert den der User selbst gewählt hat
	
	/**
	 * @return true - Das zugehörige CharElement hat einen Wert, sonst false.
	 * 
	 * TODO Überprüfe ob die Bedingung richtig ist!!!
	 */
	public boolean hasWert() {
		if (wert == IdLink.KEIN_WERT) { // -100 meint, das es keinen Wert gibt
			return false;
		}
		return true;
	}
	
	/**
	 * Errechnet den gesamtwert für diesen Link, bestehent aus dem vom User gewählten 
	 * Wert und dem Wert durch Modifikationen (typischerweise durch Herkunft).
	 * @return Der Gesamtwert für diesen Link, oder "0" falls es keinen Wert gibt
	 * @see hasWert()
	 */
	public int getWert() {
		//Guard - Gibt es überhaupt einen Wert?
		if (!hasWert()) return 0;

		return wert;
	}
}
