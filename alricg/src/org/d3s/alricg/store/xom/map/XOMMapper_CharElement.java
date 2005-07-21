package org.d3s.alricg.store.xom.map;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.RegelAnmerkung;
import org.d3s.alricg.charKomponenten.RegelAnmerkung.Modus;
import org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter;

abstract class XOMMapper_CharElement implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {

        // Auslesen des Namen (minOcc=1; maxOcc=1)
        charElement.setName(xmlElement.getAttributeValue("name"));

        // Sonderregel (minOcc=0; maxOcc=1)
        Element child = xmlElement.getFirstChildElement("sonderregel");
        if (child != null) {

            // TODO Laden von Sonderregeln einbauen! Am besten mit dynamischen laden der Klasse
            /*
             * this.sonderRegel = new Sonderregel(
             * xmlElement.getFirstChildElement("sonderregel").getAttributeValue("id"),
             * xmlElement.getFirstChildElement("sonderregel").getValue().trim() );
             */

        }

        // Anzeigen - Boolean + Text (minOcc=0; maxOcc=1)
        child = xmlElement.getFirstChildElement("anzeigen");
        if (child != null) {

            final String value = child.getAttributeValue("anzeigen").toLowerCase();

            // Pr�fung, ob der text auch nur "true" und "false" enth�lt
            assert "true".equals(value) || "false".equals(value);

            charElement.setAnzeigen(Boolean.parseBoolean(value));
            if (!charElement.isAnzeigen()) {
                charElement.setAnzeigenText(child.getValue().trim());
            }
        }

        // Regelanmerkungen (minOcc=0; maxOcc=1)
        child = xmlElement.getFirstChildElement("regelAnmerkungen");
        if (child != null) {
            final RegelAnmerkung rAnmerkung = new RegelAnmerkung(); // RegelAnmerkung erzeugen
            final Elements rAnmerkungen = child.getChildElements("regel");

            // einzelnen RegelAnmerkungen (minOcc=1; maxOcc=unbounded)
            for (int i = 0; i < rAnmerkungen.size(); i++) {
                final Element e = rAnmerkungen.get(i);
                rAnmerkung.add(e.getValue().trim(), e.getAttributeValue("modus"));
            }
            charElement.setRegelAnmerkung(rAnmerkung);
        }

        // Beschreibung (minOcc=0; maxOcc=1)
        child = xmlElement.getFirstChildElement("beschreibung");
        if (child != null) {
            charElement.setBeschreibung(child.getValue().trim());
        }

        // Sammelbegriff (minOcc=0; maxOcc=1)
        child = xmlElement.getFirstChildElement("sammelbegriff");
        if (child != null) {
            charElement.setSammelberiff(child.getValue().trim());

        }
    }

    public void map(CharElement charElement, Element xmlElement) {

        xmlElement.addAttribute(new Attribute("id", charElement.getId()));
        xmlElement.addAttribute(new Attribute("name", charElement.getName()));

        // Beschreibung
        String sValue = charElement.getBeschreibung();
        if (sValue.length() > 0) {
            final Element e = new Element("beschreibung");
            e.appendChild(sValue);
            xmlElement.appendChild(e);
        }

        // Anzeigen
        sValue = charElement.getAnzeigenText();
        if (sValue != null) {
            final Element e = new Element("anzeigen");
            final Attribute a = new Attribute("anzeigen", Boolean.toString(charElement.isAnzeigen()));
            e.addAttribute(a);
            e.appendChild(sValue);
            xmlElement.appendChild(e);
        }

        // Sonderregel
        if ( charElement.hasSonderregel() ) {
        	final SonderregelAdapter sonderregel = charElement.createSonderregel();
            final Element e = new Element("sonderregel");
            final Attribute a = new Attribute("id", sonderregel.getId());
            e.addAttribute(a);
            e.appendChild(sonderregel.getBeschreibung());
            xmlElement.appendChild(e);
        }

        // Regelanmerkungen
        RegelAnmerkung regelAnmerkung = charElement.getRegelAnmerkung();
        if (regelAnmerkung != null) {
            final Element e = new Element("regelAnmerkungen");
            final String[] anmerkungen = regelAnmerkung.getAnmerkungen();
            final Modus[] modi = regelAnmerkung.getModi();

            // Jede Regel
            for (int i = 0; i < anmerkungen.length; i++) {
                final Element regel = new Element("regel");
                final Attribute mode = new Attribute("modus", modi[i].getValue());
                regel.addAttribute(mode);
                regel.appendChild(anmerkungen[i]);
                e.appendChild(regel);
            }
            xmlElement.appendChild(e);
        }

        // Sammelbegriff
        sValue = charElement.getSammelBegriff();
        if (sValue.length() > 0) {
            final Element e = new Element("sammelbegriff");
            e.appendChild(sValue);
            xmlElement.appendChild(e);
        }
    }
}
