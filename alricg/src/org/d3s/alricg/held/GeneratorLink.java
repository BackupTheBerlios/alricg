/*
 * Created 24. April 2005 / 10:44:27
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.held;

import java.util.ArrayList;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.links.IdLink;
import org.d3s.alricg.utils.SimpleList;

/**
 * <b>Beschreibung:</b><br>
 * Wird nur f�r Helden benutzt, die gerade generiert werden. Ein Generatorlink ist
 * eine Verbindung zwischen einem Held und einem CharElemente (bzw. mehrerer). 
 * Ein GeneratorLink kann mehrere IdLinks enthalten, und so wiederspiegel das ein 
 * Element sich aus mehreren Modifiationen durch Herkunft (Rasse, Kultur, Profession) und 
 * Vor-/Nachteilen (z.B. Herausragende Eigenschaft), sowie der Auswahl des Benutzers 
 * zusammensetzt. 
 * 
 * Es ist absichtlich wenig Logik enthalten, diese soll von der �berliegenden Schicht
 * �berpr�ft werden.
 * 
 * @author V.Strelow
 */
public class GeneratorLink extends HeldenLink {
	/* �berlegungen:
	 * - Es kann durch eine Herkunft auch mehrmalig ein Link hinzugef�gt werden (z.B. regul�r und
	 * 	durch eine Auswahl nochmals)
	 * - Es kann mehrer Rassen, Kulturen und Professionen geben (Kind zweier Welten, Breitgef�cherte Bildung)
	 * - Durch den User kann nur einmal ein Wert dazukommen
	 */
	
	/* - Jeder IdLink enth�lt die Info von welcher Herkunft / Auswahl, diese mu� hier dahern 
	 *   nicht nocheinmal gespeichert werden.
	 * - Die Grundwerte (zielId, linkID, text) aller IdLinks in einem Generatorlink sind
	 *   stehts gleich, leitwert und wert k�nnen  sich unterscheiden
	 */
	
	// Alle Links die der User "dazubekommen" hat. Also durch Herkunft, Vorteile, Nachteile, usw.
	private ArrayList<IdLink> linkModiArray = new ArrayList<IdLink>(3);
	
	// Vom Benutzer selbst hinzugef�gter wert!
	private IdLink userLink;
	
	private int kosten; // Aktuelle Kosten
	
	/**
	 * Konstruktor
	 * @param link Link auf dem dieser GeneratorLink basiert.
	 */
	public GeneratorLink(IdLink link) {
		// Setzen der Grundwerte
		this.setZielId(link.getZiel());
		this.setText(link.getText());
		this.setZweitZiel(link.getZweitZiel());
		this.setWert(link.getWert());

		addLink(link);
	}
	
	/**
	 * F�gt einen Link zu diesem GeneratorLink hinzu. 
	 * @param link Der Link der hinzugef�gt werden soll.
	 */
	public void addLink(IdLink link) {
		if (link.getQuellElement() != null) {
			linkModiArray.add(link);
		} else {
			userLink = link;
		}
		
		updateWert();
	}
	
	/**
	 * @param link Der Link der entfernt werden soll.
	 */
	public void removeLink(IdLink link) {
		
		if ( link.equals(userLink) ) {
			userLink = null;
		} else {
			linkModiArray.remove(link);
		}
		
		updateWert();
	}
	
	public void removeLinkByQuelle(CharElement quelle) {
		for (int i = 0; i < linkModiArray.size(); i++) {
			if ( linkModiArray.get(i).getQuellElement().equals(quelle) ) {
				linkModiArray.remove(linkModiArray.get(i));
			}
		}
		updateWert();
	}
	
    /**
     * @return Die aktuellen kosten f�r dieses Element (Od GP oder TalentGP ergibt 
     * sich aus dem Kontext)
     */
    public int getKosten() {
    	return kosten;
    }
    
    /**
     * @param kosten Die neuen gesamtkosten f�r dieses Element
     */
    public void setKosten(int kosten) {
    	this.kosten = kosten;
    }
    
    /**
     * Liefert eine Liste aller Links, AUSSER dem was der User selbst eingestellt hat
     * (dieses kann mit "getUserLink()" abgerufen werden. Es werde also alle links
     * durch Herkunft, Vorteilen, Nachteilen usw. abgerufen.
     *  
     * VORSICHT! Der Wert des GeneratorLinks kann sich von der Summe der hier
     * angegebenden Werte der IdLinks unterscheiden! (wegen "Breitgef�cherte Bildung", 
     * "Veteran", usw.)
     * 
     * @return Eine Liste mit allen Links, durch die der Generatorlink modifiziert wird,
     * 	au�er dem, was der User selbst gew�hlt hat!
     */
    public SimpleList<IdLink> getLinkModiList() {
    	return new SimpleList<IdLink>(linkModiArray);
    }
    
    /**
     * @return Der Teil dieses GeneratorLinks, der vom User hinzugef�gt wurde (also nicht von
     * 		der Herkunft stammt). Gibt es keinen solchen Teil, wird "null" zur�ckgeliefert 
     * 		(was hei�t das der gesamte Link nur aus Modis durch Herkunft besteht) 
     */
    public IdLink getUserLink() {
    	
    	return userLink;
    }
    
    /**
     * 
     * @return Die Stufe ohne die �nderungen durch den User, nur die Modifikationen
     */
    public int getWertModis() {
    	
    	// Es gibt keinen Wert f�r diesen Link!
    	if (this.getWert() == KEIN_WERT) {
    		return KEIN_WERT;
    	}
    	
    	// Es gibt keinen vom User gew�hlten wert, sollt Modis = gesamt-Stufe
    	if (userLink == null) {
    		return this.getWert();
    	}
    	
    	// Gesamt-Stufe abz�glich dessen, was der User gew�hlt hat 
    	return (this.getWert() - userLink.getWert());

    }
    
    
	/**
	 * Hiermit wird der Wert des Elements neu gesetzt. Modifikationen
	 * durch die Herkunft u.�. werden NICHT ver�ndert, sondern nur der Teil, der
	 * durch den User gew�hlt wird. Falls ein Wert kleiner ist, als der durch die
	 * Modis vorgegebene Minimalwert, so wird der Minimalwert beibehalten.
	 * 
	 * @param wert Der gew�nschte gesamtWert des Elements
	 */
	public void setUserWert(int wert) {
		
		// Guard
		if (wert == KEIN_WERT || (wert < this.getWertModis()) ) {
			// Der gew�nschte Wert hat "keinen Wert" oder ist kleiner als m�glich
			if (userLink != null) {
				removeLink(userLink);
			}
			return;
		}
		
		if (userLink == null) {
			// userLink existiert noch nicht und wird neu angelegt 
			userLink = new IdLink(null, null);
			userLink.setZielId(this.getZiel());
			userLink.setText(this.getText());
			userLink.setZweitZiel(this.getZweitZiel());
		}
		
		// Wert setzen, so das der gew�nschte Wert erreicht wird
		userLink.setWert(wert - this.getWertModis());
		
		updateWert(); // Damit der neue Wert auch �bernommen wird
	}

	/**
	 * Bestimmt den Wert des Generator-Links neu, indem die Werte der einzelnen Teil-Links
	 * Addiert werden. Hat auch nur ein Wert den Wert "KEIN_WERT", dann hat gesamte
	 * GeneratorLink keinen Wert (Warum? : Weil es entweder alle Links einen Wert haben
	 * oder keiner)
	 */
	public void updateWert() {
		int tmpWert = 0;
		
		// Pr�fung ob es �berhaupt einen Wert git
		if (this.getWert() == KEIN_WERT) {
			return;
		}
		
		//	Pr�fung des vom User gew�hlten Wertes
		if (userLink != null) {
			tmpWert = userLink.getWert();
		}
		
		// Addition der Modis durch Herkunft u.�.
		for (int i = 0; i < linkModiArray.size(); i++) {
			tmpWert += linkModiArray.get(i).getWert();
    	}
		
		this.setWert(tmpWert); // Setzen des neuen Wertes
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.links.Link#isLeitwert()
	 */
	public boolean isLeitwert() {
		// Wenn auch nur einer der Links Leitwert ist, so ist der gesamte
		// GeneratorLink ein Leitwert!
		for (int i = 0; i < linkModiArray.size(); i++) {
    		if (linkModiArray.get(i).isLeitwert()) {
    			return true;
    		}
    	}
		if (userLink != null && userLink.isLeitwert()) {
			return true;
		}
		
		return false;
	}

}


