/*
 * Created 26. Dezember 2004 / 23:45:31
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Sprache extends SchriftSprache {
	private int komplexNichtMutterSpr;
	private int kostenNichtMutterSpr;
	private Schrift[] zugehoerigeSchrift;	

	/**
	 * @return Liefert das Attribut zugehoerigeSchrift.
	 */
	public Schrift[] getZugehoerigeSchrift() {
		return zugehoerigeSchrift;
	}
	
	/**
	 * @return Liefert das Attribut komplexNichtMutterSpr.
	 */
	public int getKomplexNichtMutterSpr() {
		return komplexNichtMutterSpr;
	}
	/**
	 * @return Liefert das Attribut kostenNichtMutterSpr.
	 */
	public int getKostenNichtMutterSpr() {
		return kostenNichtMutterSpr;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
	 */
	public void loadXmlElement(Element xmlElement) {
		this.loadXmlElementSchriftSprache(xmlElement);
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
	 */
	public Element writeXmlElement() {
		Element xmlElem = null;
		
		this.writeXmlElementSchriftSprache(xmlElem);
		// TODO Auto-generated method stub
		return null;
	}
}
