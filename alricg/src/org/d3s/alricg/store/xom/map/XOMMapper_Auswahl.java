package org.d3s.alricg.store.xom.map;

import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.links.Auswahl;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.Auswahl.Modus;
import org.d3s.alricg.charKomponenten.links.Auswahl.VariableAuswahl;
import org.d3s.alricg.controller.ProgAdmin;

class XOMMapper_Auswahl {

    public void map(Element xmlElement, Auswahl auswahl) {

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
            map(children.get(i), varianteAuswahl[i]);
        }
        auswahl.setVarianteAuswahl(varianteAuswahl);
    }

    public void map(Auswahl auswahl, Element xmlElement) {

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
            map(varianteAuswahl[i], e);
            xmlElement.appendChild(e);
        }
    }

    private void map(Element xmlElement, VariableAuswahl variableAuswahl) {

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

    private void map(VariableAuswahl variableAuswahl, Element xmlElement) { // writeXmlElemnet(String tagName) {

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

    private IdLink[] readOptionen(Elements xmlElements, VariableAuswahl va) {
        final IdLink[] optionen = new IdLink[xmlElements.size()];
        for (int i = 0; i < optionen.length; i++) {
            optionen[i] = new IdLink(va.getAuswahl().getHerkunft(), va.getAuswahl());
            XOMMappingHelper.mapIdLink(xmlElements.get(i), optionen[i]);
        }
        return optionen;
    }
}
