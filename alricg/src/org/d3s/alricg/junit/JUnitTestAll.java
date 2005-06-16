/*
 * Created on 19.05.2005 / 00:52:11
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.junit;

import org.d3s.alricg.junit.controller.NotepadTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class JUnitTestAll {

	public static void main(String[] args) {
		
	}

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(NotepadTest.class);
    
    return suite;
  }
  
}
