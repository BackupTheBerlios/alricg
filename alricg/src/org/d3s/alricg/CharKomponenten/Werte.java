/*
 * Created 20. Januar 2005 / 15:42:20
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import java.util.HashMap;

import org.d3s.alricg.Controller.ProgAdmin;

/**
 * <b>Beschreibung:</b><br>
 * Diese Klasse umfasst mehrere "Enums", die nicht Klar als eingenschaft einer 
 * bestimmten Klasse angesehen werden können. Es werden außerdem Operationen auf diesen 
 * Enums angeboten. 
 * @author V.Strelow
 */
public class Werte {
	private static HashMap<String, MagieMerkmal> magieMerkmale = 
										new HashMap<String, MagieMerkmal>();
	
	static {
		// Zum besseren auffinden in der Enum MagieMerkmal werden die 
		// Elemente in eine HashMap gelegt.
		for (int i = 0; i < MagieMerkmal.values().length; i++) {
			magieMerkmale.put(
					MagieMerkmal.values()[i].getXmlValue(), 
					MagieMerkmal.values()[i]
				);
		}
	}
	
	public enum CharArten {
		alle("alle"),
		nichtMagisch("nichtMagisch"),
		vollZauberer("vollZauberer"),
		halbZauberer("halbZauberer"),
		viertelZauberer("viertelZauberer"),
		geweiht("geweiht"),
		borbaradianer("borbaradianer")
		;
		private String xmlValue; // XML-Tag des Elements
		
		private CharArten(String xmlValue) {
			this.xmlValue = xmlValue;
		}
		
		public String getXmlValue() {
			return xmlValue;
		}
		
	}
	
	public enum Gilde { 
		weiss("weiss"), 
		grau("grau"), 
		schwarz("schwarz"), 
		unbekannt("unbekannt"), 
		keine("keine");
		private String xmlValue; // XML-Tag des Elements
		
		private Gilde(String xmlValue) {
			this.xmlValue = xmlValue;
		}
		
		public String getXmlValue() {
			return xmlValue;
		}
		
	};
	
	public enum Geschlecht {mann, frau, mannFrau};
	
	public enum MagieMerkmal {
		antimagie("antimagie"),
		beschwoerung("beschwoerung"),
		daemonisch("daemonisch"),
		daemonischBlakharaz("daemonisch (blakharaz)"),
		daemonischBelhalhar("daemonisch (belhalhar)"),
		daemonischCharyptoroth("daemonisch (charyptoroth)"),
		daemonischLolgramoth("daemonisch (lolgramoth)"),
		daemonischThargunitoth("daemonisch (thargunitoth)"),
		daemonischAmazeroth("daemonisch (amazeroth)"),
		daemonischBelshirash("daemonisch (belshirash)"),
		daemonischAsfaloth("daemonisch (asfaloth)"),
		daemonischTasfarelel("daemonisch (tasfarelel)"),
		daemonischBelzhorash("daemonisch (belzhorash)"),
		daemonischAgrimoth("daemonisch (agrimoth)"),
		daemonischBelkelel("daemonisch (belkelel)"),
		eigenschaften("eigenschaften"),
		einfluss("einfluss"),
		elementar("elementar"),
		elementarFeuer("elementar (feuer)"),
		elementarWasser("elementar (wasser)"),
		elementarLuft("elementar (luft)"),
		elementarErz("elementar (erz)"),
		elementarHumus("elementar (humus)"),
		elementarEis("elementar (eis)"),
		form("form"),
		geisterwesen("geisterwesen"),
		heilung("heilung"),
		hellsicht("hellsicht"),
		herbeirufung("herbeirufung"),
		herrschaft("herrschaft"),
		illusion("illusion"),
		kraft("kraft"),
		limbus("limbus"),
		metamagie("metamagie"),
		objekt("objekt"),
		schaden("schaden"),
		telekinese("telekinese"),
		temporal("temporal"),
		umwelt("umwelt"),
		verstaendigung("verstaendigung");
		
		private String xmlValue; // XML-Tag des Elements
		
		private MagieMerkmal(String xmlValue) {
			this.xmlValue = xmlValue;
		}
		
		public String getXmlValue() {
			return xmlValue;
		}
	}
	
	/**
	 * Liefert zu einem XML-Tag die entsprechende Enum zurück.
	 * @param xmlValue Der XML-Tag der gilde
	 * @return Die Enum Gilde die zu den xmlValue gehört
	 */
	public static Gilde getGildeByXmlValue(String xmlValue) {
		Gilde[] gildeArray = Gilde.values();
		
		// Suchen des richtigen Elements
		for (int i = 0; i < gildeArray.length; i++) {
			if (xmlValue.equals(gildeArray[i].getXmlValue())) {
				return gildeArray[i]; // Gefunden
			}
		}
		
		ProgAdmin.logger.severe("XmlValue konnte nicht gefunden werden!");
		
		return null;
	}
	
	/**
	 * Liefert zu einem XML-Tag die entsprechende Enum zurück.
	 * @param xmlValue Der XML-Tag des magieMerkmals
	 * @return Die Enum MagieMerkmal die zu den xmlValue gehört
	 */
	public static MagieMerkmal getMagieMerkmalByXmlValue(String xmlValue) {
		
		// Sicherstellen das auch ein Element gefunden wurde
		assert magieMerkmale.get(xmlValue) != null;
	
		// Suchen + zurückliefern des Elements
		return magieMerkmale.get(xmlValue);
	}
	
	/**
	 * Liefert zu einem XML-Tag die entsprechende Enum zurück.
	 * @param xmlValue Der XML-Tag des CharArten
	 * @return Die Enum CharArten die zu den xmlValue gehört
	 */
	public static CharArten getCharArtenByXmlValue(String xmlValue) {
		CharArten[] charArtenArray = CharArten.values();
		
		// Suchen des richtigen Elements
		for (int i = 0; i < charArtenArray.length; i++) {
			if (xmlValue.equals(charArtenArray[i].getXmlValue())) {
				return charArtenArray[i]; // Gefunden
			}
		}
		
		ProgAdmin.logger.severe("XmlValue konnte nicht gefunden werden!");
		
		return null;
	}
}
