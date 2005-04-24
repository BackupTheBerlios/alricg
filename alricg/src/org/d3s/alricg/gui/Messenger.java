/*
 * Created on 12.02.2005 / 16:36:58
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui;


import java.util.ArrayList;

import javax.swing.JOptionPane;

import static org.d3s.alricg.gui.Messenger.Level.frage;
import static org.d3s.alricg.gui.Messenger.Level.info;
import static org.d3s.alricg.gui.Messenger.Level.erwartetFehler;
import static org.d3s.alricg.gui.Messenger.Level.unerwartetFehler;
import static org.d3s.alricg.gui.Messenger.Level.warnung;
import org.d3s.alricg.controller.ProgAdmin;
/**
 * <u>Beschreibung:</u><br> 
 * Nimmt Nachrichten entgegen und leitet sie an alle "interessierten" Objekte (Listener)
 * die sich registriert haben weiter. Listener müssn das interface "MessageListener"
 * implementieren.
 * @author V. Strelow
 * @see org.d3s.alricg.GUI.MessageListener
 */
public class Messenger {
	public enum Level { frage, info, warnung, erwartetFehler, unerwartetFehler }
	
//	Die maximale Anzahl an Nachrichten die gespeichert wird
	private final int MAX_NACHRICHTEN = 15; 
	private ArrayList<MessageListener> listenerAL; // registrierte Listener
	private ArrayList<Nachricht> nachrichtenAL;
	
	/**
	 * Konstruktor. Initialisiert das Objekt.
	 */
	public Messenger() {
		listenerAL = new ArrayList<MessageListener>(5);
		nachrichtenAL = new ArrayList<Nachricht>(MAX_NACHRICHTEN);
	}
	
	/**
	 * Ein Listener kann sich hier registrieren. Bei ankunft einer neuen Nachricht 
	 * wird der Listener über seine "neueNachricht(Nachricht)" Methode informiert.
	 * @param listener Der zu registrierende Listener.
	 */
	public void register(MessageListener listener) {
		listenerAL.add(listener);
	}
	
	/**
	 * Ein bereits registrierter Listener wird wieder abgemeldet.
	 * @param listener Der abzumeldene Listener
	 */
	public void unregister(MessageListener listener) {
		listenerAL.remove(listener);
	}
	
	/**
	 * Dient dem Absetzen von Nachrichten. Die Nachrichten werden an "interessierte"
	 * Listener weitergereicht und gespeichert.
	 * @param level Die Art der Meldung
	 * @param text Die Meldung selbst (sollte zuvor per Library übersetzt sein)
	 */
	public void sendMessage(Level level, String text)  {
		registerMessage(level, text);
	}
	
	/**
	 * Dient dem Absetzen einer Fehler-Nachrichten. Die Nachrichten werden an "interessierte"
	 * Listener weitergereicht und gespeichert.
	 * @param text Die Meldung selbst (sollte zuvor per Library übersetzt sein)
	 */
	public void sendFehler(String text) {
		registerMessage(Level.erwartetFehler, text);
	}
	
	/**
	 * Dient dem Absetzen einer Info-Nachrichten. Die Nachrichten werden an "interessierte"
	 * Listener weitergereicht und gespeichert.
	 * @param text Die Meldung selbst (sollte zuvor per Library übersetzt sein)
	 */
	public void sendInfo(String text) {
		registerMessage(Level.info, text);
	}
	
	/**
	 * Speichert die Meldung wie auch mit sendMessage, zusätzlich wird die Meldung jedoch 
	 * in einem Fenster angezeigt. Die Icons und Buttons sind von dem Level abhängig.
	 * @param level Die Art der Meldung
	 * @param text Die Meldung selbst (sollte zuvor per Library übersetzt sein)
	 * @return Der Rückgabewert, wie bei einem JOptionPane
	 * @see javax.swing.JOptionPane
	 */
	public int showMessage(Level level, String text) {
		int messageType, buttons;
		String titel;
		
		registerMessage(level,text);
		
		switch (level) {
			case frage:
				titel = "Abfrage!?";
				messageType = JOptionPane.QUESTION_MESSAGE;
				buttons = JOptionPane.YES_NO_CANCEL_OPTION;
				break;
			case info:
				titel = "Hinweis!";
				messageType = JOptionPane.INFORMATION_MESSAGE;
				buttons = JOptionPane.OK_CANCEL_OPTION;
				break;
			case warnung:
				titel = "Warnung!";
				messageType = JOptionPane.WARNING_MESSAGE;
				buttons = JOptionPane.OK_CANCEL_OPTION;
				break;
			case erwartetFehler:
			case unerwartetFehler:
				titel = "Ein Fehler ist aufgetreten!";
				messageType = JOptionPane.ERROR_MESSAGE;
				buttons = JOptionPane.OK_CANCEL_OPTION;
				break;
			default:
				ProgAdmin.logger.severe("Nötiger Case Fall ist nicht eingetreten! ");
				return 0;
		}
		
		return JOptionPane.showConfirmDialog(null, text, titel, buttons, messageType);
	}
	
	
	
	/**
	 * TODO Hier muß die Methode noch den Erfordernissen angepasst werden!
	 * @param level
	 * @param anzeigen
	 * @param text
	 * @param buttons
	 * @return
	 */
	private void registerMessage(Level level, String text)  {

		Nachricht tempNachricht;
		
		// Was anderes als dieser Fall sollte nie eintreten können....
		assert nachrichtenAL.size() <= MAX_NACHRICHTEN;
		
		if (nachrichtenAL.size() == MAX_NACHRICHTEN) {
			// Letzten Eintag entfernen, aufheben für "Object-Recycling"
			// Objekte zu recyclen ist effizienter als neue zu erzeugen!
			tempNachricht =	nachrichtenAL.remove(MAX_NACHRICHTEN-1);
			
		} else {
			tempNachricht = new Nachricht();
		}
		
		// Werte setzen / Objekt neu füllen "Object-Recycling"
		tempNachricht.setVaules(level, text);
		
		// Als neusten Eintrag einfügen
		nachrichtenAL.add(0, tempNachricht);
		
		// Alle Listener informieren
		for (int i = 0; i < listenerAL.size(); i++) {
			listenerAL.get(i).neueNachricht(tempNachricht);
		}

	}
	
	/**
	 * <u>Beschreibung:</u><br> 
	 * Beinhaltet den Inhalt einer Nachricht, um sie zu speichern und
	 * Weiterzugeben. 
	 * @author V. Strelow
	 */
	class Nachricht {
		private Level level;
		private String text;
		
		/** Leerer Konstruktor */ 
		public Nachricht() {
			// Noop!
		}
		
		/**
		 * Hiermit können alle Werte der Nachricht gesetzt werden (Sinnvoll 
		 * für das recycling von Objekten)
		 * @param level Die "Art" der Nachricht
		 * @param text Der Text er Nachricht
		 */
		public void setVaules(Level level, String text) {
			this.level = level;
			this.text  = text;
		}
		
		/**
		 * @return Liefert das Attribut level.
		 */
		public Level getLevel() {
			return level;
		}
		
		/**
		 * @return Liefert das Attribut text.
		 */
		public String getText() {
			return text;
		}
	}

}
