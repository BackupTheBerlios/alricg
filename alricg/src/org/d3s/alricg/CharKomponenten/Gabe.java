/*
 * Created 20. Januar 2005 / 16:24:31
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einf�gen
 * @author V.Strelow
 */
public class Gabe extends Vorteil {
	private Eigenschaften[] dreiEigenschaften;
    
    /**
     * @return Liefert die drei Eigenschaften, auf die eine Probe abgelegt wird..
     */
    public Eigenschaften[] getDreiEigenschaften() {
        return dreiEigenschaften;
    }
    
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
	 */
	public void loadXmlElement(Element xmlElement) {
		super.loadXmlElementVorNachteil(xmlElement);
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
	 */
	public Element writeXmlElement() {
		Element xmlElem = super.writeXmlElement();
		// TODO Auto-generated method stub
		return null;
	}
}