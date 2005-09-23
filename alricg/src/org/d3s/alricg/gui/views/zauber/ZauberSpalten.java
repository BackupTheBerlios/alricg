/**
 * 
 */
package org.d3s.alricg.gui.views.zauber;

import java.util.Comparator;

import org.d3s.alricg.charKomponenten.Zauber;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.gui.komponenten.table.SortableTable;
import org.d3s.alricg.gui.views.SpaltenSchema;
import org.d3s.alricg.store.FactoryFinder;

/**
 * @author Vincent
 *
 */
public class ZauberSpalten implements SpaltenSchema {

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
				bezeichner = FactoryFinder.find().getLibrary().getShortTxt(value);
			}
		}
		
		public String toString() {
			return bezeichner;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.SpaltenSchema#getSpalten(org.d3s.alricg.gui.views.SpaltenSchema.SpaltenArt)
	 */
	public Enum[] getSpalten(SpaltenArt art) {
		switch (art) {
			case objektDirekt:
			case objektLinkGen:
			case editorAuswahl:
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.SpaltenSchema#initTable(org.d3s.alricg.gui.komponenten.table.SortableTable, org.d3s.alricg.gui.views.SpaltenSchema.SpaltenArt)
	 */
	public void initTable(SortableTable table, SpaltenArt art) {
		if ( art.equals(SpaltenArt.objektDirekt) ) {
			table.setColumnMultiImage(1);
			table.setColumnButton(5, "+");
			table.setColumnButton(6, "-");
		} else if ( art.equals(SpaltenArt.objektLinkGen) ) {
			
		} else if ( art.equals(SpaltenArt.editorAuswahl) ) {
		
		}


	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.SpaltenSchema#getComparator(java.lang.Object)
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

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.SpaltenSchema#isSortable(java.lang.Object)
	 */
	public boolean isSortable(Object column) {
		switch ((Spalten) column) {
			case probe: return false;
			case plus: return false;
			case minus: return false;
			default: return true;
		}
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.SpaltenSchema#getHeaderToolTip(java.lang.Object)
	 */
	public String getHeaderToolTip(Object column) {
		// TODO Auto-generated method stub
		return null;
	}

}
