package org.d3s.alricg.store.xom.map;

import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Fertigkeit;
import org.d3s.alricg.charKomponenten.Werte;
import org.d3s.alricg.charKomponenten.Werte.CharArten;
import org.d3s.alricg.charKomponenten.links.Voraussetzung;
import org.d3s.alricg.controller.ProgAdmin;

abstract class XOMMapper_Fertigkeit extends XOMMapper_CharElement implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final Fertigkeit fertigkeit = (Fertigkeit) charElement;

        // Auslesen der Additions-Werte
        Element current = xmlElement.getFirstChildElement("addition");
        if (current != null) {
            fertigkeit.setAdditionsID(current.getAttributeValue("id"));
            try {
                fertigkeit.setAdditionsWert(Integer.parseInt(current.getAttributeValue("wertigkeit")));
            } catch (NumberFormatException exc) {
                ProgAdmin.logger.log(Level.SEVERE, "String -> int failed", exc);
            }
        }

        // Auslesen, ob diese Fertigkeit noch Text benötigt
        current = xmlElement.getFirstChildElement("hatText");
        if (current != null) {

            // Sicherstellen des Wertebereiches
            assert current.getValue().equalsIgnoreCase("true") || current.getValue().equalsIgnoreCase("false");

            fertigkeit.setHasText(current.getValue().equalsIgnoreCase("true"));
        }

        // Auslesen, ob diese Fertigkeit noch die Angabe eines Elements benötigt
        current = xmlElement.getFirstChildElement("hatElement");
        if (current != null) {

            // Sicherstellen des Wertebereiches
            assert current.getValue().equalsIgnoreCase("true") || current.getValue().equalsIgnoreCase("false");
            fertigkeit.setElementAngabe(current.getValue().equalsIgnoreCase("true"));
        }
        
        // Einlesen der vorgeschlagenen Texte
        current = xmlElement.getFirstChildElement("textVorschlaege");
        if (current != null) {
            final Elements children = current.getChildElements("text");
            final String[] textVorschlaege = new String[children.size()];
            for (int i = 0; i < textVorschlaege.length; i++) {
                textVorschlaege[i] = children.get(i).getValue();
            }
            fertigkeit.setTextVorschlaege(textVorschlaege);
        }


        // Auslesen, ob normal Wählbar oder nur über Herkunft o.ä. zu bekommen
        current = xmlElement.getFirstChildElement("istWaehlbar");
        if (current != null) {
            assert current.getValue().equalsIgnoreCase("true") || current.getValue().equalsIgnoreCase("false");

            fertigkeit.setWaehlbar(current.getValue().equalsIgnoreCase("true"));
        }

        // Auslesen für welche CharArten die Fertigkeit ist
        final Elements children = xmlElement.getChildElements("fuerWelcheChars");
        Werte.CharArten[] fuerWelcheChars = new Werte.CharArten[children.size()];
        for (int i = 0; i < children.size(); i++) {
            fuerWelcheChars[i] = Werte.getCharArtenByValue(children.get(i).getValue());
        }
        fertigkeit.setFuerWelcheChars(fuerWelcheChars);

        // Auslesen der Voraussetzungen
        current = xmlElement.getFirstChildElement("voraussetzungen");
        if (current != null) {
            final Voraussetzung voraussetzung = new Voraussetzung(fertigkeit);
            XOMMappingHelper.mapVoraussetzung(current, voraussetzung);
            fertigkeit.setVoraussetzung(voraussetzung);
        }

        // Auslesen der GP Kosten
        try {
            fertigkeit.setGpKosten(Integer.parseInt(xmlElement.getAttributeValue("gp")));
        } catch (NumberFormatException exc) {
            ProgAdmin.logger.log(Level.SEVERE, "String -> int failed", exc);
        }
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        final Fertigkeit fertigkeit = (Fertigkeit) charElement;

        // Schreiben der additions-Werte
        final int additionsWert = fertigkeit.getAdditionsWert();
        final String additionsID = fertigkeit.getAdditionsID();
        if (additionsWert != CharElement.KEIN_WERT) {
            final Element e = new Element("addition");
            e.addAttribute(new Attribute("id", additionsID));
            e.addAttribute(new Attribute("wertigkeit", Integer.toString(additionsWert)));
            xmlElement.appendChild(e);
        }

        // Schreiben, ob die Fertigkeit einen Text benötigt
        {
            final Element e = new Element("hatText");
            e.appendChild(fertigkeit.hasText() ? "true" : "false");
            xmlElement.appendChild(e);
        }

        // Schreiben, ob die Fertigkeit die Angabe eines Elements benötigt
        {
            final Element e = new Element("hatElement");
            e.appendChild(fertigkeit.isElementAngabe() ? "true" : "false");
            xmlElement.appendChild(e);
        }
        
        final String [] textVorschlaege = fertigkeit.getTextVorschlaege();
        if (textVorschlaege != null) {
            final Element e= new Element("textVorschlaege");
            xmlElement.appendChild(e);
            for (int i = 0; i < textVorschlaege.length; i++) {
                final Element ee = new Element("text");
                ee.appendChild(textVorschlaege[i]);
                e.appendChild(ee);
            }
        }


        // Schreiben, ob das Element normal wählbar ist
        {
            final Element e = new Element("istWaehlbar");
            e.appendChild(fertigkeit.isWaehlbar() ? "true" : "false");
            xmlElement.appendChild(e);
        }

        // Schreiben der zulässigen CharArten
        final CharArten[] fuerWelcheChars = fertigkeit.getFuerWelcheChars();
        for (int i = 0; i < fuerWelcheChars.length; i++) {
            final Element e = new Element("fuerWelcheChars");
            e.appendChild(fuerWelcheChars[i].getValue());
            xmlElement.appendChild(e);
        }

        // Schreiben der Voraussetzungen
        final Voraussetzung voraussetzung = fertigkeit.getVoraussetzung();
        if (voraussetzung != null) {
            final Element e = new Element("voraussetzungen");
            XOMMappingHelper.mapVoraussetzung(voraussetzung, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der GP Kosten
        xmlElement.addAttribute(new Attribute("gp", Integer.toString(fertigkeit.getGpKosten())));
    }
}
