/*
 * Created on 11.11.2005 / 23:34:23
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */

package org.d3s.alricg.gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.held.HeldenLink;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class ObververAdmin implements CharElementsObserver, Observable {
	
	/**
	 * Enthält alle 
	 */
	HashMap<CharKomponente, HashSet<DataModelObserver>> dataModelSet = new HashMap<CharKomponente, HashSet<DataModelObserver>>();
	
	/**
	 * Enthält alle Observer die über Änderungen an einem Link informiert werden müssen. Dies ist bei 
	 * allen Voraussetzungen der Fall! 
	 */
	HashMap<CharKomponente, HashSet<DataChangeObserver<CharElement>>> charElementObserverSet = new HashMap<CharKomponente, HashSet<DataChangeObserver<CharElement>>();
	
	/**
	 * Enthält solche CharElemente, die von anderen CharElementen abhängen (Voraussetzungen).
	 * Pro Key sind alle CharElemente gespeichert, die von mindestens einem CharElemente von der Art "Key" 
	 * abhängen. 
	 * Wird z.B. ein CharElement "A" geändert, so müssen auch alle CharElemente, die zu der selben 
	 * CharKomponente gehören, aktualisiert werden. Es ist Möglich das bei diesen Elementen nun eine 
	 * Voraussetzung erfüllt ist oder nicht mehr erfüllt ist!
	 */
	HashMap<CharKomponente, HashSet<CharElement>> dataChangeSet = new HashMap<CharKomponente, HashSet<CharElement>>();
	
	public ObververAdmin() {
		final CharKomponente[] charKomps = CharKomponente.values();
		
		// Für jede charKomponente einen Eintrag erzeugen (außer für Sonderregeln)
		for (int i = 0; i < charKomps.length; i++) {
			
			// Sonderregeln brauchen nicht auf diese Weise behandelt zu werden
			if (charKomps[i].equals(CharKomponente.sonderregel)) {
				continue;
			}
			
			hashMap.put(charKomps[i], new HashSet());
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.CharElementsObserver#add(org.d3s.alricg.charKomponenten.CharElement)
	 */
	public void add(HeldenLink link) {
		final Iterator<CharElementsObserver> ite; 
		
		ite = hashMap.get(link.getZiel().getCharKomponente()).iterator();
		
		while(ite.hasNext()) {
			ite.next().add(link);
		}
	}


	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.CharElementsObserver#remove(org.d3s.alricg.charKomponenten.CharElement)
	 */
	public void remove(HeldenLink link) {
		final Iterator<CharElementsObserver> ite; 
		
		ite = hashMap.get(link.getZiel().getCharKomponente()).iterator();
		
		while(ite.hasNext()) {
			ite.next().remove(link);
		}
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.CharElementsObserver#update(org.d3s.alricg.charKomponenten.CharElement)
	 */
	public void update(HeldenLink link) {
		final Iterator<CharElementsObserver> ite; 
		
		ite = hashMap.get(link.getZiel().getCharKomponente()).iterator();
		
		while(ite.hasNext()) {
			ite.next().update(link);
		}
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.CharElementsObserver#updateAll(org.d3s.alricg.controller.CharKomponente)
	 */
	public void updateAll(CharKomponente komponente) {
		final Iterator<CharElementsObserver> ite = hashMap.get(komponente).iterator();
		
		while(ite.hasNext()) {
			ite.next().updateAll(komponente);
		}
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.Observable#register(org.d3s.alricg.controller.CharKomponente, org.d3s.alricg.gui.CharElementsObserver)
	 */
	public void register(CharKomponente komponente, CharElementsObserver observer) {
		// CharElementsObserver hinzufügen
		hashMap.get(komponente).add(observer);
	}
	
	public void registerX(CharKomponente komponente, CharElement element) {
		
	}

}
