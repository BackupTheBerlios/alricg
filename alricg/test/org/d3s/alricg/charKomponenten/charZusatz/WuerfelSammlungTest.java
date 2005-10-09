/*
 * Created on 09.10.2005 / 11:20:04
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */
package org.d3s.alricg.charKomponenten.charZusatz;

import junit.framework.TestCase;

/**
 * <b>Beschreibung:</b>
 * JUnit-Test f�r die Klasse "WuerfelSammlung"
 * 
 * @author V.Strelow
 */
public class WuerfelSammlungTest extends TestCase {
	private WuerfelSammlung w0;
	private WuerfelSammlung w1;
	private WuerfelSammlung w2;
	private WuerfelSammlung w3;
 	
	protected void setUp() throws Exception {
		super.setUp();
		
		//W�rfel mit 0 + 1W6
		w0 = new WuerfelSammlung(0, new int[] {1}, new int[] {6});
		
		// W�rfel mit 2 + 2W6 + 2W20
		w1 = new WuerfelSammlung(2, new int[] {2,2}, new int[] {6,20});
		
		// W�rfel mit 2 + 3W3 + 3W4
		w2 = new WuerfelSammlung(2, new int[] {3,3}, new int[] {3,4});
		
		// W�rfel mit 2 + 1W100 + 3W10
		w3 = new WuerfelSammlung(2, new int[] {1,3}, new int[] {100,4});
		
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Testet den maximal erreichbaren wert einer W�rfelsammlung
	 */
	public void textMaxWert() {
		assertEquals(6, w0.getMaxWurf());
		assertEquals(34, w1.getMinWurf());
		assertEquals(23, w2.getMinWurf());
		assertEquals(132, w3.getMinWurf());
	}
	
	/**
	 * Testet den minimal erreichbaren wert einer W�rfelsammlung
	 */
	public void textMinWert() {
		assertEquals(0, w0.getMinWurf());
		assertEquals(6, w1.getMinWurf());
		assertEquals(8, w2.getMinWurf());
		assertEquals(6, w3.getMinWurf());
	}
	
	/**
	 * Testet ob die Zufallszahlen auch in den Grenzen liegen
	 */
	public void testWuerfelwurf() {
		
		assertTrue(w0.getMinWurf() < w0.getWuerfelWurf()
				&& w0.getWuerfelWurf() < w0.getMinWurf());
		assertTrue(w1.getMinWurf() < w1.getWuerfelWurf()
				&& w1.getWuerfelWurf() < w1.getMinWurf());
		assertTrue(w2.getMinWurf() < w2.getWuerfelWurf()
				&& w2.getWuerfelWurf() < w2.getMinWurf());
		assertTrue(w3.getMinWurf() < w3.getWuerfelWurf()
				&& w3.getWuerfelWurf() < w3.getMinWurf());
	}
}