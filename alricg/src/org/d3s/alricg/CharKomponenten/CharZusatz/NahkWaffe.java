/*
 * Created 20. Januar 2005 / 17:07:37
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.CharZusatz;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class NahkWaffe extends Waffe {
	private int kkAb; // Ab diesem Wert gibt es TP Zuschlag
	private int kkStufe; // Ab diesem Wert gibt es weitere TP Zuschlag
	private int BF; // Bruchfaktor
	private int dk; // Distanzklasse
	private int wmAT; // Waffenmodifikator / AT
	private int wmPA; // Waffenmodifikator / PA
	
	
	/**
	 * @return Liefert den Bruchfaktor
	 */
	public int getBF() {
		return BF;
	}
	/**
	 * @return Liefert die Distanzklasse(n).
	 */
	public int getDk() {
		return dk;
	}
	/**A b diesem Wert gibt es TP-Zuschlag.
	 * @return Liefert das Attribut kkAb.
	 */
	public int getKkAb() {
		return kkAb;
	}
	/** Ab getKkAb()+ diesen Wert gibt es einen weiteren TP-Zuschlag.
	 * @return Liefert das Attribut kkStufe.
	 */
	public int getKkStufe() {
		return kkStufe;
	}
	/**
	 * @return Liefert den Waffenmodifikator / AT.
	 */
	public int getWmAT() {
		return wmAT;
	}
	/**
	 * @return Liefert den Waffenmodifikator / PA
	 */
	public int getWmPA() {
		return wmPA;
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
