/*
 * Created on 26.04.2005 / 08:59:04
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.links;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;

/**
 * <u>Beschreibung:</u><br> 
 * Diese Klasse dient als Bindeglied zwischen zwei (oder mehr) Elementen. �ber
 * diesen "Link" k�nnen dann Werte, ein Text oder ein weiteres Element angegeben
 * werden (z.B. "Eitelkeit 5" oder "Unf�higkeit Schwimmen" oder "Verpflichtungen
 * gegen Rondrakirche").
 * Der Link bildet die Grundlage f�r "IDLink" und "HeldenLink".
 *
 * @author V. Strelow
 */
public abstract class Link {
	public static int KEIN_WERT = -100;
	private CharElement zielId; // Das Ziel dieses Links (z.B. eine SF)
	private CharKomponente zielTyp;
	private CharElement linkId; // Ein in Beziehung stehendes Element (z.B. Unf�higkeit "SCHWERT")
	private String text = ""; // Ein Text (z.B. Vorurteile gegen "Orks")
	private int wert = KEIN_WERT; // Wert der Beziehung (z.B. H�henangst 5) / "-100": es gibt keinen Wert
	private boolean leitwert = false; // F�r Elfen, ver�ndert kosten / Default = false
	
	/**
	 * Gibt zur�ck ob das Ziel-Element ein Leitwert ist (wichtig f�r Elfische-
	 * Weltsicht). 
	 * @return true - Das Ziel-Element ist ein Leitwert, ansonsten false.
	 */
	public boolean isLeitwert() {
		return leitwert;
	}
	
	/**
	 * Wenn das Ziel-Element mit einem anderen Element verbunden ist, so kann
	 * dieses Link-Element hiermit abgerufen werden. 
	 * Z.B. "Unf�higkeit Schwerter" ("Unf�higkeit" = Ziel, "Schwerter" = link) 
	 * @return Das Link-Element mit dem das Ziel verbunden ist, oder "null"
	 * falls es kein Link-Elemnet gibt. 
	 */
	public CharElement getLinkId() {
		return linkId;
	}
	
	/**
	 * Wenn das Ziel-Element mit einem Text verbunden ist, so kann
	 * der Text hiermit abgerufen werden.
	 * Z.B. "Vorurteile gegen Orks" ("Vorurteile gegen" = Ziel, "Orks" = Text)
	 * @return Den anzuzeigenden Text oder "null", falls es keinen Text gibt.
	 */
	public String getText() {
		return text;
	}
	/**
	 * Wenn das Ziel-Element einen Wert besitzt, so kann der Text Wert 
	 * hiermit abgerufen werden.
	 * @return Den Wert der mit der Ziel Id Verbunden ist oder "-100" falls es 
	 * 		keinen wert gibt. .
	 */
	public int getWert() {
		return wert;
	}
	
	/**
	 * @return true - Das zugeh�rige CharElement hat einen Wert, sonst false.
	 */
	public boolean hasWert() {
		if (wert == KEIN_WERT) { // -100 meint, das es keinen Wert gibt
			return false;
		}
		return true;
	}
	
	/**
	 * Jeder IdLink hat ein Ziel, dieses wird �ber die ZielId abgerufen!
	 * @return Liefert das Attribut zielId.
	 */
	public CharElement getZielId() {
		return zielId;
	}
	
	/**
	 * @return Liefert das Attribut zielKomponete.
	 */
	public CharKomponente getZielTyp() {
		return zielTyp;
	}
	
	/**
	 * @param leitwert Setzt das Attribut leitwert.
	 */
	public void setLeitwert(boolean leitwert) {
		this.leitwert = leitwert;
	}
	/**
	 * @param linkId Setzt das Attribut linkId.
	 */
	public void setLinkId(CharElement linkId) {
		this.linkId = linkId;
	}
	/**
	 * @param text Setzt das Attribut text.
	 */
	public void setText(String text) {
		if (text == null)  {
			this.text  = "";
		} else {
			this.text = text;
		}
	}
	/**
	 * @param wert Setzt das Attribut wert ("-100" bedeutet, das es keinen Wert gibt).
	 */
	public void setWert(int wert) {
		this.wert = wert;
	}
	/**
	 * @param zielId Setzt das Attribut zielId. (Das Ziel dieses Links, z.B. eine SF)
	 */
	public void setZielId(CharElement zielId, CharKomponente zielKomponete) {
		this.zielId = zielId;
		this.zielTyp = zielKomponete;
	}
	
	/**
	 * �berpr�ft ob dieser Link "das selbe Element" meint, wie ein andern Link. Dies ist dann
	 * der Fall, wenn zielID, linkID und Text gleich sind. (leitwert und wert m�ssen
	 * nicht gleich sein).
	 * Bsp: 
	 * Prof mit "Schwerter +2(=Wert)" und Rasse mit "Schwerter +3" >> true
	 * Prof mit "Vorurteil gegen Orks(=Text)" und Rasse mit "Vorurteil gegen Elfen" >> false
	 * Prof mit "Unf�higkeit Dolche(=linkId)" und Rasse mit "Unf�higkeit Dolche" >> true
	 * 
	 * @param link Der zu pr�fende link
	 * @return true - Der �bergebene link ist das gleiche Element, ansonsten false 
	 */
	public boolean isEqualLink(Link link) {
		// Pr�ft ob zielId, text und linkId gleich sind, nur dann "true"
		if ( !zielId.equals(link.getZielId()) ) {
			return false;
		} else if ( !text.equals(link.getText()) ) {
			return false;
		} else if ( linkId == null && link.getLinkId() != null ) {
			return false;
		} else if ( linkId != null && link.getLinkId() == null ) {
			return false; 
		} else if ( !linkId.equals(link.getLinkId()) ) {
			return false;
		}
		
		return true;
	}
	
}
