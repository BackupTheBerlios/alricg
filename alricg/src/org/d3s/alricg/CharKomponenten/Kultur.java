/*
 * Created 22. Dezember 2004 / 13:07:48
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import org.d3s.alricg.CharKomponenten.Links.Auswahl;
/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Kultur extends Herkunft {
	private Profession[] professionMoeglich;
	private Profession[] professionUeblich;
	private Auswahl muttersprache;
	private Auswahl zweitsprache;
	private Auswahl sprachen;
	private Auswahl schriften;
	private String ausruestung;
	
	
	/**
	 * @return Liefert das Attribut ausruestung.
	 */
	public String getAusruestung() {
		return ausruestung;
	}
	/**
	 * @return Liefert das Attribut muttersprache.
	 */
	public Auswahl getMuttersprache() {
		return muttersprache;
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
	 * @return Liefert das Attribut schriften.
	 */
	public Auswahl getSchriften() {
		return schriften;
	}
	/**
	 * @return Liefert das Attribut sprachen.
	 */
	public Auswahl getSprachen() {
		return sprachen;
	}
	/**
	 * @return Liefert das Attribut zweitsprache.
	 */
	public Auswahl getZweitsprache() {
		return zweitsprache;
	}
}
