/*
 * Created on 15.06.2005 / 12:13:14
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */

package org.d3s.alricg.store;

import java.util.Map;

import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;

/**
 * Abstraktion der Konfiguration von alricg.
 * 
 * @author <a href="mailto:msturzen@mac.com">St. Martin</a>
 */
public interface ConfigStore {

    /** Enumeration der gültigen keys */
    public enum Key {
        config_file("config.file"), d3s_schema_path("d3s.schema.path"), d3s_library_path("d3s.library.path"), d3s_img_path(
                "d3s.img.path"), d3s_data_path("d3s.data.path"), user_data_path("user.data.path");

        private final String w; // wert des Elements

        /** Erzeugt eine neue Instanz mit dem übergebenen Wert */
        private Key(String wert) {
            w = wert;
        }

        // @see java.lang.Object#toString()
        public String toString() {
            return w;
        }
    };

    /**
     * Enthält Konfigurationsdaten für alricg. Insbesondere Konfigurationen allgemeiner Art wie z.B. Pfadangaben von
     * Resourcen etc.
     * 
     * @see ConfigStore.Key
     * @return die gültige alricg-Konfiguration.
     */
    Configuration getConfig();

    /**
     * Gibt die SKT (Steigerungskostentabelle) zurück.
     * <p>
     * Für die map gilt:
     * <ul>
     * <li>key = Gewünschte Kostenklasse</li>
     * <li>value = (Integer[0] - Kosten für Stufen 1) bis (Integer[29] - Kosten für Stufen 30)</li>
     * 
     * @return Die aktuelle SKT.
     * @throws ConfigurationException Falls keine SKT zurückgeliefert werden kann
     */
    Map<KostenKlasse, Integer[]> getSkt() throws ConfigurationException;
}
