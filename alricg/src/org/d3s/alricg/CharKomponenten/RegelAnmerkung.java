/*
 * Created 22. Dezember 2004 / 01:49:46
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import java.util.ArrayList;
import java.util.Arrays;

import nu.xom.Attribute;
import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br>
 * Zu jedem "CharElement" k�nnen besondere Regeln existieren, die NICHT
 * durch ALRICG automatisch beachtet werden, sondern von user beachtet
 * werden m�ssen. (Typischer weise Beschr�nkungen)
 * Diese werden durch ein Objekt von RegelAnmerkung repr�sentiert.
 * @author V.Strelow
 */
public class RegelAnmerkung
{
    /**
     * REGEL - Etwas was der Benutzer beachten sollte, aber das Programm nicht automatisch machen kann
     * TOD0 - Etwas was der Benutzer beachten sollte, aber nicht mu� (z.B. sich einen Titel geben bei dem 
     *      Vorteil "Adlige Abstammung" 
     */
    public enum Modus { REGEL, TODO }
    
	private String[] anmerkungen; // Array von Anmerkungen, gleicher index bei modus => Zugeh�rig
	private Modus[] modus;

	/**
	 * Konstruktor
	 */
	public RegelAnmerkung() {
		anmerkungen = new String[0];
		modus = new Modus[0];
	}
	
	/**
	 * Z�gt eine weitere Anmerkung hinzu!
	 * @param anmerkungIn Der Text der Regelanmerkung
	 * @param modusIn Der Modus, entweder "toDo" oder "regel"
	 */
	public void add(String anmerkungIn, String modusIn) {
		ArrayList list = new ArrayList(); // Absichtlicht ohne Typagabe, zur Mehrfachverwendung
		
		// Pr�fen ob der Modus g�ltig ist:
		assert modusIn.equals("regel") || modusIn.equals("toDo");
		
		// Hinzuf�gen der Anmerkung
		list.addAll(Arrays.asList(anmerkungen));
		
		list.add(anmerkungIn);
		anmerkungen = (String[]) list.toArray(new String[list.size()]);
		
		// Hinzuf�gen des Modus
		list.clear();
		list.addAll(Arrays.asList(modus));
		
		if (modusIn.equals("regel")) {
			list.add(Modus.REGEL);
		} else { // modusIn.equals("toDo")
			list.add(Modus.TODO);
		}
		modus = (Modus[]) list.toArray(new Modus[list.size()]);
	}

	/**
	 * Schreibt diese RegelAnmerkung in eine XML Repr�sentation zum speichern
	 * @return Ein xml-Element mit allen angaben
	 */
	public Element writeXmlElement() {
		Element element = new Element("regelAnmerkungen");
		Element elemRegel;
		
		// Einzelne Regel einf�gen
		for (int i = 0; i < anmerkungen.length; i++) {
			elemRegel = new Element("regel");
			
			elemRegel.addAttribute( new Attribute("modus", modus[i].toString()) );
			elemRegel.appendChild(anmerkungen[i]);
			element.appendChild(elemRegel);
		}
		
		return element;
	}
	
}
