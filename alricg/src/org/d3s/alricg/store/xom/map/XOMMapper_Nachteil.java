package org.d3s.alricg.store.xom.map;

import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Nachteil;

class XOMMapper_Nachteil extends XOMMapper_VorNachteil implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final Nachteil nachteil = (Nachteil) charElement;

        // Lesen ob es sich um eine schlechte eigenschaft handelt
        final Element current = xmlElement.getFirstChildElement("istSchlechteEigen");
        if (current != null) {

            // Prüfen des Wertebereichs
            assert current.getValue().equalsIgnoreCase("true") || current.getValue().equalsIgnoreCase("false");

            nachteil.setSchlechteEigen(current.getValue().equalsIgnoreCase("true"));
        }
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        final Nachteil nachteil = (Nachteil) charElement;
        xmlElement.setLocalName("nachteil");

        final Element e = new Element("istSchlechteEigen");
        e.appendChild(Boolean.toString(nachteil.isSchlechteEigen()));
        xmlElement.appendChild(e);
    }
}
