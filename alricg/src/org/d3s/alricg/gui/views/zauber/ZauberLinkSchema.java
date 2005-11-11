/*
 * Created on 20.09.2005
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */
package org.d3s.alricg.gui.views.zauber;

import java.util.ArrayList;
import java.util.Comparator;

import org.d3s.alricg.gui.views.ZeilenSchema;

/**
 * <u>Beschreibung:</u><br> 
 * Das Schema für das handling von GeneratorLinks mit Zaubern. Die Objekte hier sind Links, 
 * die als Ziel Zauber besitzen. Das Schema wird für ausgewählte Zauber bei der Generierung
 * verwendet.
 * @see org.d3s.alricg.gui.views.ZeilenSchema
 * @author V. Strelow
 */
public class ZauberLinkSchema implements ZeilenSchema {

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#hasSammelbegriff()
	 */
	public boolean hasSammelbegriff() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getCellValue(java.lang.Object, java.lang.Object)
	 */
	public Object getCellValue(Object object, Object column) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#setCellValue(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public void setCellValue(Object newValue, Object object, Object column) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#isCellEditable(java.lang.Object, java.lang.Object)
	 */
	public boolean isCellEditable(Object object, Object column) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getToolTip(java.lang.Object, java.lang.Object)
	 */
	public String getToolTip(Object object, Object column) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getOrdnungElem()
	 */
	public Enum[] getOrdnungElem() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getFilterElem()
	 */
	public Enum[] getFilterElem() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getSortOrdner()
	 */
	public Object[] getSortOrdner() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getOrdinalFromElement(java.lang.Object)
	 */
	public int[] getOrdinalFromElement(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#doFilterElements(java.lang.Enum, java.util.ArrayList)
	 */
	public ArrayList doFilterElements(Enum filter, ArrayList aList) {
		// TODO Auto-generated method stub
		return null;
	}

	public Comparator getComparator(Object column) {
		// TODO Auto-generated method stub
		return null;
	}

}
