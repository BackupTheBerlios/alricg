package org.d3s.alricg.store.xom.map;

import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Herkunft;
import org.d3s.alricg.charKomponenten.Repraesentation;
import org.d3s.alricg.charKomponenten.Werte.Geschlecht;
import org.d3s.alricg.charKomponenten.links.Auswahl;
import org.d3s.alricg.charKomponenten.links.IdLinkList;
import org.d3s.alricg.charKomponenten.links.Voraussetzung;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;

abstract class XOMMapper_Herkunft extends XOMMapper_CharElement implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final Herkunft herkunft = (Herkunft) charElement;

        // Auslesen der GP
        try {
            herkunft.setGpKosten(Integer.parseInt(xmlElement.getFirstChildElement("gp").getValue()));

            // Auslesen vom "kannGewaehltWerden"-Tag
            Element current = xmlElement.getFirstChildElement("kannGewaehltWerden");
            if (current != null) {

                // Sicherstellen, das der Wert g�ltig ist
                String value = current.getValue().toLowerCase();
                assert value.equals("false") || value.equals("true");
                herkunft.setKannGewaehltWerden(value.equals("true"));
            }

            // Auslesen des Geschlechts
            current = xmlElement.getFirstChildElement("geschlecht");
            if (current != null) {

                // Sicherstellen, das der Wert g�ltig ist
                String value = current.getValue().toUpperCase();
                assert value.equals("M") || value.equals("W") || value.equals("MW");
                if (value.equals("M")) {
                    herkunft.setGeschlecht(Geschlecht.mann);
                } else if (value.equals("W")) {
                    herkunft.setGeschlecht(Geschlecht.frau);
                } else { // ... .getValue().equals("MW")
                    herkunft.setGeschlecht(Geschlecht.mannFrau);
                }
            }

            // Auslesen der eigenschaftModi
            current = xmlElement.getFirstChildElement("eigenschaftModi");
            if (current != null) {
                final Auswahl eigenschaftModis = new Auswahl(herkunft);
                XOMMapper_Auswahl mappy = new XOMMapper_Auswahl();
                mappy.map(current, eigenschaftModis);
                herkunft.setEigenschaftModis(eigenschaftModis);
            }

            // Auslesen der Grenzen f�r den Sozialstatus
            current = xmlElement.getFirstChildElement("soGrenzen");
            if (current != null) {

                if (current.getAttribute("soMin") != null) {
                    herkunft.setSoMin(Integer.parseInt(current.getAttributeValue("soMin")));
                }
                if (current.getAttribute("soMax") != null) {
                    herkunft.setSoMax(Integer.parseInt(current.getAttributeValue("soMax")));
                }
            }

            // Auslesen der Voraussetzung
            current = xmlElement.getFirstChildElement("voraussetzung");
            if (current != null) {
                final Voraussetzung voraussetzung = new Voraussetzung(herkunft);
                XOMMapper_Voraussetzung mappy = new XOMMapper_Voraussetzung();
                mappy.map(current, voraussetzung);
                herkunft.setVoraussetzung(voraussetzung);
            }

            // Auslesen der Vorteile
            current = xmlElement.getFirstChildElement("vorteile");
            if (current != null) {
                final Auswahl auswahl = new Auswahl(herkunft);
                XOMMapper_Auswahl mappy = new XOMMapper_Auswahl();
                mappy.map(current, auswahl);
                herkunft.setVorteileAuswahl(auswahl);
            }

            // Auslesen der Nachteile
            current = xmlElement.getFirstChildElement("nachteile");
            if (current != null) {
                final Auswahl auswahl = new Auswahl(herkunft);
                XOMMapper_Auswahl mappy = new XOMMapper_Auswahl();
                mappy.map(current, auswahl);
                herkunft.setNachteileAuswahl(auswahl);
            }

            // Auslesen der Sonderfertigkeiten
            current = xmlElement.getFirstChildElement("sonderf");
            if (current != null) {
                final Auswahl auswahl = new Auswahl(herkunft);
                XOMMapper_Auswahl mappy = new XOMMapper_Auswahl();
                mappy.map(current, auswahl);
                herkunft.setSfAuswahl(auswahl);
            }

            // Auslesen der Liturgien
            current = xmlElement.getFirstChildElement("liturgien");
            if (current != null) {
                final Auswahl auswahl = new Auswahl(herkunft);
                XOMMapper_Auswahl mappy = new XOMMapper_Auswahl();
                mappy.map(current, auswahl);
                herkunft.setLiturgienAuswahl(auswahl);
            }

            // Auslesen der goetterRituale
            current = xmlElement.getFirstChildElement("goetterRituale");
            if (current != null) {
                final Auswahl auswahl = new Auswahl(herkunft);
                XOMMapper_Auswahl mappy = new XOMMapper_Auswahl();
                mappy.map(current, auswahl);
                herkunft.setRitualeAuswahl(auswahl);
            }

            // Auslesen der empfohlenen Vorteile
            current = xmlElement.getFirstChildElement("empfVorteile");
            if (current != null) {
                final IdLinkList auswahl = new IdLinkList(herkunft);
                XOMMappingHelper.mapIdLinkList(current, auswahl);
                herkunft.setEmpfVorteile(auswahl);
            }

            // Auslesen der empfohlenen Nachteile
            current = xmlElement.getFirstChildElement("empfNachteile");
            if (current != null) {
                final IdLinkList auswahl = new IdLinkList(herkunft);
                XOMMappingHelper.mapIdLinkList(current, auswahl);
                herkunft.setEmpfNachteile(auswahl);
            }

            // Auslesen der ungeeigneten Vorteile
            current = xmlElement.getFirstChildElement("ungeVorteile");
            if (current != null) {
                final IdLinkList auswahl = new IdLinkList(herkunft);
                XOMMappingHelper.mapIdLinkList(current, auswahl);
                herkunft.setUngeVorteile(auswahl);
            }

            // Auslesen der ungeeigneten Nachteile
            current = xmlElement.getFirstChildElement("ungeNachteile");
            if (current != null) {
                final IdLinkList auswahl = new IdLinkList(herkunft);
                XOMMappingHelper.mapIdLinkList(current, auswahl);
                herkunft.setUngeNachteile(auswahl);
            }

            // Auslesen der verbilligten Vorteile
            current = xmlElement.getFirstChildElement("verbilligteVorteile");
            if (current != null) {
                final IdLinkList auswahl = new IdLinkList(herkunft);
                XOMMappingHelper.mapIdLinkList(current, auswahl);
                herkunft.setVerbilligteVort(auswahl);
            }

            // Auslesen der verbilligten Nachteile
            current = xmlElement.getFirstChildElement("verbilligteNachteile");
            if (current != null) {
                final IdLinkList auswahl = new IdLinkList(herkunft);
                XOMMappingHelper.mapIdLinkList(current, auswahl);
                herkunft.setVerbilligteNacht(auswahl);
            }

            // Auslesen der verbilligten Sonderfertigkeiten
            current = xmlElement.getFirstChildElement("verbilligteSonderf");
            if (current != null) {
                final IdLinkList auswahl = new IdLinkList(herkunft);
                XOMMappingHelper.mapIdLinkList(current, auswahl);
                herkunft.setVerbilligteSonderf(auswahl);
            }

            // Auslesen der verbilligten Liturgien
            current = xmlElement.getFirstChildElement("verbilligteLiturgien");
            if (current != null) {
                final IdLinkList auswahl = new IdLinkList(herkunft);
                XOMMappingHelper.mapIdLinkList(current, auswahl);
                herkunft.setVerbilligteLiturgien(auswahl);
            }

            // Auslesen der verbilligten Rituale
            current = xmlElement.getFirstChildElement("verbilligteRituale");
            if (current != null) {
                final IdLinkList auswahl = new IdLinkList(herkunft);
                XOMMappingHelper.mapIdLinkList(current, auswahl);
                herkunft.setVerbilligteRituale(auswahl);
            }

            // Auslesen der Talente
            current = xmlElement.getFirstChildElement("talente");
            if (current != null) {
                final Auswahl auswahl = new Auswahl(herkunft);
                XOMMapper_Auswahl mappy = new XOMMapper_Auswahl();
                mappy.map(current, auswahl);
                herkunft.setTalente(auswahl);
            }

            // Auslesen der magischen Werte
            current = xmlElement.getFirstChildElement("magie");
            if (current != null) {

                // Auslesen der Art der Magischen repr�sentation
                if (current.getAttribute("repraesentId") != null) {
                    String attVal = current.getAttributeValue("repraesentId");
                    herkunft.setRepraesentation((Repraesentation) ProgAdmin.data.getCharElement(attVal,
                            CharKomponente.repraesentation));
                }

                // Auslesen der Hauszauber
                Element e = current.getFirstChildElement("hauszauber");
                if (e != null) {
                    final Auswahl auswahl = new Auswahl(herkunft);
                    XOMMapper_Auswahl mappy = new XOMMapper_Auswahl();
                    mappy.map(e, auswahl);
                    herkunft.setHauszauber(auswahl);
                }

                // Auslesen der Zauber
                e = current.getFirstChildElement("zauber");
                if (e != null) {
                    final Auswahl auswahl = new Auswahl(herkunft);
                    XOMMapper_Auswahl mappy = new XOMMapper_Auswahl();
                    mappy.map(e, auswahl);
                    herkunft.setZauber(auswahl);
                }

                // Auslesen der Zauber
                e = current.getFirstChildElement("aktivierbareZauber");
                if (e != null) {
                    final Auswahl auswahl = new Auswahl(herkunft);
                    XOMMapper_Auswahl mappy = new XOMMapper_Auswahl();
                    mappy.map(e, auswahl);
                    herkunft.setAktivierbareZauber(auswahl);
                }

                // Auslesen der Zauber, die nicht zu Beginn w�hlbar sind
                e = current.getFirstChildElement("zauberNichtZuBegin");
                if (e != null) {
                    final IdLinkList auswahl = new IdLinkList(herkunft);
                    XOMMappingHelper.mapIdLinkList(e, auswahl);
                    herkunft.setZauberNichtBeginn(auswahl);
                }
            }

        } catch (NumberFormatException ex) {
            ProgAdmin.logger.log(Level.SEVERE, "String -> int failed", ex);
        }

    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        final Herkunft herkunft = (Herkunft) charElement;

        // Hinzuf�gen der GP-Kosten
        Element e = new Element("gp");
        e.appendChild(Integer.toString(herkunft.getGpKosten()));
        xmlElement.appendChild(e);

        // Hinzuf�gen des "varianteVon" Tags wird in Rasse, Kultur bzw. Profession erledigt!

        // Hinzuf�gen des "kannGewaehltWerden"-Tags
        e = new Element("kannGewaehltWerden");
        e.appendChild(Boolean.toString(herkunft.isKannGewaehltWerden()));
        xmlElement.appendChild(e);

        // Hinzuf�gen des Geschlechts geschlecht
        if (herkunft.getGeschlecht() == Geschlecht.mann) {
            e = new Element("geschlecht");
            e.appendChild("M");
            xmlElement.appendChild(e);
        } else if (herkunft.getGeschlecht() == Geschlecht.frau) {
            e = new Element("geschlecht");
            e.appendChild("W");
            xmlElement.appendChild(e);
        } // else ... ist Default und brauch nicht beachtet zu werden

        // Hinzuf�gen der Eigenschaft-Modis
        Auswahl auswahl = herkunft.getEigenschaftModis();
        map(auswahl, "eigenschaftModi", xmlElement);

        // Hinzuf�gen der SO-Grenze
        final int soMax = herkunft.getSoMax();
        final int soMin = herkunft.getSoMin();
        if (soMax != Herkunft.SO_MAX_DEFAULT || soMin != Herkunft.SO_MIN_DEFAULT) {
            e = new Element("soGrenzen");

            if (soMin != Herkunft.SO_MIN_DEFAULT) {
                e.addAttribute(new Attribute("soMin", Integer.toString(soMin)));
            }
            if (soMax != Herkunft.SO_MAX_DEFAULT) {
                e.addAttribute(new Attribute("soMax", Integer.toString(soMax)));
            }
            xmlElement.appendChild(e);
        }

        // Hinzuf�gen der Voraussetzungen
        Voraussetzung v = herkunft.getVoraussetzung();
        if (v != null) {
            e = new Element("voraussetzung");
            final XOMMapper_Voraussetzung mappy = new XOMMapper_Voraussetzung();
            mappy.map(v, e);
            xmlElement.appendChild(e);
        }

        // Hinzuf�gen der Vorteile
        auswahl = herkunft.getVorteileAuswahl();
        map(auswahl, "vorteile", xmlElement);

        // Hinzuf�gen der Nachteile
        auswahl = herkunft.getNachteileAuswahl();
        map(auswahl, "nachteile", xmlElement);

        // Hinzuf�gen der Sonderfertigkeiten
        auswahl = herkunft.getSfAuswahl();
        map(auswahl, "sonderf", xmlElement);

        // Hinzuf�gen der Liturgien
        auswahl = herkunft.getLiturgienAuswahl();
        map(auswahl, "liturgien", xmlElement);

        // Hinzuf�gen der Rituale
        auswahl = herkunft.getRitualeAuswahl();
        map(auswahl, "goetterRituale", xmlElement);

        // Hinzuf�gen der empfohlenen Vorteile
        IdLinkList ids = herkunft.getEmpfVorteil();
        map(ids, "empfVorteile", xmlElement);

        // Hinzuf�gen der empfohlenen Nachteile
        ids = herkunft.getEmpfNachteile();
        map(ids, "empfNachteile", xmlElement);

        // Hinzuf�gen der ungeeigneten Vorteile
        ids = herkunft.getUngeVorteile();
        map(ids, "ungeVorteile", xmlElement);

        // Hinzuf�gen der ungeeigneten Nachteile
        ids = herkunft.getUngeNachteile();
        map(ids, "ungeNachteile", xmlElement);

        // Hinzuf�gen der verbilligten Vorteile
        ids = herkunft.getVerbilligteVort();
        map(ids, "verbilligteVorteile", xmlElement);

        // Hinzuf�gen der verbilligten Nachteile
        ids = herkunft.getVerbilligteNacht();
        map(ids, "verbilligteNachteile", xmlElement);

        // Hinzuf�gen der verbilligten Sonderfertigkeiten
        ids = herkunft.getVerbilligteSonderf();
        map(ids, "verbilligteSonderf", xmlElement);

        // Hinzuf�gen der verbilligten Liturgien
        ids = herkunft.getVerbilligteLiturgien();
        map(ids, "verbilligteLiturgien", xmlElement);

        // Hinzuf�gen der verbilligten Rituale
        ids = herkunft.getVerbilligteRituale();
        map(ids, "verbilligteRituale", xmlElement);

        // Hinzuf�gen der Talente
        auswahl = herkunft.getTalente();
        map(auswahl, "talente", xmlElement);

        // Hinzuf�gen der Magie
        final Repraesentation rep = herkunft.getRepraesentation();
        final Auswahl hausz = herkunft.getHauszauber();
        final Auswahl zauber = herkunft.getZauber();
        final Auswahl aktivierbareZauber = herkunft.getAktivierbareZauber();
        final IdLinkList zauberNichtBeginn = herkunft.getZauberNichtBeginn();
        if (rep != null || hausz != null || zauber != null || aktivierbareZauber != null || zauberNichtBeginn != null) {
            e = new Element("magie");

            // Schreiben der Repraesentation
            if (rep != null) {
                e.addAttribute(new Attribute("repraesentId", rep.getId()));
            }

            // Hinzuf�gen der Hauszauber
            map(hausz, "hauszauber", e);

            // Hinzuf�gen der Zauber
            map(zauber, "zauber", e);

            // Hinzuf�gen der aktivierbaren Zauber
            map(aktivierbareZauber, "aktivierbareZauber", e);

            // Hinzuf�gen der nicht zu beginn w�hlbaren Zauber
            map(zauberNichtBeginn, "zauberNichtZuBegin", e);

            xmlElement.appendChild(e);
        }
    }

    private void map(Auswahl auswahl, String tagname, Element parent) {
        if (auswahl != null) {
            final Element e = new Element(tagname);
            final XOMMapper_Auswahl mappy = new XOMMapper_Auswahl();
            mappy.map(auswahl, e);
            parent.appendChild(e);
        }
    }

    private void map(IdLinkList ids, String tagname, Element parent) {
        if (ids != null) {
            final Element e = new Element(tagname);
            XOMMappingHelper.mapIdLinkList(ids, e);
            parent.appendChild(e);
        }

    }
}