/*
 * Created 23. April 2005 / 17:23:44
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.prozessor.generierung;

import java.util.HashMap;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Herkunft;
import org.d3s.alricg.charKomponenten.Kultur;
import org.d3s.alricg.charKomponenten.Profession;
import org.d3s.alricg.charKomponenten.Rasse;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;
import org.d3s.alricg.held.HeldenLink;
import org.d3s.alricg.prozessor.HeldProzessor;
/**
 * <b>Beschreibung:</b><br>
 * Diese Klasse ist für die Bearbeitung eines Helden bei der Generierung zuständig 
 * und bildet eine Zentrale Schnittstelle dafür. Es wird die übergeordnete Logik von
 * hier gesteuert und übergeordnete Elemente (wie z.B. GP) verwaltet.
 * 
 * Es ist im package die einzige Klasse die Methoden zur Bearbeitung nach außen 
 * anbietet. Alle Anfragen zur Bearbeitung laufen somit über diese Klasse, egal ob auf Talente,
 * Zauber oder Eigenschaften zugegriffen wird.
 * 
 * @author V.Strelow
 */
public class GenerierungProzessor extends HeldProzessor {
	public static GenerierungsKonstanten genKonstanten;
	public static HashMap<CharKomponente, AbstractBoxGen> boxenHash;
	
	
	private int aktivierbarTalente; // Anzahl noch aktivierbarer Talente 
	private int aktivierbarZauber; // Anzahl noch aktivierbarer Zauber
	private int aktuelleGP; // Anzahl noch verteilbarer GP (kann auch < 0 sein)
	private int aktuelleTalentGP; // Anzahl noch verteilbarer Talent-GP (kann auch < 0 sein)
	private int aktuelleBonusWissenGP; // Anz. Talent-GP die nur für Wissen/Zauber verwendet 
										// werden dürfen
	
	public GenerierungProzessor() {
		genKonstanten = new GenerierungsKonstanten();
		
		// TODO initialisieren von boxenHash
		// TODO initialisieren des Helden
		
		
		aktivierbarTalente = genKonstanten.MAX_TALENT_AKTIVIERUNG;
		aktuelleGP = genKonstanten.VERFUEGBARE_GP;
		aktuelleTalentGP = 0; // Bei Initialisierung noch keine Eigenschaften!
		aktivierbarZauber = 0; // Bei Initialisierung noch kein Magier!
		aktuelleBonusWissenGP = 0; // Nur durch SF
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#isGenerierung()
	 */
	public boolean isGenerierung() {
		return true;
	}
	
	/**
	 * Liefert die Anzahl an noch aktivierbaren Talenten zurück
	 * @return Anzahl noch aktivierbarer Talente
	 */
	public int getAnzAktivierbarTalente() {
		return aktivierbarTalente;
	}
	
	/**
	 * Keine unterscheidung zwischen Voll und Halbzauberer, da sich dies immer auf 
	 * den aktuellen Helden bezieht
	 * @return Anzahl noch aktivierbarer Zauber
	 */
	public int getAnzAktivierbarZauber() {
		return aktivierbarZauber;
	}
	
	/**
	 * @return Anzahl an GP die noch verteilt werden können (auch negativ möglich!) 
	 */
	public int getAktuelleGP() {
		return aktuelleGP;
	}
	
	/**
	 * @return Anzahl an Talent-GP die noch verteilt werden können (auch negativ möglich!) 
	 */
	public int getAktuelleTalentGP() {
		return aktuelleTalentGP;
	}
	
	/**
	 * @return Anzahl an Bouns-GP für Wissens-Talentdie noch verteilt werden können 
	 */
	public int getAktuelleBonusGP() {
		// TODO evtl. durch Sonderregeln lösen?
		return aktuelleBonusWissenGP;
	}
	
	
//	 *******************************************************************************************
//							Überschriebene Methoden
	
	
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#addCharElement(org.d3s.alricg.charKomponenten.CharElement, java.lang.String, org.d3s.alricg.charKomponenten.CharElement, int)
	 */
	public void addCharElement(CharElement ziel, String text, CharElement zweitZiel, int wert) {
		IdLink tmpLink = new IdLink(null, null);
		
    	// Setzen aller Basis-Werte wie übergeben
    	tmpLink.setZielId(ziel);
    	tmpLink.setZweitZiel(zweitZiel);
    	tmpLink.setText(text);
    	tmpLink.setWert(wert);
    	
    	addAsNewElement(tmpLink); // Hinzufügen, Kosten werden dort berechnet
	}
	

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#updateElement(org.d3s.alricg.held.HeldenLink, int, java.lang.String, org.d3s.alricg.charKomponenten.CharElement)
	 */
	public void updateElement(HeldenLink link, int stufe, String text, CharElement zweitZiel) {
		
		boxenHash.get(link.getZiel().getCharKomponente())
									.updateElement(link, stufe, text, zweitZiel);
				
		held.getSonderregelAdmin().processUpdateElement(link, stufe, text, zweitZiel);
		// TODO Kosten neu berechnen!
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canAddCharElement(org.d3s.alricg.charKomponenten.CharElement, java.lang.String, org.d3s.alricg.charKomponenten.CharElement, int)
	 */
	public boolean canAddCharElement(CharElement ziel, String text, CharElement zweitZiel, int wert) {
		boolean ok;
		IdLink tmpLink = new IdLink(null, null);
		
    	// Setzen aller Basis-Werte wie übergeben
    	tmpLink.setZielId(ziel);
    	tmpLink.setZweitZiel(zweitZiel);
    	tmpLink.setText(text);
    	tmpLink.setWert(wert);
		
    	ok = boxenHash.get(tmpLink.getZiel().getCharKomponente()).canAddAsNewElement(tmpLink);
    	ok = held.getSonderregelAdmin().changeCanAddElement(ok, tmpLink);
    	
    	if ( tmpLink.getZiel().getSonderregel() != null) {
    		ok = tmpLink.getZiel().getSonderregel().canAddSelf(this, ok, tmpLink);
    	}
    	
    	return ok;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#removeElement(org.d3s.alricg.held.HeldenLink)
	 */
	public void removeElement(HeldenLink element) {
		// TODO implement
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canRemoveElement(org.d3s.alricg.held.HeldenLink)
	 */
	public boolean canRemoveElement(HeldenLink element) {
		boolean ok;
		
		ok = boxenHash.get(element.getZiel().getCharKomponente()).canRemoveElement(element);
		ok = held.getSonderregelAdmin().changeCanRemoveElemet(ok, element);
		
		return ok;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canUpdateStufe(org.d3s.alricg.held.HeldenLink)
	 */
	public boolean canUpdateStufe(HeldenLink link) {
		boolean ok;
		
		ok = boxenHash.get(link.getZiel().getCharKomponente()).canUpdateWert(link);
		ok = held.getSonderregelAdmin().changeCanUpdateWert(ok, link);
		
		return ok;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canUpdateText(org.d3s.alricg.held.HeldenLink)
	 */
	public boolean canUpdateText(HeldenLink link) {
		boolean ok;
		
		ok = boxenHash.get(link.getZiel().getCharKomponente()).canUpdateText(link);
		ok = held.getSonderregelAdmin().changeCanUpdateText(ok, link);
		
		return ok;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canUpdateZweitZiel(org.d3s.alricg.held.HeldenLink)
	 */
	public boolean canUpdateZweitZiel(HeldenLink link) {
		boolean ok;
		
		ok = boxenHash.get(link.getZiel().getCharKomponente()).canUpdateZweitZiel(link);
		ok = held.getSonderregelAdmin().changeCanUpdateZweitZiel(ok, link);
		
		return ok;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#getMaxWert(org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int getMaxWert(Link link) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#getMinWert(org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int getMinWert(Link link) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#setRasse(org.d3s.alricg.charKomponenten.Rasse)
	 */
	public void setRasse(Rasse rasse) {
		addHerkunft(rasse);
//		 TODO implement
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#setKultur(org.d3s.alricg.charKomponenten.Kultur)
	 */
	public void setKultur(Kultur kultur) {
		addHerkunft(kultur);
//		 TODO implement
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#setProfession(org.d3s.alricg.charKomponenten.Profession)
	 */
	public void setProfession(Profession profession) {
		addHerkunft(profession);
//		 TODO implement 
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canSetRasse(org.d3s.alricg.charKomponenten.Rasse)
	 */
	public void canSetRasse(Rasse rasse) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canSetKultur(org.d3s.alricg.charKomponenten.Kultur)
	 */
	public void canSetKultur(Kultur kultur) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canSetProfession(org.d3s.alricg.charKomponenten.Profession)
	 */
	public void canSetProfession(Profession profession) {
		// TODO Auto-generated method stub
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#addLinkToElement(org.d3s.alricg.charKomponenten.links.IdLink)
	 */
	public void addLinkToElement(IdLink link) {
		// VORSICHT! Hier muß gewährleistet werden das der Held "gültig" bleibt!
		
		// Element zur korrekten Box hinzufügen
		boxenHash.get(link.getZiel().getCharKomponente()).addLinkToElement(link, true);
		
    	// TODO Kosten neu berechnen!
	}
	
// *******************************************************************************************
// 									HilfsMethoden
	
	/**
	 * Soll dem Nachrichten austausch zwischen Programm - User dienen, damit
	 * der Benutzer möglichst transparent sehen kann, wann welche Regel zur 
	 * Anwendung kommt. 
	 * Noch nicht implimentiert.
	 * 
	 * Bsp. der Idee:
	 * 		- Es wird geprüft ob ein Talent zu Helden hinzugefügt werden kann, es wird die
	 * 		  Methode "canAddCharElement(..)" aufgerufen.
	 * 		- In dieser Methode wird vor der Prüfung "startPage()" aufgerufen, das ein neues
	 * 		  Objekt X erstellt in das Nachrichten geschrieben werden können.  
	 * 		- Nun wird die Prüfung vor genommen, dabei wird festgestellt das eine Voraussetzung 
	 * 		  für das Talent fehlt, daher wird mit "writeToPage()" in das Nachrichten Objekt 
	 * 		  etwas wie "Es fehlt die Voraussetzung Y" geschrieben.
	 * 		- Durch eine Sonderregel kann das Talent jedoch trotzdem hinzugefügt werden, die 
	 * 		  Sonderregel Schreibt ebenfalls mit "writeToPage()" etwas wie : 
	 * 		  "Konnte hinzugefügt werden durch Sonderregel Z"
	 * 		- Am ende der Methode ""canAddCharElement(..)" wird dann "sendPage()" aufgerufen,
	 * 		  wodurch das Nachrichten-Objekt an ein GUI Objekt zur anzeige geschickt wird.
	 * --> Der Benutzer sieht was das Programm gemacht hat und kann nachvollziehen warum 
	 * etwas geht oder ebend nicht.
	 * 
	 */
	private void startNewPage() {
		// TODO implement
	}
	
	// Siehe Oben
	public void writeToPage() {
		// TODO implement		
	}
	
	// Siehe Oben
	private void sendPage() {
		// TODO implement	
	}
	
	/**
	 * Bestimmt die von dem Prozesser verwalteten Kosten neu:
	 * 	aktuelleGP, aktuelleTalentGP, aktuelleBonusWissenGP,
	 *	aktivierbarTalente und aktivierbarZauber
	 */
	private void updateKostenGp() {
		// TODO implement
	}

	/**
	 * Fast gleiche Arbeitsschritte beim Hinzufügen der Herkunft (Rasse, Kultur,
	 * Profession) zusammen.
	 * @param herkunft Die Herkunft die hinzugefügt wird.
	 */
	private void addHerkunft(Herkunft herkunft) {
//		 TODO implement		
	}
	

	private boolean removeLink(Link element) {
		// VORSICHT! Hier muß gewährleistet werden, das auch ohne den Link 
		// der Held noch "gültig" ist
		
		return false;
	}
	
	/**
	 * Fügt einen Link als neues Element zum Helden hinzu. 
	 * @param link Der link der Grundlage für das neue Element ist
	 */
	private void addAsNewElement(IdLink link) {
		
		// Element zur korrekten Box hinzufügen
		boxenHash.get(link.getZiel().getCharKomponente()).addAsNewElement(link);
		
		// Falls dieses Element eine Sonderregel besitzt, diese hinzufügen
		if (link.getZiel().getSonderregel() != null) {
			held.getSonderregelAdmin().addSonderregel(link.getZiel().getSonderregel(), link);
		}

		// Sonderregeln aufrufen
		held.getSonderregelAdmin().processAddAsNewElement(link);
		
    	// TODO Kosten neu berechnen!
	}
	
	/**
	 * Wird bei dem setzen der Rasse, Kultur usw. genutzt
	 * @param link Der link der zum Helden hinzugefügt wird
	 */
	private void addLink(IdLink link) {
		IdLink newLink;
		
		// VORSICHT! Hier muß gewährleistet werden das der held "Gültig" bleibt! 
		
		// Kopie des Links anlegen
		newLink = new IdLink(link.getQuellElement(), link.getQuellAuswahl());
		newLink.setZielId(link.getZiel());
		newLink.setText(link.getText());
		newLink.setZweitZiel(link.getZweitZiel());
		newLink.setLeitwert(link.isLeitwert());
		newLink.setWert(link.getWert());
		
		// Ist dieser Link neu oder schon ein entsprechendes Element vorhanden?
		if ( held.getElementBox(link.getZiel().getCharKomponente()).contiansEqualLink(newLink) ) {
			addLinkToElement(newLink);
		} else {
			addAsNewElement(newLink);
		}
	}
	
	/**
	 * Überprüft ob ein Link als neues Element hinzugefügt werden kann. Wird u.a. beim
	 * setzen der Herkunft (Rasse, Kultur, Profession) genutzt.
	 * @param link Der Link, der als Grundlage für das neue Element dienen würde
	 * @return true - Es ist möglich, ansonsten false
	 */
	private boolean canAddLinkAsNewElement(IdLink link) {
		boolean ok;
		
		ok = boxenHash.get(link.getZiel().getCharKomponente()).canAddAsNewElement(link);
		ok = held.getSonderregelAdmin().changeCanAddElement(ok, link);
		
		if ( link.getZiel().getSonderregel() != null) {
			ok = link.getZiel().getSonderregel().canAddSelf(this, ok, link);
		}
		
		return ok;
	}

	
	/**
	 * 
	 * <u>Beschreibung:</u><br> 
	 * Diese Klasse stellt einige "Konstanten" zur Verfügung für die Erschaffung von Helden.
	 * VORSICHT: Obwohl in den Konstanten teilweise auch Maximal- und Minimal-Werte 
	 * stehen (Schlechte Eingenschaften, Eigenschaften), dürfen die Max/Min Werte eines 
	 * Elements NUR über "getMaxWert()" und "getMinWert()" abgefragt werden!
	 * Die Konstanten sind in dem Fall nur Berechnungsgrundlage.
	 * 
	 * (Es denkbar diese "Konstanten" auch zur Laufzeit ändern zu können durch ein neues 
	 * einlesen dieser Klasse, aber das sollte nicht BEIM generieren passiern, daher 
	 * Konstanten) 
	 * @author V. Strelow
	 */
	public class GenerierungsKonstanten {
		
/**		Wieviele GP insgesamt zur Verfügung stehen (normal 110 GP) */ 
		public final int VERFUEGBARE_GP;

/** 	Maximale GP die durch schlechte Eigenschaften gewonnen werden können (normal 30 GP) */
		public final int MAX_SCHLECHTE_EIGEN_GP;
		
/** 	Maximale GP die durch Nachteile gewonnen werden (normal 50 GP) */
		public final int MAX_NACHTEIL_GP; 
		
/**		Maximale GP die auf Eigenschaften (MU, KL, usw) verteilt werdem dürfen (normal 100 GP) */	
		public final int MAX_GP_EIGENSCHAFTEN; 
		
/**		Maximaler Wert von Eigenschaften ohne Modis (Z.B. durch Herkunft, Vorteile) (normal 14) */
		public final int MAX_EIGENSCHAFT_WERT;
		
/**		Minimaler Wert von Eigenschaften ohne Modis (Z.B. durch Herkunft, Nachteile) (normal 8) */
		public final int MIN_EIGENSCHAFT_WERT;
		
/**		Maximaler Wert des Sozialstatus ohne Modis (Z.B. durch Herkunft, Vorteile) (normal 12) */
		public final int MAX_SOZIALSTATUS;
		
/**		Faktor für berechnung der TalentGP; (KL+IN) * TALENT_GP_FAKTOR = TalentGP (normal 20) */
		public final int TALENT_GP_FAKTOR;
		
/**		Maximale Anzahl an Talenten die aktiviert werden können (normal 5) */	
		public final int MAX_TALENT_AKTIVIERUNG;
		
/**		Maximale Anzahl an Zaubern für VOLLzauberer die aktiviert werden können (normal 10)	*/	
		public final int MAX_ZAUBER_AKTIVIERUNG_VZ;
		
/**		Maximale Anzahl an Zaubern für HALBzauberer die aktiviert werden können (normal 10)	*/		
		public final int MAX_ZAUBER_AKTIVIERUNG_HZ;
		
/**		Maximaler Wert von schlechten Eigenschaften (normal 12) */
		public final int MAX_SCHLECHT_EIGENSCHAFT_WERT;
		
/**		Minimaler Wert von schlechten Eigenschaften (normal 5) */
		public final int MIN_SCHLECHT_EIGENSCHAFT_WERT;
		
//		 Der Minimale Wert des Sozialstatus ist "1", auch als variable?
		
		public GenerierungsKonstanten() {
			// TODO Werte korrekt initialisieren mit auslesen aus einer Config-XML
			VERFUEGBARE_GP = 10;
			MAX_SCHLECHTE_EIGEN_GP = 10;
			MAX_NACHTEIL_GP = 10;
			MAX_GP_EIGENSCHAFTEN = 10;
			MAX_EIGENSCHAFT_WERT = 10;
			MIN_EIGENSCHAFT_WERT = 10;
			MAX_SOZIALSTATUS = 10;
			TALENT_GP_FAKTOR = 10;
			MAX_TALENT_AKTIVIERUNG = 10;
			MAX_ZAUBER_AKTIVIERUNG_VZ = 10;
			MAX_ZAUBER_AKTIVIERUNG_HZ = 10;
			MAX_SCHLECHT_EIGENSCHAFT_WERT = 10;
			MIN_SCHLECHT_EIGENSCHAFT_WERT = 10;
		}
		
	}
}

