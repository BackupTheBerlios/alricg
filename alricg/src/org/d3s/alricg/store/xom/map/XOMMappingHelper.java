package org.d3s.alricg.store.xom.map;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Herkunft;
import org.d3s.alricg.charKomponenten.charZusatz.SimpelGegenstand;
import org.d3s.alricg.charKomponenten.links.Auswahl;
import org.d3s.alricg.charKomponenten.links.AuswahlAusruestung;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.IdLinkList;
import org.d3s.alricg.charKomponenten.links.Voraussetzung;
import org.d3s.alricg.charKomponenten.links.Auswahl.Modus;
import org.d3s.alricg.charKomponenten.links.Auswahl.VariableAuswahl;
import org.d3s.alricg.charKomponenten.links.AuswahlAusruestung.HilfsAuswahl;
import org.d3s.alricg.charKomponenten.links.Voraussetzung.IdLinkVoraussetzung;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;

final class XOMMappingHelper {

    private XOMMappingHelper() {

    }

    public static void mapIdLinkList(Element xmlElement, IdLinkList idLinkList) {
        final List<IdLink> links = new ArrayList<IdLink>();

        // Auslesen der Tags
        Attribute a = xmlElement.getAttribute("ids");
        if (a != null) {
            String[] ids = a.getValue().split(" ");

            // Einlesen der IdLinks aus dem Attribut
            for (int i = 0; i < ids.length; i++) {
                final IdLink link = new IdLink(idLinkList.getQuelle(), null);
                link.loadFromId(ids[i]);
                links.add(link);
            }

        }

        // Einlesen der IdLinks mit weiteren Eigenschaften
        final Elements children = xmlElement.getChildElements("linkId");
        for (int i = 0; i < children.size(); i++) {
            final IdLink link = new IdLink(idLinkList.getQuelle(), null);
            mapIdLink(children.get(i), link);
            links.add(link);
        }
        idLinkList.setLinks(links.toArray(new IdLink[0]));
    }

    public static void mapIdLinkList(IdLinkList idLinkList, Element xmlElement) {

        if (idLinkList == null)
            return;

        final StringBuffer buffy = new StringBuffer();

        // Schreiben des Attributes
        IdLink[] links = idLinkList.getLinks();
        if (links == null)
            return;

        for (int i = 0; i < links.length; i++) {

            if (links[i].getZweitZiel() != null || links[i].getText() != null || links[i].getWert() != IdLink.KEIN_WERT
                    || links[i].isLeitwert() != false) {

                // Schreiben in Option, zusätzliche Angaben nötig

                final Element e = new Element("linkId");
                mapIdLink(links[i], e);
                xmlElement.appendChild(e);

            } else { // Schreiben in Attribut, nur Id nötig
                buffy.append(links[i].getZiel().getId());
                buffy.append(" ");
            }
        }

        // XXX War das zum testen????
        if (buffy.toString().trim().equals("VOR-String VOR-String VOR-String")) {
            System.out.println("x");
        }

        // Attribut hinzufügen, falls mindestens ein element vorhanden
        if (buffy.length() > 0) {
            final Attribute a = new Attribute("ids", buffy.toString().trim());
            xmlElement.addAttribute(a);
        }
    }

    public static void mapIdLink(Element xmlElement, IdLink idLink) {

        // Typs des Ziels (Talent, Zauber, usw.); muß ein Idlink enthalten
        final String idValue = xmlElement.getAttributeValue("id");
        final CharKomponente charKomp = ProgAdmin.data.getCharKompFromId(idValue);

        // Ziel ID; muß ein Idlink enthalten
        idLink.setZielId(ProgAdmin.data.getCharElement(idValue, charKomp));

        // Optionaler Text
        Attribute attribute = xmlElement.getAttribute("text");
        if (attribute != null) {
            idLink.setText(attribute.getValue().trim());
        }

        // Optionaler Wert
        attribute = xmlElement.getAttribute("wert");
        if (attribute != null) {
            idLink.setWert(Integer.parseInt(attribute.getValue().trim()));
        }

        // Optional Leitwert (Default ist false)
        attribute = xmlElement.getAttribute("leitwert");
        if (attribute != null) {

            // Gültigkeit des Wertes überprufen
            final String value = attribute.getValue().toLowerCase();
            assert value.equals("true") || value.equals("false");

            idLink.setLeitwert(Boolean.parseBoolean(value));
        }

        // Optionale Link-ID
        attribute = xmlElement.getAttribute("linkId");
        if (attribute != null) {
            final String value = attribute.getValue();
            idLink.setZweitZiel(ProgAdmin.data.getCharElement(value, ProgAdmin.data.getCharKompFromId(value)));
        }

    }

    public static void mapIdLink(IdLink idLink, Element xmlElement) {

        // MUSS: id
        xmlElement.addAttribute(new Attribute("id", idLink.getZiel().getId()));

        // Optional: Zweitziel
        CharElement charEl = idLink.getZweitZiel();
        if (charEl != null) {
            xmlElement.addAttribute(new Attribute("linkId", charEl.getId()));
        }

        // Optional: Text
        String value = idLink.getText();
        if (value.length() > 0) {
            xmlElement.addAttribute(new Attribute("text", value));
        }

        // Optional: Wert
        int wert = idLink.getWert();
        if (wert != IdLink.KEIN_WERT) {
            xmlElement.addAttribute(new Attribute("wert", Integer.toString(wert)));
        }

        // Optional: Leitwert (false ist default)
        xmlElement.addAttribute(new Attribute("leitwert", Boolean.toString(idLink.isLeitwert())));
    }

    public static void mapIdLinkVoraussetzung(Element xmlElement, IdLinkVoraussetzung idLinkV) {
        mapIdLink(xmlElement, idLinkV);

        // Guard
        Attribute a = xmlElement.getAttribute("wertGrenze");
        if (a == null) {
            return;
        }

        // Prüfen ob der Wertebereich gültig ist!
        assert a.getValue().equalsIgnoreCase("max") || a.getValue().equalsIgnoreCase("min");

        // Setzen des Wertes, "true" ist Default
        idLinkV.setMinimum(a.getValue().equalsIgnoreCase("max"));
    }

    public static void mapIdLinkVoraussetzung(IdLinkVoraussetzung idLinkV, Element xmlElement) {
        mapIdLink(idLinkV, xmlElement);

        // Hinzufügen der "wertGrenze", falls nicht Default wert
        xmlElement.addAttribute(new Attribute("wertGrenze", idLinkV.isMinimum() ? "min" : "max"));
    }

    public static void mapVoraussetzung(Element xmlElement, Voraussetzung voraussetzung) {

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

    public static void mapVoraussetzung(Voraussetzung voraussetzung, Element xmlElement) {

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

    public static void mapAuswahl(Element xmlElement, Auswahl auswahl) {

        // Auslesen der unveränderlichen, "festen" Elemente der Auswahl
        Elements children = xmlElement.getChildElements("fest");
        final IdLink[] festeAuswahl = new IdLink[children.size()];
        for (int i = 0; i < festeAuswahl.length; i++) {
            festeAuswahl[i] = new IdLink(auswahl.getHerkunft(), auswahl);
            XOMMappingHelper.mapIdLink(children.get(i), festeAuswahl[i]);
        }
        auswahl.setFesteAuswahl(festeAuswahl);

        // Auslesen der Auswahlmöglichkeiten
        children = xmlElement.getChildElements("auswahl");
        final VariableAuswahl[] varianteAuswahl = new VariableAuswahl[children.size()];
        for (int i = 0; i < varianteAuswahl.length; i++) {
            varianteAuswahl[i] = auswahl.new VariableAuswahl(auswahl);
            XOMMappingHelper.mapVariableAuswahl(children.get(i), varianteAuswahl[i]);
        }
        auswahl.setVarianteAuswahl(varianteAuswahl);
    }

    public static void mapAuswahl(Auswahl auswahl, Element xmlElement) {

        // Schreiben der festen Elemente
        final IdLink[] festeAuswahl = auswahl.getFesteAuswahl();
        for (int i = 0; i < festeAuswahl.length; i++) {
            final Element e = new Element("fest");
            XOMMappingHelper.mapIdLink(festeAuswahl[i], e);
            xmlElement.appendChild(e);
        }

        // Schreiben der "variablen" Elemente
        final VariableAuswahl[] varianteAuswahl = auswahl.getVarianteAuswahl();
        for (int i = 0; i < varianteAuswahl.length; i++) {
            final Element e = new Element("auswahl");
            XOMMappingHelper.mapVariableAuswahl(varianteAuswahl[i], e);
            xmlElement.appendChild(e);
        }
    }

    public static void mapAuswahlAusruestung(Element xmlElement, AuswahlAusruestung ausruestung) {

        // Auslesen der festen Gegenstände
        Element current = xmlElement.getFirstChildElement("fest");
        if (current != null) {
            final HilfsAuswahl festeAuswahl = ausruestung.new HilfsAuswahl();
            XOMMappingHelper.mapHilfsauswahl(current, festeAuswahl, ausruestung.getHerkunft());
            ausruestung.setFesteAuswahl(festeAuswahl);
        }

        // Auslesen der Auswahl
        final Elements children = xmlElement.getChildElements("auswahl");
        HilfsAuswahl[] variableAuswahl = new HilfsAuswahl[children.size()];
        for (int i = 0; i < variableAuswahl.length; i++) {
            variableAuswahl[i] = ausruestung.new HilfsAuswahl();
            XOMMappingHelper.mapHilfsauswahl(children.get(i), variableAuswahl[i], ausruestung.getHerkunft());
        }
        ausruestung.setVariableAuswahl(variableAuswahl);

    }

    public static void mapAuswahlAusruestung(AuswahlAusruestung ausruestung, Element xmlElement) {

        // Schreiben der "festen" Elemente
        HilfsAuswahl festeAuswahl = ausruestung.getFesteAuswahl();
        if (festeAuswahl != null) {
            final Element e = new Element("fest");
            XOMMappingHelper.mapHilfsauswahl(festeAuswahl, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der "wählbaren" Elemente
        HilfsAuswahl[] variableAuswahl = ausruestung.getVariableAuswahl();
        for (int i = 0; i < variableAuswahl.length; i++) {
            Element e = new Element("auswahl");
            XOMMappingHelper.mapHilfsauswahl(variableAuswahl[i], e);
            xmlElement.appendChild(e);
        }
    }

    private static void mapHilfsauswahl(Element xmlElement, HilfsAuswahl hilfsauswahl, Herkunft herkunft) {

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

    private static void mapHilfsauswahl(HilfsAuswahl hilfsauswahl, Element xmlElement) {

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

    private static void mapVariableAuswahl(Element xmlElement, VariableAuswahl variableAuswahl) {

        // Überprüfung oder der Modus-Wert gültig ist:
        final String attValue = xmlElement.getAttributeValue("modus");
        assert attValue.equals(Modus.LISTE.getValue()) || attValue.equals(Modus.ANZAHL.getValue())
                || attValue.equals(Modus.VERTEILUNG.getValue());

        // Einlesen des Modus
        if (attValue.equals(Modus.LISTE.getValue())) {
            variableAuswahl.setModus(Modus.LISTE);
        } else if (attValue.equals(Modus.ANZAHL.getValue())) {
            variableAuswahl.setModus(Modus.ANZAHL);
        } else { // ... .equals(Modus.VERTEILUNG.getValue())
            variableAuswahl.setModus(Modus.VERTEILUNG);
        }

        try {
            // Einlesen der Werte / optional
            if (xmlElement.getAttribute("werte") != null) {
                final String[] attValues = xmlElement.getAttributeValue("werte").split(" ");
                final int[] werte = new int[attValues.length];
                for (int i = 0; i < attValues.length; i++) {
                    werte[i] = Integer.parseInt(attValues[i]);
                }
                variableAuswahl.setWerte(werte);
            }

            // Einlesen des optionalen Feldes Anzahl
            if (xmlElement.getAttribute("anzahl") != null) {
                variableAuswahl.setAnzahl(Integer.parseInt(xmlElement.getAttributeValue("anzahl")));
            }
        } catch (NumberFormatException exc) {
            ProgAdmin.logger.log(Level.SEVERE, "String -> int failed", exc);
        }

        // Einlesen der Optionen, sollten mind. zwei sein
        Elements children = xmlElement.getChildElements("option");
        final IdLink[] optionen = readOptionen(children, variableAuswahl);
        variableAuswahl.setOptionen(optionen);

        // Einlesen der Optionsgruppen
        children = xmlElement.getChildElements("optionsGruppe");
        IdLink[][] optionsGruppe = new IdLink[children.size()][];
        for (int i = 0; i < children.size(); i++) {
            optionsGruppe[i] = readOptionen(children.get(i).getChildElements("option"), variableAuswahl);
        }

        // Falls es keine Optionsgruppen gibt, komplettes Array wieder auf null
        if (optionsGruppe.length == 0) {
            optionsGruppe = null;
        }
        variableAuswahl.setOptionsGruppe(optionsGruppe);
    }

    private static void mapVariableAuswahl(VariableAuswahl variableAuswahl, Element xmlElement) {

        // Schreiben der einzelnen Optionen:
        final IdLink[] optionen = variableAuswahl.getOptionen();
        for (int i = 0; i < optionen.length; i++) {
            final Element e = new Element("option");
            XOMMappingHelper.mapIdLink(optionen[i], e);
            xmlElement.appendChild(e);
        }

        // Schreiben der Optionsgruppen:
        final IdLink[][] optionsGruppe = variableAuswahl.getOptionsGruppe();
        if (optionsGruppe != null) {
            for (int i = 0; i < optionsGruppe.length; i++) {
                final Element e = new Element("optionsGruppe");
                for (int ii = 0; ii < optionsGruppe[i].length; ii++) {
                    final Element ee = new Element("option");
                    XOMMappingHelper.mapIdLink(optionsGruppe[i][ii], ee);
                    e.appendChild(ee);
                }
                xmlElement.appendChild(e);
            }
        }

        // Schreiben des Attributs "werte"
        final int[] werte = variableAuswahl.getWerte();
        final StringBuffer buffy = new StringBuffer();
        for (int i = 0; i < werte.length; i++) {
            buffy.append(Integer.toString(werte[i]));
            buffy.append(" ");
        }
        if (buffy.length() > 0) {
            xmlElement.addAttribute(new Attribute("werte", buffy.toString().trim()));
        }

        // Schreiben des Attribus "anzahl"
        final int anzahl = variableAuswahl.getAnzahl();
        if (anzahl != 0) {
            xmlElement.addAttribute(new Attribute("anzahl", Integer.toString(anzahl)));
        }

        // Schreiben des Attributs "modus"
        xmlElement.addAttribute(new Attribute("modus", variableAuswahl.getModus().getValue()));
    }

    private static IdLink[] readOptionen(Elements xmlElements, VariableAuswahl va) {
        final IdLink[] optionen = new IdLink[xmlElements.size()];
        for (int i = 0; i < optionen.length; i++) {
            optionen[i] = new IdLink(va.getAuswahl().getHerkunft(), va.getAuswahl());
            XOMMappingHelper.mapIdLink(xmlElements.get(i), optionen[i]);
        }
        return optionen;
    }

}
