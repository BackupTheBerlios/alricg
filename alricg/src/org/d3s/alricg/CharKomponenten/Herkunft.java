/*
 * Created 22. Dezember 2004 / 02:25:53
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import org.d3s.alricg.CharKomponenten.Links.Auswahl;
/**
 * <b>Beschreibung:</b><br>
 * Superklasse f�r Rasse, Kultur und Profession. Fasst Gemeinsamkeiten zusammen.
 * Elemente vom Typ "Herkunft" werden nach einer Initiierung nicht mehr ver�ndert.
 * @author V.Strelow
 */
public abstract class Herkunft extends CharElement {
    private int gp;
	private String varianteVon;
	private boolean kannGewaehltWerden; // Eine Herkunft kann auch nur als Basis f�r Varianten dienen
	private int geschlecht;
    private int soMin;
    private int soMax;
    /**
     * Beispiel: An der Stelle Eigenschaft.MU im Array steht der Wert f�r Mut.
     */
	private int[] eigenschaftVoraussetzungen = new int[Eigenschaften.getAnzahlEigenschaften()];
	private int[] eigenschaftModis = new int[Eigenschaften.getAnzahlEigenschaften()];
	private Auswahl vorteileAuswahl;
	private Auswahl nachteileAuswahl;
	private Auswahl sfAuswahl;
	private Auswahl liturgienAuswahl;
	private Auswahl ritualeAuswahl;
	private Vorteil[] empfVorteil;
	private Nachteil[] empfNachteile;
	private Vorteil[] ungeVorteile;
	private Nachteil[] ungeNachteile;
	private Sonderfertigkeit[] verbilligteSonderf;
	private Liturgie[] verbilligteLiturien;
	private Ritual[] verbilligteRituale;
	private Auswahl talente;
	private Auswahl zauber;
	private Auswahl hauszauber;
	private Auswahl aktivierbareZauber;
	
	/**
	 * @return Liefert das Attribut geschlecht.
	 */
	public int getGeschlecht() {
		return geschlecht;
	}
	
	/**
	 * @return Liefert das Attribut gp.
	 */
	public int getGp() {
		return gp;
	}	
	
	/**
	 * Eine Herkunft die nicht gew�hlt werden kann, dient nur als Basis f�r
     * Varianten.
     * @return Liefert das Attribut kannGewaehltWerden
	 */
	public boolean isKannGewaehltWerden() {
		return kannGewaehltWerden;
	}	
	
	/**
	 * @return Liefert das Attribut soMax.
	 */
	public int getSoMax() {
		return soMax;
	}
	
	/**
	 * @return Liefert das Attribut soMin.
	 */
	public int getSoMin() {
		return soMin;
	}	
	
	/**
	 * @return Liefert das Attribut varianteVon.
	 */
	public String getVarianteVon() {
		return varianteVon;
	}

    /**
     * Mit dieser Methode werden die Modifikationen auf Eigenschaften (MU, KL,.. ),
     * LeP, AsP, AuP, Ka, SO, INI und MR ausgelesen.
     * 
     * @param modiID Die ID aus der Klasse "Eigenschaften"
     * @return Der Modifikator-Wert f�r diese Eigenschaft
     * @see org.d3s.alricg.CharComponenten.Eigenschaften
     */
	public int getEigenschaftModi(int modiId) {
		// TODO implement getModi
		return eigenschaftModis[modiId];
	}

	public Auswahl getVorteileAuswahl(){
        return vorteileAuswahl;
    }

	public Auswahl getNachteileAuswahl(){
        return nachteileAuswahl;
    }

	public Auswahl getSfAuswahl(){
        return sfAuswahl;
    }

	public Auswahl getLiturgienAuswahl(){
        return liturgienAuswahl;
    }

	public Auswahl getRitualeAuswahl(){
        return ritualeAuswahl;
    }

	public Vorteil[] getEmpfVorteil(){ return empfVorteil; }

	public Nachteil[] getEmpfNachteile(){ return empfNachteile; }

	public Vorteil[] getUngeVorteile(){ return ungeVorteile; }

	public Nachteil[] getUngeNachteile(){ return ungeNachteile; }

	public Sonderfertigkeit[] getVerbilligteSonderf(){ return verbilligteSonderf; }

	public Liturgie[] getVerbilligteLiturien(){ return verbilligteLiturien; }

	public Ritual[] getVerbilligteRituale(){ return verbilligteRituale; }

	public Auswahl getTalente(){ return talente; }

	public Auswahl getZauber(){ return zauber; }

	public Auswahl getHauszauber(){ return hauszauber; }

	public Auswahl getAktivierbareZauber(){ return aktivierbareZauber; }

}
