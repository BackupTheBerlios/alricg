/*
 * Created 22. Dezember 2004 / 01:07:12
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg;

/**
 * <b>Beschreibung:</b><br>
 * Dies ist die super-Klasse für alle Charakter-Elemente.
 * Alle Elemente eines Charakters in Objekte von diesem Typ.
 * @author V.Strelow
 */
abstract public class CharElement {
	private String id; 
	private String name;
	private String sammelBegriff;
	private String beschreibung;
	private String sonderRegel;
	private RegelAnmerkung regelAnmerkung;

    /**
     * @return Die eindeutige, einmalige ID des Elements
     */
	public String getId() {
		// TODO implement operation1
		return id;
	}

	/**
	 * "beschreibung" ist ein allgemeiner Text des CharElements
	 * @return Liefert das Attribut beschreibung.
	 */
	public String getBeschreibung() {
		return beschreibung;
	}
	
	/**
	 * @return Liefert das Attribut name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * "regelAnmerkung" treten auf, wenn ALRICG eine Regel nicht
	 * automatisch umsetzen kann.
	 * @return Liefert das Attribut regelAnmerkung.
	 */
	public RegelAnmerkung getRegelAnmerkung() {
		return regelAnmerkung;
	}
	
	/**
	 * Der "sammelBegriff" dient einer besseren Struckturierung, 
	 * zu können z.B. mehrer Zwergenrassen unter "Zwerge" gesammelt werden.
	 * @return Liefert das Attribut sammelBegriff.
	 */
	public String getSammelBegriff() {
		return sammelBegriff;
	}
	
	/**
	 * @return Liefert das Attribut sonderRegel.
	 */
	public String getSonderRegel() {
		return sonderRegel;
	}
	
	/**
	 * @param id Setzt das Attribut id.
	 */
	public void setId(String id) {
		this.id = id;
	}
}
