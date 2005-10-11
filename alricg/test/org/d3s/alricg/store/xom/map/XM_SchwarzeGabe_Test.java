/*
 * Created on 11.10.2005 / 13:36:19
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://www.alricg.de/".
 */
package org.d3s.alricg.store.xom.map;

import junit.framework.TestCase;
import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.charZusatz.SchwarzeGabe;

public class XM_SchwarzeGabe_Test extends TestCase {

	private XOMMapper mappy;

	public XM_SchwarzeGabe_Test(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mappy = new XOMMapper_SchwarzeGabe();
	}

	public void testMapFromXML() {
		try {
		final Element xom = new Element("gabe");
		final String name = "SGA-Schuppenechse";

		// koten int
		int kosten = 3445;
		xom.addAttribute(new Attribute("kosten", "XX.XX"));

		SchwarzeGabe s = new SchwarzeGabe(name);
		mappy.map(xom, s);
		assertEquals("Kosten falsch", CharElement.KEIN_WERT, s.getKosten());
		assertEquals("MinStufe falsch", CharElement.KEIN_WERT, s.getMinStufe());
		assertEquals("Max Stufe falsch", CharElement.KEIN_WERT, s.getMaxStufe());

		xom.getAttribute("kosten").setValue("" + kosten);
		s = new SchwarzeGabe(name);
		mappy.map(xom, s);
		assertEquals("Kosten falsch", kosten, s.getKosten());
		assertEquals("MinStufe falsch", CharElement.KEIN_WERT, s.getMinStufe());
		assertEquals("Max Stufe falsch", CharElement.KEIN_WERT, s.getMaxStufe());

		// minstufe int
		// maxstufe int
		int minstufe = 3;
		int maxstufe = 13;
		final Element stufengrenzen = new Element("stufenGrenzen");
		stufengrenzen.addAttribute(new Attribute("minStufe", "" + minstufe));
		stufengrenzen.addAttribute(new Attribute("maxStufe", "" + maxstufe));
		xom.appendChild(stufengrenzen);

		s = new SchwarzeGabe(name);
		mappy.map(xom, s);
		assertEquals("Kosten falsch", kosten, s.getKosten());
		assertEquals("MinStufe falsch", minstufe, s.getMinStufe());
		assertEquals("Max Stufe falsch", maxstufe, s.getMaxStufe());
		} catch(Exception e) {
			fail("No exception expected!");
		}
	}

	public void testMapToXML() {
		fail("Not implemented yet!");
	}

}
