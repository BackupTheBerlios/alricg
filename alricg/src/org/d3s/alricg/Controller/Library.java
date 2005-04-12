/*
 * Created on 21.01.2005 / 02:20:51
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.Controller;

import java.util.HashMap;

import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.GUI.Messenger;

/**
 * <u>Beschreibung:</u><br> 
 * Dient dem bereitstellen aller Texte die angezeigt werden. Die Texte werden 
 * aus einer XML Datei ausgelesen und anhand eines keyWords identifiziert.
 * 
 * -- Ein Singelton --
 * @author V. Strelow
 * @stereotype singelton
 */
public class Library {
    
    private static String lang;
    private static HashMap<String, String> shortTxt = new HashMap<String, String>();
    private static HashMap<String, String> middleTxt = new HashMap<String, String>();
    private static HashMap<String, String> longTxt = new HashMap<String, String>();
    private static HashMap<String, String> errorMsgTxt = new HashMap<String, String>();
    private static HashMap<String, String> toolTipTxt = new HashMap<String, String>();
    
    private Library() {
    	// Noop!
    }
    
    /**
     * Initialisiert die Library, ließt texte in der Sprache neu aus.
     * @param xmlElement Das xmlElement der Library, zum auslesen der texte
     * @param langIn Kennung für die Sprache, aus Config-file ("DE"/ "EN")
     */
    public static void initLibrary(Element xmlElement, String langIn) {
    	boolean flag = true;
    	
    	// Sprache einstellen
    	lang = langIn;
    	
    	// Nur das library-Element ist hier wichtig!
    	xmlElement = xmlElement.getFirstChildElement("library");
    	
    	// Erzeugung der drei "Rubriken"
    	flag = fillHashtable( xmlElement.getFirstChildElement("short"), shortTxt );
    	flag = flag & fillHashtable( xmlElement.getFirstChildElement("middle"), middleTxt );
    	flag = flag & fillHashtable( xmlElement.getFirstChildElement("long"), longTxt );
    	flag = flag & fillHashtable( xmlElement.getFirstChildElement("errorMsg"), errorMsgTxt );
    	flag = flag & fillHashtable( xmlElement.getFirstChildElement("toolTip"), toolTipTxt );
    	
    	if ( !flag ) { // Wenn mindestens einer der Fälle Fehlerhaft ist
    		ProgAdmin.messenger.showMessage(Messenger.Level.erwartetFehler, 
    				"Es konnten nicht alle Texte geladen werden! \n" 
    				+ "Dies ist für eine Fehlerfreie anzeige jedoch notwendig. \n" 
    				+ "Die Library Datei scheint fehlerfaft zu sein. Bitte stellen \n"
    				+ "sie sicher das diese Datei im Originalzustand ist. \n" 
    				+ "\n"
    				+ "Das Programm wird nun wahscheinlich nur Fehlerhaft funktionieren."
    		);
    	}
    	
    }
    
    /**
     * @param xmlElement Das XML-Element zum auslesen der Texte
     * @param hash Die Hashtable zum speichern der Texte
     */
    private static boolean fillHashtable(Element xmlElement, HashMap<String, String> hash) {
    	Elements tmpXmlArray, tmpXmlEntrys;
    	int idx;
    	boolean fehlerFrei = true;
    	
    	hash.clear(); // Evtl. alte einträge löschen
    	tmpXmlArray = xmlElement.getChildElements();
    	
    	for (int i = 0; i < tmpXmlArray.size(); i++) {
    		tmpXmlEntrys = tmpXmlArray.get(i).getChildElements();
    		
    		idx = -1; // Index für fehlererkennung setzen
    		
    		// richtige Sprache suchen
    		for (int ii = 0; ii < tmpXmlEntrys.size(); ii++) {
    			if ( tmpXmlEntrys.get(ii).getAttributeValue("lang").equals(lang) ) {
    				idx = ii;
    				break; // Abbruch der Schleife, Ergebnis Gefunden
    			}
    		}
    		
    		if (idx < 0) { // Fehler ist erkannt, 
    			ProgAdmin.logger.warning("Sprache (="+ lang + ") des Library-Entrys mit dem key '"
    					+ tmpXmlArray.get(i).getAttributeValue("key") + "'"
    					+ "konnte nicht gefunden werden.");
    			fehlerFrei = false;
    		} else {
	    		hash.put(
	    			tmpXmlArray.get(i).getAttributeValue("key"),
	    			tmpXmlEntrys.get(idx).getValue()
	    		);
    		}
    	}
    	
    	return fehlerFrei;
    }
    
    /**
     * Für alle Texte die aus genau EINEM Wort bestehen.
     * @param key Das Schlüsselword zu dem Text
     * @return Den Text zum Schlüsselwort
     */
    public static final String getShortTxt(String key) {
    	assert shortTxt.get(key) != null;
        return shortTxt.get(key);
    }
    
    /**
     * Für alle Texte die aus ZWEI bis DREI Worten bestehen.
     * @param key Das Schlüsselword zu dem Text
     * @return Den Text zum Schlüsselwort
     */
    public static final String getMiddleTxt(String key) {
    	assert middleTxt.get(key) != null;
        return middleTxt.get(key);
    }
    
    /**
     * Für alle Texte die aus MEHR ALS DREI Worten bestehen.
     * @param key Das Schlüsselword zu dem Text
     * @return Den Text zum Schlüsselwort
     */
    public static final String getLongTxt(String key) {
    	assert longTxt.get(key) != null;
        return longTxt.get(key);
    }
    
    /**
     * Für alle Texte die Fehlermeldungen sind.
     * @param key Das Schlüsselword zu dem Text
     * @return Den Text zum Schlüsselwort
     */
    public static final String getErrorTxt(String key) {
    	assert errorMsgTxt.get(key) != null;
        return errorMsgTxt.get(key);
    }
    
    /**
     * Für alle Texte die ToolTips sind.
     * @param key Das Schlüsselword zu dem Text
     * @return Den Text zum Schlüsselwort
     */
    public static final String getToolTipTxt(String key) {
    	assert toolTipTxt.get(key) != null;
        return toolTipTxt.get(key);
    }
}
