/*
 * Created 22. September 2005 / 00:01:02
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */
package org.d3s.alricg.store.xom.map;

import java.io.File;
import java.util.logging.Logger;

import junit.framework.TestCase;
import nu.xom.Attribute;
import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.EigenschaftEnum;
import org.d3s.alricg.charKomponenten.Faehigkeit;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.MessengerMock;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;
import org.d3s.alricg.store.FactoryFinder;

/**
 * Tests für XOMMapper_Faehigkeit
 * @author <a href="mailto:msturzen@mac.com>St. Martin</a>
 *
 */
public class XM_Faehigkeit_Test extends TestCase {

    private XOMMapper mappy;

    public XM_Faehigkeit_Test(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        ProgAdmin.logger = Logger.getLogger(XM_Faehigkeit_Test.class.getName());
        ProgAdmin.messenger = new MessengerMock();

        FactoryFinder.init(new File("test/org/d3s/alricg/store/StoreFactoryFinder.properties"));
        mappy = new XOMMapper_FaehigkeitBase();
    }

    public void testMapFromXMLSimple() {
        final Element xom = new Element("FaehigkeitBase");
        final Element probe = new Element("probenWurf");
        probe.addAttribute(new Attribute("eigen1", "EIG-MU"));
        probe.addAttribute(new Attribute("eigen2", "EIG-FF"));
        probe.addAttribute(new Attribute("eigen3", "EIG-KK"));
        xom.appendChild(probe);
        xom.addAttribute(new Attribute("kostenKlasse", "A"));

        Faehigkeit f = new FaehigkeitBase();
        mappy.map(xom, f);

        assertEquals("Eigenschaft 1 falsch", EigenschaftEnum.MU, f.get3Eigenschaften()[0].getEigenschaftEnum());
        assertEquals("Eigenschaft 2 falsch", EigenschaftEnum.FF, f.get3Eigenschaften()[1].getEigenschaftEnum());
        assertEquals("Eigenschaft 3 falsch", EigenschaftEnum.KK, f.get3Eigenschaften()[2].getEigenschaftEnum());
        assertEquals("Eigenachaften falsch", "MU/FF/KK", f.get3EigenschaftenString());
        assertEquals("KostenKlasse falsch", KostenKlasse.A, f.getKostenKlasse());
        assertEquals("KostenKlasse falsch", "A", f.getKostenKlasse().getValue());
    }
    
    public void testMapToXML() {
        // TODO Implement!
    }

    /**
     * Konkrete "do-nothing-special" Impl. der abtrakten Superklasse XOMMapper_Faehigkeit
     * 
     * @author <a href="mailto:msturzen@mac.com>St. Martin</a>
     */
    private class XOMMapper_FaehigkeitBase extends XOMMapper_Faehigkeit {
        public void map(Element xmlElement, CharElement charElement) {
            super.map(xmlElement, charElement);
        }

        public void map(CharElement charElement, Element xmlElement) {
            super.map(charElement, xmlElement);
        }
    }

    /**
     * Konkrete "do-nothing-special" Impl. der abtrakten Superklasse Faehigkeit
     * 
     * @author <a href="mailto:msturzen@mac.com>St. Martin</a>
     */
    private class FaehigkeitBase extends Faehigkeit {
        public CharKomponente getCharKomponente() {
            return null;
        }
    }
}
