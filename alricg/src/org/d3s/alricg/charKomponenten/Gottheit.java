/*
 * Created on 12.03.2005 / 12:23:19
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten;

import nu.xom.Element;

/**
 * <u>Beschreibung:</u><br> 
 * Jede Liturgie und jedes Ritual ist einer Gottheit zugeordent. Auch geweihte
 * Sonderfertigkeiten sind mit einer Gottheit verbunden.
 * @author V. Strelow
 */
public class Gottheit extends CharElement {
	
	public enum KenntnisArt {
		liturgie("liturgie"),
		ritual("ritual");
		private String xmlValue; // XML-Tag des Elements
		
		private KenntnisArt(String xmlValue) {
			this.xmlValue = xmlValue;
		}
		
		public String getXmlValue() {
			return xmlValue;
		}
	}
	public enum GottheitArt {
		nichtAlveranisch("nichtAlveranisch"),
		zwoelfGoettlich("zwoelfGoettlich"),
		animistisch("animistisch");
		private String xmlValue; // XML-Tag des Elements
		
		private GottheitArt(String xmlValue) {
			this.xmlValue = xmlValue;
		}
		
		public String getXmlValue() {
			return xmlValue;
		}
	}

	private KenntnisArt kenntnisArt;
	private GottheitArt gottheitArt;
	
	/**
	 * Konstruktur; id beginnt mit "GOT-" für Gottheit
	 * @param id Systemweit eindeutige id
	 */
	public Gottheit(String id) {
		setId(id);
	}

	/**
	 * @return Liefert das Attribut gottheitArt.
	 */
	public GottheitArt getGottheitArt() {
		return gottheitArt;
	}
	/**
	 * @param gottheitArt Setzt das Attribut gottheitArt.
	 */
	public void setGottheitArt(GottheitArt gottheitArt) {
		this.gottheitArt = gottheitArt;
	}
	/**
	 * @return Liefert das Attribut kenntnisArt.
	 */
	public KenntnisArt getKenntnisArt() {
		return kenntnisArt;
	}
	/**
	 * @param kenntnisArt Setzt das Attribut kenntnisArt.
	 */
	public void setKenntnisArt(KenntnisArt kenntnisArt) {
		this.kenntnisArt = kenntnisArt;
	}
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
 // 	Auslesen der Art der Kenntnis
    	// Prüfen des Wertebereiches
    	assert xmlElement.getFirstChildElement("kenntnisArt").getValue().equals(
						KenntnisArt.liturgie.getXmlValue())
				|| xmlElement.getFirstChildElement("kenntnisArt").getValue().equals(
						KenntnisArt.ritual.getXmlValue());
    	
    	if ( xmlElement.getFirstChildElement("kenntnisArt").getValue().equals(
    									KenntnisArt.liturgie.getXmlValue()) ) {
    		kenntnisArt = KenntnisArt.liturgie;
    	} else  {
    		kenntnisArt = KenntnisArt.ritual;
    	}
    	
// 		Auslesen der Art des Gottes/Ritus
    	// Prüfen des Wertebereiches
    	assert xmlElement.getFirstChildElement("gottheitArt").getValue().equals(
    				GottheitArt.zwoelfGoettlich.getXmlValue())
    			|| xmlElement.getFirstChildElement("gottheitArt").getValue().equals(
					GottheitArt.nichtAlveranisch.getXmlValue())
				|| xmlElement.getFirstChildElement("gottheitArt").getValue().equals(
						GottheitArt.animistisch.getXmlValue());
    	
    	if ( xmlElement.getFirstChildElement("gottheitArt").getValue().equals(
    			GottheitArt.zwoelfGoettlich.getXmlValue()) ) {
    		gottheitArt = GottheitArt.zwoelfGoettlich;
		} else if ( xmlElement.getFirstChildElement("gottheitArt").getValue().equals(
						GottheitArt.nichtAlveranisch.getXmlValue()) ) {
			gottheitArt = GottheitArt.nichtAlveranisch;
		} else {
			gottheitArt = GottheitArt.animistisch;
		}
    	
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("gottheit");
    	
    	// Schreiben der KenntnisArt
    	tmpElement = new Element("kenntnisArt");
    	tmpElement.appendChild(kenntnisArt.getXmlValue());
    	xmlElement.appendChild(tmpElement);
    	
    	// Schreiben der Gottheit
    	tmpElement = new Element("gottheitArt");
    	tmpElement.appendChild(gottheitArt.getXmlValue());
    	xmlElement.appendChild(tmpElement);
    	
    	return xmlElement;
    }

}
