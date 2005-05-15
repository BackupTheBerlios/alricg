/*
 * Created on 08.04.2005 / 00:35:21
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui.views;

import java.util.Comparator;

import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.store.TextStore;



/**
 * <u>Beschreibung:</u><br> 
 * Dient den SortableTreeTable und SortableTables als Vorlage für die Darstellung.
 * Alle speziellen Methoden für die Anzeige von Talenten werden in diesen Schema
 * zusammengefasst.
 * @author V. Strelow
 */
public class TalentView implements ViewSchema {

	public enum Spalten {
		name("Name"),
		sorte("Sorte"),
		art("Art"),
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
	};

	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getCellValue(java.lang.Object, java.lang.Enum)
	 */
	public Object getCellValue(Object object, Object column) {
		
		switch ((Spalten) column) {
			case name: 	return ((Talent) object).getName();
			case sorte: return ((Talent) object).getSorte();
			case art: 	return ((Talent) object).getArt();
			case kostenKlasse: return ((Talent) object).getKostenKlasse();
			case probe: return ((Talent) object).get3EigenschaftenString();
			case plus: 	return ViewSchema.buttonValue;
			case minus: return ViewSchema.buttonValue;
		}
		
		ProgAdmin.logger.severe("Case-Fall konnte nicht gefunden werden!");
		return null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getComparator(java.lang.Enum)
	 */
	public Comparator< ? > getComparator(Object column) {
		switch ((Spalten) column) {
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
	public boolean isSortable(Object column) {
		switch ((Spalten) column) {
		case probe: return false;
		case plus: 	return false;
		case minus: return false;
		}
		
		return true;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getToolTip(java.lang.Object, java.lang.Enum)
	 */
	public String getToolTip(Object object, Object column) {
		final TextStore lib = ProgAdmin.library;
		switch ((Spalten) column) {
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
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getHeaderToolTip(java.lang.Enum)
	 */
	public String getHeaderToolTip(Object column) {
        final TextStore lib = ProgAdmin.library;
		switch ((Spalten) column) {
			case name: 	return lib.getToolTipTxt("TblHeaderName");
			case sorte: return lib.getToolTipTxt("TblHeaderTalentSorte");
			case art: 	return lib.getToolTipTxt("TblHeaderTalentArt");
			case kostenKlasse: return lib.getToolTipTxt("TblHeaderKostenklasse");
			case probe: return lib.getToolTipTxt("TblHeaderProbe");
			case plus: 	return lib.getToolTipTxt("TblHeaderPlusButton");
			case minus: 	return lib.getToolTipTxt("TblHeaderMinusButton");
            default: return null;
		}
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
