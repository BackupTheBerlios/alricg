/*
 * Created on 13.04.2005 / 18:44:13
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.controller;

import java.io.File;

import javax.swing.ImageIcon;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class ImageAdmin {
	private static final String MERKMAL_PFAD = ProgAdmin.PFAD_BILDER 
												+ "zauberMerkmale" + File.separator;
	
	
	public static ImageIcon test;
	
//	Icons für die Zaubermerkmale
	public static ImageIcon zauberMerkmalAntimagie;
	public static ImageIcon zauberMerkmalBeschwoerung = new ImageIcon(MERKMAL_PFAD + "beschwoerung16.png");
	public static ImageIcon zauberMerkmalDaemonisch;
	public static ImageIcon zauberMerkmalEigenschaften;
	public static ImageIcon zauberMerkmalEinfluss;
	public static ImageIcon zauberMerkmalElementar;
	public static ImageIcon zauberMerkmalElementarFeuer = new ImageIcon(MERKMAL_PFAD + "elementarFeuer16.png");
	public static ImageIcon zauberMerkmalElementarWasser = new ImageIcon(MERKMAL_PFAD + "elementarWasser16.png");
	public static ImageIcon zauberMerkmalElementarLuft;
	public static ImageIcon zauberMerkmalElementarErz;
	public static ImageIcon zauberMerkmalElementarHumus;
	public static ImageIcon zauberMerkmalElementarEis = new ImageIcon(MERKMAL_PFAD + "elementarEis16.png");
	public static ImageIcon zauberMerkmalForm;
	public static ImageIcon zauberMerkmalGeisterwesen;
	public static ImageIcon zauberMerkmalHeilung = new ImageIcon(MERKMAL_PFAD + "heilung16.png");;
	public static ImageIcon zauberMerkmalHellsicht;
	public static ImageIcon zauberMerkmalHerbeirufung;
	public static ImageIcon zauberMerkmalHerrschaft;
	public static ImageIcon zauberMerkmalIllusion;
	public static ImageIcon zauberMerkmalKraft;
	public static ImageIcon zauberMerkmalLimbus = new ImageIcon(MERKMAL_PFAD + "limbus16.png");
	public static ImageIcon zauberMerkmalMetamagie;
	public static ImageIcon zauberMerkmalObjekt;
	public static ImageIcon zauberMerkmalSchaden;
	public static ImageIcon zauberMerkmalTelekinese;
	public static ImageIcon zauberMerkmalTemporal;	
	public static ImageIcon zauberMerkmalUmwelt;
	public static ImageIcon zauberMerkmalVerstaendigung;

}
