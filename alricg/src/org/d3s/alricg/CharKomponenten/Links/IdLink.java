/*
 * Created 22. Dezember 2004 / 14:23:17
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.Links;

/**
 * <b>Beschreibung:</b><br>
 * Diese Klasse dient als bindeglied zwischen zwei (oder mehr) Elementen. Über
 * diesen "LINK" können dann Werte, ein Text oder ein weiteres Element angegeben
 * werden (z.B. "Eitelkeit 5" oder "Unfähigkeit Schwimmen" oder "Verpflichtungen
 * gegen Orden").
 * 
 * @author V.Strelow
 */
public class IdLink {
	private String zielId; // Das Ziel dieses Links (z.B. eine SF)
	private String linkId; // Ein in Beziehung stehendes Element (z.B. Unfähigkeit "SCHWERT")
	private String text; // Ein Text (z.B. Vorurteile gegen "Orks")
	private int wert; // Wert der Beziehung (z.B. Höhenangst 5)
	private boolean leitwert; // Für Elfen, verändert kosten
}
