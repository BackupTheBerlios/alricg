package org.d3s.alricg.store;

import java.util.HashMap;

import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;


public interface ConfigStore {
    
    Configuration getConfig();
    
    /**
     * Liest die SKT aus dem config-File ein und liefert diese als HashMap zurück.
     * Für die HashMap gilt: 
     * - key = Gewünschte Kostenklasse
     * - value = (Integer[0] - Kosten für Stufen 1) bis (Integer[29] - Kosten für Stufen 30)
     * @return Die aktuelle SKT wie sie im config file angegeben wird.
     * @throws ConfigurationException Falls keine SKT zurückgeliefert werden kann
     * @author V.Strelow
     */
    HashMap<KostenKlasse, Integer[]> getSkt() throws ConfigurationException;
}
