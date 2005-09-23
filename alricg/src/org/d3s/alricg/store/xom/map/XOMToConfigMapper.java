package org.d3s.alricg.store.xom.map;

import java.io.File;
import java.util.Properties;

import nu.xom.Element;

import org.d3s.alricg.store.ConfigStore;
import org.d3s.alricg.store.ConfigurationException;
import org.d3s.alricg.store.xom.XOMConfigStore;
import org.d3s.alricg.store.xom.XOMHelper;

public class XOMToConfigMapper {

    private static final String CONFIG_FILE = "ressourcen/config.xml";
    
    private static final String D3S_SCHEMA_PATH = "ressourcen/daten/schema/";

    private static final String D3S_LIBRARY_PATH = "ressourcen/daten/text/";

    private static final String D3S_IMG_PATH = "ressourcen/img/";

    private static final String D3S_DATA_PATH = "ressourcen/daten/basisDaten/";

    private static final String USER_DATA_PATH = "ressourcen/userDaten/";

    public ConfigStore readData() throws ConfigurationException {

        try {
            // Validity check
            final String configFile = CONFIG_FILE;
            final Element configRoot = XOMHelper.getRootElementNoLog(new File(configFile));
            if (configRoot == null) {
                throw new ConfigurationException("Config file " + configFile + " is invalid.");
            }

            // Mapping
            final Properties result = new Properties();
            result.put("config.file", CONFIG_FILE);
            result.put("d3s.schema.path", D3S_SCHEMA_PATH);
            result.put("d3s.library.path", D3S_LIBRARY_PATH);
            result.put("d3s.img.path", D3S_IMG_PATH);
            result.put("d3s.data.path", D3S_DATA_PATH);
            result.put("user.data.path", USER_DATA_PATH);

            return new XOMConfigStore(result);
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }
}
