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
 * Eine Sammlung von Methoden die f�r mehrer Prozessoren sinnvoll sind und so nicht mehrfach 
 * implementiert werden m�ssen.
 * @author V. Strelow
 */
public class HeldUtilitis {

	
	/**
	 * Versucht �berpr�ft ob der Wert des Elements "link" innerhalb der m�glichen Grenzen ist.
	 * Wenn nicht wird versucht den Wert entsprechend zu setzten. Diese Methode wird
	 * beim �ndern der Herkunft ben�tigt.
	 *  
	 * @param link Der Link der �berpr�ft werden soll
	 * @param prozessor Der Prozessor mit dem der Link �berpr�ft wird
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
	 * �berpr�ft ob ein Link grunds�tzlich zum Held hinzuf�gbar ist, dabei wird 
	 * lediglich das Attribut "isWaehlbar" �berpr�ft. Ist "isWaehlbar" = false und 
	 * der link NICHT durch eine Herkunft hinzugef�gt, so ist das Element auch nicht 
	 * w�hlbar.
	 * - Aufruf ist nur sinnvoll f�r Instanzen von "Fertigkeit", ansonsten wird stehts "true"
	 * geliefert.
	 * 
	 * Bsp.: Die Sonderfertigkeit "Nat�rlicher RS" kann nicht gew�hlt werden, sondern
	 * 		nur durch die Herkunft erworben werden.
	 * 
	 * @param link Der Link der zu �berpr�fen ist. Das Ziel des Links sollte eine Instanz von 
	 * 		"Fertigkeit" sein, sonst ist diese �berpr�fung �berfl�ssig.
	 * @return true wenn der Link grunds�tzlich zum Helden hinzugef�gt werden kann, ansonsten
	 * 		flase
	 */
	public boolean isWaehlbar(IdLink link) {
		
		if (link.getZiel() instanceof Fertigkeit) {
			if ( !((Fertigkeit)link.getZiel()).isWaehlbar() 
					&& link.getQuellElement() == null) {
				// Element ist nicht w�hlbar und nicht durch eine Herkunft hinzugef�gt
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
	 * �berpr�ft ob ein Link mit einem Ziel das Voraussetzungen besitzen kann, diese 
	 * Voraussetzungen erf�llt. D.h. es wird gepr�ft ob der Held die entsprechenden 
	 * Elemente besitzt. 
	 * Gepr�ft wird hier die das Objekt der Klasse "Voraussetzung", die in den 
	 * entsprechenden Elementen vorhanden ist, nichts anderes! Dies beinhaltet 
	 * also NICHT: Unvereinbarkeit von Vor/ Nachteilen, Magie & Gottheit
	 * 
	 * - Aufrufbar mit instanzen von "Herkunft", "Fertigkeit", "Talent" 
	 * 
	 * Bsp.: Talent "Steinmetz" ben�tig Talent "Geisteinskunde 4"; 
	 * 		SF "Defensiver Kampfstil" ben�tigt "GE 12" und SF "Meisterparade" 
	 * 
	 * @param link Die Voraussetzungen dieses Links werden gepr�ft
	 * @return true - Die Voraussetzungen von "link" sind erf�llt, ansonsten false 
	 *
	public boolean erfuelltVoraussetzung(Link link) {
		
		// TODO implement!
		
		return true;
	}*/
	

	
	/**
	 * �berpr�ft ob das Element "link" zu der CharArt pa�t, die der Held darstellt.
	 * - Aufrufbar mit Instanzen von "Fertigekeit"
	 * 
	 * Bsp.: Vorteil "Feste Matrix" kann nur von Vollzauberern und Halbzauberern benutzt werden
	 * 
	 * @param link Der Element was �berpr�ft wird, ob die CharArt �bereinstimmt
	 * @return true - Die vom link geforderte CharArt erf�llt der Held, ansonsten false
	 */
	public boolean isCharArtOk(Link link) {
		
		// TODO implement !
		
		return true;
	}
	
}
