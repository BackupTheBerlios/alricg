/*
 * Created on 21.01.2005 / 01:14:15
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.prozessor;

import org.d3s.alricg.Controller.ProgAdmin;

/**
 * <u>Beschreibung:</u><br> 
 * Die Steigerungs-Kosten-Tabelle, nach der die Kosten für den Kauf von CharElementen bezahlt wird.
 * @author V. Strelow
 */
public class SKT {
    
    public enum KostenKlasse { 
    	A_PLUS ("A+"), 
    	A("A"), 
    	B("B"), 
    	C("C"), 
    	D("D"), 
    	E("E"), 
    	F("F"), 
    	G("G"), 
    	H("H"); 
		private String xmlValue; // XML-Tag des Elements
		
		private KostenKlasse(String xmlValue) {
			this.xmlValue = xmlValue;
		}
		
		public String getXmlValue() {
			return xmlValue;
		}	
    }
    
	/**
	 * Liefert zu einem XML-Tag die entsprechende Enum zurück.
	 * @param xmlValue Der XML-Tag der KostenKlasse
	 * @return Die Enum KostenKlasse die zu den xmlValue gehört
	 */
	public static KostenKlasse getKostenKlasseByXmlValue(String xmlValue) {
		KostenKlasse[] kostenArray = KostenKlasse.values();
		
		// Suchen des richtigen Elements
		for (int i = 0; i < kostenArray.length; i++) {
			if (xmlValue.equals(kostenArray[i].getXmlValue())) {
				return kostenArray[i]; // Gefunden
			}
		}
		
		ProgAdmin.logger.severe("XmlValue konnte nicht gefunden werden!");
		
		return null;
	}
    
}
