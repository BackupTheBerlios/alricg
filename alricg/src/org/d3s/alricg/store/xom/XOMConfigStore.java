package org.d3s.alricg.store.xom;

import java.io.File;
import java.util.HashMap;
import java.util.Properties;

import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;
import org.d3s.alricg.store.ConfigStore;
import org.d3s.alricg.store.Configuration;
import org.d3s.alricg.store.ConfigurationException;

public class XOMConfigStore implements ConfigStore {
    private final Properties props;

    public XOMConfigStore(Properties props) {
        this.props = props;
    }

    public Configuration getConfig() {
        return new Configuration(props);
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
        Element tmpElement;

        final HashMap<KostenKlasse, Integer[]> sktMap = new HashMap<KostenKlasse, Integer[]>();
        ;

        sktMap.put(KostenKlasse.A, new Integer[32]);
        sktMap.put(KostenKlasse.B, new Integer[32]);
        sktMap.put(KostenKlasse.C, new Integer[32]);
        sktMap.put(KostenKlasse.D, new Integer[32]);
        sktMap.put(KostenKlasse.E, new Integer[32]);
        sktMap.put(KostenKlasse.F, new Integer[32]);
        sktMap.put(KostenKlasse.G, new Integer[32]);
        sktMap.put(KostenKlasse.H, new Integer[32]);

        for (int i = 0; i < 32; i++) {
            tmpElement = element.getFirstChildElement("z" + (i));

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

    /**
     * Liest die SKT aus dem config-File ein und liefert diese als HashMap zurück. Für die HashMap gilt: - key =
     * Gewünschte Kostenklasse - value = (Integer[0] - Kosten für Stufen 1) bis (Integer[29] - Kosten für Stufen 30)
     * 
     * @return Die aktuelle SKT wie sie im config file angegeben wird.
     * @throws ConfigurationException Falls keine SKT zurückgeliefert werden kann
     * @author V.Strelow
     */
    public HashMap<KostenKlasse, Integer[]> getSkt() throws ConfigurationException {

        // Laden de srichtigen packages
        try {
        final Element configRoot = XOMHelper.getRootElementNoLog(new File(props.getProperty("config.file"))).getFirstChildElement("regelConfig");
        final String pack = configRoot.getFirstChildElement("benutztesPackage").getAttributeValue("id");

        Elements elements = configRoot.getChildElements("package");

        // Durchsuchen der packages nach dem mit der richtigen ID
        try {
            for (int i = 0; i < elements.size(); i++) {

                // Wenn es sich um das gesuchte package handelt....
                if (elements.get(i).getAttributeValue("id").equals(pack)) {

                    if (elements.get(i).getFirstChildElement("skt") == null
                            || elements.get(i).getFirstChildElement("skt").getAttributeValue("useOriginal").equals(
                                    "true")) {

                        // Die SKT ist "original" und wird hier eingelesen
                        return loadSkt(configRoot.getFirstChildElement("originalSkt"));

                    } else {

                        // Die SKT ist nicht original, sonder eine eigende
                        return loadSkt(elements.get(i).getFirstChildElement("skt"));

                    }

                }
            }
        } catch (NumberFormatException e) {
            ProgAdmin.logger.severe("Konnte Zahlen-String aus SKT nicht in int umwandeln.");
        } catch (NullPointerException e) {
            ProgAdmin.logger.severe("Ein erwarteter XML-Tag der SKT ist nicht vorhanden.");
        }
        } catch (Exception e) {
            throw new ConfigurationException("Fehler beim einlesen der SKT aus config-Datei.");
        }
        
        throw new ConfigurationException("Fehler beim einlesen der SKT aus config-Datei.");
    }

}
