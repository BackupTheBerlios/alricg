/*
 * Created 22. Dezember 2004 / 14:23:42
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.Links;

import org.d3s.alricg.CharKomponenten.CharElement;
import org.d3s.alricg.CharKomponenten.Herkunft;

/**
 * <b>Beschreibung:</b><br>
 * Wenn es möglich ist, das der Benutzer unter mehreren Möglichtkeiten wählt, so
 * wird diese Klasse zur Speicherung der Elemente benutzt.
 * 
 * @author V.Strelow
 */
public class Auswahl {
	public enum Modus { LISTE, ANZAHL, VERTEILUNG }
	
	private VarianteAuswahl[] varianteAuswahl;
    private Herkunft herkunft; // Das CharElement, von dem die Auswahl kommt
	private IdLink[] festeAuswahl; // Die unveränderlichen Werte
	
	
	public Auswahl(Herkunft herkunft, IdLink[] festeAuswahl) {
		this.herkunft = herkunft;
		this.festeAuswahl = festeAuswahl; 
	}
	
	/**
	 * @return Array von festen Elementen, die das auf jedenfall zu dieser
	 * Auswahl gehören (also nicht gewählt werden).
	 */
	public IdLink[] getFesteAuswahl() {
		return festeAuswahl;
	}	
	
	/**
	 * Jede Auswahl gehört zu einem Elemnt.
	 * @return Liefert das CharElement, von dem diese Auswahl stammt.
	 */
	public CharElement getHerkunft() {
		return herkunft;
	}
	
	public void addVarianteAuswahl(Modus modus, int[] werte, int anzahl, IdLink[] optionen) {
		// TODO Implement
	}
	
	/**
	 * Bedeutung des Modus (siehe enum Oben):
	 * LISTE - In "wert"steht eine Liste von Werten, wobei jeder Wert einer
     * 	"option" zugewiesen werden muß. Es werden soviele optionen gewählt, wie
     * 	 es werte gibt. (Das attribut "anzahl" wird nicht benutzt)
 	 * ANZAHL - In "wert" steht eine Zahl, die angibt wieviele der "optionen"
     * 	gewählt werden müssen. Jede option kann einen Wert haben (über den
     * 	"idLinkTyp"). (Das attribut "anzahl" wird nicht benutzt)
	 * VERTEILUNG - Der Wert in "wert" kann auf soviele der Optionen verteilt
     *  werden, wie im Attribut "anzahl" angegeben. D.h. erst werden die
     *  Optionen ausgewählt, dann der "wert" beliebig auf die gewählten optionen
     *  verteilt. (Siehe "Elfische Siedlung" S. 37 im AZ)
	*/
	private class VarianteAuswahl {
        Modus modus;
        int[] werte;
        int anzahl;
        IdLink[] optionen;

		public VarianteAuswahl(Modus modus, int[] werte, int anzahl, IdLink[] optionen) {
			this.modus = modus;
			this.werte = werte;
            this.anzahl = anzahl;
            this.optionen = optionen;
            
            
		}		
		
	}
}
