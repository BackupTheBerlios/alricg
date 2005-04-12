/*
 * Created on 08.04.2005 / 00:35:21
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.GUI.views;

import java.util.Comparator;

import org.d3s.alricg.CharKomponenten.Talent;
import org.d3s.alricg.Controller.Library;
import org.d3s.alricg.Controller.ProgAdmin;
import static org.d3s.alricg.GUI.views.TalentView.TalentSpalten.art;
import static org.d3s.alricg.GUI.views.TalentView.TalentSpalten.kostenKlasse;
import static org.d3s.alricg.GUI.views.TalentView.TalentSpalten.minus;
import static org.d3s.alricg.GUI.views.TalentView.TalentSpalten.name;
import static org.d3s.alricg.GUI.views.TalentView.TalentSpalten.plus;
import static org.d3s.alricg.GUI.views.TalentView.TalentSpalten.probe;
import static org.d3s.alricg.GUI.views.TalentView.TalentSpalten.sorte;

/**
 * <u>Beschreibung:</u><br> 
 * Dient den SortableTreeTable und SortableTables als Vorlage für die Darstellung.
 * Alle speziellen Methoden für die Anzeige von Talenten werden in diesen Schema
 * zusammengefasst.
 * @author V. Strelow
 */
public class TalentView implements ViewSchema {

	public enum TalentSpalten {
		name("Name"),
		sorte("Sorte"),
		art("Art"),
		kostenKlasse("KostenKlasse"),
		probe("Probe"),
		plus("+"),
		minus("-");
		private String bezeichner; // Name der Angezeigt wird
		
		private TalentSpalten(String value) {
			if (value.equals("+") || value.equals("-")) {
				bezeichner = value;
			} else {
				bezeichner = Library.getShortTxt(value);
			}
		}
		
		public String toString() {
			return bezeichner;
		}
	};

	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getCellValue(java.lang.Object, java.lang.Enum)
	 */
	public Object getCellValue(Object object, Enum column) {
		
		switch ((TalentSpalten) column) {
			case name: 	return ((Talent) object).getName();
			case sorte: return ((Talent) object).getSorte();
			case art: 	return ((Talent) object).getArt();
			case kostenKlasse: return ((Talent) object).getKostenKlasse();
			case probe: return ((Talent) object).get3EigenschaftenString();
			case plus: 	return "button";
			case minus: return "button";
		}
		
		ProgAdmin.logger.severe("Case-Fall konnte nicht gefunden werden!");
		return null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getComparator(java.lang.Enum)
	 */
	public Comparator< ? > getComparator(Enum column) {
		switch ((TalentSpalten) column) {
		case name: 	return new Comparator() {
						public int compare(Object arg0, Object arg1) {
							String str1, str2;
							
							if (arg0 instanceof String) {
								str1 = (String) arg0;
							} else {
								str1 = ((Talent) arg0).getName();
							}
							
							if (arg1 instanceof String) {
								str2 = (String) arg1;
							} else {
								str2 = ((Talent) arg1).getName();
							}
							
							return str1.compareTo(str2);
						}
					};
		case sorte: return new Comparator<Talent>() {
						public int compare(Talent arg0, Talent arg1) {
							return arg0.getSorte().toString().compareTo(arg1.getSorte().toString());
						}
					};
		case art: 	return new Comparator<Talent>() {
						public int compare(Talent arg0, Talent arg1) {
							return arg0.getArt().toString().compareTo(arg1.getArt().toString());
						}
					};
		case kostenKlasse: return new Comparator<Talent>() {
								public int compare(Talent arg0, Talent arg1) {
									return arg0.getKostenKlasse().toString().compareTo(arg1.getKostenKlasse().toString());
								}
							};
		}
		
		ProgAdmin.logger.severe("Case-Fall konnte nicht gefunden werden!");
		return null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#isSortable(java.lang.Enum)
	 */
	public boolean isSortable(Enum column) {
		switch ((TalentSpalten) column) {
		case probe: return false;
		case plus: 	return false;
		case minus: return false;
		}
		
		return true;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getToolTip(java.lang.Object, java.lang.Enum)
	 */
	public String getToolTip(Object object, Enum column) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getHeaderToolTip(java.lang.Enum)
	 */
	public String getHeaderToolTip(Enum column) {
		switch ((TalentSpalten) column) {
			case name: 	return Library.getToolTipTxt("TblHeaderName");
			case sorte: return Library.getToolTipTxt("TblHeaderTalentSorte");
			case art: 	return Library.getToolTipTxt("TblHeaderTalentArt");
			case kostenKlasse: return Library.getToolTipTxt("TblHeaderKostenklasse");
			case probe: return Library.getToolTipTxt("TblHeaderProbe");
			case plus: 	return Library.getToolTipTxt("TblHeaderPlusButton");
			case minus: 	return Library.getToolTipTxt("TblHeaderMinusButton");
		}
		return null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getEnums()
	 */
	public Enum[] getSortEnums() {
		return Talent.Sorte.values();
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getOrdinalFromElement(org.d3s.alricg.CharKomponenten.CharElement)
	 */
	public int getOrdinalFromElement(Object element) {
		return ((Talent) element).getSorte().ordinal();
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#isCharElement()
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

}
