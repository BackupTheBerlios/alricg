/*
 * Created on 09.03.2005 / 17:59:33
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.prozessor;

import java.util.HashMap;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.store.ConfigurationException;

/**
 * <u>Beschreibung:</u><br> 
 * 
 * @author V. Strelow
 */
public class FormelSammlung {
	public final static int KEIN_WERT = CharElement.KEIN_WERT;
	private static HashMap<KostenKlasse, Integer[]> skt = new HashMap<KostenKlasse, Integer[]>();
	//private static int[] sktPlus = { 50, 100, 150, 200, 250, 375, 500, 1000 }; // [sktSpalte] -> Der Wert bei über 30
 	private final static int maxSktStufe = 31;
 	
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
		 * @param kostenK Die bisherige Kostenklasse
		 * 		w
		 * @return Die um eine Spalte erhöhte Kostenklasse, bzw. das Maximum, 
		 *  wenn es nicht höher geht.
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
    
    public static void initFormelSanmmlung() {
    	try {
    		skt = ProgAdmin.config.getSkt();
    	} catch (ConfigurationException e) {
    		// TODO Richtige Fehlermeldung einbauen!
    		System.out.println("Konnte SKT nicht laden!");
    	}
    }
    
    /**
     * Liefert einen Wert einer Zeile/Spalte der Steigerungskosten Tabelle.
     * @param kKlasse Die gewünschte KostenKlasse
     * @param stufe Die gewünschte Stufe
     * @return Den Wert, der in der SKT in der Zeile "KostenKlasse" und in der 
     * 		Spalte "Stufe". 
     */
    public static int getSktWert(KostenKlasse kKlasse, int stufe) {
    	
    	if (stufe > maxSktStufe) {
    		stufe = maxSktStufe; // Ab 31 kosten alle Stufen gleich viel
    	}
    	
    	if (kKlasse.equals(KostenKlasse.A_PLUS)) {
    		int tmp = (skt.get(KostenKlasse.A)[stufe] - 2);
    		if (tmp < 1) tmp = 1;
    		return tmp;
    	} else {
    		return skt.get(kKlasse)[stufe];
    	}
    	
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
	 * 				 "0" Bedeutet: Generierung = Wert aus der Tabelle
	 * 				 Negative Werte sind nicht erlaubt! 
	 * @param methode Die gewünschte Lehrnmethode
	 * @param klasse Die gewünschte Kostenklasse
	 * @param isTalent Ob es sich um ein Talent handelt oder nicht.
	 * 			(Talente besitzen kein A* und es gil: Selbststudium + Stufe > 10 = kk+1)
	 * @return Die AP-Kosten für die Steigerung (inkl. evtl. aktivierung)
	 */
	public static int berechneSktKosten(int startStufe, int zielStufe, 
								   int heldenStufe, Lernmethode methode, 
								   KostenKlasse kKlasse, boolean isTalent) {
		KostenKlasse tmpKK = kKlasse;
		int tmpKosten = 0;
		
		// TODO Texte über library einlesen!
		
		// Änderung der Kostenklasse durch die Lernmethode
		switch (methode) {
			case selbstStudium: 
				tmpKK = tmpKK.plusEineKk(); 
				ProgAdmin.notepad.addSecondaryMsg("Selbststudium: K+1");
				break;
			case spezielleErfahrung:  
				tmpKK = tmpKK.minusEineKk(); 
				ProgAdmin.notepad.addSecondaryMsg("Spezielle Erfahrung: K-1");
				break;
			case sehrGuterLehrmeister:
				tmpKK = tmpKK.minusEineKk(); 
				ProgAdmin.notepad.addSecondaryMsg("Sehr guter Lehrmeister: K+1");
				break;
		}
		
		// Für Talente gibt es kein A+ als KostenKlasse
		if ( tmpKK.equals(KostenKlasse.A_PLUS) && isTalent) {
			tmpKK = KostenKlasse.A;
		}
		
		// Falls es das Talent zuvor nicht gab, ist es KEIN_WERT, zählt aber wie "0"
		if (startStufe == CharElement.KEIN_WERT) {
			startStufe = -1;
		}
		
		// Aktivierungskosten ausrechenen, wenn Stufe < 0
		if (startStufe < 0) {
			while (startStufe < 0 && startStufe < zielStufe) {
				tmpKosten += getSktWert(tmpKK, heldenStufe);
				startStufe++;
			}
		}
		
		// Unterscheiden, ob im Laufe der Berechnung die Kosten Kategorie steigt
		if ( isTalent && zielStufe > 10 && methode.equals(Lernmethode.selbstStudium)) {
			// Errechnen der Kosten / Kategorie ändert sich
			while (startStufe < zielStufe) {
				startStufe++;
				
				// Steigerung der Kategorie bei überschreiben der Stufe 10
				if (startStufe == 11) {
					tmpKK = tmpKK.plusEineKk();
					ProgAdmin.notepad.addSecondaryMsg("Selbststudium + Talent + ab Stufe 11: K+1");
				}

				tmpKosten += getSktWert(tmpKK, startStufe);
			}
			
		} else {
		
			// Errechnen der Kosten / Kategorie ändert sich nicht
			while (startStufe < zielStufe) {
				startStufe++;
				tmpKosten += getSktWert(tmpKK, startStufe);
			}
		}
		
		return tmpKosten;
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
		return berechneSktKosten(startStufe, zielStufe, 1, 
				Lernmethode.lehrmeister, kKlasse, false);
		// Es wird Talent einfach angenommen, da es für die Lernmethode "Lehrmeister" egal ist
	}
	
	/**
	 * Berechnet die Kosten zum Senken der Schlechten Eigenschaften
	 * @param startStufe
	 * @param zielStufe
	 * @return
	 */
	public static int berechneSchEigSenkenKosten(int startStufe, int zielStufe) {
		// TODO implement
		return 0;
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
