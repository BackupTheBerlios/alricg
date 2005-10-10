/*
 * Created on 10.10.2005 / 10:48:59
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://www.alricg.de/".
 */
package org.d3s.alricg.store.xom;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.store.FactoryFinder;

public class XOMStoreObjectMother {

	public <T extends CharElement> void add(CharKomponente komponente,
			String[] keys, T[] elements) {
		data().put(komponente, keys, elements);
	}

	XOMStoreMock data() {
		return (XOMStoreMock) FactoryFinder.find().getData();
	}

	XOMConfigStoreMock config() {
		return (XOMConfigStoreMock) FactoryFinder.find().getConfiguration();
	}

	XOMTextStoreMock text() {
		return (XOMTextStoreMock) FactoryFinder.find().getLibrary();
	}

}
