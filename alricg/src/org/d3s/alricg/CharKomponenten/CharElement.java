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
 * Dies ist die super-Klasse f�r alle Charakter-Elemente. Alle Elemente eines
 * Charakters in Objekte von diesem Typ.
 * 
 * @author V.Strelow
 */
abstract public class CharElement {
    private String id; // Programmweit eindeutige ID
    private String name; // Name des Element
    private String sammelBegriff; // Zur besseren Sortierung
    private String beschreibung; // text, z.B.  "Siehe MBK S. 10"
    private RegelAnmerkung regelAnmerkung; // Anmerkungen f�r den User!
    private SonderRegel sonderRegel; // Zu beachtende Sonderreglen

    /**
     * Soll das Element angezeigt werden? Sinnvoll f�r und VorNachteile die in
     * der Gui anders verwendet werden, wie z.B. Herausragende Eigenschaft.
     * Diese soll unter den Vorteilen nicht w�hlbar sein, da sie bei den
     * Eigenschaften ber�cksichtigt wird. Das Element wird grau dargestellt,will
     * man es w�hlen erscheint der Text ("Kann nur bei ... gew�hlt werden.")
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
     * @return Der Text, der ausgegeben wird, wenn das Element nicht f�r
     * die Anzeige bestimmt ist. Gilt es solch einen Text nicht, dann "null"
     */
    public String getAnzeigenText() {
        return anzeigenText;
    }
    
    /**
     * @return True - Wenn das CharElement als w�hlbar angezeigt werden soll 
     *  (Default), ansonsten ist das Element nicht w�hlbar und kann an 
     *  anderer Stelle abgerufen werden (Hinweise liefert der TEXT)
     */
    public boolean isAnzeigen() {
        return anzeigen;
    }

    /**
     * "beschreibung" ist ein allgemeiner Text des CharElements
     * 
     * @return Liefert eine Beschreibung des Elements, mei�t einen Verweis
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
     * Der "sammelBegriff" dient einer besseren Struckturierung, zu k�nnen z.B.
     * mehrer Zwergenrassen unter "Zwerge" gesammelt werden.
     * 
     * @return Liefert das Attribut sammelBegriff.
     */
    public String getSammelBegriff() {
        return sammelBegriff;
    }

    /**
     * Falls dieses Element eine besondere Behandlung durch das Programm 
     * ben�tigt, so besitzt es eine "Sonderregel". Andernfalls liefert die 
     * Methode "null".
     * 
     * @return Liefert das Attribut sonderRegel.
     */
    public SonderRegel getSonderRegel() {
        return sonderRegel;
    }
    
    /** 
     * Methode �berschrieben
     * @see java.lang.Object#toString()
     * @return Den namen das Elements f�r die Anzeige in der GUI
     */
    public String toString() {
        return name;
    }
    
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen n�tigen angaben
     */
    protected void loadXmlElementCharElement(Element xmlElement){
    	// TODO implement
    }
    
    /**
     * Dient zur Speicherung (also f�r den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt" und zugef�gt, zu einem 
     * �bergebenen XML-Elemnet
     * @param xmlElement Das Xml-Element zu dem die Daten hinzugef�gt werden
     * @return Ein Xml-Element mit allen n�tigen Angaben.
     */
    protected Element writeXmlElementCharElement(Element xmlElement) {
    	//TODO implement
    	return null;
    }
    
    /* *************************************** Abstrakte Methoden ******************************* */
    
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen n�tigen angaben
     */
    public abstract void loadXmlElement(Element xmlElement);
    
    /**
     * Dient zur Speicherung (also f�r den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt".
     * @return Ein Xml-Element mit allen n�tigen Angaben.
     */
    public abstract Element writeXmlElement();
    

}
