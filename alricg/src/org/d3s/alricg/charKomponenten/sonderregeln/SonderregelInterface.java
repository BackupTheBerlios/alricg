/*
 * Created on 30.04.2005 / 23:07:24
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.sonderregeln;

import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.prozessor.HeldProzessor;

/**
 * <u>Beschreibung:</u><br> 
 * Interface welches von allen Sonderfertigkeiten erf�llt werden mu�.
 * �ber die hier definierten Methoden kann eine Sonderregel die wichtigen abl�ufe 
 * bei der Helden Generierung/ Bearbeitung beeinflussen. F�r jede Sonderregel existiert 
 * eine eingende Klasse die sich nur um die erf�llung dieser Sonderregel k�mmert.
 * 
 * Sobald eine Sonderregel zum Helden hinzugef�gt wurde, wird jede Methode dieser 
 * Sonderregel stehts bei der, in der jeweiligen Methde beschriebenen, Aktion aufgerufen. 
 * 
 * Im Unterschied zum "BasisSonderregelInterface" sind die hier aufgef�hrten Methoden
 * kein Bestandteil des Sonderregel Admins.
 * 
 * (Bisherige Implementierungen: "HerrausragendeEigenschaft")
 * 
 * @author V. Strelow
 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter
 */
public interface SonderregelInterface extends BasisSonderregelInterface {
	
	/**
	 * Wird aufgerufen um festzustellen ob diese Sonderregel zum Helden
	 * hinzugef�gt werden kann. (vorher werden die �blichen "standart" 
	 * Pr�fungen durchgef�hrt)
	 * 
	 * @param prozessor Der HeldenProzessor f�r die Bearbeitung
	 * @param ok Das Ergebnis der "standart" Pr�fung
	 * @param srLink Der Link zu dem CharElement welches diese Sonderregel enth�lt. 
	 * 		Bei der Sonderregel "Herausragende Eigenschaft" w�hre dies der Link
	 * 		mit dem Vorteil "Herausragende Eigenschaft". Aus diesem k�nnen evtl.
	 * 		Wert, Text oder zweitZiel ausgelesen werden
	 * @return
	 */
	public boolean canAddSelf(HeldProzessor prozessor, boolean ok, Link srLink);
	
	/**
	 * Wird aufgerufen wenn diese Sonderregel zum Helden hinzugef�gt wurde.
	 * 
	 * @param prozessor Der HeldenProzessor f�r die Bearbeitung
	 * @param srLink Der Link zu dem CharElement welches diese Sonderregel enth�lt. 
	 * 		Bei der Sonderregel "Herausragende Eigenschaft" w�hre dies der Link
	 * 		mit dem Vorteil "Herausragende Eigenschaft". Aus diesem k�nnen evtl.
	 * 		Wert, Text oder zweitZiel ausgelesen werden.
	 */
	public void initSonderregel(HeldProzessor prozessor, Link srLink);
	// Evtl. �nderungen von Werten sollten hier vorgenommen werden
	
	/**
	 * Wird aufgerufen wenn eine Sonderregel wieder von einem Held entfernd wird.
	 * @param srLink Der Link zu dem CharElement welches diese Sonderregel enth�lt. 
	 * 		Bei der Sonderregel "Herausragende Eigenschaft" w�hre dies der Link
	 * 		mit dem Vorteil "Herausragende Eigenschaft". Aus diesem k�nnen evtl.
	 * 		Wert, Text oder zweitZiel ausgelesen werden.
	 */
	public void finalizeSonderregel(Link srLink);
	// Evtl. �nderungen von Werten sollten hier r�ckg�ngig gemacht werden
	
}