package org.d3s.alricg.store.xom;

import org.d3s.alricg.store.ConfigStore;
import org.d3s.alricg.store.ConfigurationException;
import org.d3s.alricg.store.DataStore;
import org.d3s.alricg.store.DataStoreFactory;
import org.d3s.alricg.store.TextStore;
import org.d3s.alricg.store.xom.map.XOMToClientMapper;
import org.d3s.alricg.store.xom.map.XOMToConfigMapper;
import org.d3s.alricg.store.xom.map.XOMToLibraryMapper;

public final class XOMFactory implements DataStoreFactory {

    private TextStore library;

    private ConfigStore config;

    private DataStore data;
    
    private boolean initialized;

    public DataStore getData() {
        return data;
    }

    public ConfigStore getConfiguration() {
        return config;
    }

    public TextStore getLibrary() {
        return library;
    }

    public synchronized void initialize() throws ConfigurationException {
        if (initialized) {
            return;
        }
        {
            // init config
            final XOMToConfigMapper mappy = new XOMToConfigMapper();
            config = mappy.readData();
        }
        {
            // init library
            final XOMToLibraryMapper mappy = new XOMToLibraryMapper();
            library = mappy.readData(config.getConfig());
        }
        {
            // init data
            final XOMToClientMapper mappy = new XOMToClientMapper();
            data = mappy.readData(config.getConfig());
        }
        initialized = true;
    }
}
