package org.d3s.alricg.store.xom.map;

import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.links.IdLinkList;
import org.d3s.alricg.charKomponenten.links.Voraussetzung;
import org.d3s.alricg.charKomponenten.links.Voraussetzung.IdLinkVoraussetzung;

class XOMMapper_Voraussetzung {

    public void map(Element xmlElement, Voraussetzung voraussetzung) {

        // Auslesen der "festen" Elemente
        Elements children = xmlElement.getChildElements("fest");
        IdLinkVoraussetzung[] festeVoraussetzung = new IdLinkVoraussetzung[children.size()];
        for (int i = 0; i < children.size(); i++) {
            festeVoraussetzung[i] = voraussetzung.new IdLinkVoraussetzung(voraussetzung.getQuelle());
            XOMMappingHelper.mapIdLinkVoraussetzung(children.get(i), festeVoraussetzung[i]);
        }
        voraussetzung.setFesteVoraussetzung(festeVoraussetzung);

        // Auslesen der "nichtErlaubt" Elemente
        Element current = xmlElement.getFirstChildElement("nichtErlaubt");
        if (current != null) {
            final IdLinkList nichtErlaubt = new IdLinkList(voraussetzung.getQuelle());
            XOMMappingHelper.mapIdLinkList(current, nichtErlaubt);
            voraussetzung.setNichtErlaubt(nichtErlaubt);
        }

        // Aulesen der "Auswahl", also wo nur eines aus einer Gruppe erfüllt sein muß
        children = xmlElement.getChildElements("auswahl");
        final IdLinkVoraussetzung[][] auswahlVoraussetzung = new IdLinkVoraussetzung[children.size()][];
        for (int i = 0; i < children.size(); i++) {
            final Elements options = children.get(i).getChildElements("option");
            auswahlVoraussetzung[i] = new IdLinkVoraussetzung[options.size()];
            for (int ii = 0; ii < options.size(); ii++) {
                auswahlVoraussetzung[i][ii] = voraussetzung.new IdLinkVoraussetzung(voraussetzung.getQuelle());
                XOMMappingHelper.mapIdLinkVoraussetzung(options.get(ii), auswahlVoraussetzung[i][ii]);
            }
        }
        voraussetzung.setAuswahlVoraussetzung(auswahlVoraussetzung);
    }

    public void map(Voraussetzung voraussetzung, Element xmlElement) {
 
        // Alle "festen" Elemente hinzufügen
        final IdLinkVoraussetzung[] festeVoraussetzung = voraussetzung.getFesteVoraussetzung();
        for (int i = 0; i < festeVoraussetzung.length; i++) {
            final Element e = new Element("fest");
            XOMMappingHelper.mapIdLinkVoraussetzung(festeVoraussetzung[i], e);
            xmlElement.appendChild(e);
        }

        // Hinzufügen der "nichtErlaubt" Elemente
        final IdLinkList nichtErlaubt = voraussetzung.getNichtErlaubt();
        if (nichtErlaubt != null) {
            final Element e = new Element("empfVorteile");
            XOMMappingHelper.mapIdLinkList(nichtErlaubt, e);
            xmlElement.appendChild(e);
        }

        // Alle Elemente der "Auswahl" hinzufügen
        final IdLinkVoraussetzung[][] auswahlVoraussetzung = voraussetzung.getAuswahlVoraussetzung();
        for (int i = 0; i < auswahlVoraussetzung.length; i++) {
            final Element e = new Element("auswahl");
            for (int ii = 0; ii < auswahlVoraussetzung[i].length; ii++) {
                final Element ee = new Element("option");
                XOMMappingHelper.mapIdLinkVoraussetzung(auswahlVoraussetzung[i][ii], ee);
                e.appendChild(ee);
            }
            xmlElement.appendChild(e);
        }
    }
}
