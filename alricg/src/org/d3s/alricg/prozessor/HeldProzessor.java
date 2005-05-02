/*
 * Created on 01.05.2005 / 12:38:36
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.prozessor;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Kultur;
import org.d3s.alricg.charKomponenten.Profession;
import org.d3s.alricg.charKomponenten.Rasse;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;
import org.d3s.alricg.held.Held;
import org.d3s.alricg.held.HeldenLink;

/**
 * <u>Beschreibung:</u><br> 
 * Diese Klasse stellt Methoden zur Verfügung um Helden zu bearbeiten. Die Unterklassen
 * dieser Klassen spezialisieren sich dann auf eine bestimmte Art der Bearbeitung des 
 * Helden (Generierung / Management). Dabei implementieren die speziellen Klasse die hier
 * vorgegebenen Methoden. 
 * So ist die Sigantur stehts gleich, egal auf welche Art ein Held bearbeitet wird 
 * (erleichtert hoffentlich das erstellen einer GUI die für Generierung und Management
 * benutzt werden kann), die Prozesse hinter der Signatur können aber an die jeweilige 
 * Art angepasst werden.
 * 
 * Jede Art von CharElement (egal ob Talent, Vorteil, Eigenschaft, usw.) kann über diese 
 * Schnittstelle bearbeitet werden.
 * 
 * Bisher ist nur der "GenerierungProzessor" implementiert.
 * 
 * @author V. Strelow
 * @see org.d3s.alricg.prozessor.generierung.GenerierungProzessor
 */
public abstract class HeldProzessor {
	protected Held held;

	/**
	 * @return Der Held der durch diesen Prozessor bearbeitet wird
	 */
	public Held getHeld() {
		return held;
	}
	
	/**
	 * Liefert zurück ob es sich um Generierung handel oder nicht.
	 * @return true - Der Held wird als Generierung bearbeitet, 
	 * 		   false - Der Held wird als fertigter Held verwaltet
	 */
	public abstract boolean isGenerierung();
	
	/**
	 * Mit dieser Methode kann aus dem zum Helden gehörenden Elementen ein bestimmtes 
	 * gesucht werden.
	 * 
	 * Beispiele: 
	 * - Gesucht "Vorurteile gegen Orks": Es wird die ID von "Vorurteile gegen", dessen 
	 * 			CharKomponente (="Nachteil") und der Text "Orks" benötigt. 
	 * 			"Vorurteile gegen Orks" ist etwas anderes als "Vorurteile gegen Zwerge"
	 * - Gesucht "Begabung Schwerter": Es wird die ID von "Begabung", dessen 
	 * 	 		CharKomponente (="Vorteil") und das zweitZiel Talent "Schwerter" benötigt.
	 * 	 		"Begabung Schwerter" ist etwas anderes als "Begabung Reiten"
	 * - Gesucht "Schwerter 6": Es wird die ID von "Schwerter" und dessen CharKomponente 
	 * 			(="Talent") benötigt. Mehr nicht, die Stufe ist egal.
	 * 
	 * Wenn das CharElemt zu der ID vorliegt, sollte lieber die Methode 
	 * "getLinkByCharElement(element, text, zweitZiel)" benutzt werden, da diese effizienter
	 * ist.
	 * 
	 * @param id Die ID des CharElements, welches als Ziel im gesuchten Link steht.
	 * @param text Der Text des gesuchten Links; kann null sein
	 * @param zweitZiel Das zweitZiel des gesuchten Links; kann null sein
	 * @param komp Die CharKomponete des CharElements, welches als Ziel im gesuchten Link steht
	 * @return Den HeldenLink zu den Parameter oder null, falls es keinen solchen gibt 
	 */
	public HeldenLink getLinkById(String id, String text, CharElement zweitZiel, CharKomponente komp) {
		
		return getLinkByCharElement(
					ProgAdmin.charKompAdmin.getCharElement(id, komp), 
					text, 
					zweitZiel);
	}
	
	/**
	 * Mit dieser Methode kann aus dem zum Helden gehörenden Elementen ein bestimmtes 
	 * gesucht werden. Siehe "getLinkById(id, text, zweitZiel, komp)" für Beispiele.
	 * Hier muß die CharKomponente "komp" nicht mit angegeben werden, was allerdings 
	 * weniger effizient ist. 
	 * Daher sollte lieber die Methode "getLinkById(id, text, zweitZiel, komp)" 
	 * benutzt werden, sofern die CharKomponente bekannt ist!
	 * 
	 * @param id Die ID des CharElements, welches als Ziel im gesuchten Link steht.
	 * @param text Der Text des gesuchten Links; kann null sein
	 * @param zweitZiel Das zweitZiel des gesuchten Links; kann null sein
	 * @return Den HeldenLink zu den Parameter oder null, falls es keinen solchen gibt 
	 */
	public HeldenLink getLinkById(String id, String text, CharElement zweitZiel) {
		
		return getLinkByCharElement(
					ProgAdmin.charKompAdmin.getCharElement(id), 
					text, 
					zweitZiel);
	}
	
	/**
	 * Mit dieser Methode kann aus dem zum Helden gehörenden Elementen ein bestimmtes 
	 * gesucht werden. Siehe "getLinkById(id, text, zweitZiel, komp)" für Beispiele.
	 * 
	 * @param id Die ID des CharElements, welches als Ziel im gesuchten Link steht.
	 * @param text Der Text des gesuchten Links; kann null sein
	 * @param zweitZiel Das zweitZiel des gesuchten Links; kann null sein
	 * @return Den HeldenLink zu den Parameter oder null, falls es keinen solchen gibt 
	 */
	public HeldenLink getLinkByCharElement(CharElement element, String text, CharElement zweitZiel) {
		IdLink tmpLink = new IdLink(null, null);
		
		if (text == null) text = "";
		
    	// Setzen aller Basis-Werte wie übergeben
    	tmpLink.setZielId(element);
    	tmpLink.setZweitZiel(zweitZiel);
    	tmpLink.setText(text);
		
    	return held.getElementBox(element.getCharKomponente()).getEqualLink(tmpLink);
	}
	
	
	/**
	 * Dient dem hinzufügen von neuen Elementen. Wird immer dann benötigt  wenn etwas NICHT
	 * über einen Link hinzugefügt wird. (z.B. Talent Aktivierung, SF Kaufen, 
	 * usw.)
	 * 
	 * @param ziel Das eigentliche Element; kann NICHT null sein
	 * @param text Text zu dem Link (Vorurteile gegen "Orks"); kann null sein 
	 * @param link Weiteres zugehöriges Element (Unfähigkeit für "Schwerter"); kann null sein
	 * @param wert Wert zu dem Link (Schwerter "5"); benutze Link.KEIN_WERT wenn ohne Wert!
	 * @see org.d3s.alricg.held.HeldProzessor#addElement(org.d3s.alricg.charKomponenten.links.IdLink)
	 */
	public abstract void addCharElement(CharElement ziel, String text, 
										CharElement zweitZiel, int wert);

	/**
	 * Verändert die Werte eines Elementes des Helden. Dabei konnen verändert werden:
	 * Der Wert (Stufe), der Text und das ZweitZiel. Es wird keine Prüfung durchgeführt
	 * ob diese Änderungen möglich sind!
	 * 
	 * @param link Der Link zu dem Element das geupdatet werden soll
	 * @param stufe Die neue (gesamt-)Stufe oder "KEIN_WERT" wenn keine Änderung
	 * @param text Der neue Text oder 'null', wenn der Text nicht geändert wird
	 * 		(text ist z.B. bei "Vorurteil gegen Orks" der String "Orks")
	 * @param zweitZiel Das neue zweitZiel oder 'null', wenn dies nicht geändert wird
	 * 		(ZweitZiel ist z.B. bei "Unfähigkeit für Schwerter" das Talent "Schwerter")
	 */
	public abstract void updateElement(HeldenLink link, int wert,
										String text, CharElement zweitZiel);

	/**
	 * Dient dem Prüfen ob ein neues Element hinzugefügt werden kann. Wird immer dann benötigt
	 * wenn der Benutzer ein neues Element hinzufügen möchte. (z.B. Talent Aktivierung, 
	 * SF Kaufen, usw.)
	 *  
	 * @param ziel Das eigentliche Element; darf NICHT null sein
	 * @param text Der neue Text oder 'null', wenn der Text nicht geändert wird
	 * 		(text ist z.B. bei "Vorurteil gegen Orks" der String "Orks")
	 * @param zweitZiel Das neue zweitZiel oder 'null', wenn dies nicht geändert wird
	 * 		(ZweitZiel ist z.B. bei "Unfähigkeit für Schwerter" das Talent "Schwerter")
	 * @param wert Wert zu dem Link (Schwerter "5") oder "KEIN_WERT" wenn ohne Wert!
	 */
	public abstract boolean canAddCharElement(CharElement ziel, String text,
			CharElement zweitZiel, int wert);

	/**
	 * Entfernd ein Element von Helden. Hierbei erfolgt keinerlei Prüfung.
	 * @param element Der Link des Elements welches entfernt werden soll
	 */
	public abstract void removeElement(HeldenLink element);

	/**
	 * Prüft ob ein Element von Held entfernd werden kann. Dies ist nicht der Fall wenn
	 * das Element durch die Herkunft modifiziert wird oder als notwendige Voraussetzung
	 * für andere Elemente des Helden fungiert.
	 * @param element Der Link zu dem Element das geprüft wird
	 * @return true - Das Element kann entfernd werden, 
	 * 			false - Das Element kann nicht entfernd werden
	 */
	public abstract boolean canRemoveElement(HeldenLink element);

	/**
	 * Wird aufgerufen um zu überprüfen ob der Wert eines Elements geändert werden darf.
	 * (Wert Ist z.B. bei "Schwerter 6" die 6). Es geht dabei NICHT um den Wert der
	 * Änderung (diese Grenzen werden mit "getMaxWert" / "getMinWert" festgelegt) 
	 * sondern nur ob es Änderung grundsätzlich möglich ist!
	 * @param link Link des Elements, dass überprüft werden soll
	 */

	public abstract boolean canUpdateStufe(HeldenLink link);

	/**
	 * Wird aufgerufen um zu überprüfen ob der Text eines Elements geändert werden darf.
	 * (text ist z.B. bei "Vorurteil gegen Orks" der String "Orks")
	 * @param link Link des Elements, dass überprüft werden soll
	 */
	public abstract boolean canUpdateText(HeldenLink link);

	/**
	 * Wird aufgerufen um zu überprüfen ob das ZweitZiel eines Elements geändert werden darf.
	 * (ZweitZiel ist z.B. bei "Unfähigkeit für Schwerter" das Talent "Schwerter")
	 * @param link Link des Elements, dass geprüft werden soll
	 */
	public abstract boolean canUpdateZweitZiel(HeldenLink link);

	/**
	 * Wird immer aufgerufen, wenn von einem Element die maximale Stufe bestimmt wird.
	 * @param maxStufe Die bisher errechnete maximale Stufe
	 * @param link Der Link zu dem Element
	 * @return Die resultierende maximale Stufe oder KEIN_WERT wenn ein keine Stufe gibt
	 */
	public abstract int getMaxWert(Link link);
	
	/**
	 * Wird immer aufgerufen, wenn von einem Element die minimale Stufe bestimmt wird.
	 * @param minStufe Die bisher errechnete minimale Stufe
	 * @param link Der Link zu dem Element
	 * @return Die resultierende minimale Stufe oder KEIN_WERT wenn ein keine Stufe gibt
	 */
	public abstract int getMinWert(Link link);
	
	/**
	 * Setzt die aktuelle Rasse des Helden, die alte Rasse wird dardurch "überschrieben".
	 * Ein Held kann immer nur einer Rasse gleichzeitig angehören,  "Kind  zweier Welten"
	 * o.ä. wird über die Klasse 
	 * "org.d3s.alricg.charKomponenten.spezial.ZweiWeltenRasse" geregelt.
	 * Es werden durch die Klasse alle nötigen Änderungen am Helden durchgeführt. Es 
	 * werden keine Prüfungen durchgeführt, ob ein setzten möglich ist, allerdings
	 * wird beachtet:
	 * - Stufen müssen in den Grenzen bleiben
	 * - Kosten müssen großteils neu berechnet werden
	 * - Automatische SF/ VT/ NT ersetzten vom User ausgewählte (und geben somit GP frei)
	 * 
	 * @param rasse
	 */
	public abstract void setRasse(Rasse rasse);
	
	/**
	 * Prüft ob die alte Rasse gelöscht werden kann und die neue Hinzugefügt 
	 * werden kann. Dabei muß vor allem Dingen geprüft werden: 
	 * Unverträglichkeit von 2 CharElementen (X darf nicht mit Y gewählt werden).
	 * CharElemente aus der alten Rasse, die als Voraussetzungen benötigt werden, 
	 * werden einfach mit GP-Kosten gekauft, sind also kein Grund eine Rasse zu 
	 * "verbieten". 
	 * Unzureichende GP Kosten sind auch kein Hinderungsgrund, werden einfach negativ
	 * @param rasse Die Rasse die überprüft wird
	 */
	public abstract void canSetRasse(Rasse rasse);

	/**
	 * Setzt die aktuelle Kultur des Helden, die alte Kultur wird dardurch "überschrieben".
	 * Ein Held kann immer nur einer Kultur gleichzeitig angehören,  "Kind  zweier Welten"
	 * o.ä. wird über die Klasse 
	 * "org.d3s.alricg.charKomponenten.spezial.ZweiWeltenKultur" geregelt.
	 * Es werden durch die Klasse alle nötigen Änderungen am Helden durchgeführt. Es 
	 * werden keine Prüfungen durchgeführt, ob ein setzten möglich ist, allerdings
	 * wird beachtet:
	 * - Stufen müssen in den Grenzen bleiben
	 * - Kosten müssen großteils neu berechnet werden
	 * - Automatische SF/ VT/ NT ersetzten vom User ausgewählte (und geben somit GP frei)
	 * 
	 * @param kultur Die neue Kultur des Helden
	 */
	public abstract void setKultur(Kultur kultur);
	
	/**
	 * Prüft ob die alte Kultur gelöscht werden kann und die neue Hinzugefügt 
	 * werden kann. Dabei muß vor allem Dingen geprüft werden: 
	 * Unverträglichkeit von 2 CharElementen (X darf nicht mit Y gewählt werden).
	 * CharElemente aus der alten Kultur, die als Voraussetzungen benötigt werden, 
	 * werden einfach mit GP-Kosten gekauft, sind also kein Grund eine Kultur zu 
	 * "verbieten". 
	 * Unzureichende GP Kosten sind auch kein Hinderungsgrund, werden einfach negativ
	 * @param kultur Die Kultur die überprüft wird
	 */
	public abstract void canSetKultur(Kultur kultur);

	/**
	 * Setzt die aktuelle Profession des Helden, die alte Profession wird dardurch 
	 * "überschrieben". Ein Held kann immer nur einer Kultur gleichzeitig angehören, 
	 *  "Veteran", "Breitgefächerte Bildung" oder "Abgebrochende Ausbildung"  
	 *  werden über die entsprechenden Klassen im package 
	 * "org.d3s.alricg.charKomponenten.spezial" geregelt.
	 * Es werden durch die Klasse alle nötigen Änderungen am Helden durchgeführt. Es 
	 * werden keine Prüfungen durchgeführt, ob ein setzten möglich ist, allerdings
	 * wird beachtet:
	 * - Stufen müssen in den Grenzen bleiben
	 * - Kosten müssen großteils neu berechnet werden
	 * - Automatische SF/ VT/ NT ersetzten vom User ausgewählte (und geben somit GP frei)
	 * 
	 * @param profession Die neue Profession des Helden
	 */
	public abstract void setProfession(Profession profession);
	
	/**
	 * Prüft ob die alte Profession gelöscht werden kann und die neue Hinzugefügt 
	 * werden kann. Dabei muß vor allem Dingen geprüft werden: 
	 * Unverträglichkeit von 2 CharElementen (X darf nicht mit Y gewählt werden).
	 * CharElemente aus der alten Profession, die als Voraussetzungen benötigt werden, 
	 * werden einfach mit GP-Kosten gekauft, sind also kein Grund eine Profession zu 
	 * "verbieten". 
	 * Unzureichende GP Kosten sind auch kein Hinderungsgrund, werden einfach negativ
	 * @param profession Die Profession die überprüft wird
	 */
	public abstract void canSetProfession(Profession profession);

	
	// *************************************************************************************

	/**
	 * Diese Methode ist vor allem für Sonderregel gedacht, bei denen Elemente Modifiziert 
	 * werden und diese Modifikation per Link zu einem Element hinzugefügt wird.
	 * Gibt es kein solches element
	 * @param link Der Link der zu einem Element hinzugefügt werden soll.
	 */
	public abstract void addLinkToElement(IdLink link);
	
}