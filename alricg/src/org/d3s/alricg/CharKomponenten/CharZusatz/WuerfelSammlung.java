/*
 * Created on 23.01.2005 / 19:37:35
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.CharKomponenten.CharZusatz;

import java.util.Random;

/**
 * <u>Beschreibung:</u><br> 
 * Eine Sammlung von W�rfel mit variabler Anzahl und Augen der W�rfel,
 * sowie einem Wert der fest hinzuaddiert wird. So k�nnen w�rfel-Anweisungen
 * in der art wie "2W6 + 3W20 - 1W3 + 5" realisiert werden. Es kann hier direkt
 * ein ergebniss abgerufen werden!
 * @author V. Strelow
 */
public class WuerfelSammlung {
	private int festWert;
	private int[] anzahlWuerfel; // Anzahl der W�rfel
	private int[] augenWuerfel; // Augenzahl beim gleichen Index wie groesseWuerfel
	private static Random ranGenerator;
	
	
	/**
	 * Konstruktor
	 * @param festWert Ein fester Wert, der zum Ergebnis gez�hlt wird (1W6 + -5-)
	 * @param anzahlWuerfel Die Anzahl der W�rfel (-1-W6 + 5), gleicher index 
	 * 		geh�rt zusammen
	 * @param augenWuerfel Die Augen der W�rfel (1W-6- + 5), gleicher index 
	 * 		geh�rt zusammen
	 */
	public WuerfelSammlung(int festWert, int[] anzahlWuerfel, int[] augenWuerfel) {
		this.festWert = festWert;
		this.anzahlWuerfel = anzahlWuerfel;
		this.augenWuerfel = augenWuerfel;
		
		// �berwachung das die Parameter nicht null sind
		assert (anzahlWuerfel != null) && (augenWuerfel != null);
		
		if (ranGenerator == null) {
			ranGenerator = new Random();
		}
	}
	
	/**
	 * Addiert je einen Zufallswert pro W�rfel und den Festwert. Dies
	 * ergibt einen g�ltigen "W�rfelwurf".
	 * @return Liefert einen g�ltigen, zuf�lligen Wert wie eine W�rfelergebniss
	 */
	public int getWuerfelWurf() {
		int tmpInt = festWert;
		
		for (int i1 = 0; i1 < anzahlWuerfel.length; i1++) {
			for (int i2 = 0; i2 < anzahlWuerfel[i1]; i2++) {
				tmpInt += 1 + Math.abs(ranGenerator.nextInt()) % augenWuerfel[i1];
			}
		}
		
		return tmpInt;
	}
	
	/**
	 * @return Liefert das Attribut anzahlWuerfel.
	 */
	public int[] getAnzahlWuerfel() {
		return anzahlWuerfel;
	}
	/**
	 * @return Liefert das Attribut augenWuerfel.
	 */
	public int[] getAugenWuerfel() {
		return augenWuerfel;
	}
	/**
	 * @return Liefert das Attribut festWert.
	 */
	public int getFestWert() {
		return festWert;
	}
}
