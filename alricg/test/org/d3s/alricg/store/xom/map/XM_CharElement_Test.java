/*
 * Created 22. September 2005 / 00:01:02
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */
package org.d3s.alricg.store.xom.map;

import junit.framework.TestCase;
import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.controller.CharKomponente;

public class XM_CharElement_Test extends TestCase {

    private XOMMapper mappy;

    public XM_CharElement_Test(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        mappy = new XOMMapper_CharElementBase();
    }

    public void testMapFromXMLSimple() {
        final Element xom = new Element("CharElementBase");

        String name = "ElementName";
        xom.addAttribute(new Attribute("name", name));

        Element aChild = new Element("anzeigen"); // true, false, fehlt
        aChild.addAttribute(new Attribute("anzeigen", "true"));
        String anzeigenText = "frise fräse fröse";
        aChild.appendChild(anzeigenText);
        xom.appendChild(aChild);

        String beschreibung = "Siehe MBK S. 10";
        Element child = new Element("beschreibung");
        child.appendChild(beschreibung);
        xom.appendChild(child);

        String sammelBegriff = "Rammelbegriff!";
        child = new Element("sammelbegriff");
        child.appendChild(sammelBegriff);
        xom.appendChild(child);

        CharElement charElement = new CharElementBase();
        mappy.map(xom, charElement);
        assertEquals("Name falsch", name, charElement.getName());
        assertTrue("Anzeigen falsch", charElement.isAnzeigen());
        assertEquals("Anzeigen Text falsch", "", charElement.getAnzeigenText());
        assertEquals("Beschreibung falsch", beschreibung, charElement.getBeschreibung());
        assertEquals("Sammelbegriff falsch", sammelBegriff, charElement.getSammelBegriff());

        aChild.getAttribute("anzeigen").setValue("false");
        charElement = new CharElementBase();
        mappy.map(xom, charElement);
        assertEquals("Name falsch", name, charElement.getName());
        assertFalse("Anzeigen falsch", charElement.isAnzeigen());
        assertEquals("Anzeigen Text falsch", anzeigenText, charElement.getAnzeigenText());
        assertEquals("Beschreibung falsch", beschreibung, charElement.getBeschreibung());
        assertEquals("Sammelbegriff falsch", sammelBegriff, charElement.getSammelBegriff());

        aChild.getAttribute("anzeigen").setValue("TRUE");
        charElement = new CharElementBase();
        mappy.map(xom, charElement);
        assertEquals("Name falsch", name, charElement.getName());
        assertTrue("Anzeigen falsch", charElement.isAnzeigen());
        assertEquals("Anzeigen Text falsch", "", charElement.getAnzeigenText());
        assertEquals("Beschreibung falsch", beschreibung, charElement.getBeschreibung());
        assertEquals("Sammelbegriff falsch", sammelBegriff, charElement.getSammelBegriff());

        aChild.getAttribute("anzeigen").setValue("FALSE");
        charElement = new CharElementBase();
        mappy.map(xom, charElement);
        assertEquals("Name falsch", name, charElement.getName());
        assertFalse("Anzeigen falsch", charElement.isAnzeigen());
        assertEquals("Anzeigen Text falsch", anzeigenText, charElement.getAnzeigenText());
        assertEquals("Beschreibung falsch", beschreibung, charElement.getBeschreibung());
        assertEquals("Sammelbegriff falsch", sammelBegriff, charElement.getSammelBegriff());

        aChild.getAttribute("anzeigen").setValue("XXXFALdDSETRUE");
        charElement = new CharElementBase();
        try {
            mappy.map(xom, charElement);
            fail("Exception expected");
        } catch (Throwable t) {
            assertTrue("AssertionException expected", t instanceof AssertionError);
        }

        xom.removeChild(aChild);
        charElement = new CharElementBase();
        mappy.map(xom, charElement);
        assertEquals("Name falsch", name, charElement.getName());
        assertTrue("Anzeigen falsch", charElement.isAnzeigen());
        assertEquals("Anzeigen Text falsch", "", charElement.getAnzeigenText());
        assertEquals("Beschreibung falsch", beschreibung, charElement.getBeschreibung());
        assertEquals("Sammelbegriff falsch", sammelBegriff, charElement.getSammelBegriff());
    }

    public void testMapFromXMLRegelAnmerkungen() {
        // TODO Implement!
    }

    public void testMapFromXMLSonderregeln() {
        // TODO Implement!
    }

    public void testMapToXML() {
        // TODO Implement!
    }

    private class XOMMapper_CharElementBase extends XOMMapper_CharElement {

        public void map(Element xmlElement, CharElement charElement) {
            super.map(xmlElement, charElement);
        }

        public void map(CharElement charElement, Element xmlElement) {
            super.map(charElement, xmlElement);
        }
    }

    private class CharElementBase extends CharElement {

        public CharKomponente getCharKomponente() {
            return null;
        }

    }

}
