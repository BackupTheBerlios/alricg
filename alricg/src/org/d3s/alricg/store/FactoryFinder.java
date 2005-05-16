package org.d3s.alricg.store;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

import org.d3s.alricg.controller.ProgAdmin;

public final class FactoryFinder {

    private static DataStoreFactory instance;

    public static final DataStoreFactory find() {

        if (instance == null) {
            synchronized (FactoryFinder.class) {
                try {
                    final Class<?> clazz = Class.forName("org.d3s.alricg.store.xom.XOMFactory");
                    final Constructor<?> conny = clazz.getConstructor(new Class[0]);
                    instance = (DataStoreFactory) conny.newInstance(new Object[0]);
                } catch (Exception e) {
                    instance = null;
                    ProgAdmin.logger.log(Level.SEVERE, "DataStoreFactory instantiation failed!", e);
                    throw new RuntimeException("DataStoreFactory instantiation failed!", e);
                }
            }
        }
        return instance;
    }

    public static final void init() throws ConfigurationException {
        instance.initialize();
    }
}
