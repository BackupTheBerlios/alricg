/*
 * Created on 27.06.2005 / 20:44:42
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.prozessor.generierung;

import junit.framework.TestCase;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Eigenschaft;
import org.d3s.alricg.charKomponenten.EigenschaftEnum;
import org.d3s.alricg.charKomponenten.Rasse;
import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.held.GeneratorLink;
import org.d3s.alricg.held.Held;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;
import org.d3s.alricg.store.DataStore;
import org.d3s.alricg.store.FactoryFinder;

/**
 * <u>Beschreibung:</u><br> 
 * 
 * @author V. Strelow
 */
public class TalentBoxGenTest extends TestCase {
	private IdLink link1, link2, link3;
	private Talent talent1, talent2, talent3;
	private Rasse ras;
	private Held held;
	private HeldProzessor prozessor;
	private DataStore data;
	
	public static void main(String[] args) {
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	
        FactoryFinder.init();
        data = FactoryFinder.find().getData();
		// Initialisierung des Helden
		ProgAdmin.heldenAdmin.initHeldGenerierung();
		held = ProgAdmin.heldenAdmin.getActiveHeld();
		prozessor = ProgAdmin.heldenAdmin.getActiveProzessor();
		
		// Rassen erzeugen
		ras = new Rasse("RAS-test");
		
		// Erzeugen der Talente:
		// Talent 1:
		talent1 = new Talent("TAL-test-1");
		talent1.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) data.getCharElement("EIG-MU", CharKomponente.eigenschaft),
						(Eigenschaft) data.getCharElement("EIG-KL", CharKomponente.eigenschaft),
						(Eigenschaft) data.getCharElement("EIG-GE", CharKomponente.eigenschaft)
				});
		talent1.setKostenKlasse(KostenKlasse.A);
		talent1.setArt(Talent.Art.spezial);
		talent1.setSorte(Talent.Sorte.koerper);
		talent1.setName("Test Talent 1");
		
		// Talent 2:
		talent2 = new Talent("TAL-test-2");
		talent2.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) data.getCharElement("EIG-KK", CharKomponente.eigenschaft),
						(Eigenschaft) data.getCharElement("EIG-KO", CharKomponente.eigenschaft),
						(Eigenschaft) data.getCharElement("EIG-FF", CharKomponente.eigenschaft)
				});
		talent2.setKostenKlasse(KostenKlasse.D);
		talent2.setArt(Talent.Art.basis);
		talent2.setSorte(Talent.Sorte.handwerk);
		talent2.setName("Test Talent 2");
		
		// Talent 3:
		talent3 = new Talent("TAL-test-3");
		talent3.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) data.getCharElement("EIG-IN", CharKomponente.eigenschaft),
						(Eigenschaft) data.getCharElement("EIG-CH", CharKomponente.eigenschaft),
						(Eigenschaft) data.getCharElement("EIG-FF", CharKomponente.eigenschaft)
				});
		talent3.setKostenKlasse(KostenKlasse.H);
		talent3.setArt(Talent.Art.beruf);
		talent3.setSorte(Talent.Sorte.natur);
		talent3.setName("Test Talent 3");
		
		// Elemente zum Helden hinzufügen
		prozessor.addCharElement(talent1, 0);
		prozessor.addCharElement(talent2, 1);
		prozessor.addCharElement(talent3, 0);
		
		// Erzeugen der Links
		link1 = new IdLink(null, null);
		link2 = new IdLink(null, null);
		link3 = new IdLink(null, null);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		
		held = null;
		prozessor = null;
		ras = null;
		
		talent1 = null;
		talent2 = null;
		talent3 = null;
		
		link1 = null;
		link2 = null;
		link3 = null;
		
	}
	
	/**
	 * Testet ob das "SetUp" richtig funktioniert hat!
	 */
	public void testRichtigHinzugefuegt() {
		
		link1.setZielId(talent1);
		link2.setZielId(talent2);
		link3.setZielId(talent3);
		
		// Sind die CharElemente im Helden vorhanden?
		assertEquals(true,
				held.getElementBox(CharKomponente.talent).contiansEqualLink(link1) 
			);
		assertEquals(true,
				held.getElementBox(CharKomponente.talent).contiansEqualLink(link2) 
			);
		assertEquals(true,
				held.getElementBox(CharKomponente.talent).contiansEqualLink(link3) 
			);
		
		// Ist die Stufe korrekt übernommen worden?
		assertEquals(0,
				held.getElementBox(CharKomponente.talent).getEqualLink(link1).getWert()
			);
		assertEquals(1,
				held.getElementBox(CharKomponente.talent).getEqualLink(link2).getWert() 
			);
		assertEquals(0,
				held.getElementBox(CharKomponente.talent).getEqualLink(link3).getWert() 
			);
	}
	/**
	 * Testet ob die Kosten für die Talente richtig berechnet werden, sowie das setzen der
	 * Stufen und die Bestimmung der aktivierten Talente.
	 */
	public void testStufeSetzenKostenBerechnen() {
		final TalentBoxGen talentBox;
		talentBox = (TalentBoxGen) prozessor.getHeld().getElementBox(CharKomponente.talent);
		
		prozessor.updateElement(getLink(talent1), 3, null, null);
		prozessor.updateElement(getLink(talent2), 5, null, null);
		prozessor.updateElement(getLink(talent3), 7, null, null);
		
		// Die Stufe wird voll bezahlt!
		assertEquals(7, getLink(talent1).getKosten()); // + aktivierungskosten
		assertEquals(61, getLink(talent2).getKosten()); // Basis-Talent -> keine AK
		assertEquals(631, getLink(talent3).getKosten()); // + aktivierungskosten
		assertEquals(2, talentBox.getAktivierteTalente().size()); // Aktivierte Talente
		
		// Gesamtkosten
		assertEquals(699, 
				((TalentBoxGen) held.getElementBox(CharKomponente.talent)).getGesamtKosten()
			);
		
		// Modifikator (durch eine Rasse) erzeugen
		link1 = new IdLink(ras, null);
		link1.setZielId(talent1);
		link1.setWert(1);
		link2 = new IdLink(ras, null);
		link2.setZielId(talent2);
		link2.setWert(5);
		link3 = new IdLink(ras, null);
		link3.setZielId(talent3);
		link3.setWert(5);
		
		// Modis hinzufügen
		prozessor.addLink(link1);
		prozessor.addLink(link2);
		prozessor.addLink(link3);
		
		assertEquals(3, getLink(talent1).getWert()); // Stufe
		assertEquals(5, getLink(talent1).getKosten()); // Kosten mit Modi
		assertEquals(5, getLink(talent2).getWert()); // Stufe
		assertEquals(0, getLink(talent2).getKosten()); // Kosten mit Modi
		assertEquals(7, getLink(talent3).getWert()); // Stufe
		assertEquals(305, getLink(talent3).getKosten()); // Kosten mit Modi
		assertEquals(0, talentBox.getAktivierteTalente().size()); // Aktivierte Talente
		
		// Gesamtkosten
		assertEquals(310, 
				((TalentBoxGen) held.getElementBox(CharKomponente.talent)).getGesamtKosten()
			);
		
		// Stufe neu setzen
		prozessor.updateElement(getLink(talent1), 4, null, null);
		prozessor.updateElement(getLink(talent2), 7, null, null); 
		prozessor.updateElement(getLink(talent3), 3, null, null);
		
		assertEquals(4, getLink(talent1).getWert()); // Stufe
		assertEquals(9, getLink(talent1).getKosten()); // Kosten mit Modi
		assertEquals(7, getLink(talent2).getWert()); // Stufe
		assertEquals(60, getLink(talent2).getKosten()); // Kosten mit Modi
		assertEquals(5, getLink(talent3).getWert()); // Stufe (min möglich)
		assertEquals(0, getLink(talent3).getKosten()); // Kosten mit Modi
		assertEquals(0, talentBox.getAktivierteTalente().size()); // Aktivierte Talente
		
		// Gesamtkosten
		assertEquals(69, 
				((TalentBoxGen) held.getElementBox(CharKomponente.talent)).getGesamtKosten()
			);
		
		// Modis wieder entfernen
		prozessor.removeLinkFromElement(link1);
		prozessor.removeLinkFromElement(link2);
		prozessor.removeLinkFromElement(link3);
		
		assertEquals(3, getLink(talent1).getWert()); // Stufe 
		assertEquals(7, getLink(talent1).getKosten()); // Kosten + Aktivierungskosten!
		assertEquals(2, getLink(talent2).getWert()); // Stufe
		assertEquals(10, getLink(talent2).getKosten()); // Kosten (Basis, keine AK)
		assertNull(prozessor.getLinkByCharElement(talent3, null, null)); // entfernt
		assertEquals(1, talentBox.getAktivierteTalente().size()); // Aktivierte Talente
		
		// Gesamtkosten
		assertEquals(17, 
				((TalentBoxGen) held.getElementBox(CharKomponente.talent)).getGesamtKosten()
			);
		
		// Enfternen von Talent1, Stufe auf "0" setzen bei Talent2
		prozessor.removeElement(getLink(talent1));
		prozessor.updateElement(getLink(talent2), 0, null, null);
		
		assertNull(prozessor.getLinkByCharElement(talent1, null, null)); // entfernt
		assertEquals(0, getLink(talent2).getWert()); // Stufe
		assertEquals(0, getLink(talent2).getKosten()); // Kosten (Basis, keine AK)
		assertNull(prozessor.getLinkByCharElement(talent3, null, null)); // entfernt
		assertEquals(0, talentBox.getAktivierteTalente().size()); // Aktivierte Talente
		
		// Gesamtkosten
		assertEquals(0, 
				((TalentBoxGen) held.getElementBox(CharKomponente.talent)).getGesamtKosten()
			);
		
		// Hinzufügen von Modis
		prozessor.addLink(link1);
		prozessor.addLink(link2);
		link3.setWert(-3);
		prozessor.addLink(link3);
		
		assertEquals(1, getLink(talent1).getWert()); // Stufe
		assertEquals(0, getLink(talent1).getKosten()); // Kosten
		assertEquals(5, getLink(talent2).getWert()); // Stufe
		assertEquals(0, getLink(talent2).getKosten()); // Kosten (Basis, keine AK)
		assertEquals(-3, getLink(talent3).getWert()); // Stufe
		assertEquals(0, getLink(talent3).getKosten()); // Kosten
		assertEquals(0, talentBox.getAktivierteTalente().size()); // Aktivierte Talente
		
		// Gesamtkosten
		assertEquals(0, 
				((TalentBoxGen) held.getElementBox(CharKomponente.talent)).getGesamtKosten()
			);
		
		// Stufen neu setzen
		prozessor.updateElement(getLink(talent1), 3, null, null);
		prozessor.updateElement(getLink(talent2), 7, null, null);
		prozessor.updateElement(getLink(talent3), 1, null, null);
		
		assertEquals(3, getLink(talent1).getWert()); // Stufe
		assertEquals(5, getLink(talent1).getKosten()); // Kosten
		assertEquals(7, getLink(talent2).getWert()); // Stufe
		assertEquals(60, getLink(talent2).getKosten()); // Kosten (Basis, keine AK)
		assertEquals(1, getLink(talent3).getWert()); // Stufe
		assertEquals(76, getLink(talent3).getKosten()); // Kosten (von -3)
		assertEquals(1, talentBox.getAktivierteTalente().size()); // Aktivierte Talente
		
		// Gesamtkosten
		assertEquals(141, 
				((TalentBoxGen) held.getElementBox(CharKomponente.talent)).getGesamtKosten()
			);
		
		// Negative Zahlen 
		prozessor.updateElement(getLink(talent3), 0, null, null);
		
		assertEquals(0, getLink(talent3).getWert()); // Stufe
		assertEquals(60, getLink(talent3).getKosten()); // Kosten (von -3)
		assertEquals(1, talentBox.getAktivierteTalente().size()); // Aktivierte Talente
		
		prozessor.updateElement(getLink(talent3), -1, null, null);
		
		assertEquals(-1, getLink(talent3).getWert()); // Stufe
		assertEquals(40, getLink(talent3).getKosten()); // Kosten (von -3)
		assertEquals(0, talentBox.getAktivierteTalente().size()); // Aktivierte Talente
		
		prozessor.updateElement(getLink(talent3), -3, null, null);
		
		assertEquals(-3, getLink(talent3).getWert()); // Stufe
		assertEquals(0, getLink(talent3).getKosten()); // Kosten (von -3)
		assertEquals(0, talentBox.getAktivierteTalente().size()); // Aktivierte Talente
	}
	
	/**
	 * Testet ob die minimalen und maximalen Werte für ein Talent richtig berechnet werden!
	 */
	public void testMinMaxWerte() {
		
		prozessor.updateElement( // MU auf 10 setzen
				prozessor.getLinkById(EigenschaftEnum.MU.getId(), null, null),
				10, null, null
			);
		prozessor.updateElement( // KL auf 11 setzen
				prozessor.getLinkById(EigenschaftEnum.KL.getId(), null, null),
				11, null, null
			);
		prozessor.updateElement( // GE auf 12 setzen
				prozessor.getLinkById(EigenschaftEnum.GE.getId(), null, null),
				12, null, null
			);
		
		
		assertEquals(15, prozessor.getMaxWert(getLink(talent1)));
		assertEquals(0, prozessor.getMinWert(getLink(talent1)));
		
		// Modifikator (durch eine Rasse) erzeugen
		link1 = new IdLink(ras, null);
		link1.setZielId(talent1);
		link1.setWert(3);
		
		// Modi hinzufügen
		prozessor.addLink(link1);
		
		assertEquals(15, prozessor.getMaxWert(getLink(talent1)));
		assertEquals(3, prozessor.getMinWert(getLink(talent1)));
		
		prozessor.updateElement( // GE auf 9 setzen
				prozessor.getLinkById(EigenschaftEnum.GE.getId(), null, null),
				9, null, null
			);
		
		assertEquals(14, prozessor.getMaxWert(getLink(talent1)));
		assertEquals(3, prozessor.getMinWert(getLink(talent1)));
		
		// Modi wieder entfernen
		prozessor.removeLinkFromElement(link1);
		prozessor.addCharElement(talent1, 0);
		
		assertEquals(14, prozessor.getMaxWert(getLink(talent1)));
		assertEquals(0, prozessor.getMinWert(getLink(talent1)));
	}
	
	/**
	 * Testet ob richtig bestimmt wird ob ein Talent hinzugefügt werden darf!
	 */
	public void testCanAddAsNewElement() {
		Talent tmpTalent;
		
		// 3. nicht Basis Talent hinzufügen
		tmpTalent = new Talent("TAL-test-4");
		tmpTalent.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) data.getCharElement("EIG-MU", CharKomponente.eigenschaft),
						(Eigenschaft) data.getCharElement("EIG-KL", CharKomponente.eigenschaft),
						(Eigenschaft) data.getCharElement("EIG-GE", CharKomponente.eigenschaft)
				});
		tmpTalent.setKostenKlasse(KostenKlasse.A);
		tmpTalent.setArt(Talent.Art.spezial);
		tmpTalent.setSorte(Talent.Sorte.koerper);
		tmpTalent.setName("Test Talent 4");
		
		// Darf das Talent hinzugefügt werden?
		assertTrue(prozessor.canAddCharElement(tmpTalent, null, null, 0));
		
		prozessor.addCharElement(tmpTalent, 0);
		
		// Jetzt darf es nicht mehr hinzugefügt werden, da schon vorhanden!
		assertFalse(prozessor.canAddCharElement(tmpTalent, null, null, 0));
		
//		 4. nicht Basis Talent hinzufügen
		tmpTalent = new Talent("TAL-test-5");
		tmpTalent.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) data.getCharElement("EIG-MU", CharKomponente.eigenschaft),
						(Eigenschaft) data.getCharElement("EIG-KL", CharKomponente.eigenschaft),
						(Eigenschaft) data.getCharElement("EIG-GE", CharKomponente.eigenschaft)
				});
		tmpTalent.setKostenKlasse(KostenKlasse.A);
		tmpTalent.setArt(Talent.Art.spezial);
		tmpTalent.setSorte(Talent.Sorte.koerper);
		tmpTalent.setName("Test Talent 5");
		
		prozessor.addCharElement(tmpTalent, 0);
		
//		 5. nicht Basis Talent hinzufügen
		tmpTalent = new Talent("TAL-test-6");
		tmpTalent.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) data.getCharElement("EIG-MU", CharKomponente.eigenschaft),
						(Eigenschaft) data.getCharElement("EIG-KL", CharKomponente.eigenschaft),
						(Eigenschaft) data.getCharElement("EIG-GE", CharKomponente.eigenschaft)
				});
		tmpTalent.setKostenKlasse(KostenKlasse.A);
		tmpTalent.setArt(Talent.Art.spezial);
		tmpTalent.setSorte(Talent.Sorte.koerper);
		tmpTalent.setName("Test Talent 6");
		
		// Darf das Talent hinzugefügt werden?
		assertTrue(prozessor.canAddCharElement(tmpTalent, null, null, 0));
		
		prozessor.addCharElement(tmpTalent, 0);
		
//		 6. nicht Basis Talent hinzufügen
		tmpTalent = new Talent("TAL-test-6");
		tmpTalent.setDreiEigenschaften(
				new Eigenschaft[]  {
						(Eigenschaft) data.getCharElement("EIG-MU", CharKomponente.eigenschaft),
						(Eigenschaft) data.getCharElement("EIG-KL", CharKomponente.eigenschaft),
						(Eigenschaft) data.getCharElement("EIG-GE", CharKomponente.eigenschaft)
				});
		tmpTalent.setKostenKlasse(KostenKlasse.A);
		tmpTalent.setArt(Talent.Art.spezial);
		tmpTalent.setSorte(Talent.Sorte.koerper);
		tmpTalent.setName("Test Talent 6");
		
		// Es dürfen nur 5 Talente aktiviert werden, daher dies nicht mehr!
		assertFalse(prozessor.canAddCharElement(tmpTalent, null, null, 0));
		
	}
	
	/**
	 * Liefert den Link zu dem CharElement zurück
	 * @param enu Die gewünschte Eigenschaft
	 * @return Der Link von Prozessor zu der Eigenschaft
	 */
	private GeneratorLink getLink(CharElement elem) {
		
		return (GeneratorLink) prozessor.getLinkByCharElement(
				elem, 
				null, 
				null);
	}

}
