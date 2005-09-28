/*
 * Created on 27.09.2005 / 00:52:11
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */
package org.d3s.alricg;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * TestSuite für alle Tests von Alricg.
 * 
 * @author <a href="mailto:msturzen@mac.com>St. Martin</a>
 */
public class AllPackagesTests extends TestCase {
    
    public static Test suite() {
        TestSuite testSuite = new TestSuite("All Tests for Alricg");
        testSuite.addTestSuite(org.d3s.alricg.AllTests.class);
        testSuite.addTestSuite(org.d3s.alricg.controller.AllTests.class);
        testSuite.addTestSuite(org.d3s.alricg.prozessor.AllTests.class);
        testSuite.addTestSuite(org.d3s.alricg.prozessor.generierung.AllTests.class);
        testSuite.addTestSuite(org.d3s.alricg.sonderregeln.AllTests.class);
        testSuite.addTestSuite(org.d3s.alricg.store.AllTests.class);
        testSuite.addTestSuite(org.d3s.alricg.store.xom.AllTests.class);
        testSuite.addTestSuite(org.d3s.alricg.store.xom.map.AllTests.class);
        return testSuite;
    }
}
