/*
 * Created on 13.08.2005 / 14:08:54
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.junit.sonderregeln;

import junit.framework.TestCase;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Eigenschaft;
import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.charKomponenten.Vorteil;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.sonderregeln.BegabungFuerTalentgruppe;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.held.GeneratorLink;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;

public class BegabungFuerTalentgruppeTest extends TestCase {
	private Vorteil vorteil;
	private HeldProzessor prozessor;
	private IdLink link1, link2, link3;
	private Talent talent1, talent2, talent3, talent4;
	private BegabungFuerTalentgruppe begabungSR;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		ProgAdmin.heldenAdmin.initHeldGenerierung();
		prozessor = ProgAdmin.heldenAdmin.getActiveProzessor();
		
		// SR erzeugen
		begabungSR = new BegabungFuerTalentgruppe();
		
		// Vorteil erzeugen
		vorteil = new Vorteil("VOR-jUnit-test");
		vorteil.setSonderregel(begabungSR);

		// Erzeugen der Talente:
		// Talent 1:
		talent1 = new Talent("TAL-test-1");
		talent1.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-MU", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-KL", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-GE", CharKomponente.eigenschaft)
				});
		talent1.setKostenKlasse(KostenKlasse.A);
		talent1.setArt(Talent.Art.spezial);
		talent1.setSorte(Talent.Sorte.koerper);
		talent1.setName("Test Talent 1");
		
		// Talent 2:
		talent2 = new Talent("TAL-test-2");
		talent2.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-KK", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-KO", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-FF", CharKomponente.eigenschaft)
				});
		talent2.setKostenKlasse(KostenKlasse.B);
		talent2.setArt(Talent.Art.basis);
		talent2.setSorte(Talent.Sorte.handwerk);
		talent2.setName("Test Talent 2");
		
		// Talent 3:
		talent3 = new Talent("TAL-test-3");
		talent3.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-IN", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-CH", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-FF", CharKomponente.eigenschaft)
				});
		talent3.setKostenKlasse(KostenKlasse.C);
		talent3.setArt(Talent.Art.beruf);
		talent3.setSorte(Talent.Sorte.natur);
		talent3.setName("Test Talent 3");
		
		//Talent 4:
		talent4 = new Talent("TAL-test-4");
		talent4.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-KK", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-KO", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-FF", CharKomponente.eigenschaft)
				});
		talent4.setKostenKlasse(KostenKlasse.D);
		talent4.setArt(Talent.Art.beruf);
		talent4.setSorte(Talent.Sorte.handwerk);
		talent4.setName("Test Talent 4");
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testKosten() {
		// Talente hinzufügen
		prozessor.addCharElement(talent1, 1);
		prozessor.addCharElement(talent2, 1);
		prozessor.addCharElement(talent3, 1);
		
		// Normale Kosten
		assertEquals(2, getLinkCharElement(talent1).getKosten());
		assertEquals(2, getLinkCharElement(talent2).getKosten()); // Keine Aktivierungskosten
		assertEquals(5, getLinkCharElement(talent3).getKosten());
		
		// Sonderregel hinzufügen
		link1= new IdLink(null, null);
		link1.setZielId(vorteil);
		link1.setText(Talent.Sorte.handwerk.getValue());
		prozessor.getSonderregelAdmin().addSonderregel(link1);
		
		// Kosten prüfen, alle HandwerksTalente nun billiger!
		assertEquals(2, getLinkCharElement(talent1).getKosten());
		assertEquals(1, getLinkCharElement(talent2).getKosten()); // Keine Aktivierungskosten !!SR!!
		assertEquals(5, getLinkCharElement(talent3).getKosten());
		
		// Weiteres Handwerkstalent hinzufügen
		prozessor.addCharElement(talent4, 3);
		
		// Kosten prüfen
		assertEquals(2, getLinkCharElement(talent1).getKosten());
		assertEquals(1, getLinkCharElement(talent2).getKosten()); // Keine Aktivierungskosten !!SR!!
		assertEquals(5, getLinkCharElement(talent3).getKosten());
		assertEquals(20, getLinkCharElement(talent4).getKosten()); // !!SR!!
		
		// Eine Stufe steigern
		prozessor.updateElement(getLinkCharElement(talent2), 3, null, null);
		
		assertEquals(2, getLinkCharElement(talent1).getKosten());
		assertEquals(6, getLinkCharElement(talent2).getKosten()); // Keine Aktivierungskosten !!SR!!
		assertEquals(5, getLinkCharElement(talent3).getKosten());
		assertEquals(20, getLinkCharElement(talent4).getKosten()); // !!SR!!
		
		// Sonderregel wieder entfernen
		prozessor.getSonderregelAdmin().removeSonderregel(link1);
		
		// Alle Kosten wieder normal
		assertEquals(2, getLinkCharElement(talent1).getKosten());
		assertEquals(12, getLinkCharElement(talent2).getKosten()); // Keine Aktivierungskosten
		assertEquals(5, getLinkCharElement(talent3).getKosten());
		assertEquals(26, getLinkCharElement(talent4).getKosten());
		
		// SR wieder hinzufügen, nun mir "körper"
		link1.setText(Talent.Sorte.koerper.getValue());
		prozessor.getSonderregelAdmin().addSonderregel(link1);
		
		// Nix ändert sich, da talent1 bereits "A" als KK hat!
		assertEquals(2, getLinkCharElement(talent1).getKosten()); // !!SR!!
		assertEquals(12, getLinkCharElement(talent2).getKosten()); // Keine Aktivierungskosten
		assertEquals(5, getLinkCharElement(talent3).getKosten());
		assertEquals(26, getLinkCharElement(talent4).getKosten());
		
		// Sonderregel wieder entfernen
		prozessor.getSonderregelAdmin().removeSonderregel(link1);
		
		// SR wieder hinzufügen, nun mir "natur"
		link1.setText(Talent.Sorte.natur.getValue());
		prozessor.getSonderregelAdmin().addSonderregel(link1);
		
		// Kosten für Naturtalente nun gesenkt!
		assertEquals(2, getLinkCharElement(talent1).getKosten()); 
		assertEquals(12, getLinkCharElement(talent2).getKosten()); // Keine Aktivierungskosten
		assertEquals(4, getLinkCharElement(talent3).getKosten()); // !!SR!!
		assertEquals(26, getLinkCharElement(talent4).getKosten());
	}
	
	/**
	 * Liefert den Link zu dem CharElement zurück
	 * @param enu Die gewünschte Eigenschaft
	 * @return Der Link von Prozessor zu der Eigenschaft
	 */
	private GeneratorLink getLinkCharElement(CharElement elem) {
		
		return (GeneratorLink) prozessor.getLinkByCharElement(
				elem, 
				null, 
				null);
	}
	
}
