/*
 * Created on 11.11.2005 / 14:54:46
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.controller.CharKomponente;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public interface CharElementsObserver {

	
	/**
	 * 
	 * @param element
	 */
	void add(CharElement element);
	/**
	 * 
	 * @param elements
	 */
	void addAll(CharElement[] elements);
	/**
	 * 
	 * @param element
	 */
	void remove(CharElement element);
	/**
	 * 
	 * @param elements
	 */
	void removeAll(CharElement[] elements);
	/**
	 * 
	 * @param element
	 */
	void update(CharElement element);
	/**
	 * 
	 * @param elements
	 */
	void updateAll(CharElement[] elements);
	
	/**
	 * Updatet alle Elemente des Observers die zu dieser Komponete gehören.
	 * @param komponente
	 */
	void updateAll(CharKomponente komponente);
}
