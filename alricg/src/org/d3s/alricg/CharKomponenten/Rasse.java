/*
 * Created 22. Dezember 2004 / 13:07:57
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.CharZusatz.WuerfelSammlung;
/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Rasse extends Herkunft {
	private Kultur[] kulturMoeglich;
	private Kultur[] kulturUeblich;
    private String[] haarfarbe = new String[20];
	private String[] augenfarbe = new String[20];
	private WuerfelSammlung groesseWuerfel;
	private WuerfelSammlung alterWuerfel;

	private int gewichtModi;
	private int geschwindigk;

	/**
	 * Konstruktur; id beginnt mit "RAS-" für Rasse
	 * @param id Systemweit eindeutige id
	 */
	public Rasse(String id) {
		setId(id);
	}
	
	/**
	 * @return Liefert das Attribut augenfarbe.
	 */
	public String[] getAugenfarbe() {
		return augenfarbe;
	}
	/**
	 * @return Liefert das Attribut gewichtModi.
	 */
	public int getGewichtModi() {
		return gewichtModi;
	}
	/**
	 * @return Liefert das Attribut haarfarbe.
	 */
	public String[] getHaarfarbe() {
		return haarfarbe;
	}
	/**
	 * @return Liefert das Attribut kulturMoeglich.
	 */
	public Kultur[] getKulturMoeglich() {
		return kulturMoeglich;
	}
	/**
	 * @return Liefert das Attribut kulturUeblich.
	 */
	public Kultur[] getKulturUeblich() {
		return kulturUeblich;
	}
	
	/**
	 * Berechnet einen einen korrekten Wert für die Grösse. Dies ist ein gültiger
	 * Zufalls-Wert, basierend auf den angegebenen Werten.
	 * @return Einen gültigen größe-Wert für diese Rasse.
	 */
	public int getGroesseZufall() {
		return groesseWuerfel.getWuerfelWurf();
	}
	
	/**
	 * @return Liefert das Attribut groesseWuerfel.
	 */
	public WuerfelSammlung getGroesseWuerfel() {
		return groesseWuerfel;
	}
	
	/**
	 * Berechnet einen einen korrekten Wert für das Alter. Dies ist ein gültiger
	 * Zufalls-Wert, basierend auf den angegebenen Werten.
	 * ACHTUNG: Das Alter kann noch durch Vor/Nachteile ("Veteran", usw) o.ä. 
	 * 		verändert werden! Dies wird hier nicht berücksichtig und ist nur der
	 * 		reine Grundwert der Rasse.
	 * @return Einen gültigen alters-Wert für diese Rasse.
	 */
	public int getAlterZufall() {
		return alterWuerfel.getWuerfelWurf();
	}
	
	/**
	 * @return Liefert das Attribut alterWuerfel.
	 */
	public WuerfelSammlung getAlterWuerfel() {
		return alterWuerfel;
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
