/*
 * Created on 28.04.2005 / 00:24:18
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.prozessor;

import org.d3s.alricg.charKomponenten.Fertigkeit;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.held.GeneratorLink;

/**
 * <u>Beschreibung:</u><br> 
 * Eine Sammlung von Methoden die für mehrer Prozessoren sinnvoll sind und so nicht mehrfach 
 * implementiert werden müssen.
 * @author V. Strelow
 */
public class HeldUtilitis {

	
	/**
	 * Versucht überprüft ob der Wert des Elements "link" innerhalb der möglichen Grenzen ist.
	 * Wenn nicht wird versucht den Wert entsprechend zu setzten. Diese Methode wird
	 * beim Ändern der Herkunft benötigt.
	 *  
	 * @param link Der Link der überprüft werden soll
	 * @param prozessor Der Prozessor mit dem der Link überprüft wird
	 */
	public void inspectWert(GeneratorLink link, HeldProzessor prozessor) {		
		// TODO Meldungen einbauen!
		
		if ( link.getWert() > prozessor.getMaxWert(link) ) {
			link.setUserGesamtWert(prozessor.getMaxWert(link));
		} else if ( link.getWert() < prozessor.getMinWert(link) ) {
			link.setUserGesamtWert(prozessor.getMinWert(link));
		}
		
	}
	
	/**
	 * Überprüft ob ein Link grundsätzlich zum Held hinzufügbar ist, dabei wird 
	 * lediglich das Attribut "isWaehlbar" überprüft. Ist "isWaehlbar" = false und 
	 * der link NICHT durch eine Herkunft hinzugefügt, so ist das Element auch nicht 
	 * wählbar.
	 * - Aufruf ist nur sinnvoll für Instanzen von "Fertigkeit", ansonsten wird stehts "true"
	 * geliefert.
	 * 
	 * Bsp.: Die Sonderfertigkeit "Natürlicher RS" kann nicht gewählt werden, sondern
	 * 		nur durch die Herkunft erworben werden.
	 * 
	 * @param link Der Link der zu überprüfen ist. Das Ziel des Links sollte eine Instanz von 
	 * 		"Fertigkeit" sein, sonst ist diese Überprüfung überflüssig.
	 * @return true wenn der Link grundsätzlich zum Helden hinzugefügt werden kann, ansonsten
	 * 		flase
	 */
	public boolean isWaehlbar(IdLink link) {
		
		if (link.getZiel() instanceof Fertigkeit) {
			if ( !((Fertigkeit)link.getZiel()).isWaehlbar() 
					&& link.getQuellElement() == null) {
				// Element ist nicht wählbar und nicht durch eine Herkunft hinzugefügt
				return false;
			}
		} else {
			ProgAdmin.logger.warning("Diese Methode ist nur von Instanzen von Fertigkeit nutzbar," 
					+ " wurde jedoch von einer anderen Instanz aufgerufen! Klasse: " 
					+ link.getZiel().getClass().toString());
		}
		
		return true;
	}
	
	/**
	 * Überprüft ob ein Link mit einem Ziel das Voraussetzungen besitzen kann, diese 
	 * Voraussetzungen erfüllt. D.h. es wird geprüft ob der Held die entsprechenden 
	 * Elemente besitzt. 
	 * Geprüft wird hier die das Objekt der Klasse "Voraussetzung", die in den 
	 * entsprechenden Elementen vorhanden ist, nichts anderes! Dies beinhaltet 
	 * also NICHT: Unvereinbarkeit von Vor/ Nachteilen, Magie & Gottheit
	 * 
	 * - Aufrufbar mit instanzen von "Herkunft", "Fertigkeit", "Talent" 
	 * 
	 * Bsp.: Talent "Steinmetz" benötig Talent "Geisteinskunde 4"; 
	 * 		SF "Defensiver Kampfstil" benötigt "GE 12" und SF "Meisterparade" 
	 * 
	 * @param link Die Voraussetzungen dieses Links werden geprüft
	 * @return true - Die Voraussetzungen von "link" sind erfüllt, ansonsten false 
	 *
	public boolean erfuelltVoraussetzung(Link link) {
		
		// TODO implement!
		
		return true;
	}*/
	

	
	/**
	 * Überprüft ob das Element "link" zu der CharArt paßt, die der Held darstellt.
	 * - Aufrufbar mit Instanzen von "Fertigekeit"
	 * 
	 * Bsp.: Vorteil "Feste Matrix" kann nur von Vollzauberern und Halbzauberern benutzt werden
	 * 
	 * @param link Der Element was überprüft wird, ob die CharArt übereinstimmt
	 * @return true - Die vom link geforderte CharArt erfüllt der Held, ansonsten false
	 */
	public boolean isCharArtOk(Link link) {
		
		// TODO implement !
		
		return true;
	}
	
}
