/*
 * Created 20. Januar 2005 / 15:31:48
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class RegionVolk extends CharElement {
	private String bindeWortMann; // Wort zwischen Vor- und Nachnamen
	private String bindeWortFrau; // Wort zwischen Vor- und Nachnamen
	private String[] vornamenMann;
	private String[] vornamenFrau;
	private String[] nachnamen;
	private String[] nachnamenEndung; // Wörter die an den Nachnamen gehangen werden
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#getCharKomponente()
	 */
	public CharKomponente getCharKomponente() {
		return CharKomponente.region;
	}
	
	/**
	 * Konstruktur; id beginnt mit "REG-" für Region
	 * @param id Systemweit eindeutige id
	 */
	public RegionVolk(String id)  {
		setId(id);
	}

	/**
	 * @param bindeWortFrau Setzt das Attribut bindeWortFrau.
	 */
	public void setBindeWortFrau(String bindeWortFrau) {
		this.bindeWortFrau = bindeWortFrau;
	}
	/**
	 * @param bindeWortMann Setzt das Attribut bindeWortMann.
	 */
	public void setBindeWortMann(String bindeWortMann) {
		this.bindeWortMann = bindeWortMann;
	}
	/**
	 * @param nachnamen Setzt das Attribut nachnamen.
	 */
	public void setNachnamen(String[] nachnamen) {
		this.nachnamen = nachnamen;
	}
	/**
	 * @param nachnamenEndung Setzt das Attribut nachnamenEndung.
	 */
	public void setNachnamenEndung(String[] nachnamenEndung) {
		this.nachnamenEndung = nachnamenEndung;
	}
	/**
	 * @param vornamenFrau Setzt das Attribut vornamenFrau.
	 */
	public void setVornamenFrau(String[] vornamenFrau) {
		this.vornamenFrau = vornamenFrau;
	}
	/**
	 * @param vornamenMann Setzt das Attribut vornamenMann.
	 */
	public void setVornamenMann(String[] vornamenMann) {
		this.vornamenMann = vornamenMann;
	}
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
	 */
	public void loadXmlElement(Element xmlElement) {
		super.loadXmlElement(xmlElement);
		Elements tmpElements;
		
		// Auslesen des Bindewortes
		if (xmlElement.getFirstChildElement("bindeWort") != null) {
			bindeWortMann = xmlElement.getFirstChildElement("bindeWort")
												.getAttributeValue("mann");
			bindeWortFrau = xmlElement.getFirstChildElement("bindeWort")
												.getAttributeValue("frau");
		}
		
		// Auslesen der männlichen Vornamen
		if (xmlElement.getFirstChildElement("vornamenMann") != null) {
			tmpElements = xmlElement.getFirstChildElement("vornamenMann")
									.getChildElements("name");
			
			vornamenMann = new String[tmpElements.size()];
			for (int i = 0; i < vornamenMann.length; i++) {
				vornamenMann[i] = tmpElements.get(i).getValue();
			}
		}
		
		// Auslesen der weiblichen Vornamen
		if (xmlElement.getFirstChildElement("vornamenFrau") != null) {
			tmpElements = xmlElement.getFirstChildElement("vornamenFrau")
									.getChildElements("name");

			vornamenFrau= new String[tmpElements.size()];
			for (int i = 0; i < vornamenFrau.length; i++) {
				vornamenFrau[i] = tmpElements.get(i).getValue();
			}		
		}
		
		// Auslesen der Nachnamen
		if (xmlElement.getFirstChildElement("nachnamen") != null) {
			tmpElements = xmlElement.getFirstChildElement("nachnamen")
									.getChildElements("name");

			nachnamen= new String[tmpElements.size()];
			for (int i = 0; i < nachnamen.length; i++) {
				nachnamen[i] = tmpElements.get(i).getValue();
			}		
		}
		
		// Auslesen der Nachnamen
		if (xmlElement.getFirstChildElement("nachnamenEndung") != null) {
			tmpElements = xmlElement.getFirstChildElement("nachnamenEndung")
									.getChildElements("endung");

			nachnamenEndung= new String[tmpElements.size()];
			for (int i = 0; i < nachnamenEndung.length; i++) {
				nachnamenEndung[i] = tmpElements.get(i).getValue();
			}		
		}
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
	 */
	public Element writeXmlElement() {
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement, tmpElement2;
    	
    	xmlElement.setLocalName("region");
    	
    	// Schreiben der Bindeworte
    	if (bindeWortMann != null) {
    		tmpElement = new Element("bindeWort");
    		tmpElement.addAttribute(new Attribute("mann", bindeWortMann));
    		tmpElement.addAttribute(new Attribute("frau", bindeWortFrau));
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben der männlichen Vornamen
    	if (vornamenMann != null) {
    		tmpElement = new Element("vornamenMann");
    		
    		for (int i = 0; i < vornamenMann.length; i++) {
    			tmpElement2 = new Element("name");
    			tmpElement2.appendChild(vornamenMann[i].trim());
    			tmpElement.appendChild(tmpElement2);
    		}
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben der weiblicher Vornamen
    	if (vornamenFrau != null) {
    		tmpElement = new Element("vornamenFrau");
    		
    		for (int i = 0; i < vornamenFrau.length; i++) {
    			tmpElement2 = new Element("name");
    			tmpElement2.appendChild(vornamenFrau[i].trim());
    			tmpElement.appendChild(tmpElement2);
    		}
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben der Nachnamen
    	if (nachnamen != null) {
    		tmpElement = new Element("nachnamen");
    		
    		for (int i = 0; i < nachnamen.length; i++) {
    			tmpElement2 = new Element("name");
    			tmpElement2.appendChild(nachnamen[i].trim());
    			tmpElement.appendChild(tmpElement2);
    		}
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben der Endungen des Nachnamens
    	if (nachnamenEndung != null) {
    		tmpElement = new Element("nachnamenEndung");
    		
    		for (int i = 0; i < nachnamenEndung.length; i++) {
    			tmpElement2 = new Element("endung");
    			tmpElement2.appendChild(nachnamenEndung[i].trim());
    			tmpElement.appendChild(tmpElement2);
    		}
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	return xmlElement;
	}
	
}
