/*
 * Created on 26.01.2005 / 16:48:15
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.CharKomponenten.CharZusatz;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.CharElement;
import org.d3s.alricg.CharKomponenten.Eigenschaften;
import org.d3s.alricg.CharKomponenten.Nachteil;
import org.d3s.alricg.CharKomponenten.Sonderfertigkeit;
import org.d3s.alricg.CharKomponenten.Talent;
import org.d3s.alricg.CharKomponenten.Vorteil;
import org.d3s.alricg.CharKomponenten.Zauber;


/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class DaemonenPakt extends CharElement {
	private String daemonenName;
	private int paktzuschlag;
	private int kosten;
	private Nachteil[] verbilligteNachteile;
	private Vorteil[] verbilligteVorteile;
	private Sonderfertigkeit[] verbilligteSonderf;
	private Talent[] verbilligteTalente;
	private Zauber[] verbilligteZauber;
	private Nachteil[] schlechteEigenschaften;
	private SchwarzeGabe[] schwarzeGaben;
	private Eigenschaften[] verbilligteEigenschaften;
	
	
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	// TODO implement
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	// TODO implement
    	return null;
    }
}
