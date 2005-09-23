/*
 * Created on 15.09.2005 / 16:08:56
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui.views.talent;

import java.util.ArrayList;

import org.d3s.alricg.gui.views.WorkSchema;

/**
 * <u>Beschreibung:</u><br> 
 * Das Schema für das handling von GeneratorLinks mit Talenten. Die Objekte hier sind Links, 
 * die als Ziel Talente besitzen. Das Schema wird für ausgewählte Talent bei der Generierung
 * verwendet.
 * @see org.d3s.alricg.gui.views.WorkSchema
 * @author V. Strelow
 */
public class TalentLinkSchema implements WorkSchema {

	/*(non-Javadoc)
	 * @see org.d3s.alricg.gui.views.WorkSchema#hasSammelbegriff()
	 */
	public boolean hasSammelbegriff() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.WorkSchema#getCellValue(java.lang.Object, java.lang.Object)
	 */
	public Object getCellValue(Object object, Object column) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.WorkSchema#setCellValue(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public void setCellValue(Object newValue, Object object, Object column) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.WorkSchema#isCellEditable(java.lang.Object, java.lang.Object)
	 */
	public boolean isCellEditable(Object object, Object column) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.WorkSchema#getToolTip(java.lang.Object, java.lang.Object)
	 */
	public String getToolTip(Object object, Object column) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.WorkSchema#getOrdnungElem()
	 */
	public Enum[] getOrdnungElem() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.WorkSchema#getFilterElem()
	 */
	public Enum[] getFilterElem() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.WorkSchema#getSortOrdner()
	 */
	public Object[] getSortOrdner() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.WorkSchema#getOrdinalFromElement(java.lang.Object)
	 */
	public int[] getOrdinalFromElement(Object element) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.WorkSchema#doFilterElements(java.lang.Enum, java.util.ArrayList)
	 */
	public ArrayList doFilterElements(Enum filter, ArrayList aList) {
		// TODO Auto-generated method stub
		return null;
	}

}
