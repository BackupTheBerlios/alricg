package org.d3s.alricg.store.xom.map;

import java.util.ArrayList;
import java.util.List;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.IdLinkList;
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
}
