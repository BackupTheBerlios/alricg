/*
 * Created 22. Dezember 2004 / 13:10:45
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import org.d3s.alricg.Controller.Library;

/**
 * <b>Beschreibung:</b><br>
 * Hilfsklasse zum besseren Arbeiten mit Eigenschaften. 
 * 
 * @author V.Strelow
 */
public enum Eigenschaften {
    MU("Mut", "MU"), 
    KL("Klugheit", "KL"), 
    IN("Intuition", "IN"), 
    CH("Charisma", "CH"), 
    FF("Fingerfertigkeit", "FF"),  
    GE("Gewandheit", "GE"), 
    KO("Konstitution", "KO"), 
    KK("K�rperkraft", "KK"),
    
    SO("Sozialstatus", "SO"),
    MR("Magieresistenz", "MR"),
    
    LEP("Lebenspunkte", "LeP"), 
    ASP("Astralpunkte", "AsP"), 
    AUP("Ausdauerpunkt", "AuP"), 
    KA("Karmaernergie", "KA"), 
    
    INI("Initiative", "INI"), 
    AT("AttackeBasis", "AT"), 
    PA("ParadeBasis", "PA");
    
    
    private String name; // Voller Name der Eigenschaft
    private String abk; // Abk�rzung der Eigenschaft


    /**
     * @param bezeichnung Key f�r Library f�r den vollen Namen
     * @param abkuerzung Key f�r Library f�r die Akk�rzung des Namens
     */
    private Eigenschaften (String bezeichnung, String abkuerzung) {
    	name = Library.getShortText(bezeichnung);
    	abk = Library.getShortText(abkuerzung);
    }

    /**
     * @return Den vollst�ndigen Namen der Eigenschaft
     */
    public String getBezeichnung()  {
        return name;
    }
    
    /**
     * @return Die Abk�rzung des Namens der Eigenschaft
     */
    public String getAbk()  {
        return abk;
    }
    
    /* (non-Javadoc) Methode �berschrieben
     * @see java.lang.Object#toString()
     */
    public String toString()  {
        return abk;
    }
    
    /**
     * @return Gibt die anzahl aller Eigenschaften dieser Klasse an
     */
	public static int getAnzahlEigenschaften() {
        return Eigenschaften.values().length;
	}
}
