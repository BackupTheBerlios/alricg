/*
 * Created on 23.01.2005 / 19:47:25
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.CharKomponenten.CharZusatz;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.Talent;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public abstract class Waffe extends Gegenstand {
	private WuerfelSammlung TP;
	private int laenge;
	private int ini;
	private Talent talent;
	
	
	/**
	 * @return Liefert das Attribut ini.
	 */
	public int getIni() {
		return ini;
	}
	/**
	 * @return Liefert das Attribut laenge.
	 */
	public int getLaenge() {
		return laenge;
	}
	/**
	 * @return Liefert das Attribut talent.
	 */
	public Talent getTalent() {
		return talent;
	}
	/**
	 * @return Liefert das Attribut tP.
	 */
	public WuerfelSammlung getTP() {
		return TP;
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
