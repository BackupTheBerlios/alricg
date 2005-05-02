/*
 * Created 23. Dezember 2004 / 12:52:37
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br>
 * Repräsentiert das Element Vorteil.
 * @author V.Strelow
 */
public class Vorteil extends VorNachteil {
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#getCharKomponente()
	 */
	public CharKomponente getCharKomponente() {
		return CharKomponente.vorteil;
	}
	
	/**
	 * Konstruktur; id beginnt mit "VOR-" für Vorteil
	 * @param id Systemweit eindeutige id
	 */
	public Vorteil(String id) {
		setId(id);
	}
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	// Noop!
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	
    	xmlElement.setLocalName("vorteil");

    	return xmlElement;
    }
}
