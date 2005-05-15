/*
 * Created 22. Dezember 2004 / 13:07:48
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.links.Auswahl;
import org.d3s.alricg.charKomponenten.links.AuswahlAusruestung;
import org.d3s.alricg.charKomponenten.links.IdLinkList;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.controller.CharKomponente;

/**
 * <b>Beschreibung: </b> <br>
 * TODO Beschreibung einfügen
 * 
 * @author V.Strelow
 */
public class Kultur extends Herkunft {
    private IdLinkList professionMoeglich;
    private IdLinkList professionUeblich;
    private Auswahl muttersprache;
    private Auswahl zweitsprache;
    private Auswahl sprachen;
    private Auswahl schriften;
    private AuswahlAusruestung ausruestung;
    private RegionVolk regionVolk;

    //private Kultur varianteVon; 
    
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#getCharKomponente()
	 */
	public CharKomponente getCharKomponente() {
		return CharKomponente.kultur;
	}

	/**
	 * Konstruktur; id beginnt mit "KUL-" für Kultur
	 * @param id Systemweit eindeutige id
	 */
	public Kultur(String id) {
		setId(id);
	}
    
    /**
     * @return Liefert das Attribut ausruestung.
     */
    public AuswahlAusruestung getAusruestung() {
        return ausruestung;
    }

    /**
     * @return Liefert das Attribut muttersprache.
     */
    public Auswahl getMuttersprache() {
        return muttersprache;
    }

    /**
     * @return Liefert das Attribut professionMoeglich.
     */
    public IdLinkList getProfessionMoeglich() {
        return professionMoeglich;
    }

    /**
     * @return Liefert das Attribut professionUeblich.
     */
    public IdLinkList getProfessionUeblich() {
        return professionUeblich;
    }

    /**
     * @return Liefert das Attribut schriften.
     */
    public Auswahl getSchriften() {
        return schriften;
    }

    /**
     * @return Liefert das Attribut sprachen.
     */
    public Auswahl getSprachen() {
        return sprachen;
    }

    /**
     * @return Liefert das Attribut zweitsprache.
     */
    public Auswahl getZweitsprache() {
        return zweitsprache;
    }
    
	/**
	 * @return Liefert das Attribut regionVolk.
	 */
	public RegionVolk getRegionVolk() {
		return regionVolk;
	}
	/**
	 * @param regionVolk Setzt das Attribut regionVolk.
	 */
	public void setRegionVolk(RegionVolk regionVolk) {
		this.regionVolk = regionVolk;
	}
	/**
	 * @param ausruestung Setzt das Attribut ausruestung.
	 */
	public void setAusruestung(AuswahlAusruestung ausruestung) {
		this.ausruestung = ausruestung;
	}
	/**
	 * @param muttersprache Setzt das Attribut muttersprache.
	 */
	public void setMuttersprache(Auswahl muttersprache) {
		this.muttersprache = muttersprache;
	}
	/**
	 * @param professionMoeglich Setzt das Attribut professionMoeglich.
	 */
	public void setProfessionMoeglich(IdLinkList professionMoeglich) {
		this.professionMoeglich = professionMoeglich;
	}
	/**
	 * @param professionUeblich Setzt das Attribut professionUeblich.
	 */
	public void setProfessionUeblich(IdLinkList professionUeblich) {
		this.professionUeblich = professionUeblich;
	}
	/**
	 * @param schriften Setzt das Attribut schriften.
	 */
	public void setSchriften(Auswahl schriften) {
		this.schriften = schriften;
	}
	/**
	 * @param sprachen Setzt das Attribut sprachen.
	 */
	public void setSprachen(Auswahl sprachen) {
		this.sprachen = sprachen;
	}
	/**
	 * @param zweitsprache Setzt das Attribut zweitsprache.
	 */
	public void setZweitsprache(Auswahl zweitsprache) {
		this.zweitsprache = zweitsprache;
	}
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	Elements tmpElements;
    	
    	// Auslesen der Region
    	if ( xmlElement.getFirstChildElement("region") != null ) {
    		regionVolk = (RegionVolk) ProgAdmin.data.getCharElement(
    				xmlElement.getFirstChildElement("region").getValue(),
    				CharKomponente.region
    			);
    	}
    	
    	// Auslesen der üblichen Professionen
    	if ( xmlElement.getFirstChildElement("professionUeblich") != null ) {
    		professionUeblich = new IdLinkList(this);
    		professionUeblich.loadXmlElement(xmlElement
    				.getFirstChildElement("professionUeblich"));
    	}
    	
    	// Auslesen der möglichen Professionen
    	if ( xmlElement.getFirstChildElement("professionMoeglich") != null ) {
    		professionMoeglich = new IdLinkList(this);
    		professionMoeglich.loadXmlElement(xmlElement
    				.getFirstChildElement("professionMoeglich"));
    	}
    	
    	// Auslesen der Muttersprache
    	muttersprache = new Auswahl(this);
    	muttersprache.loadXmlElement(xmlElement.getFirstChildElement("muttersprache"));
    	
    	// Auslesen der Zweitsprache
		if ( xmlElement.getFirstChildElement("zweitsprache") != null ) {
			zweitsprache = new Auswahl(this);
			zweitsprache.loadXmlElement(xmlElement.getFirstChildElement("zweitsprache"));
		}
		
		// Auslesen sonstiger Sprachen
		if ( xmlElement.getFirstChildElement("sprachen") != null ) {
			sprachen = new Auswahl(this);
			sprachen.loadXmlElement(xmlElement.getFirstChildElement("sprachen"));
		}
		
		// Auslesen er Schriften
		if ( xmlElement.getFirstChildElement("schriften") != null ) {
			schriften = new Auswahl(this);
			schriften.loadXmlElement(xmlElement.getFirstChildElement("schriften"));
		}
		
		// Auslesen der Ausrüstung
		if ( xmlElement.getFirstChildElement("ausruestung") != null ) {
			ausruestung = new AuswahlAusruestung(this);
			ausruestung.loadXmlElement(xmlElement.getFirstChildElement("ausruestung"));
		}
		
		
		// Auslesen der Variante (gehört nach Schema eigentlich zur Herkunft)
		if ( xmlElement.getFirstChildElement("varianteVon") !=  null ) {
			varianteVon = (Kultur) ProgAdmin.data.getCharElement(
	    			xmlElement.getFirstChildElement("varianteVon").getValue(),
	    			CharKomponente.kultur
	    		);
		}
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	int idx; 
    	
    	xmlElement.setLocalName("kultur");
    	
    	// Schreiben der Region
    	if ( regionVolk != null) {
    		tmpElement = new Element("region");
    		tmpElement.appendChild(regionVolk.getId());
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben der üblichen Professionen
    	if ( professionUeblich != null ) {
    		xmlElement.appendChild(professionUeblich.writeXmlElement("professionUeblich"));
    	}
    	
    	// Schreiben der möglichen Professionen
    	if ( professionMoeglich != null ) {
    		xmlElement.appendChild(professionMoeglich.writeXmlElement("professionMoeglich"));
    	}
    	
    	// Schreiben der Muttersprache
    	if ( muttersprache != null ) {
    		xmlElement.appendChild(muttersprache.writeXmlElement("muttersprache"));
    	}
    	
    	// Schreiben der Zweitsprache
    	if ( zweitsprache != null ) {
    		xmlElement.appendChild(zweitsprache.writeXmlElement("zweitsprache"));
    	}
    	
    	// Schreiben der weiteren Sprachen
    	if ( sprachen != null ) {
    		xmlElement.appendChild(sprachen.writeXmlElement("sprachen"));
    	}
    	
    	// Schreiben der Schriften
    	if ( schriften != null ) {
    		xmlElement.appendChild(schriften.writeXmlElement("schriften"));
    	}
    	
    	// Schreiben der Schriften
    	if ( ausruestung != null ) {
    		xmlElement.appendChild(ausruestung.writeXmlElement("ausruestung"));
    	}
    	
    	// "varianteVon" schreiben (gehört nach Schema eigentlich zur Herkunft)
    	if ( this.varianteVon != null ) {
	    	// hierfür muß die richtige Position bestimmt werden: 
	    	idx = xmlElement.indexOf( xmlElement.getFirstChildElement("gp") );
	    	tmpElement = new Element("varianteVon");
	    	tmpElement.appendChild(this.varianteVon.getId());
	    	
	    	// einfügen nach dem "gp" Element!
	    	xmlElement.insertChild(tmpElement, idx+1);
    	}
    	
		return xmlElement;
    }
}
