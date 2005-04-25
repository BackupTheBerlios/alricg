/*
 * Created 23. Januar 2005 / 15:30:43
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.held;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Herkunft;
import org.d3s.alricg.charKomponenten.Kultur;
import org.d3s.alricg.charKomponenten.Profession;
import org.d3s.alricg.charKomponenten.Rasse;
import org.d3s.alricg.charKomponenten.Sprache;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;

/**
 * <b>Beschreibung:</b><br>
 * Repr�sentiert einen Charakter der erstellt wird bzw. erstellt wurde.
 * @author V.Strelow
 */
public class Held {
	// Die Herkunft des Helden
	private Profession profession;
	private Kultur kultur;
	private Rasse rasse;    
    private Sprache[] muttersprache; // kann mehrere geben, siehe "Golbin Festumer G"

    
    /**
     * �berpr�ft ob ein CharElement zu dem Held hinzugef�gt werden kann 
     * (Ausschlu� m�glich durch nicht erf�llen von Voraussetzungen,
     * Unvereinbar mit anderen CharElementen oder dass das CharElement
     * schon vorhanden ist und nicht doppelt gew�hlt werden kann).
     * 
     * @param element Das Element welches gepr�ft wird
     * @param komponente Die Art des Elements als CharKomponente 
     * @param herkunft Falls das Element durch eine Rasse, Kultur, Prof 
     * 		hinzugef�gt wird, wird diese hier �bergeben. Ansonsten "null"
     * @return true - Kann hinzugef�gt werden, ansonsten false 
     */
	public boolean canAddElement(
				CharElement element, 
				CharKomponente komponente, 
				Herkunft herkunf) {
		// TODO implement, �berlege elegantes Konzept ob eine Meldung abgesetzt
		// wird!
		return false;
	}
	
	/**
	 * F�gt das Element hinzu, Regelverst��e werden nicht verhindert, 
	 * aber vermerkt.
	 * 
	 * @param element Das Element welches hinzugef�gt wird
     * @param komponente Die Art des Elements als CharKomponente 
     * @param herkunft Falls das Element durch eine Rasse, Kultur, Prof 
     * 		hinzugef�gt wird, wird diese hier �bergeben. Ansonsten "null"
	 */
	public void addElement(
			CharElement element, 
			CharKomponente komponente, 
			Herkunft herkunf) {
		// TODO implement
	}
	
	
	public boolean canRemoveElement(CharElement element, CharKomponente komponente) {
		// TODO implement
		return false;
	}
	
	public void removeElement(CharElement element, CharKomponente komponente) {
		// TODO implement
	}
	
	/**
	 * Liefert den maximalen Wert, den das CharElement erreichen kann. 
	 * @param element Das Element welches gepr�ft wird
	 * @return Maximal annehmbarer Wert von element
	 */
	public int getMaxWertElement(CharElement element, CharKomponente komponente) {
//		 TODO implement
		return 0;
	}
	
	/**
	 * Liefert den minimalen Wert, den das CharElement annehmen kann. 
	 * @param element Das Element welches gepr�ft wird
	 * @return Minimal annehmbarer Wert von element
	 */
	public int getMinWertElement(CharElement element, CharKomponente komponente) {
//		 TODO implement
		return 0;		
	}
    
}
