/*
 * Created 20. Januar 2005 / 16:18:50
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class RitusSF extends Sonderfertigkeit {
	private int[] dreiEigenschaften;
	private int sktGener;
	private int sktSpaet;
	private String kult;
	private boolean isLiturgie;
	
	
	/**
	 * @return Liefert das Attribut dreiEigenschaften.
	 */
	public int[] getDreiEigenschaften() {
		return dreiEigenschaften;
	}
	/**
	 * @return Liefert das Attribut isLiturgie.
	 */
	public boolean isLiturgie() {
		return isLiturgie;
	}
	/**
	 * @return Liefert das Attribut kult.
	 */
	public String getKult() {
		return kult;
	}
	/**
	 * @return Liefert das Attribut sktGener.
	 */
	public int getSktGener() {
		return sktGener;
	}
	/**
	 * @return Liefert das Attribut sktSpaet.
	 */
	public int getSktSpaet() {
		return sktSpaet;
	}
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	// TODO implement
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	// TODO implement
    	return null;
    }
}
