/*
 * Created on 27.09.2005 / 00:52:11
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */
package org.d3s.alricg.store.xom.map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * TestSuite für alle org.d3s.alricg.store.xom.map Tests.
 * 
 * @author <a href="mailto:msturzen@mac.com>St. Martin</a>
 */
public class AllTests extends TestCase {
    
    public static Test suite() {
        TestSuite testSuite = new TestSuite("All Tests for org.d3s.alricg.store.xom.map");
        testSuite.addTestSuite(XM_Ausruestung_Test.class);
        testSuite.addTestSuite(XM_CharElement_Test.class);
        testSuite.addTestSuite(XM_Faehigkeit_Test.class);
        testSuite.addTestSuite(XM_Fahrzeug_Test.class);
        testSuite.addTestSuite(XM_Gabe_Test.class);
        testSuite.addTestSuite(XM_Gegenstand_Test.class);
        testSuite.addTestSuite(XM_Gottheit_Test.class);
        testSuite.addTestSuite(XM_LiturgieRitualKenntnis_Test.class);
        testSuite.addTestSuite(XM_RegionVolk_Test.class);        
        testSuite.addTestSuite(XM_Repraesentation_Test.class);
        testSuite.addTestSuite(XM_Ritus_Test.class);
        testSuite.addTestSuite(XM_SchriftSprache_Test.class);
        testSuite.addTestSuite(XM_SchwarzeGabe_Test.class);
        testSuite.addTestSuite(XM_SimpelGegenstand_Test.class);
        testSuite.addTestSuite(XM_Talent_Test.class);
        testSuite.addTestSuite(XM_Zauber_Test.class);
        return testSuite;
    }
}
