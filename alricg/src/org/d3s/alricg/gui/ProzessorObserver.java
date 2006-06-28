/*
 * Created on 16.01.2006 / 15:30:18
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui;

import java.util.List;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public interface ProzessorObserver {
	
	public void addElement(Object obj);
	public void removeElement(Object obj);
	public void updateElement(Object obj);
	public void setData(List list);
}
