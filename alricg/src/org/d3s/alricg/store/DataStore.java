package org.d3s.alricg.store;

import java.util.Collection;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.controller.CharKomponente;

public interface DataStore {

    // void read();
    //	
    // void update();
    //	
    // void delete();
    //	
    // void save();

    CharElement getCharElement(String id);

    CharElement getCharElement(String id, CharKomponente charKomp);

    CharKomponente getCharKompFromId(String id);

    CharKomponente getCharKompFromPrefix(String prefix);

    Collection<CharElement> getUnmodifieableCollection(CharKomponente charKomp);
}
