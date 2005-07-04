/*
 * Created on 02.05.2005 / 19:58:07
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.prozessor.generierung;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Eigenschaft;
import org.d3s.alricg.charKomponenten.EigenschaftEnum;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.held.GeneratorLink;
import org.d3s.alricg.held.Held;
import org.d3s.alricg.held.HeldenLink;
import org.d3s.alricg.prozessor.FormelSammlung;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class EigenschaftBoxGen extends AbstractBoxGen {

	/**
	 * Konstruktor.
	 * @param proz Der Prozessor mit dem der zugehörige Held bearbeitet wird.
	 */
	public EigenschaftBoxGen(HeldProzessor proz) {
		super(proz);
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#addAsNewElement(org.d3s.alricg.charKomponenten.links.IdLink)
	 */
	protected GeneratorLink addAsNewElement(IdLink link) {
		final GeneratorLink tmpLink;
		
		//Link wird erstellt und zur List hinzugefügt
		tmpLink = new GeneratorLink(link);
		linkArray.add(tmpLink);
		
		// Kosten neu berechnen
		updateKosten(tmpLink);
		
//		Sonderregel wird von der überliegenden Ebende aufgerufen
		return tmpLink;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#canAddAsNewElement(org.d3s.alricg.charKomponenten.links.IdLink)
	 */
	protected boolean canAddAsNewElement(IdLink link) {
		// Zu Eigenschaften können keine neuen Elemente hinzugefügt werden
		return false;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#canRemoveElement(org.d3s.alricg.held.HeldenLink)
	 */
	protected boolean canRemoveElement(HeldenLink link) {
		// Von Eigenschaften können keine Elemente entfernt werden
		return false;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#addLinkToElement(org.d3s.alricg.charKomponenten.links.IdLink, boolean)
	 */
	protected GeneratorLink addLinkToElement(IdLink link, boolean stufeErhalten) {
		GeneratorLink tmpLink;
		int oldWert;
		
		tmpLink = getEqualLink(link);
		
		if (stufeErhalten) {
			oldWert = tmpLink.getWert(); // Alten Wert Speichern
			tmpLink.addLink(link); // Link hinzufügen
			tmpLink.setUserWert(oldWert); // Versuchen den alten Wert wiederherzustellen
		} else {
			tmpLink.addLink(link);
		}
		
		Held.heldUtils.inspectWert(tmpLink, prozessor);
		
		updateKosten(tmpLink); // Kosten Aktualisieren
		
		return tmpLink;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#getMaxWert(org.d3s.alricg.charKomponenten.links.Link)
	 */
	protected int getMaxWert(Link link) {
		// TODO hier müssen auch die Voraussetzungen (Herkunft, Vorteile usw.) mit 
		// bedacht werden!!! 
		
		return 0;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#getMinWert(org.d3s.alricg.charKomponenten.links.Link)
	 */
	protected int getMinWert(Link link) {
		// TODO hier müssen auch die Voraussetzungen (Herkunft, Vorteile usw.) mit 
		// bedacht werden!!! 
		return 0;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#updateElement(org.d3s.alricg.held.HeldenLink, int, java.lang.String, org.d3s.alricg.charKomponenten.CharElement)
	 */
	protected void updateElement(HeldenLink link, int stufe, String text,
			CharElement zweitZiel) {
		//Text und zweitZiel können nicht geändert werden bei Eigenschaften
		
		// Test das die Stufe nicht negativ wird
		assert (stufe > 0);
		// Bestimmte Eigenschaften können nicht direkt gesetzt werden
		assert ( !(((Eigenschaft) link.getZiel()).getEigenschaftEnum().equals(EigenschaftEnum.MR) 
				|| ((Eigenschaft) link.getZiel()).getEigenschaftEnum().equals(EigenschaftEnum.AT)
				|| ((Eigenschaft) link.getZiel()).getEigenschaftEnum().equals(EigenschaftEnum.FK)
				|| ((Eigenschaft) link.getZiel()).getEigenschaftEnum().equals(EigenschaftEnum.INI)
				|| ((Eigenschaft) link.getZiel()).getEigenschaftEnum().equals(EigenschaftEnum.PA)
				|| ((Eigenschaft) link.getZiel()).getEigenschaftEnum().equals(EigenschaftEnum.KA)
				|| ((Eigenschaft) link.getZiel()).getEigenschaftEnum().equals(EigenschaftEnum.GS)) );
		
		link.setUserWert(stufe);
		
		// Kosten neu berechnen
		updateKosten((GeneratorLink) link);
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#canUpdateWert(org.d3s.alricg.held.HeldenLink)
	 */
	protected boolean canUpdateWert(HeldenLink link) {
		// Grundsätzlich können werde bei Eigenschaften geändert werden
		final EigenschaftEnum eigen = ((Eigenschaft) link.getZiel()).getEigenschaftEnum();
		
		if ( eigen.equals(EigenschaftEnum.MR) 
				|| eigen.equals(EigenschaftEnum.AT)
				|| eigen.equals(EigenschaftEnum.FK)
				|| eigen.equals(EigenschaftEnum.INI)
				|| eigen.equals(EigenschaftEnum.PA)
				|| eigen.equals(EigenschaftEnum.KA)
				|| eigen.equals(EigenschaftEnum.GS) )
		{			
			// Diese Eigenschaften können nicht direkt geändert werden
			return false;	
		}
		
		return true;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#canUpdateText(org.d3s.alricg.held.HeldenLink)
	 */
	protected boolean canUpdateText(HeldenLink link) {
		// Es gibt keinen Text bei Eigenschaften
		return false;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#canUpdateZweitZiel(org.d3s.alricg.held.HeldenLink)
	 */
	protected boolean canUpdateZweitZiel(HeldenLink link) {
		// Es gibt kein Zweitziel bei Eigenschaften
		return false;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#updateKosten(org.d3s.alricg.held.GeneratorLink)
	 */
	protected void updateKosten(GeneratorLink genLink) {
		final EigenschaftEnum eigen = ((Eigenschaft) genLink.getZiel()).getEigenschaftEnum();
		int kosten;
		KostenKlasse KK = null;
		
		// KostenKlasse festlegen, wenn diese nach SKT berechent wird
		if (eigen.equals(EigenschaftEnum.LEP)) {
			KK = KostenKlasse.H;
		} else if (eigen.equals(EigenschaftEnum.ASP)) {
			KK = KostenKlasse.G;
		} else if (eigen.equals(EigenschaftEnum.AUP)) {
			KK = KostenKlasse.E;
		}
		
		if (KK != null) {
			// Berechnung mit SKT
			
			// Die Kosten-Kategorie als Nachricht absenden
			ProgAdmin.notepad.addSecondaryMsg(
					ProgAdmin.library.getMiddleTxt("Kosten-Kategorie")
					+ KK.getValue()
			);
			
			// Kostenklasse mit Sonderregeln überprüfen
			KK = prozessor.getSonderregelAdmin().changeKostenKlasse(KK, genLink);
			
			// Kosten berechnen
			kosten = FormelSammlung.berechneSktKosten(0, genLink.getUserLink().getWert(), KK);
			
			// Die Kosten als Nachricht absenden
			ProgAdmin.notepad.addSecondaryMsg(
					ProgAdmin.library.getMiddleTxt("Talent-GP-Kosten")
					+ kosten
			);
			
		} else if ( eigen.equals(EigenschaftEnum.MU)
				|| eigen.equals(EigenschaftEnum.KL)
				|| eigen.equals(EigenschaftEnum.IN)
				|| eigen.equals(EigenschaftEnum.CH)
				|| eigen.equals(EigenschaftEnum.FF)
				|| eigen.equals(EigenschaftEnum.GE)
				|| eigen.equals(EigenschaftEnum.KO)
				|| eigen.equals(EigenschaftEnum.KK)
				|| eigen.equals(EigenschaftEnum.SO) ) {
			// Berechnung ohne SKT
			
			// Die Kosten entsprechen hier dem Gewählten Wert
			kosten = genLink.getUserLink().getWert();
			
			// Die Kosten als Nachricht absenden
			ProgAdmin.notepad.addSecondaryMsg(
					ProgAdmin.library.getMiddleTxt("GP-Kosten")
					+ kosten
			);
			
		} else {
//			 keine Kosten, da dies errechnete Werte sind oder KA (durch Vorteile)
			return;
		}
		
		// Kosten mit Sonderregeln überprüfen
		kosten = prozessor.getSonderregelAdmin().changeKosten(kosten, genLink);
		
		genLink.setKosten(kosten);
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#canAddCharElement(org.d3s.alricg.charKomponenten.CharElement)
	 */
	protected boolean canAddCharElement(CharElement elem) {
		// Nach der initialisierung können keine Eigenschaften mehr hinzugefügt werden
		return false;
	}

}
