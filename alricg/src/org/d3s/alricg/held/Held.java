/*
 * Created 23. Januar 2005 / 15:30:43
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.held;

import java.util.HashMap;

import org.d3s.alricg.charKomponenten.EigenschaftEnum;
import org.d3s.alricg.charKomponenten.Kultur;
import org.d3s.alricg.charKomponenten.Profession;
import org.d3s.alricg.charKomponenten.Rasse;
import org.d3s.alricg.charKomponenten.Sprache;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;
import org.d3s.alricg.prozessor.FormelSammlung;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.HeldUtilitis;
import org.d3s.alricg.prozessor.LinkElementBox;
import org.d3s.alricg.prozessor.SonderregelAdmin;

/**
 * <b>Beschreibung:</b><br>
 * Fast die Daten die einen Helden ausmachen zusammen.
 * @author V.Strelow
 */
public class Held {

//	Die Herkunft
	private Rasse rasse;
	private Kultur kultur;
	private Profession profession;
		
// 	Allgemeine Daten des Helden
	private String Name;
	private String titel;
	private String stand;
	private String geschlecht; //TODO Klasse ändern
	private String geburtstag;
	private String zeitmass; // Ob n.Hal, v.Hal, n. Bosparans Fall, usw.
	private int groesse;
	private String haarfarbe;
	private String augenfarbe;
	private String geburtsort;
	private String aussehen;
	private String herkunft;
	private String beschreibung;

	// Alle CharElemente des Helden, nach Komponeten Sortiert
	private HashMap<CharKomponente, LinkElementBox> boxenHash;
	
	// Die Eigenschaften, diese sind auch in der "heldKomponetenBoxen"
	// enthalten, aber wegen ihrer besonderen Bedeutung nochmal hier
	private HashMap<EigenschaftEnum, HeldenLink> eigenschaftHash;
	
	private Sprache[] muttersprache;  // kann mehrere geben, siehe "Golbin Festumer G"

	private int abenteuerPunkte;
	private CharLogBuch lnkLogBuch;
	
	public static HeldUtilitis heldUtils; 
	public HeldProzessor heldProzessor; // Prozessor mit dem der Held bearbeitet wird
	
	
	public void init() {
		EigenschaftEnum[] enums = EigenschaftEnum.values();
		
		// TODO boxenHash füllen!
		// Die Eigenschaften werden auf einen Wert von "0" initialisiert
		
		// Da Eigenschaften von größerer Bedeutung sind werden sie ZUSÄTZLICH
		// direkt in ein Hash gesichert für einfachen Zugriff
		for (int i = 0; i < enums.length; i++) {
			eigenschaftHash.put(enums[i], 
					heldProzessor.getLinkById(enums[i].getId(), null, null)
			);
		}		
	}
	
	
	public LinkElementBox getElementBox(CharKomponente komponente) {
		return boxenHash.get(komponente);
	}

	public SonderregelAdmin getSonderregelAdmin() {
		// TODO implement! 
		return null;
	}
	
	public HeldProzessor getHeldProzessor() {
		return heldProzessor;
	}
	
	
	/**
	 * Ermöglicht einen einfachen Zugriff auf die Eigenschaften (alle die in 
	 * der enum "EigenschaftEnum" stehen). Auf die Eigenschaften kann auch mittels
	 * "getElementBox(...)" zugegriffen werden, es sollte jedoch diese Methode benutzt 
	 * werden.
	 * VORSICHT: Alle Eigenschaften die errechnet werden, werden nur über diese Methode
	 * 			den korrekten wert liefern, ansonsten nur den Wert der Modis.  
	 * 
	 * @param eigen Die gewünschte Eigenschaft
	 * @return Der Aktuelle Wert dieser Eigenschaft
	 */
	public int getEigenschaftsWert(EigenschaftEnum eigen) {
		
		switch (eigen) {
		
		case LEP: return FormelSammlung.berechneLep(
				eigenschaftHash.get(EigenschaftEnum.KO).getWert(),
				eigenschaftHash.get(EigenschaftEnum.KK).getWert()		
			)
			+ eigenschaftHash.get(EigenschaftEnum.LEP).getWert();
	
		case ASP: return FormelSammlung.berechneAsp(
				eigenschaftHash.get(EigenschaftEnum.MU).getWert(),
				eigenschaftHash.get(EigenschaftEnum.IN).getWert(),
				eigenschaftHash.get(EigenschaftEnum.CH).getWert()
			)
			+ eigenschaftHash.get(EigenschaftEnum.ASP).getWert();
			
		case AUP: return FormelSammlung.berechneAup(
				eigenschaftHash.get(EigenschaftEnum.MU).getWert(),
				eigenschaftHash.get(EigenschaftEnum.KO).getWert(),
				eigenschaftHash.get(EigenschaftEnum.GE).getWert()
			)
			+ eigenschaftHash.get(EigenschaftEnum.AUP).getWert();
		
		case AT: return FormelSammlung.berechneAtBasis(
				eigenschaftHash.get(EigenschaftEnum.MU).getWert(),
				eigenschaftHash.get(EigenschaftEnum.GE).getWert(),
				eigenschaftHash.get(EigenschaftEnum.KK).getWert()
			)
			+ eigenschaftHash.get(EigenschaftEnum.AT).getWert();
				
		case PA: return FormelSammlung.berechnePaBasis(
				eigenschaftHash.get(EigenschaftEnum.IN).getWert(),
				eigenschaftHash.get(EigenschaftEnum.GE).getWert(),
				eigenschaftHash.get(EigenschaftEnum.KK).getWert()
			) 
			+ eigenschaftHash.get(EigenschaftEnum.PA).getWert();
		
		case FK: return FormelSammlung.berechneFkBasis(
				eigenschaftHash.get(EigenschaftEnum.IN).getWert(),
				eigenschaftHash.get(EigenschaftEnum.FF).getWert(),
				eigenschaftHash.get(EigenschaftEnum.KK).getWert()
			) 
			+ eigenschaftHash.get(EigenschaftEnum.FK).getWert();
			
		case INI: return FormelSammlung.berechneINI(
				eigenschaftHash.get(EigenschaftEnum.MU).getWert(),
				eigenschaftHash.get(EigenschaftEnum.IN).getWert(),
				eigenschaftHash.get(EigenschaftEnum.GE).getWert()
			) 
			+ eigenschaftHash.get(EigenschaftEnum.INI).getWert();
			
		case MR: return FormelSammlung.berechneMR(
				eigenschaftHash.get(EigenschaftEnum.MU).getWert(),
				eigenschaftHash.get(EigenschaftEnum.KL).getWert(),
				eigenschaftHash.get(EigenschaftEnum.KO).getWert()
			) 
			+ eigenschaftHash.get(EigenschaftEnum.MR).getWert();
			
		default: // Es handelt sich um MU - KK, KE, GS oder SO
			// Da hier nicht errechnet werden muß, werden die Werte direkt geliefert
			return eigenschaftHash.get(eigen).getWert();
		}

	}
	
	/**
	 * Ermöglicht einen einfachen Zugriff auf die Links zu den Eigenschaften (alle die in 
	 * der enum "EigenschaftEnum" stehen). Auf die Eigenschaften kann auch mittels
	 * "getElementBox(...)" zugegriffen werden, es sollte jedoch diese Methode benutzt 
	 * werden.
	 * Diese Methode ist Sinnvoll wenn die Links benötigt werden, um diese zu bearbeiten.
	 * 
	 * VORSICHT: Bei alle Eigenschaften die errechnet werden, wird hier nicht der korrekte
	 * 			Wert im Link stehen, sondern nur die Modifikationen des Wertes. 
	 * 			Soll der Wert abgefragt werde, sollte "getEigenschaftsWert(..)" benutzt werden.
	 * 
	 * @param eigen Die gewünschte Eigenschaft
	 * @return Der Aktuelle Wert dieser Eigenschaft
	 *
	public HeldenLink getEigenschaftLink(EigenschaftEnum eigen) {
		return eigenschaftHash.get(eigen);
	}*/
}
