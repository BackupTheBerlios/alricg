/*
 * Created 20. Januar 2005 / 17:07:50
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.CharZusatz;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einf�gen
 * @author V.Strelow
 */
public class FkWaffe extends Waffe {
	private int laden;
	private String reichweite;

	
	/**
	 * @return Liefert das Attribut laden.
	 */
	public int getLaden() {
		return laden;
	}
	
	/**
	 * @return Liefert das Attribut reichweite.
	 */
	public String getReichweite() {
		return reichweite;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
	 */
	public void loadXmlElement(Element xmlElement) {
		this.loadXmlElementCharElement(xmlElement);
		this.loadXmlElementGegenstand(xmlElement);
		this.loadXmlElementWaffe(xmlElement);
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
	 */
	public Element writeXmlElement() {
		Element xmlElem = null;
		
		this.writeXmlElementCharElement(xmlElem);
		this.writeXmlElementGegenstand(xmlElem);
		this.writeXmlElementWaffe(xmlElem);
		// TODO Auto-generated method stub
		return null;
	}
}