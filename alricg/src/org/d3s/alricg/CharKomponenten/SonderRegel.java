/*
 * Created 23. Dezember 2004 / 14:58:36
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import org.d3s.alricg.Controller.CharKompAdmin.CharKomponenten;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public abstract class SonderRegel {
	private String id;
	private String beschreibung;
	
	/**
	 * @param id Die Systemweit eindeutige ID der Sonderegel
	 * @param beschreibung Ein Text zu dieser Sonderregel, die auch dem 
	 * 		Benutzer angezeigt werden kann.
	 */
	public SonderRegel(String id, String beschreibung) {
		this.id = id;
		this.beschreibung = beschreibung;
	}
	
	/**
	 * @return Liefert das Attribut id.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return Liefert eine Beschreibung dieser Sonderregel.
	 */
	public String getBeschreibung() {
		return beschreibung;
	}

//*****************************************************************************
//	Methoden die immer analog zu Methoden aus "Held" 
//	oder "HeldGenerator" aufgerufen werden
	
	/**
	 * Wird bei der Berechung der GP für Eigenschaften aufgerufen
	 * @return Modifikator auf die GP
	 */	
	public abstract int getModiGP();
	public abstract int getModiTalentGP();
	public abstract int getModiWissenGP();
	
	/**
	 * Wird aufgerufen wenn die maximal-Werte für ein Element bestimmt wird.
	 * @param element Das Element welches überprüft wird
     * @param komponente Die Art des Elements als CharKomponente
	 * @return Der modi auf den Maximal-Wert
	 */
	public abstract int getMaxWertModi(CharElement element, CharKomponenten komponente);
	public abstract int getMinWertModi(CharElement element, CharKomponenten komponente);
	
	/**
	 * Wird aufgerufen beim Hinzufügen des Elemens selbst, welche
	 * diese Sonderregel besitzt
	 */
	public abstract void addThis();
	public abstract void removeThis();
	
	/**
	 * Wird aufgerufen beim Hinzufügen eines beliebigen Elementes zum Held.
	 * @param element Das Element welches hinzugefügt wird
     * @param komponente Die Art des Elements als CharKomponente 
	 */
	public abstract void addOther(CharElement element, CharKomponenten komponente);
	public abstract void removeOther(Object obj);
}
