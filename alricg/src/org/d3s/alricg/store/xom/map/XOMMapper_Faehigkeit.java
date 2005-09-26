package org.d3s.alricg.store.xom.map;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Eigenschaft;
import org.d3s.alricg.charKomponenten.Faehigkeit;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.prozessor.FormelSammlung;
import org.d3s.alricg.store.DataStore;
import org.d3s.alricg.store.FactoryFinder;

abstract class XOMMapper_Faehigkeit extends XOMMapper_CharElement implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final Faehigkeit faehigkeit = (Faehigkeit) charElement;

        // Auslesen der Eigenschaften, auf die eine Probe abgelegt wird
        final Element wurf = xmlElement.getFirstChildElement("probenWurf");
        final DataStore data = FactoryFinder.find().getData();
        final Eigenschaft[] dreiE = new Eigenschaft[3];
        dreiE[0] = (Eigenschaft) data.getCharElement(wurf.getAttributeValue("eigen1"), CharKomponente.eigenschaft);
        dreiE[1] = (Eigenschaft) data.getCharElement(wurf.getAttributeValue("eigen2"), CharKomponente.eigenschaft);
        dreiE[2] = (Eigenschaft) data.getCharElement(wurf.getAttributeValue("eigen3"), CharKomponente.eigenschaft);
        faehigkeit.setDreiEigenschaften(dreiE);

        // Auslesen der KostenKlasse
        faehigkeit.setKostenKlasse(FormelSammlung.getKostenKlasseByValue(xmlElement.getAttributeValue("kostenKlasse")));

    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my Mapping
        final Faehigkeit faehigkeit = (Faehigkeit) charElement;
        final Eigenschaft[] eigenschaften = faehigkeit.get3Eigenschaften();

        // Schreiben der 3 Eigenschaften f�r die Proben
        Element e = new Element("probenWurf");
        e.addAttribute(new Attribute("eigen1", eigenschaften[0].getEigenschaftEnum().getValue()));
        e.addAttribute(new Attribute("eigen2", eigenschaften[1].getEigenschaftEnum().getValue()));
        e.addAttribute(new Attribute("eigen3", eigenschaften[2].getEigenschaftEnum().getValue()));
        xmlElement.appendChild(e);

        // Schreiben der KostenKlasse
        final String kostenKlasse = faehigkeit.getKostenKlasse().getValue();
        xmlElement.addAttribute(new Attribute("kostenKlasse", kostenKlasse));
    }

}
