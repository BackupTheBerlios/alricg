package org.d3s.alricg.store.xom.map;

import java.io.File;
import java.util.HashMap;
import java.util.Properties;

import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;
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
            result.put(ConfigStore.Key.config_file, CONFIG_FILE);
            result.put(ConfigStore.Key.d3s_schema_path, D3S_SCHEMA_PATH);
            result.put(ConfigStore.Key.d3s_library_path, D3S_LIBRARY_PATH);
            result.put(ConfigStore.Key.d3s_img_path, D3S_IMG_PATH);
            result.put(ConfigStore.Key.d3s_data_path, D3S_DATA_PATH);
            result.put(ConfigStore.Key.user_data_path, USER_DATA_PATH);

            // Laden des richtigen packages
            final Element regelConfig = configRoot.getFirstChildElement("regelConfig");
            final String pack = regelConfig.getFirstChildElement("benutztesPackage").getAttributeValue("id");
            final Elements elements = regelConfig.getChildElements("package");

            // Durchsuchen der packages nach dem mit der richtigen ID
            try {
                for (int i = 0; i < elements.size(); i++) {
                    final Element child = elements.get(i);
                    
                    // Wenn es sich um das gesuchte package handelt....
                    if (child.getAttributeValue("id").equals(pack)) {

                        final Element skt = child.getFirstChildElement("skt");
                        if (skt == null || skt.getAttributeValue("useOriginal").equals("true")) {

                            // Die SKT ist "original" und wird hier eingelesen
                            result.put(XOMConfigStore.SKT_KEY, loadSkt(regelConfig.getFirstChildElement("originalSkt")));
                            break;
                        } else {

                            // Die SKT ist nicht original, sonder eine eigende
                            result.put(XOMConfigStore.SKT_KEY, loadSkt(skt));
                        }

                    }
                }
            } catch (NumberFormatException e) {
                ProgAdmin.logger.severe("Konnte Zahlen-String aus SKT nicht in int umwandeln.");
            } catch (NullPointerException e) {
                ProgAdmin.logger.severe("Ein erwarteter XML-Tag der SKT ist nicht vorhanden.");
            }
            return new XOMConfigStore(result);
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    /**
     * Liest die SKT aus dem XML-Element aus. Die SKT MUSS in erwarteter Form vorliegen (siehe tag (originalSkt)!
     * 
     * @param element Das XML-Element mit der SKT
     * @return Eine HashMap mit der SKT
     * @throws NumberFormatException - Wenn die Zahlen nicht umgewandelt werden können NullPointerException - Wenn nicht
     *             30 Elemente Vorhanden sind
     * @author V.Strelow
     */
    private HashMap<KostenKlasse, Integer[]> loadSkt(Element element) throws NumberFormatException,
            NullPointerException {

        final HashMap<KostenKlasse, Integer[]> sktMap = new HashMap<KostenKlasse, Integer[]>();

        sktMap.put(KostenKlasse.A, new Integer[32]);
        sktMap.put(KostenKlasse.B, new Integer[32]);
        sktMap.put(KostenKlasse.C, new Integer[32]);
        sktMap.put(KostenKlasse.D, new Integer[32]);
        sktMap.put(KostenKlasse.E, new Integer[32]);
        sktMap.put(KostenKlasse.F, new Integer[32]);
        sktMap.put(KostenKlasse.G, new Integer[32]);
        sktMap.put(KostenKlasse.H, new Integer[32]);

        for (int i = 0; i < 32; i++) {
            Element tmpElement = element.getFirstChildElement("z" + (i));

            sktMap.get(KostenKlasse.A)[i] = Integer.parseInt(tmpElement.getAttributeValue("A"));
            sktMap.get(KostenKlasse.B)[i] = Integer.parseInt(tmpElement.getAttributeValue("B"));
            sktMap.get(KostenKlasse.C)[i] = Integer.parseInt(tmpElement.getAttributeValue("C"));
            sktMap.get(KostenKlasse.D)[i] = Integer.parseInt(tmpElement.getAttributeValue("D"));
            sktMap.get(KostenKlasse.E)[i] = Integer.parseInt(tmpElement.getAttributeValue("E"));
            sktMap.get(KostenKlasse.F)[i] = Integer.parseInt(tmpElement.getAttributeValue("F"));
            sktMap.get(KostenKlasse.G)[i] = Integer.parseInt(tmpElement.getAttributeValue("G"));
            sktMap.get(KostenKlasse.H)[i] = Integer.parseInt(tmpElement.getAttributeValue("H"));
        }
        return sktMap;
    }
}
