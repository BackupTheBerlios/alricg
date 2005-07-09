/*
 * Created on 02.05.2005 / 19:57:47
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.prozessor.generierung;

import org.d3s.alricg.charKomponenten.CharElement;
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
public class VorteilBoxGen extends AbstractBoxGen {

	/**
	 * Konstruktor.
	 * @param proz Der Prozessor mit dem der zugehörige Held bearbeitet wird.
	 */
	public VorteilBoxGen(HeldProzessor proz) {
		super(proz);
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#addAsNewElement(org.d3s.alricg.charKomponenten.links.IdLink)
	 */
	protected GeneratorLink addAsNewElement(IdLink link) {
		// TODO Auto-generated method stub
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
		
	}

	protected @Override void removeLinkFromElement(IdLink link, boolean stufeErhalten) {
		// TODO Auto-generated method stub
		
	}

}
