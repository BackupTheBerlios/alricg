/*
 * Created on 25.02.2005 / 19:13:45
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.links;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;

/**
 * <u>Beschreibung:</u><br> 
 * Beschreibt eine Liste von mehreren IdLinks. Im Gegensatz zu der Auswahl sind hier alle
 * IDs "gleichberechtigt".
 * @author V. Strelow
 */
public class IdLinkList {
	IdLink[] links;
	CharElement quelle;
	
	/**
	 * Konstruktor.
	 * @param quelle Das Element, welches die IdLinkListe "besitzt"
	 */
	public IdLinkList(CharElement quelle) {
		this.quelle = quelle;
	}
		
	/**
	 * @return Liefert das Attribut links.
	 */
	public IdLink[] getLinks() {
		return links;
	}
	/**
	 * @param links Setzt das Attribut links.
	 */
	public void setLinks(IdLink[] links) {
		this.links = links;
	}
	/**
	 * @return Liefert das Attribut quelle.
	 */
	public CharElement getQuelle() {
		return quelle;
	}
	/**
	 * @param quelle Setzt das Attribut quelle.
	 */
	public void setQuelle(CharElement quelle) {
		this.quelle = quelle;
	}
	/**
	 * Ließt aus einem XmlElement alle nötigen Daten der LinkIds aus
	 * @param xmlElement Ein -Tag als XmlElement von eine IdLink-Liste
	 */
    public void loadXmlElement(Element xmlElement) {
    	String[] ids;
    	Elements tmpElements;
    	
    	// Auslesen der Tags
    	if ( xmlElement.getAttribute("ids") != null ) {
    		ids = xmlElement.getAttributeValue("ids").split(" ");
    	} else {
    		ids = new String[0];
    	}
    	tmpElements = xmlElement.getChildElements("linkId");
    	
    	// Erstellen des link-Objekts
    	links = new IdLink[ids.length + tmpElements.size()];
    	
    	// Einlesen der IdLinks aus dem Attribut
    	for (int i = 0; i < ids.length; i++) {
    		links[i] = new IdLink(quelle, null);
    		links[i].loadFromId(ids[i]);
    	}
    	
    	// Einlesen der IdLinks mit weiteren Eigenschaften
    	for (int i = ids.length; i < (tmpElements.size()+ids.length); i++){
    		links[i] = new IdLink(quelle, null);
    		links[i].loadXmlElement(tmpElements.get(i - ids.length));
    	}
    	
    }
    
    /**
     * Schreibt diese IdLink-Liste in ein XML Element.
     * @param tagName Der name des Tags
     * @return XmlElement mit allen nötigen Daten der IdLink-Liste
     */
    public Element writeXmlElement(String tagName){
    	Element xmlElement, tmpElement;
    	StringBuffer strBuffer = new StringBuffer();
    	
    	xmlElement = new Element(tagName);
    	
    	// Schreiben des Attributes
    	for (int i = 0; i < links.length; i++) {
    		
    		if (links[i].getZweitZiel() != null 
    				|| links[i].getText() != null
    				|| links[i].getWert() != IdLink.KEIN_WERT
    				|| links[i].isLeitwert() != false) {
    			
    			// Schreiben in Option, zusätzliche Angaben nötig
    			tmpElement = links[i].writeXmlElement("linkId");
    			xmlElement.appendChild(tmpElement);
    			
    		} else { // Schreiben in Attribut, nur Id nötig
    			strBuffer.append(links[i].getZiel().getId());
    			strBuffer.append(" ");
    		}
    	}
    	
    	// Attribut hinzufügen, falls mindestens ein element vorhanden
    	if (strBuffer.toString().trim().equals("VOR-String VOR-String VOR-String")) {
    		System.out.println("x");
    	}
    	
    	
    	if ( strBuffer.length() > 0 ) {
    		xmlElement.addAttribute(new Attribute("ids", strBuffer.toString().trim()) );
    	}
    	
    	return xmlElement;
    }

}
