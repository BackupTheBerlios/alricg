/*
 * Created 22. Dezember 2004 / 13:07:57
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.CharKomponenten.CharZusatz.WuerfelSammlung;
import org.d3s.alricg.CharKomponenten.Links.IdLinkList;
import org.d3s.alricg.Controller.ProgAdmin;
import org.d3s.alricg.Controller.CharKompAdmin.CharKomponenten;
/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Rasse extends Herkunft {
	private IdLinkList kulturMoeglich;
	private IdLinkList kulturUeblich;
    private String[] haarfarbe = new String[20];
	private String[] augenfarbe = new String[20];
	private WuerfelSammlung groesseWuerfel;
	private WuerfelSammlung alterWuerfel;

	private int gewichtModi;
	private int geschwindigk = 8;
	
    private Rasse varianteVon;

	/**
	 * Konstruktur; id beginnt mit "RAS-" für Rasse
	 * @param id Systemweit eindeutige id
	 */
	public Rasse(String id) {
		setId(id);
	}
	
	/**
	 * @return Liefert das Attribut augenfarbe.
	 */
	public String[] getAugenfarbe() {
		return augenfarbe;
	}
	/**
	 * @return Liefert das Attribut gewichtModi.
	 */
	public int getGewichtModi() {
		return gewichtModi;
	}
	/**
	 * @return Liefert das Attribut haarfarbe.
	 */
	public String[] getHaarfarbe() {
		return haarfarbe;
	}
	/**
	 * @return Liefert das Attribut kulturMoeglich.
	 */
	public IdLinkList getKulturMoeglich() {
		return kulturMoeglich;
	}
	/**
	 * @return Liefert das Attribut kulturUeblich.
	 */
	public IdLinkList getKulturUeblich() {
		return kulturUeblich;
	}
	
	/**
	 * Berechnet einen einen korrekten Wert für die Grösse. Dies ist ein gültiger
	 * Zufalls-Wert, basierend auf den angegebenen Werten.
	 * @return Einen gültigen größe-Wert für diese Rasse.
	 */
	public int getGroesseZufall() {
		return groesseWuerfel.getWuerfelWurf();
	}
	
	/**
	 * @return Liefert das Attribut groesseWuerfel.
	 */
	public WuerfelSammlung getGroesseWuerfel() {
		return groesseWuerfel;
	}
	
	/**
	 * Berechnet einen einen korrekten Wert für das Alter. Dies ist ein gültiger
	 * Zufalls-Wert, basierend auf den angegebenen Werten.
	 * ACHTUNG: Das Alter kann noch durch Vor/Nachteile ("Veteran", usw) o.ä. 
	 * 		verändert werden! Dies wird hier nicht berücksichtig und ist nur der
	 * 		reine Grundwert der Rasse.
	 * @return Einen gültigen alters-Wert für diese Rasse.
	 */
	public int getAlterZufall() {
		return alterWuerfel.getWuerfelWurf();
	}
	
	/**
	 * @return Liefert das Attribut alterWuerfel.
	 */
	public WuerfelSammlung getAlterWuerfel() {
		return alterWuerfel;
	}
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	Elements tmpElements;
    	int[] anzahlWuerfel, augenWuerfel;

    	try {
	    	// "varianteVon" auslesen
			if ( xmlElement.getFirstChildElement("varianteVon") !=  null ) {
				varianteVon = (Rasse) ProgAdmin.charKompAdmin.getCharElement(
		    			xmlElement.getFirstChildElement("varianteVon").getValue(),
		    			CharKomponenten.rasse
		    		);
			}
			
			// Übliche Kulturen einlesen
			if ( xmlElement.getFirstChildElement("kulturUeblich") != null ) {
				kulturUeblich = new IdLinkList(this);
				kulturUeblich.loadXmlElement(xmlElement.getFirstChildElement("kulturUeblich"));
			}
			
			// Mögliche Kulturen einlesen
			if ( xmlElement.getFirstChildElement("kulturMoeglich") != null ) {
				kulturMoeglich = new IdLinkList(this);
				kulturMoeglich.loadXmlElement(xmlElement.getFirstChildElement("kulturMoeglich"));
			}
			
			// Einlesen der Geschwindigkeit
			if ( xmlElement.getFirstChildElement("geschwindigkeit") != null ) {
				geschwindigk = Integer.parseInt(
						xmlElement.getFirstChildElement("geschwindigkeit").getValue());
			}
			
			// Einlesen des Start-Alters
			tmpElements = xmlElement.getFirstChildElement("alter").getChildElements("wuerfel");
			anzahlWuerfel = new int[tmpElements.size()];
			augenWuerfel  = new int[tmpElements.size()];
			
			for (int i = 0; i < tmpElements.size(); i++) {
				anzahlWuerfel[i] = Integer.parseInt(tmpElements.get(i)
										  .getAttributeValue("anzWuerfel"));
				augenWuerfel[i] = Integer.parseInt(tmpElements.get(i)
						  .getAttributeValue("augenWuerfel"));
			}
			
			alterWuerfel = new WuerfelSammlung(Integer.parseInt(xmlElement
					.getFirstChildElement("alter").getAttributeValue("wert")),
					anzahlWuerfel,
					augenWuerfel
				);
			
			// Einlesen der Größe
			tmpElements = xmlElement.getFirstChildElement("groesse").getChildElements("wuerfel");
			anzahlWuerfel = new int[tmpElements.size()];
			augenWuerfel  = new int[tmpElements.size()];
			
			for (int i = 0; i < tmpElements.size(); i++) {
				anzahlWuerfel[i] = Integer.parseInt(tmpElements.get(i)
										  .getAttributeValue("anzWuerfel"));
				augenWuerfel[i] = Integer.parseInt(tmpElements.get(i)
						  .getAttributeValue("augenWuerfel"));
			}
			
			groesseWuerfel = new WuerfelSammlung(Integer.parseInt(xmlElement
					.getFirstChildElement("groesse").getAttributeValue("wert")),
					anzahlWuerfel,
					augenWuerfel
				);
		
			// Einlesen des Modis für das Gewicht
			gewichtModi = Integer.parseInt(xmlElement.getFirstChildElement("gewichtModi")
													 .getAttributeValue("wert"));
			
			// Einlesen der Haarfarbe
			loadFarbenArray(
					xmlElement.getFirstChildElement("haarfarbe").getChildElements("farbe"),
					haarfarbe
					);
			
			// Einlesen der Augenfarbe
			loadFarbenArray(
					xmlElement.getFirstChildElement("augenfarbe").getChildElements("farbe"),
					augenfarbe
					);
			
    	} catch (NumberFormatException exc) {
    		// TODO Bessere Fehlermeldung einbauen
    		ProgAdmin.logger.severe( exc.toString() );
    	}
    }
    
    
    /**
     * Zum auslesen der Farben (Maximal 20)
     * @param elements Die xml-elemente mit den angaben
     * @param array Das Array, in das die Farb-Angaben geschrieben werden 
     */
    private void loadFarbenArray(Elements elements, String[] array) {
    	int anteil, idx = 0;
    	
    	for (int i = 0; i < elements.size(); i++) {
    		anteil = Integer.parseInt(elements.get(i).getAttributeValue("anteil"));
    		
    		for (int ii = 0; ii < anteil; ii++) {
    			array[idx] = elements.get(i).getAttributeValue("farbe");
    			idx++;
    		}
    	}
    	
    	if (idx < 20) {
    		ProgAdmin.logger.warning("Index zu klein: Sollwert von 20 wurde nicht erreicht!");
    	}
    }
    
    /**
     * Zum schreiben der Farben nach XML
     * @param xmlElement Als Xml-Element, zu dem geschrieben wird.
     * @param array Das array mit allen Farben (max 20) als Array
     */
    private void writeFarbenArray(Element xmlElement, String[] array) {
    	Element element;
    	String tmpFarbe = array[0];
    	int idx = 0;
    	
    	for (int i = 0; i < array.length; i++) {
    		if ( !tmpFarbe.equals(array[i]) )
    		{
    			element = new Element("farbe");
    			element.addAttribute( new Attribute("farbe", tmpFarbe));
    			element.addAttribute( new Attribute("anteil", Integer.toString(idx)));
    			xmlElement.appendChild(element);
    			
    			idx = 1; // Für nächsten Durchlauf
    			tmpFarbe = array[i]; // Für nächsten Durchlauf, 
    		} else {
    			idx++;
    		}
    	}
    	
    	// Letztes Element "nachtragen"
		element = new Element("farbe");
		element.addAttribute( new Attribute("farbe", tmpFarbe));
		element.addAttribute( new Attribute("anteil", Integer.toString(idx)));
		xmlElement.appendChild(element);

    	
    }
    
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	int idx; 
    	Element element, element2;
    	
    	xmlElement.setLocalName("rasse");
    	
    	// "varianteVon" schreiben
    	if ( this.varianteVon != null ) {
	    	// hierfür muß die richtige Position bestimmt werden: 
	    	idx = xmlElement.indexOf( xmlElement.getFirstChildElement("gp") );
	    	element = new Element("varianteVon");
	    	element.appendChild(this.varianteVon.getId());
	    	
	    	// einfügen nach dem "gp" Element!
	    	xmlElement.insertChild(element, idx+1);
    	}
    	
		// Übliche Kulturen schreiben
    	if ( kulturUeblich != null ) {
    		xmlElement.appendChild(kulturUeblich.writeXmlElement("kulturUeblich"));
    	}
    	
		// Mögliche Kulturen einlesen
		if ( kulturMoeglich != null ) {
			xmlElement.appendChild(kulturMoeglich.writeXmlElement("kulturMoeglich"));
		}
    	
		// Geschwindigkeit schreiben
		element = new Element("geschwindigkeit");
		element.appendChild(Integer.toString(geschwindigk));
    	xmlElement.appendChild(element);
    	
    	// Alter schreiben
    	element = new Element("alter");
    	element.addAttribute(new Attribute("wert", 
    							Integer.toString(alterWuerfel.getFestWert())));
    	for (int i = 0; i < alterWuerfel.getAnzahlWuerfel().length; i++) {
    		element2 = new Element("wuerfel");
    		element2.addAttribute( new Attribute("anzWuerfel",
    				Integer.toString(alterWuerfel.getAnzahlWuerfel()[i])) );
    		element2.addAttribute( new Attribute("augenWuerfel",
    				Integer.toString(alterWuerfel.getAugenWuerfel()[i])) );
    		element.appendChild(element2);
    	}
    	xmlElement.appendChild(element);
    	
    	// Größe schreiben
    	element = new Element("groesse");
    	element.addAttribute(new Attribute("wert", 
    							Integer.toString(groesseWuerfel.getFestWert())));
    	for (int i = 0; i < groesseWuerfel.getAnzahlWuerfel().length; i++) {
    		element2 = new Element("wuerfel");
    		element2.addAttribute( new Attribute("anzWuerfel",
    				Integer.toString(groesseWuerfel.getAnzahlWuerfel()[i])) );
    		element2.addAttribute( new Attribute("augenWuerfel",
    				Integer.toString(groesseWuerfel.getAugenWuerfel()[i])) );
    		element.appendChild(element2);
    	}
    	xmlElement.appendChild(element);
    	
    	// Modifikation des Gewichtes schreiben
    	element = new Element("gewichtModi");
    	element.addAttribute( new Attribute("wert", Integer.toString(gewichtModi)) );
    	xmlElement.appendChild(element);
    	
    	// Haarfarbe schreiben
    	element = new Element("haarfarbe");
    	writeFarbenArray(element, haarfarbe);
    	xmlElement.appendChild(element);
    	
    	// Augenfarbe schreiben
    	element = new Element("augenfarbe");
    	writeFarbenArray(element, augenfarbe);
    	xmlElement.appendChild(element);
    	
    	return xmlElement;
    }
}
