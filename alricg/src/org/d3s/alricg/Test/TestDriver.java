/*
 * Created on 21.01.2005 / 16:20:34
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.Test;

import java.io.File;

/**
 * <u>Beschreibung:</u><br> 
 * Diese Klasse dient den Testen von Alricg!
 * @author V. Strelow
 */
public class TestDriver {

	public static void main(String[] args) {
		File x = new File("lala.txt");
		
		try {
			x.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
