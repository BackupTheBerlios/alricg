/*
 * Created 20. Januar 2005 / 16:18:50
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class RitualKenntnis extends Faehigkeit {
	private int sktGenerierung; // SKT bei der Generierung
	private int sktSpaet; // SKT nach der Generierung
	private String kult;
	private boolean isLiturgie;
	
	/**
	 * Konstruktur; id beginnt mit "RSF-" für RitusSF
	 * @param id Systemweit eindeutige id
	 */
	public RitualKenntnis(String id) {
		setId(id);
	}
	
	/**
	 * @return Liefert das Attribut isLiturgie.
	 */
	public boolean isLiturgie() {
		return isLiturgie;
	}
	/**
	 * @return Liefert das Attribut kult.
	 */
	public String getKult() {
		return kult;
	}
	/**
	 * @return Liefert das Attribut sktGener.
	 */
	public int getSktGenerierung() {
		return sktGenerierung;
	}
	/**
	 * @return Liefert das Attribut sktSpaet.
	 */
	public int getSktSpaet() {
		return sktSpaet;
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
