/*
 * Created 23. Dezember 2004 / 12:53:19
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

/**
 * <b>Beschreibung:</b><br>
 * Repräsentiert eine Sonderfertigkeit.
 * @author V.Strelow
 */
public class Sonderfertigkeit extends Fertigkeit {
	public static int ART_WAFFENLOSKAMPF = 0;
	public static int ART_BEWAFFNETKAMPF = 1;
	public static int ART_MAGISCH = 2;
	public static int ART_GEWEIHT = 3;
	public static int ART_SCHAMANISCH = 4;
	public static int ART_SONSTIGE = 5;

	private int apKosten;
	private int art;
	private int[] permKosten = new int[3]; // Asp, Ka, Lep,
	private org.d3s.alricg.CharKomponenten.Links.Voraussetzung voraussetzung;

	/**
	 * @return Liefert das Attribut ap - die Kosten für diese SF in
	 * Abenteuerpunkten.
	 */
	public int getApKosten() {
		return apKosten;
	}	
	
	/**
	 * @return Liefert das Attribut art.
	 */
	public int getArt() {
		return art;
	}

}
