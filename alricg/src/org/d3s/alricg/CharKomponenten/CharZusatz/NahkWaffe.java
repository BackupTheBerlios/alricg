/*
 * Created 20. Januar 2005 / 17:07:37
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.CharZusatz;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.Controller.ProgAdmin;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einf�gen
 * @author V.Strelow
 */
public class NahkWaffe extends Waffe {
	private int kkAb = KEIN_WERT; // Ab diesem Wert gibt es TP Zuschlag
	private int kkStufe = KEIN_WERT; // Ab diesem Wert gibt es weitere TP Zuschlag
	private int bf = KEIN_WERT; // Bruchfaktor
	private String dk; // Distanzklasse
	private int wmAT = KEIN_WERT; // Waffenmodifikator / AT
	private int wmPA = KEIN_WERT; // Waffenmodifikator / PA
	
	/**
	 * Konstruktur; id beginnt mit "NKW-" f�r Nahkampf-Waffe
	 * @param id Systemweit eindeutige id
	 */
	public NahkWaffe(String id) {
		setId(id);
	}
	
	/**
	 * @return Liefert den Bruchfaktor
	 */
	public int getBF() {
		return bf;
	}
	/**
	 * @return Liefert die Distanzklasse(n).
	 */
	public String getDk() {
		return dk;
	}
	/**A b diesem Wert gibt es TP-Zuschlag.
	 * @return Liefert das Attribut kkAb.
	 */
	public int getKkAb() {
		return kkAb;
	}
	/** Ab getKkAb()+ diesen Wert gibt es einen weiteren TP-Zuschlag.
	 * @return Liefert das Attribut kkStufe.
	 */
	public int getKkStufe() {
		return kkStufe;
	}
	/**
	 * @return Liefert den Waffenmodifikator / AT.
	 */
	public int getWmAT() {
		return wmAT;
	}
	/**
	 * @return Liefert den Waffenmodifikator / PA
	 */
	public int getWmPA() {
		return wmPA;
	}
	
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	Element tmpElement;
    	
    	try {
	    	tmpElement = xmlElement.getFirstChildElement("tp");
	    	// Auslesen der TP-Zus�tze f�r NahKampfwaffen
	    	if ( tmpElement != null ) {
	    		
	    		if (tmpElement.getAttribute("kk-ab") != null ) {
	    			kkAb = Integer.parseInt(tmpElement.getAttributeValue("kk-ab"));
	    		}
	    		if (tmpElement.getAttribute("kk-stufe") != null ) {
	    			kkStufe = Integer.parseInt(tmpElement.getAttributeValue("kk-stufe"));
	    		}
	    	}
	    	
	    	// Auslesen der weiteren Eigenschaften
	    	tmpElement = xmlElement.getFirstChildElement("eigenschaften");
	    	// Auslesen des Bruchfaktors
	    	if ( tmpElement != null && tmpElement.getAttribute("bf") != null ) {
	    		bf = Integer.parseInt(tmpElement.getAttributeValue("bf"));
	    	}
	    	
	    	// Auslesen der Distanzklasse
	    	if ( tmpElement != null && tmpElement.getAttribute("dk") != null ) {
	    		dk = tmpElement.getAttributeValue("dk");
	    	}
	    	
	    	// Auslesen der WaffenModis
	    	tmpElement = xmlElement.getFirstChildElement("wm");
	    	// Auslesen des Bruchfaktors
	    	if ( tmpElement != null && tmpElement.getAttribute("at") != null ) {
	    		wmAT = Integer.parseInt(tmpElement.getAttributeValue("at"));
	    	}
	    	
	    	// Auslesen der Distanzklasse
	    	if ( tmpElement != null && tmpElement.getAttribute("pa") != null ) {
	    		wmPA = Integer.parseInt(tmpElement.getAttributeValue("pa"));
	    	}
    	
    	} catch (NumberFormatException exc) {
    		// TODO Bessere Fehlermeldung einbauen
    		ProgAdmin.logger.severe("Konnte String nicht in int umwandeln: " +exc.toString());
    	}
    }
    
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("nkWaffe");
    	
    	tmpElement = xmlElement.getFirstChildElement("tp");
    	// Schreiben der "Schwelle" f�r den KK -Zuschlag
    	if (kkAb != KEIN_WERT) {
    		if (tmpElement == null) {
    			tmpElement = new Element("tp");
    			xmlElement.appendChild(tmpElement);
    		}
    		
    		tmpElement.addAttribute(new Attribute("kk-ab", Integer.toString(kkAb)));
    	}
    	
    	// Schreiben der Stufe f�r den KK-Zuschlag
    	if (kkStufe != KEIN_WERT) {
    		if (tmpElement == null) {
    			tmpElement = new Element("tp");
    			xmlElement.appendChild(tmpElement);
    		}
    		tmpElement.addAttribute(new Attribute("kk-stufe", Integer.toString(kkStufe)));
    	}
    	
    	
    	tmpElement = xmlElement.getFirstChildElement("eigenschaften");
    	// Schreiben des Bruchfaktors
    	if (bf != KEIN_WERT) {
    		if (tmpElement == null) {
    			tmpElement = new Element("eigenschaften");
    			xmlElement.appendChild(tmpElement);
    		}
    		
    		tmpElement.addAttribute(new Attribute("bf", Integer.toString(bf)));
    	}
    	
    	// Schreiben der Distanzklasse
    	if (dk != null && dk.trim().length() > 0) {
    		if (tmpElement == null) {
    			tmpElement = new Element("eigenschaften");
    			xmlElement.appendChild(tmpElement);
    		}
    		
    		tmpElement.addAttribute(new Attribute("dk", dk));
    	}
    	
    	// Schreiben der Waffen-Modis
    	if (wmAT != KEIN_WERT || wmPA != KEIN_WERT) {
			tmpElement = new Element("wm");
			
			if (wmAT != KEIN_WERT) {
				tmpElement.addAttribute(new Attribute("at", Integer.toString(wmAT)));
			}
			if (wmPA != KEIN_WERT) {
				tmpElement.addAttribute(new Attribute("pa", Integer.toString(wmPA)));
			}
			
			xmlElement.appendChild(tmpElement);
    	}
    	
    	return xmlElement;
    }
}
