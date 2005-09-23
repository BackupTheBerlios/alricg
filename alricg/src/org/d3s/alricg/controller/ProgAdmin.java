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

import org.d3s.alricg.gui.SplashScreen;
import org.d3s.alricg.prozessor.FormelSammlung;
import org.d3s.alricg.store.ConfigurationException;
import org.d3s.alricg.store.FactoryFinder;

/**
 * <b>Beschreibung:</b><br>
 * Verwaltet Progammweite Einstellgr��en wie Pfade zu Dateien. Steuert den Progammstart.
 * 
 * @author V.Strelow
 * @stereotype singelton
 */
public class ProgAdmin {

	// Singeltons
	public static Logger logger; // F�r Nachrichten aller Art

	public static Messenger messenger; // F�r Nachrichten die Angezeigt werden sollen
	
	public static HeldenAdmin heldenAdmin; // Verwaltung der Helden
	
	public static Notepad notepad;


	/**
	 * 
	 * @param args An der Stelle "0" ist der Parameter "noScreen" m�glich, 
	 * um eine anzeige des Splash-Screen zu unterdr�cken (for allem f�r Test-Zwecke)
	 */
	public static void main(String[] args) {
		final boolean showSplash;
		
		// Auswerten der Parameter
		if (args == null  || args.length == 0) {
			showSplash = true;
		} else if (args[0].equals("noScreen")) {
			showSplash = false;
		} else {
			showSplash = true;
		}
		
        // Logger & Messenger
        logger = Logger.getLogger("Programm-Logger");
        logger.setUseParentHandlers(false); // disbale default logger
        logger.addHandler(new ConsoleHandler());
        heldenAdmin = new HeldenAdmin();
        messenger = new Messenger();
        notepad = new Notepad();

        // SplashScreen
        final SplashScreen splash = new SplashScreen();
        splash.setVisible(showSplash);

        // init Programm
        init();

        // Cleanup
        splash.setVisible(false);
        splash.prepareDispose(); // Vom Messenger abmelden
        splash.dispose();
        System.gc();   
    }

    private static final void init() {
    	
        try {
            // Initialize store & factory
            FactoryFinder.init();
            logger.info("Data Store Factory initialisiert...");
            
            FormelSammlung.initFormelSanmmlung();
            
        } catch (ConfigurationException ce) {
            logger.log(Level.SEVERE, "Config Datei konnte nicht geladen werden. Programm beendet.", ce);
            messenger.showMessage(Messenger.Level.fehler,
                    "Die Config-Datei konnte nicht geladen werden! Bitte �berpr�fen sie ob die Datei \n"
                            + "zugriffsbereit ist und im Orginalzustand vorliegt. \n" + "\n"
                            + "Das Programm konnte ohne diese Datei nicht gestartet werden \n"
                            + "und wird nun wieder geschlossen!");

            System.exit(1); // Programm Beenden
        }
    }
}
