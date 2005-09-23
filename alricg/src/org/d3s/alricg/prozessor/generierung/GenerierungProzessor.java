/*
 * Created 23. April 2005 / 17:23:44
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.prozessor.generierung;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Herkunft;
import org.d3s.alricg.charKomponenten.Kultur;
import org.d3s.alricg.charKomponenten.Profession;
import org.d3s.alricg.charKomponenten.Rasse;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.held.GeneratorLink;
import org.d3s.alricg.held.Held;
import org.d3s.alricg.held.HeldenLink;
import org.d3s.alricg.prozessor.HeldProzessor;
/**
 * <b>Beschreibung:</b><br>
 * Diese Klasse ist f�r die Bearbeitung eines Helden bei der Generierung zust�ndig 
 * und bildet eine Zentrale Schnittstelle daf�r. Es wird die �bergeordnete Logik von
 * hier gesteuert und �bergeordnete Elemente (wie z.B. GP) verwaltet.
 * 
 * Es ist im package die einzige Klasse die Methoden zur Bearbeitung nach au�en 
 * anbietet. Alle Anfragen zur Bearbeitung laufen somit �ber diese Klasse, egal ob auf Talente,
 * Zauber oder Eigenschaften zugegriffen wird.
 * 
 * @author V.Strelow
 */
public class GenerierungProzessor extends HeldProzessor {
	public static GenerierungsKonstanten genKonstanten;

	private int aktivierbarTalente; // Anzahl noch aktivierbarer Talente 
	private int aktivierbarZauber; // Anzahl noch aktivierbarer Zauber
	private int aktuelleGP; // Anzahl noch verteilbarer GP (kann auch < 0 sein)
	private int aktuelleTalentGP; // Anzahl noch verteilbarer Talent-GP (kann auch < 0 sein)
	private int aktuelleBonusWissenGP; // Anz. Talent-GP die nur f�r Wissen/Zauber verwendet 
										// werden d�rfen
	
	/**
	 * Konstruktor.
	 * @param held Der Held der von diesem Prozessor bearbeitet wird
	 */
	public GenerierungProzessor(Held held) {
		super(held);
		
		genKonstanten = new GenerierungsKonstanten();
		
		aktivierbarTalente = genKonstanten.MAX_TALENT_AKTIVIERUNG;
		aktuelleGP = genKonstanten.VERFUEGBARE_GP;
		aktuelleTalentGP = 0; // Bei Initialisierung noch keine Eigenschaften!
		aktivierbarZauber = 0; // Bei Initialisierung noch kein Magier!
		aktuelleBonusWissenGP = 0; // Nur durch SF
	}
	

	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#isGenerierung()
	 */
	public boolean isGenerierung() {
		return true;
	}
	
	/**
	 * Liefert die Anzahl an noch aktivierbaren Talenten zur�ck
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
	 * @return Anzahl an GP die noch verteilt werden k�nnen (auch negativ m�glich!) 
	 */
	public int getAktuelleGP() {
		return aktuelleGP;
	}
	
	/**
	 * @return Anzahl an Talent-GP die noch verteilt werden k�nnen (auch negativ m�glich!) 
	 */
	public int getAktuelleTalentGP() {
		return aktuelleTalentGP;
	}
	
	/**
	 * @return Anzahl an Bouns-GP f�r Wissens-Talentdie noch verteilt werden k�nnen 
	 */
	public int getAktuelleBonusGP() {
		// TODO evtl. durch Sonderregeln l�sen?
		return aktuelleBonusWissenGP;
	}
	
	
//	 *******************************************************************************************
//							�berschriebene Methoden
	
	
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#addCharElement(org.d3s.alricg.charKomponenten.CharElement, java.lang.String, org.d3s.alricg.charKomponenten.CharElement, int)
	 */
	public void addCharElement(CharElement ziel, String text, CharElement zweitZiel, int wert) {
		IdLink tmpLink = new IdLink(null, null);
		
    	// Setzen aller Basis-Werte wie �bergeben
    	tmpLink.setZielId(ziel);
    	tmpLink.setZweitZiel(zweitZiel);
    	tmpLink.setText(text);
    	tmpLink.setWert(wert);
    	
    	addAsNewElement(tmpLink); // Hinzuf�gen, Kosten werden dort berechnet
	}
	

	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#updateElement(org.d3s.alricg.held.HeldenLink, int, java.lang.String, org.d3s.alricg.charKomponenten.CharElement)
	 */
	public void updateElement(HeldenLink link, int stufe, String text, CharElement zweitZiel) {
		
		boxenHash.get(link.getZiel().getCharKomponente())
									.updateElement(link, stufe, text, zweitZiel);
				
		getSonderregelAdmin().processUpdateElement(link, stufe, text, zweitZiel);
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canAddCharElement(org.d3s.alricg.charKomponenten.CharElement, java.lang.String, org.d3s.alricg.charKomponenten.CharElement, int)
	 */
	public boolean canAddCharElement(CharElement ziel, String text, CharElement zweitZiel, int wert) {
		boolean ok;
		IdLink tmpLink = new IdLink(null, null);
		
    	// Setzen aller Basis-Werte wie �bergeben
    	tmpLink.setZielId(ziel);
    	tmpLink.setZweitZiel(zweitZiel);
    	tmpLink.setText(text);
    	tmpLink.setWert(wert);
		
    	ok = boxenHash.get(tmpLink.getZiel().getCharKomponente()).canAddAsNewElement(tmpLink);
    	ok = getVoraussetzungenAdmin().changeCanAddElement(ok, tmpLink);
    	ok = getSonderregelAdmin().changeCanAddElement(ok, tmpLink);
    	
    	if ( tmpLink.getZiel().hasSonderregel()) {
    		ok = tmpLink.getZiel().createSonderregel().canAddSelf(this, ok, tmpLink);
    	}
    	
    	return ok;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#removeElement(org.d3s.alricg.held.HeldenLink)
	 */
	public void removeElement(HeldenLink link) {
		boxenHash.get(link.getZiel().getCharKomponente()).removeElement(link);
		getSonderregelAdmin().processRemoveElement(link);
		
		// Falls dieses Element eine Sonderregel besitzt, diese hinzuf�gen
		if (link.getZiel().hasSonderregel()) {
			getSonderregelAdmin().removeSonderregel(link);
		}
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canRemoveElement(org.d3s.alricg.held.HeldenLink)
	 */
	public boolean canRemoveElement(HeldenLink element) {
		boolean ok;
		
		ok = boxenHash.get(element.getZiel().getCharKomponente()).canRemoveElement(element);
		ok = getVoraussetzungenAdmin().changeCanRemoveElemet(ok, element);
		ok = getSonderregelAdmin().changeCanRemoveElemet(ok, element);
		
		return ok;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canUpdateStufe(org.d3s.alricg.held.HeldenLink)
	 */
	public boolean canUpdateStufe(HeldenLink link) {
		boolean ok;
		
		ok = boxenHash.get(link.getZiel().getCharKomponente()).canUpdateWert(link);
		ok = getSonderregelAdmin().changeCanUpdateWert(ok, link);
		
		return ok;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canUpdateText(org.d3s.alricg.held.HeldenLink)
	 */
	public boolean canUpdateText(HeldenLink link) {
		boolean ok;
		
		ok = boxenHash.get(link.getZiel().getCharKomponente()).canUpdateText(link);
		ok = getSonderregelAdmin().changeCanUpdateText(ok, link);
		
		return ok;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canUpdateZweitZiel(org.d3s.alricg.held.HeldenLink)
	 */
	public boolean canUpdateZweitZiel(HeldenLink link) {
		boolean ok;
		
		ok = boxenHash.get(link.getZiel().getCharKomponente()).canUpdateZweitZiel(link);
		ok = getSonderregelAdmin().changeCanUpdateZweitZiel(ok, link);
		
		return ok;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#getMaxWert(org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int getMaxWert(Link link) {
		int max;
		
		max = boxenHash.get(link.getZiel().getCharKomponente()).getMaxWert(link);
		max = getVoraussetzungenAdmin().changeMaxStufe(max, link);
		max = getSonderregelAdmin().changeMaxStufe(max, link);
		
		return max;
	}

	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#getMinWert(org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int getMinWert(Link link) {
		int min;
		
		min = boxenHash.get(link.getZiel().getCharKomponente()).getMinWert(link);
		min = this.getVoraussetzungenAdmin().changeMinStufe(min, link);
		min = getSonderregelAdmin().changeMinStufe(min, link);
		
		return min;
	}
	
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#setRasse(org.d3s.alricg.charKomponenten.Rasse)
	 */
	public void setRasse(Rasse rasse) {
		addHerkunft(rasse);
//		 TODO implement
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#setKultur(org.d3s.alricg.charKomponenten.Kultur)
	 */
	public void setKultur(Kultur kultur) {
		addHerkunft(kultur);
//		 TODO implement
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#setProfession(org.d3s.alricg.charKomponenten.Profession)
	 */
	public void setProfession(Profession profession) {
		addHerkunft(profession);
//		 TODO implement 
	}

	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canSetRasse(org.d3s.alricg.charKomponenten.Rasse)
	 */
	public void canSetRasse(Rasse rasse) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canSetKultur(org.d3s.alricg.charKomponenten.Kultur)
	 */
	public void canSetKultur(Kultur kultur) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canSetProfession(org.d3s.alricg.charKomponenten.Profession)
	 */
	public void canSetProfession(Profession profession) {
		// TODO Auto-generated method stub
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#addLinkToElement(org.d3s.alricg.charKomponenten.links.IdLink)
	 */
	public void addLink(IdLink link) {
		// VORSICHT! Hier mu� gew�hrleistet werden das der Held "g�ltig" bleibt!
		
		/*
		IdLink newLink;
		
		// VORSICHT! Hier mu� gew�hrleistet werden das der held "G�ltig" bleibt! 
		
		// Kopie des Links anlegen
		newLink = new IdLink(link.getQuellElement(), link.getQuellAuswahl());
		newLink.setZielId(link.getZiel());
		newLink.setText(link.getText());
		newLink.setZweitZiel(link.getZweitZiel());
		newLink.setLeitwert(link.isLeitwert());
		newLink.setWert(link.getWert());
		*/
		
		// Ist dieser Link neu oder schon ein entsprechendes Element vorhanden?
		if ( held.getElementBox(link.getZiel().getCharKomponente()).contiansEqualLink(link) ) {
			addLinkToElement(link);
		} else {
			addAsNewElement(link);
		}
		
		
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.HeldProzessor#updateKosten(org.d3s.alricg.held.HeldenLink)
	 */
	public void updateKosten(HeldenLink link) {
		boxenHash.get(link.getZiel().getCharKomponente()).updateKosten((GeneratorLink) link);
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
	 * Fast gleiche Arbeitsschritte beim Hinzuf�gen der Herkunft (Rasse, Kultur,
	 * Profession) zusammen.
	 * @param herkunft Die Herkunft die hinzugef�gt wird.
	 */
	private void addHerkunft(Herkunft herkunft) {
//		 TODO implement		
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.HeldProzessor#removeLinkFromElement
	 */
	public void removeLinkFromElement(IdLink link) {		
		boxenHash.get(link.getZiel().getCharKomponente()).removeLinkFromElement(link, true);
	}
	
	/**
	 * F�gt einen Link als neues Element zum Helden hinzu. 
	 * @param link Der link der Grundlage f�r das neue Element ist
	 */
	private void addAsNewElement(IdLink link) {
		
		// Element zur korrekten Box hinzuf�gen
		boxenHash.get(link.getZiel().getCharKomponente()).addAsNewElement(link);
		
		// Falls dieses Element eine Sonderregel besitzt, diese hinzuf�gen
		if (link.getZiel().hasSonderregel()) {
			getSonderregelAdmin().addSonderregel(link);
		}

		// Sonderregeln aufrufen
		getSonderregelAdmin().processAddAsNewElement(link);
	}
	
	/**
	 * Wird bei dem setzen der Rasse, Kultur usw. genutzt
	 * @param link Der link der zum Helden hinzugef�gt wird
	 */
	private void addLinkToElement(IdLink link) {
		// Element zur korrekten Box hinzuf�gen
		boxenHash.get(link.getZiel().getCharKomponente()).addLinkToElement(link, true);
	}
	
	/**
	 * �berpr�ft ob ein Link als neues Element hinzugef�gt werden kann. Wird u.a. beim
	 * setzen der Herkunft (Rasse, Kultur, Profession) genutzt.
	 * @param link Der Link, der als Grundlage f�r das neue Element dienen w�rde
	 * @return true - Es ist m�glich, ansonsten false
	 */
	public boolean canAddLinkAsNewElement(IdLink link) {
		boolean ok;
		
		ok = boxenHash.get(link.getZiel().getCharKomponente()).canAddAsNewElement(link);
		ok = this.getVoraussetzungenAdmin().changeCanAddElement(ok, link);
		ok = getSonderregelAdmin().changeCanAddElement(ok, link);
		
		if ( link.getZiel().hasSonderregel() ) {
			ok = link.getZiel().createSonderregel().canAddSelf(this, ok, link);
		}
		
		return ok;
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.prozessor.HeldProzessor#canAddCharElement(org.d3s.alricg.charKomponenten.CharElement)
	 */
	protected boolean canAddCharElement(CharElement elem) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * 
	 * <u>Beschreibung:</u><br> 
	 * Diese Klasse stellt einige "Konstanten" zur Verf�gung f�r die Erschaffung von Helden.
	 * VORSICHT: Obwohl in den Konstanten teilweise auch Maximal- und Minimal-Werte 
	 * stehen (Schlechte Eingenschaften, Eigenschaften), d�rfen die Max/Min Werte eines 
	 * Elements NUR �ber "getMaxWert()" und "getMinWert()" abgefragt werden!
	 * Die Konstanten sind in dem Fall nur Berechnungsgrundlage.
	 * 
	 * (Es denkbar diese "Konstanten" auch zur Laufzeit �ndern zu k�nnen durch ein neues 
	 * einlesen dieser Klasse, aber das sollte nicht BEIM generieren passiern, daher 
	 * Konstanten) 
	 * @author V. Strelow
	 */
	public class GenerierungsKonstanten {
		
/**		Wieviele GP insgesamt zur Verf�gung stehen (normal 110 GP) */ 
		public final int VERFUEGBARE_GP;

/** 	Maximale GP die durch schlechte Eigenschaften gewonnen werden k�nnen (normal 30 GP) */
		public final int MAX_SCHLECHTE_EIGEN_GP;
		
/** 	Maximale GP die durch Nachteile gewonnen werden (normal 50 GP) */
		public final int MAX_NACHTEIL_GP; 
		
/**		Maximale GP die auf Eigenschaften (MU, KL, usw) verteilt werdem d�rfen (normal 100 GP) */	
		public final int MAX_GP_EIGENSCHAFTEN; 
		
/**		Maximaler Wert von Eigenschaften ohne Modis (Z.B. durch Herkunft, Vorteile) (normal 14) */
		public final int MAX_EIGENSCHAFT_WERT;
		
/**		Minimaler Wert von Eigenschaften ohne Modis (Z.B. durch Herkunft, Nachteile) (normal 8) */
		public final int MIN_EIGENSCHAFT_WERT;
		
/**		Maximaler Wert des Sozialstatus ohne Modis (Z.B. durch Herkunft, Vorteile) (normal 12) */
		public final int MAX_SOZIALSTATUS;
		
/**		Faktor f�r berechnung der TalentGP; (KL+IN) * TALENT_GP_FAKTOR = TalentGP (normal 20) */
		public final int TALENT_GP_FAKTOR;
		
/**		Maximale Anzahl an Talenten die aktiviert werden k�nnen (normal 5) */	
		public final int MAX_TALENT_AKTIVIERUNG;
		
/**		Maximale Anzahl an Zaubern f�r VOLLzauberer die aktiviert werden k�nnen (normal 10)	*/	
		public final int MAX_ZAUBER_AKTIVIERUNG_VZ;
		
/**		Maximale Anzahl an Zaubern f�r HALBzauberer die aktiviert werden k�nnen (normal 10)	*/		
		public final int MAX_ZAUBER_AKTIVIERUNG_HZ;
		
/**		Maximaler Wert von schlechten Eigenschaften (normal 12) */
		public final int MAX_SCHLECHT_EIGENSCHAFT_WERT;
		
/**		Minimaler Wert von schlechten Eigenschaften (normal 5) */
		public final int MIN_SCHLECHT_EIGENSCHAFT_WERT;
		
/**		Wie sich der Wert der Muttersprache in abh�ngigkeit zur KL errechnet (normal -2) */		
		public final int DIFF_KULGHEIT_MUTTERSPR;
		
/**		Wie sich der Wert der Muttersprache in abh�ngigkeit zur KL errechnet (normal -4) */	
		public final int DIFF_KULGHEIT_ZWEITSPR;
		
/**		Wie gro� darf die Different zwischen verteilten Punkten auf AT und PA max. sein (normal 5) */			
		public final int MAX_DIFF_AT_PA;	
		
//		 Der Minimale Wert des Sozialstatus ist "1", auch als variable?
		
		public GenerierungsKonstanten() {
			// TODO Werte korrekt initialisieren mit auslesen aus einer Config-XML
			VERFUEGBARE_GP = 10;
			MAX_SCHLECHTE_EIGEN_GP = 10;
			MAX_NACHTEIL_GP = 10;
			MAX_GP_EIGENSCHAFTEN = 10;
			MAX_EIGENSCHAFT_WERT = 14;
			MIN_EIGENSCHAFT_WERT = 8;
			MAX_SOZIALSTATUS = 12;
			TALENT_GP_FAKTOR = 10;
			MAX_TALENT_AKTIVIERUNG = 5;
			MAX_ZAUBER_AKTIVIERUNG_VZ = 10;
			MAX_ZAUBER_AKTIVIERUNG_HZ = 10;
			MAX_SCHLECHT_EIGENSCHAFT_WERT = 10;
			MIN_SCHLECHT_EIGENSCHAFT_WERT = 10;
			DIFF_KULGHEIT_MUTTERSPR = -2;
			DIFF_KULGHEIT_ZWEITSPR = -4;
			MAX_DIFF_AT_PA = 5;	
		}
		
	}


}

