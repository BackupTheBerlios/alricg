/*
 * Created 22. Dezember 2004 / 14:23:17
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.Links;

import java.util.ArrayList;

import org.d3s.alricg.CharKomponenten.CharElement;
import org.d3s.alricg.Held.HeldenLink;

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
public class IdLink<TypZiel extends CharElement, TypLink extends CharElement> {
	private TypZiel zielId; // Das Ziel dieses Links (z.B. eine SF)
	private TypLink linkId; // Ein in Beziehung stehendes Element (z.B. Unfähigkeit "SCHWERT")
	private String text; // Ein Text (z.B. Vorurteile gegen "Orks")
	private int wert; // Wert der Beziehung (z.B. Höhenangst 5) / "-100": es gibt keinen Wert
	private boolean leitwert; // Für Elfen, verändert kosten
	
	private Auswahl auswahl; // Falls dieser IdLink durch eine Auswahl entstand
	private CharElement quelle; // Falls dieser IdLink NICHT durch eine Auswahl entstand
   
	/* Alle Verbindungen die zu einem Helden Bestehen. Pro Held kann es nur 
	 * einen Link geben, aber es kann mehrer Helden geben. Vorbereitung für
	 * Heldenverwaltung! */
	private ArrayList<HeldenLink> heldenLinks;
	
	
	/**
	 * Konstruktor
	 * @param zielId Das Element für das dieser Link gilt.
	 * @param wert Der Wert der dem Element zugeordnet wird
	 */
	public IdLink(TypZiel zielId, int wert, CharElement quelle, Auswahl auswahl) {
		initIdLink(zielId, null, null, wert, false, quelle, auswahl);
	}
	
	/**
	 * Konstruktor
	 * @param zielId Das Element für das dieser Link gilt.
	 * @param linkId Das zusätzlich verbundene Element
	 */
	public IdLink(TypZiel zielId, TypLink linkId, CharElement quelle, Auswahl auswahl) {
		initIdLink(zielId, linkId, null, -100, false, quelle, auswahl);
	}
	
	/**
	 * Konstruktor
	 * @param zielId Das Element für das dieser Link gilt.
	 * @param text Der dem Element zugehörge Text
	 */
	public IdLink(TypZiel zielId, String text, CharElement quelle, Auswahl auswahl) {
		initIdLink(zielId, null, text, -100, false, quelle, auswahl);
	}
	
	/**
	 * Initialisiert das Objekt. Wird von den Konstruktoren aufgerufen.
	 * @param zielId Das Element für das dieser Link gilt.
	 * @param linkId Für zusätzlich verbundene Elemente
	 * @param text Der dem Element zugehörge Text
	 * @param wert Der Wert der dem Element zugeordnet wird
	 * @param isLeitwert Für Elfische Weltsicht
	 * @param quelle Von wo der Link "ausgeht". Typischer weise eine Herkunft
	 * @param auswahl Falls der Link zu einer Auswahl gehört.
	 */
	private void initIdLink(TypZiel zielId, TypLink linkId, String text, 
					int wert, boolean leitwert, CharElement quelle, Auswahl auswahl) 
	{
		this.zielId = zielId;
		this.linkId = linkId;
		this.text = text;
		this.wert = wert;
		this.leitwert = leitwert;
		this.quelle = quelle;
		this.auswahl = auswahl;
		
	}
	
	/**
	 * 
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
	public TypLink getLinkId() {
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
	 * @return Liefert das Attribut zielId.
	 */
	public TypZiel getZielId() {
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
	public void setLinkId(TypLink linkId) {
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
	 * @param zielId Setzt das Attribut zielId.
	 */
	public void setZielId(TypZiel zielId) {
		this.zielId = zielId;
	}
}
