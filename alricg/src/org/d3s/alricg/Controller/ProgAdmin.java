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
import nu.xom.ParsingException;

import org.d3s.alricg.Test.TestDriver;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * <b>Beschreibung:</b><br>
 * Verwaltet Progammweite Einstellgrößen wie Pfade zu Dateien. Steuert den Progammstart.
 * @author V.Strelow
 * @stereotype singelton
 */
public class ProgAdmin {
	public static final String DATEI_CONFIG = "config.xml";
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
	public static final String PFAD_Bilder = "img" + File.separator;
	
	// Werden durch config File eingelesen:
	public static String library;
	
	
	public static void main(String[] args) {
		
		new ProgAdmin();
		
	}
	
	public ProgAdmin() {
		buildXML();
	}
	

	
	public void buildXML(){
		 String url = DATEI_CONFIG;
		  try {      
		    XMLReader xerces = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser"); 
		    xerces.setFeature("http://apache.org/xml/features/validation/schema", true);                         

		    Builder parser = new Builder(xerces, true);
		    parser.build(DATEI_CONFIG);
		    System.out.println(DATEI_CONFIG + " is schema valid.");
		  }
		  catch (SAXException ex) {
		    System.out.println("Could not load Xerces.");
		    System.out.println(ex.getMessage());
		  }
		  catch (ParsingException ex) {
		    System.out.println(DATEI_CONFIG + " is not schema valid.");
		    System.out.println(ex.getMessage());
		    System.out.println(" at line " + ex.getLineNumber() 
		      + ", column " + ex.getColumnNumber());
		  } 
		  catch (IOException ex) { 
		    System.out.println("Due to an IOException, Xerces could not check " + DATEI_CONFIG);
		  }
	}
}
