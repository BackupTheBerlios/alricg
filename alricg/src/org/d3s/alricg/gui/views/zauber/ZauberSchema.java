/**
 * 
 */
package org.d3s.alricg.gui.views.zauber;

import java.util.ArrayList;

import javax.swing.Icon;

import org.d3s.alricg.charKomponenten.Zauber;
import org.d3s.alricg.charKomponenten.Werte.MagieMerkmal;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.gui.views.SpaltenSchema;
import org.d3s.alricg.gui.views.ZeilenSchema;
import org.d3s.alricg.gui.views.talent.TalentSpalten;
import org.d3s.alricg.gui.views.zauber.ZauberSpalten.Spalten;

/**
 * @author Vincent
 *
 */
public class ZauberSchema implements ZeilenSchema {

	// Nach was diese Tabelle geordent werden kann
	public enum Ordnung {
		merkmal;
	};
	
	// TODO Richtige Filter einbauen
	// Die Filter dieser Tabelle
	public enum Filter {
		keiner;
	};
	
	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#hasSammelbegriff()
	 */
	public boolean hasSammelbegriff() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getCellValue(java.lang.Object, java.lang.Object)
	 */
	public Object getCellValue(Object object, Object column) {
		switch ((Spalten) column) {
		case name: 	return ((Zauber) object).getName();
		case merkmale: 
			MagieMerkmal[] merkmale = ((Zauber) object).getMerkmale();
			Icon[] icons = new Icon[merkmale.length];
			
			for (int i = 0; i < merkmale.length; i++ ) {
				icons[i] = merkmale[i].getIconKlein();
			}
			
			return icons;
		case repraesentation: return ((Zauber) object).getVerbreitungAbkText();
		case kostenKlasse: return ((Zauber) object).getKostenKlasse();
		case probe: return ((Zauber) object).get3EigenschaftenString();
		case plus: 	return SpaltenSchema.buttonValue;
		case minus: return SpaltenSchema.buttonValue;
	}
	
	ProgAdmin.logger.severe("Case-Fall konnte nicht gefunden werden!");
	return null;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#setCellValue(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public void setCellValue(Object newValue, Object object, Object column) {
		// noop!
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#isCellEditable(java.lang.Object, java.lang.Object)
	 */
	public boolean isCellEditable(Object object, Object column) {
		if (column.equals(TalentSpalten.Spalten.name)) {
			return true;
		}
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
		return Ordnung.values();
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getFilterElem()
	 */
	public Enum[] getFilterElem() {
		return Filter.values();
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getSortOrdner()
	 */
	public Object[] getSortOrdner() {
		// TODO Nur testweise
		return MagieMerkmal.values();
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getOrdinalFromElement(java.lang.Object)
	 */
	public int[] getOrdinalFromElement(Object element) {
		// TODO Nur testweise
		int[] tmp = new int[((Zauber) element).getMerkmale().length];
		
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = ((Zauber) element).getMerkmale()[i].ordinal();
		}
		
		return tmp;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#doFilterElements(java.lang.Enum, java.util.ArrayList)
	 */
	public ArrayList doFilterElements(Enum filter, ArrayList aList) {
		// TODO Auto-generated method stub
		return null;
	}

}
