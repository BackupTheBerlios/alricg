/*
 * Created 22. Dezember 2004 / 13:07:41
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import org.d3s.alricg.CharKomponenten.Links.Auswahl;
/**
 * <b>Beschreibung:</b><br>
 * Repräsentiert eine Profession, speichert alle nötigen Daten.
 * 
 * @author V.Strelow
 */
public class Profession extends Herkunft {
	private int aufwand;
	private int art;
	private Vorteil[] verbotenVort;
	private Nachteil[] verbotenNacht;
	private Sonderfertigkeit[] verbotenSF;
	private Auswahl sprachen;
	private Auswahl schriften;
	private String ausruestung;
	private String besondererBesitz;

    public final static int ART_HANDWERKLICH = 0;
	public final static int ART_KRIEGERISCH = 1;
    public final static int ART_GESELLSCHAFTLICH = 2;
    public final static int ART_WILDNESS = 3;
    public final static int ART_MAGISCH = 4;
    public final static int ART_GEWEIHT = 5;
    public final static int ART_SCHAMANISCH = 6;

	public final static int AUFWAND_ERTSPROF = 0;
    public final static int AUFWAND_ZEITAUFW = 1;
    public final static int AUFWAND_ZUSATZPROF = 2; // Bei Elfen


	public int getAufwand(){ return aufwand; }

	public void setAufwand(int aufwand){ this.aufwand = aufwand; }

	public int getArt(){ return art; }

	public Vorteil[] getVerbotenVort(){ return verbotenVort; }

	public Nachteil[] getVerbotenNacht(){ return verbotenNacht; }

	public Sonderfertigkeit[] getVerbotenSF(){ return verbotenSF; }

	public Auswahl getSprachen(){ return sprachen; }

	public Auswahl getSchriften(){ return schriften; }

	public String getAusruestung(){ return ausruestung; }

	public String getBesondererBesitz(){ return besondererBesitz; }
}
