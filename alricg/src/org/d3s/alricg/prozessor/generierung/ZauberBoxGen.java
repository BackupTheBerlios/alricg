/*
 * Created on 02.05.2005 / 19:57:29
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.prozessor.generierung;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.EigenschaftEnum;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.held.GeneratorLink;
import org.d3s.alricg.held.HeldenLink;
import org.d3s.alricg.prozessor.HeldProzessor;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class ZauberBoxGen extends AbstractBoxGen {
	
	/* Hält alle Zauber nach den Eigenschaften sortiert, auf die die Probe
	 * Abgelegt wird (wichtig für schnellen Zugriff bei berechnung der Min-Werte
	 * bei Eigenschaften) */ 
	private HashMap<EigenschaftEnum, ArrayList<HeldenLink>> hashMapNachEigensch;
	
	/**
	 * Konstruktor.
	 * @param proz Der Prozessor mit dem der zugehörige Held bearbeitet wird.
	 */
	public ZauberBoxGen(HeldProzessor proz) {
		super(proz);
		
		// Initialisieren der HashMap
		hashMapNachEigensch = new HashMap<EigenschaftEnum, ArrayList<HeldenLink>>();
		
		hashMapNachEigensch.put(EigenschaftEnum.MU, new ArrayList<HeldenLink>());
		hashMapNachEigensch.put(EigenschaftEnum.KL, new ArrayList<HeldenLink>());
		hashMapNachEigensch.put(EigenschaftEnum.IN, new ArrayList<HeldenLink>());
		hashMapNachEigensch.put(EigenschaftEnum.CH, new ArrayList<HeldenLink>());
		hashMapNachEigensch.put(EigenschaftEnum.FF, new ArrayList<HeldenLink>());
		hashMapNachEigensch.put(EigenschaftEnum.GE, new ArrayList<HeldenLink>());
		hashMapNachEigensch.put(EigenschaftEnum.KO, new ArrayList<HeldenLink>());
		hashMapNachEigensch.put(EigenschaftEnum.KK, new ArrayList<HeldenLink>());
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#addAsNewElement(org.d3s.alricg.charKomponenten.links.IdLink)
	 */
	protected GeneratorLink addAsNewElement(IdLink link) {
		// TODO Auto-generated method stub
		
		// TODO Zauber zu der hashMapNachEigensch hinzufügen, siehe Talente
		
		return null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#canAddAsNewElement(org.d3s.alricg.charKomponenten.links.IdLink)
	 */
	protected boolean canAddAsNewElement(IdLink link) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#canRemoveElement(org.d3s.alricg.held.HeldenLink)
	 */
	protected boolean canRemoveElement(HeldenLink link) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#addLinkToElement(org.d3s.alricg.charKomponenten.links.IdLink, boolean)
	 */
	protected GeneratorLink addLinkToElement(IdLink link, boolean stufeErhalten) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#getMaxWert(org.d3s.alricg.charKomponenten.links.Link)
	 */
	protected int getMaxWert(Link link) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#getMinWert(org.d3s.alricg.charKomponenten.links.Link)
	 */
	protected int getMinWert(Link link) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#updateElement(org.d3s.alricg.held.HeldenLink, int, java.lang.String, org.d3s.alricg.charKomponenten.CharElement)
	 */
	protected void updateElement(HeldenLink link, int stufe, String text,
			CharElement zweitZiel) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#canUpdateWert(org.d3s.alricg.held.HeldenLink)
	 */
	protected boolean canUpdateWert(HeldenLink link) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#canUpdateText(org.d3s.alricg.held.HeldenLink)
	 */
	protected boolean canUpdateText(HeldenLink link) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#canUpdateZweitZiel(org.d3s.alricg.held.HeldenLink)
	 */
	protected boolean canUpdateZweitZiel(HeldenLink link) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#updateKosten(org.d3s.alricg.held.GeneratorLink)
	 */
	protected void updateKosten(GeneratorLink genLink) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#canAddCharElement(org.d3s.alricg.charKomponenten.CharElement)
	 */
	protected boolean canAddCharElement(CharElement elem) {
		// TODO Auto-generated method stub
		return false;
	}

	protected @Override void removeElement(HeldenLink element) {
		// TODO Auto-generated method stub
		// TODO Zauber von der hashMapNachEigensch entfernen, siehe Talente
	}

	protected @Override void removeLinkFromElement(IdLink link, boolean stufeErhalten) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getGesamtKosten() {
		// TODO Auto-generated method stub
		return 0;
	}


	/**
	 * Liefert alle Links zu Zaubern, die in der Probe auf mindesten einmal die 
	 * gesuchte Eigenschaft geprüft werden. D.h.: In den 3 Eigenschaften der Probe
	 * ist bei diesen Talenten die gesuchte Eigenschaft enthalten!
	 * (Ist wichtig für die Bestimmung des Min-Wertes bei Eigenschaften)
	 * @param eigEnum Die gesuchte Eigenschaft
	 * @return Alle Zauber, die auf die EIigenschaft "eigEnum" geprobt werden
	 */
	public List<HeldenLink> getZauberList(EigenschaftEnum eigEnum) {
		return Collections.unmodifiableList(hashMapNachEigensch.get(eigEnum));
	}
}
