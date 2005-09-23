package org.d3s.alricg.store.xom.map;

import java.util.ArrayList;
import java.util.logging.Level;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.charKomponenten.charZusatz.Waffe;
import org.d3s.alricg.charKomponenten.charZusatz.WuerfelSammlung;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.store.FactoryFinder;

abstract class XOMMapper_Waffe extends XOMMapper_Gegenstand implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {

        super.map(xmlElement, charElement);

        // my mapping
        final Waffe waffe = (Waffe) charElement;

        // Trefferpunkte
        try {
            Element child = xmlElement.getFirstChildElement("tp");
            final ArrayList<Integer> arrayAugen = new ArrayList<Integer>(1);
            final ArrayList<Integer> arrayWuerfel = new ArrayList<Integer>(1);
            if (child != null) {
                Attribute attr = child.getAttribute("W6");
                if (attr != null) {
                    arrayWuerfel.add(Integer.valueOf(attr.getValue()));
                    arrayAugen.add(6);
                }
                attr = child.getAttribute("W20");
                if (attr != null) {
                    arrayWuerfel.add(Integer.valueOf(attr.getValue()));
                    arrayAugen.add(20);
                }

                int[] augenAugen = new int[arrayAugen.size()];
                int[] augenWuerfel = new int[arrayWuerfel.size()];
                for (int i = 0; i < arrayWuerfel.size(); i++) {
                    augenAugen[i] = arrayAugen.get(i);
                    augenWuerfel[i] = arrayWuerfel.get(i);
                }

                final int plus = Integer.parseInt(child.getAttributeValue("plus"));
                waffe.setTP(new WuerfelSammlung(plus, augenWuerfel, augenAugen));
            }

            // Länge
            child = xmlElement.getFirstChildElement("eigenschaften");
            Attribute attr = child.getAttribute("laenge");
            if (child != null && attr != null) {
                waffe.setLaenge(Integer.parseInt(attr.getValue()));
            }

            // ini
            attr = child.getAttribute("ini");
            if (child != null && attr != null) {
                waffe.setIni(Integer.parseInt(attr.getValue()));
            }
        } catch (NumberFormatException exc) {
            ProgAdmin.logger.log(Level.SEVERE, "String -> int failed", exc);
        }

        // Talente, mit denen die Waffe geführt werden kann
        Elements children = xmlElement.getChildElements("talentId");
        Talent[] talente = new Talent[children.size()];
        for (int i = 0; i < talente.length; i++) {
            final String value = children.get(i).getValue();
            talente[i] = (Talent) FactoryFinder.find().getData().getCharElement(value, FactoryFinder.find().getData().getCharKompFromId(value));
        }
        waffe.setTalent(talente);
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);

        // my mapping
        final Waffe waffe = (Waffe) charElement;

        // Trefferpunkte der Waffe
        WuerfelSammlung tp = waffe.getTP();
        if (tp != null) {
            Element e = new Element("tp");
            for (int i = 0; i < tp.getAugenWuerfel().length; i++) {
                if (tp.getAugenWuerfel()[i] == 6) {
                    final String value = Integer.toString(tp.getAnzahlWuerfel()[i]);
                    final Attribute a = new Attribute("W6", value);
                    e.addAttribute(a);

                } else if (tp.getAugenWuerfel()[i] == 20) {
                    final String value = Integer.toString(tp.getAnzahlWuerfel()[i]);
                    final Attribute a = new Attribute("W20", value);
                    e.addAttribute(a);
                }
            }
            final String value = Integer.toString(tp.getFestWert());
            e.addAttribute(new Attribute("plus", value));
            xmlElement.appendChild(e);
        }

        // länge der Waffe
        int laenge = waffe.getLaenge();
        Element e = null;
        if (laenge != CharElement.KEIN_WERT) {
            e = new Element("eigenschaften");
            e.addAttribute(new Attribute("laenge", Integer.toString(laenge)));
        }

        // Ini Bouns
        int ini = waffe.getIni();
        if (ini != CharElement.KEIN_WERT) {
            if (e == null) {
                e = new Element("eigenschaften");
            }
            e.addAttribute(new Attribute("ini", Integer.toString(ini)));
        }

        if (e != null) {
            xmlElement.appendChild(e);
        }

        // Talente
        Talent[] talente = waffe.getTalent();
        for (int i = 0; i < talente.length; i++) {
            Element e2 = new Element("talentId");
            e2.appendChild(talente[i].getId());
            xmlElement.appendChild(e2);
        }
    }
}
