/*
 * Created 22. Dezember 2004 / 02:25:53
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

/**
 * <b>Beschreibung:</b><br>
 * Superklasse für Rasse, Kultur und Profession. Fasst Gemeinsamkeiten zusammen. 
 * @author V.Strelow
 */
public abstract class Herkunft extends CharElement {
    private int gp;
	private String varianteVon;
	private boolean kannGewaehltWerden;
	private int geschlecht;
    private int soMin;
    private int soMax;	
	private int eigenschaftVoraussetzungen;
	private int[] eigenschaftModis = new int[Eigenschaften.getAnzahlEigenschaften()];
	
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
	 * @return Liefert das Attribut kannGewaehltWerden.
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
     * @return Der Modifikator-Wert für diese Eigenschaft
     * @see org.d3s.alricg.CharComponenten.Eigenschaften
     */
	public int getEigenschaftModi(int modiId) {
		// TODO implement getModi
		return eigenschaftModis[modiId];
	}
}
