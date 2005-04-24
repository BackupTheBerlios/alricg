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
 * Ein IdLink hat typischer weise eine Herkunft als Quelle, es kann aber auch eine
 * anderes CharElement sein, z.B. ein Vorteil (Der Vorteil x schließ Voteil y aus).
 * 
 * @author V.Strelow
 */
public class IdLink {
	public static int KEIN_WERT = -100; 
	private CharElement zielId; // Das Ziel dieses Links (z.B. eine SF)
	private CharElement linkId; // Ein in Beziehung stehendes Element (z.B. Unfähigkeit "SCHWERT")
	private String text; // Ein Text (z.B. Vorurteile gegen "Orks")
	private int wert = KEIN_WERT; // Wert der Beziehung (z.B. Höhenangst 5) / "-100": es gibt keinen Wert
	private boolean leitwert = false; // Für Elfen, verändert kosten / Default = false
	
	private Auswahl auswahl; // Falls dieser IdLink durch eine Auswahl entstand
	private CharElement quelle; // Falls dieser IdLink NICHT durch eine Auswahl entstand
   
	/* Alle Verbindungen die zu einem Helden Bestehen. Pro Held kann es nur 
	 * einen Link geben, aber es kann mehrer Helden geben. Vorbereitung für
	 * Heldenverwaltung! */
	private ArrayList<HeldenLink> heldenLinks;
	
	
	/*
	 * Konstruktor
	 * @param zielId Das Element für das dieser Link gilt.
	 * @param wert Der Wert der dem Element zugeordnet wird
	 *
	public IdLink(CharElement zielId, int wert, CharElement quelle, Auswahl auswahl) {
		initIdLink(zielId, null, null, wert, false, quelle, auswahl);
	}

	/**
	 * Konstruktor
	 * @param zielId Das Element für das dieser Link gilt.
	 * @param linkId Das zusätzlich verbundene Element
	 *
	public IdLink(CharElement zielId, CharElement linkId, CharElement quelle, Auswahl auswahl) {
		initIdLink(zielId, linkId, null, -100, false, quelle, auswahl);
	}
	
	/**
	 * Konstruktor
	 * @param zielId Das Element für das dieser Link gilt.
	 * @param text Der dem Element zugehörge Text
	 *
	public IdLink(CharElement zielId, String text, CharElement quelle, Auswahl auswahl) {
		initIdLink(zielId, null, text, -100, false, quelle, auswahl);
	}
	*/
	
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
	 * Gibt zurück ob das Ziel-Element ein Leitwert ist (wichtig für Elfische-
	 * Weltsicht). 
	 * @return true - Das Ziel-Element ist ein Leitwert, ansonsten false.
	 */
	public boolean isLeitwert() {
		return leitwert;
	}
	
	/**
	 * Wenn das Ziel-Element mit einem anderen Element verbunden ist, so kann
	 * dieses Link-Element hiermit abgerufen werden. 
	 * Z.B. "Unfähigkeit Schwerter" ("Unfähigkeit" = Ziel, "Schwerter" = link) 
	 * @return Das Link-Element mit dem das Ziel verbunden ist, oder "null"
	 * falls es kein Link-Elemnet gibt. 
	 */
	public CharElement getLinkId() {
		return linkId;
	}
	
	/**
	 * Wenn das Ziel-Element mit einem Text verbunden ist, so kann
	 * der Text hiermit abgerufen werden.
	 * Z.B. "Vorurteile gegen Orks" ("Vorurteile gegen" = Ziel, "Orks" = Text)
	 * @return Den anzuzeigenden Text oder "null", falls es keinen Text gibt.
	 */
	public String getText() {
		return text;
	}
	/**
	 * Wenn das Ziel-Element einen Wert besitzt, so kann der Text Wert 
	 * hiermit abgerufen werden.
	 * @return Den Wert der mit der Ziel Id Verbunden ist oder "-100" falls es 
	 * 		keinen wert gibt. .
	 */
	public int getWert() {
		return wert;
	}
	/**
	 * Jeder IdLink hat ein Ziel, dieses wird über die ZielId abgerufen!
	 * @return Liefert das Attribut zielId.
	 */
	public CharElement getZielId() {
		return zielId;
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
	 * @param leitwert Setzt das Attribut leitwert.
	 */
	public void setLeitwert(boolean leitwert) {
		this.leitwert = leitwert;
	}
	/**
	 * @param linkId Setzt das Attribut linkId.
	 */
	public void setLinkId(CharElement linkId) {
		this.linkId = linkId;
	}
	/**
	 * @param text Setzt das Attribut text.
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @param wert Setzt das Attribut wert ("-100" bedeutet, das es keinen Wert gibt).
	 */
	public void setWert(int wert) {
		this.wert = wert;
	}
	/**
	 * @param zielId Setzt das Attribut zielId. (Das Ziel dieses Links, z.B. eine SF)
	 */
	public void setZielId(CharElement zielId) {
		this.zielId = zielId;
	}
	
	/**
	 * Initiiert einen simplen IdLink, indem nur die zielId übergeben wird.
	 * @param id Die zielID des IdLinks.
	 */
	public void loadFromId(String id) {
    	this.zielId = ProgAdmin.charKompAdmin.getCharElement(id, 
    			ProgAdmin.charKompAdmin.getCharKompFromId(id));
	}
	
	/**
	 * Initiiert einen IdLink indem aus einem XmlElement alle nötigen Daten des 
	 * IdLinks ausgelesen werden.
	 * @param xmlElement Ein LinkID-Tag als XmlElement
	 */
    public void loadXmlElement(Element xmlElement) {
    	String prefix;
    	
// 		Auslesen der Ziel ID (einzige, die ein linkId enthalten MUSS 
    	this.zielId = ProgAdmin.charKompAdmin.getCharElement(
    						xmlElement.getAttributeValue("id"),
    						ProgAdmin.charKompAdmin.
    							getCharKompFromId(xmlElement.getAttributeValue("id"))
    					);
    	
// 		Auslesen des optionalen Texts
    	if ( xmlElement.getAttribute("text") != null ) {
    		this.text = xmlElement.getAttributeValue("text").trim();
    	}
//    	 Auslesen des optionalen Wertes
    	if ( xmlElement.getAttribute("wert") != null ) {
    		this.wert = Integer.parseInt(xmlElement.getAttributeValue("wert").trim());
    	}
//   	 Auslesen des optionalen Leitwertes (Default ist false)
    	if ( xmlElement.getAttribute("leitwert") != null ) {
    		// Gültigkeit des Wertes überprufen
    		assert xmlElement.getAttributeValue("leitwert").equals("true") ||
    			   xmlElement.getAttributeValue("leitwert").equals("false");
    		
    		if (xmlElement.getAttributeValue("leitwert").equals("true")) {
    			this.leitwert = true;
    		} else {
    			this.leitwert = false;
    		}
    	}
//  	 Auslesen der optionalen Link-ID
    	if ( xmlElement.getAttribute("linkId") != null ) {
    		this.linkId = ProgAdmin.charKompAdmin.getCharElement(
								xmlElement.getAttributeValue("linkId"),
								ProgAdmin.charKompAdmin.
								getCharKompFromId(xmlElement.getAttributeValue("linkId")));
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
    	xmlElement.addAttribute( new Attribute("id", zielId.getId()) );
    	
    	// Schreiben der Optionalen Elemente, sofern vorhanden und nicht Default
    	if (linkId != null) {
    		xmlElement.addAttribute( new Attribute("linkId", linkId.getId()) );
    	}
    	if (text != null) {
    		xmlElement.addAttribute( new Attribute("text", text) );
    	}
    	if (wert != KEIN_WERT) {
    		xmlElement.addAttribute( new Attribute("wert", Integer.toString(wert)) );
    	}
    	if (leitwert) { // false ist Default, braucht daher nicht geschrieben zu werden
    		xmlElement.addAttribute( new Attribute("leitwert", "true" ) );
    	}

    	return xmlElement;
    }
	
}
