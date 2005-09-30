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

interface XOMMapper {

    /** 
     * Maps from the <code>xom</code> to the <code>CharElement</code> model
     * @param xmlElement
     * @param charElement
     */
    void map(Element xmlElement, CharElement charElement);

    /**
     * Maps from the <code>CharElement</code> to the <code>xom</code> model.
     * @param charElement
     * @param xmlElement
     */
    void map(CharElement charElement, Element xmlElement);
}
