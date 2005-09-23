/*
 * Created on 15.09.2005 / 17:12:45
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui.views.talent;

import java.util.Comparator;

import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.gui.komponenten.table.SortableTable;
import org.d3s.alricg.gui.views.SpaltenSchema;
import org.d3s.alricg.gui.views.SpaltenSchema.SpaltenArt;
import org.d3s.alricg.store.TextStore;

/**
 * 
 * <u>Beschreibung:</u><br> 
 * Schema für die Darstellung von Talenten in SortableTables. Hier sind alle Methoden
 * zusammengefaßt, die NICHT von dem dargestellten Objekt abhängen, sondern nur
 * von der Objek-Art und der Spalte.
 * 
 * @see org.d3s.alricg.gui.views.SpaltenSchema
 * @author V. Strelow
 */
public class TalentSpalten implements SpaltenSchema {
	public enum Spalten {
		name("Name"),
		stern("*"),
		stufe("-"),
		modis("-"),
		sorte("Sorte"),
		kostenKlasse("SKT"),
		kosten("-"),
		art("Art"),
		spezialisierungen("-"),	
		auswahl("-"),
		leitTalent("-"),
		probe("Probe"),
		plus("+"),
		minus("-");
		private String bezeichner; // Name der Angezeigt wird
		
		/** Konstruktur
		 * @param value Der Tag um den bezeichner aus der Library zu laden
		 */
		private Spalten(String value) {
			if (value.equals("+") || value.equals("-") || value.equals("*")) {
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
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getSpalten()
	 */
	public Enum[] getSpalten(SpaltenArt art) {
		
		switch (art) {
		case objektDirekt:
			return new Spalten[] {
				Spalten.name, // 0
				Spalten.stern, // 1
				Spalten.sorte, // 2
				Spalten.art, // 3
				Spalten.kostenKlasse, 
				Spalten.probe, 
				Spalten.plus, 
			};
			
		case objektLinkGen:
			return new Spalten[] {
				Spalten.name, 
				Spalten.stufe, 
				Spalten.modis,
				Spalten.kostenKlasse, 
				Spalten.kosten,
				Spalten.art,
				Spalten.spezialisierungen,
				Spalten.auswahl,
				Spalten.leitTalent,
				Spalten.minus
			};
			
		case editorAuswahl:
			return new Spalten[] {
				Spalten.name, 
				Spalten.sorte, 
				Spalten.art, 
				Spalten.kostenKlasse, 
				Spalten.probe, 
				Spalten.plus
			};
		case editorGewaehlt:
			return new Spalten[] {
				Spalten.name, 
				Spalten.sorte, 
				Spalten.art, 
				Spalten.kostenKlasse, 
				Spalten.probe, 
				Spalten.minus
			};
			
		}
		
		return null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#initTable()
	 */
	public void initTable(SortableTable table, SpaltenArt art) {
		
		switch (art) {
		case objektDirekt:
			table.setColumnMultiImage(1); // Für den "stern"
			table.setColumnTextImage(4, false); // Für die SKT
			//table.setColumnButton(5, "+");
			//table.setColumnButton(6, "-");
			
		case objektLinkGen:
			
		case editorAuswahl:
			table.setColumnButton(Spalten.plus.toString(), "+");
		case editorGewaehlt:
			table.setColumnButton(Spalten.minus.toString(), "-");
		}
		
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getComparator(java.lang.Enum)
	 */
	public Comparator< ? > getComparator(Object column) {
		switch ((Spalten) column) {
		case name: 	return namensComparator;
		case sorte: return TalentSpalten.compSorte;
		case art: 	return TalentSpalten.compArt;
		case kostenKlasse: return TalentSpalten.compKostenKlasse;
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
	
	// Comperatoren, um Spalten sortieren zu können!
	private static Comparator compSorte = 
		new Comparator<Talent>() {
			public int compare(Talent arg0, Talent arg1) {
				return arg0.getSorte().toString().compareTo(arg1.getSorte().toString());
			}
		};
		
	private static Comparator compArt = 
		new Comparator<Talent>() {
			public int compare(Talent arg0, Talent arg1) {
				return arg0.getArt().toString().compareTo(arg1.getArt().toString());
			}
		};
		
	private static Comparator compKostenKlasse = 	
		new Comparator<Talent>() {
			public int compare(Talent arg0, Talent arg1) {
				return arg0.getKostenKlasse().toString().compareTo(arg1.getKostenKlasse().toString());
			}
		};

}
