/*
 * Created on 30.04.2005 / 23:02:43
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.sonderregeln;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;
import org.d3s.alricg.held.HeldenLink;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;

/**
 * <u>Beschreibung:</u><br> 
 * Ein Adapter f�r Sonderregeln, die eine Standart-Implementierung aller Methoden enth�lt.
 * Nur ID und Beschreibung m�ssen sinnigerweise in jeder Sonderregel neu gesetzt werden.
 * Jede Sonderregel sollte von diesem Adapter abgeleitet werden und nur die Methoden 
 * �berschreiben die f�r die entsprechende Sonderregel wichtig ist.
 * 
 * F�r die Beschreibungen der Methoden siehe "SonderregelInterface".
 * 
 * @author V. Strelow
 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface
 */
public abstract class SonderregelAdapter extends CharElement implements SonderregelInterface {

	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#getCharKomponente()
	 */
	public CharKomponente getCharKomponente() {
		return CharKomponente.sonderregel;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#canAddSelf(boolean, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public boolean canAddSelf(HeldProzessor prozessor, boolean ok, Link srLink) {
		return ok;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#initSonderregel(org.d3s.alricg.held.Held, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public void initSonderregel(HeldProzessor prozessor, Link srLink) {
		// Noop!
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#finalizeSonderregel(org.d3s.alricg.charKomponenten.links.Link)
	 */
	public void finalizeSonderregel(Link link) {
		// Noop!
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * Diese Methode MUSS von jeder Sonderregel implementiert werden!
	 * @see org.d3s.alricg.charKomponenten.CharElement#getBeschreibung()
	 */
	public abstract String getBeschreibung();

	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#changeCanAddElement(boolean, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public boolean changeCanAddElement(boolean canAdd, Link tmpLink) {
		return canAdd;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#changeCanRemoveElemet(boolean, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public boolean changeCanRemoveElemet(boolean canRemove, Link link) {
		return canRemove;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#changeCanUpdateStufe(boolean, org.d3s.alricg.held.HeldenLink)
	 */
	public boolean changeCanUpdateWert(boolean canUpdate, HeldenLink link) {
		return canUpdate;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#changeCanUpdateText(boolean, org.d3s.alricg.held.HeldenLink)
	 */
	public boolean changeCanUpdateText(boolean canUpdate, HeldenLink link) {
		return canUpdate;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#changeCanUpdateZweitZiel(boolean, org.d3s.alricg.held.HeldenLink)
	 */
	public boolean changeCanUpdateZweitZiel(boolean canUpdate, HeldenLink link) {
		return canUpdate;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#changeKosten(int, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int changeKosten(int kosten, Link link) {
		return kosten;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#changeKostenKlasse(org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public KostenKlasse changeKostenKlasse(KostenKlasse klasse, Link link) {
		return klasse;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#changeMaxStufe(int, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int changeMaxStufe(int maxStufe, Link link) {
		return maxStufe;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#changeMinStufe(int, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int changeMinStufe(int minStufe, Link link) {
		return minStufe;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#processAddAsNewElement(org.d3s.alricg.charKomponenten.links.Link)
	 */
	public void processAddAsNewElement(Link link) {
		// Noop!
	}
	
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#processRemoveElement(org.d3s.alricg.charKomponenten.links.Link)
	 */
	public void processRemoveElement(Link link) {
		// Noop!
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#processUpdateElement(org.d3s.alricg.held.HeldenLink, int, java.lang.String, org.d3s.alricg.charKomponenten.CharElement)
	 */
	public void processUpdateElement(HeldenLink link, int stufe, String text, CharElement zweitZiel) {
		// Noop!
	}
}