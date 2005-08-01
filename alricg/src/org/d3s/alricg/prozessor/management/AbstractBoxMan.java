/*
 * Created on 02.05.2005 / 19:55:35
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.prozessor.management;

import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.LinkElementBox;

/**
 * <u>Beschreibung:</u><br> 
 * AbstractBoxMan bildet die Grundlage für die Kapselung und Verwalten bestimmte Arten 
 * von CharElementen in der Form von Links für das Management von Helden.
 * 
 * TODO implement
 * 
 * @author V. Strelow
 */
public abstract class AbstractBoxMan extends LinkElementBox {

	/**
	 * @param proz
	 */
	public AbstractBoxMan(HeldProzessor proz) {
		super(null, proz);
		// TODO Auto-generated constructor stub
	}

}
