package org.d3s.alricg.store.xom;

import java.util.Properties;

import org.d3s.alricg.store.ConfigStore;
import org.d3s.alricg.store.Configuration;

public class XOMConfigStore implements ConfigStore {
    
    private final Properties props;
    
    public XOMConfigStore(Properties props) {
        this.props = props;
    }
    
    public Configuration getConfig() {
        return new Configuration(props);
    }

}
