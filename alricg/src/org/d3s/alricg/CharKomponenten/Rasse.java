/*
 * Created 22. Dezember 2004 / 13:07:57
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import java.util.Random;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.CharZusatz.WuerfelSammlung;
/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Rasse extends Herkunft {
	private Kultur[] kulturMoeglich;
	private Kultur[] kulturUeblich;
    private String[] haarfarbe = new String[20];
	private String[] augenfarbe = new String[20];
	private WuerfelSammlung groesse;
	private WuerfelSammlung alter;
	/*
	private int groesseWert;
	private int[] groesseWuerfel; // Anzahl der Würfel
	private int[] groesseAugen; // Augenzahl beim gleichen Index wie groesseWuerfel
	private int alterWert;
    private int alterAugen[];
    private int alterWuerfel[];
    */
	private int gewichtModi;
	private int geschwindigk;

	/**
	 * @return Liefert das Attribut augenfarbe.
	 */
	public String[] getAugenfarbe() {
		return augenfarbe;
	}
	/**
	 * @return Liefert das Attribut gewichtModi.
	 */
	public int getGewichtModi() {
		return gewichtModi;
	}
	/**
	 * @return Liefert das Attribut haarfarbe.
	 */
	public String[] getHaarfarbe() {
		return haarfarbe;
	}
	/**
	 * @return Liefert das Attribut kulturMoeglich.
	 */
	public Kultur[] getKulturMoeglich() {
		return kulturMoeglich;
	}
	/**
	 * @return Liefert das Attribut kulturUeblich.
	 */
	public Kultur[] getKulturUeblich() {
		return kulturUeblich;
	}
	
	/**
	 * Berechnet einen einen korrekten Wert für die Grösse. Dies ist Teilweise
	 * ein Zufalls-Wert, basierend auf groesseWuerfel/ groesseAugen und  groesseWert.
	 * TODO Korrektheit Testen!
	 * @return Einen gültigen größe-Wert für diese Rasse
	 */
	public int getGroesseKomplet() {
		int tmpInt = 0;
		Random r = new Random();
		
		/*for (int i1 = 0; i1 < groesseWuerfel.length; i1++) {
			for (int i2 = 0; i2 < groesseWuerfel[i1]; i2++) {
				tmpInt = 1 + Math.abs(r.nextInt()) % groesseAugen[i1];
			}
		}
		
		return (groesseWert + tmpInt);*/
		return 0;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
	 */
	public void loadXmlElement(Element xmlElement) {
		this.loadXmlElementHerkunft(xmlElement);
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
	 */
	public Element writeXmlElement() {
		Element xmlElem = null;
		
		this.writeXmlElementHerkunft(xmlElem);
		// TODO Auto-generated method stub
		return null;
	}
}
