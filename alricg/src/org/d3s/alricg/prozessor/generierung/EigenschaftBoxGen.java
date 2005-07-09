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
 * Enthält die Logik zum Verarbeiten von Eigenschaften (dazu gehören neben MU, KL, usw. 
 * auch LEP, ASP, KA, Basis-AT, GS, INI u.ä.). Viele Methoden sind hier leer, da 
 * Eigenschaften nicht hinzugefügt oder entfernd werden können. Es geht also nur um die
 * Änderung von Eigenschaften, Min/Max-Werte, hinzufügen/ entfernen von Modis.
 * 
 * Verhalten:
 * - Wird eine Modifikation hinzugefügt, wird versucht die Stufe zu halten
 * - Wird eine Modifikation entfernd, wird die Stufe neu gesetzt.
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
		final GeneratorLink tmpLink;
		final int tmpInt;
		
		tmpLink = getEqualLink(link);
		
		if (stufeErhalten) {
			tmpInt = tmpLink.getWert(); // Alten Wert Speichern
			tmpLink.addLink(link); // Link hinzufügen
			tmpLink.setUserGesamtWert(tmpInt); // Versuchen den alten Wert wiederherzustellen
		} else {
			tmpLink.addLink(link);
		}
		
		// Überprüfen ob die Stufen OK sind
		inspectEigenschaftWert(tmpLink);
		
		updateKosten(tmpLink); // Kosten Aktualisieren
		
		return tmpLink;
	}

	/**
	 * Versucht überprüft ob der Wert des Elements "link" innerhalb der möglichen Grenzen ist.
	 * Wenn nicht wird versucht den Wert entsprechend zu setzten. Diese Methode wird
	 * beim Ändern der Herkunft benötigt.
	 *  
	 * @param link Der Link der überprüft werden soll
	 * @param prozessor Der Prozessor mit dem der Link überprüft wird
	 */
	private void inspectEigenschaftWert(GeneratorLink tmpLink) {
		final EigenschaftEnum eigen = ((Eigenschaft) tmpLink.getZiel()).getEigenschaftEnum();
		int tmpInt;
		
//		 Werte die sich errechen, müssen anders behandelt werden.
		tmpInt = 0;
		if (eigen.equals(EigenschaftEnum.LEP)) {
			tmpInt = prozessor.getHeld().getEigenschaftsWert(EigenschaftEnum.LEP);
		} else if (eigen.equals(EigenschaftEnum.ASP)) {
			tmpInt = prozessor.getHeld().getEigenschaftsWert(EigenschaftEnum.ASP);
		} else if (eigen.equals(EigenschaftEnum.AUP)) {
			tmpInt = prozessor.getHeld().getEigenschaftsWert(EigenschaftEnum.AUP);
		} else if (eigen.equals(EigenschaftEnum.MR)) {
			tmpInt = prozessor.getHeld().getEigenschaftsWert(EigenschaftEnum.MR);
		} else if (eigen.equals(EigenschaftEnum.AT)) {
			tmpInt = prozessor.getHeld().getEigenschaftsWert(EigenschaftEnum.AT);
		} else if (eigen.equals(EigenschaftEnum.PA)) {
			tmpInt = prozessor.getHeld().getEigenschaftsWert(EigenschaftEnum.PA);
		} else if (eigen.equals(EigenschaftEnum.INI)) {
			tmpInt = prozessor.getHeld().getEigenschaftsWert(EigenschaftEnum.INI);
		} else if (eigen.equals(EigenschaftEnum.FK)) {
			tmpInt = prozessor.getHeld().getEigenschaftsWert(EigenschaftEnum.FK);
		}
		
		if (tmpInt > 0) {
			// Die Errechneten Werte müssen anderes behandelt werden
			if ( tmpInt > prozessor.getMaxWert(tmpLink) ) {
				tmpLink.setUserGesamtWert(prozessor.getMaxWert(tmpLink));
			} else if ( tmpInt < prozessor.getMinWert(tmpLink) ) {
				tmpLink.setUserGesamtWert(prozessor.getMinWert(tmpLink));
			}
		} else {
			// Nicht errechnete Werte 
			Held.heldUtils.inspectWert(tmpLink, prozessor);
		}
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#getMaxWert(org.d3s.alricg.charKomponenten.links.Link)
	 */
	protected int getMaxWert(Link link) {
		// TODO Texte für das Notepad einfügen
		
		final EigenschaftEnum eigen = ((Eigenschaft) link.getZiel()).getEigenschaftEnum();
		int tmpInt;
		
		if ( eigen.equals(EigenschaftEnum.MU)
				|| eigen.equals(EigenschaftEnum.KL)
				|| eigen.equals(EigenschaftEnum.IN)
				|| eigen.equals(EigenschaftEnum.CH)
				|| eigen.equals(EigenschaftEnum.FF)
				|| eigen.equals(EigenschaftEnum.GE)
				|| eigen.equals(EigenschaftEnum.KO)
				|| eigen.equals(EigenschaftEnum.KK) ) {
			// Der Maximale Wert plus die Modis aus Herkunft
			return ((GeneratorLink) link).getWertModis() +
				GenerierungProzessor.genKonstanten.MAX_EIGENSCHAFT_WERT;

		} else if ( eigen.equals(EigenschaftEnum.SO) ) {
			
			return GenerierungProzessor.genKonstanten.MAX_SOZIALSTATUS;
			
		} else if ( eigen.equals(EigenschaftEnum.LEP) ) {
			// Selbst nicht beschränkt, nur die Anzahl die Hinzugekauft werden kann!
			
			// tmpInt = Der Wert ger Maximal hinzugekauft werden darf ( = KO /2)
			tmpInt = Math.round( 
						prozessor.getLinkById(EigenschaftEnum.KO.getId(), null, null).getWert() / 2
					);
			
			// Der errechnete Wert + die Modis + das Maximum was hinzugekauft werden darf
			return 
				FormelSammlung.berechneLep(
					prozessor.getLinkById(EigenschaftEnum.KO.getId(), null, null).getWert(),
					prozessor.getLinkById(EigenschaftEnum.KK.getId(), null, null).getWert()
				) 
				+ ((GeneratorLink) link).getWertModis()
				+ tmpInt;
			
		} else if ( eigen.equals(EigenschaftEnum.ASP) ) {
			// Selbst nicht beschränkt, nur die Anzahl die Hinzugekauft werden kann!
			
			// tmpInt = Der Wert ger Maximal hinzugekauft werden darf ( = CH x 1,5 )
			tmpInt = prozessor.getLinkById(EigenschaftEnum.CH.getId(), null, null).getWert();
			
			// Der errechnete Wert + die Modis + das Maximum was hinzugekauft werden darf
			return 
				FormelSammlung.berechneAsp(
					prozessor.getLinkById(EigenschaftEnum.MU.getId(), null, null).getWert(),
					prozessor.getLinkById(EigenschaftEnum.IN.getId(), null, null).getWert(),
					prozessor.getLinkById(EigenschaftEnum.CH.getId(), null, null).getWert()
				) 
				+ ((GeneratorLink) link).getWertModis()
				+ (int) Math.round( tmpInt * 1.5d );
			
		} else if ( eigen.equals(EigenschaftEnum.AUP ) ) {
			// Selbst nicht beschränkt, nur die Anzahl die Hinzugekauft werden kann!
			
			// tmpInt = Der Wert ger Maximal hinzugekauft werden darf ( = KO * 2)
			tmpInt = prozessor.getLinkById(EigenschaftEnum.KO.getId(), null, null).getWert() * 2;
			
			// Der errechnete Wert + die Modis + das Maximum was hinzugekauft werden darf
			return 
				FormelSammlung.berechneAup(
					prozessor.getLinkById(EigenschaftEnum.MU.getId(), null, null).getWert(),
					prozessor.getLinkById(EigenschaftEnum.KO.getId(), null, null).getWert(),
					prozessor.getLinkById(EigenschaftEnum.GE.getId(), null, null).getWert()
				) 
				+ ((GeneratorLink) link).getWertModis()
				+ tmpInt;
		} else if ( eigen.equals(EigenschaftEnum.KA ) ) {
			// Siehe S. 26 im Aventurische Götterdiener
			return prozessor.getLinkById(EigenschaftEnum.IN.getId(), null, null).getWert();
			
		} else {
			// Alle anderen Werte können nicht von User gesetzt werden, ein Maximum ist unnötig
			return 100;
		}
		
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#getMinWert(org.d3s.alricg.charKomponenten.links.Link)
	 */
	protected int getMinWert(Link link) {
		// TODO Texte für das Notepad einfügen
		
		final EigenschaftEnum eigen = ((Eigenschaft) link.getZiel()).getEigenschaftEnum();
		int tmpInt;
		
		if ( eigen.equals(EigenschaftEnum.MU)
				|| eigen.equals(EigenschaftEnum.KL)
				|| eigen.equals(EigenschaftEnum.IN)
				|| eigen.equals(EigenschaftEnum.CH)
				|| eigen.equals(EigenschaftEnum.FF)
				|| eigen.equals(EigenschaftEnum.GE)
				|| eigen.equals(EigenschaftEnum.KO)
				|| eigen.equals(EigenschaftEnum.KK) ) {
			// Der Maximale Wert plus die Modis aus Herkunft
			return ((GeneratorLink) link).getWertModis() +
				GenerierungProzessor.genKonstanten.MIN_EIGENSCHAFT_WERT;

		} else if ( eigen.equals(EigenschaftEnum.SO) ) {
			
			return 1;
			
		} else if ( eigen.equals(EigenschaftEnum.LEP) ) {
			// Selbst nicht beschränkt, nur die Anzahl die Hinzugekauft werden kann!
			
			// Minimal ist der Wert ohne zukäufe durch den User
			return 
				FormelSammlung.berechneLep(
					prozessor.getLinkById(EigenschaftEnum.KO.getId(), null, null).getWert(),
					prozessor.getLinkById(EigenschaftEnum.KK.getId(), null, null).getWert()
				) 
				+ ((GeneratorLink) link).getWertModis();
			
		} else if ( eigen.equals(EigenschaftEnum.ASP) ) {
			// Selbst nicht beschränkt, nur die Anzahl die Hinzugekauft werden kann!
			
			// Minimal ist der Wert ohne zukäufe durch den User
			return 
				FormelSammlung.berechneAsp(
					prozessor.getLinkById(EigenschaftEnum.MU.getId(), null, null).getWert(),
					prozessor.getLinkById(EigenschaftEnum.IN.getId(), null, null).getWert(),
					prozessor.getLinkById(EigenschaftEnum.CH.getId(), null, null).getWert()
				) 
				+ ((GeneratorLink) link).getWertModis();
			
		} else if ( eigen.equals(EigenschaftEnum.AUP ) ) {
			// Selbst nicht beschränkt, nur die Anzahl die Hinzugekauft werden kann!
			
			// Der errechnete Wert + die Modis + das Maximum was hinzugekauft werden darf
			return 
				FormelSammlung.berechneAup(
					prozessor.getLinkById(EigenschaftEnum.MU.getId(), null, null).getWert(),
					prozessor.getLinkById(EigenschaftEnum.KO.getId(), null, null).getWert(),
					prozessor.getLinkById(EigenschaftEnum.GE.getId(), null, null).getWert()
				) 
				+ ((GeneratorLink) link).getWertModis();
			
		} else if ( eigen.equals(EigenschaftEnum.KA ) ) {
			// Siehe S. 26 im Aventurische Götterdiener
			return 0;
			
		} else {
			// Alle anderen Werte können nicht von User gesetzt werden, ein Minimum ist unnötig
			return 0;
		}

	}

	/**
	 * Methode überschrieben
	 * Ein Element des Helden wird (durch den User) geändert. Es wird hierbei keine
	 * Prüfung durchgeführt. 
	 * WICHTIG: Die Stufe ist bei allen Eigenschaften die errechnet werden NICHT die 
	 * Gesamtstufe, sonder nur die Stufe die sich aus Modis + User gewählter Stufe ergibt.
	 * 
	 * @param link Link des Elements, das geändert werden soll
	 * @param stufe Die neue Stufe oder "KEIN_WERT", wenn die Stufe nicht geändert wird
	 * @param text Der neue Text oder 'null', wenn der Text nicht geändert wird
	 * 		(text ist z.B. bei "Vorurteil gegen Orks" der String "Orks")
	 * @param zweitZiel Das neue zweitZiel oder 'null', wenn dies nicht geändert wird
	 * 		(ZweitZiel ist z.B. bei "Unfähigkeit für Schwerter" das Talent "Schwerter")
	 * 
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
		
		((GeneratorLink) link).setUserGesamtWert(stufe);
		
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
		int kosten = 0;
		KostenKlasse KK = null;
		
		// KostenKlasse festlegen, wenn diese nach SKT berechent wird
		if (eigen.equals(EigenschaftEnum.LEP)) {
			KK = KostenKlasse.H;
		} else if (eigen.equals(EigenschaftEnum.ASP)) {
			KK = KostenKlasse.G;
		} else if (eigen.equals(EigenschaftEnum.AUP)) {
			KK = KostenKlasse.E;
		} else if (eigen.equals(EigenschaftEnum.KA)) {
			KK = KostenKlasse.F;
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
			if (genLink.getUserLink() != null) {
				kosten = FormelSammlung.berechneSktKosten(0, genLink.getUserLink().getWert(), KK);
			} else {
				kosten = 0;
			}
			
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
			if ( genLink.getUserLink() != null) {
				kosten = genLink.getUserLink().getWert();
			}
			
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

	@Override 
	protected void removeElement(HeldenLink element) {
		// Noop!
		// Eigenschaften können nicht entfernd werden!
	}

	@Override 
	protected void removeLinkFromElement(IdLink link, boolean stufeErhalten) {
		final GeneratorLink genLink;
		int tmpInt;
		
		genLink = this.getEqualLink(link);
		
		if (genLink == null) {
			ProgAdmin.logger.warning("Konnte Link nicht finden beim Entfernen eines Modis");
		}
		
		/*
		if (stufeErhalten) {
			tmpInt = genLink.getWert(); // Alten Wert Speichern
			genLink.removeLink(link); // Link entfernen
			genLink.setUserGesamtWert(tmpInt); // Versuchen den alten Wert wiederherzustellen
		} else {
			genLink.removeLink(link);
		}
		*/
		
		// Link entfernen
		genLink.removeLink(link);
		
		// Stufe ggf. neu setzen
		inspectEigenschaftWert(genLink);
		
		// Kosten aktualisieren
		updateKosten(genLink);
	}

}
