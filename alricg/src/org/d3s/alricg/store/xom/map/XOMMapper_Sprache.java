package org.d3s.alricg.store.xom.map;

import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Sprache;
import org.d3s.alricg.charKomponenten.links.IdLinkList;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.prozessor.FormelSammlung;

class XOMMapper_Sprache extends XOMMapper_SchriftSprache implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final Sprache sprache = (Sprache) charElement;

        // Auslesen der Kostenklasse der Sprache
        Element current = xmlElement.getFirstChildElement("nichtMuttersprache");
        String kostenklasse = current.getAttributeValue("kostenKlasse");
        sprache.setKostenNichtMutterSpr(FormelSammlung.getKostenKlasseByValue(kostenklasse));

        // Auslesen der Kompl�xit�t der Sprache
        try {
            sprache.setKomplexNichtMutterSpr(Integer.parseInt(current.getAttributeValue("komplexitaet")));
        } catch (NumberFormatException exc) {
            ProgAdmin.logger.log(Level.SEVERE, "String -> int failed", exc);
        }

        // Auslesen der dazugeh�rigen Schrift(en)
        current = xmlElement.getFirstChildElement("schriften");
        if (current != null) {
            final IdLinkList zugehoerigeSchrift = new IdLinkList(sprache);
            XOMMappingHelper.mapIdLinkList(current, zugehoerigeSchrift);
            sprache.setZugehoerigeSchrift(zugehoerigeSchrift);
        }
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        final Sprache sprache = (Sprache) charElement;
        xmlElement.setLocalName("sprache");

        // Schreiben der Kostenklasse
        Element e = new Element("nichtMuttersprache");
        xmlElement.appendChild(e);
        String kosten = sprache.getKostenNichtMutterSpr().getValue();
        e.addAttribute(new Attribute("kostenKlasse", kosten));

        // Schreiben der Komplexit�t
        String komplexitaet = Integer.toString(sprache.getKomplexNichtMutterSpr());
        e.addAttribute(new Attribute("komplexitaet", komplexitaet));

        // Schreiben der dazugeh�rigen Schrift(en)
        final IdLinkList zugehoerigeSchrift = sprache.getZugehoerigeSchrift();
        if (zugehoerigeSchrift != null) {
            e = new Element("schriften");
            XOMMappingHelper.mapIdLinkList(zugehoerigeSchrift, e);
            xmlElement.appendChild(e);
        }

    }

}