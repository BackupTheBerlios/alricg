/*
 * Created 22. Dezember 2004 / 01:49:46
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

/**
 * <b>Beschreibung:</b><br>
 * Zu jedem "CharElement" können besondere Regeln existieren, die NICHT
 * durch ALRICG automatisch beachtet werden, sondern von user beachtet
 * werden müssen. (Typischer weise Beschränkungen)
 * Diese werden durch ein Objekt von RegelAnmerkung repräsentiert.
 * @author V.Strelow
 */
public class RegelAnmerkung
{
	private String[] anmerkungen; // Array von Anmerkungen

    /**
     * @return Ein Array mit allen Anmerkungen, oder ein leeres Array.
     */
	public String[] getAllAnmerkungen() {
        return anmerkungen;
	}

	/**
     * @return Liefert "true", wenn es mindestens eine Anmerkung gibt,
     * sonst "false".
     */
	public boolean hasAnmerkungen() {
        // TODO impelement
        return false;
	}
}
