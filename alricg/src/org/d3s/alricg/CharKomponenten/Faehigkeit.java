/*
 * Created 27. Dezember 2004 / 01:38:24
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.Controller.ProgAdmin;
import org.d3s.alricg.Controller.CharKompAdmin.CharKomponenten;
import org.d3s.alricg.Prozessor.SKT;
import org.d3s.alricg.Prozessor.SKT.KostenKlasse;

/**
 * <b>Beschreibung:</b><br>
 * Oberklasse von Talenten und Zaubern, faßt Gemeinsamkeiten zusammen.
 * @author V.Strelow
 */
public abstract class Faehigkeit extends CharElement {
	private Eigenschaft[] dreiEigenschaften = new Eigenschaft[3];
	private KostenKlasse kostenKlasse;
	
    /**
     * @see Klasse org.d3s.alricg.CharKomponenten.Eigenschaften
	 * @return Liefert die drei Eigenschaften, auf die die Probe abgelegt werden muß.
	 */
	public Eigenschaft[] getDreiEigenschaften() {
		return dreiEigenschaften;
	}
	
    /**
	 * @return Liefert die Kosten-Klasse nach der SKT.
	 */
	public KostenKlasse getKostenKlasse() {
		return kostenKlasse;
	}

    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
    	// Auslesen der Eigenschaften, auf die eine Probe abgelegt wird
    	dreiEigenschaften[0] = (Eigenschaft) ProgAdmin.charKompAdmin.getCharElement(
    			xmlElement.getFirstChildElement("probenWurf").getAttributeValue("eigen1"), 
    			CharKomponenten.eigenschaft);
    	dreiEigenschaften[1] = (Eigenschaft) ProgAdmin.charKompAdmin.getCharElement(
    			xmlElement.getFirstChildElement("probenWurf").getAttributeValue("eigen2"), 
    			CharKomponenten.eigenschaft);
    	dreiEigenschaften[2] = (Eigenschaft) ProgAdmin.charKompAdmin.getCharElement(
    			xmlElement.getFirstChildElement("probenWurf").getAttributeValue("eigen3"), 
    			CharKomponenten.eigenschaft);

    	// Auslesen der KostenKlasse
    	kostenKlasse = SKT.getKostenKlasseByXmlValue(
    			xmlElement.getAttributeValue("kostenKlasse")
    		);

    }
    

    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	
    	// Schreiben der 3 Eigenschaften für die Proben
    	tmpElement = new Element("probenWurf");
    	tmpElement.addAttribute(new Attribute(
    			"eigen1",
    			dreiEigenschaften[0].getEigenschaft().getXmlValue())
    		);
    	tmpElement.addAttribute(new Attribute(
				"eigen2",
				dreiEigenschaften[1].getEigenschaft().getXmlValue())
			);
    	tmpElement.addAttribute(new Attribute(
				"eigen3",
				dreiEigenschaften[2].getEigenschaft().getXmlValue())
			);
    	xmlElement.appendChild(tmpElement);
    	
    	// Schreiben der KostenKlasse
    	xmlElement.addAttribute(new Attribute("kostenKlasse", kostenKlasse.getXmlValue()));
    	
    	return xmlElement;
    }
}
