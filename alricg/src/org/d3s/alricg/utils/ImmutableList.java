/*
 * Created on 28.04.2005 / 17:24:45
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * <u>Beschreibung:</u><br> 
 * Stellt eine einfache Liste da, ohne die M�glichkeit diese Liste zu ver�ndern.
 * Damit soll gesichter werden, da� die Liste selbst weitergegeben werden kann, ohne das 
 * diese ver�ndert wird.
 * Sinnvoll an allen Stellen wo Objekte Listen weitergeben, diese Listen jedoch nicht 
 * ver�ndert werden sollen (Es k�nnen jedoch die Elemente ver�ndert werden).
 * 
 * @author V. Strelow
 */
public class ImmutableList<E> implements Collection<E>{
	private List<E> liste; // Die Liste die angezeigt wird
	
	public ImmutableList(List<E> liste) {
		if (liste == null) {
			// so wird "NullPointerExceptions" vorgebeugt!
			liste = new ArrayList<E>(0);
		}
		this.liste = liste;
	}
	
	public ImmutableList(Collection<E> liste) {
		this(new ArrayList<E>(liste));
	}
	
	/** Methode �berschrieben
	 * @see java.util.Collection#add(E)
	 * Diese Methode ist NICHT Implementiert (optional operation)
	 */
	public boolean add(E arg0) {
		throw new UnsupportedOperationException("Methode wird nicht unterst�tzt!");
	}
	/** Methode �berschrieben
	 * @see java.util.Collection#addAll(java.util.Collection)
	 * Diese Methode ist NICHT Implementiert (optional operation)
	 */
	public boolean addAll(Collection< ? extends E> arg0) {
		throw new UnsupportedOperationException("Methode wird nicht unterst�tzt!");
	}
	
	/** Methode �berschrieben
	 * @see java.util.Collection#clear()
	 * Diese Methode ist NICHT Implementiert (optional operation)
	 */
	public void clear() {
		throw new UnsupportedOperationException("Methode wird nicht unterst�tzt!");
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object object) {
		return liste.contains(object);
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection< ? > collection) {
		return liste.containsAll(collection);
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty() {
		return liste.isEmpty();
	}
	
	/**
	 * Liefert das Element an der Position "index"
	 * @param index Die position in der Liste des gew�nschten Elements
	 * @return Das Element an der Position "index"
	 * @throws IndexOutOfBoundsException - wenn (index < 0 || index >= size()).
	 */
	public E get(int index) {
		return liste.get(index);
	}
	
	/** Methode �berschrieben
	 * @see java.lang.Iterable#iterator()
	 * Da hier erst eine Kopie der Liste Erstellt wird, k�nnen Elemente aus dem Iterator 
	 * auch gel�scht werden. Ist jedoch aufwendig, wegen Kopie. Besser 
	 */
	public Iterator<E> iterator() {
		// Mu�te �ber eine Kopie gel�st werden, da iterator �ber remove verf�gt
		return toArrayList().iterator();
	}
	
	/** Methode �berschrieben
	 * @see java.util.Collection#remove(java.lang.Object)
	 * Diese Methode ist NICHT Implementiert (optional operation)
	 */
	public boolean remove(Object arg0) {
		throw new UnsupportedOperationException("Methode wird nicht unterst�tzt!");
	}
	
	/** Methode �berschrieben
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 * Diese Methode ist NICHT Implementiert (optional operation)
	 */
	public boolean removeAll(Collection< ? > arg0) {
		throw new UnsupportedOperationException("Methode wird nicht unterst�tzt!");
	}
	
	/** Methode �berschrieben
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 * Diese Methode ist NICHT Implementiert (optional operation)
	 */
	public boolean retainAll(Collection< ? > arg0) {
		throw new UnsupportedOperationException("Methode wird nicht unterst�tzt!");
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see java.util.Collection#size()
	 */
	public int size() {
		return liste.size();
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see java.util.Collection#toArray()
	 */
	public Object[] toArray() {
		return liste.toArray();
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see java.util.Collection#toArray(T[])
	 */
	public <T> T[] toArray(T[] arg0) {
		return liste.toArray(arg0);
	}
	
	/**
	 * Erstellt eine Kopie dieser Liste. Die Elemente der Liste sind KEINE Kopie,
	 * doch die Liste selbst, es d�rfen also Elemente aus der Liste entfernd oder
	 * hinzugef�gt werden.
	 * F�r die Elemente gilt jedoch die selbe Vorsicht wie bei den anderen Methoden!
	 * @return Eine Kopie der Liste, die bearbeitet werden kann.
	 */
	public ArrayList<E> toArrayList() {
		ArrayList<E> tmpList = new ArrayList<E>(liste.size());
		
		for (int i = 0; i < liste.size(); i++) {
			tmpList.add(liste.get(i));
		}
		
		return tmpList;
	}
}
