/*
 * Created 26. Dezember 2004 / 22:58:17
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import java.util.Hashtable;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class VorNachteil extends Fertigkeit {
	private int gp;
	private int minStufe;
	private int maxStufe;
	private Vorteil[] verbietetVorteil;
	private Nachteil[] verbietetNachteil;
	private Hashtable aendertApSf;
	private Hashtable aendertGpVorteil;
	private Hashtable aendertGpNachteil;
	private boolean istMehrfachWaehlbar;
	/**
	 * @return Liefert das Attribut aendertApSf.
	 */
	public Hashtable getAendertApSf() {
		return aendertApSf;
	}
	/**
	 * @return Liefert das Attribut aendertGpNachteil.
	 */
	public Hashtable getAendertGpNachteil() {
		return aendertGpNachteil;
	}
	/**
	 * @return Liefert das Attribut aendertGpVorteil.
	 */
	public Hashtable getAendertGpVorteil() {
		return aendertGpVorteil;
	}
	/**
	 * @return Liefert das Attribut gp.
	 */
	public int getGp() {
		return gp;
	}
	/**
	 * @return Liefert das Attribut istMehrfachWaehlbar.
	 */
	public boolean isIstMehrfachWaehlbar() {
		return istMehrfachWaehlbar;
	}
	/**
	 * @return Liefert das Attribut maxStufe.
	 */
	public int getMaxStufe() {
		return maxStufe;
	}
	/**
	 * @return Liefert das Attribut minStufe.
	 */
	public int getMinStufe() {
		return minStufe;
	}
	/**
	 * @return Liefert das Attribut verbietetNachteil.
	 */
	public Nachteil[] getVerbietetNachteil() {
		return verbietetNachteil;
	}
	/**
	 * @return Liefert das Attribut verbietetVorteil.
	 */
	public Vorteil[] getVerbietetVorteil() {
		return verbietetVorteil;
	}
}
