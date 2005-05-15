package org.d3s.alricg.store;

public interface DataStoreFactory {
	
    void initialize() throws ConfigurationException; 
    
    DataStore getData();

    ConfigStore getConfiguration(); 

    TextStore getLibrary(); 
}
