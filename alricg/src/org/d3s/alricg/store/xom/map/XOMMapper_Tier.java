package org.d3s.alricg.store.xom.map;

import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;

class XOMMapper_Tier extends XOMMapper_CharElement implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);        
        xmlElement.setLocalName("tier");
    }
}
