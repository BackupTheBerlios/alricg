/*
 * Created 23. Januar 2005 / 15:30:43
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.held;

import java.util.ArrayList;

import org.d3s.alricg.CharKomponenten.CharElement;
import org.d3s.alricg.CharKomponenten.Herkunft;
import org.d3s.alricg.CharKomponenten.Kultur;
import org.d3s.alricg.CharKomponenten.Profession;
import org.d3s.alricg.CharKomponenten.Rasse;
import org.d3s.alricg.CharKomponenten.Sprache;
import org.d3s.alricg.Controller.CharKompAdmin.CharKomponenten;

/**
 * <b>Beschreibung:</b><br>
 * Repräsentiert einen Charakter der erstellt wird bzw. erstellt wurde.
 * @author V.Strelow
 */
public class Held {
	private Profession profession;
	private Kultur kultur;
	private Rasse rasse;

    // Gewählte, OHNE auswahl durch die Herkunft
    private ArrayList nachteile;
    private ArrayList vorteile;
    private ArrayList sonderf;
    
    private Sprache[] muttersprache; // kann mehrere geben, siehe "Golbin Festumer G"

    
    /**
     * Überprüft ob ein CharElement zu dem Held hinzugefügt werden kann 
     * (Ausschluß möglich durch nicht erfüllen von Voraussetzungen,
     * Unvereinbar mit anderen CharElementen oder dass das CharElement
     * schon vorhanden ist und nicht doppelt gewählt werden kann).
     * 
     * @param element Das Element welches geprüft wird
     * @param komponente Die Art des Elements als CharKomponente 
     * @param herkunft Falls das Element durch eine Rasse, Kultur, Prof 
     * 		hinzugefügt wird, wird diese hier übergeben. Ansonsten "null"
     * @return true - Kann hinzugefügt werden, ansonsten false 
     */
	public boolean canAddCharElement(
				CharElement element, 
				CharKomponenten komponente, 
				Herkunft herkunf) {
		// TODO implement, Überlege elegantes Konzept ob eine Meldung abgesetzt
		// wird!
		return false;
	}
	
	/**
	 * Fügt das Element hinzu, Regelverstöße werden nicht verhindert, 
	 * aber vermerkt.
	 * 
	 * @param element Das Element welches hinzugefügt wird
     * @param komponente Die Art des Elements als CharKomponente 
     * @param herkunft Falls das Element durch eine Rasse, Kultur, Prof 
     * 		hinzugefügt wird, wird diese hier übergeben. Ansonsten "null"
	 */
	public void addCharElement(
			CharElement element, 
			CharKomponenten komponente, 
			Herkunft herkunf) {
		// TODO implement
	}
	
	
	public boolean canRemoveCharElement(CharElement element, CharKomponenten komponente) {
		// TODO implement
		return false;
	}
	
	public void removeCharElement(CharElement element, CharKomponenten komponente) {
		// TODO implement
	}
	
	/**
	 * Liefert den maximalen Wert, den das CharElement erreichen kann. 
	 * @param element Das Element welches geprüft wird
	 * @return Maximal annehmbarer Wert von element
	 */
	public int getMaxWertCharElement(CharElement element, CharKomponenten komponente) {
//		 TODO implement
		return 0;
	}
	
	/**
	 * Liefert den minimalen Wert, den das CharElement annehmen kann. 
	 * @param element Das Element welches geprüft wird
	 * @return Minimal annehmbarer Wert von element
	 */
	public int getMinWertCharElement(CharElement element, CharKomponenten komponente) {
//		 TODO implement
		return 0;		
	}
    
}
