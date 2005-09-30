/*
 * Created 22. September 2005 / 00:01:02
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.store.xom.map;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.EigenschaftEnum;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.store.ConfigStore;
import org.d3s.alricg.store.Configuration;
import org.d3s.alricg.store.ConfigurationException;
import org.d3s.alricg.store.FactoryFinder;
import org.d3s.alricg.store.KeyExistsException;
import org.d3s.alricg.store.TextStore;
import org.d3s.alricg.store.xom.XOMHelper;
import org.d3s.alricg.store.xom.XOMStore;

/**
 * Mapper für die <code>CharElemente</code>.
 * 
 * @author <a href="mailto:msturzen@mac.com>St. Martin</a>
 */
public class XOMToClientMapper {

    /**
     * Befüllt einen neuen <code>dataStore</code> auf Basis der Einstellungen in <code>props</code>.
     * 
     * @param props Die Konfiguration mit den benötigten Einstellungen.
     * @param dataStore Der zu befüllende Datenspeicher.
     * @throws ConfigurationException Falls die Konfiguration fehlerhaft ist.
     */
    public void readData(Configuration props, XOMStore dataStore) throws ConfigurationException {
        try {
            final Element configRoot = XOMHelper
                    .getRootElement(new File(props.getProperty(ConfigStore.Key.config_file)));
            final TextStore lib = FactoryFinder.find().getLibrary();

            ProgAdmin.messenger.sendInfo(lib.getLongTxt("Regel-Dateien werden geladen"));
            final List<File> files = getXmlFiles(props, configRoot);
            ProgAdmin.logger.info("Regel-Files aus Config geladen...");

            initCharKomponents(files, dataStore);
            ProgAdmin.logger.info("Charakter-Elemente erstellt...");

            loadCharKomponents(files, dataStore, false);
            ProgAdmin.logger.info("Charakter-Elemente initiiert...");

        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    /**
     * Wählt den korrekten <code>XOMMapper</code> zu einer <code>CharKomponente</code>.
     * 
     * @param charKomp Die Komponente wozu ein passender <code>XOMMapper</code> gesucht wird.
     * @return Der passende Mapper, oder <code>null</code>, falls kein passender gefunden werden konnte.
     */
    private XOMMapper chooseXOMMapper(CharKomponente charKomp) {
        XOMMapper mappy = null;
        switch (charKomp) {
        // >>>>>>>>>>>>>>> Herkunft
        case rasse:
            mappy = new XOMMapper_Rasse();
            break;
        case kultur:
            mappy = new XOMMapper_Kultur();
            break;
        case profession:
            mappy = new XOMMapper_Profession();
            break;
        case zusatzProfession:
            mappy = new XOMMapper_ZusatzProfession();
            break;

        // >>>>>>>>>>>>>>> Fertigkeiten & Fähigkeiten
        case vorteil:
            mappy = new XOMMapper_Vorteil();
            break;
        case gabe:
            mappy = new XOMMapper_Gabe();
            break;
        case nachteil:
            mappy = new XOMMapper_Nachteil();
            break;
        case sonderfertigkeit:
            mappy = new XOMMapper_Sonderfertigkeit();
            break;
        case ritLitKenntnis:
            mappy = new XOMMapper_LiturgieRitualKenntnis();
            break;
        case talent:
            mappy = new XOMMapper_Talent();
            break;
        case zauber:
            mappy = new XOMMapper_Zauber();
            break;

        // >>>>>>>>>>>>>>> Sprachen
        case sprache:
            mappy = new XOMMapper_Sprache();
            break;
        case schrift:
            mappy = new XOMMapper_Schrift();
            break;

        // >>>>>>>>>>>>>>> Götter
        case liturgie:
            mappy = new XOMMapper_Liturgie();
            break;
        case ritual:
            mappy = new XOMMapper_Ritual();
            break;

        // >>>>>>>>>>>>>>> Ausrüstung
        case ausruestung:
            mappy = new XOMMapper_Ausruestung();
            break;
        case fahrzeug:
            mappy = new XOMMapper_Fahrzeug();
            break;
        case waffeNk:
            mappy = new XOMMapper_NahkWaffe();
            break;
        case waffeFk:
            mappy = new XOMMapper_FkWaffe();
            break;
        case ruestung:
            mappy = new XOMMapper_Ruestung();
            break;
        case schild:
            mappy = new XOMMapper_Schild();
            break;

        // >>>>>>>>>>>>>>> Zusätzliches
        case daemonenPakt:
            mappy = new XOMMapper_DaemonenPakt();
            break;
        case schwarzeGabe:
            mappy = new XOMMapper_SchwarzeGabe();
            break;
        case tier:
            mappy = new XOMMapper_Tier();
            break;
        case region:
            mappy = new XOMMapper_RegionVolk();
            break;
        case gottheit:
            mappy = new XOMMapper_Gottheit();
            break;
        case repraesentation:
            mappy = new XOMMapper_Repraesentation();
            break;
        case eigenschaft:
            mappy = null;
            // Eigenschaften werden nicht aus XML gelesen.
            // mappy = new XOMMapper_Eigenschaft();
            // for (int i = 0; i < kategorien.size(); i++) {
            // final Element child = kategorien.get(i);
            // final String id = child.getAttributeValue("id");
            // final CharElement charEl = eigenschaftMap.get(id);
            // mappy.map(child, charEl);
            // }
            break;
        case sonderregel:
            mappy = null;
            break; // Gibt es nicht!

        // >>>>>>>>>>>>>>> DEFAULT
        default:
            mappy = null;
            ProgAdmin.logger.logp(Level.SEVERE, "CharKompAdmin", "initCharKomponents",
                    "Ein CharKomp wurde nicht gefunden: " + charKomp);
        }
        return mappy;
    }

    /**
     * Liest alle xml-Files ein, die laut Konfiguration und "benoetigtDateien"-Tag eingelesen werden sollen.
     * 
     * @param configRoot Das Config-Element um den Pfad zur Datei auszulesen
     * @return Eine ArrayList mir allen Files die eingelesen werden sollen (inklusive dem "benoetigtDateien"-Tag
     */
    private List<File> getXmlFiles(Configuration props, Element configRoot) {
        final File d3sDataDir = new File(props.getProperty(ConfigStore.Key.d3s_data_path));
        final File d3sUserDir = new File(props.getProperty(ConfigStore.Key.user_data_path));
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
     * Initialisiert die CharElemente nur mit ID.
     * 
     * @param arrayFiles Eine ArrayList mit allen Dateien die Eingelesen werden
     */
    private void initCharKomponents(List<File> arrayFiles, XOMStore charKompAdmin) throws KeyExistsException {

        loadCharKomponents(arrayFiles, charKompAdmin, true);

        List<String> ids = EigenschaftEnum.getIdArray();

        charKompAdmin.initCharKomponents(ids, CharKomponente.eigenschaft);
    }

    /**
     * Initialisiert die übergebenen Elemente, indem die Elemente erzeugt werden und die ID gesetzt wird.
     * 
     * @param kategorien Alle XML-Elemente der Art "current"
     * @param current Die Art der übergebenen Elemente
     * @param store Store in den die Elemente gespeichert werden sollen
     * @author V.Strelow
     */
    private void initHelpCharKomponents(Elements kategorien, CharKomponente current, XOMStore store)
            throws KeyExistsException {
        final CharKomponente currentVariante;

        final List<String> ids = new ArrayList<String>();
        for (int iii = 0; iii < kategorien.size(); iii++) {
            ids.add(kategorien.get(iii).getAttributeValue("id"));
        }
        store.initCharKomponents(ids, current);

        // Einlesen der Varianten
        if (current.equals(CharKomponente.rasse)) {
            currentVariante = CharKomponente.rasseVariante;
        } else if (current.equals(CharKomponente.kultur)) {
            currentVariante = CharKomponente.kulturVariante;
        } else if (current.equals(CharKomponente.profession)) {
            currentVariante = CharKomponente.professionVariante;
        } else {
            return; // Keine Varianten, daher return
        }

        // Alle Elemente durchgehen, um Varianten auszulsen
        for (int iii = 0; iii < kategorien.size(); iii++) {
            Element tmpElement = kategorien.get(iii).getFirstChildElement("varianten");

            if (tmpElement != null) {
                initHelpCharKomponents( // rekursiver aufruf
                        tmpElement.getChildElements("variante"), currentVariante, store);
            }
        }

    }

    /**
     * Befüllt die "leeren" CharKomponenten mit Daten.
     */
    private void loadCharKomponents(List<File> files, XOMStore store, boolean init) throws KeyExistsException {
        final CharKomponente[] charKomps = CharKomponente.values();

        // Files auslesen, hier kann eigentlich kein Lade Fehler auftreten,
        // da alle File bereits durch "loadRegleXML" geladen waren!
        final Element[] rootElements = new Element[files.size()];
        for (int i = 0; i < rootElements.length; i++) {
            rootElements[i] = XOMHelper.getRootElement(files.get(i));
        }

        // Erzeuge alle CharElement-Objekte
        for (int i = 0; i < charKomps.length; i++) { // Alle CharKomps...
            final CharKomponente current = charKomps[i];
            for (int ii = 0; ii < rootElements.length; ii++) { // Für alle Files...
                final Element firstChild = rootElements[ii].getFirstChildElement(current.getKategorie());

                if (firstChild == null) {
                    continue; // ... wenn kein Element, überspringen
                }

                // Alle Elemente zu dem CharKomp auslesen
                final Elements kategorien = firstChild.getChildElements();

                // Entscheiden ob initialisierung der Elemente oder mapping der Daten
                if (init) {
                    initHelpCharKomponents(kategorien, current, store);
                } else {
                    mapHelpCharKomponents(kategorien, current, store);
                }
            }
        }
    }

    /**
     * Fügt die übergebene Datei und alle im "benötigteDateien"-Tag enthaltenen Dateien zur Liste hinzu, sofern sie noch
     * nicht vorhanden sind. Die Methode lädt rekursiv alle benötigten Datein nach.
     * 
     * @param arrayFiles Array mit allen Parameter - IN/OUT Parameter
     * @param file Das File das ggf. hinzugefügt werden soll mit "benötigtenDateien".
     */
    private void loadRegelFile(Configuration props, List<File> arrayFiles, File file) {
        final File d3sDataDir = new File(props.getProperty(ConfigStore.Key.d3s_data_path));
        final File d3sUserDir = new File(props.getProperty(ConfigStore.Key.user_data_path));

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
     * Bildet die übergebenen xml-Elemente in <code>CharElemente</code> ab.
     * 
     * @param kategorien Alle xml-Elemente der Art "current"
     * @param current Die Art der übergebenen Elemente
     * @param store Store in den die Elemente gespeichert werden sollen
     */
    private void mapHelpCharKomponents(Elements kategorien, CharKomponente current, XOMStore store) {
        final XOMMapper mappy = chooseXOMMapper(current);
        if (mappy != null) {
            for (int iii = 0; iii < kategorien.size(); iii++) {
                final Element child = kategorien.get(iii);
                final String id = child.getAttributeValue("id");
                final CharElement charEl = store.getCharElement(id, current);
                mappy.map(child, charEl);
            }
        }
    }
}
