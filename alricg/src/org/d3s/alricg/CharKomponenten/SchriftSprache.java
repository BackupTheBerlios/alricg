/*
 * Created 20. Januar 2005 / 16:48:58
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.Controller.ProgAdmin;
import org.d3s.alricg.Prozessor.SKT;
import org.d3s.alricg.Prozessor.SKT.KostenKlasse;

/**
 * <b>Beschreibung:</b><br>
 * Fa�t gemeinsame Eigenschaften von Schrift und Sprache zusammen.
 * @author V.Strelow
 */
abstract public class SchriftSprache extends CharElement {
	private int komplexitaet;
	private KostenKlasse kostenKlasse;
	
	/**
	 * @return Liefert das Attribut komplexitaet.
	 */
	public int getKomplexitaet() {
		return komplexitaet;
	}
	
	/**
	 * @return Liefert das Attribut kostenKlasse.
	 */
	public KostenKlasse getKostenKlasse() {
		return kostenKlasse;
	}
	
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
    	// Auslesen der Kostenklasse der Schrift/ Sprache
    	kostenKlasse = SKT.getKostenKlasseByXmlValue( xmlElement
    			.getFirstChildElement("daten").getAttributeValue("kostenKlasse") );
    	
    	// Auslesen der Kompl�xit�t der Schrift/ Sprache
    	try {
    		komplexitaet = Integer.parseInt( xmlElement
    			.getFirstChildElement("daten").getAttributeValue("komplexitaet") );
    	} catch (NumberFormatException exc) {
    		ProgAdmin.logger.severe("Xml-Tag konnte nicht in Zahl umgewandelt werden: " + exc);
    	}
    }
    
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();

    	// Schreiben der Kostenklasse
    	xmlElement.appendChild("daten");
    	xmlElement.getFirstChildElement("daten").addAttribute(
    				new Attribute("kostenKlasse", kostenKlasse.getXmlValue())
    			);
    
    	// Schreiben der Komplexit�t
    	xmlElement.getFirstChildElement("daten").addAttribute(
				new Attribute("komplexitaet", Integer.toString(komplexitaet))
			);
    		
    	return xmlElement;
    }
}
