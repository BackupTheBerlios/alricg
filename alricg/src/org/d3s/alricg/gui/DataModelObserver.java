/*
 * Created on 12.11.2005 / 14:55:23
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.gui;

import org.d3s.alricg.charKomponenten.links.Link;

/**
 * @author Vincent
 */
public interface DataModelObserver extends DataChangeObserver<Link> {
	
	void add(Link link);
	void remove(Link link);

}
