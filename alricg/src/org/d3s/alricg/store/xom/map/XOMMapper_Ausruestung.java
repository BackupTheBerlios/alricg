package org.d3s.alricg.store.xom.map;

import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.charZusatz.Ausruestung;

class XOMMapper_Ausruestung extends XOMMapper_Gegenstand implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {

        super.map(xmlElement, charElement);

        // my mapping
        final Ausruestung ausruestung = (Ausruestung) charElement;
        xmlElement.setLocalName("gegenstand");

        // Behälter
        Element child = xmlElement.getFirstChildElement("istBehaelter");
        if (child != null) {
            final String value = child.getValue().toLowerCase();
            assert "true".equals(value) || "false".equals(value);

            ausruestung.setIstBehaelter(Boolean.parseBoolean(value));
        }

        // Haltbarkeit
        child = xmlElement.getFirstChildElement("haltbarkeit");
        if (child != null) {
            ausruestung.setHaltbarkeit(child.getValue());
        }
    }

    public void map(CharElement charElement, Element xmlElement) {

        super.map(charElement, xmlElement);

        // my mapping
        final Ausruestung ausruestung = (Ausruestung) charElement;
        xmlElement.setLocalName("gegenstand");

        // Behälter 
        if (!ausruestung.istBehaelter()) {
            final Element e = new Element("istBehaelter");
            e.appendChild("false");
            xmlElement.appendChild(e);
        }

        // Haltbarkeit
        String haltbarkeit = ausruestung.getHaltbarkeit();
        if (haltbarkeit != null && haltbarkeit.trim().length() > 0) {
            final Element e = new Element("haltbarkeit");
            e.appendChild(haltbarkeit);
            xmlElement.appendChild(e);
        }
    }

}
