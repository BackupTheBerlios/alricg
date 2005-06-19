/*
 * Created 22. Dezember 2004 / 14:23:42
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten.links;

import org.d3s.alricg.charKomponenten.Herkunft;

/**
 * <b>Beschreibung:</b><br>
 * Wenn es möglich ist, das der Benutzer unter mehreren Möglichtkeiten wählt, so
 * wird diese Klasse zur Speicherung der Elemente benutzt.
 * 
 * @author V.Strelow
 */
public class Auswahl {
	
	/**
	 *Gibt den Modus der Auswahl an
	 */
	public enum Modus {
		LISTE("LISTE"), ANZAHL("ANZAHL"), VERTEILUNG("VERTEILUNG");
		
		private String value;
		
		private Modus(String value) {
			this.value = value;
		}
		
		/**
		 * @return Den Wert, wie er im XML Dokument auftaucht
		 */
		public String getValue() {
			return value;
		}
	}
	
	private VariableAuswahl[] varianteAuswahl;
    private Herkunft herkunft; // Das CharElement, von dem die Auswahl kommt
	protected IdLink[] festeAuswahl; // Die unveränderlichen Werte
	
	
	/**
	 * Konstruktor
	 * @param herkunft Die "quelle" dieser Auswahl
	 */
	public Auswahl(Herkunft herkunft) {
		this.herkunft = herkunft;
	}
	
	/**
	 * @return Array von festen Elementen, die das auf jedenfall zu dieser
	 * Auswahl gehören (also nicht gewählt werden).
	 */
	public IdLink[] getFesteAuswahl() {
		return festeAuswahl;
	}
	
	/**
	 * Jede Auswahl gehört zu einem Element, von dem diese Auswahl stammt.
	 * @return Liefert das CharElement, von dem diese Auswahl stammt.
	 */
	public Herkunft getHerkunft() {
		return herkunft;
	}
	
	public void addVarianteAuswahl(Modus modus, int[] werte, int anzahl, IdLink[] optionen) {
		// TODO Implement
	}
	
	/**
	 * @return Liefert das Attribut varianteAuswahl.
	 */
	public VariableAuswahl[] getVarianteAuswahl() {
		return varianteAuswahl;
	}
	/**
	 * @param varianteAuswahl Setzt das Attribut varianteAuswahl.
	 */
	public void setVarianteAuswahl(VariableAuswahl[] varianteAuswahl) {
		this.varianteAuswahl = varianteAuswahl;
	}
	/**
	 * @param festeAuswahl Setzt das Attribut festeAuswahl.
	 */
	public void setFesteAuswahl(IdLink[] festeAuswahl) {
		this.festeAuswahl = festeAuswahl;
	}
	/**
	 * @param herkunft Setzt das Attribut herkunft.
	 */
	public void setHerkunft(Herkunft herkunft) {
		this.herkunft = herkunft;
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
	public class VariableAuswahl {
        private Modus modus;
        private int[] werte;
        private int anzahl;
        private int max;
        private IdLink[] optionen;
        private IdLink[][] optionsGruppe;
        private Auswahl auswahl;
        
		public VariableAuswahl(Auswahl auswahl) {
			this.auswahl = auswahl;
		}
		
		/**
		 * @return Liefert das Attribut max.
		 */
		public int getMax() {
			return max;
		}
		/**
		 * @param max Setzt das Attribut max.
		 */
		public void setMax(int max) {
			this.max = max;
		}
		
		/**
		 * @return Liefert das Attribut anzahl.
		 */
		public int getAnzahl() {
			return anzahl;
		}
		/**
		 * @param anzahl Setzt das Attribut anzahl.
		 */
		public void setAnzahl(int anzahl) {
			this.anzahl = anzahl;
		}
		/**
		 * @return Liefert das Attribut auswahl.
		 */
		public Auswahl getAuswahl() {
			return auswahl;
		}
		/**
		 * @param auswahl Setzt das Attribut auswahl.
		 */
		public void setAuswahl(Auswahl auswahl) {
			this.auswahl = auswahl;
		}
		/**
		 * @return Liefert das Attribut modus.
		 */
		public Modus getModus() {
			return modus;
		}
		/**
		 * @param modus Setzt das Attribut modus.
		 */
		public void setModus(Modus modus) {
			this.modus = modus;
		}
		/**
		 * @return Liefert das Attribut optionen.
		 */
		public IdLink[] getOptionen() {
			return optionen;
		}
		/**
		 * @param optionen Setzt das Attribut optionen.
		 */
		public void setOptionen(IdLink[] optionen) {
			this.optionen = optionen;
		}
		/**
		 * @return Liefert das Attribut optionsGruppe.
		 */
		public IdLink[][] getOptionsGruppe() {
			return optionsGruppe;
		}
		/**
		 * @param optionsGruppe Setzt das Attribut optionsGruppe.
		 */
		public void setOptionsGruppe(IdLink[][] optionsGruppe) {
			this.optionsGruppe = optionsGruppe;
		}
		/**
		 * @return Liefert das Attribut werte.
		 */
		public int[] getWerte() {
			return werte;
		}
		/**
		 * @param werte Setzt das Attribut werte.
		 */
		public void setWerte(int[] werte) {
			this.werte = werte;
		}
	}
}
