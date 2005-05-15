package org.d3s.alricg.store.xom.map;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.charZusatz.Fahrzeug;

class XOMMapper_Fahrzeug extends XOMMapper_Gegenstand implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {

        super.map(xmlElement, charElement);

        // my mapping
        final Fahrzeug fahrzeug = (Fahrzeug) charElement;

        // Aussehen
        Element child = xmlElement.getFirstChildElement("aussehen");
        if (child != null) {
            fahrzeug.setAussehen(child.getValue());
        }

        // Fahrzeugart
        Attribute a = xmlElement.getAttribute("fahrzeugArt");
        if (a != null) {
            fahrzeug.setTyp(a.getValue());
        }
    }

    public void map(CharElement charElement, Element xmlElement) {
        
        super.map(charElement, xmlElement);

        // my mapping
        final Fahrzeug fahrzeug = (Fahrzeug) charElement;
        xmlElement.setLocalName("fahrzeug");

        // Aussehen
        String sValue = fahrzeug.getAussehen();
        if (sValue != null && sValue.trim().length() > 0) {
            final Element e = new Element("aussehen");
            e.appendChild(sValue);
            xmlElement.appendChild(e);
        }

        // Typ/Art des Fahrzeugs
        sValue = fahrzeug.getTyp();
        if (sValue != null && sValue.trim().length() > 0) {
            xmlElement.addAttribute(new Attribute("fahrzeugArt", sValue));
        }
    }

}
