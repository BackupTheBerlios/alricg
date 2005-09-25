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
import java.util.Comparator;

import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.controller.ImageAdmin;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.gui.komponenten.table.renderer.ImageTextObject;
import org.d3s.alricg.gui.views.ComparatorCollection;
import org.d3s.alricg.gui.views.ObjectSchema;
import org.d3s.alricg.gui.views.SpaltenSchema;
import org.d3s.alricg.gui.views.talent.TalentSpalten.Spalten;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;
import org.d3s.alricg.store.FactoryFinder;
import org.d3s.alricg.store.TextStore;

/**
 * <u>Beschreibung:</u><br> 
 * Das Schema für das handling von Talenten. Die Objekte hier sind direkt Talente, keine Links.
 * Das Schema wird für die Auswahl von Talenten (Generierung und Management) oder für den 
 * Editor verwendet.
 * @see org.d3s.alricg.gui.views.ObjectSchema
 * @author V. Strelow
 */
public class TalentSchema implements ObjectSchema {
	private static TalentSchema self; // Statischer selbst verweis
	
	// Arbeistobjekte, um Parameter zu übergeben. Aus performancegründen 
	// als Attribute
	private final static ImageTextObject tmpImageObj = new ImageTextObject();
	private final static Link tmpLink = new IdLink(null, null);
	
	public enum Ordnung {
		bla;
	}
	
	public enum Filter {
		bla;
	}
	
	/**
	 * Konstruktur
	 */
	public TalentSchema() {

	}
	
	/**
	 * Liefert eine Instanz dieser Klasse. 
	 * @return Eine Instance von TalentSpalten
	 */
	public static TalentSchema getInstance() {
		if (self == null) {
			self = new TalentSchema();
		} 
		
		return self;
	}
	
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.gui.views.ObjectSchema#getCellValue(java.lang.Object, java.lang.Enum)
	 */
	public Object getCellValue(Object object, Object column) {
		KostenKlasse tmpKK;
		
		switch ((TalentSpalten.Spalten) column) {
			case name: 	return ((Talent) object).getName();
			case stern:	// TODO implement
			case sorte: return ((Talent) object).getSorte();
			case art: 	return ((Talent) object).getArt();
			case kostenKlasse: 
				tmpLink.setZielId((Talent) object);
				tmpKK = ProgAdmin.heldenAdmin.getActiveProzessor().getSonderregelAdmin()
										.changeKostenKlasse(
												((Talent) object).getKostenKlasse(), tmpLink
											);
				tmpImageObj.setText( tmpKK.toString() );
				
				if ( tmpKK.equals( ((Talent) object).getKostenKlasse() ) )  {
					// Kosten sind unverändert, kein Icon
					tmpImageObj.setIcon(null);
					
				} else if ( ((Talent) object).getKostenKlasse().ordinal() < tmpKK.ordinal() ) {
					// Kosten sind billiger, daher das entsprechend Icon!
					if ( ((Talent) object).getKostenKlasse().ordinal() == tmpKK.ordinal() - 1)  {
						// SKT ist um eine Spalte billiger
						tmpImageObj.setIcon(ImageAdmin.pfeileGruen1);
					} else if ( ((Talent) object).getKostenKlasse().ordinal() == tmpKK.ordinal() - 2)  {
						// SKT ist um zwei Spalten billiger
						tmpImageObj.setIcon(ImageAdmin.pfeileGruen2);
					} else {
						// SKT ist um drei oder mehr Spalten billiger
						tmpImageObj.setIcon(ImageAdmin.pfeileGruen3);
					}
					
					
				} else {
					if ( ((Talent) object).getKostenKlasse().ordinal() == tmpKK.ordinal() + 1)  {
						// SKT ist um eine Spalte billiger
						tmpImageObj.setIcon(ImageAdmin.pfeileRot1);
					} else if ( ((Talent) object).getKostenKlasse().ordinal() == tmpKK.ordinal() + 2)  {
						// SKT ist um zwei Spalten billiger
						tmpImageObj.setIcon(ImageAdmin.pfeileRot2);
					} else {
						// SKT ist um drei oder mehr Spalten billiger
						tmpImageObj.setIcon(ImageAdmin.pfeileRot3);
					}
					
				}
				
				return tmpImageObj;
				
			case probe: return ((Talent) object).get3EigenschaftenString();
			case plus: 	return SpaltenSchema.buttonValue;
			case minus: return SpaltenSchema.buttonValue;
		}
		
		ProgAdmin.logger.warning("Case-Fall konnte nicht gefunden werden!");
		return null;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.GUI.views.ViewSchema#getComparator(java.lang.Enum)
	 */
	public Comparator< ? > getComparator(Object column) {
		Comparator tmpCpm;
		
		switch ((Spalten) column) {
		case name: 	return ComparatorCollection.compNamensComparator;
		case stern:	return TalentSchema.compStern;
		case sorte: return TalentSchema.compSorte;
		case art: 	return TalentSchema.compArt;
		case kostenKlasse: return ComparatorCollection.compKostenKlasse;
		}
		
		ProgAdmin.logger.warning("Case-Fall konnte nicht gefunden werden!");
		return null;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.gui.views.ObjectSchema#setCellValue()
	 */
	public void setCellValue(Object newValue, Object object, Object column) {
		// noop!
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.gui.views.ObjectSchema#isCellEditable()
	 */
	public boolean isCellEditable(Object object, Object column) {
		if (column.equals(TalentSpalten.Spalten.name)
				|| column.equals(TalentSpalten.Spalten.minus)
				|| column.equals(TalentSpalten.Spalten.plus) ) {
			// diese müssen immer True sein, damit die Navigation funktioniert
			return true;
		}
		return false;
	}

	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.gui.views.ObjectSchema#getToolTip(java.lang.Object, java.lang.Enum)
	 */
	public String getToolTip(Object object, Object column) {
		final TextStore lib = FactoryFinder.find().getLibrary();
		
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
	 * @see org.d3s.alricg.gui.views.ObjectSchema#getEnums()
	 */
	public Enum[] getSortOrdner() {
		return Talent.Sorte.values();
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.gui.views.ObjectSchema#getOrdinalFromElement(org.d3s.alricg.CharKomponenten.CharElement)
	 */
	public int[] getOrdinalFromElement(Object element) {
		int[] tmp = new int[1];
		tmp[0] = ((Talent) element).getSorte().ordinal();
		return tmp;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.gui.views.ObjectSchema#getFilterElem()
	 */
	public Enum[] getFilterElem() {
		return Filter.values();
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.gui.views.ObjectSchema#getOrdnungElem()
	 */
	public Enum[] getOrdnungElem() {
		return Ordnung.values();
	}

	/*
	 * (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ObjectSchema#hasSammelbegriff()
	 */
	public boolean hasSammelbegriff() {
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.d3s.alricg.gui.views.ObjectSchema#doFilterElements(java.lang.Enum, java.util.ArrayList)
	 */
	public ArrayList doFilterElements(Enum filter, ArrayList aList) {
		// TODO Auto-generated method stub
		return null;
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
		
	private static Comparator compStern =
		new Comparator<Talent>() {
			public int compare(Talent arg0, Talent arg1) {
				// TODO implement
				return 0;
			}
		};
	


}
