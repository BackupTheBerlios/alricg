/*
 * Created 22. Dezember 2004 / 13:10:45
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import java.util.ArrayList;

import org.d3s.alricg.Controller.Library;

/**
 * <b>Beschreibung:</b><br>
 * Hilfsklasse zum besseren Arbeiten mit Eigenschaften. 
 * 
 * @author V.Strelow
 */
public enum EigenschaftEnum {
    MU("Mut", 			"MU", "EIG-MU"), 
    KL("Klugheit", 		"KL", "EIG-KL"), 
    IN("Intuition", 	"IN", "EIG-IN"), 
    CH("Charisma", 		"CH", "EIG-CH"), 
    FF("Fingerfertigkeit", "FF", "EIG-FF"),  
    GE("Gewandheit", 	"GE", "EIG-GE"), 
    KO("Konstitution", 	"KO", "EIG-KO"), 
    KK("K�rperkraft", 	"KK", "EIG-KK"),
    
    SO("Sozialstatus",	"SO", "EIG-SO"),
    MR("Magieresistenz", "MR", "EIG-MR"),
    
    LEP("Lebenspunkte", "LeP", "EIG-Lep"), 
    ASP("Astralpunkte", "AsP", "EIG-AsP"), 
    AUP("Ausdauerpunkt", "AuP", "EIG-AuP"), 
    KA("Karmaernergie", "KA", "EIG-KA"), 
    
    GS("Geschwindigkeit", "GS", "EIG-GS"),
    INI("Initiative", 	"INI", "EIG-INI"), 
    AT("AttackeBasis", 	"AT", "EIG-AT"), 
    PA("ParadeBasis", 	"PA", "EIG-PA");
    
    
    private String name; // Voller Name der Eigenschaft
    private String abk; // Abk�rzung der Eigenschaft
    private String id; // ID der Eigenschaft

    /**
     * @param bezeichnung Key f�r Library f�r den vollen Namen
     * @param abkuerzung Key f�r Library f�r die Akk�rzung des Namens
     */
    private EigenschaftEnum (String bezeichnung, String abkuerzung, String id) {
    	name = Library.getShortTxt(bezeichnung);
    	abk = Library.getShortTxt(abkuerzung);
    	this.id = id;
    }

    /**
     * @return Den vollst�ndigen Namen der Eigenschaft
     */
    public String getBezeichnung()  {
        return name;
    }
    
    public String getId() {
    	return id;
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
     * Diese Methode wird vor allem f�r die initialisierung ben�tigt!
     * @return Eine ArrayList mit den IDs aller Eigenschaften
     */
    public static ArrayList<String> getIdArray() {
    	ArrayList<String> ids;
    	
    	ids = new ArrayList<String>(EigenschaftEnum.values().length);
    	
    	for (int i = 0; i < EigenschaftEnum.values().length; i++) {
    		ids.add( EigenschaftEnum.values()[i].getId() );
    	}
    	
    	return ids;
    }
    
    /**
     * @return Gibt die anzahl aller Eigenschaften dieser Klasse an
     */
	public static int getAnzahlEigenschaften() {
        return EigenschaftEnum.values().length;
	}
}

