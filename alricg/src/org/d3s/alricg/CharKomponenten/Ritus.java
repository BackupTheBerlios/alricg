/*
 * Created 27. Dezember 2004 / 01:48:37
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br>
 * Fasst Gemeinsamkeiten von Liturgie und Ritual zusammen und 
 * bildet die Grundlage für diese. 
 * @author V.Strelow
 */
public abstract class Ritus extends CharElement {
	private int grad;
	private String additionsId;
	private int permanenteKaKosten;
	private String herkunft;
	
	/**
	 * @return Liefert das Attribut grad.
	 */
	public int getGrad() {
		return grad;
	}
	/**
	 * @return Liefert das Attribut herkunft.
	 */
	public String getHerkunft() {
		return herkunft;
	}
	/**
	 * Id von gleichartigen Liturgien, diese ist wichtig da bei zusammengehörigen
	 * Liturgien kosten anderes berechnet werden. Gleiche AdditionsIds zeigen
	 * zusammengehörigkeit an.
	 * @return Liefert das Attribut additionsId.
	 */
	public String getAdditionsId() {
		return additionsId;
	}
	/**
	 * @return Liefert das Attribut permanenteKaKosten.
	 */
	public int getPermanenteKaKosten() {
		return permanenteKaKosten;
	}
	
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen nötigen angaben
     */
    public void loadXmlElementRitus(Element xmlElement){
    	// TODO implement
    }
    
    /**
     * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt" und zugefügt, zu einem 
     * übergebenen XML-Elemnet
     * @param xmlElement Das Xml-Element zu dem die Daten hinzugefügt werden
     * @return Ein Xml-Element mit allen nötigen Angaben.
     */
    protected Element writeXmlElementRitus(Element xmlElement) {
    	//TODO implement
    	return null;
    }
}
