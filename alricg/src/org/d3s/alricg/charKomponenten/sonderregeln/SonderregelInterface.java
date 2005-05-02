/*
 * Created on 30.04.2005 / 23:07:24
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.sonderregeln;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.held.HeldenLink;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;

/**
 * <u>Beschreibung:</u><br> 
 * Interface welches von allen Sonderfertigkeiten erfüllt werden muß.
 * Über die hier definierten Methoden kann eine Sonderregel die wichtigen abläufe 
 * bei der Helden Generierung/ Bearbeitung beeinflussen. Für jede Sonderregel existiert 
 * eine eingende Klasse die sich nur um die erfüllung dieser Sonderregel kümmert.
 * 
 * Sobald eine Sonderregel zum Helden hinzugefügt wurde, wird jede Methode dieser 
 * Sonderregel stehts bei der, in der jeweiligen Methde beschriebenen, Aktion aufgerufen. 
 * 
 * (Bisherige Implementierungen: "HerrausragendeEigenschaft"
 * 
 * @author V. Strelow
 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter
 */
public interface SonderregelInterface {
	
	/**
	 * Wird aufgerufen um festzustellen ob diese Sonderregel zum Helden
	 * hinzugefügt werden kann. (vorher werden die üblichen "standart" 
	 * Prüfungen durchgeführt)
	 * 
	 * @param prozessor Der HeldenProzessor für die Bearbeitung
	 * @param ok Das Ergebnis der "standart" Prüfung
	 * @param srLink Der Link zu dem CharElement welches diese Sonderregel enthält. 
	 * 		Bei der Sonderregel "Herausragende Eigenschaft" währe dies der Link
	 * 		mit dem Vorteil "Herausragende Eigenschaft". Aus diesem können evtl.
	 * 		Wert, Text oder zweitZiel ausgelesen werden
	 * @return
	 */
	public boolean canAddSelf(HeldProzessor prozessor, boolean ok, Link srLink);
	
	/**
	 * Wird aufgerufen wenn diese Sonderregel zum Helden hinzugefügt wurde.
	 * 
	 * @param prozessor Der HeldenProzessor für die Bearbeitung
	 * @param srLink Der Link zu dem CharElement welches diese Sonderregel enthält. 
	 * 		Bei der Sonderregel "Herausragende Eigenschaft" währe dies der Link
	 * 		mit dem Vorteil "Herausragende Eigenschaft". Aus diesem können evtl.
	 * 		Wert, Text oder zweitZiel ausgelesen werden.
	 */
	public void initSonderregel(HeldProzessor prozessor, Link srLink);
	// Evtl. Änderungen von Werten sollten hier vorgenommen werden
	
	/**
	 * Wird aufgerufen wenn eine Sonderregel wieder von einem Held entfernd wird.
	 * @param srLink Der Link zu dem CharElement welches diese Sonderregel enthält. 
	 * 		Bei der Sonderregel "Herausragende Eigenschaft" währe dies der Link
	 * 		mit dem Vorteil "Herausragende Eigenschaft". Aus diesem können evtl.
	 * 		Wert, Text oder zweitZiel ausgelesen werden.
	 */
	public void finalizeSonderregel(Link srLink);
	// Evtl. Änderungen von Werten sollten hier rückgängig gemacht werden
	
	/**
	 * Wird immer aufgerufen, wenn ein neues Element (Link) zum Helden hinzugefügt wird.
	 * @param link Der Link der neu hinzugefügt wurde
	 */
	public void processAddAsNewElement(Link link);


	/**
	 * Wird immer aufgerufen, wenn ein Element (Link) vom Helden entfernd wird
	 * @param link Der Link der entfernd wurde
	 */
	public void processRemoveElement(Link link);

	/**
	 * Wird immer aufgerufen, wenn für ein Element die Kostenklasse bestimmt wird
	 * (Also Talente, Zauber und Gaben)
	 * @param klasse Die bisher errechnete KostenKlasse
	 * @param link Der Link von dem die Kostenklasse errechnet werden soll
	 * @return Die resultierende Kostenklasse
	 */
	public KostenKlasse changeKostenKlasse(KostenKlasse klasse, Link link);

	/**
	 * Wird immer aufgerufen, wenn Kosten (GP/AP) für ein Element errechnet werden.
	 * @param kosten Die bisher errechneten Kosten (egal ob AP, GP oder Talent-GP)
	 * @param link Der Link zu dem die Kosten errechnet wurden
	 * @return Die resultierenden Kosten
	 */
	public abstract int changeKosten(int kosten, Link link);

	
	/**
	 * Wird immer aufgerufen, wenn von einem Element der maximale Wert (Stufe) bestimmt wird.
	 * @param maxWert Der bisher errechnete maximale Wert
	 * @param link Der Link zu dem Element, desen maximaler Wert gefragt ist
	 * @return Der resultierende maximale Wert (Stufe) oder KEIN_WERT wenn es keinen Wert gibt
	 */
	public int changeMaxStufe(int maxWert, Link link);

	/**
	 * Wird immer aufgerufen, wenn von einem Element der minimale Wert bestimmt wird.
	 * @param minWert Der bisher errechnete minimale Wert
	 * @param link Der Link zu dem Element, desen minimaler Wert gefragt ist
	 * @return Der resultierende minimale Wert oder KEIN_WERT wenn es keinen Wert gibt
	 */
	public int changeMinStufe(int minWert, Link link);

	/**
	 * Wird immer aufgerufen, wenn überprüft wird ob ein neues Element zum Helden
	 * hinzugefügt werden kann. 
	 * @param canAdd Der bisher berechnete Wert der Prüfung
	 * @param tmpLink Link des Elements, das hinzugefügt werden soll
	 * @return true - Der Link kann als neues Element zum Helden hinzugefügt werden, sonst false
	 */
	public boolean changeCanAddElement(boolean canAdd, Link tmpLink);
	
	/**
	 * Wird immer aufgerufen, wenn überprüft wird ob ein Element vom Helden entfernd werden
	 * kann. 
	 * @param canRemove Der bisher berechnete Wert der Prüfung
	 * @param link Link des Elements, das entfernd werden soll
	 * @return true - Das Element kann entfernd werden, sonst false
	 */
	public boolean changeCanRemoveElemet(boolean canRemove, Link link);

	/**
	 * Wird immer dann aufgerufen, wenn ein Element des Helden (durch den User) geändert
	 * wird.
	 * @param link Link des Elements, das geändert werden soll
	 * @param stufe Die neue Stufe oder "KEIN_WERT", wenn die Stufe nicht geändert wird
	 * @param text Der neue Text oder 'null', wenn der Text nicht geändert wird
	 * 		(text ist z.B. bei "Vorurteil gegen Orks" der String "Orks")
	 * @param zweitZiel Das neue zweitZiel oder 'null', wenn dies nicht geändert wird
	 * 		(ZweitZiel ist z.B. bei "Unfähigkeit für Schwerter" das Talent "Schwerter")
	 */
	public void processUpdateElement(HeldenLink link, int stufe, String text, CharElement zweitZiel);

	/**
	 * Wird aufgerufen um zu überprüfen ob der Wert eines Elements geändert werden darf.
	 * (Wert Ist z.B. bei "Schwerter 6" die 6)
	 * Es geht dabei NICHT um den Wert der Änderung (diese Grenzen werden mit 
	 * "getMaxWert" / "getMinWert" festgelegt) sondern nur ob es Änderung grundsätzlich
	 * möglich ist!
	 * @param canUpdate Der bisher berechnete Wert der Prüfung
	 * @param link Link des Elements, dass geprüft werden soll
	 */
	public boolean changeCanUpdateWert(boolean canUpdate, HeldenLink link);

	/**
	 * Wird aufgerufen um zu überprüfen ob der Text eines Elements geändert werden darf.
	 * (text ist z.B. bei "Vorurteil gegen Orks" der String "Orks")
	 * @param canUpdate Der bisher berechnete Wert der Prüfung
	 * @param link Link des Elements, dass geprüft werden soll
	 */
	public boolean changeCanUpdateText(boolean canUpdate, HeldenLink link);

	/**
	 * Wird aufgerufen um zu überprüfen ob das ZweitZiel eines Elements geändert werden darf.
	 * (ZweitZiel ist z.B. bei "Unfähigkeit für Schwerter" das Talent "Schwerter")
	 * @param canUpdate Der bisher berechnete Wert der Prüfung
	 * @param link Link des Elements, dass geprüft werden soll
	 */
	public boolean changeCanUpdateZweitZiel(boolean canUpdate,	HeldenLink link);
}