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
import org.d3s.alricg.charKomponenten.charZusatz.SimpelGegenstand;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.store.FactoryFinder;

class XOMMapper_SimpelGegenstand extends XOMMapper_Gegenstand implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {

        // super mapping
        super.map(xmlElement, charElement);

        // my mapping
        final SimpelGegenstand simpleDing = (SimpelGegenstand) charElement;

        // Anzahl (minOcc=0; maxOcc=1)
        Attribute attribute = xmlElement.getAttribute("anzahl");
        if (attribute != null) {
            try {
                simpleDing.setAnzahl(Integer.parseInt(attribute.getValue()));
            } catch (NumberFormatException e) {
                ProgAdmin.logger.log(Level.SEVERE, "String -> int failed " + attribute.getValue(), e);
            }
        }

        // Art (minOcc=1; maxOcc=1)
        String art = xmlElement.getAttributeValue("art");
        simpleDing.setArt(FactoryFinder.find().getData().getCharKompFromPrefix(art));

    }
    
    public void map(CharElement charElement, Element xmlElement) {

        // super mapping
        super.map(charElement, xmlElement);
        
        // my mapping
        final SimpelGegenstand simpleDing = (SimpelGegenstand) charElement;
        
        // TODO This is strange somehow!
        // xmlElement.setLocalName(tagName);
        
        // Schreiben der Anzahl
        final int anzahl = simpleDing.getAnzahl();
        if (anzahl != 1) {
            xmlElement.addAttribute(new Attribute("anzahl", Integer.toString(anzahl)));
        }
        
        // Schreiben der Art
        final CharKomponente art = simpleDing.getArt();
        xmlElement.addAttribute(new Attribute("art", art.getPrefix()));        
    }
}
