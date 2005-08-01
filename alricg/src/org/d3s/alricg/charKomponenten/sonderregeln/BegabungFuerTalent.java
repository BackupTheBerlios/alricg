/*
 * Created on 31.07.2005 / 20:52:32
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.sonderregeln;

import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.prozessor.FormelSammlung;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;

/**
 * <u>Beschreibung:</u><br> 
 * Beschreibt die Sonderregel "Begabung für [Talent]" Siehe AH, Seite 107.
 * 
 * @author V. Strelow
 */
public class BegabungFuerTalent extends SonderregelAdapter {
	private static Talent begabtFuer; // Static um alle Klassen zu erreichen
	private String quelleId;
	private HeldProzessor prozessor;
	
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

		if ( !srLink.getZweitZiel().getCharKomponente().equals(CharKomponente.talent) ) {
//			 Nur Talente
			return false; 
		} else if (begabtFuer != null) {
			// Darf nur einmal gewählt werden!
			return false; 
		} else if ( ((Talent) srLink.getZweitZiel()).getKostenKlasse()
						.equals(FormelSammlung.KostenKlasse.A) ) 
		{
//			 Talente mit "A" können nicht genommen werden
			return false; 
		}
		
		return ok;
	}

	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#changeKosten(int, org.d3s.alricg.charKomponenten.links.Link)
	 */
	@Override
	public int changeKosten(int kosten, Link link) {
		
		// TODO Evtl. hier die Kosten für diese SR implementieren
		return super.changeKosten(kosten, link);
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
		quelleId = null;
		
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#initSonderregel(org.d3s.alricg.prozessor.HeldProzessor, org.d3s.alricg.charKomponenten.links.Link)
	 */
	@Override
	public void initSonderregel(HeldProzessor prozessor, Link srLink) {
		quelleId = srLink.getZiel().getId();
		begabtFuer = (Talent) srLink.getZweitZiel();
		this.prozessor = prozessor;
		
		// Da die SKT nun anders ist, sollten auch die Kosten neu berechnet werden
		if ( prozessor.getLinkById(begabtFuer.getId()) != null) {
			prozessor.updateKosten(	prozessor.getLinkById(begabtFuer.getId()) );
		}
	}

}
