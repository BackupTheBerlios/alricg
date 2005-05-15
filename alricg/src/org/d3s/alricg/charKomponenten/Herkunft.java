/*
 * Created 22. Dezember 2004 / 02:25:53
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.Werte.Geschlecht;
import org.d3s.alricg.charKomponenten.links.Auswahl;
import org.d3s.alricg.charKomponenten.links.IdLinkList;
import org.d3s.alricg.charKomponenten.links.Voraussetzung;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.controller.CharKomponente;

/**
 * <b>Beschreibung: </b> <br>
 * Superklasse für Rasse, Kultur und Profession. Fasst Gemeinsamkeiten zusammen.
 * Elemente vom Typ "Herkunft" werden nach einer Initiierung nicht mehr
 * verändert.
 * 
 * @author V.Strelow
 */
public abstract class Herkunft extends CharElement {
	
	public static int SO_MIN_DEFAULT = 0;
	public static int SO_MAX_DEFAULT = 100;
	private int gpKosten;
    private boolean kannGewaehltWerden = true; // Eine Herkunft kann auch nur als Basis
                                        // für Varianten dienen
    private Geschlecht geschlecht = Geschlecht.mannFrau;
    private int soMin = SO_MIN_DEFAULT;
    private int soMax = SO_MAX_DEFAULT;
    /**
     * Beispiel: An der Stelle Eigenschaft.MU im Array steht der Wert für Mut.
     */
    /*private int[] eigenschaftVoraussetzungen = new int[Eigenschaften
            .getAnzahlEigenschaften()];
    private int[] eigenschaftModis = new int[Eigenschaften
            .getAnzahlEigenschaften()];*/
    
    // TODO: Es gibt doch auch hier ne Auswahl??
    private Auswahl eigenschaftModis;
    private Voraussetzung voraussetzung;
    private Repraesentation repraesentation;
    
    private Auswahl vorteileAuswahl;
    private Auswahl nachteileAuswahl;
    private Auswahl sfAuswahl;
    private Auswahl liturgienAuswahl;
    private Auswahl ritualeAuswahl;
    private IdLinkList empfVorteile;
    private IdLinkList empfNachteile;
    private IdLinkList ungeVorteile;
    private IdLinkList ungeNachteile;
    private IdLinkList verbilligteVort;
    private IdLinkList verbilligteNacht;
    private IdLinkList verbilligteSonderf;
    private IdLinkList verbilligteLiturgien;
    private IdLinkList verbilligteRituale;
    private Auswahl talente;
    private Auswahl zauber;
    private Auswahl hauszauber;
    private Auswahl aktivierbareZauber;
    private IdLinkList ZauberNichtBeginn;
    
    protected Herkunft varianteVon;

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
     * @return Liefert den Maximal zulässigen SozialStatus / 100 ist "Default"
     */
    public int getSoMax() {
        return soMax;
    }

    /**
     * @return Liefert den Minimal zulässigen SozialStatus / 0 ist "Default"
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

    public IdLinkList getEmpfVorteil() {
        return empfVorteile;
    }

    public IdLinkList getEmpfNachteile() {
        return empfNachteile;
    }

    public IdLinkList getUngeVorteile() {
        return ungeVorteile;
    }

    public IdLinkList getUngeNachteile() {
        return ungeNachteile;
    }

    public IdLinkList getVerbilligteSonderf() {
        return verbilligteSonderf;
    }

    public IdLinkList getVerbilligteLiturien() {
        return verbilligteLiturgien;
    }

    public IdLinkList getVerbilligteRituale() {
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
     * @return Liefer die "Eltern-Herkunft", von der diese eine Variante ist,
     * 	oder "null"
     */
    public Herkunft getVarianteVon() {
    	return varianteVon;
    }
    
    /**
     * @return true: Diese Herkunft ist eine Variante einer anderen Herkunft,
     * 	sonst false
     */
    public boolean isVariante() {
    	return (varianteVon != null);
    }
    
    
    
    
	/**
	 * @return Liefert das Attribut eigenschaftModis.
	 */
	public Auswahl getEigenschaftModis() {
		return eigenschaftModis;
	}
	/**
	 * @param eigenschaftModis Setzt das Attribut eigenschaftModis.
	 */
	public void setEigenschaftModis(Auswahl eigenschaftModis) {
		this.eigenschaftModis = eigenschaftModis;
	}
	/**
	 * @return Liefert das Attribut empfVorteile.
	 */
	public IdLinkList getEmpfVorteile() {
		return empfVorteile;
	}
	/**
	 * @param empfVorteile Setzt das Attribut empfVorteile.
	 */
	public void setEmpfVorteile(IdLinkList empfVorteile) {
		this.empfVorteile = empfVorteile;
	}
	/**
	 * @return Liefert das Attribut repraesentation.
	 */
	public Repraesentation getRepraesentation() {
		return repraesentation;
	}
	/**
	 * @param repraesentation Setzt das Attribut repraesentation.
	 */
	public void setRepraesentation(Repraesentation repraesentation) {
		this.repraesentation = repraesentation;
	}
	/**
	 * @return Liefert das Attribut verbilligteLiturgien.
	 */
	public IdLinkList getVerbilligteLiturgien() {
		return verbilligteLiturgien;
	}
	/**
	 * @param verbilligteLiturgien Setzt das Attribut verbilligteLiturgien.
	 */
	public void setVerbilligteLiturgien(IdLinkList verbilligteLiturgien) {
		this.verbilligteLiturgien = verbilligteLiturgien;
	}
	/**
	 * @return Liefert das Attribut verbilligteNacht.
	 */
	public IdLinkList getVerbilligteNacht() {
		return verbilligteNacht;
	}
	/**
	 * @param verbilligteNacht Setzt das Attribut verbilligteNacht.
	 */
	public void setVerbilligteNacht(IdLinkList verbilligteNacht) {
		this.verbilligteNacht = verbilligteNacht;
	}
	/**
	 * @return Liefert das Attribut verbilligteVort.
	 */
	public IdLinkList getVerbilligteVort() {
		return verbilligteVort;
	}
	/**
	 * @param verbilligteVort Setzt das Attribut verbilligteVort.
	 */
	public void setVerbilligteVort(IdLinkList verbilligteVort) {
		this.verbilligteVort = verbilligteVort;
	}
	/**
	 * @return Liefert das Attribut voraussetzung.
	 */
	public Voraussetzung getVoraussetzung() {
		return voraussetzung;
	}
	/**
	 * @param voraussetzung Setzt das Attribut voraussetzung.
	 */
	public void setVoraussetzung(Voraussetzung voraussetzung) {
		this.voraussetzung = voraussetzung;
	}
	/**
	 * @return Liefert das Attribut zauberNichtBeginn.
	 */
	public IdLinkList getZauberNichtBeginn() {
		return ZauberNichtBeginn;
	}
	/**
	 * @param zauberNichtBeginn Setzt das Attribut zauberNichtBeginn.
	 */
	public void setZauberNichtBeginn(IdLinkList zauberNichtBeginn) {
		ZauberNichtBeginn = zauberNichtBeginn;
	}
	/**
	 * @param aktivierbareZauber Setzt das Attribut aktivierbareZauber.
	 */
	public void setAktivierbareZauber(Auswahl aktivierbareZauber) {
		this.aktivierbareZauber = aktivierbareZauber;
	}
	/**
	 * @param empfNachteile Setzt das Attribut empfNachteile.
	 */
	public void setEmpfNachteile(IdLinkList empfNachteile) {
		this.empfNachteile = empfNachteile;
	}
										/**
										 * @param geschlecht Setzt das Attribut geschlecht.
										 */
										public void setGeschlecht(
												Geschlecht geschlecht) {
											this.geschlecht = geschlecht;
										}
	/**
	 * @param gpKosten Setzt das Attribut gpKosten.
	 */
	public void setGpKosten(int gpKosten) {
		this.gpKosten = gpKosten;
	}
	/**
	 * @param hauszauber Setzt das Attribut hauszauber.
	 */
	public void setHauszauber(Auswahl hauszauber) {
		this.hauszauber = hauszauber;
	}
	/**
	 * @param kannGewaehltWerden Setzt das Attribut kannGewaehltWerden.
	 */
	public void setKannGewaehltWerden(boolean kannGewaehltWerden) {
		this.kannGewaehltWerden = kannGewaehltWerden;
	}
	/**
	 * @param liturgienAuswahl Setzt das Attribut liturgienAuswahl.
	 */
	public void setLiturgienAuswahl(Auswahl liturgienAuswahl) {
		this.liturgienAuswahl = liturgienAuswahl;
	}
	/**
	 * @param nachteileAuswahl Setzt das Attribut nachteileAuswahl.
	 */
	public void setNachteileAuswahl(Auswahl nachteileAuswahl) {
		this.nachteileAuswahl = nachteileAuswahl;
	}
	/**
	 * @param ritualeAuswahl Setzt das Attribut ritualeAuswahl.
	 */
	public void setRitualeAuswahl(Auswahl ritualeAuswahl) {
		this.ritualeAuswahl = ritualeAuswahl;
	}
	/**
	 * @param sfAuswahl Setzt das Attribut sfAuswahl.
	 */
	public void setSfAuswahl(Auswahl sfAuswahl) {
		this.sfAuswahl = sfAuswahl;
	}
	/**
	 * @param soMax Setzt das Attribut soMax.
	 */
	public void setSoMax(int soMax) {
		this.soMax = soMax;
	}
	/**
	 * @param soMin Setzt das Attribut soMin.
	 */
	public void setSoMin(int soMin) {
		this.soMin = soMin;
	}
	/**
	 * @param talente Setzt das Attribut talente.
	 */
	public void setTalente(Auswahl talente) {
		this.talente = talente;
	}
	/**
	 * @param ungeNachteile Setzt das Attribut ungeNachteile.
	 */
	public void setUngeNachteile(IdLinkList ungeNachteile) {
		this.ungeNachteile = ungeNachteile;
	}
	/**
	 * @param ungeVorteile Setzt das Attribut ungeVorteile.
	 */
	public void setUngeVorteile(IdLinkList ungeVorteile) {
		this.ungeVorteile = ungeVorteile;
	}
	/**
	 * @param varianteVon Setzt das Attribut varianteVon.
	 */
	public void setVarianteVon(Herkunft varianteVon) {
		this.varianteVon = varianteVon;
	}
	/**
	 * @param verbilligteRituale Setzt das Attribut verbilligteRituale.
	 */
	public void setVerbilligteRituale(IdLinkList verbilligteRituale) {
		this.verbilligteRituale = verbilligteRituale;
	}
	/**
	 * @param verbilligteSonderf Setzt das Attribut verbilligteSonderf.
	 */
	public void setVerbilligteSonderf(IdLinkList verbilligteSonderf) {
		this.verbilligteSonderf = verbilligteSonderf;
	}
	/**
	 * @param vorteileAuswahl Setzt das Attribut vorteileAuswahl.
	 */
	public void setVorteileAuswahl(Auswahl vorteileAuswahl) {
		this.vorteileAuswahl = vorteileAuswahl;
	}
	/**
	 * @param zauber Setzt das Attribut zauber.
	 */
	public void setZauber(Auswahl zauber) {
		this.zauber = zauber;
	}
    /* (non-Javadoc) Methode überschrieben / ruft super Methode auf!
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	Element tmpElement = null;
    	
// 		Auslesen der GP
    	try {
    		this.gpKosten = Integer.parseInt(xmlElement.getFirstChildElement("gp")
    													.getValue());
    		
    		// Auslesen vom "kannGewaehltWerden"-Tag
    		if ( xmlElement.getFirstChildElement("kannGewaehltWerden") != null ) {
    			
    			// Sicherstellen, das der Wert gültig ist
    			assert xmlElement.getFirstChildElement("kannGewaehltWerden")
									.getValue().equals("false") 
						|| xmlElement.getFirstChildElement("kannGewaehltWerden")
									.getValue().equals("true"); 
    			
    			if ( xmlElement.getFirstChildElement("kannGewaehltWerden")
    												.getValue().equals("false") ) 	{
    				this.kannGewaehltWerden = false;
    			} else { // ... .getValue().equals("ture") // Default
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
    		
    		// Auslesen der Grenzen für den Sozialstatus
    		if ( xmlElement.getFirstChildElement("soGrenzen") != null ) {
				
    			if ( xmlElement.getFirstChildElement("soGrenzen").getAttribute("soMin") != null ) {
    				this.soMin = Integer.parseInt(xmlElement
    							.getFirstChildElement("soGrenzen")
    							.getAttributeValue("soMin"));
    			}
    			if ( xmlElement.getFirstChildElement("soGrenzen").getAttribute("soMax") != null ) {
    				this.soMax = Integer.parseInt(xmlElement
    							.getFirstChildElement("soGrenzen")
    							.getAttributeValue("soMax"));
    			}
    		}
    		
    		// Auslesen der Voraussetzung
    		if ( xmlElement.getFirstChildElement("voraussetzung") != null ) {
    			voraussetzung = new Voraussetzung(this);
    			voraussetzung.loadXmlElement(xmlElement.getFirstChildElement("voraussetzung"));
    		}
    		
    		// Auslesen der Vorteile
    		if ( xmlElement.getFirstChildElement("vorteile") != null ) {
    			vorteileAuswahl = new Auswahl(this);
    			vorteileAuswahl.loadXmlElement(xmlElement.getFirstChildElement("vorteile"));
    		}
    		
    		// Auslesen der Nachteile
    		if ( xmlElement.getFirstChildElement("nachteile") != null ) {
    			nachteileAuswahl = new Auswahl(this);
    			nachteileAuswahl.loadXmlElement(xmlElement.getFirstChildElement("nachteile"));
    		}
    		
    		// Auslesen der Sonderfertigkeiten
    		if ( xmlElement.getFirstChildElement("sonderf") != null ) {
    			sfAuswahl = new Auswahl(this);
    			sfAuswahl.loadXmlElement(xmlElement.getFirstChildElement("sonderf"));
    		}
    		
    		// Auslesen der Liturgien
    		if ( xmlElement.getFirstChildElement("liturgien") != null ) {
    			liturgienAuswahl = new Auswahl(this);
    			liturgienAuswahl.loadXmlElement(xmlElement.getFirstChildElement("liturgien"));
    		}
    		
    		// Auslesen der goetterRituale
    		if ( xmlElement.getFirstChildElement("goetterRituale") != null ) {
    			ritualeAuswahl = new Auswahl(this);
    			ritualeAuswahl.loadXmlElement(xmlElement.getFirstChildElement("goetterRituale"));
    		}
    		
    		// Auslesen der empfohlenen Vorteile
    		if ( xmlElement.getFirstChildElement("empfVorteile") != null ) {
    			empfVorteile = new IdLinkList(this);
    			empfVorteile.loadXmlElement(xmlElement.getFirstChildElement("empfVorteile"));
    		}
    		
    		// Auslesen der empfohlenen Nachteile
    		if ( xmlElement.getFirstChildElement("empfNachteile") != null ) {
    			empfNachteile = new IdLinkList(this);
    			empfNachteile.loadXmlElement(xmlElement.getFirstChildElement("empfNachteile"));
    		}
    		
    		// Auslesen der ungeeigneten Vorteile
    		if ( xmlElement.getFirstChildElement("ungeVorteile") != null ) {
    			ungeVorteile = new IdLinkList(this);
    			ungeVorteile.loadXmlElement(xmlElement.getFirstChildElement("ungeVorteile"));
    		}
    		
    		// Auslesen der ungeeigneten Nachteile
    		if ( xmlElement.getFirstChildElement("ungeNachteile") != null ) {
    			ungeNachteile = new IdLinkList(this);
    			ungeNachteile.loadXmlElement(xmlElement.getFirstChildElement("ungeNachteile"));
    		}
    		
    		// Auslesen der verbilligten Vorteile
    		if ( xmlElement.getFirstChildElement("verbilligteVorteile") != null ) {
    			verbilligteVort = new IdLinkList(this);
    			verbilligteVort.loadXmlElement(xmlElement.getFirstChildElement("verbilligteVorteile"));
    		}
    		
    		// Auslesen der verbilligten Nachteile
    		if ( xmlElement.getFirstChildElement("verbilligteNachteile") != null ) {
    			verbilligteNacht = new IdLinkList(this);
    			verbilligteNacht.loadXmlElement(xmlElement.getFirstChildElement("verbilligteNachteile"));
    		}
    		
    		// Auslesen der verbilligten Sonderfertigkeiten
    		if ( xmlElement.getFirstChildElement("verbilligteSonderf") != null ) {
    			verbilligteSonderf = new IdLinkList(this);
    			verbilligteSonderf.loadXmlElement(xmlElement.getFirstChildElement("verbilligteSonderf"));
    		}
    		
    		// Auslesen der verbilligten Liturgien
    		if ( xmlElement.getFirstChildElement("verbilligteLiturgien") != null ) {
    			verbilligteLiturgien = new IdLinkList(this);
    			verbilligteLiturgien.loadXmlElement(xmlElement.getFirstChildElement("verbilligteLiturgien"));
    		}
    		
    		// Auslesen der verbilligten Rituale
    		if ( xmlElement.getFirstChildElement("verbilligteRituale") != null ) {
    			verbilligteRituale = new IdLinkList(this);
    			verbilligteRituale.loadXmlElement(xmlElement.getFirstChildElement("verbilligteRituale"));
    		}
    		
    		// Auslesen der Talente
    		if ( xmlElement.getFirstChildElement("talente") != null ) {
    			talente = new Auswahl(this);
    			talente.loadXmlElement(xmlElement.getFirstChildElement("talente"));
    		}
    		
    		// Auslesen der magischen Werte 
    		if ( xmlElement.getFirstChildElement("magie") != null ) {
    			tmpElement = xmlElement.getFirstChildElement("magie");
    			
    			// Auslesen der Art der Magischen repräsentation
    			if ( tmpElement.getAttribute("repraesentId") != null ) {
	    			repraesentation = (Repraesentation) ProgAdmin.data
	    				.getCharElement(
	    					tmpElement.getAttributeValue("repraesentId"),
	    					CharKomponente.repraesentation
	    				);
    			}
    			
	    		// Auslesen der Hauszauber
	    		if ( tmpElement.getFirstChildElement("hauszauber") != null ) {
	    			hauszauber = new Auswahl(this);
	    			hauszauber.loadXmlElement(tmpElement.getFirstChildElement("hauszauber"));
	    		}
	    		
	    		// Auslesen der Zauber
	    		if ( tmpElement.getFirstChildElement("zauber") != null ) {
	    			zauber = new Auswahl(this);
	    			zauber.loadXmlElement(tmpElement.getFirstChildElement("zauber"));
	    		}
	    		
	    		// Auslesen der Zauber
	    		if ( tmpElement.getFirstChildElement("aktivierbareZauber") != null ) {
	    			aktivierbareZauber = new Auswahl(this);
	    			aktivierbareZauber.loadXmlElement(tmpElement.getFirstChildElement("aktivierbareZauber"));
	    		}
	    		
	    		// Auslesen der Zauber, die nicht zu Beginn wählbar sind
	    		if ( tmpElement.getFirstChildElement("zauberNichtZuBegin") != null ) {
	    			ZauberNichtBeginn = new IdLinkList(this);
	    			ZauberNichtBeginn.loadXmlElement(tmpElement.getFirstChildElement("zauberNichtZuBegin"));
	    		}
    		}
    		
    	} catch (NumberFormatException ex) {
    		ProgAdmin.logger.severe( ex.toString() );
    	}
    }
    
    /* (non-Javadoc) Methode überschrieben / ruft super Methode auf!
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	// Hinzufügen der GP-Kosten
    	tmpElement = new Element("gp");
    	tmpElement.appendChild(Integer.toString(gpKosten));
    	xmlElement.appendChild(tmpElement);
    	
    	// Hinzufügen des "varianteVon" Tags wird in Rasse, Kultur bzw. Profession erledigt! 
    	
    	// Hinzufügen des "kannGewaehltWerden"-Tags
    	if ( !kannGewaehltWerden ) {
    		tmpElement = new Element("kannGewaehltWerden");
    		tmpElement.appendChild("false");
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Hinzufügen des Geschlechts geschlecht
    	if ( this.geschlecht == Geschlecht.mann ) {
    		tmpElement = new Element("geschlecht");
    		tmpElement.appendChild("M");
    		xmlElement.appendChild(tmpElement);
    	} else if ( this.geschlecht == Geschlecht.frau ) {
    		tmpElement = new Element("geschlecht");
    		tmpElement.appendChild("W");
    		xmlElement.appendChild(tmpElement);
    	} // else ... ist Default und brauch nicht beachtet zu werden

    	// Hinzufügen der Eigenschaft-Modis
    	if ( this.eigenschaftModis != null ) {
    		xmlElement.appendChild(eigenschaftModis.writeXmlElement("eigenschaftModi"));
    	}
    	
    	// Hinzufügen der SO-Grenzen
    	if ( this.soMax != SO_MAX_DEFAULT || this.soMin != SO_MIN_DEFAULT ) {
    		tmpElement = new Element("soGrenzen");
    		
    		if ( this.soMin != SO_MIN_DEFAULT ) {
    			tmpElement.addAttribute(new Attribute("soMin", Integer.toString(soMin)));
    		}
    		if ( this.soMax != SO_MAX_DEFAULT ) {
    			tmpElement.addAttribute(new Attribute("soMax", Integer.toString(soMax)));
    		}
  
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Hinzufügen der Voraussetzungen
    	if ( this.voraussetzung != null ) {
    		xmlElement.appendChild(voraussetzung.writeXmlElement("voraussetzung"));
    	}
    	
    	// Hinzufügen der Vorteile
    	if ( this.vorteileAuswahl != null ) {
    		xmlElement.appendChild(vorteileAuswahl.writeXmlElement("vorteile"));
    	}   	
    	
    	// Hinzufügen der Nachteile
    	if ( this.nachteileAuswahl != null ) {
    		xmlElement.appendChild(nachteileAuswahl.writeXmlElement("nachteile"));
    	}   
    	
    	// Hinzufügen der Sonderfertigkeiten
    	if ( this.sfAuswahl != null ) {
    		xmlElement.appendChild(sfAuswahl.writeXmlElement("sonderf"));
    	}
    	
    	// Hinzufügen der Liturgien
    	if ( this.liturgienAuswahl != null ) {
    		xmlElement.appendChild(liturgienAuswahl.writeXmlElement("liturgien"));
    	}
    	
    	// Hinzufügen der Rituale
    	if ( this.ritualeAuswahl != null ) {
    		xmlElement.appendChild(ritualeAuswahl.writeXmlElement("goetterRituale"));
    	}
    	
    	
    	// Hinzufügen der empfohlenen Vorteile
    	if ( this.empfVorteile != null ) {
    		xmlElement.appendChild(empfVorteile.writeXmlElement("empfVorteile"));
    	}   	
    	
    	// Hinzufügen der empfohlenen Nachteile
    	if ( this.empfNachteile != null ) {
    		xmlElement.appendChild(empfNachteile.writeXmlElement("empfNachteile"));
    	}   
    	
    	// Hinzufügen der ungeeigneten Vorteile
    	if ( this.ungeVorteile != null ) {
    		xmlElement.appendChild(ungeVorteile.writeXmlElement("ungeVorteile"));
    	}   	
    	
    	// Hinzufügen der ungeeigneten Nachteile
    	if ( this.ungeNachteile != null ) {
    		xmlElement.appendChild(ungeNachteile.writeXmlElement("ungeNachteile"));
    	}   
    	
       	// Hinzufügen der verbilligten Vorteile
    	if ( this.verbilligteVort != null ) {
    		xmlElement.appendChild(verbilligteVort.writeXmlElement("verbilligteVorteile"));
    	}   	
    	
    	// Hinzufügen der verbilligten Nachteile
    	if ( this.verbilligteNacht != null ) {
    		xmlElement.appendChild(verbilligteNacht.writeXmlElement("verbilligteNachteile"));
    	}   
    	
    	// Hinzufügen der verbilligten Sonderfertigkeiten
    	if ( this.verbilligteSonderf != null ) {
    		xmlElement.appendChild(verbilligteSonderf.writeXmlElement("verbilligteSonderf"));
    	}
    	
    	// Hinzufügen der verbilligten Liturgien
    	if ( this.verbilligteLiturgien != null ) {
    		xmlElement.appendChild(verbilligteLiturgien.writeXmlElement("verbilligteLiturgien"));
    	}
    	
    	// Hinzufügen der verbilligten Rituale
    	if ( this.verbilligteRituale != null ) {
    		xmlElement.appendChild(verbilligteRituale.writeXmlElement("verbilligteRituale"));
    	}

    	// Hinzufügen der Talente
    	if ( this.talente != null ) {
    		xmlElement.appendChild(talente.writeXmlElement("talente"));
    	}
    	
    	// Hinzufügen der Magie 
    	if (this.repraesentation != null 
    			|| this.hauszauber != null 
    			|| this.zauber != null 
    			|| this.aktivierbareZauber != null
    			|| this.ZauberNichtBeginn != null) 
    	{
    		tmpElement = new Element("magie");

    		// Schreiben der Repraesentation
    		if (repraesentation != null) {
    			tmpElement.addAttribute(new Attribute("repraesentId", repraesentation.getId()));
    		}
    		
	    	// Hinzufügen der Hauszauber
	    	if ( this.hauszauber != null ) {
	    		tmpElement.appendChild(hauszauber.writeXmlElement("hauszauber"));
	    	}
	    	
	    	// Hinzufügen der Zauber
	    	if ( this.zauber != null ) {
	    		tmpElement.appendChild(zauber.writeXmlElement("zauber"));
	    	}
	    	
	    	// Hinzufügen der aktivierbaren Zauber
	    	if ( this.aktivierbareZauber != null ) {
	    		tmpElement.appendChild(aktivierbareZauber.writeXmlElement("aktivierbareZauber"));
	    	}
	    	
	    	// Hinzufügen der nicht zu beginn wählbaren Zauber
	    	if ( this.ZauberNichtBeginn != null ) {
	    		tmpElement.appendChild(ZauberNichtBeginn.writeXmlElement("zauberNichtZuBegin"));
	    	}
	    	
	    	xmlElement.appendChild(tmpElement);
    	}
    	return xmlElement;
    }

}
