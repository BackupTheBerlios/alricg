package org.d3s.alricg.store;

import java.lang.reflect.Constructor;

public final class FactoryFinder {

    private static DataStoreFactory instance;

    public static final DataStoreFactory find() {

        if (instance == null) {
            synchronized (FactoryFinder.class) {
                try {
                    final Class<?> clazz = Class.forName("org.d3s.alricg.store.xom.XOMFactory");
                    final Constructor conny = clazz.getConstructor(new Class[0]);
                    instance = (DataStoreFactory) conny.newInstance(new Object[0]);
                } catch (Exception e) {

                    // TODO do some logging!
                }
            }
        }
        return instance;
    }

    public static final void init() throws ConfigurationException {
        instance.initialize();
    }

}
