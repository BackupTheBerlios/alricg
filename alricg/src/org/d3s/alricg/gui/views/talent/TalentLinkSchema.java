/*
 * Created on 15.09.2005 / 16:08:56
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */
package org.d3s.alricg.gui.views.talent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.gui.views.ComparatorCollection;
import org.d3s.alricg.gui.views.ZeilenSchema;
import org.d3s.alricg.gui.views.talent.TalentSpalten.Spalten;
import org.d3s.alricg.held.GeneratorLink;
import org.d3s.alricg.held.HeldenLink;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.store.FactoryFinder;
import org.d3s.alricg.store.TextStore;

/**
 * <u>Beschreibung:</u><br> 
 * Das Schema für das handling von GeneratorLinks mit Talenten. Die Objekte hier sind Links, 
 * die als Ziel Talente besitzen. Das Schema wird für ausgewählte Talent bei der Generierung
 * verwendet.
 * @see org.d3s.alricg.gui.views.ZeilenSchema
 * @author V. Strelow
 */
public class TalentLinkSchema implements ZeilenSchema {
    
    /** <code>TalentLinkSchema</code>'s logger */
    private static final Logger LOG = Logger.getLogger(TalentLinkSchema.class.getName());
    
	HeldProzessor prozessor;
	
	/**
	 * <u>Beschreibung:</u><br> 
	 * Gibt die Möglichkeiten an, nach denen die Elemente in der Tabelle geordnet 
	 * werden können. "keine" ist immer vorhanden und bedeutet das nur eine
	 * normale Tabelle angezeigt wird, keine TreeTable. Ansonsten wird die 
	 * TreeTable nach der gewählten Ordnung angeordnet.
	 * @author V. Strelow
	 */
	public enum Ordnung {
		keine,
		sorte;
		
		private String bezeichner;
		
		public String toString() {
			return bezeichner;
		}
	}
	
	/**
	 * <u>Beschreibung:</u><br> 
	 * Gibt die Möglichkeiten an, nach denen die Elemente in der Tabelle gefiltert 
	 * werden können. Es werden nur solche Elemente angezeigt, die zu dem Filter 
	 * passen.
	 * @author V. Strelow
	 */
	public enum Filter {
		keiner,
		nurAktivierte,
		nurModifizierte,
		nurBasisTalente,
		nurSpezialTalente,
		nurBerufTalente;
		
		private String bezeichner;
		
		public String toString() {
			return bezeichner;
		}
	}
	
	public TalentLinkSchema() {
		prozessor = ProgAdmin.heldenAdmin.getActiveProzessor();
	}
	
	/*(non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#hasSammelbegriff()
	 */
	public boolean hasSammelbegriff() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getCellValue(java.lang.Object, java.lang.Object)
	 */
	public Object getCellValue(Object object, Object column) {
		final List<IdLink> list;
		StringBuffer tmpString = new StringBuffer("");
		
		switch ((TalentSpalten.Spalten) column) {
			case stufe: return ((Link) object).getWert();
			
			case modis:
				list = ((GeneratorLink) object).getLinkModiList();
				
				// Falls keine Modis existieren
				if (list.size() == 0) return "";
				
				// Anhängen aller Modis
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getWert() > 0) {
						tmpString.append("+");
					}
					tmpString.append(list.get(i).getWert());
					tmpString.append("/ ");
				}
				
				// Löschen der letzen Trennzeiche ("/ ")
				tmpString.delete(tmpString.length() - 3, tmpString.length() - 1);
				return tmpString.toString();
				
			case kosten: return ((HeldenLink) object).getKosten();
			case spezialisierungen: return ((Link) object).getText();
			case auswahl: 
				list = ((GeneratorLink) object).getLinkModiList();
				
				// Falls keine Auswahlen existieren
				if (list.size() == 0) return "";
				
				// Anhängen aller Modis
				// TODO Bessere Anzeige. Im Moment wird nur die Herkunft angezeigt aus dem Enum
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getQuellAuswahl() == null) continue;

					tmpString.append(list.get(i).getQuellAuswahl().getHerkunft()
												.getCharKomponente().toString());
					tmpString.append(", ");
				}
				
				// Löschen der letzen Trennzeichen (", ")
				if (tmpString.length() > 0 ) {
					tmpString.delete(tmpString.length() - 3, tmpString.length() - 1);
				}
				return tmpString.toString();
				
			case leitTalent: return ((Link) object).isLeitwert();
			
			default: // Aufrufen des Schemas für direkte Objekte
				return TalentSchema.getInstance().getCellValue(((Link) object).getZiel(), column);
		}
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#setCellValue(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public void setCellValue(Object newValue, Object object, Object column) {
		
		switch ((TalentSpalten.Spalten) column) {
			case stufe: prozessor.updateElement(
							(HeldenLink) object, 
							(Integer) newValue, 
							null, 
							null);
			
			case spezialisierungen: prozessor.updateElement(
							(HeldenLink) object, 
							CharElement.KEIN_WERT, 
							newValue.toString(), 
							null);
			default:
				LOG.warning("Case-Fall konnte nicht gefunden werden!");
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#isCellEditable(java.lang.Object, java.lang.Object)
	 */
	public boolean isCellEditable(Object object, Object column) {
		if (column.equals(TalentSpalten.Spalten.name)
				|| column.equals(TalentSpalten.Spalten.minus)
				|| column.equals(TalentSpalten.Spalten.plus)) {
			// diese müssen immer True sein, damit die Navigation funktioniert
			return true;
		}
		
		// Diese können im Normalfall editiert werden
		if ( object instanceof HeldenLink ) {
			switch ((TalentSpalten.Spalten) column) {
				case stufe: return prozessor.canUpdateStufe((HeldenLink) object);
				case spezialisierungen:	return prozessor.canUpdateText((HeldenLink) object);
			}
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getToolTip(java.lang.Object, java.lang.Object)
	 */
	public String getToolTip(Object object, Object column) {
		final List<IdLink> list;
		final StringBuffer tmpString = new StringBuffer();
		final TextStore lib = FactoryFinder.find().getLibrary();
		
		switch ((TalentSpalten.Spalten) column) {
			case stufe: // TODO Soll die Berechnung zeigen!
				return "todo";
				
			case modis: 
				list = ((GeneratorLink) object).getLinkModiList();
				
				// Falls keine Modis existieren
				if (list.size() == 0) return lib.getToolTipTxt("TblTalentModisKeine");
				
				// Anhängen aller Modis
				for (int i = 0; i < list.size(); i++) {
					tmpString.append(
							list.get(i).getQuellElement().getCharKomponente().getBezeichnung()
					);
					tmpString.append(": ");
					tmpString.append(list.get(i).getWert());
					tmpString.append("/ ");
				}
				
				// Löschen der letzen Trennzeiche ("/ ")
				tmpString.delete(tmpString.length() - 3, tmpString.length() - 1);
				return tmpString.toString();
				
			case kosten: // TODO Soll die Berechnung zeigen!
				return "todo";
				
			case spezialisierungen:
				if (((Link) object).getText().length() == 0) {
					return lib.getToolTipTxt("TblTalentSpeziKeine");
				} else {
					return lib.getToolTipTxt("TblTalentSpezi");
				}
				
			case auswahl: // TODO implementieren
			case leitTalent:
				if (((Link) object).isLeitwert()) {
					return lib.getToolTipTxt("TblTalentLeitwertTrue");
				} else {
					return lib.getToolTipTxt("TblTalentLeitwertFalse");
				}
			
			default: // Aufrufen des Schemas für direkte Objekte
				return TalentSchema.getInstance().getToolTip(((Link) object).getZiel(), column);
		}

	}
	
	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getOrdnungElem()
	 */
	public Enum[] getOrdnungElem() {
		// TODO Auto-generated method stub
		return Ordnung.values();
	}
	
	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getFilterElem()
	 */
	public Enum[] getFilterElem() {
		// TODO Auto-generated method stub
		return Filter.values();
	}

	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getSortOrdner()
	 */
	public Object[] getSortOrdner() {
		return Talent.Sorte.values();
	}
	
	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getOrdinalFromElement(java.lang.Object)
	 */
	public int[] getOrdinalFromElement(Object element) {
		int[] tmp = new int[1];
		tmp[0] = ((Talent) ((Link) element).getZiel()).getSorte().ordinal();
		return tmp;
	}
	
	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#doFilterElements(java.lang.Enum, java.util.ArrayList)
	 */
	public ArrayList doFilterElements(Enum filter, ArrayList aList) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ZeilenSchema#getComparator(java.lang.Object)
	 */
	public Comparator getComparator(Object column) {
		Comparator tmpCpm;
		
		switch ((Spalten) column) {
		case stufe: return ComparatorCollection.compLinkWert;
		case kosten: return ComparatorCollection.compLinkKosten;
		case spezialisierungen: return ComparatorCollection.compLinkText;
		case leitTalent: return compLeittalent;
		}
		
		// Erstellung eines Wrappers für Links, mit den "standart" Comparatoren
		return new ComparatorCollection.ComparatorWrapper( TalentSchema.getInstance().getComparator(column) );
	}

	/**
	 * Um zwei Links zu vergleichen. Vergeleicht wird der Leitwert
	 */
	public static Comparator compLeittalent = 	
		new Comparator<Link>() {
			public int compare(Link arg0, Link arg1) {
				if (arg0.isLeitwert() ==  arg1.isLeitwert()) {
					return 0;
				} else if (arg0.isLeitwert()) {
					return 1;
				} else {
					return -1;
				}
			}
		};
}
