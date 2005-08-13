/*
 * Created on 30.04.2005 / 22:59:44
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten.sonderregeln;

import java.util.List;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Eigenschaft;
import org.d3s.alricg.charKomponenten.EigenschaftEnum;
import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.held.GeneratorLink;
import org.d3s.alricg.held.HeldenLink;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;

/**
 * <u>Beschreibung:</u><br> 
 * Implementiert die Sonderregel zu dem Nachteil "Stubenhocker", Siehe  AZ S. 152.
 * 
 * TODO Nicht vereinbarkeit mit anderen Sonderregeln wird im zugehörigem CharElement geregeln.
 * 
 * Erfüllt:
 * unvereinbar mit "Herausragener Eigenschaft GE" bzw. "KK", "KO" (wegen der Max stufe)
 * 
 * @author V. Strelow
 */
public class Stubenhocker extends SonderregelAdapter {
	private HeldProzessor prozessor;
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelAdapter#getId()
	 */
	public String getId() {
		return "SR-Stubenhocker";
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#finalizeSonderregel(org.d3s.alricg.charKomponenten.links.Link)
	 */
	@Override
	public void finalizeSonderregel(Link link) {
		final List<GeneratorLink> list;
		Talent tmpTalent;
		
		list = prozessor.getHeld().getElementBox(CharKomponente.talent).getUnmodifiableList();
		
		// Die Veränderung der Kosten wieder rückgängig machen!
		for (int i = 0; i < list.size(); i++) {
			tmpTalent = (Talent) list.get(i).getZiel();
			if ( list.get(i).getKosten() > 0 && 
					( tmpTalent.getSorte().equals(Talent.Sorte.kampf)
					|| tmpTalent.getSorte().equals(Talent.Sorte.koerper) ) )
			{
				// Kosten anpassen
				prozessor.updateKosten(list.get(i));
			}
		}
	}



	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#initSonderregel(org.d3s.alricg.prozessor.HeldProzessor, org.d3s.alricg.charKomponenten.links.Link)
	 */
	@Override
	public void initSonderregel(HeldProzessor prozessor, Link srLink) {
		final List<GeneratorLink> list;
		Talent tmpTalent;
		
		this.prozessor = prozessor;
		
		if ( !prozessor.isGenerierung() ) {
			return;
		}
		
		list = prozessor.getHeld().getElementBox(CharKomponente.talent).getUnmodifiableList();
		
		// Alle Kampf/Körper Talente max um zweit gesteigert!
		for (int i = 0; i < list.size(); i++) {
			tmpTalent = (Talent) list.get(i).getZiel();
			if ( list.get(i).getKosten() > 0 && 
					( tmpTalent.getSorte().equals(Talent.Sorte.kampf)
					|| tmpTalent.getSorte().equals(Talent.Sorte.koerper) ) )
			{
				if ( list.get(i).getWert()-2 >  list.get(i).getWertModis() ) {
					// Stufe aktualisieren
					prozessor.updateElement(list.get(i), list.get(i).getWertModis()+2, null, null);
				} else {
					// Kosten anpassen
					prozessor.updateKosten(list.get(i));
				}
			}
		}
		
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
		final Talent talent = (Talent) tmpLink.getZiel();
		
		// Guards: 
		if (!prozessor.isGenerierung() ) {
			// Nach der Generierung ist ein hinzufügen immer OK.
			return canAdd; 
		} else if ( !tmpLink.getZiel().getCharKomponente().equals(CharKomponente.talent) ) {
			// Wenn es kein Talent ist, ist es nicht betroffen
			return canAdd;
		} else if ( ((IdLink) tmpLink).getQuellElement() != null) {
			// Dieser Link ist über eine Rasse o.ä. hinzugefügt --> KEINE Kosten
			return canAdd;
		} 
		
		// Wenn es ein Kampf oder Körperliches Talent ist, gilt die "Max 5 Talente steigern" Regel
		if ( ((Talent) tmpLink.getZiel()).getSorte().equals(Talent.Sorte.kampf) 
				|| ((Talent) tmpLink.getZiel()).getSorte().equals(Talent.Sorte.koerper) ) {
			
			if (getKampfKoerpAnzahl() >= 5) {
				return false;
			}
		}
		
		return canAdd;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.sonderregeln.SonderregelInterface#changeCanUpdateStufe(boolean, org.d3s.alricg.held.HeldenLink)
	 * Es kann kein körperliche Eigenschaft 
	 */
	public boolean changeCanUpdateWert(boolean canUpdate, HeldenLink link) {
		final String tmpId;
		final List<GeneratorLink> list;
		int counter = 0;
		
		// Die Eigenschaften (GE, KK, KO) dürfen nach der Generierung nicht geändert werden!
		if (link.getZiel() instanceof Eigenschaft && !prozessor.isGenerierung()) {
			tmpId = ((Eigenschaft) link.getZiel()).getId();
			
			if ( tmpId.equals(EigenschaftEnum.GE.getId())
				|| tmpId.equals(EigenschaftEnum.KK.getId())
				|| tmpId.equals(EigenschaftEnum.KO.getId()) ) 
			{
				return false;
			}
		} 
		
		if (!prozessor.isGenerierung()) {
			return canUpdate;
		}

//		 Es dürfen nur 5 Kampf oder Körperliche Talente überhaupt mit GP gesteigert werden!
		if (link.getZiel() instanceof Talent
				&& ( ((Talent) link.getZiel()).getSorte().equals(Talent.Sorte.kampf)
						 || ((Talent) link.getZiel()).getSorte().equals(Talent.Sorte.koerper) )
				&& prozessor.isGenerierung()) 
		{
			if ( ((GeneratorLink) link).getKosten() == 0  
					&& getKampfKoerpAnzahl() >= 5 ) {
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
		final CharElement element = link.getZiel();
		final String tmpId;
		
		if (element instanceof Talent && prozessor.isGenerierung()) {
			if ( ((Talent) element).getSorte().equals(Talent.Sorte.kampf)
				|| ((Talent) element).getSorte().equals(Talent.Sorte.koerper) ) 
			{
				if ( ((GeneratorLink) link).getKosten() == 0 
						&& getKampfKoerpAnzahl() >= 5 ) {
					// Das Talent darf nicht gesteigert werden, da schon 5 Talente 
					// Kosten veruhrsachen
					return ( ((GeneratorLink) link).getWertModis() );
				}
				
				// Darf gesteigert werden, aber maximal +2
				return ( ((GeneratorLink) link).getWertModis() + 2 );
			}
		} else if (element instanceof Eigenschaft) {
			tmpId = link.getZiel().getId();
			
			if ( tmpId.equals(EigenschaftEnum.GE.getId())
					|| tmpId.equals(EigenschaftEnum.KK.getId())
					|| tmpId.equals(EigenschaftEnum.KO.getId()) ) 
				{
					return 11;
				}
		}
		
		return maxStufe;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.sonderregeln.SonderregelAdapter#canAddSelf(org.d3s.alricg.prozessor.HeldProzessor, boolean, org.d3s.alricg.charKomponenten.links.Link)
	 */
	@Override
	public boolean canAddSelf(HeldProzessor prozessor, boolean ok, Link srLink) {

		if ( getKampfKoerpAnzahl(prozessor) > 5) {
			return false;
		} else if ( prozessor.getHeld().getEigenschaftsWert(EigenschaftEnum.GE) > 11) {
			return false;
		} else if (prozessor.getHeld().getEigenschaftsWert(EigenschaftEnum.KK)  > 11) {
			return false;
		} else if (prozessor.getHeld().getEigenschaftsWert(EigenschaftEnum.KO)  > 11) {
			return false;
		}
		
		return ok;
	}
	
	/**
	 * Zählt wie viele Körperliche/ Kampf Talenten bisher mit Talent-GP gesteigert wurden.
	 * @return Die Anzahl an gesteigerten Kampf/ Körperlichen Talenten (max. sind 5 erlaubt)
	 */
	private int getKampfKoerpAnzahl() {
		return getKampfKoerpAnzahl(prozessor);
	}
	
	/**
	 * Zählt wie viele Körperliche/ Kampf Talenten bisher mit Talent-GP gesteigert wurden.
	 * @return Die Anzahl an gesteigerten Kampf/ Körperlichen Talenten (max. sind 5 erlaubt)
	 */
	private int getKampfKoerpAnzahl(HeldProzessor prozessorIn) {
		final List<GeneratorLink> list;
		int counter = 0;
		
		list = prozessorIn.getHeld().getElementBox(CharKomponente.talent).getUnmodifiableList();
		
		for (int i = 0; i < list.size(); i++) {
			if ( list.get(i).getKosten() > 0 && 
					( ((Talent) list.get(i).getZiel()).getSorte().equals(Talent.Sorte.kampf)
					|| ((Talent) list.get(i).getZiel()).getSorte().equals(Talent.Sorte.koerper) ) )
			{
				counter++;
			}
		}
		
		return counter;
	}
}
