/*
 * Created 23. Dezember 2004 / 12:53:19
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.Links.Voraussetzung;

/**
 * <b>Beschreibung:</b><br>
 * Repr�sentiert eine Sonderfertigkeit.
 * @author V.Strelow
 */
public class Sonderfertigkeit extends Fertigkeit {
	
	public enum art { WAFENLOSKAMPF, BEWAFFNETKAMPF, MAGISCH, 
						GEWEIHT, SCHAMANISCH, SONSTIGE } 

	private int apKosten;
	private int art;
	private int[] permKosten = new int[3]; // Asp, Ka, Lep,
	private Voraussetzung voraussetzung;

	/**
	 * @return Liefert das Attribut ap - die Kosten f�r diese SF in
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

	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
	 */
	public void loadXmlElement(Element xmlElement) {
		this.loadXmlElementFertigkeit(xmlElement);
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
	 */
	public Element writeXmlElement() {
		Element xmlElem = null;
		
		this.writeXmlElementFertigkeit(xmlElem);
		// TODO Auto-generated method stub
		return null;
	}

}
