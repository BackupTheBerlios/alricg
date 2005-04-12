/*
 * Created 20. Januar 2005 / 16:18:50
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

import org.d3s.alricg.prozessor.SKT.KostenKlasse;

/**
 * <b>Beschreibung:</b><br>
 * Nimmt nur die Werte an "Liturgiekenntnis", "Geister Rufen", "Geister bannen", 
 * "Geister binden" und "Geister aufnehmen". Die Art der Gottheit wird per LinkId
 * als Text mit angegeben.
 * @author V.Strelow
 */
public class LiturgieRitualKenntnis extends Faehigkeit {
	
	private static KostenKlasse LITURGIE_KOSTEN_GENERIERUNG = KostenKlasse.E;
	private static KostenKlasse LITURGIE_KOSTEN_SPAETER = KostenKlasse.G;
	private static KostenKlasse RITUAL_KOSTEN = KostenKlasse.E;
	/*
	public enum Art {
		liturgie("liturgie"),
		geisterBannen("geisterBannen"),
		geisterBinden("geisterBinden"),
		geisterAufnehmen("geisterAufnehmen"),
		geisterRufen("geisterRufen");
		private String xmlValue; // XML-Tag des Elements
		
		private Art(String xmlValue) {
			this.xmlValue = xmlValue;
		}
		
		public String getXmlValue() {
			return xmlValue;
		}	
	}*/

	private boolean isLiturgieKenntnis = true;
	//private String kultId;
	//private Art kenntnisArt; // Ansonsten Ritualkenntnis --> Schamanen
	
	/**
	 * Konstruktur; id beginnt mit "LRK-" f�r LiturgieRitualKenntnis
	 * @param id Systemweit eindeutige id
	 */
	public LiturgieRitualKenntnis(String id) {
		setId(id);
	}
	
	/*
	 * @return 
	 *
	public Art getKenntnisArt() {
		return kenntnisArt;
	}
	/*
	 * @return Liefert das Attribut kultId.
	 *
	public String getKult() {
		return kultId;
	}*/

	/**
	 * Die KostenKlasse f�r die Steigerung dieser Lturgie/Ritual-Kenntnis NACH
	 * der Generierung des Helden. Diese kann sich von der KK der Generierug unterscheiden!
	 * @return Die Kostenklasse NACH Generierung.
	 */
	public KostenKlasse getSktSpaet() {
		if (isLiturgieKenntnis) {
			return LITURGIE_KOSTEN_SPAETER;
		} 
		
		return RITUAL_KOSTEN;
	}
	
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
    	// Auslesen ob es zu einer "G�ttlichen" oder "Schamanischen" Tradition geh�rt
    	if ( xmlElement.getFirstChildElement("istLiturgieKenntnis") != null ) {
    		// Wertebereich pr�fen
    		assert xmlElement.getFirstChildElement("istLiturgieKenntnis").getValue().equals("true")
    			|| xmlElement.getFirstChildElement("istLiturgieKenntnis").getValue().equals("false");
    	
    		if (xmlElement.getFirstChildElement("istLiturgieKenntnis").getValue().equals("false")) {
    			isLiturgieKenntnis = false;
    		}
    	}
    	
    }
    
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("liRiKenntnis");
    	
    	// Schreiben ob es zu einer "G�ttlichen" oder "Schamanischen" Tradition geh�rt
    	if ( !isLiturgieKenntnis ) {
    		tmpElement = new Element("istLiturgieKenntnis");
    		tmpElement.appendChild("false");
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	return xmlElement;
    }
    
}
