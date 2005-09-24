/*
 * Created on 15.06.2005 / 12:13:14
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */

package org.d3s.alricg.store;

/**
 * Die <code>DataStoreFactory</code> kapselt den Zugriff auf die diversen, von alricg benötigten persistenten Daten.
 * <p>
 * Es gibt 3 Klassen von Stores in alricg:
 * <ul>
 * <li>DataStore: Regeln.</li>
 * <li>ConfigStore: Konfiguration, v.a. für interne Daten.</li>
 * <li>TextStore: lokalisierbare Texte.</li>
 * </p>
 * 
 * @author <a href="mailto:msturzen@mac.com>St. Martin</a>
 */
public interface DataStoreFactory {

    /**
     * Initialisiert die <code>DataStoreFactory</code>.
     * 
     * @throws ConfigurationException Falls ein Fehler während der Initialisierung auftritt.
     */
    void initialize() throws ConfigurationException;

    /**
     * Gibt den <code>DataStore</code> zurück.
     * 
     * @return Der zur Factory gehörige <code>DataStore</code>.
     */
    DataStore getData();

    /**
     * Gibt den <code>ConfigStore</code> zurück.
     * 
     * @return Der zur Factory gehörige <code>ConfigStore</code>.
     */
    ConfigStore getConfiguration();

    /**
     * Gibt den <code>TextStore</code> zurück.
     * 
     * @return Der zur Factory gehörige <code>TextStore</code>.
     */
    TextStore getLibrary();
}
