/*
 * Created on 21.01.2005 / 02:20:51
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.Controller;

import java.util.Hashtable;

import nu.xom.Element;
import nu.xom.Elements;

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
    // Statischer selbstverweis!
    public static Library self = null;
    
    private static Hashtable<String, String> shortTxt = new Hashtable<String, String>();
    private static Hashtable<String, String> middleTxt = new Hashtable<String, String>();
    private static Hashtable<String, String> longTxt = new Hashtable<String, String>();
    
    
    private Library() {
    	// Noop!
    }
    
    public static void initLibrary(Element xmlElement) {
    	
    	if (self == null) {
    		self = new Library();
    	}
    	
    	// Nur das library-Element ist hier wichtig!
    	xmlElement = xmlElement.getFirstChildElement("library");
    	
    	// Erzeugung der drei "Rubriken"
    	fillHashtable( xmlElement.getFirstChildElement("short"), shortTxt );
    	fillHashtable( xmlElement.getFirstChildElement("middle"), middleTxt );
    	fillHashtable( xmlElement.getFirstChildElement("long"), longTxt );
    	
    }
    
    
    /**
     * @param xmlElement Das XML-Element zum auslesen der Texte
     * @param hash Die Hashtable zum speichern der Texte
     */
    private static void fillHashtable(Element xmlElement, Hashtable<String, String> hash) {
    	Elements tmpXmlArray;
    	
    	tmpXmlArray = xmlElement.getChildElements();
    	for (int i = 0; i < tmpXmlArray.size(); i++) {
    		hash.put(
    			tmpXmlArray.get(i).getAttribute("key").getValue(),
    			tmpXmlArray.get(i).getValue()
    		);
    	}
    }
    
    /**
     * Für alle Texte die aus genau EINEM Wort bestehen.
     * @param key Das Schlüsselword zu dem Text
     * @return Den Text zum Schlüsselwort
     */
    public static String getShortText(String key) {
        return shortTxt.get(key);
    }
    
    /**
     * Für alle Texte die aus ZWEI bis DREI Worten bestehen.
     * @param key Das Schlüsselword zu dem Text
     * @return Den Text zum Schlüsselwort
     */
    public static String getMiddleText(String key) {
        return middleTxt.get(key);
    }
    
    /**
     * Für alle Texte die aus MEHR ALS DREI Worten bestehen.
     * @param key Das Schlüsselword zu dem Text
     * @return Den Text zum Schlüsselwort
     */
    public static String getLongText(String key) {
        return longTxt.get(key);
    }
}
