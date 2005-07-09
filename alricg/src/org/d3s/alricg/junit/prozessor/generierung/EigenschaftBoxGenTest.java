/*
 * Created on 02.07.2005 / 01:28:14
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.junit.prozessor.generierung;

import junit.framework.TestCase;

import org.d3s.alricg.charKomponenten.EigenschaftEnum;
import org.d3s.alricg.charKomponenten.Kultur;
import org.d3s.alricg.charKomponenten.Profession;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.held.GeneratorLink;
import org.d3s.alricg.held.Held;
import org.d3s.alricg.prozessor.HeldProzessor;

public class EigenschaftBoxGenTest extends TestCase {
	private Held held;
	private HeldProzessor prozessor;
	private Kultur k;
	private Profession p;
	private IdLink link, link2;
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		ProgAdmin.heldenAdmin.initHeldGenerierung();
		held = ProgAdmin.heldenAdmin.getActiveHeld();
		prozessor = ProgAdmin.heldenAdmin.getActiveProzessor();
		k = new Kultur("KUL-jUnit-test-eigen");
		p = new Profession("PROF-jUnit-test-eigen");
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		held = null;
		prozessor = null;
		k = null;
		p = null;
		link = null;
		link2 = null;
	}

	/**
	 * Liefert den Link zu der Eigenschaft zur�ck
	 * @param enu Die gerw�nschte Eigenschaft
	 * @return Der Link von Prozessor zu der Eigenschaft
	 */
	private GeneratorLink getLink(EigenschaftEnum enu) {
		
		return (GeneratorLink) prozessor.getLinkById(
				enu.getId(), 
				null, 
				null, 
				CharKomponente.eigenschaft);
		
	}
	
	/*
	 * Testet der Basis Funktionen: Das Setzen der Eigenschaften und 
	 * die Berechnung der Kosten!
	 */
	public void testUpdateStufe() {
		
		prozessor.updateElement(getLink(EigenschaftEnum.SO), 2, null, null);
		
		prozessor.updateElement(getLink(EigenschaftEnum.KO), 10, null, null);
		prozessor.updateElement(getLink(EigenschaftEnum.KK), 11, null, null);
		prozessor.updateElement(getLink(EigenschaftEnum.LEP), 5, null, null);
		
		prozessor.updateElement(getLink(EigenschaftEnum.MU), 9, null, null);
		prozessor.updateElement(getLink(EigenschaftEnum.IN), 12, null, null);
		prozessor.updateElement(getLink(EigenschaftEnum.CH), 14, null, null);
		prozessor.updateElement(getLink(EigenschaftEnum.ASP), 2, null, null);
		
		// "Nicht Berechnete" Werte �berpr�fen
		assertEquals(
				10,
				held.getEigenschaftsWert(EigenschaftEnum.KO)
			);
		assertEquals(
				11,
				held.getEigenschaftsWert(EigenschaftEnum.KK)
			);
		assertEquals(
				14,
				held.getEigenschaftsWert(EigenschaftEnum.CH)
			);
		
		// Errechnete Werte �berpr�fen: 
		assertEquals( // (10 + 10 + 11) / 2 = 16 + 5 = 21 
				21,
				held.getEigenschaftsWert(EigenschaftEnum.LEP)
			);
		assertEquals( // (9 + 12 + 14) / 2 = 18 + 2 = 20
				20,
				held.getEigenschaftsWert(EigenschaftEnum.ASP)
			);
		
		// �berpr�fen ob die Kosten richtig sind:
		assertEquals( // Stufe = Kosten = 10 
				10,
				getLink(EigenschaftEnum.KO).getKosten()
			);
		assertEquals( // Stufe = Kosten = 11 
				11,
				getLink(EigenschaftEnum.KK).getKosten()
			);
		assertEquals( // Stufe = Kosten = 9 
				9,
				getLink(EigenschaftEnum.MU).getKosten()
			);
		assertEquals( // Stufe = Kosten = 2 
				2,
				getLink(EigenschaftEnum.SO).getKosten()
			);
		assertEquals( // �ber SKT - Nur die gekauften Punkte
				306,
				getLink(EigenschaftEnum.LEP).getKosten()
			);
		assertEquals( // �ber SKT - Nur die gekauften Punkte
				26,
				getLink(EigenschaftEnum.ASP).getKosten()
			);		
		
		
		// Sonstiges:
		assertEquals( // Sollte nix kosten!
				0,
				getLink(EigenschaftEnum.MR).getKosten()
			);
		
		assertEquals( // Max Wert = IN
				12,
				prozessor.getMaxWert(getLink(EigenschaftEnum.KA))
			);
	}

	/*
	 * Testet mehrer Operationen auf der Eigenschaft Mut
	 * (Setzen der Eigenschaft, Kosten, Modis)
	 */
	public void testMU() {
		// ------------- Test der Eigenschaft MU
		// Kultur setzen
		link = new IdLink(k, null);
		link.setWert(2);
		link.setZielId(ProgAdmin.data.getCharElement(EigenschaftEnum.MU.getId()));
		
		prozessor.updateElement(getLink(EigenschaftEnum.MU), 10, null, null);
		prozessor.addLinkToElement(link);
		
		assertEquals( // Max Wert: 14 + 2 = 16
				16,
				prozessor.getMaxWert(getLink(EigenschaftEnum.MU))
		);
		assertEquals( // Max Wert: 8 + 2 = 10
				10,
				prozessor.getMinWert(getLink(EigenschaftEnum.MU))
		);
		assertEquals( // Gesamtstufe
				10,
				getLink(EigenschaftEnum.MU).getWert()
			);
		assertEquals( // User Gew�hhlt
				8,
				getLink(EigenschaftEnum.MU).getUserLink().getWert()
			);
		assertEquals( // Modis
				2,
				getLink(EigenschaftEnum.MU).getWertModis()
			);
		assertEquals( // Kosten
				8,
				getLink(EigenschaftEnum.MU).getKosten()
			);
		
		
		// Neu setzen der Stufe
		prozessor.updateElement(getLink(EigenschaftEnum.MU), 16, null, null);
		
		assertEquals( // Max Wert: 14 + 2 = 16
				16,
				prozessor.getMaxWert(getLink(EigenschaftEnum.MU))
		);
		assertEquals( // Max Wert: 8 + 2 = 10
				10,
				prozessor.getMinWert(getLink(EigenschaftEnum.MU))
		);
		assertEquals( // Gesamtstufe
				16,
				getLink(EigenschaftEnum.MU).getWert()
			);
		assertEquals( // User Gew�hhlt
				14,
				getLink(EigenschaftEnum.MU).getUserLink().getWert()
			);
		assertEquals( // Modis
				2,
				getLink(EigenschaftEnum.MU).getWertModis()
			);
		assertEquals( // Kosten
				14,
				getLink(EigenschaftEnum.MU).getKosten()
			);
	}
	
	/*
	 * Testet mehrer Operationen auf der Eigenschaft Klugheit
	 * (Setzen der Eigenschaft, Kosten, Modis durch 2x Herkunft)
	 */
	public void testKL() {
		// Kultur setzen
		link = new IdLink(k, null);
		link.setWert(2);
		link.setZielId(ProgAdmin.data.getCharElement(EigenschaftEnum.KL.getId()));
		// Profession setzen
		link2 = new IdLink(p, null);
		link2.setWert(2);
		link2.setZielId(ProgAdmin.data.getCharElement(EigenschaftEnum.KL.getId()));
		
		prozessor.updateElement(getLink(EigenschaftEnum.KL), 10, null, null);
		prozessor.addLinkToElement(link);
		prozessor.addLinkToElement(link2);
		
		assertEquals( // Max Wert: 14 + 2 + 2 = 18
				18,
				prozessor.getMaxWert(getLink(EigenschaftEnum.KL))
		);
		assertEquals( // Min Wert: 8 + 2 + 2 = 12
				12,
				prozessor.getMinWert(getLink(EigenschaftEnum.KL))
		);
		assertEquals( // Gesamtstufe (12 ist der kleinste m�gliche Wert)
				12,
				getLink(EigenschaftEnum.KL).getWert()
			);
		assertEquals( // User Gew�hlt (Automatisch auf 8 gesetzt)
				8,
				getLink(EigenschaftEnum.KL).getUserLink().getWert()
			);
		assertEquals( // Modis (Kultur 2 / Profession 2)
				4,
				getLink(EigenschaftEnum.KL).getWertModis()
			);
		assertEquals( // Kosten = Gew�hlte Punkte
				8,
				getLink(EigenschaftEnum.KL).getKosten()
			);
		
		//Entfernen eines Modis
		prozessor.removeLinkFromElement(link2);
		
		assertEquals( // Max Wert: 14 + 2 = 16
				16,
				prozessor.getMaxWert(getLink(EigenschaftEnum.KL))
		);
		assertEquals( // Min Wert: 8 + 2 = 10
				10,
				prozessor.getMinWert(getLink(EigenschaftEnum.KL))
		);
		assertEquals( // Gesamtstufe 
				10,
				getLink(EigenschaftEnum.KL).getWert()
			);
		assertEquals( // User Gew�hlt (Automatisch auf 8 gesetzt)
				8,
				getLink(EigenschaftEnum.KL).getUserLink().getWert()
			);
		assertEquals( // Modis (Kultur 2 / Profession 2)
				2,
				getLink(EigenschaftEnum.KL).getWertModis()
			);
		assertEquals( // Kosten = Gew�hlte Punkte
				8,
				getLink(EigenschaftEnum.KL).getKosten()
			);
		
		// Wieder hinzuf�gen des Modis
		prozessor.addLinkToElement(link2);
		
		assertEquals( // Max Wert: 14 + 2 + 2 = 18
				18,
				prozessor.getMaxWert(getLink(EigenschaftEnum.KL))
		);
		assertEquals( // Min Wert: 8 + 2 + 2 = 12
				12,
				prozessor.getMinWert(getLink(EigenschaftEnum.KL))
		);
		assertEquals( // Gesamtstufe (12 ist der kleinste m�gliche Wert)
				12,
				getLink(EigenschaftEnum.KL).getWert()
			);
		assertEquals( // User Gew�hlt (Automatisch auf 8 gesetzt)
				8,
				getLink(EigenschaftEnum.KL).getUserLink().getWert()
			);
		assertEquals( // Modis (Kultur 2 / Profession 2)
				4,
				getLink(EigenschaftEnum.KL).getWertModis()
			);
		assertEquals( // Kosten = Gew�hlte Punkte
				8,
				getLink(EigenschaftEnum.KL).getKosten()
			);
	}		
	
	/*
	 * Testet mehrer Operationen auf der Berechneten Eigenschaft Lep
	 * (Setzen der Eigenschaft, Kosten, Modis)
	 */
	public void testLEP() {
		// Setzen eines Modis durch eine Kultur
		link = new IdLink(k, null);
		link.setWert(4);
		link.setZielId(ProgAdmin.data.getCharElement(EigenschaftEnum.LEP.getId()));

		
		// Setzen der Grund-Werte f�r die Berechnung und des Modis
		prozessor.updateElement(getLink(EigenschaftEnum.KO), 10, null, null);
		prozessor.updateElement(getLink(EigenschaftEnum.KK), 11, null, null);
		prozessor.updateElement(getLink(EigenschaftEnum.LEP), 7, null, null); // = gekauft 3
		
		prozessor.addLinkToElement(link);
		
		assertEquals( //(10 + 10 + 11) / 2 = 16 + 4 + 3 = 23
				23,
				prozessor.getHeld().getEigenschaftsWert(EigenschaftEnum.LEP)
			);
		assertEquals( // User Gew�hlt
				3,
				getLink(EigenschaftEnum.LEP).getUserLink().getWert()
			);
		assertEquals( // Modis
				4,
				getLink(EigenschaftEnum.LEP).getWertModis()
			);
		assertEquals( // Kosten ( 3 Punkt auf Spalte H )
				111,
				getLink(EigenschaftEnum.LEP).getKosten()
			);
	}
	
	/*
	 * Testet mehrer Operationen auf der Berechneten Eigenschaft Asp
	 * (Setzen der Eigenschaft, Kosten, Modis)
	 */
	public void testASP() {
		// Setzen eines Modis durch Kultur
		link = new IdLink(k, null);
		link.setWert(1);
		link.setZielId(ProgAdmin.data.getCharElement(EigenschaftEnum.ASP.getId()));
		// Setzen eines Modis durch Profession
		link2 = new IdLink(p, null);
		link2.setWert(2);
		link2.setZielId(ProgAdmin.data.getCharElement(EigenschaftEnum.ASP.getId()));
		
		// Setzen der Grund-Werte f�r die Berechnung und des Modis
		prozessor.updateElement(getLink(EigenschaftEnum.MU), 10, null, null);
		prozessor.updateElement(getLink(EigenschaftEnum.IN), 11, null, null);
		prozessor.updateElement(getLink(EigenschaftEnum.CH), 11, null, null);
		prozessor.updateElement(getLink(EigenschaftEnum.ASP), 5, null, null); // = gekauft 2

		prozessor.addLinkToElement(link);
		prozessor.addLinkToElement(link2);
		
		assertEquals( // Max: (10 + 11 + 11) / 2 = 16 + 3 + 17 = 36
				36,
				prozessor.getMaxWert(getLink(EigenschaftEnum.ASP))
		);
		assertEquals( // Min: (10 + 11 + 11) / 2 = 16 + 3 = 19
				19,
				prozessor.getMinWert(getLink(EigenschaftEnum.ASP))
		);
		assertEquals( //Stufe: (10 + 11 + 11) / 2 = 16 + 3 + 2 = 21
				21,
				prozessor.getHeld().getEigenschaftsWert(EigenschaftEnum.ASP)
			);
		assertEquals( // User Gew�hlt
				2,
				getLink(EigenschaftEnum.ASP).getUserLink().getWert()
			);
		assertEquals( // Modis
				3,
				getLink(EigenschaftEnum.ASP).getWertModis()
			);
		assertEquals( // Kosten ( 3 Punkt auf Spalte G )
				26,
				getLink(EigenschaftEnum.ASP).getKosten()
			);
		
		// Entfernen eines Modis:
		prozessor.removeLinkFromElement(link2);
		
		assertEquals( // Max: (10 + 11 + 11) / 2 = 16 + 1 + 17 = 34
				34,
				prozessor.getMaxWert(getLink(EigenschaftEnum.ASP))
		);
		assertEquals( // Min: (10 + 11 + 11) / 2 = 16 + 1 = 17
				17,
				prozessor.getMinWert(getLink(EigenschaftEnum.ASP))
		);
		assertEquals( //Stufe: (10 + 11 + 11) / 2 = 16 + 1 + 2 = 19
				19,
				prozessor.getHeld().getEigenschaftsWert(EigenschaftEnum.ASP)
			);
		assertEquals( // User Gew�hlt
				2,
				getLink(EigenschaftEnum.ASP).getUserLink().getWert()
			);
		assertEquals( // Modis
				1,
				getLink(EigenschaftEnum.ASP).getWertModis()
			);
		assertEquals( // Kosten ( 3 Punkt auf Spalte G )
				26,
				getLink(EigenschaftEnum.ASP).getKosten()
			);
		
		// Modi wieder hinzuf�gen
		prozessor.addLinkToElement(link2);
		
		assertEquals( // Max: (10 + 11 + 11) / 2 = 16 + 3 + 17 = 36
				36,
				prozessor.getMaxWert(getLink(EigenschaftEnum.ASP))
		);
		assertEquals( // Min: (10 + 11 + 11) / 2 = 16 + 3 = 19
				19,
				prozessor.getMinWert(getLink(EigenschaftEnum.ASP))
		);
		assertEquals( // Stufe: (10 + 11 + 11) / 2 = 16 + 3 + 2 = 21 --> Wurde gesenkt
				19,
				prozessor.getHeld().getEigenschaftsWert(EigenschaftEnum.ASP)
			);
		assertEquals( // User Gew�hlt
				0,
				getLink(EigenschaftEnum.ASP).getUserLink().getWert()
			);
		assertEquals( // Modis
				3,
				getLink(EigenschaftEnum.ASP).getWertModis()
			);
		assertEquals( // Kosten ( 0 Punkt auf Spalte G )
				0,
				getLink(EigenschaftEnum.ASP).getKosten()
			);
	}

	public void testNegativ() {
		// Kultur setzen
		link = new IdLink(k, null);
		link.setWert(-2);
		link.setZielId(ProgAdmin.data.getCharElement(EigenschaftEnum.MU.getId()));
		
		prozessor.updateElement(getLink(EigenschaftEnum.MU), 8, null, null);
		prozessor.addLinkToElement(link);
		
		assertEquals( // Max Wert: 14 - 2 = 12
				12,
				prozessor.getMaxWert(getLink(EigenschaftEnum.MU))
		);
		assertEquals( // Min Wert: 8 + 2 = 10
				6,
				prozessor.getMinWert(getLink(EigenschaftEnum.MU))
		);
		assertEquals( // Gesamtstufe (da versucht wird die Stufe zu halten)
				8,
				getLink(EigenschaftEnum.MU).getWert()
			);
		assertEquals( // User Gew�hhlt (da versucht wird die Stufe zu halten)
				10, 
				getLink(EigenschaftEnum.MU).getUserLink().getWert()
			);
		assertEquals( // Modis
				-2,
				getLink(EigenschaftEnum.MU).getWertModis()
			);
		assertEquals( // Kosten (da versucht wird die Stufe zu halten)
				10,
				getLink(EigenschaftEnum.MU).getKosten()
			);
		
		
	}
}
