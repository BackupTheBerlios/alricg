/*
 * Created on 13.04.2005 / 16:19:57
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui.views;

import java.util.Comparator;

import javax.swing.Icon;

import org.d3s.alricg.charKomponenten.Zauber;
import org.d3s.alricg.charKomponenten.Werte.MagieMerkmal;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.gui.views.TalentView.Filter;
import org.d3s.alricg.gui.views.TalentView.Ordnung;





/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class ZauberView implements SpaltenSchema {
	
	// Nach was diese Tabelle geordent werden kann
	public enum Ordnung {
		merkmal;
	};
	
	// TODO Richtige Filter einbauen
	// Die Filter dieser Tabelle
	public enum Filter {
		keiner;
	};
	
	public enum Spalten {
		name("Name"),
		merkmale("Merkmale"),
		repraesentation("Repraesentation"),
		kostenKlasse("SKT"),
		probe("Probe"),
		plus("+"),
		minus("-");
		private String bezeichner; // Name der Angezeigt wird
		
		private Spalten(String value) {
			if (value.equals("+") || value.equals("-")) {
				bezeichner = value;
			} else {
				bezeichner = ProgAdmin.library.getShortTxt(value);
			}
		}
		
		public String toString() {
			return bezeichner;
		}
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#hasSammelbegriff()
	 */
	public boolean hasSammelbegriff() {
		return true;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#isHerkunft()
	 */
	public boolean isHerkunft() {
		return false;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getCellValue(java.lang.Object, java.lang.Object)
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

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getComparator(java.lang.Object)
	 */
	public Comparator getComparator(Object column) {
		switch ((Spalten) column) {
		case name: 	return namensComparator;
		case merkmale: return new Comparator<Zauber>() {
						public int compare(Zauber arg0, Zauber arg1) {
							return (arg0.getMerkmale().length - arg1.getMerkmale().length);
						}
					};
		case repraesentation: 	return new Comparator<Zauber>() {
						public int compare(Zauber arg0, Zauber arg1) {
							return (arg0.getVerbreitung().length - arg1.getVerbreitung().length);
						}
					};
		case kostenKlasse: return new Comparator<Zauber>() {
						public int compare(Zauber arg0, Zauber arg1) {
							return arg0.getKostenKlasse().toString().compareTo(arg1.getKostenKlasse().toString());
						}
					};
		}
		
		ProgAdmin.logger.severe("Case-Fall konnte nicht gefunden werden!");
		return null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#isSortable(java.lang.Object)
	 */
	public boolean isSortable(Object column) {
		switch ((Spalten) column) {
		case probe: return false;
		case plus: return false;
		case minus: return false;
		default: return true;
		}
		
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getToolTip(java.lang.Object, java.lang.Object)
	 */
	public String getToolTip(Object object, Object column) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getHeaderToolTip(java.lang.Object)
	 */
	public String getHeaderToolTip(Object column) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getSortOrdner()
	 */
	public Object[] getSortOrdner() {
		// TODO Nur testweise
		return MagieMerkmal.values();
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getOrdinalFromElement(java.lang.Object)
	 */
	public int[] getOrdinalFromElement(Object element) {
		// TODO Nur testweise
		int[] tmp = new int[((Zauber) element).getMerkmale().length];
		
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = ((Zauber) element).getMerkmale()[i].ordinal();
		}
		
		return tmp;
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
		return false;
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

}
