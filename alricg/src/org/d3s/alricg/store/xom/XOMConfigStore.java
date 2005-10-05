/*
 * Created on 20.06.2005 / 13:14:15
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */

package org.d3s.alricg.store.xom;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;
import org.d3s.alricg.store.ConfigStore;
import org.d3s.alricg.store.Configuration;
import org.d3s.alricg.store.ConfigurationException;
import org.d3s.alricg.store.xom.map.XOMToConfigMapper;

/**
 * <code>ConfigStore</code> auf Basis des xom-Frameworks
 * 
 * @author <a href="mailto:msturzen@mac.com">St. Martin</a>
 */
public class XOMConfigStore implements ConfigStore {

    /** Key in der Konfiguration für die Steigerungskostentabelle */
    public static final String SKT_KEY = "alricg.internal.skt";

    /** Speichert die konfigurationsrelevanten Einstellungen */
    private final Properties props;

    /**
     * Erzeugt einen neuen <code>XOMConfigStore</code>
     */
    XOMConfigStore() {
        this.props = new Properties();
    }

    // @see org.d3s.alricg.store.ConfigStore#getConfig()
    public Configuration getConfig() {
        return new Configuration(props);
    }

    // @see org.d3s.alricg.store.ConfigStore#getSkt()
    public Map<KostenKlasse, Integer[]> getSkt() throws ConfigurationException {
        return (HashMap<KostenKlasse, Integer[]>) props.get(SKT_KEY);
    }

    void readData() throws ConfigurationException {
        Properties newProps = new XOMToConfigMapper().readData();
        props.putAll(newProps);
    }
}
