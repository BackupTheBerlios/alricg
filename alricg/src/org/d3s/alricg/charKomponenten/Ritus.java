/*
 * Created 27. Dezember 2004 / 01:48:37
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import nu.xom.Element;

import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;

/**
 * <b>Beschreibung:</b><br>
 * Fasst Gemeinsamkeiten von Liturgie und Ritual zusammen und 
 * bildet die Grundlage für diese. 
 * @author V.Strelow
 */
public abstract class Ritus extends CharElement {
	private int grad;
	private String additionsId;
	private Gottheit gottheit;
	
	/**
	 * @return Liefert das Attribut grad.
	 */
	public int getGrad() {
		return grad;
	}
	/**
	 * @return Liefert das Attribut herkunft.
	 */
	public Gottheit getHerkunft() {
		return gottheit;
	}
	
	/**
	 * Id von gleichartigen Liturgien, diese ist wichtig da bei zusammengehörigen
	 * Liturgien kosten anderes berechnet werden. Gleiche AdditionsIds zeigen
	 * zusammengehörigkeit an.
	 * @return Liefert das Attribut additionsId.
	 */
	public String getAdditionsId() {
		return additionsId;
	}
	
	/**
	 * @param additionsId Setzt das Attribut additionsId.
	 */
	public void setAdditionsId(String additionsId) {
		this.additionsId = additionsId;
	}
	/**
	 * @param gottheit Setzt das Attribut gottheit.
	 */
	public void setGottheit(Gottheit gottheit) {
		this.gottheit = gottheit;
	}
	/**
	 * @param grad Setzt das Attribut grad.
	 */
	public void setGrad(int grad) {
		this.grad = grad;
	}
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
    	// Auslesen des Grades der Liturgie/ Ritual
    	try {
    		grad = Integer.parseInt(xmlElement.getFirstChildElement("grad").getValue());
    	} catch (NumberFormatException exc) {
    		// TODO Bessere Fehlermeldung einbauen
    		ProgAdmin.logger.severe("String konnte nicht in Nummer umgewandelt werden: " 
    				+ exc.toString());
    	}
    	
    	// Auslesen der AdditionsId
    	additionsId = xmlElement.getFirstChildElement("additionsId").getValue();
	
    	// Auslesen der Gottheit
    	gottheit = (Gottheit) ProgAdmin.charKompAdmin.getCharElement(
    			xmlElement.getFirstChildElement("gottheit").getValue(),
    			CharKomponente.gottheit
    	);
	
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	// Schreiben des Grades der Liturgie/ Ritual
    	tmpElement = new Element("grad");
    	tmpElement.appendChild(Integer.toString(grad));
    	xmlElement.appendChild(tmpElement);
    	
    	// Schreiben der AdditionsID
    	tmpElement = new Element("additionsId");
    	tmpElement.appendChild(additionsId);
    	xmlElement.appendChild(tmpElement);
    	
    	// Schreiben der gottheit
    	tmpElement = new Element("gottheit");
    	tmpElement.appendChild(gottheit.getId());
    	xmlElement.appendChild(tmpElement);
    	
    	return xmlElement;
    }
}
