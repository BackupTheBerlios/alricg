/*
 * Created on 23.06.2005 / 15:16:17
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */

package org.d3s.alricg.store.xom.map;

import nu.xom.Element;

import org.d3s.alricg.charKomponenten.CharElement;

/**
 * Mapping eines (xom-xml) <code>Element</code> in ein <code>CharElement</code> und zurück.
 * 
 * @author <a href="mailto:msturzen@mac.com">St. Martin</a>
 */
interface XOMMapper {

    /**
     * Bildet die Daten des xom-Modells in Instanzen der <code>CharElement</code>-Hierarchie ab.
     * Wird auch als Vorwärtsmapping bezeichnet.
     * @param xmlElement Das in ein <code>CharElement</code> abzubildende <code>Element</code>.
     * @param charElement Das zu befüllende <code>CharElement</code>.
     */
    void map(Element xmlElement, CharElement charElement);

    /**
     * Bildet die Daten einer Instanz der <code>CharElement</code>-Hierarchie in eine entsprechende Struktur des xom-Modells ab. 
     * Wird auch als Rückwärtsmapping bezeichnet.
     * @param charElement Das in ein <code>Element</code> abzubildende <code>CharElement</code>.
     * @param xmlElement Das zu befüllende <code>Element</code>.
     */
    void map(CharElement charElement, Element xmlElement);
}
