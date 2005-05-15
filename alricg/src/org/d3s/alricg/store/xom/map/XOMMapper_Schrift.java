package org.d3s.alricg.store.xom.map;

import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;

class XOMMapper_Schrift extends XOMMapper_SchriftSprache implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        // nothing to do ...
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        xmlElement.setLocalName("schrift");

        // nothing left to do ...

    }

}