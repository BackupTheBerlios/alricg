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

import org.d3s.alricg.CharKomponenten.*;
import org.d3s.alricg.CharKomponenten.CharZusatz.*;
import org.d3s.alricg.CharKomponenten.Links.*;

/**
 * <u>Beschreibung:</u><br> 
 * Diese Klasse dient den Testen von Alricg!
 * @author V. Strelow
 */
public class TestDriver {
	public enum testEnum { EINS, ZWEI, DREI }
	
	public static void main(String[] args) {
		
		TestDriver TD = new TestDriver();
		/*
		
		File x = new File("lala.txt");
		
		try {
			x.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	public TestDriver()  {
		
		testFile();
		//testGeneric();
		//testGeneric(new Integer(4));
	}
	
	public void testFile() {
		File f = new File("ressourcen" + File.separator + "lala.txt");
		System.out.println(f.exists());
		
	}
	
	
	public <T> void testGeneric( T m )
	{
		T blub;
	  	
	  	System.out.println(m.toString());
	  	//return Math.random() > 0.5 ? m : n;
	}
	
	public <T> void getTestGeneric()
	{
	    T;
	  	
	  	System.out.println(m.toString());
	  	//return Math.random() > 0.5 ? m : n;
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
