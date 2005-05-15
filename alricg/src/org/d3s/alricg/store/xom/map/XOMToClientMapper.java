package org.d3s.alricg.store.xom.map;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Eigenschaft;
import org.d3s.alricg.charKomponenten.EigenschaftEnum;
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
import org.d3s.alricg.store.Configuration;
import org.d3s.alricg.store.ConfigurationException;
import org.d3s.alricg.store.FactoryFinder;
import org.d3s.alricg.store.TextStore;
import org.d3s.alricg.store.xom.XOMHelper;
import org.d3s.alricg.store.xom.XOMStore;

public class XOMToClientMapper {

    public XOMStore readData(Configuration props) throws ConfigurationException {
        final XOMStore dataStore = new XOMStore();
        final Element configRoot = XOMHelper.getRootElement(new File(props.getProperty("config.file")));
        final TextStore lib = FactoryFinder.find().getLibrary();

        ProgAdmin.messenger.sendInfo(lib.getLongTxt("Regel-Dateien werden geladen"));
        final List<File> arrayFiles = getXmlFiles(props, configRoot);
        ProgAdmin.logger.info("Regel-Files aus Config geladen...");

        initCharKomponents(arrayFiles, dataStore);
        ProgAdmin.logger.info("Charakter-Elemente erstellt...");

        loadCharKomponents(arrayFiles, dataStore, false);
        ProgAdmin.logger.info("Charakter-Elemente initiiert...");

        return dataStore;
    }

    /**
     * Liest alle Files ein und erzeugt die CharElemente (nur mit ID)
     * 
     * @param arrayFiles Eine ArrayList mit allen Dateien die Eingelesen werden
     */
    private void initCharKomponents(List<File> arrayFiles, XOMStore charKompAdmin) {

        loadCharKomponents(arrayFiles, charKompAdmin, true);

        // Eigenschaften hinzufügen (initialisierung erstellt diese komplett)
        charKompAdmin.initCharKomponents(EigenschaftEnum.getIdArray(), CharKomponente.eigenschaft);
    }

    /**
     * Alle "leeren" CharKomponenten durchgehen und die Komponenten füllen!
     */
    private void loadCharKomponents(List<File> arrayFiles, XOMStore charKompAdmin, boolean init) {
        final CharKomponente[] charKomps = CharKomponente.values();

        // Files auslesen, hier kann eigentlich kein Lade Fehler auftreten,
        // da alle File bereits durch "loadRegleXML" geladen waren!
        final Element[] rootElements = new Element[arrayFiles.size()];
        for (int i = 0; i < rootElements.length; i++) {
            rootElements[i] = XOMHelper.getRootElement(arrayFiles.get(i));
        }

        // Erzeuge alle CharElement-Objekte

        for (int i = 0; i < rootElements.length; i++) { // Für alle Files...

            for (int ii = 0; ii < charKomps.length; ii++) { // Alle CharKomps...
                final CharKomponente current = charKomps[ii];
                final Element firstChild = rootElements[i].getFirstChildElement(current.getKategorie());
                if (firstChild == null) {
                    continue; // ... wenn nicht, überspringen
                }

                // Alle Elemente zu dem CharKomp auslesen
                final Elements kategorien = firstChild.getChildElements();
                updateCharKomponents(kategorien, current, init);
            }
        }
    }

    /**
     * Ließt alle XML-Files ein, die laut Config eingelesen werden sollen. Ließt auch die als im "benoetigtDateien" Tag
     * angegeben Dateinen ein.
     * 
     * @param configRoot Das Config-Element um den Pfad zur Datei auszulesen
     * @return Eine ArrayList mir allen Files die eingelesen werden sollen (inklusive dem "benoetigtDateien"-Tag
     */
    private List<File> getXmlFiles(Configuration props, Element configRoot) {
        final File d3sDataDir = new File(props.getProperty("d3s.data.path"));
        final File d3sUserDir = new File(props.getProperty("user.data.path"));
        final List<File> arrayFiles = new ArrayList<File>();

        // Lese die Pfade zu den XML-Files mit Komponten ein
        Elements tmpElementList = configRoot.getFirstChildElement("xmlRuleFilesD3S").getChildElements();

        // Alle Orginal-Files einlesen und als File speichern
        for (int i = 0; i < tmpElementList.size(); i++) {
            loadRegelFile(props, arrayFiles, new File(d3sDataDir, (String) tmpElementList.get(i).getValue()));
        }

        // Prüfen ob die Userdaten überhaupt eingelesen werden sollen
        if (configRoot.getFirstChildElement("xmlRuleFilesUser").getAttributeValue("readUserFiles").equals("true")) {
            tmpElementList = configRoot.getFirstChildElement("xmlRuleFilesUser").getChildElements();

            // Alle User-Files einlesen und als File speichern
            for (int i = 0; i < tmpElementList.size(); i++) {
                loadRegelFile(props, arrayFiles, new File(d3sUserDir, (String) tmpElementList.get(i).getValue()));
            }
        }

        return arrayFiles;
    }

    /**
     * Methode für Rekurisven Aufruf, fügt das übergebene file und alle im "benötigteDateien"-Tag enthaltenen Files zum
     * Array hinzu, wenn sie im Array noch nicht vorhanden sind. (Nötig für einladen der Regel Files)
     * 
     * @param arrayFiles Array mit allen Parameter - IN/OUT Parameter
     * @param file Das File das ggf. hinzugefügt werden soll mit "benötigtenDateien".
     */
    private void loadRegelFile(Configuration props, List<File> arrayFiles, File file) {
        final File d3sDataDir = new File(props.getProperty("d3s.data.path"));
        final File d3sUserDir = new File(props.getProperty("user.data.path"));

        // Wenn das File schon enthalten ist --> return
        if (arrayFiles.contains(file))
            return;

        Element tempElement = XOMHelper.getRootElement(file); // File als XML laden
        if (tempElement == null)
            return; // Konnte nicht geladen werden --> return

        arrayFiles.add(file); // ansonsten File hinzufügen

        tempElement = tempElement.getFirstChildElement("preamble").getFirstChildElement("benoetigtDateien");

        // Falls das File keine weiteren Dateien benötigt --> Return
        if (tempElement == null)
            return;

        Elements elementList = tempElement.getChildElements("datei");

        // Rekursiver Aufruf mit allen von diesem File benötigten Dateien
        for (int i = 0; i < elementList.size(); i++) {
            if (elementList.get(i).getAttribute("ordner").getValue().equals("d3s")) {
                loadRegelFile(props, arrayFiles, new File(d3sDataDir, (String) elementList.get(i).getValue()));
            } else { // value = user
                loadRegelFile(props, arrayFiles, new File(d3sUserDir, (String) elementList.get(i).getValue()));
            }
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
            ProgAdmin.messenger.sendFehler(ProgAdmin.library.getErrorTxt("CharKomponente ID doppelt"));
        }
    }

    /**
     * Initialisiert die übergebene CharKomponent und erstellt zu jeder ID das Objekt. Es wird ein sortiertes Array
     * erstellt, wobei die Objekte nur die ID zugewiesen bekommen haben.
     * 
     * @param ids Eine Liste mit allen IDs der Objekte die angelegt werden sollen
     * @param CharKomp Der Typ der CharKomponente
     */
    private final void updateCharKomponents(Elements kategorien, CharKomponente charKomp, boolean init) {
        XOMMapper mappy;
        switch (charKomp) {
        // >>>>>>>>>>>>>>> Herkunft
        case rasse:
            Map<String, CharElement> rasseMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, rasseMap);
                    rasseMap.put(id, new Rasse(id));
                }
            } else {
                mappy = new XOMMapper_Rasse();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = rasseMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case kultur:
            Map<String, CharElement> kulturMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, kulturMap);
                    kulturMap.put(id, new Kultur(id));
                }
            } else {
                mappy = new XOMMapper_Kultur();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = kulturMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case profession:
            Map<String, CharElement> professionMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, professionMap);
                    professionMap.put(id, new Profession(id));
                }
            } else {
                mappy = new XOMMapper_Profession();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = professionMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case zusatzProfession:
            Map<String, CharElement> zusatzProfMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, zusatzProfMap);
                    zusatzProfMap.put(id, new ZusatzProfession(id));
                }
            } else {
                mappy = new XOMMapper_ZusatzProfession();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = zusatzProfMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;

        // >>>>>>>>>>>>>>> Fertigkeiten & Fähigkeiten
        case vorteil:
            Map<String, CharElement> vorteilMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, vorteilMap);
                    vorteilMap.put(id, new Vorteil(id));
                }
            } else {
                mappy = new XOMMapper_Vorteil();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = vorteilMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case gabe:
            Map<String, CharElement> gabeMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, gabeMap);
                    gabeMap.put(id, new Gabe(id));
                }
            } else {
                mappy = new XOMMapper_Gabe();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = gabeMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case nachteil:
            Map<String, CharElement> nachteilMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, nachteilMap);
                    nachteilMap.put(id, new Nachteil(id));
                }
            } else {
                mappy = new XOMMapper_Nachteil();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = nachteilMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case sonderfertigkeit:
            Map<String, CharElement> sonderfMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, sonderfMap);
                    sonderfMap.put(id, new Sonderfertigkeit(id));
                }
            } else {
                mappy = new XOMMapper_Sonderfertigkeit();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = sonderfMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case ritLitKenntnis:
            Map<String, CharElement> ritLitKentMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, ritLitKentMap);
                    ritLitKentMap.put(id, new LiturgieRitualKenntnis(id));
                }
            } else {
                mappy = new XOMMapper_LiturgieRitualKenntnis();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = ritLitKentMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case talent:
            Map<String, CharElement> talentMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, talentMap);
                    talentMap.put(id, new Talent(id));
                }
            } else {
                mappy = new XOMMapper_Talent();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = talentMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case zauber:
            Map<String, CharElement> zauberMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, zauberMap);
                    zauberMap.put(id, new Zauber(id));
                }
            } else {
                mappy = new XOMMapper_Zauber();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = zauberMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;

        // >>>>>>>>>>>>>>> Sprachen
        case sprache:
            Map<String, CharElement> spracheMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, spracheMap);
                    spracheMap.put(id, new Sprache(id));
                }
            } else {
                mappy = new XOMMapper_Sprache();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = spracheMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case schrift:
            Map<String, CharElement> schriftMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, schriftMap);
                    schriftMap.put(id, new Schrift(id));
                }
            } else {
                mappy = new XOMMapper_Schrift();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = schriftMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;

        // >>>>>>>>>>>>>>> Götter
        case liturgie:
            Map<String, CharElement> liturgieMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, liturgieMap);
                    liturgieMap.put(id, new Liturgie(id));
                }
            } else {
                mappy = new XOMMapper_Liturgie();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = liturgieMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case ritual:
            Map<String, CharElement> ritualMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, ritualMap);
                    ritualMap.put(id, new Ritual(id));
                }
            } else {
                mappy = new XOMMapper_Ritual();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = ritualMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;

        // >>>>>>>>>>>>>>> Ausrüstung
        case ausruestung:
            Map<String, CharElement> ausruestungMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, ausruestungMap);
                    ausruestungMap.put(id, new Ausruestung(id));
                }
            } else {
                mappy = new XOMMapper_Ausruestung();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = ausruestungMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case fahrzeug:
            Map<String, CharElement> fahrzeugMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, fahrzeugMap);
                    fahrzeugMap.put(id, new Fahrzeug(id));
                }
            } else {
                mappy = new XOMMapper_Fahrzeug();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = fahrzeugMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case waffeNk:
            Map<String, CharElement> waffeNkMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, waffeNkMap);
                    waffeNkMap.put(id, new NahkWaffe(id));
                }
            } else {
                mappy = new XOMMapper_NahkWaffe();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = waffeNkMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case waffeFk:
            Map<String, CharElement> waffeFkMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, waffeFkMap);
                    waffeFkMap.put(id, new FkWaffe(id));
                }
            } else {
                mappy = new XOMMapper_FkWaffe();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = waffeFkMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case ruestung:
            Map<String, CharElement> ruestungMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, ruestungMap);
                    ruestungMap.put(id, new Ruestung(id));
                }
            } else {
                mappy = new XOMMapper_Ruestung();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = ruestungMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case schild:
            Map<String, CharElement> schildMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, schildMap);
                    schildMap.put(id, new Schild(id));
                }
            } else {
                mappy = new XOMMapper_Schild();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = schildMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;

        // >>>>>>>>>>>>>>> Zusätzliches
        case daemonenPakt:
            Map<String, CharElement> daemonenPaktMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, daemonenPaktMap);
                    daemonenPaktMap.put(id, new DaemonenPakt(id));
                }
            } else {
                mappy = new XOMMapper_DaemonenPakt();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = daemonenPaktMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case schwarzeGabe:
            Map<String, CharElement> schwarzeGabeMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, schwarzeGabeMap);
                    schwarzeGabeMap.put(id, new SchwarzeGabe(id));
                }
            } else {
                mappy = new XOMMapper_SchwarzeGabe();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = schwarzeGabeMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case tier:
            Map<String, CharElement> tierMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, tierMap);
                    tierMap.put(id, new Tier(id));
                }
            } else {
                mappy = new XOMMapper_Tier();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = tierMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case region:
            Map<String, CharElement> regionMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, regionMap);
                    regionMap.put(id, new RegionVolk(id));
                }
            } else {
                mappy = new XOMMapper_RegionVolk();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = regionMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case gottheit:
            Map<String, CharElement> gottheitMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, gottheitMap);
                    gottheitMap.put(id, new Gottheit(id));
                }
            } else {
                mappy = new XOMMapper_Gottheit();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = gottheitMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case repraesentation:
            Map<String, CharElement> repraesentMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, repraesentMap);
                    repraesentMap.put(id, new Repraesentation(id));
                }
            } else {
                mappy = new XOMMapper_Repraesentation();
                for (int i = 0; i < kategorien.size(); i++) {
                    final Element child = kategorien.get(i);
                    final String id = child.getAttributeValue("id");
                    final CharElement charEl = repraesentMap.get(id);
                    mappy.map(child, charEl);
                }
            }
            break;
        case eigenschaft:
            Map<String, CharElement> eigenschaftMap;
            if (init) {
                for (int i = 0; i < kategorien.size(); i++) {
                    final String id = kategorien.get(i).getAttributeValue("id");
                    keyDoppelt(id, eigenschaftMap);
                    eigenschaftMap.put(id, new Eigenschaft(id));
                }
            } else {
                // Eigenschaften werden nicht aus XML gelesen.
                // mappy = new XOMMapper_Eigenschaft();
                // for (int i = 0; i < kategorien.size(); i++) {
                // final Element child = kategorien.get(i);
                // final String id = child.getAttributeValue("id");
                // final CharElement charEl = eigenschaftMap.get(id);
                // mappy.map(child, charEl);
                // }
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
}
