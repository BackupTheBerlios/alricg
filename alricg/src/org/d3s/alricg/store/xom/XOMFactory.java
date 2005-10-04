/*
 * Created on 20.06.2005 / 13:14:15
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */

package org.d3s.alricg.store.xom;

import org.d3s.alricg.store.AbstractStoreFactory;
import org.d3s.alricg.store.ConfigStore;
import org.d3s.alricg.store.ConfigurationException;
import org.d3s.alricg.store.DataStore;
import org.d3s.alricg.store.TextStore;
import org.d3s.alricg.store.xom.map.XOMToConfigMapper;
import org.d3s.alricg.store.xom.map.XOMToDataMapper;
import org.d3s.alricg.store.xom.map.XOMToLibraryMapper;

/**
 * <code>AbstractStoreFactory</code> auf Basis des xom-Frameworks.
 * 
 * @author <a href="mailto:msturzen@mac.com">St. Martin</a>
 */
public final class XOMFactory implements AbstractStoreFactory {

    /** Lokalisierte Texte. */
    private TextStore library;

    /** alricg-Konfiguration. */
    private ConfigStore config;

    /** Alle Regeln zur Charaktererschaffung. */
    private DataStore data;

    /** Initialisierungsstatus der Factory. */
    private boolean initialized;

    // @see org.d3s.alricg.store.AbstractStoreFactory#getData()
    public DataStore getData() {
        return data;
    }

    // @see org.d3s.alricg.store.AbstractStoreFactory#getConfiguration()
    public ConfigStore getConfiguration() {
        return config;
    }

    // @see org.d3s.alricg.store.AbstractStoreFactory#getLibrary()
    public TextStore getLibrary() {
        return library;
    }

    // @see org.d3s.alricg.store.AbstractStoreFactory#initialize()
    public synchronized void initialize() throws ConfigurationException {
        if (initialized) {
            return;
        }
        // init config
        config = new XOMToConfigMapper().readData();

        // init library
        library = new XOMToLibraryMapper().readData(config.getConfig());

        // init data
        data = new XOMStore();
        new XOMToDataMapper().readData(config.getConfig(), (XOMStore) data);

        initialized = true;
    }
}
