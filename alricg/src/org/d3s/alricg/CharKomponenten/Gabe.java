/*
 * Created 20. Januar 2005 / 16:24:31
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br>
 * Repräsentiert eine Gabe. Gaben sind laut Regelwerk Sonderf, von den Eigenschaften
 * her jedoch eher als Faehigkeit einzuordnen.
 * @author V.Strelow
 */
public class Gabe extends Faehigkeit {
    
	/**
	 * Konstruktur; id beginnt mit "GAB-" für Gabe
	 * @param id Systemweit eindeutige id
	 */
	public Gabe(String id) {
		setId(id);
	}
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	// Noop!
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
//    	 Noop!
    	return xmlElement;
    }
}
