/*
 * Created on 01.03.2005 / 13:48:04
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.CharKomponenten.Links;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.CharKomponenten.Herkunft;
import org.d3s.alricg.CharKomponenten.CharZusatz.SimpelGegenstand;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class AuswahlAusruestung extends Auswahl {
	//protected VariableAuswahl[] varianteAuswahl;
    //private Herkunft herkunft; // Das CharElement, von dem die Auswahl kommt
	//private IdLink[] festeAuswahl; // Die unveränderlichen Werte
	
	/**
	 * Konstruktor
	 * @param herkunft Die "quelle" dieser Auswahl
	 */
	public AuswahlAusruestung(Herkunft herkunft) {
		super(herkunft);
	}
	
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen nötigen angaben
     */
    public void loadXmlElement(Element xmlElement) {
    	Elements tmpElements1, tmpElements2, tmpElementsX;
    	IdLink[] optionen;
    	int anzahl;
    	
    	// Auslesen der festen Gegenstände
    	if ( xmlElement.getFirstChildElement("fest") != null) {
    		
    		tmpElements1 = xmlElement.getFirstChildElement("fest")
    								.getChildElements("ausruestungLink");
    		tmpElements2 = xmlElement.getFirstChildElement("fest")
									.getChildElements("ausruestungNeu");
    		
    		festeAuswahl = new IdLink[tmpElements1.size() + tmpElements2.size()];
    
//    		Auslesen der XML-Tags
    		loadHelp(festeAuswahl, tmpElements1, tmpElements2);
    	}
    	
// 		Auslesen der Variablen Auswahl der Gegenstande
    	if ( xmlElement.getFirstChildElement("auswahl") != null ) {
    		tmpElementsX = xmlElement.getChildElements("auswahl");
    		
    		varianteAuswahl = new VariableAuswahl[tmpElementsX.size()];
    		
    		for ( int idx = 0; idx < tmpElementsX.size(); idx++) {
	    		tmpElements1 = tmpElementsX.get(idx).getChildElements("ausruestungLink");
				tmpElements2 = tmpElementsX.get(idx).getChildElements("ausruestungNeu");
				
				optionen = new IdLink[tmpElements1.size() + tmpElements2.size()];
				
				// Auslesen der XML-Tags
				loadHelp(optionen, tmpElements1, tmpElements2);
	    		
	    		if (tmpElementsX.get(idx).getAttribute("anzahl") != null) {
	    			anzahl = Integer.parseInt(tmpElementsX.get(idx)
	    										.getAttributeValue("anzahl"));
	    		} else {
	    			anzahl = 1;
	    		}
	    		
	    		// Schreiben der varianten Auswahl
	    		varianteAuswahl[idx] = new VariableAuswahl(
	    					Modus.ANZAHL, 
	    					new int[0],
	    					anzahl,
	    					optionen,
	    					null
	    			);
    		}
    	}
    }
    	
    /**
     * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt".
     * @param tagName Der name des Tags
     * @return Ein Xml-Element mit allen nötigen Angaben.
     */
    public Element writeXmlElement(String tagName){
    	Element xmlElement = new Element(tagName);
    	Element tmpElement;
    	
    	// Schreiben der "festen" Elemente, die nicht wählbar sind
    	if ( festeAuswahl != null ) {
    		
    		tmpElement = new Element("fest");
    		xmlElement.appendChild(tmpElement);
    		
    		// Schreiben der Daten:
    		writerHelp(festeAuswahl, tmpElement);
 
    	}
    	
    	// Schreiben der Elemente, von denen eine Auswahl getroffen werden kann
    	if ( varianteAuswahl != null) {

    		// Durchlaufen der verschiedenen Auswahlen
    		for ( int i = 0; i < varianteAuswahl.length; i++) {
        		tmpElement = new Element("auswahl");
        		xmlElement.appendChild(tmpElement);
    			
    			// Schreiben des Attributs "anzahl"
    			tmpElement.addAttribute( new Attribute("anzahl", 
    					Integer.toString(varianteAuswahl[i].getAnzahl())) );
    			
    			// Schreiben der eigentlichen daten
        		writerHelp(varianteAuswahl[i].getOptionen(), tmpElement);
    		}
    	}
    	
    	return xmlElement;
    }
    
    /**
     * Hilfsfunktion für das lesen von XML
     * @param linkAR In dieses Array werden die ausgelesenen Daten geschrieben
     * @param tmpElements1 XML-Elemente die zu anderen Elementen linken
     * @param tmpElements2 XML-Elemente die im Tag selbst Definiert werden
     */
    private void loadHelp(IdLink[] linkAR, Elements tmpElements1, Elements tmpElements2) {
    	SimpelGegenstand simpelGegen;
    	int tmpAnzahl;
    	
		// Auslesen der Gegenstände, die verlinkt werden	
		for (int i = 0; i < tmpElements1.size(); i++) {
			linkAR[i] = new IdLink(this.getHerkunft(), this);
			linkAR[i].loadXmlElement( tmpElements1.get(i) );
		}
		
		// Auslesen der Gegenstände, die erst hier Definiert werden
		for (int i = 0; i < tmpElements2.size(); i++) {
			simpelGegen = new SimpelGegenstand();
			simpelGegen.loadXmlElement(tmpElements2.get(i));
			
			linkAR[i+tmpElements1.size()] = new IdLink(this.getHerkunft(), this);
			linkAR[i+tmpElements1.size()].setZielId( simpelGegen );
			
			// Anzahl setzen
			if ( tmpElements2.get(i).getAttribute("anzahl") != null) {
				tmpAnzahl = Integer.parseInt(tmpElements2.get(i).getAttributeValue("anzahl"));
			} else {
				tmpAnzahl = 1; // Defaultz wert
			} 
			linkAR[i+tmpElements1.size()].setWert(tmpAnzahl);
		}

    }
    
    /**
     * Hilffunktion für das Schreiben von XML
     * @param linkAR Array von LinkIds, das gelesen wird um zu schreiben
     * @param xmlElement Aus diesem xmlElement wird ausgelesen
     */
    private void writerHelp(IdLink[] linkAR, Element xmlElement) {
    	Element tmpElement;
    	
		// Schreiben der Gegenstände MIT einer verlinkung zur Datenbasis
		for (int i = 0; i < linkAR.length; i++) {
			if ( !linkAR[i].getZielId().equals(SimpelGegenstand.TEMP_ID) ) {
				xmlElement.appendChild( 
						linkAR[i].writeXmlElement("ausruestungLink")
					);
			}
		}
		
		// Schreiben der Gegenstände die direkt Definiert werden
		for (int i = 0; i < linkAR.length; i++) {
			if ( linkAR[i].getZielId().equals(SimpelGegenstand.TEMP_ID) ) {
				tmpElement = ((SimpelGegenstand) linkAR[i].getZielId())
											.writeXmlElement("ausruestungNeu");
				
				// Anzahl setzen, wenn nicht Default (= 1)
				if ( linkAR[i].getWert() != 1 ) {
					tmpElement.addAttribute(new Attribute("anzahl", 
								Integer.toString(linkAR[i].getWert())		
							));	
				}
				
				// Schreibe Wert
				xmlElement.appendChild(tmpElement);
			}
		}
    }
}
