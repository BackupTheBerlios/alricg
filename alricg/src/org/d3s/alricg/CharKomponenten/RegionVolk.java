/*
 * Created 20. Januar 2005 / 15:31:48
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
public class RegionVolk extends CharElement {
	private String bindeWortMann;
	private String bindeWortFrau;
	private String[] vornamenMann;
	private String[] vornamenFrau;
	private String[] nachnamen;
	private String[] nachnamenEndung;
	
	/**
	 * Konstruktur; id beginnt mit "REG-" f�r Region
	 * @param id Systemweit eindeutige id
	 */
	public RegionVolk(String id)  {
		setId(id);
	}
	
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen n�tigen angaben
     */
    public void loadXmlElement(Element xmlElement) {
    	// TODO implement
    }
    
    /**
     * Dient zur Speicherung (also f�r den Editor) des Objekts. Alle Angaben 
     * werden in ein XML Objekt "gemapt".
     * @return Ein Xml-Element mit allen n�tigen Angaben.
     */
    public Element writeXmlElement(){
//    	 TODO implement
    	return null;
    }
}
