/*
 * Created on 21.07.2005 / 10:36:45
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */
package org.d3s.alricg.sonderregeln;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <u>Beschreibung:</u><br> 
 * Eine Test-Suite f�r alle Sonderregeln in Alricg.
 *  
 * @author V. Strelow
 */
public class JUnitTestSonderregeln {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.d3s.alricg.sonderregeln");
		
		//$JUnit-BEGIN$
		suite.addTestSuite(HerausragendeEigenschaftTest.class);
		suite.addTestSuite(BegabungFuerTalentTest.class);
		suite.addTestSuite(StubenhockerTest.class);
		suite.addTestSuite(BegabungFuerTalentgruppeTest.class);
		//$JUnit-END$
		
		return suite;
	}

}