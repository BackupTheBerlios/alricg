/*
 * Created on 01.05.2005 / 00:58:58
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.prozessor.generierung;

import java.util.ArrayList;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.held.GeneratorLink;
import org.d3s.alricg.held.HeldenLink;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.LinkElementBox;

/**
 * <u>Beschreibung:</u><br> 
 * AbstractBoxGen bildet die Grundlage f�r die Kapselung und Verwalten bestimmte Arten 
 * von CharElementen in der Form von Links f�r die Generierung von Helden. So verwaltet
 * z.B. die "TalentBox" alle Talente und bietet Operationen an um die Elemente zu ver�ndern.
 * 
 * Die hier spezifizierten Methoden geben an wie auf spezielle Boxen zugeriffen werden kann.
 * Diese Methoden sind nicht von au�erhalb des packages aufzurufen, sondern nur indirekt �ber 
 * den "GenerierungProzessor".  So soll ein "ordenlicher" Ablauf ohne 
 * querschlagende Zugriffe realisiert werden. 
 * 
 * Die "LinkElementBox" Spezifiziert wie von au�en auf Boxen zugegriffen werden kann,
 * auf die inneren Operationen (die in den Spezialisierten Klassen defniert sind) mit 
 * denen die Box ver�ndert werden kann, kann nicht zugeriffen werden.
 * 
 * (Bisherige Implementierungen sind "TalentBoxGen")
 * 
 * @author V. Strelow
 */
public abstract class AbstractBoxGen extends LinkElementBox<GeneratorLink> {
	
	/**
	 * Konstruktor.
	 * @param proz Der Prozessor mit dem der zugeh�rige Held bearbeitet wird.
	 */
	public AbstractBoxGen(HeldProzessor proz) {
		super(new ArrayList<GeneratorLink>(), proz);
	}


	/**
	 * Erstellt aus einem Link ein neues Element(=GeneratorLink) und f�gt diese der Box hinzu.
	 * Es wird keinerlei Pr�fung durchgef�hrt.
	 * Bsp.: - ein Talent wird durch Herkunft hinzugef�gt und war zuvor nicht vorhanden
	 * 		 - der Benutzer f�gt ein Talent zu Helden hinzu, dass zuvor nicht vorhanden war.
	 * 
	 * @param link Der Link der hinzugef�gt wird
	 * @return Das neu erzeugte Element in Form eines HeldenLinks oder GeneratorLinks
	 */
	protected abstract GeneratorLink addAsNewElement(IdLink link);

	
	/**
	 * Pr�ft ob dieser Link als neues Element zum Helden hinzugef�gt werden kann. 
	 * @param link Der Link, der gepr�ft wird
	 * @return true - Der Link kann als neues Element zum Helden hinzugef�gt werden 
	 */
	protected abstract boolean canAddAsNewElement(IdLink link);
	/* [NOT durch Herkunft] Ist das Element �berhaupt W�hlbar (vgl. "Nat�rlicher RS")
	 * [NOT durch Herkunft] Pr�fen ob Magie/Geweiht n�tig ist
	 * [NOT durch Herkunft] Link(!) Bereits vorhanden 
	 * Pr�fen ob mit bereis zum Helden geh�renden Elementen vereinbar
	 * [Talent OR Zauber] Noch Aktivierbar 
	 * [NOT Generierung] Noch gen�gend AP �brig
	 * Ist der Wert zu l�ssig (Min/Max Werte)
	 * 
	 * Sonderregeln werde von Prozessor aufgerufen
	 */ 
	
	/**
	 * Pr�ft ob ein CharElement grunds�tzlich zum Helden hinzugef�gt werden kann.
	 * Es werden Dinge �berpr�ft wie: Voraussetzungen erf�llt, Vereinbar mit
	 * anderen Elementen. Es werden KEINE Kosten �berpr�ft oder Dinge wie
	 * Stufe und zus�tzliche angaben
	 * 
	 * @param elem Das CharElement das �berpr�ft wird
	 */
	protected abstract boolean canAddCharElement(CharElement elem);
	
	/**
	 * Pr�ft ob ein Element vom Helden entfernd werden kann.
	 * @param link Der Link des Elements, das entfernt werden soll
	 */
	protected abstract boolean canRemoveElement(HeldenLink link);
	/* Ist das Element durch Herkunft an den helden gebunden?
	 * Ist das Element Voraussetzung f�r andere Elemente des Helden?
	 */
	
	/**
	 * F�gt einen Link zu einem bereits bestehenden Element (=GeneratorLink) hinzu.
	 * Dabei wird sichergestellt das das resultierende Element g�ltig bleibt, hierf�r
	 * wird der Wert des Link evtl. ge�ndert. 
	 * Diese Methode wird benutzt beim Hinzuf�gen von einer Herkunft (Rasse, Kultur, Prof.), 
	 * oder beim �ndern durch Sonderregeln.
	 * 
	 * 	Bsp.: - Ein Talent bekommt durch Kultur +2, hatte aber schon durch Rasse +1 und
	 * 			war damit bereits vohanden. 
	 * 
	 * @param link Der Link der zum bestehenden Element hinzugef�gt wird.
	 * @param stufeErhalten true - Es soll versucht werden die alte Stufe beizubehalten
	 * 				false - Die Neue Stufe mu� nur innerhalb der Grenzen bleiben.
	 */
	protected abstract GeneratorLink addLinkToElement(IdLink link, boolean stufeErhalten);
	/* ACHTUNG Hier mu� sichergestellt werden, dass der Held regel-konform bleibt!
	 * TODO Es sind mehrer M�glichkeiten denkbar, wie mit einer Stufenver�nderung 
	 *  ungegangen werden soll. "stufeErhalten" ist nicht optimal, sollte verbessert werden!
	 */
	
	/**
	 * Bestimmt von einem Element den maximalen Wert (Stufe).
	 * @param link Der Link zu dem Element desen maximaler Wert gefragt ist
	 * @return Der resultierende maximale Wert (Stufe) oder KEIN_WERT wenn ein keinen Wert gibt
	 */
	protected abstract int getMaxWert(Link link);
	
	/**
	 * Bestimmt von einem Element den minimalen Wert (Stufe).
	 * @param link Der Link zu dem Element, desen minimaler Wert gefragt ist
	 * @return Der resultierende minimale Wert oder KEIN_WERT wenn es keinen Wert gibt
	 */
	protected abstract int getMinWert(Link link);
	
	/**
	 * Ein Element des Helden wird (durch den User) ge�ndert. Es wird hierbei keine
	 * Pr�fung durchgef�hrt.
	 * @param link Link des Elements, das ge�ndert werden soll
	 * @param stufe Die neue Stufe oder "KEIN_WERT", wenn die Stufe nicht ge�ndert wird
	 * @param text Der neue Text oder 'null', wenn der Text nicht ge�ndert wird
	 * 		(text ist z.B. bei "Vorurteil gegen Orks" der String "Orks")
	 * @param zweitZiel Das neue zweitZiel oder 'null', wenn dies nicht ge�ndert wird
	 * 		(ZweitZiel ist z.B. bei "Unf�higkeit f�r Schwerter" das Talent "Schwerter")
	 */

	protected abstract void updateElement(HeldenLink link, int stufe, 
										String text, CharElement zweitZiel);
	
	/**
	 * Wird aufgerufen um zu �berpr�fen ob der Wert eines Elements ge�ndert werden darf.
	 * (Wert ist z.B. bei "Schwerter 6" die 6)
	 * Es geht dabei NICHT um den Wert der �nderung (diese Grenzen werden mit 
	 * "getMaxWert" / "getMinWert" festgelegt) sondern nur ob es �nderung grunds�tzlich
	 * m�glich ist!
	 * @param link Link des Elements, dass gepr�ft werden soll
	 */
	protected abstract boolean canUpdateWert(HeldenLink link);
	
	/**
	 * Wird aufgerufen um zu �berpr�fen ob der Text eines Elements ge�ndert werden darf.
	 * (text ist z.B. bei "Vorurteil gegen Orks" der String "Orks")
	 * @param link Link des Elements, dass gepr�ft werden soll
	 */
	protected abstract boolean canUpdateText(HeldenLink link);
	
	/**
	 * Wird aufgerufen um zu �berpr�fen ob das ZweitZiel eines Elements ge�ndert werden darf.
	 * (ZweitZiel ist z.B. bei "Unf�higkeit f�r Schwerter" das Talent "Schwerter")
	 * @param link Link des Elements, dass gepr�ft werden soll
	 */
	protected abstract boolean canUpdateZweitZiel(HeldenLink link);
	
	/**
	 * Diese Methode wird normalerweise automatisch von anderen Methoden aufgerufen.
	 * Der Sinn f�r ein Seperates aufrufen liegt vor allem darin, die bei der 
	 * berechnung entstehenden Meldungen zu erzeugen. (z.B. f�r ToolTips)
	 * 
	 * Berechnet die Kosten die f�r dieses Element aufgewendet werden m�ssen neu.
	 * @param link Der Link zu dem Element, f�r das die Kosten berechnet werden
	 */
	protected abstract void updateKosten(GeneratorLink genLink);
	
}
