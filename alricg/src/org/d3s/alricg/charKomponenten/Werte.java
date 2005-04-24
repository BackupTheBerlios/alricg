/*
 * Created 20. Januar 2005 / 15:42:20
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import java.util.HashMap;

import javax.swing.Icon;

import org.d3s.alricg.controller.ImageAdmin;
import org.d3s.alricg.controller.Library;
import org.d3s.alricg.controller.ProgAdmin;

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
		antimagie("antimagie", ImageAdmin.zauberMerkmalAntimagie),
		beschwoerung("beschwoerung", ImageAdmin.zauberMerkmalBeschwoerung),
		daemonisch("daemonisch", ImageAdmin.zauberMerkmalDaemonisch),
		daemonischBlakharaz("daemonisch (blakharaz)", ImageAdmin.zauberMerkmalDaemonisch),
		daemonischBelhalhar("daemonisch (belhalhar)", ImageAdmin.zauberMerkmalDaemonisch),
		daemonischCharyptoroth("daemonisch (charyptoroth)", ImageAdmin.zauberMerkmalDaemonisch),
		daemonischLolgramoth("daemonisch (lolgramoth)", ImageAdmin.zauberMerkmalDaemonisch),
		daemonischThargunitoth("daemonisch (thargunitoth)", ImageAdmin.zauberMerkmalDaemonisch),
		daemonischAmazeroth("daemonisch (amazeroth)", ImageAdmin.zauberMerkmalDaemonisch),
		daemonischBelshirash("daemonisch (belshirash)", ImageAdmin.zauberMerkmalDaemonisch),
		daemonischAsfaloth("daemonisch (asfaloth)", ImageAdmin.zauberMerkmalDaemonisch),
		daemonischTasfarelel("daemonisch (tasfarelel)", ImageAdmin.zauberMerkmalDaemonisch),
		daemonischBelzhorash("daemonisch (belzhorash)", ImageAdmin.zauberMerkmalDaemonisch),
		daemonischAgrimoth("daemonisch (agrimoth)", ImageAdmin.zauberMerkmalDaemonisch),
		daemonischBelkelel("daemonisch (belkelel)", ImageAdmin.zauberMerkmalDaemonisch),
		eigenschaften("eigenschaften", ImageAdmin.zauberMerkmalEigenschaften),
		einfluss("einfluss", ImageAdmin.zauberMerkmalEinfluss),
		elementar("elementar", ImageAdmin.zauberMerkmalElementar),
		elementarFeuer("elementar (feuer)", ImageAdmin.zauberMerkmalElementarFeuer),
		elementarWasser("elementar (wasser)", ImageAdmin.zauberMerkmalElementarWasser),
		elementarLuft("elementar (luft)", ImageAdmin.zauberMerkmalElementarLuft),
		elementarErz("elementar (erz)", ImageAdmin.zauberMerkmalElementarErz),
		elementarHumus("elementar (humus)", ImageAdmin.zauberMerkmalElementarHumus),
		elementarEis("elementar (eis)", ImageAdmin.zauberMerkmalElementarEis),
		form("form", ImageAdmin.zauberMerkmalForm),
		geisterwesen("geisterwesen", ImageAdmin.zauberMerkmalGeisterwesen),
		heilung("heilung", ImageAdmin.zauberMerkmalHeilung),
		hellsicht("hellsicht", ImageAdmin.zauberMerkmalHellsicht),
		herbeirufung("herbeirufung", ImageAdmin.zauberMerkmalHerbeirufung),
		herrschaft("herrschaft", ImageAdmin.zauberMerkmalHerrschaft),
		illusion("illusion", ImageAdmin.zauberMerkmalIllusion),
		kraft("kraft", ImageAdmin.zauberMerkmalKraft),
		limbus("limbus", ImageAdmin.zauberMerkmalLimbus),
		metamagie("metamagie", ImageAdmin.zauberMerkmalMetamagie),
		objekt("objekt", ImageAdmin.zauberMerkmalObjekt),
		schaden("schaden", ImageAdmin.zauberMerkmalSchaden),
		telekinese("telekinese", ImageAdmin.zauberMerkmalTelekinese),
		temporal("temporal", ImageAdmin.zauberMerkmalTemporal),
		umwelt("umwelt", ImageAdmin.zauberMerkmalUmwelt),
		verstaendigung("verstaendigung", ImageAdmin.zauberMerkmalVerstaendigung);
		
		private String xmlValue; // XML-Tag des Elements
		private String bezeichner; 
		private Icon icon;
		
		private MagieMerkmal(String xmlValue, Icon icon) {
			this.xmlValue = xmlValue;
			this.icon = icon;
			
			switch (this) {
			case daemonischBlakharaz: bezeichner = 
				Library.getShortTxt(daemonisch.getXmlValue()) + " (Blakharaz)";
				break;
			case daemonischBelhalhar: bezeichner = 
				Library.getShortTxt(daemonisch.getXmlValue()) + " (Belhalhar)";
				break;
			case daemonischCharyptoroth: bezeichner = 
				Library.getShortTxt(daemonisch.getXmlValue()) + " (Charyptoroth)";
				break;
			case daemonischLolgramoth: bezeichner = 
				Library.getShortTxt(daemonisch.getXmlValue()) + " (Lolgramoth)";
				break;
			case daemonischThargunitoth: bezeichner =
				Library.getShortTxt(daemonisch.getXmlValue()) + " (Thargunitoth)";
				break;
			case daemonischAmazeroth: bezeichner =
				Library.getShortTxt(daemonisch.getXmlValue()) + " (Amazeroth)";
				break;
			case daemonischBelshirash: bezeichner = 
				Library.getShortTxt(daemonisch.getXmlValue()) + " (Belshirash)";
				break;
			case daemonischAsfaloth: bezeichner =
				Library.getShortTxt(daemonisch.getXmlValue()) + " (Asfaloth)";
				break;
			case daemonischTasfarelel: bezeichner =
				Library.getShortTxt(daemonisch.getXmlValue()) + " (Tasfarelel)";
				break;
			case daemonischBelzhorash: bezeichner = 
				Library.getShortTxt(daemonisch.getXmlValue()) + " (Belzhorash)";
				break;
			case daemonischAgrimoth: bezeichner = 
				Library.getShortTxt(daemonisch.getXmlValue()) + " (Agrimoth)";
				break;
			case daemonischBelkelel: bezeichner = 
				Library.getShortTxt(daemonisch.getXmlValue()) + " (Belkelel)";
				break;
			default: bezeichner = Library.getShortTxt(xmlValue);
			}
			
		}
		
		public String getXmlValue() {
			return xmlValue;
		}
		
		/**
		 * @return Das zum Merkmal zugehörige Icon
		 */
		public Icon getIcon() {
			return icon;
		}
		
		public String toString() {
			return bezeichner;
		}
	}
	
	/*public enum MagieRepraesentation {
		gildenmagier("gildenmagier"),
		elfen("elfen"),
		druiden("druiden"),
		hexen("hexen"),
		geoden("geoden"),
		schelme("schelme"),
		scharlatane("scharlatane"),
		borbaradianer("borbaradianer"),
		kristallomanten("kristallomanten"),
		alchemisten("alchemisten"),
		derwische("derwische"),
		gjalsker("gjalsker"),
		darna("darna"),
		sharisadTulamidisch("sharisad (tulamidisch)"),
		sharisadNovadisch("sharisad (novadisch)"),
		sharisadZahorisch("sharisad (zahorisch)"),
		sharisadAranisch("sharisad (aranisch)"),
		zibilja("zibilja");
		private String xmlValue; // XML-Tag des Elements
		
		private MagieRepraesentation(String xmlValue) {
			this.xmlValue = xmlValue;
		}
		
		public String getXmlValue() {
			return xmlValue;
		}
	}*/
	
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
