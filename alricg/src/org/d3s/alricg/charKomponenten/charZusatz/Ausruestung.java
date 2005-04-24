/*
 * Created 20. Januar 2005 / 17:07:26
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten.charZusatz;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br>
 * Alle Gegenstande die nicht durch die anderen Klassen (Waffe, R�stung, usw.) abgedeckt 
 * werden, fallen unter diese Klasse.
 * 
 * @author V.Strelow
 */
public class Ausruestung extends Gegenstand {
    private boolean istBehaelter = false;
	private String haltbarkeit;
    
    
	/**
	 * Konstruktur; id beginnt mit "AUS-" f�r Ausr�stung
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
    public boolean istBehaelter() {
        return istBehaelter;
    }
    
	/**
	 * @return Liefert das Attribut istBehaelter.
	 */
	public boolean isIstBehaelter() {
		return istBehaelter;
	}
	/**
	 * @param istBehaelter Setzt das Attribut istBehaelter.
	 */
	public void setIstBehaelter(boolean istBehaelter) {
		this.istBehaelter = istBehaelter;
	}
	/**
	 * @param haltbarkeit Setzt das Attribut haltbarkeit.
	 */
	public void setHaltbarkeit(String haltbarkeit) {
		this.haltbarkeit = haltbarkeit;
	}
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);

    	xmlElement.setLocalName("gegenstand");
    	
    	// Auslesen ob es sich um einen Beh�lter handelt
    	if ( xmlElement.getFirstChildElement("istBehaelter") != null ) {
    		assert xmlElement.getFirstChildElement("istBehaelter").getValue().equals("true")
    			|| xmlElement.getFirstChildElement("istBehaelter").getValue().equals("false");
    		
    		if (xmlElement.getFirstChildElement("istBehaelter").getValue().equals("true")) {
    			istBehaelter = true;
    		} // false ist Default, mu� nicht �berpr�ft werden
    	}
    	
    	// Auslesen der Haltbarkeit
    	if ( xmlElement.getFirstChildElement("haltbarkeit") != null ) {
    		haltbarkeit = xmlElement.getFirstChildElement("haltbarkeit").getValue();
    	}
    }
    
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("gegenstand");
    	
    	// Schreiben ob es sich um einen Beh�lter handelt
    	if ( !istBehaelter ) {
    		tmpElement = new Element("istBehaelter");
    		tmpElement.appendChild("false");
    		xmlElement.appendChild(tmpElement);
    	}

    	// Schreiben der Haltbarkeit
    	if ( haltbarkeit != null && haltbarkeit.trim().length() > 0 ) {
    		tmpElement = new Element("haltbarkeit");
    		tmpElement.appendChild(haltbarkeit);
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	return xmlElement;
    }
}
