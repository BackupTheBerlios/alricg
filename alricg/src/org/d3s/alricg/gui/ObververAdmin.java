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
	 * Enth�lt alle 
	 */
	HashMap<CharKomponente, HashSet<DataModelObserver>> dataModelSet = new HashMap<CharKomponente, HashSet<DataModelObserver>>();
	
	/**
	 * Enth�lt alle Observer die �ber �nderungen an einem Link informiert werden m�ssen. Dies ist bei 
	 * allen Voraussetzungen der Fall! 
	 */
	HashMap<CharKomponente, HashSet<DataChangeObserver<CharElement>>> charElementObserverSet = new HashMap<CharKomponente, HashSet<DataChangeObserver<CharElement>>();
	
	/**
	 * Enth�lt solche CharElemente, die von anderen CharElementen abh�ngen (Voraussetzungen).
	 * Pro Key sind alle CharElemente gespeichert, die von mindestens einem CharElemente von der Art "Key" 
	 * abh�ngen. 
	 * Wird z.B. ein CharElement "A" ge�ndert, so m�ssen auch alle CharElemente, die zu der selben 
	 * CharKomponente geh�ren, aktualisiert werden. Es ist M�glich das bei diesen Elementen nun eine 
	 * Voraussetzung erf�llt ist oder nicht mehr erf�llt ist!
	 */
	HashMap<CharKomponente, HashSet<CharElement>> dataChangeSet = new HashMap<CharKomponente, HashSet<CharElement>>();
	
	public ObververAdmin() {
		final CharKomponente[] charKomps = CharKomponente.values();
		
		// F�r jede charKomponente einen Eintrag erzeugen (au�er f�r Sonderregeln)
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
		// CharElementsObserver hinzuf�gen
		hashMap.get(komponente).add(observer);
	}
	
	public void registerX(CharKomponente komponente, CharElement element) {
		
	}

}
