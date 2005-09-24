/*
 * Created on 15.06.2005 / 12:13:14
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */

package org.d3s.alricg.store;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.d3s.alricg.controller.ProgAdmin;

/**
 * Eine Factory zur Auswahl der konkreten <code>DataStoreFactory</code>.
 * <p>
 * Der <code>FactoryFinder</code> wählt anhand einer Konfigurationsdatei aus, welche konkrete
 * <code>DataStoreFactory</code> verwendet werden soll. <br>
 * Verwendung:
 * 
 * <pre>
 * DataStoreFactory factory = FactoryFinder.init(); // initialisiert den FactoryFinder und gibt die konkrete Factory zurück.
 * 
 * //... do something ...
 * 
 * DataStoreFactory factorz = FactoryFinder.find(); // gibt die konkrete Factory zurück.
 * </pre>
 * 
 * Die <code>init</code>-Methode muss ausgeführt werden, bevor die <code>find</code>-Methode zum ersten Mal
 * aufgerufen wird.
 * </p>
 * 
 * @author <a href="mailto:msturzen@mac.com>St. Martin</a>
 */
public final class FactoryFinder {

    /**
     * Die zu verwendende <code>DataStoreFactory</code>.
     */
    private static DataStoreFactory factoryInstance;

    /**
     * Gibt die Factory zurück.
     * 
     * @return Die zu verwendende <code>DataStoreFactory</code>.
     * @throws NullPointerException Falls <code>factoryInstance==null</code> ist.
     */
    public static final DataStoreFactory find() {
        if (factoryInstance != null) {
            return factoryInstance;
        }
        throw new NullPointerException("DataStoreFactory is not initialised!");
    }

    /**
     * Initialisiert die zu verwendende <code>DataStoreFactory</code>, sofern das noch nicht geschehen ist und gibt
     * sie zurück.
     * 
     * @return Die zu verwendende <code>DataStoreFactory</code>.
     * @throws ConfigurationException Falls während der Initialisierung der Facotry ein Fehler auftritt.
     */
    public static final DataStoreFactory init() throws ConfigurationException {
        if (factoryInstance != null) {
            return factoryInstance;
        }

        synchronized (FactoryFinder.class) {
            try {
                final File config = new File("ressourcen/StoreFactoryFinder.properties");
                String classname = "org.d3s.alricg.store.xom.XOMFactory";
                if (config.exists() && config.canRead()) {
                    ResourceBundle rb = new PropertyResourceBundle(new FileInputStream(config));
                    classname = rb.getString("data.store.factory.impl");
                }
                final Class<?> clazz = Class.forName(classname);
                final Constructor<?> conny = clazz.getConstructor(new Class[0]);
                factoryInstance = (DataStoreFactory) conny.newInstance(new Object[0]);
            } catch (Exception e) {
                factoryInstance = null;
                ProgAdmin.logger.log(Level.SEVERE, "DataStoreFactory instantiation failed!", e);
                throw new ConfigurationException("DataStoreFactory instantiation failed!", e);
            }
            factoryInstance.initialize();
            return factoryInstance;
        }
    }

    /**
     * Setzt die zu verwendende <code>DataStoreFactory</code> auf <code>null</code>.
     */
    static final void reset() {
        synchronized (FactoryFinder.class) {
            factoryInstance = null;
        }
    }
}
