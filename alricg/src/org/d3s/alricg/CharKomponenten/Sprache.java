/*
 * Created 26. Dezember 2004 / 23:45:31
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Sprache extends SchriftSprache {
	private int komplexNichtMutterSpr;
	private int kostenNichtMutterSpr;
	private Schrift[] zugehoerigeSchrift;	

	/**
	 * @return Liefert das Attribut zugehoerigeSchrift.
	 */
	public Schrift[] getZugehoerigeSchrift() {
		return zugehoerigeSchrift;
	}
}
