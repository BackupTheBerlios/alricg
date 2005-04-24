/*
 * Created 23. Januar 2005 / 16:09:44
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Eigenschaft;
import org.d3s.alricg.charKomponenten.Gabe;
import org.d3s.alricg.charKomponenten.Gottheit;
import org.d3s.alricg.charKomponenten.Kultur;
import org.d3s.alricg.charKomponenten.Liturgie;
import org.d3s.alricg.charKomponenten.LiturgieRitualKenntnis;
import org.d3s.alricg.charKomponenten.Nachteil;
import org.d3s.alricg.charKomponenten.Profession;
import org.d3s.alricg.charKomponenten.Rasse;
import org.d3s.alricg.charKomponenten.RegionVolk;
import org.d3s.alricg.charKomponenten.Repraesentation;
import org.d3s.alricg.charKomponenten.Ritual;
import org.d3s.alricg.charKomponenten.Schrift;
import org.d3s.alricg.charKomponenten.Sonderfertigkeit;
import org.d3s.alricg.charKomponenten.Sprache;
import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.charKomponenten.Vorteil;
import org.d3s.alricg.charKomponenten.Zauber;
import org.d3s.alricg.charKomponenten.ZusatzProfession;
import org.d3s.alricg.charKomponenten.charZusatz.Ausruestung;
import org.d3s.alricg.charKomponenten.charZusatz.DaemonenPakt;
import org.d3s.alricg.charKomponenten.charZusatz.Fahrzeug;
import org.d3s.alricg.charKomponenten.charZusatz.FkWaffe;
import org.d3s.alricg.charKomponenten.charZusatz.NahkWaffe;
import org.d3s.alricg.charKomponenten.charZusatz.Ruestung;
import org.d3s.alricg.charKomponenten.charZusatz.Schild;
import org.d3s.alricg.charKomponenten.charZusatz.SchwarzeGabe;
import org.d3s.alricg.charKomponenten.charZusatz.Tier;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.ausruestung;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.daemonenPakt;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.eigenschaft;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.fahrzeug;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.gabe;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.gottheit;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.kultur;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.liturgie;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.nachteil;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.profession;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.rasse;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.region;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.repraesentation;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.ritLitKenntnis;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.ritual;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.ruestung;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.schild;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.schrift;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.schwarzeGabe;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.sonderfertigkeit;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.sprache;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.talent;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.tier;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.vorteil;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.waffeFk;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.waffeNk;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.zauber;
import static org.d3s.alricg.controller.CharKompAdmin.CharKomponente.zusatzProfession;

/**
 * <b>Beschreibung:</b><br>
 * Bietet eine Schnittstelle für alle Komponenten eines Charakters!
 * @author V.Strelow
 * @stereotype singelton
 */
public class CharKompAdmin {
	
	// Herkunft
	private HashMap<String, Rasse> rasseMap = new HashMap<String, Rasse>();
	private HashMap<String, Kultur> kulturMap = new HashMap<String, Kultur>();
	private HashMap<String, Profession> professionMap = new HashMap<String, Profession>();
	private HashMap<String, ZusatzProfession> zusatzProfMap = new HashMap<String, ZusatzProfession>();
	
	// Fertigkeiten & Fähigkeiten
	private HashMap<String, Vorteil> vorteilMap = new HashMap<String, Vorteil>();
	private HashMap<String, Gabe> gabeMap = new HashMap<String, Gabe>();
	private HashMap<String, Nachteil> nachteilMap = new HashMap<String, Nachteil>();
	private HashMap<String, Sonderfertigkeit> sonderfMap = new HashMap<String, Sonderfertigkeit>();
	private HashMap<String, LiturgieRitualKenntnis> ritLitKentMap = new HashMap<String, LiturgieRitualKenntnis>();
	private HashMap<String, Talent> talentMap = new HashMap<String, Talent>();
	private HashMap<String, Zauber> zauberMap = new HashMap<String, Zauber>();
	
	// Sprache
	private HashMap<String, Schrift> schriftMap = new HashMap<String, Schrift>();
	private HashMap<String, Sprache> spracheMap = new HashMap<String, Sprache>();
	
	// Götter & Kulte
	private HashMap<String, Liturgie> liturgieMap = new HashMap<String, Liturgie>();
	private HashMap<String, Ritual> ritualMap = new HashMap<String, Ritual>();
	
	// Ausrüstung
	private HashMap<String, Ausruestung> ausruestungMap = new HashMap<String, Ausruestung>();
	private HashMap<String, Fahrzeug> fahrzeugMap = new HashMap<String, Fahrzeug>();
	private HashMap<String, NahkWaffe> waffeNkMap = new HashMap<String, NahkWaffe>();
	private HashMap<String, FkWaffe> waffeFkMap = new HashMap<String, FkWaffe>();
	private HashMap<String, Ruestung> ruestungMap = new HashMap<String, Ruestung>();
	private HashMap<String, Schild> schildMap = new HashMap<String, Schild>();

	// Zusätzliches
	private HashMap<String, DaemonenPakt> daemonenPaktMap = new HashMap<String, DaemonenPakt>();
	private HashMap<String, SchwarzeGabe> schwarzeGabeMap = new HashMap<String, SchwarzeGabe>();
	private HashMap<String, Tier> tierMap = new HashMap<String, Tier>();
	private HashMap<String, Eigenschaft> eigenschaftMap = new HashMap<String, Eigenschaft>();
	private HashMap<String, RegionVolk> regionMap = new HashMap<String, RegionVolk>();
	private HashMap<String, Gottheit> gottheitMap = new HashMap<String, Gottheit>();
	private HashMap<String, Repraesentation> repraesentMap = new HashMap<String, Repraesentation>();
	
	// Die Enums der Komponeten selbst
	private HashMap<String, CharKomponente> charKompMap = new HashMap<String, CharKomponente>();

	
	/**
	 * Privater Konstruktor - wird nur über initCharKompAdmin() aufgerufen!
	 */
	public CharKompAdmin() {
		// Initialiserung der HashMap für schnellen Zugriff auf Komponenten 
		// über deren ID
		for (int i = 0; i < CharKomponente.values().length; i++) {
			charKompMap.put(CharKomponente.values()[i].getPrefix(), 
							CharKomponente.values()[i]
							);
		}
	}
	
	/**
	 * Initialisiert die übergebene CharKomponent und erstellt zu jeder ID das Objekt.
	 * Es wird ein sortiertes Array erstellt, wobei die Objekte nur die ID zugewiesen
	 * bekommen haben.
	 * @param ids Eine Liste mit allen IDs der Objekte die angelegt werden sollen
	 * @param CharKomp Der Typ der CharKomponente
	 */
	public void initCharKomponents(ArrayList<String> ids, CharKomponente charKomp) {
		
		switch (charKomp) {
// >>>>>>>>>>>>>>> Herkunft
			case rasse:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), rasseMap);
					rasseMap.put(ids.get(i), new Rasse(ids.get(i)));
				}
				break;
			case kultur:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), kulturMap);
					kulturMap.put(ids.get(i), new Kultur(ids.get(i)));
				}
				break;
			case profession:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), professionMap);
					professionMap.put(ids.get(i), new Profession(ids.get(i)));
				}
				break;
			case zusatzProfession:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), zusatzProfMap);
					zusatzProfMap.put(ids.get(i), new ZusatzProfession(ids.get(i)));
				}
				break;
				
// >>>>>>>>>>>>>>> Fertigkeiten & Fähigkeiten
			case vorteil:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), vorteilMap);
					vorteilMap.put(ids.get(i), new Vorteil(ids.get(i)));
				}
				break;
			case gabe:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), gabeMap);
					gabeMap.put(ids.get(i), new Gabe(ids.get(i)));
				}
				break;
			case nachteil:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), nachteilMap);
					nachteilMap.put(ids.get(i), new Nachteil(ids.get(i)));
				}
				break;
			case sonderfertigkeit:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), sonderfMap);
					sonderfMap.put(ids.get(i), new Sonderfertigkeit(ids.get(i)));
				}
				break;
			case ritLitKenntnis:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), ritLitKentMap);
					ritLitKentMap.put(ids.get(i), new LiturgieRitualKenntnis(ids.get(i)));
				}
				break;
			case talent:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), talentMap);
					talentMap.put(ids.get(i), new Talent(ids.get(i)));
				}
				break;
			case zauber:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), zauberMap);
					zauberMap.put(ids.get(i), new Zauber(ids.get(i)));
				}
				break;
				
// >>>>>>>>>>>>>>> Sprachen
			case sprache:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), spracheMap);
					spracheMap.put(ids.get(i), new Sprache(ids.get(i)));
				}
				break;
			case schrift:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), schriftMap);
					schriftMap.put(ids.get(i), new Schrift(ids.get(i)));
				}
				break;
				
// >>>>>>>>>>>>>>> Götter				
			case liturgie:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), liturgieMap);
					liturgieMap.put(ids.get(i), new Liturgie(ids.get(i)));
				}
				break;
			case ritual:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), ritualMap);
					ritualMap.put(ids.get(i), new Ritual(ids.get(i)));
				}
				break;
				
// >>>>>>>>>>>>>>> Ausrüstung
			case ausruestung:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), ausruestungMap);
					ausruestungMap.put(ids.get(i), new Ausruestung(ids.get(i)));
				}
				break;
			case fahrzeug:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), fahrzeugMap);
					fahrzeugMap.put(ids.get(i), new Fahrzeug(ids.get(i)));
				}
				break;
			case waffeNk:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), waffeNkMap);
					waffeNkMap.put(ids.get(i), new NahkWaffe(ids.get(i)));
				}
				break;
			case waffeFk:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), waffeFkMap);
					waffeFkMap.put(ids.get(i), new FkWaffe(ids.get(i)));
				}
				break;
			case ruestung:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), ruestungMap);
					ruestungMap.put(ids.get(i), new Ruestung(ids.get(i)));
				}
				break;
			case schild:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), schildMap);
					schildMap.put(ids.get(i), new Schild(ids.get(i)));
				}
				break;
				
// >>>>>>>>>>>>>>> Zusätzliches
			case daemonenPakt:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), daemonenPaktMap);
					daemonenPaktMap.put(ids.get(i), new DaemonenPakt(ids.get(i)));
				}
				break;
			case schwarzeGabe:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), schwarzeGabeMap);
					schwarzeGabeMap.put(ids.get(i), new SchwarzeGabe(ids.get(i)));
				}
				break;
			case tier:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), tierMap);
					tierMap.put(ids.get(i), new Tier(ids.get(i)));
				}
				break;
			case region:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), regionMap);
					regionMap.put(ids.get(i), new RegionVolk(ids.get(i)));
				}
				break;
			case gottheit:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), gottheitMap);
					gottheitMap.put(ids.get(i), new Gottheit(ids.get(i)));
				}
				break;
			case repraesentation:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), repraesentMap);
					repraesentMap.put(ids.get(i), new Repraesentation(ids.get(i)));
				}
				break;
			case eigenschaft:
				for (int i = 0; i < ids.size(); i++) {
					keyDoppelt(ids.get(i), eigenschaftMap);
					eigenschaftMap.put(ids.get(i), new Eigenschaft(ids.get(i)));
				}
				break;
				

// >>>>>>>>>>>>>>> DEFAULT			
			default:
				ProgAdmin.logger.logp(Level.SEVERE, "CharKompAdmin", 
						"initCharKomponents", "Ein CharKomp wurde nicht gefunden: " 
						+ charKomp);
		}
	}
	
	/**
	 * Liefert eine CharKomponente, die zu einer ID gehört zurück. 
	 * @param id Die ID die gesucht wird
	 * @param charKomp Die art der Charkomponente
	 * @return Die gesuchte Charkomponente oder "null", falls zu dem Schlüssel keine 
	 * CharKomponente gefunden werden kann.
	 */
	public CharElement getCharElement(String id, CharKomponente charKomp) {
		HashMap<String, ? extends CharElement> hashMap;
		
		hashMap = getHashMap(charKomp); // Die zugehörige HashMap holen
		
		if ( hashMap.get(id) == null ) {
			// TODO Evtl. noch eine bessere Meldung einbauen!
			ProgAdmin.logger.warning("Id konnte nicht gefunden werden: " + id);
		}
		
		return hashMap.get(id);
	}
	
	/**
	 * Schreibt alle enthaltenen Charakter-Elemente in ein einziges root element,
	 * dieses ist ein Element mit einem "alricgXML"-Tag.
	 * @return "alricgXML" Element mit allen enthaltenen Elementen.
	 */
	public Element writeXML() {
		CharKomponente[] charKompArray;
		Iterator ite;
		Element tmpElement = null;
		
		Element root = new Element("alricgXML");
		charKompArray = CharKomponente.values();
		
		// TODO Die preamble noch hinzufügen
		
		// Alle charKomponenten durchgehen 
		for (int i = 0; i < charKompArray.length; i++) {
			
			if (charKompArray[i] == eigenschaft) {
				continue; // Eigenschaften werden nicht geschrieben, daher Abbruch
			}
			
			ite = getHashMap(charKompArray[i]).values().iterator(); // Alle Elemente holen
			
			// Das "Box" Element hinzufügen, wenn es Unterelemente gibt
			if (ite.hasNext()) {
				tmpElement = new Element(charKompArray[i].getXmlBoxTag());
				root.appendChild(tmpElement);
			}
			
			// Alle Elemente der CharKomponente durchgehen
			while ( ite.hasNext() ) {
				Element testElement = ((CharElement) ite.next()).writeXmlElement();
				
				tmpElement.appendChild( testElement );
			}
		}
		
		return root;
	}
	
	/**
	 * Liefert alle charKomponenten eines gewünschten Typs
	 * @param charKomp Der gewünschte Typ
	 * @return Eine Collection mit allen charKomponenten des gewünschten Typs
	 */
	public Collection<? extends CharElement> getCollection(CharKomponente charKomp) {
		
		return getHashMap(charKomp).values();
	}
	
	/**
	 * Ermöglicht den Zugriff auf die HashMap mit den charKomponenten.
	 * @param charKomp Die CharKomponente zu der die HashMap zurückgegeben werden soll
	 * @return HashMap mit allen Elementen zu dieser CharKomponente
	 */
	private HashMap<String, ? extends CharElement> getHashMap(CharKomponente charKomp) {
		
		switch (charKomp) {
//		 >>>>>>>>>>>>>>> Herkunft
			case rasse: 		return rasseMap;
			case kultur:		return kulturMap;
			case profession:	return professionMap;
			case zusatzProfession: return zusatzProfMap;
						
//		 >>>>>>>>>>>>>>> Fertigkeiten & Fähigkeiten
			case vorteil:		return vorteilMap;
			case gabe:			return gabeMap;
			case nachteil:		return nachteilMap;
			case sonderfertigkeit: return sonderfMap;
			case ritLitKenntnis:		return ritLitKentMap;
			case talent:		return talentMap;
			case zauber:		return zauberMap;
			
//		 >>>>>>>>>>>>>>> Sprachen
			case sprache:		return spracheMap;
			case schrift:		return schriftMap;
			
//		 >>>>>>>>>>>>>>> Götter				
			case liturgie: 		return liturgieMap;
			case ritual:		return ritualMap;
			
//		 >>>>>>>>>>>>>>> Ausrüstung
			case ausruestung:	return ausruestungMap;
			case fahrzeug:		return fahrzeugMap;
			case waffeNk:		return waffeNkMap;
			case waffeFk:		return waffeFkMap;
			case ruestung:		return ruestungMap;
			case schild:		return schildMap;
			
//		 >>>>>>>>>>>>>>> Zusätzliches
			case daemonenPakt:	return daemonenPaktMap;
			case schwarzeGabe:	return schwarzeGabeMap;
			case tier:			return tierMap;
			case region:		return regionMap;
			case gottheit:		return gottheitMap;
			case repraesentation: return repraesentMap;
			case eigenschaft:	return eigenschaftMap;
			
//		 >>>>>>>>>>>>>>> DEFAULT			
			default:
				ProgAdmin.logger.severe("Ein CharKomp wurde nicht gefunden: " 
						+ charKomp);
		}
		
		return null;
	}

	/**
	 * Wird aufgerufen, um zu prüfen ob ein ID Wert doppelt vorkommt! In dem Fall wird eine
	 * Warnung ausgegeben, aber nicht verhindert das der alte Wert überschrieben wird!
	 * @param key Der Key der überprüft werden soll
	 */
	private void keyDoppelt(String key, HashMap<String, ? extends CharElement> hash) {
		if ( hash.containsKey(key) ) {
			// Doppelte ID, dadurch wird der alte Wert überschrieben
			ProgAdmin.logger.warning("Bei der Initialisierung ist eine ID doppelt für die HashMap: " + key);
			ProgAdmin.messenger.sendFehler(Library.getErrorTxt("CharKomponente ID doppelt"));
		}
	}
	
	/**
	 * Erlaubt einen Zurgiff auf eine bestimmt charKomponente anhand der ID, 
	 * bzw. des Prefixes aus der ID.
	 * (Z.B. "RAS-Zwerg") "RAS" ist das Prefix, das für alle Rassen-Ids gilt.
	 * @param id Die ID mit dem Prefix
	 * @return CharKomponente zu dem Prefix der ID
	 */
	public CharKomponente getCharKompFromId(String id) {
		String prefix = "";
		
		try {
			prefix = id.split("-")[0]; // Spaltet den Prefix von der ID ab
		} catch (ArrayIndexOutOfBoundsException e) {
			ProgAdmin.logger.severe("prefix falsch aufgebaut! \n" + e.toString());
		}
		
		return getCharKompFromPrefix(prefix);
	}
	
	/**
	 * Erlaubt einen Zurgiff auf eine bestimmt charKomponente anhand der ID, 
	 * bzw. des Prefixes einer ID.
	 * @param prefix Der prefix 
	 * @return CharKomponente zu dem Prefix
	 */
	public CharKomponente getCharKompFromPrefix(String prefix) {
		assert charKompMap.get(prefix) != null; // Gültigkeitsprüfung
		
		return charKompMap.get(prefix);
	}
	
	/**
	 * <u>Beschreibung:</u><br> 
	 * Eine Konstante für alle Charakter Komponenten die vorkommen können.
	 * Die einzehnen Konstanten sind mit Zusatzinformationen angereichert.
	 * WICHTIG! Die Reihenfolge bestimmt auch die Reihenfolge wie die 
	 * Elemente in ein File geschrieben werden!
	 * @author V. Strelow
	 */
	public enum CharKomponente {
		rasse("rassen" , "RAS"), 
		kultur("kulturen", "KUL"), 
		profession("professionen", "PRO"),
		zusatzProfession("zusatzProfessionen", "ZPR"),
		vorteil("vorteile", "VOR"),
		gabe("vorteileGaben", "GAB"),
		nachteil("nachteile", "NAC"),
		sonderfertigkeit("sonderfertigkeiten", "SF"),
		ritLitKenntnis("liturgieRitualKenntnise", "LRK"),
		talent("talente", "TAL"),
		sprache("sprachen", "SPR"),
		schrift("schriften", "SFT"),
		zauber("zauber", "ZAU"),
		liturgie("liturgien", "LIT"),
		ritual("rituale", "RIT"),
		ausruestung("ausruestung", "AUS"),
		fahrzeug("fahrzeuge", "FAH"),
		waffeNk("nkWaffen", "NKW"),
		waffeFk("fkWaffen", "FKW"),
		ruestung("ruestungen", "RUE"),
		schild("schilde", "SLD"),
		daemonenPakt("daemonenPakte", "DAE"),
		schwarzeGabe("schwarzeGaben", "SGA"),
		tier("tiere", "TIE"),
		region("regionen", "REG"),
		gottheit("gottheiten", "GOT"),
		repraesentation("repraesentationen", "REP"),
		eigenschaft("x", "EIG"); // Hat keinen XML Tag, wird durch Source-Code gefüllt
		
//		Der XML-Tag der Komponente
		private String xmlTag; // XML-Tag das alle entsprechenden Elemente umschließt
		private String prefix; // Prefix der ID aller entsprechenden Elemente
		
		/**
		 * Konstruktor
		 * @param xmlTag XML-Tag des Elements, der alle anderen entrys umschließt
		 * @param hashMap Enthält alle Elemente die zu dieser CharKomponente gehören
		 */
		private CharKomponente(String xmlTag, String prefix) {
			this.xmlTag = xmlTag;
			this.prefix = prefix;
		}	
		
		/**
		 * @return Den XML-Tag des Elements. Und zwar der, der alle Elemente
		 * der Art umschließt (also z.B. "rassen", NICHT "rasse")
		 */
		public String getXmlBoxTag() {
			return xmlTag;
		}
		
		/**
		 * @return Die ersten Buchstaben einer jeden ID einer Charakter-Komponente. 
		 * Mit dem Buchstaben kann man die Art der Komponente bestimmen.
		 */
		public String getPrefix() {
			return prefix;
		}

	}
}
