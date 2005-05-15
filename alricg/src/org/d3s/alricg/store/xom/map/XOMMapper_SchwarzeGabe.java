package org.d3s.alricg.store.xom.map;

import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.charZusatz.SchwarzeGabe;
import org.d3s.alricg.controller.ProgAdmin;

class XOMMapper_SchwarzeGabe extends XOMMapper_CharElement implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final SchwarzeGabe schwarzeGabe = (SchwarzeGabe) charElement;

        try {
            // Stufengrenzen auslesen
            Element current = xmlElement.getFirstChildElement("stufenGrenzen");
            if (current != null) {
                schwarzeGabe.setMinStufe(Integer.parseInt(current.getAttributeValue("minStufe")));
                schwarzeGabe.setMaxStufe(Integer.parseInt(current.getAttributeValue("maxStufe")));
            }
            schwarzeGabe.setKosten(Integer.parseInt(xmlElement.getAttributeValue("kosten")));
        } catch (NumberFormatException exc) {
            ProgAdmin.logger.log(Level.SEVERE, "String -> int failed", exc);
        }
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        final SchwarzeGabe schwarzeGabe = (SchwarzeGabe) charElement;
        xmlElement.setLocalName("gabe");

        // Schreiben der Stufengrenzen
        final int minStufe = schwarzeGabe.getMinStufe();
        final int maxStufe = schwarzeGabe.getMaxStufe();
        if (minStufe != CharElement.KEIN_WERT || maxStufe != CharElement.KEIN_WERT) {
            final Element e = new Element("stufenGrenzen");
            if (minStufe != CharElement.KEIN_WERT) {
                e.addAttribute(new Attribute("minStufe", Integer.toString(minStufe)));
            }
            if (maxStufe != CharElement.KEIN_WERT) {
                e.addAttribute(new Attribute("maxStufe", Integer.toString(maxStufe)));
            }
            xmlElement.appendChild(e);
        }

        // Schreiben der Kosten
        xmlElement.addAttribute(new Attribute("kosten", Integer.toString(schwarzeGabe.getKosten())));

    }

}
