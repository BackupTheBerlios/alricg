/*
 * Created 26. September 2005 / 21:42:43
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.store.xom;

import org.d3s.alricg.store.ConfigStore;
import org.d3s.alricg.store.ConfigurationException;
import org.d3s.alricg.store.DataStore;
import org.d3s.alricg.store.AbstractStoreFactory;
import org.d3s.alricg.store.TextStore;

/**
 * Mock-Object für die XOMFactory
 * @author <a href="mailto:msturzen@mac.com>St. Martin</a>
 *
 */
public class XOMFactoryMock implements AbstractStoreFactory {

    private XOMFactory x = new XOMFactory();
    
    
    // @see org.d3s.alricg.store.AbstractStoreFactory#initialize()
    public void initialize() throws ConfigurationException {
        x.initialize();
        System.out.println("initialized");
    }

    // @see org.d3s.alricg.store.AbstractStoreFactory#getData()
    public DataStore getData() {
        System.out.println("getData");
        return x.getData();
    }

    // @see org.d3s.alricg.store.AbstractStoreFactory#getConfiguration()
    public ConfigStore getConfiguration() {
        System.out.println("getConfig");
        return x.getConfiguration();
    }

    // @see org.d3s.alricg.store.AbstractStoreFactory#getLibrary()
    public TextStore getLibrary() {
        System.out.println("getLib");
        return x.getLibrary();
    }

    // @see org.d3s.alricg.store.AbstractStoreFactory#storeData()
    public void storeData() throws ConfigurationException {
        System.out.println("storeData");
        x.storeData();
    }
}
