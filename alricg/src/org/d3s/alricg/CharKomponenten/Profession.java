/*
 * Created 22. Dezember 2004 / 13:07:41
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

import org.d3s.alricg.CharKomponenten.Links.Auswahl;
/**
 * <b>Beschreibung:</b><br>
 * Repräsentiert eine Profession, speichert alle nötigen Daten.
 * 
 * @author V.Strelow
 */
public class Profession extends Herkunft {
    
    public enum Art { HANDWERKLICH, KRIEGERISCH, GESELLSCHAFTLICH, WILDNESS, MAGISCH, GEWEIHT, SCHAMANISCH }
    public enum Aufwand { ERSTPROF, ZEITAUFW }
    
	private Aufwand aufwand;
	private Art art;
	private Vorteil[] verbotenVort;
	private Nachteil[] verbotenNacht;
	private Sonderfertigkeit[] verbotenSF;
	private Auswahl sprachen;
	private Auswahl schriften;
	private Auswahl ausruestung;
	private Auswahl besondererBesitz;
/*
    public final static int ART_HANDWERKLICH = 0;
	public final static int ART_KRIEGERISCH = 1;
    public final static int ART_GESELLSCHAFTLICH = 2;
    public final static int ART_WILDNESS = 3;
    public final static int ART_MAGISCH = 4;
    public final static int ART_GEWEIHT = 5;
    public final static int ART_SCHAMANISCH = 6;

	public final static int AUFWAND_ERTSPROF = 0;
    public final static int AUFWAND_ZEITAUFW = 1;
    public final static int AUFWAND_ZUSATZPROF = 2; // Bei Elfen*/

	/**
	 * Konstruktur; id beginnt mit "PRO-" für Profession
	 * @param id Systemweit eindeutige id
	 */
	public Profession(String id) {
		setId(id);
	}
	

	public Aufwand getAufwand(){ return aufwand; }

	public void setAufwand(Aufwand aufwand){ this.aufwand = aufwand; }

	public Art getArt(){ return art; }

	public Vorteil[] getVerbotenVort(){ return verbotenVort; }

	public Nachteil[] getVerbotenNacht(){ return verbotenNacht; }

	public Sonderfertigkeit[] getVerbotenSF(){ return verbotenSF; }

	public Auswahl getSprachen(){ return sprachen; }

	public Auswahl getSchriften(){ return schriften; }

	public Auswahl getAusruestung(){ return ausruestung; }

	public Auswahl getBesondererBesitz(){ return besondererBesitz; }

    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	// TODO implement
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	// TODO implement
    	return null;
    }
	
	private static class MagierAkademie {        	
		private int gilde;
    	private int besondereMerkmale[];
    	private boolean zweitStudiumMoeglich;
        private boolean drittStudiumMoeglich;
	}


}
