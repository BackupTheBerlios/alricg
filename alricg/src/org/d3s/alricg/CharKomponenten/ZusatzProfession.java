/*
 * Created 20. Januar 2005 / 16:04:34
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.Links.IdLinkList;
import org.d3s.alricg.Controller.ProgAdmin;

/**
 * <b>Beschreibung:</b><br>
 * Diese Klasse repr�sentiert "sp�te Professionen" (z.B. Kor-Geweihter) und
 * "zusatz-Professionen" (wie der Elfische Wanderer)
 * @author V.Strelow
 */
public class ZusatzProfession extends Profession {
	private IdLinkList professionMoeglich; // Ist dies leer, so sind alle m�glich
	private IdLinkList professionUeblich;
	private int apKosten; // GP Kosten durch Profession
	private boolean zusatzProf; //ansonsten sp�teProfession, "spaeteProfession" 
								//und "zusatzProfession" schlie�en sich aus!
	
	
	/**
	 * Konstruktur; id beginnt mit "ZPR-" f�r ZusatzProfession
	 * @param id Systemweit eindeutige id
	 */
	public ZusatzProfession(String id) {
		super(id);
	}
	
	/**
	 * @return Liefert das Attribut apKosten.
	 */
	public int getApKosten() {
		return apKosten;
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
	 * @return Liefert das Attribut zusatzProf.
	 */
	public boolean isZusatzProf() {
		return zusatzProf;
	}
	
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
    	// Auslesen der �blichen Professionen
    	if ( xmlElement.getFirstChildElement("professionUeblich") != null ) {
    		professionUeblich = new IdLinkList(this);
    		professionUeblich.loadXmlElement(xmlElement
    				.getFirstChildElement("professionUeblich"));
    	}
    	
    	// Auslesen der m�glichen Professionen
    	if ( xmlElement.getFirstChildElement("professionMoeglich") != null ) {
    		professionMoeglich = new IdLinkList(this);
    		professionMoeglich.loadXmlElement(xmlElement
    				.getFirstChildElement("professionMoeglich"));
    	}
    	
    	// Auslesen der AP-Kosten
    	if ( xmlElement.getAttribute("apKosten") != null ) {
	    	try {
	    		apKosten = Integer.parseInt(xmlElement.getAttributeValue("apKosten"));
	    	} catch (NumberFormatException ex) {
	    		// TODO Richtige Fehlermeldung
	    		ProgAdmin.logger.severe(ex.toString());
	    	}
    	} else {
    		apKosten = KEIN_WERT;
    	}
    	
    	// Auslesen der Art der Zusatzprofession
    	
    	// Sicherstellen des Wertebereichs
    	assert xmlElement.getAttributeValue("zusatzArt").equals("spaeteProf") ||
    			xmlElement.getAttributeValue("zusatzArt").equals("zusatzProf");
    	
    	if (xmlElement.getAttribute("zusatzArt").equals("spaeteProf")) {
    		zusatzProf = false;
    	} else { // ....equals("zusatzProf")
    		zusatzProf = true;
    	}
    }
    
    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	
    	// Schreiben der �blichen Professionen
    	if (professionUeblich != null) {
    		xmlElement.appendChild(professionUeblich.writeXmlElement("professionUeblich"));
    	}
    	
    	// Schreiben der m�glichen Professionen
    	if (professionMoeglich != null)  {
    		xmlElement.appendChild(professionMoeglich.writeXmlElement("professionMoeglich"));
    	}
    	
    	// Schreiben der AP-Kosten
    	if ( apKosten != KEIN_WERT ) {
    		xmlElement.addAttribute(new Attribute("apKosten", 
    											Integer.toString(apKosten)));
    	}
    	
    	// Die Art der Zusatzprofession schreiben
    	if (zusatzProf) {
    		xmlElement.addAttribute(new Attribute("zusatzArt", "zusatzProf"));
    	} else {
    		xmlElement.addAttribute(new Attribute("zusatzArt", "spaeteProf"));
    	}
    	
    	return xmlElement;
    }
}
