/*
 * Created 26. Dezember 2004 / 23:45:31
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.links.IdLinkList;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.prozessor.SKT;
import org.d3s.alricg.prozessor.SKT.KostenKlasse;

/**
 * <b>Beschreibung:</b><br>
 * Repräsentiert eine Sprache.
 * @author V.Strelow
 */
public class Sprache extends SchriftSprache {
	private int komplexNichtMutterSpr;
	private KostenKlasse kostenNichtMutterSpr;
	private IdLinkList zugehoerigeSchrift;	

	/**
	 * Konstruktur; id beginnt mit "SPR-" für Sprache
	 * @param id Systemweit eindeutige id
	 */
	public Sprache(String id) {
		setId(id);
	}
	
	/**
	 * @return Liefert das Attribut zugehoerigeSchrift.
	 */
	public IdLinkList getZugehoerigeSchrift() {
		return zugehoerigeSchrift;
	}
	
	/**
	 * @return Liefert das Attribut komplexNichtMutterSpr.
	 */
	public int getKomplexNichtMutterSpr() {
		return komplexNichtMutterSpr;
	}
	/**
	 * @return Liefert das Attribut kostenNichtMutterSpr.
	 */
	public KostenKlasse getKostenNichtMutterSpr() {
		return kostenNichtMutterSpr;
	}
	
	/**
	 * @param komplexNichtMutterSpr Setzt das Attribut komplexNichtMutterSpr.
	 */
	public void setKomplexNichtMutterSpr(int komplexNichtMutterSpr) {
		this.komplexNichtMutterSpr = komplexNichtMutterSpr;
	}
	/**
	 * @param kostenNichtMutterSpr Setzt das Attribut kostenNichtMutterSpr.
	 */
	public void setKostenNichtMutterSpr(KostenKlasse kostenNichtMutterSpr) {
		this.kostenNichtMutterSpr = kostenNichtMutterSpr;
	}
	/**
	 * @param zugehoerigeSchrift Setzt das Attribut zugehoerigeSchrift.
	 */
	public void setZugehoerigeSchrift(IdLinkList zugehoerigeSchrift) {
		this.zugehoerigeSchrift = zugehoerigeSchrift;
	}
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
    	// Auslesen der Kostenklasse der Sprache
    		kostenNichtMutterSpr = SKT.getKostenKlasseByXmlValue( xmlElement
    			.getFirstChildElement("nichtMuttersprache").getAttributeValue("kostenKlasse") );
    	
    	// Auslesen der Kompläxität der Sprache
    	try {
    		komplexNichtMutterSpr = Integer.parseInt( xmlElement
    			.getFirstChildElement("nichtMuttersprache").getAttributeValue("komplexitaet") );
    	} catch (NumberFormatException exc) {
    		ProgAdmin.logger.severe("Xml-Tag konnte nicht in Zahl umgewandelt werden: " + exc);
    	}
    	
    	// Auslesen der dazugehörigen Schrift(en)
    	if ( xmlElement.getFirstChildElement("schriften") != null ) {
    		zugehoerigeSchrift = new IdLinkList(this);
    		zugehoerigeSchrift.loadXmlElement(xmlElement.getFirstChildElement("schriften"));
    	}
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();

    	xmlElement.setLocalName("sprache");
    	
    	// Schreiben der Kostenklasse
    	xmlElement.appendChild(new Element("nichtMuttersprache"));
    	xmlElement.getFirstChildElement("nichtMuttersprache").addAttribute(
    				new Attribute("kostenKlasse", kostenNichtMutterSpr.getXmlValue())
    			);
    
    	// Schreiben der Komplexität
    	xmlElement.getFirstChildElement("nichtMuttersprache").addAttribute(
				new Attribute("komplexitaet", Integer.toString(komplexNichtMutterSpr))
			);
    	
    	// Schreiben der dazugehörigen Schrift(en)
    	if ( zugehoerigeSchrift != null ) {
    		xmlElement.appendChild( zugehoerigeSchrift.writeXmlElement("schriften") );
    	}
    		
    	return xmlElement;
    }
}
