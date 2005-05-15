package org.d3s.alricg.store;

public class ConfigurationException extends Exception {
  
    static final long serialVersionUID = -3387516993124229948L;

    public ConfigurationException() {
        super();
    }
    
    public ConfigurationException(String msg) {
        super(msg);
    }
    
    public ConfigurationException(Throwable t) {
        super(t);
    }
    
    public ConfigurationException(String msg, Throwable t) {
        super(msg, t);
    }
    
}
