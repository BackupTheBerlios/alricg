/*
 * Created 20. Januar 2005 / 17:08:35
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten.CharZusatz;

import nu.xom.Element;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class Ruestung extends Gegenstand {
	private int gRs;
	private int gBf;
	private int zoneKo;
	private int zoneBr;
	private int zoneRue;
	private int zoneBa;
	private int zoneLa;
	private int zoneRa;
	private int zoneLb;
	private int zoneRb;
	private int zoneges;
	
	/**
	 * @return Liefert das Attribut gBf.
	 */
	public int getGBf() {
		return gBf;
	}
	/**
	 * @return Liefert das Attribut gRs.
	 */
	public int getGRs() {
		return gRs;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#loadXmlElement(nu.xom.Element)
	 */
	public void loadXmlElement(Element xmlElement) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.CharKomponenten.CharElement#writeXmlElement()
	 */
	public Element writeXmlElement() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
