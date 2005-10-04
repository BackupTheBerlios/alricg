/*
 * Created 24. April 2005 / 10:39:15
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.prozessor.generierung;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Eigenschaft;
import org.d3s.alricg.charKomponenten.EigenschaftEnum;
import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.charKomponenten.links.Link;
import org.d3s.alricg.held.GeneratorLink;
import org.d3s.alricg.held.HeldenLink;
import org.d3s.alricg.prozessor.FormelSammlung;
import org.d3s.alricg.prozessor.HeldProzessor;
import org.d3s.alricg.prozessor.HeldUtilities;
import org.d3s.alricg.prozessor.FormelSammlung.KostenKlasse;

/**
 * <b>Beschreibung:</b><br> 
 * Diese Klasse ist nur dafür gedacht die Regeln von Talenten bei der 
 * Charaktererschaffung zu implementieren. Es enthält alle Talente die 
 * ein Held besitzt und bietet Methoden an um diese zu bearbeiten.
 * 
 * BEACHTE:
 * Um zu repräsentieren das ein Talent aktiviert werden muß, ist dessen Quelle "null".
 * 
 * @author V.Strelow
 */
public class TalentBoxGen extends AbstractBoxGen {
    
    /** <code>TalentBoxGen</code>'s logger */
    private static final Logger LOG = Logger.getLogger(TalentBoxGen.class.getName());
    
	//protected ArrayList<GeneratorLink> linkArray;
	//protected Held held;
	
	// Speicher alle Talente die aktiviert wurden
	private ArrayList<Talent> aktivierteTalente = new ArrayList<Talent>();
	
	/* Hält alle Talente nach den Eigenschaften sortiert, auf die die Probe
	 * Abgelegt wird (wichtig für schnellen Zugriff bei berechnung der Min-Werte
	 * bei Eigenschaften) */ 
	private HashMap<EigenschaftEnum, ArrayList<HeldenLink>> hashMapNachEigensch;
	
	// Die gesamtkosten für alle Talente
	private int talentGpKosten = 0;
	
	/**
	 * Konstruktor.
	 * @param proz Der Prozessor mit dem der zugehörige Held bearbeitet wird.
	 */
	public TalentBoxGen(HeldProzessor proz) {
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
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#addAsNewElement(org.d3s.alricg.charKomponenten.links.IdLink)
	 */
	protected GeneratorLink addAsNewElement(IdLink link) {
		final GeneratorLink tmpLink;
		final Talent tmpTalent = (Talent) link.getZiel();
		
		// Voraussetzungen hinzufügen, falls vorhanden
		if (tmpTalent.getVoraussetzung() != null) {
			prozessor.getVoraussetzungenAdmin().addVoraussetzung(
					tmpTalent.getVoraussetzung()
				);
		}
		
		//Link wird erstellt und zur List hinzugefügt
		tmpLink = new GeneratorLink(link);
		linkArray.add(tmpLink);
		
		// Fügt den Link zu den entsprechenden HashMaps hinzu
		for (int i = 0; i < tmpTalent.get3Eigenschaften().length; i++) {
			// Falls das Talente mehrmals auf die selbe Eigenschaft geprobt wird, 
			// wird es nur einmal hinzugefüght
			if ( !hashMapNachEigensch.get(tmpTalent.get3Eigenschaften()[i].getEigenschaftEnum())
							.contains(tmpLink) ) {
					hashMapNachEigensch.get(tmpTalent.get3Eigenschaften()[i].getEigenschaftEnum())
								.add(tmpLink);
				;
			}
		}
		
		// Prüfen ob Talent akiviert werden muß
		pruefeTalentAktivierung(tmpLink);
		
		updateKosten(tmpLink); // Kosten Aktualisieren
		
		return tmpLink;
//		 Sonderregel wird von der überliegenden Ebende aufgerufen
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#canAddAsNewElement(org.d3s.alricg.charKomponenten.links.IdLink)
	 */
	protected boolean canAddAsNewElement(IdLink link) {
		GeneratorLink tmpLink;
		
		if (link.getQuellElement() == null
				&& aktivierteTalente.size() >= 
					GenerierungProzessor.genKonstanten.MAX_TALENT_AKTIVIERUNG)
		{
			// Muß aktiviert werden, aber dies ist nicht mehr möglich!
			return false;
		}
		
		if (contiansEqualLink(link)) {
			// So ein Element ist schon vorhanden, geht also nicht!
			return false;
		} 
		
		return true;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.prozessor.generierung.AbstractBoxGen#canAddCharElement(org.d3s.alricg.charKomponenten.CharElement)
	 */
	protected boolean canAddCharElement(CharElement elem) {
		
		// TODO Wird diese Methode überhaupt noch benötigt?? Denke nein!
		// Voraussetzungen werden über den VoraussetzungsAdmin geprüft
		return true;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#canRemoveElement(org.d3s.alricg.held.HeldenLink)
	 */
	protected boolean canRemoveElement(HeldenLink link) {

		if (((Talent) link.getZiel()).getArt().equals(Talent.Art.basis) ) {
			return false; // Basis Talente können nicht entfernt werden!
		}
		// Voraussetzungen werden über den VoraussetzungsAdmin geprüft
		return true;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#addLinkToElement(org.d3s.alricg.charKomponenten.links.IdLink, boolean)
	 */
	protected GeneratorLink addLinkToElement(IdLink link, boolean stufeErhalten) {
		GeneratorLink tmpLink;
		int oldWert;
		
		tmpLink = getEqualLink(link);
		
		// Das Element muß vorhanden sein!
		assert tmpLink != null;
		
		if (stufeErhalten) {
			oldWert = tmpLink.getWert(); // Alten Wert Speichern
			tmpLink.addLink(link); // Link hinzufügen
			tmpLink.setUserGesamtWert(oldWert); // Versuchen den alten Wert wiederherzustellen
		} else {
			tmpLink.addLink(link);
		}
		
		HeldUtilities.inspectWert(tmpLink, prozessor);
		
		// evtl. den Status als "aktiviertes Talent" entziehen
		pruefeTalentAktivierung(tmpLink);
		
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
			((GeneratorLink) link).setUserGesamtWert(stufe);
		}
		
		// Updaten des Textes
		// TODO Hier kann noch Verbeserungsbedarf sein. Kosten für SF?
		if (text != null) {
			link.setText(text);
		}
		
		// ZweitZiel gibt es bei Talenten nicht!
		
		// Talent aktivierung ggf. neu setzen
		pruefeTalentAktivierung((GeneratorLink) link);
		
		updateKosten((GeneratorLink) link); // Kosten aktualisieren
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#getMaxWert(org.d3s.alricg.charKomponenten.links.Link)
	 */
	protected int getMaxWert(Link link) {
		final Eigenschaft[] eigenschaftAR;
		int maxWert = 0;
		
		// Die 3 Eigeschaften holen
		eigenschaftAR = ((Talent) link.getZiel()).get3Eigenschaften();
		
		// Höchste Eigenschaft Bestimmen
		for (int i = 0; i < eigenschaftAR.length; i++) {
			prozessor.getLinkByCharElement(eigenschaftAR[i], null, null);
			maxWert = Math.max(maxWert,
					prozessor.getHeld().getEigenschaftsWert(
							eigenschaftAR[i].getEigenschaftEnum()
						)
				);
		};
		
		// Höchste Eigenschaft +3 / Siehe MFF S. 45
		return (maxWert + 3);
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#getMinWert(org.d3s.alricg.charKomponenten.links.Link)
	 */
	protected int getMinWert(Link link) {
		
		// Der niedrigeste Wert ist stehts "0", es sei den es gibt Modis (auch negativ)
		return ((GeneratorLink) link).getWertModis();
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
		// Kann nur geändert werden, wenn schon Text (= Eine SF) besteht!)
		if (link.hasText()) {
			return true;
		}
		
		return false;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.held.box.generierung.AbstractBoxGen#canUpdateZweitZiel(org.d3s.alricg.held.HeldenLink)
	 */
	protected boolean canUpdateZweitZiel(HeldenLink link) {
		return false; // Gibt es nicht bei Talenten!
	}
	
	/**
	 * Berechnet die Kosten die für dieses Element aufgewendet werden müssen neu.
	 * @param link Der Link zu dem Element, für das die Kosten berechnet werden
	 */
	protected void updateKosten(GeneratorLink genLink) {
		final Talent tmpTalent;
		final int alteKosten;
		KostenKlasse kKlasse;
		int kosten, vonStufe;
		
		// Alte Kosten merken
		alteKosten = genLink.getKosten();
		
		// Bestimme das Talent
		tmpTalent = (Talent) genLink.getZiel();
		
		// Bestimme die Kostenklasse
		kKlasse = tmpTalent.getKostenKlasse();
		kKlasse = prozessor.getSonderregelAdmin().changeKostenKlasse(kKlasse, genLink);
		
		// Aktivieren oder nicht? Bei negativen modis muß das Talent auch aktiviert werden
		// und die "vonStufe" ist der negative Modi!
		if ( aktivierteTalente.contains(tmpTalent) 
				&& genLink.getWertModis() >= 0) {
			vonStufe = CharElement.KEIN_WERT;
		} else {
			vonStufe = genLink.getWertModis();
		}
		
		// Errechne die Kosten
		kosten = FormelSammlung.berechneSktKosten(
				vonStufe, // von Stufe (KEIN_WERT falls aktiviert)
				genLink.getWert(), // bis Stufe
				kKlasse // in dieser Kostenklasse
		);
		
		kosten = prozessor.getSonderregelAdmin().changeKosten(kosten, genLink);
		
		// Setze die Kosten
		genLink.setKosten(kosten);
		
		// Gesamt-Kosten für alle Talente setzen
		talentGpKosten += kosten - alteKosten;
	}

	
	protected @Override void removeElement(HeldenLink element) {
		final Talent tmpTalent = (Talent) element.getZiel();
		
		// evtl. den Status als "aktiviertes Talent" entziehen
		if ( aktivierteTalente.contains(tmpTalent) ) {
			aktivierteTalente.remove(tmpTalent);
		}
		
		// Voraussetzungen entfernen, falls vorhanden
		if (tmpTalent.getVoraussetzung() != null) {
			prozessor.getVoraussetzungenAdmin().removeVoraussetzung(
					tmpTalent.getVoraussetzung()
				);
		}
		
		// Entfernen aus der Liste
		linkArray.remove(element);

		// Entfernd den Link aus der entsprechenden HashMap
		for (int i = 0; i < tmpTalent.get3Eigenschaften().length; i++) {
			if ( hashMapNachEigensch.get(tmpTalent.get3Eigenschaften()[i].getEigenschaftEnum())
							.contains(element) ) {
					hashMapNachEigensch.get(tmpTalent.get3Eigenschaften()[i].getEigenschaftEnum())
							.remove(element);
			}
		}
		
		// Kosten für dieses Element von den Talent Gesamtkosten abziehen
		talentGpKosten -= ((GeneratorLink) element).getKosten();
	}

	
	protected @Override void removeLinkFromElement(IdLink link, boolean stufeErhalten) {
		final GeneratorLink genLink;
		final List<IdLink> list;
		int tmpInt;
		
		// Generator-Link holen
		genLink = this.getEqualLink(link);
		
		if (genLink == null) {
			LOG.warning("Konnte Link nicht finden beim Entfernen eines Modis");
		}
		
		// Link entfernen
		genLink.removeLink(link);
		
		// Stufe ggf. neu setzen
		HeldUtilities.inspectWert(genLink, prozessor);
		
		// Es gibt keine Modis mehr, der Held hat keine Stufe gewählt,
		// das Talent wird daher vom Helden entfernd
		if (genLink.getWert() == 0) {
			removeElement(genLink);
			return;
		}

		// Die aktivierung des Talents ggf. neu setzen.
		pruefeTalentAktivierung(genLink);
		
		// Kosten aktualisieren
		updateKosten(genLink);
	}
	
	
	/**
	 * @return Die Kosten die entstehen, um alle Talente zu bezahlen.
	 */ @Override
	public int getGesamtKosten() {
		return talentGpKosten;
	}
	
// -----------------------------------------------------------------------------------
	
	 /**
	  * Aktiviert ein Talent wenn nötig, ansonsten wird es von 
	  */
	private void pruefeTalentAktivierung(GeneratorLink genLink) {
		/* Das Talent ist genau dann aktiviert, wenn:
		 * - Es kein Basis Talent ist
		 * - Es keine Modis gibt
		 * - Die Modis negativ sind, die Stufe aber >= 0 ist
		 */ 
		
		if ( ((Talent) genLink.getZiel()).getArt().equals(Talent.Art.basis) ) {
			// Basis Talente können nie aktiviert (und somit auch nicht deaktiviert) werden
			return;
		}
		
		// Den "aktiviert Status" des Talents setzen
		aktivierteTalente.remove((Talent) genLink.getZiel());
		if ( (genLink.getLinkModiList().size() == 0)
				|| (genLink.getWertModis() < 0 && genLink.getWert() >= 0)  ) 
		{
			aktivierteTalente.add((Talent) genLink.getZiel());
		}
	}
	 
    /**
	 * @return Liefert die Talente die aktiviert wurden.
	 */
	public List<Talent> getAktivierteTalente() {
		return Collections.unmodifiableList(aktivierteTalente);
	}
	
	/**
	 * Überprüft ob ein Talent aktiviert wurde!
	 * @param tal Das Talent das geprüft wird
	 * @return true - Dieses Talent ist als aktiviert enthalten,
	 * 		false - Dieses Talent ist NICHT aktiviert (egal ob enthalten oder nicht)
	 */
	public boolean isAktiviert(Talent tal) {
		return aktivierteTalente.contains(tal);
	}
	
	/**
	 * Liefert alle Links zu Talenten, die in der Probe auf mindesten einmal die 
	 * gesuchte Eigenschaft geprüft werden. D.h.: In den 3 Eigenschaften der Probe
	 * ist bei diesen Talenten die gesuchte Eigenschaft enthalten!
	 * (Ist wichtig für die Bestimmung des Min-Wertes bei Eigenschaften)
	 * @param eigEnum Die gesuchte Eigenschaft
	 * @return Alle Talente, die auf die EIgenschaft "eigEnum" geprobt werden
	 */
	public List<HeldenLink> getTalentList(EigenschaftEnum eigEnum) {
		return Collections.unmodifiableList(hashMapNachEigensch.get(eigEnum));
	}
		
}
