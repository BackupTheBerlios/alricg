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
import org.d3s.alricg.charKomponenten.charZusatz.DaemonenPakt;
import org.d3s.alricg.charKomponenten.links.IdLinkList;
import org.d3s.alricg.controller.ProgAdmin;

/**
 * <code>XOMMapper</code> für einen <code>Daemonenpakt</code>.
 * 
 * @see org.d3s.alricg.store.xom.map.XOMMapper
 * @see org.d3s.alricg.charKomponenten.charZusatz.DaemonenPakt
 * @author <a href="mailto:msturzen@mac.com>St. Martin</a>
 */
class XOMMapper_DaemonenPakt extends XOMMapper_CharElement implements XOMMapper {

    // @see org.d3s.alricg.store.xom.map.XOMMapper#map(nu.xom.Element, org.d3s.alricg.charKomponenten.CharElement)
    public void map(Element xmlElement, CharElement charElement) {

        // super mapping
        super.map(xmlElement, charElement);

        // my mapping
        final DaemonenPakt pakt = (DaemonenPakt) charElement;

        // Name des Dämons
        Element child = xmlElement.getFirstChildElement("daemon");
        pakt.setDaemonenName(child.getAttributeValue("name"));

        int intVal = 0;
        try {
            // Paktzuschlag
            intVal = Integer.parseInt(child.getAttributeValue("paktzuschlag"));
            pakt.setPaktzuschlag(intVal);

            // Paktkosten
            intVal = Integer.parseInt(child.getAttributeValue("kosten"));
            pakt.setKosten(intVal);
        } catch (NumberFormatException e) {
            ProgAdmin.logger.log(Level.SEVERE, "String -> int failed " + intVal, e);
        }

        // Verbilligte Eigenschaften
        child = xmlElement.getFirstChildElement("verbilligteEigenschaften");
        IdLinkList idLinks = mapChild(child, pakt);
        pakt.setVerbilligteEigenschaften(idLinks);

        // Verbilligte Vorteile
        child = xmlElement.getFirstChildElement("verbilligteVorteile");
        idLinks = mapChild(child, pakt);
        pakt.setVerbilligteVorteile(idLinks);

        // Verbilligte Sonderfertigkeiten
        child = xmlElement.getFirstChildElement("verbilligteSonderf");
        idLinks = mapChild(child, pakt);
        pakt.setVerbilligteSonderf(idLinks);

        // Verbilligte Talente
        child = xmlElement.getFirstChildElement("verbilligteTalente");
        idLinks = mapChild(child, pakt);
        pakt.setVerbilligteTalente(idLinks);

        // Verbilligte Zauber
        child = xmlElement.getFirstChildElement("verbilligteZauber");
        idLinks = mapChild(child, pakt);
        pakt.setVerbilligteZauber(idLinks);

        // Zauber Merkmale
        child = xmlElement.getFirstChildElement("zauberMerkmal");
        idLinks = mapChild(child, pakt);
        pakt.setZauberMerkmal(idLinks);

        // Schlechte Eigenschaften
        child = xmlElement.getFirstChildElement("schlechteEigenschaften");
        idLinks = mapChild(child, pakt);
        pakt.setSchlechteEigenschaften(idLinks);

        // Auslesen der schwarzen Gaben
        child = xmlElement.getFirstChildElement("schwarzeGaben");
        idLinks = mapChild(child, pakt);
        pakt.setSchwarzeGaben(idLinks);
    }

    // @see org.d3s.alricg.store.xom.map.XOMMapper#map(org.d3s.alricg.charKomponenten.CharElement, nu.xom.Element)
    public void map(CharElement charElement, Element xmlElement) {

        // super mapping
        super.map(charElement, xmlElement);

        // my mapping
        final DaemonenPakt pakt = (DaemonenPakt) charElement;
        xmlElement.setLocalName("pakt");

        // Dämonenname, Paktzuschlag, Paktkosten
        Element e = new Element("daemon");
        Attribute a = new Attribute("name", pakt.getDaemonenName());
        e.addAttribute(a);
        a = new Attribute("paktzuschlag", Integer.toString(pakt.getPaktzuschlag()));
        e.addAttribute(a);
        a = new Attribute("kosten", Integer.toString(pakt.getKosten()));
        e.addAttribute(a);
        xmlElement.appendChild(e);

        // Verbilligte Eigenschaften
        e = new Element("verbilligteEigenschaften");
        XOMMappingHelper.mapIdLinkList(pakt.getVerbilligteEigenschaften(), e);
        xmlElement.appendChild(e);

        // Verbilligte Vorteile
        e = new Element("verbilligteVorteile");
        XOMMappingHelper.mapIdLinkList(pakt.getVerbilligteVorteile(), e);
        xmlElement.appendChild(e);

        // Verbilligte Sonderfertigkeiten
        e = new Element("verbilligteSonderf");
        XOMMappingHelper.mapIdLinkList(pakt.getVerbilligteSonderf(), e);
        xmlElement.appendChild(e);

        // Verbilligte Talente
        e = new Element("verbilligteTalente");
        XOMMappingHelper.mapIdLinkList(pakt.getVerbilligteTalente(), e);
        xmlElement.appendChild(e);

        // Verbilligte Zauber
        e = new Element("verbilligteZauber");
        XOMMappingHelper.mapIdLinkList(pakt.getVerbilligteEigenschaften(), e);
        xmlElement.appendChild(e);

        // Zaubermerkmale
        e = new Element("zauberMerkmal");
        XOMMappingHelper.mapIdLinkList(pakt.getZauberMerkmal(), e);
        xmlElement.appendChild(e);

        // Schlechten Eigenschaften
        e = new Element("schlechteEigenschaften");
        XOMMappingHelper.mapIdLinkList(pakt.getSchlechteEigenschaften(), e);
        xmlElement.appendChild(e);

        // schwarze Gaben
        e = new Element("schwarzeGaben");
        XOMMappingHelper.mapIdLinkList(pakt.getSchwarzeGaben(), e);
        xmlElement.appendChild(e);
    }

    /**
     * Erstellt die Liste eine Liste mit besonderen/veränderten Eigenschaften und Kosten.
     * @param child Das <code>Element</code>, das die Art der Veränderungen beschreibt.
     * @param pakt Der Pakt, der die Eigenschaften und Kosten beeinflusst.
     * @return
     */
    private IdLinkList mapChild(Element child, DaemonenPakt pakt) {
        IdLinkList result = null;
        if (child != null) {
            result = new IdLinkList(pakt);
            XOMMappingHelper.mapIdLinkList(child, result);
        }
        return result;
    }
}
