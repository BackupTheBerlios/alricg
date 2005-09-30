/*
 * Created on 23.06.2005 / 15:16:17
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */

package org.d3s.alricg.store.xom.map;

import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.SchriftSprache;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.prozessor.FormelSammlung;

abstract class XOMMapper_SchriftSprache extends XOMMapper_CharElement implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final SchriftSprache schriftSprache = (SchriftSprache) charElement;

        // Auslesen der Kostenklasse der Schrift/ Sprache
        Element current = xmlElement.getFirstChildElement("daten");
        String attVal = current.getAttributeValue("kostenKlasse");
        schriftSprache.setKostenKlasse(FormelSammlung.getKostenKlasseByValue(attVal));

        // Auslesen der Kompläxität der Schrift/ Sprache
        try {
            schriftSprache.setKomplexitaet(Integer.parseInt(current.getAttributeValue("komplexitaet")));
        } catch (NumberFormatException exc) {
            ProgAdmin.logger.log(Level.SEVERE, "String -> int failed", exc);
        }
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        final SchriftSprache schriftSprache = (SchriftSprache) charElement;

        // Schreiben der Kostenklasse
        final Element e = new Element("daten");
        xmlElement.appendChild(e);
        e.addAttribute(new Attribute("kostenKlasse", schriftSprache.getKostenKlasse().getValue()));

        // Schreiben der Komplexität
        e.addAttribute(new Attribute("komplexitaet", Integer.toString(schriftSprache.getKomplexitaet())));
    }

}
