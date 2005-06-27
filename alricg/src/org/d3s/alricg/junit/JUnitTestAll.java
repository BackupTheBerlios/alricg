/*
 * Created on 19.05.2005 / 00:52:11
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.junit;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.junit.controller.NotepadTest;
import org.d3s.alricg.junit.prozessor.FormelSammlungTest;

/**
 * <u>Beschreibung:</u><br> 
 * Über diese Klasse werden die TestCases aufgerufen. Her wird das Programm (Alricg) gestartet, 
 * die TestSuite erstellt und die TestCases hinzugefügt.
 * @author V. Strelow
 */
public class JUnitTestAll {

	public static void main(String[] args) {

	}

	public static Test suite() {
		final String[] param = new String[] { "noScreen" };
		
		// Starten des Programms
		ProgAdmin.main(param);
		
		// Starten der Test-Suite
		TestSuite suite = new TestSuite();
		
		// Hinzufügen der Test-Klassen
		suite.addTestSuite(NotepadTest.class);
		suite.addTestSuite(FormelSammlungTest.class);
	    
	    return suite;
	}
  
}
