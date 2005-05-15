package org.d3s.alricg.store;

import java.util.Properties;

public class Configuration {
    
    private final Properties props;
    
    public Configuration(Properties props) {
        this.props = props;
        
    }

    public String getProperty(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }

    public boolean contains(Object value) {
        return props.contains(value);    
    }

    public boolean containsKey(Object key) {
        return props.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return props.containsValue(value);
        
    }

    public boolean isEmpty() {
        return props.isEmpty();
    }
    
    
    

}
