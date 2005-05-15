package org.d3s.alricg.store.xom.map;

import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;

interface XOMMapper {

    /** 
     * Maps from the <code>xom</code> to the <code>CharElement</code> model
     * @param xmlElement
     * @param charElement
     */
    void map(Element xmlElement, CharElement charElement);

    /**
     * Maps from the <code>CharElement</code> to the <code>xom</code> model.
     * @param charElement
     * @param xmlElement
     */
    void map(CharElement charElement, Element xmlElement);
}
