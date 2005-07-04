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
	 * Methode überschrieben
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 * 
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
	public boolean contiansEqualLink(Object link) {
		if ( !(link instanceof Link) ) {
			return false;
		}
		
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
}
