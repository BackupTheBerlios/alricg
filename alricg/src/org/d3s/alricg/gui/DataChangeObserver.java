/*
 * Created on 12.11.2005 / 14:51:45
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui;

/**
 * @author Vincent
 */
public interface DataChangeObserver<E> {

	void update(E element);
	
}
