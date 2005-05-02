/*
 * Created on 27.01.2005 / 11:13:56
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.held;

import org.d3s.alricg.charKomponenten.links.Link;

/**
 * <u>Beschreibung:</u><br> 
 * Eine Verbindung zwischen einem Held und einem CharElement, das der Held
 * "besitzt". Z.B. ein Talent oder eine Sonderfertigkeit, die der Held hat.
 * Der Link speichert die Verbindung, den Wert usw.
 * 
 * @author V. Strelow
 */
public class HeldenLink extends Link {
	
	
	
	
	public void setUserWert(int wert) {
		setWert(wert);
	}

}
