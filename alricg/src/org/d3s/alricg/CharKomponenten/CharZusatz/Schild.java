/*
 * Created on 26.01.2005 / 00:44:45
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.CharKomponenten.CharZusatz;

import nu.xom.Element;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class Schild extends Gegenstand {
	private int BF; // Bruchfaktor
	private int ini; // Bruchfaktor
	private int wmAT; // Waffenmodifikator / AT
	private int wmPA; // Waffenmodifikator / PA
	
	/**
	 * @return Liefert den Bruchfaktor.
	 */
	public int getBF() {
		return BF;
	}
	
	/**
	 * @return Liefert den initiative Modi.
	 */
	public int getIni() {
		return ini;
	}
	
	/**
	 * @return Liefert den Waffenmodifikator / AT.
	 */
	public int getWmAT() {
		return wmAT;
	}
	
	/**
	 * @return Liefert den Waffenmodifikator / PA.
	 */
	public int getWmPA() {
		return wmPA;
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
