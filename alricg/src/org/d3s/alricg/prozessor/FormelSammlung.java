/*
 * Created on 09.03.2005 / 17:59:33
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.prozessor;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.controller.ProgAdmin;

/**
 * <u>Beschreibung:</u><br> 
 * 
 * @author V. Strelow
 */
public class FormelSammlung {
	public static int KEIN_WERT = CharElement.KEIN_WERT;
	
	/**
	 * Bildet die Spalten der SKT ab und bietet rudimentäre Operationen.
	 *
	 * @author V. Strelow
	 */
    public enum KostenKlasse { 
    	A_PLUS ("A+"), 
    	A("A"), 
    	B("B"), 
    	C("C"), 
    	D("D"), 
    	E("E"), 
    	F("F"), 
    	G("G"), 
    	H("H"); 
		private String value; // Id des Elements
		
		private KostenKlasse(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		
		/**
		 * Liefert eine KostenKlasse die um einen schritt "nach Rechts" geschoben
		 * ist, also die KostenKlasse und somit die Kosten um eine Spalte erhöht.
		 * 
		 * @return Die um eine Spalte erhöhte Kostenklasse, bzw. das Maximum, 
		 * @param kostenK Die bisherige Kostenklasse
		 * 		wenn es nicht höher geht.
		 */
		public KostenKlasse plusEineKk() {
			if ( this.ordinal() == (KostenKlasse.values().length - 1) ) {
				return this; // Es geht nicht höher
			}
			
			return KostenKlasse.values()[this.ordinal()+1];
		}
		
		/**
		 * Liefert eine KostenKlasse die um einen schritt "nach Links" geschoben
		 * ist, also die KostenKlasse und somit die Kosten um eine Spalte erniedrigt.
		 * 
		 * @param kostenK Die bisherige Kostenklasse
		 * @return Die um eine Spalte erniedrigte Kostenklasse, bzw. das Minimum, 
		 * 		wenn es nicht niedriger geht.
		 */
		public KostenKlasse minusEineKk() {
			if ( this.ordinal() == 0 ) {
				return this; // Es geht nicht niedriger
			}
			
			return KostenKlasse.values()[this.ordinal()-1];
		}
    }
    public enum Lernmethode {
    	selbstStudium, // eine Spalte schwerer, bei Talent ab 10 sogar 2 Spalten schwerer
    	lehrmeister, // wie angegeben
    	spezielleErfahrung, // ein Spalte leichter
    	sehrGuterLehrmeister // ein Spalte leichter
    }
    
	/**
	 * Liefert zu einem XML-Tag die entsprechende Enum zurück.
	 * @param value Id  der KostenKlasse
	 * @return Die Enum KostenKlasse die zu value gehört
	 */
	public static KostenKlasse getKostenKlasseByValue(String value) {
		KostenKlasse[] kostenArray = KostenKlasse.values();
		
		// Suchen des richtigen Elements
		for (int i = 0; i < kostenArray.length; i++) {
			if (value.equals(kostenArray[i].getValue())) {
				return kostenArray[i]; // Gefunden
			}
		}
		
		ProgAdmin.logger.severe("XmlValue konnte nicht gefunden werden!");
		
		return null;
	}
	
	/**
	 * Liefert die Ap-Kosten für die entsprechende Stufe und KostenKlasse mittels SKT
	 * @param startStufe Die bisherige Stufe des Elements; KEIN_WERT oder ein negativer 
	 * 			Wert bedeutet das es nicht aktiviert wurde!
	 * @param zielStufe Die gewünschte Stufe
	 * @param heldenStufe Die Stufe des Helden (nur bei aktivierung wichtig)
	 * @param methode Die gewünschte Lehrnmethode
	 * @param klasse Die gewünschte Kostenklasse
	 * @return Die AP-Kosten für die Steigerung (inkl. evtl. aktivierung)
	 */
	public static int berechneSktKosten(int startStufe, int zielStufe, 
								   int heldenStufe, Lernmethode methode, 
								   KostenKlasse kKlasse) {
		// TODO implement
		
		return 0;
	}
	
	/**
	 * Vereinfachte Version von "getSktKosten" für die Generierung. Die Heldenstufe ist
	 * stehts "1" und die Lernmethode ist stehts "lehrmeister".
	 * 
	 * @param startStufe Die bisherige Stufe des Elements; KEIN_WERT oder ein negativer 
	 * 			Wert bedeutet das es nicht aktiviert wurde!
	 * @param zielStufe Die gewünschte Stufe
	 * @param klasse Die gewünschte Kostenklasse
	 * @return Die AP-Kosten für die Steigerung (inkl. evtl. aktivierung)
	 */
	public static int berechneSktKosten(int startStufe, int zielStufe, KostenKlasse kKlasse) {
		return berechneSktKosten(startStufe, zielStufe, 1, Lernmethode.lehrmeister, kKlasse);
	}
	
	/**
	 * Berechnet die AP die für eine Sonderfertigkeit gezahlt werden muß.
	 * Dafür werden die GP der SF genommen und mit 50 Multipliziert.
	 * @param gp Die GP-Kosten einer Sonderfertigkeit
	 * @return Die AP-Kosten für diese Sonderfertigkeit
	 */
	public static int berechneSfAp(int gp) {
		return (gp * 50);
	}
	
// ------------------ Berechnung der Grundwerte -----------------------
	/**
	 * Berechnet die Magieresistenz OHNE Modifikatoren
	 * @param MU Mut des Helden
	 * @param KL Klugheit des Helden
	 * @param KO Konstitution des Helden
	 * @return Die errechnete Magieresistenz
	 */
	public static int berechneMR(int MU, int KL, int KO) {
		return (int) Math.round( (MU + KL + KO) / 5d );
	}
	
	/**
	 * Berechnet die Initiative OHNE Modifikatoren
	 * @param MU Mut 
	 * @param IN Intuition
	 * @param GE Gewandheit
	 * @return Die errechnete Initiative
	 */
	public static int berechneINI(int MU, int IN, int GE) {
		return (int) Math.round( ((MU*2) + IN + GE) / 5d );
	}
	
	/**
	 * Berechnet den Basis Attackewert OHNE Modifikatoren
	 * @param MU Mut 
	 * @param GE Gewandheit
	 * @param KK Körperkraft
	 * @return Die errechnete AT Basis
	 */
	public static int berechneAtBasis(int MU, int GE, int KK) {
		return (int) Math.round( (MU + GE + KK) / 5d );
	}
	
	/**
	 * Berechnet den Basis Paradewert OHNE Modifikatoren
	 * @param IN Intuition 
	 * @param GE Gewandheit
	 * @param KK Körperkraft
	 * @return Die errechnete PA Basis
	 */
	public static int berechnePaBasis(int IN, int GE, int KK) {
		return (int) Math.round( (IN + GE + KK) / 5d );
	}
	
	/**
	 * Berechnet den Basis Fernkampfwert OHNE Modifikatoren
	 * @param IN Intuition 
	 * @param FF Fingerfertigkeit
	 * @param KK Körperkraft
	 * @return Die errechnete FK Basis
	 */
	public static int berechneFkBasis(int IN, int FF, int KK) {
		return (int) Math.round( (IN + FF + KK) / 5d );
	}
	
	/**
	 * Berechnet den Basis Lebenspunkte OHNE Modifikatoren
	 * @param KO Konstitution
	 * @param KK Körperkraft
	 * @return Die errechneten Lebenspunkte
	 */
	public static int berechneLep(int KO, int KK) {
		return (int) Math.round( ((KO*2) + KK) / 2d );
	}
	
	/**
	 * Berechnet den Basis Ausdauer OHNE Modifikatoren
	 * @param MU Mut 
	 * @param KO Konstitution
	 * @param GE Gewandheit
	 * @return Die errechnete Ausdauer
	 */
	public static int berechneAup(int MU, int KO, int GE) {
		return (int) Math.round( (MU + KO + GE) / 2d );
	}
	
	/**
	 * Berechnet den Basis Astralpunkte OHNE Modifikatoren
	 * @param MU Mut 
	 * @param IN Intuition
	 * @param CH Charisma
	 * @return Die errechnete Ausdauer
	 */
	public static int berechneAsp(int MU, int IN, int CH) {
		return (int) Math.round( (MU + IN + CH) / 2d );
	}
}
