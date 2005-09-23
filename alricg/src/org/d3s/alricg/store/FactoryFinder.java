package org.d3s.alricg.store;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

import org.d3s.alricg.controller.ProgAdmin;

public final class FactoryFinder {

    private static DataStoreFactory instance;

    public static final DataStoreFactory find() {
        if (instance == null) {
            throw new NullPointerException("DataStoreFactory is not initialised!");
        }
        return instance;
    }

    public static final DataStoreFactory init() throws ConfigurationException {
        if (instance != null) {
            return instance;
        }
        
        synchronized (FactoryFinder.class) {
            try {
                final Class<?> clazz = Class.forName("org.d3s.alricg.store.xom.XOMFactory");
                final Constructor<?> conny = clazz.getConstructor(new Class[0]);
                instance = (DataStoreFactory) conny.newInstance(new Object[0]);
            } catch (Exception e) {
                instance = null;
                ProgAdmin.logger.log(Level.SEVERE, "DataStoreFactory instantiation failed!", e);
                throw new ConfigurationException("DataStoreFactory instantiation failed!", e);
            }
            instance.initialize();
            return instance;
        }
    }

    static final void reset() {
        synchronized (FactoryFinder.class) {
            instance = null;
        }
    }
}
