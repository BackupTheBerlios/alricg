/*
 * Created on 01.05.2005 / 00:58:29
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.prozessor;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.held.HeldenLink;

/**
 * <u>Beschreibung:</u><br> 
 * ElemetBoxen bilden die Grundlage für die Kapselung und Verwalten bestimmte Arten 
 * von CharElementen in der Form von Links. So verwaltet die von der 
 * LinkElementBox abgeleitete "TalentBox" alle Talente und bietet Operationen 
 * an, um die Elemente zu verändern.
 * Die "LinkElementBox" Spezifiziert wie von AUSSEN auf Boxen zugegriffen werden kann,
 * und zwar als UNMODIFIABLE List.
 * Auf die inneren Operationen, die in den Spezialisierten Klassen defniert sind und 
 * mit denen die Box verändert werden kann, kann nicht zugeriffen werden.
 * Der Grund dafür ist, dass alle Verändernden Operationen über den entsprechenden 
 * Prozessor durchgeführt werden sollen. So soll ein "ordenlicher" Ablauf ohne 
 * querschlagende Zugriffe realisiert werden. 
 * 
 * (Bisherige Implementierungen sind "AbstractBoxGen" und "TalentBoxGen" im package
 * generierung)
 * 
 * @author V. Strelow
 */
public abstract class LinkElementBox<E extends HeldenLink> extends AbstractList {
	protected ArrayList<E> linkArray; // ArryList mit allen Elementen diese Box
	protected HeldProzessor prozessor; // Der Prozessor
	
	/**
	 * @param liste Die Liste aller verwalteten Elemente
	 */
	public LinkElementBox(ArrayList<E> liste, HeldProzessor proz) {
		linkArray = liste;
		prozessor = proz;
	}
	
	/**
	 * @return Eine nicht veränderbare List mit allen enthaltenen Elementen
	 */
	public List<E> getUnmodifiableList() {
		return Collections.unmodifiableList(linkArray);
	}
	
	/**
	 * Sucht zu einem Link das gleichartige Gegenstück aus der ElementBox herraus.
	 * Die Prüfung ob ein Link "gleichartig" ist, erfolgt mit "Link.isEqualLink(..)".
	 * Dies ist dann zutreffend, wenn ziel, text und zweitZiel gleich sind.
	 * 
	 * @param link Der link zu dem ein gleichartige Link gesucht wird
	 * @return Den gleichartige Link aus der ElementBox zu dem gewünschten Link, 
	 * 		oder null falls es keinen übereinstimmenden Link gibt.
	 * @see org.d3s.alricg.charKomponenten.links.Link#isEqualLink(org.d3s.alricg.charKomponenten.links.Link)
	 */
	public E getEqualLink(Link link) {
		
		// TODO kann noch optimiert werden mit sortierung ??
		for (int i = 0; i < linkArray.size(); i++) {
			if (linkArray.get(i).isEqualLink(link)) {
				return linkArray.get(i);
			}
		}
		
		return null; // Dieser Link ist nicht enthalten
	}
	
	/**
	 * Mit dieser Methode kann aus dem zum Helden gehörenden Elementen ein bestimmtes 
	 * gesucht werden. Dabei ist diese Methode ungenauer, da hier weder Text noch
	 * ZweitZiel angegeben sind. Es wird lediglich das erste Element mit der passenden
	 * ID gelieftert. Gibt es mehrer so ist unbestimmt welches geliefert wird.
	 * 
	 * @param id Die id zu der das passende Elemente gesucht wird.
	 * @return Das erste Element mit der id "id" oder "null", falls es kein solches Element
	 * gibt
	 */
	public E getLinkFromId(String id) {
		for (int i = 0; i < linkArray.size(); i++) {
			if (linkArray.get(i).getZiel().getId().equals(id)) {
				return linkArray.get(i);
			}
		}
		
		return null; // Ein Link mit dieser ID ist nicht enthalten
	}
	
	/**
	 * Überprüft ob in dieser ElementBox ein gleichartiges Gegenstück zu
	 * dem Link enthalten ist. 	Die Prüfung ob ein Link "gleichartig" ist, 
	 * erfolgt mit "Link.isEqualLink(..)".
	 * Dies ist dann zutreffend, wenn ziel, text und zweitZiel gleich sind.
	 * 
	 * @param link Der link der nach einem gleichartigem Gegenstück überprüft wird
	 * @return true - Es existiert in dieser ElementBox ein gleicharties Gegenstück, 
	 * 				ansonsten false
	 * @see org.d3s.alricg.charKomponenten.links.Link#isEqualLink(org.d3s.alricg.charKomponenten.links.Link)
	 */
	public boolean contiansEqualLink(Link link) {
		
		if ( getEqualLink((Link) link) != null ) {
			return true;
		}
		return false;
	}
	
	
	/* (non-Javadoc) Methode überschrieben
	 * @see java.util.List#get(int)
	 */
	public E get(int idx) {
		return linkArray.get(idx);
		
	}
	/* (non-Javadoc) Methode überschrieben
	 * @see java.util.Collection#size()
	 */
	public int size() {
		return linkArray.size();
	}
	
	/**
	 * Liefert die Gesamt-Kosten für den "besitz" aller dieser Elemente. Ob TalentGp oder
	 * GP ergibt sich aus dem Kontext!
	 * @return Die Kosten die für alle diese Elemente entstehen!
	 */
	public abstract int getGesamtKosten();
}
