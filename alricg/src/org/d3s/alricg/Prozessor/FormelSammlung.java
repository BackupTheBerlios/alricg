/*
 * Created on 09.03.2005 / 17:59:33
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.Prozessor;

/**
 * <u>Beschreibung:</u><br> 
 * 
 * @author V. Strelow
 */
public class FormelSammlung {

	
	/**
	 * Berechnet die AP die für eine Sonderfertigkeit gezahlt werden muß.
	 * Dafür werden die GP der SF genommen und mit 50 Multipliziert.
	 * @param gp Die GP-Kosten einer Sonderfertigkeit
	 * @return Die AP-Kosten für diese Sonderfertigkeit
	 */
	public static int berechneSfAp(int gp) {
		return (gp * 50);
	}
	
}
