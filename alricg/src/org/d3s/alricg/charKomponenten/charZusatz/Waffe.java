/*
 * Created on 23.01.2005 / 19:47:25
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.charZusatz;

import java.util.ArrayList;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.controller.ProgAdmin;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public abstract class Waffe extends Gegenstand {
	private WuerfelSammlung TP;
	private int laenge = KEIN_WERT;
	private int ini = KEIN_WERT;
	private Talent[] talent;
	
	
	/**
	 * @return Liefert das Attribut ini.
	 */
	public int getIni() {
		return ini;
	}
	/**
	 * @return Liefert das Attribut laenge.
	 */
	public int getLaenge() {
		return laenge;
	}
	/**
	 * @return Liefert das Attribut talent.
	 */
	public Talent[] getTalent() {
		return talent;
	}
	/**
	 * @return Liefert das Attribut tP.
	 */
	public WuerfelSammlung getTP() {
		return TP;
	}
	
	/**
	 * @param ini Setzt das Attribut ini.
	 */
	public void setIni(int ini) {
		this.ini = ini;
	}
	/**
	 * @param laenge Setzt das Attribut laenge.
	 */
	public void setLaenge(int laenge) {
		this.laenge = laenge;
	}
	/**
	 * @param talent Setzt das Attribut talent.
	 */
	public void setTalent(Talent[] talent) {
		this.talent = talent;
	}
	/**
	 * @param tp Setzt das Attribut tP.
	 */
	public void setTP(WuerfelSammlung tp) {
		TP = tp;
	}
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	Element tmpElement;
    	Elements tmpElements;
    	int[] augenAugen;
    	int[] augenWuerfel;
    	ArrayList<Integer> arrayAugen = new ArrayList<Integer>(1);
    	ArrayList<Integer> arrayWuerfel = new ArrayList<Integer>(1);
    	
    	// Die Trefferpunkte auslesen
    	try {
	    	tmpElement = xmlElement.getFirstChildElement("tp");
	    	if ( tmpElement != null ) {
	    		if ( tmpElement.getAttribute("W6") != null ) {
	    			arrayWuerfel.add(Integer.valueOf(tmpElement.getAttributeValue("W6")));
	    			arrayAugen.add(6);
	    		}
	    		if ( tmpElement.getAttribute("W20") != null ) {
	    			arrayWuerfel.add(Integer.valueOf(tmpElement.getAttributeValue("W20")));
	    			arrayAugen.add(20);
	    		}
	    		
	    		augenAugen = new int[arrayAugen.size()];
	    		augenWuerfel = new int[arrayWuerfel.size()];
	    		for (int i = 0; i < arrayWuerfel.size(); i++) {
	    			augenAugen[i] = arrayAugen.get(i);
	    			augenWuerfel[i] = arrayWuerfel.get(i);
	    		}
	    		
	    		TP = new WuerfelSammlung(
	    			Integer.parseInt(tmpElement.getAttributeValue("plus")),
	    			augenWuerfel,
	    			augenAugen
	    		);
	    	}
	    	
	    	// lesen der Länge
	    	tmpElement = xmlElement.getFirstChildElement("eigenschaften");
	    	if (tmpElement != null && tmpElement.getAttribute("laenge") != null ) {
	    		laenge = Integer.parseInt(tmpElement.getAttributeValue("laenge"));
	    	}
	    	
	    	// lesen des init
	    	if (tmpElement != null && tmpElement.getAttribute("ini") != null ) {
	    		ini = Integer.parseInt(tmpElement.getAttributeValue("ini"));
	    	}
	    	
    	} catch (NumberFormatException exc) {
    		// TODO richtige Fehlermeldung einbauen
    		ProgAdmin.logger.severe("Konnte String nicht in int umwandeln: " + exc.toString());
    	}
    	
    	// Auslesen der Talente, mit denen die Waffe geführt werden kann
    	tmpElements = xmlElement.getChildElements("talentId");
    	talent = new Talent[tmpElements.size()];
    	for (int i = 0; i < talent.length; i++) {
    		talent[i] = (Talent) ProgAdmin.charKompAdmin.getCharElement(
    					tmpElements.get(i).getValue(),
    					ProgAdmin.charKompAdmin.getCharKompFromId(
    							tmpElements.get(i).getValue()
    						)
    				);
    	}
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement = null;
    	
    	// Schreiben der Trefferpunkte der Waffe
    	if (TP != null) {
    		tmpElement = new Element("tp");
    		
    		for (int i = 0; i < TP.getAugenWuerfel().length; i++) {
    			if (TP.getAugenWuerfel()[i] == 6) {
    				tmpElement.addAttribute(new Attribute("W6", 
    						Integer.toString(TP.getAnzahlWuerfel()[i])));
    			} else if (TP.getAugenWuerfel()[i] == 20) {
    				tmpElement.addAttribute(new Attribute("W20", 
    						Integer.toString(TP.getAnzahlWuerfel()[i])));
    			}
    		}
    		
    		tmpElement.addAttribute(new Attribute("plus", 
    				Integer.toString(TP.getFestWert())));
    		
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben der länge der Waffe
    	if (laenge != KEIN_WERT) {
    		tmpElement = new Element("eigenschaften");
    		tmpElement.addAttribute(new Attribute("laenge", Integer.toString(laenge)));
    	}
    	
    	// Schreiben des Ini Bouns
    	if (ini != KEIN_WERT) {
    		if (tmpElement == null) {
    			tmpElement = new Element("eigenschaften");
    		}
    		tmpElement.addAttribute(new Attribute("ini", Integer.toString(ini)));
    	}
    	
    	if (tmpElement != null) {
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben der Talente
    	for (int i = 0; i < talent.length; i++) {
    		tmpElement = new Element("talentId");
    		tmpElement.appendChild(talent[i].getId());
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	return xmlElement;
    }
	
}
