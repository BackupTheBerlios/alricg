/*
 * Created 23. Dezember 2004 / 14:58:36
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class SonderRegel {
	private String id;
	private String beschreibung;
	
	/**
	 * @param id Die Systemweit eindeutige ID der Sonderegel
	 * @param beschreibung Ein Text zu dieser Sonderregel, die auch dem 
	 * 		Benutzer angezeigt werden kann.
	 */
	public SonderRegel(String id, String beschreibung) {
		this.id = id;
		this.beschreibung = beschreibung;
	}
	
	/**
	 * @return Liefert das Attribut beschreibung.
	 */
	public String getBeschreibung() {
		return beschreibung;
	}
	/**
	 * @return Liefert das Attribut id.
	 */
	public String getId() {
		return id;
	}
}
