/*
 * Created 23. Dezember 2004 / 12:52:37
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br>
 * Repr�sentiert das Element Vorteil.
 * @author V.Strelow
 */
public class Vorteil extends VorNachteil {
	
	/**
	 * Konstruktur; id beginnt mit "VOR-" f�r Vorteil
	 * @param id Systemweit eindeutige id
	 */
	public Vorteil(String id) {
		setId(id);
	}
	
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	// Noop!
    }
    
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
//    	 Noop!
    	return xmlElement;
    }
}
