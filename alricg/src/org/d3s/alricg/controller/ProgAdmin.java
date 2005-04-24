/*
 * Created 23. Januar 2005 / 15:26:21
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.d3s.alricg.gui.Messenger;
import org.d3s.alricg.gui.SplashScreen;
import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.EigenschaftEnum;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;

/**
 * <b>Beschreibung:</b><br>
 * Verwaltet Progammweite Einstellgrößen wie Pfade zu Dateien. Steuert den Progammstart.
 * @author V.Strelow
 * @stereotype singelton
 */
public class ProgAdmin {
	public static final String DATEI_CONFIG = "ressourcen" + File.separator 
											+ "config.xml";
	public static final String PFAD_XML_DATEN_D3S = "ressourcen" + File.separator 
											+ "daten" + File.separator 
											+ "basisDaten" + File.separator;
	public static final String PFAD_XML_DATEN_USER = "ressourcen" + File.separator 
											+ "userDaten" + File.separator;
	public static final String PFAD_XML_SCHEMA = "ressourcen" + File.separator 
											+ "daten" + File.separator 
											+ "schema" + File.separator;
	public static final String PFAD_LIBRARY = "ressourcen" + File.separator 
											+ "daten" + File.separator 
											+ "text" + File.separator;
	public static final String PFAD_BILDER = "ressourcen" + File.separator 
											+ "img" + File.separator;
	
	// Singeltons
	public static Logger logger; // Für Nachrichten aller Art
	public static Library library; // Wird durch config File eingelesen
	public static Messenger messenger; // Für Nachrichten die Angezeigt werden sollen
	public static CharKompAdmin charKompAdmin;
	
	public static void main(String[] args) {
		
		ProgAdmin p = new ProgAdmin();
		
		p.initProgAdmin();
	}
	

	/**
	 * Konstruktor. 
	 * Initiiert den Logging-Service und den Messenger.
	 */
	public ProgAdmin() {
		
		// Initialisiere den Logger
		logger =  Logger.getLogger( "Programm-Logger" );
		logger.setUseParentHandlers(false); // Default logger ausschalten
		logger.addHandler(new ConsoleHandler());
		
		// Initialisiere den Messenger für Nachrichten
		messenger = new Messenger();
	}
	
	/**
	 * Läd beim erschaffen alle für das Programm nötigen XML-Files:
	 * 	- Config-File
	 * 	- Daten-Files
	 *  - Libraray-File
	 */
	public void initProgAdmin()  {
		Element configRoot;
		SplashScreen splash;
		ArrayList<File> arrayFiles, arrayFilesNeed;
		
		// Initialisiere SplashScreen
		splash = new SplashScreen();
		splash.setVisible(true);

// 		Einlesen von XML Files
		configRoot = initConfig();
		logger.info("Config Datei geladen...");
		messenger.sendInfo( "Config Datei geladen..." );
		
//		Library laden für Sprache
		initLibrary(configRoot); 
		logger.info("Library Datei geladen...");		
		messenger.sendInfo( Library.getMiddleTxt("Library Datei geladen") );
		
//		CharKomp initiieren
		charKompAdmin = new CharKompAdmin();
		logger.info("CharKompAdmin erzeugt...");
		
// 		Lade "Regel-Dateien"
		messenger.sendInfo( Library.getLongTxt("Regel-Dateien werden geladen") );
		arrayFiles = getXmlFiles(configRoot); // Files aus Config laden
		logger.info("Regel-Files aus Config geladen...");
		initCharKomponents(arrayFiles); // "leere"-CharKomponenten aus Files erstellen
		logger.info("Charakter-Elemente erstellt...");
		loadCharKomponents(arrayFiles); // Erzeugte CharKomponenten mit Inhalt füllen
		logger.info("Charakter-Elemente initiiert...");
		
// 		SplashScreen schließen
		splash.setVisible(false);
		splash.prepareDispose(); // Vom Messenger abmelden
		splash.dispose();
		splash = null;
		
//		Garbage Collector aufrufen um den Speicher frei zu geben
		System.gc();
	}
	
	/**
	 * Alle "leeren" CharKomponenten durchgehen und die Komponenten füllen!
	 */
	private void loadCharKomponents(ArrayList<File> arrayFiles) {
		Elements elementList;
		Element[] rootElements;
		CharKompAdmin.CharKomponente[] charKomps;
		CharElement charElem;
		
		charKomps = CharKompAdmin.CharKomponente.values();
		
		// Files auslesen, hier kann eingelich kein Lade Fehler auftreten,
		// da alle File bereits durch "loadRegleXML" geladen waren!
		rootElements = new Element[arrayFiles.size()];
		for (int i = 0; i < rootElements.length; i++) {
			rootElements[i] = getRootElement(arrayFiles.get(i));
		}
		
		// Erzeuge alle CharElement-Objekte
		for (int i = 0; i < charKomps.length; i++) { // Alle CharKomps...
			
			for (int ii = 0; ii < rootElements.length; ii++) { // Für alle Files...
				
				// Gibt es ein Tag im xml-File für die CharKomp? ...
				if ( rootElements[ii].getFirstChildElement(charKomps[i].getXmlBoxTag()) 
						== null ) {
					continue; // ... wenn nicht, überspringen
				}
				
				// Alle Elemente zu dem CharKomp auslesen
				elementList = rootElements[ii]
						.getFirstChildElement( charKomps[i].getXmlBoxTag() )
						.getChildElements(); 
				
				// Alle Elemente durchgehen und die Objekte füllen!
				for (int iii = 0; iii < elementList.size(); iii++) {
					charElem = charKompAdmin.getCharElement(
							elementList.get(iii).getAttributeValue("id"),
							charKomps[i]);
					
					charElem.loadXmlElement( elementList.get(iii) );
				}
			}
		}
		
//		 TODO implement
	}
	
	/**
	 * Läd das Config-XML File und beendet das Programm falls dies nicht möglich ist!.
	 * @return Das Root-Element des Config-XML Files
	 */
	private Element initConfig() {
		Element configRoot;
		
		configRoot = getRootElement( new File(DATEI_CONFIG) ); // Liest das Config File ein
		
		if (configRoot == null) { // Config konnte nicht geladen werden!
			logger.severe("Config Datei (" + DATEI_CONFIG + ") konnte nicht geladen werden. Programm beendet. ");
			messenger.showMessage(Messenger.Level.erwartetFehler, "Die Config-Datei kommte unter \n" 
					+ DATEI_CONFIG + "\n"
					+ "nicht geladen werden! Bitte überprüfen sie ob die Datei \n"
					+ "Zugriffsbereit ist und im Orginalzustand vorliegt. \n"
					+ "\n"
					+ "Das Programm konnte ohne diese Datei nicht gestartet werden \n" 
					+ "und wird nun wieder geschlossen!");
					 
			System.exit(1); // Programm Beenden
		}
		
		return configRoot;

	}
	
	/**
	 * Initialisiert die Library, die für die Sprachsteuerung zuständig ist!
	 * @param configRoot Das Config-Element um den Pfad zur Datei auszulesen
	 */
	private void initLibrary(Element configRoot) {
		Element tmpElement = null;
		String lang = ""; // Welche Sprache
		boolean error = false;
		
		try {
			tmpElement = getRootElement( new File( PFAD_LIBRARY 
						+ configRoot.getFirstChildElement("library")
						.getAttribute("file").getValue()) );
			lang = configRoot.getFirstChildElement("library")
								.getAttributeValue("lang");
			if (tmpElement == null) { // Datei konnte nicht geladen werden
				error = true; // Flag für schließen des Programms
			}
		} catch (Exception ex) {
			logger.severe(ex.toString());
			error = true; // Flag für schließen des Programms
		}
		
		if (error) { // Fehler ist aufgetreten, Programm wird geschlossen!
			logger.severe("Library Datei (" 
					+ PFAD_LIBRARY + configRoot.getFirstChildElement("library").getAttribute("file").getValue()
					+ ") konnte nicht geladen werden. Programm beendet. ");
			messenger.showMessage(Messenger.Level.erwartetFehler, "Die Library-Datei kommte unter \n" 
				+ PFAD_LIBRARY + configRoot.getFirstChildElement("library").getAttribute("file").getValue()+ "\n"
				+ "nicht geladen werden! Bitte überprüfen sie ob die Datei \n"
				+ "Zugriffsbereit ist und im Orginalzustand vorliegt. \n"
				+ "\n"
				+ "Das Programm konnte ohne diese Datei nicht gestartet werden \n" 
				+ "und wird nun wieder geschlossen!");
			System.exit(1);
		}
		
		Library.initLibrary(tmpElement, lang);
	}
	
	/**
	 * Ließt alle XML-Files ein, die laut Config eingelesen werden sollen.
	 * Ließt auch die als im "benoetigtDateien" Tag angegeben Dateinen ein.
	 * @param configRoot Das Config-Element um den Pfad zur Datei auszulesen
	 * @return Eine ArrayList mir allen Files die eingelesen werden sollen 
	 * 			(inklusive dem "benoetigtDateien"-Tag
	 */
	private ArrayList<File> getXmlFiles(Element configRoot) {
		Elements tmpElementList;
		ArrayList<File> arrayFiles = new ArrayList<File>();
		
		// Lese die Pfade zu den XML-Files mit Komponten ein
		tmpElementList = configRoot.getFirstChildElement("xmlRuleFilesD3S")
									.getChildElements();
		
		// Alle Orginal-Files einlesen und als File speichern
		for (int i = 0; i < tmpElementList.size(); i++) {
			loadRegelFile(arrayFiles, new File (PFAD_XML_DATEN_D3S 
					+ tmpElementList.get(i).getValue()) );
		}
		
		// Prüfen ob die Userdaten überhaupt eingelesen werden sollen
		if ( configRoot.getFirstChildElement("xmlRuleFilesUser")
					   .getAttributeValue("readUserFiles")
					   .equals("true") ) 
		{
			tmpElementList = configRoot.getFirstChildElement("xmlRuleFilesUser")
										.getChildElements();
			
			// Alle User-Files einlesen und als File speichern
			for (int i = 0; i < tmpElementList.size(); i++) {
				loadRegelFile(arrayFiles, new File (PFAD_XML_DATEN_USER 
						+ tmpElementList.get(i).getValue()) );
			}
		}
		
		return arrayFiles;
	}
	
	/**
	 * Methode für Rekurisven Aufruf, fügt das übergebene file und alle
	 * im "benötigteDateien"-Tag enthaltenen Files zum Array hinzu, wenn
	 * sie im Array noch nicht vorhanden sind. (Nötig für einladen der Regel
	 * Files) 
	 * @param arrayFiles Array mit allen Parameter - IN/OUT Parameter
	 * @param file Das File das ggf. hinzugefügt werden soll mit "benötigtenDateien".
	 */
	private void loadRegelFile(ArrayList<File> arrayFiles, File file) {
		Element tempElement;
		Elements elementList;
		
		// Wenn das File schon enthalten ist --> return
		if ( arrayFiles.contains(file) ) return;
		
		tempElement = getRootElement(file); // File als XML laden
		if (tempElement == null) return; // Konnte nicht geladen werden --> return
		
		arrayFiles.add(file); // ansonsten File hinzufügen
		
		tempElement = tempElement.getFirstChildElement("preamble")
						.getFirstChildElement("benoetigtDateien");
		
		// Falls das File keine weiteren Dateien benötigt --> Return
		if (tempElement == null) return;
		
		elementList = tempElement.getChildElements("datei");
		
		// Rekursiver Aufruf mit allen von diesem File benötigten Dateien
		for (int i = 0; i < elementList.size(); i++) {
			if (elementList.get(i).getAttribute("ordner").getValue().equals("d3s") ) 
			{
				loadRegelFile(arrayFiles, new File(PFAD_XML_DATEN_D3S 
						+ elementList.get(i).getValue()));
			} else { // value = user
				loadRegelFile(arrayFiles, new File(PFAD_XML_DATEN_USER 
						+ elementList.get(i).getValue()));
			}
		}
	}
	
	/**
	 * Ließt alle Files ein und erzeugt die CharElemente (nur mit ID)
	 * @param arrayFiles Eine ArrayList mit allen Dateien die Eingelesen werden
	 */
	private void initCharKomponents(ArrayList<File> arrayFiles) {
		Elements elementList;
		Element[] rootElements;
		CharKomponente[] charKomps;
		ArrayList<String> idArray;
		
		charKomps = CharKompAdmin.CharKomponente.values();
		idArray = new ArrayList<String>();
		
		// Files auslesen, hier kann eingelich kein Lade Fehler auftreten,
		// da alle File bereits durch "loadRegleXML" geladen waren!
		rootElements = new Element[arrayFiles.size()];
		for (int i = 0; i < rootElements.length; i++) {
			rootElements[i] = getRootElement(arrayFiles.get(i));
		}
		
		// Erzeuge alle CharElement-Objekte, nur mit ID
		for (int i = 0; i < charKomps.length; i++) { // Alle CharKomps...
			idArray.clear();
			
			for (int ii = 0; ii < rootElements.length; ii++) { // Für alle Files...
				
				// Gibt es ein Tag im xml-File für die CharKomp? ...
				if (rootElements[ii].getFirstChildElement( charKomps[i].getXmlBoxTag() ) 
						== null) {
					continue; // ... wenn nicht, überspringen
				}
				
				// Alle Elemente zu dem CharKomp auslesen
				elementList = rootElements[ii]
						.getFirstChildElement( charKomps[i].getXmlBoxTag() )
						.getChildElements(); 
				
				// Ids in ein Array Schreiben
				for (int iii = 0; iii < elementList.size(); iii++) { 
					idArray.add(elementList.get(iii).getAttributeValue("id"));
				}
			}
			
			//Objekte erzeugen lassen (nur mit ID)
			charKompAdmin.initCharKomponents(idArray, charKomps[i]);
		}
		
		// Eigenschaften hinzufügen (initialisierung erstellt diese komplett)
		charKompAdmin.initCharKomponents(EigenschaftEnum.getIdArray(), 
										CharKomponente.eigenschaft);
	}
	
	
	/**
	 * Ließt ein XML-File ein und gibt das RootElement zurück.
	 * @param xmlFile Das File das eingelesen werden soll
	 * @return Das rootElement des XML-Files oder null, falls die Datei nicht geladen 
	 * werden konnte!
	 */
	private Element getRootElement(File xmlFile) {
		
		try {
			Builder parser = new Builder(); // Auf true setzen für Validierung
			Document doc = parser.build(xmlFile);
			return doc.getRootElement();
		
		} catch (ValidityException ex) { 
			logger.severe(ex.getMessage());
			messenger.showMessage(Messenger.Level.erwartetFehler, 
					Library.getErrorTxt("Fehlerhafte Datei") + "\n" 
					+ "  " + xmlFile.getName() + "\n"
					+ Library.getErrorTxt("XML Validierungsfehler") );
			
		} catch (ParsingException ex) {
			logger.severe(ex.getMessage());
			messenger.showMessage( Messenger.Level.erwartetFehler,
					Library.getErrorTxt("Fehlerhafte Datei") + "\n" 
					+ "  " + xmlFile.getName() + "\n"
					+ Library.getErrorTxt("XML Parsingfehler") );
			
		} catch (IOException ex) {
			if ( !xmlFile.exists() ) {
				logger.severe(ex.getMessage());
				messenger.showMessage( Messenger.Level.erwartetFehler,
						Library.getErrorTxt("Fehlerhafte Datei") + "\n" 
						+ "  " + xmlFile.getName() + "\n"
						+ Library.getErrorTxt("Datei existiert nicht Fehler") );
			} else if (	!xmlFile.canRead() ) {
				logger.severe(ex.getMessage());
				messenger.showMessage( Messenger.Level.erwartetFehler,
						Library.getErrorTxt("Fehlerhafte Datei") + "\n" 
						+ "  " + xmlFile.getName() + "\n"
						+ Library.getErrorTxt("Datei nicht lesbar Fehler") );
			} else {
				logger.severe(ex.getMessage());
				messenger.showMessage( Messenger.Level.erwartetFehler,
						Library.getErrorTxt("Fehlerhafte Datei") + "\n" 
						+ "  " + xmlFile.getName() + "\n"
						+ Library.getErrorTxt("Datei nicht lesbar/unbekannt Fehler") );
			}
		}
		
		return null;
	}
}

