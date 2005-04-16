/*
 * Created 22. Dezember 2004 / 01:07:12
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
/**
 * <b>Beschreibung: </b> <br>
 * Dies ist die super-Klasse f�r alle Charakter-Elemente. Alle Elemente eines
 * Charakters in Objekte von diesem Typ.
 * 
 * @author V.Strelow
 */
abstract public class CharElement implements Comparable<CharElement> {
	public static int KEIN_WERT = -100;
    private String id; // Programmweit eindeutige ID
    private String name; // Name des Element
    private String sammelBegriff = ""; // Zur besseren Sortierung
    private String beschreibung = ""; // text, z.B.  "Siehe MBK S. 10"
    private RegelAnmerkung regelAnmerkung; // Anmerkungen f�r den User!
    private SonderRegel sonderRegel; // Zu beachtende Sonderreglen

    /**
     * Soll das Element angezeigt werden? Sinnvoll f�r und VorNachteile die in
     * der Gui anders verwendet werden, wie z.B. Herausragende Eigenschaft.
     * Diese soll unter den Vorteilen nicht w�hlbar sein, da sie bei den
     * Eigenschaften ber�cksichtigt wird. Das Element wird grau dargestellt,will
     * man es w�hlen erscheint der Text ("Kann nur bei ... gew�hlt werden.")
     */
    private boolean anzeigen = true; // Default Wert
    private String anzeigenText;

    /**
     * Setzt die ID des Elements neu. Vor allem Benutzt von abgeleiteten Klassen
     * @param id Neue Id des Elements
     */
    protected void setId(String id) {
    	assert id != null;
    	
    	this.id = id;
    }
    
    /**
     * Wird von allen Konstruktoren aufgerufen
     * @return Die eindeutige, einmalige ID des Elements
     */
    public String getId() {
        return id;
    }

    /**
     * @return Der Text, der ausgegeben wird, wenn das Element nicht f�r
     * die Anzeige bestimmt ist. Gilt es solch einen Text nicht, dann "null"
     */
    public String getWirdAngezeigtText() {
        return anzeigenText;
    }
    
    /**
     * @return True - Wenn das CharElement als w�hlbar angezeigt werden soll 
     *  (Default), ansonsten ist das Element nicht w�hlbar und kann an 
     *  anderer Stelle abgerufen werden (Hinweise liefert der TEXT)
     */
    public boolean isAnzeigen() {
        return anzeigen;
    }

    /**
     * "beschreibung" ist ein allgemeiner Text des CharElements
     * 
     * @return Liefert eine Beschreibung des Elements, mei�t einen Verweis
     *  auf ein Regelbuch.
     */
    public String getBeschreibung() {
        return beschreibung;
    }
    
    
    /**
     * @param besch Der neue text f�r die Beschreibung
     */
    protected void setBeschreibung(String besch) {
    	beschreibung = besch;
    }

    /**
     * @return Der Name des Elements, der auch angezeigt werden soll.
     */
    public String getName() {
        return name;
    }

    /**
     * "regelAnmerkung" treten auf, wenn ALRICG eine Regel nicht automatisch
     * umsetzen kann. Bei der Wahl des Elements wird dann die Regelanmerkung
     * angezeigt.
     *
     * @return Liefert die regelAnmerkung. Gibt es keine Anmerkungen, liefert
     * die Methode "null".
     */
    public RegelAnmerkung getRegelAnmerkung() {
        return regelAnmerkung;
    }

    /**
     * Der "sammelBegriff" dient einer besseren Struckturierung, zu k�nnen z.B.
     * mehrer Zwergenrassen unter "Zwerge" gesammelt werden.
     * 
     * @return Liefert das Attribut sammelBegriff.
     */
    public String getSammelBegriff() {
        return sammelBegriff;
    }
    
    /** 
     * @return true Dieses Element verf�gt �ber einen Sammelbegriff, 
     * 			sonst false.
     */
    public boolean hasSammelBegriff() {
    	return (!sammelBegriff.equals(""));
    }
    /**
     * Falls dieses Element eine besondere Behandlung durch das Programm 
     * ben�tigt, so besitzt es eine "Sonderregel". Andernfalls liefert die 
     * Methode "null".
     * 
     * @return Liefert das Attribut sonderRegel.
     */
    public SonderRegel getSonderRegel() {
        return sonderRegel;
    }
    
    /** 
     * Methode �berschrieben
     * @see java.lang.Object#toString()
     * @return Den namen das Elements f�r die Anzeige in der GUI
     */
    public String toString() {
        return name;
    }
        
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * Diese Methode wird von jeder Klasse �berschrieben, es wird 
     * stehts die Methode der super-klasse aufgerufen und dann
     * die eigenden informationen zus�tzlich ausgelesen.
     * @param xmlElement Das Xml-Element mit allen n�tigen angaben
     */
    public void loadXmlElement(Element xmlElement) {
    	Elements tmpElements;
    	
// 		Auslesen des Namen ( = 1 )
    	this.name = xmlElement.getAttributeValue("name");
    	
// 		Auslesen der Sonderregel ( min: 0 / max: 1
    	if (xmlElement.getFirstChildElement("sonderregel") != null) {
    		
    		// TODO Manager f�r Sonderregeln einbauen!
    		/*
    		this.sonderRegel = new SonderRegel(
    				xmlElement.getFirstChildElement("sonderregel").getAttributeValue("id"),
    				xmlElement.getFirstChildElement("sonderregel").getValue().trim()
    			);
    		*/
    		
    	}
    	
// 		Auslesen der "Anzeigen" Elements - Boolean + Text ( min: 0 / max: 1)
    	if ( xmlElement.getFirstChildElement("anzeigen") != null ) {
    		
    		// Pr�fung, ob der text auch nur "true" und "false" enth�lt
    		assert xmlElement.getFirstChildElement("anzeigen")
			  				.getAttributeValue("anzeigen").equals("true") || 
			  			xmlElement.getFirstChildElement("anzeigen")
			  				.getAttributeValue("anzeigen").equals("false");
    		
    		if ( xmlElement.getFirstChildElement("anzeigen")
					  .getAttributeValue("anzeigen").equals("true") ) {
    			this.anzeigen = true; // Entspricht dem Default wert -> Kein Text n�tig
    		} else {
    			this.anzeigen = false;
    			this.anzeigenText = xmlElement.getFirstChildElement("anzeigen").getValue().trim();
    		}
    	}

//		Hinzuf�gen der Regelanmerkungen (min: 0 / max: 1)
    	if ( xmlElement.getFirstChildElement("regelAnmerkungen") != null ) {
    		regelAnmerkung = new RegelAnmerkung(); // RegelAnmerkung erzeugen
    		tmpElements = xmlElement.getFirstChildElement("regelAnmerkungen")
    								.getChildElements("regel");

    		// Hinzuf�gen der einzelnen RegelAnmerkungen (min: 1 / max: beliebig)
    		for (int i = 0; i < tmpElements.size(); i++) {
    			regelAnmerkung.add(
    				tmpElements.get(i).getValue().trim(),
    				tmpElements.get(i).getAttributeValue("modus")
    			);
    		}
    	}
// 		Hinzuf�gen der Beschreibung (min: 0 / max: 1)
    	if ( xmlElement.getFirstChildElement("beschreibung") != null ) {
    		this.beschreibung = xmlElement.getFirstChildElement("beschreibung").getValue().trim();
    	}
    	
// 		Hinzuf�gen des Sammelbegriffs
    	if ( xmlElement.getFirstChildElement("sammelbegriff") != null ) {
    		this.sammelBegriff = xmlElement.getFirstChildElement("sammelbegriff").getValue().trim();
    		
    	}
    }
    
    /**
     * Dient zur Speicherung (also f�r den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt".
     * Diese Methode wird von jeder Klasse �berschrieben, es wird 
     * stehts die Methode der super-klasse aufgerufen und dann
     * die eigenden informationen zus�tzlich hinzugef�gt.
     * @return Ein Xml-Element mit allen n�tigen Angaben.
     */
    public Element writeXmlElement(){
		Element element, tmpElement;
		
		element = new Element("temp"); // Mu� in den Unterklassen neu gesetzt werden!
		
		element.addAttribute( new Attribute("id", this.id) );
		element.addAttribute( new Attribute("name", this.name) );
		
		// Beschreibung einf�gen
		if (this.beschreibung.length() > 0) {
			tmpElement = new Element("beschreibung");
			tmpElement.appendChild(this.beschreibung);
			
			element.appendChild( tmpElement );
		}
		
		// Anzeigen einf�gen
		if (this.anzeigenText != null) {
			tmpElement = new Element("anzeigen");
			
			if (this.anzeigen) {
				tmpElement.addAttribute( new Attribute("anzeigen", "true") );
			} else {
				tmpElement.addAttribute( new Attribute("anzeigen", "false") );
			}
			tmpElement.appendChild(this.anzeigenText);
			element.appendChild( tmpElement );
		}
		
		// Sonderregel einf�gen
		if (this.sonderRegel != null) {
			tmpElement = new Element("sonderregel");
			
			tmpElement.addAttribute( new Attribute("id", this.sonderRegel.getId()) );
			tmpElement.appendChild( this.sonderRegel.getBeschreibung() );
			
			element.appendChild( tmpElement );
		}
		
		// Regelanmerkungen einf�gen
		if (this.regelAnmerkung != null) {
			element.appendChild( regelAnmerkung.writeXmlElement() );
		}
		
		// Sammelbegriff einf�gen
		if (this.sammelBegriff.length() > 0) {
			tmpElement = new Element("sammelbegriff");
			tmpElement.appendChild(this.sammelBegriff);
			
			element.appendChild( tmpElement );
		}
    	
		return element;
    }
    
    /**
     * Erm�glicht einen vergleich zwischen CharElementen. Dieser Vergelich wird
     * anhand der ID-Strings durchgef�hrt. Vor allem wichtig um Arrays mit CharElementen
     * sortieren zu k�nnen!
     * @see java.lang.compareTo()
     * @param ce Das CharElement, mit dem verglichen werden soll
     * @return "a negative integer, zero, or a positive integer as this object is less than,
     * 				 equal to, or greater than the specified object."
     */
    public int compareTo(CharElement ce) {
    	return id.compareTo(ce.getId());
    }
    
}
