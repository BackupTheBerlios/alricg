/*
 * Created 22. Dezember 2004 / 14:23:42
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.Links;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.CharKomponenten.Herkunft;
import org.d3s.alricg.Controller.ProgAdmin;

/**
 * <b>Beschreibung:</b><br>
 * Wenn es möglich ist, das der Benutzer unter mehreren Möglichtkeiten wählt, so
 * wird diese Klasse zur Speicherung der Elemente benutzt.
 * 
 * @author V.Strelow
 */
public class Auswahl {
	
	/**
	 *Gibt den Modus der Auswahl an
	 */
	public enum Modus {
		LISTE("LISTE"), ANZAHL("ANZAHL"), VERTEILUNG("VERTEILUNG");
		
		private String xmlWert;
		
		private Modus(String xmlWert) {
			this.xmlWert = xmlWert;
		}
		
		/**
		 * @return Den Wert, wie er im XML Dokument auftaucht
		 */
		public String getXmlWert() {
			return xmlWert;
		}
	}
	
	private VariableAuswahl[] varianteAuswahl;
    private Herkunft herkunft; // Das CharElement, von dem die Auswahl kommt
	protected IdLink[] festeAuswahl; // Die unveränderlichen Werte
	
	
	/**
	 * Konstruktor
	 * @param herkunft Die "quelle" dieser Auswahl
	 */
	public Auswahl(Herkunft herkunft) {
		this.herkunft = herkunft;
	}
	
	/**
	 * @return Array von festen Elementen, die das auf jedenfall zu dieser
	 * Auswahl gehören (also nicht gewählt werden).
	 */
	public IdLink[] getFesteAuswahl() {
		return festeAuswahl;
	}
	
	/**
	 * Jede Auswahl gehört zu einem Element, von dem diese Auswahl stammt.
	 * @return Liefert das CharElement, von dem diese Auswahl stammt.
	 */
	public Herkunft getHerkunft() {
		return herkunft;
	}
	
	public void addVarianteAuswahl(Modus modus, int[] werte, int anzahl, IdLink[] optionen) {
		// TODO Implement
	}
	
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen nötigen angaben
     */
    public void loadXmlElement(Element xmlElement) {
    	Elements tmpElements;

    	// Auslesen der unveränderlichen, "festen" Elemente der Auswahl
    	tmpElements = xmlElement.getChildElements("fest");
    	festeAuswahl = new IdLink[tmpElements.size()];
    	for (int i = 0; i < festeAuswahl.length; i++) {
    		festeAuswahl[i] = new IdLink(herkunft, this);
    		festeAuswahl[i].loadXmlElement(tmpElements.get(i));
    	}
    	
    	// Auslesen der Auswahlmöglichkeiten
    	tmpElements = xmlElement.getChildElements("auswahl");
    	varianteAuswahl = new VariableAuswahl[tmpElements.size()];
    	for (int i = 0; i < varianteAuswahl.length; i++) {
    		varianteAuswahl[i] = new VariableAuswahl(this);
    		varianteAuswahl[i].loadXmlElement(tmpElements.get(i));
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
    	
// 		Schreiben der festen Elemente
    	for (int i = 0; i < festeAuswahl.length; i++) {
    		xmlElement.appendChild(festeAuswahl[i].writeXmlElement("fest"));
    	}
    	
//    	 Schreiben der "variablen" Elemente
    	for (int i = 0; i < varianteAuswahl.length; i++) {
    		xmlElement.appendChild(varianteAuswahl[i].writeXmlElemnet("auswahl"));
    	}
    	
    	return xmlElement;
    }
	
	/**
	 * Bedeutung des Modus (siehe enum Oben):
	 * LISTE - In "wert"steht eine Liste von Werten, wobei jeder Wert einer
     * 	"option" zugewiesen werden muß. Es werden soviele optionen gewählt, wie
     * 	 es werte gibt. (Das attribut "anzahl" wird nicht benutzt)
 	 * ANZAHL - In "wert" steht eine Zahl, die angibt wieviele der "optionen"
     * 	gewählt werden müssen. Jede option kann einen Wert haben (über den
     * 	"idLinkTyp"). (Das attribut "anzahl" wird nicht benutzt)
	 * VERTEILUNG - Der Wert in "wert" kann auf soviele der Optionen verteilt
     *  werden, wie im Attribut "anzahl" angegeben. D.h. erst werden die
     *  Optionen ausgewählt, dann der "wert" beliebig auf die gewählten optionen
     *  verteilt. (Siehe "Elfische Siedlung" S. 37 im AZ)
	*/
	private class VariableAuswahl {
        private Modus modus;
        private int[] werte;
        private int anzahl;
        private IdLink[] optionen;
        private IdLink[][] optionsGruppe;
        private Auswahl auswahl;
        
		public VariableAuswahl(Auswahl auswahl) {
			this.auswahl = auswahl;
		}
		
		/**
		 * Liest alle Daten aus dem xmlElement aus
		 * @param xmlElement Element mit allen Daten
		 */
		public void loadXmlElement(Element xmlElement) {
			Elements tmpElements;
			String tmpString;
			String[] tmpStringAR;
			
    		// Überprüfung oder der Modus-Wert gültig ist:
			tmpString = xmlElement.getAttributeValue("modus");
    		assert tmpString.equals(Modus.LISTE.getXmlWert()) 
		    		|| tmpString.equals(Modus.ANZAHL.getXmlWert())
		    		|| tmpString.equals(Modus.VERTEILUNG.getXmlWert());
			
    		// Einlesen des Modus
    		if (tmpString.equals(Modus.LISTE.getXmlWert())) {
    			modus = Modus.LISTE;
    		} else if (tmpString.equals(Modus.ANZAHL.getXmlWert())) {
    			modus = Modus.ANZAHL;
    		} else { // ... .equals(Modus.VERTEILUNG.getXmlWert())
    			modus = Modus.VERTEILUNG;
    		}
	    	
    		try {
		    	// Einlesen der Werte / optional
		    	if ( xmlElement.getAttribute("werte") != null ) {
		    		tmpStringAR = xmlElement.getAttributeValue("werte").split(" ");
		    		werte = new int[tmpStringAR.length];
		    		for (int i = 0; i < tmpStringAR.length; i++){
		    			werte[i] = Integer.parseInt(tmpStringAR[i]);
		    		}
		    	}
		    		
		    	// Einlesen des optionalen Feldes Anzahl
		    	if ( xmlElement.getAttribute("anzahl") != null ) {
		    		anzahl = Integer.parseInt( xmlElement.getAttributeValue("anzahl") );
		    	}
    		} catch (NumberFormatException exc) {
    			ProgAdmin.logger.severe("Konnte String nicht in int umwandeln: " + exc);
    		}
	    		
    		// Einlesen der Optionen, sollten mind. zwei sein
    		tmpElements =  xmlElement.getChildElements("option");
    		optionen = readOptionen(tmpElements);
	    		
    		// Einlesen der Optionsgruppen
    		tmpElements =  xmlElement.getChildElements("optionsGruppe");
    		optionsGruppe = new IdLink[tmpElements.size()][];
    		for (int i = 0; i < tmpElements.size(); i++) {
    			optionsGruppe[i] = readOptionen(tmpElements.get(i).getChildElements("option"));
    		}
    		// Falls es keine Optionsgruppen gibt, komplettes Array wieder auf null
    		if (optionsGruppe.length == 0) {
    			optionsGruppe = null;
    		}
		}
		
		/**
		 * @return ein xml-Element mit allen Daten dieser Klasse!
		 */
		public Element writeXmlElemnet(String tagName) {
			Element xmlElement = new Element(tagName);
			Element tmpElement;
			StringBuffer tmpString = new StringBuffer();
			
	   		// Schreiben der einzelnen Optionen:
    		for (int i = 0; i < optionen.length; i++ ){
    			xmlElement.appendChild(optionen[i].writeXmlElement("option"));
    		}
    		
    		// Schreiben der Optionsgruppen:
    		if (optionsGruppe != null) {
	    		for (int i = 0; i < optionsGruppe.length; i++ ){
	    			tmpElement = new Element("optionsGruppe");
	    			for (int ii = 0; ii < optionsGruppe[i].length; ii++) {
	    				tmpElement.appendChild(optionsGruppe[i][ii].writeXmlElement("option"));
	    			}
	    			xmlElement.appendChild(tmpElement);
	      		}
    		}
    		// Schreiben des Attributs "werte"
    		for (int i = 0; i < werte.length; i++) {
    			tmpString.append(Integer.toString(werte[i]));
    			tmpString.append(" ");
    		}
    		if (tmpString.length() > 0) {
    			xmlElement.addAttribute(new Attribute( "werte", tmpString.toString().trim() ));
    		}
    		
    		// Schreiben des Attribus "anzahl"
    		if ( anzahl != 0) {
    			xmlElement.addAttribute(new Attribute( "anzahl", Integer.toString(anzahl) ));
    		}
    		
    		// Schreiben des Attributs "modus"
    		xmlElement.addAttribute(new Attribute( "modus", modus.getXmlWert() ));

			return xmlElement;
		}
	    
		/**
		 * Hilfsklasse zum einlesen der Optionen
		 * @param xmlElements Das xml-Element mit den Optionen
		 * @param auswahl Die Auswahl, zu der diese VariableAuswahl gehört
		 * @return Die Optionen als LinkIds
		 */
		private IdLink[] readOptionen(Elements xmlElements) {
			IdLink[] tmpOptionen;
			
			tmpOptionen = new IdLink[xmlElements.size()];
    		for (int i = 0; i < tmpOptionen.length; i++) {
    			tmpOptionen[i] = new IdLink(herkunft, auswahl);
    			tmpOptionen[i].loadXmlElement(xmlElements.get(i));
    		}
    		
    		return tmpOptionen;
		}
	}
}
