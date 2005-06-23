/*
 * Created on 20.06.2005 / 01:31:15
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.junit.prozessor;

import junit.framework.TestCase;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.prozessor.FormelSammlung;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;
import org.d3s.alricg.prozessor.FormelSammlung.Lernmethode;

/**
 * <u>Beschreibung:</u><br> 
 * Teste die KLasse "FormelSammlung". Diese Tests beziehen sich auf die Originaldaten von
 * DSA (SKT), wenn also die Daten ge�nert werden, sind diese Tests hinf�llig. 
 * @author V. Strelow
 */
public class FormelSammlungTest extends TestCase {

	public static void main(String[] args) {
	}

	/**
	 * Teste die original SKT, indem alle Stufen bis 30 Addiert werden und mit der
	 * Summen-SKT verglichen werden 
	 */
	public void testGetSktWert() {
		int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0;
		
		for (int i = 1; i < 31; i++) {
			a += FormelSammlung.getSktWert(KostenKlasse.A, i);
			b += FormelSammlung.getSktWert(KostenKlasse.B, i);
			c += FormelSammlung.getSktWert(KostenKlasse.C, i);
			d += FormelSammlung.getSktWert(KostenKlasse.D, i);
			e += FormelSammlung.getSktWert(KostenKlasse.E, i);
			f += FormelSammlung.getSktWert(KostenKlasse.F, i);
			g += FormelSammlung.getSktWert(KostenKlasse.G, i);
			h += FormelSammlung.getSktWert(KostenKlasse.H, i);
		}
		
		assertEquals(670, a);
		assertEquals(1341, b);
		assertEquals(2000, c);
		assertEquals(2675, d);
		assertEquals(3355, e);
		assertEquals(5030, f);
		assertEquals(6713, g);
		assertEquals(13386, h);
	}

	public void testGetKostenKlasseByValue() {
		
	}

	/*
	 * Class under test for int berechneSktKosten(int, int, int, Lernmethode, KostenKlasse, boolean)
	 * Testet Arten von Berechnungen auf der SKT. 
	 */
	public void testBerechneSktKostenintintintLernmethodeKostenKlasseboolean() {
		
		// Aktivierung 
		assertEquals(
			17,
			FormelSammlung.berechneSktKosten(
				CharElement.KEIN_WERT, 0, 7, Lernmethode.lehrmeister, KostenKlasse.B, true
			)
		);
		
		// Negative Talente steigern
		assertEquals(
				34,
				FormelSammlung.berechneSktKosten(
						-3, -1, 5, Lernmethode.spezielleErfahrung, KostenKlasse.D, true
				)
			);
		
		// Negativ und positiv steigern
		assertEquals(
				97,
				FormelSammlung.berechneSktKosten(
						-3, 2, 5, Lernmethode.selbstStudium, KostenKlasse.D, true
				)
			);
		
		// Stufe �ber 31 steigern
		assertEquals(
				18386,
				FormelSammlung.berechneSktKosten(
						0, 35, 5, Lernmethode.lehrmeister, KostenKlasse.H, false
				)
			);
		
		// Talent mit selbststudium und Stufe �ber 10 --> ab 11 zus�tzlich 1 Kategorien nach oben
		assertEquals(
				845,
				FormelSammlung.berechneSktKosten(
						8, 14, 5, Lernmethode.selbstStudium, KostenKlasse.E, true
				)
			);
		
		// Aktivierung bei der Generierung (HeldenStufe = 0)
		assertEquals(
				28,
				FormelSammlung.berechneSktKosten(
						CharElement.KEIN_WERT, 2, 0, Lernmethode.lehrmeister, KostenKlasse.F, true
				)
			);
		
		// Steigern mit Kateorgie �ber H hinaus (=H)
		assertEquals(
				415,
				FormelSammlung.berechneSktKosten(
						4, 7, 15, Lernmethode.selbstStudium, KostenKlasse.H, true
				)
			);
		
		// Steigern mit Kateorgie A* 
		assertEquals(
				2,
				FormelSammlung.berechneSktKosten(
						0, 2, 15, Lernmethode.spezielleErfahrung, KostenKlasse.A, false
				)
			);
		assertEquals(
				28,
				FormelSammlung.berechneSktKosten(
						5, 9, 15, Lernmethode.spezielleErfahrung, KostenKlasse.A, false
				)
			);
		
		//	"normales" steigern 
		assertEquals(
				29,
				FormelSammlung.berechneSktKosten(
						1, 5, 15, Lernmethode.spezielleErfahrung, KostenKlasse.C, false
				)
			);
		assertEquals(
				235,
				FormelSammlung.berechneSktKosten(
						10, 13, 15, Lernmethode.sehrGuterLehrmeister, KostenKlasse.F, true
				)
			);
		assertEquals(
				144,
				FormelSammlung.berechneSktKosten(
						5, 9, 15, Lernmethode.lehrmeister, KostenKlasse.D, false
				)
			);
	}

	/*
	 * Class under test for int berechneSktKosten(int, int, KostenKlasse)
	 */
	public void testBerechneSktKostenintintKostenKlasse() {
	}

	public void testBerechneSfAp() {
	}

	public void testBerechneMR() {
	}

	public void testBerechneINI() {
	}

	public void testBerechneAtBasis() {
	}

	public void testBerechnePaBasis() {
	}

	public void testBerechneFkBasis() {
	}

	public void testBerechneLep() {
			
	}

	public void testBerechneAup() {
	}

	public void testBerechneAsp() {
		
	}

}
