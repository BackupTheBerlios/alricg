/*
 * Created 23. Januar 2005 / 15:30:43
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.Held;

import org.d3s.alricg.CharKomponenten.Rasse;
import org.d3s.alricg.CharKomponenten.Kultur;
import org.d3s.alricg.CharKomponenten.Profession;
import java.util.ArrayList;

/**
 * <b>Beschreibung:</b><br>
 * Repräsentiert einen Charakter der erstellt wird bzw. erstellt wurde.
 * @author V.Strelow
 */
public class Held {
	private Profession profession;
	private Kultur kultur;
	private Rasse rasse;

    // Gewählte, OHNE auswahl durch die Herkunft
    private ArrayList nachteile;
    private ArrayList vorteile;
    private ArrayList sonderf;

}
