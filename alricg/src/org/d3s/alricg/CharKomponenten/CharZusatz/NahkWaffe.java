/*
 * Created 20. Januar 2005 / 17:07:37
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.CharZusatz;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.Talent;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class NahkWaffe extends Waffe {
	private int kkAb;
	private int kkStufe;
	private int BF;
	private int dk;
	private int wmAT;
	private int wmPA;
	
	
	/**
	 * @return Liefert das Attribut bF.
	 */
	public int getBF() {
		return BF;
	}
	/**
	 * @return Liefert das Attribut dk.
	 */
	public int getDk() {
		return dk;
	}
	/**
	 * @return Liefert das Attribut kkAb.
	 */
	public int getKkAb() {
		return kkAb;
	}
	/**
	 * @return Liefert das Attribut kkStufe.
	 */
	public int getKkStufe() {
		return kkStufe;
	}
	/**
	 * @return Liefert das Attribut wmAT.
	 */
	public int getWmAT() {
		return wmAT;
	}
	/**
	 * @return Liefert das Attribut wmPA.
	 */
	public int getWmPA() {
		return wmPA;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
	 */
	public void loadXmlElement(Element xmlElement) {
		this.loadXmlElementWaffe(xmlElement);
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
	 */
	public Element writeXmlElement() {
		Element xmlElem = null;
		
		this.writeXmlElementWaffe(xmlElem);
		// TODO Auto-generated method stub
		return null;
	}
}
