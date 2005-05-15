package org.d3s.alricg.store.xom;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.store.DataStore;

public class XOMStore implements DataStore {

    // Herkunft
    private final Map<String, Rasse> rasseMap = new HashMap<String, Rasse>();

    private final Map<String, Kultur> kulturMap = new HashMap<String, Kultur>();

    private final Map<String, Profession> professionMap = new HashMap<String, Profession>();

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

            if (charKompArray[i] == CharKomponente.eigenschaft || charKompArray[i] == CharKomponente.sonderregel) {
                continue; // Eigenschaften werden nicht geschrieben, daher Abbruch
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
     * Ermöglicht den Zugriff auf die HashMap mit den charKomponenten.
     * 
     * @param charKomp Die CharKomponente zu der die HashMap zurückgegeben werden soll
     * @return HashMap mit allen Elementen zu dieser CharKomponente
     */
    private Map<String, ? extends CharElement> getMap(CharKomponente charKomp) {

        switch (charKomp) {
        // >>>>>>>>>>>>>>> Herkunft
        case rasse:
            return rasseMap;
        case kultur:
            return kulturMap;
        case profession:
            return professionMap;
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
}
