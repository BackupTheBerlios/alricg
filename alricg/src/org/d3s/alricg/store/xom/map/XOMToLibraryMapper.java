/*
 * Created on 23.06.2005 / 15:16:17
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */

package org.d3s.alricg.store.xom.map;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.controller.Messenger;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.store.ConfigStore;
import org.d3s.alricg.store.Configuration;
import org.d3s.alricg.store.ConfigurationException;
import org.d3s.alricg.store.TextStore;
import org.d3s.alricg.store.xom.XOMHelper;
import org.d3s.alricg.store.xom.XOMTextStore;

public class XOMToLibraryMapper {

    public TextStore readData(Configuration props) throws ConfigurationException {

        try {
            final Element configRoot = XOMHelper.getRootElementNoLog(new File(props.getProperty(ConfigStore.Key.config_file)));
            final String d3sLibDir = props.getProperty(ConfigStore.Key.d3s_library_path);
            final String fileName = configRoot.getFirstChildElement("library").getAttribute("file").getValue();
            final Element element = XOMHelper.getRootElementNoLog(new File(d3sLibDir, fileName));
            final String language = configRoot.getFirstChildElement("library").getAttributeValue("lang");
            if (element != null) {

                final Element xmlElement = element.getFirstChildElement("library");
                final List<String> messages = new ArrayList<String>();
                final Map<String, String> s = read(language, xmlElement.getFirstChildElement("short"), messages);
                final Map<String, String> m = read(language, xmlElement.getFirstChildElement("middle"), messages);
                final Map<String, String> l = read(language, xmlElement.getFirstChildElement("long"), messages);
                final Map<String, String> e = read(language, xmlElement.getFirstChildElement("errorMsg"), messages);
                final Map<String, String> t = read(language, xmlElement.getFirstChildElement("toolTip"), messages);

                if (!messages.isEmpty()) {
                    ProgAdmin.logger.warning("Library-Entries f�r die Sprache " + language + " mit den keys '"
                            + messages.toString() + "'" + "konnten nicht gefunden werden.");

                    ProgAdmin.messenger.showMessage(Messenger.Level.fehler,
                            "Es konnten nicht alle Texte geladen werden! \n"
                                    + "Dies ist f�r eine fehlerfreie Anzeige jedoch notwendig. \n"
                                    + "Bitte stellen sie sicher das die 'library' Datei \n"
                                    + "im Originalzustand vorliegt. \n" + "\n"
                                    + "Das Programm wird wahrscheinlich nur fehlerhaft funktionieren.");
                }

                final TextStore library = new XOMTextStore(language, s, m, l, e, t);
                return library;
            } else {

                // Fehler ist aufgetreten, Programm wird geschlossen!
                ProgAdmin.logger.log(Level.SEVERE, "Library Datei (" + d3sLibDir + fileName
                        + ") konnte nicht geladen werden. Programm beendet.");

                ProgAdmin.messenger.showMessage(Messenger.Level.fehler, "Die 'library' Datei \n" + d3sLibDir + fileName
                        + "\nkonnte nicht geladen werden! Bitte �berpr�fen sie ob die Datei \n"
                        + "zugriffsbereit ist und im Orginalzustand vorliegt. \n" + "\n"
                        + "Das Programm kann ohne diese Datei nicht gestartet werden \n"
                        + "und wird wieder geschlossen!");
                System.exit(1);
                return null;
            }
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    private Map<String, String> read(String language, Element xmlElement, List<String> msg) {
        final Map<String, String> result = new HashMap<String, String>();
        final Elements xmlChildren = xmlElement.getChildElements();
        for (int i = 0; i < xmlChildren.size(); i++) {
            boolean entryExists = false;
            final Elements xmlEntrys = xmlChildren.get(i).getChildElements();

            // richtige Sprache suchen
            for (int ii = 0; ii < xmlEntrys.size(); ii++) {
                if (xmlEntrys.get(ii).getAttributeValue("lang").equals(language)) {
                    entryExists = true;
                    result.put(xmlChildren.get(i).getAttributeValue("key"), xmlEntrys.get(ii).getValue());
                    break;
                }
            }

            if (!entryExists) {
                msg.add(xmlElement.getLocalName() + " - " + xmlChildren.get(i).getAttributeValue("key"));
            }
        }
        return result;
    }
}
