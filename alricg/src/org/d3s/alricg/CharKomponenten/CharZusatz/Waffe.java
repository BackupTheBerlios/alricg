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
	
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen nötigen angaben
     */
    public void loadXmlElementWaffe(Element xmlElement){
    	// TODO implement
    }
    
    /**
     * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt" und zugefügt, zu einem 
     * übergebenen XML-Elemnet
     * @param xmlElement Das Xml-Element zu dem die Daten hinzugefügt werden
     * @return Ein Xml-Element mit allen nötigen Angaben.
     */
    protected Element writeXmlElementWaffe(Element xmlElement) {
    	//TODO implement
    	return null;
    }
	
}
