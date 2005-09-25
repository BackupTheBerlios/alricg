/*
 * Created on 15.09.2005 / 17:12:45
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui.views.talent;

import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.gui.komponenten.table.SortableTable;
import org.d3s.alricg.gui.views.SpaltenSchema;
import org.d3s.alricg.store.FactoryFinder;
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
	//private static TalentSpalten self; // Statischer selbst verweis
	
	public enum Spalten {
		name("Name"),
		stern("*"),
		stufe("Stufe"),
		modis("Modis"),
		sorte("Sorte"),
		kostenKlasse("SKT"),
		kosten("Kosten"),
		art("Art"),
		spezialisierungen("Spez."),	
		auswahl("Auswahl"),
		leitTalent("L.T."),
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
				bezeichner = FactoryFinder.find().getLibrary().getShortTxt(value);
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
				Spalten.name,
				Spalten.stern,
				Spalten.sorte,
				Spalten.art,
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
			
		case objektLinkHel:
			// TODO implement
			return null;
			
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
		
		ProgAdmin.logger.warning("Case-Fall konnte nicht gefunden werden!");
		return null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#initTable()
	 */
	public void initTable(SortableTable table, SpaltenArt art) {
		
		switch (art) {
		case objektDirekt:
			table.setColumnMultiImage(Spalten.stern.toString());
			table.setColumnTextImage(Spalten.kostenKlasse.toString(), false);
			table.setColumnButton(Spalten.plus.toString(), "+");
			break;
			
		case objektLinkGen:
			table.setColumnTextImage(Spalten.kostenKlasse.toString(), false);
			table.setColumnButton(Spalten.minus.toString(), "+");
			break;
			
		case editorAuswahl:
			table.setColumnButton(Spalten.plus.toString(), "+");
			break;
			
		case editorGewaehlt:
			table.setColumnButton(Spalten.minus.toString(), "-");
			break;
			
		default:
			ProgAdmin.logger.warning("Case-Fall konnte nicht gefunden werden!");
		}
		
		
		
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#isSortable(java.lang.Enum)
	 */
	public boolean isSortable(Object column) {

		switch ((Spalten) column) {
		case modis: return false;
		case auswahl: return false;
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
        final TextStore lib = FactoryFinder.find().getLibrary();
        
		switch ((Spalten) column) {
			case name: 	return lib.getToolTipTxt("TblHeaderName");
			case stern: 	return lib.getToolTipTxt("TblHeaderStern");
			case stufe: 	return lib.getToolTipTxt("TblHeaderStufe");
			case modis: 	return lib.getToolTipTxt("TblHeaderModis");
			case kosten: 	return lib.getToolTipTxt("TblHeaderKosten");
			case spezialisierungen:	return lib.getToolTipTxt("TblHeaderSpezi");
			case auswahl:	return lib.getToolTipTxt("TblHeaderAuswahl");
			case leitTalent: return lib.getToolTipTxt("TblHeaderLeittalent");
			case sorte: return lib.getToolTipTxt("TblHeaderTalentSorte");
			case art: 	return lib.getToolTipTxt("TblHeaderTalentArt");
			case kostenKlasse: return lib.getToolTipTxt("TblHeaderKostenklasse");
			case probe: return lib.getToolTipTxt("TblHeaderProbe");
			case plus: 	return lib.getToolTipTxt("TblHeaderPlusButton");
			case minus: 	return lib.getToolTipTxt("TblHeaderMinusButton");
		}
		
		ProgAdmin.logger.warning("Case-Fall konnte nicht gefunden werden!");
		return null;
	}	

}
