/*
 * Created on 02.07.2005 / 17:23:10
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.prozessor.management;

import org.d3s.alricg.prozessor.HeldProzessor;

public class EigenschaftBoxMan extends AbstractBoxMan {

	public EigenschaftBoxMan(HeldProzessor proz) {
		super(proz);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getGesamtKosten() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#updateKosten(org.d3s.alricg.held.GeneratorLink)
	 *
	protected void updateKosten(GeneratorLink genLink) {
		final EigenschaftEnum eigen = ((Eigenschaft) genLink.getZiel()).getEigenschaftEnum();
		int kosten;
		KostenKlasse KK;
		
		// Bestimme die KostenKlasse
		switch (eigen) {
		case LEP: KK = KostenKlasse.H; break;
		case ASP: KK = KostenKlasse.G; break;
		case AUP: KK = KostenKlasse.E; break;
		case MU: 
		case KL:
		case IN:
		case CH:
		case FF:
		case GE:
		case KO:
		case KK:
			KK = KostenKlasse.H; break;
		default: // keine Kosten, da dies errechnete Werte sind oder KA (durch Vorteile)
			return;
		}
		
		// Die Kosten-Kategorie als Nachricht absenden
		ProgAdmin.notepad.addSecondaryMsg(
				ProgAdmin.library.getMiddleTxt("Kosten-Kategorie")
				+ KK.getValue()
		);
		
		// Kostenklasse mit Sonderregeln überprüfen
		KK = prozessor.getSonderregelAdmin().changeKostenKlasse(KK, genLink);
		
		// Kosten Berechnen
		kosten = FormelSammlung.berechneSktKosten(0, genLink.getUserLink().getWert(), KK);
		
		// Die Kosten als Nachricht absenden
		ProgAdmin.notepad.addSecondaryMsg(
				ProgAdmin.library.getMiddleTxt("GP-Kosten")
				+ kosten
		);
		
		// Kosten mit Sonderregeln überprüfen
		kosten = prozessor.getSonderregelAdmin().changeKosten(kosten, genLink);
		
		genLink.setKosten(kosten);
	}
	*/
	
}
