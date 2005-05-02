 /*
 * Created 22. Dezember 2004 / 14:23:17
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten.links;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;
import org.d3s.alricg.held.HeldenLink;

/**
 * <b>Beschreibung:</b><br>
 * Diese Klasse dient als Bindeglied zwischen zwei (oder mehr) Elementen. Über
 * diesen "Link" können dann Werte, ein Text oder ein weiteres Element angegeben
 * werden (z.B. "Eitelkeit 5" oder "Unfähigkeit Schwimmen" oder "Verpflichtungen
 * gegen Orden").
 * Ein IdLink hat typischer weise eine Herkunft(Profession, Kultur, Rasse) als Quelle,
 * es kann aber auch eine anderes CharElement sein, z.B. ein Vorteil 
 * (Der Vorteil x schließ Voteil y aus).
 * 
 * @author V.Strelow
 */
public class IdLink extends Link {
	
	private Auswahl auswahl; // Falls dieser IdLink durch eine Auswahl entstand
	private CharElement quelle; // Falls dieser IdLink NICHT durch eine Auswahl entstand
   
	/* 
	 * Falls dieser Link im Helden "gespeichtert" ist, wird hier eine Verbindung zwischen 
	 * Held und Links gehalten
	 */
	private HeldenLink heldenLink;
	
	/**
	 * Konstruktor, initialisiert das Objekt. Nach der Erstellung sollte mit 
	 * "loadFromId()" oder "loadXmlElement()" der IdLink mit Inhalt gefüllt werden.
	 * @param quelle Von wo der Link "ausgeht". Typischer weise eine Herkunft.
	 * @param auswahl Falls der Link zu einer Auswahl gehört, ansonsten "null".
	 */
	public IdLink(CharElement quelle, Auswahl auswahl) 
	{
		this.quelle = quelle;
		this.auswahl = auswahl;
	}
	
	/**
	 * Liefert das CharElement, welches diesen IdLink "besitzt"
	 * @return Das CharElement, zu dem der IdLink gehört
	 */
	public CharElement getQuellElement() {
		return quelle;
	}
	
	/**
	 * Liefert das CharElement, welches diesen IdLink "besitzt"
	 * @return Das CharElement, zu dem der IdLink gehört
	 */
	public Auswahl getQuellAuswahl() {
		return auswahl;
	}

	/**
	 * Wenn ein Held einen neuen Wert über ein CharElement erhält, so 
	 * wird dies hiermit auch dem IdLink mitgeteilt und mit dem Held verbunden.
	 * @param link Die Verbindung zum Helden
	 */
	public void setHeldenLink(HeldenLink link) {
		heldenLink = link;
	}
	
	/**
	 * @param link
	 * @return 
	 */
	public HeldenLink getHeldenLink() {
		return heldenLink;
	}
	
	/**
	 * Initiiert einen simplen IdLink, indem nur die zielId übergeben wird.
	 * @param id Die zielID des IdLinks.
	 */
	public void loadFromId(String id) {
    	this.setZielId(	ProgAdmin.charKompAdmin.getCharElement(id) );
	}
	
	/**
	 * Initiiert einen IdLink indem aus einem XmlElement alle nötigen Daten des 
	 * IdLinks ausgelesen werden.
	 * @param xmlElement Ein LinkID-Tag als XmlElement
	 */
    public void loadXmlElement(Element xmlElement) {
    	String prefix;
    	CharKomponente tmpKomp;
    	
//		Auslesen des Typs des Ziels, also ob Talent, Zauber, usw. (muß ein Idlink enthalten)
    	tmpKomp = ProgAdmin.charKompAdmin.getCharKompFromId(xmlElement.getAttributeValue("id"));
    	
// 		Auslesen des Objectes zur Ziel ID (muß ein Idlink enthalten)
    	this.setZielId(ProgAdmin.charKompAdmin
    						.getCharElement(
    							xmlElement.getAttributeValue("id"), 
    							tmpKomp
    						)
	    				);

// 		Auslesen des optionalen Texts
    	if ( xmlElement.getAttribute("text") != null ) {
    		this.setText(xmlElement.getAttributeValue("text").trim());
    	}
//    	 Auslesen des optionalen Wertes
    	if ( xmlElement.getAttribute("wert") != null ) {
    		this.setWert( Integer.parseInt(xmlElement.getAttributeValue("wert").trim()) );
    	}
//   	 Auslesen des optionalen Leitwertes (Default ist false)
    	if ( xmlElement.getAttribute("leitwert") != null ) {
    		// Gültigkeit des Wertes überprufen
    		assert xmlElement.getAttributeValue("leitwert").equals("true") ||
    			   xmlElement.getAttributeValue("leitwert").equals("false");
    		
    		if (xmlElement.getAttributeValue("leitwert").equals("true")) {
    			this.setLeitwert(true);
    		} else {
    			this.setLeitwert(false);
    		}
    	}
//  	 Auslesen der optionalen Link-ID
    	if ( xmlElement.getAttribute("linkId") != null ) {
    		this.setZweitZiel(ProgAdmin.charKompAdmin.getCharElement(
								xmlElement.getAttributeValue("linkId"),
								ProgAdmin.charKompAdmin.
								getCharKompFromId(xmlElement.getAttributeValue("linkId"))
							));
    	}
    	
    }
    
    /**
     * Schreibt diesen ID-Link in ein XML Element.
     * @param tagName Der name des Tags
     * @return XmlElement mit allen nötigen Daten der LinkID
     */
    public Element writeXmlElement(String tagName){
    	Element xmlElement;
    	
    	xmlElement = new Element(tagName); // Element erstellen
  
    	// Schreiben des einzigen elements, das vorhanden sein MUSS
    	xmlElement.addAttribute( new Attribute("id", this.getZiel().getId()) );
    	
    	// Schreiben der Optionalen Elemente, sofern vorhanden und nicht Default
    	if (this.getZweitZiel() != null) {
    		xmlElement.addAttribute( new Attribute("linkId", this.getZweitZiel().getId()) );
    	}
    	if (this.getText().length() > 0) {
    		xmlElement.addAttribute( new Attribute("text", this.getText()) );
    	}
    	if (this.getWert() != KEIN_WERT) {
    		xmlElement.addAttribute( new Attribute("wert", Integer.toString(this.getWert())) );
    	}
    	if (this.isLeitwert()) { // false ist Default, braucht daher nicht geschrieben zu werden
    		xmlElement.addAttribute( new Attribute("leitwert", "true" ) );
    	}

    	return xmlElement;
    }
    
}
