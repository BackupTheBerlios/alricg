/*
 * Created 27. Dezember 2004 / 01:38:24
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

import org.d3s.alricg.Prozessor.SKT.KostenKlasse;

/**
 * <b>Beschreibung:</b><br>
 * Oberklasse von Talenten und Zaubern, fa�t Gemeinsamkeiten zusammen.
 * @author V.Strelow
 */
public abstract class Faehigkeit extends CharElement {
	private Eigenschaften[] dreiEigenschaften = new Eigenschaften[3];
	private KostenKlasse kostenKlasse;
	
    /**
     * @see Klasse org.d3s.alricg.CharKomponenten.Eigenschaften
	 * @return Liefert die drei Eigenschaften, auf die die Probe abgelegt werden mu�.
	 */
	public Eigenschaften[] getDreiEigenschaften() {
		return dreiEigenschaften;
	}
	
    /**
	 * @return Liefert die Kosten-Klasse nach der SKT.
	 */
	public KostenKlasse getKostenKlasse() {
		return kostenKlasse;
	}
	
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen n�tigen angaben
     */
	protected void loadXmlElementFaehigkeit(Element xmlElement){
    	// TODO implement
    }
    
    /**
     * Dient zur Speicherung (also f�r den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt" und zugef�gt, zu einem 
     * �bergebenen XML-Elemnet
     * @param xmlElement Das Xml-Element zu dem die Daten hinzugef�gt werden
     * @return Ein Xml-Element mit allen n�tigen Angaben.
     */
    protected Element writeXmlElementFaehigkeit(Element xmlElement) {
    	//TODO implement
    	return null;
    }
}
