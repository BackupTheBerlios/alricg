package org.d3s.alricg.store.xom.map;

import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Gabe;
import org.d3s.alricg.controller.ProgAdmin;

class XOMMapper_Gabe extends XOMMapper_Faehigkeit implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // My mapping
        final Gabe gabe = (Gabe) charElement;

        // Auslesen der Stufengrenzen
        Element current = xmlElement.getFirstChildElement("stufenGrenzen");
        try {
            if (current != null) {
                Attribute a = current.getAttribute("minStufe");
                if ( a!= null) {
                    gabe.setMinStufe(Integer.parseInt(a.getValue()));
                }
                a = current.getAttribute("maxStufe");
                if (a != null) {
                    gabe.setMaxStufe(Integer.parseInt(a.getValue()));
                }
            }
        } catch (NumberFormatException exc) {
            ProgAdmin.logger.log(Level.SEVERE, "String -> int failed", exc);
        }

    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);
        
        // my mapping
        final Gabe gabe = (Gabe) charElement;
        xmlElement.setLocalName("gabe");
        
        //schreiben der Stufengrenzen
        final int minStufe = gabe.getMinStufe();
        final int maxStufe = gabe.getMaxStufe();
        if ( minStufe != 1 || maxStufe != 1 ) {
            final Element e = new Element("stufenGrenzen");
            
            if ( minStufe != 1 ) {
                e.addAttribute(new Attribute("minStufe", Integer.toString(minStufe)));
            }
            if ( maxStufe != 1 ) {
                e.addAttribute(new Attribute("maxStufe", Integer.toString(maxStufe)));
            }
            xmlElement.appendChild(e);
        }
    }
}
