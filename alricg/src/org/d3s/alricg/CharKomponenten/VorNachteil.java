/*
 * Created 26. Dezember 2004 / 22:58:17
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import java.util.Hashtable;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.Links.Voraussetzung;

/**
 * <b>Beschreibung:</b><br>
 * Fast gemeinsamkeiten von Vor- und Nachteilen zusammen und bildet die 
 * Grundlage für diese.
 * @author V.Strelow
 */
public abstract class VorNachteil extends Fertigkeit {
	private int proStufe; // Bezieht sich auf GP
	private int minStufe;
	private int maxStufe;
	private Vorteil[] verbietetVorteil;
	private Nachteil[] verbietetNachteil;
	private Hashtable aendertApSf;
	private Hashtable aendertGpVorteil;
	private Hashtable aendertGpNachteil;
	private boolean istMehrfachWaehlbar;
	private Voraussetzung voraussetzung;

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
	 * @return Liefert das Attribut proStufe.
	 */
	public int getProStufe() {
		return proStufe;
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
	
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen nötigen angaben
     */
	protected void loadXmlElementVorNachteil(Element xmlElement){
    	// TODO implement
    }
    
    /**
     * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt" und zugefügt, zu einem 
     * übergebenen XML-Elemnet
     * @param xmlElement Das Xml-Element zu dem die Daten hinzugefügt werden
     * @return Ein Xml-Element mit allen nötigen Angaben.
     */
    protected Element writeXmlElementVorNachteil(Element xmlElement) {
    	//TODO implement
    	return null;
    }
}
