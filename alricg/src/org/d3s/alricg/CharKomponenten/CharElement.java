/*
 * Created 22. Dezember 2004 / 01:07:12
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.*;
/**
 * <b>Beschreibung: </b> <br>
 * Dies ist die super-Klasse für alle Charakter-Elemente. Alle Elemente eines
 * Charakters in Objekte von diesem Typ.
 * 
 * @author V.Strelow
 */
abstract public class CharElement {
    private String id; // Programmweit eindeutige ID
    private String name; // Name des Element
    private String sammelBegriff; // Zur besseren Sortierung
    private String beschreibung; // text, z.B.  "Siehe MBK S. 10"
    private RegelAnmerkung regelAnmerkung; // Anmerkungen für den User!
    private SonderRegel sonderRegel; // Zu beachtende Sonderreglen

    /**
     * Soll das Element angezeigt werden? Sinnvoll für und VorNachteile die in
     * der Gui anders verwendet werden, wie z.B. Herausragende Eigenschaft.
     * Diese soll unter den Vorteilen nicht wählbar sein, da sie bei den
     * Eigenschaften berücksichtigt wird. Das Element wird grau dargestellt,will
     * man es wählen erscheint der Text ("Kann nur bei ... gewählt werden.")
     */
    private boolean anzeigen;
    private String anzeigenText;

    
    /**
     * @return Die eindeutige, einmalige ID des Elements
     */
    public String getId() {
        return id;
    }

    /**
     * @return Der Text, der ausgegeben wird, wenn das Element nicht für
     * die Anzeige bestimmt ist. Gilt es solch einen Text nicht, dann "null"
     */
    public String getAnzeigenText() {
        return anzeigenText;
    }
    
    /**
     * @return True - Wenn das CharElement als wählbar angezeigt werden soll 
     *  (Default), ansonsten ist das Element nicht wählbar und kann an 
     *  anderer Stelle abgerufen werden (Hinweise liefert der TEXT)
     */
    public boolean isAnzeigen() {
        return anzeigen;
    }

    /**
     * "beschreibung" ist ein allgemeiner Text des CharElements
     * 
     * @return Liefert eine Beschreibung des Elements, meißt einen Verweis
     *  auf ein Regelbuch.
     */
    /**
     * @return
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * @return Der Name des Elements, der auch angezeigt werden soll.
     */
    public String getName() {
        return name;
    }

    /**
     * "regelAnmerkung" treten auf, wenn ALRICG eine Regel nicht automatisch
     * umsetzen kann. Bei der Wahl des Elements wird dann die Regelanmerkung
     * angezeigt.
     *
     * @return Liefert die regelAnmerkung. Gibt es keine Anmerkungen, liefert
     * die Methode "null".
     */
    public RegelAnmerkung getRegelAnmerkung() {
        return regelAnmerkung;
    }

    /**
     * Der "sammelBegriff" dient einer besseren Struckturierung, zu können z.B.
     * mehrer Zwergenrassen unter "Zwerge" gesammelt werden.
     * 
     * @return Liefert das Attribut sammelBegriff.
     */
    public String getSammelBegriff() {
        return sammelBegriff;
    }

    /**
     * Falls dieses Element eine besondere Behandlung durch das Programm 
     * benötigt, so besitzt es eine "Sonderregel". Andernfalls liefert die 
     * Methode "null".
     * 
     * @return Liefert das Attribut sonderRegel.
     */
    public SonderRegel getSonderRegel() {
        return sonderRegel;
    }
    
    /** 
     * Methode überschrieben
     * @see java.lang.Object#toString()
     * @return Den namen das Elements für die Anzeige in der GUI
     */
    public String toString() {
        return name;
    }
    
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen nötigen angaben
     */
    protected void loadXmlElementCharElement(Element xmlElement){
    	// TODO implement
    }
    
    /**
     * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt" und zugefügt, zu einem 
     * übergebenen XML-Elemnet
     * @param xmlElement Das Xml-Element zu dem die Daten hinzugefügt werden
     * @return Ein Xml-Element mit allen nötigen Angaben.
     */
    protected Element writeXmlElementCharElement(Element xmlElement) {
    	//TODO implement
    	return null;
    }
    
    /* *************************************** Abstrakte Methoden ******************************* */
    
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen nötigen angaben
     */
    public abstract void loadXmlElement(Element xmlElement);
    
    /**
     * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt".
     * @return Ein Xml-Element mit allen nötigen Angaben.
     */
    public abstract Element writeXmlElement();
    

}
