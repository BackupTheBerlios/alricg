/*
 * Created 26. Dezember 2004 / 22:58:17
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import java.util.Hashtable;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.Links.IdLink;
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
	private IdLink[] verbietetVorteil; // TODO Generics benutzen
	private IdLink[] verbietetNachteil; // TODO Generics benutzen
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
	public IdLink[] getVerbietetNachteil() {
		return verbietetNachteil;
	}
	/**
	 * @return Liefert das Attribut verbietetVorteil.
	 */
	public IdLink[] getVerbietetVorteil() {
		return verbietetVorteil;
	}
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	// TODO implement
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	// TODO implement
    	return null;
    }
}
