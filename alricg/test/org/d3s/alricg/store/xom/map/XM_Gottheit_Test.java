/*
 * Created on 11.10.2005 / 13:36:19
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://www.alricg.de/".
 */
package org.d3s.alricg.store.xom.map;

import junit.framework.TestCase;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Gottheit;
import org.d3s.alricg.charKomponenten.Gottheit.GottheitArt;
import org.d3s.alricg.charKomponenten.Gottheit.KenntnisArt;

public class XM_Gottheit_Test extends TestCase {

	private XOMMapper<CharElement> mappy;

	public XM_Gottheit_Test(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mappy = new XOMMapper_Gottheit();
	}

	public void testMapFromXML() {
		try {
			final Element xom = new Element("gabe");
			final String name = "GOT-Namenlos";

			// Kenntnisart [liturgie, ritual]
			String[] kenntnisArt = { "liTurGie", "RituaL" };
			Element e0 = new Element("kenntnisArt");
			e0.appendChild(kenntnisArt[0]);
			xom.appendChild(e0);

			// Gottheitart
			String[] gottheitArt = { "nichtAlveraniSCH", "ZWOelfGoettlich",
					"ANIMIstisch" };
			Element e1 = new Element("gottheitArt");
			e1.appendChild(gottheitArt[0]);
			xom.appendChild(e1);

			Gottheit g = new Gottheit(name);
			mappy.map(xom, g);
			assertEquals("Kenntnisart falsch", KenntnisArt.liturgie, g
					.getKenntnisArt());
			assertEquals("Gottheitart falsch", GottheitArt.nichtAlveranisch, g
					.getGottheitArt());

			e0.removeChild(0);
			e1.removeChild(0);
			e0.appendChild(kenntnisArt[1]);
			e1.appendChild(gottheitArt[1]);
			g = new Gottheit(name);
			mappy.map(xom, g);
			assertEquals("Kenntnisart falsch", KenntnisArt.ritual, g
					.getKenntnisArt());
			assertEquals("Gottheitart falsch", GottheitArt.zwoelfGoettlich, g
					.getGottheitArt());

			e1.removeChild(0);
			e1.appendChild(gottheitArt[2]);
			g = new Gottheit(name);
			mappy.map(xom, g);
			assertEquals("Kenntnisart falsch", KenntnisArt.ritual, g
					.getKenntnisArt());
			assertEquals("Gottheitart falsch", GottheitArt.animistisch, g
					.getGottheitArt());

		} catch (Exception e) {
			fail("No exception expected!");
		}
	}

	public void testMapToXML() {
		fail("Not implemented yet!");
	}

}
