/*
 * Created 26. Dezember 2004 / 23:45:31
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.Links.IdLinkList;
import org.d3s.alricg.Controller.ProgAdmin;
import org.d3s.alricg.Prozessor.SKT;
import org.d3s.alricg.Prozessor.SKT.KostenKlasse;

/**
 * <b>Beschreibung:</b><br>
 * Repr�sentiert eine Sprache.
 * @author V.Strelow
 */
public class Sprache extends SchriftSprache {
	private int komplexNichtMutterSpr;
	private KostenKlasse kostenNichtMutterSpr;
	private IdLinkList zugehoerigeSchrift;	

	/**
	 * Konstruktur; id beginnt mit "SPR-" f�r Sprache
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
	
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
    	// Auslesen der Kostenklasse der Sprache
    		kostenNichtMutterSpr = SKT.getKostenKlasseByXmlValue( xmlElement
    			.getFirstChildElement("nichtMuttersprache").getAttributeValue("kostenKlasse") );
    	
    	// Auslesen der Kompl�xit�t der Sprache
    	try {
    		komplexNichtMutterSpr = Integer.parseInt( xmlElement
    			.getFirstChildElement("nichtMuttersprache").getAttributeValue("komplexitaet") );
    	} catch (NumberFormatException exc) {
    		ProgAdmin.logger.severe("Xml-Tag konnte nicht in Zahl umgewandelt werden: " + exc);
    	}
    	
    	// Auslesen der dazugeh�rigen Schrift(en)
    	if ( xmlElement.getFirstChildElement("schriften") != null ) {
    		zugehoerigeSchrift = new IdLinkList(this);
    		zugehoerigeSchrift.loadXmlElement(xmlElement.getFirstChildElement("schriften"));
    	}
    }
    
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();

    	// Schreiben der Kostenklasse
    	xmlElement.appendChild("nichtMuttersprache");
    	xmlElement.getFirstChildElement("nichtMuttersprache").addAttribute(
    				new Attribute("kostenKlasse", kostenNichtMutterSpr.getXmlValue())
    			);
    
    	// Schreiben der Komplexit�t
    	xmlElement.getFirstChildElement("nichtMuttersprache").addAttribute(
				new Attribute("komplexitaet", Integer.toString(komplexNichtMutterSpr))
			);
    	
    	// Schreiben der dazugeh�rigen Schrift(en)
    	if ( zugehoerigeSchrift != null ) {
    		xmlElement.appendChild( zugehoerigeSchrift.writeXmlElement("schriften") );
    	}
    		
    	return xmlElement;
    }
}
