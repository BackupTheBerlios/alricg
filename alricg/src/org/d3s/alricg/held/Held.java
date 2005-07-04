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
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.prozessor.FormelSammlung;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.HeldUtilitis;
import org.d3s.alricg.prozessor.LinkElementBox;
import org.d3s.alricg.prozessor.generierung.AbstractBoxGen;
import org.d3s.alricg.prozessor.generierung.EigenschaftBoxGen;
import org.d3s.alricg.prozessor.generierung.NachteilBoxGen;
import org.d3s.alricg.prozessor.generierung.SonderfBoxGen;
import org.d3s.alricg.prozessor.generierung.TalentBoxGen;
import org.d3s.alricg.prozessor.generierung.VorteilBoxGen;
import org.d3s.alricg.prozessor.generierung.ZauberBoxGen;

/**
 * <b>Beschreibung:</b><br>
 * Fast die Daten die einen Helden ausmachen zusammen.
 * 
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
	private HashMap<CharKomponente, AbstractBoxGen> boxenHash;
	
	// Die Eigenschaften, diese sind auch in der "heldKomponetenBoxen"
	// enthalten, aber wegen ihrer besonderen Bedeutung nochmal hier
	private HashMap<EigenschaftEnum, HeldenLink> eigenschaftHash;
	
	private Sprache[] muttersprache;  // kann mehrere geben, siehe "Golbin Festumer G"
	private Sprache[] zweitsprache; 
	private Sprache[] lehrsprache; 
	
	private int abenteuerPunkte;
	private CharLogBuch lnkLogBuch;
	
	public static HeldUtilitis heldUtils; 
	//private HeldProzessor heldProzessor; // Prozessor mit dem der Held bearbeitet wird
	
	/**
	 * Konstruktor. Erzeugt einen neuen Helden, nur mit den Eigenschaften ausgestattet.
	 */
	public Held() {
		heldUtils = new HeldUtilitis();
	}
	
	/**
	 * Initiiert den Helden für das Management des Helden, vor allem werden die Boxen erzeugt
	 */
	public void initManagement(HeldProzessor prozessor) {
		// TODO Boxen für das Management erzeugen
		
		initEigenschaften(prozessor);
		initEigenschaftMap(prozessor);
		
	}
	
	public HashMap<CharKomponente, AbstractBoxGen> initHashMap() {
		// Hash erzeugen
		boxenHash = new HashMap<CharKomponente, AbstractBoxGen>();
		
		return boxenHash;
	}
	
	/**
	 * Initiiert den Helden für die Generierung, vor allem werden die Boxen erzeugt.
	 * @param Der Prozessor zu diesem Helden
	 */
	public void initGenrierung(HeldProzessor prozessor) {
		
		// Boxen für die CharElemente erzeugen und platzieren
		boxenHash.put(CharKomponente.eigenschaft, new EigenschaftBoxGen(prozessor));
		boxenHash.put(CharKomponente.talent, new TalentBoxGen(prozessor));
		boxenHash.put(CharKomponente.vorteil, new VorteilBoxGen(prozessor));
		boxenHash.put(CharKomponente.nachteil, new NachteilBoxGen(prozessor));
		boxenHash.put(CharKomponente.sonderfertigkeit, new SonderfBoxGen(prozessor));
		boxenHash.put(CharKomponente.zauber, new ZauberBoxGen(prozessor));
		// TODO restliche Boxen einbauen
		
		// Erzeugt alle Eigenschaften
		initEigenschaften(prozessor);
		
		// Setzt die Eigenschaften in ein zusätzliches Hash für besseren Zugriff
		initEigenschaftMap(prozessor);
	}
	
	/**
	 * Erzeugt alle Eigenschaften und fügt sie zum Helden hinzu. Es werden initiale Werte
	 * gesetzt.
	 * @param Der Prozessor zu diesem Helden
	 */ 
	private void initEigenschaften(HeldProzessor prozessor) {
		
		EigenschaftEnum[] enums = EigenschaftEnum.values();
		
		// Erstmal alle Eigenschaften mit "0" setzen
		for (int i = 0; i < enums.length; i++) {
			prozessor.addCharElement(
				ProgAdmin.data.getCharElement(enums[i].getId(), CharKomponente.eigenschaft),
				0
			);
		}
		
		//  Grund-Eigenschaften auf initialwert "8" setzen
		prozessor.updateElement(
				prozessor.getLinkById(EigenschaftEnum.MU.getId(), null, null),
				8,
				null, null
		);
		prozessor.updateElement(
				prozessor.getLinkById(EigenschaftEnum.CH.getId(), null, null),
				8,
				null, null
		);
		prozessor.updateElement(
				prozessor.getLinkById(EigenschaftEnum.FF.getId(), null, null),
				8,
				null, null
		);
		prozessor.updateElement(
				prozessor.getLinkById(EigenschaftEnum.GE.getId(), null, null),
				8,
				null, null
		);
		prozessor.updateElement(
				prozessor.getLinkById(EigenschaftEnum.IN.getId(), null, null),
				8,
				null, null
		);
		prozessor.updateElement(
				prozessor.getLinkById(EigenschaftEnum.KK.getId(), null, null),
				8,
				null, null
		);
		prozessor.updateElement(
				prozessor.getLinkById(EigenschaftEnum.KL.getId(), null, null),
				8,
				null, null
		);
		prozessor.updateElement(
				prozessor.getLinkById(EigenschaftEnum.KO.getId(), null, null),
				8,
				null, null
		);
		
		prozessor.updateElement(
				prozessor.getLinkById(EigenschaftEnum.SO.getId(), null, null),
				1,
				null, null
		);
		
	}
	
	/**
	 * Setzt die Eigenschaften in ein Zusätzliches Hash für besseren Zugriff, da die
	 * Eigenschaften öfter benötigt werden.
	 * @param prozessor
	 */
	private void initEigenschaftMap(HeldProzessor prozessor) {
		
		EigenschaftEnum[] enums = EigenschaftEnum.values();
		
		eigenschaftHash = new HashMap<EigenschaftEnum, HeldenLink>();
		
		// Da Eigenschaften von größerer Bedeutung sind werden sie ZUSÄTZLICH
		// direkt in ein Hash gesichert für einfachen Zugriff
		for (int i = 0; i < enums.length; i++) {
			eigenschaftHash.put(enums[i], 
					prozessor.getLinkById(enums[i].getId(), null, null)
			);
		}
	}
	
	/**
	 * Hiermit kann die Box der CharKomponente "komponente" abgerufen werden. Darüber 
	 * kann auf alle Elemente zu der Box zugegriffen werden. Eigenschaften (MU, Lep, usw.) 
	 * können mit dieser Methode nicht abgerufen werden, es wird "null" zurückgeliefert. 
	 * Hier für ist die Methode "getEigenschaftsWert()" gedacht.
	 * 
	 * @param komponente Die gewünschte CharKomponete
	 * @return Die LinkElementBoxzu die alle CharElemente des Helden der Art "komponente"
	 *  enthält.
	 */
	public LinkElementBox getElementBox(CharKomponente komponente) {
		// Eigenschaften könne hier NICHT abgerufen werden, da sie errechnet werden
		//if ( komponente.equals(CharKomponente.eigenschaft) ) return null;
		
		return boxenHash.get(komponente);
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
