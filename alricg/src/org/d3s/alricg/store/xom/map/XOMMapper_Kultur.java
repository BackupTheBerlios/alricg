/*
 * Created on 23.06.2005 / 15:16:17
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */

package org.d3s.alricg.store.xom.map;

import java.util.ArrayList;

import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Kultur;
import org.d3s.alricg.charKomponenten.KulturVariante;
import org.d3s.alricg.charKomponenten.RegionVolk;
import org.d3s.alricg.charKomponenten.links.Auswahl;
import org.d3s.alricg.charKomponenten.links.AuswahlAusruestung;
import org.d3s.alricg.charKomponenten.links.IdLinkList;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.store.FactoryFinder;

/**
 * <code>XOMMapper</code> für eine <code>Kultur</code>.
 * 
 * @see org.d3s.alricg.store.xom.map.XOMMapper
 * @see org.d3s.alricg.charKomponenten.Kultur
 * @author <a href="mailto:msturzen@mac.com>St. Martin</a>
 */
class XOMMapper_Kultur extends XOMMapper_Herkunft implements XOMMapper {

    // @see org.d3s.alricg.store.xom.map.XOMMapper#map(nu.xom.Element, org.d3s.alricg.charKomponenten.CharElement)
    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final Kultur kultur = (Kultur) charElement;

        // Auslesen der Region
        Element current = xmlElement.getFirstChildElement("region");
        if (current != null) {
            kultur.setRegionVolk((RegionVolk) FactoryFinder.find().getData().getCharElement(current.getValue(),
                    CharKomponente.region));
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
        ArrayList<KulturVariante> arList = new ArrayList<KulturVariante>();
        current = xmlElement.getFirstChildElement("varianten");
        if (current != null) {
            Elements varianten = current.getChildElements("variante");
            for (int i = 0; i < varianten.size(); i++) {
                final KulturVariante variante = (KulturVariante) FactoryFinder.find().getData().getCharElement(
                        varianten.get(i).getAttributeValue("id"));
                XOMMappingHelper.mapVarianten(varianten.get(i), variante, kultur, this);
                arList.add(variante);
            }
            kultur.setVarianten(arList.toArray(new KulturVariante[arList.size()]));
        }

    }

    // @see org.d3s.alricg.store.xom.map.XOMMapper#map(org.d3s.alricg.charKomponenten.CharElement, nu.xom.Element)
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

        // Schreiben der Varianten
        KulturVariante[] varianten = kultur.getVarianten();
        if (varianten != null) {
            final Element e = new Element("varianten");

            for (int i = 0; i < varianten.length; i++) {
                final Element variante = new Element("variante");
                XOMMappingHelper.mapVarianten(varianten[i], variante, this);
                e.appendChild(variante);
            }
            xmlElement.appendChild(e);
        }

    }

}
