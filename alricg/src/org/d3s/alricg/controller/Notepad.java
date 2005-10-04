/*
 * Created on 16.05.2005 / 00:54:50
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.controller;

import java.util.logging.Logger;

import org.d3s.alricg.store.FactoryFinder;


/**
 * <u>Beschreibung:</u><br> 
 * Diese Klasse dient der Speicherung von Meldungen die zu einer Nachricht zusammen
 * gesetzt werden. 
 * Diese Klasse ist vor allem für die HeldenProzessoren gedacht, um die Verarbeitung eines
 * Helden für den User transparent zu gestallten. Bei jeder Aktion auf dem Helden wird durch
 * den Heldprozessor eine neue Nachricht erzeugt, bei der Verarbeitung werden die Texte 
 * hinzugefügt. 
 * 
 * Es gibt zwei Klassen von Nachrichten:
 * - Primäre:
 * 	Dies sind alle Nachrichten, die der Benutzer in jedem Fall erwartet. 
 * 	Beispiele:
 * 		- Wenn Werte durch das Hinzufügen einer Sonderregel neu gesetzt werden
 * 		- Ein CharElement mit Voraussetzungen hinzugefügt wird, anzeigen welche CharElemente
 * 			als Voraussetzungen hinzugefügt wurden.
 * 
 * - Sekundäre:
 * 	Dies sind "Hintergrund"-Informationen über die Verarbeitung des Helden. Diese Nachrichten
 * 	erwartet der Benutzer normalerweise nicht, sie werden nur dann angezeigt wenn der Benutzer
 * 	dies explizit wünscht. ("Warum kostet das Talent X GP?", "Woher kommt der Modi der Stufe?")
 *  Sekundäre Nachrichten werden vor allem bei der Berechnung von Stufen, Kosten, Modis,
 *  "ob etwas erlaubt ist oder nicht" usw. erzeugt.
 *  
 * Es kann angegeben werden ob Sekundäre Nachrichten auch gespeichert werden sollen, oder nicht. 
 * 
 * @author V. Strelow
 */
public class Notepad {
    
    private static final Logger LOG = Logger.getLogger(Notepad.class.getName());
    
	private StringBuilder messageBuf; // Sammelt die Texte
	private Nachricht lastMessage; // Speichert die letzte beendete Nachricht
	private String titel;
	
	/**
	 * 
	 * <u>Beschreibung:</u><br> 
	 * Bei manchen Methoden wird die angabe benötigt, welcher Tag aus der 
	 * Library gemeint ist. Dafür ist diese enum vorhanden.
	 * @author V. Strelow
	 */
	public enum LibTag {
		shortTag,
		middleTag,
		longTag
	}
	
	/* true - Es werden alle Nachrichten gespeichert
	 * false - Es werden nur die "nicht Hintergrund" Nachrichten gespeichert
	 */
	private boolean storeSecondaryMsg;
	
	/**
	 * Erzeugt ein neues Nodepad
	 */
	public Notepad() {
		messageBuf = new StringBuilder();
		storeSecondaryMsg = false;
	}
	
	/**
	 * Initiiert eine Neue Nachricht. Zu dieser Nachricht kann mit den 
	 * "add..." Methoden Text hinzugefügt werden.
	 */
	public void startMessage(String titel) {
		messageBuf = new StringBuilder();
		messageBuf.append("<html>");
		this.titel = titel;
	}
	
	/**
	 * Setzt ob auch die per "addSecondaryMsg()" gesendeten Texte zur 
	 * Nachricht hinzugefügt werden sollen.
	 * @param storeHg
	 */
	public void setStoreSecondary(boolean storeSecondary) {
		storeSecondaryMsg = storeSecondary;
	}
	
	/**
	 * Liefert zurück ob per "addSecondaryMsg()" gesendeten Texte zur 
	 * Nachricht hinzugefügt werden.
	 * @return true Per "addSecondaryMsg()" gesendeten Texte werden hinzugefügt,
	 * 	ansonsten false.
	 */
	public boolean isStoreSecondary() {
		return storeSecondaryMsg;
	}
	
	/**
	 * Fügt einen Text der Nachricht hinzu. (Am Ende der Nachricht wird
	 * ein Zeilenumbruch gemacht)
	 * @param text Der text der Hinzugefügt wird
	 */
	public void addPrimaryMsg(String text) {
		messageBuf.append("&#8226; ").append(text).append("<br>");
	}
	
	/**
	 * Fügt einen Text der Nachricht hinzu, aber nur wenn "storeSecondaryMsg = true"
	 * ist. Diese Methode ist für typischen Zugriff mittels einer Library gedacht.
	 * Diese Methode  Performanter als "addSecondaryMsg(String text)", da
	 * nur auf die Library zugegriffen wird, wenn es nötig ist.
	 * Er Text wird in der Form zusammengesetzt:
	 * "*" + "text-durch-key-aus-Library" + "text"
	 * 
	 * @param tag Der Tag in Library File, in dem der Text steht
	 * @param key Das Schlüsselword zu dem Text 
	 * @param text zusätzlicher Text der hinten angehangen wird (typischer weise eine Zahl)
	 */
	public void addSecondaryMsg(LibTag tag, String key, String text) {
		// Prüfen ob überhaupt eine derartige Nachricht hinzugefügt werden soll
		if (!storeSecondaryMsg) {
			return;
		}
		
		messageBuf.append("&#8226; ");
		
		// Auswählen der richtigen Library
		switch (tag) {
		case shortTag:
			messageBuf.append(FactoryFinder.find().getLibrary().getShortTxt("Kosten-Kategorie"));
			break;
		case middleTag:
			messageBuf.append(FactoryFinder.find().getLibrary().getMiddleTxt("Kosten-Kategorie"));
			break;
		case longTag:
			messageBuf.append(FactoryFinder.find().getLibrary().getLongTxt("Kosten-Kategorie"));
			break;
		default: 
			LOG.warning("Der angegebene Library Tag konnte nicht " +
					"gefunden werden: " + tag);
		}
		
		messageBuf.append(text).append("<br>");
	}
	
	
	/**
	 * Fügt einen Text der Nachricht hinzu, aber nur wenn "storeSecondaryMsg = true"
	 * ist. Fall für den Text auf die Library zugegriffen wir, sollte wenn möglich
	 * die Methode "addSecondaryMsg(LibTag tag, String key, String text)" benutzt werden,
	 *  da diese Performanter ist.
	 * @param text Der text der Hinzugefügt wird
	 */
	public void addSecondaryMsg(String text) {
		// Prüfen ob überhaupt eine derartige Nachricht hinzugefügt werden soll
		if (!storeSecondaryMsg) {
			return;
		}
		messageBuf.append("&#8226; ").append(text).append("<br>");
	}

	/**
	 * Beendet eine Nachricht und sendet diese an den Messenger weiter, wenn
	 * "sendToListeners = true". Ansonsten wird nur die "letzte-Nachricht" 
	 * neu gesetzt.
	 */
	public void endMessage() {
		// Löschen des letzten "<br>"
		messageBuf.delete(messageBuf.length()- 4, messageBuf.length() + 1);
		
		messageBuf.append("</html>");
		
		lastMessage = new Nachricht(titel, Messenger.Level.regeln, messageBuf.toString());
	}
	
	/**
	 * @return Die letzte Nachricht wie beim letzten Aufruf von "endMessage()".
	 */
	public Nachricht getLastMessage() {
		return lastMessage;
	}
	
}
