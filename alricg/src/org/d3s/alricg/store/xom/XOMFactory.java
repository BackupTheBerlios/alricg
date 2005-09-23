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
        // init config
        config = new XOMToConfigMapper().readData();

        // init library
        library = new XOMToLibraryMapper().readData(config.getConfig());

        //init data
        data = new XOMStore();
        new XOMToClientMapper().readData(config.getConfig(), (XOMStore) data);

        initialized = true;
    }
}
