package org.d3s.alricg.store.xom.map;

import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Repraesentation;
import org.d3s.alricg.charKomponenten.Werte;
import org.d3s.alricg.charKomponenten.Zauber;
import org.d3s.alricg.charKomponenten.Werte.MagieMerkmal;
import org.d3s.alricg.charKomponenten.Zauber.Verbreitung;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;

class XOMMapper_Zauber extends XOMMapper_Faehigkeit implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final Zauber zauber = (Zauber) charElement;

        // Auslesen der Merkmale
        Elements children = xmlElement.getChildElements("merkmale");
        final MagieMerkmal[] merkmale = new MagieMerkmal[children.size()];
        for (int i = 0; i < merkmale.length; i++) {
            merkmale[i] = Werte.getMagieMerkmalByValue(children.get(i).getValue());
        }
        zauber.setMerkmale(merkmale);

        // Auslesen der repraesentationen
        children = xmlElement.getChildElements("verbreitung");
        final Verbreitung[] verbreitung = new Verbreitung[children.size()];
        for (int i = 0; i < verbreitung.length; i++) {
            verbreitung[i] = zauber.new Verbreitung();
            mapVerbreitung(children.get(i), verbreitung[i]);
        }
        zauber.setVerbreitung(verbreitung);

        // Auslesen der Modis auf die Probe
        Element current = xmlElement.getFirstChildElement("probenModi");
        if (current != null) {
            zauber.setProbenModi(current.getValue());
        }

        // Auslesen der zauberdauer
        current = xmlElement.getFirstChildElement("zauberdauer");
        zauber.setZauberdauer(current.getValue());

        // Auslesen der aspKosten
        current = xmlElement.getFirstChildElement("aspKosten");
        zauber.setAspKosten(current.getValue());

        // Auslesen des ziels
        current = xmlElement.getFirstChildElement("ziel");
        zauber.setZiel(current.getValue());

        // Auslesen der reichweite
        current = xmlElement.getFirstChildElement("reichweite");
        zauber.setReichweite(current.getValue());

        // Auslesen der Wirkungsdauer
        current = xmlElement.getFirstChildElement("wirkungsdauer");
        zauber.setWirkungsdauer(current.getValue());
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my Mapping
        Zauber zauber = (Zauber) charElement;
        xmlElement.setLocalName("zauber");

        // Schreiben der Merkmale
        final MagieMerkmal[] merkmale = zauber.getMerkmale();
        for (int i = 0; i < merkmale.length; i++) {
            Element e = new Element("merkmale");
            e.appendChild(merkmale[i].getValue());
            xmlElement.appendChild(e);
        }

        // Schreiben der Repr�sentationen
        final Verbreitung[] verbreitung = zauber.getVerbreitung();
        for (int i = 0; i < verbreitung.length; i++) {
            Element e = new Element("verbreitung");
            mapVerbreitung(verbreitung[i], e);
            xmlElement.appendChild(e);
        }

        // Schreiben der Modis auf die Probe
        String value = zauber.getProbenModi();
        if (value.length() > 0) {
            final Element e = new Element("probenModi");
            e.appendChild(value);
            xmlElement.appendChild(e);
        }

        // Schreiben der Zauberdauer
        Element e = new Element("zauberdauer");
        e.appendChild(zauber.getZauberdauer());
        xmlElement.appendChild(e);

        // Schreiben der Asp Kosten
        e = new Element("aspKosten");
        e.appendChild(zauber.getAspKosten());
        xmlElement.appendChild(e);

        // Schreiben des Ziels
        e = new Element("ziel");
        e.appendChild(zauber.getZiel());
        xmlElement.appendChild(e);

        // Schreiben der Reichweite
        e = new Element("reichweite");
        e.appendChild(zauber.getReichweite());
        xmlElement.appendChild(e);

        // Schreiben der Wirkungsweise
        e = new Element("wirkungsdauer");
        e.appendChild(zauber.getWirkungsdauer());
        xmlElement.appendChild(e);
    }

    private void mapVerbreitung(Element xmlElement, Verbreitung verbreitung) {

        // Einlesen des "Bekannt bei" Wertes
        if (xmlElement.getAttribute("bekanntBei") != null) {
            Repraesentation bekanntBei = (Repraesentation) ProgAdmin.data.getCharElement(xmlElement
                    .getAttributeValue("bekanntBei"), CharKomponente.repraesentation);

            verbreitung.setBekanntBei(bekanntBei);
        }

        // Einlesen der Repraesentation
        Repraesentation repraesentation = (Repraesentation) ProgAdmin.data.getCharElement(xmlElement
                .getAttributeValue("repraesentation"), CharKomponente.repraesentation);
        verbreitung.setRepraesentation(repraesentation);

        // Einlesen des Wertes
        try {
            verbreitung.setWert(Integer.parseInt(xmlElement.getAttributeValue("wert")));
        } catch (NumberFormatException exc) {
            ProgAdmin.logger.log(Level.SEVERE, "String -> int failed", exc);
        }
    }

    private void mapVerbreitung(Verbreitung verbreitung, Element xmlElement) {

        // Schreiben des "bekanntBei"
        final Repraesentation bekanntBei = verbreitung.getBekanntBei();
        final Repraesentation repraesentation = verbreitung.getRepraesentation();
        if (bekanntBei != null && !bekanntBei.equals(repraesentation)) {
            xmlElement.addAttribute(new Attribute("bekanntBei", bekanntBei.getId()));
        }

        // Schreiben der Repr�sentation
        xmlElement.addAttribute(new Attribute("repraesentation", repraesentation.getId()));

        // Schreibe den Wert
        xmlElement.addAttribute(new Attribute("wert", Integer.toString(verbreitung.getWert())));
    }
}