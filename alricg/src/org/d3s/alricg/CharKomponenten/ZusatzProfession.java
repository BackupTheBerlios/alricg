/*
 * Created 20. Januar 2005 / 16:04:34
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

/**
 * <b>Beschreibung:</b><br>
 * Diese Klasse repräsentiert "späte Professionen" (z.B. Kor-Geweihter) und
 * "zusatz-Professionen" (wie der Elfische Wanderer)
 * @author V.Strelow
 */
public class ZusatzProfession extends Profession {

	private Profession[] professionMoeglich; // Ist dies leer, so sind alle möglich
	private Profession[] professionUeblich;
	private int apKosten; // GP Kosten durch Profession
	private boolean zusatzProf; //ansonsten späteProfession
	private Voraussetzung voraussetzung;
}
