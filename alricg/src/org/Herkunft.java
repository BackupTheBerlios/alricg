/*
 * Created 22. Dezember 2004 / 02:25:53
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org;

/**
 * <b>Beschreibung:</b><br>
 * Superklasse für Rasse, Kultur und Profession. Fasst Gemeinsamkeiten zusammen. 
 * @author V.Strelow
 */
public abstract class Herkunft {
    private int gp;
	private String varianteVon;
	private boolean kannGewaehltWerden;
	private int geschlecht; // 1 = Nur Mänlich, 2 = nur Weiblich, sonst beides
	private int lepModi;
    private int aspModi;
    private int aupModi;
    private int kaModi;
	private int mrModi;
    private int soModi;
    private int soMin;
    private int soMax;
    
	/**
	 * @return Liefert das Attribut aspModi.
	 */
	public int getAspModi() {
		return aspModi;
	}
	
	/**
	 * @return Liefert das Attribut aupModi.
	 */
	public int getAupModi() {
		return aupModi;
	}
	
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
	 * @return Liefert das Attribut kaModi.
	 */
	public int getKaModi() {
		return kaModi;
	}
	
	/**
	 * @return Liefert das Attribut kannGewaehltWerden.
	 */
	public boolean isKannGewaehltWerden() {
		return kannGewaehltWerden;
	}
	
	/**
	 * @return Liefert das Attribut lepModi.
	 */
	public int getLepModi() {
		return lepModi;
	}
	
	/**
	 * @return Liefert das Attribut mrModi.
	 */
	public int getMrModi() {
		return mrModi;
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
	 * @return Liefert das Attribut soModi.
	 */
	public int getSoModi() {
		return soModi;
	}
	
	/**
	 * @return Liefert das Attribut varianteVon.
	 */
	public String getVarianteVon() {
		return varianteVon;
	}
}
