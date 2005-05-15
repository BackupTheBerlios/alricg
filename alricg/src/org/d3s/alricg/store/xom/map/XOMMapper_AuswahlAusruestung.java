package org.d3s.alricg.store.xom.map;

import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.Herkunft;
import org.d3s.alricg.charKomponenten.charZusatz.SimpelGegenstand;
import org.d3s.alricg.charKomponenten.links.AuswahlAusruestung;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.AuswahlAusruestung.HilfsAuswahl;
import org.d3s.alricg.controller.ProgAdmin;

class XOMMapper_AuswahlAusruestung {

    public void map(Element xmlElement, AuswahlAusruestung ausruestung) {

        // Auslesen der festen Gegenstände
        Element current = xmlElement.getFirstChildElement("fest");
        if (current != null) {
            final HilfsAuswahl festeAuswahl = ausruestung.new HilfsAuswahl();
            map(current, festeAuswahl, ausruestung.getHerkunft());
            ausruestung.setFesteAuswahl(festeAuswahl);
        }

        // Auslesen der Auswahl
        final Elements children = xmlElement.getChildElements("auswahl");
        HilfsAuswahl[] variableAuswahl = new HilfsAuswahl[children.size()];
        for (int i = 0; i < variableAuswahl.length; i++) {
            variableAuswahl[i] = ausruestung.new HilfsAuswahl();
            map(children.get(i), variableAuswahl[i], ausruestung.getHerkunft());
        }
        ausruestung.setVariableAuswahl(variableAuswahl);

    }

    public void map(AuswahlAusruestung ausruestung, Element xmlElement) {

        // Schreiben der "festen" Elemente
        HilfsAuswahl festeAuswahl = ausruestung.getFesteAuswahl();
        if (festeAuswahl != null) {
            final Element e = new Element("fest");
            map(festeAuswahl, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der "wählbaren" Elemente
        HilfsAuswahl[] variableAuswahl = ausruestung.getVariableAuswahl();
        for (int i = 0; i < variableAuswahl.length; i++) {
            Element e = new Element("auswahl");
            map(variableAuswahl[i], e);
            xmlElement.appendChild(e);
        }
    }

    private void map(Element xmlElement, HilfsAuswahl hilfsauswahl, Herkunft herkunft) {

        // Die Anzahl auslesen, falls angegeben?
        if (xmlElement.getAttribute("anzahl") != null) {
            try {
                hilfsauswahl.setAnzahl(Integer.parseInt(xmlElement.getAttributeValue("anzahl")));
            } catch (NumberFormatException exc) {
                ProgAdmin.logger.log(Level.SEVERE, "String -> umwandeln failed", exc);
            }
        }

        // Auslesen der Gegenstände die verlinkt werden!
        Elements children = xmlElement.getChildElements("ausruestungLink");
        final IdLink[] links = new IdLink[children.size()];
        for (int i = 0; i < children.size(); i++) {

            // ACHTUNG Die "null" an der Stelle könnte zu Problemen führen!!
            links[i] = new IdLink(herkunft, null);
            XOMMappingHelper.mapIdLink(children.get(i), links[i]);
        }
        hilfsauswahl.setLinks(links);

        // Auslesen der simplen Gegenstände, die direkt angegeben werden
        children = xmlElement.getChildElements("ausruestungNeu");
        final SimpelGegenstand[] simpGegenstand = new SimpelGegenstand[children.size()];
        for (int i = 0; i < children.size(); i++) {
            simpGegenstand[i] = new SimpelGegenstand();
            final XOMMapper mappy = new XOMMapper_SimpelGegenstand();
            mappy.map(children.get(i), simpGegenstand[i]);
        }
        hilfsauswahl.setSimpGegenstand(simpGegenstand);
    }

    private void map(HilfsAuswahl hilfsauswahl, Element xmlElement) {

        // Schreiben der Anzahl
        final int anzahl = hilfsauswahl.getAnzahl();
        if (anzahl != 1) {
            xmlElement.addAttribute(new Attribute("anzahl", Integer.toString(anzahl)));
        }

        // Schreiben der Links
        final IdLink[] links = hilfsauswahl.getLinks();
        for (int i = 0; i < links.length; i++) {
            final Element e = new Element("ausruestungLink");
            XOMMappingHelper.mapIdLink(links[i], e);
            xmlElement.appendChild(e);
        }

        // Schreiben der simplen Gegenstände, die direkt angegeben werden
        final SimpelGegenstand[] simpGegenstand = hilfsauswahl.getSimpGegenstand();
        for (int i = 0; i < simpGegenstand.length; i++) {
            final Element e = new Element("ausruestungNeu");
            final XOMMapper mappy = new XOMMapper_SimpelGegenstand();
            mappy.map(simpGegenstand[i], e);
            xmlElement.appendChild(e);
        }
    }
}
