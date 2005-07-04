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
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.store.TextStore;

/**
 * <b>Beschreibung:</b><br>
 * Diese Klasse umfasst mehrere "Enums", die nicht Klar als eingenschaft einer 
 * bestimmten Klasse angesehen werden können. Es werden außerdem Operationen auf diesen 
 * Enums angeboten. 
 * @author V.Strelow
 */
public class Werte {
	private final static HashMap<String, MagieMerkmal> magieMerkmale = 
					new HashMap<String, MagieMerkmal>();
	
	static  {
		// Zum besseren auffinden in der Enum MagieMerkmal werden die 
		// Elemente in eine HashMap gelegt.
		
		for (int i = 0; i < MagieMerkmal.values().length; i++) {
			magieMerkmale.put(
					MagieMerkmal.values()[i].getValue(), 
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
		borbaradianer("borbaradianer");
		
		private String value; // ID des Elements
		
		private CharArten(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		
	}
	
	public enum Gilde { 
		weiss("weiss"), 
		grau("grau"), 
		schwarz("schwarz"), 
		unbekannt("unbekannt"), 
		keine("keine");
		private String value; // IDdes Elements
		
		private Gilde(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
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
		
		private String value; // ID des Elements
		private String bezeichner; 
		private Icon icon;
		
		private MagieMerkmal(String value, Icon icon) {
			this.value = value;
			this.icon = icon;
			TextStore lib = ProgAdmin.library;
			
			switch (this.ordinal()) {
			case 3: bezeichner = 
                lib.getShortTxt(daemonisch.getValue()) + " (Blakharaz)";
				break;
			case 4: bezeichner = 
                lib.getShortTxt(daemonisch.getValue()) + " (Belhalhar)";
				break;
			case 5: bezeichner = 
                lib.getShortTxt(daemonisch.getValue()) + " (Charyptoroth)";
				break;
			case 6: bezeichner = 
                lib.getShortTxt(daemonisch.getValue()) + " (Lolgramoth)";
				break;
			case 7: bezeichner =
                lib.getShortTxt(daemonisch.getValue()) + " (Thargunitoth)";
				break;
			case 8: bezeichner =
                lib.getShortTxt(daemonisch.getValue()) + " (Amazeroth)";
				break;
			case 9: bezeichner = 
                lib.getShortTxt(daemonisch.getValue()) + " (Belshirash)";
				break;
			case 10: bezeichner =
                lib.getShortTxt(daemonisch.getValue()) + " (Asfaloth)";
				break;
			case 11: bezeichner =
                lib.getShortTxt(daemonisch.getValue()) + " (Tasfarelel)";
				break;
			case 12: bezeichner = 
                lib.getShortTxt(daemonisch.getValue()) + " (Belzhorash)";
				break;
			case 13: bezeichner = 
                lib.getShortTxt(daemonisch.getValue()) + " (Agrimoth)";
				break;
			case 14: bezeichner = 
                lib.getShortTxt(daemonisch.getValue()) + " (Belkelel)";
				break;
			default: bezeichner = lib.getShortTxt(value);
			}
			
		}
		
		public String getValue() {
			return value;
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
		private String value; // ID des Elements
		
		private MagieRepraesentation(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}*/
	
	/**
	 * Liefert zu einem value die entsprechende Enum zurück.
	 * @param value Die ID der gilde
	 * @return Die Enum Gilde die zu value gehört
	 */
	public static Gilde getGildeByValue(String value) {
		Gilde[] gildeArray = Gilde.values();
		
		// Suchen des richtigen Elements
		for (int i = 0; i < gildeArray.length; i++) {
			if (value.equals(gildeArray[i].getValue())) {
				return gildeArray[i]; // Gefunden
			}
		}
		ProgAdmin.logger.severe("value konnte nicht gefunden werden!");
		return null;
	}
	
	/**
	 * Liefert zu einem valuedie entsprechende Enum zurück.
	 * @param value Die ID des magieMerkmals
	 * @return Die Enum MagieMerkmal die zu value gehört
	 */
	public static MagieMerkmal getMagieMerkmalByValue(String value) {
		
		// Sicherstellen das auch ein Element gefunden wurde
		assert magieMerkmale.get(value) != null;
	
		// Suchen + zurückliefern des Elements
		return magieMerkmale.get(value);
	}
	
	/**
	 * Liefert zu einem value die entsprechende Enum zurück.
	 * @param value Die ID der CharArten
	 * @return Die Enum CharArten die zu value gehört
	 */
	public static CharArten getCharArtenByValue(String value) {
		CharArten[] charArtenArray = CharArten.values();
		
		// Suchen des richtigen Elements
		for (int i = 0; i < charArtenArray.length; i++) {
			if (value.equals(charArtenArray[i].getValue())) {
				return charArtenArray[i]; // Gefunden
			}
		}
		
		ProgAdmin.logger.severe("XmlValue konnte nicht gefunden werden!");
		
		return null;
	}
}
