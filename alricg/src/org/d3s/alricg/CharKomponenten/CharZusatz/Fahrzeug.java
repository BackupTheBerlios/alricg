/*
 * Created 20. Januar 2005 / 17:38:35
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.CharZusatz;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Fahrzeug extends Gegenstand {
	private String typ; // Der name, z.B. "Kastenwagen"
	private String aussehen; // Ein allgemeiner Text zur Farbe, Zustand, usw.
	
	/**
	 * Konstruktur; id beginnt mit "FAH-" für Fahrzeug
	 * @param id Systemweit eindeutige id
	 */
	public Fahrzeug(String id) {
		setId(id);
	}
	
	/**
	 * @return Liefert das Attribut aussehen.
	 */
	public String getAussehen() {
		return aussehen;
	}
	
	/**
	 * @return Liefert das Attribut bezeichung.
	 */
	public String getTyp() {
		return typ;
	}
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);

    	
    	// Lesen wie das Fahrzeug aussieht
    	if ( xmlElement.getFirstChildElement("aussehen") != null ) {
    		aussehen = xmlElement.getFirstChildElement("aussehen").getValue();
    	}
    	
    	// Lesen was für ein Fahrzeugtyp das ist
    	if ( xmlElement.getAttribute("fahrzeugArt") != null ) {
    		typ = xmlElement.getAttributeValue("fahrzeugArt");
    	}
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("fahrzeug");
    	
    	// Schreiben wie das Fahrzeug Aussieht
    	if ( aussehen != null && aussehen.trim().length() > 0 ) {
    		tmpElement = new Element("aussehen");
    		tmpElement.appendChild(aussehen);
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben was für ein Typ/ Art das Fahrzeug ist
    	if ( typ != null && typ.trim().length() > 0 ) {
    		tmpElement = new Element("fahrzeugArt");
    		tmpElement.appendChild(typ);
    		xmlElement.appendChild(tmpElement);
    	}

    	return xmlElement;
    }
}
