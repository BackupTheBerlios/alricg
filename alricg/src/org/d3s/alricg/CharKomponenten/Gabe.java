/*
 * Created 20. Januar 2005 / 16:24:31
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import org.d3s.alricg.Controller.ProgAdmin;

import nu.xom.Attribute;
import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br>
 * Repräsentiert eine Gabe. Gaben sind laut Regelwerk Sonderf, von den Eigenschaften
 * her jedoch eher als Faehigkeit einzuordnen.
 * @author V.Strelow
 */
public class Gabe extends Faehigkeit {
	private int minStufe = 1;
	private int maxStufe = 1;
	
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
	    
    	// Auslesen der Stufengrenzen
    	try {
	    	if ( xmlElement.getFirstChildElement("stufenGrenzen") != null ) {
	    		if ( xmlElement.getFirstChildElement("stufenGrenzen")
	    				.getAttribute("minStufe") != null ) 
	    		{
	    			minStufe = Integer.parseInt(xmlElement.getFirstChildElement("stufenGrenzen")
		    				.getAttributeValue("minStufe"));
	    		}
	    		if ( xmlElement.getFirstChildElement("stufenGrenzen")
	    				.getAttribute("maxStufe") != null ) 
	    		{
	    			maxStufe = Integer.parseInt(xmlElement.getFirstChildElement("stufenGrenzen")
		    				.getAttributeValue("maxStufe"));
	    		}
	    	}
		} catch (NumberFormatException exc) {
			// TODO Bessere Fehlermeldung einbauen
			ProgAdmin.logger.severe("Fehler bei Umrechnung von Zahlen: " + exc);
		}
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("gabe");
    	
    	//schreiben der Stufengrenzen
    	if ( minStufe != 1 || maxStufe != 1 ) {
    		tmpElement = new Element("stufenGrenzen");
    		
    		if ( minStufe != 1 ) {
    			tmpElement.addAttribute(new Attribute("minStufe", Integer.toString(minStufe)));
    		}
    		if ( maxStufe != 1 ) {
    			tmpElement.addAttribute(new Attribute("maxStufe", Integer.toString(maxStufe)));
    		}
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	return xmlElement;
    }
}
