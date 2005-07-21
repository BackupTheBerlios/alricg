/*
 * Created 26. April 2005 / 00:13:05
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.prozessor;

import java.util.HashMap;
import java.util.Iterator;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.charKomponenten.sonderregeln.BasisSonderregelInterface;
import org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter;
import org.d3s.alricg.controller.ProgAdmin;
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
	private final HashMap<Link, SonderregelAdapter> sonderRegelMap;
	private final HeldProzessor prozessor;
	private Iterator<SonderregelAdapter> iterator;
	//private ArrayList<SonderregelAdapter> sonderregeln;
	
	public SonderregelAdmin(HeldProzessor prozessor) {
		sonderRegelMap = new HashMap<Link, SonderregelAdapter >();
		this.prozessor = prozessor;
	}
	
	
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
	public void addSonderregel(Link link) {
		// TODO implement
		// Es sollte eine Sortierung vorgenommen werden, damit �berpr�ft werden kann
		// in welcher Reichenfolge Sonderregeln ausgef�hrt wurden / werden!
		// ACHTUNG Es k�nnen mehrer Links die gleiche Sonderregel besitzen!
		
		if ( sonderRegelMap.containsKey(link) ) {
			ProgAdmin.logger.warning("Dieser Link ist bereits im SonderregelAmdin registriert!");
		}
		
		sonderRegelMap.put(link, link.getZiel().createSonderregel() ); // SR zum Admin hinzuf�gen
		sonderRegelMap.get(link).initSonderregel(prozessor, link); // SR initialisieren
	}
	
	/**
	 * @return Liefert die Anzahl der aktiven Sonderregeln des Helden
	 */
	public int countSonderregeln() {
		return sonderRegelMap.size();
	}
	
	/**
	 * �berpr�ft ob diese Sonderregel bereits vorhanden ist
	 * @param id  Die Id der ST die �berpr�ft wird
	 * @return true - Die SR ist im SR-Admin enthalten, ansonsten false
	 */
	public boolean hasSonderregel(String id) {
		iterator = sonderRegelMap.values().iterator();
		
		while (iterator.hasNext()) {
			if ( id.equals(iterator.next().getId()) ) return true;
		}
		
		return false;
	}
	
	/**
	 * Liefert die aktuelle Sonderegel zur�ck mit der entsprechenden ID, oder 
	 * null falls keine entsprechnende SR vorhanden ist
	 * @param id ID der gesuchten SR
	 * @return Die SR aus dem SrAdmin mit der entsprechenden ID, oder null
	 * 		falls es eine solche SR nicht gibt.
	 */
	public SonderregelAdapter getSonderregel(String id) {
		iterator = sonderRegelMap.values().iterator();
		SonderregelAdapter sondAdapt;
		
		while (iterator.hasNext()) {
			sondAdapt = iterator.next();
			if ( id.equals(sondAdapt.getId()) ) { 
				return sondAdapt;
			}
		}
		
		return null;
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
	public void removeSonderregel(Link link) {
		final SonderregelAdapter sRegel = sonderRegelMap.get(link); 
		
		sonderRegelMap.remove(link); // SR entfernen
		sRegel.finalizeSonderregel(link); // SR finalizer aufrufen
	}

	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#processAddAsNewElement(org.d3s.alricg.charKomponenten.links.Link)
	 */
	public void processAddAsNewElement(Link link) {
		iterator = sonderRegelMap.values().iterator();
		
		// Alle Sonderregeln durchlaufen und entsprechend aufrufen
		while (iterator.hasNext()) {
			iterator.next().processAddAsNewElement(link);
		}
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#processRemoveElement(org.d3s.alricg.charKomponenten.links.Link)
	 */
	public void processRemoveElement(Link link) {
		iterator = sonderRegelMap.values().iterator();
		
		// Alle Sonderregeln durchlaufen und entsprechend aufrufen
		while (iterator.hasNext()) {
			iterator.next().processRemoveElement(link);
		}
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeKostenKlasse(org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public KostenKlasse changeKostenKlasse(KostenKlasse klasse, Link link)  {
		iterator = sonderRegelMap.values().iterator();
		
		// Alle Sonderregeln durchlaufen und entsprechend aufrufen
		while (iterator.hasNext()) {
			klasse = iterator.next().changeKostenKlasse(klasse, link);
		}
		return klasse;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeKosten(int, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int changeKosten(int kosten, Link link) {
		iterator = sonderRegelMap.values().iterator();
		
		// Alle Sonderregeln durchlaufen und entsprechend aufrufen
		while (iterator.hasNext()) {
			kosten = iterator.next().changeKosten(kosten, link);
		}
		return kosten;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeMaxStufe(int, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int changeMaxStufe(int maxStufe, Link link) {
		iterator = sonderRegelMap.values().iterator();
		
		// Alle Sonderregeln durchlaufen und entsprechend aufrufen
		while (iterator.hasNext()) {
			maxStufe = iterator.next().changeMaxStufe(maxStufe, link);
		}
		return maxStufe;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeMinStufe(int, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int changeMinStufe(int minStufe, Link link) {
		iterator = sonderRegelMap.values().iterator();
		
		// Alle Sonderregeln durchlaufen und entsprechend aufrufen
		while (iterator.hasNext()) {
			minStufe = iterator.next().changeMinStufe(minStufe, link);
		}
		return minStufe;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeCanAddElement(boolean, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public boolean changeCanAddElement(boolean ok, Link link) {
		iterator = sonderRegelMap.values().iterator();
		
		// Alle Sonderregeln durchlaufen und entsprechend aufrufen
		while (iterator.hasNext()) {
			ok = iterator.next().changeCanAddElement(ok, link);
		}
		return ok;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeCanRemoveElemet(boolean, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public boolean changeCanRemoveElemet(boolean canRemove, Link link) {
		iterator = sonderRegelMap.values().iterator();
		
		// Alle Sonderregeln durchlaufen und entsprechend aufrufen
		while (iterator.hasNext()) {
			canRemove = iterator.next().changeCanRemoveElemet(canRemove, link);
		}
		return canRemove;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#processUpdateElement(org.d3s.alricg.held.HeldenLink, int, java.lang.String, org.d3s.alricg.charKomponenten.CharElement)
	 */
	public void processUpdateElement(HeldenLink link, int stufe, String text, CharElement zweitZiel) {
		iterator = sonderRegelMap.values().iterator();
		
		// Alle Sonderregeln durchlaufen und entsprechend aufrufen
		while (iterator.hasNext()) {
			iterator.next().processUpdateElement(link, stufe, text, zweitZiel);
		}
	}

	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeCanUpdateStufe(boolean, org.d3s.alricg.held.HeldenLink)
	 */
	public boolean changeCanUpdateWert(boolean canUpdate, HeldenLink link) {
		iterator = sonderRegelMap.values().iterator();
		
		// Alle Sonderregeln durchlaufen und entsprechend aufrufen
		while (iterator.hasNext()) {
			canUpdate = iterator.next().changeCanUpdateWert(canUpdate, link);
		}
		return canUpdate;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeCanUpdateText(boolean, org.d3s.alricg.held.HeldenLink)
	 */
	public boolean changeCanUpdateText(boolean canUpdate, HeldenLink link) {
		iterator = sonderRegelMap.values().iterator();
		
		// Alle Sonderregeln durchlaufen und entsprechend aufrufen
		while (iterator.hasNext()) {
			canUpdate = iterator.next().changeCanUpdateText(canUpdate, link);
		}
		return canUpdate;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeCanUpdateZweitZiel(boolean, org.d3s.alricg.held.HeldenLink)
	 */
	public boolean changeCanUpdateZweitZiel(boolean canUpdate, HeldenLink link) {
		iterator = sonderRegelMap.values().iterator();
		
		// Alle Sonderregeln durchlaufen und entsprechend aufrufen
		while (iterator.hasNext()) {
			canUpdate = iterator.next().changeCanUpdateZweitZiel(canUpdate, link);
		}
		return canUpdate;
	}
	
}
