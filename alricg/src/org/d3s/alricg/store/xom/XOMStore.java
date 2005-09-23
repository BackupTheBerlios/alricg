package org.d3s.alricg.store.xom;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Eigenschaft;
import org.d3s.alricg.charKomponenten.Gabe;
import org.d3s.alricg.charKomponenten.Gottheit;
import org.d3s.alricg.charKomponenten.Kultur;
import org.d3s.alricg.charKomponenten.KulturVariante;
import org.d3s.alricg.charKomponenten.Liturgie;
import org.d3s.alricg.charKomponenten.LiturgieRitualKenntnis;
import org.d3s.alricg.charKomponenten.Nachteil;
import org.d3s.alricg.charKomponenten.Profession;
import org.d3s.alricg.charKomponenten.ProfessionVariante;
import org.d3s.alricg.charKomponenten.Rasse;
import org.d3s.alricg.charKomponenten.RasseVariante;
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
import org.d3s.alricg.controller.CharKomponente;
import static org.d3s.alricg.controller.CharKomponente.ausruestung;
import static org.d3s.alricg.controller.CharKomponente.daemonenPakt;
import static org.d3s.alricg.controller.CharKomponente.eigenschaft;
import static org.d3s.alricg.controller.CharKomponente.fahrzeug;
import static org.d3s.alricg.controller.CharKomponente.gabe;
import static org.d3s.alricg.controller.CharKomponente.gottheit;
import static org.d3s.alricg.controller.CharKomponente.kultur;
import static org.d3s.alricg.controller.CharKomponente.kulturVariante;
import static org.d3s.alricg.controller.CharKomponente.liturgie;
import static org.d3s.alricg.controller.CharKomponente.nachteil;
import static org.d3s.alricg.controller.CharKomponente.profession;
import static org.d3s.alricg.controller.CharKomponente.professionVariante;
import static org.d3s.alricg.controller.CharKomponente.rasse;
import static org.d3s.alricg.controller.CharKomponente.rasseVariante;
import static org.d3s.alricg.controller.CharKomponente.region;
import static org.d3s.alricg.controller.CharKomponente.repraesentation;
import static org.d3s.alricg.controller.CharKomponente.ritLitKenntnis;
import static org.d3s.alricg.controller.CharKomponente.ritual;
import static org.d3s.alricg.controller.CharKomponente.ruestung;
import static org.d3s.alricg.controller.CharKomponente.schild;
import static org.d3s.alricg.controller.CharKomponente.schrift;
import static org.d3s.alricg.controller.CharKomponente.schwarzeGabe;
import static org.d3s.alricg.controller.CharKomponente.sonderfertigkeit;
import static org.d3s.alricg.controller.CharKomponente.sonderregel;
import static org.d3s.alricg.controller.CharKomponente.sprache;
import static org.d3s.alricg.controller.CharKomponente.talent;
import static org.d3s.alricg.controller.CharKomponente.tier;
import static org.d3s.alricg.controller.CharKomponente.vorteil;
import static org.d3s.alricg.controller.CharKomponente.waffeFk;
import static org.d3s.alricg.controller.CharKomponente.waffeNk;
import static org.d3s.alricg.controller.CharKomponente.zauber;
import static org.d3s.alricg.controller.CharKomponente.zusatzProfession;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.store.ConfigStore;
import org.d3s.alricg.store.DataStore;
import org.d3s.alricg.store.FactoryFinder;
import org.d3s.alricg.store.TextStore;

public class XOMStore implements DataStore {

    // Herkunft
    private final Map<String, Rasse> rasseMap = new HashMap<String, Rasse>();

    private final Map<String, Kultur> kulturMap = new HashMap<String, Kultur>();

    private final Map<String, Profession> professionMap = new HashMap<String, Profession>();

    private final Map<String, RasseVariante> rasseVarianteMap = new HashMap<String, RasseVariante>();

    private final Map<String, KulturVariante> kulturVarianteMap = new HashMap<String, KulturVariante>();

    private final Map<String, ProfessionVariante> professionVarianteMap = new HashMap<String, ProfessionVariante>();

    private final Map<String, ZusatzProfession> zusatzProfMap = new HashMap<String, ZusatzProfession>();

    // Fertigkeiten & Fähigkeiten
    private final Map<String, Vorteil> vorteilMap = new HashMap<String, Vorteil>();

    private final Map<String, Gabe> gabeMap = new HashMap<String, Gabe>();

    private final Map<String, Nachteil> nachteilMap = new HashMap<String, Nachteil>();

    private final Map<String, Sonderfertigkeit> sonderfMap = new HashMap<String, Sonderfertigkeit>();

    private final Map<String, LiturgieRitualKenntnis> ritLitKentMap = new HashMap<String, LiturgieRitualKenntnis>();

    private final Map<String, Talent> talentMap = new HashMap<String, Talent>();

    private final Map<String, Zauber> zauberMap = new HashMap<String, Zauber>();

    // Sprache
    private final Map<String, Schrift> schriftMap = new HashMap<String, Schrift>();

    private final Map<String, Sprache> spracheMap = new HashMap<String, Sprache>();

    // Götter & Kulte
    private final Map<String, Liturgie> liturgieMap = new HashMap<String, Liturgie>();

    private final Map<String, Ritual> ritualMap = new HashMap<String, Ritual>();

    // Ausrüstung
    private final Map<String, Ausruestung> ausruestungMap = new HashMap<String, Ausruestung>();

    private final Map<String, Fahrzeug> fahrzeugMap = new HashMap<String, Fahrzeug>();

    private final Map<String, NahkWaffe> waffeNkMap = new HashMap<String, NahkWaffe>();

    private final Map<String, FkWaffe> waffeFkMap = new HashMap<String, FkWaffe>();

    private final Map<String, Ruestung> ruestungMap = new HashMap<String, Ruestung>();

    private final Map<String, Schild> schildMap = new HashMap<String, Schild>();

    // Zusätzliches
    private final Map<String, DaemonenPakt> daemonenPaktMap = new HashMap<String, DaemonenPakt>();

    private final Map<String, SchwarzeGabe> schwarzeGabeMap = new HashMap<String, SchwarzeGabe>();

    private final Map<String, Tier> tierMap = new HashMap<String, Tier>();

    private final Map<String, Eigenschaft> eigenschaftMap = new HashMap<String, Eigenschaft>();

    private final Map<String, RegionVolk> regionMap = new HashMap<String, RegionVolk>();

    private final Map<String, Gottheit> gottheitMap = new HashMap<String, Gottheit>();

    private final Map<String, Repraesentation> repraesentMap = new HashMap<String, Repraesentation>();

    // Die Enums der Komponeten selbst
    private final Map<String, CharKomponente> charKompMap = new HashMap<String, CharKomponente>();

    /**
     * Privater Konstruktor - wird nur über initCharKompAdmin() aufgerufen!
     */
    public XOMStore() {
        // Initialiserung der HashMap für schnellen Zugriff auf Komponenten
        // über deren ID
        for (int i = 0; i < CharKomponente.values().length; i++) {
            charKompMap.put(CharKomponente.values()[i].getPrefix(), CharKomponente.values()[i]);
        }
    }

    /**
     * Liefert eine CharKomponente, die zu einer ID gehört zurück. Wenn die CharKomponente zu der ID bekannt ist, sollte
     * aus performance Gründen "getCharElement(String id, CharKomponente charKomp)" benutzt werden.
     * 
     * @param id Die ID die gesucht wird
     * @return Die gesuchte Charkomponente oder "null", falls zu dem Schlüssel keine CharKomponente gefunden werden
     *         kann.
     */
    public CharElement getCharElement(String id) {
        return getCharElement(id, this.getCharKompFromId(id));
    }

    /**
     * Liefert eine CharKomponente, die zu einer ID gehört zurück.
     * 
     * @param id Die ID die gesucht wird
     * @param charKomp Die art der Charkomponente
     * @return Die gesuchte Charkomponente oder "null", falls zu dem Schlüssel keine CharKomponente gefunden werden
     *         kann.
     */
    public CharElement getCharElement(String id, CharKomponente charKomp) {
        final Map<String, ? extends CharElement> map = getMap(charKomp); // Die zugehörige HashMap holen
        if (map.get(id) == null) {
            ProgAdmin.logger.warning("Id konnte nicht gefunden werden: " + id);
        }
        return map.get(id);
    }

    /**
     * Schreibt alle enthaltenen Charakter-Elemente in ein einziges root element, dieses ist ein Element mit einem
     * "alricgXML"-Tag.
     * 
     * @return "alricgXML" Element mit allen enthaltenen Elementen.
     */
    public Element writeXML() {
        // TODO muss noch ganz anders gemaccht werden
        CharKomponente[] charKompArray;
        Iterator ite;
        Element tmpElement = null;

        Element root = new Element("alricgXML");
        charKompArray = CharKomponente.values();

        // TODO Die preamble noch hinzufügen

        // Alle charKomponenten durchgehen
        for (int i = 0; i < charKompArray.length; i++) {

            if (charKompArray[i] == CharKomponente.eigenschaft || charKompArray[i] == CharKomponente.sonderregel
                    || charKompArray[i] == CharKomponente.rasseVariante
                    || charKompArray[i] == CharKomponente.kulturVariante
                    || charKompArray[i] == CharKomponente.professionVariante) {
                continue; // Diese CharElemente werden nicht (hier) geschrieben, daher Abbruch
            }

            ite = getMap(charKompArray[i]).values().iterator(); // Alle Elemente holen

            // Das "Box" Element hinzufügen, wenn es Unterelemente gibt
            if (ite.hasNext()) {
                tmpElement = new Element(charKompArray[i].getKategorie());
                root.appendChild(tmpElement);
            }

            // Alle Elemente der CharKomponente durchgehen
            while (ite.hasNext()) {
                Element testElement = ((CharElement) ite.next()).writeXmlElement();

                tmpElement.appendChild(testElement);
            }
        }

        return root;
    }

    /**
     * Liefert alle charKomponenten eines gewünschten Typs
     * 
     * @param charKomp Der gewünschte Typ
     * @return Eine Collection mit allen charKomponenten des gewünschten Typs
     */
    public Collection<CharElement> getUnmodifieableCollection(CharKomponente charKomp) {
        return Collections.unmodifiableCollection(getMap(charKomp).values());
    }

    /**
     * Ermöglicht den lesenden Zugriff auf die map mit den charKomponenten. Bem.: Durch die Konstruktion
     * <code>Map<String, ? extends CharElement></code> kann kein Element zu einer <code>map</code> hinzugefügt
     * werden.
     * 
     * @param charKomp Die CharKomponente zu der die HashMap zurückgegeben werden soll
     * @return HashMap mit allen Elementen zu dieser CharKomponente
     */
    public Map<String, ? extends CharElement> getMap(CharKomponente charKomp) {

        switch (charKomp) {
        // >>>>>>>>>>>>>>> Herkunft
        case rasse:
            return rasseMap;
        case kultur:
            return kulturMap;
        case profession:
            return professionMap;
        case rasseVariante:
            return rasseVarianteMap;
        case kulturVariante:
            return kulturVarianteMap;
        case professionVariante:
            return professionVarianteMap;
        case zusatzProfession:
            return zusatzProfMap;

        // >>>>>>>>>>>>>>> Fertigkeiten & Fähigkeiten
        case vorteil:
            return vorteilMap;
        case gabe:
            return gabeMap;
        case nachteil:
            return nachteilMap;
        case sonderfertigkeit:
            return sonderfMap;
        case ritLitKenntnis:
            return ritLitKentMap;
        case talent:
            return talentMap;
        case zauber:
            return zauberMap;

        // >>>>>>>>>>>>>>> Sprachen
        case sprache:
            return spracheMap;
        case schrift:
            return schriftMap;

        // >>>>>>>>>>>>>>> Götter
        case liturgie:
            return liturgieMap;
        case ritual:
            return ritualMap;

        // >>>>>>>>>>>>>>> Ausrüstung
        case ausruestung:
            return ausruestungMap;
        case fahrzeug:
            return fahrzeugMap;
        case waffeNk:
            return waffeNkMap;
        case waffeFk:
            return waffeFkMap;
        case ruestung:
            return ruestungMap;
        case schild:
            return schildMap;

        // >>>>>>>>>>>>>>> Zusätzliches
        case daemonenPakt:
            return daemonenPaktMap;
        case schwarzeGabe:
            return schwarzeGabeMap;
        case tier:
            return tierMap;
        case region:
            return regionMap;
        case gottheit:
            return gottheitMap;
        case repraesentation:
            return repraesentMap;
        case eigenschaft:
            return eigenschaftMap;

        // >>>>>>>>>>>>>>> DEFAULT
        default:
            ProgAdmin.logger.severe("Ein CharKomp wurde nicht gefunden: " + charKomp);
        }

        return null;
    }

    /**
     * Erlaubt einen Zurgiff auf eine bestimmt charKomponente anhand der ID, bzw. des Prefixes aus der ID. (Z.B.
     * "RAS-Zwerg") "RAS" ist das Prefix, das für alle Rassen-Ids gilt.
     * 
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
     * Erlaubt einen Zurgiff auf eine bestimmt charKomponente anhand der ID, bzw. des Prefixes einer ID.
     * 
     * @param prefix Der prefix
     * @return CharKomponente zu dem Prefix
     */
    public CharKomponente getCharKompFromPrefix(String prefix) {
        assert charKompMap.get(prefix) != null; // Gültigkeitsprüfung

        return charKompMap.get(prefix);
    }

    public void initCharKomponents(List<String> ids, CharKomponente charKomp) {
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
        case rasseVariante:
            for (int i = 0; i < ids.size(); i++) {
                keyDoppelt(ids.get(i), rasseVarianteMap);
                rasseVarianteMap.put(ids.get(i), new RasseVariante(ids.get(i)));
            }
            break;
        case kulturVariante:
            for (int i = 0; i < ids.size(); i++) {
                keyDoppelt(ids.get(i), kulturVarianteMap);
                kulturVarianteMap.put(ids.get(i), new KulturVariante(ids.get(i)));
            }
            break;
        case professionVariante:
            for (int i = 0; i < ids.size(); i++) {
                keyDoppelt(ids.get(i), professionVarianteMap);
                professionVarianteMap.put(ids.get(i), new ProfessionVariante(ids.get(i)));
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
        case sonderregel:
            break; // Gibt es nicht!

        // >>>>>>>>>>>>>>> DEFAULT
        default:
            ProgAdmin.logger.logp(Level.SEVERE, "CharKompAdmin", "initCharKomponents",
                    "Ein CharKomp wurde nicht gefunden: " + charKomp);
        }
    }

    /**
     * Wird aufgerufen, um zu prüfen ob ein ID Wert doppelt vorkommt! In dem Fall wird eine Warnung ausgegeben, aber
     * nicht verhindert das der alte Wert überschrieben wird!
     * 
     * @param key Der Key der überprüft werden soll
     */
    private void keyDoppelt(String key, Map<String, ? extends CharElement> hash) {
        if (hash.containsKey(key)) {
            // Doppelte ID, dadurch wird der alte Wert überschrieben
            ProgAdmin.logger.warning("Bei der Initialisierung ist eine ID doppelt für die HashMap: " + key);
            ProgAdmin.messenger.sendFehler(FactoryFinder.find().getLibrary().getErrorTxt("CharKomponente ID doppelt"));
        }
    }
}
