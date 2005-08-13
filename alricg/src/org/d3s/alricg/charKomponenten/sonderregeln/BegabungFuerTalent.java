/*
 * Created on 31.07.2005 / 20:52:32
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.sonderregeln;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.prozessor.FormelSammlung;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;

/**
 * <u>Beschreibung:</u><br> 
 * Beschreibt die Sonderregel "Begabung für [Talent]" Siehe AH, Seite 107.
 * TODO NICHT kumultativ mit "Akademische Ausbildung"! (ersetzt diesen Vorteil)
 * TODO NICHT wählbar mit einer entsprechenden Unfähigkeit!
 * TODO Kostenberechnung. Hier oder im Vorteil?
 * 
 * Erledigt:
 * NICHT vereinbar mit "Begabung für TalentGruppe" in derselben Kategorie!
 * 
 * @author V. Strelow
 */
public class BegabungFuerTalent extends SonderregelAdapter {
	private static Talent begabtFuer; // Static um alle Klassen zu erreichen
	
	/**
	 * Konstruktor
	 */
	public BegabungFuerTalent() {
		this.setId("SR-BegabungFuerTalent");
	}
	
	@Override
	public String getBeschreibung() {
		// TODO implement
		return null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#canAddSelf(org.d3s.alricg.prozessor.HeldProzessor, boolean, org.d3s.alricg.charKomponenten.links.Link)
	 */
	@Override
	public boolean canAddSelf(HeldProzessor prozessor, boolean ok, Link srLink) {
		final SonderregelAdapter sr;
		
		if ( srLink.getZweitZiel() == null)  {
			// Es wird ein ZweitZiel benötigt!
			ProgAdmin.logger.warning("Es wird ein ZweitZiel benötigt, es ist jedoch keins angegeben!");
			return false;
		} else if ( !srLink.getZweitZiel().getCharKomponente().equals(CharKomponente.talent) ) {
			//	Nur Talente
			return false; 
		} else if (begabtFuer != null) {
			// Darf nur einmal gewählt werden!
			return false; 
		} else if ( ((Talent) srLink.getZweitZiel()).getKostenKlasse()
						.equals(FormelSammlung.KostenKlasse.A) ) 
		{
			// Talente mit "A" können nicht gewählt werden
			return false; 
		}
		
		// Prüfen ob "Begabt für Talentgruppe" mit der selben Sorte --> Nicht erlaubt!
		sr = prozessor.getSonderregelAdmin().getSonderregel("SR-BegabungFuerTalentgruppe", null, null);

		if (sr != null) {
			if ( ((BegabungFuerTalentgruppe) sr).getZweitZiel().equals(
					((Talent) srLink.getZweitZiel()).getSorte()) )
			{
				return false;
			}
		}
		
		return ok;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#changeKostenKlasse(org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse, org.d3s.alricg.charKomponenten.links.Link)
	 */
	@Override
	public KostenKlasse changeKostenKlasse(KostenKlasse klasse, Link link) {
		
		if (link.getZiel().equals(begabtFuer)) {
			return klasse.minusEineKk();
		}
		
		return klasse;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#finalizeSonderregel(org.d3s.alricg.charKomponenten.links.Link)
	 */
	@Override
	public void finalizeSonderregel(Link link) {
		
		// Da die SKT nun anders ist, sollten auch die Kosten neu berechnet werden
		if ( prozessor.getLinkById(begabtFuer.getId()) != null) {
			prozessor.updateKosten(	prozessor.getLinkById(begabtFuer.getId()) );
		}
		
		begabtFuer = null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * NUR mit gültigem ZweitZiel aufrufen (das ZweitZiel MUSS ein Talent sein)
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#initSonderregel(org.d3s.alricg.prozessor.HeldProzessor, org.d3s.alricg.charKomponenten.links.Link)
	 */
	@Override
	public void initSonderregel(HeldProzessor prozessor, Link srLink) {
		begabtFuer = (Talent) srLink.getZweitZiel();
		this.prozessor = prozessor;
		
		// Da die SKT nun anders ist, sollten auch die Kosten neu berechnet werden
		if ( prozessor.getLinkById(begabtFuer.getId()) != null) {
			prozessor.updateKosten(	prozessor.getLinkById(begabtFuer.getId()) );
		}
	}

	/** 
	 * Wird das Zweitziel angegeben, so wird es auch geprüft (als Talent). Wird es nicht
	 * angegeben, so wird auch nur die ID überprüft!
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#isSonderregel(java.lang.String, java.lang.String, org.d3s.alricg.charKomponenten.CharElement)
	 */
	@Override
	public boolean isSonderregel(String id, String text, CharElement zweitZiel) {
		
		// Wenn ein zweitziel angebenen ist, so muß dieses auch stimmen
		if (zweitZiel != null) {
			if ( !zweitZiel.equals(begabtFuer) ) {
				return false;
			}
		}

		// Ansonsten wird nur die ID überprüft
		return super.isSonderregel(id, text, zweitZiel);
	}
	
	// ------------------------------------------------------------------------------
	
	/**
	 * @return Die ID des ZweitZiels, also des Elements für das die Begabung gilt
	 */
	public Talent getZweitZiel() {
		return begabtFuer;
	}

}
