/*
 * Created 20. Januar 2005 / 17:07:26
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.CharZusatz;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br>
 * Alle Gegenstande die nicht durch die anderen Klassen (Waffe, R�stung, usw.) abgedeckt werden, fallen unter
 * diese Klasse.
 * 
 * @author V.Strelow
 */
public class Ausruestung extends Gegenstand {
    private boolean isBehaelter;
	private String haltbarkeit;
    
    
	/**
	 * Konstruktur; id beginnt mit "AUS-" f�r Vorteil
	 * @param id Systemweit eindeutige id
	 */
	public Ausruestung(String id) {
		setId(id);
	}
	
    /**
     * @return Liefert die ungef�hre Haltbarkeit des Gegenstands.
     */
    public String getHaltbarkeit() {
        return haltbarkeit;
    }
    /**
     * @return true - Die Ausr�stung ist ein Beh�lter und kann beliebige andere Gegenst�nde enthalten, 
     *  ansonsten ist dies kein Beh�lter.
     */
    public boolean isBehaelter() {
        return isBehaelter;
    }
    
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	// TODO implement
    }
    
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	// TODO implement
    	return null;
    }
}
