package org.d3s.alricg.store.xom.map;

import java.util.ArrayList;

import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.HerkunftVariante;
import org.d3s.alricg.charKomponenten.Kultur;
import org.d3s.alricg.charKomponenten.KulturVariante;
import org.d3s.alricg.charKomponenten.RegionVolk;
import org.d3s.alricg.charKomponenten.links.Auswahl;
import org.d3s.alricg.charKomponenten.links.AuswahlAusruestung;
import org.d3s.alricg.charKomponenten.links.IdLinkList;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;

class XOMMapper_Kultur extends XOMMapper_Herkunft implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final Kultur kultur = (Kultur) charElement;

        // Auslesen der Region
        Element current = xmlElement.getFirstChildElement("region");
        if (current != null) {
            kultur.setRegionVolk((RegionVolk) ProgAdmin.data.getCharElement(current.getValue(), CharKomponente.region));
        }

        // Auslesen der üblichen Professionen
        current = xmlElement.getFirstChildElement("professionUeblich");
        if (current != null) {
            final IdLinkList ids = new IdLinkList(kultur);
            XOMMappingHelper.mapIdLinkList(current, ids);
            kultur.setProfessionUeblich(ids);
        }

        // Auslesen der möglichen Professionen
        current = xmlElement.getFirstChildElement("professionMoeglich");
        if (current != null) {
            final IdLinkList ids = new IdLinkList(kultur);
            XOMMappingHelper.mapIdLinkList(current, ids);
            kultur.setProfessionMoeglich(ids);
        }

        // Auslesen der Muttersprache
        current = xmlElement.getFirstChildElement("muttersprache");
        Auswahl auswahl = new Auswahl(kultur);
        XOMMappingHelper.mapAuswahl(current, auswahl);
        kultur.setMuttersprache(auswahl);

        // Auslesen der Zweitsprache
        current = xmlElement.getFirstChildElement("zweitsprache");
        if (current != null) {
            auswahl = new Auswahl(kultur);
            XOMMappingHelper.mapAuswahl(current, auswahl);
            kultur.setZweitsprache(auswahl);
        }
        
        // Auslesen der Lehrsprache
        current = xmlElement.getFirstChildElement("lehrsprache");
        if (current != null) {
            auswahl = new Auswahl(kultur);
            XOMMappingHelper.mapAuswahl(current, auswahl);
            kultur.setLehrsprache(auswahl);
        }

        // Auslesen sonstiger Sprachen
        current = xmlElement.getFirstChildElement("sprachen");
        if (current != null) {
            auswahl = new Auswahl(kultur);
            XOMMappingHelper.mapAuswahl(current, auswahl);
            kultur.setSprachen(auswahl);
        }
        
        // Auslesen der Schriften
        current = xmlElement.getFirstChildElement("schriften");
        if (current != null) {
            auswahl = new Auswahl(kultur);
            XOMMappingHelper.mapAuswahl(current, auswahl);
            kultur.setSchriften(auswahl);
        }

        // Auslesen der Ausrüstung
        current = xmlElement.getFirstChildElement("ausruestung");
        if (current != null) {
            AuswahlAusruestung auswahlA = new AuswahlAusruestung(kultur);
            XOMMappingHelper.mapAuswahlAusruestung(current, auswahlA);
            kultur.setAusruestung(auswahlA);
        }
        
        // Auslesen der Varianten
        ArrayList<HerkunftVariante> arList = new ArrayList<HerkunftVariante>();
        current = xmlElement.getFirstChildElement("varianten");
        if (current != null) {
        	 Elements varianten = current.getChildElements("variante");
        	 for (int i = 0; i < varianten.size(); i++) {
        	 	arList.add( XOMMappingHelper.mapVarianten(
        	 			varianten.get(i), 
        	 			ProgAdmin.data.getCharElement(varianten.get(i).getAttributeValue("id")),
        	 			kultur,
        	 			this) );
        	 }
        	 kultur.setVarianten(arList.toArray(new KulturVariante[arList.size()]));
        }
        
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        final Kultur kultur = (Kultur) charElement;
        xmlElement.setLocalName("kultur");

        // Schreiben der Region
        final RegionVolk regionVolk = kultur.getRegionVolk();
        if (regionVolk != null) {
            final Element e = new Element("region");
            e.appendChild(regionVolk.getId());
            xmlElement.appendChild(e);
        }

        // Schreiben der üblichen Professionen
        IdLinkList ids = kultur.getProfessionUeblich();
        if (ids != null) {
            final Element e = new Element("professionUeblich");
            XOMMappingHelper.mapIdLinkList(ids, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der möglichen Professionen
        ids = kultur.getProfessionMoeglich();
        if (ids != null) {
            final Element e = new Element("professionMoeglich");
            XOMMappingHelper.mapIdLinkList(ids, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der Muttersprache
        Auswahl auswahl = kultur.getMuttersprache();
        if (auswahl != null) {
            final Element e = new Element("muttersprache");
            XOMMappingHelper.mapAuswahl(auswahl, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der Zweitsprache
        auswahl = kultur.getZweitsprache();
        if (auswahl != null) {
            final Element e = new Element("zweitsprache");
            XOMMappingHelper.mapAuswahl(auswahl, e);
            xmlElement.appendChild(e);
        }
        
        // Schreiben der Lehrsprache
        auswahl = kultur.getLehrsprache();
        if (auswahl != null) {
            final Element e = new Element("lehrsprache");
            XOMMappingHelper.mapAuswahl(auswahl, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der weiteren Sprachen
        auswahl = kultur.getSprachen();
        if (auswahl != null) {
            final Element e = new Element("sprachen");
            XOMMappingHelper.mapAuswahl(auswahl, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der Schriften
        auswahl = kultur.getSchriften();
        if (auswahl != null) {
            final Element e = new Element("schriften");
            XOMMappingHelper.mapAuswahl(auswahl, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der Ausruestung
        AuswahlAusruestung auswahlA = kultur.getAusruestung();
        if (auswahl != null) {
            final Element e = new Element("ausruestung");
            XOMMappingHelper.mapAuswahlAusruestung(auswahlA, e);
            xmlElement.appendChild(e);
        }
        
        //Schreiben der Varianten
        CharElement[] elements = kultur.getVarianten();
        if (elements != null) {
        	final Element e = new Element("varianten");
        	 
        	for (int i = 0; i < elements.length; i++) {
        	 	e.appendChild(XOMMappingHelper.mapVarianten(
        	 			elements[i], 
        	 			this));
        	}
        	xmlElement.appendChild(e);
        }

    }

}
