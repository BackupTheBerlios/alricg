/*
 * Created 22. Dezember 2004 / 02:25:53
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.Links.Auswahl;
import org.d3s.alricg.Controller.ProgAdmin;

/**
 * <b>Beschreibung: </b> <br>
 * Superklasse für Rasse, Kultur und Profession. Fasst Gemeinsamkeiten zusammen.
 * Elemente vom Typ "Herkunft" werden nach einer Initiierung nicht mehr
 * verändert.
 * 
 * @author V.Strelow
 */
public abstract class Herkunft extends CharElement {
    
	public enum Geschlecht {mann, frau, mannFrau};
	
	private int gpKosten;
    private boolean kannGewaehltWerden = true; // Eine Herkunft kann auch nur als Basis
                                        // für Varianten dienen
    private Geschlecht geschlecht = Geschlecht.mannFrau;
    private int soMin;
    private int soMax;
    /**
     * Beispiel: An der Stelle Eigenschaft.MU im Array steht der Wert für Mut.
     */
    /*private int[] eigenschaftVoraussetzungen = new int[Eigenschaften
            .getAnzahlEigenschaften()];
    private int[] eigenschaftModis = new int[Eigenschaften
            .getAnzahlEigenschaften()];*/
    
    // TODO: Es gibt doch auch hier ne Auswahl??
    private Auswahl eigenschaftModis;
    
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
    public Geschlecht getGeschlecht() {
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
     * Mit dieser Methode werden die Modifikationen auf Eigenschaften (MU, KL,.. ),
     * LeP, AsP, AuP, Ka, SO, INI und MR ausgelesen.
     * 
     * @param eigenschaft Die Enum aus der Klasse "Eigenschaften"
     * @return Der Modifikator-Wert für diese Eigenschaft
     * @see org.d3s.alricg.CharComponenten.EigenschaftEnum
     */
    public int getEigenschaftModi(EigenschaftEnum eigenschaft) {
        // TODO implement getModi mit enum
        return 0; // eigenschaftModis[modiId];
    }

    /**
     * @return Auswahl an Vorteilen, die gewählt werden können,
     */
    public Auswahl getVorteileAuswahl() {
        return vorteileAuswahl;
    }

    /**
     * @return Auswahl an Nachteilen, die gewählt werden können
     */
    public Auswahl getNachteileAuswahl() {
        return nachteileAuswahl;
    }
    
    /**
     * @return Auswahl an Sonderfertigkeiten, die gewählt werden können
     */
    public Auswahl getSfAuswahl() {
        return sfAuswahl;
    }
    
    /**
     * @return Auswahl an Liturgien, die gewählt werden können
     */
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
    
    /* (non-Javadoc) Methode überschrieben / ruft super Methode auf!
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
// 		Auslesen der GP
    	try {
    		this.gpKosten = Integer.parseInt(xmlElement.getFirstChildElement("gp")
    													.getValue());
    		
    		// Auslesen vom "kannGewaehltWerde"-Tag
    		if ( xmlElement.getFirstChildElement("kannGewaehltWerden") != null ) {
    			
    			// Sicherstellen, das der Wert gültig ist
    			assert xmlElement.getFirstChildElement("kannGewaehltWerden")
									.getValue().equals("false") 
						|| xmlElement.getFirstChildElement("kannGewaehltWerden")
									.getValue().equals("true"); 
    			
    			if ( xmlElement.getFirstChildElement("kannGewaehltWerden")
    												.getValue().equals("false") ) 
    			{
    				this.kannGewaehltWerden = false;
    			} else { // ... .getValue().equals("ture")
    				this.kannGewaehltWerden = true;
    			}
    		}

    		// Auslesen des Geschlechts
    		if ( xmlElement.getFirstChildElement("geschlecht") != null ) {
    			
    			// Sicherstellen, das der Wert gültig ist
    			assert xmlElement.getFirstChildElement("geschlecht")
									.getValue().equals("M") 
						|| xmlElement.getFirstChildElement("geschlecht")
									.getValue().equals("W")
						|| xmlElement.getFirstChildElement("geschlecht")
									.getValue().equals("MW"); 
    			
    			if ( xmlElement.getFirstChildElement("geschlecht").getValue().equals("M") ) {
    				this.geschlecht = Geschlecht.mann;
    			} else if ( xmlElement.getFirstChildElement("geschlecht").getValue().equals("W") ) {
    				this.geschlecht = Geschlecht.frau;
    			} else { //... .getValue().equals("MW")
    				this.geschlecht = Geschlecht.mannFrau;
    			}
    		}
    		
    		// Auslesen der eigenschaftModi
    		if ( xmlElement.getFirstChildElement("eigenschaftModi") != null ) {
    			eigenschaftModis = new Auswahl( this );
    			eigenschaftModis.loadXmlElement(xmlElement.getFirstChildElement("eigenschaftModi"));
    		}
    		
    	} catch (NumberFormatException ex) {
    		ProgAdmin.logger.severe( ex.toString() );
    	}
    	
    	// TODO implement
    }
    
    /* (non-Javadoc) Methode überschrieben / ruft super Methode auf!
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	// TODO implement
    	return xmlElement;
    }

}
