/*
 * Created 23. Januar 2005 / 15:26:21
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.Controller;

import java.io.File;
import java.io.IOException;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
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
	
	// Werden durch config File eingelesen:
	public static String library;
	
	
	public static void main(String[] args) {
		
		new ProgAdmin();
		
	}
	
	public ProgAdmin() {
		Element configRoot, tmpElement;
		
		configRoot = getRootElement( new File(DATEI_CONFIG) );
		
		// Initialisiere die Library
		tmpElement = getRootElement( new File( PFAD_LIBRARY 
						+ configRoot.getFirstChildElement("library")
						.getAttribute("file").getValue()) );
		Library.initLibrary(tmpElement);
		
		// Lese die Komponenten ein
		
		
		// TEST Wieder entfernen!
		System.out.println(Library.getShortText(Eigenschaften.FF.getBezeichnung()));
		System.out.println(Library.getShortText("AT"));
		System.out.println(Library.getShortText("Körperkraft"));
		
	}


	
	public Element getRootElement(File xmlFile){
		
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
