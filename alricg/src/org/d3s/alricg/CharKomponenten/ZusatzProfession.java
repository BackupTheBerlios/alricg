/*
 * Created 20. Januar 2005 / 16:04:34
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.Links.Voraussetzung;

/**
 * <b>Beschreibung:</b><br>
 * Diese Klasse repräsentiert "späte Professionen" (z.B. Kor-Geweihter) und
 * "zusatz-Professionen" (wie der Elfische Wanderer)
 * @author V.Strelow
 */
public class ZusatzProfession extends Profession {
	private Profession[] professionMoeglich; // Ist dies leer, so sind alle möglich
	private Profession[] professionUeblich;
	private int apKosten; // GP Kosten durch Profession
	private boolean zusatzProf; //ansonsten späteProfession
	private Voraussetzung voraussetzung;
	
	
	/**
	 * @return Liefert das Attribut apKosten.
	 */
	public int getApKosten() {
		return apKosten;
	}
	/**
	 * @return Liefert das Attribut professionMoeglich.
	 */
	public Profession[] getProfessionMoeglich() {
		return professionMoeglich;
	}
	/**
	 * @return Liefert das Attribut professionUeblich.
	 */
	public Profession[] getProfessionUeblich() {
		return professionUeblich;
	}
	/**
	 * @return Liefert das Attribut voraussetzung.
	 */
	public Voraussetzung getVoraussetzung() {
		return voraussetzung;
	}
	/**
	 * @return Liefert das Attribut zusatzProf.
	 */
	public boolean isZusatzProf() {
		return zusatzProf;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
	 */
	public void loadXmlElement(Element xmlElement) {
		super.loadXmlElement(xmlElement);
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
	 */
	public Element writeXmlElement() {
		Element xmlElem = super.writeXmlElement();
		// TODO Auto-generated method stub
		return null;
	}
}
