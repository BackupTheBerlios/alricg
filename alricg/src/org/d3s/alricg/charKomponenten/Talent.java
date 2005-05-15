/*
 * Created 23. Dezember 2004 / 14:53:48
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import org.d3s.alricg.charKomponenten.links.Voraussetzung;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einf�gen
 * @author V.Strelow
 */
public class Talent extends Faehigkeit {
	
	public enum Art {
		basis("basis"), 
		spezial("spezial"), 
		beruf("beruf");
		private String value; // XML-Tag des Elements
		private String bezeichner; // Name der Angezeigt wird
		
		private Art(String value) {
			this.value = value;
			bezeichner = ProgAdmin.library.getShortTxt(value);
		}
		
		public String getValue() {
			return value;
		}
		
		public String toString() {
			return bezeichner;
		}
	}
	public enum Sorte {
		kampf("kampf"), 
		koerper("koerper"), 
		gesellschaft("gesellschaft"), 
		natur("natur"), 
		wissen("wissen"), 
		handwerk("handwerk");
		private String value; // XML-Tag des Elements
		private String bezeichner; // Name der Angezeigt wird
		
		private Sorte(String value) {
			this.value = value;
			bezeichner = ProgAdmin.library.getShortTxt(value);
		}
		
		public String getValue() {
			return value;
		}
		
		public String toString() {
			return bezeichner;
		}
	}
	
	private String[] spezialisierungen;
	private Art art;
    private Sorte sorte;
    private int abWert; // Bezieht sich auf Voraussetzung
    private TalentVoraussetzung voraussetzung;
    
    
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#getCharKomponente()
	 */
	public CharKomponente getCharKomponente() {
		return CharKomponente.talent;
	}
    
	/**
	 * Konstruktur id; beginnt mit "TAL-" f�r Talent
	 * @param id Systemweit eindeutige id
	 */
	public Talent(String id) {
		setId(id);
	}
    
	/**
	 * @return Liefert das Attribut art.
	 */
	public Art getArt() {
		return art;
	}
	/**
	 * @return Liefert das Attribut sorte.
	 */
	public Sorte getSorte() {
		return sorte;
	}
	
	/**
	 * @return Liefert das Attribut abWert.
	 */
	public int getAbWert() {
		return abWert;
	}
	/**
	 * @param abWert Setzt das Attribut abWert.
	 */
	public void setAbWert(int abWert) {
		this.abWert = abWert;
	}
	/**
	 * @return Liefert das Attribut spezialisierungen.
	 */
	public String[] getSpezialisierungen() {
		return spezialisierungen;
	}
	/**
	 * @param spezialisierungen Setzt das Attribut spezialisierungen.
	 */
	public void setSpezialisierungen(String[] spezialisierungen) {
		this.spezialisierungen = spezialisierungen;
	}
	/**
	 * @return Liefert das Attribut voraussetzung.
	 */
	public TalentVoraussetzung getVoraussetzung() {
		return voraussetzung;
	}
	/**
	 * @param voraussetzung Setzt das Attribut voraussetzung.
	 */
	public void setVoraussetzung(TalentVoraussetzung voraussetzung) {
		this.voraussetzung = voraussetzung;
	}
	/**
	 * @param art Setzt das Attribut art.
	 */
	public void setArt(Art art) {
		this.art = art;
	}
	/**
	 * @param sorte Setzt das Attribut sorte.
	 */
	public void setSorte(Sorte sorte) {
		this.sorte = sorte;
	}
    
    /**
     * <u>Beschreibung:</u><br> 
     * Bei manchen Talenten gilt die Voraussetzung erst am einem bestimmten Wert
     * (typischer Weise 10). Dieser Wert kann hier angegeben werden!
     * @author V. Strelow
     */
    public class TalentVoraussetzung extends Voraussetzung {
    	private int abWert = 1; // Ab welchem Wert diese Voraussetzung gilt
    	
        public TalentVoraussetzung(CharElement quelle) {
            super(quelle);
        }

        public TalentVoraussetzung(CharElement quelle, int abWert) {
			super(quelle);
            this.abWert = abWert;
		}
        
        public int getAbWert() {
            return abWert;
        }
    }
}
