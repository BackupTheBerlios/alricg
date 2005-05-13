/*
 * Created 26. Dezember 2004 / 22:58:17
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.links.IdLinkList;
import org.d3s.alricg.controller.ProgAdmin;

/**
 * <b>Beschreibung:</b><br>
 * Fast gemeinsamkeiten von Vor- und Nachteilen zusammen und bildet die 
 * Grundlage für diese.
 * @author V.Strelow
 */
public abstract class VorNachteil extends Fertigkeit {
	private int stufenSchritt = 1; // 
	private int kostenProSchritt = 1; // wieviele GP pro Stufe (+ feste GPKosten)
	private int minStufe = 1;
	private int maxStufe = 1;
	//private IdLinkList verbietetVorteil; 
	//private IdLinkList verbietetNachteil;
	private IdLinkList aendertApSf;
	private IdLinkList aendertGpVorteil;
	private IdLinkList aendertGpNachteil;

	/**
	 * @return Liefert das Attribut aendertApSf.
	 */
	public IdLinkList getAendertApSf() {
		return aendertApSf;
	}
	/**
	 * @return Liefert das Attribut aendertGpNachteil.
	 */
	public IdLinkList getAendertGpNachteil() {
		return aendertGpNachteil;
	}
	/**
	 * @return Liefert das Attribut aendertGpVorteil.
	 */
	public IdLinkList getAendertGpVorteil() {
		return aendertGpVorteil;
	}

	/**
	 * @return Liefert das Attribut maxStufe.
	 */
	public int getMaxStufe() {
		return maxStufe;
	}
	/**
	 * @return Liefert das Attribut minStufe.
	 */
	public int getMinStufe() {
		return minStufe;
	}
	
	/**
	 * @param aendertApSf Setzt das Attribut aendertApSf.
	 */
	public void setAendertApSf(IdLinkList aendertApSf) {
		this.aendertApSf = aendertApSf;
	}
	/**
	 * @param aendertGpNachteil Setzt das Attribut aendertGpNachteil.
	 */
	public void setAendertGpNachteil(IdLinkList aendertGpNachteil) {
		this.aendertGpNachteil = aendertGpNachteil;
	}
	/**
	 * @param aendertGpVorteil Setzt das Attribut aendertGpVorteil.
	 */
	public void setAendertGpVorteil(IdLinkList aendertGpVorteil) {
		this.aendertGpVorteil = aendertGpVorteil;
	}
	/**
	 * @param maxStufe Setzt das Attribut maxStufe.
	 */
	public void setMaxStufe(int maxStufe) {
		this.maxStufe = maxStufe;
	}
	/**
	 * @param minStufe Setzt das Attribut minStufe.
	 */
	public void setMinStufe(int minStufe) {
		this.minStufe = minStufe;
	}

    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
		
    	try {
    		// Auslesen pro wie viele Punkte die GP Kosten gelten 
    		if ( xmlElement.getAttribute("kostenProSchritt") != null ) {
    			kostenProSchritt = Integer.parseInt(xmlElement.getAttributeValue("kostenProSchritt"));
    		}
    		
    		// Auslesen in welchen Schritten gesteigert werden darf 
    		if ( xmlElement.getAttribute("stufenSchritt") != null ) {
    			stufenSchritt = Integer.parseInt(xmlElement.getAttributeValue("stufenSchritt"));
    		}
    		
	    	// Auslesen wie of der Vor/Nachteil wählbar ist
	    	/*if ( xmlElement.getFirstChildElement("istMehrfachWaehlbar") != null ) {
	    		istMehrfachWaehlbar = Integer.parseInt(xmlElement
	    						.getFirstChildElement("istMehrfachWaehlbar").getValue());
	    	}*/
	    	
	    	// Auslesen der Stufengrenzen
	    	if ( xmlElement.getFirstChildElement("stufenGrenzen") != null ) {
	    		if ( xmlElement.getFirstChildElement("stufenGrenzen")
	    				.getAttribute("minStufe") != null ) 
	    		{
	    			minStufe = Integer.parseInt(xmlElement.getFirstChildElement("stufenGrenzen")
		    				.getAttributeValue("minStufe"));
	    		}
	    		if ( xmlElement.getFirstChildElement("stufenGrenzen")
	    				.getAttribute("maxStufe") != null ) 
	    		{
	    			maxStufe = Integer.parseInt(xmlElement.getFirstChildElement("stufenGrenzen")
		    				.getAttributeValue("maxStufe"));
	    		}
	    	}
		} catch (NumberFormatException exc) {
			// TODO Bessere Fehlermeldung einbauen
			ProgAdmin.logger.severe("Fehler beim Umrechnung von Zahlen: " + exc);
		}
		
		/*
    	// Auslesen welche Vorteile mit diesem Vor/Nachteil verboten sind
    	if ( xmlElement.getFirstChildElement("verbietetVorteil") != null ) {
    		verbietetVorteil = new IdLinkList(this);
    		verbietetVorteil.loadXmlElement(xmlElement.getFirstChildElement("verbietetVorteil"));
    	}
    	
    	// Auslesen welche Nachteile mit diesem Vor/Nachteil verboten sind
    	if ( xmlElement.getFirstChildElement("verbietetNachteil") != null ) {
    		verbietetNachteil = new IdLinkList(this);
    		verbietetNachteil.loadXmlElement(xmlElement.getFirstChildElement("verbietetNachteil"));
    	}
    	*/
    	
    	// Auslesen bei welchen SF der Vor/Nachteil die Kosten verändert
    	if ( xmlElement.getFirstChildElement("aendertApSf") != null ) {
    		aendertApSf = new IdLinkList(this);
    		aendertApSf.loadXmlElement(xmlElement.getFirstChildElement("aendertApSf"));
    	}
    	
    	// Auslesen bei welchen SF der Vor/Nachteil die Kosten verändert
    	if ( xmlElement.getFirstChildElement("aendertGpVorteil") != null ) {
    		aendertGpVorteil =  new IdLinkList(this);
    		aendertGpVorteil.loadXmlElement(xmlElement.getFirstChildElement("aendertGpVorteil"));
    	}
    	
    	// Auslesen bei welchen SF der Vor/Nachteil die Kosten verändert
    	if ( xmlElement.getFirstChildElement("aendertGpNachteil") != null ) {
    		aendertGpNachteil =  new IdLinkList(this);
    		aendertGpNachteil.loadXmlElement(xmlElement.getFirstChildElement("aendertGpNachteil"));
    	}
	
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	// Schreiben wieviele GP ein Schritt kostet
    	if ( kostenProSchritt != 1 ) {
    		xmlElement.addAttribute(new Attribute("kostenProSchritt", Integer.toString(kostenProSchritt)));
    	}
    	
    	// Schreiben in welchen Schritten gesteigert werden kann
    	if ( stufenSchritt != 1 ) {
    		xmlElement.addAttribute(new Attribute("stufenSchritt", Integer.toString(stufenSchritt)));
    	}
    	
    	// Schreiben wie oft der Vor/Nachteil wählbar ist
    	/*if ( istMehrfachWaehlbar != 1 ) {
    		tmpElement = new Element("istMehrfachWaehlbar");
    		tmpElement.appendChild(Integer.toString(istMehrfachWaehlbar));
    		xmlElement.appendChild(tmpElement);
    	}*/
    	
    	//schreiben der Stufengrenzen
    	if ( minStufe != 1 || maxStufe != 1 ) {
    		tmpElement = new Element("stufenGrenzen");
    		
    		if ( minStufe != 1 ) {
    			tmpElement.addAttribute(new Attribute("minStufe", Integer.toString(minStufe)));
    		}
    		if ( maxStufe != 1 ) {
    			tmpElement.addAttribute(new Attribute("maxStufe", Integer.toString(maxStufe)));
    		}
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	/*
    	// Schreiben welche Vorteile verboten sind
    	if ( verbietetVorteil != null ) {
    		xmlElement.appendChild(verbietetVorteil.writeXmlElement("verbietetVorteil"));
    	}
    	
    	// Schreiben welche Nachteile verboten sind
    	if ( verbietetNachteil != null ) {
    		xmlElement.appendChild(verbietetNachteil.writeXmlElement("verbietetNachteil"));
    	}
    	*/
    	
    	// Schreiben welche Kosten von SF sich wie ändern
    	if ( aendertApSf != null ) {
    		xmlElement.appendChild(aendertApSf.writeXmlElement("aendertApSf"));
    	}
    	
    	// Schreiben welche Kosten von Vorteilen sich wie ändern
    	if ( aendertGpVorteil != null ) {
    		xmlElement.appendChild(aendertGpVorteil.writeXmlElement("aendertGpVorteil"));
    	}
    	
    	// Schreiben welche Kosten von Nachteilen sich wie ändern
    	if ( aendertGpNachteil != null ) {
    		xmlElement.appendChild(aendertGpNachteil.writeXmlElement("aendertGpNachteil"));
    	}
    	
    	return xmlElement;
    }
}
