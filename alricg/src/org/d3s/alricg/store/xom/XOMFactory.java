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
        
        /*
         * In zwei Methoden ausgelagert, um das Programm starten zu können
        {
        	// init data
            final XOMToClientMapper mappy = new XOMToClientMapper();
            data = mappy.initData(config.getConfig());
        }
        */
        
        initialized = true;
    }
    
    /**
     * Initialisiert die Daten (=CharElemente). Jedes CharElement wird mit einer 
     * ID erzeugt, aber nicht mit Daten gefüllt. 
     * @throws ConfigurationException
     */
    public synchronized void initializeData() throws ConfigurationException {
    	// init data
        final XOMToClientMapper mappy = new XOMToClientMapper();
        data = mappy.initData(config.getConfig());
    }
    
    /**
     * Liest die Daten (=CharElemente) ein, dabei werden alle bereits vorhandenen CharElemente
     * mit Inhalt gefüllt.
     * @throws ConfigurationException
     * @see initializeData()
     */
    public synchronized void readData() throws ConfigurationException {
        // read data
        final XOMToClientMapper mappy = new XOMToClientMapper();
        data = mappy.readData(config.getConfig(), (XOMStore) data);
    }
}
