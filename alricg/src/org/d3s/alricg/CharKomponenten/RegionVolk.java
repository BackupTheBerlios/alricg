/*
 * Created 20. Januar 2005 / 15:31:48
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
public class RegionVolk extends CharElement {
	private String bindeWortMann;
	private String bindeWortFrau;
	private String[] vornamenMann;
	private String[] vornamenFrau;
	private String[] nachnamen;
	private String[] nachnamenEndung;
	
	/**
	 * Konstruktur; id beginnt mit "REG-" für Region
	 * @param id Systemweit eindeutige id
	 */
	public RegionVolk(String id)  {
		setId(id);
	}
	
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen nötigen angaben
     */
    public void loadXmlElement(Element xmlElement) {
    	// TODO implement
    }
    
    /**
     * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben 
     * werden in ein XML Objekt "gemapt".
     * @return Ein Xml-Element mit allen nötigen Angaben.
     */
    public Element writeXmlElement(){
//    	 TODO implement
    	return null;
    }
}
