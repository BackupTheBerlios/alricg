package org.d3s.alricg.store.xom.map;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Gottheit;
import org.d3s.alricg.charKomponenten.Profession;
import org.d3s.alricg.charKomponenten.Werte;
import org.d3s.alricg.charKomponenten.Profession.Art;
import org.d3s.alricg.charKomponenten.Profession.Aufwand;
import org.d3s.alricg.charKomponenten.Profession.MagierAkademie;
import org.d3s.alricg.charKomponenten.Werte.MagieMerkmal;
import org.d3s.alricg.charKomponenten.links.Auswahl;
import org.d3s.alricg.charKomponenten.links.AuswahlAusruestung;
import org.d3s.alricg.charKomponenten.links.IdLinkList;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;

class XOMMapper_Profession extends XOMMapper_Herkunft implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final Profession profession = (Profession) charElement;
        xmlElement.setLocalName("profession");

        // Auslesen des Attribus "aufwand"
        Attribute a = xmlElement.getAttribute("aufwand");
        if (a != null) {

            // Sicherstellen das der Wertebereich gültig ist:
            String attVal = a.getValue();
            assert attVal.equalsIgnoreCase(Aufwand.erstprof.getValue())
                    || attVal.equalsIgnoreCase(Aufwand.zeitaufw.getValue());

            if (attVal.equalsIgnoreCase(Aufwand.erstprof.getValue())) {
                profession.setAufwand(Aufwand.erstprof);
            } else {
                profession.setAufwand(Aufwand.zeitaufw);
            }
        }

        // Auslesen der Art dieser Profession
        profession.setArt(getArtByValue(xmlElement.getFirstChildElement("art").getValue()));

        // Auslesen der MagierAkademie, sofern vorhanden
        Element current = xmlElement.getFirstChildElement("magierAkademie");
        if (current != null) {
            final Element e = xmlElement.getFirstChildElement("magierAkademie");
            final MagierAkademie magierAkademie = profession.new MagierAkademie(profession);
            mapMagierAkademie(e, magierAkademie);
            profession.setMagierAkademie(magierAkademie);
        }

        // Auslesen der Geweihten-Werte, sofern vorhanden
        current = xmlElement.getFirstChildElement("geweiht");
        if (current != null) {
            final String val = current.getAttributeValue("gottheitId");
            profession.setGeweihtGottheit((Gottheit) ProgAdmin.data.getCharElement(val, CharKomponente.gottheit));

            final Auswahl ritusModis = new Auswahl(profession);
            XOMMappingHelper.mapAuswahl(current.getFirstChildElement("modis"), ritusModis);
            profession.setRitusModis(ritusModis);
        }

        // Auslesen der verbotenen Vorteile
        current = xmlElement.getFirstChildElement("verboteVT");
        if (current != null) {
            final IdLinkList ids = new IdLinkList(profession);
            XOMMappingHelper.mapIdLinkList(current, ids);
            profession.setVerbotenVort(ids);
        }

        // Auslesen der verbotenen Nachteile
        current = xmlElement.getFirstChildElement("verboteNT");
        if (current != null) {
            final IdLinkList ids = new IdLinkList(profession);
            XOMMappingHelper.mapIdLinkList(current, ids);
            profession.setVerbotenNacht(ids);
        }

        // Auslesen der verbotenen Sonderfertigkeiten
        current = xmlElement.getFirstChildElement("verboteSF");
        if (current != null) {
            final IdLinkList ids = new IdLinkList(profession);
            XOMMappingHelper.mapIdLinkList(current, ids);
            profession.setVerbotenSF(ids);
        }

        // Auslesen der Sprachen Auswahl
        current = xmlElement.getFirstChildElement("sprachen");
        if (current != null) {
            final Auswahl auswahl = new Auswahl(profession);
            XOMMappingHelper.mapAuswahl(current, auswahl);
            profession.setSprachen(auswahl);
        }

        // Auslesen der Schriften Auswahl
        current = xmlElement.getFirstChildElement("schriften");
        if (current != null) {
            final Auswahl auswahl = new Auswahl(profession);
            XOMMappingHelper.mapAuswahl(current, auswahl);
            profession.setSchriften(auswahl);
        }

        // Auslesen der Ausrüstung Auswahl
        current = xmlElement.getFirstChildElement("ausruestung");
        if (current != null) {
            final AuswahlAusruestung auswahlA = new AuswahlAusruestung(profession);
            XOMMappingHelper.mapAuswahlAusruestung(current, auswahlA);
            profession.setAusruestung(auswahlA);
        }

        // Auslesen der besondere Besitz Auswahl
        current = xmlElement.getFirstChildElement("besondererBesitz");
        if (current != null) {
            final AuswahlAusruestung auswahlA = new AuswahlAusruestung(profession);
            XOMMappingHelper.mapAuswahlAusruestung(current, auswahlA);
            profession.setBesondererBesitz(auswahlA);
        }

    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        final Profession profession = (Profession) charElement;
        xmlElement.setLocalName("profession");

        // Schreiben des Attributs "Aufwand", wenn nötig
        xmlElement.addAttribute(new Attribute("aufwand", profession.getAufwand().getValue()));

        // Schreiben der Art der Profession
        Element e = new Element("art");
        e.appendChild(profession.getArt().getValue());
        xmlElement.appendChild(e);

        // Schreiben der MagierAkademie
        final MagierAkademie magierAkademie = profession.getMagierAkademie();
        if (magierAkademie != null) {
            e = new Element("magierAkademie");
            mapMagierAkademie(magierAkademie, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der Geweihten angaben
        final Gottheit geweihtGottheit = profession.getGeweihtGottheit();
        if (geweihtGottheit != null) {
            e = new Element("geweiht");
            e.addAttribute(new Attribute("gottheitId", geweihtGottheit.getId()));
            final Element ee = new Element("modis");
            XOMMappingHelper.mapAuswahl(profession.getRitusModis(), ee);
            e.appendChild(ee);
            xmlElement.appendChild(e);
        }

        // Schreiben der verbotenen Vorteile
        IdLinkList ids = profession.getVerbotenVort();
        if (ids != null) {
            e = new Element("verboteVT");
            XOMMappingHelper.mapIdLinkList(ids, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der verbotenen Nachteile
        ids = profession.getVerbotenNacht();
        if (ids != null) {
            e = new Element("verboteNT");
            XOMMappingHelper.mapIdLinkList(ids, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der verbotenen Sonderfertigkeiten
        ids = profession.getVerbotenSF();
        if (ids != null) {
            e = new Element("verboteSF");
            XOMMappingHelper.mapIdLinkList(ids, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der Sprachen
        Auswahl auswahl = profession.getSprachen();
        if (auswahl != null) {
            e = new Element("sprachen");
            XOMMappingHelper.mapAuswahl(auswahl, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der Schriften
        auswahl = profession.getSchriften();
        if (auswahl != null) {
            e = new Element("schriften");
            XOMMappingHelper.mapAuswahl(auswahl, e);
            xmlElement.appendChild(e);
        }

        // Schreiben der Ausrüstung
        AuswahlAusruestung auswahlA = profession.getAusruestung();
        if (auswahl != null) {
            e = new Element("ausruestung");
            XOMMappingHelper.mapAuswahlAusruestung(auswahlA, e);
            xmlElement.appendChild(e);
        }

        // Schreiben des besonderen Besitzens
        auswahlA = profession.getBesondererBesitz();
        if (auswahl != null) {
            e = new Element("besondererBesitz");
            XOMMappingHelper.mapAuswahlAusruestung(auswahlA, e);
            xmlElement.appendChild(e);
        }

    }

    /**
     * Liefert zu einem XML-Tag die entsprechende Enum der Prof-Art zurück.
     * 
     * @param xmlValue Der XML-Tag art aus dem Element Profession
     * @return Die Enum Art die zu den xmlTag gehört
     */
    private Art getArtByValue(String value) {
        Art[] artArray = Art.values();

        // Suchen des richtigen Elements
        for (int i = 0; i < artArray.length; i++) {
            if (value.equals(artArray[i].getValue())) {
                return artArray[i]; // Gefunden
            }
        }
        ProgAdmin.logger.severe("XmlValue konnte nicht gefunden werden!");
        return null;
    }

    private void mapMagierAkademie(Element xmlElement, MagierAkademie makademie) {

        // Auslesen der Gildenzugehörigkeit
        makademie.setGilde(Werte.getGildeByValue(xmlElement.getFirstChildElement("gilde").getValue()));

        // Auslesen der Merkmale der Akademie
        Elements children = xmlElement.getChildElements("merkmale");
        final MagieMerkmal[] merkmale = new MagieMerkmal[children.size()];
        for (int i = 0; i < merkmale.length; i++) {
            merkmale[i] = Werte.getMagieMerkmalByValue(children.get(i).getValue());
        }

        // Auslesen ob ein Zweitstudium möglich ist
        Element current = xmlElement.getFirstChildElement("zweitStudiumMoeglich");
        if (current != null) {
            String v = current.getValue().toLowerCase();
            assert v.equals("false") || v.equals("true");
            makademie.setZweitStudiumMoeglich(v.equals("true"));
        }

        // Auslesen ob ein Drittstudium möglich ist
        current = xmlElement.getFirstChildElement("drittStudiumMoeglich");
        if (current != null) {
            String v = current.getValue().toLowerCase();
            assert v.equals("false") || v.equals("true");
            makademie.setDrittStudiumMoeglich(v.equals("true"));
        }

        // Anmerkungen zu der Akademie auslesen
        current = xmlElement.getFirstChildElement("anmerkung");
        if (current != null) {
            makademie.setAnmerkung(current.getValue());
        }
    }

    private void mapMagierAkademie(MagierAkademie makadmie, Element xmlElement) {

        // Schreiben der Gilde
        Element e = new Element("gilde");
        e.appendChild(makadmie.getGilde().getValue());
        xmlElement.appendChild(e);

        // Schreiben der magischen Merkmale
        final MagieMerkmal[] merkmale = makadmie.getMerkmale();
        for (int i = 0; i < merkmale.length; i++) {
            e = new Element("merkmale");
            e.appendChild(merkmale[i].getValue());
            xmlElement.appendChild(e);
        }

        // Schreiben ob Zweitstudium möglich
        e = new Element("zweitStudiumMoeglich");
        e.appendChild(Boolean.toString(makadmie.isZweitStudiumMoeglich()));
        xmlElement.appendChild(e);

        // Schreiben ob Drittstudium möglich
        e = new Element("drittStudiumMoeglich");
        e.appendChild(Boolean.toString(makadmie.isDrittStudiumMoeglich()));
        xmlElement.appendChild(e);

        // Schreiben der Anmerkung zur Akademie
        final String anmerkung = makadmie.getAnmerkung();
        if (anmerkung != null && anmerkung.trim().length() > 0) {
            e = new Element("anmerkung");
            e.appendChild(anmerkung.trim());
            xmlElement.appendChild(e);
        }

    }

}
