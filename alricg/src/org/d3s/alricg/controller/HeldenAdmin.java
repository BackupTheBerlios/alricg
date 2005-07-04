/*
 * Created on 29.06.2005 / 23:32:09
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.controller;

import java.util.HashMap;

import org.d3s.alricg.held.Held;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.generierung.AbstractBoxGen;
import org.d3s.alricg.prozessor.generierung.GenerierungProzessor;

/**
 * <u>Beschreibung:</u><br> 
 * Diese Klasse verwaltet die geladenen Helden und übernimmt die Erzeugung/ das Einladen von 
 * Helden. 
 * @author V. Strelow
 */
public class HeldenAdmin {
	private Held aktHeld; // Der zum bearbeiten geöffnete Held 
	private HeldProzessor aktHeldProzessor; // Zum Held gehörende Prozessor
	
	/**
	 * @return Der Held der aktuell im Programm zum Bearbeiten geöffnet ist
	 */
	public Held getActiveHeld() {
		return aktHeld;
	}
	
	/**
	 * @return Der Held der aktuell im Programm zum Bearbeiten geöffnet ist
	 */
	public HeldProzessor getActiveProzessor() {
		return aktHeldProzessor;
	}
	
	/**
	 * Erstellt einen neuen, leeren Helden. Ist für die Generierung von Helden 
	 * gedacht.
	 * @return Einen neuen Helden, der vollständig initialisiert ist. 
	 */
	public void initHeldGenerierung() {
		final HashMap<CharKomponente, AbstractBoxGen> map;
		
		aktHeld = new Held();
		aktHeldProzessor = new GenerierungProzessor(aktHeld);
		
		// Hash mit den CharElementBoxen initialisieren
		map = aktHeld.initHashMap();
		
		// Setzen der CharElement boxen im Prozessor
		aktHeldProzessor.setBoxenHash( map );
		
		// Boxen für die CharElemente initialisieren
		aktHeld.initGenrierung(aktHeldProzessor);
		
	}
	
	
	public void initHeldManagement() {
		final HashMap<CharKomponente, AbstractBoxGen> map;
		
		aktHeld = new Held();
		aktHeldProzessor = new GenerierungProzessor(aktHeld);
		
		// Hash mit den CharElementBoxen initialisieren
		map = aktHeld.initHashMap();
		
		// Setzen der CharElement boxen im Prozessor
		aktHeldProzessor.setBoxenHash( map );
		
		// Boxen für die CharElemente initialisieren
		aktHeld.initManagement(aktHeldProzessor);
		
	}

}
