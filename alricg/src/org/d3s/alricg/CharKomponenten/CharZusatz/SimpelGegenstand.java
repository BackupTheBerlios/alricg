/*
 * Created on 01.03.2005 / 13:39:16
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.CharKomponenten.CharZusatz;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.Controller.ProgAdmin;
import org.d3s.alricg.Controller.CharKompAdmin.CharKomponenten;

/**
 * <u>Beschreibung:</u><br> 
 * Eine simple Implementierung der abstrakten Klasse "Gegenstand".
 * Diese Klasse diend zur repräsentation von Gegenstanden, ohne die für bestimmte
 * Gegenstände typischen zusätzlichen Attribute (TP, RS). Dient vor allem um in den
 * Kulturen / Professionen direkt angegebene Gegenstande abzubilden (Tag "ausruestungNeu").  
 * @author V. Strelow
 */
public class SimpelGegenstand extends Gegenstand {
	public static final String TEMP_ID = "SIM-temp"; 
//	 Legt die art des Gegenstandes Fest, da dies aus der Klasse nicht erkennbar ist
	private CharKomponenten art; 
	
	/**
	 * Konstruktor
	 */
	public SimpelGegenstand() {
		this.setId(TEMP_ID);
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
	 */
	public void loadXmlElement(Element xmlElement) {
		super.loadXmlElement(xmlElement);
		
		art = ProgAdmin.charKompAdmin.getCharKompFromPrefix(
					xmlElement.getAttributeValue("art")
				);
	}
	
	/** 
	 * @param Der Name des Tags der Geschrieben wird 
	 * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
	 */
	public Element writeXmlElement(String tagName) {
		Element xmlElement = super.writeXmlElement();
		
		xmlElement.addAttribute(new Attribute("art", art.getPrefix()));
		
		return xmlElement;
	}
}
