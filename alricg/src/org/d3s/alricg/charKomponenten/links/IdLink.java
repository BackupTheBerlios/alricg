 /*
 * Created 22. Dezember 2004 / 14:23:17
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten.links;

import java.util.ArrayList;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.controller.ProgAdmin;
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
public class IdLink extends AbstractLink{
	
	private Auswahl auswahl; // Falls dieser IdLink durch eine Auswahl entstand
	private CharElement quelle; // Falls dieser IdLink NICHT durch eine Auswahl entstand
   
	/* Alle Verbindungen die zu einem Helden Bestehen. Pro Held kann es nur 
	 * einen Link geben, aber es kann mehrer Helden geben. Vorbereitung für
	 * Heldenverwaltung! */
	private ArrayList<HeldenLink> heldenLinks;
	
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
	 * Wenn ein Held einen neuen Wert über ein CharElement erhält, so 
	 * wird dies hiermit auch dem IdLink mitgeteilt und mit dem Held verbunden.
	 * @param link Die Verbindung zum Helden
	 */
	public void addHeldenLink(HeldenLink link) {
		if (heldenLinks == null) {
			heldenLinks = new ArrayList<HeldenLink>(1);
		}
		heldenLinks.add(link);
	}
	
	/**
	 * Wenn ein Held einen Wert über ein CharElement abwählt, so 
	 * wird dies hiermit auch dem IdLink mitgeteilt und die Verbindung zu Held gelöscht.
	 * @param link Die zu löschende Verbindung zum Helden
	 */
	public void removeHeldenLink(HeldenLink link) {
		heldenLinks.remove(link);
		
		// Um den Speicher wieder freizugeben, wenn alle Links abgewählt wurde
		if (heldenLinks.isEmpty()) {
			heldenLinks = null;
		}
	}
	
	/**
	 * Initiiert einen simplen IdLink, indem nur die zielId übergeben wird.
	 * @param id Die zielID des IdLinks.
	 */
	public void loadFromId(String id) {
    	this.setZielId(
    			ProgAdmin.charKompAdmin.getCharElement(id, 
    			ProgAdmin.charKompAdmin.getCharKompFromId(id))
    		);
	}
	
	/**
	 * Initiiert einen IdLink indem aus einem XmlElement alle nötigen Daten des 
	 * IdLinks ausgelesen werden.
	 * @param xmlElement Ein LinkID-Tag als XmlElement
	 */
    public void loadXmlElement(Element xmlElement) {
    	String prefix;
    	
// 		Auslesen der Ziel ID (einzige, die ein linkId enthalten MUSS 
    	this.setZielId(ProgAdmin.charKompAdmin.getCharElement(
    						xmlElement.getAttributeValue("id"),
    						ProgAdmin.charKompAdmin.
    							getCharKompFromId(xmlElement.getAttributeValue("id"))
    					));
    	
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
    		this.setLinkId(ProgAdmin.charKompAdmin.getCharElement(
								xmlElement.getAttributeValue("linkId"),
								ProgAdmin.charKompAdmin.
								getCharKompFromId(xmlElement.getAttributeValue("linkId")))
							);
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
    	xmlElement.addAttribute( new Attribute("id", this.getZielId().getId()) );
    	
    	// Schreiben der Optionalen Elemente, sofern vorhanden und nicht Default
    	if (this.getLinkId() != null) {
    		xmlElement.addAttribute( new Attribute("linkId", this.getLinkId().getId()) );
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
