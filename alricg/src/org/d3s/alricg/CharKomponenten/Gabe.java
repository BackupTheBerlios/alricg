/*
 * Created 20. Januar 2005 / 16:24:31
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Gabe extends Vorteil {
	private EigenschaftEnum[] dreiEigenschaften;
    
	/**
	 * Konstruktur; id beginnt mit "GAB-" für Gabe
	 * @param id Systemweit eindeutige id
	 */
	public Gabe(String id) {
		super(id);
	}
	
    /**
     * @return Liefert die drei Eigenschaften, auf die eine Probe abgelegt wird..
     */
    public EigenschaftEnum[] getDreiEigenschaften() {
        return dreiEigenschaften;
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
