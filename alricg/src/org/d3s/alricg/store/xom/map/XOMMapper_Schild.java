package org.d3s.alricg.store.xom.map;

import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.charZusatz.Schild;
import org.d3s.alricg.controller.ProgAdmin;

class XOMMapper_Schild extends XOMMapper_Gegenstand implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final Schild schild = (Schild) charElement;

        // Schild-Typ
        Element child = xmlElement.getFirstChildElement("typ");
        if (child != null) {
            schild.setTyp(child.getValue());
        }
        try {
            // Eigenschaften
            child = xmlElement.getFirstChildElement("eigenschaften");
            if (child != null) {

                // bf
                Attribute attr = child.getAttribute("bf");
                if (attr != null) {
                    schild.setBf(Integer.parseInt(attr.getValue()));
                }

                // ini
                attr = child.getAttribute("ini");
                if (attr != null) {
                    schild.setIni(Integer.parseInt(attr.getValue()));
                }
            }

            // WM
            child = xmlElement.getFirstChildElement("wm");
            if (child != null) {

                // at
                Attribute attr = child.getAttribute("at");
                if (attr != null) {
                    schild.setWmAT(Integer.parseInt(attr.getValue()));
                }

                // pa
                attr = child.getAttribute("pa");
                if (attr != null) {
                    schild.setWmPA(Integer.parseInt(attr.getValue()));
                }
            }
        } catch (NumberFormatException exc) {
            ProgAdmin.logger.log(Level.SEVERE, "string -> int failed ", exc);
        }
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        final Schild schild = (Schild) charElement;
        xmlElement.setLocalName("schild");

        // typ des Schildes Schreiben
        String typ = schild.getTyp();
        if (typ != null && typ.trim().length() > 0) {
            final Element e = new Element("typ");
            e.appendChild(typ.trim());
            xmlElement.appendChild(e);
        }

        // Eigenschaften
        final int bf = schild.getBf();
        final int ini = schild.getIni();
        if (bf != CharElement.KEIN_WERT || ini != CharElement.KEIN_WERT) {
            final Element e = new Element("eigenschaften");

            // bf
            if (bf != CharElement.KEIN_WERT) {
                e.addAttribute(new Attribute("bf", Integer.toString(bf)));
            }

            // ini
            if (ini != CharElement.KEIN_WERT) {
                e.addAttribute(new Attribute("ini", Integer.toString(ini)));
            }
            xmlElement.appendChild(e);
        }

        // Waffen Modis
        final int wmAT = schild.getWmAT();
        final int wmPA = schild.getWmPA();
        if (wmAT != CharElement.KEIN_WERT || wmPA != CharElement.KEIN_WERT) {
            final Element e = new Element("wm");

            // at
            if (wmAT != CharElement.KEIN_WERT) {
                e.addAttribute(new Attribute("at", Integer.toString(wmAT)));
            }

            // pa
            if (wmPA != CharElement.KEIN_WERT) {
                e.addAttribute(new Attribute("pa", Integer.toString(wmPA)));
            }
            xmlElement.appendChild(e);
        }
    }
}
