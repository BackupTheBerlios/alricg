/*
 * Created on 30.04.2005 / 22:59:44
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.sonderregeln;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Eigenschaft;
import org.d3s.alricg.charKomponenten.EigenschaftEnum;
import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.held.Held;
import org.d3s.alricg.held.HeldenLink;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class Stubenhocker extends SonderregelAdapter {

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelAdapter#getId()
	 */
	public String getId() {
		return "SR-Stubenhocker";
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#initSonderregel(org.d3s.alricg.held.Held, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public void initSonderregel(Held held, Link link) {
		// TODO Auto-generated method stub
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelAdapter#getBeschreibung()
	 */
	public String getBeschreibung() {
		// TODO Auto-generated method stub
		return null;
	}
	

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#changeCanAddElement(boolean, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public boolean changeCanAddElement(boolean canAdd, Link tmpLink) {
		// TODO Auto-generated method stub
		return super.changeCanAddElement(canAdd, tmpLink);
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#changeCanUpdateStufe(boolean, org.d3s.alricg.held.HeldenLink)
	 * Es kann kein körperliche Eigesnchaft 
	 */
	public boolean changeCanUpdateWert(boolean canUpdate, HeldenLink link) {
		String tmpId;
		
		if (link.getZiel() instanceof Eigenschaft) {
			tmpId = ((Eigenschaft) link.getZiel()).getId();
			
			if ( tmpId.equals(EigenschaftEnum.GE.getId())
				|| tmpId.equals(EigenschaftEnum.KK.getId())
				|| tmpId.equals(EigenschaftEnum.KO.getId()) ) 
			{
				return false;
			}
		}
		
		return canUpdate;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#changeKostenKlasse(org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public KostenKlasse changeKostenKlasse(KostenKlasse klasse, Link link) {
		CharElement element = link.getZiel();
		
		if (element instanceof Talent) {
			if ( ((Talent) element).getSorte().equals(Talent.Sorte.kampf)
				|| ((Talent) element).getSorte().equals(Talent.Sorte.koerper) ) 
			{
				return klasse.plusEineKk();
			}
		}

		return klasse;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#changeMaxStufe(int, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int changeMaxStufe(int maxStufe, Link link) {
		// TODO Auto-generated method stub
		return super.changeMaxStufe(maxStufe, link);
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#changeMinStufe(int, org.d3s.alricg.charKomponenten.links.Link)
	 */
	public int changeMinStufe(int minStufe, Link link) {
		// TODO Auto-generated method stub
		return super.changeMinStufe(minStufe, link);
	}
	

}
