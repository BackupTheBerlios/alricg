/*
 * Created 22. Dezember 2004 / 14:23:42
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.Links;

/**
 * <b>Beschreibung:</b><br>
 * Wenn es möglich ist, das der Benutzer unter mehreren möglichtkeiten wählt, so
 * wird diese Klasse zur Speicherung der Elemente benutzt.
 * 
 * @author V.Strelow
 */
public class Auswahl {
	private VarianteAuswahl[] varianteAuswahl;

	/**
	 * @clientCardinality 1 
	 */
	private IdLink[] festeAuswahl;

	/**
	 *	LISTE - In "wert"steht eine Liste von Werten, wobei jeder Wert einer
     * 	"option" zugewiesen werden muß. Es werden soviele optionen gewählt, wie
     * 	 es werte gibt. (Das attribut "anzahl" wird nicht benutzt)
 	 *	ANZAHL - In "wert" steht eine Zahl, die angibt wieviele der "optionen"
     * 	gewählt werden müssen. Jede option kann einen Wert haben (über den
     * 	"idLinkTyp"). (Das attribut "anzahl" wird nicht benutzt)
	 *	VERTEILUNG - Der Wert in "wert" kann auf soviele der Optionen verteilt
     *  werden, wie im Attribut "anzahl" angegeben. D.h. erst werden die
     * Optionen ausgewählt, dann der "wert" beliebig auf die gewählten optionen
     * verteilt. (Siehe "Elfische Siedlung" S. 37 im AZ)
	*/
	private static class VarianteAuswahl {
		public static final int LISTE = 0;
		public static final int ANZAHL = 1;
        public static final int VERTEILUNG = 2;
        int modus;
        int[] werte;
        int anzahl;
		IdLink[] optionen;

		public VarianteAuswahl(int modus, int[] werte, int anzahl, IdLink[] optionen) {
			this.modus = modus;
			this.werte = werte;
            this.anzahl = anzahl;
            this.optionen = optionen;
		}
	}
}
