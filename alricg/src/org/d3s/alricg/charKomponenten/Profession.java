/*
 * Created 22. Dezember 2004 / 13:07:41
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.Werte.Gilde;
import org.d3s.alricg.charKomponenten.Werte.MagieMerkmal;
import org.d3s.alricg.charKomponenten.links.Auswahl;
import org.d3s.alricg.charKomponenten.links.AuswahlAusruestung;
import org.d3s.alricg.charKomponenten.links.IdLinkList;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.controller.CharKompAdmin.CharKomponente;
/**
 * <b>Beschreibung:</b><br>
 * Repräsentiert eine Profession, speichert alle nötigen Daten.
 * 
 * @author V.Strelow
 */
public class Profession extends Herkunft {
   
    public enum Art { 
    	handwerklich("handwerklich"), 
    	kriegerisch("kriegerisch"), 
    	gesellschaftlich("gesellschaftlich"),
    	wildnis("wildnis"), 
    	magisch("magisch"), 
    	geweiht("geweiht"), 
    	schamanisch("schamanisch");
		private String xmlValue; // XML-Tag des Elements
		
		private Art(String xmlValue) {
			this.xmlValue = xmlValue;
		}
		
		public String getXmlValue() {
			return xmlValue;
		}
    }
    public enum Aufwand { 
    	erstprof("erstprofession"), 
    	zeitaufw("zeitaufwendig"),
    	normal("-");
    
		private String xmlValue; // XML-Tag des Elements
		
		private Aufwand(String xmlValue) {
			this.xmlValue = xmlValue;
		}
		
		public String getXmlValue() {
			return xmlValue;
		} 
    }
	
	private Aufwand aufwand;
	private Art art;
	private MagierAkademie magierAkademie;
	private IdLinkList verbotenVort;
	private IdLinkList verbotenNacht;
	private IdLinkList verbotenSF;
	private Auswahl sprachen;
	private Auswahl schriften;
	private AuswahlAusruestung ausruestung;
	private AuswahlAusruestung besondererBesitz;
	
	private Gottheit geweihtGottheit; // Falls die Pro Geweiht ist: Der Name des Ritus
//	Modis auf LiturgieKenntnis, Geister rufen, usw.; Der Name des Ritus steht mit im Link
	private Auswahl ritusModis; 
	
   //private Profession varianteVon;
    
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.charKomponenten.CharElement#getCharKomponente()
	 */
	public CharKomponente getCharKomponente() {
		return CharKomponente.profession;
	}
	
	/**
	 * Konstruktur; id beginnt mit "PRO-" für Profession
	 * @param id Systemweit eindeutige id
	 */
	public Profession(String id) {
		setId(id);
	}

	/**
	 * @return Liefert das Attribut art.
	 */
	public Art getArt() {
		return art;
	}
	/**
	 * @param art Setzt das Attribut art.
	 */
	public void setArt(Art art) {
		this.art = art;
	}
	/**
	 * @return Liefert das Attribut aufwand.
	 */
	public Aufwand getAufwand() {
		return aufwand;
	}
	/**
	 * @param aufwand Setzt das Attribut aufwand.
	 */
	public void setAufwand(Aufwand aufwand) {
		this.aufwand = aufwand;
	}
	/**
	 * @return Liefert das Attribut ausruestung.
	 */
	public AuswahlAusruestung getAusruestung() {
		return ausruestung;
	}
	/**
	 * @param ausruestung Setzt das Attribut ausruestung.
	 */
	public void setAusruestung(AuswahlAusruestung ausruestung) {
		this.ausruestung = ausruestung;
	}
	/**
	 * @return Liefert das Attribut besondererBesitz.
	 */
	public AuswahlAusruestung getBesondererBesitz() {
		return besondererBesitz;
	}
	/**
	 * @param besondererBesitz Setzt das Attribut besondererBesitz.
	 */
	public void setBesondererBesitz(AuswahlAusruestung besondererBesitz) {
		this.besondererBesitz = besondererBesitz;
	}
	/**
	 * @return Liefert das Attribut geweihtGottheit.
	 */
	public Gottheit getGeweihtGottheit() {
		return geweihtGottheit;
	}
	/**
	 * @param geweihtGottheit Setzt das Attribut geweihtGottheit.
	 */
	public void setGeweihtGottheit(Gottheit geweihtGottheit) {
		this.geweihtGottheit = geweihtGottheit;
	}
	/**
	 * @return Liefert das Attribut magierAkademie.
	 */
	public MagierAkademie getMagierAkademie() {
		return magierAkademie;
	}
	/**
	 * @param magierAkademie Setzt das Attribut magierAkademie.
	 */
	public void setMagierAkademie(MagierAkademie magierAkademie) {
		this.magierAkademie = magierAkademie;
	}
	/**
	 * @return Liefert das Attribut ritusModis.
	 */
	public Auswahl getRitusModis() {
		return ritusModis;
	}
	/**
	 * @param ritusModis Setzt das Attribut ritusModis.
	 */
	public void setRitusModis(Auswahl ritusModis) {
		this.ritusModis = ritusModis;
	}
	/**
	 * @return Liefert das Attribut schriften.
	 */
	public Auswahl getSchriften() {
		return schriften;
	}
	/**
	 * @param schriften Setzt das Attribut schriften.
	 */
	public void setSchriften(Auswahl schriften) {
		this.schriften = schriften;
	}
	/**
	 * @return Liefert das Attribut sprachen.
	 */
	public Auswahl getSprachen() {
		return sprachen;
	}
	/**
	 * @param sprachen Setzt das Attribut sprachen.
	 */
	public void setSprachen(Auswahl sprachen) {
		this.sprachen = sprachen;
	}
	/**
	 * @return Liefert das Attribut verbotenNacht.
	 */
	public IdLinkList getVerbotenNacht() {
		return verbotenNacht;
	}
	/**
	 * @param verbotenNacht Setzt das Attribut verbotenNacht.
	 */
	public void setVerbotenNacht(IdLinkList verbotenNacht) {
		this.verbotenNacht = verbotenNacht;
	}
	/**
	 * @return Liefert das Attribut verbotenSF.
	 */
	public IdLinkList getVerbotenSF() {
		return verbotenSF;
	}
	/**
	 * @param verbotenSF Setzt das Attribut verbotenSF.
	 */
	public void setVerbotenSF(IdLinkList verbotenSF) {
		this.verbotenSF = verbotenSF;
	}
	/**
	 * @return Liefert das Attribut verbotenVort.
	 */
	public IdLinkList getVerbotenVort() {
		return verbotenVort;
	}
	/**
	 * @param verbotenVort Setzt das Attribut verbotenVort.
	 */
	public void setVerbotenVort(IdLinkList verbotenVort) {
		this.verbotenVort = verbotenVort;
	}
	/**
	 * Liefert zu einem XML-Tag die entsprechende Enum der Prof-Art zurück.
	 * @param xmlValue Der XML-Tag art aus dem Element Profession
	 * @return Die Enum Art die zu den xmlTag gehört
	 */
	private Art getArtByXmlTag(String xmlValue) {
		Art[] artArray = Art.values();
		
		// Suchen des richtigen Elements
		for (int i = 0; i < artArray.length; i++) {
			if (xmlValue.equals(artArray[i].getXmlValue())) {
				return artArray[i]; // Gefunden
			}
		}
		
		ProgAdmin.logger.severe("XmlValue konnte nicht gefunden werden!");
		
		return null;
		
	}
	
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#loadXmlElement(nu.xom.Element)
     */
    public void loadXmlElement(Element xmlElement) {
    	super.loadXmlElement(xmlElement);
    	
    	xmlElement.setLocalName("profession");
    	
    	// Auslesen des Attribus "aufwand"
    	if ( xmlElement.getAttribute("aufwand") != null ) {
    		
    		// Sicherstellen das der Wertebereich gültig ist:
    		assert xmlElement.getAttributeValue("aufwand")
    									.equals(Aufwand.erstprof.getXmlValue())
					|| xmlElement.getAttributeValue("aufwand")
										.equals(Aufwand.zeitaufw.getXmlValue());
    		
    		if ( xmlElement.getAttributeValue("aufwand")
    				.equals(Aufwand.erstprof.getXmlValue()) ) {
    			aufwand = Aufwand.erstprof;
    		} else {
    			aufwand = Aufwand.zeitaufw;
    		}
    	}
    	
    	// Auslesen der Art dieser Profession
    	this.art = getArtByXmlTag(xmlElement.getFirstChildElement("art").getValue());
    	
    	// Auslesen der MagierAkademie, sofern vorhanden
    	if ( xmlElement.getFirstChildElement("magierAkademie") != null ) {
    		magierAkademie = new MagierAkademie(this);
    		magierAkademie.loadXmlElement(xmlElement.getFirstChildElement("magierAkademie"));
    	}
    	
    	// Auslesen der Geweihten-Werte, sofern vorhanden   	
    	if ( xmlElement.getFirstChildElement("geweiht") != null ) {
    		geweihtGottheit = (Gottheit) ProgAdmin.charKompAdmin.getCharElement(
    			xmlElement.getFirstChildElement("geweiht").getAttributeValue("gottheitId"),
    			CharKomponente.gottheit
    		);
    		
    		ritusModis = new Auswahl(this);
    		ritusModis.loadXmlElement(xmlElement.getFirstChildElement("geweiht")
    											.getFirstChildElement("modis"));
    	}
    	
    	// Auslesen der verbotenen Vorteile
    	if ( xmlElement.getFirstChildElement("verboteVT") != null ) {
    		verbotenVort = new IdLinkList(this);
    		verbotenVort.loadXmlElement(xmlElement.getFirstChildElement("verboteVT"));
    	}
    	
    	// Auslesen der verbotenen Nachteile
    	if ( xmlElement.getFirstChildElement("verboteNT") != null ) {
    		verbotenNacht = new IdLinkList(this);
    		verbotenNacht.loadXmlElement(xmlElement.getFirstChildElement("verboteNT"));
    	}
    	
    	// Auslesen der verbotenen Sonderfertigkeiten
    	if ( xmlElement.getFirstChildElement("verboteSF") != null ) {
    		verbotenSF = new IdLinkList(this);
    		verbotenSF.loadXmlElement(xmlElement.getFirstChildElement("verboteSF"));
    	}
    	
    	// Auslesen der Sprachen Auswahl
    	if ( xmlElement.getFirstChildElement("sprachen") != null ) {
    		sprachen = new Auswahl(this);
    		sprachen.loadXmlElement(xmlElement.getFirstChildElement("sprachen"));
    	}
    	
    	// Auslesen der Schriften Auswahl
    	if ( xmlElement.getFirstChildElement("schriften") != null ) {
    		schriften = new Auswahl(this);
    		schriften.loadXmlElement(xmlElement.getFirstChildElement("schriften"));
    	}
    	
    	// Auslesen der Ausrüstung Auswahl
    	if ( xmlElement.getFirstChildElement("ausruestung") != null ) {
    		ausruestung = new AuswahlAusruestung(this);
    		ausruestung.loadXmlElement(xmlElement.getFirstChildElement("ausruestung"));
    	}
    	
    	// Auslesen der besondere Besitz Auswahl
    	if ( xmlElement.getFirstChildElement("besondererBesitz") != null ) {
    		besondererBesitz = new AuswahlAusruestung(this);
    		besondererBesitz.loadXmlElement(xmlElement.getFirstChildElement("besondererBesitz"));
    	}
    	
    	// Lesen der Varaiante (eigentlich von Herkunft)
		if ( xmlElement.getFirstChildElement("varianteVon") !=  null ) {
			varianteVon = (Profession) ProgAdmin.charKompAdmin.getCharElement(
	    			xmlElement.getFirstChildElement("varianteVon").getValue(),
	    			CharKomponente.profession
	    		);
		}
		
    }
    
    /* (non-Javadoc) Methode überschrieben
     * @see org.d3s.alricg.charKomponenten.CharElement#writeXmlElement()
     */
    public Element writeXmlElement(){
    	Element xmlElement = super.writeXmlElement();
    	Element tmpElement;
    	int idx; 
    	
    	xmlElement.setLocalName("profession");
    	
    	// Schreiben des Attributs "Aufwand", wenn nötig
    	if ( aufwand != Aufwand.normal ) {
    		xmlElement.addAttribute(new Attribute("aufwand", aufwand.getXmlValue()));
    	}
    	
    	// Schreiben der Art der Profession
    	tmpElement = new Element("art");
    	tmpElement.appendChild(art.getXmlValue());
    	xmlElement.appendChild(tmpElement);
    	
    	// Schreiben der MagierAkademie
    	if ( magierAkademie != null ) {
    		xmlElement.appendChild(magierAkademie.writeXmlElement("magierAkademie"));
    	}
    	
    	// Schreiben der Geweihten angaben
    	if ( geweihtGottheit != null )  {
    		tmpElement = new Element("geweiht");
    		tmpElement.addAttribute( new Attribute("gottheitId", geweihtGottheit.getId()) );
    		tmpElement.appendChild(ritusModis.writeXmlElement("modis"));
    		xmlElement.appendChild(tmpElement);
    	}
    	
    	// Schreiben der verbotenen Vorteile
    	if ( verbotenVort != null ) {
    		xmlElement.appendChild(verbotenVort.writeXmlElement("verboteVT"));
    	}
    	
    	// Schreiben der verbotenen Nachteile
    	if ( verbotenNacht != null ) {
    		xmlElement.appendChild(verbotenNacht.writeXmlElement("verboteNT"));
    	}
    	
    	// Schreiben der verbotenen Sonderfertigkeiten
    	if ( verbotenSF != null ) {
    		xmlElement.appendChild(verbotenSF.writeXmlElement("verboteSF"));
    	}
    	
    	// Schreiben der Sprachen
    	if ( sprachen != null ) {
    		xmlElement.appendChild(sprachen.writeXmlElement("sprachen"));
    	}
    	
    	// Schreiben der Schriften
    	if ( schriften != null ) {
    		xmlElement.appendChild(schriften.writeXmlElement("schriften"));
    	}
    	
    	// Schreiben der Ausrüstung
    	if ( ausruestung != null ) {
    		xmlElement.appendChild(ausruestung.writeXmlElement("ausruestung"));
    	}
    	
    	// Schreiben des besonderen Besitzens
    	if ( besondererBesitz != null ) {
    		xmlElement.appendChild(besondererBesitz.writeXmlElement("besondererBesitz"));
    	}
    	

    	// "varianteVon" schreiben (eigentlich von Herkunft)
    	if ( this.varianteVon != null ) {
	    	// hierfür muß die richtige Position bestimmt werden: 
	    	idx = xmlElement.indexOf( xmlElement.getFirstChildElement("gp") );
	    	tmpElement = new Element("varianteVon");
	    	tmpElement.appendChild(this.varianteVon.getId());
	    	
	    	// einfügen nach dem "gp" Element!
	    	xmlElement.insertChild(tmpElement, idx+1);
    	}
    	
    	return xmlElement;
    }
    
    /**
     * 
     * <u>Beschreibung:</u><br> 
     * Repräsentiert die üblichen angaben die für eine Magier-Akedemie benötigt werden,
     * wenn diese als Profession gewählt werden kann.
     * @author V. Strelow
     */
	public class MagierAkademie {        	
		private Gilde gilde;
    	private MagieMerkmal merkmale[];
    	private boolean zweitStudiumMoeglich = true;
        private boolean drittStudiumMoeglich = false;
        private String anmerkung;
        private Profession herkunft;
        
        /**
         * Konstruktor
         * @param herkunft Die Profession, von der diese MagierAkademie stammt.
         */
        MagierAkademie(Profession herkunft) {
        	this.herkunft = herkunft;
        }
        
        /**
         * Dient zum initialisieren des Objekts. Ein XML-Elements wird gegeben, daraus
         * werden alle relevanten Informationen ausgelesen.
         * @param xmlElement Das Xml-Element mit allen nötigen angaben
         */
        public void loadXmlElement(Element xmlElement) {
        	Elements tmpElements;
        	
        	// Auslesen der Gildenzugehörigkeit
        	gilde = Werte.getGildeByXmlValue(
        				xmlElement.getFirstChildElement("gilde").getValue()
        			);
        	
        	// Auslesen der Merkmale der Akademie
        	tmpElements = xmlElement.getChildElements("merkmale");
        	merkmale = new MagieMerkmal[tmpElements.size()];
        	
        	for (int i = 0; i < merkmale.length; i++) {
        		merkmale[i] = Werte.getMagieMerkmalByXmlValue(
        							tmpElements.get(i).getValue()
        						);
        	}
        	
        	// Auslesen ob ein Zweitstudium möglich ist
        	if ( xmlElement.getFirstChildElement("zweitStudiumMoeglich") != null ) {
        		
        		// Überprüfung ob es dem Wertebereich entspricht
        		assert xmlElement.getFirstChildElement("zweitStudiumMoeglich").getValue().equals("false")
        			|| xmlElement.getFirstChildElement("zweitStudiumMoeglich").getValue().equals("true");
        		
        		if (xmlElement.getFirstChildElement("zweitStudiumMoeglich").getValue().equals("false")) {
        			zweitStudiumMoeglich = false;
        		}
        	}
        	
        	// Auslesen ob ein Drittstudium möglich ist
        	if ( xmlElement.getFirstChildElement("drittStudiumMoeglich") != null ) {
        		
        		// Überprüfung ob es dem Wertebereich entspricht
        		assert xmlElement.getFirstChildElement("drittStudiumMoeglich").getValue().equals("false")
        			|| xmlElement.getFirstChildElement("drittStudiumMoeglich").getValue().equals("true");
        		
        		if (xmlElement.getFirstChildElement("drittStudiumMoeglich").getValue().equals("true")) {
        			drittStudiumMoeglich = true;
        		}
        	}
        	
        	// Anmerkungen zu der Akademie auslesen
        	if ( xmlElement.getFirstChildElement("anmerkung") != null ) {
        		anmerkung = xmlElement.getFirstChildElement("anmerkung").getValue();
        	}
        	
        }
        
        /**
         * Dient zur Speicherung (also für den Editor) des Objekts. Alle Angaben werden 
         * in ein XML Objekt "gemapt".
         * @param tagName Der name des Tags
         * @return Ein Xml-Element mit allen nötigen Angaben.
         */
        public Element writeXmlElement(String xmlTag){
        	Element xmlElement = new Element(xmlTag);
        	Element tmpElement;
        	
        	// Schreiben der Gilde
        	tmpElement = new Element("gilde");
        	tmpElement.appendChild(gilde.getXmlValue());
        	xmlElement.appendChild(tmpElement);
        	
        	// Schreiben der magischen Merkmale
        	for (int i = 0; i < merkmale.length; i++) {
            	tmpElement = new Element("merkmale");
            	tmpElement.appendChild(merkmale[i].getXmlValue());
            	xmlElement.appendChild(tmpElement);
        	}
        	
        	// Schreiben ob Zweitstudium möglich
        	if ( !zweitStudiumMoeglich )  {
            	tmpElement = new Element("zweitStudiumMoeglich");
            	tmpElement.appendChild("false");
            	xmlElement.appendChild(tmpElement);
        	}
        	
        	// Schreiben ob Drittstudium möglich
        	if ( drittStudiumMoeglich )  {
            	tmpElement = new Element("drittStudiumMoeglich");
            	tmpElement.appendChild("true");
            	xmlElement.appendChild(tmpElement);
        	}
        	
        	// Schreiben der Anmerkung zur Akademie
        	if ( anmerkung != null && anmerkung.trim().length() > 0 ) {
            	tmpElement = new Element("anmerkung");
            	tmpElement.appendChild(anmerkung.trim());
            	xmlElement.appendChild(tmpElement);
        	}
        	
        	return xmlElement;
        }
        
	}
}
