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
 * Oftmals werden Werte von CharElementen modifiziert, die geschieht über die
 * Auswahl. In einer Auswahl kann einerseits eine Liste von festen Modifikationen
 * stehten und außerdem kann eine Liste von variablen Modifikationen 
 * enthalten sein.
 * 
 *  Beispiel von festen Elementen einer Auswahl:
 * - "Schwerter +3, Klettern +2, Schwimmen +4". Dies ist eine feste Liste von 
 * Modis. Es ist einfach eine Auflistung der Modifikationen ohne das der
 * Benutzer etwas wählen kann. Diese Liste kann als Array mit der 
 * Methode "getFesteAuswahl()" abgerufen werden
 * 
 * 
 * Wenn es möglich ist, das der Benutzer unter mehreren Möglichtkeiten wählt, so
 * wird ebenfalls diese Klasse zur Speicherung der Elemente benutzt.
 * 
 * Beispiel von variablen Elementen einer Auswahl:
 * - "Talent Schwerter +2 oder Talent Dolche +3" (Modus ANZAHL) 
 * - "Die Werte +3, +2, +1 beliebig auf die Talente Schwerter, Dolche, 
 * 		Klettern verteilen" (Modus LISTE)
 * - "Fünf Punkte beliebig verteilen auf die Talente Schwerter und Dolche"
 * (Modus VERTEILUNG)
 * 
 * Ein Array aller variablen Auswahlen kann über die Methode 
 * "getVarianteAuswahl()" abgerufen werden. Wie oben angegeben hat jede v. Auswahl
 * einen Modus, der angibt wie die Werte zu interpretieren sind.
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
	 * Dies ist eine feste Liste von Modis. Es ist einfach eine Auflistung der
	 * Modifikationen ohne das der Benutzer etwas wählen kann.
	 * Beispiel:
	 * 	"Schwerter +3, Klettern +2, Schwimmen +4" 
	 *   
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
	
	/*
	public void addVarianteAuswahl(Modus modus, int[] werte, int anzahl, IdLink[] optionen) {
		// TODO Implement
	}
	*/
	
	/**
	 * 
	 * Ein Array aller variablen Auswahlen kann. Wie oben angegeben hat 
	 * jede v. Auswahl einen Modus, der angibt wie die Werte zu interpretieren 
	 * sind.
	 * 
	 * 	Beispiele:
	 * - "Talent Schwerter +2 oder Talent Dolche +3" (Modus ANZAHL) 
	 * - "Die Werte +3, +2, +1 beliebig auf die Talente Schwerter, Dolche, 
	 * 		Klettern verteilen" (Modus LISTE)
	 * - "Fünf Punkte beliebig verteilen auf die Talente Schwerter und Dolche"
	 * (Modus VERTEILUNG)
	 * 
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
		 * Nur im Modus "VERTEILUNG" wichtig!
		 * Gibt die maximal Stufe der gewählten CharElemente an.
		 * 
		 * @return Liefert das Attribut max.
		 */
		public int getMax() {
			return max;
		}
		/**
		 * Nur im Modus "VERTEILUNG" wichtig!
		 * Gibt die maximal Stufe der gewählten CharElemente an.
		 * 
		 * @param max Setzt das Attribut max.
		 */
		public void setMax(int max) {
			this.max = max;
		}
		
		/**
		 * Nur im Modus "ANZAHL" & "VERTEILUNG" wichtig!
		 * Gibt die Anzahl an zu wählenden CharElemente an.
		 * 
		 * @return Liefert das Attribut anzahl.
		 */
		public int getAnzahl() {
			return anzahl;
		}
		/**
		 * Nur im Modus "ANZAHL" & "VERTEILUNG" wichtig!
		 * Gibt die Anzahl an zu wählenden CharElemente an.
		 * 
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
		 * Eine Option ist eine möglichkeit die gewählt werden kann. Jede
		 * Option ist ein Link und somit auch ein CharElement.
		 * Diese Methode leifert ein Array aller möglichen Optionen, jede 
		 * Arraystelle ist eine Option. Wie die Optionen ausgewählt werden,
		 * wird durch den Modus angegeben.
		 * 
		 * Beispiel:
		 * - "Schwerter +5 oder Dolche +5"
		 * 		Optionen sind:
		 * 			Link mit Ziel "Schwerter" und Wert "5"
		 * 			LInk mit Ziel "Dolche" und Wert "5"
		 * - "3 Punkte 
		 * 
		 * @see getOptionsGruppe()
		 * @return Liefert das Attribut optionen.
		 */
		public IdLink[] getOptionen() {
			return optionen;
		}
		/**
		 * Eine Optionsgruppe ist erstmal eine gleichberechtigte Option
		 * wie auch jene, die per "getOptionen()" geliefert werden. In einer 
		 * Optionsgruppe sind jedoch mehrer Link (und somit CharElemente) enthalten,
		 * die alle nur gemeinsam gewählt oder nicht gewählt werden können.
		 * 
		 * Beispiel:
		 * 	- "Schwerter +5 oder Dolche +2 und Klettern +3"
		 * In diesem Beispiel ist eine normale Option enthalten (Schwerter)
		 * und eine Gruppe (Dolche und Klettern) die nur gemeinsam gewählt 
		 * werden können. 
		 * 
		 * @see getOptionen()
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
		 * Nur für "LISTE" und "VERTEILUNG" wichtig!
		 * LISTE - In "wert" steht eine Liste von Werten, wobei jeder Wert einer
		 * 	"option" zugewiesen werden muß. Es werden soviele optionen gewählt, wie
		 * 	 es werte gibt.
		 * ANZAHL - In "wert" steht eine Zahl, die angibt wieviele der "optionen"
		 * 	gewählt werden müssen. Jede option kann einen Wert haben (über den
		 * 	"Link").
		 * 
		 * @return Liefert das Attribut werte.
		 */
		public int[] getWerte() {
			return werte;
		}
		/**
		 * Nur für "LISTE" und "VERTEILUNG" wichtig!
		 * LISTE - In "wert"steht eine Liste von Werten, wobei jeder Wert einer
		 * 	"option" zugewiesen werden muß. Es werden soviele optionen gewählt, wie
		 * 	 es werte gibt. 
		 * ANZAHL - In "wert" steht eine Zahl, die angibt wieviele der "optionen"
		 * 	gewählt werden müssen. Jede option kann einen Wert haben (über den
		 * 	"Link").
		 * 
		 * @param werte Setzt das Attribut werte.
		 */
		public void setWerte(int[] werte) {
			this.werte = werte;
		}
	}
}
