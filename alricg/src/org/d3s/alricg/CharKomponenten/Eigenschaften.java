/*
 * Created 22. Dezember 2004 / 13:10:45
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

/**
 * <b>Beschreibung:</b><br>
 * Hilfsklasse zum besseren Arbeiten mit Eigenschaften. Alle Eigenschaften werden
 * hier auf eine Zahl abgebildet.
 * 
 * @author V.Strelow
 */
public class Eigenschaften {
	public final static int MU = 0;
	public final static int KL = 1;
	public final static int IN = 2;
	public final static int CH = 3;
	public final static int FF = 4;
	public final static int GE = 5;
	public final static int KO = 6;
	public final static int KK = 7;
    public final static int INI = 8;
    public final static int MR = 9;
    public final static int LEP = 10;
    public final static int ASP = 11;
    public final static int AUP = 12;
    public final static int KA = 13;
    public final static int SO = 14;

    private final static int anzahlEigenschaften = 14;

    /**
     * @return Gibt die anzahl aller Eigenschaften dieser Klasse an
     */
	public static int getAnzahlEigenschaften() {
		return anzahlEigenschaften;
	}
}
