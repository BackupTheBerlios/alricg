/*
 * Created 23. Dezember 2004 / 12:52:45
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br
 * Repräsentiert das Element Nachteil.
 * @author V.Strelow
 */
public class Nachteil extends VorNachteil {
	private boolean isSchlechteEigen;
	/**
	 * @return Liefert das Attribut isSchlechteEigen.
	 */
	public boolean isSchlechteEigen() {
		return isSchlechteEigen;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
	 */
	public void loadXmlElement(Element xmlElement) {
		this.loadXmlElementVorNachteil(xmlElement);
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
	 */
	public Element writeXmlElement() {
		Element xmlElem = null;
		
		this.writeXmlElementVorNachteil(xmlElem);
		// TODO Auto-generated method stub
		return null;
	}
}
