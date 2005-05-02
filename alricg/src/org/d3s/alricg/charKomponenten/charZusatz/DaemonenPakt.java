/*
 * Created on 26.01.2005 / 16:48:15
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.charZusatz;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.links.IdLinkList;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;


/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class DaemonenPakt extends CharElement {
	private String daemonenName;
	private int paktzuschlag;
	private int kosten;
	private IdLinkList verbilligteNachteile;
	private IdLinkList verbilligteVorteile;
	private IdLinkList verbilligteSonderf;
	private IdLinkList verbilligteTalente;
	private IdLinkList verbilligteZauber;
	private IdLinkList zauberMerkmal;
	private IdLinkList schlechteEigenschaften;
	private IdLinkList schwarzeGaben;
	private IdLinkList verbilligteEigenschaften;
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#getCharKomponente()
	 */
	public CharKomponente getCharKomponente() {
		return CharKomponente.daemonenPakt;
	}
	
	/**
	 * Konstruktur; id beginnt mit "DAE-" für DaemonenPakt
	 * @param id Systemweit eindeutige id
	 */
	public DaemonenPakt(String id) {
		setId(id);
	}
	
	/**
	 * @return Liefert das Attribut daemonenName.
	 */
	public String getDaemonenName() {
		return daemonenName;
	}
	/**
	 * @param daemonenName Setzt das Attribut daemonenName.
	 */
	public void setDaemonenName(String daemonenName) {
		this.daemonenName = daemonenName;
	}
	/**
	 * @return Liefert das Attribut kosten.
	 */
	public int getKosten() {
		return kosten;
	}
	/**
	 * @param kosten Setzt das Attribut kosten.
	 */
	public void setKosten(int kosten) {
		this.kosten = kosten;
	}
	/**
	 * @return Liefert das Attribut paktzuschlag.
	 */
	public int getPaktzuschlag() {
		return paktzuschlag;
	}
	/**
	 * @param paktzuschlag Setzt das Attribut paktzuschlag.
	 */
	public void setPaktzuschlag(int paktzuschlag) {
		this.paktzuschlag = paktzuschlag;
	}
	/**
	 * @return Liefert das Attribut schlechteEigenschaften.
	 */
	public IdLinkList getSchlechteEigenschaften() {
		return schlechteEigenschaften;
	}
	/**
	 * @param schlechteEigenschaften Setzt das Attribut schlechteEigenschaften.
	 */
	public void setSchlechteEigenschaften(IdLinkList schlechteEigenschaften) {
		this.schlechteEigenschaften = schlechteEigenschaften;
	}
	/**
	 * @return Liefert das Attribut schwarzeGaben.
	 */
	public IdLinkList getSchwarzeGaben() {
		return schwarzeGaben;
	}
	/**
	 * @param schwarzeGaben Setzt das Attribut schwarzeGaben.
	 */
	public void setSchwarzeGaben(IdLinkList schwarzeGaben) {
		this.schwarzeGaben = schwarzeGaben;
	}
	/**
	 * @return Liefert das Attribut verbilligteEigenschaften.
	 */
	public IdLinkList getVerbilligteEigenschaften() {
		return verbilligteEigenschaften;
	}
	/**
	 * @param verbilligteEigenschaften Setzt das Attribut verbilligteEigenschaften.
	 */
	public void setVerbilligteEigenschaften(IdLinkList verbilligteEigenschaften) {
		this.verbilligteEigenschaften = verbilligteEigenschaften;
	}
	/**
	 * @return Liefert das Attribut verbilligteNachteile.
	 */
	public IdLinkList getVerbilligteNachteile() {
		return verbilligteNachteile;
	}
	/**
	 * @param verbilligteNachteile Setzt das Attribut verbilligteNachteile.
	 */
	public void setVerbilligteNachteile(IdLinkList verbilligteNachteile) {
		this.verbilligteNachteile = verbilligteNachteile;
	}
	/**
	 * @return Liefert das Attribut verbilligteSonderf.
	 */
	public IdLinkList getVerbilligteSonderf() {
		return verbilligteSonderf;
	}
	/**
	 * @param verbilligteSonderf Setzt das Attribut verbilligteSonderf.
	 */
	public void setVerbilligteSonderf(IdLinkList verbilligteSonderf) {
		this.verbilligteSonderf = verbilligteSonderf;
	}
	/**
	 * @return Liefert das Attribut verbilligteTalente.
	 */
	public IdLinkList getVerbilligteTalente() {
		return verbilligteTalente;
	}
	/**
	 * @param verbilligteTalente Setzt das Attribut verbilligteTalente.
	 */
	public void setVerbilligteTalente(IdLinkList verbilligteTalente) {
		this.verbilligteTalente = verbilligteTalente;
	}
	/**
	 * @return Liefert das Attribut verbilligteVorteile.
	 */
	public IdLinkList getVerbilligteVorteile() {
		return verbilligteVorteile;
	}
	/**
	 * @param verbilligteVorteile Setzt das Attribut verbilligteVorteile.
	 */
	public void setVerbilligteVorteile(IdLinkList verbilligteVorteile) {
		this.verbilligteVorteile = verbilligteVorteile;
	}
	/**
	 * @return Liefert das Attribut verbilligteZauber.
	 */
	public IdLinkList getVerbilligteZauber() {
		return verbilligteZauber;
	}
	/**
	 * @param verbilligteZauber Setzt das Attribut verbilligteZauber.
	 */
	public void setVerbilligteZauber(IdLinkList verbilligteZauber) {
		this.verbilligteZauber = verbilligteZauber;
	}
	/**
	 * @return Liefert das Attribut zauberMerkmal.
	 */
	public IdLinkList getZauberMerkmal() {
		return zauberMerkmal;
	}
	/**
	 * @param zauberMerkmal Setzt das Attribut zauberMerkmal.
	 */
	public void setZauberMerkmal(IdLinkList zauberMerkmal) {
		this.zauberMerkmal = zauberMerkmal;
	}
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	Elements tmpElements;
    	
    	// Auslesen des Namen des Dämons
    	daemonenName = xmlElement.getFirstChildElement("daemon").getAttributeValue("name");
    
    	try {
	    	// Auslesen des Paktzuschlages
	    	paktzuschlag = Integer.parseInt(xmlElement.getFirstChildElement("daemon")
	    											  .getAttributeValue("paktzuschlag"));
	    	// Auslesen der Kosten für diesen Pakt
	    	kosten = Integer.parseInt(xmlElement.getFirstChildElement("daemon")
					  							.getAttributeValue("kosten"));
    	} catch (NumberFormatException exc) {
    		// TODO bessere Fehlermeldung
    		ProgAdmin.logger.severe("Konnte String nicht in int umwandeln: " + exc.toString());
    	}
    	
    	// Auslesen der verbilligten Eigenschaften
    	if ( xmlElement.getFirstChildElement("verbilligteEigenschaften") != null ) {
    		verbilligteEigenschaften = new IdLinkList(this);
    		verbilligteEigenschaften.loadXmlElement(
    				xmlElement.getFirstChildElement("verbilligteEigenschaften")		
    			);
    	}
    	
    	// Auslesen der verbilligten Vorteile
    	if ( xmlElement.getFirstChildElement("verbilligteVorteile") != null ) {
    		verbilligteVorteile = new IdLinkList(this);
    		verbilligteVorteile.loadXmlElement(
    				xmlElement.getFirstChildElement("verbilligteVorteile")		
    			);
    	}
    	
    	// Auslesen der verbilligten Sonderfertigkeiten
    	if ( xmlElement.getFirstChildElement("verbilligteSonderf") != null ) {
    		verbilligteSonderf = new IdLinkList(this);
    		verbilligteSonderf.loadXmlElement(
    				xmlElement.getFirstChildElement("verbilligteSonderf")		
    			);
    	}
    	
    	// Auslesen der verbilligten Talente
    	if ( xmlElement.getFirstChildElement("verbilligteTalente") != null ) {
    		verbilligteTalente = new IdLinkList(this);
    		verbilligteTalente.loadXmlElement(
    				xmlElement.getFirstChildElement("verbilligteTalente")		
    			);
    	}
    	
    	// Auslesen der verbilligten Zauber
    	if ( xmlElement.getFirstChildElement("verbilligteZauber") != null ) {
    		verbilligteZauber = new IdLinkList(this);
    		verbilligteZauber.loadXmlElement(
    				xmlElement.getFirstChildElement("verbilligteZauber")		
    			);
    	}
    	
    	// Auslesen der Zauber Merkmale
    	if ( xmlElement.getFirstChildElement("zauberMerkmal") != null ) {
    		zauberMerkmal = new IdLinkList(this);
    		zauberMerkmal.loadXmlElement(
    				xmlElement.getFirstChildElement("zauberMerkmal")		
    			);
    	}
    	
    	// Auslesen der schlechten Eigenschaften
    	if ( xmlElement.getFirstChildElement("schlechteEigenschaften") != null ) {
    		schlechteEigenschaften = new IdLinkList(this);
    		schlechteEigenschaften.loadXmlElement(
    				xmlElement.getFirstChildElement("schlechteEigenschaften")		
    			);
    	}
    	
    	// Auslesen der schwarzen Gaben
    	if ( xmlElement.getFirstChildElement("schwarzeGaben") != null ) {
    		schwarzeGaben = new IdLinkList(this);
    		schwarzeGaben.loadXmlElement(
    				xmlElement.getFirstChildElement("schwarzeGaben")		
    			);
    	}
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	xmlElement.setLocalName("pakt");
    	
    	// Schreiben des Dämonennames, paktzuschlag und kosten
    	tmpElement = new Element("daemon");
    	tmpElement.addAttribute(new Attribute("name", daemonenName));
    	tmpElement.addAttribute(new Attribute("paktzuschlag", Integer.toString(paktzuschlag)));
    	tmpElement.addAttribute(new Attribute("kosten", Integer.toString(kosten)));
    	xmlElement.appendChild(tmpElement);
    	
    	// Schreiben der verbilligten Eigenschaften
    	xmlElement.appendChild(verbilligteEigenschaften
    						.writeXmlElement("verbilligteEigenschaften"));
    	
    	// Schreiben der verbilligten Vorteile
    	xmlElement.appendChild(verbilligteVorteile.writeXmlElement("verbilligteVorteile"));
    	
    	// Schreiben der verbilligten SF
    	xmlElement.appendChild(verbilligteSonderf.writeXmlElement("verbilligteSonderf"));
    	
    	// Schreiben der verbilligten Talente
    	xmlElement.appendChild(verbilligteTalente.writeXmlElement("verbilligteTalente"));
    	
    	// Schreiben der verbilligten Zauber
    	xmlElement.appendChild(verbilligteZauber.writeXmlElement("verbilligteZauber"));
    	
    	// Schreiben der Zauber Merkmale
    	xmlElement.appendChild(zauberMerkmal.writeXmlElement("zauberMerkmal"));
    	
    	// Schreiben der schlechten Eigenschaften
    	xmlElement.appendChild(schlechteEigenschaften.writeXmlElement("schlechteEigenschaften"));
    	
    	// Schreiben der Zauber Merkmale
    	xmlElement.appendChild(schwarzeGaben.writeXmlElement("schwarzeGaben"));

    	return xmlElement;
    }
}
