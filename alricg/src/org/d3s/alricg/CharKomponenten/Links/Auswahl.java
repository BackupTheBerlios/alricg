/*
 * Created 22. Dezember 2004 / 14:23:42
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.Links;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.CharKomponenten.Herkunft;

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
	
	protected VariableAuswahl[] varianteAuswahl;
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
    	Elements tmpElements, tmpOptions;
    	String tmpString;
    	String[] tmpStringAR;
        Modus modus;
        int[] werte = new int[0]; // Default
        int anzahl = 0; // Default
        IdLink[] optionen;
        
    	// Auslesen der unveränderlichen, "festen" Elemente der Auswahl
    	tmpElements = xmlElement.getChildElements("fest");
    	festeAuswahl = new IdLink[tmpElements.size()];
    	for (int i = 0; i < festeAuswahl.length; i++) {
    		festeAuswahl[i] = new IdLink(herkunft, this);
    		festeAuswahl[i].loadXmlElement(tmpElements.get(i));
    	}
    	
    	// Auslesen der Auswahlmöglichkeiten
    	tmpElements = xmlElement.getChildElements("modus");
    	varianteAuswahl = new VariableAuswahl[tmpElements.size()];
    	for (int i = 0; i < varianteAuswahl.length; i++) {
    		tmpString = tmpElements.get(i).getAttributeValue("modus");
    		
    		// Überprüfung oder der Modus-Wert gültig ist:
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
    		
    		// Einlesen der Werte / optional
    		if ( tmpElements.get(i).getAttribute("werte") != null ) {
    			tmpStringAR = tmpElements.get(i).getAttributeValue("werte")
    											.split(" ");
    			werte = new int[tmpStringAR.length];
    			for (int ii = 0; ii < tmpStringAR.length; ii++){
    				werte[ii] = Integer.parseInt(tmpStringAR[ii]);
    			}
    		}
    		
    		// EInlesen des optionalen Feldes Anzahl
    		if ( tmpElements.get(i).getAttribute("anzahl") != null ) {
    			anzahl = Integer.parseInt( tmpElements.get(i)
    							.getAttributeValue("anzahl") );
    		}
    		
    		// Einlesen der Optionen, sollten mind. zwei sein
    		tmpOptions =  tmpElements.get(i).getChildElements("option");
    		optionen = new IdLink[tmpOptions.size()];
    		for (int ii = 0; ii < optionen.length; ii++) {
    			optionen[ii] = new IdLink(herkunft, this);
    			optionen[ii].loadXmlElement(tmpOptions.get(ii));
    		}
    		
    		// Erzeugen der Varianten Auswahl...
    		varianteAuswahl[i] = new VariableAuswahl(modus, werte, anzahl, optionen);
    	}
    	
    }
    	
    /**
     * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt".
     * @param tagName Der name des Tags
     * @return Ein Xml-Element mit allen nötigen Angaben.
     */
    public Element writeXmlElement(String tagName){
    	Element tmpXmlElemnt, xmlElement = new Element("tagName");
    	StringBuffer tmpString = new StringBuffer("");
    	
// 		Schreiben der festen Elemente
    	for (int i = 0; i < festeAuswahl.length; i++) {
    		xmlElement.appendChild(festeAuswahl[i].writeXmlElement("fest"));
    	}
    	
//    	 Schreiben der "variablen" Elemente
    	for (int i = 0; i < varianteAuswahl.length; i++) {
    		tmpXmlElemnt = new Element("auswahl");

    		// Scheiben der einzelnen Optionen:
    		for (int ii = 0; ii < varianteAuswahl[i].getOptionen().length; ii++ ){
    			tmpXmlElemnt.appendChild(varianteAuswahl[i]
    			                         .getOptionen()[ii].writeXmlElement("option"));
    		}
    		
    		// Schreiben des Attributs "werte"
    		for (int ii = 0; ii < varianteAuswahl[i].getWerte().length; ii++) {
    			tmpString.append(Integer.toString(varianteAuswahl[i].getWerte()[ii]));
    			tmpString.append(" ");
    		}
    		if (tmpString.length() > 0) {
    			tmpXmlElemnt.addAttribute(new Attribute( "werte", 
    											tmpString.toString().trim() ));
    		}
    		
    		// Schreiben des Attribus "anzahl"
    		if (varianteAuswahl[i].getAnzahl() != 0) {
    			tmpXmlElemnt.addAttribute(new Attribute( "anzahl", 
    								Integer.toString(varianteAuswahl[i].getAnzahl()) ));
    		}
    		
    		// Schreiben des Attributs "modus"
    		tmpXmlElemnt.addAttribute(new Attribute( "modus",
    										varianteAuswahl[i].getModus().getXmlWert() ));
    		
    		
    		// Anhängen des Elements:
    		xmlElement.appendChild(tmpXmlElemnt);
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
	protected class VariableAuswahl {
        private Modus modus;
        private int[] werte;
        private int anzahl;
        private IdLink[] optionen;

		public VariableAuswahl(Modus modus, int[] werte, int anzahl, IdLink[] optionen) {
			this.modus = modus;
			this.werte = werte;
            this.anzahl = anzahl;
            this.optionen = optionen;
		}
		
		/**
		 * @return Liefert das Attribut anzahl.
		 */
		public int getAnzahl() {
			return anzahl;
		}
		/**
		 * @return Liefert das Attribut modus.
		 */
		public Modus getModus() {
			return modus;
		}
		/**
		 * @return Liefert das Attribut optionen.
		 */
		public IdLink[] getOptionen() {
			return optionen;
		}
		/**
		 * @return Liefert das Attribut werte.
		 */
		public int[] getWerte() {
			return werte;
		}
	}
}
