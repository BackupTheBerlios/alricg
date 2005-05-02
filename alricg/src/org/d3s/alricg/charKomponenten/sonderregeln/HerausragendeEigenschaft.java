/*
 * Created on 30.04.2005 / 14:22:03
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.sonderregeln;

import java.util.ArrayList;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.EigenschaftEnum;
import org.d3s.alricg.charKomponenten.Kultur;
import org.d3s.alricg.charKomponenten.Rasse;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;
import org.d3s.alricg.held.GeneratorLink;
import org.d3s.alricg.held.HeldenLink;
import org.d3s.alricg.prozessor.HeldProzessor;

/**
 * <u>Beschreibung:</u><br> 
 * Erste Implementierung einer Sonderregel. 
 * Implementiert die Sonderregel des Vorteils "Herausragende Eigenschaft", AH S. 108.
 * 
 * Die Sonderregel ist bei der Entwicklung der Architektur entwickelt worden.
 * 
 * TODO Offender Punkt: Kostenberechnnung. Entweder in XML mit einbauen oder auch
 * 		über die Sonderregel.
 * 
 * @author V. Strelow
 */
public class HerausragendeEigenschaft extends SonderregelAdapter {
//	Liste aller möglichen Ziele dieser Sonderregel
	private CharElement[] moeglicheZweitZiele;
	
//	 Speicher die Eigenschaften durch diese Sonderregel "herausragend" sind
	private ArrayList<String> eigenschArray; 
	private HeldProzessor prozessor;
	
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#getId()
	 */
	public String getId() {
		return "SR-HerausragendeEigenschaft";
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#getBeschreibung()
	 */
	public String getBeschreibung() {
		// TODO Muß noch lokalisiert werden
		return "Siehe AH S. 108";
	}
	
	/**
	 * Konstruktor
	 */
	public HerausragendeEigenschaft() {
		
		// Erstellen der wählbaren Eigenschaften
		if (moeglicheZweitZiele == null) {
			moeglicheZweitZiele = new CharElement[] {
					ProgAdmin.charKompAdmin.getCharElement(EigenschaftEnum.MU.getId(), 
															CharKomponente.eigenschaft),
					ProgAdmin.charKompAdmin.getCharElement(EigenschaftEnum.KL.getId(), 
															CharKomponente.eigenschaft),
					ProgAdmin.charKompAdmin.getCharElement(EigenschaftEnum.IN.getId(), 
															CharKomponente.eigenschaft),
					ProgAdmin.charKompAdmin.getCharElement(EigenschaftEnum.CH.getId(), 
															CharKomponente.eigenschaft),
					ProgAdmin.charKompAdmin.getCharElement(EigenschaftEnum.FF.getId(), 
															CharKomponente.eigenschaft),
					ProgAdmin.charKompAdmin.getCharElement(EigenschaftEnum.GE.getId(), 
															CharKomponente.eigenschaft),
					ProgAdmin.charKompAdmin.getCharElement(EigenschaftEnum.KO.getId(), 
															CharKomponente.eigenschaft),																
					ProgAdmin.charKompAdmin.getCharElement(EigenschaftEnum.KK.getId(), 
															CharKomponente.eigenschaft),
			};
			eigenschArray = new ArrayList<String>();
		}

	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#canAddSelf(org.d3s.alricg.held.box.HeldProzessor, boolean, org.d3s.alricg.charKomponenten.links.Link)
	 * 
	 * - Das Ziel dieser Sonderregel (also die Eigenschaft) wird aus dem 
	 * 	 srLink ausgelesen und ist das "zweitZiel".
	 * - Es darf keine Eigenschaft die SF "Herausragende Eigenschaft" bekommen, die 
	 *   durch die Herkunft gesenkt wurde.
	 */
	public boolean canAddSelf(HeldProzessor prozessor, boolean ok, Link srlink) {
		CharElement element;
		Link tmpLink;
		
		// Vorhergehende Prüfungen werden NICHT übergangen
		if (!ok) {
			return false;
		}
		
		// Auslesen des gewünschten Links
		element = srlink.getZweitZiel();
		tmpLink = prozessor.getLinkByCharElement(element, null, null);
		
		// Überprüfung ob die Modis durch die Herkunft u.ä. negativ sind
		if ( ((GeneratorLink) tmpLink).getWertModis() < 0 ) {
			return false;
		}
		
		return true;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#initSonderregel(org.d3s.alricg.held.box.HeldProzessor, org.d3s.alricg.charKomponenten.links.Link)
	 * 
	 * - Das Ziel dieser Sonderregel (also die Eigenschaft) wird aus dem 
	 * 	 srElementlink ausgelesen und ist das "zweitZiel".
	 *  - Die betreffende Eigenschaft wird auf das Maximum gesetzt und um den
	 *    Wert in "link" erhöht. 
	 */
	public void initSonderregel(HeldProzessor prozessor, Link srLink) {
		CharElement element;
		GeneratorLink tmpLink;
		IdLink modiLink;
		
		assert srLink.getZweitZiel() != null;
		this.prozessor = prozessor;
		
		// Auslesen des gewünschten Links
		element = srLink.getZweitZiel();
		tmpLink = (GeneratorLink) prozessor.getLinkByCharElement(element, null, null);

		// Diese Element "merken"
		eigenschArray.add(element.getId());
		
		// Link erstellen der die Eigenschaft um den gewünschten Wert erhöht  
		modiLink = new IdLink(this, null);
		modiLink.setWert(srLink.getWert());
		
		// Neuen Link hinzufügen,
		tmpLink.addLink(modiLink);
		
		// Eigenschaft auf den Maximalen Wert setzen
		tmpLink.setUserWert(prozessor.getMaxWert(tmpLink));	
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#finalizeSonderregel(org.d3s.alricg.charKomponenten.links.Link)
	 */
	public void finalizeSonderregel(Link link) {
		CharElement element;
		GeneratorLink tmpLink, modiLink;
		
		assert link.getZweitZiel() != null;
		
		// Auslesen des gewünschten Links
		element = link.getZweitZiel();
		tmpLink = (GeneratorLink) prozessor.getLinkByCharElement(element, null, null);

		// Suche und entferne den Link aus dem Element
		eigenschArray.remove(link.getZweitZiel().getId());
		tmpLink.removeLinkByQuelle(this);
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeCanAddElement(boolean, org.d3s.alricg.charKomponenten.links.Link)
	 * 
	 * - Es kann keine Rasse oder Kultur hinzugefügt werden, die einen Eigenschaft 
	 *  mit "Herausragender Eigenschaft" erniedrigt
	 */
	public boolean changeCanAddElement(boolean ok, Link tmpLink) {
		CharElement element = tmpLink.getZiel();
		
		if (element instanceof Rasse) {
//			TODO Es darf keine Eigenschaft gesenkt werden, die "Herausragende Eigenschaft" ist
		} else if (element instanceof Kultur) {
//			TODO Es darf keine Eigenschaft gesenkt werden, die "Herausragende Eigenschaft" ist
		}

		return true;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeMaxStufe(int, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int changeMaxStufe(int maxStufe, Link link) {
		// TODO implement??? Muß wahrscheinlich nicht Implementiert werden!
		return maxStufe;
	}
	

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeMinStufe(int, org.d3s.alricg.charKomponenten.links.Link)
	 * - Da eine Herausragende Eigenschaft nicht gesenkt werden kann gilt:
	 *  Minimale Stufe = Maximale Stufe.
	 */
	public int changeMinStufe(int minStufe, Link link) {
		
		if ( eigenschArray.contains(link.getZiel().getId()) ) {
			// Der Wert kann nicht mehr gesenkt werden!
			return prozessor.getMaxWert(link);
		}
		
		return minStufe;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelInterface#changeCanUpdateStufe(boolean, org.d3s.alricg.held.HeldenLink)
	 * - Ein "H. Eigenschaft" kann nicht mehr geändert werden
	 */
	public boolean changeCanUpdateWert(boolean canUpdate, HeldenLink link) {
		// Die Stufe kann (auf normalem Weg) nicht mehr geändert werden
		if ( eigenschArray.contains(link.getZiel().getId()) ) {
			return false;
		}
		return true;
	}
	
	
// ------------------------------------------------------------------------------
	/**
	 * Diese Regel ist nicht im Interface enthalten, da sie NUR diese Sonderregel betrifft
	 * @return Liste der möglichen Zweitziele
	 */
	public CharElement[] getMoeglicheZweitZiele() {
		return moeglicheZweitZiele;
	}
}
