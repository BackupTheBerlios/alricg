/*
 * Created 26. April 2005 / 00:13:05
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.prozessor;

import java.util.ArrayList;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.charKomponenten.sonderregeln.BasisSonderregelInterface;
import org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter;
import org.d3s.alricg.held.HeldenLink;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;
/**
 * <b>Beschreibung:</b><br> 
 * Dient der Verwaltung der Sonderregel. Registriert alle aktiven Sonderregel und 
 * dient als eine zentrale Schnittstelle die die Methodenaufrufe an alle aktiven
 * Sonderregeln weiterleitet.
 * 
 * @author V.Strelow
 */
public class SonderregelAdmin implements BasisSonderregelInterface  {
	ArrayList<SonderregelAdapter> sonderregeln;
	
	/**
	 * F�gt eine Sonderregel zu Admin hinzu. Diese Sonderregel wird nach 
	 * dem Hinzuf�gen bei jedem Methodenaufruf des Admins mit beachtet
	 * 
	 * (Wenn ein neues Element zu Held hinzugef�gt wurde, dass eine Sonderregel 
	 * besitzt, dann wir die Sonderregel mit dieser Methode hinzugef�gt.)
	 * 
	 * @param sRegel Die neue Sonderregel
	 * @param link Der Link zu dem die Sonderregel geh�rt
	 */
	public void addSonderregel(SonderregelAdapter sRegel, Link link) {
		// TODO implement
		// Es sollte eine Sortierung vorgenommen werden, damit �berpr�ft werden kann
		// in welcher Reichenfolge Sonderregeln ausgef�hrt wurden / werden!
		// ACHTUNG Es k�nnen mehrer Links die gleiche Sonderregel besitzen!
		
		// TODO Sonderregel initialisieren!
	}
	
	/**
	 * Entfernd eine Sonderregel aus dem Admin. Diese Sonderregel wird nicht
	 * mehr bei Methodenaufrufen mit beachtet.
	 * 
	 * (Wenn ein Element vom Held entfernd wird, dann wird die Sonderregel 
	 * mit dieser Methode entfernd)
	 * 
	 * @param sRegel Die Sonderregel die entfernd wird
	 * @param link Der Link zu dem die Sonderregel geh�rt
	 */
	public void removeSonderregel(SonderregelAdapter sRegel, Link link) {
		// TODO implement
		
		// TODO Sonderregel "finalizeSonderregel(Link)"
	}

	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#processAddAsNewElement(org.d3s.alricg.charKomponenten.links.Link)
	 */
	public void processAddAsNewElement(Link link) {
		// TODO implement
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#processRemoveElement(org.d3s.alricg.charKomponenten.links.Link)
	 */
	public void processRemoveElement(Link link) {
		// TODO implement
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeKostenKlasse(org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public KostenKlasse changeKostenKlasse(KostenKlasse klasse, Link link)  {
		// TODO implement
		return null;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeKosten(int, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int changeKosten(int kosten, Link link) {
		// TODO implement
		return 0;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeMaxStufe(int, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int changeMaxStufe(int maxStufe, Link link) {
		// TODO implement
		return 0;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeMinStufe(int, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int changeMinStufe(int minStufe, Link link) {
		// TODO implement
		return 0;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeCanAddElement(boolean, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public boolean changeCanAddElement(boolean ok, Link tmpLink) {
		// TODO implement
		return true;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeCanRemoveElemet(boolean, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public boolean changeCanRemoveElemet(boolean canRemove, Link link) {
		// TODO implement
		return true;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#processUpdateElement(org.d3s.alricg.held.HeldenLink, int, java.lang.String, org.d3s.alricg.charKomponenten.CharElement)
	 */
	public void processUpdateElement(HeldenLink link, int stufe, String text, CharElement zweitZiel) {
		//TODO implement
	}

	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeCanUpdateStufe(boolean, org.d3s.alricg.held.HeldenLink)
	 */
	public boolean changeCanUpdateWert(boolean canUpdate, HeldenLink link) {
		// TODO implement
		return true;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeCanUpdateText(boolean, org.d3s.alricg.held.HeldenLink)
	 */
	public boolean changeCanUpdateText(boolean canUpdate, HeldenLink link) {
		// TODO implement
		return true;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeCanUpdateZweitZiel(boolean, org.d3s.alricg.held.HeldenLink)
	 */
	public boolean changeCanUpdateZweitZiel(boolean canUpdate, HeldenLink link) {
		// TODO implement
		return true;
	}
	
}
