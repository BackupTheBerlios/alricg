/*
 * Created 22. September 2005 / 00:01:02
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.store.xom.map;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nu.xom.Document;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.store.Configuration;
import org.d3s.alricg.store.ConfigurationException;
import org.d3s.alricg.store.xom.XOMStore;

/**
 * Mapper für die <code>CharElemente</code>.
 * 
 * @author <a href="mailto:msturzen@mac.com">St. Martin</a>
 */
public class DataToXOMMapper {

    /**
     * Speichert einen <code>dataStore</code> auf Basis der Einstellungen in <code>props</code>.
     * 
     * @param props Die Konfiguration mit den benötigten Einstellungen.
     * @param dataStore Der zu schriebende Datenspeicher.
     * @throws ConfigurationException Falls die Konfiguration fehlerhaft ist.
     */
    public void storeData(Configuration props, XOMStore dataStore) throws ConfigurationException {
        try {

            // Menge von files, die geschrieben werden sollen, holen.
            final Set<String> fileNames = dataStore.getOrigin().keySet();
            for (Iterator<String> i = fileNames.iterator(); i.hasNext();) {
                final String fileName = i.next();
                
                // Die Liste mit ids holen, die in das File geschrieben werden sollen.
                final List<String> ids = dataStore.getOrigin().get(fileName);

                // TODO Die Liste mit den ids in die richtige, d.h. zum Schema passende Reiehenfolge bringen.
                // TODO xml header etc. schreiben
                final Element root = new Element("alricgXML");

                // Die CharElemente zu den ids holen und über ihre XOMMapper persistieren.
                for (Iterator<String> ii = ids.iterator(); ii.hasNext();) {
                    final String id = ii.next();
                    final CharKomponente charKomp = dataStore.getCharKompFromId(id);
                    final CharElement charElement = dataStore.getCharElement(id, charKomp);
                    final XOMMapper mappy = XOMMappingHelper.instance().chooseXOMMapper(charKomp);
                    final Element xmlElement = new Element("");
                    mappy.map(charElement, xmlElement);
                    root.appendChild(xmlElement);
                }

                // xom ins file schreiben
                File output = new File(fileName);
                if (output.exists()) {
                    output.renameTo(new File(fileName + ".old")); //REVISIT [msturzen] Sollte delete sein. Nach erfolgreichem test!
                    output = new File(fileName);
                }
                final Writer writer = new BufferedWriter(new FileWriter(output));
                writer.write(new Document(root).toXML());
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }
}
