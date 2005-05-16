package org.d3s.alricg.store.xom.map;

import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Rasse;
import org.d3s.alricg.charKomponenten.charZusatz.WuerfelSammlung;
import org.d3s.alricg.charKomponenten.links.IdLinkList;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;

public class XOMMapper_Rasse extends XOMMapper_Herkunft implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);

        // my mapping
        final Rasse rasse = (Rasse) charElement;

        try {
            // "varianteVon" auslesen
            Element current = xmlElement.getFirstChildElement("varianteVon");
            if (current != null) {
                rasse.setVarianteVon((Rasse) ProgAdmin.data.getCharElement(current.getValue(), CharKomponente.rasse));
            }

            // Übliche Kulturen einlesen
            current = xmlElement.getFirstChildElement("kulturUeblich");
            if (current != null) {
                final IdLinkList kulturUeblich = new IdLinkList(rasse);
                XOMMappingHelper.mapIdLinkList(current, kulturUeblich);
                rasse.setKulturUeblich(kulturUeblich);
            }

            // Mögliche Kulturen einlesen
            current = xmlElement.getFirstChildElement("kulturMoeglich");
            if (current != null) {
                final IdLinkList kulturMoeglich = new IdLinkList(rasse);
                XOMMappingHelper.mapIdLinkList(current, kulturMoeglich);
                rasse.setKulturMoeglich(kulturMoeglich);
            }

            // Einlesen der Geschwindigkeit
            current = xmlElement.getFirstChildElement("geschwindigkeit");
            if (current != null) {
                rasse.setGeschwindigk(Integer.parseInt(current.getValue()));
            }

            // Einlesen des Start-Alters
            current = xmlElement.getFirstChildElement("alter");
            Elements children = current.getChildElements("wuerfel");
            int[] anzahlWuerfel = new int[children.size()];
            int[] augenWuerfel = new int[children.size()];
            for (int i = 0; i < children.size(); i++) {
                anzahlWuerfel[i] = Integer.parseInt(children.get(i).getAttributeValue("anzWuerfel"));
                augenWuerfel[i] = Integer.parseInt(children.get(i).getAttributeValue("augenWuerfel"));
            }

            final int wert = Integer.parseInt(current.getAttributeValue("wert"));
            rasse.setAlterWuerfel(new WuerfelSammlung(wert, anzahlWuerfel, augenWuerfel));

            // Einlesen der Größe
            current = xmlElement.getFirstChildElement("groesse");
            children = current.getChildElements("wuerfel");
            anzahlWuerfel = new int[children.size()];
            augenWuerfel = new int[children.size()];
            for (int i = 0; i < children.size(); i++) {
                anzahlWuerfel[i] = Integer.parseInt(children.get(i).getAttributeValue("anzWuerfel"));
                augenWuerfel[i] = Integer.parseInt(children.get(i).getAttributeValue("augenWuerfel"));
            }

            final int groesse = Integer.parseInt(xmlElement.getFirstChildElement("groesse").getAttributeValue("wert"));
            rasse.setGroesseWuerfel(new WuerfelSammlung(groesse, anzahlWuerfel, augenWuerfel));

            // Einlesen des Modis für das Gewicht
            rasse.setGewichtModi(Integer.parseInt(xmlElement.getFirstChildElement("gewichtModi").getAttributeValue(
                    "wert")));

            // Einlesen der Haarfarbe
            current = xmlElement.getFirstChildElement("haarfarbe");
            farbenFromXML(current.getChildElements("farbe"), rasse.getHaarfarbe());

            // Einlesen der Augenfarbe
            current = xmlElement.getFirstChildElement("augenfarbe");
            farbenFromXML(current.getChildElements("farbe"), rasse.getAugenfarbe());

        } catch (NumberFormatException exc) {
            ProgAdmin.logger.log(Level.SEVERE, "String -> int failed", exc);
        }
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        final Rasse rasse = (Rasse) charElement;
        xmlElement.setLocalName("rasse");

        int idx;

        // "varianteVon" schreiben
        if (rasse.getVarianteVon() != null) {
            // hierfür muß die richtige Position bestimmt werden:
            idx = xmlElement.indexOf(xmlElement.getFirstChildElement("gp"));
            final Element e = new Element("varianteVon");
            e.appendChild(rasse.getVarianteVon().getId());

            // einfügen nach dem "gp" Element!
            xmlElement.insertChild(e, idx + 1);
        }

        // Übliche Kulturen schreiben
        IdLinkList ids = rasse.getKulturUeblich();
        if (ids != null) {
            final Element e = new Element("kulturUeblich");
            XOMMappingHelper.mapIdLinkList(ids, e);
            xmlElement.appendChild(e);
        }

        // Mögliche Kulturen einlesen
        ids = rasse.getKulturMoeglich();
        if (ids != null) {
            final Element e = new Element("kulturMoeglich");
            XOMMappingHelper.mapIdLinkList(ids, e);
            xmlElement.appendChild(e);
        }

        // Geschwindigkeit schreiben
        Element e = new Element("geschwindigkeit");
        e.appendChild(Integer.toString(rasse.getGeschwindigk()));
        xmlElement.appendChild(e);

        // Alter schreiben
        e = new Element("alter");
        WuerfelSammlung ws = rasse.getAlterWuerfel();
        e.addAttribute(new Attribute("wert", Integer.toString(ws.getFestWert())));
        for (int i = 0; i < ws.getAnzahlWuerfel().length; i++) {
            final Element ee = new Element("wuerfel");
            ee.addAttribute(new Attribute("anzWuerfel", Integer.toString(ws.getAnzahlWuerfel()[i])));
            ee.addAttribute(new Attribute("augenWuerfel", Integer.toString(ws.getAugenWuerfel()[i])));
            e.appendChild(ee);
        }
        xmlElement.appendChild(e);

        // Größe schreiben
        e = new Element("groesse");
        ws = rasse.getGroesseWuerfel();
        e.addAttribute(new Attribute("wert", Integer.toString(ws.getFestWert())));
        for (int i = 0; i < ws.getAnzahlWuerfel().length; i++) {
            final Element ee = new Element("wuerfel");
            ee.addAttribute(new Attribute("anzWuerfel", Integer.toString(ws.getAnzahlWuerfel()[i])));
            ee.addAttribute(new Attribute("augenWuerfel", Integer.toString(ws.getAugenWuerfel()[i])));
            e.appendChild(ee);
        }
        xmlElement.appendChild(e);

        // Modifikation des Gewichtes schreiben
        e = new Element("gewichtModi");
        e.addAttribute(new Attribute("wert", Integer.toString(rasse.getGewichtModi())));
        xmlElement.appendChild(e);

        // Haarfarbe schreiben
        e = new Element("haarfarbe");
        farbenToXML(e, rasse.getHaarfarbe());
        xmlElement.appendChild(e);

        // Augenfarbe schreiben
        e = new Element("augenfarbe");
        farbenToXML(e, rasse.getAugenfarbe());
        xmlElement.appendChild(e);

    }

    /**
     * Zum auslesen der Farben (Maximal 20)
     * 
     * @param elements Die xml-elemente mit den angaben
     * @param array Das Array, in das die Farb-Angaben geschrieben werden
     */
    private void farbenFromXML(Elements elements, String[] array) {
        int idx = 0;
        for (int i = 0; i < elements.size(); i++) {
            final int anteil = Integer.parseInt(elements.get(i).getAttributeValue("anteil"));

            for (int ii = 0; ii < anteil; ii++) {
                array[idx] = elements.get(i).getAttributeValue("farbe");
                idx++;
            }
        }

        if (idx < 20) {
            ProgAdmin.logger.warning("Index zu klein: Sollwert von 20 wurde nicht erreicht!");
        }
    }

    /**
     * Zum schreiben der Farben nach XML
     * 
     * @param xmlElement Als Xml-Element, zu dem geschrieben wird.
     * @param array Das array mit allen Farben (max 20) als Array
     */
    private void farbenToXML(Element xmlElement, String[] array) {
        String currentFarbe = array[0];
        int idx = 0;

        for (int i = 0; i < array.length; i++) {
            if (!currentFarbe.equals(array[i])) {
                final Element element = new Element("farbe");
                element.addAttribute(new Attribute("farbe", currentFarbe));
                element.addAttribute(new Attribute("anteil", Integer.toString(idx)));
                xmlElement.appendChild(element);

                idx = 1; // Für nächsten Durchlauf
                currentFarbe = array[i]; // Für nächsten Durchlauf,
            } else {
                idx++;
            }
        }

        // Letztes Element "nachtragen"
        final Element element = new Element("farbe");
        element.addAttribute(new Attribute("farbe", currentFarbe));
        element.addAttribute(new Attribute("anteil", Integer.toString(idx)));
        xmlElement.appendChild(element);
    }
}
