/*
 * Created 23. Januar 2005 / 16:09:44
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import org.d3s.alricg.CharKomponenten.Gabe;
import org.d3s.alricg.CharKomponenten.Kultur;
import org.d3s.alricg.CharKomponenten.Liturgie;
import org.d3s.alricg.CharKomponenten.Nachteil;
import org.d3s.alricg.CharKomponenten.Profession;
import org.d3s.alricg.CharKomponenten.Rasse;
import org.d3s.alricg.CharKomponenten.Ritual;
import org.d3s.alricg.CharKomponenten.RitusSF;
import org.d3s.alricg.CharKomponenten.Schrift;
import org.d3s.alricg.CharKomponenten.Sonderfertigkeit;
import org.d3s.alricg.CharKomponenten.Sprache;
import org.d3s.alricg.CharKomponenten.Talent;
import org.d3s.alricg.CharKomponenten.Vorteil;
import org.d3s.alricg.CharKomponenten.Zauber;
import org.d3s.alricg.CharKomponenten.ZusatzProfession;
import org.d3s.alricg.CharKomponenten.CharZusatz.Ausruestung;
import org.d3s.alricg.CharKomponenten.CharZusatz.DaemonenPakt;
import org.d3s.alricg.CharKomponenten.CharZusatz.Fahrzeug;
import org.d3s.alricg.CharKomponenten.CharZusatz.FkWaffe;
import org.d3s.alricg.CharKomponenten.CharZusatz.NahkWaffe;
import org.d3s.alricg.CharKomponenten.CharZusatz.Ruestung;
import org.d3s.alricg.CharKomponenten.CharZusatz.Schild;
import org.d3s.alricg.CharKomponenten.CharZusatz.SchwarzeGabe;
import org.d3s.alricg.CharKomponenten.CharZusatz.Tier;
import static org.d3s.alricg.Controller.CharKompAdmin.CharKomponenten.kultur;
import static org.d3s.alricg.Controller.CharKompAdmin.CharKomponenten.profession;
import static org.d3s.alricg.Controller.CharKompAdmin.CharKomponenten.rasse;

/**
 * <b>Beschreibung:</b><br>
 * Bietet eine Schnittstelle für alle Komponenten eines Charakters!
 * @author V.Strelow
 * @stereotype singelton
 */
public class CharKompAdmin {
	public static CharKompAdmin self;
	
	// Herkunft
	private Rasse[] rasseList;
	private Kultur[] kulterList;
	private Profession[] profList;
	private ZusatzProfession[] zusatzProfList;
	
	// Fertigkeiten & Fähigkeiten
	private Vorteil[] vorteilList;
	private Gabe[] gabeList;
	private Nachteil[] nachteilList;
	private Sonderfertigkeit[] sondefList;
	private RitusSF[] ritusSfList;
	private Talent[] talentList;
	private Zauber[] zauberList;
	
	// Sprache
	private Schrift[] schriftList;
	private Sprache[] spracheList;
	
	// Götter & Kulte
	private Liturgie[] liturgieList;
	private Ritual[] ritualList;
	
	// Ausrüstung
	private Ausruestung[] ausruestungList;
	private Fahrzeug[] fahrzeugList;
	private NahkWaffe[] waffeNkList;
	private FkWaffe[] waffeFkList;
	private Ruestung[] ruestungList;
	private Schild[] schildList;

	// Zusätzliches
	private DaemonenPakt[] daemonenPaktList;
	private SchwarzeGabe[] schwarzeGabeList;
	private Tier[] tierList;

	/**
	 * Initialisiert die übergebene CharKomponent und erstellt zu jeder ID das Objekt.
	 * Es wird ein sortiertes Array erstellt, wobei die Objekte nur die ID zugewiesen
	 * bekommen haben.
	 * @param ids
	 * @param CharKomp
	 */
	public void initCharKomponents(ArrayList<String> ids, CharKomponenten CharKomp) {
		
		switch (CharKomp) {
// >>>>>>>>>>>>>>> Herkunft
			case rasse:
				rasseList = new Rasse[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					rasseList[i] = new Rasse(ids.get(i));
				}
				Arrays.sort(rasseList);
				break;
			case kultur:
				kulterList = new Kultur[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					kulterList[i] = new Kultur(ids.get(i));
				}
				Arrays.sort(kulterList);
				break;
			case profession:
				profList = new Profession[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					profList[i] = new Profession(ids.get(i));
				}
				Arrays.sort(profList);
				break;
			case zusatzProfession:
				zusatzProfList = new ZusatzProfession[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					zusatzProfList[i] = new ZusatzProfession(ids.get(i));
				}
				Arrays.sort(zusatzProfList);
				break;
				
// >>>>>>>>>>>>>>> Fertigkeiten & Fähigkeiten
			case vorteil:
				vorteilList = new Vorteil[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					vorteilList[i] = new Vorteil(ids.get(i));
				}
				Arrays.sort(vorteilList);
				break;
			case gabe:
				gabeList = new Gabe[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					gabeList[i] = new Gabe(ids.get(i));
				}
				Arrays.sort(gabeList);
				break;
			case nachteil:
				nachteilList = new Nachteil[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					nachteilList[i] = new Nachteil(ids.get(i));
				}
				Arrays.sort(nachteilList);
				break;
			case sonderfertigkeit:
				sondefList = new Sonderfertigkeit[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					sondefList[i] = new Sonderfertigkeit(ids.get(i));
				}
				Arrays.sort(sondefList);
				break;
			case ritusSF:
				ritusSfList = new RitusSF[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					ritusSfList[i] = new RitusSF(ids.get(i));
				}
				Arrays.sort(ritusSfList);
				break;
			case talent:
				talentList = new Talent[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					talentList[i] = new Talent(ids.get(i));
				}
				Arrays.sort(talentList);
				break;
			case zauber:
				zauberList = new Zauber[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					zauberList[i] = new Zauber(ids.get(i));
				}
				Arrays.sort(zauberList);
				break;
				
// >>>>>>>>>>>>>>> Sprachen
			case sprache:
				spracheList = new Sprache[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					spracheList[i] = new Sprache(ids.get(i));
				}
				Arrays.sort(spracheList);
				break;
			case schrift:
				schriftList = new Schrift[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					schriftList[i] = new Schrift(ids.get(i));
				}
				Arrays.sort(schriftList);
				break;
				
// >>>>>>>>>>>>>>> Götter				
			case liturgie:
				liturgieList = new Liturgie[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					liturgieList[i] = new Liturgie(ids.get(i));
				}
				Arrays.sort(liturgieList);
				break;
			case ritual:
				ritualList = new Ritual[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					ritualList[i] = new Ritual(ids.get(i));
				}
				Arrays.sort(ritualList);
				break;
				
// >>>>>>>>>>>>>>> Ausrüstung
			case ausruestung:
				ausruestungList = new Ausruestung[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					ausruestungList[i] = new Ausruestung(ids.get(i));
				}
				Arrays.sort(ausruestungList);
				break;
			case fahrzeug:
				fahrzeugList = new Fahrzeug[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					fahrzeugList[i] = new Fahrzeug(ids.get(i));
				}
				Arrays.sort(fahrzeugList);
				break;
			case waffeNk:
				waffeNkList = new NahkWaffe[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					waffeNkList[i] = new NahkWaffe(ids.get(i));
				}
				Arrays.sort(waffeNkList);
				break;
			case waffeFk:
				waffeFkList = new FkWaffe[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					waffeFkList[i] = new FkWaffe(ids.get(i));
				}
				Arrays.sort(waffeFkList);
				break;
			case ruestung:
				ruestungList = new Ruestung[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					ruestungList[i] = new Ruestung(ids.get(i));
				}
				Arrays.sort(ruestungList);
				break;
			case schild:
				schildList = new Schild[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					schildList[i] = new Schild(ids.get(i));
				}
				Arrays.sort(schildList);
				break;
				
// >>>>>>>>>>>>>>> Zusätzliches
			case daemonenPakt:
				daemonenPaktList = new DaemonenPakt[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					daemonenPaktList[i] = new DaemonenPakt(ids.get(i));
				}
				Arrays.sort(daemonenPaktList);
				break;
			case schwarzeGabe:
				schwarzeGabeList = new SchwarzeGabe[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					schwarzeGabeList [i] = new SchwarzeGabe(ids.get(i));
				}
				Arrays.sort(schwarzeGabeList);
				break;
			case tier:
				tierList = new Tier[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					tierList [i] = new Tier(ids.get(i));
				}
				Arrays.sort(tierList);
				break;
				
// >>>>>>>>>>>>>>> DEFAULT			
			default:
				ProgAdmin.logger.logp(Level.SEVERE, "CharKompAdmin", 
						"initCharKomponents", "Ein CharKomp wurde nicht gefunden: " 
						+ CharKomp);
		}
	}
	
	
	public <T> T getCharElement(String id, CharKomponenten charKomp) {
		int idx;
		idx = Arrays.binarySearch(rasseList, id);
		
		return (T) rasseList[idx];
	}
	
	public enum CharKomponenten {
		rasse("rassen"), 
		kultur("kulturen"), 
		profession("professionen"),
		zusatzProfession("zusatzProfessionen"),
		vorteil("vorteile"),
		gabe("vorteileGaben"),
		nachteil("nachteil"),
		sonderfertigkeit("sonderfertigkeiten"),
		ritusSF("sonderfertigkeitenLiRi"),
		talent("talente"),
		zauber("zauber"),
		sprache("sprachen"),
		schrift("schriften"),
		liturgie("liturgien"),
		ritual("rituale"),
		ausruestung("ausruestung"),
		fahrzeug("fahrzeuge"),
		waffeNk("nkWaffen"),
		waffeFk("fkWaffen"),
		ruestung("ruestungen"),
		schild("schilde"),
		daemonenPakt("daemonenPakte"),
		schwarzeGabe("schwarzeGaben"),
		tier("tiere");
		
		private String xmlTag;
		
		private CharKomponenten(String xmlTag) {
			this.xmlTag = xmlTag;
		}
		
		public String getXmlBoxTag() {
			return xmlTag;
		}
	}
}
