package org.d3s.alricg.store.xom.map;

import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.charZusatz.FkWaffe;
import org.d3s.alricg.controller.ProgAdmin;

class XOMMapper_FkWaffe extends XOMMapper_Waffe implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        FkWaffe waffe = (FkWaffe) charElement;

        Element e = xmlElement.getFirstChildElement("tp");

        // Auslesen der TP-Zusätze für Fernkampfwaffen
        if (e != null) {
            waffe.setReichweiteTpPlus(e.getAttributeValue("plus-reichweite"));
        }

        try {

            // Auslesen der weiteren Eigenschaften
            e = xmlElement.getFirstChildElement("eigenschaften");
            // Auslesen der benötigten Aktionen zum Laden
            if (e != null && e.getAttribute("laden") != null) {
                waffe.setLaden(Integer.parseInt(e.getAttributeValue("laden")));
            }

            // Auslesen der Reichweite
            if (e != null && e.getAttribute("reichweite") != null) {
                waffe.setReichweite(Integer.parseInt(e.getAttributeValue("reichweite")));
            }

        } catch (NumberFormatException exc) {
            ProgAdmin.logger.log(Level.SEVERE, "String -> int failed", exc);
        }
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(xmlElement, charElement);

        // my mapping
        FkWaffe waffe = (FkWaffe) charElement;
        xmlElement.setLocalName("fkWaffe");

        Element e = xmlElement.getFirstChildElement("tp");

        // Schreiben der "Schwelle" für den KK -Zuschlag durch Reichweite
        String reichweiteTpPlus = waffe.getReichweiteTpPlus();
        if (reichweiteTpPlus != null) {
            if (e == null) {
                e = new Element("tp");
                xmlElement.appendChild(e);
            }
            e.addAttribute(new Attribute("plus-reichweite", reichweiteTpPlus));
        }

        e = xmlElement.getFirstChildElement("eigenschaften");

        // Schreiben wie lange zum Laden benötigt wird
        int laden = waffe.getLaden();
        if (laden != CharElement.KEIN_WERT) {
            if (e == null) {
                e = new Element("eigenschaften");
                xmlElement.appendChild(e);
            }
            e.addAttribute(new Attribute("laden", Integer.toString(laden)));
        }

        // Schreiben der Reichweite
        int reichweite = waffe.getReichweite();
        if (reichweite != CharElement.KEIN_WERT) {
            if (e == null) {
                e = new Element("eigenschaften");
                xmlElement.appendChild(e);
            }
            e.addAttribute(new Attribute("reichweite", Integer.toString(reichweite)));
        }
    }

}
