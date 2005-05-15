package org.d3s.alricg.store.xom.map;

import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.LiturgieRitualKenntnis;

class XOMMapper_LiturgieRitualKenntnis extends XOMMapper_Faehigkeit implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final LiturgieRitualKenntnis lrk = (LiturgieRitualKenntnis) charElement;

        // Auslesen ob es zu einer "Göttlichen" oder "Schamanischen" Tradition gehört
        final Element current = xmlElement.getFirstChildElement("istLiturgieKenntnis");
        if (current != null) {
            // Wertebereich prüfen
            assert current.getValue().equals("true") || current.getValue().equals("false");

            lrk.setLiturgieKenntnis(current.getValue().equalsIgnoreCase("true"));
        }
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        final LiturgieRitualKenntnis lrk = (LiturgieRitualKenntnis) charElement;
        xmlElement.setLocalName("liRiKenntnis");

        // Schreiben ob es zu einer "Göttlichen" oder "Schamanischen" Tradition gehört
        Element e = new Element("istLiturgieKenntnis");
        e.appendChild(Boolean.toString(lrk.isLiturgieKenntnis()));
        xmlElement.appendChild(e);
    }
}
