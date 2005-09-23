/*
 * Created on 09.06.2005 / 12:40:04
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */
package org.d3s.alricg.controller;

import junit.framework.TestCase;

import org.d3s.alricg.controller.Notepad;

/**
 * <u>Beschreibung:</u><br> 
 * JUnit Test der Klasse Notepad.
 * @author V. Strelow
 */
public class NotepadTest extends TestCase {
	private Notepad notepad;
	private final String text1 = "test1";
	private final String text2 = "test2";
	private final String text3 = "test3";
	private final String titel = "titel";
	private final String erwartetesErgebnis =
		"<html>"
		+ "&#8226; " + text1 + "<br>"
		+ "&#8226; " + text2 + "<br>"
		+ "&#8226; " + text3
		+ "</html>";

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		notepad = new Notepad();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		notepad = null;
	}
	
	/**
	 * - Testet ob Nachrichten erwartungsgemäß gespeichert werden
	 * - Testet das NUR primäre Nachrichten gespeichert werden
	 */
	public void testAddPrimaryMsg() {
		notepad.startMessage(titel);
		notepad.setStoreSecondary(false);
		
		notepad.addPrimaryMsg(text1);
		notepad.addSecondaryMsg("störText");
		notepad.addPrimaryMsg(text2);
		notepad.addSecondaryMsg("störText");
		notepad.addPrimaryMsg(text3);
		notepad.addSecondaryMsg("störText");
		
		notepad.endMessage();
		
		assertEquals(erwartetesErgebnis, notepad.getLastMessage().getText());
		assertEquals(titel, notepad.getLastMessage().getTitel());
	}

	/**
	 * - Testet ob Nachrichten erwartungsgemäß gespeichert werden
	 * - Testet ob Primär und Sekundär Nachrichten gespeichert werden
	 */
	public void testAddSecondaryMsg() {
		notepad.startMessage(titel);
		notepad.setStoreSecondary(true);
		
		notepad.addPrimaryMsg(text1);
		notepad.addSecondaryMsg(text2);
		notepad.addSecondaryMsg(text3);
		
		notepad.endMessage();
		
		assertEquals(erwartetesErgebnis, notepad.getLastMessage().getText());
		assertEquals(titel, notepad.getLastMessage().getTitel());
	}

}
