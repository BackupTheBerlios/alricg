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
	 *Gibt den Modus der Auswahl an.
	 *
	 * LISTE - In "wert" steht eine Liste von Werten, wobei jeder Wert einer "option"
	 *   zugewiesen werden muß. Es werden soviele optionen gewählt, wie es werte gibt. 
	 *  (Das Attribut "anzahl" und "max" wird nicht benutzt)
	 *  Beispiel: "Gaukler" S. 87 AH
	 *  	"Abrichten o. Falschspiel o. Malen/Zeichen o. Musizieren +4, ein anderes +2"
	 *  	: wert="4,2" 
	 *  	  optionen="Abrichten, Falschspiel, Malen/Zeichen, Musizieren"
	 *  
	 * ANZAHL - In "anzahl" steht eine Zahl, die angibt wieviele der "optionen" gewählt 
	 *  werden müssen. Jede option kann einen Wert haben (über den "idLinkTyp").
	 *  (Das attribut "wert" und "max" wird	nicht benutzt)
	 *  Beispiel:
	 *  	"Goldgier 5 o. Schulden 1000 Dukaten"
	 *  	: anzahl="1" 
	 *  	  optionen="Goldgier(wert=4), Schulden(wert=1000)"
	 *  
	 * VERTEILUNG - Der Wert in "wert" kann auf soviele der Optionen verteilt werden, wie
	 *  im Attribut "anzahl" angegeben. D.h. erst werden die Optionen ausgewählt, dann der
	 *  "wert" beliebig auf die gewählten optionen verteilt. 
	 *  (Siehe "Elfische Siedlung" S. 37 im AZ( In "max" steht wie hoch die Stufe
	 *   jeder gewählten option sein darf.
	 *  Beispiel: "Tagelöhnerin" S. 101 AH
	 *  	"20 Punkte auf passende Handwerkstalente (je max +3)"
	 *  	: wert="20" 
	 *  	  anzahl="0" (für keine Begegrenzung)
	 * 		  mas = "3"
	 *  	  optionen="Holzarbeiten, Lederarbeiten, Wundenheilen, ...."
	 */
	public enum Modus {
		LISTE("LISTE"), ANZAHL("ANZAHL"), VERTEILUNG("VERTEILUNG");
		
		private String value;
		
		/* Konstruktor
		 */
		private Modus(String value) {
			this.value = value;
		}
		
		/**
		 * @return Den Bezeichner, wie er im XML Dokument auftaucht
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
	 * @return true - Es gibt eine "feste Auswahl", d.h. es gibt eine Liste 
	 * 	von Werten bei denen der User nicht wählen kann. 
	 * 		false - Es gibt KEINE solche Liste.
	 */
	public boolean hasFesteAuswahl() {
		return (festeAuswahl != null && festeAuswahl.length != 0);
	}
	
	/**
	 * Jede Auswahl gehört zu einem Element, von dem diese Auswahl stammt.
	 * @return Liefert das CharElement, von dem diese Auswahl stammt.
	 */
	public Herkunft getHerkunft() {
		return herkunft;
	}
	
	/**
	 * 
	 * Ein Array aller variablen Auswahlen kann. Wie oben angegeben hat 
	 * jede variable Auswahl einen Modus, der angibt wie die Werte zu interpretieren 
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
	 * @return true - Es gibt eine "variante Auswahl", d.h. es gibt eine Liste 
	 * 	von Werten bei denen der User wählen kann welche Optionen der haben 
	 *  möchte und welche nicht. 
	 * 		false - Es gibt KEINE solche Liste.
	 */
	public boolean hasVarianteAuswahl() {
		return (varianteAuswahl != null && varianteAuswahl.length != 0);
	}
	
	/**
	 * Ein Array aller variablen Auswahlen kann. Wie oben angegeben hat 
	 * jede variable Auswahl einen Modus, der angibt wie die Werte zu interpretieren 
	 * sind.
	 * 
	 * 	Beispiele:
	 * - "Talent Schwerter +2 oder Talent Dolche +3" (Modus ANZAHL) 
	 * - "Die Werte +3, +2, +1 beliebig auf die Talente Schwerter, Dolche, 
	 * 		Klettern verteilen" (Modus LISTE)
	 * - "Fünf Punkte beliebig verteilen auf die Talente Schwerter und Dolche"
	 * (Modus VERTEILUNG)
	 * 
	 * @param varianteAuswahl Setzt das Attribut varianteAuswahl.
	 */
	public void setVarianteAuswahl(VariableAuswahl[] varianteAuswahl) {
		this.varianteAuswahl = varianteAuswahl;
	}
	
	/**
	 * Dies ist eine feste Liste von Modis. Es ist einfach eine Auflistung der
	 * Modifikationen ohne das der Benutzer etwas wählen kann.
	 * Beispiel:
	 * 	"Schwerter +3, Klettern +2, Schwimmen +4"
	 *  
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
	 * Wenn der Benutzer zwischen mehrern Optionen auswählen kann, wird eine 
	 * Opbjekt dieser Klasse zur repräsentation benutzt.
	 */
	public class VariableAuswahl {
        private Modus modus;
        private int[] werte;
        private int anzahl;
        private int max;
        private IdLink[] optionen;
        private IdLink[][] optionsGruppe;
        private Auswahl auswahl;
        
        /**
         * Konstruktor
         * @param auswahl Die Auswahl, zu der diese VariableAuswahl gehört
         */
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
		 * 
		 * ANZAHL - In "anzahl" steht eine Zahl, die angibt wieviele der "optionen" gewählt
		 *  werden müssen. Jede option kann einen Wert haben (über den "idLinkTyp"). 
		 * VERTEILUNG - Der Wert in "wert" kann auf soviele der Optionen verteilt werden,
		 *  wie im Attribut "anzahl" angegeben. D.h. erst werden die Optionen ausgewählt, 
		 *  dann der "wert" beliebig auf die gewählten optionen verteilt. (Siehe "Elfische 
		 *  Siedlung" S. 37 im AZ) In "max" steht wie hoch die Stufe jeder 
		 *  gewählten option sein darf 
		 * 
		 * Ist "anzahl = 0", so gibt es keine Begrenzung!
		 * @return Liefert das Attribut anzahl.
		 */
		public int getAnzahl() {
			return anzahl;
		}
		/**
		 * Nur im Modus "ANZAHL" & "VERTEILUNG" wichtig!
		 * Ist "anzahl = 0", so gibt es keine Begrenzung!
		 * 
		 * @see getAnzahl()
		 * @param anzahl Setzt das Attribut anzahl.
		 */
		public void setAnzahl(int anzahl) {
			this.anzahl = anzahl;
		}
		/**
		 * 
		 * @return Liefert die Auswahl, zu der diese VariableAuswahl gehört
		 */
		public Auswahl getAuswahl() {
			return auswahl;
		}
		/**
		 * @param auswahl Die Auswahl, zu der diese VariableAuswahl gehört
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
		 * - "Schwerter +5 oder Dolche +5" (modus ANZAHL)
		 * 		Optionen sind:
		 * 			Link mit Ziel "Schwerter" und Wert "5"
		 * 			Link mit Ziel "Dolche" und Wert "5"
		 * - "3 Punkte beliebig auf Schwerter und Dolche" (Modus VERTEILUNG)
		 * 		Optionen sind:
		 * 			Link mit Ziel "Schwerter"
		 * 			Link mit Ziel "Dolche"
		 * 			(Werte werden in Auswahl angegeben, nicht in dem Link)
		 * 
		 * @see getOptionsGruppe()
		 * @return Liefert ein Array aller (ausgenommen Optionsgruppen) wählbareren Optionen.
		 */
		public IdLink[] getOptionen() {
			return optionen;
		}
		
		/**
		 * @return true - Es gibt eine Liste von wählbaren "einstelligen"
		 * 	Optionen, ansonsten false (es gibt keine einstelligen Optionenen sondern
		 * nur evtl. Optionsgruppen).
		 */
		public boolean hasOptionen() {
			return (optionen != null && optionen.length != 0);
		}
		
		/**
		 * @return true - Es gibt eine Liste von wählbaren Gruppen von
		 * 	Optionen, ansonsten false.
		 */
		public boolean hasOptionsgruppen() {
			return (optionsGruppe != null && optionsGruppe.length != 0);
		}
		
		/**
		 * @see getOptionen()
		 * @param optionen Alle wählbaren Optionen
		 */
		public void setOptionen(IdLink[] optionen) {
			this.optionen = optionen;
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
		public IdLink[][] getOptionsGruppe() {
			return optionsGruppe;
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
		 * @param optionsGruppe Setzt das Attribut optionsGruppe.
		 */
		public void setOptionsGruppe(IdLink[][] optionsGruppe) {
			this.optionsGruppe = optionsGruppe;
		}
		/**
		 * Nur für "LISTE" und "VERTEILUNG" wichtig!
		 * LISTE - In "wert"steht eine Liste von Werten, wobei jeder Wert einer
		 * 	"option" zugewiesen werden muß. Es werden soviele optionen gewählt, wie
		 * 	 es werte gibt. 
		 * VERTEILUNG - Der Wert in "wert" kann auf soviele der Optionen verteilt werden,
		 *  wie im Attribut "anzahl" angegeben. D.h. erst werden die Optionen ausgewählt, 
		 *  dann der "wert" beliebig auf die gewählten optionen verteilt. (Siehe 
		 *  "Elfische Siedlung" S. 37 im AZ)
		 * 	In "max" steht wie hoch die Stufe jeder gewählten option sein darf
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
		 * VERTEILUNG - Der Wert in "wert" kann auf soviele der Optionen verteilt werden,
		 *  wie im Attribut "anzahl" angegeben. D.h. erst werden die Optionen ausgewählt, 
		 *  dann der "wert" beliebig auf die gewählten optionen verteilt. (Siehe 
		 *  "Elfische Siedlung" S. 37 im AZ)
		 * 	In "max" steht wie hoch die Stufe jeder gewählten option sein darf
		 * 
		 * @param werte Setzt das Attribut werte.
		 */
		public void setWerte(int[] werte) {
			this.werte = werte;
		}
	}
}
