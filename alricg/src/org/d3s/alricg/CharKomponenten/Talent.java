/*
 * Created 23. Dezember 2004 / 14:53:48
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import org.d3s.alricg.CharKomponenten.Links.Voraussetzung;
/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Talent extends Faehigkeit {
	public final static int ART_BASIS = 0;
	public final static int ART_SPEZIAL = 1;
	public final static int ART_BERUF = 2;
    public final static int  SORTE_KAMPF = 0;
    public final static int  SORTE_KOERPER = 1;
    public final static int  SORTE_GESELLSCHAFT = 2;
    public final static int  SORTE_NATUR = 3;
    public final static int  SORTE_WISSEN = 4;
    public final static int  SORTE_HANDWERK = 5;

	private int art;
    private int sorte;
    private Voraussetzung Voraussetzung;

}
