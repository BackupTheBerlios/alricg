/*
 * Created 26. Dezember 2004 / 23:10:42
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.charKomponenten.links;

import org.d3s.alricg.charKomponenten.CharElement;

/**
 * <b>Beschreibung:</b><br>
 * TODO Beschreibung einfügen
 * 
 * @author V.Strelow
 */
public class Voraussetzung {
    private IdLinkVoraussetzung[] festeVoraussetzung; // Die unveränderlichen Werte

    private IdLinkList nichtErlaubt; // Diese Elemente sind NICHT erlaubt

    private CharElement quelle; // Das CharElement, das diese Voraussetzung besitzt
    // Pro Array muß mindesten eine LinkVoraussetzung erfüllt sein

    private IdLinkVoraussetzung[][] auswahlVoraussetzung;

    /**
     * Konstruktor
     * 
     * @param quelle Das CharElement, bei dem diese Voraussetzung erfüllt sein muß
     */
    public Voraussetzung(CharElement quelle) {
        this.quelle = quelle;
    }

    /**
     * @return Liefert das Attribut festeVoraussetzung.
     */
    public IdLinkVoraussetzung[] getFesteVoraussetzung() {
        return festeVoraussetzung;
    }

    /**
     * @param festeVoraussetzung Setzt das Attribut festeVoraussetzung.
     */
    public void setFesteVoraussetzung(IdLinkVoraussetzung[] festeVoraussetzung) {
        this.festeVoraussetzung = festeVoraussetzung;
    }

    /**
     * @return Liefert das Attribut quelle.
     */
    public CharElement getQuelle() {
        return quelle;
    }

    /**
     * @param quelle Setzt das Attribut quelle.
     */
    public void setQuelle(CharElement quelle) {
        this.quelle = quelle;
    }

    /**
     * <u>Beschreibung:</u><br>
     * Beschreib eine Verbindung zwischen einer Voraussetzung und den Elementen, die vorausgesetzt werden. IdLink wird
     * hierbei erweitert um ein flag, ob ein Grenzwert ein Minimum ist, oder ein Maximum.
     * 
     * @author V. Strelow
     */
    public class IdLinkVoraussetzung extends IdLink {
        private boolean isMinimum = true; // Ob der Grenzwert ein Minimum oder maximum ist

        public IdLinkVoraussetzung(CharElement quelle) {
            super(quelle, null);
        }

        /**
         * @return Liefert das Attribut isMinimum.
         */
        public boolean isMinimum() {
            return isMinimum;
        }

        /**
         * @param isMinimum Setzt das Attribut isMinimum.
         */
        public void setMinimum(boolean isMinimum) {
            this.isMinimum = isMinimum;
        }
    }

    public IdLinkVoraussetzung[][] getAuswahlVoraussetzung() {
        return auswahlVoraussetzung;
    }

    public void setAuswahlVoraussetzung(IdLinkVoraussetzung[][] auswahlVoraussetzung) {
        this.auswahlVoraussetzung = auswahlVoraussetzung;
    }

    public IdLinkList getNichtErlaubt() {
        return nichtErlaubt;
    }

    public void setNichtErlaubt(IdLinkList nichtErlaubt) {
        this.nichtErlaubt = nichtErlaubt;
    }

}
