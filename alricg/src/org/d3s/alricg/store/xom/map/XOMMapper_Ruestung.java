package org.d3s.alricg.store.xom.map;

import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.charZusatz.Ruestung;
import org.d3s.alricg.controller.ProgAdmin;

class XOMMapper_Ruestung extends XOMMapper_Gegenstand implements XOMMapper {

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        final Ruestung ruestung = (Ruestung) charElement;

        try {
            // Zonen
            Element child = xmlElement.getFirstChildElement("zonen");
            if (child != null) {
                ruestung.setZoneKo(Integer.parseInt(child.getAttributeValue("ko")));
                ruestung.setZoneBr(Integer.parseInt(child.getAttributeValue("br")));
                ruestung.setZoneRue(Integer.parseInt(child.getAttributeValue("rue")));
                ruestung.setZoneBa(Integer.parseInt(child.getAttributeValue("ba")));
                ruestung.setZoneLa(Integer.parseInt(child.getAttributeValue("la")));
                ruestung.setZoneRa(Integer.parseInt(child.getAttributeValue("ra")));
                ruestung.setZoneLb(Integer.parseInt(child.getAttributeValue("lb")));
                ruestung.setZoneRb(Integer.parseInt(child.getAttributeValue("rb")));
                ruestung.setZoneGes(Integer.parseInt(child.getAttributeValue("ges")));
            }

            // RS und BE
            ruestung.setGRs(Integer.parseInt(xmlElement.getFirstChildElement("gRs").getValue()));
            ruestung.setGBe(Integer.parseInt(xmlElement.getFirstChildElement("gBe").getValue()));

        } catch (NumberFormatException nfe) {
            ProgAdmin.logger.log(Level.SEVERE, "String -> int failed", nfe);
        }

    }

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final Ruestung ruestung = (Ruestung) charElement;
        xmlElement.setLocalName("ruestung");

        // ZonenRs
        if (ruestung.getZoneKo() != CharElement.KEIN_WERT) {
            final Element e = new Element("zonen");
            e.addAttribute(new Attribute("ko", Integer.toString(ruestung.getZoneKo())));
            e.addAttribute(new Attribute("br", Integer.toString(ruestung.getZoneBr())));
            e.addAttribute(new Attribute("rue", Integer.toString(ruestung.getZoneRue())));
            e.addAttribute(new Attribute("ba", Integer.toString(ruestung.getZoneBa())));
            e.addAttribute(new Attribute("la", Integer.toString(ruestung.getZoneLa())));
            e.addAttribute(new Attribute("ra", Integer.toString(ruestung.getZoneRa())));
            e.addAttribute(new Attribute("lb", Integer.toString(ruestung.getZoneLb())));
            e.addAttribute(new Attribute("rb", Integer.toString(ruestung.getZoneRb())));
            e.addAttribute(new Attribute("ges", Integer.toString(ruestung.getZoneGes())));
            xmlElement.appendChild(e);
        }

        // RS
        Element e = new Element("gRs");
        e.appendChild(Integer.toString(ruestung.getGRs()));
        xmlElement.appendChild(e);

        // BE
        e = new Element("gBe");
        e.appendChild(Integer.toString(ruestung.getGBe()));
        xmlElement.appendChild(e);
    }

}
