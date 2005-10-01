/*
 * Created on 23.06.2005 / 15:16:17
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */

package org.d3s.alricg.store.xom.map;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Herkunft;
import org.d3s.alricg.charKomponenten.HerkunftVariante;
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
import org.d3s.alricg.store.FactoryFinder;

/**
 * Hilfklasse (als Singleton implementiert) für das Mapping von und nach xml.
 * 
 * @author <a href="mailto:msturzen@mac.com">St. Martin</a>
 */
final class XOMMappingHelper {
    
    private static final XOMMappingHelper instance = new XOMMappingHelper();
    
    public static XOMMappingHelper instance() {
        return instance;
    }
    
    /**
     * Wählt den korrekten <code>XOMMapper</code> zu einer <code>CharKomponente</code>.
     * 
     * @param charKomp Die Komponente wozu ein passender <code>XOMMapper</code> gesucht wird.
     * @return Der passende Mapper, oder <code>null</code>, falls kein passender gefunden werden konnte.
     */
    public XOMMapper chooseXOMMapper(CharKomponente charKomp) {
        XOMMapper mappy = null;
        switch (charKomp) {
        // >>>>>>>>>>>>>>> Herkunft
        case rasse:
            mappy = new XOMMapper_Rasse();
            break;
        case kultur:
            mappy = new XOMMapper_Kultur();
            break;
        case profession:
            mappy = new XOMMapper_Profession();
            break;
        case zusatzProfession:
            mappy = new XOMMapper_ZusatzProfession();
            break;

        // >>>>>>>>>>>>>>> Fertigkeiten & Fähigkeiten
        case vorteil:
            mappy = new XOMMapper_Vorteil();
            break;
        case gabe:
            mappy = new XOMMapper_Gabe();
            break;
        case nachteil:
            mappy = new XOMMapper_Nachteil();
            break;
        case sonderfertigkeit:
            mappy = new XOMMapper_Sonderfertigkeit();
            break;
        case ritLitKenntnis:
            mappy = new XOMMapper_LiturgieRitualKenntnis();
            break;
        case talent:
            mappy = new XOMMapper_Talent();
            break;
        case zauber:
            mappy = new XOMMapper_Zauber();
            break;

        // >>>>>>>>>>>>>>> Sprachen
        case sprache:
            mappy = new XOMMapper_Sprache();
            break;
        case schrift:
            mappy = new XOMMapper_Schrift();
            break;

        // >>>>>>>>>>>>>>> Götter
        case liturgie:
            mappy = new XOMMapper_Liturgie();
            break;
        case ritual:
            mappy = new XOMMapper_Ritual();
            break;

        // >>>>>>>>>>>>>>> Ausrüstung
        case ausruestung:
            mappy = new XOMMapper_Ausruestung();
            break;
        case fahrzeug:
            mappy = new XOMMapper_Fahrzeug();
            break;
        case waffeNk:
            mappy = new XOMMapper_NahkWaffe();
            break;
        case waffeFk:
            mappy = new XOMMapper_FkWaffe();
            break;
        case ruestung:
            mappy = new XOMMapper_Ruestung();
            break;
        case schild:
            mappy = new XOMMapper_Schild();
            break;

        // >>>>>>>>>>>>>>> Zusätzliches
        case daemonenPakt:
            mappy = new XOMMapper_DaemonenPakt();
            break;
        case schwarzeGabe:
            mappy = new XOMMapper_SchwarzeGabe();
            break;
        case tier:
            mappy = new XOMMapper_Tier();
            break;
        case region:
            mappy = new XOMMapper_RegionVolk();
            break;
        case gottheit:
            mappy = new XOMMapper_Gottheit();
            break;
        case repraesentation:
            mappy = new XOMMapper_Repraesentation();
            break;
        case eigenschaft:
            mappy = null;
            // Eigenschaften werden nicht aus XML gelesen.
            // mappy = new XOMMapper_Eigenschaft();
            // for (int i = 0; i < kategorien.size(); i++) {
            // final Element child = kategorien.get(i);
            // final String id = child.getAttributeValue("id");
            // final CharElement charEl = eigenschaftMap.get(id);
            // mappy.map(child, charEl);
            // }
            break;
        case sonderregel:
            mappy = null;
            break; // Gibt es nicht!

        // >>>>>>>>>>>>>>> DEFAULT
        default:
            mappy = null;
            ProgAdmin.logger.logp(Level.SEVERE, "CharKompAdmin", "initCharKomponents",
                    "Ein CharKomp wurde nicht gefunden: " + charKomp);
        }
        return mappy;
    }

    /**
     * Bildet eine Auswahl in ein xml-Element ab.
     * 
     * @param auswahl Die Auswahl, die ausgelesen werden soll.
     * @param xmlElement Das xml-Element, das geschrieben werden soll.
     */
    public void mapAuswahl(Auswahl auswahl, Element xmlElement) {

        // Schreiben der festen Elemente
        final IdLink[] festeAuswahl = auswahl.getFesteAuswahl();
        for (int i = 0; i < festeAuswahl.length; i++) {
            final Element e = new Element("fest");
            instance.mapIdLink(festeAuswahl[i], e);
            xmlElement.appendChild(e);
        }

        // Schreiben der "variablen" Elemente
        final VariableAuswahl[] varianteAuswahl = auswahl.getVarianteAuswahl();
        for (int i = 0; i < varianteAuswahl.length; i++) {
            final Element e = new Element("auswahl");
            instance.mapVariableAuswahl(varianteAuswahl[i], e);
            xmlElement.appendChild(e);
        }
    }

    /**
     * Bildet ein xml-Element in eine Auswahl ab.
     * 
     * @param xmlElement Das xml-Element mit den Wahlmöglichkeiten.
     * @param auswahl Die Auswahl, der verändert werden soll.
     */
    public void mapAuswahl(Element xmlElement, Auswahl auswahl) {

        // Auslesen der unveränderlichen, "festen" Elemente der Auswahl
        Elements children = xmlElement.getChildElements("fest");
        final IdLink[] festeAuswahl = new IdLink[children.size()];
        for (int i = 0; i < festeAuswahl.length; i++) {
            festeAuswahl[i] = new IdLink(auswahl.getHerkunft(), auswahl);
            instance.mapIdLink(children.get(i), festeAuswahl[i]);
        }
        auswahl.setFesteAuswahl(festeAuswahl);

        // Auslesen der Auswahlmöglichkeiten
        children = xmlElement.getChildElements("auswahl");
        final VariableAuswahl[] varianteAuswahl = new VariableAuswahl[children.size()];
        for (int i = 0; i < varianteAuswahl.length; i++) {
            varianteAuswahl[i] = auswahl.new VariableAuswahl(auswahl);
            instance.mapVariableAuswahl(children.get(i), varianteAuswahl[i]);
        }
        auswahl.setVarianteAuswahl(varianteAuswahl);
    }

    /**
     * Bildet eine AuwahlAusruestung in ein xml-Element ab.
     * 
     * @param ausruestung Die AuswahlAusruestung, die ausgelesen werden soll.
     * @param xmlElement Das xml-Element, das geschrieben werden soll.
     */
    public void mapAuswahlAusruestung(AuswahlAusruestung ausruestung, Element xmlElement) {

        // Schreiben der "festen" Elemente
        HilfsAuswahl festeAuswahl = ausruestung.getFesteAuswahl();
        if (festeAuswahl != null) {
            final Element e = new Element("fest");
            instance.mapHilfsauswahl(festeAuswahl, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der "wählbaren" Elemente
        HilfsAuswahl[] variableAuswahl = ausruestung.getVariableAuswahl();
        for (int i = 0; i < variableAuswahl.length; i++) {
            Element e = new Element("auswahl");
            instance.mapHilfsauswahl(variableAuswahl[i], e);
            xmlElement.appendChild(e);
        }
    }

    /**
     * Bildet ein xml-Element in eine AuswahlAusruestung ab.
     * 
     * @param xmlElement Das xml-Element mit den Ausrüstungs-Wahlmöglichkeiten.
     * @param ausruestung Die AuswahlAusruestung , der verändert werden soll.
     */
    public void mapAuswahlAusruestung(Element xmlElement, AuswahlAusruestung ausruestung) {

        // Auslesen der festen Gegenstände
        Element current = xmlElement.getFirstChildElement("fest");
        if (current != null) {
            final HilfsAuswahl festeAuswahl = ausruestung.new HilfsAuswahl();
            instance.mapHilfsauswahl(current, festeAuswahl, ausruestung.getHerkunft());
            ausruestung.setFesteAuswahl(festeAuswahl);
        }

        // Auslesen der Auswahl
        final Elements children = xmlElement.getChildElements("auswahl");
        HilfsAuswahl[] variableAuswahl = new HilfsAuswahl[children.size()];
        for (int i = 0; i < variableAuswahl.length; i++) {
            variableAuswahl[i] = ausruestung.new HilfsAuswahl();
            instance.mapHilfsauswahl(children.get(i), variableAuswahl[i], ausruestung.getHerkunft());
        }
        ausruestung.setVariableAuswahl(variableAuswahl);

    }

    /**
     * Bildet das Attribut "id" eines xml-Elements in einen Link ab
     * 
     * @param xmlElement Ein xml-Element mit "id" Attribut.
     * @param idLink Der Link, der verändert werden soll.
     */
    public void mapIdLink(Element xmlElement, IdLink idLink) {

        // Typs des Ziels (Talent, Zauber, usw.); muß ein Idlink enthalten
        final String idValue = xmlElement.getAttributeValue("id");
        final CharKomponente charKomp = FactoryFinder.find().getData().getCharKompFromId(idValue);

        // Ziel ID; muß ein Idlink enthalten
        idLink.setZielId(FactoryFinder.find().getData().getCharElement(idValue, charKomp));

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
            idLink.setZweitZiel(FactoryFinder.find().getData().getCharElement(value,
                    FactoryFinder.find().getData().getCharKompFromId(value)));
        }

    }

    /**
     * Bildet einen Link im "id"-Attribut eines xml-Elements ab.
     * 
     * @param idLink Der Link, der ausgelesen werden soll.
     * @param xmlElement Das xml-Element, dessen "id"-Attribut gesetzt werden soll.
     */
    public void mapIdLink(IdLink idLink, Element xmlElement) {

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

    /**
     * Bildet das Attribut "ids" eines xml-Elements in eine Liste ab.
     * 
     * @param xmlElement Ein xml-Element mit "ids" Attribut.
     * @param idLinkList Die Liste, wozu neue links hinzugefügt werden sollen.
     */
    public void mapIdLinkList(Element xmlElement, IdLinkList idLinkList) {
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

    /**
     * Bildet eine Link-Liste in das Attribut "ids" eines xml-Elements ab.
     * 
     * @param idLinkList Die Liste, deren Elemente ausgelesen werden sollen.
     * @param xmlElement Das xml-Element, dessen "ids" Attribut gesetzt werden soll.
     */
    public void mapIdLinkList(IdLinkList idLinkList, Element xmlElement) {

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

        // Attribut hinzufügen, falls mindestens ein element vorhanden
        if (buffy.length() > 0) {
            final Attribute a = new Attribute("ids", buffy.toString().trim());
            xmlElement.addAttribute(a);
        }
    }

    /**
     * Bildet ein xml-Element in eine Link-Voraussetzung ab.
     * 
     * @param xmlElement Das xml-Element mit den Link-Voraussetzungen.
     * @param idLinkV Die Link-Voraussetzung, der verändert werden soll.
     */
    public void mapIdLinkVoraussetzung(Element xmlElement, IdLinkVoraussetzung idLinkV) {
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

    /**
     * Bildet eine Link-Voraussetzung in ein xml-Element ab.
     * 
     * @param idLinkV Die Link-Voraussetzung, die ausgelesen werden soll.
     * @param xmlElement Das xml-Element, das geschrieben werden soll.
     */
    public void mapIdLinkVoraussetzung(IdLinkVoraussetzung idLinkV, Element xmlElement) {
        mapIdLink(idLinkV, xmlElement);

        // Hinzufügen der "wertGrenze", falls nicht Default wert
        xmlElement.addAttribute(new Attribute("wertGrenze", idLinkV.isMinimum() ? "min" : "max"));
    }

    /**
     * Bildet ein xml-Element in eine Rasse-, Kultur- oder Profession-Variante einer Herkunft ab.
     * 
     * @param xmlElement Das xml-Element worin die Daten der Variante entahlten sind.
     * @param herkunftV Die Variante, die verändert werden soll.
     * @param mapper Der zu verwendende Mapper für Rasse, Kultur oder Profession
     */
    public void mapVarianten(Element xmlElement, HerkunftVariante herkunftV, Herkunft herkunft,
            XOMMapper mapper) {
        mapper.map(xmlElement, (CharElement) herkunftV);

        // my mapping

        // Auslesen der Elemente die von dem "original" entfernd werden sollen
        Element current = xmlElement.getFirstChildElement("entferneElement");
        if (current != null) {
            final IdLinkList ids = new IdLinkList((Herkunft) herkunftV);
            instance.mapIdLinkList(current, ids);
            herkunftV.setEntferneElement(ids);
        }

        // Auslesen der XML-Tags die von dem "original" entfernd werden sollen
        current = xmlElement.getFirstChildElement("entferneXmlTag");
        if (current != null) {
            String[] strAr = current.getValue().split(",");
            for (int i = 0; i < strAr.length; i++) {
                strAr[i] = strAr[i].trim();
            }
            herkunftV.setEntferneXmlTag(strAr);
        }

        // Auslesen des Attribus "isMultibel"
        Attribute a = xmlElement.getAttribute("isMultibel");

        // Sicherstellen, das der Wert gültig ist
        assert a.getValue().equals("false") || a.getValue().equals("true");
        herkunftV.setMultibel(a.getValue().equals("true"));

        // Auslesen des Attribus "isAdditionsVariante"
        a = xmlElement.getAttribute("isAdditionsVariante");

        // Sicherstellen, das der Wert gültig ist
        assert a.getValue().equals("false") || a.getValue().equals("true");
        herkunftV.setAdditionsVariante(a.getValue().equals("true"));

        // Setzen von welcher Herkunft dies eine Variante ist
        herkunftV.setVarianteVon(herkunft);
    }

    /**
     * Bildet eine eine Rasse-, Kultur- oder Profession-Variante in ein xml-Element ab.
     * 
     * @param herkunftV Die Variante, die ausgelesen werden soll.
     * @param xmlElement Das xml-Element, das geschrieben werden soll.
     * @param mapper Der zu verwendende Mapper für Rasse, Kultur oder Profession
     */
    public void mapVarianten(HerkunftVariante herkunftV, Element xmlElement, XOMMapper mapper) {

        mapper.map((CharElement) herkunftV, xmlElement);
        StringBuffer strBuffer = new StringBuffer();

        // my mapping
        xmlElement.setLocalName("variante");

        // Schreiben der Elemente, die aus dem "original" entfernd werden sollen
        IdLinkList ids = herkunftV.getEntferneElement();
        if (ids != null) {
            final Element e = new Element("entferneElement");
            instance.mapIdLinkList(ids, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der XML-Tags die entfernd werden sollen
        String[] stringAr = herkunftV.getEntferneXmlTag();
        if (stringAr != null) {
            for (int i = 0; i < stringAr.length - 1; i++) {
                strBuffer.append(stringAr[i]).append(",");
            }
            xmlElement.appendChild(strBuffer.toString());
        }

        // Schreiben ob Multibel, wenn nicht default
        if (herkunftV.isMultibel()) {
            xmlElement.addAttribute(new Attribute("isMultibel", "true"));
        }

        // Schreiben ob Multibel, wenn nicht default
        if (!herkunftV.isAdditionsVariante()) {
            xmlElement.addAttribute(new Attribute("isAdditionsVariante", "false"));
        }
    }

    /**
     * Bildet ein xml-Element in eine Voraussetzung ab.
     * 
     * @param xmlElement Das xml-Element mit den Voraussetzungen.
     * @param voraussetzung Die Voraussetzung, der verändert werden soll.
     */
    public void mapVoraussetzung(Element xmlElement, Voraussetzung voraussetzung) {

        // Auslesen der "festen" Elemente
        Elements children = xmlElement.getChildElements("fest");
        IdLinkVoraussetzung[] festeVoraussetzung = new IdLinkVoraussetzung[children.size()];
        for (int i = 0; i < children.size(); i++) {
            festeVoraussetzung[i] = voraussetzung.new IdLinkVoraussetzung(voraussetzung.getQuelle());
            instance.mapIdLinkVoraussetzung(children.get(i), festeVoraussetzung[i]);
        }
        voraussetzung.setFesteVoraussetzung(festeVoraussetzung);

        // Auslesen der "nichtErlaubt" Elemente
        Element current = xmlElement.getFirstChildElement("nichtErlaubt");
        if (current != null) {
            final IdLinkList nichtErlaubt = new IdLinkList(voraussetzung.getQuelle());
            instance.mapIdLinkList(current, nichtErlaubt);
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
                instance.mapIdLinkVoraussetzung(options.get(ii), auswahlVoraussetzung[i][ii]);
            }
        }
        voraussetzung.setAuswahlVoraussetzung(auswahlVoraussetzung);
    }

    /**
     * Bildet eine Voraussetzung in ein xml-Element ab.
     * 
     * @param voraussetzung Die Voraussetzung, die ausgelesen werden soll.
     * @param xmlElement Das xml-Element, das geschrieben werden soll.
     */
    public void mapVoraussetzung(Voraussetzung voraussetzung, Element xmlElement) {

        // Alle "festen" Elemente hinzufügen
        final IdLinkVoraussetzung[] festeVoraussetzung = voraussetzung.getFesteVoraussetzung();
        for (int i = 0; i < festeVoraussetzung.length; i++) {
            final Element e = new Element("fest");
            instance.mapIdLinkVoraussetzung(festeVoraussetzung[i], e);
            xmlElement.appendChild(e);
        }

        // Hinzufügen der "nichtErlaubt" Elemente
        final IdLinkList nichtErlaubt = voraussetzung.getNichtErlaubt();
        if (nichtErlaubt != null) {
            final Element e = new Element("empfVorteile");
            instance.mapIdLinkList(nichtErlaubt, e);
            xmlElement.appendChild(e);
        }

        // Alle Elemente der "Auswahl" hinzufügen
        final IdLinkVoraussetzung[][] auswahlVoraussetzung = voraussetzung.getAuswahlVoraussetzung();
        for (int i = 0; i < auswahlVoraussetzung.length; i++) {
            final Element e = new Element("auswahl");
            for (int ii = 0; ii < auswahlVoraussetzung[i].length; ii++) {
                final Element ee = new Element("option");
                instance.mapIdLinkVoraussetzung(auswahlVoraussetzung[i][ii], ee);
                e.appendChild(ee);
            }
            xmlElement.appendChild(e);
        }
    }

    /**
     * Bildet ein xml-Element in eine Hilfsauswahl ab und verknüpft diese mit herkunft.
     * 
     * @param xmlElement Das xml-Element mit den Daten der hilfsauswahl.
     * @param hilfsauswahl Die HilfsAuswahl, die verändert werden soll.
     * @param herkunft Die Herkunft mit der die Links der Auwahl verknüft werden sollen
     */
    private void mapHilfsauswahl(Element xmlElement, HilfsAuswahl hilfsauswahl, Herkunft herkunft) {

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
            instance.mapIdLink(children.get(i), links[i]);
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

    /**
     * Bildet eine HilfsAuswahl in ein xml-Element ab.
     * 
     * @param hilfsauswahl Die Hilfsauswahl, die ausgelesen werden soll.
     * @param xmlElement Das xml-Element, das geschrieben werden soll.
     */
    private void mapHilfsauswahl(HilfsAuswahl hilfsauswahl, Element xmlElement) {

        // Schreiben der Anzahl
        final int anzahl = hilfsauswahl.getAnzahl();
        if (anzahl != 1) {
            xmlElement.addAttribute(new Attribute("anzahl", Integer.toString(anzahl)));
        }

        // Schreiben der Links
        final IdLink[] links = hilfsauswahl.getLinks();
        for (int i = 0; i < links.length; i++) {
            final Element e = new Element("ausruestungLink");
            instance.mapIdLink(links[i], e);
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

    /**
     * Bildet ein xml-Element in eine VariableAuswahl ab.
     * 
     * @param xmlElement Das xml-Element mit der variablen Auswahl.
     * @param variableAuswahl Die VariableAuswahl, der verändert werden soll.
     */
    private void mapVariableAuswahl(Element xmlElement, VariableAuswahl variableAuswahl) {

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

            // Einlesen des Maximalen Wertes / optional
            if (xmlElement.getAttribute("max") != null) {
                variableAuswahl.setMax(Integer.parseInt(xmlElement.getAttributeValue("max")));
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

    /**
     * Bildet eine VariableAuswahl in ein xml-Element ab.
     * 
     * @param variableAuswahl Die VariableAuswahl, die ausgelesen werden soll.
     * @param xmlElement Das xml-Element, das geschrieben werden soll.
     */
    private void mapVariableAuswahl(VariableAuswahl variableAuswahl, Element xmlElement) {

        // Schreiben der einzelnen Optionen:
        final IdLink[] optionen = variableAuswahl.getOptionen();
        for (int i = 0; i < optionen.length; i++) {
            final Element e = new Element("option");
            instance.mapIdLink(optionen[i], e);
            xmlElement.appendChild(e);
        }

        // Schreiben der Optionsgruppen:
        final IdLink[][] optionsGruppe = variableAuswahl.getOptionsGruppe();
        if (optionsGruppe != null) {
            for (int i = 0; i < optionsGruppe.length; i++) {
                final Element e = new Element("optionsGruppe");
                for (int ii = 0; ii < optionsGruppe[i].length; ii++) {
                    final Element ee = new Element("option");
                    instance.mapIdLink(optionsGruppe[i][ii], ee);
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

        // Schreiben des Attribus "max"
        final int max = variableAuswahl.getMax();
        if (max != 0) {
            xmlElement.addAttribute(new Attribute("max", Integer.toString(max)));
        }

        // Schreiben des Attributs "modus"
        xmlElement.addAttribute(new Attribute("modus", variableAuswahl.getModus().getValue()));
    }

    /**
     * Bildet ein xml-Element unter Berücksichtigung der variablen Auswahl in eine Liste von Links ab.
     * 
     * @param xmlElements Das xml-Element mit den ID-Verknüpfungen.
     * @param va Die zu berücksichtigende VariableAuswahl
     * @return Ein Array, der die ID-Links des xml-Elements enthält
     */
    private IdLink[] readOptionen(Elements xmlElements, VariableAuswahl va) {
        final IdLink[] optionen = new IdLink[xmlElements.size()];
        for (int i = 0; i < optionen.length; i++) {
            optionen[i] = new IdLink(va.getAuswahl().getHerkunft(), va.getAuswahl());
            instance.mapIdLink(xmlElements.get(i), optionen[i]);
        }
        return optionen;
    }

    /**
     * Erzeugt eine neue Helper-Instanz. <code>private</code>, da nur statische Methoden vorhanden sind.
     */
    private XOMMappingHelper() {

    }
}
