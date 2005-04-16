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
 * Dies ist die super-Klasse für alle Charakter-Elemente. Alle Elemente eines
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
    private RegelAnmerkung regelAnmerkung; // Anmerkungen für den User!
    private SonderRegel sonderRegel; // Zu beachtende Sonderreglen

    /**
     * Soll das Element angezeigt werden? Sinnvoll für und VorNachteile die in
     * der Gui anders verwendet werden, wie z.B. Herausragende Eigenschaft.
     * Diese soll unter den Vorteilen nicht wählbar sein, da sie bei den
     * Eigenschaften berücksichtigt wird. Das Element wird grau dargestellt,will
     * man es wählen erscheint der Text ("Kann nur bei ... gewählt werden.")
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
     * @return Der Text, der ausgegeben wird, wenn das Element nicht für
     * die Anzeige bestimmt ist. Gilt es solch einen Text nicht, dann "null"
     */
    public String getWirdAngezeigtText() {
        return anzeigenText;
    }
    
    /**
     * @return True - Wenn das CharElement als wählbar angezeigt werden soll 
     *  (Default), ansonsten ist das Element nicht wählbar und kann an 
     *  anderer Stelle abgerufen werden (Hinweise liefert der TEXT)
     */
    public boolean isAnzeigen() {
        return anzeigen;
    }

    /**
     * "beschreibung" ist ein allgemeiner Text des CharElements
     * 
     * @return Liefert eine Beschreibung des Elements, meißt einen Verweis
     *  auf ein Regelbuch.
     */
    public String getBeschreibung() {
        return beschreibung;
    }
    
    
    /**
     * @param besch Der neue text für die Beschreibung
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
     * Der "sammelBegriff" dient einer besseren Struckturierung, zu können z.B.
     * mehrer Zwergenrassen unter "Zwerge" gesammelt werden.
     * 
     * @return Liefert das Attribut sammelBegriff.
     */
    public String getSammelBegriff() {
        return sammelBegriff;
    }
    
    /** 
     * @return true Dieses Element verfügt über einen Sammelbegriff, 
     * 			sonst false.
     */
    public boolean hasSammelBegriff() {
    	return (!sammelBegriff.equals(""));
    }
    /**
     * Falls dieses Element eine besondere Behandlung durch das Programm 
     * benötigt, so besitzt es eine "Sonderregel". Andernfalls liefert die 
     * Methode "null".
     * 
     * @return Liefert das Attribut sonderRegel.
     */
    public SonderRegel getSonderRegel() {
        return sonderRegel;
    }
    
    /** 
     * Methode überschrieben
     * @see java.lang.Object#toString()
     * @return Den namen das Elements für die Anzeige in der GUI
     */
    public String toString() {
        return name;
    }
        
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * Diese Methode wird von jeder Klasse Überschrieben, es wird 
     * stehts die Methode der super-klasse aufgerufen und dann
     * die eigenden informationen zusätzlich ausgelesen.
     * @param xmlElement Das Xml-Element mit allen nötigen angaben
     */
    public void loadXmlElement(Element xmlElement) {
    	Elements tmpElements;
    	
// 		Auslesen des Namen ( = 1 )
    	this.name = xmlElement.getAttributeValue("name");
    	
// 		Auslesen der Sonderregel ( min: 0 / max: 1
    	if (xmlElement.getFirstChildElement("sonderregel") != null) {
    		
    		// TODO Manager für Sonderregeln einbauen!
    		/*
    		this.sonderRegel = new SonderRegel(
    				xmlElement.getFirstChildElement("sonderregel").getAttributeValue("id"),
    				xmlElement.getFirstChildElement("sonderregel").getValue().trim()
    			);
    		*/
    		
    	}
    	
// 		Auslesen der "Anzeigen" Elements - Boolean + Text ( min: 0 / max: 1)
    	if ( xmlElement.getFirstChildElement("anzeigen") != null ) {
    		
    		// Prüfung, ob der text auch nur "true" und "false" enthält
    		assert xmlElement.getFirstChildElement("anzeigen")
			  				.getAttributeValue("anzeigen").equals("true") || 
			  			xmlElement.getFirstChildElement("anzeigen")
			  				.getAttributeValue("anzeigen").equals("false");
    		
    		if ( xmlElement.getFirstChildElement("anzeigen")
					  .getAttributeValue("anzeigen").equals("true") ) {
    			this.anzeigen = true; // Entspricht dem Default wert -> Kein Text nötig
    		} else {
    			this.anzeigen = false;
    			this.anzeigenText = xmlElement.getFirstChildElement("anzeigen").getValue().trim();
    		}
    	}

//		Hinzufügen der Regelanmerkungen (min: 0 / max: 1)
    	if ( xmlElement.getFirstChildElement("regelAnmerkungen") != null ) {
    		regelAnmerkung = new RegelAnmerkung(); // RegelAnmerkung erzeugen
    		tmpElements = xmlElement.getFirstChildElement("regelAnmerkungen")
    								.getChildElements("regel");

    		// Hinzufügen der einzelnen RegelAnmerkungen (min: 1 / max: beliebig)
    		for (int i = 0; i < tmpElements.size(); i++) {
    			regelAnmerkung.add(
    				tmpElements.get(i).getValue().trim(),
    				tmpElements.get(i).getAttributeValue("modus")
    			);
    		}
    	}
// 		Hinzufügen der Beschreibung (min: 0 / max: 1)
    	if ( xmlElement.getFirstChildElement("beschreibung") != null ) {
    		this.beschreibung = xmlElement.getFirstChildElement("beschreibung").getValue().trim();
    	}
    	
// 		Hinzufügen des Sammelbegriffs
    	if ( xmlElement.getFirstChildElement("sammelbegriff") != null ) {
    		this.sammelBegriff = xmlElement.getFirstChildElement("sammelbegriff").getValue().trim();
    		
    	}
    }
    
    /**
     * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt".
     * Diese Methode wird von jeder Klasse Überschrieben, es wird 
     * stehts die Methode der super-klasse aufgerufen und dann
     * die eigenden informationen zusätzlich hinzugefügt.
     * @return Ein Xml-Element mit allen nötigen Angaben.
     */
    public Element writeXmlElement(){
		Element element, tmpElement;
		
		element = new Element("temp"); // Muß in den Unterklassen neu gesetzt werden!
		
		element.addAttribute( new Attribute("id", this.id) );
		element.addAttribute( new Attribute("name", this.name) );
		
		// Beschreibung einfügen
		if (this.beschreibung.length() > 0) {
			tmpElement = new Element("beschreibung");
			tmpElement.appendChild(this.beschreibung);
			
			element.appendChild( tmpElement );
		}
		
		// Anzeigen einfügen
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
		
		// Sonderregel einfügen
		if (this.sonderRegel != null) {
			tmpElement = new Element("sonderregel");
			
			tmpElement.addAttribute( new Attribute("id", this.sonderRegel.getId()) );
			tmpElement.appendChild( this.sonderRegel.getBeschreibung() );
			
			element.appendChild( tmpElement );
		}
		
		// Regelanmerkungen einfügen
		if (this.regelAnmerkung != null) {
			element.appendChild( regelAnmerkung.writeXmlElement() );
		}
		
		// Sammelbegriff einfügen
		if (this.sammelBegriff.length() > 0) {
			tmpElement = new Element("sammelbegriff");
			tmpElement.appendChild(this.sammelBegriff);
			
			element.appendChild( tmpElement );
		}
    	
		return element;
    }
    
    /**
     * Ermöglicht einen vergleich zwischen CharElementen. Dieser Vergelich wird
     * anhand der ID-Strings durchgeführt. Vor allem wichtig um Arrays mit CharElementen
     * sortieren zu können!
     * @see java.lang.compareTo()
     * @param ce Das CharElement, mit dem verglichen werden soll
     * @return "a negative integer, zero, or a positive integer as this object is less than,
     * 				 equal to, or greater than the specified object."
     */
    public int compareTo(CharElement ce) {
    	return id.compareTo(ce.getId());
    }
    
}
