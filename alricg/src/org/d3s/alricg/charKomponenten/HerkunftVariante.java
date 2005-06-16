/*
 * Created on 11.06.2005 / 15:31:36
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.charKomponenten;

import org.d3s.alricg.charKomponenten.links.IdLinkList;

/**
 * <u>Beschreibung:</u><br> 
 * Dieses Interface Beschreibt die Strucktur der Varianten von Rasse, Kultur und
 * Profession. Alle drei Varianten-Klassen implementieren dieses interface. 
 *
 * @author V. Strelow
 */
public interface HerkunftVariante {
	
	/**
	 * @return Liefert das Attribut entferneElement.
	 */
	public IdLinkList getEntferneElement();
	
	/**
	 * @param entferneElement Setzt das Attribut entferneElement.
	 */
	public void setEntferneElement(IdLinkList entferneElement);
	
	/**
	 * @return Liefert das Attribut entferneXmlTag.
	 */
	public String[] getEntferneXmlTag();
	
	/**
	 * @param entferneXmlTag Setzt das Attribut entferneXmlTag.
	 */
	public void setEntferneXmlTag(String[] entferneXmlTag);
	
	/**
	 * @return Liefert das Attribut isMultibel.
	 */
	public boolean isMultibel();
	
	/**
	 * @param isMultibel Setzt das Attribut isMultibel.
	 */
	public void setMultibel(boolean isMultibel);
	
	/**
	 * @return Liefert das Attribut varianteVon.
	 */
	public Herkunft getVarianteVon();
	
	/**
	 * @param varianteVon Setzt das Attribut varianteVon.
	 */
	public void setVarianteVon(Herkunft varianteVon);
	
	/**
	 * @return Liefert das Attribut isAdditionsVariante.
	 */
	public boolean isAdditionsVariante();
	
	/**
	 * @param isAdditionsVariante Setzt das Attribut isAdditionsVariante.
	 */
	public void setAdditionsVariante(boolean isAdditionsVariante);
	
}
