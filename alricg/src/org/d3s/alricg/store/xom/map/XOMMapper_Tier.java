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

class XOMMapper_Tier extends XOMMapper_CharElement implements XOMMapper {

    public void map(Element xmlElement, CharElement charElement) {
        super.map(xmlElement, charElement);
    }

    public void map(CharElement charElement, Element xmlElement) {
        super.map(charElement, xmlElement);        
        xmlElement.setLocalName("tier");
    }
}
