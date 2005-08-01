/*
 * Created 23. April 2005 / 17:23:25
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.prozessor.management;

import java.util.HashMap;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Kultur;
import org.d3s.alricg.charKomponenten.Profession;
import org.d3s.alricg.charKomponenten.Rasse;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.held.Held;
import org.d3s.alricg.held.HeldenLink;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.generierung.AbstractBoxGen;

/**
 * <b>Beschreibung:</b><br> 
 * Diese Klasse ist für die Bearbeitung eines Helden bei der Generierung zuständig 
 * und bildet eine Zentrale Schnittstelle dafür. Es wird die übergeordnete Logik von
 * hier gesteuert und übergeordnete Elemente (wie z.B. GP) verwaltet.
 * 
 * TODO Diese Klasse ist noch nicht implementiert!
 * 
 * @author V.Strelow
 */
public class ManagementProzessor extends HeldProzessor {
	
	/**
	 * Konstruktor.
	 * @param held Der Held der von diesem Prozessor bearbeitet wird
	 */
	public ManagementProzessor(Held held) {
		super(held);
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.AbstractElementBox#isGenerierung()
	 */
	public boolean isGenerierung() {
		return false;
	}
	
	/**
	 * Setzt das HashMap mit den CharKomponente-Boxen. Sollte nur zur Initialisierung
	 * gesetzt werden. 
	 * @param boxenHash HashMap mit allen CharElementen des Helden 
	 */
	public void setBoxenHash(HashMap<CharKomponente, AbstractBoxGen> boxen) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#addCharElement(org.d3s.alricg.charKomponenten.CharElement, java.lang.String, org.d3s.alricg.charKomponenten.CharElement, int)
	 */
	public void addCharElement(CharElement ziel, String text, CharElement zweitZiel, int wert) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#updateElement(org.d3s.alricg.held.HeldenLink, int, java.lang.String, org.d3s.alricg.charKomponenten.CharElement)
	 */
	public void updateElement(HeldenLink link, int wert, String text, CharElement zweitZiel) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canAddCharElement(org.d3s.alricg.charKomponenten.CharElement, java.lang.String, org.d3s.alricg.charKomponenten.CharElement, int)
	 */
	public boolean canAddCharElement(CharElement ziel, String text, CharElement zweitZiel, int wert) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#removeElement(org.d3s.alricg.held.HeldenLink)
	 */
	public void removeElement(HeldenLink element) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canRemoveElement(org.d3s.alricg.held.HeldenLink)
	 */
	public boolean canRemoveElement(HeldenLink element) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canUpdateStufe(org.d3s.alricg.held.HeldenLink)
	 */
	public boolean canUpdateStufe(HeldenLink link) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canUpdateText(org.d3s.alricg.held.HeldenLink)
	 */
	public boolean canUpdateText(HeldenLink link) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canUpdateZweitZiel(org.d3s.alricg.held.HeldenLink)
	 */
	public boolean canUpdateZweitZiel(HeldenLink link) {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canSetRasse(org.d3s.alricg.charKomponenten.Rasse)
	 */
	public void canSetRasse(Rasse rasse) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#setKultur(org.d3s.alricg.charKomponenten.Kultur)
	 */
	public void setKultur(Kultur kultur) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#canSetKultur(org.d3s.alricg.charKomponenten.Kultur)
	 */
	public void canSetKultur(Kultur kultur) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.HeldProzessor#setProfession(org.d3s.alricg.charKomponenten.Profession)
	 */
	public void setProfession(Profession profession) {
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
	public void addLink(IdLink link) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.HeldProzessor#updateKosten(org.d3s.alricg.held.HeldenLink)
	 */
	public void updateKosten(HeldenLink genLink) {
		// TODO implement
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.HeldProzessor#canAddCharElement(org.d3s.alricg.charKomponenten.CharElement)
	 */
	protected boolean canAddCharElement(CharElement elem) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeLinkFromElement(IdLink link) {
		// TODO Auto-generated method stub
		
	}
	
}
