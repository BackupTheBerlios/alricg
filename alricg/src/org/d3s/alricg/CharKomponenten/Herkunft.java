/*
 * Created 22. Dezember 2004 / 02:25:53
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.Links.Auswahl;

/**
 * <b>Beschreibung: </b> <br>
 * Superklasse für Rasse, Kultur und Profession. Fasst Gemeinsamkeiten zusammen.
 * Elemente vom Typ "Herkunft" werden nach einer Initiierung nicht mehr
 * verändert.
 * 
 * @author V.Strelow
 */
public abstract class Herkunft extends CharElement {
    private int gpKosten;
    private Herkunft varianteVon; //TODO Etwas besseres finden, evtl. Generics?
    private boolean kannGewaehltWerden; // Eine Herkunft kann auch nur als Basis
                                        // für Varianten dienen
    private int geschlecht;
    private int soMin;
    private int soMax;
    /**
     * Beispiel: An der Stelle Eigenschaft.MU im Array steht der Wert für Mut.
     */
    private int[] eigenschaftVoraussetzungen = new int[Eigenschaften
            .getAnzahlEigenschaften()];
    private int[] eigenschaftModis = new int[Eigenschaften
            .getAnzahlEigenschaften()];
    private Auswahl vorteileAuswahl;
    private Auswahl nachteileAuswahl;
    private Auswahl sfAuswahl;
    private Auswahl liturgienAuswahl;
    private Auswahl ritualeAuswahl;
    private Vorteil[] empfVorteil;
    private Nachteil[] empfNachteile;
    private Vorteil[] ungeVorteile;
    private Nachteil[] ungeNachteile;
    private Sonderfertigkeit[] verbilligteSonderf;
    private Liturgie[] verbilligteLiturien;
    private Ritual[] verbilligteRituale;
    private Auswahl talente;
    private Auswahl zauber;
    private Auswahl hauszauber;
    private Auswahl aktivierbareZauber;

    /**
     * Für manche Herkunft ist das Geschlecht wichtig. In dem Fall wird hier das
     * Geschlecht angegeben.
     * 
     * @return Liefert das vorgeschriebene Geschlecht für diese Herkunft.
     */
    public int getGeschlecht() {
        return geschlecht;
    }

    /**
     * @return Liefert die Kosten in Generierungs-Punkten.
     */
    public int getGpKosten() {
        return gpKosten;
    }

    /**
     * Eine Herkunft die nicht gewählt werden kann, dient als Basis für
     * Varianten und kann nicht direkt gewählt werden. Aber durchaus indirekt.
     * 
     * @return Liefert das Attribut kannGewaehltWerden
     */
    public boolean isKannGewaehltWerden() {
        return kannGewaehltWerden;
    }

    /**
     * @return Liefert den Maximal zulässigen SozialStatus.
     */
    public int getSoMax() {
        return soMax;
    }

    /**
     * @return Liefert den Minimal zulässigen SozialStatus.
     */
    public int getSoMin() {
        return soMin;
    }

    /**
     * Wenn diese Herkunft eine Variante von einer anderen ist, so wird dies
     * hier vermerkt.
     * 
     * @return Liefert die "Eltern-Herkunft"
     */
    public Herkunft getVarianteVon() {
        // TODO implement getModi mit generics
        return varianteVon;
    }

    /**
     * Mit dieser Methode werden die Modifikationen auf Eigenschaften (MU, KL,.. ),
     * LeP, AsP, AuP, Ka, SO, INI und MR ausgelesen.
     * 
     * @param modiID
     *            Die ID aus der Klasse "Eigenschaften"
     * @return Der Modifikator-Wert für diese Eigenschaft
     * @see org.d3s.alricg.CharComponenten.Eigenschaften
     */
    public int getEigenschaftModi(int modiId) {
        // TODO implement getModi mit enum
        return eigenschaftModis[modiId];
    }

    public Auswahl getVorteileAuswahl() {
        return vorteileAuswahl;
    }

    public Auswahl getNachteileAuswahl() {
        return nachteileAuswahl;
    }

    public Auswahl getSfAuswahl() {
        return sfAuswahl;
    }

    public Auswahl getLiturgienAuswahl() {
        return liturgienAuswahl;
    }

    public Auswahl getRitualeAuswahl() {
        return ritualeAuswahl;
    }

    public Vorteil[] getEmpfVorteil() {
        return empfVorteil;
    }

    public Nachteil[] getEmpfNachteile() {
        return empfNachteile;
    }

    public Vorteil[] getUngeVorteile() {
        return ungeVorteile;
    }

    public Nachteil[] getUngeNachteile() {
        return ungeNachteile;
    }

    public Sonderfertigkeit[] getVerbilligteSonderf() {
        return verbilligteSonderf;
    }

    public Liturgie[] getVerbilligteLiturien() {
        return verbilligteLiturien;
    }

    public Ritual[] getVerbilligteRituale() {
        return verbilligteRituale;
    }

    public Auswahl getTalente() {
        return talente;
    }

    public Auswahl getZauber() {
        return zauber;
    }

    public Auswahl getHauszauber() {
        return hauszauber;
    }

    public Auswahl getAktivierbareZauber() {
        return aktivierbareZauber;
    }
    
    /**
     * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
     * werden alle relevanten Informationen ausgelesen.
     * @param xmlElement Das Xml-Element mit allen nötigen angaben
     */
    public void loadXmlElementHerkunft(Element xmlElement){
    	// TODO implement
    }
    
    /**
     * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben werden 
     * in ein XML Objekt "gemapt" und zugefügt, zu einem 
     * übergebenen XML-Elemnet
     * @param xmlElement Das Xml-Element zu dem die Daten hinzugefügt werden
     * @return Ein Xml-Element mit allen nötigen Angaben.
     */
    protected Element writeXmlElementHerkunft(Element xmlElement) {
    	//TODO implement
    	return null;
    }

}
