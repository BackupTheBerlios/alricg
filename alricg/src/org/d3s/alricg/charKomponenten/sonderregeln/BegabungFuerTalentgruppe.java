/*
 * Created on 12.08.2005 / 22:54:43
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.sonderregeln;

import java.util.List;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.held.GeneratorLink;
import org.d3s.alricg.prozessor.FormelSammlung;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;

/**
 * <u>Beschreibung:</u><br> 
 * Diese Sonderregel implementiert das Verhalten wie im Vorteil "Begabung für [Talentgruppe]"
 * beschrieben, siehe AH S. 107
 * TODO NICHT kumultativ mit "Akademische Ausbildung"! (ersetzt diesen Vorteil)
 * TODO NICHT wählbar mit einer entsprechenden Unfähigkeit!
 * TODO Kostenberechnung. Hier oder im Vorteil?
 * 
 * Erledigt:
 * NICHT vereinbar mit "Begabung für Talent" in derselben Kategorie!
 * 
 * @author V. Strelow
 */
public class BegabungFuerTalentgruppe extends SonderregelAdapter {
	private static Talent.Sorte sorte;
	
	public BegabungFuerTalentgruppe() {
		this.setId("SR-BegabungFuerTalentgruppe");
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#getBeschreibung()
	 */
	@Override
	public String getBeschreibung() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#canAddSelf(org.d3s.alricg.prozessor.HeldProzessor, boolean, org.d3s.alricg.charKomponenten.links.Link)
	 */
	@Override
	public boolean canAddSelf(HeldProzessor prozessor, boolean ok, Link srLink) {
		final SonderregelAdapter sr;
		
		// Prüfen ob "Begabt für Talent" mit der selben Sorte --> Nicht erlaubt!
		sr = prozessor.getSonderregelAdmin().getSonderregel("SR-BegabungFuerTalent", null, null);

		if (sr != null) {
			if ( ((BegabungFuerTalent) sr).getZweitZiel().getSorte()
						.getValue().equals( srLink.getText() ) )
			{
				return false;
			}
		}
		
		return super.canAddSelf(prozessor, ok, srLink);
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#changeKostenKlasse(org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse, org.d3s.alricg.charKomponenten.links.Link)
	 */
	@Override
	public KostenKlasse changeKostenKlasse(KostenKlasse klasse, Link link) {
		
		// Wenn es sich um kein Talent handelt, ist es auch nicht betroffen
		if ( !link.getZiel().getCharKomponente().equals(CharKomponente.talent) ) {
			return klasse;
		}
		
		// Kostenklasse des Talents ändern, aber nicht unter A
		if ( ((Talent) link.getZiel()).getSorte().equals(sorte)
				&& !klasse.equals(FormelSammlung.KostenKlasse.A) ) 
		{
			return klasse.minusEineKk();
		}
		
		return klasse;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#finalizeSonderregel(org.d3s.alricg.charKomponenten.links.Link)
	 */
	@Override
	public void finalizeSonderregel(Link link) {
		final List<GeneratorLink> list;
		
		// Alle Kosten der Talente updaten
		list = prozessor.getHeld().getElementBox(CharKomponente.talent).getUnmodifiableList();
		
		for (int i = 0; i < list.size(); i++) {
			if ( list.get(i).getKosten() > 0 && 
					( ((Talent) list.get(i).getZiel()).getSorte().equals(sorte) ) )
			{
				prozessor.updateKosten(list.get(i));
			}
		}
		
		sorte = null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * Im Text des Links wird angegeben für welche Sorte von Talenten diese SR gilt!
	 * Es gilt der value des Enums!
	 * 
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#initSonderregel(org.d3s.alricg.prozessor.HeldProzessor, org.d3s.alricg.charKomponenten.links.Link)
	 */
	@Override
	public void initSonderregel(HeldProzessor prozessor, Link srLink) {
		final List<GeneratorLink> list;
		
		this.prozessor = prozessor;
		sorte = null;
		
		// Nach der richtigen Sorte suchen. Die sorte wird als Text im Link übergeben
		// Der Text muß mit dem "getValue" der Sorte übereinstimmen
		for (int i = 0; i < Talent.Sorte.values().length; i++) {
			if ( srLink.getText().equals(Talent.Sorte.values()[i].getValue()) ) {
				sorte = Talent.Sorte.values()[i];
				break;
			}
		}
		
		if (sorte == null) {
			ProgAdmin.logger.severe("Konnte in SR nicht die erwartete Talent-Sorte finden!");
		}
		
		// Alle Kosten der Talente updaten
		list = prozessor.getHeld().getElementBox(CharKomponente.talent).getUnmodifiableList();
		
		for (int i = 0; i < list.size(); i++) {
			if ( list.get(i).getKosten() > 0 && 
					( ((Talent) list.get(i).getZiel()).getSorte().equals(sorte) ) )
			{
				prozessor.updateKosten(list.get(i));
			}
		}

	}

	/** Methode überschrieben
	 * Wird ein Text angegeben, so wird der Text auch geprüft (als Value einer Talent.Sorte). 
	 * Wird es nicht angegeben, so wird auch nur die ID überprüft!
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#isSonderregel(java.lang.String, java.lang.String, org.d3s.alricg.charKomponenten.CharElement)
	 */
	@Override
	public boolean isSonderregel(String id, String text, CharElement zweitZiel) {
		
		// Wenn ein text angebenen ist, so muß dieser auch stimmen
		if (text != null) {
			if ( !text.equals(sorte.getValue()) ) {
				return false;
			}
		}

		// Ansonsten wird nur die ID überprüft
		return super.isSonderregel(id, text, zweitZiel);
	}
	
	// ------------------------------------------------------------------------------
	/**
	 * @return Die Talent-Sorte für die diese SR gilt
	 */
	public Talent.Sorte getZweitZiel() {
		return sorte;
	}

}
