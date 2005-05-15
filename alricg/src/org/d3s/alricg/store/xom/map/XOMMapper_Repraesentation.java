package org.d3s.alricg.store.xom.map;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Repraesentation;

class XOMMapper_Repraesentation extends XOMMapper_CharElement implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final Repraesentation repraesentation = (Repraesentation) charElement;

        // Auslesen, ob es eine "echte" Repräsentation ist
        final Element current = xmlElement.getFirstChildElement("isEchteRep");
        if (current != null) {
            assert current.getValue().equalsIgnoreCase("true") || current.getValue().equalsIgnoreCase("false");
            repraesentation.setEchteRep(current.getValue().equalsIgnoreCase("true"));
        }

        // Auslesen der Abkürzung
        repraesentation.setAbkuerzung(xmlElement.getAttributeValue("abkuerzung"));

    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        final Repraesentation repraesentation = (Repraesentation) charElement;
        xmlElement.setLocalName("repraesentation");

        // Schreiben ob es eine "echte" Repräsentation ist
        final Element e = new Element("isEchteRep");
        e.appendChild(Boolean.toString(repraesentation.isEchteRep()));
        xmlElement.appendChild(e);

        // Schreiben des Attributs Abkürzung
        xmlElement.addAttribute(new Attribute("abkuerzung", repraesentation.getAbkuerzung()));

    }

}
