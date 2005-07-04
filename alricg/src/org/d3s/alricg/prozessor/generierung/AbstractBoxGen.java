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
 * AbstractBoxGen bildet die Grundlage für die Kapselung und Verwalten bestimmte Arten 
 * von CharElementen in der Form von Links für die Generierung von Helden. So verwaltet
 * z.B. die "TalentBox" alle Talente und bietet Operationen an um die Elemente zu verändern.
 * 
 * Die hier spezifizierten Methoden geben an wie auf spezielle Boxen zugeriffen werden kann.
 * Diese Methoden sind nicht von außerhalb des packages aufzurufen, sondern nur indirekt über 
 * den "GenerierungProzessor".  So soll ein "ordenlicher" Ablauf ohne 
 * querschlagende Zugriffe realisiert werden. 
 * 
 * Die "LinkElementBox" Spezifiziert wie von außen auf Boxen zugegriffen werden kann,
 * auf die inneren Operationen (die in den Spezialisierten Klassen defniert sind) mit 
 * denen die Box verändert werden kann, kann nicht zugeriffen werden.
 * 
 * (Bisherige Implementierungen sind "TalentBoxGen")
 * 
 * @author V. Strelow
 */
public abstract class AbstractBoxGen extends LinkElementBox<GeneratorLink> {
	
	/**
	 * Konstruktor.
	 * @param proz Der Prozessor mit dem der zugehörige Held bearbeitet wird.
	 */
	public AbstractBoxGen(HeldProzessor proz) {
		super(new ArrayList<GeneratorLink>(), proz);
	}


	/**
	 * Erstellt aus einem Link ein neues Element(=GeneratorLink) und fügt diese der Box hinzu.
	 * Es wird keinerlei Prüfung durchgeführt.
	 * Bsp.: - ein Talent wird durch Herkunft hinzugefügt und war zuvor nicht vorhanden
	 * 		 - der Benutzer fügt ein Talent zu Helden hinzu, dass zuvor nicht vorhanden war.
	 * 
	 * @param link Der Link der hinzugefügt wird
	 * @return Das neu erzeugte Element in Form eines HeldenLinks oder GeneratorLinks
	 */
	protected abstract GeneratorLink addAsNewElement(IdLink link);

	
	/**
	 * Prüft ob dieser Link als neues Element zum Helden hinzugefügt werden kann. 
	 * @param link Der Link, der geprüft wird
	 * @return true - Der Link kann als neues Element zum Helden hinzugefügt werden 
	 */
	protected abstract boolean canAddAsNewElement(IdLink link);
	/* [NOT durch Herkunft] Ist das Element überhaupt Wählbar (vgl. "Natürlicher RS")
	 * [NOT durch Herkunft] Prüfen ob Magie/Geweiht nötig ist
	 * [NOT durch Herkunft] Link(!) Bereits vorhanden 
	 * Prüfen ob mit bereis zum Helden gehörenden Elementen vereinbar
	 * [Talent OR Zauber] Noch Aktivierbar 
	 * [NOT Generierung] Noch genügend AP übrig
	 * Ist der Wert zu lässig (Min/Max Werte)
	 * 
	 * Sonderregeln werde von Prozessor aufgerufen
	 */ 
	
	/**
	 * Prüft ob ein CharElement grundsätzlich zum Helden hinzugefügt werden kann.
	 * Es werden Dinge überprüft wie: Voraussetzungen erfüllt, Vereinbar mit
	 * anderen Elementen. Es werden KEINE Kosten überprüft oder Dinge wie
	 * Stufe und zusätzliche angaben
	 * 
	 * @param elem Das CharElement das überprüft wird
	 */
	protected abstract boolean canAddCharElement(CharElement elem);
	
	/**
	 * Prüft ob ein Element vom Helden entfernd werden kann.
	 * @param link Der Link des Elements, das entfernt werden soll
	 */
	protected abstract boolean canRemoveElement(HeldenLink link);
	/* Ist das Element durch Herkunft an den helden gebunden?
	 * Ist das Element Voraussetzung für andere Elemente des Helden?
	 */
	
	/**
	 * Fügt einen Link zu einem bereits bestehenden Element (=GeneratorLink) hinzu.
	 * Dabei wird sichergestellt das das resultierende Element gültig bleibt, hierfür
	 * wird der Wert des Link evtl. geändert. 
	 * Diese Methode wird benutzt beim Hinzufügen von einer Herkunft (Rasse, Kultur, Prof.), 
	 * oder beim ändern durch Sonderregeln.
	 * 
	 * 	Bsp.: - Ein Talent bekommt durch Kultur +2, hatte aber schon durch Rasse +1 und
	 * 			war damit bereits vohanden. 
	 * 
	 * @param link Der Link der zum bestehenden Element hinzugefügt wird.
	 * @param stufeErhalten true - Es soll versucht werden die alte Stufe beizubehalten
	 * 				false - Die Neue Stufe muß nur innerhalb der Grenzen bleiben.
	 */
	protected abstract GeneratorLink addLinkToElement(IdLink link, boolean stufeErhalten);
	/* ACHTUNG Hier muß sichergestellt werden, dass der Held regel-konform bleibt!
	 * TODO Es sind mehrer Möglichkeiten denkbar, wie mit einer Stufenveränderung 
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
	 * Ein Element des Helden wird (durch den User) geändert. Es wird hierbei keine
	 * Prüfung durchgeführt.
	 * @param link Link des Elements, das geändert werden soll
	 * @param stufe Die neue Stufe oder "KEIN_WERT", wenn die Stufe nicht geändert wird
	 * @param text Der neue Text oder 'null', wenn der Text nicht geändert wird
	 * 		(text ist z.B. bei "Vorurteil gegen Orks" der String "Orks")
	 * @param zweitZiel Das neue zweitZiel oder 'null', wenn dies nicht geändert wird
	 * 		(ZweitZiel ist z.B. bei "Unfähigkeit für Schwerter" das Talent "Schwerter")
	 */

	protected abstract void updateElement(HeldenLink link, int stufe, 
										String text, CharElement zweitZiel);
	
	/**
	 * Wird aufgerufen um zu überprüfen ob der Wert eines Elements geändert werden darf.
	 * (Wert ist z.B. bei "Schwerter 6" die 6)
	 * Es geht dabei NICHT um den Wert der Änderung (diese Grenzen werden mit 
	 * "getMaxWert" / "getMinWert" festgelegt) sondern nur ob es Änderung grundsätzlich
	 * möglich ist!
	 * @param link Link des Elements, dass geprüft werden soll
	 */
	protected abstract boolean canUpdateWert(HeldenLink link);
	
	/**
	 * Wird aufgerufen um zu überprüfen ob der Text eines Elements geändert werden darf.
	 * (text ist z.B. bei "Vorurteil gegen Orks" der String "Orks")
	 * @param link Link des Elements, dass geprüft werden soll
	 */
	protected abstract boolean canUpdateText(HeldenLink link);
	
	/**
	 * Wird aufgerufen um zu überprüfen ob das ZweitZiel eines Elements geändert werden darf.
	 * (ZweitZiel ist z.B. bei "Unfähigkeit für Schwerter" das Talent "Schwerter")
	 * @param link Link des Elements, dass geprüft werden soll
	 */
	protected abstract boolean canUpdateZweitZiel(HeldenLink link);
	
	/**
	 * Diese Methode wird normalerweise automatisch von anderen Methoden aufgerufen.
	 * Der Sinn für ein Seperates aufrufen liegt vor allem darin, die bei der 
	 * berechnung entstehenden Meldungen zu erzeugen. (z.B. für ToolTips)
	 * 
	 * Berechnet die Kosten die für dieses Element aufgewendet werden müssen neu.
	 * @param link Der Link zu dem Element, für das die Kosten berechnet werden
	 */
	protected abstract void updateKosten(GeneratorLink genLink);
	
}
