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
import java.io.IOException;

import nu.xom.Document;
import nu.xom.Serializer;

import org.d3s.alricg.CharKomponenten.CharZusatz.WuerfelSammlung;
import org.d3s.alricg.Controller.ProgAdmin;

/**
 * <u>Beschreibung:</u><br> 
 * Diese Klasse dient den Testen von Alricg!
 * @author V. Strelow
 */
public class TestDriver {
	public enum testEnum { EINS, ZWEI, DREI }
	ProgAdmin pa;
	
	
	public static void main(String[] args) {
		
		TestDriver TD = new TestDriver();
	}
	
	public TestDriver()  {
		
		pa = new ProgAdmin();
		pa.initProgAdmin();
		
		xmlWriteTest();
		
		//testFile();
		//testGeneric();
		//testGeneric(new Integer(4));
	}
	

	public void xmlWriteTest() {
		Document doc = new Document(ProgAdmin.charKompAdmin.writeXML());
		
	    try {
	        Serializer serializer = new Serializer(System.out, "ISO-8859-1");
	        serializer.setIndent(4);
	        serializer.setMaxLength(64);
	        serializer.write(doc);
	      } catch (IOException ex) {
	         System.err.println(ex);
	      }
	}
	
	public void testFile() {
		File f = new File("ressourcen" + File.separator + "lala.txt");
		System.out.println(f.exists());
		
	}
	
	private void testWuerfel() {
		WuerfelSammlung w = new WuerfelSammlung(5, null, new int[]{6});
		
		System.out.println(w.getWuerfelWurf());
		System.out.println(w.getWuerfelWurf());
		System.out.println(w.getWuerfelWurf());
		System.out.println(w.getWuerfelWurf());
		System.out.println(w.getWuerfelWurf());
		System.out.println(w.getWuerfelWurf());
		
	}
	
	private void testEnum() {
		/*int[] i = new int[] {0,1,2,3}; 
		
		
		Eigenschaften e = Eigenschaften.MU;
		
		//System.out.println(i[Eigenschaften.MU]);
		
		switch (e) {
		case Eigenschaften.MU: System.out.println(""); break;
		default:
		}*/
	}
}
