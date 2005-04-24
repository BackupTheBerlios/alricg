/*
 * Created 27. Dezember 2004 / 01:38:24
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;
import org.d3s.alricg.prozessor.SKT;
import org.d3s.alricg.prozessor.SKT.KostenKlasse;

/**
 * <b>Beschreibung:</b><br>
 * Oberklasse von Talenten und Zaubern, faßt Gemeinsamkeiten zusammen.
 * @author V.Strelow
 */
public abstract class Faehigkeit extends CharElement {
	// Die 3 Eigenschaften, auf das eine Probe abgelegt wird
	private Eigenschaft[] dreiEigenschaften = new Eigenschaft[3];
	private KostenKlasse kostenKlasse; // Die Kostenklasse für das Elememt
	
    /**
     * @see Klasse org.d3s.alricg.charKomponenten.Eigenschaften
	 * @return Liefert die drei Eigenschaften, auf die die Probe abgelegt werden muß.
	 */
	public Eigenschaft[] get3Eigenschaften() {
		return dreiEigenschaften;
	}
	
	public String get3EigenschaftenString() {
		return dreiEigenschaften[0].getEigenschaftEnum().getAbk()
				+ "/" 
				+ dreiEigenschaften[1].getEigenschaftEnum().getAbk()
				+ "/"
				+ dreiEigenschaften[2].getEigenschaftEnum().getAbk();
	}
	
    /**
	 * @return Liefert die Kosten-Klasse nach der SKT.
	 */
	public KostenKlasse getKostenKlasse() {
		return kostenKlasse;
	}

	/**
	 * @param dreiEigenschaften Setzt das Attribut dreiEigenschaften.
	 */
	public void setDreiEigenschaften(Eigenschaft[] dreiEigenschaften) {
		this.dreiEigenschaften = dreiEigenschaften;
	}
	/**
	 * @param kostenKlasse Setzt das Attribut kostenKlasse.
	 */
	public void setKostenKlasse(KostenKlasse kostenKlasse) {
		this.kostenKlasse = kostenKlasse;
	}
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
    	// Auslesen der Eigenschaften, auf die eine Probe abgelegt wird
    	dreiEigenschaften[0] = (Eigenschaft) ProgAdmin.charKompAdmin.getCharElement(
    			xmlElement.getFirstChildElement("probenWurf").getAttributeValue("eigen1"), 
    			CharKomponente.eigenschaft);
    	dreiEigenschaften[1] = (Eigenschaft) ProgAdmin.charKompAdmin.getCharElement(
    			xmlElement.getFirstChildElement("probenWurf").getAttributeValue("eigen2"), 
    			CharKomponente.eigenschaft);
    	dreiEigenschaften[2] = (Eigenschaft) ProgAdmin.charKompAdmin.getCharElement(
    			xmlElement.getFirstChildElement("probenWurf").getAttributeValue("eigen3"), 
    			CharKomponente.eigenschaft);

    	// Auslesen der KostenKlasse
    	kostenKlasse = SKT.getKostenKlasseByXmlValue(
    			xmlElement.getAttributeValue("kostenKlasse")
    		);

    }
    

    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	// Schreiben der 3 Eigenschaften für die Proben
    	tmpElement = new Element("probenWurf");
    	tmpElement.addAttribute(new Attribute(
    			"eigen1",
    			dreiEigenschaften[0].getEigenschaftEnum().getXmlValue())
    		);
    	tmpElement.addAttribute(new Attribute(
				"eigen2",
				dreiEigenschaften[1].getEigenschaftEnum().getXmlValue())
			);
    	tmpElement.addAttribute(new Attribute(
				"eigen3",
				dreiEigenschaften[2].getEigenschaftEnum().getXmlValue())
			);
    	xmlElement.appendChild(tmpElement);
    	
    	// Schreiben der KostenKlasse
    	xmlElement.addAttribute(new Attribute("kostenKlasse", kostenKlasse.getXmlValue()));
    	
    	return xmlElement;
    }
}
