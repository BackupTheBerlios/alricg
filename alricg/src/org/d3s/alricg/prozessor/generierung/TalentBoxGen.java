/*
 * Created 24. April 2005 / 10:39:15
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.prozessor.generierung;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.held.GeneratorLink;
import org.d3s.alricg.held.Held;
import org.d3s.alricg.held.HeldenLink;
import org.d3s.alricg.prozessor.FormelSammlung;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;

/**
 * <b>Beschreibung:</b><br> 
 * Diese Klasse ist nur dafür gedacht die Regeln von Talenten bei der 
 * Charaktererschaffung zu implementieren. Es enthält alle Talente die 
 * ein Held besitzt und bietet Methoden an um diese zu bearbeiten.
 * 
 * Diese Klasse ist bei der Entwicklung der Achitektur Teilweise implementiert worden.
 * 
 * Sie ist jedoch nicht völlig fertig!
 * 
 * @author V.Strelow
 */
public class TalentBoxGen extends AbstractBoxGen {
	//protected ArrayList<GeneratorLink> linkArray;
	//protected Held held;
	
	private int aktivierteTalente;
	

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#addAsNewElement(org.d3s.alricg.charKomponenten.links.IdLink)
	 */
	protected GeneratorLink addAsNewElement(IdLink link) {
		GeneratorLink tmpLink;
		
		if (link.getQuellElement() == null) {
			aktivierteTalente++; // Wurde vom User aktiviert
		}
		
		//Link wird erstellt und zur List hinzugefügt
		tmpLink = new GeneratorLink(link);
		linkArray.add(tmpLink);
		
		updateKosten(tmpLink); // Kosten Aktualisieren
		
		return tmpLink;
//		 Sonderregel wird von der überliegenden Ebende aufgerufen
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#canAddAsNewElement(org.d3s.alricg.charKomponenten.links.IdLink)
	 */
	protected boolean canAddAsNewElement(IdLink link) {
		GeneratorLink tmpLink;
		
		tmpLink = getEqualLink(link);
		if (tmpLink == null) {
			// Der Link würde ein neues Element sein
			
			// TODO Noch Aktivierbar?
			// TODO Voraussetzungen erfüllt?
			// TODO Mit anderen Elementen vereinbar?
		} 
		
		if (link.getQuellElement() == null) {
			Held.heldUtils.erfuelltVoraussetzung(tmpLink);
		}
		
		return true;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#canAddCharElement(org.d3s.alricg.charKomponenten.CharElement)
	 */
	protected boolean canAddCharElement(CharElement elem) {
		
		// TODO Voraussetzungen erfüllt?
		// TODO Mit anderen Elementen vereinbar?
		
		return true;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#canRemoveElement(org.d3s.alricg.held.HeldenLink)
	 */
	protected boolean canRemoveElement(HeldenLink link) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#addLinkToElement(org.d3s.alricg.charKomponenten.links.IdLink, boolean)
	 */
	protected GeneratorLink addLinkToElement(IdLink link, boolean stufeErhalten) {
		GeneratorLink tmpLink;
		int oldWert;
		
		tmpLink = getEqualLink(link);
		
		if (stufeErhalten) {
			oldWert = tmpLink.getWert(); // Alten Wert Speichern
			tmpLink.addLink(link); // Link hinzufügen
			tmpLink.setUserWert(oldWert); // Versuchen den alten Wert wiederherzustellen
		} else {
			tmpLink.addLink(link);
		}
		
		Held.heldUtils.inspectWert(tmpLink, prozessor);
		
		updateKosten(tmpLink); // Kosten Aktualisieren
		
		return tmpLink;
//		 Sonderregel wird von der überliegenden Ebende aufgerufen
	}
		

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#updateElement(org.d3s.alricg.held.HeldenLink, int, java.lang.String, org.d3s.alricg.charKomponenten.CharElement)
	 * 
	 * Ein bereits bestehendes Element durch den User ändern.
	 */
	protected void updateElement(HeldenLink link, int stufe, String text, CharElement zweitZiel) {
		int tmpInt;

		// Updaten der Stufe
		if (stufe != Link.KEIN_WERT) {
			((GeneratorLink) link).setUserWert(stufe);
		}
		
		// Updaten des Textes
		// TODO Hier kann noch Verbeserungsbedarf sein.
		if (text != null) {
			link.setText(text);
		}
		
		// Updaten des zweitZiels
		// TODO Hier kann noch Verbeserungsbedarf sein.
		if (zweitZiel != null) {
			link.setZweitZiel(zweitZiel);
		}
		
		updateKosten((GeneratorLink) link); // Kosten Aktualisieren
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#getMaxWert(org.d3s.alricg.charKomponenten.links.Link)
	 */
	protected int getMaxWert(Link link) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#getMinWert(org.d3s.alricg.charKomponenten.links.Link)
	 */
	protected int getMinWert(Link link) {
		// TODO Auto-generated method stub
		return 0;
	}


	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#canUpdateWert(org.d3s.alricg.held.HeldenLink)
	 */
	protected boolean canUpdateWert(HeldenLink link) {
		// Grundsätzlich kann der Wert eines Talents geändert werden
		return true;
	}
	

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#canUpdateText(org.d3s.alricg.held.HeldenLink)
	 */
	protected boolean canUpdateText(HeldenLink link) {
		if (link.hasText()) {
			return true;
		}
		
		return false;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#canUpdateZweitZiel(org.d3s.alricg.held.HeldenLink)
	 */
	protected boolean canUpdateZweitZiel(HeldenLink link) {
		return false;
	}
	
	/**
	 * Berechnet die Kosten die für dieses Element aufgewendet werden müssen neu.
	 * @param link Der Link zu dem Element, für das die Kosten berechnet werden
	 */
	protected void updateKosten(GeneratorLink genLink) {
		Talent tmpTalent;
		KostenKlasse kKlasse;
		int kosten;
		
//		Hat der User überhaupt einen Wert gewählt, ansonsten auch keine Kosten
		if (genLink.getUserLink() != null) {
			return;
		}
		
		// Bestimme das Talent
		tmpTalent = (Talent) genLink.getZiel();
		
		// Bestimme die Kostenklasse
		kKlasse = tmpTalent.getKostenKlasse();
		kKlasse = prozessor.getHeld().getSonderregelAdmin().changeKostenKlasse(kKlasse, genLink);
		
		// Errechne die Kosten
		kosten = FormelSammlung.berechneSktKosten(
				genLink.getWertModis(), // von Stufe
				genLink.getWert(), // bis Stufe
				kKlasse // in dieser Kostenklasse
		);
		
		kosten = prozessor.getHeld().getSonderregelAdmin().changeKosten(kosten, genLink);
		
		// Setze die Kosten
		genLink.setKosten(kosten);
	}

	/**
	 * @return Liefert die Anzahl an Talenten die Aktiviert wurden.
	 */
	protected int getAktivierteTalente() {
		return aktivierteTalente;
	}
}
