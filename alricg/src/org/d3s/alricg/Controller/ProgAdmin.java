/*
 * Created 23. Januar 2005 / 15:26:21
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.Controller;

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

import org.d3s.alricg.CharKomponenten.Eigenschaften;

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
	public static final String PFAD_BILDER = "img" + File.separator;
	
	// Singeltons
	public static Logger logger; // Für Nachrichten aller Art
	public static String library; // Wird durch config File eingelesen
	
	
	public static void main(String[] args) {
		
		new ProgAdmin();
		
	}
	
	public ProgAdmin() {
		Element configRoot, tmpElement;
		Elements tmpElementList;
		ArrayList<File> arrayFiles = new ArrayList<File>();
		
	// ******* Initialisiere den Logger
		logger =  Logger.getLogger( "Programm-Logger" );
		logger.addHandler(new ConsoleHandler());
		
		
		// Bearbeite das Config File:
		configRoot = getRootElement( new File(DATEI_CONFIG) );
		
	// ********* Initialisiere die Library *********
		tmpElement = getRootElement( new File( PFAD_LIBRARY 
						+ configRoot.getFirstChildElement("library")
						.getAttribute("file").getValue()) );
		Library.initLibrary(tmpElement);
		
	// ******** Lese die Pfade zu den XML-Files mit Komponten ein *********
		tmpElementList = configRoot.getFirstChildElement("xmlRuleFilesD3S")
									.getChildElements();
		
		// Alle Orginal-Files einlesen und als File speichern
		for (int i = 0; i < tmpElementList.size(); i++) {
			arrayFiles.add( new File (PFAD_XML_DATEN_D3S 
					+ tmpElementList.get(i).getValue() )
			);
		}
		
		// Prüfen ob die Userdaten überhaupt eingelesen werden sollen
		if ( configRoot.getFirstChildElement("xmlRuleFilesUser")
					.getAttributeValue("readUserFiles").equals("true") ) 
		{
			tmpElementList = configRoot.getFirstChildElement("xmlRuleFilesUser")
										.getChildElements();
			
			// Alle User-Files einlesen und als File speichern
			for (int i = 0; i < tmpElementList.size(); i++) {
				arrayFiles.add( new File (PFAD_XML_DATEN_USER 
						+ tmpElementList.get(i).getValue() )
				);
			}
		}	
		
		// Einlesen der Files
		
		loadKomponents(arrayFiles);
		
		// TEST Wieder entfernen!
		System.out.println(Library.getShortText(Eigenschaften.FF.getBezeichnung()));
		System.out.println(Library.getShortText("AT"));
		System.out.println(Library.getShortText("Körperkraft"));
	}
	
	
	/**
	 * @param arrayFiles
	 */
	private void loadKomponents(ArrayList<File> arrayFiles) {
		Elements elementList;
		Element[] rootElements;
		CharKompAdmin.CharKomponenten[] charKomps;
		ArrayList<String> idArray;
		
		// GUARD Prüfe ob die Dateien eingelesen werden können
		for (int i = 0; i < arrayFiles.size(); i++) {
			if ( !arrayFiles.get(i).canRead() ) {
				// TODO eine richtige Fehlermeldung einfügen!
				System.out.println("Die Datei " + arrayFiles.get(i).getName() 
						+ " kann nicht gelesen werden!");
				return;
			}
		}
		
		charKomps = CharKompAdmin.CharKomponenten.values();
		idArray = new ArrayList<String>();
		
		//Files auslesen:
		rootElements = new Element[arrayFiles.size()];
		for (int i = 0; i < rootElements.length; i++) {
			rootElements[i] = getRootElement(arrayFiles.get(i));
		}
		
		// Erzeuge alle CharElement-Objekte, nur mit ID
		for (int i = 0; i < charKomps.length; i++) { // Alle CharKompos...
			idArray.clear();
			
			for (int ii = 0; ii < rootElements.length; ii++) { // Für alle Files...
				
				elementList = rootElements[ii]
						.getFirstChildElement( charKomps[i].getXmlBoxTag() )
						.getChildElements(); // Alle Elemente auslesen
				
				// Ids in ein Array Schreiben
				for (int iii = 0; iii < elementList.size(); iii++) { 
					idArray.add(elementList.get(iii).getAttributeValue("id"));
				}
			}
			
			//Objekte erzeugen lassen
			CharKompAdmin.self.initCharKomponents(idArray, charKomps[i]);
		}
	}
	
	private Element getRootElement(File xmlFile){
		
		try {
			Builder parser = new Builder();
			Document doc = parser.build(xmlFile);
			return doc.getRootElement();
		} catch (ParsingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
}

