/*
 * Created 20. Januar 2005 / 16:18:50
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

import org.d3s.alricg.Prozessor.SKT.KostenKlasse;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class LiturgieRitualKenntnis extends Faehigkeit {
	private KostenKlasse sktGenerierung; // SKT bei der Generierung
	private KostenKlasse sktSpaet; // SKT nach der Generierung
	private String kultId;
	private boolean isLiturgieKenntnis; // Ansonsten Ritualkenntnis --> Schamanen
	
	/**
	 * Konstruktur; id beginnt mit "LRK-" für LiturgieRitualKenntnis
	 * @param id Systemweit eindeutige id
	 */
	public LiturgieRitualKenntnis(String id) {
		setId(id);
	}
	
	/**
	 * @return true - Dies eine LiturgieKenntnis / false - RitualKenntnis --> Schamanen
	 */
	public boolean isLiturgie() {
		return isLiturgieKenntnis;
	}
	/**
	 * @return Liefert das Attribut kultId.
	 */
	public String getKult() {
		return kultId;
	}
	/**
	 * Die KostenKlasse für die Steigerung dieser Lturgie/Ritual-Kenntnis bei
	 * der Generierung eines Helden.
	 * @return Die Kostenklasse bei Generierung.
	 */
	public KostenKlasse getSktGenerierung() {
		return sktGenerierung;
	}
	/**
	 * Die KostenKlasse für die Steigerung dieser Lturgie/Ritual-Kenntnis NACH
	 * der Generierung des Helden
	 * @return Die Kostenklasse NACH Generierung.
	 */
	public KostenKlasse getSktSpaet() {
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
