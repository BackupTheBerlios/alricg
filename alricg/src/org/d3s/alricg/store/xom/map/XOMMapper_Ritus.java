package org.d3s.alricg.store.xom.map;

import java.util.logging.Level;

import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Gottheit;
import org.d3s.alricg.charKomponenten.Ritus;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.store.FactoryFinder;

abstract class XOMMapper_Ritus extends XOMMapper_CharElement implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final Ritus ritus = (Ritus) charElement;

        // Auslesen des Grades der Liturgie/ Ritual
        try {
            ritus.setGrad(Integer.parseInt(xmlElement.getFirstChildElement("grad").getValue()));
        } catch (NumberFormatException exc) {
            ProgAdmin.logger.log(Level.SEVERE, "String -> int failed", exc);
        }

        // Auslesen der AdditionsId
        ritus.setAdditionsId(xmlElement.getFirstChildElement("additionsId").getValue());

        // Auslesen der Gottheiten
        final Elements children = xmlElement.getChildElements("gottheit");
        final Gottheit[] gottheit = new Gottheit[children.size()];
        for (int i = 0; i < children.size(); i++) {
            final String val = children.get(i).getValue();
            gottheit[i] = (Gottheit) FactoryFinder.find().getData().getCharElement(val, CharKomponente.gottheit);
        }
        ritus.setGottheit(gottheit);
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        final Ritus ritus = (Ritus) charElement;

        // Schreiben des Grades der Liturgie/ Ritual
        Element e = new Element("grad");
        e.appendChild(Integer.toString(ritus.getGrad()));
        xmlElement.appendChild(e);
        
        // Schreiben der AdditionsID
        e = new Element("additionsId");
        e.appendChild(ritus.getAdditionsId());
        xmlElement.appendChild(e);
        
        // Schreiben der gottheit
        final Gottheit[] gottheit = ritus.getGottheit();
        for (int i = 0; i < gottheit.length; i++) {
            e = new Element("gottheit");
            e.appendChild(gottheit[i].getId());
            xmlElement.appendChild(e);
        }
    }
}
