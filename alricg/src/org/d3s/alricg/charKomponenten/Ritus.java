/*
 * Created 27. Dezember 2004 / 01:48:37
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;

/**
 * <b>Beschreibung:</b><br>
 * Fasst Gemeinsamkeiten von Liturgie und Ritual zusammen und 
 * bildet die Grundlage für diese. 
 * @author V.Strelow
 */
public abstract class Ritus extends CharElement {
	private int grad;
	private String additionsId;
	private Gottheit[] gottheit;
	
	/**
	 * @return Liefert das Attribut grad.
	 */
	public int getGrad() {
		return grad;
	}
	/**
	 * @return Liefert das Attribut herkunft.
	 */
	public Gottheit[] getGottheit() {
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
	public void setGottheit(Gottheit[] gottheit) {
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
    	Elements tmpElements;
    	
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
	
    	// Auslesen der Gottheiten
    	tmpElements = xmlElement.getChildElements("gottheit");
    	gottheit = new Gottheit[tmpElements.size()];
    	for (int i = 0; i < tmpElements.size(); i++) {
    		gottheit[i] = (Gottheit) ProgAdmin.data.getCharElement(
    					tmpElements.get(i).getValue(),
    					CharKomponente.gottheit
    					);
    	}
	
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
    	for (int i = 0; i < gottheit.length; i++) {
    		tmpElement = new Element("gottheit");
    		tmpElement.appendChild(gottheit[i].getId());
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	return xmlElement;
    }
}
