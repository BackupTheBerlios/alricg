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
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.held.GeneratorLink;
import org.d3s.alricg.held.Held;
import org.d3s.alricg.prozessor.HeldProzessor;

public class EigenschaftBoxGenTest extends TestCase {
	Held held;
	HeldProzessor prozessor;
	
	/* (non-Javadoc) Methode überschrieben
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		ProgAdmin.heldenAdmin.initHeldGenerierung();
		held = ProgAdmin.heldenAdmin.getActiveHeld();
		prozessor = ProgAdmin.heldenAdmin.getActiveProzessor();
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		held = null;
		prozessor = null;
	}

	/**
	 * Liefert den Link zu der Eigenschaft zurück
	 * @param enu Die gerwünschte Eigenschaft
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
	 * Testet das Setzen der Eigenschaften und die Berechnung der Kosten!
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
		
		// "Nicht Berechnete" Werte überprüfen
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
		
		// Errechnete Werte überprüfen: 
		assertEquals( // (10 + 10 + 11) / 2 = 16 + 5 = 21 
				21,
				held.getEigenschaftsWert(EigenschaftEnum.LEP)
			);
		assertEquals( // (9 + 12 + 14) / 2 = 18 + 2 = 20
				20,
				held.getEigenschaftsWert(EigenschaftEnum.ASP)
			);
		
		
		// Überprüfen ob die Kosten richtig sind:
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
		assertEquals( // Über SKT - Nur die gekauften Punkte
				306,
				getLink(EigenschaftEnum.LEP).getKosten()
			);
		assertEquals( // Über SKT - Nur die gekauften Punkte
				26,
				getLink(EigenschaftEnum.ASP).getKosten()
			);
		
		
		assertEquals( // Sollte nix kosten!
				0,
				getLink(EigenschaftEnum.MR).getKosten()
			);
		
		//getLink(EigenschaftEnum.KO).getKosten()
	}

	/*
	 * Test method for 'org.d3s.alricg.prozessor.generierung.EigenschaftBoxGen.addLinkToElement(IdLink, boolean)'
	 */
	public void testAddLinkToElement() {
		Kultur k = new Kultur("KUL-jUnit-test-eigen");
		
		IdLink link = new IdLink(k, null);
		link.setWert(2);
		link.setZielId(ProgAdmin.data.getCharElement(EigenschaftEnum.MU.getId()));
		
		prozessor.updateElement(getLink(EigenschaftEnum.MU), 10, null, null);
		prozessor.addLinkToElement(link);
		
		assertEquals( // Gesamtstufe
				12,
				getLink(EigenschaftEnum.MU).getKosten()
			);
		assertEquals( // User Gewähhlt
				10,
				getLink(EigenschaftEnum.MU).getUserLink().getWert()
			);
		assertEquals( // Modis
				2,
				getLink(EigenschaftEnum.MU).getWertModis()
			);
		assertEquals( // Kosten
				10,
				getLink(EigenschaftEnum.MU).getKosten()
			);
		
	}

	/*
	 * Test method for 'org.d3s.alricg.prozessor.generierung.EigenschaftBoxGen.getMaxWert(Link)'
	 */
	public void testGetMaxWert() {

	}

	/*
	 * Test method for 'org.d3s.alricg.prozessor.generierung.EigenschaftBoxGen.getMinWert(Link)'
	 */
	public void testGetMinWert() {

	}

}
