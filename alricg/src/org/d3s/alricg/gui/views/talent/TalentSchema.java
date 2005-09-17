/*
 * Created on 15.09.2005 / 16:20:12
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui.views.talent;


import java.util.ArrayList;

import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.gui.views.SpaltenSchema;
import org.d3s.alricg.gui.views.WorkSchema;
import org.d3s.alricg.store.TextStore;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class TalentSchema implements WorkSchema {
	
	public enum Ordnung {
		bla;
	}
	
	public enum Filter {
		bla;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getCellValue(java.lang.Object, java.lang.Enum)
	 */
	public Object getCellValue(Object object, Object column) {
		
		switch ((TalentSpalten.Spalten) column) {
			case name: 	return ((Talent) object).getName();
			case sorte: return ((Talent) object).getSorte();
			case art: 	return ((Talent) object).getArt();
			case kostenKlasse: return ((Talent) object).getKostenKlasse();
			case probe: return ((Talent) object).get3EigenschaftenString();
			case plus: 	return SpaltenSchema.buttonValue;
			case minus: return SpaltenSchema.buttonValue;
		}
		
		ProgAdmin.logger.severe("Case-Fall konnte nicht gefunden werden!");
		return null;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.gui.views.ViewSchema#setCellValue()
	 */
	public void setCellValue(Object newValue, Object object, Object column) {
		// noop!
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.gui.views.ViewSchema#isCellEditable()
	 */
	public boolean isCellEditable(Object object, Object column) {
		return true;
	}

	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getToolTip(java.lang.Object, java.lang.Enum)
	 */
	public String getToolTip(Object object, Object column) {
		final TextStore lib = ProgAdmin.library;
		switch ((TalentSpalten.Spalten) column) {
			case name: 	
				if (object instanceof Talent) {
					return lib.getToolTipTxt("TblHeaderName");
				} else {
					return lib.getToolTipTxt("TblOrdner");
				}
			case art: 	
				
				if (object instanceof Talent) {
					switch (((Talent) object).getArt()) {
						case basis: return lib.getToolTipTxt("TblTalentArtBasis");
						case beruf: return lib.getToolTipTxt("TblTalentArtBeruf");
						case spezial: return lib.getToolTipTxt("TblTalentArtSpezial");
					}
				} else {
					return lib.getToolTipTxt("KeinWert");
				}
				
			case probe:
				
				if (object instanceof Talent) {
					return ((Talent) object).get3Eigenschaften()[0].getName() + " / "
							+ ((Talent) object).get3Eigenschaften()[1].getName() + " / "
							+ ((Talent) object).get3Eigenschaften()[2].getName();
				}else {
					return lib.getToolTipTxt("KeinWert");
				}
				
			case sorte: return lib.getToolTipTxt("TblHeaderTalentSorte");
			case kostenKlasse: return lib.getToolTipTxt("TblHeaderKostenklasse");
			case plus: 	return lib.getToolTipTxt("TblHeaderPlusButton");
			case minus: return lib.getToolTipTxt("TblHeaderMinusButton");
		}
		
		ProgAdmin.logger.severe("Case-Fall konnte nicht gefunden werden!");
		return null;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getEnums()
	 */
	public Enum[] getSortOrdner() {
		return Talent.Sorte.values();
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getOrdinalFromElement(org.d3s.alricg.CharKomponenten.CharElement)
	 */
	public int[] getOrdinalFromElement(Object element) {
		int[] tmp = new int[1];
		tmp[0] = ((Talent) element).getSorte().ordinal();
		return tmp;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.gui.views.ViewSchema#getFilterElem()
	 */
	public Enum[] getFilterElem() {
		return Filter.values();
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.gui.views.ViewSchema#getOrdnungElem()
	 */
	public Enum[] getOrdnungElem() {
		return Ordnung.values();
	}

	public boolean hasSammelbegriff() {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList doFilterElements(Enum filter, ArrayList aList) {
		// TODO Auto-generated method stub
		return null;
	}


}
