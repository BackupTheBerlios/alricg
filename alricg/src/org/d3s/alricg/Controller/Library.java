/*
 * Created on 21.01.2005 / 02:20:51
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.Controller;

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

    private Library() {
    	
    }
    
    public static void initLibrary() {
    	if (self == null) {
    		self = new Library();
    	}
    	// TODO Load Texts from XML!
    }
    
    public String getShortText(String key) {
        return "?";
    }
    
    public String getMiddleText(String key) {
        return "?";
    }
    
    public String getLongText(String key) {
        return "?";
    }
}
