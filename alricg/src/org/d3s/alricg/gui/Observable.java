/*
 * Created on 11.11.2005 / 23:28:23
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui;

import org.d3s.alricg.controller.CharKomponente;

/**
 * 
 * @author Vincent
 */
public interface Observable {
	
	void register(CharKomponente komponente, CharElementsObserver observer);

}
