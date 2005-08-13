/*
 * Created on 12.08.2005 / 17:20:22
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */
package org.d3s.alricg.junit.sonderregeln;

import junit.framework.TestCase;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Eigenschaft;
import org.d3s.alricg.charKomponenten.EigenschaftEnum;
import org.d3s.alricg.charKomponenten.Nachteil;
import org.d3s.alricg.charKomponenten.Rasse;
import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.sonderregeln.Stubenhocker;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.held.GeneratorLink;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;
import org.d3s.alricg.prozessor.generierung.GenerierungProzessor;

/**
 * <u>Beschreibung:</u><br> 
 * Testet die Sonderregel "Stubenhocker".
 * @author V. Strelow
 */
public class StubenhockerTest extends TestCase {
	private Talent talent1, talent2, talent3, talent4, talent5, talent6, 
				talentBasis1, talentBasis2, talentBasis3;
	private IdLink link1, link2, link3, link4;
	private Rasse ras;
	private Nachteil nachteil;
	private HeldProzessor prozessor;
	private Stubenhocker stubenSR;
	
	
	protected void setUp() throws Exception {
		super.setUp();
		
		// Initialisierung des Helden
		ProgAdmin.heldenAdmin.initHeldGenerierung();
		prozessor = ProgAdmin.heldenAdmin.getActiveProzessor();
		
		// Sonderregel erzeugen
		stubenSR = new Stubenhocker();
		
		// Rasse erzeugen
		ras = new Rasse("RAS-test");
		
		// Nachteil Erzeugen
		nachteil = new Nachteil("NAC-test");
		nachteil.setSonderregel(stubenSR);
		
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
		talent2.setArt(Talent.Art.spezial);
		talent2.setSorte(Talent.Sorte.kampf);
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
		talent3.setSorte(Talent.Sorte.koerper);
		talent3.setName("Test Talent 3");
		
		// Talent 4:
		talent4 = new Talent("TAL-test-4");
		talent4.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-IN", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-CH", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-FF", CharKomponente.eigenschaft)
				});
		talent4.setKostenKlasse(KostenKlasse.D);
		talent4.setArt(Talent.Art.beruf);
		talent4.setSorte(Talent.Sorte.kampf);
		talent4.setName("Test Talent 4");
		
		// Talent 5:
		talent5 = new Talent("TAL-test-5");
		talent5.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-IN", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-CH", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-FF", CharKomponente.eigenschaft)
				});
		talent5.setKostenKlasse(KostenKlasse.E);
		talent5.setArt(Talent.Art.beruf);
		talent5.setSorte(Talent.Sorte.koerper);
		talent5.setName("Test Talent 5");
		
		// Talent 6:
		talent6 = new Talent("TAL-test-6");
		talent6.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-IN", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-CH", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-FF", CharKomponente.eigenschaft)
				});
		talent6.setKostenKlasse(KostenKlasse.F);
		talent6.setArt(Talent.Art.beruf);
		talent6.setSorte(Talent.Sorte.kampf);
		talent6.setName("Test Talent 6");
		
		// Basis Talent 1:
		talentBasis1 = new Talent("TAL-test-basis-1");
		talentBasis1.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-MU", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-KL", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-GE", CharKomponente.eigenschaft)
				});
		talentBasis1.setKostenKlasse(KostenKlasse.A);
		talentBasis1.setArt(Talent.Art.basis);
		talentBasis1.setSorte(Talent.Sorte.koerper);
		talentBasis1.setName("Test Basis Talent 1");
		
		// Basis Talent 2:
		talentBasis2 = new Talent("TAL-test-basis-2");
		talentBasis2.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-MU", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-KL", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-GE", CharKomponente.eigenschaft)
				});
		talentBasis2.setKostenKlasse(KostenKlasse.A);
		talentBasis2.setArt(Talent.Art.basis);
		talentBasis2.setSorte(Talent.Sorte.kampf);
		talentBasis2.setName("Test Basis Talent 2");
		
		// Basis Talent 3:
		talentBasis3 = new Talent("TAL-test-basis-3");
		talentBasis3.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-MU", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-KL", CharKomponente.eigenschaft),
						(Eigenschaft) ProgAdmin.data.getCharElement("EIG-GE", CharKomponente.eigenschaft)
				});
		talentBasis3.setKostenKlasse(KostenKlasse.A);
		talentBasis3.setArt(Talent.Art.basis);
		talentBasis3.setSorte(Talent.Sorte.koerper);
		talentBasis3.setName("Test Basis Talent 3");
	}

	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Testet ob die Methode "canAddSelf" richtig funktioniert, die überprüft ob die SR zum
	 * Helden hinzugefügt werden kann.
	 */
	public void testCanAddSelf() {
		prozessor.updateElement(getLinkEigenschaft(EigenschaftEnum.GE), 9, null, null);
		prozessor.updateElement(getLinkEigenschaft(EigenschaftEnum.KO), 10, null, null);
		prozessor.updateElement(getLinkEigenschaft(EigenschaftEnum.KK), 11, null, null);
		
		// Link zu dem Nachteil erzeugen
		link1 = new IdLink(null, null);
		link1.setZielId(nachteil);
		
		// Prüfen ob hinzugefügt werden darf
		assertEquals(true, stubenSR.canAddSelf(prozessor, true, link1));
		
		// Ändern der KK auf unzulässigen Wert
		prozessor.updateElement(getLinkEigenschaft(EigenschaftEnum.KK), 12, null, null);
		assertEquals(false, stubenSR.canAddSelf(prozessor, true, link1));
		
		// Wieder auf zulässigen Wert setzen
		prozessor.updateElement(getLinkEigenschaft(EigenschaftEnum.KK), 11, null, null);
		assertEquals(true, stubenSR.canAddSelf(prozessor, true, link1));
		
		// 5 körperliche/ Kampf-Talente, noch zulässig
		prozessor.addCharElement(talent1, 1);
		prozessor.addCharElement(talent2, 1);
		prozessor.addCharElement(talent3, 1);
		prozessor.addCharElement(talent4, 1);
		prozessor.addCharElement(talent5, 1);
		prozessor.addCharElement(talentBasis1, 0); // Egal, da keine Kosten
		prozessor.addCharElement(talentBasis2, 0); // Egal, da keine Kosten
		
		assertEquals(true, stubenSR.canAddSelf(prozessor, true, link1));
		
		// Jetzt nicht mehr zulässig
		prozessor.addCharElement(talent6, 0); // Auch Null kostet, da Aktivierungskosten
		assertEquals(false, stubenSR.canAddSelf(prozessor, true, link1));
	}
	
	/**
	 * Testet ob die Beschränkungen im Bezug auf die Eigenschaften richtig funktionieren.
	 */
	public void testEigenschaften() {
		// Link zu dem Nachteil erzeugen
		link1 = new IdLink(null, null);
		link1.setZielId(nachteil);
		
		// Sonderregel hinzufügen
		prozessor.getSonderregelAdmin().addSonderregel(link1);
		
		// Jetzt sind die Max Wert (KO, KK, GE) auf 11 begrenzt
		assertEquals(11, prozessor.getMaxWert(getLinkEigenschaft(EigenschaftEnum.GE)));
		assertEquals(11, prozessor.getMaxWert(getLinkEigenschaft(EigenschaftEnum.KO)));
		assertEquals(11, prozessor.getMaxWert(getLinkEigenschaft(EigenschaftEnum.KK)));
		assertEquals(14, prozessor.getMaxWert(getLinkEigenschaft(EigenschaftEnum.FF)));
		
		// Sonderregel entfernen
		prozessor.getSonderregelAdmin().removeSonderregel(link1);
		
		// Normale Begrenzung mehr
		assertEquals(14, prozessor.getMaxWert(getLinkEigenschaft(EigenschaftEnum.GE)));
		assertEquals(14, prozessor.getMaxWert(getLinkEigenschaft(EigenschaftEnum.KO)));
		assertEquals(14, prozessor.getMaxWert(getLinkEigenschaft(EigenschaftEnum.KK)));
		assertEquals(14, prozessor.getMaxWert(getLinkEigenschaft(EigenschaftEnum.FF)));
	}
	
	/**
	 * Testet ob die Stufen der Talente richtige gesetzt/ begrenzt werden
	 */
	public void testTalentStufen() {
		// Link zu dem Nachteil erzeugen
		link1 = new IdLink(null, null);
		link1.setZielId(nachteil);
		
		// Sonderregel hinzufügen
		prozessor.getSonderregelAdmin().addSonderregel(link1);
		
		// Prüfen ob hinzufügbar
		assertTrue(prozessor.canAddCharElement(talent1, null, null, 1));
		
		// Körperliche/Kampf-Talente hinzufügen noch zulässig
		prozessor.addCharElement(talent1, 1);
		prozessor.addCharElement(talent2, 1);
		
		// Maximalwerte Testen
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent1)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent2)));
		
		// Modifikationen hinzufügen
		// Modifikator (durch eine Rasse) erzeugen
		link2 = new IdLink(ras, null);
		link2.setZielId(talent1);
		link2.setWert(3);

		// Modifikator hinzufügen
		prozessor.addLink(link2);
		
		// Maximalwerte Testen
		assertEquals(5, prozessor.getMaxWert(this.getLinkCharElement(talent1)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent2)));
		
		// Weitere Körper/Kampf Talente hinzufügen
		prozessor.addCharElement(talent3, 1);
		prozessor.addCharElement(talent4, 1);
		prozessor.addCharElement(talent5, 1);
		prozessor.addCharElement(talentBasis1, 0); // Egal, da keine Kosten
		prozessor.addCharElement(talentBasis2, 0); // Egal, da keine Kosten
		
		// talent1, talentBasis1, talentBasis2 kosten nix! Daher alle anderen Steigerbar!
		assertEquals(5, prozessor.getMaxWert(this.getLinkCharElement(talent1)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent2)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent3)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent4)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent5)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talentBasis1)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talentBasis2)));
		
		// Stufe ändern um 5 Talente mit Kosten zu haben
		prozessor.updateElement(getLinkCharElement(talentBasis1), 1, null, null);
		
		// Nur die 5 Talente steigerbar, die bereits etwas kosten
		assertEquals(3, prozessor.getMaxWert(this.getLinkCharElement(talent1)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent2)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent3)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent4)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent5)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talentBasis1)));
		assertEquals(0, prozessor.getMaxWert(this.getLinkCharElement(talentBasis2)));

		// Stufen ändern, sollte aber nix am ergebnis verändern!
		prozessor.updateElement(getLinkCharElement(talent2), 0, null, null);
		prozessor.updateElement(getLinkCharElement(talent3), 0, null, null);
		
		// Nur die 5 Talente steigerbar, die bereits etwas kosten
		assertEquals(3, prozessor.getMaxWert(this.getLinkCharElement(talent1)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent2)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent3)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent4)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent5)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talentBasis1)));
		assertEquals(0, prozessor.getMaxWert(this.getLinkCharElement(talentBasis2)));
		
		// Modifikator (durch eine Rasse) erzeugen
		link2 = new IdLink(ras, null);
		link2.setZielId(talent6);
		link2.setWert(3);
		
		// Link der KEIN modi ist erzeugen
		link3 = new IdLink(null, null);
		link3.setZielId(talent6);
		link3.setWert(3);		
		
		// Prüfen ob die Links hinzugefügt werden können
		assertTrue(((GenerierungProzessor) prozessor).canAddLinkAsNewElement(link2));
		assertFalse(((GenerierungProzessor) prozessor).canAddLinkAsNewElement(link3));
		
		// Prüfen von "canUpdate"
		assertFalse(prozessor.canUpdateStufe(getLinkCharElement(talent1)));
		assertTrue(prozessor.canUpdateStufe(getLinkCharElement(talent2)));
		assertTrue(prozessor.canUpdateStufe(getLinkCharElement(talentBasis1)));
		assertFalse(prozessor.canUpdateStufe(getLinkCharElement(talentBasis2)));
		
		// Ein Talent entfernen, wieder alle Talente um zwei steigerbar!
		prozessor.removeElement(getLinkCharElement(talent2));

		// Alle Talente wieder um zwei steigerbar!
		assertEquals(5, prozessor.getMaxWert(this.getLinkCharElement(talent1)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent3)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent4)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent5)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talentBasis1)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talentBasis2)));
		assertTrue(((GenerierungProzessor) prozessor).canAddLinkAsNewElement(link2));
		assertTrue(((GenerierungProzessor) prozessor).canAddLinkAsNewElement(link3));
		
		// Stufen ändern, jetzt kosten wieder 5 Talente etwas!
		prozessor.updateElement(getLinkCharElement(talent1), 4, null, null);
		
		// Alle Talente wieder um zwei steigerbar!
		assertEquals(5, prozessor.getMaxWert(this.getLinkCharElement(talent1)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent3)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent4)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talent5)));
		assertEquals(2, prozessor.getMaxWert(this.getLinkCharElement(talentBasis1)));
		assertEquals(0, prozessor.getMaxWert(this.getLinkCharElement(talentBasis2)));
		
		// Prüfen von "canUpdate"
		assertTrue(prozessor.canUpdateStufe(getLinkCharElement(talent3)));
		assertTrue(prozessor.canUpdateStufe(getLinkCharElement(talentBasis1)));
		assertFalse(prozessor.canUpdateStufe(getLinkCharElement(talentBasis2)));
	}
	
	/**
	 * Testet ob die Kosten für ein Talent richtig berechnet werden.
	 */
	public void testTalentKosten() {
		// Link zu dem Nachteil erzeugen
		link1 = new IdLink(null, null);
		link1.setZielId(nachteil);
		
		// Körperliche/Kampf-Talente hinzufügen
		prozessor.addCharElement(talent1, 1);
		prozessor.addCharElement(talent2, 1);
		prozessor.addCharElement(talent3, 1);
		
		// Normale Kosten
		assertEquals(2, getLinkCharElement(talent1).getKosten());
		assertEquals(4, getLinkCharElement(talent2).getKosten());
		assertEquals(5, getLinkCharElement(talent3).getKosten());
		
		// Sonderregel hinzufügen
		prozessor.getSonderregelAdmin().addSonderregel(link1);
		
		// Kosten um eine Klasse erhöht
		assertEquals(4, getLinkCharElement(talent1).getKosten());
		assertEquals(5, getLinkCharElement(talent2).getKosten());
		assertEquals(7, getLinkCharElement(talent3).getKosten());
		
		// Talent hinzufügen
		prozessor.addCharElement(talent4, 1);
		assertEquals(9, getLinkCharElement(talent4).getKosten());
		
		// Sonderregel entfernen
		prozessor.getSonderregelAdmin().removeSonderregel(link1);
		
		// Normale Kosten
		assertEquals(2, getLinkCharElement(talent1).getKosten());
		assertEquals(4, getLinkCharElement(talent2).getKosten());
		assertEquals(5, getLinkCharElement(talent3).getKosten());
		assertEquals(7, getLinkCharElement(talent4).getKosten());

	}
	
// ---------------------------------------------------------------------------------------
	
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
	
	/**
	 * Liefert den Link zu der Eigenschaft zurück
	 * @param enu Die gerwünschte Eigenschaft
	 * @return Der Link von Prozessor zu der Eigenschaft
	 */
	private GeneratorLink getLinkEigenschaft(EigenschaftEnum enu) {
		
		return (GeneratorLink) prozessor.getLinkById(
				enu.getId(), 
				null, 
				null, 
				CharKomponente.eigenschaft);
		
	}
}
