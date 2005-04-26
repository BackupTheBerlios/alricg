/*
 * Created on 27.01.2005 / 11:13:56
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.held;

import org.d3s.alricg.charKomponenten.links.AbstractLink;

/**
 * <u>Beschreibung:</u><br> 
 * Eine Verbindung zwischen einem Held und einem CharElement, das der Held
 * "besitzt". Z.B. ein Talent oder eine Sonderfertigkeit, die der Held hat.
 * Der Link speichert die Verbindung, den Wert und ggf. die Modifikationen 
 * durch Herkunft.
 * 
 * @author V. Strelow
 */
public class HeldenLink extends AbstractLink {
	
	
	
	/**
	 * Errechnet den gesamtwert für diesen Link, bestehent aus dem vom User gewählten 
	 * Wert und dem Wert durch Modifikationen (typischerweise durch Herkunft).
	 * @return Der Gesamtwert für diesen Link, oder "0" falls es keinen Wert gibt
	 * @see hasWert()
	 */
	public int getWert() {
		//Guard - Gibt es überhaupt einen Wert?
		if (!hasWert()) return 0;

		return getWert();
	}

}
