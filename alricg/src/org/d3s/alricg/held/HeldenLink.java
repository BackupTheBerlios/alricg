/*
 * Created on 27.01.2005 / 11:13:56
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.held;

import java.util.HashMap;
import java.util.Iterator;

import org.d3s.alricg.CharKomponenten.CharElement;
import org.d3s.alricg.CharKomponenten.Links.IdLink;

/**
 * <u>Beschreibung:</u><br> 
 * Eine Verbindung zwischen einem Held und einem CharElement, das der Held
 * "besitzt". Z.B. ein Talent oder eine Sonderfertigkeit, die der Held hat.
 * Der Link speichert die Verbindung, den Wert und ggf. die Modifikationen 
 * durch Herkunft.
 * 
 * @author V. Strelow
 */
public class HeldenLink {
	private String zielId; // Das Ziel dieses Links (z.B. eine SF)
	private String linkId; // Ein in Beziehung stehendes Element (z.B. Unf�higkeit "SCHWERT")
	private String text; // Ein Text (z.B. Vorurteile gegen "Orks")
	//private int wert; // Wert der Beziehung (z.B. H�henangst 5) / "-100": es gibt keinen Wert
	private boolean leitwert; // F�r Elfen, ver�ndert kosten
	
	private int gewaehlterWert; // Wert den der User selbst gew�hlt hat
	private HashMap<IdLink, HeldenLinkModi> modis; // Modis durch Herkunft
	
	
	/**
	 * @return true - Das zugeh�rige CharElement hat einen Wert, sonst false.
	 * 
	 * TODO �berpr�fe ob die Bedingung richtig ist!!!
	 */
	public boolean hasWert() {
		if (gewaehlterWert == -100) { // -100 meint, das es keinen Wert gibt
			return false;
		}
		return true;
	}
	
	/**
	 * Errechnet den gesamtwert f�r diesen Link, bestehent aus dem vom User gew�hlten 
	 * Wert und dem Wert durch Modifikationen (typischerweise durch Herkunft).
	 * @return Der Gesamtwert f�r diesen Link, oder "0" falls es keinen Wert gibt
	 * @see hasWert()
	 */
	public int getGesamtWert() {
		//Guard - Gibt es �berhaupt einen Wert?
		if (!hasWert()) return 0;
		
		int gesamt = gewaehlterWert;
		Iterator<HeldenLinkModi> ite;
		HeldenLinkModi linkModi;
		
		if (modis != null) {
			ite = modis.values().iterator();
			while ( ite.hasNext() ) {
				linkModi = ite.next();
				gesamt += linkModi.getWert();
			}
		}
		
		return gesamt;
	}

	// ***************************************************************************
	private class HeldenLinkModi {
		IdLink link;
		private int wert; // Wird ben�tigt f�r eine Auswahl wo Stufen verteilt werden k�nnen!
		
		/**
		 * Konstruktor
		 * @param link Der IdLink, zu dem die Modifikation geh�rt
		 */
		protected HeldenLinkModi(IdLink link) {
			this.link = link;
			wert = 0;
		}
		
		/**
		 * Konstruktor
		 * @param link Der IdLink, zu dem die Modifikation geh�rt
		 * @param wert Der Wert um den die Modifikation den Helden-Wert ver�ndert.
		 * 		ACHTUNG: Nur f�r den fall das der Wert Variabel ist, also durch 
		 * 			eine Auswahl entsteht, indem der Wert auf verschiedene 
		 * 			M�glichkeiten verteilt werden kann. 
		 */
		protected HeldenLinkModi(IdLink link, int wert) {
			this.link = link;
			this.wert = wert;
		}
		
		/**
		 * @return Den Wert dieser Modifikation
		 */
		protected int getWert() {
			return (wert + link.getWert());
		}
		
		protected CharElement getQuellElement() {
			return null;
		}
	}
}
