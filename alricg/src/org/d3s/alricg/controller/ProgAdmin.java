/*
 * Created 23. Januar 2005 / 15:26:21
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.controller;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.d3s.alricg.gui.Messenger;
import org.d3s.alricg.gui.SplashScreen;
import org.d3s.alricg.store.ConfigStore;
import org.d3s.alricg.store.ConfigurationException;
import org.d3s.alricg.store.DataStore;
import org.d3s.alricg.store.DataStoreFactory;
import org.d3s.alricg.store.FactoryFinder;
import org.d3s.alricg.store.TextStore;

/**
 * <b>Beschreibung:</b><br>
 * Verwaltet Progammweite Einstellgrößen wie Pfade zu Dateien. Steuert den Progammstart.
 * 
 * @author V.Strelow
 * @stereotype singelton
 */
public class ProgAdmin {

	// Singeltons
	public static Logger logger; // Für Nachrichten aller Art

	public static Messenger messenger; // Für Nachrichten die Angezeigt werden sollen

	public static ConfigStore config;

	public static TextStore library;

	public static DataStore data;

	public static void main(String[] args) {

        // Logger & Messenger
        logger = Logger.getLogger("Programm-Logger");
        logger.setUseParentHandlers(false); // disbale default logger
        logger.addHandler(new ConsoleHandler());
        messenger = new Messenger();

        // SplashScreen
        final SplashScreen splash = new SplashScreen();
        splash.setVisible(true);

        // init Program
        init();

        // Cleanup
        splash.setVisible(false);
        splash.prepareDispose(); // Vom Messenger abmelden
        splash.dispose();
        System.gc();
    }

    private static final void init() {

        try {
            // Store factory
            final DataStoreFactory factory = FactoryFinder.find();
            factory.initialize();
            logger.info("Data Store Factory initialisiert...");

            // TODO Die drei stores sind nicht nötig in der Main Klasse!
            // sie können diekt über die Factory und den Factoryfinder angesprochen werden!!
            // Um Nachrichten zu senden könnt ein kleiner EventListener dafür impl. werden.
            // Oder ProgAdmin.messenger direkt verwenden!
            // Config
            config = factory.getConfiguration();
            logger.info("Configuration geladen...");
            messenger.sendInfo("Configuration geladen...");

            // Library
            library = factory.getLibrary();
            logger.info("Library geladen...");
            messenger.sendInfo("Library geladen...");

            // CharElemente laden
            factory.initializeData();
            data = factory.getData();
            factory.readData();
            logger.info("Daten geladen...");
            messenger.sendInfo("Daten geladen...");
            
        } catch (ConfigurationException ce) {
            logger.log(Level.SEVERE, "Config Datei konnte nicht geladen werden. Programm beendet.", ce);
            messenger.showMessage(Messenger.Level.fehler,
                    "Die Config-Datei konnte nicht geladen werden! Bitte überprüfen sie ob die Datei \n"
                            + "zugriffsbereit ist und im Orginalzustand vorliegt. \n" + "\n"
                            + "Das Programm konnte ohne diese Datei nicht gestartet werden \n"
                            + "und wird nun wieder geschlossen!");

            System.exit(1); // Programm Beenden
        }
    }
}
